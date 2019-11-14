/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.batch.engine.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.BatchEngineImportTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.headless.delivery.dto.v1_0.BlogPosting;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Ivica Cardic
 */
@RunWith(Arquillian.class)
public class BatchEngineImportTaskExecutorTest
	extends BaseBatchEngineTaskExecutorTest {

	@BeforeClass
	public static void setUpClass() {
		_fieldNamesMappingMap = HashMapBuilder.put(
			"alternativeHeadline1", "alternativeHeadline"
		).put(
			"articleBody1", "articleBody"
		).put(
			"datePublished1", "datePublished"
		).put(
			"headline1", "headline"
		).put(
			"siteId1", "siteId"
		).build();
	}

	@Test
	public void testCreateBlogPostingsFromCSVFile() {
		_importBlogPostings(
			BatchEngineTaskOperation.CREATE,
			_getBlogPostingsCSVCreateContent(group.getGroupId(), FIELD_NAMES),
			"CSV", null);

		_assertCreatedBlogPostings();
	}

	@Test
	public void testCreateBlogPostingsFromCSVFileWithFieldMappings() {
		_importBlogPostings(
			BatchEngineTaskOperation.CREATE,
			_getBlogPostingsCSVCreateContent(
				group.getGroupId(), _ALTERNATE_FIELD_NAMES),
			"CSV", _fieldNamesMappingMap);

		_assertCreatedBlogPostings();
	}

	@Test
	public void testCreateBlogPostingsFromInvalidCSVFile() {
		StringBundler sb = new StringBundler();

		_createCSVRow(
			sb, FIELD_NAMES[0], FIELD_NAMES[1], FIELD_NAMES[2], FIELD_NAMES[3],
			FIELD_NAMES[4], "unknownColumn");

		_createCSVRow(
			sb, "alternativeHeadline", "articleBody",
			dateFormat.format(new Date(baseDate.getTime())), "headline",
			String.valueOf(group.getGroupId()), "unknownValue");

		String content = sb.toString();

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_CLASS_NAME_BATCH_ENGINE_IMPORT_TASK_EXECUTOR_IMPL,
					Level.ERROR)) {

			_importBlogPostings(
				BatchEngineTaskOperation.CREATE,
				content.getBytes(StandardCharsets.UTF_8), "CSV", null);

			_assertInvalidFile(captureAppender);
		}
	}

	@Test
	public void testCreateBlogPostingsFromInvalidJSONFile() {
		StringBundler sb = new StringBundler();

		sb.append(StringPool.OPEN_BRACKET);

		_createJSONRow(
			sb, FIELD_NAMES[0], _toJSONValue("alternativeHeadline"),
			FIELD_NAMES[1], _toJSONValue("articleBody"), FIELD_NAMES[2],
			_toJSONValue(dateFormat.format(new Date(baseDate.getTime()))),
			FIELD_NAMES[3], _toJSONValue("headline"), FIELD_NAMES[4],
			String.valueOf(group.getGroupId()), "unknownColumn",
			_toJSONValue("unknownValue"));

		sb.append(StringPool.CLOSE_BRACKET);

		String content = sb.toString();

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_CLASS_NAME_BATCH_ENGINE_IMPORT_TASK_EXECUTOR_IMPL,
					Level.ERROR)) {

			_importBlogPostings(
				BatchEngineTaskOperation.CREATE,
				content.getBytes(StandardCharsets.UTF_8), "JSON", null);

			_assertInvalidFile(captureAppender);
		}
	}

	@Test
	public void testCreateBlogPostingsFromInvalidJSONLFile() {
		StringBundler sb = new StringBundler();

		_createJSONRow(
			sb, FIELD_NAMES[0], _toJSONValue("alternativeHeadline"),
			FIELD_NAMES[1], _toJSONValue("articleBody"), FIELD_NAMES[2],
			_toJSONValue(dateFormat.format(new Date(baseDate.getTime()))),
			FIELD_NAMES[3], _toJSONValue("headline"), FIELD_NAMES[4],
			String.valueOf(group.getGroupId()), "unknownColumn",
			_toJSONValue("unknownValue"));

		String content = sb.toString();

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_CLASS_NAME_BATCH_ENGINE_IMPORT_TASK_EXECUTOR_IMPL,
					Level.ERROR)) {

			_importBlogPostings(
				BatchEngineTaskOperation.CREATE,
				content.getBytes(StandardCharsets.UTF_8), "JSONL", null);

			_assertInvalidFile(captureAppender);
		}
	}

	@Test
	public void testCreateBlogPostingsFromInvalidXLSFile() throws Exception {
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook();

		Sheet sheet = xssfWorkbook.createSheet();

		_createXLSRow(
			sheet.createRow(0), FIELD_NAMES[0], FIELD_NAMES[1], FIELD_NAMES[2],
			FIELD_NAMES[3], FIELD_NAMES[4], "unknownColumn");

		_createXLSRow(
			sheet.createRow(1), "alternativeHeadline", "articleBody",
			dateFormat.format(new Date(baseDate.getTime())), "headline",
			group.getGroupId(), "unknownValue");

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_CLASS_NAME_BATCH_ENGINE_IMPORT_TASK_EXECUTOR_IMPL,
					Level.ERROR)) {

			_importBlogPostings(
				BatchEngineTaskOperation.CREATE, _toContent(xssfWorkbook),
				"XLS", null);

			_assertInvalidFile(captureAppender);
		}
	}

	@Test
	public void testCreateBlogPostingsFromJSONFile() {
		_importBlogPostings(
			BatchEngineTaskOperation.CREATE,
			_getBlogPostingsJSONCreateContent(group.getGroupId(), FIELD_NAMES),
			"JSON", null);

		_assertCreatedBlogPostings();
	}

	@Test
	public void testCreateBlogPostingsFromJSONFileWithFieldMappings() {
		_importBlogPostings(
			BatchEngineTaskOperation.CREATE,
			_getBlogPostingsJSONCreateContent(
				group.getGroupId(), _ALTERNATE_FIELD_NAMES),
			"JSON", _fieldNamesMappingMap);

		_assertCreatedBlogPostings();
	}

	@Test
	public void testCreateBlogPostingsFromJSONLFile() {
		_importBlogPostings(
			BatchEngineTaskOperation.CREATE,
			_getBlogPostingsJSONLCreateContent(group.getGroupId(), FIELD_NAMES),
			"JSONL", null);

		_assertCreatedBlogPostings();
	}

	@Test
	public void testCreateBlogPostingsFromJSONLFileWithFieldMappings() {
		_importBlogPostings(
			BatchEngineTaskOperation.CREATE,
			_getBlogPostingsJSONLCreateContent(
				group.getGroupId(), _ALTERNATE_FIELD_NAMES),
			"JSONL", _fieldNamesMappingMap);

		_assertCreatedBlogPostings();
	}

	@Test
	public void testCreateBlogPostingsFromXLSFile() throws Exception {
		_importBlogPostings(
			BatchEngineTaskOperation.CREATE,
			_getBlogPostingsXLSCreateContent(group.getGroupId(), FIELD_NAMES),
			"XLS", null);

		_assertCreatedBlogPostings();
	}

	@Test
	public void testCreateBlogPostingsFromXLSFileWithFieldMappings()
		throws Exception {

		_importBlogPostings(
			BatchEngineTaskOperation.CREATE,
			_getBlogPostingsXLSCreateContent(
				group.getGroupId(), _ALTERNATE_FIELD_NAMES),
			"XLS", _fieldNamesMappingMap);

		_assertCreatedBlogPostings();
	}

	@Test
	public void testDeleteBlogPostingsFromCSVFile() throws Exception {
		List<BlogsEntry> blogsEntries = addBlogsEntries();

		Assert.assertEquals(
			ROWS_COUNT, blogsEntryLocalService.getBlogsEntriesCount());

		_importBlogPostings(
			BatchEngineTaskOperation.DELETE,
			_getBlogPostingsCSVDeleteContent(blogsEntries), "CSV", null);

		Assert.assertEquals(0, blogsEntryLocalService.getBlogsEntriesCount());
	}

	@Test
	public void testDeleteBlogPostingsFromJSONFile() throws Exception {
		List<BlogsEntry> blogsEntries = addBlogsEntries();

		Assert.assertEquals(
			ROWS_COUNT, blogsEntryLocalService.getBlogsEntriesCount());

		_importBlogPostings(
			BatchEngineTaskOperation.DELETE,
			_getBlogPostingsJSONDeleteContent(blogsEntries), "JSON", null);

		Assert.assertEquals(0, blogsEntryLocalService.getBlogsEntriesCount());
	}

	@Test
	public void testDeleteBlogPostingsFromJSONLFile() throws Exception {
		List<BlogsEntry> blogsEntries = addBlogsEntries();

		Assert.assertEquals(
			ROWS_COUNT, blogsEntryLocalService.getBlogsEntriesCount());

		_importBlogPostings(
			BatchEngineTaskOperation.DELETE,
			_getBlogPostingsJSONLDeleteContent(blogsEntries), "JSONL", null);

		Assert.assertEquals(0, blogsEntryLocalService.getBlogsEntriesCount());
	}

	@Test
	public void testDeleteBlogPostingsFromXLSFile() throws Exception {
		List<BlogsEntry> blogsEntries = addBlogsEntries();

		Assert.assertEquals(
			ROWS_COUNT, blogsEntryLocalService.getBlogsEntriesCount());

		_importBlogPostings(
			BatchEngineTaskOperation.DELETE,
			_getBlogPostingsXLSDeleteContent(blogsEntries), "XLS", null);

		Assert.assertEquals(0, blogsEntryLocalService.getBlogsEntriesCount());
	}

	@Test
	public void testUpdateBlogPostingsFromCSVFile() throws Exception {
		List<BlogsEntry> blogsEntries = addBlogsEntries();

		Assert.assertEquals(
			ROWS_COUNT, blogsEntryLocalService.getBlogsEntriesCount());

		_importBlogPostings(
			BatchEngineTaskOperation.UPDATE,
			_getBlogPostingsCSVUpdateContent(blogsEntries), "CSV", null);

		_assertUpdatedBlogPostings();
	}

	@Test
	public void testUpdateBlogPostingsFromJSONFile() throws Exception {
		List<BlogsEntry> blogsEntries = addBlogsEntries();

		Assert.assertEquals(
			ROWS_COUNT, blogsEntryLocalService.getBlogsEntriesCount());

		_importBlogPostings(
			BatchEngineTaskOperation.UPDATE,
			_getBlogPostingsJSONUpdateContent(blogsEntries), "JSON", null);

		_assertUpdatedBlogPostings();
	}

	@Test
	public void testUpdateBlogPostingsFromJSONLFile() throws Exception {
		List<BlogsEntry> blogsEntries = addBlogsEntries();

		Assert.assertEquals(
			ROWS_COUNT, blogsEntryLocalService.getBlogsEntriesCount());

		_importBlogPostings(
			BatchEngineTaskOperation.UPDATE,
			_getBlogPostingsJSONLUpdateContent(blogsEntries), "JSONL", null);

		_assertUpdatedBlogPostings();
	}

	@Test
	public void testUpdateBlogPostingsFromXLSFile() throws Exception {
		List<BlogsEntry> blogsEntries = addBlogsEntries();

		Assert.assertEquals(
			ROWS_COUNT, blogsEntryLocalService.getBlogsEntriesCount());

		_importBlogPostings(
			BatchEngineTaskOperation.UPDATE,
			_getBlogPostingsXLSUpdateContent(blogsEntries), "XLS", null);

		_assertUpdatedBlogPostings();
	}

	private void _assertCreatedBlogPostings() {
		Assert.assertEquals(
			ROWS_COUNT, blogsEntryLocalService.getBlogsEntriesCount());

		List<BlogsEntry> blogsEntries = new ArrayList<>(
			blogsEntryLocalService.getBlogsEntries(
				0, blogsEntryLocalService.getBlogsEntriesCount()));

		blogsEntries.sort(Comparator.comparingLong(BlogsEntry::getEntryId));

		for (int i = 0; i < blogsEntries.size(); i++) {
			BlogsEntry blogsEntry = blogsEntries.get(i);

			Assert.assertEquals(
				"alternativeHeadline" + i, blogsEntry.getSubtitle());
			Assert.assertEquals("articleBody" + i, blogsEntry.getContent());
			Assert.assertEquals(
				_toTime(baseDate, i), _toTime(blogsEntry.getDisplayDate(), 0));
			Assert.assertEquals("headline" + i, blogsEntry.getTitle());
		}
	}

	private void _assertInvalidFile(CaptureAppender captureAppender) {
		Assert.assertEquals(0, blogsEntryLocalService.getBlogsEntriesCount());

		List<LoggingEvent> loggingEvents = captureAppender.getLoggingEvents();

		Assert.assertEquals(loggingEvents.toString(), 1, loggingEvents.size());

		LoggingEvent loggingEvent = loggingEvents.get(0);

		Assert.assertEquals(Level.ERROR, loggingEvent.getLevel());

		String message = (String)loggingEvent.getMessage();

		Assert.assertTrue(
			message.startsWith("Unable to update batch engine import task"));
	}

	private void _assertUpdatedBlogPostings() {
		Assert.assertEquals(
			ROWS_COUNT, blogsEntryLocalService.getBlogsEntriesCount());

		List<BlogsEntry> blogsEntries = new ArrayList<>(
			blogsEntryLocalService.getBlogsEntries(
				0, blogsEntryLocalService.getBlogsEntriesCount()));

		blogsEntries.sort(Comparator.comparingLong(BlogsEntry::getEntryId));

		for (int i = 0; i < blogsEntries.size(); i++) {
			BlogsEntry blogsEntry = blogsEntries.get(i);

			Assert.assertEquals(
				"alternativeHeadline" + i + i, blogsEntry.getSubtitle());
			Assert.assertEquals("articleBody" + i + i, blogsEntry.getContent());
			Assert.assertEquals(
				_toTime(baseDate, i), _toTime(blogsEntry.getDisplayDate(), 0));
			Assert.assertEquals("headline" + i + i, blogsEntry.getTitle());
		}
	}

	private void _createCSVRow(StringBundler sb, String... values) {
		for (String value : values) {
			sb.append(value);
			sb.append(StringPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		sb.append(StringPool.NEW_LINE);
	}

	private void _createJSONRow(StringBundler sb, String... values) {
		sb.append(StringPool.OPEN_CURLY_BRACE);

		for (int i = 0; i < values.length; i = i + 2) {
			sb.append(StringPool.QUOTE);
			sb.append(values[i]);
			sb.append("\": ");
			sb.append(values[i + 1]);

			sb.append(StringPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		sb.append(StringPool.CLOSE_CURLY_BRACE);
	}

	private void _createXLSRow(Row row, Object... values) {
		for (int i = 0; i < values.length; i++) {
			Cell cell = row.createCell(i);

			if (values[i] instanceof Boolean) {
				cell.setCellValue((Boolean)values[i]);
			}
			else if (values[i] instanceof Date) {
				cell.setCellValue((Date)values[i]);
			}
			else if (values[i] instanceof Number) {
				Number value = (Number)values[i];

				cell.setCellValue(value.doubleValue());
			}
			else {
				cell.setCellValue((String)values[i]);
			}
		}
	}

	private byte[] _getBlogPostingsCSVCreateContent(
		long siteId, String[] fieldNames) {

		StringBundler sb = new StringBundler();

		_createCSVRow(sb, fieldNames);

		for (int i = 0; i < ROWS_COUNT; i++) {
			_createCSVRow(
				sb, "alternativeHeadline" + i, "articleBody" + i,
				dateFormat.format(new Date(_toTime(baseDate, i))),
				"headline" + i, String.valueOf(siteId));
		}

		return _toContent(sb);
	}

	private byte[] _getBlogPostingsCSVDeleteContent(
		List<BlogsEntry> blogsEntries) {

		StringBundler sb = new StringBundler();

		_createCSVRow(sb, "id");

		for (BlogsEntry blogsEntry : blogsEntries) {
			_createCSVRow(sb, String.valueOf(blogsEntry.getEntryId()));
		}

		return _toContent(sb);
	}

	private byte[] _getBlogPostingsCSVUpdateContent(
		List<BlogsEntry> blogsEntries) {

		StringBundler sb = new StringBundler();

		_createCSVRow(
			sb, "alternativeHeadline", "articleBody", "datePublished",
			"headline", "id");

		for (int i = 0; i < blogsEntries.size(); i++) {
			BlogsEntry blogsEntry = blogsEntries.get(i);

			_createCSVRow(
				sb, blogsEntry.getSubtitle() + i, blogsEntry.getContent() + i,
				dateFormat.format(
					new Date(_toTime(blogsEntry.getDisplayDate(), i))),
				blogsEntry.getTitle() + i,
				String.valueOf(blogsEntry.getEntryId()));
		}

		return _toContent(sb);
	}

	private byte[] _getBlogPostingsJSONCreateContent(
		long siteId, String[] fieldNames) {

		StringBundler sb = new StringBundler();

		sb.append(StringPool.OPEN_BRACKET);

		for (int i = 0; i < ROWS_COUNT; i++) {
			_createJSONRow(
				sb, fieldNames[0], _toJSONValue("alternativeHeadline" + i),
				fieldNames[1], _toJSONValue("articleBody" + i), fieldNames[2],
				_toJSONValue(dateFormat.format(new Date(_toTime(baseDate, i)))),
				fieldNames[3], _toJSONValue("headline" + i), fieldNames[4],
				String.valueOf(siteId));

			sb.append(StringPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		sb.append(StringPool.CLOSE_BRACKET);

		return _toContent(sb);
	}

	private byte[] _getBlogPostingsJSONDeleteContent(
		List<BlogsEntry> blogsEntries) {

		StringBundler sb = new StringBundler();

		sb.append(StringPool.OPEN_BRACKET);

		for (BlogsEntry blogsEntry : blogsEntries) {
			_createJSONRow(sb, "id", String.valueOf(blogsEntry.getEntryId()));

			sb.append(StringPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		sb.append(StringPool.CLOSE_BRACKET);

		return _toContent(sb);
	}

	private byte[] _getBlogPostingsJSONLCreateContent(
		long siteId, String[] fieldNames) {

		StringBundler sb = new StringBundler();

		for (int i = 0; i < ROWS_COUNT; i++) {
			_createJSONRow(
				sb, fieldNames[0], _toJSONValue("alternativeHeadline" + i),
				fieldNames[1], _toJSONValue("articleBody" + i), fieldNames[2],
				_toJSONValue(dateFormat.format(new Date(_toTime(baseDate, i)))),
				fieldNames[3], _toJSONValue("headline" + i), fieldNames[4],
				String.valueOf(siteId));

			sb.append(StringPool.NEW_LINE);
		}

		sb.setIndex(sb.index() - 1);

		return _toContent(sb);
	}

	private byte[] _getBlogPostingsJSONLDeleteContent(
		List<BlogsEntry> blogsEntries) {

		StringBundler sb = new StringBundler();

		for (BlogsEntry blogsEntry : blogsEntries) {
			_createJSONRow(sb, "id", String.valueOf(blogsEntry.getEntryId()));

			sb.append(StringPool.NEW_LINE);
		}

		sb.setIndex(sb.index() - 1);

		return _toContent(sb);
	}

	private byte[] _getBlogPostingsJSONLUpdateContent(
		List<BlogsEntry> blogsEntries) {

		StringBundler sb = new StringBundler();

		for (int i = 0; i < blogsEntries.size(); i++) {
			BlogsEntry blogsEntry = blogsEntries.get(i);

			_createJSONRow(
				sb, FIELD_NAMES[0], _toJSONValue(blogsEntry.getSubtitle() + i),
				FIELD_NAMES[1], _toJSONValue(blogsEntry.getContent() + i),
				FIELD_NAMES[2],
				_toJSONValue(
					dateFormat.format(
						new Date(_toTime(blogsEntry.getDisplayDate(), i)))),
				FIELD_NAMES[3], _toJSONValue(blogsEntry.getTitle() + i), "id",
				String.valueOf(blogsEntry.getEntryId()));

			sb.append(StringPool.NEW_LINE);
		}

		sb.setIndex(sb.index() - 1);

		return _toContent(sb);
	}

	private byte[] _getBlogPostingsJSONUpdateContent(
		List<BlogsEntry> blogsEntries) {

		StringBundler sb = new StringBundler();

		sb.append(StringPool.OPEN_BRACKET);

		for (int i = 0; i < blogsEntries.size(); i++) {
			BlogsEntry blogsEntry = blogsEntries.get(i);

			_createJSONRow(
				sb, FIELD_NAMES[0], _toJSONValue(blogsEntry.getSubtitle() + i),
				FIELD_NAMES[1], _toJSONValue(blogsEntry.getContent() + i),
				FIELD_NAMES[2],
				_toJSONValue(
					dateFormat.format(
						new Date(_toTime(blogsEntry.getDisplayDate(), i)))),
				FIELD_NAMES[3], _toJSONValue(blogsEntry.getTitle() + i), "id",
				String.valueOf(blogsEntry.getEntryId()));

			sb.append(StringPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		sb.append(StringPool.CLOSE_BRACKET);

		return _toContent(sb);
	}

	private byte[] _getBlogPostingsXLSCreateContent(
			long siteId, String[] fieldNames)
		throws IOException {

		XSSFWorkbook xssfWorkbook = new XSSFWorkbook();

		Sheet sheet = xssfWorkbook.createSheet();

		_createXLSRow(
			sheet.createRow(0), fieldNames[0], fieldNames[1], fieldNames[2],
			fieldNames[3], fieldNames[4]);

		for (int i = 0; i < ROWS_COUNT; i++) {
			_createXLSRow(
				sheet.createRow(i + 1), "alternativeHeadline" + i,
				"articleBody" + i,
				dateFormat.format(new Date(_toTime(baseDate, i))),
				"headline" + i, siteId);
		}

		return _toContent(xssfWorkbook);
	}

	private byte[] _getBlogPostingsXLSDeleteContent(
			List<BlogsEntry> blogsEntries)
		throws IOException {

		XSSFWorkbook xssfWorkbook = new XSSFWorkbook();

		Sheet sheet = xssfWorkbook.createSheet();

		_createXLSRow(sheet.createRow(0), "id");

		for (int i = 0; i < blogsEntries.size(); i++) {
			BlogsEntry blogsEntry = blogsEntries.get(i);

			_createXLSRow(sheet.createRow(i + 1), blogsEntry.getEntryId());
		}

		return _toContent(xssfWorkbook);
	}

	private byte[] _getBlogPostingsXLSUpdateContent(
			List<BlogsEntry> blogsEntries)
		throws IOException {

		XSSFWorkbook xssfWorkbook = new XSSFWorkbook();

		Sheet sheet = xssfWorkbook.createSheet();

		_createXLSRow(
			sheet.createRow(0), FIELD_NAMES[0], FIELD_NAMES[1], FIELD_NAMES[2],
			FIELD_NAMES[3], "id");

		for (int i = 0; i < blogsEntries.size(); i++) {
			BlogsEntry blogsEntry = blogsEntries.get(i);

			_createXLSRow(
				sheet.createRow(i + 1), blogsEntry.getSubtitle() + i,
				blogsEntry.getContent() + i,
				dateFormat.format(
					new Date(_toTime(blogsEntry.getDisplayDate(), i))),
				blogsEntry.getTitle() + i, blogsEntry.getEntryId());
		}

		return _toContent(xssfWorkbook);
	}

	private void _importBlogPostings(
		BatchEngineTaskOperation batchEngineTaskOperation, byte[] content,
		String contentType, Map<String, String> fieldNameMappingMap) {

		_batchEngineImportTask =
			_batchEngineImportTaskLocalService.addBatchEngineImportTask(
				user.getCompanyId(), user.getUserId(), 10, null,
				BlogPosting.class.getName(), content, contentType,
				BatchEngineTaskExecuteStatus.INITIAL.name(),
				fieldNameMappingMap, batchEngineTaskOperation.name(), null,
				"v1.0");

		_batchEngineImportTaskExecutor.execute(_batchEngineImportTask);
	}

	private byte[] _toContent(StringBundler sb) {
		String content = sb.toString();

		return content.getBytes(StandardCharsets.UTF_8);
	}

	private byte[] _toContent(XSSFWorkbook xssfWorkbook) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		xssfWorkbook.write(byteArrayOutputStream);

		xssfWorkbook.close();

		try {
			return byteArrayOutputStream.toByteArray();
		}
		finally {
			byteArrayOutputStream.close();
		}
	}

	private String _toJSONValue(String value) {
		return StringPool.QUOTE + value + StringPool.QUOTE;
	}

	private long _toTime(Date date, int index) {
		return date.getTime() + index * Time.MINUTE;
	}

	private static final String[] _ALTERNATE_FIELD_NAMES = {
		"alternativeHeadline1", "articleBody1", "datePublished1", "headline1",
		"siteId1"
	};

	private static final String
		_CLASS_NAME_BATCH_ENGINE_IMPORT_TASK_EXECUTOR_IMPL =
			"com.liferay.batch.engine.internal." +
				"BatchEngineImportTaskExecutorImpl";

	private static Map<String, String> _fieldNamesMappingMap;

	@DeleteAfterTestRun
	private BatchEngineImportTask _batchEngineImportTask;

	@Inject
	private BatchEngineImportTaskExecutor _batchEngineImportTaskExecutor;

	@Inject
	private BatchEngineImportTaskLocalService
		_batchEngineImportTaskLocalService;

}
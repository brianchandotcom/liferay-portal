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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.BatchEngineExportTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.batch.engine.service.BatchEngineExportTaskLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.headless.delivery.dto.v1_0.BlogPosting;
import com.liferay.petra.io.unsync.UnsyncBufferedReader;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
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
public class BatchEngineExportTaskExecutorTest
	extends BaseBatchEngineTaskExecutorTest {

	@BeforeClass
	public static void setUpClass() {
		_objectMapper.addMixIn(BlogPosting.class, BlogPostingMixin.class);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();

		_parameters = HashMapBuilder.<String, Serializable>put(
			"siteId", group.getGroupId()
		).build();
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		if (_batchEngineExportTask != null) {
			_batchEngineExportTaskLocalService.deleteBatchEngineExportTask(
				_batchEngineExportTask.getBatchEngineExportTaskId());
		}
	}

	@Test
	public void testExportBlogPostingsToCSVFileWithEmptyFieldNames()
		throws Exception {

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_CLASS_NAME_BATCH_ENGINE_EXPORT_TASK_EXECUTOR_IMPL,
					Level.ERROR)) {

			_testExportBlogPostingsToCSVFile(
				Collections.emptyList(), line -> new Object[0], _parameters);

			_assertEmptyFieldNames(captureAppender);
		}
	}

	@Test
	public void testExportBlogPostingsToCSVFileWithFieldNames()
		throws Exception {

		_testExportBlogPostingsToCSVFile(
			Arrays.asList("articleBody", "datePublished", "headline", "id"),
			_csvFilterFunction, _parameters);
	}

	@Test
	public void testExportBlogPostingsToCSVFileWithFilterParameter()
		throws Exception {

		_parameters.put("filter", "headline eq 'headline1'");

		List<BlogsEntry> blogsEntries = addBlogsEntries();

		Assert.assertEquals(
			ROWS_COUNT, blogsEntryLocalService.getBlogsEntriesCount());

		List<String> fieldNames = Arrays.asList(
			"articleBody", "datePublished", "headline", "id");

		_exportBlogPostings("CSV", fieldNames, _parameters);

		BatchEngineExportTask batchEngineExportTask =
			_batchEngineExportTaskLocalService.getBatchEngineExportTask(
				_batchEngineExportTask.getBatchEngineExportTaskId());

		_assertExportedValues(
			Collections.singletonList(blogsEntries.get(1)), fieldNames,
			_readRowValuesList(_csvFilterFunction, batchEngineExportTask));
	}

	@Test
	public void testExportBlogPostingsToCSVFileWithSortParameter()
		throws Exception {

		_parameters.put("sort", "headline:desc'");

		List<BlogsEntry> blogsEntries = addBlogsEntries();

		Assert.assertEquals(
			ROWS_COUNT, blogsEntryLocalService.getBlogsEntriesCount());

		List<String> fieldNames = Arrays.asList(
			"articleBody", "datePublished", "headline", "id");

		_exportBlogPostings("CSV", fieldNames, _parameters);

		BatchEngineExportTask batchEngineExportTask =
			_batchEngineExportTaskLocalService.getBatchEngineExportTask(
				_batchEngineExportTask.getBatchEngineExportTaskId());

		blogsEntries.sort(
			Comparator.comparing(
				BlogsEntry::getEntryId,
				(entryId1, entryId2) -> -entryId1.compareTo(entryId2)));

		_assertExportedValues(
			blogsEntries, fieldNames,
			_readRowValuesList(_csvFilterFunction, batchEngineExportTask));
	}

	@Test
	public void testExportBlogPostingsToJSONFileWithEmptyFieldNames()
		throws Exception {

		_testExportBlogPostingsToJSONFile(
			Collections.emptyList(),
			blogPosting -> new Object[] {
				blogPosting.getAlternativeHeadline(),
				blogPosting.getArticleBody(), blogPosting.getDatePublished(),
				blogPosting.getHeadline(), blogPosting.getId(),
				blogPosting.getSiteId()
			},
			_parameters);
	}

	@Test
	public void testExportBlogPostingsToJSONFileWithFieldNames()
		throws Exception {

		_testExportBlogPostingsToJSONFile(
			Arrays.asList(
				"alternativeHeadline", "datePublished", "headline", "id"),
			blogPosting -> new Object[] {
				blogPosting.getAlternativeHeadline(),
				blogPosting.getDatePublished(), blogPosting.getHeadline(),
				blogPosting.getId()
			},
			_parameters);
	}

	@Test
	public void testExportBlogPostingsToJSONLFileWithEmptyFieldNames()
		throws Exception {

		_testExportBlogPostingsToJSONLFile(
			Collections.emptyList(),
			blogPosting -> new Object[] {
				blogPosting.getAlternativeHeadline(),
				blogPosting.getArticleBody(), blogPosting.getDatePublished(),
				blogPosting.getHeadline(), blogPosting.getId(),
				blogPosting.getSiteId()
			},
			_parameters);
	}

	@Test
	public void testExportBlogPostingsToJSONLFileWithFieldNames()
		throws Exception {

		_testExportBlogPostingsToJSONLFile(
			Arrays.asList(
				"alternativeHeadline", "datePublished", "headline", "id"),
			blogPosting -> new Object[] {
				blogPosting.getAlternativeHeadline(),
				blogPosting.getDatePublished(), blogPosting.getHeadline(),
				blogPosting.getId()
			},
			HashMapBuilder.<String, Serializable>put(
				"siteId", group.getGroupId()
			).build());
	}

	@Test
	public void testExportBlogPostingsToXLSFileWithEmptyFieldNames()
		throws Exception {

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_CLASS_NAME_BATCH_ENGINE_EXPORT_TASK_EXECUTOR_IMPL,
					Level.ERROR)) {

			_testExportBlogPostingsToXLSFile(
				Collections.emptyList(), rowValues -> new Object[0],
				HashMapBuilder.<String, Serializable>put(
					"siteId", group.getGroupId()
				).build());
		}
	}

	@Test
	public void testExportBlogPostingsToXLSFileWithFieldNames()
		throws Exception {

		_testExportBlogPostingsToXLSFile(
			Arrays.asList("articleBody", "datePublished", "headline", "id"),
			rowValues -> new Object[] {
				rowValues[0], rowValues[1], rowValues[2], rowValues[3]
			},
			HashMapBuilder.<String, Serializable>put(
				"siteId", group.getGroupId()
			).build());
	}

	public abstract class BlogPostingMixin {

		@JsonProperty(access = JsonProperty.Access.READ_WRITE)
		public Long id;

		@JsonProperty(access = JsonProperty.Access.READ_WRITE)
		public Long siteId;

		@JsonProperty(access = JsonProperty.Access.READ_WRITE)
		protected Date dateCreated;

	}

	private void _assertEmptyFieldNames(CaptureAppender captureAppender) {
		List<LoggingEvent> loggingEvents = captureAppender.getLoggingEvents();

		Assert.assertEquals(loggingEvents.toString(), 1, loggingEvents.size());

		LoggingEvent loggingEvent = loggingEvents.get(0);

		Assert.assertEquals(Level.ERROR, loggingEvent.getLevel());

		String message = (String)loggingEvent.getMessage();

		Assert.assertTrue(
			message.startsWith("Unable to update batch engine export task"));
	}

	private void _assertExportedValues(
			List<BlogsEntry> blogsEntries, List<String> fieldNames,
			List<Object[]> rowValuesList)
		throws ParseException {

		blogsEntries.sort(Comparator.comparing(BlogsEntry::getSubtitle));
		rowValuesList.sort(
			Comparator.comparing(rowValues -> (String)rowValues[0]));

		Set<String> fieldNamesSet = new HashSet<>(fieldNames);

		for (int i = 0; i < blogsEntries.size(); i++) {
			BlogsEntry blogsEntry = blogsEntries.get(i);
			Object[] rowValues = rowValuesList.get(i);

			int index = 0;

			if (fieldNamesSet.isEmpty() ||
				fieldNamesSet.contains(FIELD_NAMES[0])) {

				Assert.assertEquals(
					blogsEntry.getSubtitle(), rowValues[index++]);
			}

			if (fieldNamesSet.isEmpty() ||
				fieldNamesSet.contains(FIELD_NAMES[1])) {

				Assert.assertEquals(
					blogsEntry.getContent(), rowValues[index++]);
			}

			if (fieldNamesSet.isEmpty() ||
				fieldNamesSet.contains(FIELD_NAMES[2])) {

				Object value = rowValues[index++];

				if (value instanceof String) {
					value = dateFormat.parse((String)value);
				}

				Assert.assertEquals(blogsEntry.getDisplayDate(), value);
			}

			if (fieldNamesSet.isEmpty() ||
				fieldNamesSet.contains(FIELD_NAMES[3])) {

				Assert.assertEquals(blogsEntry.getTitle(), rowValues[index++]);
			}

			if (fieldNamesSet.isEmpty() || fieldNamesSet.contains("id")) {
				Object value = rowValues[index++];

				if (value instanceof String) {
					value = GetterUtil.getLong(value);
				}

				if (value instanceof Double) {
					Double doubleValue = (Double)value;

					value = doubleValue.longValue();
				}

				Assert.assertEquals(blogsEntry.getEntryId(), value);
			}

			if (fieldNamesSet.isEmpty() || fieldNamesSet.contains("siteId")) {
				Object value = rowValues[index];

				if (value instanceof String) {
					value = GetterUtil.getLong(value);
				}

				if (value instanceof Double) {
					Double doubleValue = (Double)value;

					value = doubleValue.longValue();
				}

				Assert.assertEquals(blogsEntry.getGroupId(), value);
			}
		}
	}

	private void _exportBlogPostings(
		String contentType, List<String> fieldNames,
		Map<String, Serializable> parameters) {

		parameters.put("siteId", group.getGroupId());

		_batchEngineExportTask =
			_batchEngineExportTaskLocalService.addBatchEngineExportTask(
				user.getCompanyId(), user.getUserId(), null,
				BlogPosting.class.getName(), contentType,
				BatchEngineTaskExecuteStatus.INITIAL.name(), fieldNames,
				parameters);

		_batchEngineExportTaskExecutor.execute(_batchEngineExportTask);
	}

	private ZipInputStream _getZipInputStream(InputStream inputStream)
		throws IOException {

		ZipInputStream zipInputStream = new ZipInputStream(inputStream);

		zipInputStream.getNextEntry();

		return zipInputStream;
	}

	private List<Object[]> _readRowValuesList(
			Function<String, Object[]> filterFunction,
			BatchEngineExportTask batchEngineExportTask)
		throws IOException {

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new InputStreamReader(
				_getZipInputStream(
					_batchEngineExportTaskLocalService.openContentInputStream(
						batchEngineExportTask.getBatchEngineExportTaskId()))));

		unsyncBufferedReader.readLine();

		String line = null;
		List<Object[]> rowValuesList = new ArrayList<>();

		while ((line = unsyncBufferedReader.readLine()) != null) {
			rowValuesList.add(filterFunction.apply(line));
		}

		return rowValuesList;
	}

	private void _testExportBlogPostingsToCSVFile(
			List<String> fieldNames, Function<String, Object[]> filterFunction,
			Map<String, Serializable> parameters)
		throws Exception {

		List<BlogsEntry> blogsEntries = addBlogsEntries();

		Assert.assertEquals(
			ROWS_COUNT, blogsEntryLocalService.getBlogsEntriesCount());

		_exportBlogPostings("CSV", fieldNames, parameters);

		BatchEngineExportTask batchEngineExportTask =
			_batchEngineExportTaskLocalService.getBatchEngineExportTask(
				_batchEngineExportTask.getBatchEngineExportTaskId());

		if (fieldNames.isEmpty()) {
			Assert.assertEquals(
				BatchEngineTaskExecuteStatus.FAILED.toString(),
				batchEngineExportTask.getExecuteStatus());

			return;
		}

		_assertExportedValues(
			blogsEntries, fieldNames,
			_readRowValuesList(filterFunction, batchEngineExportTask));
	}

	private void _testExportBlogPostingsToJSONFile(
			List<String> fieldNames,
			Function<BlogPosting, Object[]> filterFunction,
			Map<String, Serializable> parameters)
		throws Exception {

		List<BlogsEntry> blogsEntries = addBlogsEntries();

		Assert.assertEquals(
			ROWS_COUNT, blogsEntryLocalService.getBlogsEntriesCount());

		_exportBlogPostings("JSON", fieldNames, parameters);

		BatchEngineExportTask batchEngineExportTask =
			_batchEngineExportTaskLocalService.getBatchEngineExportTask(
				_batchEngineExportTask.getBatchEngineExportTaskId());

		List<BlogPosting> blogPostings = _objectMapper.readValue(
			_getZipInputStream(
				_batchEngineExportTaskLocalService.openContentInputStream(
					batchEngineExportTask.getBatchEngineExportTaskId())),
			new TypeReference<List<BlogPosting>>() {
			});

		List<Object[]> rowValuesList = new ArrayList<>();

		for (BlogPosting blogPosting : blogPostings) {
			rowValuesList.add(filterFunction.apply(blogPosting));
		}

		_assertExportedValues(blogsEntries, fieldNames, rowValuesList);
	}

	private void _testExportBlogPostingsToJSONLFile(
			List<String> fieldNames,
			Function<BlogPosting, Object[]> filterFunction,
			Map<String, Serializable> parameters)
		throws Exception {

		List<BlogsEntry> blogsEntries = addBlogsEntries();

		Assert.assertEquals(
			ROWS_COUNT, blogsEntryLocalService.getBlogsEntriesCount());

		_exportBlogPostings("JSONL", fieldNames, parameters);

		BatchEngineExportTask batchEngineExportTask =
			_batchEngineExportTaskLocalService.getBatchEngineExportTask(
				_batchEngineExportTask.getBatchEngineExportTaskId());

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new InputStreamReader(
				_getZipInputStream(
					_batchEngineExportTaskLocalService.openContentInputStream(
						batchEngineExportTask.getBatchEngineExportTaskId()))));

		List<BlogPosting> blogPostings = new ArrayList<>();
		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			blogPostings.add(
				_objectMapper.readValue(
					line,
					new TypeReference<BlogPosting>() {
					}));
		}

		List<Object[]> rowValuesList = new ArrayList<>();

		for (BlogPosting blogPosting : blogPostings) {
			rowValuesList.add(filterFunction.apply(blogPosting));
		}

		_assertExportedValues(blogsEntries, fieldNames, rowValuesList);
	}

	private void _testExportBlogPostingsToXLSFile(
			List<String> fieldNames,
			Function<Object[], Object[]> filterFunction,
			Map<String, Serializable> parameters)
		throws Exception {

		List<BlogsEntry> blogsEntries = addBlogsEntries();

		Assert.assertEquals(
			ROWS_COUNT, blogsEntryLocalService.getBlogsEntriesCount());

		_exportBlogPostings("XLS", fieldNames, parameters);

		BatchEngineExportTask batchEngineExportTask =
			_batchEngineExportTaskLocalService.getBatchEngineExportTask(
				_batchEngineExportTask.getBatchEngineExportTaskId());

		if (fieldNames.isEmpty()) {
			Assert.assertEquals(
				BatchEngineTaskExecuteStatus.FAILED.toString(),
				batchEngineExportTask.getExecuteStatus());

			return;
		}

		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(
			_getZipInputStream(
				_batchEngineExportTaskLocalService.openContentInputStream(
					batchEngineExportTask.getBatchEngineExportTaskId())));

		Sheet sheet = xssfWorkbook.getSheetAt(0);

		Iterator<Row> rowIterator = sheet.iterator();

		rowIterator.next();

		List<Object[]> rowValuesList = new ArrayList<>();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			List<Object> rowValues = new ArrayList<>();

			for (Cell cell : row) {
				if (CellType.BOOLEAN == cell.getCellType()) {
					rowValues.add(cell.getBooleanCellValue());
				}
				else if (CellType.NUMERIC == cell.getCellType()) {
					if (DateUtil.isCellDateFormatted(cell)) {
						rowValues.add(cell.getDateCellValue());
					}
					else {
						rowValues.add(cell.getNumericCellValue());
					}
				}
				else {
					rowValues.add(cell.getStringCellValue());
				}
			}

			rowValuesList.add(filterFunction.apply(rowValues.toArray()));
		}

		_assertExportedValues(blogsEntries, fieldNames, rowValuesList);
	}

	private static final String
		_CLASS_NAME_BATCH_ENGINE_EXPORT_TASK_EXECUTOR_IMPL =
			"com.liferay.batch.engine.internal." +
				"BatchEngineExportTaskExecutorImpl";

	private static final ObjectMapper _objectMapper = new ObjectMapper();

	private BatchEngineExportTask _batchEngineExportTask;

	@Inject
	private BatchEngineExportTaskExecutor _batchEngineExportTaskExecutor;

	@Inject
	private BatchEngineExportTaskLocalService
		_batchEngineExportTaskLocalService;

	private final Function<String, Object[]> _csvFilterFunction = line -> {
		String[] values = StringUtil.split(line, CharPool.COMMA);

		return new Object[] {values[0], values[1], values[2], values[3]};
	};

	private Map<String, Serializable> _parameters;

}
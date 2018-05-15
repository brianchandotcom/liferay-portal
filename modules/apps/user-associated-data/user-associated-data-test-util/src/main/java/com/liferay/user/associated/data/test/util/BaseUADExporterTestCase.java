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

package com.liferay.user.associated.data.test.util;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.user.associated.data.exporter.UADExporter;

import java.io.ByteArrayInputStream;
import java.io.File;

import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Pei-Jung Lan
 */
public abstract class BaseUADExporterTestCase<T extends BaseModel> {

	@Before
	public void setUp() throws Exception {
		uadExporter = getUADExporter();
		user = UserTestUtil.addUser();
	}

	@Test
	public void testExport() throws Exception {
		T baseModel = addBaseModel(user.getUserId());

		Document document = getExportDocument(baseModel);

		assertColumnValue(document, "userId", String.valueOf(user.getUserId()));
		assertColumnValue(
			document, getPrimaryKeyName(),
			String.valueOf(baseModel.getPrimaryKeyObj()));
	}

	@Test
	public void testExportAll() throws Exception {
		addBaseModel(user.getUserId());

		File file = uadExporter.exportAll(user.getUserId());

		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(file);

		List<String> entries = zipReader.getEntries();

		Assert.assertEquals(entries.toString(), 1, entries.size());
	}

	@Test
	public void testExportByStatusByUserId() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasStatusByUserIdField);

		WhenHasStatusByUserIdField<T> whenHasStatusByUserIdField =
			(WhenHasStatusByUserIdField)this;

		T baseModel = whenHasStatusByUserIdField.addBaseModelWithStatusByUserId(
			TestPropsValues.getUserId(), user.getUserId());

		Document document = getExportDocument(baseModel);

		assertColumnValue(
			document, "statusByUserId", String.valueOf(user.getUserId()));
		assertColumnValue(
			document, getPrimaryKeyName(),
			String.valueOf(baseModel.getPrimaryKeyObj()));
	}

	protected abstract T addBaseModel(long userId) throws Exception;

	protected void assertColumnValue(
		Document document, String columnName, String expectedColumnValue) {

		String nodeSelector =
			"/model/column[column-name='" + columnName + "']/column-value";

		Node columnNode = document.selectSingleNode(nodeSelector);

		Assert.assertEquals(expectedColumnValue, columnNode.getText());
	}

	protected Document getExportDocument(T baseModel) throws Exception {
		byte[] bytes = uadExporter.export(baseModel);

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			bytes);

		return SAXReaderUtil.read(byteArrayInputStream);
	}

	protected abstract String getPrimaryKeyName();

	protected abstract UADExporter<T> getUADExporter();

	protected UADExporter<T> uadExporter;

	@DeleteAfterTestRun
	protected User user;

}
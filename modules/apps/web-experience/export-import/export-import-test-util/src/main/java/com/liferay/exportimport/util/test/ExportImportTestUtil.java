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

package com.liferay.exportimport.util.test;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipWriter;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class ExportImportTestUtil {

	public static PortletDataContext getExportPortletDataContext()
		throws Exception {

		return getExportPortletDataContext(TestPropsValues.getGroupId());
	}

	public static PortletDataContext getExportPortletDataContext(long groupId)
		throws Exception {

		TestReaderWriter testReaderWriter = new TestReaderWriter();

		Document document = SAXReaderUtil.createDocument();

		Element manifestRootElement = document.addElement("root");

		manifestRootElement.addElement("header");

		testReaderWriter.addEntry("/manifest.xml", document.asXML());

		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				TestPropsValues.getCompanyId(), groupId,
				new HashMap<String, String[]>(), null, null, testReaderWriter);

		Element rootElement = SAXReaderUtil.createElement("root");

		portletDataContext.setExportDataRootElement(rootElement);

		Element missingReferencesElement = rootElement.addElement(
			"missing-references");

		portletDataContext.setMissingReferencesElement(
			missingReferencesElement);

		return portletDataContext;
	}

	public static PortletDataContext getImportPortletDataContext()
		throws Exception {

		return getImportPortletDataContext(TestPropsValues.getGroupId());
	}

	public static PortletDataContext getImportPortletDataContext(long groupId)
		throws Exception {

		TestReaderWriter testReaderWriter = new TestReaderWriter();

		Document document = SAXReaderUtil.createDocument();

		Element manifestRootElement = document.addElement("root");

		manifestRootElement.addElement("header");

		testReaderWriter.addEntry("/manifest.xml", document.asXML());

		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
				TestPropsValues.getCompanyId(), groupId,
				new HashMap<String, String[]>(), new TestUserIdStrategy(),
				testReaderWriter);

		Element rootElement = SAXReaderUtil.createElement("root");

		portletDataContext.setImportDataRootElement(rootElement);

		Element missingReferencesElement = rootElement.addElement(
			"missing-references");

		portletDataContext.setMissingReferencesElement(
			missingReferencesElement);

		return portletDataContext;
	}

	private static class TestReaderWriter implements ZipReader, ZipWriter {

		@Override
		public void addEntry(String name, byte[] bytes) {
			_binaryEntries.add(name);
		}

		@Override
		public void addEntry(String name, InputStream inputStream) {
			_binaryEntries.add(name);
		}

		@Override
		public void addEntry(String name, String s) {
			_entries.put(name, s);
		}

		@Override
		public void addEntry(String name, StringBuilder sb) {
			_entries.put(name, sb.toString());
		}

		@Override
		public void close() {
		}

		@Override
		public byte[] finish() {
			return new byte[0];
		}

		public List<String> getBinaryEntries() {
			return _binaryEntries;
		}

		@Override
		public List<String> getEntries() {
			return new ArrayList<>(_entries.keySet());
		}

		@Override
		public byte[] getEntryAsByteArray(String name) {
			return null;
		}

		@Override
		public InputStream getEntryAsInputStream(String name) {
			return new InputStream() {

				@Override
				public int read() {
					return -1;
				}

			};
		}

		@Override
		public String getEntryAsString(String name) {
			return _entries.get(name);
		}

		@Override
		public File getFile() {
			return null;
		}

		@Override
		public List<String> getFolderEntries(String path) {
			return null;
		}

		@Override
		public String getPath() {
			return StringPool.BLANK;
		}

		private final List<String> _binaryEntries = new ArrayList<>();
		private final Map<String, String> _entries = new HashMap<>();

	}

	private static class TestUserIdStrategy implements UserIdStrategy {

		@Override
		public long getUserId(String userUuid) {
			try {
				return TestPropsValues.getUserId();
			}
			catch (Exception e) {
				return 0;
			}
		}

	}

}
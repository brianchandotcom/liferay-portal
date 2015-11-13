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

package com.liferay.document.library.lar.xstream;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.repository.proxy.FileEntryProxyBean;
import com.liferay.portal.repository.proxy.FileVersionProxyBean;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.exportimport.xstream.BaseXStreamConverter;
import com.liferay.portlet.exportimport.xstream.XStreamHierarchicalStreamReader;
import com.liferay.portlet.exportimport.xstream.XStreamUnmarshallingContext;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Akos Thurzo
 */
public class FileEntryConverter extends BaseXStreamConverter {

	@Override
	public boolean canConvert(Class<?> clazz) {
		return clazz.equals(FileEntryProxyBean.class);
	}

	@Override
	public Object unmarshal(
			XStreamHierarchicalStreamReader xStreamHierarchicalStreamReader,
			XStreamUnmarshallingContext xStreamUnmarshallingContext)
		throws Exception {

		DLFileEntry dlFileEntry = new DLFileEntryImpl();
		boolean escapedModel = false;
		LiferayFileVersion liferayFileVersion = null;

		while (xStreamHierarchicalStreamReader.hasMoreChildren()) {
			xStreamHierarchicalStreamReader.moveDown();

			String nodeName = xStreamHierarchicalStreamReader.getNodeName();

			Class<?> clazz = BeanPropertiesUtil.getObjectType(
				dlFileEntry, nodeName);

			if (nodeName.equals(FieldConstants.FILE_VERSION)) {
				clazz = FileVersionProxyBean.class;
			}

			Object convertedValue = xStreamUnmarshallingContext.convertAnother(
				xStreamHierarchicalStreamReader.getValue(), clazz);

			if (fields.contains(nodeName)) {
				if (nodeName.equals(FieldConstants.ESCAPED_MODEL)) {
					escapedModel = (Boolean)convertedValue;
				}
				else if (nodeName.equals(FieldConstants.FILE_VERSION)) {
					liferayFileVersion = (LiferayFileVersion)convertedValue;
				}
				else {
					BeanPropertiesUtil.setProperty(
						dlFileEntry, nodeName, convertedValue);
				}
			}

			xStreamHierarchicalStreamReader.moveUp();
		}

		LiferayFileEntry liferayFileEntry = new LiferayFileEntry(
			dlFileEntry, escapedModel);

		liferayFileEntry.setCachedFileVersion(liferayFileVersion);

		return liferayFileEntry;
	}

	@Override
	protected List<String> getFields() {
		return fields;
	}

	protected static List<String> fields = new LinkedList<>();

	static {
		fields.add(FieldConstants.COMPANY_ID);
		fields.add(FieldConstants.CREATE_DATE);
		fields.add(FieldConstants.DESCRIPTION);
		fields.add(FieldConstants.ESCAPED_MODEL);
		fields.add(FieldConstants.EXTENSION);
		fields.add(FieldConstants.FILE_ENTRY_ID);
		fields.add(FieldConstants.FILE_VERSION);
		fields.add(FieldConstants.FOLDER_ID);
		fields.add(FieldConstants.GROUP_ID);
		fields.add(FieldConstants.MANUAL_CHECK_IN_REQUIRED);
		fields.add(FieldConstants.MIME_TYPE);
		fields.add(FieldConstants.MODIFIED_DATE);
		fields.add(FieldConstants.READ_COUNT);
		fields.add(FieldConstants.REPOSITORY_ID);
		fields.add(FieldConstants.SIZE);
		fields.add(FieldConstants.TITLE);
		fields.add(FieldConstants.USER_ID);
		fields.add(FieldConstants.USER_NAME);
		fields.add(FieldConstants.USER_UUID);
		fields.add(FieldConstants.UUID);
		fields.add(FieldConstants.VERSION);
	}

}
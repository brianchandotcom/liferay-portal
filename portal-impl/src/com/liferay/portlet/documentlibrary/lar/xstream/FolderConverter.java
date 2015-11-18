/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.lar.xstream;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.lar.xstream.BaseXStreamConverter;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.repository.proxy.FolderProxyBean;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;

import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Akos Thurzo
 */
public class FolderConverter extends BaseXStreamConverter {

	@Override
	public boolean canConvert(Class clazz) {
		return clazz.equals(FolderProxyBean.class);
	}

	@Override
	public Object unmarshal(
		HierarchicalStreamReader hierarchicalStreamReader,
		UnmarshallingContext unmarshallingContext) {

		DLFolder dlFolder = new DLFolderImpl();
		boolean escapedModel = false;

		while (hierarchicalStreamReader.hasMoreChildren()) {
			hierarchicalStreamReader.moveDown();

			String nodeName = hierarchicalStreamReader.getNodeName();

			Class<?> clazz = BeanPropertiesUtil.getObjectType(
				dlFolder, nodeName);

			Object convertedValue = unmarshallingContext.convertAnother(
				hierarchicalStreamReader.getValue(), clazz);

			if (fields.contains(nodeName)) {
				if (nodeName.equals(FieldConstants.ESCAPED_MODEL)) {
					escapedModel = (Boolean)convertedValue;
				}
				else {
					BeanPropertiesUtil.setProperty(
						dlFolder, nodeName, convertedValue);
				}
			}

			hierarchicalStreamReader.moveUp();
		}

		return new LiferayFolder(dlFolder, escapedModel);
	}

	@Override
	protected List<String> getFields() {
		return fields;
	}

	protected static List<String> fields = new LinkedList<String>();

	static {
		fields.add(FieldConstants.COMPANY_ID);
		fields.add(FieldConstants.CREATE_DATE);
		fields.add(FieldConstants.DESCRIPTION);
		fields.add(FieldConstants.ESCAPED_MODEL);
		fields.add(FieldConstants.FOLDER_ID);
		fields.add(FieldConstants.GROUP_ID);
		fields.add(FieldConstants.LAST_POST_DATE);
		fields.add(FieldConstants.MODIFIED_DATE);
		fields.add(FieldConstants.MOUNT_POINT);
		fields.add(FieldConstants.NAME);
		fields.add(FieldConstants.PARENT_FOLDER_ID);
		fields.add(FieldConstants.REPOSITORY_ID);
		fields.add(FieldConstants.USER_ID);
		fields.add(FieldConstants.USER_NAME);
		fields.add(FieldConstants.USER_UUID);
		fields.add(FieldConstants.UUID);
	}

}
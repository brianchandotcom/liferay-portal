/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.lar.xstream;

import com.liferay.document.library.internal.lar.xstream.constants.FieldConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.exportimport.kernel.xstream.BaseXStreamConverter;
import com.liferay.exportimport.kernel.xstream.XStreamHierarchicalStreamReader;
import com.liferay.exportimport.kernel.xstream.XStreamUnmarshallingContext;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.repository.proxy.FileVersionProxyBean;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionImpl;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Akos Thurzo
 */
public class FileVersionConverter extends BaseXStreamConverter {

	@Override
	public boolean canConvert(Class<?> clazz) {
		return clazz.equals(FileVersionProxyBean.class);
	}

	@Override
	public Object unmarshal(
			XStreamHierarchicalStreamReader xStreamHierarchicalStreamReader,
			XStreamUnmarshallingContext xStreamUnmarshallingContext)
		throws Exception {

		DLFileVersion dlFileVersion = new DLFileVersionImpl();
		boolean escapedModel = false;

		while (xStreamHierarchicalStreamReader.hasMoreChildren()) {
			xStreamHierarchicalStreamReader.moveDown();

			String nodeName = xStreamHierarchicalStreamReader.getNodeName();

			Class<?> clazz = BeanPropertiesUtil.getObjectType(
				dlFileVersion, nodeName);

			if (fields.contains(nodeName)) {
				Object convertedValue =
					xStreamUnmarshallingContext.convertAnother(
						xStreamHierarchicalStreamReader.getValue(), clazz);

				if (nodeName.equals(FieldConstants.ESCAPED_MODEL)) {
					escapedModel = (Boolean)convertedValue;
				}
				else {
					BeanPropertiesUtil.setProperty(
						dlFileVersion, nodeName, convertedValue);
				}
			}

			xStreamHierarchicalStreamReader.moveUp();
		}

		return new LiferayFileVersion(dlFileVersion, escapedModel);
	}

	@Override
	protected List<String> getFields() {
		return fields;
	}

	protected static List<String> fields = new LinkedList<String>() {
		{
			add(FieldConstants.CHANGE_LOG);
			add(FieldConstants.COMPANY_ID);
			add(FieldConstants.CREATE_DATE);
			add(FieldConstants.DESCRIPTION);
			add(FieldConstants.ESCAPED_MODEL);
			add(FieldConstants.EXTENSION);
			add(FieldConstants.EXTRA_SETTINGS);
			add(FieldConstants.FILE_ENTRY_ID);
			add(FieldConstants.FILE_VERSION_ID);
			add(FieldConstants.GROUP_ID);
			add(FieldConstants.MIME_TYPE);
			add(FieldConstants.MODIFIED_DATE);
			add(FieldConstants.REPOSITORY_ID);
			add(FieldConstants.SIZE);
			add(FieldConstants.STATUS);
			add(FieldConstants.STATUS_BY_USER_ID);
			add(FieldConstants.STATUS_BY_USER_NAME);
			add(FieldConstants.STATUS_DATE);
			add(FieldConstants.TITLE);
			add(FieldConstants.USER_ID);
			add(FieldConstants.USER_NAME);
			add(FieldConstants.UUID);
			add(FieldConstants.VERSION);
		}
	};

}
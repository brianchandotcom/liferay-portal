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

package com.liferay.dynamic.data.mapping.uad.util;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.uad.constants.DDMFormInstanceUADConstants;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Time;

import java.io.IOException;
import java.io.StringReader;

import java.util.Date;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Gabriel Ibson
 * @author Marcos Martins
 */
@Component(immediate = true, service = DDMUADHelper.class)
public class DDMUADHelper {

	public void addGroupIdRestriction(
		DynamicQuery dynamicQuery, long[] groupIds) {

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"groupId", ArrayUtil.toLongArray(groupIds)));
	}

	public void formatCreateDateIfExist(Map<String, Object> fieldValues) {
		Date createdDate = (Date)fieldValues.get("createDate");

		if (createdDate != null) {
			fieldValues.put(
				"createDate",
				Time.getSimpleDate(
					createdDate,
					DDMFormInstanceUADConstants.
						DEFAULT_DDM_FORM_INSTANCE_DATE_FORMAT));
		}
	}

	public String getDDMFormInstanceFormattedName(
		DDMFormInstance ddmFormInstance) {

		Document document = toXMLDocument(ddmFormInstance.getName());

		Node firstChildNode = document.getFirstChild();

		return firstChildNode.getTextContent();
	}

	public Document toXMLDocument(String xml) {
		try {
			DocumentBuilderFactory documentBuilderFactory =
				SecureXMLFactoryProviderUtil.newDocumentBuilderFactory();

			DocumentBuilder documentBuilder =
				documentBuilderFactory.newDocumentBuilder();

			return documentBuilder.parse(
				new InputSource(new StringReader(xml)));
		}
		catch (IOException | ParserConfigurationException | SAXException
					exception) {

			_log.error(exception, exception);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(DDMUADHelper.class);

	@Reference
	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;

}
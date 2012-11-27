/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.io.IOException;

import java.util.Locale;

/**
 * @author Bruno Basto
 * @author Brian Wing Shun Chan
 */
public class DDMXMLUtil {

	public static String formatXML(Document document) throws IOException {
		return getDDMXML().formatXML(document);
	}

	public static String formatXML(String xml)
		throws DocumentException, IOException {

		return getDDMXML().formatXML(xml);
	}

	public static DDMXML getDDMXML() {
		PortalRuntimePermission.checkGetBeanProperty(DDMXMLUtil.class);

		return _ddmXML;
	}

	public static String getXML(Fields fields) throws Exception {
		return getDDMXML().getXML(fields);
	}

	public static String getXML(
			long ddmContentId, Fields fields, boolean mergeFields)
		throws Exception {

		return getDDMXML().getXML(ddmContentId, fields, mergeFields);
	}

	public static String updateXMLDefaultLocale(
			String xml, Locale contentDefaultLocale,
			Locale contentNewDefaultLocale)
		throws DocumentException, IOException {

		return getDDMXML().updateXMLDefaultLocale(
			xml, contentDefaultLocale, contentNewDefaultLocale);
	}

	public void setDDMXML(DDMXML ddmXML) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_ddmXML = ddmXML;
	}

	private static DDMXML _ddmXML;

}
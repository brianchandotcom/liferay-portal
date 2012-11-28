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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.util.List;
import java.util.Locale;

/**
 * @author Bruno Basto
 * @author Brian Wing Shun Chan
 */
public interface DDMXML {

	public String formatXML(Document document) throws SystemException;

	public String formatXML(String xml) throws SystemException;

	public Fields getFields(DDMStructure ddmStructure, String xml)
		throws PortalException, SystemException;

	public Fields getFields(
			DDMStructure ddmStructure, XPath conditionXPath, String xml,
			List<String> fieldNames)
		throws PortalException, SystemException;

	public String getXML(Fields fields)
		throws PortalException, SystemException;

	public String getXML(long ddmContentId, Fields fields, boolean mergeFields)
		throws PortalException, SystemException;

	public String updateXMLDefaultLocale(
			String xml, Locale contentDefaultLocale,
			Locale contentNewDefaultLocale)
		throws SystemException;

}
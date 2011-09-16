/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.util.ContentUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public abstract class BaseDefaultDDMStructureAction extends SimpleAction {

	protected void addDDMStructures(
			long userId, long groupId, long classNameId,
			String fileName, ServiceContext serviceContext)
		throws DocumentException, PortalException, SystemException {

		String xml = ContentUtil.get(
			"com/liferay/portal/events/dependencies/" + fileName);

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		List<Element> structureElements = rootElement.elements("structure");

		for (Element structureElement : structureElements) {
			String name = structureElement.elementText("name");

			String description = structureElement.elementText("description");

			String ddmStructureKey = name;

			DDMStructure ddmStructure =
				DDMStructureLocalServiceUtil.fetchStructure(
					groupId, ddmStructureKey);

			if (ddmStructure != null) {
				continue;
			}

			Element structureElementRootElement = structureElement.element(
				"root");

			String xsd = structureElementRootElement.asXML();

			Map<Locale, String> nameMap = new HashMap<Locale, String>();

			nameMap.put(LocaleUtil.getDefault(), name);

			Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

			descriptionMap.put(LocaleUtil.getDefault(), description);

			DDMStructureLocalServiceUtil.addStructure(
				userId, groupId, classNameId, ddmStructureKey, nameMap,
				descriptionMap, xsd, "xml", serviceContext);
		}
	}

}
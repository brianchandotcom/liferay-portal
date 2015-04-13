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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.ElementHandler;
import com.liferay.portal.kernel.xml.ElementProcessor;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;

import java.io.StringReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * @author Zsolt Berentey
 */
public class DeletionSystemEventImporter {

	public void importDeletionSystemEvents(
			final PortletDataContext portletDataContext)
		throws Exception {

		if (!MapUtil.getBoolean(
				portletDataContext.getParameterMap(),
				PortletDataHandlerKeys.DELETIONS)) {

			return;
		}

		initDeletionSystemEventStagedModelTypes(portletDataContext);

		String xml = portletDataContext.getZipEntryAsString(
			ExportImportPathUtil.getSourceRootPath(portletDataContext) +
				"/deletion-system-events.xml");

		if (xml == null) {
			return;
		}

		XMLReader xmlReader = SecureXMLFactoryProviderUtil.newXMLReader();

		ElementHandler elementHandler = new ElementHandler(
			new ElementProcessor() {

				@Override
				public void processElement(Element element) {
					doImportDeletionSystemEvents(portletDataContext, element);
				}

			},
			new String[] {"deletion-system-event"});

		xmlReader.setContentHandler(elementHandler);

		xmlReader.parse(new InputSource(new StringReader(xml)));
	}

	protected void initDeletionSystemEventStagedModelTypes(
		PortletDataContext portletDataContext) {

		Element importDataRootElement =
			portletDataContext.getImportDataRootElement();

		if (importDataRootElement == null) {
			return;
		}

		Element portletsElement = importDataRootElement.element("portlets");

		List<Element> portletElements = Collections.emptyList();

		if (portletsElement != null) {
			portletElements = portletsElement.elements("portlet");
		}
		else {
			Element element = importDataRootElement.element("portlet");

			portletElements = new ArrayList<Element>();

			portletElements.add(element);
		}

		for (Element portletElement : portletElements) {
			String portletPath = portletElement.attributeValue("path");

			Document portletDocument = null;

			try {
				portletDocument = SAXReaderUtil.read(
					portletDataContext.getZipEntryAsString(portletPath));
			}
			catch (DocumentException de) {
				continue;
			}

			portletElement = portletDocument.getRootElement();

			if (portletElement == null) {
				continue;
			}

			Element portletDataElement = portletElement.element("portlet-data");

			String portletId = portletElement.attributeValue("portlet-id");

			Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);

			if (!portlet.isActive()) {
				continue;
			}

			PortletDataHandler portletDataHandler =
				portlet.getPortletDataHandlerInstance();

			if (portletDataHandler == null) {
				continue;
			}

			Map<String, Boolean> importPortletControlsMap =
				Collections.emptyMap();

			try {
				importPortletControlsMap =
					LayoutImporter.getImportPortletControlsMap(
						portletDataContext.getCompanyId(), portletId,
						portletDataContext.getParameterMap(),
						portletDataElement,
						portletDataContext.getManifestSummary());
			}
			catch (Exception e) {
			}

			if (importPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_DATA)) {

				portletDataContext.addDeletionSystemEventStagedModelTypes(
					portletDataHandler.
						getDeletionSystemEventStagedModelTypes());
			}
		}
	}

	protected void doImportDeletionSystemEvents(
		PortletDataContext portletDataContext, Element element) {

		Set<StagedModelType> stagedModelTypes =
			portletDataContext.getDeletionSystemEventStagedModelTypes();

		StagedModelType stagedModelType = new StagedModelType(
			element.attributeValue("class-name"),
			element.attributeValue("referrer-class-name"));

		if (!stagedModelTypes.contains(stagedModelType)) {
			return;
		}

		try {
			StagedModelDataHandlerUtil.deleteStagedModel(
				portletDataContext, element);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(4);

				sb.append("Unable to process deletion for ");
				sb.append(stagedModelType);
				sb.append(" with UUID ");
				sb.append(element.attributeValue("uuid"));

				_log.warn(sb.toString());
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DeletionSystemEventImporter.class);

}
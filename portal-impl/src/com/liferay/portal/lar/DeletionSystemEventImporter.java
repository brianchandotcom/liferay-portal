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
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.ElementHandler;
import com.liferay.portal.kernel.xml.ElementProcessor;

import java.io.StringReader;

import java.util.Set;

import org.apache.xerces.parsers.SAXParser;

import org.xml.sax.InputSource;

/**
 * @author Zsolt Berentey
 */
public class DeletionSystemEventImporter {

	public void importDeletions(final PortletDataContext portletDataContext)
		throws Exception {

		String xml = portletDataContext.getZipEntryAsString(
			ExportImportPathUtil.getSourceRootPath(portletDataContext) +
				"/deletion-system-events.xml");

		if (xml == null) {
			return;
		}

		ElementHandler elementHandler = new ElementHandler(
			new ElementProcessor() {

				@Override
				public void processElement(Element element) {
					doImport(portletDataContext, element);
				}

			},
			new String[] {"deletion-system-event"});

		SAXParser saxParser = new SAXParser();

		saxParser.setContentHandler(elementHandler);

		saxParser.parse(new InputSource(new StringReader(xml)));
	}

	protected void doImport(
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
				StringBundler sb = new StringBundler(5);

				sb.append("Unable to process deletion for ");
				sb.append(stagedModelType);
				sb.append(" (uuid: ");
				sb.append(element.attributeValue("uuid"));
				sb.append(StringPool.CLOSE_PARENTHESIS);

				_log.warn(sb.toString());
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DeletionSystemEventImporter.class);

}
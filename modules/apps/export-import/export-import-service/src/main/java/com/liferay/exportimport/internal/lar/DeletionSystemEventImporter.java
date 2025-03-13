/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.lar;

import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.ElementHandler;
import com.liferay.portal.kernel.xml.ElementProcessor;

import java.io.StringReader;

import java.util.List;
import java.util.Set;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * @author Zsolt Berentey
 */
public class DeletionSystemEventImporter {

	public static DeletionSystemEventImporter getInstance() {
		return _deletionSystemEventImporter;
	}

	public void importDeletionSystemEvents(
			final PortletDataContext portletDataContext)
		throws Exception {

		if (!MapUtil.getBoolean(
				portletDataContext.getParameterMap(),
				PortletDataHandlerKeys.DELETIONS)) {

			return;
		}

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
					_importDeletionSystemEvents(portletDataContext, element);
				}

			},
			new String[] {"deletion-system-event"});

		xmlReader.setContentHandler(elementHandler);

		xmlReader.parse(new InputSource(new StringReader(xml)));

		_importBatchDeletions(portletDataContext);
	}

	private DeletionSystemEventImporter() {
	}

	private void _importBatchDeletions(PortletDataContext portletDataContext)
		throws Exception {

		Element rootElement = portletDataContext.getImportDataRootElement();

		Element sitePortletsElement = rootElement.element("site-portlets");

		List<Element> sitePortletElements = sitePortletsElement.elements();

		for (Element portletElement : sitePortletElements) {
			String portletId = portletElement.attributeValue("portlet-id");

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				portletDataContext.getCompanyId(), portletId);

			if (!portlet.isActive() || portlet.isUndeployedPortlet()) {
				continue;
			}

			String portletIdPrefix =
				"com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet_";

			if (portletId.startsWith(portletIdPrefix)) {
				String part = portletId.substring(portletIdPrefix.length());

				String className =
					"com.liferay.object.model.ObjectDefinition#" + part;

				ObjectDefinition objectDefinition =
					ObjectDefinitionLocalServiceUtil.
						fetchObjectDefinitionByClassName(
							portletDataContext.getCompanyId(), className);

				if (objectDefinition != null) {
					_importDeletionsOfObjectsDefinition(
						portletDataContext, objectDefinition, portletId);
				}
			}
		}
	}

	private void _importDeletionsOfObjectsDefinition(
			PortletDataContext portletDataContext,
			ObjectDefinition objectDefinition, String portletId)
		throws Exception {

		String fileName = objectDefinition.getName() + "_deletions.json";

		String content = portletDataContext.getZipEntryAsString(fileName);

		if (Validator.isNotNull(content)) {
			try (ServiceTrackerList<PortletDataHandler> pdhs =
					ServiceTrackerListFactory.open(
						SystemBundleUtil.getBundleContext(),
						PortletDataHandler.class,
						"(javax.portlet.name=" + portletId + ")")) {

				for (PortletDataHandler pdh : pdhs) {
					pdh.deleteData(portletDataContext, portletId, null);
				}
			}
		}
	}

	private void _importDeletionSystemEvents(
		PortletDataContext portletDataContext, Element element) {

		StagedModelType stagedModelType = new StagedModelType(
			element.attributeValue("class-name"),
			element.attributeValue("referrer-class-name"));

		if (!_shouldImportDeletionSystemEvent(
				portletDataContext, stagedModelType)) {

			return;
		}

		try {
			StagedModelDataHandlerUtil.deleteStagedModel(
				portletDataContext, element);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to process deletion for ", stagedModelType,
						" with UUID ", element.attributeValue("uuid")),
					exception);
			}
		}
	}

	private boolean _shouldImportDeletionSystemEvent(
		PortletDataContext portletDataContext,
		StagedModelType stagedModelType) {

		Set<StagedModelType> stagedModelTypes =
			portletDataContext.getDeletionSystemEventStagedModelTypes();

		if (stagedModelTypes.contains(stagedModelType)) {
			return true;
		}

		for (StagedModelType curStagedModelType : stagedModelTypes) {
			if ((curStagedModelType.getClassNameId() ==
					stagedModelType.getClassNameId()) &&
				(StagedModelType.REFERRER_CLASS_NAME_ALL.equals(
					curStagedModelType.getReferrerClassName()) ||
				 (Validator.isNotNull(stagedModelType.getReferrerClassName()) &&
				  StagedModelType.REFERRER_CLASS_NAME_ANY.equals(
					  curStagedModelType.getReferrerClassName())))) {

				return true;
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DeletionSystemEventImporter.class);

	private static final DeletionSystemEventImporter
		_deletionSystemEventImporter = new DeletionSystemEventImporter();

}
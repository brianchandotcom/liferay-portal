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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.SystemEvent;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.service.persistence.SystemEventActionableDynamicQuery;
import com.liferay.portal.util.PortalUtil;

import java.util.Date;
import java.util.Set;

/**
 * @author Zsolt Berentey
 */
public class DeletionSystemEventExporter {

	public void export(PortletDataContext portletDataContext) throws Exception {
		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("deletion-system-events");

		Set<Long> deletionEventClassNameIds =
			portletDataContext.getDeletionSystemEventClassNameIds();

		if (!deletionEventClassNameIds.isEmpty()) {
			doExport(
				portletDataContext, rootElement, deletionEventClassNameIds);
		}

		portletDataContext.addZipEntry(
			ExportImportPathUtil.getRootPath(portletDataContext) +
				"/deletion-system-events.xml",
			document.formattedString());
	}

	protected void doExport(
			final PortletDataContext portletDataContext,
			final Element rootElement,
			final Set<Long> deletionEventClassNameIds)
		throws PortalException, SystemException {

		ActionableDynamicQuery actionableDynamicQuery =
			new SystemEventActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				if (!deletionEventClassNameIds.isEmpty()) {
					Property classNameIdProperty = PropertyFactoryUtil.forName(
						"classNameId");

					dynamicQuery.add(
						classNameIdProperty.in(
							deletionEventClassNameIds.toArray()));
				}

				Property typeProperty = PropertyFactoryUtil.forName("type");

				dynamicQuery.add(
					typeProperty.eq(SystemEventConstants.TYPE_DELETE));

				_addCreateDateProperty(dynamicQuery);
			}

			@Override
			protected void performAction(Object object) {
				SystemEvent systemEvent = (SystemEvent)object;

				exportDeletionSystemEvent(
					portletDataContext, systemEvent, rootElement);
			}

			private void _addCreateDateProperty(DynamicQuery dynamicQuery) {
				if (!portletDataContext.hasDateRange()) {
					return;
				}

				Property createDateProperty = PropertyFactoryUtil.forName(
					"createDate");

				Date startDate = portletDataContext.getStartDate();

				dynamicQuery.add(createDateProperty.ge(startDate));

				Date endDate = portletDataContext.getEndDate();

				dynamicQuery.add(createDateProperty.le(endDate));
			}
		};

		actionableDynamicQuery.setGroupId(portletDataContext.getScopeGroupId());

		actionableDynamicQuery.performActions();
	}

	protected void exportDeletionSystemEvent(
		PortletDataContext portletDataContext, SystemEvent systemEvent,
		Element deletionSystemEventsElement) {

		Element deletionSystemEventElement =
			deletionSystemEventsElement.addElement("deletion-system-event");

		deletionSystemEventElement.addAttribute(
			"class-name",
			PortalUtil.getClassName(systemEvent.getClassNameId()));
		deletionSystemEventElement.addAttribute(
			"group-id", String.valueOf(systemEvent.getGroupId()));

		if (systemEvent.getReferrerClassNameId() > 0) {
			deletionSystemEventElement.addAttribute(
				"referrer-class-name",
				PortalUtil.getClassName(systemEvent.getReferrerClassNameId()));
		}

		deletionSystemEventElement.addAttribute(
			"uuid", systemEvent.getClassUuid());

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		manifestSummary.incrementModelDeletionCount(
			PortalUtil.getClassName(systemEvent.getClassNameId()));
	}

}
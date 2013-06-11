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
public class DeletionEventExporter {

	public void export(
			final com.liferay.portal.kernel.lar.PortletDataContext
				portletDataContext)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		final Element rootElement = document.addElement("deletions");

		final Set<Long> deletionEventClassNameIds =
			portletDataContext.getDeletionEventClassNameIds();

		if (!deletionEventClassNameIds.isEmpty()) {
			doExport(
				portletDataContext, rootElement, deletionEventClassNameIds);
		}

		portletDataContext.addZipEntry(
			ExportImportPathUtil.getRootPath(portletDataContext) +
				"/deletions.xml",
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
					if (deletionEventClassNameIds.isEmpty()) {
						return;
					}

					setGroupId(portletDataContext.getScopeGroupId());

					Property typeProperty = PropertyFactoryUtil.forName("type");

					dynamicQuery.add(
						typeProperty.eq(SystemEventConstants.TYPE_DELETE));

					Property classNameIdProperty = PropertyFactoryUtil.forName(
						"classNameId");

					dynamicQuery.add(
						classNameIdProperty.in(
							deletionEventClassNameIds.toArray()));

					_addCreateDateProperty(dynamicQuery);
				}

				@Override
				protected void performAction(Object object)
					throws PortalException, SystemException {

					SystemEvent systemEvent = (SystemEvent)object;

					exportDeletion(systemEvent, rootElement);
				}

				private void _addCreateDateProperty(DynamicQuery dynamicQuery) {
					if (!portletDataContext.hasDateRange()) {
						return;
					}

					Property modifiedDateProperty = PropertyFactoryUtil.forName(
						"createDate");

					Date startDate = portletDataContext.getStartDate();
					Date endDate = portletDataContext.getEndDate();

					dynamicQuery.add(
						modifiedDateProperty.ge(startDate.getTime()));
					dynamicQuery.add(
						modifiedDateProperty.le(endDate.getTime()));
				}
			};

		actionableDynamicQuery.performActions();
	}

	protected void exportDeletion(
		SystemEvent systemEvent, Element deletionsElement) {

		Element deletionElement = deletionsElement.addElement("deletion");

		deletionElement.addAttribute(
			"class-name",
			PortalUtil.getClassName(systemEvent.getClassNameId()));

		deletionElement.addAttribute(
			"group-id", String.valueOf(systemEvent.getGroupId()));

		deletionElement.addAttribute("uuid", systemEvent.getClassUuid());
	}

}
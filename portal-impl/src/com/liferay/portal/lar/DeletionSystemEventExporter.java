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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.SystemEvent;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.service.SystemEventLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.util.Date;
import java.util.Set;

/**
 * @author Zsolt Berentey
 */
public class DeletionSystemEventExporter {

	public static DeletionSystemEventExporter getInstance() {
		return _instance;
	}

	public void exportDeletionSystemEvents(
			PortletDataContext portletDataContext)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("deletion-system-events");

		Set<StagedModelType> deletionSystemEventStagedModelTypes =
			portletDataContext.getDeletionSystemEventStagedModelTypes();

		if (!deletionSystemEventStagedModelTypes.isEmpty() &&
			MapUtil.getBoolean(
				portletDataContext.getParameterMap(),
				PortletDataHandlerKeys.DELETIONS)) {

			doExportDeletionSystemEvents(
				portletDataContext, rootElement,
				deletionSystemEventStagedModelTypes);
		}

		portletDataContext.addZipEntry(
			ExportImportPathUtil.getRootPath(portletDataContext) +
				"/deletion-system-events.xml",
			document.formattedString());
	}

	protected void addCreateDateProperty(
		PortletDataContext portletDataContext, DynamicQuery dynamicQuery) {

		if (!portletDataContext.hasDateRange()) {
			return;
		}

		Property createDateProperty = PropertyFactoryUtil.forName("createDate");

		Date startDate = portletDataContext.getStartDate();

		dynamicQuery.add(createDateProperty.ge(startDate));

		Date endDate = portletDataContext.getEndDate();

		dynamicQuery.add(createDateProperty.le(endDate));
	}

	protected void doAddCriteria(
		PortletDataContext portletDataContext,
		Set<StagedModelType> deletionSystemEventStagedModelTypes,
		DynamicQuery dynamicQuery) {

		Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

		Property groupIdProperty = PropertyFactoryUtil.forName("groupId");

		disjunction.add(groupIdProperty.eq(0L));
		disjunction.add(
			groupIdProperty.eq(portletDataContext.getScopeGroupId()));

		dynamicQuery.add(disjunction);

		if (!deletionSystemEventStagedModelTypes.isEmpty()) {
			Property classNameIdProperty = PropertyFactoryUtil.forName(
				"classNameId");

			Property referrerClassNameIdProperty = PropertyFactoryUtil.forName(
				"referrerClassNameId");

			Disjunction referrerClassNameIdDisjunction =
				RestrictionsFactoryUtil.disjunction();

			for (StagedModelType stagedModelType :
					deletionSystemEventStagedModelTypes) {

				Conjunction conjunction = RestrictionsFactoryUtil.conjunction();

				conjunction.add(
					classNameIdProperty.eq(stagedModelType.getClassNameId()));

				if (stagedModelType.getReferrerClassNameId() >= 0) {
					conjunction.add(
						referrerClassNameIdProperty.eq(
							stagedModelType.getReferrerClassNameId()));
				}

				referrerClassNameIdDisjunction.add(conjunction);
			}

			dynamicQuery.add(referrerClassNameIdDisjunction);
		}

		Property typeProperty = PropertyFactoryUtil.forName("type");

		dynamicQuery.add(typeProperty.eq(SystemEventConstants.TYPE_DELETE));

		addCreateDateProperty(portletDataContext, dynamicQuery);
	}

	protected void doExportDeletionSystemEvents(
			final PortletDataContext portletDataContext,
			final Element rootElement,
			final Set<StagedModelType> deletionSystemEventStagedModelTypes)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			SystemEventLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					doAddCriteria(
						portletDataContext, deletionSystemEventStagedModelTypes,
						dynamicQuery);
				}

			});
		actionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object) {
					SystemEvent systemEvent = (SystemEvent)object;

					exportDeletionSystemEvent(
						portletDataContext, systemEvent, rootElement);
				}

			});

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
			"extra-data", systemEvent.getExtraData());
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
			new StagedModelType(
				systemEvent.getClassNameId(),
				systemEvent.getReferrerClassNameId()));
	}

	private DeletionSystemEventExporter() {
	}

	private static final DeletionSystemEventExporter _instance =
		new DeletionSystemEventExporter();

}
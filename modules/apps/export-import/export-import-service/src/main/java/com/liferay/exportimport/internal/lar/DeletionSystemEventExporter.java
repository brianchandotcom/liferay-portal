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

package com.liferay.exportimport.internal.lar;

import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.SystemEvent;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.service.SystemEventLocalServiceUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.Set;

/**
 * @author Zsolt Berentey
 */
public class DeletionSystemEventExporter {

	public static DeletionSystemEventExporter getInstance() {
		return _deletionSystemEventExporter;
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

			if (!MapUtil.getBoolean(
					portletDataContext.getParameterMap(),
					PortletDataHandlerKeys.DELETE_LAYOUTS)) {

				deletionSystemEventStagedModelTypes.remove(
					new StagedModelType(Layout.class));
			}

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

		dynamicQuery.add(
			createDateProperty.ge(portletDataContext.getStartDate()));

		dynamicQuery.add(
			createDateProperty.le(portletDataContext.getEndDate()));
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

				String className = stagedModelType.getClassName();

				if (className.equals(Layout.class.getName())) {
					Property extraDataProperty = PropertyFactoryUtil.forName(
						"extraData");

					conjunction.add(
						extraDataProperty.like(
							"%\"privateLayout\":\"" +
								portletDataContext.isPrivateLayout() + "\"%"));
				}

				referrerClassNameIdDisjunction.add(conjunction);
			}

			dynamicQuery.add(referrerClassNameIdDisjunction);
		}

		Property typeProperty = PropertyFactoryUtil.forName("type");

		dynamicQuery.add(typeProperty.eq(SystemEventConstants.TYPE_DELETE));

		if (ExportImportDateUtil.isRangeDateRange(portletDataContext)) {
			addCreateDateProperty(portletDataContext, dynamicQuery);
		}
	}

	protected void doExportDeletionSystemEvents(
			final PortletDataContext portletDataContext,
			final Element rootElement,
			final Set<StagedModelType> deletionSystemEventStagedModelTypes)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			SystemEventLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> doAddCriteria(
				portletDataContext, deletionSystemEventStagedModelTypes,
				dynamicQuery));
		actionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());
		actionableDynamicQuery.setPerformActionMethod(
			(SystemEvent systemEvent) -> exportDeletionSystemEvent(
				portletDataContext, systemEvent, rootElement));

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

		if (ExportImportThreadLocal.isStagingInProcess()) {
			try {
				SystemEventLocalServiceUtil.deleteSystemEvent(
					systemEvent.getSystemEventId());
			}
			catch (PortalException pe) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to delete system event. The events are being " +
							"cleaned up reagularly by a scheduled process.",
						pe);
				}
			}
		}
	}

	private DeletionSystemEventExporter() {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DeletionSystemEventExporter.class);

	private static final DeletionSystemEventExporter
		_deletionSystemEventExporter = new DeletionSystemEventExporter();

}
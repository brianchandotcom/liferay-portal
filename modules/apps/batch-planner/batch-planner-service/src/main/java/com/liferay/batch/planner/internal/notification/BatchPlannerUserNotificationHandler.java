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

package com.liferay.batch.planner.internal.notification;

import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.planner.constants.BatchPlannerPortletKeys;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;

import java.util.AbstractMap;
import java.util.Map;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + BatchPlannerPortletKeys.BATCH_PLANNER,
	service = UserNotificationHandler.class
)
public class BatchPlannerUserNotificationHandler
	extends BaseUserNotificationHandler {

	public BatchPlannerUserNotificationHandler() {
		setPortletId(BatchPlannerPortletKeys.BATCH_PLANNER);
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		String className = jsonObject.getString("className");
		String fileName = jsonObject.getString("fileName");
		String status = jsonObject.getString("status");
		String taskType = jsonObject.getString("taskType");

		Map.Entry<String, String> messageAndTitleEntry =
			_getMessageAndTitleEntry(
				className, fileName, serviceContext, status, taskType);

		return String.format(
			"<h2 class=\"title\">%s</h2><div class=\"body\">%s</div>",
			messageAndTitleEntry.getValue(), messageAndTitleEntry.getKey());
	}

	@Override
	protected String getLink(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				serviceContext.getRequest(), serviceContext.getScopeGroup(),
				BatchPlannerPortletKeys.BATCH_PLANNER, 0, 0,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/batch_planner/view_batch_planner_plan"
		).setBackURL(
			serviceContext.getCurrentURL()
		).setParameter(
			"batchPlannerPlanId",
			() -> {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					userNotificationEvent.getPayload());

				return jsonObject.getLong("batchPlannerPlanId");
			}
		).buildString();
	}

	private Map.Entry<String, String> _getMessageAndTitleEntry(
		String className, String fileName, ServiceContext serviceContext,
		String status, String taskType) {

		if (status.equals(BatchEngineTaskExecuteStatus.COMPLETED.name())) {
			if (taskType.equals("export")) {
				return new AbstractMap.SimpleImmutableEntry<>(
					serviceContext.translate(
						_MESSAGE_EXPORT_COMPLETED,
						_getSimpleClassNamePlural(className)),
					serviceContext.translate(
						_TITLE_EXPORT_COMPLETED,
						_getSimpleClassNamePlural(className)));
			}

			return new AbstractMap.SimpleImmutableEntry<>(
				serviceContext.translate(
					_MESSAGE_IMPORT_COMPLETED,
					_getSimpleClassNamePlural(className), fileName,
					StringUtil.toLowerCase(_getSimpleClassName(className))),
				serviceContext.translate(
					_TITLE_IMPORT_COMPLETED,
					_getSimpleClassNamePlural(className)));
		}
		else if (status.equals(BatchEngineTaskExecuteStatus.FAILED.name())) {
			if (taskType.equals("export")) {
				return new AbstractMap.SimpleImmutableEntry<>(
					serviceContext.translate(
						_MESSAGE_EXPORT_FAILED, _getSimpleClassName(className)),
					serviceContext.translate(
						_TITLE_EXPORT_FAILED,
						_getSimpleClassNamePlural(className)));
			}

			return new AbstractMap.SimpleImmutableEntry<>(
				serviceContext.translate(
					_MESSAGE_IMPORT_FAILED, fileName,
					StringUtil.toLowerCase(_getSimpleClassName(className))),
				serviceContext.translate(
					_TITLE_IMPORT_FAILED,
					_getSimpleClassNamePlural(className)));
		}

		throw new IllegalArgumentException(
			StringBundler.concat(
				"No batch planner user notification defined for status ",
				status, " and task type ", taskType));
	}

	private String _getSimpleClassName(String className) {
		return className.substring(
			className.lastIndexOf(StringPool.PERIOD) + 1);
	}

	private String _getSimpleClassNamePlural(String className) {
		return TextFormatter.formatPlural(_getSimpleClassName(className));
	}

	private static final String _MESSAGE_EXPORT_COMPLETED =
		"x-were-exported-to-an-export-file";

	private static final String _MESSAGE_EXPORT_FAILED =
		"x-entity-export-encountered-an-error-while-creating-an-export-file";

	private static final String _MESSAGE_IMPORT_COMPLETED =
		"x-from-x-were-imported-to-the-x-entity";

	private static final String _MESSAGE_IMPORT_FAILED =
		"x-encountered-an-error-while-importing-to-the-x-entity";

	private static final String _TITLE_EXPORT_COMPLETED = "x-exported";

	private static final String _TITLE_EXPORT_FAILED = "x-export-stopped";

	private static final String _TITLE_IMPORT_COMPLETED = "x-imported";

	private static final String _TITLE_IMPORT_FAILED = "x-import-stopped";

	@Reference
	private Portal _portal;

}
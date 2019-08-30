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

package com.liferay.change.tracking.change.lists.history.web.internal.portlet.action;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.text.Format;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CTPortletKeys.CHANGE_LISTS_HISTORY,
		"mvc.command.name=/change_lists_history/get_ct_processes"
	},
	service = MVCResourceCommand.class
)
public class GetCTProcessesMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

		try {
			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(resourceResponse);

			httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

			JSONArray jsonArray = _getProcessEntriesJSONArray(resourceRequest);

			ServletResponseUtil.write(
				httpServletResponse, jsonArray.toString());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private String _getCTProcessStatusLabel(CTProcess ctProcess) {
		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.fetchBackgroundTask(
				ctProcess.getBackgroundTaskId());

		if (backgroundTask == null) {
			return StringPool.BLANK;
		}

		String statusLabel = backgroundTask.getStatusLabel();

		if (statusLabel.equals(_TYPE_SUCCESSFUL)) {
			statusLabel = _TYPE_PUBLISHED;
		}

		return statusLabel;
	}

	private OrderByComparator<CTProcess> _getOrderByComparator(String sort) {
		OrderByComparator<CTProcess> orderByComparator = null;

		String[] sortColumns = StringUtil.split(sort, ":");

		if (sortColumns.length == 2) {
			boolean asc = true;

			if (sortColumns[1].equals("desc")) {
				asc = false;
			}

			if (Objects.equals(sortColumns[0], "name")) {
				orderByComparator = OrderByComparatorFactoryUtil.create(
					"CTCollection", new Object[] {sortColumns[0], asc});
			}
			else if (Objects.equals(sortColumns[0], "publishDate")) {
				orderByComparator = OrderByComparatorFactoryUtil.create(
					"CTProcess", "createDate", asc);
			}
		}

		return orderByComparator;
	}

	private JSONArray _getProcessEntriesJSONArray(
			ResourceRequest resourceRequest)
		throws PortalException {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String keywords = ParamUtil.getString(
			resourceRequest, "keywords", null);
		String sort = ParamUtil.getString(resourceRequest, "sort");
		String type = ParamUtil.getString(resourceRequest, "type");
		long userId = ParamUtil.getLong(
			resourceRequest, "user", CTConstants.USER_FILTER_ALL);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<CTProcess> ctProcesses = _ctProcessLocalService.getCTProcesses(
			themeDisplay.getCompanyId(), userId, keywords, _getStatus(type), 0,
			5, _getOrderByComparator(sort));

		PortletURL detailsURL = PortletURLFactoryUtil.create(
			httpServletRequest, CTPortletKeys.CHANGE_LISTS_HISTORY,
			PortletRequest.RENDER_PHASE);

		detailsURL.setParameter(
			"mvcRenderCommandName", "/change_lists_history/view_details");
		detailsURL.setParameter(
			"backURL", _portal.getCurrentURL(httpServletRequest));
		detailsURL.setParameter("orderByCol", "title");
		detailsURL.setParameter("orderByType", "desc");

		for (CTProcess ctProcess : ctProcesses) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			CTCollection ctCollection =
				_ctCollectionLocalService.getCTCollection(
					ctProcess.getCtCollectionId());
			detailsURL.setParameter(
				"ctCollectionId",
				String.valueOf(ctProcess.getCtCollectionId()));
			Format format = FastDateFormatFactoryUtil.getDateTime(
				themeDisplay.getLocale());
			User user = _userLocalService.getUser(ctProcess.getUserId());

			jsonArray.put(
				jsonObject.put(
					"description", ctCollection.getDescription()
				).put(
					"detailsLink", detailsURL.toString()
				).put(
					"name", ctCollection.getName()
				).put(
					"state", _getCTProcessStatusLabel(ctProcess)
				).put(
					"timestamp", format.format(ctProcess.getCreateDate())
				).put(
					"userInitials", user.getInitials()
				).put(
					"userName", user.getFullName()
				));
		}

		return jsonArray;
	}

	private int _getStatus(String type) {
		int status = 0;

		if (_TYPE_ALL.equals(type)) {
			status = WorkflowConstants.STATUS_ANY;
		}
		else if (_TYPE_FAILED.equals(type)) {
			status = BackgroundTaskConstants.STATUS_FAILED;
		}
		else if (_TYPE_IN_PROGRESS.equals(type)) {
			status = BackgroundTaskConstants.STATUS_IN_PROGRESS;
		}
		else if (_TYPE_PUBLISHED.equals(type)) {
			status = BackgroundTaskConstants.STATUS_SUCCESSFUL;
		}

		return status;
	}

	private static final String _TYPE_ALL = "all";

	private static final String _TYPE_FAILED = "failed";

	private static final String _TYPE_IN_PROGRESS = "in-progress";

	private static final String _TYPE_PUBLISHED = "published";

	private static final String _TYPE_SUCCESSFUL = "successful";

	private static final Log _log = LogFactoryUtil.getLog(
		GetCTProcessesMVCResourceCommand.class);

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTProcessLocalService _ctProcessLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}
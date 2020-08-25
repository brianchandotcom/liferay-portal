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

package com.liferay.portal.search.admin.web.internal.portlet.action;

import com.liferay.portal.instances.service.PortalInstancesLocalService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.uuid.PortalUUID;
import com.liferay.portal.search.admin.web.internal.constants.SearchAdminPortletKeys;
import com.liferay.portal.search.admin.web.internal.util.DictionaryReindexer;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 */
@Component(
	property = {
		"javax.portlet.name=" + SearchAdminPortletKeys.SEARCH_ADMIN,
		"mvc.command.name=/search_admin/edit"
	},
	service = MVCActionCommand.class
)
public class EditMVCActionCommand extends BaseMVCActionCommand {

	@Override
	public void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!permissionChecker.isOmniadmin()) {
			SessionErrors.add(
				actionRequest,
				PrincipalException.MustBeOmniadmin.class.getName());

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");

			return;
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (cmd.equals("reindex")) {
			reindex(actionRequest);
		}
		else if (cmd.equals("reindexDictionaries")) {
			reindexDictionaries(actionRequest);
		}

		sendRedirect(actionRequest, actionResponse, redirect);
	}

	protected void reindex(final ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] companyIds = _portalInstancesLocalService.getCompanyIds();

		if (!ArrayUtil.contains(companyIds, CompanyConstants.SYSTEM)) {
			companyIds = ArrayUtil.append(
				new long[] {CompanyConstants.SYSTEM}, companyIds);
		}

		String className = ParamUtil.getString(actionRequest, "className");
		Map<String, Serializable> taskContextMap = new HashMap<>();

		if (!ParamUtil.getBoolean(actionRequest, "blocking")) {
			_indexWriterHelper.reindex(
				themeDisplay.getUserId(), "reindex", companyIds, className,
				taskContextMap);

			return;
		}

		final String jobName = "reindex-".concat(_portalUUID.generate());

		final CountDownLatch countDownLatch = new CountDownLatch(1);

		MessageListener messageListener = new MessageListener() {

			@Override
			public void receive(Message message)
				throws MessageListenerException {

				int status = message.getInteger("status");

				if ((status != BackgroundTaskConstants.STATUS_CANCELLED) &&
					(status != BackgroundTaskConstants.STATUS_FAILED) &&
					(status != BackgroundTaskConstants.STATUS_SUCCESSFUL)) {

					return;
				}

				if (!jobName.equals(message.getString("name"))) {
					return;
				}

				PortletSession portletSession =
					actionRequest.getPortletSession();

				long lastAccessedTime = portletSession.getLastAccessedTime();
				int maxInactiveInterval =
					portletSession.getMaxInactiveInterval();

				int extendedMaxInactiveIntervalTime =
					(int)(System.currentTimeMillis() - lastAccessedTime +
						maxInactiveInterval);

				portletSession.setMaxInactiveInterval(
					extendedMaxInactiveIntervalTime);

				countDownLatch.countDown();
			}

		};

		_messageBus.registerMessageListener(
			DestinationNames.BACKGROUND_TASK_STATUS, messageListener);

		try {
			_indexWriterHelper.reindex(
				themeDisplay.getUserId(), jobName, companyIds, className,
				taskContextMap);

			countDownLatch.await(
				ParamUtil.getLong(actionRequest, "timeout", Time.HOUR),
				TimeUnit.MILLISECONDS);
		}
		finally {
			_messageBus.unregisterMessageListener(
				DestinationNames.BACKGROUND_TASK_STATUS, messageListener);
		}
	}

	protected void reindexDictionaries(ActionRequest actionRequest)
		throws Exception {

		DictionaryReindexer dictionaryReindexer = new DictionaryReindexer(
			_indexWriterHelper, _portalInstancesLocalService);

		dictionaryReindexer.reindexDictionaries();
	}

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

	@Reference
	private MessageBus _messageBus;

	@Reference
	private PortalInstancesLocalService _portalInstancesLocalService;

	@Reference
	private PortalUUID _portalUUID;

}
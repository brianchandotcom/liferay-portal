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

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.instances.service.PortalInstancesLocalService;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.uuid.PortalUUID;
import com.liferay.portal.search.admin.web.internal.constants.SearchAdminPortletKeys;
import com.liferay.portal.search.admin.web.internal.util.DictionaryReindexer;
import com.liferay.portal.search.spi.reindexer.IndexReindexer;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletSession;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 */
@Component(
	property = {
		"javax.portlet.name=" + SearchAdminPortletKeys.SEARCH_ADMIN,
		"mvc.command.name=/portal_search_admin/edit"
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

		if (cmd.equals("reindex")) {
			_reindex(actionRequest);

			_reindexIndexReindexers(actionRequest);
		}
		else if (cmd.equals("reindexDictionaries")) {
			_reindexDictionaries(actionRequest);
		}
		else if (cmd.equals("reindexIndexReindexer")) {
			_reindexIndexReindexer(actionRequest);
		}

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		redirect = HttpComponentsUtil.setParameter(
			redirect, actionResponse.getNamespace() + "companyIds",
			StringUtil.merge(
				ParamUtil.getLongValues(actionRequest, "companyIds")));
		redirect = HttpComponentsUtil.setParameter(
			redirect, actionResponse.getNamespace() + "scope",
			ParamUtil.getString(actionRequest, "scope"));

		sendRedirect(actionRequest, actionResponse, redirect);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, IndexReindexer.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(indexReindexer, emitter) -> {
					Class<? extends IndexReindexer> clazz =
						indexReindexer.getClass();

					emitter.emit(clazz.getName());
				}));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private void _reindex(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] companyIds = ParamUtil.getLongValues(
			actionRequest, "companyIds");

		String className = ParamUtil.getString(actionRequest, "className");
		Map<String, Serializable> taskContextMap = new HashMap<>();

		if (!ParamUtil.getBoolean(actionRequest, "blocking")) {
			_indexWriterHelper.reindex(
				themeDisplay.getUserId(), "reindex", companyIds, className,
				taskContextMap);

			return;
		}

		String jobName = "reindex-".concat(_portalUUID.generate());

		CountDownLatch countDownLatch = new CountDownLatch(1);

		MessageListener messageListener = message -> {
			int status = message.getInteger("status");

			if ((status != BackgroundTaskConstants.STATUS_CANCELLED) &&
				(status != BackgroundTaskConstants.STATUS_FAILED) &&
				(status != BackgroundTaskConstants.STATUS_SUCCESSFUL)) {

				return;
			}

			if (!jobName.equals(message.getString("name"))) {
				return;
			}

			PortletSession portletSession = actionRequest.getPortletSession();

			long lastAccessedTime = portletSession.getLastAccessedTime();
			int maxInactiveInterval = portletSession.getMaxInactiveInterval();

			int extendedMaxInactiveIntervalTime =
				(int)(System.currentTimeMillis() - lastAccessedTime +
					maxInactiveInterval);

			portletSession.setMaxInactiveInterval(
				extendedMaxInactiveIntervalTime);

			countDownLatch.countDown();
		};

		Destination destination = _messageBus.getDestination(
			DestinationNames.BACKGROUND_TASK_STATUS);

		destination.register(messageListener);

		try {
			_indexWriterHelper.reindex(
				themeDisplay.getUserId(), jobName, companyIds, className,
				taskContextMap);

			countDownLatch.await(
				ParamUtil.getLong(actionRequest, "timeout", Time.HOUR),
				TimeUnit.MILLISECONDS);
		}
		finally {
			destination.unregister(messageListener);
		}
	}

	private void _reindexDictionaries(ActionRequest actionRequest)
		throws Exception {

		DictionaryReindexer dictionaryReindexer = new DictionaryReindexer(
			_indexWriterHelper, _portalInstancesLocalService);

		dictionaryReindexer.reindexDictionaries(
			ParamUtil.getLongValues(actionRequest, "companyIds"));
	}

	private void _reindexIndexReindexer(ActionRequest actionRequest)
		throws Exception {

		String className = ParamUtil.getString(actionRequest, "className");

		IndexReindexer indexReindexer = _serviceTrackerMap.getService(
			className);

		indexReindexer.reindex(
			ParamUtil.getLongValues(actionRequest, "companyIds"));
	}

	private void _reindexIndexReindexers(ActionRequest actionRequest)
		throws Exception {

		for (IndexReindexer indexReindexer : _serviceTrackerMap.values()) {
			indexReindexer.reindex(
				ParamUtil.getLongValues(actionRequest, "companyIds"));
		}
	}

	@Reference
	private IndexWriterHelper _indexWriterHelper;

	@Reference
	private MessageBus _messageBus;

	@Reference
	private PortalInstancesLocalService _portalInstancesLocalService;

	@Reference
	private PortalUUID _portalUUID;

	private ServiceTrackerMap<String, IndexReindexer> _serviceTrackerMap;

}
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

package com.liferay.alloy.mvc;

import com.liferay.alloy.mvc.internal.json.web.service.AlloyControllerInvokerManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.Router;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.framework.ServiceRegistration;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class AlloyPortlet extends GenericPortlet {

	@Override
	public void destroy() {
		for (BaseAlloyControllerImpl baseAlloyControllerImpl :
				_alloyControllers.values()) {

			ServiceRegistration<?> indexerServiceRegistration =
				baseAlloyControllerImpl.indexerServiceRegistration;

			if (indexerServiceRegistration != null) {
				indexerServiceRegistration.unregister();
			}

			Map<String, ServiceRegistration<Destination>> serviceRegistrations =
				baseAlloyControllerImpl.destinationServiceRegistrations;

			MessageListener controllerMessageListener =
				baseAlloyControllerImpl.controllerMessageListener;

			if (controllerMessageListener != null) {
				ServiceRegistration<Destination> serviceRegistration =
					serviceRegistrations.remove(
						baseAlloyControllerImpl.getControllerDestinationName());

				if (serviceRegistration != null) {
					serviceRegistration.unregister();
				}
			}

			MessageListener schedulerMessageListener =
				baseAlloyControllerImpl.schedulerMessageListener;

			if (schedulerMessageListener != null) {
				try {
					SchedulerEngineHelperUtil.unschedule(
						baseAlloyControllerImpl.getSchedulerJobName(),
						baseAlloyControllerImpl.getMessageListenerGroupName(),
						StorageType.MEMORY_CLUSTERED);

					ServiceRegistration<Destination> serviceRegistration =
						serviceRegistrations.remove(
							baseAlloyControllerImpl.
								getSchedulerDestinationName());

					if (serviceRegistration != null) {
						serviceRegistration.unregister();
					}
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			}
		}

		_alloyControllerInvokerManager.unregisterControllers();
	}

	@Override
	public void init(PortletConfig portletConfig) throws PortletException {
		super.init(portletConfig);

		Router router = friendlyURLMapper.getRouter();

		router.urlToParameters(HttpMethods.GET, _defaultRouteParameters);

		_alloyControllerInvokerManager = new AlloyControllerInvokerManager(
			(LiferayPortletConfig)portletConfig);
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		include(getPath(actionRequest), actionRequest, actionResponse);
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		include(getPath(renderRequest), renderRequest, renderResponse);
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		include(getPath(resourceRequest), resourceRequest, resourceResponse);
	}

	protected AlloyControllerInvokerManager getAlloyInvokerManager() {
		return _alloyControllerInvokerManager;
	}

	protected String getControllerPath(PortletRequest portletRequest) {
		String controllerPath = ParamUtil.getString(
			portletRequest, "controller");

		if (Validator.isNull(controllerPath)) {
			Map<String, String> defaultRouteParameters =
				getDefaultRouteParameters();

			controllerPath = defaultRouteParameters.get("controller");
		}

		return controllerPath;
	}

	protected Map<String, String> getDefaultRouteParameters() {
		/*Map<String, String> defaultRouteParameters = new HashMap<>();

		defaultRouteParameters.put("action", new String[] {"index"});
		defaultRouteParameters.put("controller", new String[] {"assets"});

		return defaultRouteParameters;*/

		return _defaultRouteParameters;
	}

	protected String getPath(PortletRequest portletRequest) {
		return StringBundler.concat(
			"/alloy_mvc/jsp/", friendlyURLMapper.getMapping(), "/controllers/",
			getControllerPath(portletRequest), "_controller.jsp");
	}

	protected void include(
			String path, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws IOException, PortletException {

		PortletContext portletContext = getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(path);

		if (portletRequestDispatcher == null) {
			_log.error(path + " is not a valid include");
		}
		else {
			portletRequestDispatcher.include(portletRequest, portletResponse);
		}
	}

	protected void registerAlloyController(AlloyController alloyController) {
		BaseAlloyControllerImpl newBaseAlloyControllerImpl =
			(BaseAlloyControllerImpl)alloyController;

		String controller = newBaseAlloyControllerImpl.controllerPath;

		BaseAlloyControllerImpl oldBaseAlloyControllerImpl =
			_alloyControllers.get(controller);

		if ((oldBaseAlloyControllerImpl == null) ||
			(newBaseAlloyControllerImpl.getClass() !=
				oldBaseAlloyControllerImpl.getClass())) {

			synchronized (controller.intern()) {
				oldBaseAlloyControllerImpl = _alloyControllers.get(controller);

				if ((oldBaseAlloyControllerImpl == null) ||
					(newBaseAlloyControllerImpl.getClass() !=
						oldBaseAlloyControllerImpl.getClass())) {

					_alloyControllers.put(
						controller, newBaseAlloyControllerImpl);

					_alloyControllerInvokerManager.registerController(
						newBaseAlloyControllerImpl.getThemeDisplay(), this,
						newBaseAlloyControllerImpl.portlet, controller,
						newBaseAlloyControllerImpl.getClass());
				}
			}
		}
	}

	protected abstract void setFriendlyURLMapper(
		FriendlyURLMapper friendlyURLMapper);

	protected FriendlyURLMapper friendlyURLMapper;

	private static final Log _log = LogFactoryUtil.getLog(AlloyPortlet.class);

	private AlloyControllerInvokerManager _alloyControllerInvokerManager;
	private final Map<String, BaseAlloyControllerImpl> _alloyControllers =
		new ConcurrentHashMap<>();
	private final Map<String, String> _defaultRouteParameters = new HashMap<>();

}
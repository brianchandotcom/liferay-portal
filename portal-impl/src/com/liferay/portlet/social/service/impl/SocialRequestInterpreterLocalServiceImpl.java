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

package com.liferay.portlet.social.service.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.social.service.base.SocialRequestInterpreterLocalServiceBaseImpl;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;
import com.liferay.registry.collections.ServiceRegistrationMapImpl;
import com.liferay.social.kernel.model.SocialRequest;
import com.liferay.social.kernel.model.SocialRequestFeedEntry;
import com.liferay.social.kernel.model.SocialRequestInterpreter;
import com.liferay.social.kernel.model.impl.SocialRequestInterpreterImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The social request interpreter local service. Social request interpreters are
 * responsible for translating social requests into human readable form as well
 * as handling social request confirmations and rejections. This service holds a
 * list of interpreters and provides methods to add or remove items from this
 * list.
 *
 * <p>
 * Social request interpreters use the language files to get text fragments
 * based on the request's type. An interpreter is created for a specific request
 * type and is only capable of handling requests of that type. As an example,
 * there is an interpreter FriendsRequestInterpreter in the social networking
 * portlet can only translate and handle interpretation, confirmation, and
 * rejection of friend requests.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class SocialRequestInterpreterLocalServiceImpl
	extends SocialRequestInterpreterLocalServiceBaseImpl {

	/**
	 * Adds the social request interpreter to the list of available
	 * interpreters.
	 *
	 * @param requestInterpreter the social request interpreter
	 */
	@Override
	public void addRequestInterpreter(
		SocialRequestInterpreter requestInterpreter) {

		Registry registry = RegistryUtil.getRegistry();

		SocialRequestInterpreterImpl socialRequestInterpreterImpl =
			(SocialRequestInterpreterImpl)requestInterpreter;

		Map<String, Object> properties = new HashMap<>();

		properties.put(
			"javax.portlet.name", socialRequestInterpreterImpl.getPortletId());

		ServiceRegistration<SocialRequestInterpreter> serviceRegistration =
			registry.registerService(
				SocialRequestInterpreter.class, requestInterpreter, properties);

		_serviceRegistrations.put(requestInterpreter, serviceRegistration);
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();

		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(javax.portlet.name=*)(objectClass=" +
				SocialRequestInterpreter.class.getName() + "))");

		_serviceTracker = registry.trackServices(
			filter, new SocialRequestInterpreterServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	/**
	 * Removes the social request interpreter from the list of available
	 * interpreters.
	 *
	 * @param requestInterpreter the social request interpreter
	 */
	@Override
	public void deleteRequestInterpreter(
		SocialRequestInterpreter requestInterpreter) {

		ServiceRegistration<SocialRequestInterpreter> serviceRegistration =
			_serviceRegistrations.remove(requestInterpreter);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	/**
	 * Creates a human readable request feed entry for the social request using
	 * an available compatible request interpreter.
	 *
	 * <p>
	 * This method finds the appropriate interpreter for the request by going
	 * through the available interpreters to find one that can handle the asset
	 * type of the request.
	 * </p>
	 *
	 * @param  request the social request to be translated to human readable
	 *         form
	 * @param  themeDisplay the theme display needed by interpreters to create
	 *         links and get localized text fragments
	 * @return the social request feed entry
	 */
	@Override
	public SocialRequestFeedEntry interpret(
		SocialRequest request, ThemeDisplay themeDisplay) {

		String className = PortalUtil.getClassName(request.getClassNameId());

		for (SocialRequestInterpreter requestInterpreter :
				_requestInterpreters) {

			SocialRequestInterpreterImpl socialRequestInterpreterImpl =
				(SocialRequestInterpreterImpl)requestInterpreter;

			if (matches(socialRequestInterpreterImpl, className, request)) {
				SocialRequestFeedEntry requestFeedEntry =
					socialRequestInterpreterImpl.interpret(
						request, themeDisplay);

				if (requestFeedEntry != null) {
					requestFeedEntry.setPortletId(
						socialRequestInterpreterImpl.getPortletId());

					return requestFeedEntry;
				}
			}
		}

		return null;
	}

	/**
	 * Processes the confirmation of the social request.
	 *
	 * <p>
	 * Confirmations are handled by finding the appropriate social request
	 * interpreter and calling its processConfirmation() method. To find the
	 * appropriate interpreter this method goes through the available
	 * interpreters to find one that can handle the asset type of the request.
	 * </p>
	 *
	 * @param request the social request being confirmed
	 * @param themeDisplay the theme display needed by interpreters to create
	 *        links and get localized text fragments
	 */
	@Override
	public void processConfirmation(
		SocialRequest request, ThemeDisplay themeDisplay) {

		String className = PortalUtil.getClassName(request.getClassNameId());

		for (SocialRequestInterpreter requestInterpreter :
				_requestInterpreters) {

			SocialRequestInterpreterImpl socialRequestInterpreterImpl =
				(SocialRequestInterpreterImpl)requestInterpreter;

			if (matches(socialRequestInterpreterImpl, className, request)) {
				boolean value =
					socialRequestInterpreterImpl.processConfirmation(
						request, themeDisplay);

				if (value) {
					return;
				}
			}
		}
	}

	/**
	 * Processes the rejection of the social request.
	 *
	 * <p>
	 * Rejections are handled by finding the appropriate social request
	 * interpreters and calling their processRejection() methods. To find the
	 * appropriate interpreters this method goes through the available
	 * interpreters and asks them if they can handle the asset type of the
	 * request.
	 * </p>
	 *
	 * @param request the social request being rejected
	 * @param themeDisplay the theme display needed by interpreters to create
	 *        links and get localized text fragments
	 */
	@Override
	public void processRejection(
		SocialRequest request, ThemeDisplay themeDisplay) {

		String className = PortalUtil.getClassName(request.getClassNameId());

		for (SocialRequestInterpreter requestInterpreter :
				_requestInterpreters) {

			SocialRequestInterpreterImpl socialRequestInterpreterImpl =
				(SocialRequestInterpreterImpl)requestInterpreter;

			if (matches(socialRequestInterpreterImpl, className, request)) {
				boolean value = socialRequestInterpreterImpl.processRejection(
					request, themeDisplay);

				if (value) {
					return;
				}
			}
		}
	}

	protected String getSocialRequestPortletId(SocialRequest request) {
		try {
			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject(
				request.getExtraData());

			return extraDataJSONObject.getString("portletId");
		}
		catch (JSONException jsone) {
			_log.error(
				"Unable to create JSON object from " + request.getExtraData(),
				jsone);

			return StringPool.BLANK;
		}
	}

	protected boolean matches(
		SocialRequestInterpreterImpl socialRequestInterpreterImpl,
		String className, SocialRequest request) {

		if (!socialRequestInterpreterImpl.hasClassName(className)) {
			return false;
		}

		String requestPortletId = getSocialRequestPortletId(request);

		if (Validator.isNull(requestPortletId) ||
			requestPortletId.equals(
				socialRequestInterpreterImpl.getPortletId())) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SocialRequestInterpreterLocalServiceImpl.class);

	private final List<SocialRequestInterpreter> _requestInterpreters =
		new CopyOnWriteArrayList<>();
	private final ServiceRegistrationMap<SocialRequestInterpreter>
		_serviceRegistrations = new ServiceRegistrationMapImpl<>();
	private ServiceTracker<SocialRequestInterpreter, SocialRequestInterpreter>
		_serviceTracker;

	private class SocialRequestInterpreterServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<SocialRequestInterpreter, SocialRequestInterpreter> {

		@Override
		public SocialRequestInterpreter addingService(
			ServiceReference<SocialRequestInterpreter> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			SocialRequestInterpreter requestInterpreter = registry.getService(
				serviceReference);

			if (!(requestInterpreter instanceof SocialRequestInterpreterImpl)) {
				String portletId = (String)serviceReference.getProperty(
					"javax.portlet.name");

				requestInterpreter = new SocialRequestInterpreterImpl(
					portletId, requestInterpreter);
			}

			_requestInterpreters.add(requestInterpreter);

			return requestInterpreter;
		}

		@Override
		public void modifiedService(
			ServiceReference<SocialRequestInterpreter> serviceReference,
			SocialRequestInterpreter requestInterpreter) {
		}

		@Override
		public void removedService(
			ServiceReference<SocialRequestInterpreter> serviceReference,
			SocialRequestInterpreter requestInterpreter) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_requestInterpreters.remove(requestInterpreter);
		}

	}

}
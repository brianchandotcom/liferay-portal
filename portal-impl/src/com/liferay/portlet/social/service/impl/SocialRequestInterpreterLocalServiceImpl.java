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

package com.liferay.portlet.social.service.impl;

import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.model.SocialRequest;
import com.liferay.portlet.social.model.SocialRequestFeedEntry;
import com.liferay.portlet.social.model.SocialRequestInterpreter;
import com.liferay.portlet.social.model.impl.SocialRequestInterpreterImpl;
import com.liferay.portlet.social.service.base.SocialRequestInterpreterLocalServiceBaseImpl;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;

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

	@Override
	public void afterPropertiesSet() {
		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(javax.portlet.name=*)(objectClass="
				+ SocialRequestInterpreter.class.getName() + "))");

		_serviceTracker = registry.trackServices(filter,
			new SocialRequestInterpreterServiceTrackerCustomizer());

		_serviceTracker.open();
	}

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

		Map<String, Object> properties = new HashMap<String, Object>();

		SocialRequestInterpreterImpl requestInterpreterImpl =
			(SocialRequestInterpreterImpl)requestInterpreter;

		properties.put(
			"javax.portlet.name", requestInterpreterImpl.getPortletId());

		ServiceRegistration<SocialRequestInterpreter> serviceRegistration =
			registry.registerService(
				SocialRequestInterpreter.class, requestInterpreter,
				properties);

		_serviceRegistrations.put(requestInterpreter, serviceRegistration);
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

		for (int i = 0; i < _requestInterpreters.size(); i++) {
			SocialRequestInterpreterImpl requestInterpreter =
				(SocialRequestInterpreterImpl)_requestInterpreters.get(i);

			if (requestInterpreter.hasClassName(className)) {
				SocialRequestFeedEntry requestFeedEntry =
					requestInterpreter.interpret(request, themeDisplay);

				if (requestFeedEntry != null) {
					requestFeedEntry.setPortletId(
						requestInterpreter.getPortletId());

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

		for (int i = 0; i < _requestInterpreters.size(); i++) {
			SocialRequestInterpreterImpl requestInterpreter =
				(SocialRequestInterpreterImpl)_requestInterpreters.get(i);

			if (requestInterpreter.hasClassName(className)) {
				boolean value = requestInterpreter.processConfirmation(
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

		for (int i = 0; i < _requestInterpreters.size(); i++) {
			SocialRequestInterpreterImpl requestInterpreter =
				(SocialRequestInterpreterImpl)_requestInterpreters.get(i);

			if (requestInterpreter.hasClassName(className)) {
				boolean value = requestInterpreter.processRejection(
					request, themeDisplay);

				if (value) {
					return;
				}
			}
		}
	}

	private List<SocialRequestInterpreter> _requestInterpreters =
		new CopyOnWriteArrayList<SocialRequestInterpreter>();
	private ServiceRegistrationMap<SocialRequestInterpreter>
		_serviceRegistrations =
			new ServiceRegistrationMap<SocialRequestInterpreter>();
	private ServiceTracker<SocialRequestInterpreter, SocialRequestInterpreter>
		_serviceTracker;

	private class SocialRequestInterpreterServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<SocialRequestInterpreter, SocialRequestInterpreter> {

		@Override
		public SocialRequestInterpreter addingService(
			ServiceReference<SocialRequestInterpreter> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			SocialRequestInterpreter socialRequestInterpreter =
				registry.getService(serviceReference);

			String portletId = (String) serviceReference.getProperty(
				"javax.portlet.name");

			if (!(socialRequestInterpreter instanceof
					SocialRequestInterpreterImpl)) {

				socialRequestInterpreter = new SocialRequestInterpreterImpl(
					portletId, socialRequestInterpreter);
			}

			_requestInterpreters.add(socialRequestInterpreter);

			return socialRequestInterpreter;
		}

		@Override
		public void modifiedService(
			ServiceReference<SocialRequestInterpreter> serviceReference,
			SocialRequestInterpreter socialRequestInterpreter) {
		}

		@Override
		public void removedService(
			ServiceReference<SocialRequestInterpreter> serviceReference,
			SocialRequestInterpreter socialRequestInterpreter) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_requestInterpreters.remove(socialRequestInterpreter);
		}

	}

}
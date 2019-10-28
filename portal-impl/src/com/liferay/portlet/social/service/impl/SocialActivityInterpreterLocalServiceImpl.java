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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.social.service.base.SocialActivityInterpreterLocalServiceBaseImpl;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;
import com.liferay.registry.collections.ServiceRegistrationMapImpl;
import com.liferay.social.kernel.model.SocialActivity;
import com.liferay.social.kernel.model.SocialActivityFeedEntry;
import com.liferay.social.kernel.model.SocialActivityInterpreter;
import com.liferay.social.kernel.model.SocialActivitySet;
import com.liferay.social.kernel.model.impl.SocialActivityInterpreterImpl;
import com.liferay.social.kernel.model.impl.SocialRequestInterpreterImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * The social activity interpreter local service. Activity interpreters are
 * classes responsible for translating activity records into human readable
 * form. This service holds a list of interpreters and provides methods to add
 * or remove items from this list.
 *
 * <p>
 * Activity interpreters use the language files to get text fragments based on
 * the activity's type and the type of asset on which the activity was done.
 * Interpreters are created for specific asset types and are only capable of
 * translating activities done on assets of those types. As an example, there is
 * an interpreter BlogsActivityInterpreter that can only translate activity
 * records for blog entries.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class SocialActivityInterpreterLocalServiceImpl
	extends SocialActivityInterpreterLocalServiceBaseImpl {

	/**
	 * Adds the activity interpreter to the list of available interpreters.
	 *
	 * @param activityInterpreter the activity interpreter
	 */
	@Override
	public void addActivityInterpreter(
		SocialActivityInterpreter activityInterpreter) {

		Registry registry = RegistryUtil.getRegistry();

		SocialActivityInterpreterImpl socialActivityInterpreterImpl =
			(SocialActivityInterpreterImpl)activityInterpreter;

		Map<String, Object> properties = new HashMap<>();

		properties.put(
			"javax.portlet.name", socialActivityInterpreterImpl.getPortletId());

		ServiceRegistration<SocialActivityInterpreter> serviceRegistration =
			registry.registerService(
				SocialActivityInterpreter.class, activityInterpreter,
				properties);

		_serviceRegistrations.put(activityInterpreter, serviceRegistration);
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();

		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(javax.portlet.name=*)(objectClass=" +
				SocialActivityInterpreter.class.getName() + "))");

		_serviceTracker = registry.trackServices(
			filter, new SocialActivityInterpreterServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	/**
	 * Removes the activity interpreter from the list of available interpreters.
	 *
	 * @param activityInterpreter the activity interpreter
	 */
	@Override
	public void deleteActivityInterpreter(
		SocialActivityInterpreter activityInterpreter) {

		ServiceRegistration<SocialActivityInterpreter> serviceRegistration =
			_serviceRegistrations.remove(activityInterpreter);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	@Override
	public Map<String, List<SocialActivityInterpreter>>
		getActivityInterpreters() {

		return _activityInterpreters;
	}

	@Override
	public List<SocialActivityInterpreter> getActivityInterpreters(
		String selector) {

		return _activityInterpreters.get(selector);
	}

	/**
	 * Creates a human readable activity feed entry for the activity using an
	 * available compatible activity interpreter.
	 *
	 * <p>
	 * This method finds the appropriate interpreter for the activity by going
	 * through the available interpreters and asking them if they can handle the
	 * asset type of the activity.
	 * </p>
	 *
	 * @param  selector the context in which the activity interpreter is used
	 * @param  activity the activity to be translated to human readable form
	 * @param  serviceContext the service context to be applied
	 * @return the activity feed that is a human readable form of the activity
	 *         record or <code>null</code> if a compatible interpreter is not
	 *         found
	 */
	@Override
	public SocialActivityFeedEntry interpret(
		String selector, SocialActivity activity,
		ServiceContext serviceContext) {

		HttpServletRequest httpServletRequest = serviceContext.getRequest();

		if (httpServletRequest == null) {
			return null;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			if (activity.getUserId() == themeDisplay.getDefaultUserId()) {
				return null;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (activity.getMirrorActivityId() > 0) {
			SocialActivity mirrorActivity = null;

			try {
				mirrorActivity = socialActivityLocalService.getActivity(
					activity.getMirrorActivityId());
			}
			catch (Exception e) {
			}

			if (mirrorActivity != null) {
				activity = mirrorActivity;
			}
		}

		List<SocialActivityInterpreter> activityInterpreters =
			_activityInterpreters.get(selector);

		if (activityInterpreters == null) {
			return null;
		}

		String className = PortalUtil.getClassName(activity.getClassNameId());

		for (SocialActivityInterpreter activityInterpreter :
				activityInterpreters) {

			SocialActivityInterpreterImpl socialActivityInterpreterImpl =
				(SocialActivityInterpreterImpl)activityInterpreter;

			if (socialActivityInterpreterImpl.hasClassName(className)) {
				SocialActivityFeedEntry activityFeedEntry =
					socialActivityInterpreterImpl.interpret(
						activity, serviceContext);

				if (activityFeedEntry != null) {
					activityFeedEntry.setPortletId(
						socialActivityInterpreterImpl.getPortletId());

					return activityFeedEntry;
				}
			}
		}

		return null;
	}

	@Override
	public SocialActivityFeedEntry interpret(
		String selector, SocialActivitySet activitySet,
		ServiceContext serviceContext) {

		HttpServletRequest httpServletRequest = serviceContext.getRequest();

		if (httpServletRequest == null) {
			return null;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			if (activitySet.getUserId() == themeDisplay.getDefaultUserId()) {
				return null;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		List<SocialActivityInterpreter> activityInterpreters =
			_activityInterpreters.get(selector);

		if (activityInterpreters == null) {
			return null;
		}

		String className = PortalUtil.getClassName(
			activitySet.getClassNameId());

		for (SocialActivityInterpreter activityInterpreter :
				activityInterpreters) {

			SocialActivityInterpreterImpl socialActivityInterpreterImpl =
				(SocialActivityInterpreterImpl)activityInterpreter;

			if (socialActivityInterpreterImpl.hasClassName(className)) {
				SocialActivityFeedEntry activityFeedEntry =
					socialActivityInterpreterImpl.interpret(
						activitySet, serviceContext);

				if (activityFeedEntry != null) {
					activityFeedEntry.setPortletId(
						socialActivityInterpreterImpl.getPortletId());

					return activityFeedEntry;
				}
			}
		}

		return null;
	}

	@Override
	public void updateActivitySet(long activityId) throws PortalException {
		if (!PropsValues.SOCIAL_ACTIVITY_SETS_BUNDLING_ENABLED) {
			socialActivitySetLocalService.addActivitySet(activityId);

			return;
		}

		List<SocialActivityInterpreter> activityInterpreters =
			_activityInterpreters.get(
				PropsValues.SOCIAL_ACTIVITY_SETS_SELECTOR);

		if (activityInterpreters != null) {
			SocialActivity activity =
				socialActivityPersistence.findByPrimaryKey(activityId);

			String className = PortalUtil.getClassName(
				activity.getClassNameId());

			for (SocialActivityInterpreter activityInterpreter :
					activityInterpreters) {

				SocialActivityInterpreterImpl socialActivityInterpreterImpl =
					(SocialActivityInterpreterImpl)activityInterpreter;

				if (socialActivityInterpreterImpl.hasClassName(className)) {
					socialActivityInterpreterImpl.updateActivitySet(activityId);

					return;
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SocialActivityInterpreterLocalServiceImpl.class);

	private final Map<String, List<SocialActivityInterpreter>>
		_activityInterpreters = new HashMap<>();
	private final ServiceRegistrationMap<SocialActivityInterpreter>
		_serviceRegistrations = new ServiceRegistrationMapImpl<>();
	private ServiceTracker<SocialActivityInterpreter, SocialActivityInterpreter>
		_serviceTracker;

	private class SocialActivityInterpreterServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<SocialActivityInterpreter, SocialActivityInterpreter> {

		@Override
		public SocialActivityInterpreter addingService(
			ServiceReference<SocialActivityInterpreter> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			SocialActivityInterpreter activityInterpreter = registry.getService(
				serviceReference);

			if (!(activityInterpreter instanceof
					SocialRequestInterpreterImpl)) {

				String portletId = (String)serviceReference.getProperty(
					"javax.portlet.name");

				activityInterpreter = new SocialActivityInterpreterImpl(
					portletId, activityInterpreter);
			}

			List<SocialActivityInterpreter> activityInterpreters =
				_activityInterpreters.get(activityInterpreter.getSelector());

			if (activityInterpreters == null) {
				activityInterpreters = new ArrayList<>();
			}

			activityInterpreters.add(activityInterpreter);

			_activityInterpreters.put(
				activityInterpreter.getSelector(), activityInterpreters);

			return activityInterpreter;
		}

		@Override
		public void modifiedService(
			ServiceReference<SocialActivityInterpreter> serviceReference,
			SocialActivityInterpreter activityInterpreter) {
		}

		@Override
		public void removedService(
			ServiceReference<SocialActivityInterpreter> serviceReference,
			SocialActivityInterpreter activityInterpreter) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			List<SocialActivityInterpreter> activityInterpreters =
				_activityInterpreters.get(activityInterpreter.getSelector());

			if (activityInterpreters == null) {
				return;
			}

			activityInterpreters.remove(activityInterpreter);
		}

	}

}
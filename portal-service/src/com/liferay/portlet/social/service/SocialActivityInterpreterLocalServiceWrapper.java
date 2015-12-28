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

package com.liferay.portlet.social.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SocialActivityInterpreterLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityInterpreterLocalService
 * @generated
 */
@ProviderType
public class SocialActivityInterpreterLocalServiceWrapper
	implements SocialActivityInterpreterLocalService,
		ServiceWrapper<SocialActivityInterpreterLocalService> {
	public SocialActivityInterpreterLocalServiceWrapper(
		SocialActivityInterpreterLocalService socialActivityInterpreterLocalService) {
		_socialActivityInterpreterLocalService = socialActivityInterpreterLocalService;
	}

	/**
	* Adds the activity interpreter to the list of available interpreters.
	*
	* @param activityInterpreter the activity interpreter
	*/
	@Override
	public void addActivityInterpreter(
		com.liferay.portlet.social.model.SocialActivityInterpreter activityInterpreter) {
		_socialActivityInterpreterLocalService.addActivityInterpreter(activityInterpreter);
	}

	/**
	* Removes the activity interpreter from the list of available interpreters.
	*
	* @param activityInterpreter the activity interpreter
	*/
	@Override
	public void deleteActivityInterpreter(
		com.liferay.portlet.social.model.SocialActivityInterpreter activityInterpreter) {
		_socialActivityInterpreterLocalService.deleteActivityInterpreter(activityInterpreter);
	}

	@Override
	public java.util.Map<java.lang.String, java.util.List<com.liferay.portlet.social.model.SocialActivityInterpreter>> getActivityInterpreters() {
		return _socialActivityInterpreterLocalService.getActivityInterpreters();
	}

	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivityInterpreter> getActivityInterpreters(
		java.lang.String selector) {
		return _socialActivityInterpreterLocalService.getActivityInterpreters(selector);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _socialActivityInterpreterLocalService.getOSGiServiceIdentifier();
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #interpret(String,
	SocialActivity, ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.social.model.SocialActivityFeedEntry interpret(
		com.liferay.portlet.social.model.SocialActivity activity,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return _socialActivityInterpreterLocalService.interpret(activity,
			themeDisplay);
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
	* @param selector the context in which the activity interpreter is used
	* @param activity the activity to be translated to human readable form
	* @param serviceContext the service context to be applied
	* @return the activity feed that is a human readable form of the activity
	record or <code>null</code> if a compatible interpreter is not
	found
	*/
	@Override
	public com.liferay.portlet.social.model.SocialActivityFeedEntry interpret(
		java.lang.String selector,
		com.liferay.portlet.social.model.SocialActivity activity,
		com.liferay.portal.service.ServiceContext serviceContext) {
		return _socialActivityInterpreterLocalService.interpret(selector,
			activity, serviceContext);
	}

	@Override
	public com.liferay.portlet.social.model.SocialActivityFeedEntry interpret(
		java.lang.String selector,
		com.liferay.portlet.social.model.SocialActivitySet activitySet,
		com.liferay.portal.service.ServiceContext serviceContext) {
		return _socialActivityInterpreterLocalService.interpret(selector,
			activitySet, serviceContext);
	}

	@Override
	public void updateActivitySet(long activityId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_socialActivityInterpreterLocalService.updateActivitySet(activityId);
	}

	@Override
	public SocialActivityInterpreterLocalService getWrappedService() {
		return _socialActivityInterpreterLocalService;
	}

	@Override
	public void setWrappedService(
		SocialActivityInterpreterLocalService socialActivityInterpreterLocalService) {
		_socialActivityInterpreterLocalService = socialActivityInterpreterLocalService;
	}

	private SocialActivityInterpreterLocalService _socialActivityInterpreterLocalService;
}
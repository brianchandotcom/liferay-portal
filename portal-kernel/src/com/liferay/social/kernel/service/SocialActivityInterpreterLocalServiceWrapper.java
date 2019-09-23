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

package com.liferay.social.kernel.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SocialActivityInterpreterLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityInterpreterLocalService
 * @generated
 */
public class SocialActivityInterpreterLocalServiceWrapper
	implements ServiceWrapper<SocialActivityInterpreterLocalService>,
			   SocialActivityInterpreterLocalService {

	public SocialActivityInterpreterLocalServiceWrapper(
		SocialActivityInterpreterLocalService
			socialActivityInterpreterLocalService) {

		_socialActivityInterpreterLocalService =
			socialActivityInterpreterLocalService;
	}

	/**
	 * Adds the activity interpreter to the list of available interpreters.
	 *
	 * @param activityInterpreter the activity interpreter
	 */
	@Override
	public void addActivityInterpreter(
		com.liferay.social.kernel.model.SocialActivityInterpreter
			activityInterpreter) {

		_socialActivityInterpreterLocalService.addActivityInterpreter(
			activityInterpreter);
	}

	/**
	 * Removes the activity interpreter from the list of available interpreters.
	 *
	 * @param activityInterpreter the activity interpreter
	 */
	@Override
	public void deleteActivityInterpreter(
		com.liferay.social.kernel.model.SocialActivityInterpreter
			activityInterpreter) {

		_socialActivityInterpreterLocalService.deleteActivityInterpreter(
			activityInterpreter);
	}

	@Override
	public java.util.Map
		<String,
		 java.util.List
			 <com.liferay.social.kernel.model.SocialActivityInterpreter>>
				getActivityInterpreters() {

		return _socialActivityInterpreterLocalService.getActivityInterpreters();
	}

	@Override
	public java.util.List
		<com.liferay.social.kernel.model.SocialActivityInterpreter>
			getActivityInterpreters(String selector) {

		return _socialActivityInterpreterLocalService.getActivityInterpreters(
			selector);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _socialActivityInterpreterLocalService.
			getOSGiServiceIdentifier();
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
	public com.liferay.social.kernel.model.SocialActivityFeedEntry interpret(
		String selector,
		com.liferay.social.kernel.model.SocialActivity activity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _socialActivityInterpreterLocalService.interpret(
			selector, activity, serviceContext);
	}

	@Override
	public com.liferay.social.kernel.model.SocialActivityFeedEntry interpret(
		String selector,
		com.liferay.social.kernel.model.SocialActivitySet activitySet,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return _socialActivityInterpreterLocalService.interpret(
			selector, activitySet, serviceContext);
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
		SocialActivityInterpreterLocalService
			socialActivityInterpreterLocalService) {

		_socialActivityInterpreterLocalService =
			socialActivityInterpreterLocalService;
	}

	private SocialActivityInterpreterLocalService
		_socialActivityInterpreterLocalService;

}
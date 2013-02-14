/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivitySet;
import com.liferay.portlet.social.model.impl.SocialActivityInterpreterImpl;
import com.liferay.portlet.social.service.base.SocialActivityInterpreterLocalServiceBaseImpl;

import java.util.HashMap;
import java.util.Map;

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
	public void addActivityInterpreter(
		SocialActivityInterpreter activityInterpreter) {

		String[] classNames = activityInterpreter.getClassNames();

		for (String className : classNames) {
			_activityInterpreters.put(className, activityInterpreter);
		}
	}

	/**
	 * Removes the activity interpreter from the list of available interpreters.
	 *
	 * @param activityInterpreter the activity interpreter
	 */
	public void deleteActivityInterpreter(
		SocialActivityInterpreter activityInterpreter) {

		String[] classNames = activityInterpreter.getClassNames();

		for (String className : classNames) {
			_activityInterpreters.remove(className);
		}
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
	 * @param  activity the activity to be translated to human readable form
	 * @param  themeDisplay the theme display needed by interpreters to create
	 *         links and get localized text fragments
	 * @return the activity feed that is a human readable form of the activity
	 *         record or <code>null</code> if a compatible interpreter is not
	 *         found
	 */
	public SocialActivityFeedEntry interpret(
		SocialActivity activity, ThemeDisplay themeDisplay) {

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

		String className = PortalUtil.getClassName(activity.getClassNameId());

		SocialActivityInterpreterImpl activityInterpreter =
			(SocialActivityInterpreterImpl)_activityInterpreters.get(className);

		if (activityInterpreter != null) {
			SocialActivityFeedEntry activityFeedEntry =
				activityInterpreter.interpret(activity, themeDisplay);

			if (activityFeedEntry != null) {
				activityFeedEntry.setPortletId(
					activityInterpreter.getPortletId());

				return activityFeedEntry;
			}
		}

		return null;
	}

	public SocialActivityFeedEntry interpret(
		SocialActivitySet activitySet, ThemeDisplay themeDisplay) {

		try {
			if (activitySet.getUserId() == themeDisplay.getDefaultUserId()) {
				return null;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		String className = PortalUtil.getClassName(
			activitySet.getClassNameId());

		SocialActivityInterpreterImpl activityInterpreter =
			(SocialActivityInterpreterImpl)_activityInterpreters.get(className);

		if (activityInterpreter != null) {
			SocialActivityFeedEntry activityFeedEntry =
					activityInterpreter.interpret(activitySet, themeDisplay);

			if (activityFeedEntry != null) {
				activityFeedEntry.setPortletId(
					activityInterpreter.getPortletId());

				return activityFeedEntry;
			}
		}

		return null;
	}

	private static Log _log = LogFactoryUtil.getLog(
		SocialActivityInterpreterLocalServiceImpl.class);

	private Map<String, SocialActivityInterpreter> _activityInterpreters =
		new HashMap<String, SocialActivityInterpreter>();

}
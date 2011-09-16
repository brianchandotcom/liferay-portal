/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.model.impl.SocialActivityInterpreterImpl;
import com.liferay.portlet.social.service.base.SocialActivityInterpreterLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * The social activity interpreter local service. Activity interpreters are
 * classes responsible for translating activity records into human readable
 * form. This service holds a list of interpreters and provides methods to add
 * or remove items from this list.
 *
 * <p>
 * Activity interpreters use the language files to get text fragments based on
 * the activity's type and the type of asset the activity was done on.
 * Interpreters are created for a specific asset type and are only capable of
 * translating activities done on that particular asset. As an example, there
 * is an interpreter called BlogsActivityInterpreter. This class can only
 * translate activity records for blog entries.
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

		_activityInterpreters.add(activityInterpreter);
	}

	/**
	 * Removes the interpreter from the list of available interpreters.
	 *
	 * @param activityInterpreter the activity interpreter
	 */
	public void deleteActivityInterpreter(
		SocialActivityInterpreter activityInterpreter) {

		if (activityInterpreter != null) {
			_activityInterpreters.remove(activityInterpreter);
		}
	}

	/**
	 * Creates a human readable activity feed entry for the activity using one
	 * of the available activity interpreters.
	 *
	 * <p>
	 * This method finds the appropriate interpreter for the activity by going
	 * through the available interpreters and asking them if they can handle
	 * the asset type of the activity.
	 * </p>
	 *
	 * @param  activity the activity to be translated to human readable form
	 * @param  themeDisplay the theme display needed by interpreters to create
	 *         links and get localized text fragments
	 * @return the activity feed that is a human readable form of the activity
	 *         record
	 */
	public SocialActivityFeedEntry interpret(
		SocialActivity activity, ThemeDisplay themeDisplay) {

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

		for (int i = 0; i < _activityInterpreters.size(); i++) {
			SocialActivityInterpreterImpl activityInterpreter =
				(SocialActivityInterpreterImpl)_activityInterpreters.get(i);

			if (activityInterpreter.hasClassName(className)) {
				SocialActivityFeedEntry activityFeedEntry =
					activityInterpreter.interpret(activity, themeDisplay);

				if (activityFeedEntry != null) {
					activityFeedEntry.setPortletId(
						activityInterpreter.getPortletId());

					return activityFeedEntry;
				}
			}
		}

		return null;
	}

	private List<SocialActivityInterpreter> _activityInterpreters =
		new ArrayList<SocialActivityInterpreter>();

}
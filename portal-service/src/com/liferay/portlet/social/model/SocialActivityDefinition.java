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

package com.liferay.portlet.social.model;

import com.liferay.portal.kernel.language.LanguageUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Zsolt Berentey
 */
public class SocialActivityDefinition implements Serializable {

	public void addCounter(
		SocialActivityCounterDefinition activityCounterDefinition) {

		_activityCounterDefinitions.put(
			activityCounterDefinition.getName(), activityCounterDefinition);
	}

	@Override
	public SocialActivityDefinition clone() {
		SocialActivityDefinition activityDefinition =
			new SocialActivityDefinition();

		for (SocialActivityCounterDefinition activityCounterDefinition :
				_activityCounterDefinitions.values()) {

			activityDefinition.addCounter(activityCounterDefinition.clone());
		}

		List<SocialAchievement> achievements =
			activityDefinition.getAchievements();

		achievements.addAll(_achievements);

		activityDefinition.setActivityProcessor(_activityProcessor);
		activityDefinition.setActivityType(_activityType);
		activityDefinition.setEnabled(_enabled);
		activityDefinition.setLanguageKey(_languageKey);
		activityDefinition.setLogActivity(_logActivity);
		activityDefinition.setModelName(_modelName);

		return activityDefinition;
	}

	public List<SocialAchievement> getAchievements() {
		return _achievements;
	}

	public SocialActivityCounterDefinition getActivityCounterDefinition(
		String name) {

		return _activityCounterDefinitions.get(name);
	}

	public Collection<SocialActivityCounterDefinition>
		getActivityCounterDefinitions() {

		return _activityCounterDefinitions.values();
	}

	public SocialActivityProcessor getActivityProcessor() {
		return _activityProcessor;
	}

	public int getActivityType() {
		return _activityType;
	}

	public String getLanguageKey() {
		return _languageKey;
	}

	public String getModelName() {
		return _modelName;
	}

	public String getName(Locale locale) {
		return LanguageUtil.get(
			locale, "social.activity." + _modelName + "." + _languageKey);
	}

	public boolean isEnabled() {
		return _enabled;
	}

	public boolean isLogActivity() {
		return _logActivity;
	}

	public void setActivityProcessor(
		SocialActivityProcessor activityProcessor) {

		_activityProcessor = activityProcessor;
	}

	public void setActivityType(int activityKey) {
		_activityType = activityKey;
	}

	public void setCounters(
		List<SocialActivityCounterDefinition> activityCounterDefinitions) {

		_activityCounterDefinitions.clear();

		for (SocialActivityCounterDefinition activityCounterDefinition :
				activityCounterDefinitions) {

			_activityCounterDefinitions.put(
				activityCounterDefinition.getName(), activityCounterDefinition);
		}
	}

	public void setEnabled(boolean enabled) {
		_enabled = enabled;
	}

	public void setLanguageKey(String languageKey) {
		_languageKey = languageKey;
	}

	public void setLogActivity(boolean logActivity) {
		_logActivity = logActivity;
	}

	public void setModelName(String modelName) {
		_modelName = modelName;
	}

	private List<SocialAchievement> _achievements =
		new ArrayList<SocialAchievement>();
	private Map<String, SocialActivityCounterDefinition>
		_activityCounterDefinitions =
			new HashMap<String, SocialActivityCounterDefinition>();
	private SocialActivityProcessor _activityProcessor;
	private int _activityType;
	private boolean _enabled = true;
	private String _languageKey;
	private boolean _logActivity;
	private String _modelName;

}
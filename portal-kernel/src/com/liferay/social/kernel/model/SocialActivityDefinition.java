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

package com.liferay.social.kernel.model;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

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
		activityDefinition.setCountersEnabled(_countersEnabled);
		activityDefinition.setLanguageKey(_languageKey);
		activityDefinition.setLogActivity(_logActivity);
		activityDefinition.setModelName(_modelName);

		return activityDefinition;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SocialActivityDefinition)) {
			return false;
		}

		SocialActivityDefinition activityDefinition =
			(SocialActivityDefinition)obj;

		if ((activityDefinition != null) &&
			Objects.equals(
				_achievements, activityDefinition.getAchievements()) &&
			Objects.equals(
				_activityCounterDefinitions,
				activityDefinition._activityCounterDefinitions) &&
			Objects.equals(
				_activityProcessor,
				activityDefinition.getActivityProcessor()) &&
			(_activityType == activityDefinition.getActivityType()) &&
			(_countersEnabled == activityDefinition.isCountersEnabled()) &&
			Objects.equals(_languageKey, activityDefinition.getLanguageKey()) &&
			(_logActivity == activityDefinition.isLogActivity()) &&
			Objects.equals(_modelName, activityDefinition.getModelName())) {

			return true;
		}

		return false;
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

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _achievements);

		hash = HashUtil.hash(hash, _activityCounterDefinitions);
		hash = HashUtil.hash(hash, _activityProcessor);
		hash = HashUtil.hash(hash, _activityType);
		hash = HashUtil.hash(hash, _countersEnabled);
		hash = HashUtil.hash(hash, _languageKey);
		hash = HashUtil.hash(hash, _logActivity);

		return HashUtil.hash(hash, _modelName);
	}

	public boolean isCountersEnabled() {
		return _countersEnabled;
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

	public void setCountersEnabled(boolean enabled) {
		_countersEnabled = enabled;
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

	private final List<SocialAchievement> _achievements = new ArrayList<>();
	private final Map<String, SocialActivityCounterDefinition>
		_activityCounterDefinitions = new HashMap<>();
	private SocialActivityProcessor _activityProcessor;
	private int _activityType;
	private boolean _countersEnabled = true;
	private String _languageKey;
	private boolean _logActivity;
	private String _modelName;

}
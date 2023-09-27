/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.model;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.LanguageUtil;

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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SocialActivityDefinition)) {
			return false;
		}

		SocialActivityDefinition activityDefinition =
			(SocialActivityDefinition)object;

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
			locale,
			StringBundler.concat(
				"social.activity.", _modelName, ".", _languageKey));
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
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

package com.liferay.portlet.social.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zsolt Berentey
 */
public class SocialActivityDefinition implements Serializable {

	public void addCounter(SocialActivityCounterDefinition counter) {
		_counters.put(counter.getName(), counter);
	}

	public SocialActivityDefinition clone() {

		SocialActivityDefinition activityDefinition =
			new SocialActivityDefinition();

		activityDefinition.setActivityHandler(_activityHandler);
		activityDefinition.setActivityKey(_activityKey);
		activityDefinition.setLanguageKey(_languageKey);
		activityDefinition.setLogActivity(_logActivity);
		activityDefinition.setModelName(_modelName);
		activityDefinition.setStatsEnabled(_statsEnabled);

		// Counters

		for (SocialActivityCounterDefinition counter : _counters.values()) {
			activityDefinition.addCounter(counter.clone());
		}

		// Achievements

		activityDefinition.getAchievements().addAll(_achievements);

		return activityDefinition;
	}

	public List<SocialAchievement> getAchievements() {
		return _achievements;
	}

	public SocialActivityHandler getActivityHandler() {
		return _activityHandler;
	}

	public int getActivityKey() {
		return _activityKey;
	}

	public SocialActivityCounterDefinition getCounter(String name) {
		return _counters.get(name);
	}

	public Collection<SocialActivityCounterDefinition> getCounters() {
		return _counters.values();
	}

	public String getLanguageKey() {
		return _languageKey;
	}

	public String getModelName() {
		return _modelName;
	}

	public boolean isLogActivity() {
		return _logActivity;
	}

	public boolean isStatsEnabled() {
		return _statsEnabled;
	}

	public void setActivityHandler(SocialActivityHandler activityHandler) {
		_activityHandler = activityHandler;
	}

	public void setActivityKey(int activityKey) {
		_activityKey = activityKey;
	}

	public void setCounters(List<SocialActivityCounterDefinition> counters) {
		_counters.clear();

		for (SocialActivityCounterDefinition counter: counters) {
			_counters.put(counter.getName(), counter);
		}
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

	public void setStatsEnabled(boolean statsEnabled) {
		_statsEnabled = statsEnabled;
	}

	private List<SocialAchievement> _achievements =
		new ArrayList<SocialAchievement>();
	private SocialActivityHandler _activityHandler;
	private int _activityKey;
	private Map<String, SocialActivityCounterDefinition> _counters =
		new HashMap<String, SocialActivityCounterDefinition>();
	private String _languageKey;
	private boolean _logActivity;
	private String _modelName;
	private boolean _statsEnabled = true;

}
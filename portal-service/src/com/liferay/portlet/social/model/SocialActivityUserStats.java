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

import com.liferay.portal.kernel.util.KeyValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zsolt Berentey
 */
public class SocialActivityUserStats {

	public void addStat(KeyValuePair stat) {
		_stats.add(stat);
	}

	public List<KeyValuePair> getStats() {
		return _stats;
	}

	public KeyValuePair getStat(String statName) {
		for (KeyValuePair stat : _stats) {
			if (stat.getKey().equalsIgnoreCase(statName)) {
				return stat;
			}
		}

		return null;
	}

	public long getUserId() {
		return _userId;
	}

	public void removeStat(KeyValuePair stat) {
		_stats.remove(stat);
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	private long _userId;
	private List<KeyValuePair> _stats = new ArrayList<KeyValuePair>();

}
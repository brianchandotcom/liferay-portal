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

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Zsolt Berentey
 */
public class SocialActivityCounterDefinition implements Serializable {

	public static final int LIMIT_PERIOD_DAY = 1;

	public static final int LIMIT_PERIOD_LIFETIME = 2;

	public static final int LIMIT_PERIOD_PERIOD = 3;

	public SocialActivityCounterDefinition() {
	}

	public SocialActivityCounterDefinition(String name, int ownerType) {
		_name = name;
		_ownerType = ownerType;
	}

	@Override
	public SocialActivityCounterDefinition clone() {
		SocialActivityCounterDefinition activityCounterDefinition =
			new SocialActivityCounterDefinition();

		activityCounterDefinition.setEnabled(_enabled);
		activityCounterDefinition.setIncrement(_increment);
		activityCounterDefinition.setLimitEnabled(_limitEnabled);
		activityCounterDefinition.setLimitPeriod(_limitPeriod);
		activityCounterDefinition.setLimitValue(_limitValue);
		activityCounterDefinition.setName(_name);
		activityCounterDefinition.setOwnerType(_ownerType);
		activityCounterDefinition.setPeriodLength(_periodLength);
		activityCounterDefinition.setTransient(_transient);

		return activityCounterDefinition;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SocialActivityCounterDefinition)) {
			return false;
		}

		SocialActivityCounterDefinition activityCounterDefinition =
			(SocialActivityCounterDefinition)obj;

		if ((activityCounterDefinition != null) &&
			(_enabled == activityCounterDefinition._enabled) &&
			(_increment == activityCounterDefinition._increment) &&
			(_limitEnabled == activityCounterDefinition._limitEnabled) &&
			(_limitPeriod == activityCounterDefinition._limitPeriod) &&
			(_limitValue == activityCounterDefinition._limitValue) &&
			Objects.equals(_name, activityCounterDefinition._name) &&
			(_ownerType == activityCounterDefinition._ownerType) &&
			(_periodLength == activityCounterDefinition._periodLength) &&
			(_transient == activityCounterDefinition._transient)) {

			return true;
		}

		return false;
	}

	public int getIncrement() {
		return _increment;
	}

	public String getKey() {
		return _name.concat(
			StringPool.SLASH
		).concat(
			String.valueOf(_ownerType)
		);
	}

	public int getLimitPeriod() {
		return _limitPeriod;
	}

	public int getLimitValue() {
		return _limitValue;
	}

	public String getName() {
		return _name;
	}

	public int getOwnerType() {
		return _ownerType;
	}

	public int getPeriodLength() {
		return _periodLength;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _enabled);

		hash = HashUtil.hash(hash, _increment);
		hash = HashUtil.hash(hash, _limitEnabled);
		hash = HashUtil.hash(hash, _limitPeriod);
		hash = HashUtil.hash(hash, _limitValue);
		hash = HashUtil.hash(hash, _name);
		hash = HashUtil.hash(hash, _ownerType);
		hash = HashUtil.hash(hash, _periodLength);

		return HashUtil.hash(hash, _transient);
	}

	public boolean isEnabled() {
		return _enabled;
	}

	public boolean isLimitEnabled() {
		return _limitEnabled;
	}

	public boolean isTransient() {
		return _transient;
	}

	public void setEnabled(boolean enabled) {
		_enabled = enabled;
	}

	public void setIncrement(int increment) {
		_increment = increment;
	}

	public void setLimitEnabled(boolean limitEnabled) {
		_limitEnabled = limitEnabled;
	}

	public void setLimitPeriod(int limitPeriod) {
		_limitPeriod = limitPeriod;
	}

	public void setLimitPeriod(String limitPeriod) {
		if (StringUtil.equalsIgnoreCase(limitPeriod, "day")) {
			setLimitPeriod(LIMIT_PERIOD_DAY);
		}
		else if (StringUtil.equalsIgnoreCase(limitPeriod, "lifetime")) {
			setLimitPeriod(LIMIT_PERIOD_LIFETIME);
		}
		else {
			setLimitPeriod(LIMIT_PERIOD_PERIOD);
		}
	}

	public void setLimitValue(int limitValue) {
		_limitValue = limitValue;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setOwnerType(int ownerType) {
		_ownerType = ownerType;
	}

	public void setOwnerType(String ownerType) {
		if (StringUtil.equalsIgnoreCase(ownerType, "actor")) {
			setOwnerType(SocialActivityCounterConstants.TYPE_ACTOR);
		}
		else if (StringUtil.equalsIgnoreCase(ownerType, "asset")) {
			setOwnerType(SocialActivityCounterConstants.TYPE_ASSET);
		}
		else if (StringUtil.equalsIgnoreCase(ownerType, "creator")) {
			setOwnerType(SocialActivityCounterConstants.TYPE_CREATOR);
		}
	}

	public void setPeriodLength(int periodLength) {
		_periodLength = periodLength;
	}

	public void setTransient(boolean transientCounter) {
		_transient = transientCounter;
	}

	private boolean _enabled = true;
	private int _increment = 1;
	private boolean _limitEnabled = true;
	private int _limitPeriod = LIMIT_PERIOD_DAY;
	private int _limitValue;
	private String _name;
	private int _ownerType;
	private int _periodLength =
		SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM;
	private boolean _transient;

}
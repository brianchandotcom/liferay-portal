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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

/**
 * @author Zsolt Berentey
 */
public class SocialActivityCounterDefinition implements Serializable {

	public static final int LIMIT_PERIOD_DAY = 1;

	public static final int LIMIT_PERIOD_LIFETIME = 2;

	public static final int LIMIT_PERIOD_PERIOD = 3;

	@Override
	public SocialActivityCounterDefinition clone() {
		SocialActivityCounterDefinition activityCounterDefinition =
			new SocialActivityCounterDefinition();

		activityCounterDefinition.setIncrement(_increment);
		activityCounterDefinition.setLimitValue(_limitValue);
		activityCounterDefinition.setLimitPeriod(_limitPeriod);
		activityCounterDefinition.setName(_name);
		activityCounterDefinition.setOwnerType(_ownerType);

		return activityCounterDefinition;
	}

	public boolean equals(
		SocialActivityCounterDefinition activityCounterDefinition) {

		if (Validator.equals(
				_increment, activityCounterDefinition._increment) &&
			Validator.equals(
				_limitValue, activityCounterDefinition._limitValue) &&
			Validator.equals(
				_limitPeriod, activityCounterDefinition._limitPeriod) &&
			Validator.equals(_name, activityCounterDefinition._name) &&
			Validator.equals(
				_ownerType, activityCounterDefinition._ownerType)) {

			return true;
		}

		return false;
	}

	public int getIncrement() {
		return _increment;
	}

	public String getKey() {
		return _name.concat(StringPool.SLASH).concat(
			String.valueOf(_ownerType));
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

	public void setIncrement(int increment) {
		_increment = increment;
	}

	public void setLimitPeriod(int limitPeriod) {
		_limitPeriod = limitPeriod;
	}

	public void setLimitPeriod(String limitPeriod) {
		if (limitPeriod.equalsIgnoreCase("day")) {
			setLimitPeriod(LIMIT_PERIOD_DAY);
		}
		else if (limitPeriod.equalsIgnoreCase("lifetime")) {
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
		if (ownerType.equalsIgnoreCase("actor")) {
			setOwnerType(SocialActivityCounterConstants.TYPE_ACTOR);
		}
		else if (ownerType.equalsIgnoreCase("asset")) {
			setOwnerType(SocialActivityCounterConstants.TYPE_ASSET);
		}
		else if (ownerType.equalsIgnoreCase("creator")) {
			setOwnerType(SocialActivityCounterConstants.TYPE_CREATOR);
		}
	}

	private int _increment;
	private int _limitPeriod;
	private int _limitValue;
	private String _name;
	private int _ownerType;

}
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

import java.io.Serializable;

/**
 * @author Zsolt Berentey
 */
public class SocialActivityCounterDefinition implements Serializable {

	public SocialActivityCounterDefinition() {
	}

	public SocialActivityCounterDefinition(String name, int ownerType) {
		_name = name;
		_ownerType = ownerType;
	}

	public SocialActivityCounterDefinition clone() {
		SocialActivityCounterDefinition counter =
			new SocialActivityCounterDefinition();

		counter.setName(_name);
		counter.setOwnerType(_ownerType);
		counter.setIncrement(_increment);
		counter.setLimit(_limit);
		counter.setLimitType(_limitType);

		return counter;
	}

	public boolean equals(SocialActivityCounterDefinition counter) {
		if (_name.equals(counter.getName())) {
			if (_ownerType == counter.getOwnerType()) {
				if (_increment == counter.getIncrement()) {
					if (_limit == counter.getLimit()) {
						if (_limitType == counter.getLimitType()) {
							return true;
						}
					}
				}
			}
 		}

		return false;
	}

	public int getIncrement() {
		return _increment;
	}

	public String getKey() {
		return _name + StringPool.SLASH + _ownerType;
	}

	public int getLimit() {
		return _limit;
	}

	public int getLimitType() {
		return _limitType;
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

	public void setLimit(int limit) {
		_limit = limit;
	}

	public void setLimitType(int limitType) {
		if (limitType < 1 || limitType > 3) {
			limitType = SocialActivityConstants.LIMIT_PER_PERIOD;
		}

		_limitType = limitType;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setOwnerType(int ownerType) {
		_ownerType = ownerType;
	}

	public void setOwnerType(String ownerType) {
		if ("actor".equals(ownerType)) {
			setOwnerType(SocialActivityCounterConstants.TYPE_ACTOR);
		} else if ("asset".equals(ownerType)) {
			setOwnerType(SocialActivityCounterConstants.TYPE_ASSET);
		} else if ("creator".equals(ownerType)) {
			setOwnerType(SocialActivityCounterConstants.TYPE_CREATOR);
		}
	}

	private int _limit;
	private int _limitType;
	private String _name;
	private int _ownerType;
	private int _increment;

}
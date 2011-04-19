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

/**
 * @author Zsolt Berentey
 */
public class SocialActivityConstants {

	// Limit Period

	public static final int LIMIT_PER_DAY = 1;

	public static final int LIMIT_PER_LIFETIME = 2;

	public static final int LIMIT_PER_PERIOD = 3;

	// Owner Type

	public static final int OWNER_TYPE_ALL = 0;

	public static final int OWNER_TYPE_ACTOR = 1;

	public static final int OWNER_TYPE_ASSET = 2;

	public static final int OWNER_TYPE_CREATOR = 3;

	// Stat Name

	public static final String STAT_ASSET_ACTIVITY = "asset.activity";

	public static final String STAT_CONTRIBUTION = "contribution";

	public static final String STAT_PARTICIPATION = "participation";

	public static final String STAT_POPULARITY = "popularity";

	public static final String STAT_USER_ACHIEVEMENT = "user.achievement";

	public static final String STAT_USER_ACTIVITY = "user.activity";

	// Special Activity Keys

	public static final int VIEW = 10001;

	public static final int SUBSCRIBE = 10002;

	public static final int UNSUBSCRIBE = 10003;

	public static final int ADD_VOTE = 10004;

}
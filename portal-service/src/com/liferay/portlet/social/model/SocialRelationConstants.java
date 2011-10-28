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
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class SocialRelationConstants {

	public static final int TYPE_BI_CONNECTION = 12;

	public static final int TYPE_BI_COWORKER = 1;

	public static final int TYPE_BI_FRIEND = 2;

	public static final int TYPE_BI_ROMANTIC_PARTNER = 3;

	public static final int TYPE_BI_SIBLING = 4;

	public static final int TYPE_BI_SPOUSE = 5;

	public static final int TYPE_UNI_CHILD = 6;

	public static final int TYPE_UNI_ENEMY = 9;

	public static final int TYPE_UNI_FOLLOWER = 8;

	public static final int TYPE_UNI_PARENT = 7;

	public static final int TYPE_UNI_SUBORDINATE = 10;

	public static final int TYPE_UNI_SUPERVISOR = 11;

	public static boolean isTypeBi(int type) {
		return !isTypeUni(type);
	}

	public static boolean isTypeUni(int type) {
		if ((type == TYPE_UNI_CHILD) || (type == TYPE_UNI_ENEMY) ||
			(type == TYPE_UNI_FOLLOWER) || (type == TYPE_UNI_PARENT) ||
			(type == TYPE_UNI_SUBORDINATE) || (type == TYPE_UNI_SUPERVISOR)) {

			return true;
		}
		else {
			return false;
		}
	}

}
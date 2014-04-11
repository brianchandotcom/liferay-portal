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

package com.liferay.portlet.social.model;

import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.List;

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

	public static List<Integer> getAllSocialRelationTypes() {
		return _allSocialRelationTypes;
	}

	public static String getTypeLabel(int type) {
		if (type == TYPE_BI_CONNECTION) {
			return "connection";
		}
		else if (type == TYPE_BI_COWORKER) {
			return "coworker";
		}
		else if (type == TYPE_BI_FRIEND) {
			return "friend";
		}
		else if (type == TYPE_BI_ROMANTIC_PARTNER) {
			return "romantic-partner";
		}
		else if (type == TYPE_BI_SIBLING) {
			return "sibling";
		}
		else if (type == TYPE_BI_SPOUSE) {
			return "spouse";
		}
		else if (type == TYPE_UNI_CHILD) {
			return "child";
		}
		else if (type == TYPE_UNI_ENEMY) {
			return "enemy";
		}
		else if (type == TYPE_UNI_FOLLOWER) {
			return "follower";
		}
		else if (type == TYPE_UNI_PARENT) {
			return "parent";
		}
		else if (type == TYPE_UNI_SUBORDINATE) {
			return "subordinate";
		}
		else if (type == TYPE_UNI_SUPERVISOR) {
			return "supervisor";
		}

		return StringPool.BLANK;
	}

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

	private static List<Integer> _allSocialRelationTypes =
		new ArrayList<Integer>();

	static {
		_allSocialRelationTypes.add(TYPE_BI_CONNECTION);
		_allSocialRelationTypes.add(TYPE_BI_COWORKER);
		_allSocialRelationTypes.add(TYPE_BI_FRIEND);
		_allSocialRelationTypes.add(TYPE_BI_ROMANTIC_PARTNER);
		_allSocialRelationTypes.add(TYPE_BI_SIBLING);
		_allSocialRelationTypes.add(TYPE_BI_SPOUSE);
		_allSocialRelationTypes.add(TYPE_UNI_CHILD);
		_allSocialRelationTypes.add(TYPE_UNI_ENEMY);
		_allSocialRelationTypes.add(TYPE_UNI_FOLLOWER);
		_allSocialRelationTypes.add(TYPE_UNI_PARENT);
		_allSocialRelationTypes.add(TYPE_UNI_SUBORDINATE);
		_allSocialRelationTypes.add(TYPE_UNI_SUPERVISOR);
	}

}
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

package com.liferay.portlet.social.util;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class SocialRelationTypesUtil {

	public static List<Integer> getAllSocialRelationTypes() {
		return getSocialRelationTypes().getAllSocialRelationTypes();
	}

	public static SocialRelationTypes getSocialRelationTypes() {
		PortalRuntimePermission.checkGetBeanProperty(SocialRelationTypes.class);

		return _socialRelationTypes;
	}

	public static String getTypeLabel(int type) {
		return getSocialRelationTypes().getTypeLabel(type);
	}

	public static boolean isTypeBi(int type) {
		return getSocialRelationTypes().isTypeBi(type);
	}

	public static boolean isTypeUni(int type) {
		return getSocialRelationTypes().isTypeUni(type);
	}

	public void setSocialRelationTypes(
		SocialRelationTypes socialRelationTypes) {

		PortalRuntimePermission.checkSetBeanProperty(SocialRelationTypes.class);

		_socialRelationTypes = socialRelationTypes;
	}

	private static SocialRelationTypes _socialRelationTypes;

}

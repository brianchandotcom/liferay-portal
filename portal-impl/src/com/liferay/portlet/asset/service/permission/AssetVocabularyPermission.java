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

package com.liferay.portlet.asset.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;

/**
 * @author Eduardo Lundgren
 * @author JorgeFerrer
 */
public class AssetVocabularyPermission {

	public static void check(
			PermissionChecker permissionChecker, AssetVocabulary vocabulary,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, vocabulary, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker.getUserId(), AssetVocabulary.class.getName(),
				vocabulary.getVocabularyId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long vocabularyId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, vocabularyId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker.getUserId(), AssetVocabulary.class.getName(),
				vocabularyId, actionId);
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, AssetVocabulary vocabulary,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				vocabulary.getCompanyId(), AssetVocabulary.class.getName(),
				vocabulary.getVocabularyId(), vocabulary.getUserId(),
				actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			vocabulary.getGroupId(), AssetVocabulary.class.getName(),
			vocabulary.getVocabularyId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long vocabularyId,
			String actionId)
		throws PortalException {

		AssetVocabulary vocabulary =
			AssetVocabularyLocalServiceUtil.getVocabulary(vocabularyId);

		return contains(permissionChecker, vocabulary, actionId);
	}

}
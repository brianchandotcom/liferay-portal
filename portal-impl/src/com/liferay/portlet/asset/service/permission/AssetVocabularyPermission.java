/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.service.permission;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

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
				permissionChecker, AssetVocabulary.class.getName(),
				vocabulary.getVocabularyId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long vocabularyId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, vocabularyId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, AssetVocabulary.class.getName(),
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

		return contains(
			permissionChecker,
			AssetVocabularyLocalServiceUtil.getVocabulary(vocabularyId),
			actionId);
	}

}
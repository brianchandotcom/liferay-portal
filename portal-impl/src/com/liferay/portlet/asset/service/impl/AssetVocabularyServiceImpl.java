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

package com.liferay.portlet.asset.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.base.AssetVocabularyServiceBaseImpl;
import com.liferay.portlet.asset.service.permission.AssetPermission;
import com.liferay.portlet.asset.service.permission.AssetVocabularyPermission;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Alvaro del Castillo
 * @author Eduardo Lundgren
 * @author Jorge Ferrer
 */
public class AssetVocabularyServiceImpl
	extends AssetVocabularyServiceBaseImpl {

	/**
	 * @deprecated
	 */
	public AssetVocabulary addVocabulary(
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String settings, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addVocabulary(
			StringPool.BLANK, titleMap, descriptionMap, settings,
			serviceContext);
	}

	public AssetVocabulary addVocabulary(
			String title, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String settings,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		AssetPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_VOCABULARY);

		return assetVocabularyLocalService.addVocabulary(
			getUserId(), title, titleMap, descriptionMap, settings,
			serviceContext);
	}

	public void deleteVocabulary(long vocabularyId)
		throws PortalException, SystemException {

		AssetVocabularyPermission.check(
			getPermissionChecker(), vocabularyId, ActionKeys.DELETE);

		assetVocabularyLocalService.deleteVocabulary(vocabularyId);
	}

	public void deleteVocabularies(long[] vocabularyIds)
		throws PortalException, SystemException {

		for (long vocabularyId : vocabularyIds) {
			AssetVocabularyPermission.check(
				getPermissionChecker(), vocabularyId, ActionKeys.DELETE);

			assetVocabularyLocalService.deleteVocabulary(vocabularyId);
		}
	}

	public List<AssetVocabulary> getCompanyVocabularies(long companyId)
		throws PortalException, SystemException {

		return filterVocabularies(
			assetVocabularyLocalService.getCompanyVocabularies(companyId));
	}

	public List<AssetVocabulary> getGroupsVocabularies(long[] groupIds)
		throws PortalException, SystemException {

		return getGroupsVocabularies(groupIds, null);
	}

	public List<AssetVocabulary> getGroupsVocabularies(
			long[] groupIds, String className)
		throws PortalException, SystemException {

		return filterVocabularies(
			assetVocabularyLocalService.getGroupsVocabularies(
				groupIds, className));
	}

	public List<AssetVocabulary> getGroupVocabularies(long groupId)
		throws PortalException, SystemException {

		return filterVocabularies(
			assetVocabularyLocalService.getGroupVocabularies(groupId));
	}

	public List<AssetVocabulary> getVocabularies(long[] vocabularyIds)
		throws PortalException, SystemException {

		return filterVocabularies(
			assetVocabularyLocalService.getVocabularies(vocabularyIds));
	}

	public AssetVocabulary getVocabulary(long vocabularyId)
		throws PortalException, SystemException {

		AssetVocabularyPermission.check(
			getPermissionChecker(), vocabularyId, ActionKeys.VIEW);

		return assetVocabularyLocalService.getVocabulary(vocabularyId);
	}

	/**
	 * @deprecated
	 */
	public AssetVocabulary updateVocabulary(
			long vocabularyId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String settings,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return updateVocabulary(
			vocabularyId, StringPool.BLANK, titleMap, descriptionMap, settings,
			serviceContext);
	}

	public AssetVocabulary updateVocabulary(
			long vocabularyId, String title, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String settings,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		AssetVocabularyPermission.check(
			getPermissionChecker(), vocabularyId, ActionKeys.UPDATE);

		return assetVocabularyLocalService.updateVocabulary(
			vocabularyId, title, titleMap, descriptionMap, settings,
			serviceContext);
	}

	protected List<AssetVocabulary> filterVocabularies(
			List<AssetVocabulary> vocabularies)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		vocabularies = ListUtil.copy(vocabularies);

		Iterator<AssetVocabulary> itr = vocabularies.iterator();

		while (itr.hasNext()) {
			AssetVocabulary vocabulary = itr.next();

			if (!AssetVocabularyPermission.contains(
					permissionChecker, vocabulary, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return vocabularies;
	}

}

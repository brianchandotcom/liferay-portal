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

package com.liferay.portlet.asset.service.impl;

import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyDisplay;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.asset.service.base.AssetVocabularyServiceBaseImpl;
import com.liferay.portlet.asset.service.permission.AssetCategoriesPermission;
import com.liferay.portlet.asset.service.permission.AssetVocabularyPermission;
import com.liferay.portlet.asset.util.AssetUtil;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service for accessing, adding, deleting, and updating
 * asset vocabularies. Its methods include permission checks.
 *
 * @author Alvaro del Castillo
 * @author Eduardo Lundgren
 * @author Jorge Ferrer
 * @author Juan Fernández
 */
public class AssetVocabularyServiceImpl extends AssetVocabularyServiceBaseImpl {

	@Override
	public AssetVocabulary addVocabulary(
			long groupId, String title, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String settings,
			ServiceContext serviceContext)
		throws PortalException {

		AssetCategoriesPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_VOCABULARY);

		return assetVocabularyLocalService.addVocabulary(
			getUserId(), groupId, title, titleMap, descriptionMap, settings,
			serviceContext);
	}

	@Override
	public AssetVocabulary addVocabulary(
			long groupId, String title, ServiceContext serviceContext)
		throws PortalException {

		AssetCategoriesPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_VOCABULARY);

		return assetVocabularyLocalService.addVocabulary(
			getUserId(), groupId, title, serviceContext);
	}

	@Override
	public List<AssetVocabulary> deleteVocabularies(
			long[] vocabularyIds, ServiceContext serviceContext)
		throws PortalException {

		List<AssetVocabulary> failedVocabularies = new ArrayList<>();

		for (long vocabularyId : vocabularyIds) {
			try {
				AssetVocabularyPermission.check(
					getPermissionChecker(), vocabularyId, ActionKeys.DELETE);

				assetVocabularyLocalService.deleteVocabulary(vocabularyId);
			}
			catch (PortalException pe) {
				if (serviceContext == null) {
					return null;
				}

				if (serviceContext.isFailOnPortalException()) {
					throw pe;
				}

				AssetVocabulary vocabulary =
					assetVocabularyPersistence.fetchByPrimaryKey(vocabularyId);

				if (vocabulary == null) {
					vocabulary = assetVocabularyPersistence.create(
						vocabularyId);
				}

				failedVocabularies.add(vocabulary);
			}
		}

		return failedVocabularies;
	}

	@Override
	public void deleteVocabulary(long vocabularyId) throws PortalException {
		AssetVocabularyPermission.check(
			getPermissionChecker(), vocabularyId, ActionKeys.DELETE);

		assetVocabularyLocalService.deleteVocabulary(vocabularyId);
	}

	@Override
	public AssetVocabulary fetchVocabulary(long vocabularyId)
		throws PortalException {

		AssetVocabulary vocabulary =
			assetVocabularyLocalService.fetchAssetVocabulary(vocabularyId);

		if (vocabulary != null) {
			AssetVocabularyPermission.check(
				getPermissionChecker(), vocabulary, ActionKeys.VIEW);
		}

		return vocabulary;
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public List<AssetVocabulary> getCompanyVocabularies(long companyId)
		throws PortalException {

		return filterVocabularies(
			assetVocabularyLocalService.getCompanyVocabularies(companyId));
	}

	@Override
	public List<AssetVocabulary> getGroupsVocabularies(long[] groupIds) {
		return getGroupsVocabularies(groupIds, null);
	}

	@Override
	public List<AssetVocabulary> getGroupsVocabularies(
		long[] groupIds, String className) {

		return getGroupsVocabularies(
			groupIds, className, AssetCategoryConstants.ALL_CLASS_TYPE_PK);
	}

	@Override
	public List<AssetVocabulary> getGroupsVocabularies(
		long[] groupIds, String className, long classTypePK) {

		List<AssetVocabulary> vocabularies =
			assetVocabularyPersistence.filterFindByGroupId(groupIds);

		if (Validator.isNull(className)) {
			return vocabularies;
		}

		return AssetUtil.filterVocabularies(
			vocabularies, className, classTypePK);
	}

	@Override
	public List<AssetVocabulary> getGroupVocabularies(long groupId)
		throws PortalException {

		return getGroupVocabularies(groupId, true);
	}

	@Override
	public List<AssetVocabulary> getGroupVocabularies(
			long groupId, boolean createDefaultVocabulary)
		throws PortalException {

		return getGroupVocabularies(
			groupId, createDefaultVocabulary, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	@Override
	public List<AssetVocabulary> getGroupVocabularies(
			long groupId, boolean createDefaultVocabulary, int start, int end,
			OrderByComparator<AssetVocabulary> obc)
		throws PortalException {

		List<AssetVocabulary> vocabularies = getGroupVocabularies(
			groupId, start, end, obc);

		if (!vocabularies.isEmpty() || !createDefaultVocabulary) {
			return vocabularies;
		}

		int count = assetVocabularyLocalService.getGroupVocabulariesCount(
			new long[] {groupId});

		if (count > 0) {
			return vocabularies;
		}

		vocabularies = new ArrayList<>();

		AssetVocabulary vocabulary =
			assetVocabularyLocalService.addDefaultVocabulary(groupId);

		vocabularies.add(vocabulary);

		return vocabularies;
	}

	@Override
	public List<AssetVocabulary> getGroupVocabularies(
		long groupId, int start, int end,
		OrderByComparator<AssetVocabulary> obc) {

		return assetVocabularyPersistence.filterFindByGroupId(
			groupId, start, end, obc);
	}

	@Override
	public List<AssetVocabulary> getGroupVocabularies(
		long groupId, String name, int start, int end,
		OrderByComparator<AssetVocabulary> obc) {

		return assetVocabularyPersistence.filterFindByG_LikeN(
			groupId, name, start, end, obc);
	}

	@Override
	public List<AssetVocabulary> getGroupVocabularies(long[] groupIds) {
		return assetVocabularyPersistence.filterFindByGroupId(groupIds);
	}

	@Override
	public int getGroupVocabulariesCount(long groupId) {
		return assetVocabularyPersistence.filterCountByGroupId(groupId);
	}

	@Override
	public int getGroupVocabulariesCount(long groupId, String name) {
		return assetVocabularyPersistence.filterCountByG_LikeN(groupId, name);
	}

	@Override
	public int getGroupVocabulariesCount(long[] groupIds) {
		return assetVocabularyPersistence.filterCountByGroupId(groupIds);
	}

	@Override
	public AssetVocabularyDisplay getGroupVocabulariesDisplay(
			long groupId, String name, int start, int end,
			boolean addDefaultVocabulary,
			OrderByComparator<AssetVocabulary> obc)
		throws PortalException {

		List<AssetVocabulary> vocabularies;
		int total = 0;

		if (Validator.isNotNull(name)) {
			name = CustomSQLUtil.keywords(name)[0];

			vocabularies = getGroupVocabularies(groupId, name, start, end, obc);
			total = getGroupVocabulariesCount(groupId, name);
		}
		else {
			vocabularies = getGroupVocabularies(groupId, start, end, obc);
			total = getGroupVocabulariesCount(groupId);
		}

		if (addDefaultVocabulary && (total == 0) &&
			(assetVocabularyPersistence.countByGroupId(groupId) == 0)) {

			vocabularies = new ArrayList<>();

			vocabularies.add(
				assetVocabularyLocalService.addDefaultVocabulary(groupId));

			total = 1;
		}

		return new AssetVocabularyDisplay(vocabularies, total, start, end);
	}

	@Override
	public AssetVocabularyDisplay getGroupVocabulariesDisplay(
			long groupId, String name, int start, int end,
			OrderByComparator<AssetVocabulary> obc)
		throws PortalException {

		return getGroupVocabulariesDisplay(
			groupId, name, start, end, false, obc);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             AssetUtil#filterVocabularyIds(PermissionChecker, long[])}
	 */
	@Deprecated
	@Override
	public List<AssetVocabulary> getVocabularies(long[] vocabularyIds)
		throws PortalException {

		return filterVocabularies(
			assetVocabularyLocalService.getVocabularies(vocabularyIds));
	}

	@Override
	public AssetVocabulary getVocabulary(long vocabularyId)
		throws PortalException {

		AssetVocabularyPermission.check(
			getPermissionChecker(), vocabularyId, ActionKeys.VIEW);

		return assetVocabularyLocalService.getVocabulary(vocabularyId);
	}

	@Override
	public AssetVocabularyDisplay searchVocabulariesDisplay(
			long groupId, String title, boolean addDefaultVocabulary, int start,
			int end)
		throws PortalException {

		return searchVocabulariesDisplay(
			groupId, title, addDefaultVocabulary, start, end, null);
	}

	@Override
	public AssetVocabularyDisplay searchVocabulariesDisplay(
			long groupId, String title, boolean addDefaultVocabulary, int start,
			int end, Sort sort)
		throws PortalException {

		User user = getUser();

		BaseModelSearchResult<AssetVocabulary> baseModelSearchResult =
			assetVocabularyLocalService.searchVocabularies(
				user.getCompanyId(), groupId, title, start, end, sort);

		List<AssetVocabulary> vocabularies =
			baseModelSearchResult.getBaseModels();
		int total = baseModelSearchResult.getLength();

		if (addDefaultVocabulary && (total == 0)) {
			total = assetVocabularyPersistence.countByGroupId(groupId);

			if (total == 0) {
				vocabularies = new ArrayList<>(1);

				AssetVocabulary defaultVocabulary =
					assetVocabularyLocalService.addDefaultVocabulary(groupId);

				vocabularies.add(defaultVocabulary);

				total = 1;
			}
		}

		return new AssetVocabularyDisplay(vocabularies, total, start, end);
	}

	@Override
	public AssetVocabulary updateVocabulary(
			long vocabularyId, String title, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String settings,
			ServiceContext serviceContext)
		throws PortalException {

		AssetVocabularyPermission.check(
			getPermissionChecker(), vocabularyId, ActionKeys.UPDATE);

		return assetVocabularyLocalService.updateVocabulary(
			vocabularyId, title, titleMap, descriptionMap, settings,
			serviceContext);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
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
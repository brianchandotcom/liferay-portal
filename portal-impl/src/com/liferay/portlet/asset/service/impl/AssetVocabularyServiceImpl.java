/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.model.AssetVocabularyDisplay;
import com.liferay.portlet.asset.service.base.AssetVocabularyServiceBaseImpl;
import com.liferay.portlet.asset.service.permission.AssetPermission;
import com.liferay.portlet.asset.service.permission.AssetVocabularyPermission;
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

	/**
	 * @deprecated As of 6.1.0 {@link #addVocabulary(String, Map, Map, String,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public AssetVocabulary addVocabulary(
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String settings, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addVocabulary(
			StringPool.BLANK, titleMap, descriptionMap, settings,
			serviceContext);
	}

	@Override
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

	@Override
	public AssetVocabulary addVocabulary(
			String title, ServiceContext serviceContext)
		throws PortalException, SystemException {

		AssetPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_VOCABULARY);

		return assetVocabularyLocalService.addVocabulary(
			getUserId(), title, serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, Replaced by {@link #deleteVocabularies(long[],
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public void deleteVocabularies(long[] vocabularyIds)
		throws PortalException, SystemException {

		deleteVocabularies(vocabularyIds, null);
	}

	@Override
	public List<AssetVocabulary> deleteVocabularies(
			long[] vocabularyIds, ServiceContext serviceContext)
		throws PortalException, SystemException {

		List<AssetVocabulary> failedVocabularies =
			new ArrayList<AssetVocabulary>();

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
	public void deleteVocabulary(long vocabularyId)
		throws PortalException, SystemException {

		AssetVocabularyPermission.check(
			getPermissionChecker(), vocabularyId, ActionKeys.DELETE);

		assetVocabularyLocalService.deleteVocabulary(vocabularyId);
	}

	@Override
	public List<AssetVocabulary> getCompanyVocabularies(long companyId)
		throws PortalException, SystemException {

		return filterVocabularies(
			assetVocabularyLocalService.getCompanyVocabularies(companyId));
	}

	@Override
	public List<AssetVocabulary> getGroupsVocabularies(long[] groupIds)
		throws PortalException, SystemException {

		return getGroupsVocabularies(groupIds, null);
	}

	@Override
	public List<AssetVocabulary> getGroupsVocabularies(
			long[] groupIds, String className)
		throws PortalException, SystemException {

		return filterVocabularies(
			assetVocabularyLocalService.getGroupsVocabularies(
				groupIds, className));
	}

	@Override
	public List<AssetVocabulary> getGroupVocabularies(long groupId)
		throws PortalException, SystemException {

		return filterVocabularies(
			assetVocabularyLocalService.getGroupVocabularies(groupId));
	}

	@Override
	public List<AssetVocabulary> getGroupVocabularies(
			long groupId, boolean createDefaultVocabulary)
		throws PortalException, SystemException {

		return filterVocabularies(
			assetVocabularyLocalService.getGroupVocabularies(
				groupId, createDefaultVocabulary));
	}

	@Override
	public List<AssetVocabulary> getGroupVocabularies(
			long groupId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return assetVocabularyPersistence.filterFindByGroupId(
			groupId, start, end, obc);
	}

	@Override
	public List<AssetVocabulary> getGroupVocabularies(
			long groupId, String name, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return assetVocabularyPersistence.filterFindByG_LikeN(
			groupId, name, start, end, obc);
	}

	@Override
	public int getGroupVocabulariesCount(long groupId) throws SystemException {
		return assetVocabularyPersistence.filterCountByGroupId(groupId);
	}

	@Override
	public int getGroupVocabulariesCount(long groupId, String name)
		throws SystemException {

		return assetVocabularyPersistence.filterCountByG_LikeN(groupId, name);
	}

	@Override
	public AssetVocabularyDisplay getGroupVocabulariesDisplay(
			long groupId, String name, int start, int end,
			boolean addDefaultVocabulary, OrderByComparator obc)
		throws PortalException, SystemException {

		List<AssetVocabulary> vocabularies;
		int total = 0;

		if (Validator.isNotNull(name)) {
			name = (CustomSQLUtil.keywords(name))[0];

			vocabularies = getGroupVocabularies(groupId, name, start, end, obc);
			total = getGroupVocabulariesCount(groupId, name);
		}
		else {
			vocabularies = getGroupVocabularies(groupId, start, end, obc);
			total = getGroupVocabulariesCount(groupId);
		}

		if (addDefaultVocabulary && (total == 0) &&
			(assetVocabularyPersistence.countByGroupId(groupId) == 0)) {

			vocabularies = new ArrayList<AssetVocabulary>();

			vocabularies.add(
				assetVocabularyLocalService.addDefaultVocabulary(groupId));

			total = 1;
		}

		return new AssetVocabularyDisplay(vocabularies, total, start, end);
	}

	@Override
	public AssetVocabularyDisplay getGroupVocabulariesDisplay(
			long groupId, String name, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		return getGroupVocabulariesDisplay(
			groupId, name, start, end, false, obc);
	}

	/**
	 * @deprecated As of 6.2.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public JSONObject getJSONGroupVocabularies(
			long groupId, String name, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		int page = end / (end - start);

		jsonObject.put("page", page);

		List<AssetVocabulary> vocabularies;
		int total = 0;

		if (Validator.isNotNull(name)) {
			name = (CustomSQLUtil.keywords(name))[0];

			vocabularies = getGroupVocabularies(groupId, name, start, end, obc);
			total = getGroupVocabulariesCount(groupId, name);
		}
		else {
			vocabularies = getGroupVocabularies(groupId, start, end, obc);
			total = getGroupVocabulariesCount(groupId);
		}

		String vocabulariesJSON = JSONFactoryUtil.looseSerialize(vocabularies);

		JSONArray vocabulariesJSONArray = JSONFactoryUtil.createJSONArray(
			vocabulariesJSON);

		jsonObject.put("vocabularies", vocabulariesJSONArray);

		jsonObject.put("total", total);

		return jsonObject;
	}

	@Override
	public List<AssetVocabulary> getVocabularies(long[] vocabularyIds)
		throws PortalException, SystemException {

		return filterVocabularies(
			assetVocabularyLocalService.getVocabularies(vocabularyIds));
	}

	@Override
	public AssetVocabulary getVocabulary(long vocabularyId)
		throws PortalException, SystemException {

		AssetVocabularyPermission.check(
			getPermissionChecker(), vocabularyId, ActionKeys.VIEW);

		return assetVocabularyLocalService.getVocabulary(vocabularyId);
	}

	/**
	 * @deprecated As of 6.1.0, {@link #updateVocabulary(long, String, Map, Map,
	 *             String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public AssetVocabulary updateVocabulary(
			long vocabularyId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String settings,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return updateVocabulary(
			vocabularyId, StringPool.BLANK, titleMap, descriptionMap, settings,
			serviceContext);
	}

	@Override
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
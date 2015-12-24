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

package com.liferay.portlet.asset.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the remote service utility for AssetVocabulary. This utility wraps
 * {@link com.liferay.portlet.asset.service.impl.AssetVocabularyServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyService
 * @see com.liferay.portlet.asset.service.base.AssetVocabularyServiceBaseImpl
 * @see com.liferay.portlet.asset.service.impl.AssetVocabularyServiceImpl
 * @generated
 */
@ProviderType
public class AssetVocabularyServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.asset.service.impl.AssetVocabularyServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portlet.asset.model.AssetVocabulary addVocabulary(
		long groupId, java.lang.String title,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addVocabulary(groupId, title, serviceContext);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary addVocabulary(
		long groupId, java.lang.String title,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String settings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addVocabulary(groupId, title, titleMap, descriptionMap,
			settings, serviceContext);
	}

	/**
	* @deprecated As of 6.2.0, Replaced by {@link #deleteVocabularies(long[],
	ServiceContext)}
	*/
	@Deprecated
	public static void deleteVocabularies(long[] vocabularyIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteVocabularies(vocabularyIds);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> deleteVocabularies(
		long[] vocabularyIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteVocabularies(vocabularyIds, serviceContext);
	}

	public static void deleteVocabulary(long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteVocabulary(vocabularyId);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary fetchVocabulary(
		long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchVocabulary(vocabularyId);
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getCompanyVocabularies(
		long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCompanyVocabularies(companyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupVocabularies(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getGroupVocabularies(groupId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupVocabularies(
		long groupId, boolean createDefaultVocabulary)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getGroupVocabularies(groupId, createDefaultVocabulary);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupVocabularies(
		long groupId, boolean createDefaultVocabulary, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetVocabulary> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getGroupVocabularies(groupId, createDefaultVocabulary,
			start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupVocabularies(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetVocabulary> obc) {
		return getService().getGroupVocabularies(groupId, name, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupVocabularies(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetVocabulary> obc) {
		return getService().getGroupVocabularies(groupId, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupVocabularies(
		long[] groupIds) {
		return getService().getGroupVocabularies(groupIds);
	}

	public static int getGroupVocabulariesCount(long groupId) {
		return getService().getGroupVocabulariesCount(groupId);
	}

	public static int getGroupVocabulariesCount(long groupId,
		java.lang.String name) {
		return getService().getGroupVocabulariesCount(groupId, name);
	}

	public static int getGroupVocabulariesCount(long[] groupIds) {
		return getService().getGroupVocabulariesCount(groupIds);
	}

	public static com.liferay.portlet.asset.model.AssetVocabularyDisplay getGroupVocabulariesDisplay(
		long groupId, java.lang.String name, int start, int end,
		boolean addDefaultVocabulary,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetVocabulary> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getGroupVocabulariesDisplay(groupId, name, start, end,
			addDefaultVocabulary, obc);
	}

	public static com.liferay.portlet.asset.model.AssetVocabularyDisplay getGroupVocabulariesDisplay(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetVocabulary> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getGroupVocabulariesDisplay(groupId, name, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupsVocabularies(
		long[] groupIds) {
		return getService().getGroupsVocabularies(groupIds);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupsVocabularies(
		long[] groupIds, java.lang.String className) {
		return getService().getGroupsVocabularies(groupIds, className);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupsVocabularies(
		long[] groupIds, java.lang.String className, long classTypePK) {
		return getService()
				   .getGroupsVocabularies(groupIds, className, classTypePK);
	}

	/**
	* @deprecated As of 6.2.0, with no direct replacement
	*/
	@Deprecated
	public static com.liferay.portal.kernel.json.JSONObject getJSONGroupVocabularies(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetVocabulary> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getJSONGroupVocabularies(groupId, name, start, end, obc);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	AssetUtil#filterVocabularyIds(PermissionChecker, long[])}
	*/
	@Deprecated
	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getVocabularies(
		long[] vocabularyIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getVocabularies(vocabularyIds);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary getVocabulary(
		long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getVocabulary(vocabularyId);
	}

	public static com.liferay.portlet.asset.model.AssetVocabularyDisplay searchVocabulariesDisplay(
		long groupId, java.lang.String title, boolean addDefaultVocabulary,
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchVocabulariesDisplay(groupId, title,
			addDefaultVocabulary, start, end);
	}

	public static com.liferay.portlet.asset.model.AssetVocabularyDisplay searchVocabulariesDisplay(
		long groupId, java.lang.String title, boolean addDefaultVocabulary,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchVocabulariesDisplay(groupId, title,
			addDefaultVocabulary, start, end, sort);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary updateVocabulary(
		long vocabularyId, java.lang.String title,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String settings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateVocabulary(vocabularyId, title, titleMap,
			descriptionMap, settings, serviceContext);
	}

	public static AssetVocabularyService getService() {
		if (_service == null) {
			_service = (AssetVocabularyService)PortalBeanLocatorUtil.locate(AssetVocabularyService.class.getName());

			ReferenceRegistry.registerReference(AssetVocabularyServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static AssetVocabularyService _service;
}
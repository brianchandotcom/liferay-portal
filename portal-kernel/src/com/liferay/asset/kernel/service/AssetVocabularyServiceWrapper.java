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

package com.liferay.asset.kernel.service;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetVocabularyService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyService
 * @generated
 */
public class AssetVocabularyServiceWrapper
	implements AssetVocabularyService, ServiceWrapper<AssetVocabularyService> {

	public AssetVocabularyServiceWrapper(
		AssetVocabularyService assetVocabularyService) {

		_assetVocabularyService = assetVocabularyService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetVocabularyServiceUtil} to access the asset vocabulary remote service. Add custom service methods to <code>com.liferay.portlet.asset.service.impl.AssetVocabularyServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public AssetVocabulary addVocabulary(
			long groupId, String title,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String settings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetVocabularyService.addVocabulary(
			groupId, title, titleMap, descriptionMap, settings, serviceContext);
	}

	@Override
	public AssetVocabulary addVocabulary(
			long groupId, String title,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetVocabularyService.addVocabulary(
			groupId, title, serviceContext);
	}

	@Override
	public java.util.List<AssetVocabulary> deleteVocabularies(
			long[] vocabularyIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetVocabularyService.deleteVocabularies(
			vocabularyIds, serviceContext);
	}

	@Override
	public void deleteVocabulary(long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetVocabularyService.deleteVocabulary(vocabularyId);
	}

	@Override
	public AssetVocabulary fetchVocabulary(long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetVocabularyService.fetchVocabulary(vocabularyId);
	}

	@Override
	public java.util.List<AssetVocabulary> getGroupsVocabularies(
		long[] groupIds) {

		return _assetVocabularyService.getGroupsVocabularies(groupIds);
	}

	@Override
	public java.util.List<AssetVocabulary> getGroupsVocabularies(
		long[] groupIds, String className) {

		return _assetVocabularyService.getGroupsVocabularies(
			groupIds, className);
	}

	@Override
	public java.util.List<AssetVocabulary> getGroupsVocabularies(
		long[] groupIds, String className, long classTypePK) {

		return _assetVocabularyService.getGroupsVocabularies(
			groupIds, className, classTypePK);
	}

	@Override
	public java.util.List<AssetVocabulary> getGroupVocabularies(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetVocabularyService.getGroupVocabularies(groupId);
	}

	@Override
	public java.util.List<AssetVocabulary> getGroupVocabularies(
			long groupId, boolean createDefaultVocabulary)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetVocabularyService.getGroupVocabularies(
			groupId, createDefaultVocabulary);
	}

	@Override
	public java.util.List<AssetVocabulary> getGroupVocabularies(
			long groupId, boolean createDefaultVocabulary, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary>
				obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetVocabularyService.getGroupVocabularies(
			groupId, createDefaultVocabulary, start, end, obc);
	}

	@Override
	public java.util.List<AssetVocabulary> getGroupVocabularies(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary> obc) {

		return _assetVocabularyService.getGroupVocabularies(
			groupId, start, end, obc);
	}

	@Override
	public java.util.List<AssetVocabulary> getGroupVocabularies(
		long groupId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetVocabulary> obc) {

		return _assetVocabularyService.getGroupVocabularies(
			groupId, name, start, end, obc);
	}

	@Override
	public java.util.List<AssetVocabulary> getGroupVocabularies(
		long[] groupIds) {

		return _assetVocabularyService.getGroupVocabularies(groupIds);
	}

	@Override
	public int getGroupVocabulariesCount(long groupId) {
		return _assetVocabularyService.getGroupVocabulariesCount(groupId);
	}

	@Override
	public int getGroupVocabulariesCount(long groupId, String name) {
		return _assetVocabularyService.getGroupVocabulariesCount(groupId, name);
	}

	@Override
	public int getGroupVocabulariesCount(long[] groupIds) {
		return _assetVocabularyService.getGroupVocabulariesCount(groupIds);
	}

	@Override
	public com.liferay.asset.kernel.model.AssetVocabularyDisplay
			getGroupVocabulariesDisplay(
				long groupId, String name, int start, int end,
				boolean addDefaultVocabulary,
				com.liferay.portal.kernel.util.OrderByComparator
					<AssetVocabulary> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetVocabularyService.getGroupVocabulariesDisplay(
			groupId, name, start, end, addDefaultVocabulary, obc);
	}

	@Override
	public com.liferay.asset.kernel.model.AssetVocabularyDisplay
			getGroupVocabulariesDisplay(
				long groupId, String name, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<AssetVocabulary> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetVocabularyService.getGroupVocabulariesDisplay(
			groupId, name, start, end, obc);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _assetVocabularyService.getOSGiServiceIdentifier();
	}

	@Override
	public AssetVocabulary getVocabulary(long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetVocabularyService.getVocabulary(vocabularyId);
	}

	@Override
	public com.liferay.asset.kernel.model.AssetVocabularyDisplay
			searchVocabulariesDisplay(
				long groupId, String title, boolean addDefaultVocabulary,
				int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetVocabularyService.searchVocabulariesDisplay(
			groupId, title, addDefaultVocabulary, start, end);
	}

	@Override
	public com.liferay.asset.kernel.model.AssetVocabularyDisplay
			searchVocabulariesDisplay(
				long groupId, String title, boolean addDefaultVocabulary,
				int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetVocabularyService.searchVocabulariesDisplay(
			groupId, title, addDefaultVocabulary, start, end, sort);
	}

	@Override
	public AssetVocabulary updateVocabulary(
			long vocabularyId, String title,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String settings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetVocabularyService.updateVocabulary(
			vocabularyId, title, titleMap, descriptionMap, settings,
			serviceContext);
	}

	@Override
	public AssetVocabularyService getWrappedService() {
		return _assetVocabularyService;
	}

	@Override
	public void setWrappedService(
		AssetVocabularyService assetVocabularyService) {

		_assetVocabularyService = assetVocabularyService;
	}

	private AssetVocabularyService _assetVocabularyService;

}
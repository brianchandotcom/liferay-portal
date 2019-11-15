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

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetTagService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagService
 * @generated
 */
public class AssetTagServiceWrapper
	implements AssetTagService, ServiceWrapper<AssetTagService> {

	public AssetTagServiceWrapper(AssetTagService assetTagService) {
		_assetTagService = assetTagService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetTagServiceUtil} to access the asset tag remote service. Add custom service methods to <code>com.liferay.portlet.asset.service.impl.AssetTagServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public AssetTag addTag(
			long groupId, String name,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetTagService.addTag(groupId, name, serviceContext);
	}

	@Override
	public void deleteTag(long tagId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetTagService.deleteTag(tagId);
	}

	@Override
	public void deleteTags(long[] tagIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetTagService.deleteTags(tagIds);
	}

	@Override
	public java.util.List<AssetTag> getGroupsTags(long[] groupIds) {
		return _assetTagService.getGroupsTags(groupIds);
	}

	@Override
	public java.util.List<AssetTag> getGroupTags(long groupId) {
		return _assetTagService.getGroupTags(groupId);
	}

	@Override
	public java.util.List<AssetTag> getGroupTags(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTag> obc) {

		return _assetTagService.getGroupTags(groupId, start, end, obc);
	}

	@Override
	public int getGroupTagsCount(long groupId) {
		return _assetTagService.getGroupTagsCount(groupId);
	}

	@Override
	public com.liferay.asset.kernel.model.AssetTagDisplay getGroupTagsDisplay(
		long groupId, String name, int start, int end) {

		return _assetTagService.getGroupTagsDisplay(groupId, name, start, end);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _assetTagService.getOSGiServiceIdentifier();
	}

	@Override
	public AssetTag getTag(long tagId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetTagService.getTag(tagId);
	}

	@Override
	public java.util.List<AssetTag> getTags(
		long groupId, long classNameId, String name) {

		return _assetTagService.getTags(groupId, classNameId, name);
	}

	@Override
	public java.util.List<AssetTag> getTags(
		long groupId, long classNameId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTag> obc) {

		return _assetTagService.getTags(
			groupId, classNameId, name, start, end, obc);
	}

	@Override
	public java.util.List<AssetTag> getTags(
		long groupId, String name, int start, int end) {

		return _assetTagService.getTags(groupId, name, start, end);
	}

	@Override
	public java.util.List<AssetTag> getTags(
		long groupId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTag> obc) {

		return _assetTagService.getTags(groupId, name, start, end, obc);
	}

	@Override
	public java.util.List<AssetTag> getTags(
		long[] groupIds, String name, int start, int end) {

		return _assetTagService.getTags(groupIds, name, start, end);
	}

	@Override
	public java.util.List<AssetTag> getTags(
		long[] groupIds, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTag> obc) {

		return _assetTagService.getTags(groupIds, name, start, end, obc);
	}

	@Override
	public java.util.List<AssetTag> getTags(String className, long classPK) {
		return _assetTagService.getTags(className, classPK);
	}

	@Override
	public int getTagsCount(long groupId, String name) {
		return _assetTagService.getTagsCount(groupId, name);
	}

	@Override
	public int getVisibleAssetsTagsCount(
		long groupId, long classNameId, String name) {

		return _assetTagService.getVisibleAssetsTagsCount(
			groupId, classNameId, name);
	}

	@Override
	public int getVisibleAssetsTagsCount(long groupId, String name) {
		return _assetTagService.getVisibleAssetsTagsCount(groupId, name);
	}

	@Override
	public void mergeTags(long fromTagId, long toTagId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetTagService.mergeTags(fromTagId, toTagId);
	}

	@Override
	public void mergeTags(long[] fromTagIds, long toTagId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetTagService.mergeTags(fromTagIds, toTagId);
	}

	@Override
	public com.liferay.portal.kernel.json.JSONArray search(
		long groupId, String name, int start, int end) {

		return _assetTagService.search(groupId, name, start, end);
	}

	@Override
	public com.liferay.portal.kernel.json.JSONArray search(
		long[] groupIds, String name, int start, int end) {

		return _assetTagService.search(groupIds, name, start, end);
	}

	@Override
	public AssetTag updateTag(
			long tagId, String name,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetTagService.updateTag(tagId, name, serviceContext);
	}

	@Override
	public AssetTagService getWrappedService() {
		return _assetTagService;
	}

	@Override
	public void setWrappedService(AssetTagService assetTagService) {
		_assetTagService = assetTagService;
	}

	private AssetTagService _assetTagService;

}
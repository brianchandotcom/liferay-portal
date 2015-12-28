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
 * Provides the remote service utility for AssetTag. This utility wraps
 * {@link com.liferay.portlet.asset.service.impl.AssetTagServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagService
 * @see com.liferay.portlet.asset.service.base.AssetTagServiceBaseImpl
 * @see com.liferay.portlet.asset.service.impl.AssetTagServiceImpl
 * @generated
 */
@ProviderType
public class AssetTagServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.asset.service.impl.AssetTagServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portlet.asset.model.AssetTag addTag(
		long groupId, java.lang.String name,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addTag(groupId, name, serviceContext);
	}

	public static void deleteTag(long tagId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteTag(tagId);
	}

	public static void deleteTags(long[] tagIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteTags(tagIds);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getGroupTags(
		long groupId) {
		return getService().getGroupTags(groupId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getGroupTags(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> obc) {
		return getService().getGroupTags(groupId, start, end, obc);
	}

	public static int getGroupTagsCount(long groupId) {
		return getService().getGroupTagsCount(groupId);
	}

	public static com.liferay.portlet.asset.model.AssetTagDisplay getGroupTagsDisplay(
		long groupId, java.lang.String name, int start, int end) {
		return getService().getGroupTagsDisplay(groupId, name, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getGroupsTags(
		long[] groupIds) {
		return getService().getGroupsTags(groupIds);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupTagsDisplay(long,
	String, int, int)}
	*/
	@Deprecated
	public static com.liferay.portal.kernel.json.JSONObject getJSONGroupTags(
		long groupId, java.lang.String name, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getJSONGroupTags(groupId, name, start, end);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portlet.asset.model.AssetTag getTag(long tagId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getTag(tagId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		java.lang.String className, long classPK) {
		return getService().getTags(className, classPK);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long groupId, long classNameId, java.lang.String name) {
		return getService().getTags(groupId, classNameId, name);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long groupId, long classNameId, java.lang.String name, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> obc) {
		return getService().getTags(groupId, classNameId, name, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long groupId, java.lang.String name, int start, int end) {
		return getService().getTags(groupId, name, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> obc) {
		return getService().getTags(groupId, name, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long[] groupIds, java.lang.String name, int start, int end) {
		return getService().getTags(groupIds, name, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long[] groupIds, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> obc) {
		return getService().getTags(groupIds, name, start, end, obc);
	}

	public static int getTagsCount(long groupId, java.lang.String name) {
		return getService().getTagsCount(groupId, name);
	}

	public static int getVisibleAssetsTagsCount(long groupId, long classNameId,
		java.lang.String name) {
		return getService().getVisibleAssetsTagsCount(groupId, classNameId, name);
	}

	public static int getVisibleAssetsTagsCount(long groupId,
		java.lang.String name) {
		return getService().getVisibleAssetsTagsCount(groupId, name);
	}

	public static void mergeTags(long fromTagId, long toTagId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().mergeTags(fromTagId, toTagId);
	}

	public static void mergeTags(long[] fromTagIds, long toTagId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().mergeTags(fromTagIds, toTagId);
	}

	public static com.liferay.portal.kernel.json.JSONArray search(
		long groupId, java.lang.String name, int start, int end) {
		return getService().search(groupId, name, start, end);
	}

	public static com.liferay.portal.kernel.json.JSONArray search(
		long[] groupIds, java.lang.String name, int start, int end) {
		return getService().search(groupIds, name, start, end);
	}

	public static com.liferay.portlet.asset.model.AssetTag updateTag(
		long tagId, java.lang.String name,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateTag(tagId, name, serviceContext);
	}

	public static AssetTagService getService() {
		if (_service == null) {
			_service = (AssetTagService)PortalBeanLocatorUtil.locate(AssetTagService.class.getName());

			ReferenceRegistry.registerReference(AssetTagServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static AssetTagService _service;
}
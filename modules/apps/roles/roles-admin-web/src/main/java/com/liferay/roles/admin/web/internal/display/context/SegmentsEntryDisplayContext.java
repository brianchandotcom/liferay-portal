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

package com.liferay.roles.admin.web.internal.display.context;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.segments.configuration.provider.SegmentsConfigurationProvider;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.provider.SegmentsEntryProviderRegistry;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = {})
public class SegmentsEntryDisplayContext {

	public static String getGroupDescriptiveName(
			SegmentsEntry segmentsEntry, Locale locale)
		throws Exception {

		Group group = _groupLocalService.fetchGroup(segmentsEntry.getGroupId());

		return group.getDescriptiveName(locale);
	}

	public static String getSegmentsCompanyConfigurationURL(
		HttpServletRequest httpServletRequest) {

		try {
			return _segmentsConfigurationProvider.getCompanyConfigurationURL(
				httpServletRequest);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return StringPool.BLANK;
	}

	public static List<User> getSegmentsEntryUsers(
			long segmentsEntryId, int start, int end)
		throws Exception {

		return TransformUtil.transformToList(
			ArrayUtil.toLongArray(
				_segmentsEntryProviderRegistry.getSegmentsEntryClassPKs(
					segmentsEntryId, start, end)),
			_userLocalService::fetchUser);
	}

	public static int getSegmentsEntryUsersCount(long segmentsEntryId)
		throws Exception {

		return _segmentsEntryProviderRegistry.getSegmentsEntryClassPKsCount(
			segmentsEntryId);
	}

	public static boolean isRoleSegmentationEnabled(long companyId) {
		try {
			return _segmentsConfigurationProvider.isRoleSegmentationEnabled(
				companyId);
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setSegmentsConfigurationProvider(
		SegmentsConfigurationProvider segmentsConfigurationProvider) {

		_segmentsConfigurationProvider = segmentsConfigurationProvider;
	}

	@Reference(unbind = "-")
	protected void setSegmentsEntryProviderRegistry(
		SegmentsEntryProviderRegistry segmentsEntryProviderRegistry) {

		_segmentsEntryProviderRegistry = segmentsEntryProviderRegistry;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsEntryDisplayContext.class);

	private static GroupLocalService _groupLocalService;
	private static SegmentsConfigurationProvider _segmentsConfigurationProvider;
	private static SegmentsEntryProviderRegistry _segmentsEntryProviderRegistry;
	private static UserLocalService _userLocalService;

}
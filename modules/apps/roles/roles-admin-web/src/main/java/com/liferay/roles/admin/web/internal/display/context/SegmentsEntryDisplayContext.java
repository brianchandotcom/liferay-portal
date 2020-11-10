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

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.segments.configuration.SegmentsConfiguration;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.provider.SegmentsEntryProviderRegistry;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	configurationPid = "com.liferay.segments.configuration.SegmentsConfiguration",
	service = {}
)
public class SegmentsEntryDisplayContext {

	public static String getGroupDescriptiveName(
			SegmentsEntry segmentsEntry, Locale locale)
		throws Exception {

		Group group = _groupLocalService.fetchGroup(segmentsEntry.getGroupId());

		return group.getDescriptiveName(locale);
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

	public static boolean isRoleSegmentationEnabled() {
		return _roleSegmentationEnabled;
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		SegmentsConfiguration segmentsConfiguration =
			ConfigurableUtil.createConfigurable(
				SegmentsConfiguration.class, properties);

		_roleSegmentationEnabled =
			segmentsConfiguration.roleSegmentationEnabled();
	}

	@Deactivate
	protected void deactivate() {
		_roleSegmentationEnabled = false;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
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

	private static GroupLocalService _groupLocalService;
	private static boolean _roleSegmentationEnabled;
	private static SegmentsEntryProviderRegistry _segmentsEntryProviderRegistry;
	private static UserLocalService _userLocalService;

}
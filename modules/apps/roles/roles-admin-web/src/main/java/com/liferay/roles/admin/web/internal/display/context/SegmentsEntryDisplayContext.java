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

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.provider.SegmentsEntryProviderRegistry;

import java.util.Locale;

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

	public static String getMembersCount(long segmentsEntryId)
		throws Exception {

		int count =
			_segmentsEntryProviderRegistry.getSegmentsEntryClassPKsCount(
				segmentsEntryId);

		return String.valueOf(count);
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

	private static GroupLocalService _groupLocalService;
	private static SegmentsEntryProviderRegistry _segmentsEntryProviderRegistry;

}
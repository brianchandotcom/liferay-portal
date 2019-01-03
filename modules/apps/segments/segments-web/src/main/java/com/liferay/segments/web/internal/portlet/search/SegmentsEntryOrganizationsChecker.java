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

package com.liferay.segments.web.internal.portlet.search;

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryRelService;

import javax.portlet.RenderResponse;

/**
 * @author Eduardo García
 */
public class SegmentsEntryOrganizationsChecker extends EmptyOnClickRowChecker {

	public SegmentsEntryOrganizationsChecker(
		RenderResponse renderResponse,
		SegmentsEntryRelService segmentsEntryRelService, Portal portal,
		SegmentsEntry segmentsEntry) {

		super(renderResponse);

		_segmentsEntryRelService = segmentsEntryRelService;
		_portal = portal;
		_segmentsEntry = segmentsEntry;
	}

	@Override
	public boolean isChecked(Object obj) {
		Organization organization = null;

		if (obj instanceof Organization) {
			organization = (Organization)obj;
		}
		else if (obj instanceof Object[]) {
			organization = (Organization)((Object[])obj)[0];
		}
		else {
			throw new IllegalArgumentException(obj + " is not an organization");
		}

		return _segmentsEntryRelService.hasSegmentsEntryRel(
			_segmentsEntry.getSegmentsEntryId(),
			_portal.getClassNameId(Organization.class),
			organization.getOrganizationId());
	}

	@Override
	public boolean isDisabled(Object obj) {
		Organization organization = (Organization)obj;

		return isChecked(organization);
	}

	private final Portal _portal;
	private final SegmentsEntry _segmentsEntry;
	private final SegmentsEntryRelService _segmentsEntryRelService;

}
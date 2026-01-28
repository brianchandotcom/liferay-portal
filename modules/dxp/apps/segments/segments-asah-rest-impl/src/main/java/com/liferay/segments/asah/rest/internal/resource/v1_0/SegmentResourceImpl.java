/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.rest.internal.resource.v1_0;

import com.liferay.segments.asah.connector.util.IndividualSegmentsHelper;
import com.liferay.segments.asah.rest.dto.v1_0.Membership;
import com.liferay.segments.asah.rest.dto.v1_0.Segment;
import com.liferay.segments.asah.rest.resource.v1_0.SegmentResource;
import com.liferay.segments.model.SegmentsEntry;

import java.util.Arrays;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rachael Koestartyo
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/segment.properties",
	scope = ServiceScope.PROTOTYPE, service = SegmentResource.class
)
public class SegmentResourceImpl extends BaseSegmentResourceImpl {

	@Override
	public Segment postSegment(String segmentsEntryKey, Segment segment)
		throws Exception {

		SegmentsEntry segmentsEntry =
			_individualSegmentsHelper.addSegmentsEntry(
				contextCompany.getCompanyId(), segmentsEntryKey,
				segment.getName());

		for (Membership membership : segment.getMemberships()) {
			_individualSegmentsHelper.updateIndividualSegmentMemberships(
				membership.getIndividualPK(),
				Arrays.asList(membership.getIndividualSegmentIds()),
				membership.getRemoved(), segmentsEntry, membership.getUserId());
		}

		return segment;
	}

	@Reference
	private IndividualSegmentsHelper _individualSegmentsHelper;

}
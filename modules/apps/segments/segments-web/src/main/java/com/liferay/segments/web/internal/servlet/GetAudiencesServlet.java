/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.web.internal.servlet;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributorRegistry;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.web.internal.audiences.AudiencesJSONObjectBuilder;

import jakarta.servlet.Servlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(
	property = {
		"osgi.http.whiteboard.context.path=/audiences",
		"osgi.http.whiteboard.servlet.name=com.liferay.audience.web.internal.servlet.GetAudiencesServlet",
		"osgi.http.whiteboard.servlet.pattern=/audiences/*"
	},
	service = Servlet.class
)
public class GetAudiencesServlet extends HttpServlet {

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		long companyId = _portal.getCompanyId(httpServletRequest);

		if (!FeatureFlagManagerUtil.isEnabled(companyId, "LPD-86384")) {
			throw new UnsupportedOperationException();
		}

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);
		httpServletResponse.setStatus(HttpServletResponse.SC_OK);

		JSONArray audiencesJSONArray = _jsonFactory.createJSONArray();

		SegmentsCriteriaContributor contextContributor =
			_getContextContributor();

		if (contextContributor != null) {
			try {
				Group companyGroup = _groupLocalService.getCompanyGroup(
					companyId);

				List<SegmentsEntry> segmentsEntries =
					_segmentsEntryLocalService.getSegmentsEntries(
						companyGroup.getGroupId(),
						new String[] {SegmentsEntryConstants.SOURCE_AUDIENCE},
						QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

				for (SegmentsEntry segmentsEntry : segmentsEntries) {
					JSONObject audienceJSONObject =
						AudiencesJSONObjectBuilder.toAudienceJSONObject(
							_jsonFactory, contextContributor, segmentsEntry);

					if (audienceJSONObject != null) {
						audiencesJSONArray.put(audienceJSONObject);
					}
				}
			}
			catch (Exception exception) {
				_log.error(exception);

				httpServletResponse.setStatus(
					HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

				return;
			}
		}

		ServletResponseUtil.write(
			httpServletResponse,
			JSONUtil.put(
				"audiences", audiencesJSONArray
			).toString());
	}

	private SegmentsCriteriaContributor _getContextContributor() {
		for (SegmentsCriteriaContributor contributor :
				_segmentsCriteriaContributorRegistry.
					getSegmentsCriteriaContributors()) {

			if (Objects.equals(contributor.getKey(), "context")) {
				return contributor;
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetAudiencesServlet.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsCriteriaContributorRegistry
		_segmentsCriteriaContributorRegistry;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

}
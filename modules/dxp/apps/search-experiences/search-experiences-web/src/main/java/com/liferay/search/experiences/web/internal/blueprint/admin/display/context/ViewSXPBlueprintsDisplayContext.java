/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.web.internal.blueprint.admin.display.context;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintService;
import com.liferay.search.experiences.web.internal.security.permission.resource.SXPBlueprintEntryPermission;

import java.util.Collections;
import java.util.List;

/**
 * @author Petteri Karttunen
 */
public class ViewSXPBlueprintsDisplayContext
	extends BaseDisplayContext<SXPBlueprint> {

	public ViewSXPBlueprintsDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, Queries queries,
		Searcher searcher,
		SearchRequestBuilderFactory searchRequestBuilderFactory, Sorts sorts,
		SXPBlueprintService sxpBlueprintService) {

		super(
			liferayPortletRequest, liferayPortletResponse, queries, searcher,
			searchRequestBuilderFactory, sorts);

		_sxpBlueprintService = sxpBlueprintService;
	}

	public List<String> getAvailableActions(SXPBlueprint sxpBlueprint)
		throws PortalException {

		if (SXPBlueprintEntryPermission.contains(
				themeDisplay.getPermissionChecker(), sxpBlueprint,
				ActionKeys.DELETE)) {

			return Collections.singletonList("deleteSXPBlueprints");
		}

		return Collections.emptyList();
	}

	public SearchContainer<SXPBlueprint> getSearchContainer()
		throws PortalException {

		return getSearchContainer(
			"no-blueprints-were-found", SXPBlueprint.class);
	}

	@Override
	protected String getDisplayStylePreferenceName() {
		return "sxp-blueprints-display-style";
	}

	@Override
	protected String getMVCRenderCommandName() {
		return "/sxp_blueprint_admin/view_sxp_blueprints";
	}

	@Override
	protected void processBooleanQuery(BooleanQuery booleanQuery) {
	}

	@Override
	protected SXPBlueprint toBaseModel(long entryClassPK) {
		try {
			return _sxpBlueprintService.getSXPBlueprint(entryClassPK);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get search experiences blueprint " +
						entryClassPK);
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewSXPBlueprintsDisplayContext.class);

	private final SXPBlueprintService _sxpBlueprintService;

}
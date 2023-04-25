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

package com.liferay.osb.faro.admin.web.internal.portlet.action;

import com.liferay.osb.faro.admin.web.internal.constants.FaroAdminPortletKeys;
import com.liferay.osb.faro.admin.web.internal.constants.FaroAdminWebKeys;
import com.liferay.osb.faro.admin.web.internal.model.FaroProjectAdminDisplay;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Kong
 */
@Component(
	property = {
		"javax.portlet.name=" + FaroAdminPortletKeys.FARO_ADMIN,
		"mvc.command.name=/faro_admin/info_panel"
	},
	service = MVCResourceCommand.class
)
public class InfoPanelMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		List<FaroProjectAdminDisplay> faroProjectAdminDisplays =
			new ArrayList<>();

		long[] faroProjectIds = ParamUtil.getLongValues(
			resourceRequest, "rowIds");

		if (faroProjectIds.length == 1) {
			Indexer<FaroProject> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(FaroProject.class);

			SearchContext searchContext = new SearchContext();

			searchContext.setAttribute("faroProjectId", faroProjectIds[0]);

			Hits hits = indexer.search(searchContext);

			List<Document> documents = hits.toList();

			if (!documents.isEmpty()) {
				faroProjectAdminDisplays.add(
					new FaroProjectAdminDisplay(
						_faroProjectLocalService.getFaroProject(
							faroProjectIds[0]),
						documents.get(0)));
			}
		}

		resourceRequest.setAttribute(
			FaroAdminWebKeys.FARO_PROJECT_ENTRIES, faroProjectAdminDisplays);
		resourceRequest.setAttribute(
			FaroAdminWebKeys.FARO_PROJECT_ENTRIES_COUNT, faroProjectIds.length);

		include(resourceRequest, resourceResponse, "/info_panel.jsp");
	}

	@Reference
	private FaroProjectLocalService _faroProjectLocalService;

}
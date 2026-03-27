/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.controller.contacts;

import com.liferay.osb.faro.engine.client.model.AssetSummaryCategory;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.web.internal.controller.BaseFaroController;
import com.liferay.osb.faro.web.internal.model.display.FaroFDSResultsDisplay;
import com.liferay.osb.faro.web.internal.model.display.contacts.AssetSummaryCategoryDisplay;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.function.Function;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ivica Cardic
 */
@Component(service = AssetSummaryCategoryController.class)
@Path("/{groupId}/asset-summary-categories")
@Produces(MediaType.APPLICATION_JSON)
public class AssetSummaryCategoryController extends BaseFaroController {

	@GET
	public FaroFDSResultsDisplay getAssetSummaryCategories(
			@PathParam("groupId") long groupId,
			@QueryParam("channelId") long channelId,
			@QueryParam("rangeEnd") String rangeEnd,
			@DefaultValue("30") @QueryParam("rangeKey") int rangeKey,
			@QueryParam("rangeStart") String rangeStart,
			@QueryParam("cur") int cur,
			@DefaultValue("20") @QueryParam("delta") int delta)
		throws Exception {

		FaroProject faroProject =
			faroProjectLocalService.getFaroProjectByGroupId(groupId);

		Results<AssetSummaryCategory> results =
			contactsEngineClient.getAssetSummaryCategories(
				faroProject, channelId, rangeEnd, rangeKey, rangeStart, cur,
				delta);

		Function<AssetSummaryCategory, AssetSummaryCategoryDisplay> function =
			AssetSummaryCategoryDisplay::new;

		return new FaroFDSResultsDisplay(results, function, cur, delta);
	}

}
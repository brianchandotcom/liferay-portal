/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.controller.contacts;

import com.liferay.osb.faro.engine.client.model.AssetSummaryType;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.web.internal.controller.BaseFaroController;
import com.liferay.osb.faro.web.internal.model.display.FaroFDSResultsDisplay;
import com.liferay.osb.faro.web.internal.model.display.contacts.AssetSummaryTypeDisplay;

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
@Component(service = AssetSummaryTypeController.class)
@Path("/{groupId}/asset-summary-types")
@Produces(MediaType.APPLICATION_JSON)
public class AssetSummaryTypeController extends BaseFaroController {

	@GET
	public FaroFDSResultsDisplay getAssetSummaryTypes(
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

		Results<AssetSummaryType> results =
			contactsEngineClient.getAssetSummaryTypes(
				faroProject, channelId, rangeEnd, rangeKey, rangeStart, cur,
				delta);

		Function<AssetSummaryType, AssetSummaryTypeDisplay> function =
			AssetSummaryTypeDisplay::new;

		return new FaroFDSResultsDisplay(results, function, cur, delta);
	}

}
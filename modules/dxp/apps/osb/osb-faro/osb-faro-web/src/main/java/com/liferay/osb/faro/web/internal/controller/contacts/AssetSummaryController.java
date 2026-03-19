/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.controller.contacts;

import com.liferay.osb.faro.engine.client.model.AssetSummary;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.engine.client.util.OrderByField;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.web.internal.controller.BaseFaroController;
import com.liferay.osb.faro.web.internal.controller.FaroController;
import com.liferay.osb.faro.web.internal.model.display.FaroFDSResultsDisplay;
import com.liferay.osb.faro.web.internal.model.display.contacts.AssetSummaryDisplay;
import com.liferay.osb.faro.web.internal.param.FaroParam;
import com.liferay.petra.string.StringPool;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.function.Function;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcos Martins
 */
@Component(service = {AssetSummaryController.class, FaroController.class})
@Path("/{groupId}/asset-summary")
@Produces(MediaType.APPLICATION_JSON)
public class AssetSummaryController extends BaseFaroController {

	@GET
	public FaroFDSResultsDisplay getAssetSummary(
			@PathParam("groupId") long groupId,
			@QueryParam("channelId") long channelId,
			@QueryParam("keywords") String keywords,
			@QueryParam("rangeKey") int rangeKey, @QueryParam("cur") int cur,
			@DefaultValue("20") @QueryParam("delta") int delta,
			@DefaultValue(StringPool.BLANK) @QueryParam("orderByFields")
				FaroParam<List<OrderByField>> orderByFieldsFaroParam)
		throws Exception {

		FaroProject faroProject =
			faroProjectLocalService.getFaroProjectByGroupId(groupId);

		Results<AssetSummary> results = contactsEngineClient.getAssetSummaries(
			faroProject, channelId, keywords, rangeKey, cur, delta,
			orderByFieldsFaroParam.getValue());

		Function<AssetSummary, AssetSummaryDisplay> function =
			AssetSummaryDisplay::new;

		return new FaroFDSResultsDisplay(results, function, cur, delta);
	}

}
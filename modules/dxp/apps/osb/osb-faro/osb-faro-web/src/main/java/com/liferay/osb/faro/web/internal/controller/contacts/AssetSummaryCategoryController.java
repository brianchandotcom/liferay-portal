/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.controller.contacts;

import com.liferay.osb.faro.engine.client.model.AssetSummaryCategory;
import com.liferay.osb.faro.web.internal.controller.BaseFaroController;
import com.liferay.osb.faro.web.internal.model.display.FaroFDSResultsDisplay;
import com.liferay.osb.faro.web.internal.model.display.contacts.AssetSummaryCategoryDisplay;
import com.liferay.petra.string.StringPool;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ivica Cardic
 */
@Component(service = AssetSummaryCategoryController.class)
@Path("/{groupId}/asset-summary-categories")
@Produces(MediaType.APPLICATION_JSON)
public class AssetSummaryCategoryController extends BaseFaroController {

	@GET
	public FaroFDSResultsDisplay<AssetSummaryCategory>
			getAssetSummaryCategoriesFaroFDSResultsDisplay(
				@PathParam("groupId") long groupId,
				@QueryParam("channelId") long channelId,
				@QueryParam("keywords") String keywords,
				@QueryParam("page") int page,
				@DefaultValue("20") @QueryParam("pageSize") int pageSize,
				@QueryParam("rangeEnd") String rangeEnd,
				@QueryParam("rangeKey") int rangeKey,
				@QueryParam("rangeStart") String rangeStart,
				@DefaultValue(StringPool.BLANK) @QueryParam("sort") String
					sortString,
				@QueryParam("vocabularyId") String vocabularyId)
		throws Exception {

		return new FaroFDSResultsDisplay<>(
			contactsEngineClient.getAssetSummaryCategories(
				faroProjectLocalService.getFaroProjectByGroupId(groupId),
				channelId, keywords, rangeEnd, rangeKey, rangeStart, sortString,
				vocabularyId, page, pageSize),
			AssetSummaryCategoryDisplay::new, page, pageSize);
	}

}
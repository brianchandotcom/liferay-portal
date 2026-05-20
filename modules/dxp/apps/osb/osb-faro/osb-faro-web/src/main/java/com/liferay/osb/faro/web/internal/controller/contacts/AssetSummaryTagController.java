/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.controller.contacts;

import com.liferay.osb.faro.engine.client.model.AssetSummaryTag;
import com.liferay.osb.faro.web.internal.controller.BaseFaroController;
import com.liferay.osb.faro.web.internal.model.display.FaroFDSResultsDisplay;
import com.liferay.osb.faro.web.internal.model.display.contacts.AssetSummaryTagDisplay;
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
@Component(service = AssetSummaryTagController.class)
@Path("/{groupId}/asset-summary-tags")
@Produces(MediaType.APPLICATION_JSON)
public class AssetSummaryTagController extends BaseFaroController {

	@GET
	public FaroFDSResultsDisplay<AssetSummaryTag>
			getAssetSummaryTagsFaroFDSResultsDisplay(
				@PathParam("groupId") long groupId,
				@QueryParam("channelId") long channelId,
				@QueryParam("keywords") String keywords,
				@QueryParam("page") int page,
				@DefaultValue("20") @QueryParam("pageSize") int pageSize,
				@QueryParam("rangeEnd") String rangeEnd,
				@QueryParam("rangeKey") int rangeKey,
				@QueryParam("rangeStart") String rangeStart,
				@DefaultValue(StringPool.BLANK) @QueryParam("sort") String
					sortString)
		throws Exception {

		return new FaroFDSResultsDisplay<>(
			contactsEngineClient.getAssetSummaryTags(
				faroProjectLocalService.getFaroProjectByGroupId(groupId),
				channelId, keywords, rangeEnd, rangeKey, rangeStart, sortString,
				page, pageSize),
			AssetSummaryTagDisplay::new, page, pageSize);
	}

}
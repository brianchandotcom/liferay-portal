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

package com.liferay.osb.faro.web.internal.controller.contacts;

import com.liferay.osb.faro.engine.client.model.Asset;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.engine.client.util.OrderByField;
import com.liferay.osb.faro.web.internal.controller.BaseFaroController;
import com.liferay.osb.faro.web.internal.controller.FaroController;
import com.liferay.osb.faro.web.internal.model.display.FaroResultsDisplay;
import com.liferay.osb.faro.web.internal.model.display.contacts.AssetDisplay;
import com.liferay.osb.faro.web.internal.param.FaroParam;
import com.liferay.petra.string.StringPool;

import java.util.List;
import java.util.function.Function;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;

/**
 * @author Matthew Kong
 */
@Component(
	immediate = true, service = {AssetController.class, FaroController.class}
)
@Path("/{groupId}/asset")
@Produces(MediaType.APPLICATION_JSON)
public class AssetController extends BaseFaroController {

	@GET
	@Path("/{id}")
	public AssetDisplay get(
			@PathParam("groupId") long groupId, @PathParam("id") String id)
		throws Exception {

		return new AssetDisplay(
			contactsEngineClient.getAsset(
				faroProjectLocalService.getFaroProjectByGroupId(groupId), id));
	}

	@GET
	public FaroResultsDisplay search(
			@PathParam("groupId") long groupId,
			@QueryParam("dataSourceId") String dataSourceId,
			@QueryParam("query") String query,
			@DefaultValue("-1") @QueryParam("action") int action,
			@QueryParam("assetType") String assetType,
			@QueryParam("cur") int cur, @QueryParam("delta") int delta,
			@DefaultValue(StringPool.BLANK) @FormParam("orderByFields")
				FaroParam<List<OrderByField>> orderByFieldsFaroParam)
		throws Exception {

		return search(
			groupId, dataSourceId, query, action, assetType, cur, delta,
			orderByFieldsFaroParam.getValue());
	}

	@Path("/search")
	@POST
	public FaroResultsDisplay searchByForm(
			@PathParam("groupId") long groupId,
			@FormParam("dataSourceId") String dataSourceId,
			@FormParam("query") String query,
			@DefaultValue("-1") @FormParam("action") int action,
			@FormParam("assetType") String assetType, @FormParam("cur") int cur,
			@FormParam("delta") int delta,
			@DefaultValue(StringPool.BLANK) @FormParam("orderByFields")
				FaroParam<List<OrderByField>> orderByFieldsFaroParam)
		throws Exception {

		return search(
			groupId, dataSourceId, query, action, assetType, cur, delta,
			orderByFieldsFaroParam.getValue());
	}

	@SuppressWarnings("unchecked")
	protected FaroResultsDisplay search(
			long groupId, String dataSourceId, String query, int action,
			String assetType, int cur, int delta,
			List<OrderByField> orderByFields)
		throws Exception {

		Results<Asset> results = contactsEngineClient.getAssets(
			faroProjectLocalService.getFaroProjectByGroupId(groupId),
			dataSourceId, query, action, assetType, cur, delta, orderByFields);

		Function<Asset, AssetDisplay> function = AssetDisplay::new;

		return new FaroResultsDisplay(results, function);
	}

}
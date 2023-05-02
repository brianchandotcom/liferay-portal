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

import com.liferay.osb.faro.engine.client.model.ActivityGroup;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.engine.client.util.OrderByField;
import com.liferay.osb.faro.web.internal.controller.BaseFaroController;
import com.liferay.osb.faro.web.internal.controller.FaroController;
import com.liferay.osb.faro.web.internal.model.display.FaroResultsDisplay;
import com.liferay.osb.faro.web.internal.model.display.contacts.ActivityGroupDisplay;
import com.liferay.osb.faro.web.internal.param.FaroParam;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;

/**
 * @author Matthew Kong
 */
@Component(service = {ActivityGroupController.class, FaroController.class})
@Path("/{groupId}/activity_group")
@Produces(MediaType.APPLICATION_JSON)
public class ActivityGroupController extends BaseFaroController {

	@GET
	@RolesAllowed(RoleConstants.SITE_MEMBER)
	@SuppressWarnings("unchecked")
	public FaroResultsDisplay search(
			@PathParam("groupId") long groupId,
			@QueryParam("channelId") String channelId,
			@QueryParam("contactsEntityId") String contactsEntityId,
			@QueryParam("contactsEntityType") int contactsEntityType,
			@QueryParam("query") String query,
			@DefaultValue(StringPool.BLANK) @QueryParam("startDate") FaroParam
				<Date> startDateFaroParam,
			@DefaultValue(StringPool.BLANK) @QueryParam("endDate") FaroParam
				<Date> endDateFaroParam,
			@QueryParam("cur") int cur, @QueryParam("delta") int delta,
			@DefaultValue(StringPool.BLANK) @QueryParam("orderByFields")
				FaroParam<List<OrderByField>> orderByFieldsFaroParam)
		throws Exception {

		Results<ActivityGroup> results = contactsEngineClient.getActivityGroups(
			faroProjectLocalService.getFaroProjectByGroupId(groupId), channelId,
			contactsEntityId, contactsHelper.getOwnerType(contactsEntityType),
			query, startDateFaroParam.getValue(), endDateFaroParam.getValue(),
			cur, delta, orderByFieldsFaroParam.getValue());

		return new FaroResultsDisplay(
			TransformUtil.transform(
				results.getItems(),
				activityGroup -> {
					ActivityGroupDisplay activityGroupDisplay =
						new ActivityGroupDisplay(activityGroup);

					if (ListUtil.isEmpty(
							activityGroupDisplay.getActivityDisplays())) {

						return null;
					}

					return activityGroupDisplay;
				}),
			results.getTotal());
	}

}
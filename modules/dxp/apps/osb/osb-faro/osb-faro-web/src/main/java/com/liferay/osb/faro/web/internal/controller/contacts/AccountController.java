/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.controller.contacts;

import com.liferay.osb.faro.engine.client.model.Account;
import com.liferay.osb.faro.engine.client.model.AccountDetails;
import com.liferay.osb.faro.engine.client.model.AccountLifecycleStatus;
import com.liferay.osb.faro.engine.client.model.AccountMetric;
import com.liferay.osb.faro.engine.client.model.Individual;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.engine.client.util.OrderByField;
import com.liferay.osb.faro.web.internal.constants.FaroConstants;
import com.liferay.osb.faro.web.internal.controller.BaseFaroController;
import com.liferay.osb.faro.web.internal.controller.FaroController;
import com.liferay.osb.faro.web.internal.model.display.FaroFDSResultsDisplay;
import com.liferay.osb.faro.web.internal.model.display.FaroResultsDisplay;
import com.liferay.osb.faro.web.internal.model.display.contacts.AccountDisplay;
import com.liferay.osb.faro.web.internal.model.display.contacts.IndividualDisplay;
import com.liferay.osb.faro.web.internal.param.FaroParam;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.RoleConstants;

import jakarta.annotation.security.RolesAllowed;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Matthew Kong
 */
@Component(service = {AccountController.class, FaroController.class})
@Path("/{groupId}/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountController extends BaseFaroController {

	@GET
	@Path("/{id}/details")
	@RolesAllowed(RoleConstants.SITE_MEMBER)
	public FaroFDSResultsDisplay getAccountDetailsFaroFDSResultsDisplay(
			@PathParam("groupId") long groupId, @PathParam("id") String id,
			@QueryParam("page") int page, @QueryParam("pageSize") int pageSize)
		throws Exception {

		List<AccountDetails.Field> fields =
			contactsEngineClient.getAccountDetails(
				faroProjectLocalService.getFaroProjectByGroupId(groupId), id
			).getFields();

		return new FaroFDSResultsDisplay(
			new Results<>(fields, fields.size()), page, pageSize);
	}

	@GET
	@Path("/{id}")
	@RolesAllowed(RoleConstants.SITE_MEMBER)
	public AccountDisplay getAccountDisplay(
			@PathParam("groupId") long groupId, @PathParam("id") String id)
		throws Exception {

		return new AccountDisplay(
			contactsEngineClient.getAccount(
				faroProjectLocalService.getFaroProjectByGroupId(groupId), id));
	}

	@GET
	@Path("/distribution")
	@RolesAllowed(RoleConstants.SITE_MEMBER)
	public FaroResultsDisplay getAccountDistributionFaroResultsDisplay(
			@PathParam("groupId") long groupId,
			@QueryParam("channelId") String channelId,
			@QueryParam("fieldMappingFieldName") String fieldMappingFieldName,
			@QueryParam("filter") String filterString,
			@QueryParam("individualSegmentId") String individualSegmentId,
			@QueryParam("count") int count,
			@QueryParam("numberOfBins") int numberOfBins,
			@DefaultValue(StringPool.BLANK) @QueryParam("orderByFields")
				FaroParam<List<OrderByField>> orderByFieldsFaroParam)
		throws Exception {

		return new FaroResultsDisplay(
			contactsEngineClient.getAccountsDistribution(
				faroProjectLocalService.getFaroProjectByGroupId(groupId),
				channelId, fieldMappingFieldName, filterString,
				individualSegmentId, count, numberOfBins,
				orderByFieldsFaroParam.getValue()));
	}

	@GET
	@Path("/{id}/account-lifecycles/{accountLifecycleId}")
	@RolesAllowed(RoleConstants.SITE_MEMBER)
	public AccountLifecycleStatus getAccountLifecycleStatus(
			@PathParam("groupId") long groupId, @PathParam("id") String id,
			@PathParam("accountLifecycleId") String accountLifecycleId)
		throws Exception {

		return contactsEngineClient.getAccountLifecycleStatus(
			faroProjectLocalService.getFaroProjectByGroupId(groupId),
			accountLifecycleId, id);
	}

	@GET
	@Path("/metrics")
	@RolesAllowed(RoleConstants.SITE_MEMBER)
	public List<AccountMetric> getAccountMetrics(
			@PathParam("groupId") long groupId,
			@QueryParam("channelId") long channelId)
		throws Exception {

		return contactsEngineClient.getAccountMetrics(
			faroProjectLocalService.getFaroProjectByGroupId(groupId),
			channelId);
	}

	@Override
	public int[] getEntityTypes() {
		return _ENTITY_TYPES.clone();
	}

	@GET
	@Path("/{id}/individuals")
	@RolesAllowed(RoleConstants.SITE_MEMBER)
	public FaroFDSResultsDisplay<Individual>
			getIndividualsFaroFDSResultsDisplay(
				@PathParam("groupId") long groupId, @PathParam("id") String id,
				@QueryParam("page") int page,
				@QueryParam("pageSize") int pageSize,
				@DefaultValue(StringPool.BLANK) @QueryParam("sort") String
					sortString)
		throws Exception {

		return new FaroFDSResultsDisplay<>(
			contactsEngineClient.getAccountIndividuals(
				faroProjectLocalService.getFaroProjectByGroupId(groupId), id,
				page, pageSize, sortString),
			IndividualDisplay::new, page, pageSize);
	}

	@GET
	@Path("/search")
	@RolesAllowed(RoleConstants.SITE_MEMBER)
	public FaroFDSResultsDisplay<Account> search(
			@PathParam("groupId") long groupId,
			@QueryParam("channelId") String channelId,
			@QueryParam("filter") String filterString,
			@QueryParam("page") int page, @QueryParam("pageSize") int pageSize,
			@QueryParam("search") String search,
			@DefaultValue(StringPool.BLANK) @QueryParam("sort") String
				sortString)
		throws Exception {

		return new FaroFDSResultsDisplay<>(
			contactsEngineClient.getAccounts(
				faroProjectLocalService.getFaroProjectByGroupId(groupId),
				channelId, filterString, search, page, pageSize, sortString),
			AccountDisplay::new, page, pageSize);
	}

	@GET
	@Path("/fds_field_values")
	@RolesAllowed(RoleConstants.SITE_MEMBER)
	public FaroFDSResultsDisplay<Object> searchFDSFieldValues(
			@PathParam("groupId") long groupId,
			@QueryParam("channelId") long channelId,
			@QueryParam("fieldMappingFieldName") String fieldMappingFieldName,
			@QueryParam("query") String query, @QueryParam("page") int page,
			@QueryParam("pageSize") int pageSize)
		throws Exception {

		return new FaroFDSResultsDisplay<>(
			contactsEngineClient.getAccountFieldValues(
				faroProjectLocalService.getFaroProjectByGroupId(groupId),
				channelId, fieldMappingFieldName, query, page, pageSize),
			object -> Collections.singletonMap("name", String.valueOf(object)),
			page, pageSize);
	}

	@GET
	@Path("/field_values")
	@RolesAllowed(RoleConstants.SITE_MEMBER)
	@SuppressWarnings("unchecked")
	public FaroResultsDisplay searchValues(
			@PathParam("groupId") long groupId,
			@QueryParam("channelId") long channelId,
			@QueryParam("fieldMappingFieldName") String fieldMappingFieldName,
			@QueryParam("query") String query, @QueryParam("cur") int cur,
			@QueryParam("delta") int delta)
		throws Exception {

		return new FaroResultsDisplay(
			contactsEngineClient.getAccountFieldValues(
				faroProjectLocalService.getFaroProjectByGroupId(groupId),
				channelId, fieldMappingFieldName, query, cur, delta));
	}

	private static final int[] _ENTITY_TYPES = {FaroConstants.TYPE_ACCOUNT};

}
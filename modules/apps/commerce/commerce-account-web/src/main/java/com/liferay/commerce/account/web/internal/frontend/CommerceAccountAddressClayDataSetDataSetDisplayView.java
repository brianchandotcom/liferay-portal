/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.account.web.internal.frontend;

import com.liferay.commerce.account.constants.CommerceAccountActionKeys;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.web.internal.model.Address;
import com.liferay.commerce.constants.CommerceAddressConstants;
import com.liferay.commerce.frontend.CommerceDataSetDataProvider;
import com.liferay.commerce.frontend.Filter;
import com.liferay.commerce.frontend.Pagination;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetAction;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetActionProvider;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetDisplayView;
import com.liferay.commerce.frontend.clay.table.ClayTableDataSetDisplayView;
import com.liferay.commerce.frontend.clay.table.ClayTableSchema;
import com.liferay.commerce.frontend.clay.table.ClayTableSchemaBuilder;
import com.liferay.commerce.frontend.clay.table.ClayTableSchemaBuilderFactory;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"commerce.data.provider.key=" + CommerceAccountAddressClayDataSetDataSetDisplayView.NAME,
		"commerce.data.set.display.name=" + CommerceAccountAddressClayDataSetDataSetDisplayView.NAME
	},
	service = {
		ClayDataSetActionProvider.class, ClayDataSetDisplayView.class,
		CommerceDataSetDataProvider.class
	}
)
public class CommerceAccountAddressClayDataSetDataSetDisplayView
	extends ClayTableDataSetDisplayView
	implements ClayDataSetActionProvider, CommerceDataSetDataProvider<Address> {

	public static final String NAME = "commerceAccountAddresses";

	@Override
	public List<ClayDataSetAction> clayDataSetActions(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long commerceAccountId = ParamUtil.getLong(
			httpServletRequest, "commerceAccountId");

		if (!_modelResourcePermission.contains(
				themeDisplay.getPermissionChecker(), commerceAccountId,
				CommerceAccountActionKeys.MANAGE_ADDRESSES)) {

			return Collections.emptyList();
		}

		List<ClayDataSetAction> clayDataSetActions = new ArrayList<>();

		Address address = (Address)model;

		StringBundler sb = new StringBundler(7);

		sb.append("deleteCommerceAddress");
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(StringPool.APOSTROPHE);
		sb.append(address.getAddressId());
		sb.append(StringPool.APOSTROPHE);
		sb.append(StringPool.CLOSE_PARENTHESIS);
		sb.append(StringPool.SEMICOLON);

		ClayDataSetAction deleteClayDataSetAction = new ClayDataSetAction(
			StringPool.BLANK, StringPool.POUND, StringPool.BLANK,
			LanguageUtil.get(httpServletRequest, "delete"), sb.toString(),
			false, false);

		clayDataSetActions.add(deleteClayDataSetAction);

		sb.setStringAt("editCommerceAddress", 0);

		ClayDataSetAction editClayDataSetAction = new ClayDataSetAction(
			StringPool.BLANK, StringPool.POUND, StringPool.BLANK,
			LanguageUtil.get(httpServletRequest, "edit"), sb.toString(), false,
			false);

		clayDataSetActions.add(editClayDataSetAction);

		return clayDataSetActions;
	}

	@Override
	public int countItems(HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long commerceAccountId = ParamUtil.getLong(
			httpServletRequest, "commerceAccountId");

		return _commerceAddressService.getCommerceAddressesCountByCompanyId(
			themeDisplay.getCompanyId(), CommerceAccount.class.getName(),
			commerceAccountId);
	}

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.clayTableSchemaBuilder();

		clayTableSchemaBuilder.addField("address", "address");
		clayTableSchemaBuilder.addField("type", "type");
		clayTableSchemaBuilder.addField("referent", "name");
		clayTableSchemaBuilder.addField("phoneNumber", "phone");

		return clayTableSchemaBuilder.build();
	}

	@Override
	public List<Address> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<Address> addresses = new ArrayList<>();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long commerceAccountId = ParamUtil.getLong(
			httpServletRequest, "commerceAccountId");

		List<CommerceAddress> commerceAddresses =
			_commerceAddressService.getCommerceAddressesByCompanyId(
				themeDisplay.getCompanyId(), CommerceAccount.class.getName(),
				commerceAccountId, pagination.getStartPosition(),
				pagination.getEndPosition(), null);

		for (CommerceAddress commerceAddress : commerceAddresses) {
			String typeLabel = LanguageUtil.get(
				themeDisplay.getLocale(),
				CommerceAddressConstants.getAddressTypeLabel(
					commerceAddress.getType()));

			addresses.add(
				new Address(
					commerceAddress.getCommerceAddressId(),
					getCompleteAddress(commerceAddress), typeLabel,
					commerceAddress.getName(),
					commerceAddress.getPhoneNumber()));
		}

		return addresses;
	}

	protected String getCompleteAddress(CommerceAddress commerceAddress)
		throws PortalException {

		StringBundler sb = new StringBundler(7);

		sb.append(commerceAddress.getZip());
		sb.append(StringPool.SPACE);
		sb.append(commerceAddress.getStreet1());
		sb.append(StringPool.SPACE);
		sb.append(commerceAddress.getCity());
		sb.append(" - ");

		CommerceCountry commerceCountry = commerceAddress.getCommerceCountry();

		sb.append(commerceCountry.getThreeLettersISOCode());

		return sb.toString();
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.account.model.CommerceAccount)"
	)
	private ModelResourcePermission<CommerceAccount> _modelResourcePermission;

}
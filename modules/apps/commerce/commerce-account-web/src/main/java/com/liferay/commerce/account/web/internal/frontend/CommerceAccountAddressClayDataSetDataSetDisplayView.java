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
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetActionProvider;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.frontend.taglib.clay.data.set.view.table.BaseTableClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilderFactory;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"clay.data.provider.key=" + CommerceAccountAddressClayDataSetDataSetDisplayView.NAME,
		"clay.data.set.display.name=" + CommerceAccountAddressClayDataSetDataSetDisplayView.NAME
	},
	service = {
		ClayDataSetActionProvider.class, ClayDataSetDataProvider.class,
		ClayDataSetDisplayView.class
	}
)
public class CommerceAccountAddressClayDataSetDataSetDisplayView
	extends BaseTableClayDataSetDisplayView
	implements ClayDataSetActionProvider, ClayDataSetDataProvider<Address> {

	public static final String NAME = "commerceAccountAddresses";

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		clayTableSchemaBuilder.addClayTableSchemaField("address", "address");
		clayTableSchemaBuilder.addClayTableSchemaField("type", "type");
		clayTableSchemaBuilder.addClayTableSchemaField("referent", "name");
		clayTableSchemaBuilder.addClayTableSchemaField("phoneNumber", "phone");

		return clayTableSchemaBuilder.build();
	}

	@Override
	public List<DropdownItem> getDropdownItems(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		long commerceAccountId = ParamUtil.getLong(
			httpServletRequest, "commerceAccountId");

		Address address = (Address)model;

		return DropdownItemListBuilder.add(
			() -> _modelResourcePermission.contains(
				permissionChecker, commerceAccountId,
				CommerceAccountActionKeys.MANAGE_ADDRESSES),
			dropdownItem -> {
				StringBundler sb = new StringBundler(7);

				sb.append("javascript:deleteCommerceAddress");
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(StringPool.APOSTROPHE);
				sb.append(address.getAddressId());
				sb.append(StringPool.APOSTROPHE);
				sb.append(StringPool.CLOSE_PARENTHESIS);
				sb.append(StringPool.SEMICOLON);

				dropdownItem.setHref(sb.toString());

				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
			}
		).add(
			() -> _modelResourcePermission.contains(
				permissionChecker, commerceAccountId,
				CommerceAccountActionKeys.MANAGE_ADDRESSES),
			dropdownItem -> {
				StringBundler sb = new StringBundler(7);

				sb.append("javascript:editCommerceAddress");
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(StringPool.APOSTROPHE);
				sb.append(address.getAddressId());
				sb.append(StringPool.APOSTROPHE);
				sb.append(StringPool.CLOSE_PARENTHESIS);
				sb.append(StringPool.SEMICOLON);

				dropdownItem.setHref(sb.toString());

				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "edit"));
			}
		).build();
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

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
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

	protected String getCompleteAddress(CommerceAddress commerceAddress)
		throws PortalException {

		StringBundler sb = new StringBundler(7);

		sb.append(commerceAddress.getZip());
		sb.append(StringPool.SPACE);
		sb.append(commerceAddress.getStreet1());
		sb.append(StringPool.SPACE);
		sb.append(commerceAddress.getCity());
		sb.append(" - ");

		Country country = commerceAddress.getCountry();

		sb.append(country.getA3());

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
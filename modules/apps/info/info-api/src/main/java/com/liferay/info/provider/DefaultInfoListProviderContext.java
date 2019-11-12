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

package com.liferay.info.provider;

import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;

import java.util.Optional;

/**
 * @author     Jorge Ferrer
 * @deprecated As of Mueller (7.2.x), moved to {@link
 *             com.liferay.info.list.provider.DefaultInfoListProviderContext}
 */
@Deprecated
public class DefaultInfoListProviderContext implements InfoListProviderContext {

	public DefaultInfoListProviderContext(Company company, User user) {
		_company = company;
		_user = user;
	}

	public DefaultInfoListProviderContext(Group group, User user) {
		_group = group;
		_user = user;

		try {
			_company = CompanyLocalServiceUtil.getCompany(group.getCompanyId());
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	@Override
	public Company getCompany() {
		return _company;
	}

	@Override
	public Optional<Group> getGroupOptional() {
		return Optional.of(_group);
	}

	@Override
	public Optional<InfoDisplayObjectProvider>
		getInfoDisplayObjectProviderOptional() {

		return Optional.ofNullable(_infoDisplayObjectProvider);
	}

	@Override
	public Optional<Layout> getLayoutOptional() {
		return Optional.of(_layout);
	}

	@Override
	public User getUser() {
		return _user;
	}

	public void setInfoDisplayObjectProvider(
		InfoDisplayObjectProvider infoDisplayObjectProvider) {

		_infoDisplayObjectProvider = infoDisplayObjectProvider;
	}

	public void setLayout(Layout layout) {
		_layout = layout;
	}

	private final Company _company;
	private Group _group;
	private InfoDisplayObjectProvider _infoDisplayObjectProvider;
	private Layout _layout;
	private final User _user;

}
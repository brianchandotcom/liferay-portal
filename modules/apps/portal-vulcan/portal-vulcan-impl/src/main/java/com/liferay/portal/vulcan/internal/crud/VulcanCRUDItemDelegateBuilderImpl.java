/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.crud;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.crud.VulcanCRUDItemDelegate;
import com.liferay.portal.vulcan.crud.VulcanCRUDItemDelegateBuilder;

import javax.ws.rs.core.UriInfo;

/**
 * @author Carlos Correa
 */
public class VulcanCRUDItemDelegateBuilderImpl
	implements VulcanCRUDItemDelegateBuilder,
			   VulcanCRUDItemDelegateBuilder.
				   BuildStepVulcanCRUDItemDelegateBuilder,
			   VulcanCRUDItemDelegateBuilder.
				   UriInfoStepVulcanCRUDItemDelegateBuilder,
			   VulcanCRUDItemDelegateBuilder.
				   UserStepVulcanCRUDItemDelegateBuilder {

	@Override
	public UriInfoStepVulcanCRUDItemDelegateBuilder acceptLanguage(
		AcceptLanguage acceptLanguage) {

		_vulcanCRUDItemDelegate.setContextAcceptLanguage(acceptLanguage);

		return this;
	}

	@Override
	public VulcanCRUDItemDelegate build() {
		return _vulcanCRUDItemDelegate;
	}

	@Override
	public UserStepVulcanCRUDItemDelegateBuilder uriInfo(UriInfo uriInfo) {
		_vulcanCRUDItemDelegate.setContextUriInfo(uriInfo);

		return this;
	}

	@Override
	public BuildStepVulcanCRUDItemDelegateBuilder user(User user) {
		_vulcanCRUDItemDelegate.setContextUser(user);

		return this;
	}

	protected VulcanCRUDItemDelegateBuilderImpl(
		Company company, VulcanCRUDItemDelegate vulcanCRUDItemDelegate) {

		vulcanCRUDItemDelegate.setContextCompany(company);

		_vulcanCRUDItemDelegate = vulcanCRUDItemDelegate;
	}

	private final VulcanCRUDItemDelegate _vulcanCRUDItemDelegate;

}
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
import com.liferay.portal.vulcan.jaxrs.context.ContextDataInjector;
import com.liferay.portal.vulcan.jaxrs.context.ContextDataInjectorBuilder;

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

		_acceptLanguage = acceptLanguage;

		return this;
	}

	@Override
	public VulcanCRUDItemDelegate build() throws Exception {
		ContextDataInjector contextDataInjector =
			_contextDataInjectorBuilder.acceptLanguage(
				_acceptLanguage
			).company(
				_company
			).uriInfo(
				_uriInfo
			).user(
				_user
			).build();

		return (VulcanCRUDItemDelegate)contextDataInjector.inject(
			_vulcanCRUDItemDelegate);
	}

	@Override
	public UserStepVulcanCRUDItemDelegateBuilder uriInfo(UriInfo uriInfo) {
		_uriInfo = uriInfo;

		return this;
	}

	@Override
	public BuildStepVulcanCRUDItemDelegateBuilder user(User user) {
		_user = user;

		return this;
	}

	protected VulcanCRUDItemDelegateBuilderImpl(
		Company company, ContextDataInjectorBuilder contextDataInjectorBuilder,
		VulcanCRUDItemDelegate vulcanCRUDItemDelegate) {

		_company = company;
		_contextDataInjectorBuilder = contextDataInjectorBuilder;
		_vulcanCRUDItemDelegate = vulcanCRUDItemDelegate;
	}

	private AcceptLanguage _acceptLanguage;
	private final Company _company;
	private final ContextDataInjectorBuilder _contextDataInjectorBuilder;
	private UriInfo _uriInfo;
	private User _user;
	private final VulcanCRUDItemDelegate _vulcanCRUDItemDelegate;

}
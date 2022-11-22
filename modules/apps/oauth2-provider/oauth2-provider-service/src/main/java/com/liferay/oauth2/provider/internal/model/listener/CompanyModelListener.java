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

package com.liferay.oauth2.provider.internal.model.listener;

import com.liferay.oauth2.provider.model.OAuth2ScopeGrant;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.oauth2.provider.service.OAuth2ScopeGrantLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Díaz
 */
@Component(service = ModelListener.class)
public class CompanyModelListener extends BaseModelListener<Company> {

	@Override
	public void onAfterRemove(Company company) throws ModelListenerException {
		try {
			_oAuth2ApplicationLocalService.deleteOAuth2Applications(
				company.getCompanyId());

			_deleteOAuth2ScopeGrant(company);
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	private void _deleteOAuth2ScopeGrant(Company company)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_oAuth2ScopeGrantLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> dynamicQuery.add(
				RestrictionsFactoryUtil.eq(
					"companyId", company.getCompanyId())));
		actionableDynamicQuery.setPerformActionMethod(
			oAuth2ScopeGrant ->
				_oAuth2ScopeGrantLocalService.deleteOAuth2ScopeGrant(
					(OAuth2ScopeGrant)oAuth2ScopeGrant));

		actionableDynamicQuery.performActions();
	}

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference
	private OAuth2ScopeGrantLocalService _oAuth2ScopeGrantLocalService;

}
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

package com.liferay.oauth2.provider.service.persistence.impl;

import com.liferay.oauth2.provider.model.OAuth2ScopeGrant;
import com.liferay.oauth2.provider.service.persistence.OAuth2ScopeGrantPersistence;
import com.liferay.oauth2.provider.service.persistence.impl.constants.OAuthTwoPersistenceConstants;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public abstract class OAuth2ScopeGrantFinderBaseImpl
	extends BasePersistenceImpl<OAuth2ScopeGrant> {

	public OAuth2ScopeGrantFinderBaseImpl() {
		setModelClass(OAuth2ScopeGrant.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"oAuth2ApplicationScopeAliasesId", "oA2AScopeAliasesId");

		setDBColumnNames(dbColumnNames);
	}

	@Override
	public Set<String> getBadColumnNames() {
		return oAuth2ScopeGrantPersistence.getBadColumnNames();
	}

	@Override
	@Reference(
		target = OAuthTwoPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);
	}

	@Override
	@Reference(
		target = OAuthTwoPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = OAuthTwoPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected OAuth2ScopeGrantPersistence oAuth2ScopeGrantPersistence;

	private static final Log _log = LogFactoryUtil.getLog(
		OAuth2ScopeGrantFinderBaseImpl.class);

	static {
		try {
			Class.forName(OAuthTwoPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}
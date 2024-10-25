/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.service.persistence.impl;

import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.model.OAuth2ScopeGrant;
import com.liferay.oauth2.provider.model.impl.OAuth2AuthorizationImpl;
import com.liferay.oauth2.provider.model.impl.OAuth2ScopeGrantImpl;
import com.liferay.oauth2.provider.service.persistence.OAuth2ScopeGrantFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(service = OAuth2ScopeGrantFinder.class)
public class OAuth2ScopeGrantFinderImpl
	extends OAuth2ScopeGrantFinderBaseImpl implements OAuth2ScopeGrantFinder {

	public static final String FIND_BY_C_A_B_A =
		OAuth2ScopeGrantFinder.class.getName() + ".findByC_A_B_A";

	@Override
	public Collection<OAuth2ScopeGrant> findByC_A_B_A(
		long companyId, String applicationName, String bundleSymbolicName,
		String accessTokenContent) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_C_A_B_A);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			sqlQuery.addEntity("OAuth2ScopeGrant", OAuth2ScopeGrantImpl.class);
			sqlQuery.addEntity(
				"OAuth2Authorization", OAuth2AuthorizationImpl.class);

			queryPos.add(companyId);
			queryPos.add(applicationName);
			queryPos.add(bundleSymbolicName);
			queryPos.add(accessTokenContent.hashCode());

			List<Object[]> rows = (List<Object[]>)QueryUtil.list(
				sqlQuery, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			ArrayList<OAuth2ScopeGrant> oAuth2ScopeGrants = new ArrayList<>();

			for (Object[] row : rows) {
				OAuth2Authorization oAuth2Authorization =
					(OAuth2Authorization)row[1];

				if (accessTokenContent.equals(
						oAuth2Authorization.getAccessTokenContent())) {

					oAuth2ScopeGrants.add((OAuth2ScopeGrant)row[0]);
				}
			}

			return oAuth2ScopeGrants;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Reference
	private CustomSQL _customSQL;

}
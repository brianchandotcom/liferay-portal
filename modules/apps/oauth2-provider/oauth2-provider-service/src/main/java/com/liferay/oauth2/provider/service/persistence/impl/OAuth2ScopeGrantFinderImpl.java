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
import com.liferay.oauth2.provider.service.persistence.OAuth2ScopeGrantUtil;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
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
 * @author Raymond Augé
 */
@Component(service = OAuth2ScopeGrantFinder.class)
public class OAuth2ScopeGrantFinderImpl
	extends OAuth2ScopeGrantFinderBaseImpl implements OAuth2ScopeGrantFinder {

	public static final String FIND_BY_C_A_B_A =
		OAuth2ScopeGrantFinder.class.getName() + ".findByC_A_B_A";

	public static final FinderPath FINDER_PATH_FIND_BY_C_A_B_A = new FinderPath(
		OAuth2ScopeGrantPersistenceImpl.
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
		"OAuth2ScopeGrantFinderImpl_findByC_A_B_A",
		new String[] {
			Long.class.getName(), String.class.getName(),
			String.class.getName(), String.class.getName()
		},
		new String[] {
			"companyId", "applicationName", "bundleSymbolicName",
			"accessTokenContent"
		},
		false);

	@Override
	public Collection<OAuth2ScopeGrant> findByC_A_B_A(
		long companyId, String applicationName, String bundleSymbolicName,
		String accessTokenContent) {

		Object[] finderArgs = {
			companyId, applicationName, bundleSymbolicName, accessTokenContent
		};

		List<OAuth2ScopeGrant> oAuth2ScopeGrants =
			(List<OAuth2ScopeGrant>)FinderCacheUtil.getResult(
				FINDER_PATH_FIND_BY_C_A_B_A, finderArgs,
				OAuth2ScopeGrantUtil.getPersistence());

		if (oAuth2ScopeGrants != null) {
			return oAuth2ScopeGrants;
		}

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

			oAuth2ScopeGrants = new ArrayList<>();

			for (Object[] row : rows) {
				OAuth2Authorization oAuth2Authorization =
					(OAuth2Authorization)row[1];

				if (accessTokenContent.equals(
						oAuth2Authorization.getAccessTokenContent())) {

					oAuth2ScopeGrants.add((OAuth2ScopeGrant)row[0]);
				}
			}

			FinderCacheUtil.putResult(
				FINDER_PATH_FIND_BY_C_A_B_A, finderArgs, oAuth2ScopeGrants);

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
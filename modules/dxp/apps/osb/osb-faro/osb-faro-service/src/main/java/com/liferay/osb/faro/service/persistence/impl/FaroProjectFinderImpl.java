/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.service.persistence.impl;

import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.model.impl.FaroProjectImpl;
import com.liferay.osb.faro.service.persistence.FaroProjectFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Geyson Silva
 */
public class FaroProjectFinderImpl
	extends FaroProjectFinderBaseImpl implements FaroProjectFinder {

	public static final String FIND_BY_ED =
		FaroProjectFinder.class.getName() + ".findByED";

	@Override
	public List<FaroProject> findByEmailAddressDomain(
		String emailAddressDomain) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_ED);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("OSBFaro_FaroProject", FaroProjectImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(emailAddressDomain);

			return (List<FaroProject>)QueryUtil.list(
				q, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}
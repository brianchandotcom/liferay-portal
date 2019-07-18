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

package com.liferay.microblogs.service.persistence.impl;

import com.liferay.microblogs.model.MicroblogsEntry;
import com.liferay.microblogs.model.MicroblogsEntryConstants;
import com.liferay.microblogs.model.impl.MicroblogsEntryImpl;
import com.liferay.microblogs.service.persistence.MicroblogsEntryFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.social.kernel.model.SocialRelationConstants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jonathan Lee
 */
@Component(service = MicroblogsEntryFinder.class)
public class MicroblogsEntryFinderImpl
	extends MicroblogsEntryFinderBaseImpl implements MicroblogsEntryFinder {

	public static final String COUNT_BY_USER_ID =
		MicroblogsEntryFinder.class.getName() + ".countByUserId";

	public static final String COUNT_BY_C_U =
		MicroblogsEntryFinder.class.getName() + ".countByC_U";

	public static final String COUNT_BY_U_MU =
		MicroblogsEntryFinder.class.getName() + ".countByU_MU";

	public static final String COUNT_BY_U_ATN =
		MicroblogsEntryFinder.class.getName() + ".countByU_ATN";

	public static final String COUNT_BY_CCNI_ATN =
		MicroblogsEntryFinder.class.getName() + ".countByCCNI_ATN";

	public static final String COUNT_BY_C_U_ATN =
		MicroblogsEntryFinder.class.getName() + ".countByC_U_ATN";

	public static final String COUNT_BY_C_CCNI_ATN =
		MicroblogsEntryFinder.class.getName() + ".countByC_CCNI_ATN";

	public static final String COUNT_BY_U_T_MU =
		MicroblogsEntryFinder.class.getName() + ".countByU_T_MU";

	public static final String COUNT_BY_CCNI_CCPK_ATN =
		MicroblogsEntryFinder.class.getName() + ".countByCCNI_CCPK_ATN";

	public static final String COUNT_BY_C_CCNI_CCPK_ATN =
		MicroblogsEntryFinder.class.getName() + ".countByC_CCNI_CCPK_ATN";

	public static final String FIND_BY_USER_ID =
		MicroblogsEntryFinder.class.getName() + ".findByUserId";

	public static final String FIND_BY_C_U =
		MicroblogsEntryFinder.class.getName() + ".findByC_U";

	public static final String FIND_BY_U_MU =
		MicroblogsEntryFinder.class.getName() + ".findByU_MU";

	public static final String FIND_BY_U_ATN =
		MicroblogsEntryFinder.class.getName() + ".findByU_ATN";

	public static final String FIND_BY_CCNI_ATN =
		MicroblogsEntryFinder.class.getName() + ".findByCCNI_ATN";

	public static final String FIND_BY_C_U_ATN =
		MicroblogsEntryFinder.class.getName() + ".findByC_U_ATN";

	public static final String FIND_BY_C_CCNI_ATN =
		MicroblogsEntryFinder.class.getName() + ".findByC_CCNI_ATN";

	public static final String FIND_BY_U_T_MU =
		MicroblogsEntryFinder.class.getName() + ".findByU_T_MU";

	public static final String FIND_BY_CCNI_CCPK_ATN =
		MicroblogsEntryFinder.class.getName() + ".findByCCNI_CCPK_ATN";

	public static final String FIND_BY_C_CCNI_CCPK_ATN =
		MicroblogsEntryFinder.class.getName() + ".findByC_CCNI_CCPK_ATN";

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #countByC_U(long,
	 *             long)}
	 */
	@Deprecated
	@Override
	public int countByUserId(long userId) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_USER_ID);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(MicroblogsEntryConstants.TYPE_EVERYONE);
			qPos.add(userId);
			qPos.add(SocialRelationConstants.TYPE_UNI_ENEMY);
			qPos.add(userId);
			qPos.add(userId);
			qPos.add(MicroblogsEntryConstants.TYPE_REPLY);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public int countByC_U(long companyId, long userId) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_C_U);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(MicroblogsEntryConstants.TYPE_EVERYONE);
			qPos.add(userId);
			qPos.add(SocialRelationConstants.TYPE_UNI_ENEMY);
			qPos.add(userId);
			qPos.add(userId);
			qPos.add(MicroblogsEntryConstants.TYPE_REPLY);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public int countByU_MU(long userId, long microblogsEntryUserId) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_U_MU);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(MicroblogsEntryConstants.TYPE_EVERYONE);
			qPos.add(userId);
			qPos.add(microblogsEntryUserId);
			qPos.add(MicroblogsEntryConstants.TYPE_REPLY);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #countByC_U_ATN(long, long, String)}
	 */
	@Deprecated
	@Override
	public int countByU_ATN(long userId, String assetTagName) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_U_ATN);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(MicroblogsEntryConstants.TYPE_EVERYONE);
			qPos.add(userId);
			qPos.add(assetTagName);
			qPos.add(userId);
			qPos.add(assetTagName);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #countByC_CCNI_ATN(long, long, String)}
	 */
	@Deprecated
	@Override
	public int countByCCNI_ATN(long creatorClassNameId, String assetTagName) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_CCNI_ATN);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(creatorClassNameId);
			qPos.add(assetTagName);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public int countByC_U_ATN(
		long companyId, long userId, String assetTagName) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_C_U_ATN);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(MicroblogsEntryConstants.TYPE_EVERYONE);
			qPos.add(userId);
			qPos.add(assetTagName);
			qPos.add(userId);
			qPos.add(assetTagName);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public int countByC_CCNI_ATN(
		long companyId, long creatorClassNameId, String assetTagName) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_C_CCNI_ATN);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(creatorClassNameId);
			qPos.add(assetTagName);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public int countByU_T_MU(
		long userId, int type, long microblogsEntryUserId) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_U_T_MU);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(MicroblogsEntryConstants.TYPE_EVERYONE);
			qPos.add(userId);
			qPos.add(type);
			qPos.add(microblogsEntryUserId);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #countByC_CCNI_CCPK_ATN(long, long, long, String, boolean)}
	 */
	@Deprecated
	@Override
	public int countByCCNI_CCPK_ATN(
		long creatorClassNameId, long creatorClassPK, String assetTagName,
		boolean andOperator) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_CCNI_CCPK_ATN);

			sql = _customSQL.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(creatorClassNameId);
			qPos.add(creatorClassPK);
			qPos.add(assetTagName);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public int countByC_CCNI_CCPK_ATN(
		long companyId, long creatorClassNameId, long creatorClassPK,
		String assetTagName, boolean andOperator) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_C_CCNI_CCPK_ATN);

			sql = _customSQL.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(creatorClassNameId);
			qPos.add(creatorClassPK);
			qPos.add(assetTagName);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #findByC_U(long,
	 *             long, int, int)}
	 */
	@Deprecated
	@Override
	public List<MicroblogsEntry> findByUserId(long userId, int start, int end) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_USER_ID);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar("microblogsEntryId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(MicroblogsEntryConstants.TYPE_EVERYONE);
			qPos.add(userId);
			qPos.add(SocialRelationConstants.TYPE_UNI_ENEMY);
			qPos.add(userId);
			qPos.add(userId);
			qPos.add(MicroblogsEntryConstants.TYPE_REPLY);

			Iterator<Long> itr = (Iterator<Long>)QueryUtil.iterate(
				q, getDialect(), start, end);

			List<MicroblogsEntry> microblogsEntries = new ArrayList<>();

			while (itr.hasNext()) {
				microblogsEntries.add(
					microblogsEntryPersistence.fetchByPrimaryKey(
						(Long)itr.next()));
			}

			return microblogsEntries;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<MicroblogsEntry> findByC_U(
		long companyId, long userId, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_C_U);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar("microblogsEntryId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(MicroblogsEntryConstants.TYPE_EVERYONE);
			qPos.add(userId);
			qPos.add(SocialRelationConstants.TYPE_UNI_ENEMY);
			qPos.add(userId);
			qPos.add(userId);
			qPos.add(MicroblogsEntryConstants.TYPE_REPLY);

			Iterator<Long> itr = (Iterator<Long>)QueryUtil.iterate(
				q, getDialect(), start, end);

			List<MicroblogsEntry> microblogsEntries = new ArrayList<>();

			while (itr.hasNext()) {
				microblogsEntries.add(
					microblogsEntryPersistence.fetchByPrimaryKey(
						(Long)itr.next()));
			}

			return microblogsEntries;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<MicroblogsEntry> findByU_MU(
		long userId, long microblogsEntryUserId, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_U_MU);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MicroblogsEntry", MicroblogsEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(MicroblogsEntryConstants.TYPE_EVERYONE);
			qPos.add(userId);
			qPos.add(microblogsEntryUserId);
			qPos.add(MicroblogsEntryConstants.TYPE_REPLY);

			return (List<MicroblogsEntry>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #findByC_U_ATN(long,
	 *             long, String, int, int)}
	 */
	@Deprecated
	@Override
	public List<MicroblogsEntry> findByU_ATN(
		long userId, String assetTagName, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_U_ATN);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MicroblogsEntry", MicroblogsEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(MicroblogsEntryConstants.TYPE_EVERYONE);
			qPos.add(userId);
			qPos.add(assetTagName);
			qPos.add(userId);
			qPos.add(assetTagName);

			return (List<MicroblogsEntry>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #findByC_CCNI_ATN(long, long, String, int, int)}
	 */
	@Deprecated
	@Override
	public List<MicroblogsEntry> findByCCNI_ATN(
		long creatorClassNameId, String assetTagName, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_CCNI_ATN);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MicroblogsEntry", MicroblogsEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(creatorClassNameId);
			qPos.add(assetTagName);

			return (List<MicroblogsEntry>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<MicroblogsEntry> findByC_U_ATN(
		long companyId, long userId, String assetTagName, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_C_U_ATN);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MicroblogsEntry", MicroblogsEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(MicroblogsEntryConstants.TYPE_EVERYONE);
			qPos.add(userId);
			qPos.add(assetTagName);
			qPos.add(userId);
			qPos.add(assetTagName);

			return (List<MicroblogsEntry>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<MicroblogsEntry> findByC_CCNI_ATN(
		long companyId, long creatorClassNameId, String assetTagName, int start,
		int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_C_CCNI_ATN);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MicroblogsEntry", MicroblogsEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(creatorClassNameId);
			qPos.add(assetTagName);

			return (List<MicroblogsEntry>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<MicroblogsEntry> findByU_T_MU(
		long userId, int type, long microblogsEntryUserId, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_U_T_MU);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MicroblogsEntry", MicroblogsEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(MicroblogsEntryConstants.TYPE_EVERYONE);
			qPos.add(userId);
			qPos.add(type);
			qPos.add(microblogsEntryUserId);

			return (List<MicroblogsEntry>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #findByC_CCNI_CCPK_ATN(long, long, long, String, boolean,
	 *             int, int)}
	 */
	@Deprecated
	@Override
	public List<MicroblogsEntry> findByCCNI_CCPK_ATN(
		long creatorClassNameId, long creatorClassPK, String assetTagName,
		boolean andOperator, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_CCNI_CCPK_ATN);

			sql = _customSQL.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MicroblogsEntry", MicroblogsEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(creatorClassNameId);
			qPos.add(creatorClassPK);
			qPos.add(assetTagName);

			return (List<MicroblogsEntry>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<MicroblogsEntry> findByC_CCNI_CCPK_ATN(
		long companyId, long creatorClassNameId, long creatorClassPK,
		String assetTagName, boolean andOperator, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_C_CCNI_CCPK_ATN);

			sql = _customSQL.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("MicroblogsEntry", MicroblogsEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(creatorClassNameId);
			qPos.add(creatorClassPK);
			qPos.add(assetTagName);

			return (List<MicroblogsEntry>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Reference
	private CustomSQL _customSQL;

}
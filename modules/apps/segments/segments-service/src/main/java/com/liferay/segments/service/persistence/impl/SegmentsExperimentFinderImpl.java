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

package com.liferay.segments.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.impl.SegmentsExperimentImpl;
import com.liferay.segments.service.persistence.SegmentsExperimentFinder;

import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(service = SegmentsExperimentFinder.class)
public class SegmentsExperimentFinderImpl
	extends SegmentsExperimentFinderBaseImpl
	implements SegmentsExperimentFinder {

	public static final String COUNT_BY_S_C_C_S =
		SegmentsExperimentFinder.class.getName() + ".countByS_C_C_S";

	public static final String FIND_BY_S_C_C_S =
		SegmentsExperimentFinder.class.getName() + ".findByS_C_C_S";

	@Override
	public int countByS_C_C_S(
		long segmentsExperienceId, long classNameId, long classPK,
		int[] statuses) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_S_C_C_S);

			sql = _replaceSegmentsExperimentStatus(sql, statuses);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(segmentsExperienceId);
			qPos.add(classNameId);
			qPos.add(classPK);

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
	public List<SegmentsExperiment> findByS_C_C_S(
		long segmentsExperienceId, long classNameId, long classPK,
		int[] statuses, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_S_C_C_S);

			sql = _replaceSegmentsExperimentStatus(sql, statuses);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("SegmentsExperiment", SegmentsExperimentImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(segmentsExperienceId);
			qPos.add(classNameId);
			qPos.add(classPK);

			return (List<SegmentsExperiment>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private String _replaceSegmentsExperimentStatus(
		String sql, int[] statuses) {

		StringBundler sb = new StringBundler(statuses.length);

		for (int i = 0; i < statuses.length; i++) {
			sb.append(statuses[i]);

			if (i != (statuses.length - 1)) {
				sb.append(", ");
			}
		}

		return StringUtil.replace(sql, "[$STATUSES$]", sb.toString());
	}

	@Reference
	private CustomSQL _customSQL;

}
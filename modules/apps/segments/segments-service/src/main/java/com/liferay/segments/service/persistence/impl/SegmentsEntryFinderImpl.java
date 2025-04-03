/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.service.persistence.impl;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.impl.SegmentsEntryImpl;
import com.liferay.segments.service.persistence.SegmentsEntryFinder;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(service = SegmentsEntryFinder.class)
public class SegmentsEntryFinderImpl
	extends SegmentsEntryFinderBaseImpl implements SegmentsEntryFinder {

	public static final String FIND_BY_SEGMENTSENTRYIDS =
		SegmentsEntryFinder.class.getName() + ".findBySegmentsEntryIds";

	@Override
	public List<SegmentsEntry> findBySegmentsEntryIds(
		List<Long> segmentsEntryIds, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_SEGMENTSENTRYIDS);

			sql = StringUtil.replace(
				sql, "[$SEGMENTS_ENTRY_IDS$]",
				getSegmentsEntryIds(segmentsEntryIds));

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity("SegmentsEntry", SegmentsEntryImpl.class);

			return (List<SegmentsEntry>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getSegmentsEntryIds(List<Long> segmentsEntryIds) {
		if (segmentsEntryIds.isEmpty()) {
			return StringPool.BLANK;
		}

		return StringUtil.merge(
			TransformUtil.transform(segmentsEntryIds, String::valueOf), ", ");
	}

	@Reference
	private CustomSQL _customSQL;

}
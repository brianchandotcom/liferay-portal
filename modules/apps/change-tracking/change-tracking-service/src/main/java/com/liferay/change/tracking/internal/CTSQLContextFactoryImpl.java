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

package com.liferay.change.tracking.internal;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.change.tracking.sql.CTSQLContextFactory;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = CTSQLContextFactory.class)
public class CTSQLContextFactoryImpl implements CTSQLContextFactory {

	@Override
	public CTSQLContext createCTSQLContext(
		long ctCollectionId, String tableName, String primaryColumnName,
		Class<?> clazz) {

		long classNameId = _classNameLocalService.getClassNameId(clazz);

		List<CTEntry> ctEntries = _ctEntryLocalService.getCTEntries(
			ctCollectionId, classNameId);

		if (ctEntries.isEmpty()) {
			return new CTSQLContextImpl(Collections.emptyList(), false, false);
		}

		List<Long> excludePKs = new ArrayList<>();

		boolean added = false;

		boolean modified = false;

		for (CTEntry ctEntry : ctEntries) {
			int changeType = ctEntry.getChangeType();

			if (changeType == CTConstants.CT_CHANGE_TYPE_ADDITION) {
				added = true;
			}
			else {
				excludePKs.add(ctEntry.getModelClassPK());

				if (changeType == CTConstants.CT_CHANGE_TYPE_MODIFICATION) {
					modified = true;
				}
			}
		}

		CTService<?> ctService = _ctServiceRegistry.getCTService(classNameId);

		if (ctService == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No CTService found for classNameId " + classNameId);
			}

			return new CTSQLContextImpl(excludePKs, added, modified);
		}

		CTPersistence<?> ctPersistence = ctService.getCTPersistence();

		List<String[]> uniqueIndexColumnNames =
			ctPersistence.getUniqueIndexColumnNames();

		if (uniqueIndexColumnNames.isEmpty()) {
			return new CTSQLContextImpl(excludePKs, added, modified);
		}

		Session session = ctPersistence.getCurrentSession();

		org.hibernate.Session wrappedSession =
			(org.hibernate.Session)session.getWrappedSession();

		boolean[] modifiedMarker = new boolean[1];

		wrappedSession.doWork(
			connection -> {
				for (String[] columnNames : uniqueIndexColumnNames) {
					StringBundler sb = new StringBundler(
						4 * columnNames.length + 13);

					sb.append("select ct1.");
					sb.append(primaryColumnName);
					sb.append(" from ");
					sb.append(tableName);
					sb.append(" ct1 inner join ");
					sb.append(tableName);
					sb.append(" ct2 on ct1.");
					sb.append(primaryColumnName);
					sb.append(" != ct2.");
					sb.append(primaryColumnName);
					sb.append(" and ct1.ctCollectionId = 0 and ");
					sb.append("ct2.ctCollectionId = ");
					sb.append(ctCollectionId);

					for (String column : columnNames) {
						sb.append(" and ct1.");
						sb.append(column);
						sb.append(" = ct2.");
						sb.append(column);
					}

					try (PreparedStatement ps = connection.prepareStatement(
							sb.toString());
						ResultSet rs = ps.executeQuery()) {

						while (rs.next()) {
							excludePKs.add(rs.getLong(1));

							modifiedMarker[0] = true;
						}
					}
				}
			});

		return new CTSQLContextImpl(
			excludePKs, added, modified || modifiedMarker[0]);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTSQLContextFactoryImpl.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private CTServiceRegistry _ctServiceRegistry;

	private static class CTSQLContextImpl implements CTSQLContext {

		@Override
		public List<Long> getExcludePKs() {
			return _excludePKs;
		}

		@Override
		public boolean hasAdded() {
			return _added;
		}

		@Override
		public boolean hasModified() {
			return _modified;
		}

		private CTSQLContextImpl(
			List<Long> excludePKs, boolean added, boolean modified) {

			_excludePKs = excludePKs;
			_added = added;
			_modified = modified;
		}

		private final boolean _added;
		private final List<Long> _excludePKs;
		private final boolean _modified;

	}

}
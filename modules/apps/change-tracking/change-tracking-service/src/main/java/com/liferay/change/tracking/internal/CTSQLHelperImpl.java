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
import com.liferay.portal.change.tracking.sql.CTSQLHelper;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = CTSQLHelper.class)
public class CTSQLHelperImpl implements CTSQLHelper {

	@Override
	public boolean visitExcludes(
		long ctCollectionId, String tableName, String primaryColumnName,
		long classNameId, ExcludeVisitor excludeVisitor) {

		if (ctCollectionId < 0) {
			return true;
		}

		List<CTEntry> ctEntries = _ctEntryLocalService.getCTEntries(
			ctCollectionId, classNameId);

		if (ctEntries.isEmpty()) {
			return false;
		}

		boolean added = false;

		for (CTEntry ctEntry : ctEntries) {
			int changeType = ctEntry.getChangeType();

			if (changeType == CTConstants.CT_CHANGE_TYPE_ADDITION) {
				added = true;
			}
			else if (changeType == CTConstants.CT_CHANGE_TYPE_DELETION) {
				excludeVisitor.acceptExclude(
					ctEntry.getModelClassPK(), ExcludeType.DELETE);
			}
			else {
				excludeVisitor.acceptExclude(
					ctEntry.getModelClassPK(), ExcludeType.MODIFY);
			}
		}

		CTService<?> ctService = _ctServiceRegistry.getCTService(classNameId);

		if (ctService == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No CTService found for classNameId " + classNameId);
			}

			return added;
		}

		CTPersistence<?> ctPersistence = ctService.getCTPersistence();

		List<String[]> uniqueIndexColumnNames =
			ctPersistence.getUniqueIndexColumnNames();

		if (uniqueIndexColumnNames.isEmpty()) {
			return added;
		}

		Session session = ctPersistence.getCurrentSession();

		org.hibernate.Session wrappedSession =
			(org.hibernate.Session)session.getWrappedSession();

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
							excludeVisitor.acceptExclude(
								rs.getLong(1), ExcludeType.CONFLICT);
						}
					}
				}
			});

		return added;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTSQLHelperImpl.class);

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private CTServiceRegistry _ctServiceRegistry;

}
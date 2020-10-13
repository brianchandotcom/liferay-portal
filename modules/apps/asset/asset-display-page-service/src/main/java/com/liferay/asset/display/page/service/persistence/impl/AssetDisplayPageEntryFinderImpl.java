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

package com.liferay.asset.display.page.service.persistence.impl;

import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.model.impl.AssetDisplayPageEntryImpl;
import com.liferay.asset.display.page.service.persistence.AssetDisplayPageEntryFinder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(service = AssetDisplayPageEntryFinder.class)
public class AssetDisplayPageEntryFinderImpl
	extends AssetDisplayPageEntryFinderBaseImpl
	implements AssetDisplayPageEntryFinder {

	public static final String COUNT_BY_CNI_DT_LPTEI =
		AssetDisplayPageEntryFinder.class.getName() + ".countByCNI_DT_LPTEI";

	public static final String FIND_BY_CNI_DT_LPTEI =
		AssetDisplayPageEntryFinder.class.getName() + ".findByCNI_DT_LPTEI";

	public static final String JOIN_BY_ASSET_ENTRY =
		AssetDisplayPageEntryFinder.class.getName() + ".joinByAssetEntry";

	@Override
	public int countByCNI_DT_LPTEI(
		long classNameId, long classTypeId, boolean defaultTemplate,
		long layoutPageTemplateEntryId) {

		QueryDefinition<AssetDisplayPageEntry> queryDefinition =
			new QueryDefinition<>();

		return doCountByCNI_DT_LPTEI(
			classNameId, classTypeId, defaultTemplate,
			layoutPageTemplateEntryId, queryDefinition);
	}

	@Override
	public List<AssetDisplayPageEntry> findByCNI_DT_LPTEI(
		long classNameId, long classTypeId, boolean defaultTemplate,
		long layoutPageTemplateEntryId, int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		QueryDefinition<AssetDisplayPageEntry> queryDefinition =
			new QueryDefinition<>(
				WorkflowConstants.STATUS_ANY, start, end, orderByComparator);

		return doFindByCNI_DT_LPTEI(
			classNameId, classTypeId, defaultTemplate,
			layoutPageTemplateEntryId, queryDefinition);
	}

	protected int doCountByCNI_DT_LPTEI(
		long classNameId, long classTypeId, boolean defaultTemplate,
		long layoutPageTemplateEntryId,
		QueryDefinition<AssetDisplayPageEntry> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), COUNT_BY_CNI_DT_LPTEI, queryDefinition,
				"AssetDisplayPageEntry");

			sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(classTypeId));

			sql = StringUtil.replace(
				sql, "[$CLASS_TYPE_ID$]", getClassTypeId(classTypeId));

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(classNameId);

			if (defaultTemplate) {
				queryPos.add(1);
			}
			else {
				queryPos.add(0);
			}

			queryPos.add(layoutPageTemplateEntryId);

			if (classTypeId > 0) {
				queryPos.add(classTypeId);
			}

			Iterator<Long> iterator = sqlQuery.iterate();

			if (iterator.hasNext()) {
				Long count = iterator.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<AssetDisplayPageEntry> doFindByCNI_DT_LPTEI(
		long classNameId, long classTypeId, boolean defaultTemplate,
		long layoutPageTemplateEntryId,
		QueryDefinition<AssetDisplayPageEntry> queryDefinition) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_BY_CNI_DT_LPTEI, queryDefinition,
				"AssetDisplayPageEntry");

			sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(classTypeId));

			sql = StringUtil.replace(
				sql, "[$CLASS_TYPE_ID$]", getClassTypeId(classTypeId));

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				AssetDisplayPageEntryImpl.TABLE_NAME,
				AssetDisplayPageEntryImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(classNameId);

			if (defaultTemplate) {
				queryPos.add(1);
			}
			else {
				queryPos.add(0);
			}

			queryPos.add(layoutPageTemplateEntryId);

			if (classTypeId > 0) {
				queryPos.add(classTypeId);
			}

			return (List<AssetDisplayPageEntry>)QueryUtil.list(
				sqlQuery, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getClassTypeId(long classTypeId) {
		if (classTypeId > 0) {
			return "AND ((AssetEntry.classTypeId = ?))";
		}

		return StringPool.BLANK;
	}

	protected String getJoin(long classTypeId) {
		if (classTypeId > 0) {
			return _customSQL.get(getClass(), JOIN_BY_ASSET_ENTRY);
		}

		return StringPool.BLANK;
	}

	@Reference
	private CustomSQL _customSQL;

}
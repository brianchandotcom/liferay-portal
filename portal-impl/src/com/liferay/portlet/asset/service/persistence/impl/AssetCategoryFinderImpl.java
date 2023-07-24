/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.service.persistence.impl;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.service.persistence.AssetCategoryFinder;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.asset.model.impl.AssetCategoryImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Jorge Ferrer
 * @author Shuyang Zhou
 */
public class AssetCategoryFinderImpl
	extends AssetCategoryFinderBaseImpl implements AssetCategoryFinder {

	public static final String COUNT_BY_CC =
		AssetCategoryFinder.class.getName() + ".countByC_C";

	public static final String FIND_BY_C_C =
		AssetCategoryFinder.class.getName() + ".findByC_C";

	public static final String FIND_BY_G_N_P =
		AssetCategoryFinder.class.getName() + ".findByG_N_P";

	/**
	 * Returns the number of assetCategories related to an AssetEntry with the
	 * given "classNameId-classPK". This method also checks inline permissions.
	 *
	 * @param  classNameId the className of the asset
	 * @param  classPK the classPK of the asset
	 * @return the number of matching assetCategories
	 */
	@Override
	public int filterCountByC_C(long classNameId, long classPK) {
		return doCountByC_C(classNameId, classPK, true);
	}

	/**
	 * Returns a range of assetCategories related to an AssetEntry with the
	 * given "classNameId-classPK". This method also checks inline permissions.
	 *
	 * @param  classNameId the className of the asset
	 * @param  classPK the classPK of the asset
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the matching assetCategories
	 */
	@Override
	public List<AssetCategory> filterFindByC_C(
		long classNameId, long classPK, int start, int end) {

		return doFindByC_C(classNameId, classPK, start, end, true);
	}

	@Override
	public List<AssetCategory> findByG_N_P(
		long groupId, String name, String[] categoryProperties, int start,
		int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_N_P);

			sql = StringUtil.replace(
				sql, "[$JOIN$]", getJoin(categoryProperties));

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity("AssetCategory", AssetCategoryImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			setJoin(queryPos, categoryProperties);

			queryPos.add(groupId);
			queryPos.add(name);
			queryPos.add(name);

			return (List<AssetCategory>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected int doCountByC_C(
		long classNameId, long classPK, boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_CC);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, AssetCategory.class.getName(),
					"AssetCategory.categoryId");
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(classNameId);
			queryPos.add(classPK);

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

	protected List<AssetCategory> doFindByC_C(
		long classNameId, long classPK, int start, int end,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_C);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, AssetCategory.class.getName(),
					"AssetCategory.categoryId");
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity("AssetCategory", AssetCategoryImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(classNameId);
			queryPos.add(classPK);

			return (List<AssetCategory>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getJoin(String[] categoryProperties) {
		if (categoryProperties.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(
			(categoryProperties.length * 3) + 2);

		sb.append(" INNER JOIN AssetCategoryProperty ON ");
		sb.append("(AssetCategoryProperty.categoryId = ");
		sb.append("AssetCategory.categoryId) AND ");

		for (int i = 0; i < categoryProperties.length; i++) {
			sb.append("(AssetCategoryProperty.key_ = ? AND ");
			sb.append("AssetCategoryProperty.value = ?) ");

			if ((i + 1) < categoryProperties.length) {
				sb.append(" AND ");
			}
		}

		return sb.toString();
	}

	protected void setJoin(QueryPos queryPos, String[] categoryProperties) {
		for (String categoryProperty : categoryProperties) {
			String[] categoryPropertyArray = StringUtil.split(
				categoryProperty,
				AssetCategoryConstants.PROPERTY_KEY_VALUE_SEPARATOR);

			if (categoryPropertyArray.length <= 1) {
				categoryPropertyArray = StringUtil.split(
					categoryProperty, CharPool.COLON);
			}

			String key = StringPool.BLANK;

			if (categoryPropertyArray.length > 0) {
				key = GetterUtil.getString(categoryPropertyArray[0]);
			}

			String value = StringPool.BLANK;

			if (categoryPropertyArray.length > 1) {
				value = GetterUtil.getString(categoryPropertyArray[1]);
			}

			queryPos.add(key);
			queryPos.add(value);
		}
	}

}
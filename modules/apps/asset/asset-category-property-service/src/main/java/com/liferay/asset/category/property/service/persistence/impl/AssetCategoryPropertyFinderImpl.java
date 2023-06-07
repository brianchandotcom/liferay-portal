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

package com.liferay.asset.category.property.service.persistence.impl;

import com.liferay.asset.category.property.model.AssetCategoryProperty;
import com.liferay.asset.category.property.model.impl.AssetCategoryPropertyImpl;
import com.liferay.asset.category.property.service.persistence.AssetCategoryPropertyFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
@Component(service = AssetCategoryPropertyFinder.class)
public class AssetCategoryPropertyFinderImpl
	extends AssetCategoryPropertyFinderBaseImpl
	implements AssetCategoryPropertyFinder {

	public static final String FIND_BY_G_K =
		AssetCategoryPropertyFinder.class.getName() + ".findByG_K";

	@Override
	public List<AssetCategoryProperty> findByG_K(long groupId, String key) {
		return findByG_K(groupId, key, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public List<AssetCategoryProperty> findByG_K(
		long groupId, String key, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_G_K);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar("categoryPropertyValue", Type.STRING);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);
			queryPos.add(key);

			List<AssetCategoryProperty> categoryProperties = new ArrayList<>();

			Iterator<String> iterator = (Iterator<String>)QueryUtil.iterate(
				sqlQuery, getDialect(), start, end);

			while (iterator.hasNext()) {
				String value = iterator.next();

				AssetCategoryProperty categoryProperty =
					new AssetCategoryPropertyImpl();

				categoryProperty.setKey(key);
				categoryProperty.setValue(value);

				categoryProperties.add(categoryProperty);
			}

			return categoryProperties;
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
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

package com.liferay.asset.list.service.persistence.impl;

import com.liferay.asset.list.service.persistence.AssetListEntryAssetEntryRelFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier de Arcos
 */
@Component(service = AssetListEntryAssetEntryRelFinder.class)
public class AssetListEntryAssetEntryRelFinderImpl
	extends AssetListEntryAssetEntryRelFinderBaseImpl
	implements AssetListEntryAssetEntryRelFinder {

	public static final String COUNT_BY_A_S_VISIBLE =
		AssetListEntryAssetEntryRelFinder.class.getName() +
			".countByA_S_Visible";

	@Override
	public int countByA_S_Visible(long assetListEntryId, long segmentsEntryId) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_A_S_VISIBLE);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(assetListEntryId);
			qPos.add(segmentsEntryId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
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
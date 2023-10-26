/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.query;

import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.search.Query;

/**
 * @author Michael C. Han
 */
public class FieldQueryFactoryUtil {

	public static Query createQuery(
		String field, String value, boolean like, boolean splitKeywords) {

		FieldQueryFactory fieldQueryFactory = _fieldQueryFactorySnapshot.get();

		return fieldQueryFactory.createQuery(field, value, like, splitKeywords);
	}

	private static final Snapshot<FieldQueryFactory>
		_fieldQueryFactorySnapshot = new Snapshot<>(
			FieldQueryFactoryUtil.class, FieldQueryFactory.class);

}
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.service.persistence.impl;

import com.liferay.asset.kernel.model.AssetVocabularyGroupRelTable;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portlet.asset.model.impl.AssetVocabularyGroupRelImpl;
import com.liferay.portlet.asset.model.impl.AssetVocabularyGroupRelModelImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The arguments resolver class for retrieving value from AssetVocabularyGroupRel.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@OSGiBeanProperties(
	property = {
		"class.name=com.liferay.portlet.asset.model.impl.AssetVocabularyGroupRelImpl",
		"table.name=AssetVocabularyGroupRel"
	},
	service = ArgumentsResolver.class
)
public class AssetVocabularyGroupRelModelArgumentsResolver
	implements ArgumentsResolver {

	@Override
	public Object[] getArguments(
		FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
		boolean original) {

		String[] columnNames = finderPath.getColumnNames();

		if ((columnNames == null) || (columnNames.length == 0)) {
			if (baseModel.isNew()) {
				return new Object[0];
			}

			return null;
		}

		AssetVocabularyGroupRelModelImpl assetVocabularyGroupRelModelImpl =
			(AssetVocabularyGroupRelModelImpl)baseModel;

		long columnBitmask =
			assetVocabularyGroupRelModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(
				assetVocabularyGroupRelModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					assetVocabularyGroupRelModelImpl.getColumnBitmask(
						columnName);
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(
				assetVocabularyGroupRelModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return AssetVocabularyGroupRelImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return AssetVocabularyGroupRelTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		AssetVocabularyGroupRelModelImpl assetVocabularyGroupRelModelImpl,
		String[] columnNames, boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] =
					assetVocabularyGroupRelModelImpl.getColumnOriginalValue(
						columnName);
			}
			else {
				arguments[i] = assetVocabularyGroupRelModelImpl.getColumnValue(
					columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

}
// LIFERAY-SERVICE-BUILDER-HASH:310316851
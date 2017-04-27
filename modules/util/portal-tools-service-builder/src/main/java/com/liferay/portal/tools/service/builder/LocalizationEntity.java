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

package com.liferay.portal.tools.service.builder;

import com.liferay.portal.kernel.util.StringPool;

import java.util.Collections;
import java.util.List;

/**
 * @author Preston Crary
 */
public class LocalizationEntity extends Entity {

	public LocalizationEntity(
		String packagePath, String apiPackagePath, String portletName,
		String portletShortName, String name, String humanName, String table,
		String alias, String persistenceClass, String dataSource,
		String sessionFactory, String txManager, boolean cacheEnabled,
		boolean dynamicUpdateEnabled, boolean deprecated,
		List<EntityColumn> pkList, List<EntityColumn> regularColList,
		List<EntityColumn> blobList, List<EntityColumn> collectionList,
		List<EntityColumn> columnList, List<EntityFinder> finderList,
		List<Entity> referenceList, List<String> txRequiredList) {

		super(
			packagePath, apiPackagePath, portletName, portletShortName, name,
			humanName, table, alias, false, false, false, false,
			persistenceClass, StringPool.BLANK, dataSource, sessionFactory,
			txManager, cacheEnabled, dynamicUpdateEnabled, false, true, false,
			deprecated, pkList, regularColList, blobList, collectionList,
			columnList, Collections.<LocalizationColumn>emptyList(), null,
			finderList, referenceList, Collections.<String>emptyList(),
			txRequiredList, false);
	}

	public String getLocalizationFinderName() {
		return _localizationFinderName;
	}

	public String getPrimaryKeyMethodName() {
		return _primaryKeyMethodName;
	}

	public void setLocalizationFinderName(String localizationFinderName) {
		_localizationFinderName = localizationFinderName;
	}

	public void setPrimaryKeyMethodName(String primaryKeyMethodName) {
		_primaryKeyMethodName = primaryKeyMethodName;
	}

	private String _localizationFinderName;
	private String _primaryKeyMethodName;

}
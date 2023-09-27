/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.related.models;

/**
 * @author Luis Miguel Barcos
 */
public interface ObjectRelatedModelsPredicateProviderRegistry {

	public ObjectRelatedModelsPredicateProvider
		getObjectRelatedModelsPredicateProvider(String className, String type);

}
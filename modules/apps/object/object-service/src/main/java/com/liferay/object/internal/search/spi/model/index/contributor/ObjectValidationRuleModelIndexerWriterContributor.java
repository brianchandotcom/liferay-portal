/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.index.contributor;

import com.liferay.object.model.ObjectValidationRule;
import com.liferay.object.service.ObjectValidationRuleLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author Marcela Cunha
 */
public class ObjectValidationRuleModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<ObjectValidationRule> {

	public ObjectValidationRuleModelIndexerWriterContributor(
		ObjectValidationRuleLocalService objectValidationRuleLocalService) {

		_objectValidationRuleLocalService = objectValidationRuleLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _objectValidationRuleLocalService.
			getIndexableActionableDynamicQuery();
	}

	private final ObjectValidationRuleLocalService
		_objectValidationRuleLocalService;

}
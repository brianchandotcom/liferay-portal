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

package com.liferay.object.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectEntryModelPreFilterContributor
	implements ModelPreFilterContributor {

	public ObjectEntryModelPreFilterContributor(
		ModelPreFilterContributor workflowStatusModelPreFilterContributor) {

		_workflowStatusModelPreFilterContributor =
			workflowStatusModelPreFilterContributor;
	}

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		long objectDefinitionId = GetterUtil.getLong(
			searchContext.getAttribute("objectDefinitionId"));

		if (_log.isDebugEnabled()) {
			_log.debug("Object definition ID " + objectDefinitionId);
		}

		if (objectDefinitionId > 0) {
			booleanFilter.addRequiredTerm(
				"objectDefinitionId", objectDefinitionId);
		}

		_workflowStatusModelPreFilterContributor.contribute(
			booleanFilter, modelSearchSettings, searchContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryModelPreFilterContributor.class);

	private final ModelPreFilterContributor
		_workflowStatusModelPreFilterContributor;

}
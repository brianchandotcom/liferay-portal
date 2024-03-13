/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.entries.frontend.data.set.filter.factory;

import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.object.field.filter.parser.ObjectFieldFilterContext;
import com.liferay.object.field.filter.parser.ObjectFieldFilterContributor;
import com.liferay.object.field.filter.parser.ObjectFieldFilterContributorRegistry;
import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

/**
 * @author Feliphe Marinho
 */
public class ListTypeEntryObjectFieldFDSFilterFactory
	implements ObjectFieldFDSFilterFactory {

	public ListTypeEntryObjectFieldFDSFilterFactory(
		ObjectFieldFilterContributorRegistry
			objectFieldFilterContributorRegistry) {

		_objectFieldFilterContributorRegistry =
			objectFieldFilterContributorRegistry;
	}

	@Override
	public FDSFilter create(
			Locale locale, long objectDefinitionId,
			ObjectViewFilterColumn objectViewFilterColumn)
		throws PortalException {

		ObjectFieldFilterContributor objectFieldFilterContributor =
			_objectFieldFilterContributorRegistry.
				getObjectFieldFilterContributor(
					new ObjectFieldFilterContext(
						locale, objectDefinitionId, objectViewFilterColumn));

		return objectFieldFilterContributor.getFDSFilter();
	}

	private final ObjectFieldFilterContributorRegistry
		_objectFieldFilterContributorRegistry;

}
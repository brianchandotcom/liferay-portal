/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.internal.odata.entity.v1_0;

import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.StringEntityField;

import java.util.Map;

/**
 * Declares the fields a site preview can be sorted on.
 *
 * <p>
 * Vulcan resolves the <code>sort</code> query parameter into a
 * <code>Sort[]</code> only for a resource that answers with an entity model, so
 * a resource without one silently ignores every sort it is given.
 * </p>
 *
 * @author Petteri Karttunen
 */
public class PreviewSiteEntityModel implements EntityModel {

	public PreviewSiteEntityModel() {
		_entityFieldsMap = EntityModel.toEntityFieldsMap(
			new StringEntityField(
				"descriptiveName", locale -> "descriptiveName"));
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	private final Map<String, EntityField> _entityFieldsMap;

}
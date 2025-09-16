/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.internal.odata.entity.v1_0;

import com.liferay.portal.odata.entity.BooleanEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;

import java.util.Map;

/**
 * @author Luca Pellizzon
 */
public class BulkActionEntityModel implements EntityModel {

	public BulkActionEntityModel() {
		_entityFieldsMap = EntityModel.toEntityFieldsMap(
			new BooleanEntityField("cmsRoot", locale -> "cms_root"),
			new IntegerEntityField("folderId", locale -> "folderId"),
			new IntegerEntityField("status", locale -> "status"),
			new StringEntityField("cmsKind", locale -> "cms_kind"),
			new StringEntityField("cmsSection", locale -> "cms_section"),
			new StringEntityField("name", locale -> "name"));
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	private final Map<String, EntityField> _entityFieldsMap;

}
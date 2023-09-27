/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.odata.entity.v1_0;

import com.liferay.headless.common.spi.odata.entity.EntityFieldsMapFactory;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IdEntityField;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;

import java.util.Map;

/**
 * @author Sarai Díaz
 */
public class BlogPostingImageEntityModel implements EntityModel {

	public BlogPostingImageEntityModel() {
		_entityFieldsMap = EntityFieldsMapFactory.create(
			new IdEntityField(
				"encodingFormat",
				locale -> Field.getSortableFieldName("mimeType_String"),
				mimeType -> {
					String encodingFormat = String.valueOf(mimeType);

					return StringUtil.replace(
						encodingFormat, CharPool.SLASH, CharPool.UNDERLINE);
				}),
			new IntegerEntityField(
				"sizeInBytes", locale -> Field.getSortableFieldName("size")),
			new StringEntityField(
				"fileExtension",
				locale -> Field.getSortableFieldName("extension_String")),
			new StringEntityField(
				"title",
				locale -> Field.getSortableFieldName(
					"localized_title_".concat(LocaleUtil.toLanguageId(locale))),
				locale -> {
					String sortableFieldName = Field.getSortableFieldName(
						"localized_title_".concat(
							LocaleUtil.toLanguageId(locale)));

					return sortableFieldName.concat(".keyword_lowercase");
				}));
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	private final Map<String, EntityField> _entityFieldsMap;

}
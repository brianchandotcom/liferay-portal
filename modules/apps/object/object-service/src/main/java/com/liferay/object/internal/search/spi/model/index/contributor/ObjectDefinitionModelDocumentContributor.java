/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.index.contributor;

import com.liferay.object.constants.ObjectDefinitionSettingConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectDefinitionSetting;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carolina Barbosa
 */
@Component(
	property = "indexer.class.name=com.liferay.object.model.ObjectDefinition",
	service = ModelDocumentContributor.class
)
public class ObjectDefinitionModelDocumentContributor
	implements ModelDocumentContributor<ObjectDefinition> {

	@Override
	public void contribute(
		Document document, ObjectDefinition objectDefinition) {

		document.addKeyword(Field.HIDDEN, !objectDefinition.isVisible());
		document.addText(Field.NAME, objectDefinition.getShortName());
		document.addKeyword(Field.STATUS, objectDefinition.getStatus());
		document.addKeyword(
			ObjectDefinitionSettingConstants.NAME_ACCEPTED_GROUP_IDS,
			_getAcceptedGroupIds(
				objectDefinition.getObjectDefinitionSettings()));
		document.addLocalizedKeyword(
			"localized_label", objectDefinition.getLabelMap(), true, true);
		document.addKeyword("modifiable", objectDefinition.isModifiable());
		document.addKeyword(
			"objectDefinitionId", objectDefinition.getObjectDefinitionId());
		document.addKeyword(
			"objectFolderExternalReferenceCode",
			objectDefinition.getObjectFolderExternalReferenceCode(), true);
		document.addKeyword(
			"rootObjectDefinitionExternalReferenceCode",
			objectDefinition.getRootObjectDefinitionExternalReferenceCode(),
			true);

		document.remove(Field.USER_NAME);
	}

	private long[] _getAcceptedGroupIds(
		List<ObjectDefinitionSetting> objectDefinitionSettings) {

		if ((objectDefinitionSettings != null) &&
			!objectDefinitionSettings.isEmpty()) {

			for (ObjectDefinitionSetting objectDefinitionSetting :
					objectDefinitionSettings) {

				if (StringUtil.equals(
						objectDefinitionSetting.getName(),
						ObjectDefinitionSettingConstants.
							NAME_ACCEPTED_GROUP_IDS)) {

					return StringUtil.split(
						objectDefinitionSetting.getValue(), 0L);
				}
			}
		}

		return null;
	}

}
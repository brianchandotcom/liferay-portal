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

package com.liferay.journal.internal.upgrade.v4_0_0;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFieldLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.journal.internal.upgrade.v4_0_0.util.JournalArticleTable;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.util.JournalConverter;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Portal;

/**
 * @author Preston Crary
 */
public class JournalArticleDDMFieldsUpgradeProcess extends UpgradeProcess {

	public JournalArticleDDMFieldsUpgradeProcess(
		ClassNameLocalService classNameLocalService,
		DDMFieldLocalService ddmFieldLocalService,
		DDMStructureLocalService ddmStructureLocalService,
		FieldsToDDMFormValuesConverter fieldsToDDMFormValuesConverter,
		JournalConverter journalConverter, Portal portal) {

		_classNameLocalService = classNameLocalService;
		_ddmFieldLocalService = ddmFieldLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_fieldsToDDMFormValuesConverter = fieldsToDDMFormValuesConverter;
		_journalConverter = journalConverter;
		_portal = portal;
	}

	@Override
	protected void doUpgrade() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(
			JournalArticle.class);

		processConcurrently(
			"select id_, groupId, content, DDMStructureKey from " +
				"JournalArticle where ctCollectionId = 0",
			resultSet -> new Object[] {
				resultSet.getLong("id_"), resultSet.getLong("groupId"),
				resultSet.getString("content"),
				resultSet.getString("DDMStructureKey")
			},
			values -> {
				long id = (Long)values[0];
				long groupId = (Long)values[1];

				String content = (String)values[2];

				String ddmStructureKey = (String)values[3];

				DDMStructure ddmStructure =
					_ddmStructureLocalService.getStructure(
						_portal.getSiteGroupId(groupId), classNameId,
						ddmStructureKey, true);

				DDMFormValues ddmFormValues =
					_fieldsToDDMFormValuesConverter.convert(
						ddmStructure,
						_journalConverter.getDDMFields(ddmStructure, content));

				_ddmFieldLocalService.updateDDMFormValues(
					ddmStructure.getStructureId(), id, ddmFormValues);
			},
			null);

		alter(JournalArticleTable.class, new AlterTableDropColumn("content"));
	}

	private final ClassNameLocalService _classNameLocalService;
	private final DDMFieldLocalService _ddmFieldLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final FieldsToDDMFormValuesConverter
		_fieldsToDDMFormValuesConverter;
	private final JournalConverter _journalConverter;
	private final Portal _portal;

}
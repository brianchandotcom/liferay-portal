/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.internal.search;

import com.liferay.dynamic.data.lists.internal.search.spi.model.index.contributor.DDLRecordModelIndexerWriterContributor;
import com.liferay.dynamic.data.lists.internal.search.spi.model.result.contributor.DDLRecordModelSummaryContributor;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(service = ModelSearchConfigurator.class)
public class DDLRecordModelSearchConfigurator
	implements ModelSearchConfigurator<DDLRecord> {

	@Override
	public String getClassName() {
		return DDLRecord.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.UID
		};
	}

	@Override
	public String[] getDefaultSelectedLocalizedFieldNames() {
		return new String[] {Field.DESCRIPTION, Field.TITLE};
	}

	@Override
	public ModelIndexerWriterContributor<DDLRecord>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public ModelSummaryContributor getModelSummaryContributor() {
		return _modelSummaryContributor;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new DDLRecordModelIndexerWriterContributor(
				_ddlRecordLocalService, _ddlRecordSetLocalService,
				_ddlRecordVersionLocalService);
		_modelSummaryContributor = new DDLRecordModelSummaryContributor(
			_ddlRecordSetLocalService, _language);
	}

	@Reference
	private DDLRecordLocalService _ddlRecordLocalService;

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDLRecordVersionLocalService _ddlRecordVersionLocalService;

	@Reference
	private Language _language;

	private ModelIndexerWriterContributor<DDLRecord>
		_modelIndexWriterContributor;
	private ModelSummaryContributor _modelSummaryContributor;

}
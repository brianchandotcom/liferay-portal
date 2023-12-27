/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.search;

import com.liferay.message.boards.internal.search.spi.model.index.contributor.MBMessageModelIndexerWriterContributor;
import com.liferay.message.boards.internal.search.spi.model.result.contributor.MBMessageModelSummaryContributor;
import com.liferay.message.boards.internal.search.spi.model.result.contributor.MBMessageModelVisibilityContributor;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.liferay.portal.search.spi.model.result.contributor.ModelVisibilityContributor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luan Maoski
 */
@Component(service = ModelSearchConfigurator.class)
public class MBMessageModelSearchConfigurator
	implements ModelSearchConfigurator<MBMessage> {

	@Override
	public String getClassName() {
		return MBMessage.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.ASSET_TAG_NAMES, Field.CLASS_NAME_ID, Field.CLASS_PK,
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.MODIFIED_DATE, Field.SCOPE_GROUP_ID, Field.UID
		};
	}

	@Override
	public String[] getDefaultSelectedLocalizedFieldNames() {
		return new String[] {Field.CONTENT, Field.TITLE};
	}

	@Override
	public ModelIndexerWriterContributor<MBMessage>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public ModelSummaryContributor getModelSummaryContributor() {
		return _modelSummaryContributor;
	}

	@Override
	public ModelVisibilityContributor getModelVisibilityContributor() {
		return _modelVisibilityContributor;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new MBMessageModelIndexerWriterContributor(
				_dynamicQueryBatchIndexingActionableFactory,
				_mbMessageLocalService, _mbThreadLocalService);
		_modelSummaryContributor = new MBMessageModelSummaryContributor(
			_localization);
		_modelVisibilityContributor = new MBMessageModelVisibilityContributor(
			_mbMessageLocalService);
	}

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	@Reference
	private Localization _localization;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

	private ModelIndexerWriterContributor<MBMessage>
		_modelIndexWriterContributor;
	private ModelSummaryContributor _modelSummaryContributor;
	private ModelVisibilityContributor _modelVisibilityContributor;

}
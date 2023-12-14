/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.internal.search;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.redirect.internal.search.spi.model.index.contributor.RedirectEntryModelIndexerWriterContributor;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.service.RedirectEntryLocalService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = ModelSearchConfigurator.class)
public class RedirectEntryModelSearchConfigurator
	implements ModelSearchConfigurator<RedirectEntry> {

	@Override
	public String getClassName() {
		return RedirectEntry.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.GROUP_ID, Field.ENTRY_CLASS_NAME,
			Field.ENTRY_CLASS_PK, Field.MODIFIED_DATE, Field.UID
		};
	}

	@Override
	public ModelIndexerWriterContributor<RedirectEntry>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new RedirectEntryModelIndexerWriterContributor(
				_dynamicQueryBatchIndexingActionableFactory,
				_redirectEntryLocalService);
	}

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	private ModelIndexerWriterContributor<RedirectEntry>
		_modelIndexWriterContributor;

	@Reference
	private RedirectEntryLocalService _redirectEntryLocalService;

}
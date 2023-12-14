/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.internal.search;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.redirect.internal.search.spi.model.index.contributor.RedirectNotFoundEntryModelIndexerWriterContributor;
import com.liferay.redirect.model.RedirectNotFoundEntry;
import com.liferay.redirect.service.RedirectNotFoundEntryLocalService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = ModelSearchConfigurator.class)
public class RedirectNotFoundEntryModelSearchConfigurator
	implements ModelSearchConfigurator<RedirectNotFoundEntry> {

	@Override
	public String getClassName() {
		return RedirectNotFoundEntry.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.GROUP_ID, Field.ENTRY_CLASS_NAME,
			Field.ENTRY_CLASS_PK, Field.MODIFIED_DATE, Field.UID
		};
	}

	@Override
	public ModelIndexerWriterContributor<RedirectNotFoundEntry>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new RedirectNotFoundEntryModelIndexerWriterContributor(
				_dynamicQueryBatchIndexingActionableFactory,
				_redirectNotFoundEntryLocalService);
	}

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	private ModelIndexerWriterContributor<RedirectNotFoundEntry>
		_modelIndexWriterContributor;

	@Reference
	private RedirectNotFoundEntryLocalService
		_redirectNotFoundEntryLocalService;

}
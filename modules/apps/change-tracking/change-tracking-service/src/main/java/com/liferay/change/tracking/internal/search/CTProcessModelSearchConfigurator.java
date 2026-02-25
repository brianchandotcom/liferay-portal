/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.search;

import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = ModelSearchConfigurator.class)
public class CTProcessModelSearchConfigurator
	implements ModelSearchConfigurator<CTProcess> {

	@Override
	public String getClassName() {
		return CTProcess.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.UID
		};
	}

	@Override
	public ModelIndexerWriterContributor<CTProcess>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public boolean isSearchResultPermissionFilterSuppressed() {
		return true;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor = new ModelIndexerWriterContributor<>(
			() -> {
				IndexableActionableDynamicQuery
					indexableActionableDynamicQuery =
						_ctProcessLocalService.
							getIndexableActionableDynamicQuery();

				if (!CTCollectionThreadLocal.isProductionMode()) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							StringBundler.concat(
								"Restricting indexable results of ",
								CTProcess.class.getName(), " because this can ",
								"only be performed in production mode"));
					}

					indexableActionableDynamicQuery.setAddCriteriaMethod(
						dynamicQuery -> dynamicQuery.add(
							RestrictionsFactoryUtil.eq("ctCollectionId", -1L)));
				}

				return indexableActionableDynamicQuery;
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTProcessModelSearchConfigurator.class);

	@Reference
	private CTProcessLocalService _ctProcessLocalService;

	private ModelIndexerWriterContributor<CTProcess>
		_modelIndexWriterContributor;

}
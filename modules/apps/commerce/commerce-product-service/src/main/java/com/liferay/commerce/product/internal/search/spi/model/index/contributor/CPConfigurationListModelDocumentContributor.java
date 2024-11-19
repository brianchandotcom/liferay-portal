/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.search.spi.model.index.contributor;

import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.model.CPConfigurationList;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.product.model.CPConfigurationList",
	service = ModelDocumentContributor.class
)
public class CPConfigurationListModelDocumentContributor
	implements ModelDocumentContributor<CPConfigurationList> {

	@Override
	public void contribute(
		Document document, CPConfigurationList cpConfigurationList) {

		try {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Indexing commerce product configuration list " +
						cpConfigurationList);
			}

			document.addKeyword(
				CPField.EXTERNAL_REFERENCE_CODE,
				cpConfigurationList.getExternalReferenceCode());
			document.addKeyword(
				CPField.CP_CONFIGURATION_LIST_ID,
				cpConfigurationList.getCPConfigurationListId());
			document.addText(Field.NAME, cpConfigurationList.getName());
			document.addNumber(
				Field.PRIORITY, cpConfigurationList.getPriority());

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Commerce product configuration list " +
						cpConfigurationList + " indexed successfully");
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to index commerce product configuration list" +
						cpConfigurationList,
					exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPConfigurationListModelDocumentContributor.class);

}
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.search.spi.model.index.contributor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

import org.osgi.service.component.annotations.Component;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = "indexer.class.name=com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion",
	service = ModelDocumentContributor.class
)
public class KaleoDefinitionVersionModelDocumentContributor
	implements ModelDocumentContributor<KaleoDefinitionVersion> {

	@Override
	public void contribute(
		Document document, KaleoDefinitionVersion kaleoDefinitionVersion) {

		document.addKeyword(
			Field.DESCRIPTION, kaleoDefinitionVersion.getDescription());
		document.addKeyword(Field.NAME, kaleoDefinitionVersion.getName());

		try {
			KaleoDefinition kaleoDefinition =
				kaleoDefinitionVersion.getKaleoDefinition();

			document.addNumber("active", kaleoDefinition.isActive() ? 1 : 0);
			document.addKeyword("scope", kaleoDefinition.getScope());
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}

		document.addLocalizedText(
			Field.TITLE, kaleoDefinitionVersion.getTitleMap(), true);

		int[] versionParts = StringUtil.split(
			kaleoDefinitionVersion.getVersion(), StringPool.PERIOD, 0);

		document.addNumber(Field.VERSION, versionParts[0]);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoDefinitionVersionModelDocumentContributor.class);

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.portal.workflow.kaleo.internal.search.KaleoInstanceTokenField;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author István András Dézsi
 */
@Component(
	property = "indexer.class.name=com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken",
	service = ModelDocumentContributor.class
)
public class KaleoInstanceTokenModelDocumentContributor
	extends BaseKaleoModelDocumentContributor
	implements ModelDocumentContributor<KaleoInstanceToken> {

	@Override
	public void contribute(
		Document document, KaleoInstanceToken kaleoInstanceToken) {

		document.addKeyword(
			KaleoInstanceTokenField.CLASS_NAME,
			kaleoInstanceToken.getClassName());
		document.addKeyword(
			Field.CLASS_NAME_ID,
			portal.getClassNameId(kaleoInstanceToken.getClassName()));
		document.addKeyword(Field.CLASS_PK, kaleoInstanceToken.getClassPK());
		document.addKeywordSortable(
			KaleoInstanceTokenField.COMPLETED,
			kaleoInstanceToken.isCompleted());
		document.addDateSortable(
			KaleoInstanceTokenField.COMPLETION_DATE,
			kaleoInstanceToken.getCompletionDate());
		document.addDateSortable(
			Field.CREATE_DATE, kaleoInstanceToken.getCreateDate());
		document.addKeywordSortable(
			KaleoInstanceTokenField.CURRENT_KALEO_NODE_NAME,
			kaleoInstanceToken.getCurrentKaleoNodeName());
		document.addNumberSortable(
			KaleoInstanceTokenField.KALEO_INSTANCE_ID,
			kaleoInstanceToken.getKaleoInstanceId());
		document.addKeyword(
			KaleoInstanceTokenField.KALEO_INSTANCE_TOKEN_ID,
			kaleoInstanceToken.getKaleoInstanceTokenId());
		document.addDateSortable(
			Field.MODIFIED_DATE, kaleoInstanceToken.getModifiedDate());
		document.addKeyword(
			KaleoInstanceTokenField.PARENT_KALEO_INSTANCE_TOKEN_ID,
			kaleoInstanceToken.getParentKaleoInstanceTokenId());

		try {
			KaleoInstance kaleoInstance =
				kaleoInstanceLocalService.getKaleoInstance(
					kaleoInstanceToken.getKaleoInstanceId());

			document.addKeyword(
				KaleoInstanceTokenField.KALEO_DEFINITION_NAME,
				kaleoInstance.getKaleoDefinitionName());
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}

		addAssetEntryAttributes(
			kaleoInstanceToken.getClassName(), kaleoInstanceToken.getClassPK(),
			document, kaleoInstanceToken.getGroupId());
	}

	protected String[] getLanguageIds(
		String defaultLanguageId, String content) {

		String[] languageIds = _localization.getAvailableLanguageIds(content);

		if (languageIds.length == 0) {
			languageIds = new String[] {defaultLanguageId};
		}

		return languageIds;
	}

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	protected KaleoInstanceLocalService kaleoInstanceLocalService;

	@Reference
	protected Portal portal;

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoInstanceTokenModelDocumentContributor.class);

	@Reference
	private Localization _localization;

}
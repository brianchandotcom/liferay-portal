/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.content.web.internal.portlet;

import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.DisplayInformationProvider;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.Validator;

import jakarta.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "jakarta.portlet.name=" + JournalContentPortletKeys.JOURNAL_CONTENT,
	service = DisplayInformationProvider.class
)
public class JournalContentDisplayInformationProvider
	implements DisplayInformationProvider {

	@Override
	public String getClassName() {
		return JournalArticle.class.getName();
	}

	@Override
	public String getClassPK(
		PortletPreferences portletPreferences, long scopeGroupId) {

		String articleExternalReferenceCode = portletPreferences.getValue(
			"articleExternalReferenceCode", StringPool.BLANK);

		if (Validator.isNull(articleExternalReferenceCode)) {
			return StringPool.BLANK;
		}

		JournalArticle article =
			_journalArticleLocalService.
				fetchLatestArticleByExternalReferenceCode(
					_getGroupId(portletPreferences, scopeGroupId),
					articleExternalReferenceCode);

		if (article == null) {
			return StringPool.BLANK;
		}

		return article.getArticleId();
	}

	private long _getGroupId(
		PortletPreferences portletPreferences, long scopeGroupId) {

		String groupExternalReferenceCode = portletPreferences.getValue(
			"groupExternalReferenceCode", null);

		if (Validator.isNull(groupExternalReferenceCode)) {
			return scopeGroupId;
		}

		Group group = _groupLocalService.fetchGroupByExternalReferenceCode(
			groupExternalReferenceCode, CompanyThreadLocal.getCompanyId());

		if (group == null) {
			return scopeGroupId;
		}

		return group.getGroupId();
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

}
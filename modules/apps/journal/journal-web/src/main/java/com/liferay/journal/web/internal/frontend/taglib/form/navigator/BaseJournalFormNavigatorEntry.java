/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.frontend.taglib.form.navigator;

import com.liferay.frontend.taglib.form.navigator.BaseJSPFormNavigatorEntry;
import com.liferay.frontend.taglib.form.navigator.constants.FormNavigatorConstants;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public abstract class BaseJournalFormNavigatorEntry
	extends BaseJSPFormNavigatorEntry<JournalArticle> {

	@Override
	public String getCategoryKey() {
		return StringPool.BLANK;
	}

	@Override
	public String getFormNavigatorId() {
		return FormNavigatorConstants.FORM_NAVIGATOR_ID_JOURNAL;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, getKey());
	}

	protected boolean isClassNameIdDefault(JournalArticle journalArticle) {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		HttpServletRequest httpServletRequest = serviceContext.getRequest();

		PortletRequest portletRequest =
			(PortletRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		long classNameId = BeanPropertiesUtil.getLong(
			journalArticle, "classNameId",
			JournalArticleConstants.CLASS_NAME_ID_DEFAULT);

		if (portletRequest != null) {
			if (ParamUtil.getLong(portletRequest, "classNameId", classNameId) <=
					JournalArticleConstants.CLASS_NAME_ID_DEFAULT) {

				return true;
			}

			return false;
		}

		String portletId = ParamUtil.getString(httpServletRequest, "p_p_id");

		if (Validator.isNotNull(portletId)) {
			classNameId = ParamUtil.getLong(
				httpServletRequest,
				PortalUtil.getPortletNamespace(portletId) + "classNameId",
				classNameId);
		}

		if (classNameId <= JournalArticleConstants.CLASS_NAME_ID_DEFAULT) {
			return true;
		}

		return false;
	}

	protected boolean isDepotOrGlobalScopeArticle(JournalArticle article) {
		Group group = null;

		if ((article != null) && (article.getId() > 0)) {
			group = GroupLocalServiceUtil.fetchGroup(article.getGroupId());
		}
		else {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

			group = themeDisplay.getScopeGroup();
		}

		if ((group != null) && (group.isCompany() || group.isDepot())) {
			return true;
		}

		return false;
	}

	protected boolean isEditDefaultValues(JournalArticle article) {
		if (!isClassNameIdDefault(article)) {
			return true;
		}

		return false;
	}

}
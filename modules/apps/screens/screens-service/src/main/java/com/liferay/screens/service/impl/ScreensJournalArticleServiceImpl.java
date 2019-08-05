/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.screens.service.impl;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.screens.service.base.ScreensJournalArticleServiceBaseImpl;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 */
@Component(
	property = {
		"json.web.service.context.name=screens",
		"json.web.service.context.path=ScreensJournalArticle"
	},
	service = AopService.class
)
public class ScreensJournalArticleServiceImpl
	extends ScreensJournalArticleServiceBaseImpl {

	@Override
	public String getJournalArticleContent(long classPK, Locale locale)
		throws PortalException {

		JournalArticleResource journalArticleResource =
			journalArticleResourceLocalService.getArticleResource(classPK);

		_checkPermission(
			journalArticleResource.getGroupId(),
			journalArticleResource.getArticleId());

		return journalArticleLocalService.getArticleContent(
			journalArticleResource.getGroupId(),
			journalArticleResource.getArticleId(), null, null,
			getLanguageId(locale), null, null);
	}

	@Override
	public String getJournalArticleContent(
			long classPK, long ddmTemplateId, Locale locale)
		throws PortalException {

		JournalArticleResource journalArticleResource =
			journalArticleResourceLocalService.getArticleResource(classPK);

		_checkPermission(
			journalArticleResource.getGroupId(),
			journalArticleResource.getArticleId());

		return journalArticleLocalService.getArticleContent(
			journalArticleResource.getGroupId(),
			journalArticleResource.getArticleId(), null,
			getDDMTemplateKey(ddmTemplateId), getLanguageId(locale), null,
			null);
	}

	@Override
	public String getJournalArticleContent(
			long groupId, String articleId, long ddmTemplateId, Locale locale)
		throws PortalException {

		_checkPermission(groupId, articleId);

		return journalArticleLocalService.getArticleContent(
			groupId, articleId, null, getDDMTemplateKey(ddmTemplateId),
			getLanguageId(locale), null, null);
	}

	protected String getDDMTemplateKey(long ddmTemplateId)
		throws PortalException {

		DDMTemplate ddmTemplate = _ddmTemplateLocalService.getTemplate(
			ddmTemplateId);

		return ddmTemplate.getTemplateKey();
	}

	protected String getLanguageId(Locale locale) {
		if (locale == null) {
			locale = LocaleUtil.getSiteDefault();
		}

		return LocaleUtil.toLanguageId(locale);
	}

	private void _checkPermission(long groupId, String articleId)
		throws PortalException {

		JournalArticle article = journalArticleLocalService.getArticle(
			groupId, articleId);

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!_journalArticleModelResourcePermission.contains(
				permissionChecker, article, ActionKeys.VIEW)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, JournalArticle.class.getName(),
				article.getArticleId(), ActionKeys.VIEW);
		}
	}

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.journal.model.JournalArticle)"
	)
	private ModelResourcePermission<JournalArticle>
		_journalArticleModelResourcePermission;

}
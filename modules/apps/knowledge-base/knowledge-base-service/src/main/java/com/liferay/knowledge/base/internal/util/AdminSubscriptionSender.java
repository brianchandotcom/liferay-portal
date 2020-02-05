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

package com.liferay.knowledge.base.internal.util;

import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalServiceUtil;
import com.liferay.knowledge.base.util.KnowledgeBaseUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Subscription;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.SubscriptionSender;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;

/**
 * @author Peter Shin
 * @author Brian Wing Shun Chan
 */
public class AdminSubscriptionSender extends SubscriptionSender {

	public AdminSubscriptionSender(
		KBArticle kbArticle,
		ModelResourcePermission<KBArticle> modelResourcePermission,
		ServiceContext serviceContext) {

		_kbArticle = kbArticle;
		_kbArticleModelResourcePermission = modelResourcePermission;
		_serviceContext = serviceContext;
	}

	@Override
	public void initialize() throws Exception {
		super.initialize();

		String kbArticleURL = KnowledgeBaseUtil.getKBArticleURL(
			_serviceContext.getPlid(), _kbArticle.getResourcePrimKey(),
			_kbArticle.getStatus(), _serviceContext.getPortalURL(), false);

		setContextAttribute("[$ARTICLE_TITLE$]", _kbArticle.getTitle());
		setContextAttribute("[$ARTICLE_URL$]", kbArticleURL);
		setLocalizedContextAttributeWithFunction(
			"[$ARTICLE_ATTACHMENTS$]", _getEmailKBArticleAttachmentsFunction());
		setLocalizedContextAttributeWithFunction(
			"[$ARTICLE_VERSION$]",
			locale -> LanguageUtil.format(
				locale, "version-x", String.valueOf(_kbArticle.getVersion()),
				false));
		setLocalizedContextAttributeWithFunction(
			"[$CATEGORY_TITLE$]",
			locale -> LanguageUtil.get(locale, "category.kb"));
	}

	@Override
	protected void deleteSubscription(Subscription subscription)
		throws Exception {

		// KB article subscription

		if (subscription.getClassPK() == _kbArticle.getResourcePrimKey()) {
			KBArticleLocalServiceUtil.unsubscribeKBArticle(
				subscription.getUserId(), _kbArticle.getResourcePrimKey());
		}

		// Group subscription

		if (subscription.getClassPK() == _kbArticle.getGroupId()) {
			KBArticleLocalServiceUtil.unsubscribeGroupKBArticles(
				subscription.getUserId(), _kbArticle.getGroupId());
		}
	}

	@Override
	protected boolean hasPermission(
			Subscription subscription, String inferredClassName,
			long inferredClassPK, User user)
		throws Exception {

		String name = PrincipalThreadLocal.getName();

		PermissionChecker contextPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PrincipalThreadLocal.setName(user.getUserId());

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			return _kbArticleModelResourcePermission.contains(
				permissionChecker, _kbArticle, KBActionKeys.VIEW);
		}
		finally {
			PrincipalThreadLocal.setName(name);

			PermissionThreadLocal.setPermissionChecker(
				contextPermissionChecker);
		}
	}

	private Function<Locale, String> _getEmailKBArticleAttachmentsFunction()
		throws PortalException {

		List<FileEntry> attachmentsFileEntries =
			_kbArticle.getAttachmentsFileEntries();

		if (attachmentsFileEntries.isEmpty()) {
			return locale -> StringPool.BLANK;
		}

		return locale -> {
			StringBundler sb = new StringBundler(
				attachmentsFileEntries.size() * 5);

			for (FileEntry fileEntry : attachmentsFileEntries) {
				sb.append(fileEntry.getTitle());
				sb.append(" (");
				sb.append(
					LanguageUtil.formatStorageSize(
						fileEntry.getSize(), locale));
				sb.append(")");
				sb.append("<br />");
			}

			return sb.toString();
		};
	}

	private final KBArticle _kbArticle;
	private final ModelResourcePermission<KBArticle>
		_kbArticleModelResourcePermission;
	private final ServiceContext _serviceContext;

}
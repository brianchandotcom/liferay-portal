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

package com.liferay.journal.internal.instance.lifecycle;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class AddDefaultAssetVocabulariesPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		AssetVocabulary audienceVocabulary =
			_assetVocabularyLocalService.fetchGroupVocabulary(
				company.getGroupId(), "audience");

		Locale defaultLocale = LocaleUtil.getSiteDefault();
		User defaultUser = company.getDefaultUser();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		UnicodeProperties unicodeProperties = new UnicodeProperties();

		unicodeProperties.putIfAbsent(
			"selectedClassNameIds",
			_portal.getClassNameId(JournalArticle.class) + ":-1");

		if (audienceVocabulary == null) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				defaultLocale, getClass());

			_assetVocabularyLocalService.addVocabulary(
				defaultUser.getUserId(), company.getGroupId(), "audience",
				StringPool.BLANK,
				Collections.singletonMap(
					defaultLocale,
					LanguageUtil.get(resourceBundle, "audience")),
				Collections.emptyMap(), unicodeProperties.toString(),
				serviceContext);
		}

		AssetVocabulary stageVocabulary =
			_assetVocabularyLocalService.fetchGroupVocabulary(
				company.getGroupId(), "stage");

		if (stageVocabulary == null) {
			_assetVocabularyLocalService.addVocabulary(
				defaultUser.getUserId(), company.getGroupId(), "stage",
				StringPool.BLANK,
				Collections.singletonMap(
					defaultLocale, LanguageUtil.get(defaultLocale, "stage")),
				Collections.emptyMap(), unicodeProperties.toString(),
				serviceContext);
		}
	}

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private Portal _portal;

}
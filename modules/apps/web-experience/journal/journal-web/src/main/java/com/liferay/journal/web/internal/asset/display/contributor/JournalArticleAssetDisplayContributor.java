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

package com.liferay.journal.web.internal.asset.display.contributor;

import com.liferay.asset.display.contributor.AssetDisplayContributor;
import com.liferay.asset.display.contributor.BaseAssetDisplayContributor;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.util.JournalContent;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;

import java.util.Locale;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = AssetDisplayContributor.class)
public class JournalArticleAssetDisplayContributor
	extends BaseAssetDisplayContributor<JournalArticle>
	implements AssetDisplayContributor {

	@Override
	public String getClassName() {
		return JournalArticle.class.getName();
	}

	@Override
	protected String[] getAssetEntryModelFields() {
		return new String[] {"content"};
	}

	@Override
	protected Object getFieldValue(
		JournalArticle article, String field, Locale locale) {

		String languageId = LanguageUtil.getLanguageId(locale);

		if (Objects.equals(field, "content")) {
			return _journalContent.getContent(
				article.getGroupId(), article.getArticleId(), Constants.VIEW,
				languageId);
		}

		return StringPool.BLANK;
	}

	@Override
	@Reference(
		target = "(bundle.symbolic.name=com.liferay.journal.web)", unbind = "-"
	)
	protected void setResourceBundleLoader(
		ResourceBundleLoader resourceBundleLoader) {

		this.resourceBundleLoader = new AggregateResourceBundleLoader(
			resourceBundleLoader,
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader());
	}

	@Reference
	private JournalContent _journalContent;

}
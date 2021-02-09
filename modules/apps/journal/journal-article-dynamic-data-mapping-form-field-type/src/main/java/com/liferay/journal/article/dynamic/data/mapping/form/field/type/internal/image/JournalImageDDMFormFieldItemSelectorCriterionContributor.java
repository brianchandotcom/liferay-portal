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

package com.liferay.journal.article.dynamic.data.mapping.form.field.type.internal.image;

import com.liferay.dynamic.data.mapping.form.field.type.image.ImageDDMFormFieldItemSelectorCriterionContributor;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.item.selector.criterion.JournalItemSelectorCriterion;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	service = ImageDDMFormFieldItemSelectorCriterionContributor.class
)
public class JournalImageDDMFormFieldItemSelectorCriterionContributor
	implements ImageDDMFormFieldItemSelectorCriterionContributor {

	@Override
	public ItemSelectorCriterion getItemSelectorCriterion(
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		HttpServletRequest httpServletRequest =
			ddmFormFieldRenderingContext.getHttpServletRequest();

		String articleId = ParamUtil.getString(httpServletRequest, "articleId");
		long groupId = ParamUtil.getLong(httpServletRequest, "groupId");

		long resourcePrimaryKey = _getResourcePrimaryKey(articleId, groupId);

		long folderId = ParamUtil.getLong(httpServletRequest, "folderId");

		JournalItemSelectorCriterion journalItemSelectorCriterion =
			new JournalItemSelectorCriterion(resourcePrimaryKey, folderId);

		journalItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType());

		return journalItemSelectorCriterion;
	}

	@Override
	public boolean isVisible(
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		if (Objects.equals(
				ddmFormFieldRenderingContext.getPortletNamespace(),
				_portal.getPortletNamespace(JournalPortletKeys.JOURNAL))) {

			return true;
		}

		return false;
	}

	private long _getResourcePrimaryKey(String articleId, long groupId) {
		JournalArticle journalArticle =
			_journalArticleLocalService.fetchArticle(groupId, articleId);

		if (journalArticle != null) {
			return journalArticle.getResourcePrimKey();
		}

		return 0L;
	}

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private Portal _portal;

}
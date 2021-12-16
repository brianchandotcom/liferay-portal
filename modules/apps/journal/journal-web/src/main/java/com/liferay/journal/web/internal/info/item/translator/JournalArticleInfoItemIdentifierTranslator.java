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

package com.liferay.journal.web.internal.info.item.translator;

import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.GroupKeyInfoItemIdentifier;
import com.liferay.info.item.GroupUrlTitleInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.info.item.translator.InfoItemIdentifierTranslator;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "item.class.name=com.liferay.journal.model.JournalArticle",
	service = InfoItemIdentifierTranslator.class
)
public class JournalArticleInfoItemIdentifierTranslator
	implements InfoItemIdentifierTranslator<JournalArticle> {

	@Override
	public <S extends InfoItemIdentifier> S translateInfoItemIdentifier(
			InfoItemIdentifier infoItemIdentifier,
			Class<S> targetInfoItemIdentifierClass)
		throws PortalException {

		if (targetInfoItemIdentifierClass.isAssignableFrom(
				infoItemIdentifier.getClass())) {

			return (S)infoItemIdentifier;
		}

		return (S)_getTargetInfoItemIdentifier(
			_infoItemObjectProvider.getInfoItem(infoItemIdentifier),
			targetInfoItemIdentifierClass);
	}

	private InfoItemIdentifier _getTargetInfoItemIdentifier(
			JournalArticle article,
			Class<? extends InfoItemIdentifier> targetInfoItemIdentifierClass)
		throws NoSuchInfoItemException {

		if (ClassPKInfoItemIdentifier.class.isAssignableFrom(
				targetInfoItemIdentifierClass)) {

			return new ClassPKInfoItemIdentifier(article.getResourcePrimKey());
		}
		else if (GroupKeyInfoItemIdentifier.class.isAssignableFrom(
					targetInfoItemIdentifierClass)) {

			return new GroupKeyInfoItemIdentifier(
				article.getGroupId(), article.getArticleId());
		}
		else if (GroupUrlTitleInfoItemIdentifier.class.isAssignableFrom(
					targetInfoItemIdentifierClass)) {

			return new GroupUrlTitleInfoItemIdentifier(
				article.getGroupId(), article.getUrlTitle());
		}
		else {
			throw new NoSuchInfoItemException(
				"Unsupported info item identifier type " +
					targetInfoItemIdentifierClass.getName());
		}
	}

	@Reference(
		target = "(item.class.name=com.liferay.journal.model.JournalArticle)"
	)
	private InfoItemObjectProvider<JournalArticle> _infoItemObjectProvider;

}
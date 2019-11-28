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

package com.liferay.journal.web.internal.info.item.provider;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.info.item.provider.InfoItemDDMTemplateProvider;
import com.liferay.journal.model.JournalArticle;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pavel Savinov
 */
@Component(service = InfoItemDDMTemplateProvider.class)
public class JournalArticleInfoItemDDMTemplateProviderImpl
	implements InfoItemDDMTemplateProvider<JournalArticle> {

	@Override
	public List<DDMTemplate> getDDMTemplates(JournalArticle article) {
		DDMStructure ddmStructure = article.getDDMStructure();

		return ddmStructure.getTemplates();
	}

}
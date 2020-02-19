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

package com.liferay.journal.article.dynamic.data.mapping.form.field.type.internal;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"ddm.form.field.type.data.domain=journal_article",
		"ddm.form.field.type.description=journal-article-description",
		"ddm.form.field.type.display.order:Integer=10",
		"ddm.form.field.type.group=basic",
		"ddm.form.field.type.icon=web-content",
		"ddm.form.field.type.label=journal-article",
		"ddm.form.field.type.name=journal_article",
		"ddm.form.field.type.scope=journal"
	},
	service = DDMFormFieldType.class
)
public class JournalArticleDDMFormFieldType extends BaseDDMFormFieldType {

	@Override
	public Class<? extends DDMFormFieldTypeSettings>
		getDDMFormFieldTypeSettings() {

		return JournalArticleDDMFormFieldTypeSettings.class;
	}

	@Override
	public String getModuleName() {
		JSPackage jsPackage = _npmResolver.getJSPackage();

		return jsPackage.getResolvedId() + "/JournalArticleSelector";
	}

	@Override
	public String getName() {
		return "journal_article";
	}

	@Override
	public boolean isCustomDDMFormFieldType() {
		return true;
	}

	@Reference
	private NPMResolver _npmResolver;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.article.dynamic.data.mapping.form.field.type.internal.journal.article;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.journal.article.dynamic.data.mapping.form.field.type.constants.JournalArticleDDMFormFieldTypeConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = {
		"ddm.form.field.type.data.domain=journal_article",
		"ddm.form.field.type.description=journal-article-description",
		"ddm.form.field.type.display.order:Integer=10",
		"ddm.form.field.type.group=basic",
		"ddm.form.field.type.icon=web-content",
		"ddm.form.field.type.label=journal-article",
		"ddm.form.field.type.name=" + JournalArticleDDMFormFieldTypeConstants.JOURNAL_ARTICLE,
		"ddm.form.field.type.scope=document-library,journal"
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
		return JournalArticleDDMFormFieldTypeConstants.JOURNAL_ARTICLE;
	}

	@Override
	public boolean isCustomDDMFormFieldType() {
		return true;
	}

	@Reference
	private NPMResolver _npmResolver;

}
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

package com.liferay.journal.internal.util;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.util.JournalDefaultTemplateProvider;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.template.TemplateVariableDefinition;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collection;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = JournalDefaultTemplateProvider.class)
public class JournalDefaultTemplateProviderImpl
	implements JournalDefaultTemplateProvider {

	@Override
	public String getLanguage() {
		return TemplateConstants.LANG_TYPE_FTL;
	}

	@Override
	public String getScript(long ddmStructureId) throws Exception {
		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(
				JournalArticle.class.getName());

		Map<String, TemplateVariableGroup> templateVariableGroups =
			templateHandler.getTemplateVariableGroups(
				ddmStructureId, getLanguage(),
				LocaleUtil.getMostRelevantLocale());

		TemplateVariableGroup templateVariableGroup =
			templateVariableGroups.get("fields");

		if (templateVariableGroup == null) {
			return StringPool.BLANK;
		}

		Collection<TemplateVariableDefinition> templateVariableDefinitions =
			templateVariableGroup.getTemplateVariableDefinitions();

		StringBundler sb = new StringBundler(
			templateVariableDefinitions.size() * 5 + 2);

		sb.append("<dl>");

		for (TemplateVariableDefinition templateVariableDefinition :
				templateVariableDefinitions) {

			String code =
				templateVariableDefinition.generateCode(getLanguage())[0];

			sb.append("<dt class=\"text-capitalize\">");
			sb.append(templateVariableDefinition.getLabel());
			sb.append("</dt><dd>");
			sb.append(code);
			sb.append("</dd>");
		}

		sb.append("</dl>");

		return sb.toString();
	}

	@Override
	public boolean isCacheable() {
		return false;
	}

}
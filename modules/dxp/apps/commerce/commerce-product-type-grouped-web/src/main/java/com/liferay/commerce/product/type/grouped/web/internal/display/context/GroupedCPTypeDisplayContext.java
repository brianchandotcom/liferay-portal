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

package com.liferay.commerce.product.type.grouped.web.internal.display.context;

import com.liferay.commerce.product.content.web.configuration.CPContentConfigurationHelper;
import com.liferay.commerce.product.content.web.display.context.CPTypeDisplayContext;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueService;
import com.liferay.commerce.product.service.CPOptionCategoryService;
import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.service.CPDefinitionGroupedEntryService;
import com.liferay.commerce.product.type.grouped.util.comparator.CPDefinitionGroupedEntryPriorityComparator;
import com.liferay.commerce.product.util.CPContentContributorRegistry;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class GroupedCPTypeDisplayContext extends CPTypeDisplayContext {

	public GroupedCPTypeDisplayContext(
			CPAttachmentFileEntryService cpAttachmentFileEntryService,
			CPContentConfigurationHelper cpContentConfigurationHelper,
			CPContentContributorRegistry cpContentContributorRegistry,
			CPDefinition cpDefinition,
			CPDefinitionGroupedEntryService cpDefinitionGroupedEntryService,
			CPInstanceHelper cpInstanceHelper,
			CPDefinitionSpecificationOptionValueService
				cpDefinitionSpecificationOptionValueService,
			CPOptionCategoryService cpOptionCategoryService,
			HttpServletRequest httpServletRequest, Portal portal)
		throws Exception {

		super(
			cpAttachmentFileEntryService, cpContentConfigurationHelper,
			cpContentContributorRegistry, cpDefinition, cpInstanceHelper,
			cpDefinitionSpecificationOptionValueService,
			cpOptionCategoryService, httpServletRequest, portal);

		_cpDefinitionGroupedEntryService = cpDefinitionGroupedEntryService;
	}

	public List<CPDefinitionGroupedEntry> getCPDefinitionGroupedEntry()
		throws PortalException {

		return _cpDefinitionGroupedEntryService.getCPDefinitionGroupedEntries(
			cpDefinition.getCPDefinitionId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS,
			new CPDefinitionGroupedEntryPriorityComparator());
	}

	public String getLabel(Locale locale, String key) {
		return getLabel(locale, key, null);
	}

	public String getLabel(Locale locale, String key, Object argument) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		if (argument == null) {
			return LanguageUtil.get(resourceBundle, key);
		}

		return LanguageUtil.format(resourceBundle, key, argument, true);
	}

	private final CPDefinitionGroupedEntryService
		_cpDefinitionGroupedEntryService;

}
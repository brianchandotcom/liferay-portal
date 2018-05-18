/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.type.grouped.web.internal.display.context;

import com.liferay.asset.kernel.service.AssetCategoryService;
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
			AssetCategoryService assetCategoryService,
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
			assetCategoryService, cpAttachmentFileEntryService,
			cpContentConfigurationHelper, cpContentContributorRegistry,
			cpDefinition, cpInstanceHelper,
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
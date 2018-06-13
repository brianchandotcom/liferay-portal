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

package com.liferay.commerce.product.type.grouped.web.internal.portlet.template.contributor;

import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.service.CPDefinitionGroupedEntryService;
import com.liferay.commerce.product.type.grouped.util.comparator.CPDefinitionGroupedEntryPriorityComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = GroupedCPTypeHelper.class)
public class GroupedCPTypeHelper {

	public List<CPDefinitionGroupedEntry> getCPDefinitionGroupedEntry(
			long cpDefinitionId)
		throws PortalException {

		return _cpDefinitionGroupedEntryService.getCPDefinitionGroupedEntries(
			cpDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
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

	@Reference
	private CPDefinitionGroupedEntryService _cpDefinitionGroupedEntryService;

}
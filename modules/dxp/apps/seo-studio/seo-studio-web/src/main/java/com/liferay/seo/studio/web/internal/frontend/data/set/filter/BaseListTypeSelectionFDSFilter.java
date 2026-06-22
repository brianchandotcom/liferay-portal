/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.internal.frontend.data.set.filter;

import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Noor Najjar
 */
public abstract class BaseListTypeSelectionFDSFilter
	extends BaseSelectionFDSFilter {

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		ListTypeDefinition listTypeDefinition =
			listTypeDefinitionLocalService.
				fetchListTypeDefinitionByExternalReferenceCode(
					getListTypeDefinitionExternalReferenceCode(),
					CompanyThreadLocal.getCompanyId());

		if (listTypeDefinition == null) {
			return Collections.emptyList();
		}

		return TransformUtil.transform(
			listTypeEntryLocalService.getListTypeEntries(
				listTypeDefinition.getListTypeDefinitionId()),
			listTypeEntry -> new SelectionFDSFilterItem(
				listTypeEntry.getName(locale), listTypeEntry.getKey()));
	}

	protected abstract String getListTypeDefinitionExternalReferenceCode();

	@Reference
	protected ListTypeDefinitionLocalService listTypeDefinitionLocalService;

	@Reference
	protected ListTypeEntryLocalService listTypeEntryLocalService;

}
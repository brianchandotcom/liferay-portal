/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.frontend.data.set.filter;

import com.liferay.commerce.order.content.web.internal.constants.CommerceOrderFragmentFDSNames;
import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tancredi Covioli
 */
@Component(
	property = {
		"frontend.data.set.name=" + CommerceOrderFragmentFDSNames.PENDING_ORDER_ATTACHMENTS,
		"frontend.data.set.name=" + CommerceOrderFragmentFDSNames.PLACED_ORDER_ATTACHMENTS
	},
	service = FDSFilter.class
)
public class CommerceOrderAttachmentTypeSelectionFDSFilter
	extends BaseSelectionFDSFilter {

	@Override
	public String getEntityFieldType() {
		return FDSEntityFieldTypes.COLLECTION;
	}

	@Override
	public String getId() {
		return "type";
	}

	@Override
	public String getLabel() {
		return "type";
	}

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		List<SelectionFDSFilterItem> selectionFDSFilterItems =
			new ArrayList<>();

		try {
			ListTypeDefinition listTypeDefinition =
				_listTypeDefinitionLocalService.
					fetchListTypeDefinitionByExternalReferenceCode(
						"L_COMMERCE_ORDER_ATTACHMENT_TYPES",
						CompanyThreadLocal.getCompanyId());

			if (listTypeDefinition == null) {
				return selectionFDSFilterItems;
			}

			for (ListTypeEntry listTypeEntry :
					_listTypeEntryService.getListTypeEntries(
						listTypeDefinition.getListTypeDefinitionId(),
						QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

				selectionFDSFilterItems.add(
					new SelectionFDSFilterItem(
						listTypeEntry.getName(locale), listTypeEntry.getKey()));
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return selectionFDSFilterItems;
	}

	@Override
	public boolean isMultiple() {
		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderAttachmentTypeSelectionFDSFilter.class);

	@Reference
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Reference
	private ListTypeEntryService _listTypeEntryService;

}
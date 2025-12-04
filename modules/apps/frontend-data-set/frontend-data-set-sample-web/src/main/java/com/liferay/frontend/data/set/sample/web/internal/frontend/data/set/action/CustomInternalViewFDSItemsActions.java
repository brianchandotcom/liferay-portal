/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.sample.web.internal.frontend.data.set.action;

import com.liferay.frontend.data.set.FDSEntryItemImportPolicy;
import com.liferay.frontend.data.set.action.FDSItemsActions;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.model.FDSActionDropdownItemBuilder;
import com.liferay.frontend.data.set.model.FDSActionDropdownItemList;
import com.liferay.frontend.data.set.sample.web.internal.constants.FDSSampleFDSNames;
import com.liferay.portal.kernel.language.Language;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marko Cikos
 */
@Component(
	property = "frontend.data.set.name=" + FDSSampleFDSNames.CUSTOM_INTERNAL_VIEW,
	service = FDSItemsActions.class
)
public class CustomInternalViewFDSItemsActions implements FDSItemsActions {

	@Override
	public List<FDSActionDropdownItem> getFDSActionDropdownItems(
		HttpServletRequest httpServletRequest) {

		return FDSActionDropdownItemList.of(
			FDSActionDropdownItemBuilder.setHref(
				"#"
			).setIcon(
				"code"
			).setLabel(
				_language.get(httpServletRequest, "code")
			).setTarget(
				"link"
			).setType(
				"item"
			).build(
				"openCode"
			),
			FDSActionDropdownItemBuilder.setHref(
				"#"
			).setIcon(
				"document"
			).setLabel(
				_language.get(httpServletRequest, "document")
			).setTarget(
				"link"
			).setType(
				"item"
			).build(
				"openDocument"
			),
			FDSActionDropdownItemBuilder.setHref(
				"#"
			).setIcon(
				"cog"
			).setLabel(
				"Turn Green"
			).setTarget(
				"link"
			).setType(
				"item"
			).build(
				"turnGreen"
			));
	}

	@Override
	public FDSEntryItemImportPolicy getFDSEntryItemImportPolicy() {
		return FDSEntryItemImportPolicy.GROUP_PROXY;
	}

	@Reference
	private Language _language;

}
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
import com.liferay.portal.kernel.util.HashMapBuilder;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marko Cikos
 */
@Component(
	property = "frontend.data.set.name=" + FDSSampleFDSNames.CLASSIC,
	service = FDSItemsActions.class
)
public class ClassicFDSItemsActions implements FDSItemsActions {

	@Override
	public List<FDSActionDropdownItem> getFDSActionDropdownItems(
		HttpServletRequest httpServletRequest) {

		return FDSActionDropdownItemList.of(
			FDSActionDropdownItemBuilder.setHref(
				"#"
			).setIcon(
				"book"
			).setLabel(
				_language.get(httpServletRequest, "book")
			).setTarget(
				"link"
			).setType(
				"item"
			).build(
				"navigateToLibrary"
			),
			FDSActionDropdownItemBuilder.setHref(
				"#"
			).setIcon(
				"archive"
			).setLabel(
				_language.get(httpServletRequest, "job-archive")
			).setTarget(
				"link"
			).setType(
				"item"
			).build(
				"navigateToArchive"
			),
			FDSActionDropdownItemBuilder.setHref(
				"#"
			).setIcon(
				"cog"
			).setLabel(
				_language.get(httpServletRequest, "deactivate")
			).setTarget(
				"link"
			).setType(
				"item"
			).setVisibilityFilters(
				HashMapBuilder.<String, Object>put(
					"active", Boolean.TRUE
				).build()
			).build(
				"deactivate"
			),
			FDSActionDropdownItemBuilder.setHref(
				"#"
			).setIcon(
				"cog"
			).setLabel(
				_language.get(httpServletRequest, "activate")
			).setTarget(
				"link"
			).setType(
				"item"
			).setVisibilityFilters(
				HashMapBuilder.<String, Object>put(
					"active", Boolean.FALSE
				).build()
			).build(
				"activate"
			),
			FDSActionDropdownItemBuilder.setHref(
				"#"
			).setIcon(
				"cog"
			).setLabel(
				_language.get(httpServletRequest, "activity")
			).setTarget(
				"link"
			).setType(
				"item"
			).setVisibilityFilters(
				HashMapBuilder.<String, Object>put(
					"active", Boolean.TRUE
				).put(
					"emailAddress", "manager.user@liferay.com"
				).build()
			).build(
				"activity"
			));
	}

	@Override
	public FDSEntryItemImportPolicy getFDSEntryItemImportPolicy() {
		return FDSEntryItemImportPolicy.ITEM_PROXY;
	}

	@Reference
	private Language _language;

}
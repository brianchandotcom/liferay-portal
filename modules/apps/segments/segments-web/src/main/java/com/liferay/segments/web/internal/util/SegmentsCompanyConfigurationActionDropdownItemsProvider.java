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

package com.liferay.segments.web.internal.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.segments.web.internal.display.context.SegmentsCompanyConfigurationDisplayContext;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Diego Hu
 */
public class SegmentsCompanyConfigurationActionDropdownItemsProvider {

	public SegmentsCompanyConfigurationActionDropdownItemsProvider(
		HttpServletRequest httpServletRequest,
		SegmentsCompanyConfigurationDisplayContext
			segmentsCompanyConfigurationDisplayContext) {

		_httpServletRequest = httpServletRequest;
		_segmentsCompanyConfigurationDisplayContext =
			segmentsCompanyConfigurationDisplayContext;
	}

	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> dropdownGroupItem.setDropdownItems(
				DropdownItemListBuilder.add(
					dropdownItem -> {
						dropdownItem.setHref(
							_segmentsCompanyConfigurationDisplayContext.
								getDeleteConfigurationActionURL());
						dropdownItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "reset-default-values"));
					}
				).add(
					dropdownItem -> {
						dropdownItem.setHref(
							_segmentsCompanyConfigurationDisplayContext.
								getExportConfigurationActionURL());
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "export"));
					}
				).build())
		).build();
	}

	private final HttpServletRequest _httpServletRequest;
	private final SegmentsCompanyConfigurationDisplayContext
		_segmentsCompanyConfigurationDisplayContext;

}
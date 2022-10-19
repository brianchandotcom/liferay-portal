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

package com.liferay.client.extension.web.internal.frontend.data.set.provider;

import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.client.extension.web.internal.constants.ClientExtensionAdminFDSNames;
import com.liferay.client.extension.web.internal.frontend.data.set.model.CETFDSEntry;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = "fds.data.provider.key=" + ClientExtensionAdminFDSNames.CLIENT_EXTENSION_TYPES,
	service = FDSDataProvider.class
)
public class CETFDSDataProvider implements FDSDataProvider<CETFDSEntry> {

	@Override
	public List<CETFDSEntry> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return TransformUtil.transform(
			_cetManager.getCETs(
				themeDisplay.getCompanyId(), fdsKeywords.getKeywords(), null,
				Pagination.of(
					fdsPagination.getPage(), fdsPagination.getPageSize()),
				sort),
			cet -> new CETFDSEntry(cet, themeDisplay.getLocale()));
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _cetManager.getCETsCount(
			themeDisplay.getCompanyId(), fdsKeywords.getKeywords(), null);
	}

	@Reference
	private CETManager _cetManager;

}
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

package com.liferay.roles.admin.internal.segments.entry;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.roles.admin.segments.entry.RoleSegmentsEntryRetriever;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;

import java.util.LinkedHashMap;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = RoleSegmentsEntryRetriever.class)
public class RoleSegmentsEntryRetrieverImpl
	implements RoleSegmentsEntryRetriever {

	@Override
	public BaseModelSearchResult<SegmentsEntry> searchRoleSegmentsEntries(
			long roleId, String keywords, int cur, int delta, String sortField,
			boolean reverse)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("roleIds", new long[] {roleId});

		Sort sort = null;

		if ((sortField != null) && sortField.equals("name")) {
			Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

			String sortFieldName = Field.getSortableFieldName(
				"localized_name_".concat(locale.getLanguage()));

			sort = new Sort(sortFieldName, Sort.STRING_TYPE, reverse);
		}
		else {
			sort = new Sort(Field.MODIFIED_DATE, Sort.LONG_TYPE, reverse);
		}

		return _segmentsEntryLocalService.searchSegmentsEntries(
			serviceContext.getCompanyId(), serviceContext.getScopeGroupId(),
			keywords, true, params, cur, cur + delta, sort);
	}

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

}
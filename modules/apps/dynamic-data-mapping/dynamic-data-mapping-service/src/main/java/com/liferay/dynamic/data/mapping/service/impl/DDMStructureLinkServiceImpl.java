/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.service.impl;

import com.liferay.dynamic.data.mapping.model.DDMStructureLink;
import com.liferay.dynamic.data.mapping.service.base.DDMStructureLinkServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=ddm",
		"json.web.service.context.path=DDMStructureLink"
	},
	service = AopService.class
)
public class DDMStructureLinkServiceImpl
	extends DDMStructureLinkServiceBaseImpl {

	@Override
	public List<DDMStructureLink> getStructureLinks(
		long classNameId, long classPK, long[] groupIds, String keywords,
		String resourceClassName, int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator) {

		return ddmStructureLinkFinder.filterFindByKeywords(
			classNameId, classPK, groupIds, keywords, resourceClassName, start,
			end, orderByComparator);
	}

	@Override
	public int getStructureLinksCount(
		long classNameId, long classPK, long[] groupIds, String keywords,
		String resourceClassName) {

		return ddmStructureLinkFinder.filterCountByKeywords(
			classNameId, classPK, groupIds, keywords, resourceClassName);
	}

}
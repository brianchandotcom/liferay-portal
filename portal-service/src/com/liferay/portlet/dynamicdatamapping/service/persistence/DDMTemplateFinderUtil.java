/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMTemplateFinderUtil {
	public static int countByKeywords(long companyId, long groupId,
		long structureId, java.lang.String keywords, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .countByKeywords(companyId, groupId, structureId, keywords,
			type);
	}

	public static int countByC_G_S_N_D_T_L(long companyId, long groupId,
		long structureId, java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String language, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .countByC_G_S_N_D_T_L(companyId, groupId, structureId, name,
			description, type, language, andOperator);
	}

	public static int countByC_G_S_N_D_T_L(long companyId, long groupId,
		long structureId, java.lang.String[] names,
		java.lang.String[] descriptions, java.lang.String[] types,
		java.lang.String[] languages, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .countByC_G_S_N_D_T_L(companyId, groupId, structureId,
			names, descriptions, types, languages, andOperator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByKeywords(
		long companyId, long groupId, long structureId,
		java.lang.String keywords, java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByKeywords(companyId, groupId, structureId, keywords,
			type, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByC_G_S_N_D_T_L(
		long companyId, long groupId, long structureId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String language, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByC_G_S_N_D_T_L(companyId, groupId, structureId, name,
			description, type, language, andOperator, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByC_G_S_N_D_T_L(
		long companyId, long groupId, long structureId,
		java.lang.String[] names, java.lang.String[] descriptions,
		java.lang.String[] types, java.lang.String[] languages,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByC_G_S_N_D_T_L(companyId, groupId, structureId, names,
			descriptions, types, languages, andOperator, start, end,
			orderByComparator);
	}

	public static DDMTemplateFinder getFinder() {
		if (_finder == null) {
			_finder = (DDMTemplateFinder)PortalBeanLocatorUtil.locate(DDMTemplateFinder.class.getName());

			ReferenceRegistry.registerReference(DDMTemplateFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(DDMTemplateFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(DDMTemplateFinderUtil.class,
			"_finder");
	}

	private static DDMTemplateFinder _finder;
}
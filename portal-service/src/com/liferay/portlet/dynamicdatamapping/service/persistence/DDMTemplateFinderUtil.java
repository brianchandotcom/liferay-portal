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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @generated
 */
@ProviderType
public class DDMTemplateFinderUtil {
	public static int countByKeywords(long companyId, long groupId,
		long classNameId, long classPK, java.lang.String keywords,
		java.lang.String type, java.lang.String mode) {
		return getFinder()
				   .countByKeywords(companyId, groupId, classNameId, classPK,
			keywords, type, mode);
	}

	public static int countByKeywords(long companyId, long[] groupIds,
		long[] classNameIds, long[] classPKs, java.lang.String keywords,
		java.lang.String type, java.lang.String mode) {
		return getFinder()
				   .countByKeywords(companyId, groupIds, classNameIds,
			classPKs, keywords, type, mode);
	}

	public static int countByG_SC(long groupId, long structureClassNameId) {
		return getFinder().countByG_SC(groupId, structureClassNameId);
	}

	public static int countByC_G_C_C_N_D_T_M_L(long companyId, long groupId,
		long classNameId, long classPK, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String mode, java.lang.String language, boolean andOperator) {
		return getFinder()
				   .countByC_G_C_C_N_D_T_M_L(companyId, groupId, classNameId,
			classPK, name, description, type, mode, language, andOperator);
	}

	public static int countByC_G_C_C_N_D_T_M_L(long companyId, long groupId,
		long classNameId, long classPK, java.lang.String[] names,
		java.lang.String[] descriptions, java.lang.String[] types,
		java.lang.String[] modes, java.lang.String[] languages,
		boolean andOperator) {
		return getFinder()
				   .countByC_G_C_C_N_D_T_M_L(companyId, groupId, classNameId,
			classPK, names, descriptions, types, modes, languages, andOperator);
	}

	public static int countByC_G_C_C_N_D_T_M_L(long companyId, long[] groupIds,
		long[] classNameIds, long[] classPKs, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String mode, java.lang.String language, boolean andOperator) {
		return getFinder()
				   .countByC_G_C_C_N_D_T_M_L(companyId, groupIds, classNameIds,
			classPKs, name, description, type, mode, language, andOperator);
	}

	public static int countByC_G_C_C_N_D_T_M_L(long companyId, long[] groupIds,
		long[] classNameIds, long[] classPKs, java.lang.String[] names,
		java.lang.String[] descriptions, java.lang.String[] types,
		java.lang.String[] modes, java.lang.String[] languages,
		boolean andOperator) {
		return getFinder()
				   .countByC_G_C_C_N_D_T_M_L(companyId, groupIds, classNameIds,
			classPKs, names, descriptions, types, modes, languages, andOperator);
	}

	public static int filterCountByKeywords(long companyId, long groupId,
		long classNameId, long classPK, java.lang.String keywords,
		java.lang.String type, java.lang.String mode) {
		return getFinder()
				   .filterCountByKeywords(companyId, groupId, classNameId,
			classPK, keywords, type, mode);
	}

	public static int filterCountByKeywords(long companyId, long[] groupIds,
		long[] classNameIds, long[] classPKs, java.lang.String keywords,
		java.lang.String type, java.lang.String mode) {
		return getFinder()
				   .filterCountByKeywords(companyId, groupIds, classNameIds,
			classPKs, keywords, type, mode);
	}

	public static int filterCountByC_G_C_C_N_D_T_M_L(long companyId,
		long groupId, long classNameId, long classPK, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String mode, java.lang.String language, boolean andOperator) {
		return getFinder()
				   .filterCountByC_G_C_C_N_D_T_M_L(companyId, groupId,
			classNameId, classPK, name, description, type, mode, language,
			andOperator);
	}

	public static int filterCountByC_G_C_C_N_D_T_M_L(long companyId,
		long groupId, long classNameId, long classPK, java.lang.String[] names,
		java.lang.String[] descriptions, java.lang.String[] types,
		java.lang.String[] modes, java.lang.String[] languages,
		boolean andOperator) {
		return getFinder()
				   .filterCountByC_G_C_C_N_D_T_M_L(companyId, groupId,
			classNameId, classPK, names, descriptions, types, modes, languages,
			andOperator);
	}

	public static int filterCountByC_G_C_C_N_D_T_M_L(long companyId,
		long[] groupIds, long[] classNameIds, long[] classPKs,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, boolean andOperator) {
		return getFinder()
				   .filterCountByC_G_C_C_N_D_T_M_L(companyId, groupIds,
			classNameIds, classPKs, name, description, type, mode, language,
			andOperator);
	}

	public static int filterCountByC_G_C_C_N_D_T_M_L(long companyId,
		long[] groupIds, long[] classNameIds, long[] classPKs,
		java.lang.String[] names, java.lang.String[] descriptions,
		java.lang.String[] types, java.lang.String[] modes,
		java.lang.String[] languages, boolean andOperator) {
		return getFinder()
				   .filterCountByC_G_C_C_N_D_T_M_L(companyId, groupIds,
			classNameIds, classPKs, names, descriptions, types, modes,
			languages, andOperator);
	}

	public static int filterCountByG_SC(long groupId, long structureClassNameId) {
		return getFinder().filterCountByG_SC(groupId, structureClassNameId);
	}

	public static int filterCountByG_SC(long[] groupIds,
		long structureClassNameId) {
		return getFinder().filterCountByG_SC(groupIds, structureClassNameId);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> filterFindByKeywords(
		long companyId, long groupId, long classNameId, long classPK,
		java.lang.String keywords, java.lang.String type,
		java.lang.String mode, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> orderByComparator) {
		return getFinder()
				   .filterFindByKeywords(companyId, groupId, classNameId,
			classPK, keywords, type, mode, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> filterFindByKeywords(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		java.lang.String keywords, java.lang.String type,
		java.lang.String mode, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> orderByComparator) {
		return getFinder()
				   .filterFindByKeywords(companyId, groupIds, classNameIds,
			classPKs, keywords, type, mode, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> filterFindByC_G_C_C_N_D_T_M_L(
		long companyId, long groupId, long classNameId, long classPK,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> orderByComparator) {
		return getFinder()
				   .filterFindByC_G_C_C_N_D_T_M_L(companyId, groupId,
			classNameId, classPK, name, description, type, mode, language,
			andOperator, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> filterFindByC_G_C_C_N_D_T_M_L(
		long companyId, long groupId, long classNameId, long classPK,
		java.lang.String[] names, java.lang.String[] descriptions,
		java.lang.String[] types, java.lang.String[] modes,
		java.lang.String[] languages, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> orderByComparator) {
		return getFinder()
				   .filterFindByC_G_C_C_N_D_T_M_L(companyId, groupId,
			classNameId, classPK, names, descriptions, types, modes, languages,
			andOperator, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> filterFindByC_G_C_C_N_D_T_M_L(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> orderByComparator) {
		return getFinder()
				   .filterFindByC_G_C_C_N_D_T_M_L(companyId, groupIds,
			classNameIds, classPKs, name, description, type, mode, language,
			andOperator, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> filterFindByC_G_C_C_N_D_T_M_L(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		java.lang.String[] names, java.lang.String[] descriptions,
		java.lang.String[] types, java.lang.String[] modes,
		java.lang.String[] languages, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> orderByComparator) {
		return getFinder()
				   .filterFindByC_G_C_C_N_D_T_M_L(companyId, groupIds,
			classNameIds, classPKs, names, descriptions, types, modes,
			languages, andOperator, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> filterFindByG_SC(
		long groupId, long structureClassNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> orderByComparator) {
		return getFinder()
				   .filterFindByG_SC(groupId, structureClassNameId, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> filterFindByG_SC(
		long[] groupIds, long structureClassNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> orderByComparator) {
		return getFinder()
				   .filterFindByG_SC(groupIds, structureClassNameId, start,
			end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByKeywords(
		long companyId, long groupId, long classNameId, long classPK,
		java.lang.String keywords, java.lang.String type,
		java.lang.String mode, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> orderByComparator) {
		return getFinder()
				   .findByKeywords(companyId, groupId, classNameId, classPK,
			keywords, type, mode, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByKeywords(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		java.lang.String keywords, java.lang.String type,
		java.lang.String mode, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> orderByComparator) {
		return getFinder()
				   .findByKeywords(companyId, groupIds, classNameIds, classPKs,
			keywords, type, mode, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByG_SC(
		long groupId, long structureClassNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> orderByComparator) {
		return getFinder()
				   .findByG_SC(groupId, structureClassNameId, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByG_SC(
		long[] groupIds, long structureClassNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> orderByComparator) {
		return getFinder()
				   .findByG_SC(groupIds, structureClassNameId, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByC_G_C_C_N_D_T_M_L(
		long companyId, long groupId, long classNameId, long classPK,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> orderByComparator) {
		return getFinder()
				   .findByC_G_C_C_N_D_T_M_L(companyId, groupId, classNameId,
			classPK, name, description, type, mode, language, andOperator,
			start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByC_G_C_C_N_D_T_M_L(
		long companyId, long groupId, long classNameId, long classPK,
		java.lang.String[] names, java.lang.String[] descriptions,
		java.lang.String[] types, java.lang.String[] modes,
		java.lang.String[] languages, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> orderByComparator) {
		return getFinder()
				   .findByC_G_C_C_N_D_T_M_L(companyId, groupId, classNameId,
			classPK, names, descriptions, types, modes, languages, andOperator,
			start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByC_G_C_C_N_D_T_M_L(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> orderByComparator) {
		return getFinder()
				   .findByC_G_C_C_N_D_T_M_L(companyId, groupIds, classNameIds,
			classPKs, name, description, type, mode, language, andOperator,
			start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByC_G_C_C_N_D_T_M_L(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		java.lang.String[] names, java.lang.String[] descriptions,
		java.lang.String[] types, java.lang.String[] modes,
		java.lang.String[] languages, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> orderByComparator) {
		return getFinder()
				   .findByC_G_C_C_N_D_T_M_L(companyId, groupIds, classNameIds,
			classPKs, names, descriptions, types, modes, languages,
			andOperator, start, end, orderByComparator);
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
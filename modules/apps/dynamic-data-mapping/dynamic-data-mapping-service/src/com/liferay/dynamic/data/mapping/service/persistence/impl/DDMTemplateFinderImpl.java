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

package com.liferay.dynamic.data.mapping.service.persistence.impl;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.impl.DDMTemplateImpl;
import com.liferay.dynamic.data.mapping.service.permission.DDMTemplatePermission;
import com.liferay.dynamic.data.mapping.service.persistence.DDMTemplateFinder;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @author Eduardo Lundgren
 * @author Connor McKay
 * @author Marcellus Tavares
 * @author Juan Fernández
 */
public class DDMTemplateFinderImpl
	extends BasePersistenceImpl<DDMTemplate> implements DDMTemplateFinder {

	public static final String COUNT_BY_G_C_SC_S =
		DDMTemplateFinder.class.getName() + ".countByG_C_SC_S";

	public static final String COUNT_BY_C_G_C_C_R_N_D_T_M_L_S =
		DDMTemplateFinder.class.getName() + ".countByC_G_C_C_R_N_D_T_M_L_S";

	public static final String FIND_BY_G_C_SC_S =
		DDMTemplateFinder.class.getName() + ".findByG_C_SC_S";

	public static final String FIND_BY_C_G_C_C_R_N_D_T_M_L_S =
		DDMTemplateFinder.class.getName() + ".findByC_G_C_C_R_N_D_T_M_L_S";

	@Override
	public int countByKeywords(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, String keywords, String type, String mode,
		int status) {

		String[] names = null;
		String[] descriptions = null;
		String[] types = CustomSQLUtil.keywords(type, false);
		String[] modes = CustomSQLUtil.keywords(mode, false);
		String[] languages = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = CustomSQLUtil.keywords(keywords);
			descriptions = CustomSQLUtil.keywords(keywords, false);
			languages = CustomSQLUtil.keywords(keywords, false);
		}
		else {
			andOperator = true;
		}

		return countByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupId, classNameId, classPK, resourceClassNameId,
			names, descriptions, types, modes, languages, status, andOperator);
	}

	@Override
	public int countByKeywords(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, String keywords, String type, String mode,
		int status) {

		String[] names = null;
		String[] descriptions = null;
		String[] types = CustomSQLUtil.keywords(type, false);
		String[] modes = CustomSQLUtil.keywords(mode, false);
		String[] languages = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = CustomSQLUtil.keywords(keywords);
			descriptions = CustomSQLUtil.keywords(keywords, false);
			languages = CustomSQLUtil.keywords(keywords, false);
		}
		else {
			andOperator = true;
		}

		return countByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupIds, classNameIds, classPKs, resourceClassNameId,
			names, descriptions, types, modes, languages, status, andOperator);
	}

	@Override
	public int countByG_SC_S(
		long groupId, long structureClassNameId, int status) {

		long[] groupIds = new long[] {groupId};
		long classNameId = PortalUtil.getClassNameId(DDMStructure.class);

		return doCountByG_C_SC_S(
			groupIds, classNameId, structureClassNameId, status, false);
	}

	@Override
	public int countByC_G_C_C_R_T_M_S(
		long companyId, long[] groupIds, long classNameId, long classPK,
		long resourceClassNameId, String type, String mode, int status) {

		long[] classNameIds = new long[] {classNameId};
		long[] classPKs = new long[] {classPK};
		String[] types = CustomSQLUtil.keywords(type, false);
		String[] modes = CustomSQLUtil.keywords(mode, false);

		return doCountByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupIds, classNameIds, classPKs, resourceClassNameId,
			null, null, types, modes, null, status, true, false);
	}

	@Override
	public int countByC_G_C_C_R_N_D_T_M_L_S(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, String name, String description, String type,
		String mode, String language, int status, boolean andOperator) {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description, false);
		String[] types = CustomSQLUtil.keywords(type, false);
		String[] modes = CustomSQLUtil.keywords(mode, false);
		String[] languages = CustomSQLUtil.keywords(language, false);

		return countByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupId, classNameId, classPK, resourceClassNameId,
			names, descriptions, types, modes, languages, status, andOperator);
	}

	@Override
	public int countByC_G_C_C_R_N_D_T_M_L_S(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, String[] names, String[] descriptions,
		String[] types, String[] modes, String[] languages, int status,
		boolean andOperator) {

		long[] groupIds = new long[] {groupId};
		long[] classNameIds = new long[] {classNameId};
		long[] classPKs = new long[] {classPK};

		return doCountByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupIds, classNameIds, classPKs, resourceClassNameId,
			names, descriptions, types, modes, languages, status, andOperator,
			false);
	}

	@Override
	public int countByC_G_C_C_R_N_D_T_M_L_S(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, String name, String description, String type,
		String mode, String language, int status, boolean andOperator) {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description, false);
		String[] types = CustomSQLUtil.keywords(type, false);
		String[] modes = CustomSQLUtil.keywords(mode, false);
		String[] languages = CustomSQLUtil.keywords(language, false);

		return countByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupIds, classNameIds, classPKs, resourceClassNameId,
			names, descriptions, types, modes, languages, status, andOperator);
	}

	@Override
	public int countByC_G_C_C_R_N_D_T_M_L_S(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, String[] names, String[] descriptions,
		String[] types, String[] modes, String[] languages, int status,
		boolean andOperator) {

		return doCountByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupIds, classNameIds, classPKs, resourceClassNameId,
			names, descriptions, types, modes, languages, status, andOperator,
			false);
	}

	@Override
	public int filterCountByKeywords(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, String keywords, String type, String mode,
		int status) {

		String[] names = null;
		String[] descriptions = null;
		String[] types = CustomSQLUtil.keywords(type, false);
		String[] modes = CustomSQLUtil.keywords(mode, false);
		String[] languages = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = CustomSQLUtil.keywords(keywords);
			descriptions = CustomSQLUtil.keywords(keywords, false);
			languages = CustomSQLUtil.keywords(keywords, false);
		}
		else {
			andOperator = true;
		}

		return filterCountByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupId, classNameId, classPK, resourceClassNameId,
			names, descriptions, types, modes, languages, status, andOperator);
	}

	@Override
	public int filterCountByKeywords(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, String keywords, String type, String mode,
		int status) {

		String[] names = null;
		String[] descriptions = null;
		String[] types = CustomSQLUtil.keywords(type, false);
		String[] modes = CustomSQLUtil.keywords(mode, false);
		String[] languages = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = CustomSQLUtil.keywords(keywords);
			descriptions = CustomSQLUtil.keywords(keywords, false);
			languages = CustomSQLUtil.keywords(keywords, false);
		}
		else {
			andOperator = true;
		}

		return filterCountByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupIds, classNameIds, classPKs, resourceClassNameId,
			names, descriptions, types, modes, languages, status, andOperator);
	}

	@Override
	public int filterCountByC_G_C_C_R_T_M_S(
		long companyId, long[] groupIds, long classNameId, long classPK,
		long resourceClassNameId, String type, String mode, int status) {

		long[] classNameIds = new long[] {classNameId};
		long[] classPKs = new long[] {classPK};
		String[] types = CustomSQLUtil.keywords(type, false);
		String[] modes = CustomSQLUtil.keywords(mode, false);

		return doCountByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupIds, classNameIds, classPKs, resourceClassNameId,
			null, null, types, modes, null, status, true, true);
	}

	@Override
	public int filterCountByC_G_C_C_R_N_D_T_M_L_S(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, String name, String description, String type,
		String mode, String language, int status, boolean andOperator) {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description, false);
		String[] types = CustomSQLUtil.keywords(type, false);
		String[] modes = CustomSQLUtil.keywords(mode, false);
		String[] languages = CustomSQLUtil.keywords(language, false);

		return filterCountByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupId, classNameId, classPK, resourceClassNameId,
			names, descriptions, types, modes, languages, status, andOperator);
	}

	@Override
	public int filterCountByC_G_C_C_R_N_D_T_M_L_S(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, String[] names, String[] descriptions,
		String[] types, String[] modes, String[] languages, int status,
		boolean andOperator) {

		long[] groupIds = new long[] {groupId};
		long[] classNameIds = new long[] {classNameId};
		long[] classPKs = new long[] {classPK};

		return filterCountByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupIds, classNameIds, classPKs, resourceClassNameId,
			names, descriptions, types, modes, languages, status, andOperator);
	}

	@Override
	public int filterCountByC_G_C_C_R_N_D_T_M_L_S(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, String name, String description, String type,
		String mode, String language, int status, boolean andOperator) {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description, false);
		String[] types = CustomSQLUtil.keywords(type, false);
		String[] modes = CustomSQLUtil.keywords(mode, false);
		String[] languages = CustomSQLUtil.keywords(language, false);

		return filterCountByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupIds, classNameIds, classPKs, resourceClassNameId,
			names, descriptions, types, modes, languages, status, andOperator);
	}

	@Override
	public int filterCountByC_G_C_C_R_N_D_T_M_L_S(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, String[] names, String[] descriptions,
		String[] types, String[] modes, String[] languages, int status,
		boolean andOperator) {

		return doCountByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupIds, classNameIds, classPKs, resourceClassNameId,
			names, descriptions, types, modes, languages, status, andOperator,
			true);
	}

	@Override
	public int filterCountByG_SC_S(
		long groupId, long structureClassNameId, int status) {

		long[] groupIds = new long[] {groupId};
		long classNameId = PortalUtil.getClassNameId(DDMStructure.class);

		return doCountByG_C_SC_S(
			groupIds, classNameId, structureClassNameId, status, true);
	}

	@Override
	public int filterCountByG_SC_S(
		long[] groupIds, long structureClassNameId, int status) {

		long classNameId = PortalUtil.getClassNameId(DDMStructure.class);

		return doCountByG_C_SC_S(
			groupIds, classNameId, structureClassNameId, status, true);
	}

	@Override
	public List<DDMTemplate> filterFindByKeywords(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, String keywords, String type, String mode,
		int status, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {

		String[] names = null;
		String[] descriptions = null;
		String[] types = CustomSQLUtil.keywords(type, false);
		String[] modes = CustomSQLUtil.keywords(mode, false);
		String[] languages = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = CustomSQLUtil.keywords(keywords);
			descriptions = CustomSQLUtil.keywords(keywords, false);
			languages = CustomSQLUtil.keywords(keywords, false);
		}
		else {
			andOperator = true;
		}

		return filterFindByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupId, classNameId, classPK, resourceClassNameId,
			names, descriptions, types, modes, languages, status, andOperator,
			start, end, orderByComparator);
	}

	@Override
	public List<DDMTemplate> filterFindByKeywords(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, String keywords, String type, String mode,
		int status, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {

		String[] names = null;
		String[] descriptions = null;
		String[] types = CustomSQLUtil.keywords(type, false);
		String[] modes = CustomSQLUtil.keywords(mode, false);
		String[] languages = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = CustomSQLUtil.keywords(keywords);
			descriptions = CustomSQLUtil.keywords(keywords, false);
			languages = CustomSQLUtil.keywords(languages, false);
		}
		else {
			andOperator = true;
		}

		return filterFindByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupIds, classNameIds, classPKs, resourceClassNameId,
			names, descriptions, types, modes, languages, status, andOperator,
			start, end, orderByComparator);
	}

	@Override
	public List<DDMTemplate> filterFindByC_G_C_C_R_T_M_S(
		long companyId, long[] groupIds, long classNameId, long classPK,
		long resourceClassNameId, String type, String mode, int status,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator) {

		long[] classNameIds = new long[] {classNameId};
		long[] classPKs = new long[] {classPK};
		String[] types = CustomSQLUtil.keywords(type, false);
		String[] modes = CustomSQLUtil.keywords(mode, false);

		return doFindByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupIds, classNameIds, classPKs, resourceClassNameId,
			null, null, types, modes, null, status, true, start, end,
			orderByComparator, true);
	}

	@Override
	public List<DDMTemplate> filterFindByC_G_C_C_R_N_D_T_M_L_S(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, String name, String description, String type,
		String mode, String language, int status, boolean andOperator,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator) {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description, false);
		String[] types = CustomSQLUtil.keywords(type, false);
		String[] modes = CustomSQLUtil.keywords(mode, false);
		String[] languages = CustomSQLUtil.keywords(language, false);

		return filterFindByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupId, classNameId, classPK, resourceClassNameId,
			names, descriptions, types, modes, languages, status, andOperator,
			start, end, orderByComparator);
	}

	@Override
	public List<DDMTemplate> filterFindByC_G_C_C_R_N_D_T_M_L_S(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, String[] names, String[] descriptions,
		String[] types, String[] modes, String[] languages, int status,
		boolean andOperator, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {

		long[] groupIds = new long[] {groupId};
		long[] classNameIds = new long[] {classNameId};
		long[] classPKs = new long[] {classPK};

		return filterFindByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupIds, classNameIds, classPKs, resourceClassNameId,
			names, descriptions, types, modes, languages, status, andOperator,
			start, end, orderByComparator);
	}

	@Override
	public List<DDMTemplate> filterFindByC_G_C_C_R_N_D_T_M_L_S(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, String name, String description, String type,
		String mode, String language, int status, boolean andOperator,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator) {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description, false);
		String[] types = CustomSQLUtil.keywords(type, false);
		String[] modes = CustomSQLUtil.keywords(mode, false);
		String[] languages = CustomSQLUtil.keywords(language, false);

		return filterFindByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupIds, classNameIds, classPKs, resourceClassNameId,
			names, descriptions, types, modes, languages, status, andOperator,
			start, end, orderByComparator);
	}

	@Override
	public List<DDMTemplate> filterFindByC_G_C_C_R_N_D_T_M_L_S(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, String[] names, String[] descriptions,
		String[] types, String[] modes, String[] languages, int status,
		boolean andOperator, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {

		return doFindByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupIds, classNameIds, classPKs, resourceClassNameId,
			names, descriptions, types, modes, languages, status, andOperator,
			start, end, orderByComparator, true);
	}

	@Override
	public List<DDMTemplate> filterFindByG_SC_S(
		long groupId, long structureClassNameId, int status, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {

		long[] groupIds = new long[] {groupId};
		long classNameId = PortalUtil.getClassNameId(DDMStructure.class);

		return doFindByG_C_SC_S(
			groupIds, classNameId, structureClassNameId, status, start, end,
			orderByComparator, true);
	}

	@Override
	public List<DDMTemplate> filterFindByG_SC_S(
		long[] groupIds, long structureClassNameId, int status, int start,
		int end, OrderByComparator<DDMTemplate> orderByComparator) {

		long classNameId = PortalUtil.getClassNameId(DDMStructure.class);

		return doFindByG_C_SC_S(
			groupIds, classNameId, structureClassNameId, status, start, end,
			orderByComparator, true);
	}

	@Override
	public List<DDMTemplate> findByKeywords(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, String keywords, String type, String mode,
		int status, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {

		String[] names = null;
		String[] descriptions = null;
		String[] types = CustomSQLUtil.keywords(type, false);
		String[] modes = CustomSQLUtil.keywords(mode, false);
		String[] languages = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = CustomSQLUtil.keywords(keywords);
			descriptions = CustomSQLUtil.keywords(keywords, false);
			languages = CustomSQLUtil.keywords(keywords, false);
		}
		else {
			andOperator = true;
		}

		return findByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupId, classNameId, classPK, resourceClassNameId,
			names, descriptions, types, modes, languages, status, andOperator,
			start, end, orderByComparator);
	}

	@Override
	public List<DDMTemplate> findByKeywords(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, String keywords, String type, String mode,
		int status, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {

		String[] names = null;
		String[] descriptions = null;
		String[] types = CustomSQLUtil.keywords(type, false);
		String[] modes = CustomSQLUtil.keywords(mode, false);
		String[] languages = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = CustomSQLUtil.keywords(keywords);
			descriptions = CustomSQLUtil.keywords(keywords, false);
			languages = CustomSQLUtil.keywords(languages, false);
		}
		else {
			andOperator = true;
		}

		return findByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupIds, classNameIds, classPKs, resourceClassNameId,
			names, descriptions, types, modes, languages, status, andOperator,
			start, end, orderByComparator);
	}

	@Override
	public List<DDMTemplate> findByG_SC_S(
		long groupId, long structureClassNameId, int status, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {

		long[] groupIds = new long[] {groupId};
		long classNameId = PortalUtil.getClassNameId(DDMStructure.class);

		return doFindByG_C_SC_S(
			groupIds, classNameId, structureClassNameId, status, start, end,
			orderByComparator, false);
	}

	@Override
	public List<DDMTemplate> findByG_SC_S(
		long[] groupIds, long structureClassNameId, int status, int start,
		int end, OrderByComparator<DDMTemplate> orderByComparator) {

		long classNameId = PortalUtil.getClassNameId(DDMStructure.class);

		return doFindByG_C_SC_S(
			groupIds, classNameId, structureClassNameId, status, start, end,
			orderByComparator, false);
	}

	@Override
	public List<DDMTemplate> findByC_G_C_C_R_T_M_S(
		long companyId, long[] groupIds, long classNameId, long classPK,
		long resourceClassNameId, String type, String mode, int status,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator) {

		long[] classNameIds = new long[] {classNameId};
		long[] classPKs = new long[] {classPK};
		String[] types = CustomSQLUtil.keywords(type, false);
		String[] modes = CustomSQLUtil.keywords(mode, false);

		return doFindByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupIds, classNameIds, classPKs, resourceClassNameId,
			null, null, types, modes, null, status, true, start, end,
			orderByComparator, false);
	}

	@Override
	public List<DDMTemplate> findByC_G_C_C_R_N_D_T_M_L_S(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, String name, String description, String type,
		String mode, String language, int status, boolean andOperator,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator) {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description, false);
		String[] types = CustomSQLUtil.keywords(type, false);
		String[] modes = CustomSQLUtil.keywords(mode, false);
		String[] languages = CustomSQLUtil.keywords(language, false);

		return findByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupId, classNameId, classPK, resourceClassNameId,
			names, descriptions, types, modes, languages, status, andOperator,
			start, end, orderByComparator);
	}

	@Override
	public List<DDMTemplate> findByC_G_C_C_R_N_D_T_M_L_S(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, String[] names, String[] descriptions,
		String[] types, String[] modes, String[] languages, int status,
		boolean andOperator, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {

		long[] groupIds = new long[] {groupId};
		long[] classNameIds = new long[] {classNameId};
		long[] classPKs = new long[] {classPK};

		return doFindByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupIds, classNameIds, classPKs, resourceClassNameId,
			names, descriptions, types, modes, languages, status, andOperator,
			start, end, orderByComparator, false);
	}

	@Override
	public List<DDMTemplate> findByC_G_C_C_R_N_D_T_M_L_S(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, String name, String description, String type,
		String mode, String language, int status, boolean andOperator,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator) {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description, false);
		String[] types = CustomSQLUtil.keywords(type, false);
		String[] modes = CustomSQLUtil.keywords(mode, false);
		String[] languages = CustomSQLUtil.keywords(language, false);

		return findByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupIds, classNameIds, classPKs, resourceClassNameId,
			names, descriptions, types, modes, languages, status, andOperator,
			start, end, orderByComparator);
	}

	@Override
	public List<DDMTemplate> findByC_G_C_C_R_N_D_T_M_L_S(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, String[] names, String[] descriptions,
		String[] types, String[] modes, String[] languages, int status,
		boolean andOperator, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {

		return doFindByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupIds, classNameIds, classPKs, resourceClassNameId,
			names, descriptions, types, modes, languages, status, andOperator,
			start, end, orderByComparator, false);
	}

	protected int doCountByG_C_SC_S(
		long[] groupIds, long classNameId, long structureClassNameId,
		int status, boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_C_SC_S);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql,
					DDMTemplatePermission.getTemplateModelResourceName(
						structureClassNameId),
					"DDMTemplate.templateId", groupIds);
			}

			sql = StringUtil.replace(
				sql, "[$GROUP_ID$]", getGroupIds(groupIds));
			sql = StringUtil.replace(sql, "[$STATUS$]", getStatus(status));

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (groupIds != null) {
				qPos.add(groupIds);
			}

			qPos.add(classNameId);
			qPos.add(structureClassNameId);

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
			}

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected int doCountByC_G_C_C_R_N_D_T_M_L_S(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, String[] names, String[] descriptions,
		String[] types, String[] modes, String[] languages, int status,
		boolean andOperator, boolean inlineSQLHelper) {

		names = CustomSQLUtil.keywords(names);
		descriptions = CustomSQLUtil.keywords(descriptions, false);
		types = CustomSQLUtil.keywords(types, false);
		modes = CustomSQLUtil.keywords(modes, false);
		languages = CustomSQLUtil.keywords(languages, false);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_C_G_C_C_R_N_D_T_M_L_S);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql,
					DDMTemplatePermission.getTemplateModelResourceName(
						resourceClassNameId),
					"DDMTemplate.templateId", groupIds);
			}

			sql = StringUtil.replace(
				sql, "[$GROUP_ID$]", getGroupIds(groupIds));
			sql = StringUtil.replace(
				sql, "[$CLASSNAME_ID$]", getClassNameIds(classNameIds));
			sql = StringUtil.replace(
				sql, "[$CLASS_PK$]", getClassPKs(classPKs));
			sql = StringUtil.replace(sql, "[$STATUS$]", getStatus(status));
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(CAST_TEXT(name))", StringPool.LIKE, false, names);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "description", StringPool.LIKE, false, descriptions);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "type", StringPool.LIKE, false, types);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "mode", StringPool.LIKE, false, modes);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "language", StringPool.LIKE, true, languages);
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (groupIds != null) {
				qPos.add(groupIds);
			}

			if (classNameIds != null) {
				qPos.add(classNameIds);
			}

			if (classPKs != null) {
				qPos.add(classPKs);
			}

			qPos.add(resourceClassNameId);
			qPos.add(names, 2);
			qPos.add(descriptions, 2);
			qPos.add(types, 2);
			qPos.add(modes, 2);
			qPos.add(languages, 2);

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
			}

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<DDMTemplate> doFindByG_C_SC_S(
		long[] groupIds, long classNameId, long structureClassNameId,
		int status, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_C_SC_S);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql,
					DDMTemplatePermission.getTemplateModelResourceName(
						structureClassNameId),
					"DDMTemplate.templateId", groupIds);
			}

			sql = StringUtil.replace(
				sql, "[$GROUP_ID$]", getGroupIds(groupIds));
			sql = StringUtil.replace(sql, "[$STATUS$]", getStatus(status));

			if (orderByComparator != null) {
				sql = CustomSQLUtil.replaceOrderBy(sql, orderByComparator);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("DDMTemplate", DDMTemplateImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			if (groupIds != null) {
				qPos.add(groupIds);
			}

			qPos.add(classNameId);
			qPos.add(structureClassNameId);

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
			}

			return (List<DDMTemplate>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<DDMTemplate> doFindByC_G_C_C_R_N_D_T_M_L_S(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, String[] names, String[] descriptions,
		String[] types, String[] modes, String[] languages, int status,
		boolean andOperator, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator,
		boolean inlineSQLHelper) {

		names = CustomSQLUtil.keywords(names);
		descriptions = CustomSQLUtil.keywords(descriptions, false);
		types = CustomSQLUtil.keywords(types, false);
		modes = CustomSQLUtil.keywords(modes, false);
		languages = CustomSQLUtil.keywords(languages, false);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_G_C_C_R_N_D_T_M_L_S);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql,
					DDMTemplatePermission.getTemplateModelResourceName(
						resourceClassNameId),
					"DDMTemplate.templateId", groupIds);
			}

			sql = StringUtil.replace(
				sql, "[$GROUP_ID$]", getGroupIds(groupIds));
			sql = StringUtil.replace(
				sql, "[$CLASSNAME_ID$]", getClassNameIds(classNameIds));
			sql = StringUtil.replace(
				sql, "[$CLASS_PK$]", getClassPKs(classPKs));
			sql = StringUtil.replace(sql, "[$STATUS$]", getStatus(status));
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(CAST_TEXT(name))", StringPool.LIKE, false, names);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "description", StringPool.LIKE, false, descriptions);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "type", StringPool.LIKE, false, types);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "mode", StringPool.LIKE, false, modes);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "language", StringPool.LIKE, true, languages);
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			if (orderByComparator != null) {
				sql = CustomSQLUtil.replaceOrderBy(sql, orderByComparator);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("DDMTemplate", DDMTemplateImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (groupIds != null) {
				qPos.add(groupIds);
			}

			if (classNameIds != null) {
				qPos.add(classNameIds);
			}

			if (classPKs != null) {
				qPos.add(classPKs);
			}

			qPos.add(resourceClassNameId);
			qPos.add(names, 2);
			qPos.add(descriptions, 2);
			qPos.add(types, 2);
			qPos.add(modes, 2);
			qPos.add(languages, 2);

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
			}

			return (List<DDMTemplate>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getClassNameIds(long[] classNameIds) {
		if (ArrayUtil.isEmpty(classNameIds)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(classNameIds.length * 2);

		sb.append(StringPool.OPEN_PARENTHESIS);

		for (int i = 0; i < classNameIds.length; i++) {
			sb.append("classNameId = ?");

			if ((i + 1) < classNameIds.length) {
				sb.append(" OR ");
			}
		}

		sb.append(") AND");

		return sb.toString();
	}

	protected String getClassPKs(long[] classPKs) {
		if (ArrayUtil.isEmpty(classPKs)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(classPKs.length * 2);

		sb.append(StringPool.OPEN_PARENTHESIS);

		for (int i = 0; i < classPKs.length; i++) {
			sb.append("classPK = ?");

			if ((i + 1) < classPKs.length) {
				sb.append(" OR ");
			}
		}

		sb.append(") AND");

		return sb.toString();
	}

	protected String getGroupIds(long[] groupIds) {
		if (ArrayUtil.isEmpty(groupIds)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(groupIds.length * 2);

		sb.append(StringPool.OPEN_PARENTHESIS);

		for (int i = 0; i < groupIds.length; i++) {
			sb.append("groupId = ?");

			if ((i + 1) < groupIds.length) {
				sb.append(" OR ");
			}
		}

		sb.append(") AND");

		return sb.toString();
	}

	protected String getStatus(int status) {
		if (status == WorkflowConstants.STATUS_ANY) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(5);

		sb.append("AND EXISTS (SELECT 1 FROM DDMTemplateVersion WHERE ");
		sb.append("(DDMTemplateVersion.templateId = ");
		sb.append("DDMTemplate.templateId) AND ");
		sb.append("(DDMTemplateVersion.version = DDMTemplate.version) AND ");
		sb.append("(DDMTemplateVersion.status = ?))");

		return sb.toString();
	}

}
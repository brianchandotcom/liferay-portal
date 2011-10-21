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

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMTemplateImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @author Eduardo Lundgren
 * @author Connor McKay
 */
public class DDMTemplateFinderImpl
	extends BasePersistenceImpl<DDMTemplate> implements DDMTemplateFinder {

	public static String COUNT_BY_C_G_S_N_D_T_M_L =
		DDMTemplateFinder.class.getName() + ".countByC_G_S_N_D_T_M_L";

	public static String FIND_BY_C_G_S_N_D_T_M_L =
		DDMTemplateFinder.class.getName() + ".findByC_G_S_N_D_T_M_L";

	public int countByKeywords(
			long companyId, long groupId, long structureId, String keywords,
			String type, String mode)
		throws SystemException {

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

		return countByC_G_S_N_D_T_M_L(
			companyId, groupId, structureId, names, descriptions, types, modes,
			languages, andOperator);
	}

	public int countByC_G_S_N_D_T_M_L(
			long companyId, long groupId, long structureId, String name,
			String description, String type, String mode, String language,
			boolean andOperator)
		throws SystemException {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description, false);
		String[] types = CustomSQLUtil.keywords(type, false);
		String[] modes = CustomSQLUtil.keywords(mode, false);
		String[] languages = CustomSQLUtil.keywords(language, false);

		return countByC_G_S_N_D_T_M_L(
			companyId, groupId, structureId, names, descriptions, types, modes,
			languages, andOperator);
	}

	public int countByC_G_S_N_D_T_M_L(
			long companyId, long groupId, long structureId, String[] names,
			String[] descriptions, String[] types, String[] modes,
			String[] languages, boolean andOperator)
		throws SystemException {

		names = CustomSQLUtil.keywords(names);
		descriptions = CustomSQLUtil.keywords(descriptions, false);
		types = CustomSQLUtil.keywords(types, false);
		modes = CustomSQLUtil.keywords(modes, false);
		languages = CustomSQLUtil.keywords(languages, false);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_C_G_S_N_D_T_M_L);

			if (groupId <= 0) {
				sql = StringUtil.replace(sql, "(groupId = ?) AND", "");
			}

			if (structureId <= 0) {
				sql = StringUtil.replace(sql, "(structureId = ?) AND", "");
			}

			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(name)", StringPool.LIKE, false, names);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "description", StringPool.LIKE, false, descriptions);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "type", StringPool.LIKE, false, types);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "mode", StringPool.LIKE, false, modes);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "language", StringPool.LIKE, false, languages);
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (groupId > 0) {
				qPos.add(groupId);
			}

			if (structureId > 0) {
				qPos.add(structureId);
			}

			qPos.add(names, 2);
			qPos.add(descriptions, 2);
			qPos.add(types, 2);
			qPos.add(modes, 2);
			qPos.add(languages, 2);

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

	public List<DDMTemplate> findByKeywords(
			long companyId, long groupId, long structureId, String keywords,
			String type, String mode, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

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

		return findByC_G_S_N_D_T_M_L(
			companyId, groupId, structureId, names, descriptions, types, modes,
			languages, andOperator, start, end, orderByComparator);
	}

	public List<DDMTemplate> findByC_G_S_N_D_T_M_L(
			long companyId, long groupId, long structureId, String name,
			String description, String type, String mode, String language,
			boolean andOperator, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description, false);
		String[] types = CustomSQLUtil.keywords(type, false);
		String[] modes = CustomSQLUtil.keywords(mode, false);
		String[] languages = CustomSQLUtil.keywords(language, false);

		return findByC_G_S_N_D_T_M_L(
			companyId, groupId, structureId, names, descriptions, types, modes,
			languages, andOperator, start, end, orderByComparator);
	}

	public List<DDMTemplate> findByC_G_S_N_D_T_M_L(
			long companyId, long groupId, long structureId, String[] names,
			String[] descriptions, String[] types, String[] modes,
			String[] languages, boolean andOperator, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		names = CustomSQLUtil.keywords(names);
		descriptions = CustomSQLUtil.keywords(descriptions, false);
		types = CustomSQLUtil.keywords(types, false);
		modes = CustomSQLUtil.keywords(modes, false);
		languages = CustomSQLUtil.keywords(languages, false);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_G_S_N_D_T_M_L);

			if (groupId <= 0) {
				sql = StringUtil.replace(sql, "(groupId = ?) AND", "");
			}

			if (structureId <= 0) {
				sql = StringUtil.replace(sql, "(structureId = ?) AND", "");
			}

			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(name)", StringPool.LIKE, false, names);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "description", StringPool.LIKE, false, descriptions);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "type", StringPool.LIKE, false, types);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "mode", StringPool.LIKE, false, modes);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "language", StringPool.LIKE, false, languages);
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			if (orderByComparator != null) {
				String orderByFields = StringUtil.merge(
					orderByComparator.getOrderByFields(), StringPool.COMMA);

				sql = StringUtil.replace(
					sql, "templateId DESC", orderByFields.concat(" DESC"));
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("DDMTemplate", DDMTemplateImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (groupId > 0) {
				qPos.add(groupId);
			}

			if (structureId > 0) {
				qPos.add(structureId);
			}

			qPos.add(names, 2);
			qPos.add(descriptions, 2);
			qPos.add(types, 2);
			qPos.add(modes, 2);
			qPos.add(languages, 2);

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

}
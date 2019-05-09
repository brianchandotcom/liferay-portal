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

package com.liferay.segments.criteria.extension.sample.internal.criteria.contributor;

import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.criteria.extension.sample.internal.odata.entity.KBArticleEntityModel;
import com.liferay.segments.field.Field;
import com.liferay.segments.odata.retriever.ODataRetriever;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = {
		"segments.criteria.contributor.key=" + UserKBArticleSegmentsCriteriaContributor.KEY,
		"segments.criteria.contributor.model.class.name=com.liferay.portal.kernel.model.User",
		"segments.criteria.contributor.priority:Integer=70"
	},
	service = SegmentsCriteriaContributor.class
)
public class UserKBArticleSegmentsCriteriaContributor
	implements SegmentsCriteriaContributor {

	public static final String KEY = "user-kb-article";

	@Override
	public void contribute(
		Criteria criteria, String filterString,
		Criteria.Conjunction conjunction) {

		criteria.addCriterion(getKey(), getType(), filterString, conjunction);

		long companyId = CompanyThreadLocal.getCompanyId();
		String newFilterString = null;

		try {
			StringBundler sb = new StringBundler();

			List<KBArticle> kbArticles = _oDataRetriever.getResults(
				companyId, filterString, LocaleUtil.getDefault(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (int i = 0; i < kbArticles.size(); i++) {
				KBArticle kbArticle = kbArticles.get(i);

				sb.append("(userId eq '");
				sb.append(kbArticle.getUserId());
				sb.append("')");

				if (i < (kbArticles.size() - 1)) {
					sb.append(" or ");
				}
			}

			newFilterString = sb.toString();
		}
		catch (PortalException pe) {
			_log.error(
				com.liferay.petra.string.StringBundler.concat(
					"Unable to evaluate criteria ", criteria, " with filter ",
					filterString, " and conjunction ", conjunction.getValue()),
				pe);
		}

		if (Validator.isNull(newFilterString)) {
			newFilterString = "(userId eq '0')";
		}

		criteria.addFilter(getType(), newFilterString, conjunction);
	}

	@Override
	public EntityModel getEntityModel() {
		return _entityModel;
	}

	@Override
	public String getEntityName() {
		return KBArticleEntityModel.NAME;
	}

	@Override
	public List<Field> getFields(PortletRequest portletRequest) {
		return Collections.singletonList(
			new Field(
				"title",
				LanguageUtil.get(_portal.getLocale(portletRequest), "title"),
				"string"));
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public Criteria.Type getType() {
		return Criteria.Type.MODEL;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserKBArticleSegmentsCriteriaContributor.class);

	private static final EntityModel _entityModel = new KBArticleEntityModel();

	@Reference(
		target = "(model.class.name=com.liferay.knowledge.base.model.KBArticle)"
	)
	private ODataRetriever<KBArticle> _oDataRetriever;

	@Reference
	private Portal _portal;

}
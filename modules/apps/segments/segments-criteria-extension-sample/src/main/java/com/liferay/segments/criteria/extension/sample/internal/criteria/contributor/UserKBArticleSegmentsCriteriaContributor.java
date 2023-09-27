/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.criteria.extension.sample.internal.criteria.contributor;

import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.criteria.extension.sample.internal.odata.entity.KBArticleEntityModel;
import com.liferay.segments.criteria.mapper.SegmentsCriteriaJSONObjectMapper;
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
		catch (PortalException portalException) {
			_log.error(
				StringBundler.concat(
					"Unable to evaluate criteria ", criteria, " with filter ",
					filterString, " and conjunction ", conjunction.getValue()),
				portalException);
		}

		if (Validator.isNull(newFilterString)) {
			newFilterString = "(userId eq '0')";
		}

		criteria.addFilter(getType(), newFilterString, conjunction);
	}

	@Override
	public JSONObject getCriteriaJSONObject(Criteria criteria)
		throws Exception {

		return _segmentsCriteriaJSONObjectMapper.toJSONObject(criteria, this);
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
				_language.get(_portal.getLocale(portletRequest), "title"),
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

	@Reference
	private Language _language;

	@Reference(
		target = "(model.class.name=com.liferay.knowledge.base.model.KBArticle)"
	)
	private ODataRetriever<KBArticle> _oDataRetriever;

	@Reference
	private Portal _portal;

	@Reference(target = "(segments.criteria.mapper.key=odata)")
	private SegmentsCriteriaJSONObjectMapper _segmentsCriteriaJSONObjectMapper;

}
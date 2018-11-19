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

package com.liferay.segments.internal.criteria.contributor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.odata.retriever.ODataRetriever;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(
	immediate = true,
	property = {
		"segments.criteria.contributor.key=" + OrganizationSegmentsCriteriaContributor.KEY,
		"segments.criteria.contributor.model.class.name=com.liferay.portal.kernel.model.User",
		"segments.criteria.contributor.priority:Integer=60"
	},
	service = SegmentsCriteriaContributor.class
)
public class OrganizationSegmentsCriteriaContributor
	implements SegmentsCriteriaContributor {

	public static final String KEY = "organization";

	@Override
	public void contribute(
		Criteria criteria, String filterString,
		Criteria.Conjunction conjunction) {

		criteria.addCriterion(getKey(), filterString, conjunction);

		long companyId = CompanyThreadLocal.getCompanyId();

		try {
			List<Organization> organizations = _oDataRetriever.getResults(
				companyId, filterString, LocaleUtil.getDefault(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			StringBundler sb = new StringBundler();

			for (int i = 0; i < organizations.size(); i++) {
				Organization organization = organizations.get(i);

				sb.append("contains");
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append("organizationIds");
				sb.append(StringPool.COMMA);
				sb.append(StringPool.APOSTROPHE);
				sb.append(organization.getOrganizationId());
				sb.append(StringPool.APOSTROPHE);
				sb.append(StringPool.CLOSE_PARENTHESIS);

				if (i < (organizations.size() - 1)) {
					sb.append(" or ");
				}
			}

			criteria.addFilter(sb.toString(), conjunction);
		}
		catch (PortalException pe) {
			_log.error(
				com.liferay.petra.string.StringBundler.concat(
					"Error evaluating criteria ", criteria,
					" with filterString ", filterString, " and conjunction ",
					conjunction.getValue()),
				pe);
		}
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		return LanguageUtil.get(resourceBundle, getKey());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrganizationSegmentsCriteriaContributor.class);

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.model.Organization)"
	)
	private ODataRetriever<Organization> _oDataRetriever;

}
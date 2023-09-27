/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.internal.resource.v1_0.util;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.kernel.search.generic.MultiMatchQuery;
import com.liferay.portal.kernel.search.generic.WildcardQueryImpl;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchUtil extends com.liferay.portal.vulcan.util.SearchUtil {

	public static void processSXPBooleanQuery(
			AcceptLanguage acceptLanguage, BooleanQuery booleanQuery1,
			String search)
		throws Exception {

		if (Validator.isBlank(search)) {
			return;
		}

		BooleanQuery booleanQuery2 = new BooleanQueryImpl() {
			{
				MultiMatchQuery multiMatchQuery = new MultiMatchQuery(search);

				multiMatchQuery.addFields(
					Arrays.asList(
						LocalizationUtil.getLocalizedName(
							Field.DESCRIPTION,
							acceptLanguage.getPreferredLanguageId()),
						LocalizationUtil.getLocalizedName(
							Field.TITLE,
							acceptLanguage.getPreferredLanguageId())));
				multiMatchQuery.setOperator(MatchQuery.Operator.AND);
				multiMatchQuery.setType(MultiMatchQuery.Type.PHRASE_PREFIX);

				add(multiMatchQuery, BooleanClauseOccur.SHOULD);

				WildcardQuery wildcardQuery = new WildcardQueryImpl(
					Field.USER_NAME, search + "*");

				add(wildcardQuery, BooleanClauseOccur.SHOULD);
			}
		};

		booleanQuery1.add(booleanQuery2, BooleanClauseOccur.MUST);
	}

}
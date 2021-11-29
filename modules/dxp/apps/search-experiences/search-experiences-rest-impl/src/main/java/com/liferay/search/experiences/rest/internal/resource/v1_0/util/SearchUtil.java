/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
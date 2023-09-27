/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.spi.model.query.contributor;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.query.QueryHelper;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = KeywordQueryContributor.class)
public class AssetTagNamesKeywordQueryContributor
	implements KeywordQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		SearchContext searchContext =
			keywordQueryContributorHelper.getSearchContext();

		Locale locale = _getLocale(searchContext);

		_queryHelper.addSearchTerm(
			booleanQuery, searchContext,
			_localization.getLocalizedName(
				Field.ASSET_TAG_NAMES, LocaleUtil.toLanguageId(locale)),
			false);
	}

	private long _getGroupId(SearchContext searchContext) {
		Layout layout = searchContext.getLayout();

		if (layout != null) {
			return layout.getGroupId();
		}

		long[] groupIds = searchContext.getGroupIds();

		if (ArrayUtil.isNotEmpty(groupIds)) {
			return GetterUtil.getLong(groupIds[0]);
		}

		return 0;
	}

	private Locale _getLocale(SearchContext searchContext) {
		long groupId = _getGroupId(searchContext);

		if (groupId > 0) {
			return _getSiteDefaultLocale(groupId);
		}

		return searchContext.getLocale();
	}

	private Locale _getSiteDefaultLocale(long groupId) {
		try {
			return _portal.getSiteDefaultLocale(groupId);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Reference
	private Localization _localization;

	@Reference
	private Portal _portal;

	@Reference
	private QueryHelper _queryHelper;

}
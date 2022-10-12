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

package com.liferay.commerce.machine.learning.internal.recommendation.data.source;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.machine.learning.recommendation.FrequentPatternCommerceMLRecommendation;
import com.liferay.commerce.machine.learning.recommendation.FrequentPatternCommerceMLRecommendationManager;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.constants.CPWebKeys;
import com.liferay.commerce.product.data.source.CPDataSource;
import com.liferay.commerce.product.data.source.CPDataSourceResult;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	immediate = true,
	property = "commerce.product.data.source.name=" + FrequentPatternCommerceMLRecommendationCPDataSourceImpl.NAME,
	service = CPDataSource.class
)
public class FrequentPatternCommerceMLRecommendationCPDataSourceImpl
	extends BaseCommerceMLRecommendationCPDataSource {

	public static final String NAME =
		"frequentPatternCommerceMLRecommendationDataSource";

	@Override
	public String getLabel(Locale locale) {
		return _language.get(
			getResourceBundle(locale), "frequent-pattern-recommendations");
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public CPDataSourceResult getResult(
			HttpServletRequest httpServletRequest, int start, int end)
		throws Exception {

		CommerceAccount commerceAccount =
			commerceAccountHelper.getCurrentCommerceAccount(httpServletRequest);

		if (commerceAccount == null) {
			return new CPDataSourceResult(Collections.emptyList(), 0);
		}

		CPCatalogEntry cpCatalogEntry =
			(CPCatalogEntry)httpServletRequest.getAttribute(
				CPWebKeys.CP_CATALOG_ENTRY);

		if (cpCatalogEntry == null) {
			return new CPDataSourceResult(Collections.emptyList(), 0);
		}

		List<FrequentPatternCommerceMLRecommendation>
			frequentPatternCommerceMLRecommendations =
				_frequentPatternCommerceMLRecommendationManager.
					getFrequentPatternCommerceMLRecommendations(
						portal.getCompanyId(httpServletRequest),
						new long[] {cpCatalogEntry.getCPDefinitionId()});

		if (frequentPatternCommerceMLRecommendations.isEmpty()) {
			return new CPDataSourceResult(Collections.emptyList(), 0);
		}

		List<CPCatalogEntry> cpCatalogEntries = new ArrayList<>();

		List<FrequentPatternCommerceMLRecommendation>
			frequentPatternCommerceMLRecommendationList = ListUtil.subList(
				frequentPatternCommerceMLRecommendations, start, end);

		for (FrequentPatternCommerceMLRecommendation
				frequentPatternCommerceMLRecommendation :
					frequentPatternCommerceMLRecommendationList) {

			long recommendedEntryClassPK =
				frequentPatternCommerceMLRecommendation.
					getRecommendedEntryClassPK();

			if (_log.isTraceEnabled()) {
				_log.trace(
					StringBundler.concat(
						"Recommended entry ", recommendedEntryClassPK,
						" has score ",
						frequentPatternCommerceMLRecommendation.getScore()));
			}

			try {
				long groupId = portal.getScopeGroupId(httpServletRequest);

				CPCatalogEntry recommendedCPCatalogEntry =
					cpDefinitionHelper.getCPCatalogEntry(
						commerceAccount.getCommerceAccountId(), groupId,
						recommendedEntryClassPK,
						portal.getLocale(httpServletRequest));

				cpCatalogEntries.add(recommendedCPCatalogEntry);
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException);
				}
			}
		}

		return new CPDataSourceResult(
			cpCatalogEntries, frequentPatternCommerceMLRecommendations.size());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FrequentPatternCommerceMLRecommendationCPDataSourceImpl.class);

	@Reference(unbind = "-")
	private FrequentPatternCommerceMLRecommendationManager
		_frequentPatternCommerceMLRecommendationManager;

	@Reference
	private Language _language;

}
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

package com.liferay.segments.asah.connector.internal.asset.list.asset.entry.query.processor;

import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.list.asset.entry.query.processor.AssetListAssetEntryQueryProcessor;
import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.asah.connector.internal.provider.AsahInterestTermProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sarai Díaz
 */
@Component(
	property = "javax.portlet.name=" + AssetListPortletKeys.ASSET_LIST,
	service = AssetListAssetEntryQueryProcessor.class
)
public class AsahInterestTermAssetListAssetEntryQueryProcessor
	implements AssetListAssetEntryQueryProcessor {

	@Override
	public void processAssetEntryQuery(
		long companyId, String userId, UnicodeProperties unicodeProperties,
		AssetEntryQuery assetEntryQuery) {

		if (Validator.isNull(userId)) {
			return;
		}

		boolean enableContentRecommendation = GetterUtil.getBoolean(
			unicodeProperties.getProperty("enableContentRecommendation"));

		if (!enableContentRecommendation ||
			(_asahInterestTermProvider == null)) {

			return;
		}

		String[] interestTerms = _asahInterestTermProvider.getInterestTerms(
			companyId, userId);

		if (interestTerms.length == 0) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Adding interest terms \"", StringUtil.merge(interestTerms),
					"\" to asset query for user ID ", userId));
		}

		assetEntryQuery.setAnyKeywords(interestTerms);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AsahInterestTermAssetListAssetEntryQueryProcessor.class);

	@Reference
	private AsahInterestTermProvider _asahInterestTermProvider;

}
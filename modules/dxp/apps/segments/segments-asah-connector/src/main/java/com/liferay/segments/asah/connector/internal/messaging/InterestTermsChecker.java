/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.messaging;

import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.segments.asah.connector.internal.cache.AsahInterestTermCache;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClient;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClientImpl;
import com.liferay.segments.asah.connector.internal.client.model.Results;
import com.liferay.segments.asah.connector.internal.client.model.Topic;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sarai Díaz
 */
@Component(service = InterestTermsChecker.class)
public class InterestTermsChecker {

	public void checkInterestTerms(long companyId, String userId)
		throws Exception {

		if ((_asahInterestTermCache.getInterestTerms(userId) != null) ||
			!_analyticsSettingsManager.isAnalyticsEnabled(companyId)) {

			return;
		}

		AsahFaroBackendClient asahFaroBackendClient =
			_asahFaroBackendClientDCLSingleton.getSingleton(
				() -> new AsahFaroBackendClientImpl(
					_analyticsSettingsManager, _http));

		Results<Topic> interestTermsResults =
			asahFaroBackendClient.getInterestTermsResults(companyId, userId);

		if (interestTermsResults == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get interest terms for user ID " + userId);
			}

			_asahInterestTermCache.putInterestTerms(userId, new String[0]);

			return;
		}

		List<Topic> topics = interestTermsResults.getItems();

		if (ListUtil.isEmpty(topics)) {
			if (_log.isDebugEnabled()) {
				_log.debug("No interest terms received for user ID " + userId);
			}

			_asahInterestTermCache.putInterestTerms(userId, new String[0]);

			return;
		}

		List<String> termsList = new ArrayList<>();

		for (Topic topic : topics) {
			for (Topic.TopicTerm topicTerm : topic.getTerms()) {
				termsList.add(topicTerm.getKeyword());
			}
		}

		String[] terms = termsList.toArray(new String[0]);

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Interest terms \"", StringUtil.merge(terms),
					"\" received for user ID ", userId));
		}

		_asahInterestTermCache.putInterestTerms(userId, terms);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InterestTermsChecker.class);

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	private final DCLSingleton<AsahFaroBackendClient>
		_asahFaroBackendClientDCLSingleton = new DCLSingleton<>();

	@Reference
	private AsahInterestTermCache _asahInterestTermCache;

	@Reference
	private Http _http;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

}
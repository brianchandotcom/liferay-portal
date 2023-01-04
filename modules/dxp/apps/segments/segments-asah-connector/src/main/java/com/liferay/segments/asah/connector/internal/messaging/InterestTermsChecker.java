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

package com.liferay.segments.asah.connector.internal.messaging;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.segments.asah.connector.internal.cache.AsahInterestTermCache;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClient;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClientImpl;
import com.liferay.segments.asah.connector.internal.client.JSONWebServiceClient;
import com.liferay.segments.asah.connector.internal.client.model.Results;
import com.liferay.segments.asah.connector.internal.client.model.Topic;
import com.liferay.segments.asah.connector.internal.util.AsahUtil;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sarai Díaz
 */
@Component(service = InterestTermsChecker.class)
public class InterestTermsChecker {

	public void checkInterestTerms(long companyId, String userId) {
		if ((_asahInterestTermCache.getInterestTerms(userId) != null) ||
			!AsahUtil.isAnalyticsEnabled(companyId)) {

			return;
		}

		Results<Topic> interestTermsResults =
			_asahFaroBackendClient.getInterestTermsResults(companyId, userId);

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

	@Activate
	protected void activate() {
		_asahFaroBackendClient = new AsahFaroBackendClientImpl(
			_jsonWebServiceClient);
	}

	@Deactivate
	protected void deactivate() {
		_asahFaroBackendClient = null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InterestTermsChecker.class);

	private AsahFaroBackendClient _asahFaroBackendClient;

	@Reference
	private AsahInterestTermCache _asahInterestTermCache;

	@Reference
	private JSONWebServiceClient _jsonWebServiceClient;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

}
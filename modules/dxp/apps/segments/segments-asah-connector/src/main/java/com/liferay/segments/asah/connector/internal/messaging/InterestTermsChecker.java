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

package com.liferay.segments.asah.connector.internal.messaging;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.segments.asah.connector.internal.cache.AsahInterestTermCache;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClient;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClientFactory;
import com.liferay.segments.asah.connector.internal.client.model.Results;
import com.liferay.segments.asah.connector.internal.client.model.Topic;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sarai Díaz
 */
@Component(immediate = true, service = InterestTermsChecker.class)
public class InterestTermsChecker {

	public void checkInterestTerms(String userId) {
		if (_asahInterestTermCache.getInterestTerms(userId) != null) {
			return;
		}

		if (_asahFaroBackendClient == null) {
			Optional<AsahFaroBackendClient> asahFaroBackendClientOptional =
				_asahFaroBackendClientFactory.createAsahFaroBackendClient();

			if (!asahFaroBackendClientOptional.isPresent()) {
				return;
			}

			_asahFaroBackendClient = asahFaroBackendClientOptional.get();
		}

		Results<Topic> interestTermsResults =
			_asahFaroBackendClient.getInterestTermsResults(userId);

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

		Stream<Topic> stream = topics.stream();

		String[] terms = stream.flatMap(
			topic -> {
				List<Topic.TopicTerm> topicTerms = topic.getTerms();

				return topicTerms.stream();
			}
		).map(
			Topic.TopicTerm::getKeyword
		).toArray(
			String[]::new
		);

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

	private AsahFaroBackendClient _asahFaroBackendClient;

	@Reference
	private AsahFaroBackendClientFactory _asahFaroBackendClientFactory;

	@Reference
	private AsahInterestTermCache _asahInterestTermCache;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

}
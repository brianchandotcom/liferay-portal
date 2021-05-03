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

package com.liferay.portal.search.tuning.blueprints.content.analysis.internal.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.search.tuning.blueprints.content.analysis.request.ContentAnalysisRequest;
import com.liferay.portal.search.tuning.blueprints.content.analysis.response.CategoryAnalysisResponse;
import com.liferay.portal.search.tuning.blueprints.content.analysis.response.ModerationAnalysisResponse;
import com.liferay.portal.search.tuning.blueprints.content.analysis.service.ContentAnalysisService;
import com.liferay.portal.search.tuning.blueprints.content.analysis.spi.analyzer.CategoryAnalyzer;
import com.liferay.portal.search.tuning.blueprints.content.analysis.spi.analyzer.ModerationAnalyzer;
import com.liferay.portal.search.tuning.blueprints.util.component.ServiceComponentReference;
import com.liferay.portal.search.tuning.blueprints.util.component.ServiceComponentReferenceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = ContentAnalysisService.class)
public class ContentAnalysisServiceImpl implements ContentAnalysisService {

	@Override
	public Optional<List<CategoryAnalysisResponse>> analyzeCategories(
		ContentAnalysisRequest contentAnalysisRequest) {

		if (_categoryAnalyzers.size() == 0) {
			return Optional.empty();
		}

		List<CategoryAnalysisResponse> responses = new ArrayList<>();

		for (Map.Entry<String, ServiceComponentReference<CategoryAnalyzer>>
				entry : _categoryAnalyzers.entrySet()) {

			try {
				ServiceComponentReference<CategoryAnalyzer> value =
					entry.getValue();

				CategoryAnalyzer categoryAnalyzer = value.getServiceComponent();

				Optional<CategoryAnalysisResponse> optional =
					categoryAnalyzer.analyze(contentAnalysisRequest);

				if (!optional.isPresent()) {
					if (_log.isDebugEnabled()) {
						StringBundler sb = new StringBundler(3);

						sb.append("Category analyzer ");
						sb.append(categoryAnalyzer.getClass());
						sb.append(" gave an empty response");

						_log.debug(sb.toString());
					}

					continue;
				}

				responses.add(optional.get());
			}
			catch (IllegalArgumentException illegalArgumentException) {
				_log.error(
					illegalArgumentException.getMessage(),
					illegalArgumentException);
			}
		}

		return Optional.of(responses);
	}

	@Override
	public Optional<List<ModerationAnalysisResponse>> analyzeModeration(
		ContentAnalysisRequest contentAnalysisRequest) {

		if (_moderationAnalyzers.size() == 0) {
			return Optional.empty();
		}

		List<ModerationAnalysisResponse> responses = new ArrayList<>();

		for (Map.Entry<String, ServiceComponentReference<ModerationAnalyzer>>
				entry : _moderationAnalyzers.entrySet()) {

			try {
				ServiceComponentReference<ModerationAnalyzer> value =
					entry.getValue();

				ModerationAnalyzer moderationAnalyzer =
					value.getServiceComponent();

				Optional<ModerationAnalysisResponse> optional =
					moderationAnalyzer.analyze(contentAnalysisRequest);

				if (!optional.isPresent()) {
					if (_log.isDebugEnabled()) {
						StringBundler sb = new StringBundler(3);

						sb.append("Moderation analyzer ");
						sb.append(moderationAnalyzer.getClass());
						sb.append(" gave an empty response");

						_log.debug(sb.toString());
					}

					continue;
				}

				responses.add(optional.get());
			}
			catch (IllegalArgumentException illegalArgumentException) {
				_log.error(
					illegalArgumentException.getMessage(),
					illegalArgumentException);
			}
		}

		return Optional.of(responses);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void registerCategoryAnalyzer(
		CategoryAnalyzer categoryAnalyzer, Map<String, Object> properties) {

		ServiceComponentReferenceUtil.addToMapByName(
			_categoryAnalyzers, categoryAnalyzer, properties);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void registerModerationAnalyzer(
		ModerationAnalyzer moderationAnalyzer, Map<String, Object> properties) {

		ServiceComponentReferenceUtil.addToMapByName(
			_moderationAnalyzers, moderationAnalyzer, properties);
	}

	protected void unregisterCategoryAnalyzer(
		CategoryAnalyzer categoryAnalyzer, Map<String, Object> properties) {

		ServiceComponentReferenceUtil.removeFromMapByName(
			_categoryAnalyzers, categoryAnalyzer, properties);
	}

	protected void unregisterModerationAnalyzer(
		ModerationAnalyzer moderationAnalyzer, Map<String, Object> properties) {

		ServiceComponentReferenceUtil.removeFromMapByName(
			_moderationAnalyzers, moderationAnalyzer, properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentAnalysisServiceImpl.class);

	private volatile Map<String, ServiceComponentReference<CategoryAnalyzer>>
		_categoryAnalyzers = new ConcurrentHashMap<>();
	private volatile Map<String, ServiceComponentReference<ModerationAnalyzer>>
		_moderationAnalyzers = new ConcurrentHashMap<>();

}
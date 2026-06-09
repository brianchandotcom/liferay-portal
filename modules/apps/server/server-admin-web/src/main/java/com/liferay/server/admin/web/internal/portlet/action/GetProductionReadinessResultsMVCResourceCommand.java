/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.server.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.server.admin.web.internal.production.readiness.ProductionReadinessIgnoredRuleUtil;
import com.liferay.server.admin.web.internal.production.readiness.ProductionReadinessResult;
import com.liferay.server.admin.web.internal.production.readiness.ProductionReadinessRuleUtil;

import jakarta.portlet.ResourceRequest;
import jakarta.portlet.ResourceResponse;

import java.io.PrintWriter;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lily Chi
 */
@Component(
	property = {
		"jakarta.portlet.name=" + PortletKeys.SERVER_ADMIN,
		"mvc.command.name=/server_admin/get_production_readiness_results"
	},
	service = MVCResourceCommand.class
)
public class GetProductionReadinessResultsMVCResourceCommand
	extends BaseProductionReadinessMVCResourceCommand {

	@Override
	protected void serveProductionReadinessResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		JSONArray resultsJSONArray = _jsonFactory.createJSONArray();

		int failed = 0;
		int ignored = 0;
		int passed = 0;

		List<String> ignoredRules =
			ProductionReadinessIgnoredRuleUtil.getIgnoredRules();

		for (ProductionReadinessResult productionReadinessResult :
				ProductionReadinessRuleUtil.check()) {

			if (ignoredRules.contains(productionReadinessResult.getKey())) {
				ignored++;
			}
			else if (productionReadinessResult.getStatus() ==
						ProductionReadinessResult.Status.PASS) {

				passed++;
			}
			else {
				failed++;
			}

			resultsJSONArray.put(
				_toJSONObject(ignoredRules, locale, productionReadinessResult));
		}

		JSONObject summaryJSONObject = _jsonFactory.createJSONObject(
		).put(
			"failed", failed
		).put(
			"ignored", ignored
		).put(
			"passed", passed
		);

		JSONObject responseJSONObject = _jsonFactory.createJSONObject(
		).put(
			"results", resultsJSONArray
		).put(
			"summary", summaryJSONObject
		);

		resourceResponse.setContentType(ContentTypes.APPLICATION_JSON);

		PrintWriter printWriter = resourceResponse.getWriter();

		printWriter.write(responseJSONObject.toString());
	}

	private JSONObject _toJSONObject(
		List<String> ignoredRules, Locale locale,
		ProductionReadinessResult productionReadinessResult) {

		String message = LanguageUtil.format(
			locale, productionReadinessResult.getMessageKey(),
			productionReadinessResult.getMessageParameters(), false);

		return _jsonFactory.createJSONObject(
		).put(
			"category", productionReadinessResult.getCategory()
		).put(
			"categoryLabel",
			LanguageUtil.get(
				locale,
				"production-readiness-category-" +
					productionReadinessResult.getCategory())
		).put(
			"currentValue", productionReadinessResult.getCurrentValue()
		).put(
			"ignored", ignoredRules.contains(productionReadinessResult.getKey())
		).put(
			"message", message
		).put(
			"name",
			LanguageUtil.get(
				locale,
				"production-readiness-rule-" +
					productionReadinessResult.getKey())
		).put(
			"recommendedValue", productionReadinessResult.getRecommendedValue()
		).put(
			"ruleKey", productionReadinessResult.getKey()
		).put(
			"severity", String.valueOf(productionReadinessResult.getSeverity())
		).put(
			"status", String.valueOf(productionReadinessResult.getStatus())
		);
	}

	@Reference
	private JSONFactory _jsonFactory;

}
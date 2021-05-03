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

package com.liferay.portal.search.tuning.blueprints.admin.web.internal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.query.contributor.QueryPreFilterContributor;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = {})
public class BlueprintsAdminComponentUtil {

	public static List<String> getKeywordQueryContributors() {
		return _keywordQueryContributors;
	}

	public static List<String> getModelPrefilterContributors() {
		return _modelPreFilterContributors;
	}

	public static List<String> getQueryPrefilterContributors() {
		return _queryPreFilterContributors;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_keywordQueryContributors = _getComponentList(
			bundleContext, KeywordQueryContributor.class.getName());

		_modelPreFilterContributors = _getComponentList(
			bundleContext, ModelPreFilterContributor.class.getName());

		_queryPreFilterContributors = _getComponentList(
			bundleContext, QueryPreFilterContributor.class.getName());
	}

	private List<String> _getComponentList(
		BundleContext bundleContext, String className) {

		List<String> list = new ArrayList<>();

		try {
			ServiceReference<?>[] references =
				bundleContext.getAllServiceReferences(className, null);

			for (ServiceReference<?> serviceReference : references) {
				list.add(
					(String)serviceReference.getProperty("component.name"));
			}
		}
		catch (InvalidSyntaxException invalidSyntaxException) {
			_log.error(
				invalidSyntaxException.getMessage(), invalidSyntaxException);
		}

		return list;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BlueprintsAdminComponentUtil.class);

	private static List<String> _keywordQueryContributors;
	private static List<String> _modelPreFilterContributors;
	private static List<String> _queryPreFilterContributors;

}
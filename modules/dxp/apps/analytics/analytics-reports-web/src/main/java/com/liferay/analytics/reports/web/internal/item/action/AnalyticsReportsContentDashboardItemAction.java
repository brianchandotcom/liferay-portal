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

package com.liferay.analytics.reports.web.internal.item.action;

import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author David Arques
 */
public class AnalyticsReportsContentDashboardItemAction
	implements ContentDashboardItemAction {

	public AnalyticsReportsContentDashboardItemAction(
		ResourceBundleLoader resourceBundleLoader, String url) {

		_resourceBundleLoader = resourceBundleLoader;
		_url = url;
	}

	@Override
	public String getIcon() {
		return "analytics";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return ResourceBundleUtil.getString(resourceBundle, "view-metrics");
	}

	@Override
	public String getName() {
		return _NAME;
	}

	@Override
	public Type getType() {
		return Type.VIEW_IN_PANEL;
	}

	@Override
	public String getURL() {
		return _url;
	}

	@Override
	public String getURL(Locale locale) {
		return _url;
	}

	private static final String _NAME = "viewMetrics";

	private final ResourceBundleLoader _resourceBundleLoader;
	private final String _url;

}
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.editor.ckeditor5.sample.web.internal.editor.configuration;

import com.liferay.frontend.editor.ckeditor5.sample.web.internal.constants.CKEditor5SamplePortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * Provides the CKEditor 5 sample portlet with an editor that always loads the
 * premium plugins, so that they can be exercised by our test suites.
 *
 * <p>
 * The premium plugins are gated behind <code>ReleaseInfo.isDXP()</code> and a
 * valid license key, a combination that never holds in CI. This contributor
 * replaces the license key with one entitled to the premium features and
 * enables the plugins unconditionally. Since it only lives in the sample
 * module, which is never deployed to a production installation, the gate is
 * left untouched everywhere else.
 * </p>
 *
 * @author Miguel Arroyo
 */
@Component(
	property = {
		"editor.config.key=sampleReactCETPremiumEditor",
		"jakarta.portlet.name=" + CKEditor5SamplePortletKeys.CKEDITOR_5_SAMPLE
	},
	service = EditorConfigContributor.class
)
public class ReactCETPremiumEditorConfigContributor
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		jsonObject.put(
			"licenseKey", _LICENSE_KEY
		).put(
			"showPasteFromOfficeEnhanced", true
		).put(
			"showSourceEditingEnhanced", true
		);
	}

	private static final String _LICENSE_KEY = StringBundler.concat(
		"eyJhbGciOiJFUzI1NiJ9.eyJleHAiOjE4NjE5MTk5OTksImp0aSI6IjliZGFlYTgwLTQ",
		"yODMtNGMxNS04ODY3LWRiYTFkZmUwZDZkMyIsImRpc3RyaWJ1dGlvbkNoYW5uZWwiOls",
		"ic2giLCJkcnVwYWwiXSwid2hpdGVMYWJlbCI6dHJ1ZSwibGljZW5zZVR5cGUiOiJkZXZ",
		"lbG9wbWVudCIsImZlYXR1cmVzIjpbIkRSVVAiLCJETyIsIkZQIiwiU0MiLCJUT0MiLCJ",
		"UUEwiLCJQT0UiLCJDQyIsIk1GIiwiU0VFIiwiRUNIIiwiRUlTIiwiTEgiLCJGT08iXSw",
		"idmMiOiIzNWRhYzk5MCJ9.pdjVHuGLsySX9-jsCx9hBeLbQ9nasyNG1q66nqhCWPPdNV",
		"3xeAjMNVlU926cxdfrRdiEpaRTnPS_Twc9rxxnCQ");

}
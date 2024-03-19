/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.forgot.password.internal;

import com.liferay.layout.utility.page.kernel.LayoutUtilityPageEntryViewRenderer;
import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.portal.kernel.feature.flag.FeatureFlagListener;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alvaro Saugar
 */
@Component(
	property = "featureFlagKey=LPD-6378", service = FeatureFlagListener.class
)
public class ForgotPasswordLayoutUtilityPageEntryViewRendererFeatureFlagListener
	implements FeatureFlagListener {

	@Override
	public void onValue(
		long companyId, String featureFlagKey, boolean enabled) {

		if (!enabled) {
			if (_serviceRegistration != null) {
				_serviceRegistration.unregister();
			}

			return;
		}

		_serviceRegistration = _bundleContext.registerService(
			LayoutUtilityPageEntryViewRenderer.class,
			new ForgotPasswordLayoutUtilityPageEntryViewRenderer(
				_language, _servletContext),
			HashMapDictionaryBuilder.<String, Object>put(
				"utility.page.type",
				LayoutUtilityPageEntryConstants.TYPE_FORGOT_PASSWORD
			).build());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private volatile BundleContext _bundleContext;

	@Reference
	private Language _language;

	private ServiceRegistration<?> _serviceRegistration;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.layout.utility.page.forgot.password)"
	)
	private ServletContext _servletContext;

}
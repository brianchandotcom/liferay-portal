/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.servlet;

import com.liferay.frontend.js.web.internal.LanguageState;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.ResourceBundle;

import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
public class ResourceBundleServiceTrackerCustomizer
	implements ServiceTrackerCustomizer<ResourceBundle, String> {

	@Override
	public String addingService(
		ServiceReference<ResourceBundle> serviceReference) {

		String languageId = (String)serviceReference.getProperty("language.id");

		if (languageId != null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Resource bundle for language  '", languageId,
						"' was added"));
			}

			LanguageState.reload();

			return languageId;
		}

		return null;
	}

	@Override
	public void modifiedService(
		ServiceReference<ResourceBundle> serviceReference, String languageId) {

		if (languageId == null) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Resource bundle for language  '", languageId,
					"' was modified"));
		}

		LanguageState.reload();
	}

	@Override
	public void removedService(
		ServiceReference<ResourceBundle> serviceReference, String languageId) {

		if (languageId == null) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Resource bundle for language  '", languageId,
					"' was removed"));
		}

		LanguageState.reload();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResourceBundleServiceTrackerCustomizer.class);

}
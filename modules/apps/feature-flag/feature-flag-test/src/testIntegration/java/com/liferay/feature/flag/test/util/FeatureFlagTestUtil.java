/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.feature.flag.test.util;

import com.liferay.portal.kernel.feature.flag.FeatureFlagManager;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Application;

import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Drew Brokke
 */
public class FeatureFlagTestUtil {

	public static boolean getFeatureFlagValue(
		long companyId, String featureFlagKey) {

		FeatureFlagManager featureFlagManager =
			_featureFlagManagerSnapshot.get();

		return featureFlagManager.isEnabled(companyId, featureFlagKey);
	}

	public static void setFeatureFlagValue(
		long companyId, boolean enabled, String featureFlagKey) {

		ReflectionTestUtil.invoke(
			_applicationSnapshot.get(), "confirm",
			new Class<?>[] {
				HttpServletRequest.class, HttpServletResponse.class, long.class,
				boolean.class, String.class
			},
			new MockHttpServletRequest(), new MockHttpServletResponse(),
			companyId, enabled, featureFlagKey);
	}

	private static final Snapshot<Application> _applicationSnapshot =
		new Snapshot<>(
			FeatureFlagTestUtil.class, Application.class,
			String.format(
				"(%s=%s)", JaxrsWhiteboardConstants.JAX_RS_NAME,
				"com.liferay.feature.flag.web.internal.jaxrs.application." +
					"FeatureFlagApplication"));
	private static final Snapshot<FeatureFlagManager>
		_featureFlagManagerSnapshot = new Snapshot<>(
			FeatureFlagTestUtil.class, FeatureFlagManager.class);

}
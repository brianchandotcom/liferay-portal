/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.fragment.renderer;

import com.liferay.ai.hub.web.internal.constants.AIHubWebConstants;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Davyson Melo
 * @author Feliphe Marinho
 */
public abstract class BaseFragmentRenderer<T> implements FragmentRenderer {

	@Override
	public void render(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(getJSPPath());

			T displayContext = getDisplayContext(httpServletRequest);

			Class<?> clazz = displayContext.getClass();

			httpServletRequest.setAttribute(clazz.getName(), displayContext);

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (IOException | RuntimeException exception) {
			throw exception;
		}
		catch (Exception exception) {
			throw new IOException(exception);
		}
	}

	protected abstract T getDisplayContext(
		HttpServletRequest httpServletRequest);

	protected abstract String getJSPPath();

	@Reference(
		target = "(osgi.web.symbolicname=" + AIHubWebConstants.BUNDLE_SYMBOLIC_NAME + ")"
	)
	protected ServletContext servletContext;

}
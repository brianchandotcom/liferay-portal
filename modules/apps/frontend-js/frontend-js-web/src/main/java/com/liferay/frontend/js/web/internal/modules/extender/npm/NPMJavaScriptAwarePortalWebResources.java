/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.modules.extender.npm;

import com.liferay.frontend.js.loader.modules.extender.npm.JavaScriptAwarePortalWebResources;
import com.liferay.portal.kernel.servlet.PortalWebResourceConstants;
import com.liferay.portal.kernel.servlet.PortalWebResources;

import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Fellwock
 */
@Component(
	service = {
		JavaScriptAwarePortalWebResources.class, PortalWebResources.class
	}
)
public class NPMJavaScriptAwarePortalWebResources
	implements JavaScriptAwarePortalWebResources {

	@Override
	public String getContextPath() {
		return _servletContext.getContextPath();
	}

	@Override
	public long getLastModified() {
		return _lastModified.get();
	}

	@Override
	public String getResourceType() {
		return PortalWebResourceConstants.RESOURCE_TYPE_JS;
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public void updateLastModifed(long lastModified) {
		_lastModified.accumulateAndGet(lastModified, Math::max);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		Bundle bundle = bundleContext.getBundle();

		_lastModified.set(bundle.getLastModified());
	}

	private final AtomicLong _lastModified = new AtomicLong();

	@Reference(target = "(osgi.web.symbolicname=com.liferay.frontend.js.web)")
	private ServletContext _servletContext;

}
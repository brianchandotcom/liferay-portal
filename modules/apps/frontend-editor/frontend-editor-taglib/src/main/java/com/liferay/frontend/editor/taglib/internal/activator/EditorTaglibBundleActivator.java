/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.editor.taglib.internal.activator;

import com.liferay.frontend.editor.taglib.internal.EditorRendererUtil;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author Iván Zaera Avellón
 */
public class EditorTaglibBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		EditorRendererUtil.start(bundleContext);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		EditorRendererUtil.stop();
	}

}
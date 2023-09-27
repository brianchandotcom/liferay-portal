/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.loader.modules.extender.npm;

import org.osgi.framework.Bundle;

/**
 * This is an interface for registering OSGi services that get notified whenever
 * the {@link NPMRegistry} is updated.
 *
 * {@link JSBundleTracker} objects can be registered with the
 * {@link NPMRegistry} calling
 * {@link NPMRegistry#addJSBundleTracker(JSBundleTracker)} or declaring them as
 * OSGi services (in that case the {@link NPMRegistry} will find and attach
 * them).
 *
 * The reason for {@link NPMRegistry#addJSBundleTracker(JSBundleTracker)} to
 * exist is that sometimes you need to implement a {@link JSBundleTracker} in a
 * service which depends on {@link NPMRegistry} and thus, you cannot declare it
 * as a {@link JSBundleTracker} because OSGi will complain about a soft circular
 * dependency.
 *
 * @author     Iván Zaera
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 * @review
 */
@Deprecated
public interface JSBundleTracker {

	public void addedJSBundle(
		JSBundle jsBundle, Bundle bundle, NPMRegistry npmRegistry);

	public void removedJSBundle(
		JSBundle jsBundle, Bundle bundle, NPMRegistry npmRegistry);

}
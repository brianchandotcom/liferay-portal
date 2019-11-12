/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
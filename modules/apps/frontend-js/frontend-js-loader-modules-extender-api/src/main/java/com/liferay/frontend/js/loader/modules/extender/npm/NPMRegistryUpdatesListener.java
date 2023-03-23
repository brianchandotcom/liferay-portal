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

/**
 * Can be implemented by any service needing to be notified when the
 * {@link NPMRegistry} is updated.
 *
 * Note that implementers of this interface must not depend on
 * {@link NPMRegistry}, since that would create circular references.
 *
 * If, for some reason, you need to invoke {@link NPMRegistry} from your
 * listener, the best way to obtain a reference to it is by using a
 * {@link org.osgi.util.tracker.ServiceTracker} and assuming that any data you
 * compute that depends on {@link NPMRegistry} is optional, since it is possible
 * that, by the time you need to access it, the {@link NPMRegistry} is not
 * available.
 *
 * Another caveat: the {@link NPMRegistry} invokes listeners whenever it goes
 * up and down, so you don't need to worry about that in your listener
 * implementation. However, if your listener goes down, then up, the
 * {@link NPMRegistry} won't invoke it, so it's your responsibility to handle
 * that situation, whether by simulating an update or doing anything you need to
 * make your data depending on {@link NPMRegistry} available.
 *
 * @author Iván Zaera Avellón
 * @review
 */
public interface NPMRegistryUpdatesListener {

	public void onAfterUpdate();

}
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

package com.liferay.osgi.felix.util;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;

/**
 * @author Shuyang Zhou
 */
public abstract class AbstractExtender
	extends org.apache.felix.utils.extender.AbstractExtender {

	public AbstractExtender() {
		setSynchronous(true);
	}

	@Override
	public void bundleChanged(BundleEvent event) {
		synchronized (this) {
			if (_stopped) {
				return;
			}

			if (event.getType() == BundleEvent.STOPPING) {
				BundleContext bundleContext = getBundleContext();

				Bundle bundle = event.getBundle();

				if (bundleContext == bundle.getBundleContext()) {
					bundleContext.removeBundleListener(this);

					_stopped = true;
				}
			}

			super.bundleChanged(event);
		}
	}

	@Override
	public final void setSynchronous(boolean synchronous) {
		super.setSynchronous(synchronous);
	}

	private boolean _stopped;

}
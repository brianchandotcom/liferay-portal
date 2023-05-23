/*******************************************************************************
 * Copyright (c) Dec 1, 2014 Liferay, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Liferay, Inc. - initial API and implementation and/or initial 
 *                    documentation
 ******************************************************************************/

package org.eclipse.equinox.http.servlet.internal;

import org.eclipse.equinox.http.servlet.internal.context.HttpContextHelperFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceRegistration;

public class HttpServiceObjectRegistration {
	public final Object serviceKey;
	public final ServiceRegistration<?> registration;
	public final HttpContextHelperFactory factory;
	public final Bundle bundle;
	public HttpServiceObjectRegistration(
		Object serviceKey, ServiceRegistration<?> registration,
		HttpContextHelperFactory factory, Bundle bundle) {
		this.serviceKey = serviceKey;
		this.registration = registration;
		this.factory = factory;
		this.bundle = bundle;
	}
}

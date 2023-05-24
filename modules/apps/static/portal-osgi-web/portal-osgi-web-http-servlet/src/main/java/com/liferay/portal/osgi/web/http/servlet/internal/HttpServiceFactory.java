/*******************************************************************************
 * Copyright (c) 2005, 2014 Cognos Incorporated, IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Cognos Incorporated - initial API and implementation
 *     IBM Corporation - bug fixes and enhancements
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 *******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet.internal;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.HttpService;

/**
 * @author Cognos Incorporated
 * @author IBM Corporation
 * @author Raymond Augé
 */
public class HttpServiceFactory implements ServiceFactory<HttpService> {

	public HttpServiceFactory(HttpServiceRuntimeImpl httpServiceRuntimeImpl) {
		_httpServiceRuntimeImpl = httpServiceRuntimeImpl;
	}

	@Override
	public HttpService getService(
		Bundle bundle, ServiceRegistration<HttpService> serviceRegistration) {

		return new HttpServiceImpl(bundle, _httpServiceRuntimeImpl);
	}

	@Override
	public void ungetService(
		Bundle bundle, ServiceRegistration<HttpService> serviceRegistration,
		HttpService httpService) {

		HttpServiceImpl httpServiceImpl = (HttpServiceImpl)httpService;

		httpServiceImpl.shutdown();
	}

	private final HttpServiceRuntimeImpl _httpServiceRuntimeImpl;

}
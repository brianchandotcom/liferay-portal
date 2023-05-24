/*******************************************************************************
 * Copyright (c) Dec 5, 2014 Liferay, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Liferay, Inc. - initial API and implementation and/or initial
 *                    documentation
 ******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet.context;

import org.osgi.framework.ServiceReference;
import org.osgi.service.http.context.ServletContextHelper;

/**
 * @author Liferay, Inc.
 */
public abstract class ContextPathCustomizer {

	public String getContextPathPrefix(
		ServiceReference<ServletContextHelper> serviceReference) {

		return null;
	}

	public String getDefaultContextSelectFilter(
		ServiceReference<?> serviceReference) {

		return null;
	}

}
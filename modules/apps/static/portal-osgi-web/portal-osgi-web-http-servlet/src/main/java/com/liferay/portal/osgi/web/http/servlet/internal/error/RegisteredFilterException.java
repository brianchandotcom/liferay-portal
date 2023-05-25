/*******************************************************************************
 * Copyright (c) 2014 Raymond Augé and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 ******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet.internal.error;

import javax.servlet.Filter;
import javax.servlet.ServletException;

/**
 * @author Raymond Augé
 */
public class RegisteredFilterException extends ServletException {

	public RegisteredFilterException(Filter filter) {
		super("Filter has already been registered: " + filter);
	}

	private static final long serialVersionUID = 4321327145573490998L;

}
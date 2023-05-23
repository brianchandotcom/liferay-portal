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

package org.eclipse.equinox.http.servlet.internal.error;

import javax.servlet.ServletException;

/**
 * @author Raymond Augé
 */
public class RegisteredServletContextHelperException
	extends ServletException {

	private static final long serialVersionUID = 7301237379456486249L;

	public RegisteredServletContextHelperException() {
		super("ServletContextHelper has already been registered.");
	}

}
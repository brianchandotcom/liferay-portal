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

import org.osgi.service.http.NamespaceException;

/**
 * @author Raymond Augé
 */
public class PatternInUseException extends NamespaceException {

	private static final long serialVersionUID = -4196149175131735927L;

	public PatternInUseException(String pattern) {
		super("Pattern already in use: " + pattern);
	}
}
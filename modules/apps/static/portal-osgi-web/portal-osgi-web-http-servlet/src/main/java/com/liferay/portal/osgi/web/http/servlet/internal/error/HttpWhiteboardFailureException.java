/*******************************************************************************
 * Copyright (c) Feb 23, 2015 Raymond Augé and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Raymond Augé <raymond.auge@liferay.com> - Bug 460639
 ******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet.internal.error;

/**
 * @author Raymond Augé
 */
public class HttpWhiteboardFailureException extends IllegalArgumentException {

	public HttpWhiteboardFailureException(String message, int failureReason) {
		super(message);

		_failureReason = failureReason;
	}

	public int getFailureReason() {
		return _failureReason;
	}

	private static final long serialVersionUID = 1944632136470074075L;

	private final int _failureReason;

}
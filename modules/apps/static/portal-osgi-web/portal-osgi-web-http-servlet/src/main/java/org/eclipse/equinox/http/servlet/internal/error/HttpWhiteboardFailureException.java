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

package org.eclipse.equinox.http.servlet.internal.error;

/**
 * @author Raymond Augé
 */
public class HttpWhiteboardFailureException extends IllegalArgumentException {

	private static final long serialVersionUID = 1944632136470074075L;

	public HttpWhiteboardFailureException(String message, int failureReason) {
		super(message);

		this.failureReason = failureReason;
	}

	public int getFailureReason() {
		return failureReason;
	}

	private final int failureReason;

}
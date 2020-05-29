/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.app.builder.workflow.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchTaskLinkException extends NoSuchModelException {

	public NoSuchTaskLinkException() {
	}

	public NoSuchTaskLinkException(String msg) {
		super(msg);
	}

	public NoSuchTaskLinkException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public NoSuchTaskLinkException(Throwable cause) {
		super(cause);
	}

}
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

package com.liferay.vldap.server.internal.handler;

import com.liferay.portal.kernel.exception.SystemException;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class SearchTimeLimitException extends SystemException {

	public SearchTimeLimitException() {
	}

	public SearchTimeLimitException(String msg) {
		super(msg);
	}

	public SearchTimeLimitException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public SearchTimeLimitException(Throwable cause) {
		super(cause);
	}

}
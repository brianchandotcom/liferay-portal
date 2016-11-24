/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.captcha;

/**
 * @author     Jack Li
 * @deprecated As of 7.0.0, with no direct replacement
 */
@Deprecated
public class CaptchaMaxChallengesException extends CaptchaException {

	public CaptchaMaxChallengesException() {
	}

	public CaptchaMaxChallengesException(String msg) {
		super(msg);
	}

	public CaptchaMaxChallengesException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public CaptchaMaxChallengesException(Throwable cause) {
		super(cause);
	}

}
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

package com.liferay.portal.search.tuning.blueprints.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchBlueprintException extends NoSuchModelException {

	public NoSuchBlueprintException() {
	}

	public NoSuchBlueprintException(String msg) {
		super(msg);
	}

	public NoSuchBlueprintException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchBlueprintException(Throwable throwable) {
		super(throwable);
	}

	private static final long serialVersionUID = 1409818413197840373L;

}
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

import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Petteri Karttunen
 */
public class BlueprintValidationException extends PortalException {

	public BlueprintValidationException() {
	}

	public BlueprintValidationException(List<String> errors) {
		super(StringUtil.merge(errors, ","));

		_errors = errors;
	}

	public BlueprintValidationException(String msg) {
		super(msg);
	}

	public BlueprintValidationException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public List<String> getErrors() {
		return _errors;
	}

	private static final long serialVersionUID = 1L;

	private List<String> _errors;

}
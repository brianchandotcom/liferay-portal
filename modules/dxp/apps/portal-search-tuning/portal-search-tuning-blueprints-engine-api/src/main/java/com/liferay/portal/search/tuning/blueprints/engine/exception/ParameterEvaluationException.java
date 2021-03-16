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

package com.liferay.portal.search.tuning.blueprints.engine.exception;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.search.tuning.blueprints.message.Message;

/**
 * @author Petteri Karttunen
 */
public class ParameterEvaluationException extends PortalException {

	public ParameterEvaluationException() {
	}

	public ParameterEvaluationException(Message message) {
		_message = message;
	}

	public ParameterEvaluationException(String msg) {
		super(msg);
	}

	public ParameterEvaluationException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ParameterEvaluationException(Throwable throwable) {
		super(throwable);
	}

	public Message getDetailsMessage() {
		return _message;
	}

	private static final long serialVersionUID = 1L;

	private Message _message;

}
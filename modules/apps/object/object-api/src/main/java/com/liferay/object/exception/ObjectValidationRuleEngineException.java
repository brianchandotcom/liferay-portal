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

package com.liferay.object.exception;

import com.liferay.object.validation.rule.ObjectValidationRuleResult;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Marco Leo
 */
public class ObjectValidationRuleEngineException extends PortalException {

	public ObjectValidationRuleEngineException(
		List<ObjectValidationRuleResult> objectValidationRuleResults) {

		_objectValidationRuleResults = objectValidationRuleResults;
	}

	public String getMessage() {
		return _message;
	}

	public String getMessageKey() {
		return _messageKey;
	}

	public List<ObjectValidationRuleResult> getObjectValidationRuleResults() {
		return _objectValidationRuleResults;
	}

	public static class InvalidFields
		extends ObjectValidationRuleEngineException {

		public InvalidFields(String message) {
			super(message);
		}

	}

	public static class InvalidScript
		extends ObjectValidationRuleEngineException {

		public InvalidScript() {
			super(
				"There was an error validating your data.",
				"there-was-an-error-validating-your-data");
		}

	}

	public static class MustNotBeNull
		extends ObjectValidationRuleEngineException {

		public MustNotBeNull() {
			super("Engine is null");
		}

	}

	public static class NoSuchEngine
		extends ObjectValidationRuleEngineException {

		public NoSuchEngine(String engine) {
			super("Engine \"" + engine + "\" does not exist");
		}

	}

	private ObjectValidationRuleEngineException(String message) {
		super(message);

		_message = message;
	}

	private ObjectValidationRuleEngineException(
		String message, String messageKey) {

		super(message);

		_messageKey = messageKey;
	}

	private String _message;
	private String _messageKey;
	private List<ObjectValidationRuleResult> _objectValidationRuleResults;

}
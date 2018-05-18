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

package com.liferay.commerce.model;

/**
 * @author Andrea Di Giorgi
 */
public class CommercePaymentEngineResult {

	public CommercePaymentEngineResult(String content) {
		_content = content;
	}

	public String getContent() {
		return _content;
	}

	public static class StartPayment extends CommercePaymentEngineResult {

		public StartPayment(String content, String output) {
			super(content);

			_output = output;
		}

		public String getOutput() {
			return _output;
		}

		private final String _output;

	}

	private final String _content;

}
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
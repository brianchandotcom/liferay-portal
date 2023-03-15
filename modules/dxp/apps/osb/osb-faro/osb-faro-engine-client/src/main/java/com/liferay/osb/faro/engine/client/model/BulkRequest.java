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

package com.liferay.osb.faro.engine.client.model;

import java.util.List;

/**
 * @author Matthew Kong
 */
public class BulkRequest {

	public List<Operation> getOperations() {
		return _operations;
	}

	public void setOperations(List<Operation> operations) {
		_operations = operations;
	}

	public static class Operation {

		public Operation() {
		}

		public Operation(Object body, String method, String url) {
			_body = body;
			_method = method;
			_url = url;
		}

		public Object getBody() {
			return _body;
		}

		public String getMethod() {
			return _method;
		}

		public String getUrl() {
			return _url;
		}

		public void setBody(Object body) {
			_body = body;
		}

		public void setMethod(String method) {
			_method = method;
		}

		public void setUrl(String url) {
			_url = url;
		}

		private Object _body;
		private String _method;
		private String _url;

	}

	private List<Operation> _operations;

}
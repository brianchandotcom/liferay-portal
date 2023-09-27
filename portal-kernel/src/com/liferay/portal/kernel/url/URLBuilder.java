/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.url;

import com.liferay.portal.kernel.util.HttpComponentsUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Julius Lee
 */
public class URLBuilder {

	public static URLBuilder create(String url) {
		return new URLBuilder(url);
	}

	public URLBuilder addParameter(String name, String value) {
		_urlOperations.add(new URLOperation(name, OperationType.ADD, value));

		return this;
	}

	public String build() {
		if (_url == null) {
			return null;
		}

		for (URLOperation urlOperation : _urlOperations) {
			String name = urlOperation.getName();
			OperationType operationType = urlOperation.getOperationType();
			String value = urlOperation.getValue();

			if (operationType == OperationType.ADD) {
				_url = HttpComponentsUtil.addParameter(_url, name, value);
			}
			else if (operationType == OperationType.SET) {
				_url = HttpComponentsUtil.setParameter(_url, name, value);
			}
			else if (operationType == OperationType.REMOVE) {
				_url = HttpComponentsUtil.removeParameter(_url, name);
			}
		}

		_urlOperations.clear();

		return _url;
	}

	public URLBuilder removeParameter(String name) {
		_urlOperations.add(new URLOperation(name, OperationType.REMOVE, null));

		return this;
	}

	public URLBuilder setParameter(String name, String value) {
		_urlOperations.add(new URLOperation(name, OperationType.SET, value));

		return this;
	}

	private URLBuilder(String url) {
		_url = url;
	}

	private String _url;
	private final List<URLOperation> _urlOperations = new LinkedList<>();

	private static class URLOperation {

		public String getName() {
			return _name;
		}

		public OperationType getOperationType() {
			return _operationType;
		}

		public String getValue() {
			return _value;
		}

		private URLOperation(
			String name, OperationType operationType, String value) {

			_name = name;
			_operationType = operationType;
			_value = value;
		}

		private final String _name;
		private final OperationType _operationType;
		private final String _value;

	}

	private enum OperationType {

		ADD, REMOVE, SET

	}

}
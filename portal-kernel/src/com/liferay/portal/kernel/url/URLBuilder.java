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
		_urlOperations.add(new URLOperation(OperationType.ADD, name, value));

		return this;
	}

	public String build() {
		if (_url == null) {
			return null;
		}

		for (URLOperation urlOperation : _urlOperations) {
			OperationType operationType = urlOperation.getType();
			String name = urlOperation.getName();
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
		_urlOperations.add(new URLOperation(OperationType.REMOVE, name, null));

		return this;
	}

	public URLBuilder setParameter(String name, String value) {
		_urlOperations.add(new URLOperation(OperationType.SET, name, value));

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

		public OperationType getType() {
			return _operationType;
		}

		public String getValue() {
			return _value;
		}

		private URLOperation(
			OperationType operationType, String name, String value) {

			_operationType = operationType;
			_name = name;
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
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

package com.liferay.portal.search.tuning.blueprints.fields.provider;

/**
 * @author Petteri Karttunen
 */
public class FieldInfo {

	public FieldInfo(FieldInfo field) {
		_languageIdPosition = field._languageIdPosition;
		_name = field._name;
		_type = field._type;
	}

	public int getLanguageIdPosition() {
		return _languageIdPosition;
	}

	public String getName() {
		return _name;
	}

	public String getType() {
		return _type;
	}

	public void setLanguageIdPosition(int languageIdPosition) {
		_languageIdPosition = languageIdPosition;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setType(String type) {
		_type = type;
	}

	public static class FieldInfoBuilder {

		public FieldInfoBuilder() {
			_fieldInfo = new FieldInfo();
		}

		public FieldInfoBuilder(FieldInfo field) {
			_fieldInfo = field;
		}

		public FieldInfo build() {
			return new FieldInfo(_fieldInfo);
		}

		public FieldInfoBuilder languageIdPosition(int languageIdPosition) {
			_fieldInfo._languageIdPosition = languageIdPosition;

			return this;
		}

		public FieldInfoBuilder name(String name) {
			_fieldInfo._name = name;

			return this;
		}

		public FieldInfoBuilder type(String type) {
			_fieldInfo._type = type;

			return this;
		}

		private final FieldInfo _fieldInfo;

	}

	private FieldInfo() {
	}

	private int _languageIdPosition;
	private String _name;
	private String _type;

}
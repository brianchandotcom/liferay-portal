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

package com.liferay.search.experiences.index;

/**
 * @author Petteri Karttunen
 */
public class IndexFieldInfo {

	public IndexFieldInfo(IndexFieldInfo indexFieldInfo) {
		_languageIdPosition = indexFieldInfo._languageIdPosition;
		_name = indexFieldInfo._name;
		_type = indexFieldInfo._type;
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

	public static class IndexFieldInfoBuilder {

		public IndexFieldInfoBuilder() {
			_indexFieldInfo = new IndexFieldInfo();
		}

		public IndexFieldInfoBuilder(IndexFieldInfo indexFieldInfo) {
			_indexFieldInfo = indexFieldInfo;
		}

		public IndexFieldInfo build() {
			return new IndexFieldInfo(_indexFieldInfo);
		}

		public IndexFieldInfoBuilder languageIdPosition(
			int languageIdPosition) {

			_indexFieldInfo._languageIdPosition = languageIdPosition;

			return this;
		}

		public IndexFieldInfoBuilder name(String name) {
			_indexFieldInfo._name = name;

			return this;
		}

		public IndexFieldInfoBuilder type(String type) {
			_indexFieldInfo._type = type;

			return this;
		}

		private final IndexFieldInfo _indexFieldInfo;

	}

	private IndexFieldInfo() {
	}

	private int _languageIdPosition;
	private String _name;
	private String _type;

}
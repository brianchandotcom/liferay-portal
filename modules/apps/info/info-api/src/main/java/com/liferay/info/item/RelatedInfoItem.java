/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item;

/**
 * @author Eudaldo Alonso
 */
public class RelatedInfoItem {

	public static Builder builder() {
		return new Builder();
	}

	public String getClassName() {
		return _builder._className;
	}

	public String getName() {
		return _builder._name;
	}

	public static class Builder {

		public RelatedInfoItem build() {
			return new RelatedInfoItem(this);
		}

		public Builder className(String className) {
			_className = className;

			return this;
		}

		public Builder name(String name) {
			_name = name;

			return this;
		}

		private String _className;
		private String _name;

	}

	private RelatedInfoItem(Builder builder) {
		_builder = builder;
	}

	private final Builder _builder;

}
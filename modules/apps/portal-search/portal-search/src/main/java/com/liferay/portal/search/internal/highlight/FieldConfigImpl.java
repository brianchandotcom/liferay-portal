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

package com.liferay.portal.search.internal.highlight;

import com.liferay.portal.search.highlight.FieldConfig;
import com.liferay.portal.search.highlight.FieldConfigBuilder;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
public class FieldConfigImpl implements FieldConfig {

	public FieldConfigImpl() {
	}

	public FieldConfigImpl(FieldConfigImpl fieldConfigImpl) {
		_field = fieldConfigImpl._field;
		_fragmentOffset = fieldConfigImpl._fragmentOffset;
		_fragmentSize = fieldConfigImpl._fragmentSize;
		_numFragments = fieldConfigImpl._numFragments;
	}

	@Override
	public String getField() {
		return _field;
	}

	@Override
	public Integer getFragmentOffset() {
		return _fragmentOffset;
	}

	@Override
	public Integer getFragmentSize() {
		return _fragmentSize;
	}

	@Override
	public Integer getNumFragments() {
		return _numFragments;
	}

	public static final class FieldConfigBuilderImpl
		implements FieldConfigBuilder {

		@Override
		public FieldConfig build() {
			return new FieldConfigImpl(_fieldConfigImpl);
		}

		@Override
		public FieldConfigBuilder field(String field) {
			_fieldConfigImpl._field = field;

			return this;
		}

		@Override
		public FieldConfigBuilder fragmentOffset(Integer fragmentOffset) {
			_fieldConfigImpl._fragmentOffset = fragmentOffset;

			return this;
		}

		@Override
		public FieldConfigBuilder fragmentSize(Integer fragmentSize) {
			_fieldConfigImpl._fragmentSize = fragmentSize;

			return this;
		}

		@Override
		public FieldConfigBuilder numFragments(Integer numFragments) {
			_fieldConfigImpl._numFragments = numFragments;

			return this;
		}

		private final FieldConfigImpl _fieldConfigImpl = new FieldConfigImpl();

	}

	private String _field;
	private Integer _fragmentOffset;
	private Integer _fragmentSize;
	private Integer _numFragments;

}
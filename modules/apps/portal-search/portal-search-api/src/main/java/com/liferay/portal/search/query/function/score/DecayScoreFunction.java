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

package com.liferay.portal.search.query.function.score;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.search.query.MultiValueMode;

/**
 * @author Michael C. Han
 */
@ProviderType
public abstract class DecayScoreFunction extends ScoreFunction {

	public DecayScoreFunction(
		String field, Object origin, Object scale, Object offset) {

		this(field, origin, scale, offset, null);
	}

	public DecayScoreFunction(
		String field, Object origin, Object scale, Object offset,
		Double decay) {

		_field = field;
		_origin = origin;
		_scale = scale;
		_offset = offset;
		_decay = decay;
	}

	public Double getDecay() {
		return _decay;
	}

	public String getField() {
		return _field;
	}

	public MultiValueMode getMultiValueMode() {
		return _multiValueMode;
	}

	public Object getOffset() {
		return _offset;
	}

	public Object getOrigin() {
		return _origin;
	}

	public Object getScale() {
		return _scale;
	}

	public void setMultiValueMode(MultiValueMode multiValueMode) {
		_multiValueMode = multiValueMode;
	}

	private Double _decay;
	private final String _field;
	private MultiValueMode _multiValueMode;
	private Object _offset;
	private Object _origin;
	private Object _scale;

}
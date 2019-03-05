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

package com.liferay.portal.search.internal.query.function.score;

import com.liferay.portal.search.query.MultiValueMode;
import com.liferay.portal.search.query.function.score.DecayScoreFunction;

/**
 * @author Michael C. Han
 * @author Wade Cao
 * @author André de Oliveira
 */
public abstract class DecayScoreFunctionImpl
	extends ScoreFunctionImpl implements DecayScoreFunction {

	public DecayScoreFunctionImpl(
		String field, Object origin, Object scale, Object offset) {

		this(field, origin, scale, offset, null);
	}

	public DecayScoreFunctionImpl(
		String field, Object origin, Object scale, Object offset,
		Double decay) {

		_field = field;
		_origin = origin;
		_scale = scale;
		_offset = offset;
		_decay = decay;
	}

	@Override
	public Double getDecay() {
		return _decay;
	}

	@Override
	public String getField() {
		return _field;
	}

	@Override
	public MultiValueMode getMultiValueMode() {
		return _multiValueMode;
	}

	@Override
	public Object getOffset() {
		return _offset;
	}

	@Override
	public Object getOrigin() {
		return _origin;
	}

	@Override
	public Object getScale() {
		return _scale;
	}

	@Override
	public void setMultiValueMode(MultiValueMode multiValueMode) {
		_multiValueMode = multiValueMode;
	}

	private final Double _decay;
	private final String _field;
	private MultiValueMode _multiValueMode;
	private final Object _offset;
	private final Object _origin;
	private final Object _scale;

}
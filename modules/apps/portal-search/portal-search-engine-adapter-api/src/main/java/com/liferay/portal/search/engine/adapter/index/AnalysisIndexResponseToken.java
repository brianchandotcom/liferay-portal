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

package com.liferay.portal.search.engine.adapter.index;

import java.util.Map;

/**
 * @author Michael C. Han
 */
public class AnalysisIndexResponseToken {

	public AnalysisIndexResponseToken(String term) {
		_term = term;
	}

	public Map<String, Object> getAttributes() {
		return _attributes;
	}

	public int getEndOffset() {
		return _endOffset;
	}

	public int getPosition() {
		return _position;
	}

	public int getPositionLength() {
		return _positionLength;
	}

	public int getStartOffset() {
		return _startOffset;
	}

	public String getTerm() {
		return _term;
	}

	public String getType() {
		return _type;
	}

	public void setAttributes(Map<String, Object> attributes) {
		_attributes = attributes;
	}

	public void setEndOffset(int endOffset) {
		_endOffset = endOffset;
	}

	public void setPosition(int position) {
		_position = position;
	}

	public void setPositionLength(int positionLength) {
		_positionLength = positionLength;
	}

	public void setStartOffset(int startOffset) {
		_startOffset = startOffset;
	}

	public void setType(String type) {
		_type = type;
	}

	private Map<String, Object> _attributes;
	private int _endOffset;
	private int _position;
	private int _positionLength;
	private int _startOffset;
	private final String _term;
	private String _type;

}
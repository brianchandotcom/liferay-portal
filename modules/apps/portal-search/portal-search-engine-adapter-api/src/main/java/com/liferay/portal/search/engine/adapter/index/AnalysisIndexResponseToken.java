/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
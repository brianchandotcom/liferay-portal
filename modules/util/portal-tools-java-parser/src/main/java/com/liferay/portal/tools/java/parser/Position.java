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

package com.liferay.portal.tools.java.parser;

/**
 * @author Hugo Huijser
 */
public class Position implements Comparable<Position> {

	public Position(int lineNumber, int linePosition) {
		_lineNumber = lineNumber;
		_linePosition = linePosition;
	}

	@Override
	public int compareTo(Position position) {
		if (_lineNumber != position.getLineNumber()) {
			return _lineNumber - position.getLineNumber();
		}

		return _linePosition - position.getLinePosition();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Position)) {
			return false;
		}

		Position position = (Position)obj;

		if ((_lineNumber == position.getLineNumber()) &&
			(_linePosition == position.getLinePosition())) {

			return true;
		}

		return false;
	}

	public int getLineNumber() {
		return _lineNumber;
	}

	public int getLinePosition() {
		return _linePosition;
	}

	private final int _lineNumber;
	private final int _linePosition;

}
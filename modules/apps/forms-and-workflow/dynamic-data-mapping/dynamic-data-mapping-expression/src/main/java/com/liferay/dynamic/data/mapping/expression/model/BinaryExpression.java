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

package com.liferay.dynamic.data.mapping.expression.model;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Leonardo Barros
 */
@ProviderType
public abstract class BinaryExpression extends Expression {

	public Expression getLeftOperand() {
		return _leftOperand;
	}

	public Expression getRightOperand() {
		return _rightOperand;
	}

	public void setLeftOperand(Expression leftOperand) {
		_leftOperand = leftOperand;
	}

	public void setRightOperand(Expression rightOperand) {
		_rightOperand = rightOperand;
	}

	private Expression _leftOperand;
	private Expression _rightOperand;

}
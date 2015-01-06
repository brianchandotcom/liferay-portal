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

package com.liferay.pmd.rules.java;

import java.lang.reflect.Field;

import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTImplementsList;
import net.sourceforge.pmd.lang.java.ast.JavaNode;

/**
 * @author Shuyang Zhou
 */
public class OverrideBothEqualsAndHashcodeRule
	extends net.sourceforge.pmd.lang.java.rule.basic.OverrideBothEqualsAndHashcodeRule {

	@Override
	public Object visit(ASTImplementsList astImplementsList, Object data) {
		for (int i = 0; i < astImplementsList.jjtGetNumChildren(); i++) {
			Node node = astImplementsList.jjtGetChild(i);

			if (node instanceof ASTClassOrInterfaceType) {
				ASTClassOrInterfaceType astClassOrInterfaceType =
					(ASTClassOrInterfaceType)node;

				if ((astClassOrInterfaceType.getType() != null) &&
					node.hasImageEqualTo("Comparable")) {

					try {
						_implementsComparableField.set(this, true);
					}
					catch (ReflectiveOperationException roe) {
						throw new RuntimeException(roe);
					}

					return data;
				}
			}
		}

		return visit((JavaNode)astImplementsList, data);
	}

	private static final Field _implementsComparableField;

	static {
		Class<?> clazz = OverrideBothEqualsAndHashcodeRule.class;

		clazz = clazz.getSuperclass();

		try {
			_implementsComparableField = clazz.getDeclaredField(
				"implementsComparable");

			_implementsComparableField.setAccessible(true);
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

}
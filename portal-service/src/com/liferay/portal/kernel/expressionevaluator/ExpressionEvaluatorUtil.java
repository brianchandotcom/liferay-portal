
package com.liferay.portal.kernel.expressionevaluator;

import java.lang.reflect.InvocationTargetException;

import org.codehaus.commons.compiler.CompileException;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class ExpressionEvaluatorUtil {

	public static Object evaluate(String expression, Class<?> expressionType,
		String[] parameterNames, Class<?>[] parameterTypes, Object[] values)
		throws CompileException, InvocationTargetException {

		org.codehaus.janino.ExpressionEvaluator ee =
			new org.codehaus.janino.ExpressionEvaluator();

		ee.setExpressionType(expressionType);
		ee.setParameters(parameterNames, parameterTypes);
		ee.setExtendedClass(Functions.class);
		ee.cook(expression);

		Object result = ee.evaluate(values);

		return result;
	}
}
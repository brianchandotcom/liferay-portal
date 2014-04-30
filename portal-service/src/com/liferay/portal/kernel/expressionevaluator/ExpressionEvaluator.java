
package com.liferay.portal.kernel.expressionevaluator;

import com.liferay.portal.kernel.expressionevaluator.model.VariableDependencies;

import java.lang.reflect.InvocationTargetException;

import java.util.Map;

import org.codehaus.commons.compiler.CompileException;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public interface ExpressionEvaluator {

	Boolean evaluateBooleanExpression(String expression)
		throws CompileException, InvocationTargetException;

	Double evaluateDoubleExpression(String expression)
		throws CompileException, InvocationTargetException;

	Object evaluateExpression(String expression, Class<?> returnedType)
		throws CompileException, InvocationTargetException;

	Float evaluateFloatExpression(String expression)
		throws CompileException, InvocationTargetException;

	Integer evaluateIntegerExpression(String expression)
		throws CompileException, InvocationTargetException;

	Long evaluateLongExpression(String expression)
		throws CompileException, InvocationTargetException;

	String evaluateStringExpression(String expression)
		throws CompileException, InvocationTargetException;

	Map<String, VariableDependencies> getDependenciesMap();

}
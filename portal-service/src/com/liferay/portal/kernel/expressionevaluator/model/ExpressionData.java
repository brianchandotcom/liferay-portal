
package com.liferay.portal.kernel.expressionevaluator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class ExpressionData {

	public ExpressionData(String originalExpression) {

		_originalExpression = originalExpression;
		_variableNames = new ArrayList<String>();
		_variableTypes = new ArrayList<Class<?>>();
		_variableValues = new ArrayList<Object>();
	}

	public void addVariable(String variableName, Class<?> variableClass,
		Object variableValue) {

		_variableNames.add(variableName);
		_variableTypes.add(variableClass);
		_variableValues.add(variableValue);
	}

	public String getOriginalExpression() {

		return _originalExpression;
	}

	public String getRewritedExpression() {

		if (_rewritedExpression == null) {
			return _originalExpression;
		}

		return _rewritedExpression;
	}

	public String[] getVariableNames() {

		return _variableNames.toArray(new String[_variableNames.size()]);
	}

	public Class<?>[] getVariableTypes() {

		return _variableTypes.toArray(new Class<?>[_variableTypes.size()]);
	}

	public Object[] getVariableValues() {

		return _variableValues.toArray();
	}

	public void setRewritedExpression(String rewritedExpression) {

		this._rewritedExpression = rewritedExpression;
	}

	private final String _originalExpression;
	private String _rewritedExpression;
	private final List<String> _variableNames;
	private final List<Class<?>> _variableTypes;
	private final List<Object> _variableValues;

}
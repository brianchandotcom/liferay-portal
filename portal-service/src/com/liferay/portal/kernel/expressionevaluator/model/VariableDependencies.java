
package com.liferay.portal.kernel.expressionevaluator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class VariableDependencies {

	public VariableDependencies(String name) {

		this._name = name;
		_dependencies = new ArrayList<String>();
		_dependents = new ArrayList<String>();
	}

	public void addDependency(String variableDependency) {

		_dependencies.add(variableDependency);
	}

	public void addDependent(String variableDependent) {

		_dependents.add(variableDependent);
	}

	public List<String> getDependencies() {

		return _dependencies;
	}

	public List<String> getDependents() {

		return _dependents;
	}

	public String getName() {

		return _name;
	}

	private final List<String> _dependencies;
	private final List<String> _dependents;
	private final String _name;
}
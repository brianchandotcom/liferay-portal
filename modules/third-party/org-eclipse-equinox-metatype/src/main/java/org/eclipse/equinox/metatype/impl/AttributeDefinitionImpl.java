/*******************************************************************************
 * Copyright (c) 2005, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.equinox.metatype.impl;

import java.util.*;
import org.eclipse.equinox.metatype.EquinoxAttributeDefinition;
import org.eclipse.osgi.util.NLS;
import org.osgi.service.log.LogService;

/**
 * Implementation of AttributeDefintion
 */
public class AttributeDefinitionImpl extends LocalizationElement implements EquinoxAttributeDefinition, Cloneable {

	String _name;
	String _id;
	String _description;
	int _cardinality = 0;
	int _dataType;
	Object _minValue = null;
	Object _maxValue = null;
	boolean _isRequired = true;

	String[] _defaults = null;
	Vector<String> _values = new Vector<String>(7);
	Vector<String> _labels = new Vector<String>(7);

	private final LogService logger;
	private final ExtendableHelper helper;

	/**
	 * Constructor of class AttributeDefinitionImpl.
	 */
	public AttributeDefinitionImpl(String id, String name, String description, int type, int cardinality, Object min, Object max, boolean isRequired, String localization, LogService logger, Map<String, Map<String, String>> extensionAttributes) {
		this(id, name, description, type, cardinality, min, max, isRequired, localization, logger, new ExtendableHelper(extensionAttributes));
	}

	private AttributeDefinitionImpl(String id, String name, String description, int type, int cardinality, Object min, Object max, boolean isRequired, String localization, LogService logger, ExtendableHelper helper) {
		super(localization);
		this._id = id;
		this._name = name;
		this._description = description;
		this._dataType = type;
		this._cardinality = cardinality;
		this._minValue = min;
		this._maxValue = max;
		this._isRequired = isRequired;
		this.logger = logger;
		this.helper = helper;
	}

	/*
	 * 
	 */
	public synchronized Object clone() {

		AttributeDefinitionImpl ad = new AttributeDefinitionImpl(_id, _name, _description, _dataType, _cardinality, _minValue, _maxValue, _isRequired, getLocalization(), logger, helper);

		if (_defaults != null) {
			ad.setDefaultValue(_defaults.clone());
		}
		if ((_labels != null) && (_values != null)) {
			@SuppressWarnings("unchecked")
			Vector<String> labels = (Vector<String>) _labels.clone();
			@SuppressWarnings("unchecked")
			Vector<String> values = (Vector<String>) _values.clone();
			ad.setOption(labels, values, false);
		}

		return ad;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.service.metatype.AttributeDefinition#getName()
	 */
	public String getName() {
		return getLocalized(_name);
	}

	/**
	 * Method to set the name of AttributeDefinition.
	 */
	void setName(String name) {
		this._name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.service.metatype.AttributeDefinition#getID()
	 */
	public String getID() {
		return _id;
	}

	/**
	 * Method to set the ID of AttributeDefinition.
	 */
	void setID(String id) {
		this._id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.service.metatype.AttributeDefinition#getDescription()
	 */
	public String getDescription() {
		return getLocalized(_description);
	}

	/**
	 * Method to set the description of AttributeDefinition.
	 */
	void setDescription(String description) {
		this._description = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.service.metatype.AttributeDefinition#getCardinality()
	 */
	public int getCardinality() {
		return _cardinality;
	}

	/**
	 * Method to set the cardinality of AttributeDefinition.
	 */
	void setCardinality(int cardinality) {
		this._cardinality = cardinality;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.service.metatype.AttributeDefinition#getType()
	 */
	public int getType() {
		return _dataType;
	}

	/**
	 * Method to set the data type of AttributeDefinition.
	 */
	void setType(int type) {
		this._dataType = type;
	}

	/**
	 * Method to get the required flag of AttributeDefinition.
	 */
	boolean isRequired() {
		return _isRequired;
	}

	/**
	 * Method to set the required flag of AttributeDefinition.
	 */
	void setRequired(boolean isRequired) {
		this._isRequired = isRequired;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.service.metatype.AttributeDefinition#getOptionLabels()
	 */
	public String[] getOptionLabels() {

		if ((_labels == null) || (_labels.size() == 0)) {
			return null;
		}

		String[] returnedLabels = new String[_labels.size()];
		Enumeration<String> labelKeys = _labels.elements();
		int i = 0;
		while (labelKeys.hasMoreElements()) {
			String labelKey = labelKeys.nextElement();
			returnedLabels[i] = getLocalized(labelKey);
			i++;
		}
		return returnedLabels;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.service.metatype.AttributeDefinition#getOptionValues()
	 */
	public String[] getOptionValues() {

		if ((_values == null) || (_values.size() == 0)) {
			return null;
		}

		return _values.toArray(new String[_values.size()]);
	}

	/**
	 * Method to set the Option values of AttributeDefinition.
	 */
	void setOption(Vector<String> labels, Vector<String> values, boolean needValidation) {
		if ((labels == null) || (values == null)) {
			logger.log(LogService.LOG_ERROR, NLS.bind(MetaTypeMsg.NULL_OPTIONS, getID()));
			return;
		}
		if (labels.size() != values.size()) {
			logger.log(LogService.LOG_ERROR, NLS.bind(MetaTypeMsg.INCONSISTENT_OPTIONS, getID()));
			return;
		}
		_labels = labels;
		_values = values;
		if (needValidation) {
			for (int index = 0; index < _values.size(); index++) {
				ValueTokenizer vt = new ValueTokenizer(_cardinality, _values.get(index), logger);
				_values.set(index, vt.getValuesAsString());
				String reason = vt.validate(this);
				if ((reason != null) && reason.length() > 0) {
					logger.log(LogService.LOG_WARNING, NLS.bind(MetaTypeMsg.INVALID_OPTIONS, new Object[] {_values.get(index), getID(), reason}));
					_labels.remove(index);
					_values.remove(index);
					index--; // Because this one has been removed.
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.service.metatype.AttributeDefinition#getDefaultValue()
	 */
	public String[] getDefaultValue() {
		return _defaults;
	}

	/**
	 * Method to set the default value of AttributeDefinition.
	 * The given parameter is a comma delimited list needed to be parsed.
	 */
	void setDefaultValue(String defaults_str, boolean needValidation) {
		ValueTokenizer vt = new ValueTokenizer(_cardinality, defaults_str, logger);
		String reason = vt.validate(this);
		if ((reason != null) && reason.length() > 0) {
			logger.log(LogService.LOG_WARNING, NLS.bind(MetaTypeMsg.INVALID_DEFAULTS, new Object[] {vt.getValuesAsString(), getID(), reason}));
			return;
		}
		String[] defaults = vt.getValuesAsArray();
		// If the default value is a single empty string and cardinality != 0, the default value must become String[0].
		// We know the cardinality has already been set in the constructor.
		if (_cardinality != 0 && defaults.length == 1 && defaults[0].length() == 0)
			setDefaultValue(new String[0]);
		else
			setDefaultValue(vt.getValuesAsArray());
	}

	/**
	 * Method to set the default value of AttributeDefinition.
	 * The given parameter is a String array of multi values.
	 */
	private void setDefaultValue(String[] defaults) {
		_defaults = defaults;
	}

	/**
	 * Method to set the validation value - min of AttributeDefinition.
	 */
	void setMinValue(Object minValue) {
		this._minValue = minValue;
	}

	/**
	 * Method to set the validation value - max of AttributeDefinition.
	 */
	void setMaxValue(Object maxValue) {
		this._maxValue = maxValue;
	}

	/*
	 * (non-Javadoc)
	 * In order to be valid, a value must pass all of the following tests.
	 * (1) The value must not be null.
	 * (2) The value must be convertible into the attribute definition's type.
	 * (3) The following relation must hold: min <= value <= max, if either min or max was specified.
	 * (4) If options were specified, the value must be equal to one of them.
	 * 
	 * Note this method will never return null to indicate there's no validation
	 * present. The type compatibility check can always be performed.
	 * 
	 * @see org.osgi.service.metatype.AttributeDefinition#validate(java.lang.String)
	 */
	public String validate(String value) {
		ValueTokenizer vt = new ValueTokenizer(_cardinality, value, logger);
		return vt.validate(this);
	}

	public Map<String, String> getExtensionAttributes(String schema) {
		return helper.getExtensionAttributes(schema);
	}

	public Set<String> getExtensionUris() {
		return helper.getExtensionUris();
	}

	public String getMax() {
		return _maxValue == null ? null : String.valueOf(_maxValue);
	}

	public String getMin() {
		return _minValue == null ? null : String.valueOf(_minValue);
	}
}
/* @generated */
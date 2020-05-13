/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *	  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.felix.utils.properties;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.framework.BundleContext;

/**
 * <p>
 * Enhancement of the standard <code>Properties</code>
 * managing the maintain of comments, etc.
 * </p>
 *
 * @author gnodet, jbonofre
 */
public class InterpolationHelper {

	/**
	 * Perform substitution on a property set
	 *
	 * @param properties the property set to perform substitution on
	 */
	public static void performSubstitution(Map<String, String> properties) {
		performSubstitution(properties, (BundleContext)null);
	}

	/**
	 * Perform substitution on a property set
	 *
	 * @param properties the property set to perform substitution on
	 * @param bundleContext The bundle context
	 */
	public static void performSubstitution(
		Map<String, String> properties, BundleContext bundleContext) {

		performSubstitution(
			properties, new BundleContextSubstitutionCallback(bundleContext));
	}

	/**
	 * Perform substitution on a property set
	 *
	 * @param properties the property set to perform substitution on
	 * @param substitutionCallback Callback for substituion
	 */
	public static void performSubstitution(
		Map<String, String> properties,
		SubstitutionCallback substitutionCallback) {

		performSubstitution(properties, substitutionCallback, true, true, true);
	}

	/**
	 * Perform substitution on a property set
	 *
	 * @param properties the property set to perform substitution on
	 * @param callback the callback to obtain substitution values
	 * @param substituteFromConfig If substitute from configuration
	 * @param substituteFromSystemProperties If substitute from system properties
	 * @param defaultsToEmptyString sets an empty string if a replacement value is not found, leaves intact otherwise
	 */
	public static void performSubstitution(
		Map<String, String> properties, SubstitutionCallback callback,
		boolean substituteFromConfig, boolean substituteFromSystemProperties,
		boolean defaultsToEmptyString) {

		Map<String, String> map = new HashMap<>(properties);

		for (Map.Entry<String, String> entry : properties.entrySet()) {
			String name = entry.getKey();

			properties.put(
				name,
				substVars(
					entry.getValue(), name, null, map, callback,
					substituteFromConfig, substituteFromSystemProperties,
					defaultsToEmptyString));
		}
	}

	/**
	 * <p>
	 * This method performs property variable substitution on the
	 * specified value. If the specified value contains the syntax
	 * <tt>${&lt;prop-name&gt;}</tt>, where <tt>&lt;prop-name&gt;</tt>
	 * refers to either a configuration property or a system property,
	 * then the corresponding property value is substituted for the variable
	 * placeholder. Multiple variable placeholders may exist in the
	 * specified value as well as nested variable placeholders, which
	 * are substituted from inner most to outer most. Configuration
	 * properties override system properties.
	 * </p>
	 *
	 * @param value The string on which to perform property substitution.
	 * @param currentKey The key of the property being evaluated used to
	 *		detect cycles.
	 * @param cycleMap Map of variable references used to detect nested cycles.
	 * @param configProps Set of configuration properties.
	 * @return The value of the specified string after system property substitution.
	 * @throws IllegalArgumentException If there was a syntax error in the
	 *		 property placeholder syntax or a recursive variable reference.
	 **/
	public static String substVars(
			String value, String currentKey, Map<String, String> cycleMap,
			Map<String, String> configProps)
		throws IllegalArgumentException {

		return substVars(
			value, currentKey, cycleMap, configProps,
			(SubstitutionCallback)null);
	}

	/**
	 * <p>
	 * This method performs property variable substitution on the
	 * specified value. If the specified value contains the syntax
	 * <tt>${&lt;prop-name&gt;}</tt>, where <tt>&lt;prop-name&gt;</tt>
	 * refers to either a configuration property or a system property,
	 * then the corresponding property value is substituted for the variable
	 * placeholder. Multiple variable placeholders may exist in the
	 * specified value as well as nested variable placeholders, which
	 * are substituted from inner most to outer most. Configuration
	 * properties override system properties.
	 * </p>
	 *
	 * @param value The string on which to perform property substitution.
	 * @param currentKey The key of the property being evaluated used to
	 *		detect cycles.
	 * @param cycleMap Map of variable references used to detect nested cycles.
	 * @param configProps Set of configuration properties.
	 * @param bundleContext the bundle context to retrieve properties from
	 * @return The value of the specified string after system property substitution.
	 * @throws IllegalArgumentException If there was a syntax error in the
	 *		 property placeholder syntax or a recursive variable reference.
	 **/
	public static String substVars(
			String value, String currentKey, Map<String, String> cycleMap,
			Map<String, String> configProps, BundleContext bundleContext)
		throws IllegalArgumentException {

		return substVars(
			value, currentKey, cycleMap, configProps,
			new BundleContextSubstitutionCallback(bundleContext));
	}

	/**
	 * <p>
	 * This method performs property variable substitution on the
	 * specified value. If the specified value contains the syntax
	 * <tt>${&lt;prop-name&gt;}</tt>, where <tt>&lt;prop-name&gt;</tt>
	 * refers to either a configuration property or a system property,
	 * then the corresponding property value is substituted for the variable
	 * placeholder. Multiple variable placeholders may exist in the
	 * specified value as well as nested variable placeholders, which
	 * are substituted from inner most to outer most. Configuration
	 * properties override system properties.
	 * </p>
	 *
	 * @param value The string on which to perform property substitution.
	 * @param currentKey The key of the property being evaluated used to
	 *		detect cycles.
	 * @param cycleMap Map of variable references used to detect nested cycles.
	 * @param configProps Set of configuration properties.
	 * @param callback the callback to obtain substitution values
	 * @return The value of the specified string after system property substitution.
	 * @throws IllegalArgumentException If there was a syntax error in the
	 *		 property placeholder syntax or a recursive variable reference.
	 **/
	public static String substVars(
			String value, String currentKey, Map<String, String> cycleMap,
			Map<String, String> configProps, SubstitutionCallback callback)
		throws IllegalArgumentException {

		return substVars(
			value, currentKey, cycleMap, configProps, callback, true, true,
			true);
	}

	/**
	 * <p>
	 * This method performs property variable substitution on the
	 * specified value. If the specified value contains the syntax
	 * <tt>${&lt;prop-name&gt;}</tt>, where <tt>&lt;prop-name&gt;</tt>
	 * refers to either a configuration property or a system property,
	 * then the corresponding property value is substituted for the variable
	 * placeholder. Multiple variable placeholders may exist in the
	 * specified value as well as nested variable placeholders, which
	 * are substituted from inner most to outer most. Configuration
	 * properties override system properties.
	 * </p>
	 *
	 * @param value The string on which to perform property substitution.
	 * @param currentKey The key of the property being evaluated used to
	 *		detect cycles.
	 * @param cycleMap Map of variable references used to detect nested cycles.
	 * @param configProps Set of configuration properties.
	 * @param callback the callback to obtain substitution values
	 * @param substituteFromConfig If substitute from configuration
	 * @param substituteFromSystemProperties If substitute from system properties
	 * @param defaultsToEmptyString sets an empty string if a replacement value is not found, leaves intact otherwise
	 * @return The value of the specified string after system property substitution.
	 * @throws IllegalArgumentException If there was a syntax error in the
	 *		 property placeholder syntax or a recursive variable reference.
	 **/
	public static String substVars(
			String value, String currentKey, Map<String, String> cycleMap,
			Map<String, String> configProps, SubstitutionCallback callback,
			boolean substituteFromConfig,
			boolean substituteFromSystemProperties,
			boolean defaultsToEmptyString)
		throws IllegalArgumentException {

		return _unescape(
			_substVars(
				value, currentKey, cycleMap, configProps, callback,
				substituteFromConfig, substituteFromSystemProperties,
				defaultsToEmptyString));
	}

	public static class BundleContextSubstitutionCallback
		implements SubstitutionCallback {

		public BundleContextSubstitutionCallback(BundleContext context) {
			_bundleContext = context;
		}

		@Override
		public String getValue(String key) {
			String value = null;

			if (key.startsWith(_ENV_PREFIX)) {
				value = System.getenv(key.substring(_ENV_PREFIX.length()));
			}
			else {
				if (_bundleContext != null) {
					value = _bundleContext.getProperty(key);
				}

				if (value == null) {
					value = System.getProperty(key);
				}
			}

			return value;
		}

		private final BundleContext _bundleContext;

	}

	/**
	 * Callback for substitution
	 */
	public interface SubstitutionCallback {

		public String getValue(String key);

	}

	private static int _indexOf(String value, int fromIndex) {
		Matcher escapedOpeningCurlyMatcher = _escapedOpeningCurly.matcher(
			value);

		Matcher escapedClosingCurlyMatcher = _escapedClosingCurly.matcher(
			value);

		int escapedOpeningCurlyMatcherIndex = Integer.MAX_VALUE;

		if (escapedOpeningCurlyMatcher.find(fromIndex)) {
			escapedOpeningCurlyMatcherIndex =
				escapedOpeningCurlyMatcher.start();
		}

		int escapedClosingCurlyMatcherIndex = Integer.MAX_VALUE;

		if (escapedClosingCurlyMatcher.find(fromIndex)) {
			escapedClosingCurlyMatcherIndex =
				escapedClosingCurlyMatcher.start();
		}

		int index = Math.min(
			escapedOpeningCurlyMatcherIndex, escapedClosingCurlyMatcherIndex);

		if (index == Integer.MAX_VALUE) {
			return -1;
		}

		return index;
	}

	private static String _substVars(
			String value, String currentKey, Map<String, String> cycleMap,
			Map<String, String> configProps, SubstitutionCallback callback,
			boolean substituteFromConfig,
			boolean substituteFromSystemProperties,
			boolean defaultsToEmptyString)
		throws IllegalArgumentException {

		if (cycleMap == null) {
			cycleMap = new HashMap<>();
		}

		// Put the current key in the cycle map.

		cycleMap.put(currentKey, currentKey);

		// Assume we have a value that is something like:
		// "leading ${foo.${bar}} middle ${baz} trailing"

		// Find the first ending '}' variable delimiter, which
		// will correspond to the first deepest nested variable
		// placeholder.

		int startDelim = value.indexOf(_DELIM_START);
		int stopDelim = value.indexOf(_DELIM_STOP);

		while ((startDelim >= 0) && (stopDelim >= 0)) {
			while ((stopDelim > 0) &&
				   (value.charAt(stopDelim - 1) == _ESCAPE_CHAR)) {

				stopDelim = value.indexOf(_DELIM_STOP, stopDelim + 1);
			}

			// Find the matching starting "${" variable delimiter
			// by looping until we find a start delimiter that is
			// greater than the stop delimiter we have found.

			while (stopDelim >= 0) {
				int index = value.indexOf(
					_DELIM_START, startDelim + _DELIM_START.length());

				if ((index < 0) || (index > stopDelim)) {
					break;
				}
				else if (index < stopDelim) {
					startDelim = index;
				}
			}

			if (startDelim < stopDelim) {
				break;
			}

			stopDelim = value.indexOf(_DELIM_STOP, stopDelim + 1);
			startDelim = value.indexOf(_DELIM_START);
		}

		// If we do not have a start or stop delimiter, then just
		// return the existing value.

		if ((startDelim < 0) || (stopDelim < 0)) {
			cycleMap.remove(currentKey);

			return value;
		}

		// At this point, we have found a variable placeholder so
		// we must perform a variable substitution on it.
		// Using the start and stop delimiter indices, extract
		// the first, deepest nested variable placeholder.

		String variable = value.substring(
			startDelim + _DELIM_START.length(), stopDelim);

		String original = variable;

		// Strip expansion modifiers

		int index1 = variable.lastIndexOf(":-");
		int index2 = variable.lastIndexOf(":+");

		int index = -1;

		if ((index1 >= 0) && (index2 >= 0)) {
			index = Math.min(index1, index1);
		}
		else if (index1 >= 0) {
			index = index1;
		}
		else {
			index = index2;
		}

		String op = null;

		if ((index >= 0) && (index < variable.length())) {
			op = variable.substring(index);

			variable = variable.substring(0, index);
		}

		// Verify that this is not a recursive variable reference.

		if (cycleMap.get(variable) != null) {
			throw new IllegalArgumentException(
				"recursive variable reference: " + variable);
		}

		String substValue = null;

		// Get the value of the deepest nested variable placeholder.
		// Try to configuration properties first.

		if (substituteFromConfig && (configProps != null)) {
			substValue = configProps.get(variable);
		}

		if ((substValue == null) && (variable.length() > 0)) {
			if (callback != null) {
				substValue = callback.getValue(variable);
			}

			if ((substValue == null) && substituteFromSystemProperties) {
				substValue = System.getProperty(variable);
			}
		}

		if (op != null) {
			if (op.startsWith(":-")) {
				if ((substValue == null) || (substValue.length() == 0)) {
					substValue = op.substring(":-".length());
				}
			}
			else if (op.startsWith(":+")) {
				if ((substValue != null) && (substValue.length() != 0)) {
					substValue = op.substring(":+".length());
				}
			}
			else {
				throw new IllegalArgumentException(
					"Bad substitution: ${" + original + "}");
			}
		}

		if (substValue == null) {
			if (defaultsToEmptyString) {
				substValue = "";
			}
			else {

				// alters the original token to avoid infinite recursion
				// altered tokens are reverted in substVarsPreserveUnresolved()

				substValue = _MARKER + "{" + variable + "}";
			}
		}

		// Remove the found variable from the cycle map, since
		// it may appear more than once in the value and we don't
		// want such situations to appear as a recursive reference.

		cycleMap.remove(variable);

		// Append the leading characters, the substituted value of
		// the variable, and the trailing characters to get the new
		// value.

		value =
			value.substring(0, startDelim) + substValue +
				value.substring(stopDelim + _DELIM_STOP.length());

		// Now perform substitution again, since there could still
		// be substitutions to make.

		value = _substVars(
			value, currentKey, cycleMap, configProps, callback,
			substituteFromConfig, substituteFromSystemProperties,
			defaultsToEmptyString);

		cycleMap.remove(currentKey);

		// Return the value.

		return value;
	}

	private static String _unescape(String value) {
		value = value.replaceAll("\\" + _MARKER, "\\$");

		Matcher existingSubstVarMatcher = _existingSubstVar.matcher(value);

		if (!existingSubstVarMatcher.matches()) {
			return value;
		}

		int escape = _indexOf(value, 0);

		while ((escape >= 0) && (escape < (value.length() - 1))) {
			char c = value.charAt(escape + 1);

			if ((c == '{') || (c == '}') || (c == _ESCAPE_CHAR)) {
				value =
					value.substring(0, escape) + value.substring(escape + 1);
			}

			escape = _indexOf(value, escape + 1);
		}

		return value;
	}

	private InterpolationHelper() {
	}

	private static final String _DELIM_START = "${";

	private static final String _DELIM_STOP = "}";

	private static final String _ENV_PREFIX = "env:";

	private static final char _ESCAPE_CHAR = '\\';

	private static final String _MARKER = "$__";

	private static final Pattern _escapedClosingCurly = Pattern.compile(
		"\\\\+\\}");
	private static final Pattern _escapedOpeningCurly = Pattern.compile(
		"\\\\+\\{");
	private static final Pattern _existingSubstVar = Pattern.compile(
		".*\\$\\\\*\\{.*\\}.*");

}
/* @generated */
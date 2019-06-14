/*******************************************************************************
 * Copyright (c) 2003, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.osgi.internal.framework;

import java.lang.reflect.*;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;
import java.util.concurrent.Callable;

import org.eclipse.osgi.framework.util.CaseInsensitiveDictionaryMap;
import org.eclipse.osgi.framework.util.WeakCacheHook;
import org.eclipse.osgi.internal.debug.Debug;
import org.eclipse.osgi.internal.messages.Msg;
import org.eclipse.osgi.internal.serviceregistry.ServiceReferenceImpl;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.*;

/**
 * RFC 1960-based Filter. Filter objects can be created by calling
 * the constructor with the desired filter string.
 * A Filter object can be called numerous times to determine if the
 * match argument matches the filter string that was used to create the Filter
 * object.
 *
 * <p>The syntax of a filter string is the string representation
 * of LDAP search filters as defined in RFC 1960:
 * <i>A String Representation of LDAP Search Filters</i> (available at
 * http://www.ietf.org/rfc/rfc1960.txt).
 * It should be noted that RFC 2254:
 * <i>A String Representation of LDAP Search Filters</i>
 * (available at http://www.ietf.org/rfc/rfc2254.txt) supersedes
 * RFC 1960 but only adds extensible matching and is not applicable for this
 * API.
 *
 * <p>The string representation of an LDAP search filter is defined by the
 * following grammar. It uses a prefix format.
 * <pre>
 *   &lt;filter&gt; ::= '(' &lt;filtercomp&gt; ')'
 *   &lt;filtercomp&gt; ::= &lt;and&gt; | &lt;or&gt; | &lt;not&gt; | &lt;item&gt;
 *   &lt;and&gt; ::= '&' &lt;filterlist&gt;
 *   &lt;or&gt; ::= '|' &lt;filterlist&gt;
 *   &lt;not&gt; ::= '!' &lt;filter&gt;
 *   &lt;filterlist&gt; ::= &lt;filter&gt; | &lt;filter&gt; &lt;filterlist&gt;
 *   &lt;item&gt; ::= &lt;simple&gt; | &lt;present&gt; | &lt;substring&gt;
 *   &lt;simple&gt; ::= &lt;attr&gt; &lt;filtertype&gt; &lt;value&gt;
 *   &lt;filtertype&gt; ::= &lt;equal&gt; | &lt;approx&gt; | &lt;greater&gt; | &lt;less&gt;
 *   &lt;equal&gt; ::= '='
 *   &lt;approx&gt; ::= '~='
 *   &lt;greater&gt; ::= '&gt;='
 *   &lt;less&gt; ::= '&lt;='
 *   &lt;present&gt; ::= &lt;attr&gt; '=*'
 *   &lt;substring&gt; ::= &lt;attr&gt; '=' &lt;initial&gt; &lt;any&gt; &lt;final&gt;
 *   &lt;initial&gt; ::= NULL | &lt;value&gt;
 *   &lt;any&gt; ::= '*' &lt;starval&gt;
 *   &lt;starval&gt; ::= NULL | &lt;value&gt; '*' &lt;starval&gt;
 *   &lt;final&gt; ::= NULL | &lt;value&gt;
 * </pre>
 *
 * <code>&lt;attr&gt;</code> is a string representing an attribute, or
 * key, in the properties objects of the registered services.
 * Attribute names are not case sensitive;
 * that is cn and CN both refer to the same attribute.
 * <code>&lt;value&gt;</code> is a string representing the value, or part of
 * one, of a key in the properties objects of the registered services.
 * If a <code>&lt;value&gt;</code> must
 * contain one of the characters '<code>*</code>' or '<code>(</code>'
 * or '<code>)</code>', these characters
 * should be escaped by preceding them with the backslash '<code>\</code>'
 * character.
 * Note that although both the <code>&lt;substring&gt;</code> and
 * <code>&lt;present&gt;</code> productions can
 * produce the <code>'attr=*'</code> construct, this construct is used only to
 * denote a presence filter.
 *
 * <p>Examples of LDAP filters are:
 *
 * <pre>
 *   &quot;(cn=Babs Jensen)&quot;
 *   &quot;(!(cn=Tim Howes))&quot;
 *   &quot;(&(&quot; + Constants.OBJECTCLASS + &quot;=Person)(|(sn=Jensen)(cn=Babs J*)))&quot;
 *   &quot;(o=univ*of*mich*)&quot;
 * </pre>
 *
 * <p>The approximate match (<code>~=</code>) is implementation specific but
 * should at least ignore case and white space differences. Optional are
 * codes like soundex or other smart "closeness" comparisons.
 *
 * <p>Comparison of values is not straightforward. Strings
 * are compared differently than numbers and it is
 * possible for a key to have multiple values. Note that
 * that keys in the match argument must always be strings.
 * The comparison is defined by the object type of the key's
 * value. The following rules apply for comparison:
 *
 * <blockquote>
 * <TABLE BORDER=0>
 * <TR><TD><b>Property Value Type </b></TD><TD><b>Comparison Type</b></TD></TR>
 * <TR><TD>String </TD><TD>String comparison</TD></TR>
 * <TR valign=top><TD>Integer, Long, Float, Double, Byte, Short, BigInteger, BigDecimal </TD><TD>numerical comparison</TD></TR>
 * <TR><TD>Character </TD><TD>character comparison</TD></TR>
 * <TR><TD>Boolean </TD><TD>equality comparisons only</TD></TR>
 * <TR><TD>[] (array)</TD><TD>recursively applied to values </TD></TR>
 * <TR><TD>Vector</TD><TD>recursively applied to elements </TD></TR>
 * </TABLE>
 * Note: arrays of primitives are also supported.
 * </blockquote>
 *
 * A filter matches a key that has multiple values if it
 * matches at least one of those values. For example,
 * <pre>
 *   Dictionary d = new Hashtable();
 *   d.put( "cn", new String[] { "a", "b", "c" } );
 * </pre>
 *   d will match <code>(cn=a)</code> and also <code>(cn=b)</code>
 *
 * <p>A filter component that references a key having an unrecognizable
 * data type will evaluate to <code>false</code> .
 */

public class FilterImpl implements Filter /* since Framework 1.1 */ {
	/* public methods in org.osgi.framework.Filter */

	/**
	 * Constructs a {@link FilterImpl} object. This filter object may be used
	 * to match a {@link ServiceReferenceImpl} or a Dictionary.
	 *
	 * <p> If the filter cannot be parsed, an {@link InvalidSyntaxException}
	 * will be thrown with a human readable message where the
	 * filter became unparsable.
	 *
	 * @param filterString the filter string.
	 * @exception InvalidSyntaxException If the filter parameter contains
	 * an invalid filter string that cannot be parsed.
	 */
	public static FilterImpl newInstance(String filterString) throws InvalidSyntaxException {
		return newInstance(filterString, false);
	}

	public static FilterImpl newInstance(final String filterString, boolean debug) throws InvalidSyntaxException {
		if ((_weakCacheHook != null) && !debug) {
			try {
				return _weakCacheHook.computeIfAbsent(
					filterString,
					new Callable<FilterImpl>() {

						@Override
						public FilterImpl call() throws InvalidSyntaxException {
							return new Parser(filterString, false).parse();
						}

					});
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Exception e) {
				throw (InvalidSyntaxException)e;
			}
		}

		return new Parser(filterString, debug).parse();
	}

	/**
	 * Filter using a service's properties.
	 * <p>
	 * This {@code Filter} is executed using the keys and values of the
	 * referenced service's properties. The keys are looked up in a case
	 * insensitive manner.
	 * 
	 * @param reference The reference to the service whose properties are used
	 *        in the match.
	 * @return {@code true} if the service's properties match this
	 *         {@code Filter}; {@code false} otherwise.
	 */
	public boolean match(ServiceReference<?> reference) {
		if (reference instanceof ServiceReferenceImpl) {
			return matches(((ServiceReferenceImpl<?>) reference).getRegistration().getProperties());
		}
		return matches(new ServiceReferenceMap(reference));
	}

	/**
	 * Filter using a {@code Dictionary} with case insensitive key lookup. This
	 * {@code Filter} is executed using the specified {@code Dictionary}'s keys
	 * and values. The keys are looked up in a case insensitive manner.
	 * 
	 * @param dictionary The {@code Dictionary} whose key/value pairs are used
	 *        in the match.
	 * @return {@code true} if the {@code Dictionary}'s values match this
	 *         filter; {@code false} otherwise.
	 * @throws IllegalArgumentException If {@code dictionary} contains case
	 *         variants of the same key name.
	 */
	public boolean match(Dictionary<String, ?> dictionary) {
		if (dictionary == null) {
			return matches(null);
		}
		return matches(new CaseInsensitiveDictionaryMap<>(dictionary));
	}

	/**
	 * Filter using a {@code Dictionary}. This {@code Filter} is executed using
	 * the specified {@code Dictionary}'s keys and values. The keys are looked
	 * up in a normal manner respecting case.
	 * 
	 * @param dictionary The {@code Dictionary} whose key/value pairs are used
	 *        in the match.
	 * @return {@code true} if the {@code Dictionary}'s values match this
	 *         filter; {@code false} otherwise.
	 * @since 1.3
	 */
	public boolean matchCase(Dictionary<String, ?> dictionary) {
		switch (op) {
			case AND : {
				FilterImpl[] filters = (FilterImpl[]) value;
				for (FilterImpl f : filters) {
					if (!f.matchCase(dictionary)) {
						return false;
					}
				}

				return true;
			}

			case OR : {
				FilterImpl[] filters = (FilterImpl[]) value;
				for (FilterImpl f : filters) {
					if (f.matchCase(dictionary)) {
						return true;
					}
				}

				return false;
			}

			case NOT : {
				FilterImpl filter = (FilterImpl) value;

				return !filter.matchCase(dictionary);
			}

			case SUBSTRING :
			case EQUAL :
			case GREATER :
			case LESS :
			case APPROX : {
				Object prop = (dictionary == null) ? null : dictionary.get(attr);

				return compare(op, prop, value);
			}

			case PRESENT : {
				if (debug) {
					Debug.println("PRESENT(" + attr + ")"); //$NON-NLS-1$ //$NON-NLS-2$
				}

				Object prop = (dictionary == null) ? null : dictionary.get(attr);

				return prop != null;
			}
		}

		return false;
	}

	/**
	 * Filter using a {@code Map}. This {@code Filter} is executed using the
	 * specified {@code Map}'s keys and values. The keys are looked up in a
	 * normal manner respecting case.
	 * 
	 * @param map The {@code Map} whose key/value pairs are used in the match.
	 *        Maps with {@code null} key or values are not supported. A
	 *        {@code null} value is considered not present to the filter.
	 * @return {@code true} if the {@code Map}'s values match this filter;
	 *         {@code false} otherwise.
	 * @since 1.6
	 */
	public boolean matches(Map<String, ?> map) {
		switch (op) {
			case AND : {
				FilterImpl[] filters = (FilterImpl[]) value;
				for (FilterImpl f : filters) {
					if (!f.matches(map)) {
						return false;
					}
				}

				return true;
			}

			case OR : {
				FilterImpl[] filters = (FilterImpl[]) value;
				for (FilterImpl f : filters) {
					if (f.matches(map)) {
						return true;
					}
				}

				return false;
			}

			case NOT : {
				FilterImpl filter = (FilterImpl) value;

				return !filter.matches(map);
			}

			case SUBSTRING :
			case EQUAL :
			case GREATER :
			case LESS :
			case APPROX : {
				Object prop = (map == null) ? null : map.get(attr);

				return compare(op, prop, value);
			}

			case PRESENT : {
				if (debug) {
					Debug.println("PRESENT(" + attr + ")"); //$NON-NLS-1$ //$NON-NLS-2$
				}

				Object prop = (map == null) ? null : map.get(attr);

				return prop != null;
			}
		}

		return false;
	}

	/**
	 * Returns this <code>Filter</code> object's filter string.
	 * <p>
	 * The filter string is normalized by removing whitespace which does not
	 * affect the meaning of the filter.
	 *
	 * @return Filter string.
	 */

	public String toString() {
		String result = filterString;
		if (result == null) {
			filterString = result = normalize().toString();
		}
		return result;
	}

	/**
	 * Returns this <code>Filter</code>'s normalized filter string.
	 * <p>
	 * The filter string is normalized by removing whitespace which does not
	 * affect the meaning of the filter.
	 * 
	 * @return This <code>Filter</code>'s filter string.
	 */
	private StringBuffer normalize() {
		StringBuffer sb = new StringBuffer();
		sb.append('(');

		switch (op) {
			case AND : {
				sb.append('&');

				FilterImpl[] filters = (FilterImpl[]) value;
				for (FilterImpl f : filters) {
					sb.append(f.normalize());
				}

				break;
			}

			case OR : {
				sb.append('|');

				FilterImpl[] filters = (FilterImpl[]) value;
				for (FilterImpl f : filters) {
					sb.append(f.normalize());
				}

				break;
			}

			case NOT : {
				sb.append('!');
				FilterImpl filter = (FilterImpl) value;
				sb.append(filter.normalize());

				break;
			}

			case SUBSTRING : {
				sb.append(attr);
				sb.append('=');

				String[] substrings = (String[]) value;

				for (String substr : substrings) {
					if (substr == null) /* * */ {
						sb.append('*');
					} else /* xxx */ {
						sb.append(encodeValue(substr));
					}
				}

				break;
			}
			case EQUAL : {
				sb.append(attr);
				sb.append('=');
				sb.append(encodeValue((String) value));

				break;
			}
			case GREATER : {
				sb.append(attr);
				sb.append(">="); //$NON-NLS-1$
				sb.append(encodeValue((String) value));

				break;
			}
			case LESS : {
				sb.append(attr);
				sb.append("<="); //$NON-NLS-1$
				sb.append(encodeValue((String) value));

				break;
			}
			case APPROX : {
				sb.append(attr);
				sb.append("~="); //$NON-NLS-1$
				sb.append(encodeValue(approxString((String) value)));

				break;
			}

			case PRESENT : {
				sb.append(attr);
				sb.append("=*"); //$NON-NLS-1$

				break;
			}
		}

		sb.append(')');

		return sb;
	}

	/**
	 * Compares this <code>Filter</code> object to another object.
	 *
	 * @param obj The object to compare against this <code>Filter</code>
	 *        object.
	 * @return If the other object is a <code>Filter</code> object, then
	 *         returns <code>this.toString().equals(obj.toString()</code>;
	 *         <code>false</code> otherwise.
	 */
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Filter)) {
			return false;
		}

		return this.toString().equals(obj.toString());
	}

	/**
		 * Returns the hashCode for this <code>Filter</code> object.
	 *
		 * @return The hashCode of the filter string; that is,
	 * <code>this.toString().hashCode()</code>.
	 */
	public int hashCode() {
		return this.toString().hashCode();
	}

	/* non public fields and methods for the Filter implementation */

	/** filter operation */
	private final int op;
	private static final int EQUAL = 1;
	private static final int APPROX = 2;
	private static final int GREATER = 3;
	private static final int LESS = 4;
	private static final int PRESENT = 5;
	private static final int SUBSTRING = 6;
	private static final int AND = 7;
	private static final int OR = 8;
	private static final int NOT = 9;

	/** filter attribute or null if operation AND, OR or NOT */
	private final String attr;
	/** filter operands */
	private final Object value;
	/** debug mode */
	private final boolean debug;

	/* normalized filter string for topLevel Filter object */
	private transient volatile String filterString;

	FilterImpl(int operation, String attr, Object value, boolean debug) {
		this.op = operation;
		this.attr = attr;
		this.value = value;
		this.debug = debug;
	}

	/**
	 * Encode the value string such that '(', '*', ')'
	 * and '\' are escaped.
	 *
	 * @param value unencoded value string.
	 * @return encoded value string.
	 */
	private static String encodeValue(String value) {
		boolean encoded = false;
		int inlen = value.length();
		int outlen = inlen << 1; /* inlen * 2 */

		char[] output = new char[outlen];
		value.getChars(0, inlen, output, inlen);

		int cursor = 0;
		for (int i = inlen; i < outlen; i++) {
			char c = output[i];

			switch (c) {
				case '(' :
				case '*' :
				case ')' :
				case '\\' : {
					output[cursor] = '\\';
					cursor++;
					encoded = true;

					break;
				}
			}

			output[cursor] = c;
			cursor++;
		}

		return encoded ? new String(output, 0, cursor) : value;
	}

	private boolean compare(int operation, Object value1, Object value2) {
		if (value1 == null) {
			if (debug) {
				Debug.println("compare(" + value1 + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}

			return false;
		}

		if (value1 instanceof String) {
			return compare_String(operation, (String) value1, value2);
		}

		if (value1 instanceof Version) {
			return compare_Version(operation, (Version) value1, value2);
		}

		Class<?> clazz = value1.getClass();
		if (clazz.isArray()) {
			Class<?> type = clazz.getComponentType();
			if (type.isPrimitive()) {
				return compare_PrimitiveArray(operation, type, value1, value2);
			}
			return compare_ObjectArray(operation, (Object[]) value1, value2);
		}
		if (value1 instanceof Collection<?>) {
			return compare_Collection(operation, (Collection<?>) value1, value2);
		}

		if (value1 instanceof Integer) {
			return compare_Integer(operation, ((Integer) value1).intValue(), value2);
		}

		if (value1 instanceof Long) {
			return compare_Long(operation, ((Long) value1).longValue(), value2);
		}

		if (value1 instanceof Byte) {
			return compare_Byte(operation, ((Byte) value1).byteValue(), value2);
		}

		if (value1 instanceof Short) {
			return compare_Short(operation, ((Short) value1).shortValue(), value2);
		}

		if (value1 instanceof Character) {
			return compare_Character(operation, ((Character) value1).charValue(), value2);
		}

		if (value1 instanceof Float) {
			return compare_Float(operation, ((Float) value1).floatValue(), value2);
		}

		if (value1 instanceof Double) {
			return compare_Double(operation, ((Double) value1).doubleValue(), value2);
		}

		if (value1 instanceof Boolean) {
			return compare_Boolean(operation, ((Boolean) value1).booleanValue(), value2);
		}
		if (value1 instanceof Comparable<?>) {
			@SuppressWarnings("unchecked")
			Comparable<Object> comparable = (Comparable<Object>) value1;
			return compare_Comparable(operation, comparable, value2);
		}

		return compare_Unknown(operation, value1, value2); // RFC 59
	}

	private boolean compare_Collection(int operation, Collection<?> collection, Object value2) {
		for (Object value1 : collection) {
			if (compare(operation, value1, value2)) {
				return true;
			}
		}

		return false;
	}

	private boolean compare_ObjectArray(int operation, Object[] array, Object value2) {
		for (Object value1 : array) {
			if (compare(operation, value1, value2)) {
				return true;
			}
		}

		return false;
	}

	private boolean compare_PrimitiveArray(int operation, Class<?> type, Object primarray, Object value2) {
		if (Integer.TYPE.isAssignableFrom(type)) {
			int[] array = (int[]) primarray;
			for (int value1 : array) {
				if (compare_Integer(operation, value1, value2)) {
					return true;
				}
			}

			return false;
		}

		if (Long.TYPE.isAssignableFrom(type)) {
			long[] array = (long[]) primarray;
			for (long value1 : array) {
				if (compare_Long(operation, value1, value2)) {
					return true;
				}
			}

			return false;
		}

		if (Byte.TYPE.isAssignableFrom(type)) {
			byte[] array = (byte[]) primarray;
			for (byte value1 : array) {
				if (compare_Byte(operation, value1, value2)) {
					return true;
				}
			}

			return false;
		}

		if (Short.TYPE.isAssignableFrom(type)) {
			short[] array = (short[]) primarray;
			for (short value1 : array) {
				if (compare_Short(operation, value1, value2)) {
					return true;
				}
			}

			return false;
		}

		if (Character.TYPE.isAssignableFrom(type)) {
			char[] array = (char[]) primarray;
			for (char value1 : array) {
				if (compare_Character(operation, value1, value2)) {
					return true;
				}
			}

			return false;
		}

		if (Float.TYPE.isAssignableFrom(type)) {
			float[] array = (float[]) primarray;
			for (float value1 : array) {
				if (compare_Float(operation, value1, value2)) {
					return true;
				}
			}

			return false;
		}

		if (Double.TYPE.isAssignableFrom(type)) {
			double[] array = (double[]) primarray;
			for (double value1 : array) {
				if (compare_Double(operation, value1, value2)) {
					return true;
				}
			}

			return false;
		}

		if (Boolean.TYPE.isAssignableFrom(type)) {
			boolean[] array = (boolean[]) primarray;
			for (boolean value1 : array) {
				if (compare_Boolean(operation, value1, value2)) {
					return true;
				}
			}

			return false;
		}

		return false;
	}

	private boolean compare_String(int operation, String string, Object value2) {
		switch (operation) {
			case SUBSTRING : {
				if (debug) {
					Debug.println("SUBSTRING(" + string + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}

				String[] substrings = (String[]) value2;
				int pos = 0;
				for (int i = 0, size = substrings.length; i < size; i++) {
					String substr = substrings[i];

					if (i + 1 < size) /* if this is not that last substr */ {
						if (substr == null) /* * */ {
							String substr2 = substrings[i + 1];

							if (substr2 == null) /* ** */
								continue; /* ignore first star */
							/* *xxx */
							if (debug) {
								Debug.println("indexOf(\"" + substr2 + "\"," + pos + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
							}
							int index = string.indexOf(substr2, pos);
							if (index == -1) {
								return false;
							}

							pos = index + substr2.length();
							if (i + 2 < size) // if there are more substrings, increment over the string we just matched; otherwise need to do the last substr check
								i++;
						} else /* xxx */ {
							int len = substr.length();

							if (debug) {
								Debug.println("regionMatches(" + pos + ",\"" + substr + "\")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
							}
							if (string.regionMatches(pos, substr, 0, len)) {
								pos += len;
							} else {
								return false;
							}
						}
					} else /* last substr */ {
						if (substr == null) /* * */ {
							return true;
						}
						/* xxx */
						if (debug) {
							Debug.println("regionMatches(" + pos + "," + substr + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						}
						return string.endsWith(substr);
					}
				}

				return true;
			}
			case EQUAL : {
				if (debug) {
					Debug.println("EQUAL(" + string + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return string.equals(value2);
			}
			case APPROX : {
				if (debug) {
					Debug.println("APPROX(" + string + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}

				string = approxString(string);
				String string2 = approxString((String) value2);

				return string.equalsIgnoreCase(string2);
			}
			case GREATER : {
				if (debug) {
					Debug.println("GREATER(" + string + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return string.compareTo((String) value2) >= 0;
			}
			case LESS : {
				if (debug) {
					Debug.println("LESS(" + string + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return string.compareTo((String) value2) <= 0;
			}
		}

		return false;
	}

	private boolean compare_Integer(int operation, int intval, Object value2) {
		if (operation == SUBSTRING) {
			if (debug) {
				Debug.println("SUBSTRING(" + intval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
			return false;
		}

		int intval2;
		try {
			intval2 = Integer.parseInt(((String) value2).trim());
		} catch (IllegalArgumentException e) {
			return false;
		}
		switch (operation) {
			case EQUAL : {
				if (debug) {
					Debug.println("EQUAL(" + intval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return intval == intval2;
			}
			case APPROX : {
				if (debug) {
					Debug.println("APPROX(" + intval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return intval == intval2;
			}
			case GREATER : {
				if (debug) {
					Debug.println("GREATER(" + intval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return intval >= intval2;
			}
			case LESS : {
				if (debug) {
					Debug.println("LESS(" + intval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return intval <= intval2;
			}
		}

		return false;
	}

	private boolean compare_Long(int operation, long longval, Object value2) {
		if (operation == SUBSTRING) {
			if (debug) {
				Debug.println("SUBSTRING(" + longval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
			return false;
		}

		long longval2;
		try {
			longval2 = Long.parseLong(((String) value2).trim());
		} catch (IllegalArgumentException e) {
			return false;
		}
		switch (operation) {
			case EQUAL : {
				if (debug) {
					Debug.println("EQUAL(" + longval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return longval == longval2;
			}
			case APPROX : {
				if (debug) {
					Debug.println("APPROX(" + longval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return longval == longval2;
			}
			case GREATER : {
				if (debug) {
					Debug.println("GREATER(" + longval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return longval >= longval2;
			}
			case LESS : {
				if (debug) {
					Debug.println("LESS(" + longval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return longval <= longval2;
			}
		}

		return false;
	}

	private boolean compare_Byte(int operation, byte byteval, Object value2) {
		if (operation == SUBSTRING) {
			if (debug) {
				Debug.println("SUBSTRING(" + byteval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
			return false;
		}

		byte byteval2;
		try {
			byteval2 = Byte.parseByte(((String) value2).trim());
		} catch (IllegalArgumentException e) {
			return false;
		}
		switch (operation) {
			case EQUAL : {
				if (debug) {
					Debug.println("EQUAL(" + byteval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return byteval == byteval2;
			}
			case APPROX : {
				if (debug) {
					Debug.println("APPROX(" + byteval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return byteval == byteval2;
			}
			case GREATER : {
				if (debug) {
					Debug.println("GREATER(" + byteval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return byteval >= byteval2;
			}
			case LESS : {
				if (debug) {
					Debug.println("LESS(" + byteval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return byteval <= byteval2;
			}
		}

		return false;
	}

	private boolean compare_Short(int operation, short shortval, Object value2) {
		if (operation == SUBSTRING) {
			if (debug) {
				Debug.println("SUBSTRING(" + shortval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
			return false;
		}

		short shortval2;
		try {
			shortval2 = Short.parseShort(((String) value2).trim());
		} catch (IllegalArgumentException e) {
			return false;
		}
		switch (operation) {
			case EQUAL : {
				if (debug) {
					Debug.println("EQUAL(" + shortval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return shortval == shortval2;
			}
			case APPROX : {
				if (debug) {
					Debug.println("APPROX(" + shortval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return shortval == shortval2;
			}
			case GREATER : {
				if (debug) {
					Debug.println("GREATER(" + shortval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return shortval >= shortval2;
			}
			case LESS : {
				if (debug) {
					Debug.println("LESS(" + shortval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return shortval <= shortval2;
			}
		}

		return false;
	}

	private boolean compare_Character(int operation, char charval, Object value2) {
		if (operation == SUBSTRING) {
			if (debug) {
				Debug.println("SUBSTRING(" + charval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
			return false;
		}

		char charval2;
		try {
			charval2 = ((String) value2).charAt(0);
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
		switch (operation) {
			case EQUAL : {
				if (debug) {
					Debug.println("EQUAL(" + charval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return charval == charval2;
			}
			case APPROX : {
				if (debug) {
					Debug.println("APPROX(" + charval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return (charval == charval2) || (Character.toUpperCase(charval) == Character.toUpperCase(charval2)) || (Character.toLowerCase(charval) == Character.toLowerCase(charval2));
			}
			case GREATER : {
				if (debug) {
					Debug.println("GREATER(" + charval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return charval >= charval2;
			}
			case LESS : {
				if (debug) {
					Debug.println("LESS(" + charval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return charval <= charval2;
			}
		}

		return false;
	}

	private boolean compare_Boolean(int operation, boolean boolval, Object value2) {
		if (operation == SUBSTRING) {
			if (debug) {
				Debug.println("SUBSTRING(" + boolval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
			return false;
		}

		boolean boolval2 = Boolean.valueOf(((String) value2).trim()).booleanValue();
		switch (operation) {
			case EQUAL : {
				if (debug) {
					Debug.println("EQUAL(" + boolval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return boolval == boolval2;
			}
			case APPROX : {
				if (debug) {
					Debug.println("APPROX(" + boolval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return boolval == boolval2;
			}
			case GREATER : {
				if (debug) {
					Debug.println("GREATER(" + boolval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return boolval == boolval2;
			}
			case LESS : {
				if (debug) {
					Debug.println("LESS(" + boolval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return boolval == boolval2;
			}
		}

		return false;
	}

	private boolean compare_Float(int operation, float floatval, Object value2) {
		if (operation == SUBSTRING) {
			if (debug) {
				Debug.println("SUBSTRING(" + floatval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
			return false;
		}

		float floatval2;
		try {
			floatval2 = Float.parseFloat(((String) value2).trim());
		} catch (IllegalArgumentException e) {
			return false;
		}
		switch (operation) {
			case EQUAL : {
				if (debug) {
					Debug.println("EQUAL(" + floatval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return Float.compare(floatval, floatval2) == 0;
			}
			case APPROX : {
				if (debug) {
					Debug.println("APPROX(" + floatval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return Float.compare(floatval, floatval2) == 0;
			}
			case GREATER : {
				if (debug) {
					Debug.println("GREATER(" + floatval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return Float.compare(floatval, floatval2) >= 0;
			}
			case LESS : {
				if (debug) {
					Debug.println("LESS(" + floatval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return Float.compare(floatval, floatval2) <= 0;
			}
		}

		return false;
	}

	private boolean compare_Double(int operation, double doubleval, Object value2) {
		if (operation == SUBSTRING) {
			if (debug) {
				Debug.println("SUBSTRING(" + doubleval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
			return false;
		}

		double doubleval2;
		try {
			doubleval2 = Double.parseDouble(((String) value2).trim());
		} catch (IllegalArgumentException e) {
			return false;
		}
		switch (operation) {
			case EQUAL : {
				if (debug) {
					Debug.println("EQUAL(" + doubleval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return Double.compare(doubleval, doubleval2) == 0;
			}
			case APPROX : {
				if (debug) {
					Debug.println("APPROX(" + doubleval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return Double.compare(doubleval, doubleval2) == 0;
			}
			case GREATER : {
				if (debug) {
					Debug.println("GREATER(" + doubleval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return Double.compare(doubleval, doubleval2) >= 0;
			}
			case LESS : {
				if (debug) {
					Debug.println("LESS(" + doubleval + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				return Double.compare(doubleval, doubleval2) <= 0;
			}
		}

		return false;
	}

	private static Object valueOf(Class<?> target, String value2) {
		do {
			Method method;
			try {
				method = target.getMethod("valueOf", String.class); //$NON-NLS-1$
			} catch (NoSuchMethodException e) {
				break;
			}
			if (Modifier.isStatic(method.getModifiers()) && target.isAssignableFrom(method.getReturnType())) {
				setAccessible(method);
				try {
					return method.invoke(null, value2.trim());
				} catch (IllegalAccessException e) {
					return null;
				} catch (InvocationTargetException e) {
					return null;
				}
			}
		} while (false);

		do {
			Constructor<?> constructor;
			try {
				constructor = target.getConstructor(String.class);
			} catch (NoSuchMethodException e) {
				break;
			}
			setAccessible(constructor);
			try {
				return constructor.newInstance(value2.trim());
			} catch (IllegalAccessException e) {
				return null;
			} catch (InvocationTargetException e) {
				return null;
			} catch (InstantiationException e) {
				return null;
			}
		} while (false);

		return null;
	}

	private static void setAccessible(AccessibleObject accessible) {
		if (!accessible.isAccessible()) {
			AccessController.doPrivileged(new SetAccessibleAction(accessible));
		}
	}

	private boolean compare_Version(int operation, Version value1, Object value2) {
		if (operation == SUBSTRING) {
			if (debug) {
				Debug.println("SUBSTRING(" + value1 + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
			return false;
		}
		try {
			Version version = Version.valueOf(((String) value2).trim());

			switch (operation) {
				case EQUAL : {
					if (debug) {
						Debug.println("EQUAL(" + value1 + "," + value + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					}
					return value1.equals(version);
				}
				case APPROX : {
					if (debug) {
						Debug.println("APPROX(" + value1 + "," + value + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					}
					return value1.equals(version);
				}
				case GREATER : {
					if (debug) {
						Debug.println("GREATER(" + value1 + "," + value + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					}
					return value1.compareTo(version) >= 0;
				}
				case LESS : {
					if (debug) {
						Debug.println("LESS(" + value1 + "," + value + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					}
					return value1.compareTo(version) <= 0;
				}
			}
		} catch (Exception e) {
			// if the valueOf or compareTo method throws an exception
			return false;
		}
		return false;
	}

	private boolean compare_Comparable(int operation, Comparable<Object> value1, Object value2) {
		if (operation == SUBSTRING) {
			if (debug) {
				Debug.println("SUBSTRING(" + value1 + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
			return false;
		}
		value2 = valueOf(value1.getClass(), (String) value2);
		if (value2 == null) {
			return false;
		}

		try {
			switch (operation) {
				case EQUAL : {
					if (debug) {
						Debug.println("EQUAL(" + value1 + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					}
					return value1.compareTo(value2) == 0;
				}
				case APPROX : {
					if (debug) {
						Debug.println("APPROX(" + value1 + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					}
					return value1.compareTo(value2) == 0;
				}
				case GREATER : {
					if (debug) {
						Debug.println("GREATER(" + value1 + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					}
					return value1.compareTo(value2) >= 0;
				}
				case LESS : {
					if (debug) {
						Debug.println("LESS(" + value1 + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					}
					return value1.compareTo(value2) <= 0;
				}
			}
		} catch (Exception e) {
			// if the compareTo method throws an exception; return false
			return false;
		}
		return false;
	}

	private boolean compare_Unknown(int operation, Object value1, Object value2) { //RFC 59
		if (operation == SUBSTRING) {
			if (debug) {
				Debug.println("SUBSTRING(" + value1 + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
			return false;
		}
		value2 = valueOf(value1.getClass(), (String) value2);
		if (value2 == null) {
			return false;
		}

		try {
			switch (operation) {
				case EQUAL : {
					if (debug) {
						Debug.println("EQUAL(" + value1 + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					}
					return value1.equals(value2);
				}
				case APPROX : {
					if (debug) {
						Debug.println("APPROX(" + value1 + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					}
					return value1.equals(value2);
				}
				case GREATER : {
					if (debug) {
						Debug.println("GREATER(" + value1 + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					}
					return value1.equals(value2);
				}
				case LESS : {
					if (debug) {
						Debug.println("LESS(" + value1 + "," + value2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					}
					return value1.equals(value2);
				}
			}
		} catch (Exception e) {
			// if the equals method throws an exception; return false
			return false;
		}

		return false;
	}

	/**
	 * Map a string for an APPROX (~=) comparison.
	 *
	 * This implementation removes white spaces.
	 * This is the minimum implementation allowed by
	 * the OSGi spec.
	 *
	 * @param input Input string.
	 * @return String ready for APPROX comparison.
	 */
	private static String approxString(String input) {
		boolean changed = false;
		char[] output = input.toCharArray();
		int cursor = 0;
		for (char c : output) {
			if (Character.isWhitespace(c)) {
				changed = true;
				continue;
			}

			output[cursor] = c;
			cursor++;
		}

		return changed ? new String(output, 0, cursor) : input;
	}

	/**
	 * Returns the leftmost required objectClass value for the filter to evaluate to true.
	 * 
	 * @return The leftmost required objectClass value or null if none could be determined.
	 */
	public String getRequiredObjectClass() {
		return getPrimaryKeyValue(Constants.OBJECTCLASS);
	}

	/**
	 * Returns the leftmost required primary key value for the filter to evaluate to true.
	 * This is useful for indexing candidates to match against this filter.
	 * @param primaryKey the primary key
	 * @return The leftmost required primary key value or null if none could be determined.
	 */
	public String getPrimaryKeyValue(String primaryKey) {
		// just checking for simple filters here where primaryKey is the only attr or it is one attr of a base '&' clause
		// (primaryKey=org.acme.BrickService) OK
		// (&(primaryKey=org.acme.BrickService)(|(vendor=IBM)(vendor=SUN))) OK
		// (primaryKey=org.acme.*) NOT OK
		// (|(primaryKey=org.acme.BrickService)(primaryKey=org.acme.CementService)) NOT OK
		// (&(primaryKey=org.acme.BrickService)(primaryKey=org.acme.CementService)) OK but only the first objectClass is returned
		switch (op) {
			case EQUAL :
				if (attr.equalsIgnoreCase(primaryKey) && (value instanceof String))
					return (String) value;
				break;
			case AND :
				FilterImpl[] clauses = (FilterImpl[]) value;
				for (FilterImpl clause : clauses)
					if (clause.op == EQUAL) {
						String result = clause.getPrimaryKeyValue(primaryKey);
						if (result != null)
							return result;
					}
				break;
		}
		return null;
	}

	public List<FilterImpl> getChildren() {
		if (value instanceof FilterImpl[]) {
			return new ArrayList<>(Arrays.asList((FilterImpl[]) value));
		}
		return Collections.emptyList();
	}

	/**
	 * Returns all the attributes contained within this filter
	 * @return all the attributes contained within this filter
	 */
	public String[] getAttributes() {
		List<String> results = new ArrayList<>();
		getAttributesInternal(results);
		return results.toArray(new String[results.size()]);
	}

	private void getAttributesInternal(List<String> results) {
		if (value instanceof FilterImpl[]) {
			FilterImpl[] children = (FilterImpl[]) value;
			for (FilterImpl child : children)
				child.getAttributesInternal(results);
			return;
		} else if (value instanceof FilterImpl) {
			// The NOT operation only has one child filter (bug 188075)
			FilterImpl child = ((FilterImpl) value);
			child.getAttributesInternal(results);
			return;
		}
		if (attr != null)
			results.add(attr);
	}

	/**
	 * Parser class for OSGi filter strings. This class parses
	 * the complete filter string and builds a tree of Filter
	 * objects rooted at the parent.
	 */
	private static class Parser {
		private final boolean debug;
		private final String filterstring;
		private final char[] filterChars;
		private int pos;

		Parser(String filterstring, boolean debug) {
			this.debug = debug;
			this.filterstring = filterstring;
			filterChars = filterstring.toCharArray();
			pos = 0;
		}

		FilterImpl parse() throws InvalidSyntaxException {
			FilterImpl filter;
			try {
				filter = parse_filter();
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new InvalidSyntaxException(Msg.FILTER_TERMINATED_ABRUBTLY, filterstring, e);
			}

			if (pos != filterChars.length) {
				throw new InvalidSyntaxException(NLS.bind(Msg.FILTER_TRAILING_CHARACTERS, filterstring.substring(pos)), filterstring);
			}
			return filter;
		}

		private FilterImpl parse_filter() throws InvalidSyntaxException {
			FilterImpl filter;
			skipWhiteSpace();

			if (filterChars[pos] != '(') {
				throw new InvalidSyntaxException(NLS.bind(Msg.FILTER_MISSING_LEFTPAREN, filterstring.substring(pos)), filterstring);
			}

			pos++;

			filter = parse_filtercomp();

			skipWhiteSpace();

			if (filterChars[pos] != ')') {
				throw new InvalidSyntaxException(NLS.bind(Msg.FILTER_MISSING_RIGHTPAREN, filterstring.substring(pos)), filterstring);
			}

			pos++;

			skipWhiteSpace();

			return filter;
		}

		private FilterImpl parse_filtercomp() throws InvalidSyntaxException {
			skipWhiteSpace();

			char c = filterChars[pos];

			switch (c) {
				case '&' : {
					pos++;
					return parse_and();
				}
				case '|' : {
					pos++;
					return parse_or();
				}
				case '!' : {
					pos++;
					return parse_not();
				}
			}
			return parse_item();
		}

		private FilterImpl parse_and() throws InvalidSyntaxException {
			int lookahead = pos;
			skipWhiteSpace();

			if (filterChars[pos] != '(') {
				pos = lookahead - 1;
				return parse_item();
			}

			List<FilterImpl> operands = new ArrayList<>(10);

			while (filterChars[pos] == '(') {
				FilterImpl child = parse_filter();
				operands.add(child);
			}

			return new FilterImpl(FilterImpl.AND, null, operands.toArray(new FilterImpl[operands.size()]), debug);
		}

		private FilterImpl parse_or() throws InvalidSyntaxException {
			int lookahead = pos;
			skipWhiteSpace();

			if (filterChars[pos] != '(') {
				pos = lookahead - 1;
				return parse_item();
			}

			List<FilterImpl> operands = new ArrayList<>(10);

			while (filterChars[pos] == '(') {
				FilterImpl child = parse_filter();
				operands.add(child);
			}

			return new FilterImpl(FilterImpl.OR, null, operands.toArray(new FilterImpl[operands.size()]), debug);
		}

		private FilterImpl parse_not() throws InvalidSyntaxException {
			int lookahead = pos;
			skipWhiteSpace();

			if (filterChars[pos] != '(') {
				pos = lookahead - 1;
				return parse_item();
			}

			FilterImpl child = parse_filter();

			return new FilterImpl(FilterImpl.NOT, null, child, debug);
		}

		private FilterImpl parse_item() throws InvalidSyntaxException {
			String attr = parse_attr();

			skipWhiteSpace();

			switch (filterChars[pos]) {
				case '~' : {
					if (filterChars[pos + 1] == '=') {
						pos += 2;
						return new FilterImpl(FilterImpl.APPROX, attr, parse_value(), debug);
					}
					break;
				}
				case '>' : {
					if (filterChars[pos + 1] == '=') {
						pos += 2;
						return new FilterImpl(FilterImpl.GREATER, attr, parse_value(), debug);
					}
					break;
				}
				case '<' : {
					if (filterChars[pos + 1] == '=') {
						pos += 2;
						return new FilterImpl(FilterImpl.LESS, attr, parse_value(), debug);
					}
					break;
				}
				case '=' : {
					if (filterChars[pos + 1] == '*') {
						int oldpos = pos;
						pos += 2;
						skipWhiteSpace();
						if (filterChars[pos] == ')') {
							return new FilterImpl(FilterImpl.PRESENT, attr, null, debug);
						}
						pos = oldpos;
					}

					pos++;
					Object string = parse_substring();

					if (string instanceof String) {
						return new FilterImpl(FilterImpl.EQUAL, attr, string, debug);
					}
					return new FilterImpl(FilterImpl.SUBSTRING, attr, string, debug);
				}
			}

			throw new InvalidSyntaxException(NLS.bind(Msg.FILTER_INVALID_OPERATOR, filterstring.substring(pos)), filterstring);
		}

		private String parse_attr() throws InvalidSyntaxException {
			skipWhiteSpace();

			int begin = pos;
			int end = pos;

			char c = filterChars[pos];

			while (c != '~' && c != '<' && c != '>' && c != '=' && c != '(' && c != ')') {
				pos++;

				if (!Character.isWhitespace(c)) {
					end = pos;
				}

				c = filterChars[pos];
			}

			int length = end - begin;

			if (length == 0) {
				throw new InvalidSyntaxException(NLS.bind(Msg.FILTER_MISSING_ATTR, filterstring.substring(pos)), filterstring);
			}

			return new String(filterChars, begin, length);
		}

		private String parse_value() throws InvalidSyntaxException {
			StringBuffer sb = new StringBuffer(filterChars.length - pos);

			parseloop: while (true) {
				char c = filterChars[pos];

				switch (c) {
					case ')' : {
						break parseloop;
					}

					case '(' : {
						throw new InvalidSyntaxException(NLS.bind(Msg.FILTER_INVALID_VALUE, filterstring.substring(pos)), filterstring);
					}

					case '\\' : {
						pos++;
						c = filterChars[pos];
						/* fall through into default */
					}

					default : {
						sb.append(c);
						pos++;
						break;
					}
				}
			}

			if (sb.length() == 0) {
				throw new InvalidSyntaxException(NLS.bind(Msg.FILTER_MISSING_VALUE, filterstring.substring(pos)), filterstring);
			}

			return sb.toString();
		}

		private Object parse_substring() throws InvalidSyntaxException {
			StringBuffer sb = new StringBuffer(filterChars.length - pos);

			List<String> operands = new ArrayList<>(10);

			parseloop: while (true) {
				char c = filterChars[pos];

				switch (c) {
					case ')' : {
						if (sb.length() > 0) {
							operands.add(sb.toString());
						}

						break parseloop;
					}

					case '(' : {
						throw new InvalidSyntaxException(NLS.bind(Msg.FILTER_INVALID_VALUE, filterstring.substring(pos)), filterstring);
					}

					case '*' : {
						if (sb.length() > 0) {
							operands.add(sb.toString());
						}

						sb.setLength(0);

						operands.add(null);
						pos++;

						break;
					}

					case '\\' : {
						pos++;
						c = filterChars[pos];
						/* fall through into default */
					}

					default : {
						sb.append(c);
						pos++;
						break;
					}
				}
			}

			int size = operands.size();

			if (size == 0) {
				return ""; //$NON-NLS-1$
			}

			if (size == 1) {
				Object single = operands.get(0);

				if (single != null) {
					return single;
				}
			}

			return operands.toArray(new String[size]);
		}

		private void skipWhiteSpace() {
			for (int length = filterChars.length; (pos < length) && Character.isWhitespace(filterChars[pos]);) {
				pos++;
			}
		}
	}

	/**
	 * This Map is used for key lookup from a ServiceReference during
	 * filter evaluation. This Map implementation only supports the get
	 * operation using a String key as no other operations are used by the
	 * Filter implementation.
	 * 
	 */
	static private final class ServiceReferenceMap extends AbstractMap<String, Object> implements Map<String, Object> {
		private final ServiceReference<?> reference;

		ServiceReferenceMap(ServiceReference<?> reference) {
			this.reference = reference;
		}

		@Override
		public Object get(Object key) {
			if (reference == null) {
				return null;
			}
			return reference.getProperty((String) key);
		}

		@Override
		public Set<Entry<String, Object>> entrySet() {
			throw new UnsupportedOperationException();
		}
	}

	private static class SetAccessibleAction implements PrivilegedAction<Void> {
		private final AccessibleObject accessible;

		SetAccessibleAction(AccessibleObject accessible) {
			this.accessible = accessible;
		}

		public Void run() {
			accessible.setAccessible(true);
			return null;
		}
	}

	static class Range {
		private char leftRule = 0;
		private Version leftVersion;
		private Version rightVersion;
		private char rightRule = 0;
		private Collection<Version> excludes = new ArrayList<>(0);

		public String toString() {
			if (rightVersion == null) {
				return leftVersion.toString();
			}
			return leftRule + leftVersion.toString() + ',' + rightVersion.toString() + rightRule;
		}

		void addExclude(Version exclude) {
			this.excludes.add(exclude);
			setLeft(leftRule, leftVersion);
			setRight(rightRule, rightVersion);
		}

		boolean setLeft(char leftRule, Version leftVersion) {
			if (this.leftVersion != null && this.leftVersion != leftVersion)
				return false;
			this.leftRule = excludes.contains(leftVersion) ? '(' : leftRule;
			this.leftVersion = leftVersion;
			return true;
		}

		boolean setRight(char rightRule, Version rightVersion) {
			if (this.rightVersion != null && this.rightVersion != rightVersion)
				return false;
			this.rightRule = excludes.contains(rightVersion) ? ')' : rightRule;
			this.rightVersion = rightVersion;
			return true;
		}
	}

	public Map<String, String> getStandardOSGiAttributes(String... versions) {
		if (op != AND && op != EQUAL && op != SUBSTRING && op != PRESENT)
			throw new IllegalArgumentException("Invalid filter for Starndard OSGi Attributes: " + op); //$NON-NLS-1$
		Map<String, String> result = new HashMap<>();
		Map<String, Range> versionAttrs = new HashMap<>();
		if (versions != null) {
			for (String versionAttr : versions) {
				versionAttrs.put(versionAttr, null);
			}
		}
		addAttributes(result, versionAttrs, false);
		for (Map.Entry<String, Range> entry : versionAttrs.entrySet()) {
			Range range = entry.getValue();
			if (range != null) {
				result.put(entry.getKey(), range.toString());
			}
		}

		return result;
	}

	private void addAttributes(Map<String, String> attributes, Map<String, Range> versionAttrs, boolean not) {
		if (op == EQUAL) {
			if (!versionAttrs.containsKey(attr)) {
				attributes.put(attr, (String) value);
			} else {
				// this is an exact range e.g. [value,value]
				Range currentRange = versionAttrs.get(attr);
				if (currentRange != null) {
					if (not) {
						// this is an expanded form of the filter, e.g.:
						// [1.0,2.0) -> (&(version>=1.0)(version<=2.0)(!(version=2.0)))
						currentRange.addExclude(new Version((String) value));
					} else {
						throw new IllegalStateException("Invalid range for: " + attr); //$NON-NLS-1$
					}
				} else {
					currentRange = new Range();
					Version version = new Version((String) value);
					currentRange.setLeft('[', version);
					currentRange.setRight(']', version);
					versionAttrs.put(attr, currentRange);
				}
			}
		} else if (op == SUBSTRING || op == PRESENT) {
			if (value == null) {
				attributes.put(attr, "*"); //$NON-NLS-1$
			} else {
				StringBuilder builder = new StringBuilder();
				for (String component : (String[]) value) {
					if (component == null) {
						builder.append('*');
					} else {
						builder.append(component);
					}
				}
				attributes.put(attr, builder.toString());
			}

		} else if (op == LESS) {
			if (!versionAttrs.containsKey(attr))
				throw new IllegalStateException("Invalid attribute: " + attr); //$NON-NLS-1$
			Range currentRange = versionAttrs.get(attr);
			if (currentRange == null) {
				currentRange = new Range();
				versionAttrs.put(attr, currentRange);
			}
			if (not) {
				// this must be a range start "(value"
				if (!currentRange.setLeft('(', new Version((String) value)))
					throw new IllegalStateException("range start is already processed for attribute: " + attr); //$NON-NLS-1$
			} else {
				// this must be a range end "value]"
				if (!currentRange.setRight(']', new Version((String) value)))
					throw new IllegalStateException("range end is already processed for attribute: " + attr); //$NON-NLS-1$
			}
		} else if (op == GREATER) {
			if (!versionAttrs.containsKey(attr))
				throw new IllegalStateException("Invalid attribute: " + attr); //$NON-NLS-1$
			Range currentRange = versionAttrs.get(attr);
			if (currentRange == null) {
				currentRange = new Range();
				versionAttrs.put(attr, currentRange);
			}
			if (not) {
				// this must be a range end "value)"
				if (!currentRange.setRight(')', new Version((String) value)))
					throw new IllegalStateException("range end is already processed for attribute: " + attr); //$NON-NLS-1$
			} else {
				// this must be a range start "[value"
				if (!currentRange.setLeft('[', new Version((String) value)))
					throw new IllegalStateException("range start is already processed for attribute: " + attr); //$NON-NLS-1$
			}
		} else if (op == AND) {
			for (FilterImpl component : (FilterImpl[]) value) {
				component.addAttributes(attributes, versionAttrs, false);
			}
		} else if (op == NOT) {
			((FilterImpl) value).addAttributes(attributes, versionAttrs, true);
		} else {
			throw new IllegalStateException("Invalid filter for standard OSGi requirements: " + op); //$NON-NLS-1$
		}
	}

	private static final WeakCacheHook _weakCacheHook;

	static {
		WeakCacheHook weakCacheHook = null;

		for (WeakCacheHook currentWeakCacheHook : ServiceLoader.load(WeakCacheHook.class)) {
			weakCacheHook = currentWeakCacheHook;
		}

		_weakCacheHook = weakCacheHook;
	}

}
/* @generated */
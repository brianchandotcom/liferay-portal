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

package com.liferay.portal.configuration.easyconf;

import com.germinus.easyconf.AggregatedProperties;
import com.germinus.easyconf.ClasspathUtil;
import com.germinus.easyconf.Conventions;
import com.germinus.easyconf.Filter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationConverter;
import org.apache.commons.configuration.DataConfiguration;
import org.apache.commons.configuration.MapConfiguration;
import org.apache.commons.configuration.beanutils.ConfigurationDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Shuyang Zhou
 */
public class ComponentProperties {

    public static final String NULL_STRING = null;

    private static final List EMPTY_LIST = Collections
            .unmodifiableList(new ArrayList());

    private static final Log log = LogFactory.getLog(ComponentProperties.class);

    AggregatedProperties properties;

    ComponentProperties(AggregatedProperties conf) {
        this.properties = conf;
        setThrowExceptionOnMissing(false);
    }

    // .................. Conversion methods ...................

    /**
     * Returns a decorator of the configuration that implements the Map
     * interface. Note that any changes made to this decorator will be made to
     * the original configuration and viceversa.
     *
     * @return a <code>java.util.Map</code> instance
     */
    public Map toMap() {
        return ConfigurationConverter.getMap(properties);
    }

    /**
     * Returns a decorator of the configuration that can be used as a DynaBean.
     * Note that any changes made to this decorator will be made to the original
     * configuration and viceversa.
     *
     * @return a <code>DynaBean</code> instance
     */
    public DynaBean toDynaBean() {
        return new ConfigurationDynaBean(properties);
    }

    /**
     * Returns a decorator of the configuration of type
     * <tt>org.apache.commons.configuration.Configuration</tt> Note that any
     * changes made to this decorator will be made to the original configuration
     * and viceversa.
     *
     * @return a <code>Configuration</code> instance
     */
    public Configuration toConfiguration() {
        return properties;
    }

    /**
     * Returns a decorator of the configuration of type
     * <tt>org.apache.commons.configuration.DataConfiguration</tt>. This
     * decorator has many extra methods for retrieving typed properties such as
     * color, URL, locale, Calendar and lists and array of any of these types.
     *
     * Note that any changes made to this decorator will be made to the original
     * configuration and viceversa.
     *
     * @return a <code>DataConfiguration</code> instance
     */
    public DataConfiguration toDataConfiguration() {
        return new DataConfiguration(properties);
    }

    /**
     * Returns a <b>copy </b> of the configuration into a
     * <tt>java.util.Properties</tt> class. Multivalued properties will be
     * converted to comma-separated strings. Any changes made to the
     * configuration will <b>not </b>be available to the Properties and
     * viceversa.
     *
     * @return a <code>Properties</code> instance
     */
    public java.util.Properties getProperties() {
        return ConfigurationConverter.getProperties(properties);
    }

    public Object getProperty(String key) {
        return properties.getProperty(key);
    }

    // ..................... Delegated methods ...............

    public boolean containsKey(String key) {
        return properties.containsKey(key);
    }

    public boolean equals(Object obj) {
        return properties.equals(obj);
    }

    public Iterator getKeys() {
        return properties.getKeys();
    }

    public Iterator getKeys(String prefix) {
        return properties.getKeys(prefix);
    }

    public int hashCode() {
        return properties.hashCode();
    }

    public boolean isEmpty() {
        return properties.isEmpty();
    }

    public void setProperty(String key, Object value) {
        properties.setProperty(key, value);
    }

    public Configuration subset(String prefix) {
        return properties.subset(prefix);
    }

    public String toString() {
        return properties.toString();
    }

    // ..................... BigDecimal ......................

    public BigDecimal getBigDecimal(String key) {
        return properties.getBigDecimal(key);
    }

    public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
        return properties.getBigDecimal(key, defaultValue);
    }

    public BigDecimal getBigDecimal(String key, Filter filter) {
        BigDecimal value = getBigDecimal(key, filter, null);
        validateValue(key, value);
        return value;
    }

    public BigDecimal getBigDecimal(String key, Filter filter,
                                    BigDecimal defaultValue) {
        return (BigDecimal) getPropertyWithFilter(key, filter,
                BigDecimal.class, defaultValue);
    }

    // ..................... BigInteger ......................

    public BigInteger getBigInteger(String key) {
        return properties.getBigInteger(key);
    }

    public BigInteger getBigInteger(String key, BigInteger defaultValue) {
        return properties.getBigInteger(key, defaultValue);
    }

    public BigInteger getBigInteger(String key, Filter filter) {
        BigInteger value = getBigInteger(key, filter, null);
        validateValue(key, value);
        return value;
    }

    public BigInteger getBigInteger(String key, Filter filter,
                                    BigInteger defaultValue) {
        return (BigInteger) getPropertyWithFilter(key, filter,
                BigInteger.class, defaultValue);
    }

    // ..................... Boolean ......................

    public boolean getBoolean(String key) {
        return properties.getBoolean(key);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return properties.getBoolean(key, defaultValue);
    }

    public Boolean getBoolean(String key, Boolean defaultValue)
            throws NoClassDefFoundError {
        return properties.getBoolean(key, defaultValue);
    }

    public boolean getBoolean(String key, Filter filter) {
        Boolean value = getBoolean(key, filter, null);
        validateValue(key, value);
        return value.booleanValue();
    }

    public Boolean getBoolean(String key, Filter filter, Boolean defaultValue)
            throws NoClassDefFoundError {
        return (Boolean) getPropertyWithFilter(key, filter, Boolean.class,
                defaultValue);
    }

    public boolean getBoolean(String key, Filter filter, boolean defaultValue) {
        return getBoolean(key, filter, new Boolean(defaultValue))
                .booleanValue();
    }

    // ..................... Byte ......................

    public byte getByte(String key) {
        return properties.getByte(key);
    }

    public byte getByte(String key, byte defaultValue) {
        return properties.getByte(key, defaultValue);
    }

    public Byte getByte(String key, Byte defaultValue) {
        return properties.getByte(key, defaultValue);
    }

    public byte getByte(String key, Filter filter) {
        Byte value = getByte(key, filter, null);
        validateValue(key, value);
        return value.byteValue();
    }

    public Byte getByte(String key, Filter filter, Byte defaultValue) {
        return (Byte) getPropertyWithFilter(key, filter, Byte.class,
                defaultValue);
    }

    public byte getByte(String key, Filter filter, byte defaultValue) {
        return getByte(key, filter, new Byte(defaultValue)).byteValue();
    }

    // ..................... Double ......................

    public double getDouble(String key) {
        return properties.getDouble(key);
    }

    public double getDouble(String key, double defaultValue) {
        return properties.getDouble(key, defaultValue);
    }

    public Double getDouble(String key, Double defaultValue) {
        return properties.getDouble(key, defaultValue);
    }

    public double getDouble(String key, Filter filter) {
        Double value = getDouble(key, filter, null);
        validateValue(key, value);
        return value.doubleValue();
    }

    public Double getDouble(String key, Filter filter, Double defaultValue) {
        return (Double) getPropertyWithFilter(key, filter, Double.class,
                defaultValue);
    }

    public double getDouble(String key, Filter filter, double defaultValue) {
        return getDouble(key, filter, new Double(defaultValue)).doubleValue();
    }

    // ..................... Class ......................

    /**
     * Get the <code>Class</code> representation of the class name specified
     * in the given property
     *
     * @throws ClassNotFoundException
     *             if the specified class is not found
     */
    public Class getClass(String key) throws ClassNotFoundException {
        String className = getString(key);
        return ClasspathUtil.locateClass(className);
    }

    /**
     * Get the <code>Class</code> representation of the class name specified
     * in the given property. Or the <code>defaultValue</code> if no value has
     * been given to the property.
     *
     * @throws ClassNotFoundException
     *             if a class has been configured but it is not found
     */
    public Class getClass(String key, Class defaultValue)
            throws ClassNotFoundException {
        String defaultStringValue = null;
        String className = getString(key, defaultStringValue);
        if (className == defaultStringValue) {
            return defaultValue;
        }
        return ClasspathUtil.locateClass(className);
    }

    /**
     * Similar to the previous methods but complementing the property key with
     * the given filter
     */
    public Class getClass(String key, Filter filter)
            throws ClassNotFoundException {
        String className = getString(key, filter);
        return ClasspathUtil.locateClass(className);
    }

    /**
     * Equivalent to the previous method but giving a default value which will
     * be used if no value has been specified in the configurations file
     */
    public Class getClass(String key, Filter filter, Class defaultValue)
            throws ClassNotFoundException {
        String defaultStringValue = null;
        String className = getString(key, filter, null);
        if (className == defaultStringValue) {
            return defaultValue;
        }
        return ClasspathUtil.locateClass(className);
    }

    // ..................... Class ......................

    /**
     * Get an array of <code>Class</code> objects for the class names
     * specified in the given property
     *
     * @throws ClassNotFoundException
     *             if the any of the configured classes is not found
     */
    public Class[] getClassArray(String key) throws ClassNotFoundException {
        String[] classNames = getStringArray(key);
        return ClasspathUtil.locateClasses(classNames);
    }

    /**
     * Get an array of <code>Class</code> objects for the class names
     * specified in the given property. Or the <code>defaultValue</code> if no
     * value has been given to the property.
     *
     * @throws ClassNotFoundException
     *             if the any of the configured classes is not found
     */
    public Class[] getClassArray(String key, Class[] defaultValue)
            throws ClassNotFoundException {
        String[] defaultStringArrayValue = null;
        String[] classNames = getStringArray(key, defaultStringArrayValue);
        if (classNames == defaultStringArrayValue) {
            return defaultValue;
        }
        return ClasspathUtil.locateClasses(classNames);
    }

    /**
     * Similar to the previous methods but complementing the property key with
     * the given filter
     */
    public Class[] getClassArray(String key, Filter filter)
            throws ClassNotFoundException {
        String[] classNames = getStringArray(key, filter);
        return ClasspathUtil.locateClasses(classNames);
    }

    /**
     * Equivalent to the previous method but giving a default value which will
     * be used if no value has been specified in the configurations file
     */
    public Class[] getClassArray(String key, Filter filter, Class[] defaultValue)
            throws ClassNotFoundException {
        String[] defaultStringArrayValue = null;
        String[] classNames = getStringArray(key, filter,
                defaultStringArrayValue);
        if (classNames == defaultStringArrayValue) {
            return defaultValue;
        }
        return ClasspathUtil.locateClasses(classNames);
    }

    // ..................... Float ......................

    public float getFloat(String key) {
        return properties.getFloat(key);
    }

    public float getFloat(String key, float defaultValue) {
        return properties.getFloat(key, defaultValue);
    }

    public Float getFloat(String key, Float defaultValue) {
        return properties.getFloat(key, defaultValue);
    }

    public float getFloat(String key, Filter filter) {
        Float value = getFloat(key, filter, null);
        validateValue(key, value);
        return value.floatValue();
    }

    public Float getFloat(String key, Filter filter, Float defaultValue) {
        return (Float) getPropertyWithFilter(key, filter, Float.class,
                defaultValue);
    }

    public float getFloat(String key, Filter filter, float defaultValue) {
        return getFloat(key, filter, new Float(defaultValue)).floatValue();
    }

    // ..................... Integer ......................

    public int getInt(String key) {
        return properties.getInt(key);
    }

    public int getInt(String key, int defaultValue) {
        return properties.getInt(key, defaultValue);
    }

    public Integer getInteger(String key, Integer defaultValue) {
        return properties.getInteger(key, defaultValue);
    }

    public int getInt(String key, Filter filter) {
        Integer value = getInteger(key, filter, null);
        validateValue(key, value);
        return value.intValue();
    }

    public Integer getInteger(String key, Filter filter, Integer defaultValue) {
        return (Integer) getPropertyWithFilter(key, filter, Integer.class,
                defaultValue);
    }

    public int getInt(String key, Filter filter, int defaultValue) {
        return getInteger(key, filter, new Integer(defaultValue)).intValue();
    }

    // ..................... List ......................

    public List getList(String key) {
        return properties.getList(key);
    }

    public List getList(String key, List defaultValue) {
        return properties.getList(key, defaultValue);
    }

    public List getList(String key, Filter filter) {
        List value = (List) getPropertyWithFilter(key, filter, List.class, null);
        validateValue(key, value);
        if (value == null) {
            value = EMPTY_LIST;
        }
        return value;
    }

    public List getList(String key, Filter filter, List defaultValue) {
        return (List) getPropertyWithFilter(key, filter, List.class,
                defaultValue);
    }

    // ..................... Long ......................

    public long getLong(String key) {
        return properties.getLong(key);
    }

    public Long getLong(String key, Long defaultValue) {
        return properties.getLong(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return properties.getLong(key, defaultValue);
    }

    public long getLong(String key, Filter filter) {
        Long value = getLong(key, filter, null);
        validateValue(key, value);
        return value.longValue();
    }

    public Long getLong(String key, Filter filter, Long defaultValue) {
        return (Long) getPropertyWithFilter(key, filter, Long.class,
                defaultValue);
    }

    public long getLong(String key, Filter filter, long defaultValue) {
        return getLong(key, filter, new Long(defaultValue)).longValue();
    }

    // ..................... Short ......................

    public short getShort(String key) {
        return properties.getShort(key);
    }

    public Short getShort(String key, Short defaultValue) {
        return properties.getShort(key, defaultValue);
    }

    public short getShort(String key, short defaultValue) {
        return properties.getShort(key, defaultValue);
    }

    public short getShort(String key, Filter filter) {
        Short value = getShort(key, filter, null);
        validateValue(key, value);
        return value.shortValue();
    }

    public Short getShort(String key, Filter filter, Short defaultValue) {
        return (Short) getPropertyWithFilter(key, filter, Short.class,
                defaultValue);
    }

    public short getShort(String key, Filter filter, short defaultValue) {
        return getShort(key, filter, new Short(defaultValue)).shortValue();
    }

    // ..................... String ......................

    /**
     * Get the String value of the given key. If it contains a list of values,
     * they will be serialized to a comma-separated format (use getList or
     * getStringArray if you want separated list items)
     */
    public String getString(String key) {
        Object value = properties.getProperty(key);
        String result;
        if (value instanceof List) {
            result = listToString((List) value);
        } else {
            result = properties.getString(key);
        }
        return result;
        //		return properties.getString(key);
    }

    public String getString(String key, String defaultValue) {
        return properties.getString(key, defaultValue);
    }

    public String getString(String key, Filter filter) {
        String value = getString(key, filter, null);
        validateValue(key, value);
        return value;
    }

    public String getString(String key, Filter filter, String defaultValue) {
        return (String) getPropertyWithFilter(key, filter, String.class,
                defaultValue);
    }

    // ..................... StringArray ......................

    public String[] getStringArray(String key) {
        return properties.getStringArray(key);
    }

    public String[] getStringArray(String key, Filter filter) {
        List value = getList(key, filter);
        return (String[]) value.toArray(new String[0]);
    }

    public String[] getStringArray(String key, Filter filter,
                                   String[] defaultValue) {
        List defaultList = null;
        if (defaultValue != null) {
            defaultList = Arrays.asList(defaultValue);
        }
        List value = getList(key, filter, defaultList);
        if (value == null) { //This should never happen
            return null;
        } else {
            return (String[]) value.toArray(new String[0]);
        }
    }

    public String[] getStringArray(String key, String[] defaultValue) {
        List defaultList = null;
        if (defaultValue != null) {
            defaultList = Arrays.asList(defaultValue);
        }
        List value = getList(key, defaultList);
        if (value == null) {
            return null;
        } else {
            return (String[]) value.toArray(new String[0]);
        }
    }

    // ................. Control methods .....................

    public boolean hasBaseConfiguration() {
        return properties.hasBaseConfiguration();
    }

    /**
     * Get a list of the sources which have been loaded for this component
     */
    public List getLoadedSources() {
        return properties.loadedSources();
    }

    /**
     * Set the flag throwExceptionOnMissing. See the class documentation for
     * more details.
     */
    public void setThrowExceptionOnMissing(boolean throwExceptionOnMissing) {
        properties.setThrowExceptionOnMissing(throwExceptionOnMissing);
    }

    public boolean isThrowExceptionOnMissing() {
        return properties.isThrowExceptionOnMissing();
    }

    /**
     * Returned the configured delay period for this component or null if
     * reloading is not being performed
     */
    public Long getDelayPeriod() {
        Long nullLong = null;
        return getLong(Conventions.RELOAD_DELAY_PROPERTY, nullLong);
    }

    public String getComponentName() {
        return properties.getComponentName();
    }

    // ............. Helper methods ......................

    protected Object getPropertyWithFilter(String key, Filter filter,
                                           Class theClass, Object defaultValue) {
        CompositeConfiguration filteredConf = properties;
        Object value = null;
        for (int i = filter.numOfSelectors(); (i >= 0) && (value == null); i--) {
            MapConfiguration varsConf = null;
            if (filter.hasVariables()) {
                varsConf = new MapConfiguration(filter.getVariables());
                filteredConf = new CompositeConfiguration();
                filteredConf.addConfiguration(varsConf);
                filteredConf.addConfiguration(properties);
            }
            value = getTypedPropertyWithDefault(
                    key + filter.getFilterSuffix(i), theClass, filteredConf,
                    null);
            if (varsConf != null) {
                properties.removeConfiguration(varsConf);
            }
            log.debug("Value for " + key + filter.getFilterSuffix(i) + "="
                    + value);
        }
		if (value == null) {
			value = defaultValue;
		}
        return value;
    }

    protected static Object getTypedPropertyWithDefault(
                                                        String key,
                                                        Class theClass,
                                                        Configuration properties,
                                                        Object defaultValue) {
        if (theClass.equals(Float.class)) {
            return properties.getFloat(key, (Float) defaultValue);

        } else if (theClass.equals(Integer.class)) {
            return properties.getInteger(key, (Integer) defaultValue);

        } else if (theClass.equals(String.class)) {
            return properties.getString(key, (String) defaultValue);

        } else if (theClass.equals(Double.class)) {
            return properties.getDouble(key, (Double) defaultValue);

        } else if (theClass.equals(Long.class)) {
            return properties.getLong(key, (Long) defaultValue);

        } else if (theClass.equals(Boolean.class)) {
            return properties.getBoolean(key, (Boolean) defaultValue);

        } else if (theClass.equals(List.class)) {
            return properties.getList(key, (List) defaultValue);

        } else if (theClass.equals(BigInteger.class)) {
            return properties.getBigInteger(key, (BigInteger) defaultValue);

        } else if (theClass.equals(BigDecimal.class)) {
            return properties.getBigDecimal(key, (BigDecimal) defaultValue);

        } else if (theClass.equals(Byte.class)) {
            return properties.getByte(key, (Byte) defaultValue);

        } else if (theClass.equals(Short.class)) {
            return properties.getShort(key, (Short) defaultValue);
        }
        throw new IllegalArgumentException("Class " + theClass + " is not"
                + "supported for properties");
    }

    protected void validateValue(String key, Object value) {
        if ((value == null) && isThrowExceptionOnMissing()) {
            throw new NoSuchElementException("Property with key=" + key
                    + " was not found");
        }
    }

    protected String listToString(List list) {
        StringBuffer property = new StringBuffer();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            property.append(String.valueOf(it.next()));
            if (it.hasNext()) {
                property.append(",");
            }
        }
        return property.toString();
    }
}
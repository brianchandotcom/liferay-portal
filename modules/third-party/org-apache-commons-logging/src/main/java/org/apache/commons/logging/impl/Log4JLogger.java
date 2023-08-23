/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.logging.impl;

import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

/**
 * Implementation of {@link Log} that maps directly to a
 * <strong>Logger</strong> for log4J version 2.17.
 *
 * @version $Id: Log4JLogger.java 1448119 2013-02-20 12:28:04Z tn $
 */
public class Log4JLogger implements Log, Serializable {

    /** Serializable version identifier. */
    private static final long serialVersionUID = 5160705895411730424L;

    // ------------------------------------------------------------- Attributes

    /** The fully qualified name of the Log4JLogger class. */
    private static final String FQCN = Log4JLogger.class.getName();

    /** Log to this logger */
    private transient volatile Logger logger;

    /** Logger name */
    private final String name;

    // ------------------------------------------------------------ Constructor

    public Log4JLogger() {
        name = null;
    }

    /**
     * Base constructor.
     */
    public Log4JLogger(String name) {
        this.name = name;
        this.logger = getLogger();
    }

    /**
     * For use with a log4j factory.
     */
    public Log4JLogger(Logger logger) {
        if (logger == null) {
            throw new IllegalArgumentException(
                "Warning - null logger in constructor; possible log4j misconfiguration.");
        }
        this.name = logger.getName();
        this.logger = logger;
    }

    /**
     * Logs a message with <code>org.apache.logging.log4j.Level.TRACE</code>.
     *
     * @param message to log
     * @see org.apache.commons.logging.Log#trace(Object)
     */
    public void trace(Object message) {
		getLogger().logIfEnabled(FQCN, Level.TRACE, null, message, null);
    }

    /**
     * Logs a message with <code>org.apache.logging.log4j.Level.TRACE</code>.
     *
     * @param message to log
     * @param t log this cause
     * @see org.apache.commons.logging.Log#trace(Object, Throwable)
     */
    public void trace(Object message, Throwable t) {
		getLogger().logIfEnabled(FQCN, Level.TRACE, null, message, t);
    }

    /**
     * Logs a message with <code>org.apache.logging.log4j.Level.DEBUG</code>.
     *
     * @param message to log
     * @see org.apache.commons.logging.Log#debug(Object)
     */
    public void debug(Object message) {
		getLogger().logIfEnabled(FQCN, Level.DEBUG, null, message, null);
    }

    /**
     * Logs a message with <code>org.apache.logging.log4j.Level.DEBUG</code>.
     *
     * @param message to log
     * @param t log this cause
     * @see org.apache.commons.logging.Log#debug(Object, Throwable)
     */
    public void debug(Object message, Throwable t) {
		getLogger().logIfEnabled(FQCN, Level.DEBUG, null, message, t);
    }

    /**
     * Logs a message with <code>org.apache.logging.log4j.Level.INFO</code>.
     *
     * @param message to log
     * @see org.apache.commons.logging.Log#info(Object)
     */
    public void info(Object message) {
		getLogger().logIfEnabled(FQCN, Level.INFO, null, message, null);
    }

    /**
     * Logs a message with <code>org.apache.logging.log4j.Level.INFO</code>.
     *
     * @param message to log
     * @param t log this cause
     * @see org.apache.commons.logging.Log#info(Object, Throwable)
     */
    public void info(Object message, Throwable t) {
		getLogger().logIfEnabled(FQCN, Level.INFO, null, message, t);
    }

    /**
     * Logs a message with <code>org.apache.logging.log4j.Level.WARN</code>.
     *
     * @param message to log
     * @see org.apache.commons.logging.Log#warn(Object)
     */
    public void warn(Object message) {
		getLogger().logIfEnabled(FQCN, Level.WARN, null, message, null);
    }

    /**
     * Logs a message with <code>org.apache.logging.log4j.Level.WARN</code>.
     *
     * @param message to log
     * @param t log this cause
     * @see org.apache.commons.logging.Log#warn(Object, Throwable)
     */
    public void warn(Object message, Throwable t) {
		getLogger().logIfEnabled(FQCN, Level.WARN, null, message, t);
    }

    /**
     * Logs a message with <code>org.apache.logging.log4j.Level.ERROR</code>.
     *
     * @param message to log
     * @see org.apache.commons.logging.Log#error(Object)
     */
    public void error(Object message) {
		getLogger().logIfEnabled(FQCN, Level.ERROR, null, message, null);
    }

    /**
     * Logs a message with <code>org.apache.logging.log4j.Level.ERROR</code>.
     *
     * @param message to log
     * @param t log this cause
     * @see org.apache.commons.logging.Log#error(Object, Throwable)
     */
    public void error(Object message, Throwable t) {
		getLogger().logIfEnabled(FQCN, Level.ERROR, null, message, t);
    }

    /**
     * Logs a message with <code>org.apache.logging.log4j.Level.FATAL</code>.
     *
     * @param message to log
     * @see org.apache.commons.logging.Log#fatal(Object)
     */
    public void fatal(Object message) {
		getLogger().logIfEnabled(FQCN, Level.FATAL, null, message, null);
    }

    /**
     * Logs a message with <code>org.apache.logging.log4j.Level.FATAL</code>.
     *
     * @param message to log
     * @param t log this cause
     * @see org.apache.commons.logging.Log#fatal(Object, Throwable)
     */
    public void fatal(Object message, Throwable t) {
		getLogger().logIfEnabled(FQCN, Level.FATAL, null, message, t);
    }

    /**
     * Return the native Logger instance we are using.
     */
    public Logger getLogger() {
        Logger result = logger;
        if (result == null) {
            synchronized(this) {
                result = logger;
                if (result == null) {
                    logger = result =
                    	(org.apache.logging.log4j.core.Logger)
                    		LogManager.getLogger(name);
                }
            }
        }
        return result;
    }

    /**
     * Check whether the Log4j Logger used is enabled for <code>DEBUG</code> priority.
     */
    public boolean isDebugEnabled() {
        return getLogger().isDebugEnabled();
    }

    /**
     * Check whether the Log4j Logger used is enabled for <code>ERROR</code> priority.
     */
    public boolean isErrorEnabled() {
        return getLogger().isErrorEnabled();
    }

    /**
     * Check whether the Log4j Logger used is enabled for <code>FATAL</code> priority.
     */
    public boolean isFatalEnabled() {
        return getLogger().isFatalEnabled();
    }

    /**
     * Check whether the Log4j Logger used is enabled for <code>INFO</code> priority.
     */
    public boolean isInfoEnabled() {
        return getLogger().isInfoEnabled();
    }

    /**
     * Check whether the Log4j Logger used is enabled for <code>TRACE</code> priority.
     */
    public boolean isTraceEnabled() {
        return getLogger().isTraceEnabled();
    }

    /**
     * Check whether the Log4j Logger used is enabled for <code>WARN</code> priority.
     */
    public boolean isWarnEnabled() {
        return getLogger().isWarnEnabled();
    }
}
/* @generated */
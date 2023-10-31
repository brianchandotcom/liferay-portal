package org.apache.velocity.runtime.log;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.spi.AbstractLogger;

import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.log.internal.Log4jUtil;
import org.apache.velocity.util.ExceptionUtils;

/**
 * Implementation of a simple log4j system that will either latch onto
 * an existing category, or just do a simple rolling file log.
 *
 * @author <a href="mailto:geirm@apache.org>Geir Magnusson Jr.</a>
 * @author <a href="mailto:dlr@finemaltcoding.com>Daniel L. Rall</a>
 * @author <a href="mailto:nbubna@apache.org>Nathan Bubna</a>
 * @version $Id: Log4JLogChute.java 718424 2008-11-17 22:50:43Z nbubna $
 * @since Velocity 1.5
 * @since 1.5
 */
public class Log4JLogChute implements LogChute
{
    public static final String RUNTIME_LOG_LOG4J_LOGGER =
            "runtime.log.logsystem.log4j.logger";
    public static final String RUNTIME_LOG_LOG4J_LOGGER_LEVEL =
            "runtime.log.logsystem.log4j.logger.level";

    private RuntimeServices rsvc = null;
    private RollingFileAppender appender = null;

    /**
     * <a href="http://jakarta.apache.org/log4j/">Log4J</a> logging API.
     */
    protected Logger logger = null;

    /**
     * @see org.apache.velocity.runtime.log.LogChute#init(org.apache.velocity.runtime.RuntimeServices)
     */
    public void init(RuntimeServices rs) throws Exception
    {
        rsvc = rs;

        /* first see if there is a category specified and just use that - it allows
         * the application to make us use an existing logger
         */
        String name = (String)rsvc.getProperty(RUNTIME_LOG_LOG4J_LOGGER);
        if (name != null)
        {
			logger = (Logger)LogManager.getLogger(name);

			log(DEBUG_ID, "Log4JLogChute using logger '" + name + '\'');
        }
        else
        {
            // create a logger with this class name to avoid conflicts
			logger = (Logger)LogManager.getLogger(this.getClass().getName());

            // if we have a file property, then create a separate
            // rolling file log for velocity messages only
            String file = rsvc.getString(RuntimeConstants.RUNTIME_LOG);
            if (file != null && file.length() > 0)
            {
				appender = Log4jUtil.createRollingFileAppender(file);

				logger.addAppender(appender);

				// don't inherit appenders from higher in the logger heirarchy

				LoggerConfig loggerConfig = logger.get();

				loggerConfig.setAdditive(false);

				log(
					DEBUG_ID,
					"Log4JLogChute initialized using file '" + file + '\'');
            }
        }

        /* get and set specified level for this logger */
        String lvl = rsvc.getString(RUNTIME_LOG_LOG4J_LOGGER_LEVEL);
        if (lvl != null) {
			Log4jUtil.setLevel(logger.getName(), Level.toLevel(lvl));
        }
    }

    /**
     *  logs messages
     *
     *  @param level severity level
     *  @param message complete error message
     */
    public void log(int level, String message)
    {
        switch (level)
        {
			case LogChute.WARN_ID:
				logger.logIfEnabled(
					_FQCN, Level.WARN, null, (Object)message, null);
				break;
			case LogChute.INFO_ID:
				logger.logIfEnabled(
					_FQCN, Level.INFO, null, (Object)message, null);
				break;
			case LogChute.TRACE_ID:
				logger.logIfEnabled(
					_FQCN, Level.TRACE, null, (Object)message, null);
				break;
			case LogChute.ERROR_ID:
				logger.logIfEnabled(
					_FQCN, Level.ERROR, null, (Object)message, null);
				break;
			case LogChute.DEBUG_ID:
			default:
				logger.logIfEnabled(
					_FQCN, Level.DEBUG, null, (Object)message, null);
                break;
        }
    }

    /**
     * @see org.apache.velocity.runtime.log.LogChute#log(int, java.lang.String, java.lang.Throwable)
     */
    public void log(int level, String message, Throwable t)
    {
        switch (level)
        {
			case LogChute.WARN_ID:
				logger.logIfEnabled(
					_FQCN, Level.WARN, null, (Object)message, t);
				break;
			case LogChute.INFO_ID:
				logger.logIfEnabled(
					_FQCN, Level.INFO, null, (Object)message, t);
				break;
			case LogChute.TRACE_ID:
				logger.logIfEnabled(
					_FQCN, Level.TRACE, null, (Object)message, t);
				break;
			case LogChute.ERROR_ID:
				logger.logIfEnabled(
					_FQCN, Level.ERROR, null, (Object)message, t);
				break;
			case LogChute.DEBUG_ID:
			default:
				logger.logIfEnabled(
					_FQCN, Level.DEBUG, null, (Object)message, t);
                break;
        }
    }

    /**
     * @see org.apache.velocity.runtime.log.LogChute#isLevelEnabled(int)
     */
    public boolean isLevelEnabled(int level)
    {
        switch (level)
        {
            case LogChute.DEBUG_ID:
                return logger.isDebugEnabled();
            case LogChute.INFO_ID:
                return logger.isInfoEnabled();
            case LogChute.TRACE_ID:
				return logger.isTraceEnabled();
            case LogChute.WARN_ID:
				return logger.isWarnEnabled();
            case LogChute.ERROR_ID:
				return logger.isErrorEnabled();
            default:
                return true;
        }
    }

    /**
     * Also do a shutdown if the object is destroy()'d.
     * @throws Throwable
     */
    protected void finalize() throws Throwable
    {
        shutdown();
    }

    /** Close all destinations*/
    public void shutdown()
    {
        if (appender != null)
        {
			logger.removeAppender(appender);

			appender.stop();

			appender = null;
        }
    }

	private static final String _FQCN = AbstractLogger.class.getName();

}
/* @generated */
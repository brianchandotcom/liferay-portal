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

/**
 * <p><em>This class is deprecated in favor of the new {@link Log4JLogChute},
 * which makes use of Log4J's <code>Logger</code> rather than its deprecated
 * <code>Category</code> class.</em></p>
 *
 *  Implementation of a simple log4j system that will either
 *  latch onto an existing category, or just do a simple
 *  rolling file log.  Derived from Jon's 'complicated'
 *  version :)
 *
 * @author <a href="mailto:geirm@apache.org>Geir Magnusson Jr.</a>
 * @version $Id: SimpleLog4JLogSystem.java 718424 2008-11-17 22:50:43Z nbubna $
 * @deprecated Use Log4JLogChute instead.
 */
public class SimpleLog4JLogSystem implements LogSystem
{
    private RuntimeServices rsvc = null;
    private RollingFileAppender appender = null;

    /** log4java logging interface */
    protected Logger logger = null;

    /**
     *
     */
    public SimpleLog4JLogSystem()
    {
    }

    /**
     * @see org.apache.velocity.runtime.log.LogSystem#init(org.apache.velocity.runtime.RuntimeServices)
     */
    public void init( RuntimeServices rs )
    {
        rsvc = rs;

        /*
         *  first see if there is a category specified and just use that - it allows
         *  the application to make us use an existing logger
         */

        String categoryname =  (String) rsvc.getProperty("runtime.log.logsystem.log4j.category");

        if ( categoryname != null )
        {
			logger = (Logger)LogManager.getLogger(categoryname);

            logVelocityMessage( 0,
                                "SimpleLog4JLogSystem using category '" + categoryname + "'");

            return;
        }

        /*
         *  if not, use the file...
         */

        String logfile = rsvc.getString( RuntimeConstants.RUNTIME_LOG );

        /*
         *  now init.  If we can't, panic!
         */
        try
        {
            internalInit( logfile );

            logVelocityMessage( 0,
                "SimpleLog4JLogSystem initialized using logfile '" + logfile + "'" );
        }
        catch( Exception e )
        {
            System.err.println(
                "PANIC : error configuring SimpleLog4JLogSystem : " + e );
        }
    }

    /**
     *  initializes the log system using the logfile argument
     */
    private void internalInit( String logfile )
        throws Exception
    {
        /*
         *  do it by our classname to avoid conflicting with anything else
         *  that might be used...
         */

    	String loggerName = this.getClass().getName();

		logger = (Logger)LogManager.getLogger(loggerName);

		/*
		 * Priority is set for DEBUG becouse this implementation checks
		 * log level.
		 */

		Log4jUtil.setLevel(loggerName, Level.DEBUG);

		LoggerConfig loggerConfig = logger.get();

		loggerConfig.setAdditive(false);

		appender = Log4jUtil.createRollingFileAppender(logfile);

		logger.addAppender(appender);
    }

    /**
     *  logs messages
     *
     *  @param level severity level
     *  @param message complete error message
     */
    public void logVelocityMessage(int level, String message)
    {
        switch (level)
        {
            case LogSystem.WARN_ID:
				logger.logIfEnabled(
					_FQCN, Level.WARN, null, (Object)message, null);
				break;
            case LogSystem.INFO_ID:
				logger.logIfEnabled(
					_FQCN, Level.INFO, null, (Object)message, null);
				break;
            case LogSystem.ERROR_ID:
				logger.logIfEnabled(
					_FQCN, Level.ERROR, null, (Object)message, null);
				break;
            case LogSystem.DEBUG_ID:
            default:
				logger.logIfEnabled(
					_FQCN, Level.DEBUG, null, (Object)message, null);
				break;
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
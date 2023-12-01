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
package org.apache.felix.scr.impl.logger;

import org.apache.felix.scr.impl.manager.ScrConfiguration;
import org.osgi.framework.BundleContext;

/**
 * This abstract class adds support for using a LogService
 * (or LoggerFactory for R7+).
 */
public abstract class LogServiceEnabledLogger extends AbstractLogger
{
    private static volatile LogServiceSupport _logServiceSupport;

	public static void setLogService(Object logService) {
		_logServiceSupport = new LogServiceSupport(null, logService);
	}


    public LogServiceEnabledLogger(final ScrConfiguration config, final BundleContext bundleContext)
    {
        super(config, getBundleIdentifier(bundleContext.getBundle()));
    }

    /**
     * Close the logger
     */
    public void close()
    {
    }

    @Override
    InternalLogger getLogger()
    {
		LogServiceSupport logServiceSupport = _logServiceSupport;

		if (logServiceSupport == null) {
			return getDefaultLogger();
		}

		return logServiceSupport.getLogger();
    }

    abstract InternalLogger getDefaultLogger();
}
/* @generated */
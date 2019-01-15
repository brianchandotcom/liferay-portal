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

package com.liferay.arquillian.extension.junit.bridge.protocol.jmx;

import java.util.Map;
import javax.management.NotificationBroadcaster;
import org.jboss.arquillian.container.test.spi.command.Command;

/**
 *
 * @author Matthew Tambara
 */
public interface JMXTestRunnerMBean extends NotificationBroadcaster {

    /** The ObjectName for this service */
    String OBJECT_NAME = "jboss.arquillian:service=jmx-test-runner";

    /**
     * Runs a test method on the given test class
     *
     * @param className the test class name
     * @param methodName the test method name
     * @return a serialized {@link TestResult}
     * @deprecated
     */
    @Deprecated
    public byte[] runTestMethod(String className, String methodName);

    /**
     * Runs a test method on the given test class
     *
     * @param className the test class name
     * @param methodName the test method name
     * @param protocol configuration properties
     * @return a serialized {@link TestResult}
     */
    public byte[] runTestMethod(String className, String methodName, Map<String, String> protocolProps);
    
    /**
     * Broadcast {@link Command} commands to any listeners
     * 
     * @param command Command object containing the request
     */
    void send(Command<?> command);

    /**
     * Receive {@link Command} results
     * 
     * @return command Command object containing the result, null if none received (yet)
     */
    Command<?> receive();

    /**
     * Client side to push a {@link Command} result back to container.
     * 
     * @param eventId used to correlate the result
     * @param command Command object containing the result, serialized
     */
    void push(String eventId, byte[] command);
}

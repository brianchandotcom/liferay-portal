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
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import org.jboss.arquillian.container.test.spi.ContainerMethodExecutor;
import org.jboss.arquillian.container.test.spi.command.Command;
import org.jboss.arquillian.container.test.spi.command.CommandCallback;
import org.jboss.arquillian.test.spi.TestMethodExecutor;
import org.jboss.arquillian.test.spi.TestResult;

/**
 * @author Matthew Tambara
 */
public class JMXMethodExecutor implements ContainerMethodExecutor {

    private final MBeanServerConnection mbeanServer;
    private final String objectName;
    private final CommandCallback callback;
    private final Map<String, String> protocolProps;

    public JMXMethodExecutor(MBeanServerConnection mbeanServer, CommandCallback callback) {
        this(mbeanServer, callback, JMXTestRunner.OBJECT_NAME, null);
    }

    public JMXMethodExecutor(MBeanServerConnection mbeanServer, CommandCallback callback, String objectName, Map<String, String> protocolProps) {
        this.mbeanServer = mbeanServer;
        this.callback = callback;
        this.objectName = objectName;
        this.protocolProps = protocolProps;
    }

    public TestResult invoke(TestMethodExecutor testMethodExecutor) {
        if (testMethodExecutor == null)
            throw new IllegalArgumentException("TestMethodExecutor null");

        String testClass = testMethodExecutor.getInstance().getClass().getName();
        String testMethod = testMethodExecutor.getMethod().getName();
        String testCanonicalName = testClass + "." + testMethod;

        NotificationListener commandListener = null;
        ObjectName objectName = null;
        TestResult result = null;
        try {
            objectName = new ObjectName(this.objectName);
            commandListener = new CallbackNotificationListener(objectName);
            mbeanServer.addNotificationListener(objectName, commandListener, null, null);

            JMXTestRunnerMBean testRunner = getMBeanProxy(objectName, JMXTestRunnerMBean.class);

            result = Serializer.toObject(TestResult.class, testRunner.runTestMethod(testClass, testMethod, protocolProps));

        } catch (final Throwable th) {
            result = new TestResult(TestResult.Status.FAILED);
            result.setThrowable(th);
        } finally {
            result.setEnd(System.currentTimeMillis());
            if (objectName != null && commandListener != null) {
                try {
                    mbeanServer.removeNotificationListener(objectName, commandListener);
                } catch (Throwable th) {
                }
            }
        }

        return result;
    }

    private <T> T getMBeanProxy(ObjectName name, Class<T> interf) {
        return MBeanServerInvocationHandler.newProxyInstance(mbeanServer, name, interf, false);
    }

    private class CallbackNotificationListener implements NotificationListener {
        private ObjectName serviceName;

        public CallbackNotificationListener(ObjectName serviceName) {
            this.serviceName = serviceName;
        }

        @Override
        public void handleNotification(Notification notification, Object handback) {
            String eventMessage = notification.getMessage();
            Command<?> command = Serializer.toObject(Command.class, (byte[]) notification.getUserData());
            callback.fired(command);

            try {
                mbeanServer.invoke(serviceName, "push", new Object[] { eventMessage, Serializer.toByteArray(command) }, new String[] { String.class.getName(),
                        byte[].class.getName() });
            } catch (Exception e) {
                throw new RuntimeException("Could not return command result for command " + command, e);
            }
        }
    }
}
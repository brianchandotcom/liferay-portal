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

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.ObjectName;
import org.jboss.arquillian.container.test.spi.TestRunner;
import org.jboss.arquillian.container.test.spi.command.Command;
import org.jboss.arquillian.container.test.spi.util.TestRunners;
import org.jboss.arquillian.test.spi.TestResult;

/**
 * @author Matthew Tambara
 */
public class JMXTestRunner extends NotificationBroadcasterSupport implements JMXTestRunnerMBean
{
   private static Logger log = Logger.getLogger(JMXTestRunner.class.getName());

   static MBeanServer localMBeanServer;

   private ConcurrentHashMap<String, Command<?>> events;

   private ThreadLocal<String> currentCall;

   private AtomicInteger integer = new AtomicInteger();

   private TestRunner mockTestRunner;

   private TestClassLoader testClassLoader;

   private final String objectName;

   public interface TestClassLoader
   {
      Class<?> loadTestClass(String className) throws ClassNotFoundException;
   }
   public JMXTestRunner(TestClassLoader classLoader)
   {
      this(classLoader,JMXTestRunnerMBean.OBJECT_NAME);
   }

   public JMXTestRunner(TestClassLoader classLoader, String objectName) {
      this.testClassLoader = classLoader;

      if (testClassLoader == null)
      {
         testClassLoader = new TestClassLoader()
         {
            public Class<?> loadTestClass(String className) throws ClassNotFoundException
            {
               ClassLoader classLoader = JMXTestRunner.class.getClassLoader();
               return classLoader.loadClass(className);
            }
         };
      }
      events = new ConcurrentHashMap<String, Command<?>>();
      currentCall = new ThreadLocal<String>();
      this.objectName = objectName;
   }

   public ObjectName registerMBean(MBeanServer mbeanServer) throws JMException
   {
      ObjectName oname = new ObjectName(this.objectName);
      mbeanServer.registerMBean(this, oname);
      log.fine("JMXTestRunner registered: " + oname);
      localMBeanServer = mbeanServer;
      return oname;
   }

   public void unregisterMBean(MBeanServer mbeanServer) throws JMException
   {
      ObjectName oname = new ObjectName(this.objectName);
      if (mbeanServer.isRegistered(oname))
      {
         mbeanServer.unregisterMBean(oname);
         log.fine("JMXTestRunner unregistered: " + oname);
      }
      localMBeanServer = null;
   }

   @Override
   public byte[] runTestMethod(String className, String methodName)
   {
       TestResult result = runTestMethodInternal(className, methodName, new HashMap<String, String>());
       return Serializer.toByteArray(result);
   }

   @Override
   public byte[] runTestMethod(String className, String methodName, Map<String, String> protocolProps)
   {
      try {
          final Class<?> impl = this.getClass();
          Method m = AccessController.doPrivileged(new PrivilegedExceptionAction<Method>() {
                 public Method run() throws NoSuchMethodException {
                    return impl.getMethod("runTestMethod", String.class, String.class);
                 }
              });

          if(m.getDeclaringClass() != JMXTestRunner.class) {
              return runTestMethod(className, methodName);
          }
      } catch(Exception e) { }

      TestResult result = runTestMethodInternal(className, methodName, protocolProps);
      return Serializer.toByteArray(result);
   }

   private TestResult runTestMethodInternal(String className, String methodName, Map<String, String> protocolProps)
   {
      currentCall.set(className + methodName);
      TestResult result = new TestResult();
      try
      {
         TestRunner runner = mockTestRunner;
         if (runner == null)
         {
            runner = TestRunners.getTestRunner(getClass().getClassLoader());
         }

         log.fine("Load test class: " + className);
         Class<?> testClass = testClassLoader.loadTestClass(className);
         log.fine("Test class loaded from: " + testClass.getClassLoader());

         log.fine("Execute: " + className + "." + methodName);
         result = doRunTestMethod(runner, testClass, methodName, protocolProps);
      }
      catch (Throwable th)
      {
         result.setStatus(TestResult.Status.FAILED);
         result.setEnd(System.currentTimeMillis());
         result.setThrowable(th);
      }
      finally
      {
         log.fine("Result: " + result);
         if (result.getStatus() == TestResult.Status.FAILED)
            log.log(Level.SEVERE, "Failed: " + className + "." + methodName, result.getThrowable());
      }
      return result;
   }

   protected TestResult doRunTestMethod(TestRunner runner, Class<?> testClass, String methodName, Map<String, String> protocolProps) {
       return runner.execute(testClass, methodName);
   }

   @Override
   public void send(Command<?> command)
   {
      Notification notification = new Notification("arquillian-command", this, integer.incrementAndGet(),
            currentCall.get());
      notification.setUserData(Serializer.toByteArray(command));
      sendNotification(notification);
   }

   @Override
   public Command<?> receive()
   {
      return events.remove(currentCall.get());
   }

   @Override
   public void push(String eventId, byte[] command)
   {
      events.put(eventId, Serializer.toObject(Command.class, command));
   }

   protected String getCurrentCall()
   {
      return currentCall.get();
   }

   protected void setCurrentCall(String current)
   {
      currentCall.set(current);
   }

   void setExposedTestRunnerForTest(TestRunner mockTestRunner)
   {
      this.mockTestRunner = mockTestRunner;
   }
}

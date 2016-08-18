/*
 * Copyright 2002-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.axis.configuration;

import org.apache.axis.AxisProperties;
import org.apache.axis.EngineConfigurationFactory;
import org.apache.axis.components.logger.LogFactory;
import org.apache.axis.utils.Messages;
import org.apache.commons.discovery.ResourceClassIterator;
import org.apache.commons.discovery.tools.ClassUtils;
import org.apache.commons.logging.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;


/**
 * This is a default implementation of EngineConfigurationFactory.
 * It is user-overrideable by a system property without affecting
 * the caller. If you decide to override it, use delegation if
 * you want to inherit the behaviour of this class as using
 * class extension will result in tight loops. That is, your
 * class should implement EngineConfigurationFactory and keep
 * an instance of this class in a member field and delegate
 * methods to that instance when the default behaviour is
 * required.
 *
 * @author Richard A. Sitze
 */
public class EngineConfigurationFactoryFinder
{
    protected static Log log =
        LogFactory.getLog(EngineConfigurationFactoryFinder.class.getName());

    private static final Class mySpi = EngineConfigurationFactory.class;

    private static final Class[] newFactoryParamTypes =
        new Class[] { Object.class };

    private static final String requiredMethod =
        "public static EngineConfigurationFactory newFactory(Object)";

    static {
        AxisProperties.setClassOverrideProperty(
                EngineConfigurationFactory.class,
                EngineConfigurationFactory.SYSTEM_PROPERTY_NAME);

        AxisProperties.setClassDefaults(EngineConfigurationFactory.class,
                            new String[] {
                                "org.apache.axis.configuration.EngineConfigurationFactoryServlet",
                                "org.apache.axis.configuration.EngineConfigurationFactoryDefault",
                                });
    }

    private EngineConfigurationFactoryFinder() {
    }


    /**
     * Create the default engine configuration and detect whether the user
     * has overridden this with their own.
     *
     * The discovery mechanism will use the following logic:
     *
     * - discover all available EngineConfigurationFactories
     *   - find all META-INF/services/org.apache.axis.EngineConfigurationFactory
     *     files available through class loaders.
     *   - read files (see Discovery) to obtain implementation(s) of that
     *     interface
     * - For each impl, call 'newFactory(Object param)'
     * - Each impl should examine the 'param' and return a new factory ONLY
     *   - if it knows what to do with it
     *     (i.e. it knows what to do with the 'real' type)
     *   - it can find it's configuration information
     * - Return first non-null factory found.
     * - Try EngineConfigurationFactoryServlet.newFactory(obj)
     * - Try EngineConfigurationFactoryDefault.newFactory(obj)
     * - If zero found (all return null), throw exception
     *
     * ***
     * This needs more work: System.properties, etc.
     * Discovery will have more tools to help with that
     * (in the manner of use below) in the near future.
     * ***
     *
     */
    public static EngineConfigurationFactory newFactory(final Object obj) {
        /**
         * recreate on each call is critical to gaining
         * the right class loaders.  Do not cache.
         */
        final Object[] params = new Object[] { obj };

        /**
         * Find and examine each service
         */
        return (EngineConfigurationFactory)AccessController.doPrivileged(
                new PrivilegedAction() {
                    public Object run() {
                        ResourceClassIterator services = AxisProperties.getResourceClassIterator(mySpi);

                        EngineConfigurationFactory factory = null;

                        while (factory == null  &&  services.hasNext()) {
                          try {
                            Class service = services.nextResourceClass().loadClass();
                
                            /* service == null
                             * if class resource wasn't loadable
                             */
                            if (service != null) {
                                factory = newFactory(service, newFactoryParamTypes, params);
                            }
                          } catch (Exception e) {
                            // there was an exception creating the factory
                            // the most likely cause was the JDK 1.4 problem
                            // in the discovery code that requires servlet.jar
                            // to be in the client classpath.  For now, fall
                            // through to the next factory
                          }
                        }
                
                        if (factory != null) {
                            if(log.isDebugEnabled()) {
                                log.debug(Messages.getMessage("engineFactory", factory.getClass().getName()));
                            }
                        } else {
                            log.error(Messages.getMessage("engineConfigFactoryMissing"));
                            // we should be throwing an exception here,
                            //
                            // but again, requires more refactoring than we want to swallow
                            // at this point in time.  Ifthis DOES occur, it's a coding error:
                            // factory should NEVER be null.
                            // Testing will find this, as NullPointerExceptions will be generated
                            // elsewhere.
                        }
                
                        return factory;
                    }
                });
    }

    public static EngineConfigurationFactory newFactory() {
        return newFactory(null);
    }

    private static EngineConfigurationFactory newFactory(Class service,
                                                         Class[] paramTypes,
                                                         Object[] param) {
        /**
         * Some JDK's may link on method resolution (findPublicStaticMethod)
         * and others on method call (method.invoke).
         * 
         * Either way, catch class load/resolve problems and return null.
         */
        
        try {
            /**
             * Verify that service implements:
             *  public static EngineConfigurationFactory newFactory(Object);
             */
            Method method = ClassUtils.findPublicStaticMethod(service,
                                                  EngineConfigurationFactory.class,
                                                  "newFactory",
                                                  paramTypes);
    
            if (method == null) {
                log.warn(Messages.getMessage("engineConfigMissingNewFactory",
                                              service.getName(),
                                              requiredMethod));
            } else {
                try {
                    return (EngineConfigurationFactory)method.invoke(null, param);
                } catch (InvocationTargetException e) {
                    if (e.getTargetException() instanceof NoClassDefFoundError) {
                        log.debug(Messages.getMessage("engineConfigLoadFactory",
                                                      service.getName()));
                    } else {
                        log.warn(Messages.getMessage("engineConfigInvokeNewFactory",
                                                      service.getName(),
                                                      requiredMethod), e);
                    }
                } catch (Exception e) {
                    log.warn(Messages.getMessage("engineConfigInvokeNewFactory",
                                                  service.getName(),
                                                  requiredMethod), e);
                }
            }
        } catch (NoClassDefFoundError e) {
            log.debug(Messages.getMessage("engineConfigLoadFactory",
                                          service.getName()));
        }

        return null;
    }
}

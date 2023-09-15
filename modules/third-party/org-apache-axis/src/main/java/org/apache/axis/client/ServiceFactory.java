/*
 * Copyright 2001-2004 The Apache Software Foundation.
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

package org.apache.axis.client;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.configuration.EngineConfigurationFactoryFinder;
import org.apache.axis.utils.ClassUtils;
import org.apache.axis.utils.Messages;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

/**
 * Helper class for obtaining Services from JNDI.
 *
 * !!! WORK IN PROGRESS
 * 
 * @author Glen Daniels (gdaniels@apache.org)
 */ 

public class ServiceFactory extends javax.xml.rpc.ServiceFactory
        implements ObjectFactory
{
    // Constants for RefAddrs in the Reference.
    public static final String SERVICE_CLASSNAME  = "service classname";
    public static final String WSDL_LOCATION      = "WSDL location";
    public static final String MAINTAIN_SESSION   = "maintain session";
    public static final String SERVICE_NAMESPACE  = "service namespace";
    public static final String SERVICE_LOCAL_PART = "service local part";
    public static final String SERVICE_IMPLEMENTATION_NAME_PROPERTY = "serviceImplementationName";

    private static final String SERVICE_IMPLEMENTATION_SUFFIX = "Locator";

    private static EngineConfiguration _defaultEngineConfig = null;

    private static ThreadLocal threadDefaultConfig = new ThreadLocal();

    public static void setThreadDefaultConfig(EngineConfiguration config)
    {
        threadDefaultConfig.set(config);
    }
    
    private static EngineConfiguration getDefaultEngineConfig() {
        if (_defaultEngineConfig == null) {
            _defaultEngineConfig =
                EngineConfigurationFactoryFinder.newFactory().getClientEngineConfig();
        }
        return _defaultEngineConfig;
    }

    /**
     * Obtain an AxisClient reference, using JNDI if possible, otherwise
     * creating one using the standard Axis configuration pattern.  If we
     * end up creating one and do have JNDI access, bind it to the passed
     * name so we find it next time.
     *
     * @param environment
     * @return a service
     */
    public static Service getService(Map environment)
    {
        Service service = null;
        InitialContext context = null;

        EngineConfiguration configProvider =
            (EngineConfiguration)environment.get(EngineConfiguration.PROPERTY_NAME);

        if (configProvider == null)
            configProvider = (EngineConfiguration)threadDefaultConfig.get();

        if (configProvider == null)
            configProvider = getDefaultEngineConfig();

        // First check to see if JNDI works
        // !!! Might we need to set up context parameters here?
        try {
            context = new InitialContext();
        } catch (NamingException e) {
        }
        
        if (context != null) {
            String name = (String)environment.get("jndiName");
            if (name == null) {
                name = "axisServiceName";
            }

            // We've got JNDI, so try to find an AxisClient at the
            // specified name.
            try {
                service = (Service)context.lookup(name);
            } catch (NamingException e) {
                service = new Service(configProvider);
                try {
                    context.bind(name, service);
                } catch (NamingException e1) {
                    // !!! Couldn't do it, what should we do here?
                }
            }
        } else {
            service = new Service(configProvider);
        }

        return service;
    }

    public Object getObjectInstance(Object refObject, Name name,
            Context nameCtx, Hashtable environment) throws Exception
    {
        Object instance = null;
        if (refObject instanceof Reference) {
            Reference ref = (Reference) refObject;

            RefAddr addr = ref.get(SERVICE_CLASSNAME);
            Object obj = null;
            // If an explicit service classname is provided, then this is a
            // generated Service class.  Just use its default constructor.
            if (addr != null && (obj = addr.getContent()) instanceof String) {
                instance = ClassUtils.forName((String) obj).newInstance();
            }
            // else this is an instance of the Service class, so grab the
            // reference data...
            else {
                // Get the WSDL location...
                addr = ref.get(WSDL_LOCATION);
                if (addr != null && (obj = addr.getContent()) instanceof String) {
                    URL wsdlLocation = new URL((String) obj);

                    // Build the service qname...
                    addr = ref.get(SERVICE_NAMESPACE);
                    if (addr != null
                        && (obj = addr.getContent()) instanceof String) {
                        String namespace = (String) obj;
                        addr = ref.get(SERVICE_LOCAL_PART);
                        if (addr != null
                            && (obj = addr.getContent()) instanceof String) {
                            String localPart = (String) obj;
                            QName serviceName = new QName(namespace, localPart);

                            // Construct an instance of the service
                            Class[] formalArgs = new Class[]
                                    {URL.class, QName.class};
                            Object[] actualArgs = new Object[]
                                    {wsdlLocation, serviceName};
                            Constructor ctor =
                                    Service.class.getDeclaredConstructor(
                                    formalArgs);
                            instance = ctor.newInstance(actualArgs);
                        }
                    }
                }
            }
            // If maintainSession should be set to true, there will be an
            // addr for it.
            addr = ref.get(MAINTAIN_SESSION);
            if (addr != null && instance instanceof Service) {
                ((Service) instance).setMaintainSession(true);
            }
        }
        return instance;
    } // getObjectInstance

    /**
     *  Create a Service instance.
     *  @param   wsdlDocumentLocation URL for the WSDL document location
                              for the service
     *  @param   serviceName  QName for the service.
     *  @return  Service.
     *  @throws  ServiceException If any error in creation of the specified service
     */
    public javax.xml.rpc.Service createService(URL wsdlDocumentLocation,
            QName serviceName) throws ServiceException {
        return new Service(wsdlDocumentLocation, serviceName);
    } // createService

    /**
     * Create a Service instance.  Since the WSDL file is not provided
     * here, the Service object returned is quite simpleminded.
     * Likewise, the Call object that service.createCall will return
     * will also be simpleminded.  The caller must explicitly fill in
     * all the info on the Call object (ie., endpoint address, etc.).
     *
     *  @param   serviceName QName for the service
     *  @return  Service.
     *  @throws  ServiceException If any error in creation of the specified service
     */
    public javax.xml.rpc.Service createService(QName serviceName)
            throws ServiceException {
        return new Service(serviceName);
    } // createService

    /**
     * Create an instance of the generated service implementation class 
     * for a given service interface, if available. 
     *
     *  @param   serviceInterface Service interface 
     *  @return  Service.
     *  @throws  ServiceException If there is any error while creating the specified service, 
     *      including the case where a generated service implementation class cannot be located
     */
    public javax.xml.rpc.Service loadService(Class serviceInterface) throws ServiceException {
        if (serviceInterface == null) {
            throw new IllegalArgumentException(
                    Messages.getMessage("serviceFactoryIllegalServiceInterface"));
        }
        if (!(javax.xml.rpc.Service.class).isAssignableFrom(serviceInterface))
        {
            throw new ServiceException(
                    Messages.getMessage("serviceFactoryServiceInterfaceRequirement", serviceInterface.getName()));
        } else {
            String serviceImplementationName = serviceInterface.getName() + SERVICE_IMPLEMENTATION_SUFFIX;
            Service service = createService(serviceImplementationName);
            return service;
        }
    }

    /**
     * Create an instance of the generated service implementation class 
     * for a given service interface, if available. 
     * An implementation may use the provided wsdlDocumentLocation and properties 
     * to help locate the generated implementation class. 
     * If no such class is present, a ServiceException will be thrown.
     *
     *  @param   wsdlDocumentLocation URL for the WSDL document location for the service or null 
     *  @param   serviceInterface Service interface 
     *  @param   properties A set of implementation-specific properties 
     *      to help locate the generated service implementation class 
     *  @return  Service.
     *  @throws  ServiceException If there is any error while creating the specified service, 
     *      including the case where a generated service implementation class cannot be located
     */
    public javax.xml.rpc.Service loadService(URL wsdlDocumentLocation, 
            Class serviceInterface, Properties properties) throws ServiceException {
        if (serviceInterface == null) {
            throw new IllegalArgumentException(
                    Messages.getMessage("serviceFactoryIllegalServiceInterface"));
        }
        if (!(javax.xml.rpc.Service.class).isAssignableFrom(serviceInterface))
        {
            throw new ServiceException(
                    Messages.getMessage("serviceFactoryServiceInterfaceRequirement", serviceInterface.getName()));
        } else {
            String serviceImplementationName = serviceInterface.getName() + SERVICE_IMPLEMENTATION_SUFFIX;
            Service service = createService(serviceImplementationName);
            return service;
        }
    }

    /**
     * Create an instance of the generated service implementation class 
     * for a given service, if available. 
     * The service is uniquely identified by the wsdlDocumentLocation and serviceName arguments. 
     * An implementation may use the provided properties to help locate the generated implementation class. 
     * If no such class is present, a ServiceException will be thrown. 
     *
     *  @param   wsdlDocumentLocation URL for the WSDL document location for the service or null 
     *  @param   serviceName Qualified name for the service 
     *  @param   properties A set of implementation-specific properties 
     *      to help locate the generated service implementation class 
     *  @return  Service.
     *  @throws  ServiceException If there is any error while creating the specified service, 
     *      including the case where a generated service implementation class cannot be located
     */
    public javax.xml.rpc.Service loadService(URL wsdlDocumentLocation, 
            QName serviceName, Properties properties) throws ServiceException {
        String serviceImplementationName = properties.getProperty(SERVICE_IMPLEMENTATION_NAME_PROPERTY);
        javax.xml.rpc.Service service = createService(serviceImplementationName);
        if (service.getServiceName().equals(serviceName)) {
            return service;
        } else {
            throw new ServiceException(
                    Messages.getMessage("serviceFactoryServiceImplementationNotFound", serviceImplementationName));
        }
    }

    private Service createService(String serviceImplementationName) throws ServiceException {
        if(serviceImplementationName == null) {
            throw new IllegalArgumentException(Messages.getMessage("serviceFactoryInvalidServiceName"));
        }
        try {
            Class serviceImplementationClass;
            serviceImplementationClass = Thread.currentThread().getContextClassLoader().loadClass(serviceImplementationName);
            if (!(org.apache.axis.client.Service.class).isAssignableFrom(serviceImplementationClass)) {
                throw new ServiceException(
                        Messages.getMessage("serviceFactoryServiceImplementationRequirement", serviceImplementationName));
            }
            Service service = (Service) serviceImplementationClass.newInstance();
            if (service.getServiceName() != null) {
                return service;
            } else {
                throw new ServiceException(Messages.getMessage("serviceFactoryInvalidServiceName"));
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e){
            throw new ServiceException(e);
        }
        
    }
}

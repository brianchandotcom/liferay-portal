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

package org.apache.axis;

import org.apache.axis.encoding.TypeMappingRegistry;
import org.apache.axis.handlers.soap.SOAPService;

import javax.xml.namespace.QName;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * EngineConfiguration is an interface that the Message Flow subsystem
 * provides so that engine configuration can be provided in a pluggable
 * way. An instance of EngineConfiguration provides configuration
 * for a particular engine instance.
 * <p>
 * Concrete implementations of this interface will obtain configuration
 * information from some source (examples might be files, Strings, or
 * databases) and are responsible for writing it into an AxisEngine, and
 * writing an AxisEngine's state back out to whatever storage medium is in use.
 *
 * @author Glyn Normington (glyn@apache.org)
 * @author Glen Daniels (gdaniels@apache.org)
 */
public interface EngineConfiguration extends java.io.Serializable {
    /**
     * Property name used for setting an EngineConfiguration to be used
     * in creating engines.
     */
    static final String PROPERTY_NAME = "engineConfig";

     /**
     * Configure this AxisEngine using whatever data source we have.
     *
     * @param engine the AxisEngine we'll deploy state to
     * @throws ConfigurationException if there was a problem
     */
    void configureEngine(AxisEngine engine) throws ConfigurationException;

    /**
     * Read the configuration from an engine, and store it somehow.
     *
     * @param engine the AxisEngine from which to read state.
     * @throws ConfigurationException if there was a problem
     */
    void writeEngineConfig(AxisEngine engine) throws ConfigurationException;

    // fixme: if no handler is found, do we return null, or throw a
    //  ConfigurationException, or throw another exception? IMHO returning
    //  null is nearly always evil
    /**
     * Retrieve an instance of the named handler.
     *
     * @param qname the <code>QName</code> identifying the
     *              <code>Handler</code>
     * @return the <code>Handler</code> associated with <code>qname</code>
     * @throws ConfigurationException if there was a failure in resolving
     *              <code>qname</code>
     */
    Handler getHandler(QName qname) throws ConfigurationException;

    /**
     * Retrieve an instance of the named service.
     *
     * @param qname the <code>QName</code> identifying the
     *              <code>Service</code>
     * @return the <code>Service</code> associated with <code>qname</code>
     * @throws ConfigurationException if there was an error resolving the
     *              qname
     */
    SOAPService getService(QName qname) throws ConfigurationException;

    /**
     * Get a service which has been mapped to a particular namespace.
     *
     * @param namespace a namespace URI
     * @return an instance of the appropriate Service, or null
     * @throws ConfigurationException if there was an error resolving the
     *              namespace
     */
    SOAPService getServiceByNamespaceURI(String namespace)
        throws ConfigurationException;

    /**
     * Retrieve an instance of the named transport.
     *
     * @param qname the <code>QName</code> of the transport
     * @return a <code>Handler</code> implementing the transport
     * @throws ConfigurationException if there was an error resolving the
     *              transport
     */
    Handler getTransport(QName qname) throws ConfigurationException;

    /**
     * Retrieve the TypeMappingRegistry for this engine.
     *
     * @return the type mapping registry
     * @throws ConfigurationException  if there was an error resolving the
     *              registry
     */
    TypeMappingRegistry getTypeMappingRegistry()
        throws ConfigurationException;

    /**
     * Returns a global request handler.
     *
     * @return the <code>Handler</code> that globally handles requests
     * @throws ConfigurationException  if there was some error fetching the
     *              handler
     */
    Handler getGlobalRequest() throws ConfigurationException;

    /**
     * Returns a global response handler.
     *
     * @return the <code>Handler</code> that globally handles responses
     * @throws ConfigurationException  if there was some error fetching the
     *              handler
     */
    Handler getGlobalResponse() throws ConfigurationException;

    // fixme: where is the contract for what can be in this Hashtable?
    // fixme: did we intend to use Hashtable? Will Map do? Do we need
    //  synchronization? If so, will one of the Collections synchronized
    //  wrappers do fine?
    /**
     * Returns the global configuration options.
     *
     * @return the global options as a <code>Hashtable</code>
     * @throws ConfigurationException if the global options could not be
     *              returned
     */
    Hashtable getGlobalOptions() throws ConfigurationException;

    /**
     * Get an enumeration of the services deployed to this engine.
     * Each service is represented as <code>ServiceDesc</code> object.
     *
     * @see org.apache.axis.description.ServiceDesc
     * @return an <code>Iterator</code> over the <code>ServiceDesc</code>
     *              objects
     * @throws ConfigurationException if the deployed services could not be
     *              returned
     */
    Iterator getDeployedServices() throws ConfigurationException;

    /**
     * Get a list of roles that this engine plays globally.  Services
     * within the engine configuration may also add additional roles.
     *
     * @return a <code>List</code> of the roles for this engine
     */
    List getRoles();
}
/* @generated */
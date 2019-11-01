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
package org.apache.axis.description;

import org.apache.axis.AxisServiceConfig;
import org.apache.axis.Constants;
import org.apache.axis.InternalException;
import org.apache.axis.AxisProperties;
import org.apache.axis.components.logger.LogFactory;
import org.apache.axis.encoding.*;
import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;
import org.apache.axis.message.SOAPBodyElement;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.axis.utils.JavaUtils;
import org.apache.axis.utils.Messages;
import org.apache.axis.utils.bytecode.ParamNameExtractor;
import org.apache.axis.wsdl.Skeleton;
import org.apache.axis.wsdl.fromJava.Namespaces;
import org.apache.commons.logging.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import javax.xml.rpc.holders.Holder;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;


/**
 * A ServiceDesc is an abstract description of a service.
 *
 * ServiceDescs contain OperationDescs, which are descriptions of operations.
 * The information about a service's operations comes from one of two places:
 * 1) deployment, or 2) introspection.
 *
 * @author Glen Daniels (gdaniels@apache.org)
 */
public class JavaServiceDesc implements ServiceDesc {
    protected static Log log =
            LogFactory.getLog(JavaServiceDesc.class.getName());

    /** The name of this service */
    private String name = null;

    /** The documentation of this service */
	private String documentation = null;

    /** Style/Use */
    private Style style = Style.RPC;
    private Use   use   = Use.ENCODED;

    // Style and Use are related.  By default, if Style==RPC, Use should be
    // ENCODED.  But if Style==DOCUMENT, Use should be LITERAL.  So we want
    // to keep the defaults synced until someone explicitly sets the Use.
    private boolean useSet = false;

    /** Our operations - a list of OperationDescs */
    private ArrayList operations = new ArrayList();

    /** A collection of namespaces which will map to this service */
    private List namespaceMappings = null;

    /**
     * Where does our WSDL document live?  If this is non-null, the "?WSDL"
     * generation will automatically return this file instead of dynamically
     * creating a WSDL.  BE CAREFUL because this means that Handlers will
     * not be able to add to the WSDL for extensions/headers....
     */
    private String wsdlFileName = null;

    /**
     * An endpoint URL which someone has specified for this service.  If
     * this is set, WSDL generation will pick it up instead of defaulting
     * to the transport URL.
     */
    private String endpointURL = null;

    /** Place to store user-extensible service-related properties */
    private HashMap properties = null;

    /** Lookup caches */
    private HashMap name2OperationsMap = null;
    private HashMap qname2OperationsMap = null;
    private transient HashMap method2OperationMap = new HashMap();
    
    // THE FOLLOWING STUFF IS ALL JAVA-SPECIFIC, AND WILL BE FACTORED INTO
    // A JAVA-SPECIFIC SUBCLASS.  --Glen

    /** List of allowed methods */
    /** null allows everything, an empty ArrayList allows nothing */
    private List allowedMethods = null;

    /** List if disallowed methods */
    private List disallowedMethods = null;

    /** Implementation class */
    private Class implClass = null;

    /**
     * Is the implementation a Skeleton?  If this is true, it will generate
     * a Fault to provide OperationDescs via WSDD.
     */
    private boolean isSkeletonClass = false;

    /** Cached copy of the skeleton "getOperationDescByName" method */
    private transient Method skelMethod = null;

    /** Classes at which we should stop looking up the inheritance chain
     *  when introspecting
     */
    private ArrayList stopClasses = null;

    /** Lookup caches */
    private transient HashMap method2ParamsMap = new HashMap();
    private OperationDesc messageServiceDefaultOp = null;

    /** Method names for which we have completed any introspection necessary */
    private ArrayList completedNames = new ArrayList();

    /** Our typemapping for resolving Java<->XML type issues */
    private TypeMapping tm = null;
    private TypeMappingRegistry tmr = null;

    private boolean haveAllSkeletonMethods = false;
    private boolean introspectionComplete = false;

    /**
     * Default constructor
     */
    public JavaServiceDesc() {
    }

    /**
     * What kind of service is this?
     * @return
     */
    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
        if (!useSet) {
            // Use hasn't been explicitly set, so track style
            use = style == Style.RPC ? Use.ENCODED : Use.LITERAL;
        }
    }


    /**
     * What kind of use is this?
     * @return
     */
    public Use getUse() {
        return use;
    }

    public void setUse(Use use) {
        useSet = true;
        this.use = use;
    }

    /**
     * Determine whether or not this is a "wrapped" invocation, i.e. whether
     * the outermost XML element of the "main" body element represents a
     * method call, with the immediate children of that element representing
     * arguments to the method.
     *
     * @return true if this is wrapped (i.e. RPC or WRAPPED style),
     *         false otherwise
     */
    public boolean isWrapped()
    {
        return ((style == Style.RPC) || 
                (style == Style.WRAPPED));
    }

    /**
     * the wsdl file of the service.
     * When null, it means that the wsdl should be autogenerated
     * @return filename or null
     */
    public String getWSDLFile() {
        return wsdlFileName;
    }

    /**
     * set the wsdl file of the service; this causes the named
     * file to be returned on a ?wsdl, probe, not introspection
     * generated wsdl.
     * @param wsdlFileName filename or null to re-enable introspection
     */
    public void setWSDLFile(String wsdlFileName) {
        this.wsdlFileName = wsdlFileName;
    }

    public List getAllowedMethods() {
        return allowedMethods;
    }

    public void setAllowedMethods(List allowedMethods) {
        this.allowedMethods = allowedMethods;
    }

    public Class getImplClass() {
        return implClass;
    }

    /**
     * set the implementation class
     * <p>
     * Warning: You cannot call getInitializedServiceDesc() after setting this
     * as it uses this to indicate its work has already been done.
     *
     * @param implClass
     * @throws IllegalArgumentException if the implementation class is already
     *         set
     */
    public void setImplClass(Class implClass) {
        if (this.implClass != null)
            throw new IllegalArgumentException(
                    Messages.getMessage("implAlreadySet"));

        this.implClass = implClass;
        if (Skeleton.class.isAssignableFrom(implClass)) {
            isSkeletonClass = true;
            loadSkeletonOperations();
        }
    }

    private void loadSkeletonOperations() {
        Method method = null;
        try {
            method = implClass.getDeclaredMethod("getOperationDescs",
                                                 new Class [] {});
        } catch (NoSuchMethodException e) {
        } catch (SecurityException e) {
        }
        if (method == null) {
            // FIXME : Throw an error?
            return;
        }

        try {
            Collection opers = (Collection)method.invoke(implClass, null);
            for (Iterator i = opers.iterator(); i.hasNext();) {
                OperationDesc skelDesc = (OperationDesc)i.next();
                addOperationDesc(skelDesc);
            }
        } catch (IllegalAccessException e) {
            if(log.isDebugEnabled()) {
                log.debug(Messages.getMessage("exception00"), e);
            }
            return;
        } catch (IllegalArgumentException e) {
            if(log.isDebugEnabled()) {
                log.debug(Messages.getMessage("exception00"), e);
            }
            return;
        } catch (InvocationTargetException e) {
            if(log.isDebugEnabled()) {
                log.debug(Messages.getMessage("exception00"), e);
            }
            return;
        }
        haveAllSkeletonMethods = true;
    }

    public TypeMapping getTypeMapping() {
        if(tm == null) {
            return DefaultTypeMappingImpl.getSingletonDelegate();
//            throw new RuntimeException(Messages.getMessage("noDefaultTypeMapping00"));
        }
        return tm;
    }

    public void setTypeMapping(TypeMapping tm) {
        this.tm = tm;
    }

    /**
     * the name of the service
     */
    public String getName() {
        return name;
    }

    /**
     * the name of the service
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the documentation for the service
     */
	public String getDocumentation() {
    	return documentation; 
    }

    /**
     * set the documentation for the service
     */
	public void setDocumentation(String documentation) {
    	this.documentation = documentation;
    }

    public ArrayList getStopClasses() {
        return stopClasses;
    }

    public void setStopClasses(ArrayList stopClasses) {
        this.stopClasses = stopClasses;
    }

    public List getDisallowedMethods() {
        return disallowedMethods;
    }

    public void setDisallowedMethods(List disallowedMethods) {
        this.disallowedMethods = disallowedMethods;
    }

    public void removeOperationDesc(OperationDesc operation) {
        operations.remove(operation);
        operation.setParent(null);

        if (name2OperationsMap != null) {
            String name = operation.getName();
            ArrayList overloads = (ArrayList)name2OperationsMap.get(name);
            if (overloads != null) {
                overloads.remove(operation);
                if (overloads.size() == 0) {
                    name2OperationsMap.remove(name);
                }
            }
        }
        
        if (qname2OperationsMap != null) {
            QName qname = operation.getElementQName();
            ArrayList list = (ArrayList)qname2OperationsMap.get(qname);
            if (list != null) {
                list.remove(operation);
            }
        }
        
        if (method2OperationMap != null) {
            Method method = operation.getMethod();
            if (method != null) {
                method2OperationMap.remove(method);
            }
        }
    }
    
    public void addOperationDesc(OperationDesc operation)
    {
        operations.add(operation);
        operation.setParent(this);
        // liferay init map if null
        if (name2OperationsMap == null) {
            name2OperationsMap = new HashMap();
        }

        // Add name to name2Operations Map
        String name = operation.getName();
        ArrayList overloads = (ArrayList)name2OperationsMap.get(name);
        if (overloads == null) {
            overloads = new ArrayList();
            name2OperationsMap.put(name, overloads);
        } else if (JavaUtils.isTrue(
                AxisProperties.getProperty(Constants.WSIBP11_COMPAT_PROPERTY)) &&
                overloads.size() > 0) {
            throw new RuntimeException(Messages.getMessage("noOverloadedOperations", name));
        }
        overloads.add(operation);
    }

    /**
     * get all the operations as a list of OperationDescs.
     * this method triggers an evaluation of the valid operations by
     * introspection, so use sparingly
     * @return reference to the operations array. This is not a copy
     */
    public ArrayList getOperations()
    {
        loadServiceDescByIntrospection();  // Just in case...
        return operations;
    }

    /**
     * get all overloaded operations by name
     * @param methodName
     * @return null for no match, or an array of OperationDesc objects
     */
    public OperationDesc [] getOperationsByName(String methodName)
    {
        getSyncedOperationsForName(implClass, methodName);

        if (name2OperationsMap == null)
            return null;

        ArrayList overloads = (ArrayList)name2OperationsMap.get(methodName);
        if (overloads == null) {
            return null;
        }

        OperationDesc [] array = new OperationDesc [overloads.size()];
        return (OperationDesc[])overloads.toArray(array);
    }

    /**
     * Return an operation matching the given method name.  Note that if we
     * have multiple overloads for this method, we will return the first one.
     * @return null for no match
     */
    public OperationDesc getOperationByName(String methodName)
    {
        // If we need to load up operations from introspection data, do it.
        // This returns fast if we don't need to do anything, so it's not very
        // expensive.
        getSyncedOperationsForName(implClass, methodName);

        if (name2OperationsMap == null)
            return null;

        ArrayList overloads = (ArrayList)name2OperationsMap.get(methodName);
        if (overloads == null) {
            return null;
        }

        return (OperationDesc)overloads.get(0);
    }

    /**
     * Map an XML QName to an operation.  Returns the first one it finds
     * in the case of mulitple matches.
     * @return null for no match
     */
    public OperationDesc getOperationByElementQName(QName qname)
    {
        OperationDesc [] overloads = getOperationsByQName(qname);

        // Return the first one....
        if ((overloads != null) && overloads.length > 0)
            return overloads[0];

        return null;
    }

    /**
     * Return all operations which match this QName (i.e. get all the
     * overloads)
     * @return null for no match
     */
    public OperationDesc [] getOperationsByQName(QName qname)
    {
        // Look in our mapping of QNames -> operations.

        // But first, let's make sure we've initialized said mapping....
        initQNameMap();

        ArrayList overloads = (ArrayList)qname2OperationsMap.get(qname);
        if (overloads == null) {
            // Nothing specifically matching this QName.
            if (name2OperationsMap != null) {
                if ((isWrapped() ||
                     ((style == Style.MESSAGE) &&
                      (getDefaultNamespace() == null)))) {
                    // Try ignoring the namespace....?
                    overloads = (ArrayList) name2OperationsMap.get(qname.getLocalPart());
                } else {
                    // TODO the above code is weird: a JavaServiceDesc can  be document or rpc and
                    // still define a WSDL operation using a wrapper style  mapping.
                    // The following code handles this case.
                    Object ops = name2OperationsMap.get(qname.getLocalPart());
                    if (ops != null) {
                        overloads = new ArrayList((Collection) ops);
                        for (Iterator iter = overloads.iterator(); iter.hasNext();) {
                            OperationDesc operationDesc = (OperationDesc) iter.next();
                            if (Style.WRAPPED != operationDesc.getStyle()) {
                                iter.remove();
                            }
                        }
                    }
                }
            }
            // Handle the case where a single Message-style operation wants
            // to accept anything.
            if ((style == Style.MESSAGE) && (messageServiceDefaultOp != null))
                return new OperationDesc [] { messageServiceDefaultOp };

            if (overloads == null)
                return null;
        }

        getSyncedOperationsForName(implClass,
                                   ((OperationDesc)overloads.get(0)).getName());

        // Sort the overloads by number of arguments - prevents us calling methods
        // with more parameters than supplied in the request (with missing parameters
        // defaulted to null) when a perfectly good method exists with exactly the
        // supplied parameters.
        Collections.sort(overloads,
            new Comparator() {
                public int compare(Object o1, Object o2)
                {
                    Method meth1 = ((OperationDesc)o1).getMethod();
                    Method meth2 = ((OperationDesc)o2).getMethod();
                    return meth2.toString().compareTo(meth1.toString());
                }
            });

        OperationDesc [] array = new OperationDesc [overloads.size()];
        return (OperationDesc[])overloads.toArray(array);
    }

    private synchronized void initQNameMap() {
        if (qname2OperationsMap == null) {
            loadServiceDescByIntrospection();

            qname2OperationsMap = new HashMap();
            for (Iterator i = operations.iterator(); i.hasNext();) {
                OperationDesc operationDesc = (OperationDesc) i.next();
                QName qname = operationDesc.getElementQName();
                ArrayList list = (ArrayList)qname2OperationsMap.get(qname);
                if (list == null) {
                    list = new ArrayList();
                    qname2OperationsMap.put(qname, list);
                }
                list.add(operationDesc);
            }
        }
    }

    /**
     * Synchronize an existing OperationDesc to a java.lang.Method.
     *
     * This method is used when the deployer has specified operation metadata
     * and we want to match that up with a real java Method so that the
     * Operation-level dispatch carries us all the way to the implementation.
     * Search the declared methods on the implementation class to find one
     * with an argument list which matches our parameter list.
     */
    private void syncOperationToClass(OperationDesc oper, Class implClass)
    {
        // ------------------------------------------------
        // Developer Note:
        //
        // The goal of the sync code is to associate
        // the OperationDesc/ParamterDesc with the
        // target Method.  There are a number of ways to get to this
        // point depending on what information
        // is available.  Here are the main scenarios:
        //
        // A) Deployment with wsdd (non-skeleton):
        //   * OperationDesc/ParameterDesc loaded from deploy.wsdd
        //   * Loaded ParameterDesc does not have javaType,
        //     so it is discovered using the TypeMappingRegistry
        //     (also loaded via deploy.wsdd) and the
        //     typeQName specified by the ParameterDesc.
        //   * Sync occurs using the discovered
        //     javaTypes and the javaTypes of the Method
        //     parameters
        //
        // B) Deployment with no wsdd OperationDesc info (non-skeleton):
        //   * Implementation Class introspected to build
        //     OperationDesc/ParameterDesc.
        //   * ParameterDesc is known via introspection.
        //   * ParameterDesc are discovered using javaType
        //     and TypeMappingRegistry.
        //   * Sync occurs using the introspected
        //     javaTypes and the javaTypes of the Method
        //     parameters
        //
        // C) Deployment with wsdd (skeleton):
        //   * OperationDesc/ParameterDesc loaded from the Skeleton
        //   * In this scenario the ParameterDescs' already
        //     have javaTypes (see E below).
        //   * Sync occurs using the ParameterDesc
        //     javaTypes and the javaTypes of the Method
        //     parameters.
        //
        // D) Commandline Java2WSDL loading non-Skeleton Class/Interface
        //   * Class/Interface introspected to build
        //     OperationDesc/ParameterDesc.
        //   * The javaTypes of the ParameterDesc are set using introspection.
        //   * typeQNames are determined for built-in types using
        //     from the default TypeMappingRegistry.  Other
        //     typeQNames are guessed from the javaType.  Note
        //     that there is no loaded TypeMappingRegistry.
        //   * Sync occurs using the ParameterDesc
        //     javaTypes and the javaTypes of the Method
        //     parameters.
        //
        // E) Commandline Java2WSDL loading Skeleton Class
        //   * OperationDesc/ParameterDesc loaded from Skeleton
        //   * Each ParameterDesc has an appropriate typeQName
        //   * Each ParameterDesc also has a javaType, which is
        //     essential for sync'ing up with the
        //     method since there is no loaded TypeMappingRegistry.
        //   * Syncronization occurs using the ParameterDesc
        //     javaTypes and the javaTypes of the Method
        //     parameters.
        //
        // So in each scenario, the ultimate sync'ing occurs
        // using the javaTypes of the ParameterDescs and the
        // javaTypes of the Method parameters.
        //
        // ------------------------------------------------

        // If we're already mapped to a Java method, no need to do anything.
        if (oper.getMethod() != null)
            return;

        // Find the method.  We do this once for each Operation.
        
        Method[] methods = getMethods(implClass);

		Arrays.sort(methods, new Comparator<Method>() {
			@Override
			public int compare(Method m1, Method m2) {
				return m2.toString().compareTo(m1.toString());
			}
		});
        // A place to keep track of possible matches
        Method possibleMatch = null;
        
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];

            if (method2OperationMap == null) {
                method2OperationMap = new HashMap();
            }

            if (Modifier.isPublic(method.getModifiers()) &&
                    method.getName().equals(oper.getName()) &&
                    method2OperationMap.get(method) == null) {

                if (style == Style.MESSAGE) {
                    int messageOperType = checkMessageMethod(method);
                    if(messageOperType == OperationDesc.MSG_METHOD_NONCONFORMING) continue;
                    if (messageOperType == -1) {
                        throw new InternalException("Couldn't match method to any of the allowable message-style patterns!");
                    }
                    oper.setMessageOperationStyle(messageOperType);

                    // Don't bother checking params if we're message style
                    possibleMatch = method;
                    break;
                }

                // Check params
                Class [] paramTypes = method.getParameterTypes();
                if (paramTypes.length != oper.getNumParams())
                    continue;

                int j;
                boolean conversionNecessary = false;
                for (j = 0; j < paramTypes.length; j++) {
                    Class type = paramTypes[j];
                    Class actualType = type;
                    if (Holder.class.isAssignableFrom(type)) {
                        actualType = JavaUtils.getHolderValueType(type);
                    }
                    ParameterDesc param = oper.getParameter(j);
                    QName typeQName = param.getTypeQName();
                    if (typeQName == null) {
                        // No typeQName is available.  Set it using
                        // information from the actual type.
                        // (Scenarios B and D)
                        // There is no need to try and match with
                        // the Method parameter javaType because
                        // the ParameterDesc is being constructed
                        // by introspecting the Method.
                        typeQName = getTypeMapping().getTypeQName(actualType);
                        param.setTypeQName(typeQName);
                    } else {
                        // A type qname is available.
                        // Ensure that the ParameterDesc javaType
                        // is convertable to the Method parameter type
                        //
                        // Use the available javaType (Scenarios C and E)
                        // or get one from the TMR (Scenario A).
                        Class paramClass = param.getJavaType();
                        if (paramClass != null &&
                            JavaUtils.getHolderValueType(paramClass) != null) {
                            paramClass = JavaUtils.getHolderValueType(paramClass);
                        }
                        if (paramClass == null) {
                            paramClass = getTypeMapping().getClassForQName(param.getTypeQName(),
                                                                           null);
                        }

                        if (paramClass != null && paramClass != Object.class) {
                            // This is a match if the paramClass is somehow
                            // convertable to the "real" parameter type.  If not,
                            // break out of this loop.
                            if (!JavaUtils.isConvertable(paramClass, actualType)) {
                                break;
                            }
                            
                            if (!actualType.isAssignableFrom(paramClass)) {
                                // This doesn't fit without conversion
                                conversionNecessary = true;
                            }
                        }
                    }
                    // In all scenarios the ParameterDesc javaType is set to
                    // match the javaType in the corresponding parameter.
                    // This is essential.
                    param.setJavaType(type);
                }

                if (j != paramTypes.length) {
                    // failed.
                    continue;
                }
                
                // This is our latest possibility
                possibleMatch = method;

                // If this is exactly it, stop now.  Otherwise keep looking
                // just in case we find a better match.
                if (!conversionNecessary) {
                    break;
                }

            }
        }

        // At this point, we may or may not have a possible match.
        // FIXME : Should we prefer an exact match from a base class over
        //         a with-conversion match from the target class?  If so,
        //         we'll need to change the logic below.
        if (possibleMatch != null) {
            Class returnClass = possibleMatch.getReturnType();
            oper.setReturnClass(returnClass);
            
            QName returnType = oper.getReturnType();
            if (returnType == null) {
                oper.setReturnType(getTypeMapping().getTypeQName(returnClass));
            }

            // Do the faults
            createFaultMetadata(possibleMatch, oper);
                
            oper.setMethod(possibleMatch);
            method2OperationMap.put(possibleMatch, oper);
            return;
        }

        // Didn't find a match.  Try the superclass, if appropriate
        Class superClass = implClass.getSuperclass();
        if (superClass != null &&
                !superClass.getName().startsWith("java.") &&
                !superClass.getName().startsWith("javax.") &&
                (stopClasses == null ||
                          !stopClasses.contains(superClass.getName()))) {
            syncOperationToClass(oper, superClass);
        }

        // Exception if sync fails to find method for operation
        if (oper.getMethod() == null) {
            InternalException ie =
                new InternalException(Messages.getMessage("serviceDescOperSync00",
                                                           oper.getName(),
                                                           implClass.getName()));
            throw ie;
        }
    }

    private Method[] getMethods(Class implClass) {
        if (implClass.isInterface()){
            // only return methods that are not part of start classes
            List methodsList = new ArrayList();
            Method[] methods = implClass.getMethods();
            if (methods != null) {
                for (int i = 0; i < methods.length; i++) {
                    String declaringClass = methods[i].getDeclaringClass().getName();
                    if (!declaringClass.startsWith("java.") &&
                        !declaringClass.startsWith("javax.")) {
                        methodsList.add(methods[i]);
                    }
                }
            }
            return (Method[])methodsList.toArray(new Method[]{}); 
        } else {
            return implClass.getDeclaredMethods();
        }
    }

    private int checkMessageMethod(Method method) {
        // Collect the types so we know what we're dealing with in the target
        // method.
        Class [] params = method.getParameterTypes();

        if (params.length == 1) {
            if ((params[0] == Element[].class) &&
                    (method.getReturnType() == Element[].class)) {
                return OperationDesc.MSG_METHOD_ELEMENTARRAY;
            }

            if ((params[0] == SOAPBodyElement[].class) &&
                    (method.getReturnType() == SOAPBodyElement[].class)) {
                return OperationDesc.MSG_METHOD_BODYARRAY;
            }

            if ((params[0] == Document.class) &&
                    (method.getReturnType() == Document.class)) {
                return OperationDesc.MSG_METHOD_DOCUMENT;
            }
        } else if (params.length == 2) {
            if (((params[0] == SOAPEnvelope.class) &&
                    (params[1] == SOAPEnvelope.class)) || 
                ((params[0] == javax.xml.soap.SOAPEnvelope.class) &&
                    (params[1] == javax.xml.soap.SOAPEnvelope.class)) &&
                    (method.getReturnType() == void.class)){
                return OperationDesc.MSG_METHOD_SOAPENVELOPE;
            }
        }
        if( null != allowedMethods && !allowedMethods.isEmpty() )
          throw new InternalException (Messages.getMessage("badMsgMethodParams",
                                                         method.getName()));
        return    OperationDesc.MSG_METHOD_NONCONFORMING;                                              
    }

    /**
     * Fill in a service description by introspecting the implementation
     * class.
     */
    public void loadServiceDescByIntrospection()
    {
        loadServiceDescByIntrospection(implClass);

        // Setting this to null means there is nothing more to do, and it
        // avoids future string compares.
        completedNames = null;
    }

    /**
     * Fill in a service description by introspecting the implementation
     * class.
     */
    public void loadServiceDescByIntrospection(Class implClass) {
        if (introspectionComplete || implClass == null) {
            return;
        }

        // set the implementation class for the service description
        this.implClass = implClass;
        if (Skeleton.class.isAssignableFrom(implClass)) {
            isSkeletonClass = true;
            loadSkeletonOperations();
        }

        /** If the class knows what it should be exporting,
        * respect its wishes.
        */
        AxisServiceConfig axisConfig = null;
        try {
            Method method = implClass.getDeclaredMethod(
                    "getAxisServiceConfig", new Class [] {});
            if (method != null && Modifier.isStatic(method.getModifiers())) {
                axisConfig = (AxisServiceConfig)method.invoke(null, null);
            }
        } catch (Exception e) {
            // No problem, just continue without...
        }

        if (axisConfig != null) {
            String allowedMethodsStr = axisConfig.getAllowedMethods();
            if (allowedMethodsStr != null && !"*".equals(allowedMethodsStr)) {
                ArrayList methodList = new ArrayList();
                StringTokenizer tokenizer =
                        new StringTokenizer(allowedMethodsStr, " ,");
                while (tokenizer.hasMoreTokens()) {
                    methodList.add(tokenizer.nextToken());
                }
                setAllowedMethods(methodList);
            }
        }

        loadServiceDescByIntrospectionRecursive(implClass);

        // All operations should now be synchronized.  Check it.
        for (Iterator iterator = operations.iterator(); iterator.hasNext();) {
            OperationDesc operation = (OperationDesc) iterator.next();
            if (operation.getMethod() == null) {
                throw new InternalException(
                        Messages.getMessage("badWSDDOperation",
                                            operation.getName(),
                                            "" + operation.getNumParams()));
            }
        }

        if ((style == Style.MESSAGE) && operations.size() == 1) {
            messageServiceDefaultOp = (OperationDesc)operations.get(0);
        }

        introspectionComplete = true;
    }

    /**
     * Is this method from ServiceLifeCycle interface?
     * @param m
     * @return true if this method is from ServiceLifeCycle interface
     */ 
    private boolean isServiceLifeCycleMethod(Class implClass, Method m) {
        if(javax.xml.rpc.server.ServiceLifecycle.class.isAssignableFrom(implClass)) {
            String methodName = m.getName(); 

            if(methodName.equals("init")) {
                // Check if the method signature is 
                // "public abstract void init(Object context) throws ServiceException;"
                Class[] classes = m.getParameterTypes(); 
                if(classes != null && 
                   classes.length == 1 && 
                   classes[0] == Object.class && 
                   m.getReturnType() == Void.TYPE) {
                    return true;
                }
            } else if (methodName.equals("destroy")){
                // Check if the method signature is 
                // "public abstract void destroy();"
                Class[] classes = m.getParameterTypes(); 
                if(classes != null && 
                   classes.length == 0 &&
                   m.getReturnType() == Void.TYPE) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Recursive helper class for loadServiceDescByIntrospection
     */
    private void loadServiceDescByIntrospectionRecursive(Class implClass)
    {
        if (Skeleton.class.equals(implClass)) {
            return;
        }

        Method [] methods = getMethods(implClass);

        for (int i = 0; i < methods.length; i++) {
            if (Modifier.isPublic(methods[i].getModifiers()) && !isServiceLifeCycleMethod(implClass, methods[i])) {
                getSyncedOperationsForName(implClass, methods[i].getName());
            }
        }

        if (implClass.isInterface()) {
            Class [] superClasses = implClass.getInterfaces();
            for (int i = 0; i < superClasses.length; i++) {
                Class superClass = superClasses[i];
                if (!superClass.getName().startsWith("java.") &&
                        !superClass.getName().startsWith("javax.") &&
                        (stopClasses == null ||
                        !stopClasses.contains(superClass.getName()))) {
                    loadServiceDescByIntrospectionRecursive(superClass);
                }
            }
        } else {
            Class superClass = implClass.getSuperclass();
            if (superClass != null &&
                    !superClass.getName().startsWith("java.") &&
                    !superClass.getName().startsWith("javax.") &&
                    (stopClasses == null ||
                        !stopClasses.contains(superClass.getName()))) {
                loadServiceDescByIntrospectionRecursive(superClass);
            }
        }
    }

    /**
     * Fill in a service description by introspecting the implementation
     * class.  This version takes the implementation class and the in-scope
     * TypeMapping.
     */
    public void loadServiceDescByIntrospection(Class cls, TypeMapping tm)
    {
        // Should we complain if the implClass changes???
        implClass = cls;
        this.tm = tm;

        if (Skeleton.class.isAssignableFrom(implClass)) {
            isSkeletonClass = true;
            loadSkeletonOperations();
        }

        loadServiceDescByIntrospection();
    }

    /**
     * Makes sure we have completely synchronized OperationDescs with
     * the implementation class.
     */
    private void getSyncedOperationsForName(Class implClass, String methodName)
    {
        // If we're a Skeleton deployment, skip the statics.
        if (isSkeletonClass) {
            if (methodName.equals("getOperationDescByName") ||
                methodName.equals("getOperationDescs"))
                return;
        }
        
        // If we have no implementation class, don't worry about it (we're
        // probably on the client)
        if (implClass == null)
            return;

        // If we're done introspecting, or have completed this method, return
        if (completedNames == null || completedNames.contains(methodName))
            return;

        // Skip it if it's not a sanctioned method name
        if ((allowedMethods != null) &&
            !allowedMethods.contains(methodName))
            return;

        if ((disallowedMethods != null) &&
            disallowedMethods.contains(methodName))
            return;

        // If we're a skeleton class, make sure we don't already have any
        // OperationDescs for this name (as that might cause conflicts),
        // then load them up from the Skeleton class.
        if (isSkeletonClass && !haveAllSkeletonMethods) {
            // FIXME : Check for existing ones and fault if found

            if (skelMethod == null) {
                // Grab metadata from the Skeleton for parameter info
                try {
                    skelMethod = implClass.getDeclaredMethod(
                                            "getOperationDescByName",
                                            new Class [] { String.class });
                } catch (NoSuchMethodException e) {
                } catch (SecurityException e) {
                }
                if (skelMethod == null) {
                    // FIXME : Throw an error?
                    return;
                }
            }
            try {
                List skelList =
                        (List)skelMethod.invoke(implClass,
                                new Object [] { methodName });
                if (skelList != null) {
                    Iterator i = skelList.iterator();
                    while (i.hasNext()) {
                        addOperationDesc((OperationDesc)i.next());
                    }
                }
            } catch (IllegalAccessException e) {
                if(log.isDebugEnabled()) {
                    log.debug(Messages.getMessage("exception00"), e);
                }
                return;
            } catch (IllegalArgumentException e) {
                if(log.isDebugEnabled()) {
                    log.debug(Messages.getMessage("exception00"), e);
                }
                return;
            } catch (InvocationTargetException e) {
                if(log.isDebugEnabled()) {
                    log.debug(Messages.getMessage("exception00"), e);
                }
                return;
            }
        }

        // OK, go find any current OperationDescs for this method name and
        // make sure they're synced with the actual class.
        if (name2OperationsMap != null) {
            ArrayList currentOverloads =
                    (ArrayList)name2OperationsMap.get(methodName);
            if (currentOverloads != null) {
                // For each one, sync it to the implementation class' methods
                for (Iterator i = currentOverloads.iterator(); i.hasNext();) {
                    OperationDesc oper = (OperationDesc) i.next();
                    if (oper.getMethod() == null) {
                        syncOperationToClass(oper, implClass);
                    }
                }
            }
        }

        // Now all OperationDescs from deployment data have been completely
        // filled in.  So we now make new OperationDescs for any method
        // overloads which were not covered above.
        // NOTE : This is the "lenient" approach, which allows you to
        // specify one overload and still get the others by introspection.
        // We could equally well return above if we found OperationDescs,
        // and have a rule that if you specify any overloads, you must specify
        // all the ones you want accessible.

        createOperationsForName(implClass, methodName);

        // Note that we never have to look at this method name again.
        completedNames.add(methodName);
    }

    private String getUniqueOperationName(String name) {
        int i = 1;
        String candidate;
        do {
            candidate = name + i++;
        } while (name2OperationsMap.get(candidate) != null);

        return candidate;
    }

    /**
     * Look for methods matching this name, and for each one, create an
     * OperationDesc (if it's not already in our list).
     *
     * TODO: Make this more efficient
     */
    private void createOperationsForName(Class implClass, String methodName)
    {
        // If we're a Skeleton deployment, skip the statics.
        if (isSkeletonClass) {
            if (methodName.equals("getOperationDescByName") ||
                methodName.equals("getOperationDescs"))
                return;
        }
        
        Method [] methods = getMethods(implClass);

        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (Modifier.isPublic(method.getModifiers()) &&
                method.getName().equals(methodName) &&
                !isServiceLifeCycleMethod(implClass, method)) {
                createOperationForMethod(method);
            }
        }

        Class superClass = implClass.getSuperclass();
        if (superClass != null &&
                !superClass.getName().startsWith("java.") &&
                !superClass.getName().startsWith("javax.") &&
                    (stopClasses == null ||
                        !stopClasses.contains(superClass.getName()))) {
            createOperationsForName(superClass, methodName);
        }
    }

    /**
     * Make an OperationDesc from a Java method.
     *
     * In the absence of deployment metadata, this code will introspect a
     * Method and create an appropriate OperationDesc.  If the class
     * implements the Skeleton interface, we will use the metadata from there
     * in constructing the OperationDesc.  If not, we use parameter names
     * from the bytecode debugging info if available, or "in0", "in1", etc.
     * if not.
     */
    private void createOperationForMethod(Method method) {
        // If we've already got it, never mind
        if (method2OperationMap.get(method) != null) {
            return;
        }

        Class [] paramTypes = method.getParameterTypes();

        // And if we've already got an exact match (i.e. an override),
        // never mind

        ArrayList overloads = name2OperationsMap == null ? null :
                (ArrayList)name2OperationsMap.get(method.getName());
        if (overloads != null && !overloads.isEmpty()) {
            // Search each OperationDesc that already has a Method
            // associated with it, and check for parameter type equivalence.
            for (int i = 0; i < overloads.size(); i++) {
                OperationDesc op = (OperationDesc)overloads.get(i);
                Method checkMethod = op.getMethod();
                if (checkMethod != null) {
                    Class [] others = checkMethod.getParameterTypes();
                    if (paramTypes.length == others.length) {
                        int j = 0;
                        for (; j < others.length; j++) {
                            if (!others[j].equals(paramTypes[j]))
                                break;
                        }
                        // If we got all the way through, we have a match.
                        if (j == others.length)
                            return;
                    }
                }
            }
        }

        boolean isWSICompliant = JavaUtils.isTrue(
                AxisProperties.getProperty(Constants.WSIBP11_COMPAT_PROPERTY));
        
        // Make an OperationDesc, fill in common stuff
        OperationDesc operation = new OperationDesc();
        
        // If we're WS-I compliant, we can't have overloaded operation names.
        // If we find duplicates, we generate unique names for them and map
        // those names to the correct Method.
        String name = method.getName();
        if (isWSICompliant && name2OperationsMap != null) {
            Collection methodNames = name2OperationsMap.keySet();
            name = JavaUtils.getUniqueValue(methodNames, name);
        }
        operation.setName(name);
        String defaultNS = "";
        if (namespaceMappings != null && !namespaceMappings.isEmpty()) {
            // If we have a default namespace mapping, require callers to
            // use that namespace.
            defaultNS = (String)namespaceMappings.get(0);
        }
        if(defaultNS.length() == 0) {
            defaultNS = Namespaces.makeNamespace(method.getDeclaringClass().getName());
        }
        operation.setElementQName(new QName(defaultNS, name));
        operation.setMethod(method);

        // If this is a MESSAGE style service, set up the OperationDesc
        // appropriately.
        if (style == Style.MESSAGE) {
            int messageOperType = checkMessageMethod(method);
            if(messageOperType == OperationDesc.MSG_METHOD_NONCONFORMING) return;
            if (messageOperType == -1) {
                throw new InternalException("Couldn't match method to any of the allowable message-style patterns!");
            }
            operation.setMessageOperationStyle(messageOperType);
            operation.setReturnClass(Object.class);
            operation.setReturnType(Constants.XSD_ANYTYPE);
        } else {
            // For other styles, continue here.
            Class retClass = method.getReturnType();
            operation.setReturnClass(retClass);
            QName typeQName = getTypeQName(retClass);
            operation.setReturnType(typeQName);

            String [] paramNames = getParamNames(method);

            for (int k = 0; k < paramTypes.length; k++) {
                Class type = paramTypes[k];
                ParameterDesc paramDesc = new ParameterDesc();
                // param should be unqualified if we're using rpc style,
                // or should use the operation's namespace if its document style
                String paramNamespace = (this.style == Style.RPC ? "" : operation.getElementQName().getNamespaceURI());

                // If we have a name for this param, use it, otherwise call
                // it "in*"
                if (paramNames != null && paramNames[k] != null &&
                        paramNames[k].length()>0) {
                    paramDesc.setQName(new QName(paramNamespace, paramNames[k]));
                } else {
                    paramDesc.setQName(new QName(paramNamespace, "in" + k));
                }

                // If it's a Holder, mark it INOUT, and set the XML type QName
                // to the held type.  Otherwise it's IN.

                Class heldClass = JavaUtils.getHolderValueType(type);
                if (heldClass != null) {
                    paramDesc.setMode(ParameterDesc.INOUT);
                    paramDesc.setTypeQName(getTypeQName(heldClass));
                } else {
                    paramDesc.setMode(ParameterDesc.IN);
                    paramDesc.setTypeQName(getTypeQName(type));
                }
                paramDesc.setJavaType(type);
                operation.addParameter(paramDesc);
            }
        }

        createFaultMetadata(method, operation);

        addOperationDesc(operation);
        method2OperationMap.put(method, operation);
    }

    private QName getTypeQName(Class javaClass) {
        QName typeQName;
        TypeMapping tm = getTypeMapping();
        if (style == Style.RPC) {
            typeQName = tm.getTypeQName(javaClass);
        } else {
            typeQName = tm.getTypeQNameExact(javaClass);
            if (typeQName == null && javaClass.isArray()) {
                typeQName = tm.getTypeQName(javaClass.getComponentType());
            } else {
                typeQName = tm.getTypeQName(javaClass);
            }
        }
        return typeQName;
    }

    private void createFaultMetadata(Method method, OperationDesc operation) {
        // Create Exception Types
        Class[] exceptionTypes = method.getExceptionTypes();

        for (int i=0; i < exceptionTypes.length; i++) {
            // Every remote method declares a java.rmi.RemoteException
            // Only interested in application specific exceptions.
            // Ignore java and javax package exceptions.
            Class ex = exceptionTypes[i];
            if (ex != java.rmi.RemoteException.class &&
                ex != org.apache.axis.AxisFault.class &&
                !ex.getName().startsWith("java.") &&
                !ex.getName().startsWith("javax.")) {

                // For JSR 101 v.1.0, there is a simple fault mapping
                // and a complexType fault mapping...both mappings
                // generate a class that extends (directly or indirectly)
                // Exception.
                // When converting java back to wsdl it is not possible
                // to determine which way to do the mapping,
                // so it is always mapped back using the complexType
                // fault mapping because it is more useful (i.e. it
                // establishes a hierarchy of exceptions).  Note that this
                // will not cause any roundtripping problems.
                // Rich


                /* Old Simple Type Mode
                Field[] f = ex.getDeclaredFields();
                ArrayList exceptionParams = new ArrayList();
                for (int j = 0; j < f.length; j++) {
                    int mod = f[j].getModifiers();
                    if (Modifier.isPublic(mod) &&
                         !Modifier.isStatic(mod)) {
                        QName qname = new QName("", f[j].getName());
                        QName typeQName = tm.getTypeQName(f[j].getType());
                        ParameterDesc param = new ParameterDesc(qname,
                                                                ParameterDesc.IN,
                                                                typeQName);
                        param.setJavaType(f[j].getType());
                        exceptionParams.add(param);
                    }
                }
                String pkgAndClsName = ex.getName();
                FaultDesc fault = new FaultDesc();
                fault.setName(pkgAndClsName);
                fault.setParameters(exceptionParams);
                operation.addFault(fault);
                */

                FaultDesc fault = operation.getFaultByClass(ex, false);
                boolean isNew;
                
                // If we didn't find one, create a new one
                if (fault == null) {
                    fault = new FaultDesc();
                    isNew = true;
                } else {
                    isNew = false;
                }
                
                // Try to fil in any parts of the faultDesc that aren't there
                
                // XMLType
                QName xmlType = fault.getXmlType();
                if (xmlType == null) {
                    fault.setXmlType(getTypeMapping().getTypeQName(ex));
                }
                
                // Name and Class Name
                String pkgAndClsName = ex.getName();
                if (fault.getClassName() == null) {
                    fault.setClassName(pkgAndClsName);
                }
                if (fault.getName() == null) {
                    String name = pkgAndClsName.substring(
                            pkgAndClsName.lastIndexOf('.') + 1,
                            pkgAndClsName.length());
                    fault.setName(name);
                }
                
                // Parameters
                // We add a single parameter which points to the type
                if (fault.getParameters() == null) {
                    if (xmlType == null) {
                        xmlType = getTypeMapping().getTypeQName(ex);
                    }
                    QName qname = fault.getQName();
                    if (qname == null) {
                        qname = new QName("", "fault");
                    }
                    ParameterDesc param = new ParameterDesc(
                            qname,
                            ParameterDesc.IN,
                            xmlType);
                    param.setJavaType(ex);
                    ArrayList exceptionParams = new ArrayList();
                    exceptionParams.add(param);
                    fault.setParameters(exceptionParams);
                }
                
                // QName
                if (fault.getQName() == null) {
                    fault.setQName(new QName(pkgAndClsName));
                }

                if (isNew) {
                    // Add the fault to the operation
                    operation.addFault(fault);
                }
            }
        }
    }

    private String[] getParamNames(Method method) {
        synchronized (method2ParamsMap) {
            String [] paramNames = (String []) method2ParamsMap.get(method);
            if(paramNames != null)
                return paramNames;
            paramNames = ParamNameExtractor.getParameterNamesFromDebugInfo(method);
            method2ParamsMap.put(method, paramNames);
            return paramNames;
        }
    }

    public void setNamespaceMappings(List namespaces) {
        namespaceMappings = namespaces;
    }

    public String getDefaultNamespace() {
        if (namespaceMappings == null || namespaceMappings.isEmpty())
            return null;
        return (String)namespaceMappings.get(0);
    }

    public void setDefaultNamespace(String namespace) {
        if (namespaceMappings == null)
            namespaceMappings = new ArrayList();
        namespaceMappings.add(0, namespace);
    }

    public void setProperty(String name, Object value) {
        if (properties == null) {
            properties = new HashMap();
        }
        properties.put(name, value);
    }

    public Object getProperty(String name) {
        if (properties == null)
            return null;

        return properties.get(name);
    }

    public String getEndpointURL() {
        return endpointURL;
    }

    public void setEndpointURL(String endpointURL) {
        this.endpointURL = endpointURL;
    }

    public TypeMappingRegistry getTypeMappingRegistry() {
        if (tmr == null) {
            tmr = new TypeMappingRegistryImpl(false);
        }
        return tmr;
    }

    public void setTypeMappingRegistry(TypeMappingRegistry tmr) {
        this.tmr = tmr;
    }

    public boolean isInitialized() {
        return implClass != null;
    }
}
/* @generated */
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

package org.apache.axis.encoding.ser;

import org.apache.axis.components.logger.LogFactory;
import org.apache.axis.encoding.DeserializationContext;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.DeserializerImpl;
import org.apache.axis.encoding.DeserializerTarget;
import org.apache.axis.message.SOAPHandler;
import org.apache.axis.utils.Messages;
import org.apache.commons.logging.Log;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Map;

import org.apache.axis.soap.SOAPConstants;

/*
 * A <code>MapSerializer</code> is be used to deserialize
 * deserialize Maps using the <code>SOAP-ENC</code>
 * encoding style.<p>
 * 
 * @author Glen Daniels (gdaniels@apache.org)
 * Modified by @author Rich scheuerle <scheu@us.ibm.com>
 * Modified by @author Igor Spasic <igor.spasic@liferay.com>
 */
public class MapDeserializer extends DeserializerImpl {

    protected static Log log =
        LogFactory.getLog(MapDeserializer.class.getName());

    // Fixed objects to act as hints to the set() callback
    public static final Object KEYHINT = new Object();
    public static final Object VALHINT = new Object();
    public static final Object NILHINT = new Object();        


    /**
     * This method is invoked after startElement when the element requires
     * deserialization (i.e. the element is not an href and the value is not nil.)
     * 
     * Simply creates map.
     * @param namespace is the namespace of the element
     * @param localName is the name of the element
     * @param prefix is the prefix of the element
     * @param attributes are the attributes on the element...used to get the type
     * @param context is the DeserializationContext
     */
    public void onStartElement(String namespace, String localName,
                               String prefix, Attributes attributes,
                               DeserializationContext context)
        throws SAXException {
        if (log.isDebugEnabled()) {
            log.debug("Enter MapDeserializer::startElement()");
        }
        
        if (context.isNil(attributes)) {
            return;
        }
        
        // Create a hashmap to hold the deserialized values.
        setValue(new HashMap());
        
        if (log.isDebugEnabled()) {
            log.debug("Exit: MapDeserializer::startElement()");
        }
    }
    
    /**
     * onStartChild is called on each child element.
     *
     * @param namespace is the namespace of the child element
     * @param localName is the local name of the child element
     * @param prefix is the prefix used on the name of the child element
     * @param attributes are the attributes of the child element
     * @param context is the deserialization context.
     * @return is a Deserializer to use to deserialize a child (must be
     * a derived class of SOAPHandler) or null if no deserialization should
     * be performed.
     */
    public SOAPHandler onStartChild(String namespace,
                                    String localName,
                                    String prefix,
                                    Attributes attributes,
                                    DeserializationContext context)
        throws SAXException {

        if (log.isDebugEnabled()) {
            log.debug("Enter: MapDeserializer::onStartChild()");
        }

        if(localName.equals("item")) {

            // liferay - changes started
            // check if 'href' attribute is present. if so, process it
            // with BigItemHandler (see bellow)
            SOAPConstants soapConstants = context.getSOAPConstants();
            String href = attributes.getValue(soapConstants.getAttrHref());

            if (href != null) {
                Object ref = context.getObjectByRef(href);

				BigItemHandler handler = new BigItemHandler(this);

				addChildDeserializer(handler);

				return handler;
			}
			// liferay - end of changes

			ItemHandler handler = new ItemHandler(this);

			// This item must be complete before we're complete...
			addChildDeserializer(handler);

			if (log.isDebugEnabled()) {
				log.debug("Exit: MapDeserializer::onStartChild()");
			}

			return handler;
        }
        
        return this;
    }
    
    /**
     * The registerValueTarget code above causes this set function to be invoked when
     * each value is known.
     * @param value is the value of an element
     * @param hint is the key
     */
    public void setChildValue(Object value, Object hint) throws SAXException
    {
        if (log.isDebugEnabled()) {
            log.debug(Messages.getMessage("gotValue00", "MapDeserializer", "" + value));
        }
        ((Map)this.value).put(hint, value);
    }
    
    /**
     * A deserializer for an <item>.  Handles getting the key and
     * value objects from their own deserializers, and then putting
     * the values into the HashMap we're building.
     * 
     */
    class ItemHandler extends DeserializerImpl {
        Object key;
        Object myValue;
        int numSet = 0;
        MapDeserializer md = null;

        ItemHandler(MapDeserializer md) {
            this.md = md;
        }
        /** 
         * Callback from our deserializers.  The hint indicates
         * whether the passed "val" argument is the key or the value
         * for this mapping.
         */
        public void setChildValue(Object val, Object hint) throws SAXException 
        {
            if (hint == KEYHINT) {
                key = val;
            } else if (hint == VALHINT) {
                myValue = val;
            } else if (hint != NILHINT) {
                return;
            }
            numSet++;
            if (numSet == 2)
                md.setChildValue(myValue, key);
        }
        
        public SOAPHandler onStartChild(String namespace,
                                    String localName,
                                    String prefix,
                                    Attributes attributes,
                                    DeserializationContext context)
        throws SAXException
        {
            QName typeQName = context.getTypeFromAttributes(namespace,
                                                            localName,
                                                            attributes);
            Deserializer dser = context.getDeserializerForType(typeQName);

            // If no deserializer, use the base DeserializerImpl.
            if (dser == null)
                dser = new DeserializerImpl();

            // When the child value is ready, we
            // want our set method to be invoked.
            // To do this register a DeserializeTarget on the
            // new Deserializer.
            DeserializerTarget dt = null;
            if (context.isNil(attributes)) {
                dt = new DeserializerTarget(this, NILHINT);
            } else if (localName.equals("key")) {
                dt = new DeserializerTarget(this, KEYHINT);
            } else if (localName.equals("value")) {
                dt = new DeserializerTarget(this, VALHINT);
            } else {
                // Do nothing
            }
            if (dt != null) {
                dser.registerValueTarget(dt);
            }
            
            // We need this guy to complete for us to complete.
            addChildDeserializer(dser);
            
            return (SOAPHandler)dser;
        }
    }

    // liferay - new class
    // Handles simple items array, where each element has a href
    // to actual key/value pair
    class BigItemHandler extends DeserializerImpl {
        Object key;
        Object myValue;
        int numSet = 0;
        MapDeserializer md = null;

        BigItemHandler(MapDeserializer md) {
            this.md = md;
        }

        public SOAPHandler onStartChild(String namespace,
                                    String localName,
                                    String prefix,
                                    Attributes attributes,
                                    DeserializationContext context)
        throws SAXException
        {
            QName typeQName = context.getTypeFromAttributes(namespace,
                                                            localName,
                                                            attributes);
            Deserializer dser = context.getDeserializerForType(typeQName);

            if (dser == null)
                dser = new DeserializerImpl();


			SOAPConstants soapConstants = context.getSOAPConstants();
   			String href = attributes.getValue(soapConstants.getAttrHref());

			if (href != null) {
				Object ref = context.getObjectByRef(href);

				ItemHandler handler = new ItemHandler(md);
				
                addChildDeserializer(handler);

				return handler;
			}

            addChildDeserializer(dser);
            return (SOAPHandler)dser;
        }
    }
}
/* @generated */
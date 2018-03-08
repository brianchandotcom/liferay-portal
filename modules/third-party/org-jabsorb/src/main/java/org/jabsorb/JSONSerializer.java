/*
 * jabsorb - a Java to JavaScript Advanced Object Request Broker
 * http://www.jabsorb.org
 *
 * Copyright 2007-2009 The jabsorb team
 *
 * based on original code from
 * JSON-RPC-Java - a JSON-RPC to Java Bridge with dynamic invocation
 *
 * Copyright Metaparadigm Pte. Ltd. 2004.
 * Michael Clark <michael@metaparadigm.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.jabsorb;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.jabsorb.serializer.MarshallException;
import org.jabsorb.serializer.ObjectMatch;
import org.jabsorb.serializer.ProcessedObject;
import org.jabsorb.serializer.Serializer;
import org.jabsorb.serializer.SerializerState;
import org.jabsorb.serializer.UnmarshallException;
import org.jabsorb.serializer.impl.ArraySerializer;
import org.jabsorb.serializer.impl.BeanSerializer;
import org.jabsorb.serializer.impl.BooleanSerializer;
import org.jabsorb.serializer.impl.DateSerializer;
import org.jabsorb.serializer.impl.DictionarySerializer;
import org.jabsorb.serializer.impl.ListSerializer;
import org.jabsorb.serializer.impl.MapSerializer;
import org.jabsorb.serializer.impl.NumberSerializer;
import org.jabsorb.serializer.impl.PrimitiveSerializer;
import org.jabsorb.serializer.impl.RawJSONArraySerializer;
import org.jabsorb.serializer.impl.RawJSONObjectSerializer;
import org.jabsorb.serializer.impl.SetSerializer;
import org.jabsorb.serializer.impl.StringSerializer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is the public entry point to the serialization code and provides
 * methods for marshalling Java objects into JSON objects and unmarshalling JSON
 * objects into Java objects.
 */
public class JSONSerializer implements Serializable
{
  /**
   * Unique serialisation id.
   */
  private final static long serialVersionUID = 2;

  /**
   * The logger for this class
   */
  private final static Logger log = LoggerFactory
      .getLogger(JSONSerializer.class);

  /**
   * Key: Serializer
   */
  private Set serializerSet = new HashSet();

  /**
   * key: Class, value: Serializer
   */
  private transient Map serializableMap = null;

  /**
   * List for reverse registration order search
   */
  private List serializerList = new ArrayList();

  /**
   * Should serializers defined in this object include the fully qualified class
   * name of objects being serialized? This can be helpful when unmarshalling,
   * though if not needed can be left out in favor of increased performance and
   * smaller size of marshalled String.
   */
  private boolean marshallClassHints = true;

  /**
   * Should attributes will null values still be included in the serialized JSON
   * object.
   */
  private boolean marshallNullAttributes = true;

  /**
   * Are FixUps are generated to handle circular references found during
   * marshalling?  If false, an exception is thrown if a circular reference
   * is found during serialization.
   */
  private boolean fixupCircRefs = true;

  /**
   * Are FixUps are generated for duplicate objects found during marshalling?
   * If false, the duplicates are re-serialized.
   */
  private boolean fixupDuplicates = true;

  /**
   * Are FixUps are generated for primitive objects (classes of type String,
   * Boolean, Integer, Boolean, Long, Byte, Double, Float and Short)
   * This flag will have no effect if fixupDuplicates is false.
   */
  private boolean fixupDuplicatePrimitives = false;

  /**
   * The list of class types that are considered primitives
   * that should not be fixed up when fixupDuplicatePrimitives is false.
   */
  protected static Class[] duplicatePrimitiveTypes =
  {
    String.class, Integer.class, Boolean.class, Long.class,
    Byte.class, Double.class, Float.class, Short.class
  };

  /**
   * Determine if this serializer considers the given Object to be a primitive
   * wrapper type Object.  This is used to determine which types of Objects
   * should be fixed up as duplicates if the fixupDuplicatePrimitives flag
   * is false.
   *
   * @param o Object to test for primitive.
   */
  public boolean isPrimitive(Object o)
  {
    if (o == null)
    {
      return true;  // extra safety check- null is considered primitive too
    }

    Class c = o.getClass();

    for (int i=0,j=duplicatePrimitiveTypes.length; i<j; i++)
    {
      if (duplicatePrimitiveTypes[i] == c)
      {
        return true;
      }
    }
    return false;
  }

  /**
   * Get the fixupCircRefs flag.  If true, FixUps are generated to handle circular
   * references found during marshalling.  If false, an exception is thrown if a
   * circular reference is found during serialization.
   *
   * @return the fixupCircRefs flag.
   */
  public boolean getFixupCircRefs()
  {
    return fixupCircRefs;
  }

  /**
   * Set the fixupCircRefs flag.  If true, FixUps are generated to handle circular
   * references found during marshalling.  If false, an exception is thrown if a
   * circular reference is found during serialization.
   *
   * @param fixupCircRefs  the fixupCircRefs flag.
   */
  public void setFixupCircRefs(boolean fixupCircRefs)
  {
    this.fixupCircRefs = fixupCircRefs;
  }

  /**
   * Get the fixupDuplicates flag.  If true, FixUps are generated for duplicate
   * objects found during marshalling. If false, the duplicates are re-serialized.
   *
   * @return the fixupDuplicates flag.
   */
  public boolean getFixupDuplicates()
  {
    return fixupDuplicates;
  }

  /**
   * Set the fixupDuplicates flag.  If true, FixUps are generated for duplicate
   * objects found during marshalling. If false, the duplicates are re-serialized.
   *
   * @param fixupDuplicates the fixupDuplicates flag.
   */
  public void setFixupDuplicates(boolean fixupDuplicates)
  {
    this.fixupDuplicates = fixupDuplicates;
  }

  /**
   * Get the fixupDuplicatePrimitives flag.  If true (and fixupDuplicates is
   * also true), FixUps are generated for duplicate primitive objects found
   * during marshalling.  If false, the duplicates are re-serialized.
   *
   * @return the fixupDuplicatePrimitives flag.
   */
  public boolean getFixupDuplicatePrimitives()
  {
    return fixupDuplicatePrimitives;
  }

  /**
   * Set the fixupDuplicatePrimitives flag.  If true (and fixupDuplicates is
   * also true), FixUps are generated for duplicate primitive objects found
   * during marshalling. If false, the duplicates are re-serialized.
   *
   * @param fixupDuplicatePrimitives the fixupDuplicatePrimitives flag.
   */
  public void setFixupDuplicatePrimitives(boolean fixupDuplicatePrimitives)
  {
    this.fixupDuplicatePrimitives = fixupDuplicatePrimitives;
  }

  /**
   * Convert a string in JSON format into Java objects.
   *
   * @param jsonString The JSON format string.
   * @return An object (or tree of objects) representing the data in the JSON
   *         format string.
   * @throws UnmarshallException If unmarshalling fails
   */
  public Object fromJSON(String jsonString) throws UnmarshallException
  {
    JSONTokener tok = new JSONTokener(jsonString);
    Object json;
    try
    {
      json = tok.nextValue();
    }
    catch (JSONException e)
    {
      throw new UnmarshallException("couldn't parse JSON", e);
    }
    SerializerState state = new SerializerState();
    return unmarshall(state, null, json);
  }

  /**
   * Should serializers defined in this object include the fully qualified class
   * name of objects being serialized? This can be helpful when unmarshalling,
   * though if not needed can be left out in favor of increased performance and
   * smaller size of marshalled String. Default is true.
   *
   * @return whether Java Class hints are included in the serialised JSON
   *         objects
   */
  public boolean getMarshallClassHints()
  {
    return marshallClassHints;
  }

  /**
   * Returns true if attributes will null values should still be included in the
   * serialized JSON object. Defaults to true. Set to false for performance
   * gains and small JSON serialized size. Useful because null and undefined for
   * JSON object attributes is virtually the same thing.
   *
   * @return boolean value as to whether null attributes will be in the
   *         serialized JSON objects
   */
  public boolean getMarshallNullAttributes()
  {
    return marshallNullAttributes;
  }

  /**
   * Special token Object to indicate the fact that the given object being
   * marshalled is a duplicate or circular reference and so it should not
   * be placed into the json stream.
   */
  public static final Object CIRC_REF_OR_DUPLICATE = new Object();

  /**
   * Marshall java into an equivalent json representation (JSONObject or
   * JSONArray.) <p/> This involves finding the correct Serializer for the class
   * of the given java object and then invoking it to marshall the java object
   * into json. <p/> The Serializer will invoke this method recursively while
   * marshalling complex object graphs.
   *
   * @param state can be used by the underlying Serializer objects to hold state
   *          while marshalling.
   *
   * @param parent parent object of the object being converted.  this can be null if
   *               it's the root object being converted.
   * @param java java object to convert into json.
   *
   * @param ref reference within the parent's point of view of the object being serialized.
   *            this will be a String for JSONObjects and an Integer for JSONArrays.
   *
   * @return the JSONObject or JSONArray (or primitive object) containing the json
   *         for the marshalled java object or the special token Object,
   *         JSONSerializer.CIRC_REF_OR_DUP to indicate to the caller that the
   *         given Object has already been serialized and so therefore the result
   *         should be ignored.
   *
   * @throws MarshallException if there is a problem marshalling java to json.
   */
  public Object marshall(SerializerState state, Object parent, Object java, Object ref)
      throws MarshallException
  {
    if (java == null)
    {
      if (log.isDebugEnabled())
      {
        log.debug("marshall null");
      }
      return JSONObject.NULL;
    }

    // check for duplicate objects or circular references
    ProcessedObject p = state.getProcessedObject(java);

    // if this object hasn't been seen before, mark it as seen and continue forth
    if (p == null)
    {
      state.push(parent, java, ref);
    }
    else
    {
      //todo: make test cases to explicitly handle all 4 combinations of the 2 option
      //todo: settings (both on the client and server)

      // handle throwing of circular reference exception and/or serializing duplicates, depending
      // on the options set in the serializer!
      boolean foundCircRef = state.isAncestor(p, parent);

      // throw an exception if a circular reference found, and the
      // serializer option is not set to fixup these circular references
      if (!fixupCircRefs && foundCircRef)
      {
        throw new MarshallException("Circular Reference");
      }

      // if its a duplicate only, and we aren't fixing up duplicates or if
      // it is a primitive, and fixing up of primitives is not allowed then
      // re-serialize the object into the json.
      if (!foundCircRef  &&
         (!fixupDuplicates || (!fixupDuplicatePrimitives && isPrimitive(java))))
      {
        //todo: if a duplicate is being reserialized... it will overwrite the original location of the
        //todo: first one found... need to think about the ramifications of this -- optimally, circ refs found
        //todo: underneath duplicates need to point to the "original" one found, but they also need to be fixed
        //todo: up to the correct location, of course.
        state.push(parent, java, ref);
      }
      else
      {
        // generate a fix up entry for the duplicate/circular reference
        state.addFixUp(p.getLocation(), ref);
        return CIRC_REF_OR_DUPLICATE;
      }
    }

    try
    {
      if (log.isDebugEnabled())
      {
        log.debug("marshall class " + java.getClass().getName());
      }
      Serializer s = getSerializer(java.getClass(), null);
      if (s != null)
      {
        return s.marshall(state, parent, java);
      }
      throw new MarshallException("can't marshall " + java.getClass().getName());
    }
    finally
    {
      state.pop();
    }
  }

  /**
   * Register all of the provided standard serializers.
   *
   * @throws Exception If a serialiser has already been registered for a class.
   *
   * TODO: Should this be thrown: This can only happen if there is an internal
   * problem with the code
   */
  public void registerDefaultSerializers() throws Exception
  {

    // the order of registration is important:
    // when trying to marshall java objects into json, first,
    // a direct match (by Class) is looked for in the serializeableMap
    // if a direct match is not found, all serializers are
    // searched in the reverse order that they were registered here (via the
    // serializerList)
    // for the first serializer that canSerialize the java class type.

    registerSerializer(new RawJSONArraySerializer());
    registerSerializer(new RawJSONObjectSerializer());
    registerSerializer(new BeanSerializer());
    registerSerializer(new ArraySerializer());
    registerSerializer(new DictionarySerializer());
    registerSerializer(new MapSerializer());
    registerSerializer(new SetSerializer());
    registerSerializer(new ListSerializer());
    registerSerializer(new DateSerializer());
    registerSerializer(new StringSerializer());
    registerSerializer(new NumberSerializer());
    registerSerializer(new BooleanSerializer());
    registerSerializer(new PrimitiveSerializer());
  }

  /**
   * Register a new type specific serializer. The order of registration is
   * important. More specific serializers should be added after less specific
   * serializers. This is because when the JSONSerializer is trying to find a
   * serializer, if it can't find the serializer by a direct match, it will
   * search for a serializer in the reverse order that they were registered.
   *
   * @param s A class implementing the Serializer interface (usually derived
   *          from AbstractSerializer).
   */
  public void registerSerializer(Serializer s)
  {
    Class classes[] = s.getSerializableClasses();
    synchronized (serializerSet)
    {
      if (serializableMap == null)
      {
        serializableMap = new HashMap();
      }
      if (!serializerSet.contains(s))
      {
        if (log.isDebugEnabled())
        {
          log.debug("registered serializer " + s.getClass().getName());
        }
        s.setOwner(this);
        serializerSet.add(s);
        serializerList.add(0, s);
        for (int j = 0; j < classes.length; j++)
        {
          serializableMap.put(classes[j], s);
        }
      }
    }
  }

  /**
   * Should serializers defined in this object include the fully qualified class
   * name of objects being serialized? This can be helpful when unmarshalling,
   * though if not needed can be left out in favor of increased performance and
   * smaller size of marshalled String. Default is true.
   *
   * @param marshallClassHints flag to enable/disable inclusion of Java class
   *          hints in the serialized JSON objects
   */
  public void setMarshallClassHints(boolean marshallClassHints)
  {
    this.marshallClassHints = marshallClassHints;
  }

  /**
   * Returns true if attributes will null values should still be included in the
   * serialized JSON object. Defaults to true. Set to false for performance
   * gains and small JSON serialized size. Useful because null and undefined for
   * JSON object attributes is virtually the same thing.
   *
   * @param marshallNullAttributes flag to enable/disable marshalling of null
   *          attributes in the serialized JSON objects
   */
  public void setMarshallNullAttributes(boolean marshallNullAttributes)
  {
    this.marshallNullAttributes = marshallNullAttributes;
  }

  /**
   * Convert a Java objects (or tree of Java objects) into a string in JSON
   * format.  Note that this method will remove any circular references / duplicates
   * and not handle the potential fixups that could be generated.  (unless duplicates/circular
   * references are turned off.
   *
   * todo: have some way to transmit the fixups back to the caller of this method.
   *
   * @param obj the object to be converted to JSON.
   * @return the JSON format string representing the data in the the Java
   *         object.
   * @throws MarshallException If marshalling fails.
   */
  public String toJSON(Object obj) throws MarshallException
  {
    SerializerState state = new SerializerState();

    // todo: what do we do about fix ups here?
    Object json = marshall(state, null, obj, "result");

    // todo: fixups will be in state.getFixUps() if someone wants to do something with them...
    return json.toString();
  }

  /**
   * <p>
   * Determine if a given JSON object matches a given class type, and to what
   * degree it matches.  An ObjectMatch instance is returned which contains a
   * number indicating the number of fields that did not match.  Therefore when a given
   * parameter could potentially match in more that one way, this is a metric
   * to compare these ObjectMatches to determine which one matches more closely.
   * </p><p>
   * This is only used when there are overloaded method names that are being called
   * from JSON-RPC to determine which call signature the method call matches most
   * closely and therefore which method is the intended target method to call.
   * </p>
   * @param state used by the underlying Serializer objects to hold state
   *          while unmarshalling for detecting circular references and duplicates.
   *
   * @param clazz optional java class to unmarshall to- if set to null then it
   *        will be looked for via the javaClass hinting mechanism.
   *
   * @param json JSONObject or JSONArray or primitive Object wrapper that contains the json to unmarshall.
   *
   * @return an ObjectMatch indicating the degree to which the object matched the class,
   * @throws UnmarshallException if getClassFromHint() fails
   */
  public ObjectMatch tryUnmarshall(SerializerState state, Class clazz,
      Object json) throws UnmarshallException
  {
    // check for duplicate objects or circular references
    ProcessedObject p = state.getProcessedObject(json);

    // if this object hasn't been seen before, mark it as seen and continue forth

    if (p == null)
    {
      p = state.store(json);
    }
    else
    {
      // get original serialized version
      // to recreate circular reference / duplicate object on the java side
      return (ObjectMatch) p.getSerialized();
    }

    /*
     * If we have a JSON object class hint that is a sub class of the signature
     * 'clazz', then override 'clazz' with the hint class.
     */
    if (clazz != null && json instanceof JSONObject
        && ((JSONObject) json).has("javaClass")
        && clazz.isAssignableFrom(getClassFromHint(json)))
    {
      clazz = getClassFromHint(json);
    }

    if (clazz == null)
    {
      clazz = getClassFromHint(json);
    }
    if (clazz == null)
    {
      throw new UnmarshallException("no class hint");
    }
    if (json == null || json == JSONObject.NULL)
    {
      if (!clazz.isPrimitive())
      {
        return ObjectMatch.NULL;
      }

      throw new UnmarshallException("can't assign null primitive");

    }
    Serializer s = getSerializer(clazz, json.getClass());
    if (s != null)
    {
      return s.tryUnmarshall(state, clazz, json);
    }
    // As a last resort, we check if the object is in fact an instance of the
    // desired class. This will typically happen when the parameter is of
    // type java.lang.Object and the passed object is a String or an Integer
    // that is passed verbatim by JSON
    if(clazz.isInstance(json))
    {
      return ObjectMatch.SIMILAR;
    }

    throw new UnmarshallException("no match");
  }

  /**
   * Unmarshall json into an equivalent java object. <p/> This involves finding
   * the correct Serializer to use and then delegating to that Serializer to
   * unmarshall for us. This method will be invoked recursively as Serializers
   * unmarshall complex object graphs.
   *
   * @param state used by the underlying Serializer objects to hold state
   *          while unmarshalling for detecting circular references and duplicates.
   *
   * @param clazz optional java class to unmarshall to- if set to null then it
   *          will be looked for via the javaClass hinting mechanism.
   *
   * @param json JSONObject or JSONArray or primitive Object wrapper that contains the json to unmarshall.
   *
   * @return the java object representing the json that was unmarshalled.
   *
   * @throws UnmarshallException if there is a problem unmarshalling json to
   *           java.
   */
  public Object unmarshall(SerializerState state, Class clazz, Object json)
      throws UnmarshallException
  {
    // check for duplicate objects or circular references
    ProcessedObject p = state.getProcessedObject(json);

    // if this object hasn't been seen before, mark it as seen and continue forth

    if (p == null)
    {
      p = state.store(json);
    }
    else
    {
      // get original serialized version
      // to recreate circular reference / duplicate object on the java side
      return p.getSerialized();
    }

    // If we have a JSON object class hint that is a sub class of the
    // signature 'clazz', then override 'clazz' with the hint class.
    if (clazz != null && json instanceof JSONObject
        && ((JSONObject) json).has("javaClass")
        && clazz.isAssignableFrom(getClassFromHint(json)))
    {
      clazz = getClassFromHint(json);
    }

    // if no clazz type was passed in, look for the javaClass hint
    if (clazz == null)
    {
      clazz = getClassFromHint(json);
    }

    if (clazz == null)
    {
      throw new UnmarshallException("no class hint");
    }
    if (json == null || json == JSONObject.NULL)
    {
      if (!clazz.isPrimitive())
      {
        return null;
      }

      throw new UnmarshallException("can't assign null primitive");
    }
    Class jsonClass = json.getClass();
    Serializer s = getSerializer(clazz, jsonClass);
    if (s != null)
    {
      return s.unmarshall(state, clazz, json);
    }

    // As a last resort, we check if the object is in fact an instance of the
    // desired class. This will typically happen when the parameter is of
    // type java.lang.Object and the passed object is a String or an Integer
    // that is passed verbatim by JSON
    if(clazz.isInstance(json))
    {
      return json;
    }


    throw new UnmarshallException("no serializer found that can unmarshall " +
      (jsonClass!=null?jsonClass.getName():"null") + " to " +  clazz.getName());
  }

  /**
   * Find the corresponding java Class type from json (as represented by a
   * JSONObject or JSONArray,) using the javaClass hinting mechanism. <p/> If
   * the Object is a JSONObject, the simple javaClass property is looked for. If
   * it is a JSONArray then this method is invoked recursively on the first
   * element of the array. <p/> then the Class is returned as an array type for
   * the type of class hinted by the first Object in the array. <p/> If the
   * object is neither a JSONObject or JSONArray, return the Class of the object
   * directly. (this implies a primitive type, such as String, Integer or
   * Boolean)
   *
   * @param o a JSONObject or JSONArray object to get the Class type from the
   *          javaClass hint.
   * @return the Class of javaClass hint found, or null if the passed in Object
   *         is null, or the Class of the Object passed in, if that object is
   *         not a JSONArray or JSONObject.
   * @throws UnmarshallException if javaClass hint was not found (except for
   *           null case or primitive object case), or the javaClass hint is not
   *           a valid java class. <p/> todo: the name of this method is a bit
   *           misleading because it doesn't actually get the class from todo:
   *           the javaClass hint if the type of Object passed in is not
   *           JSONObject|JSONArray.
   */
  protected Class getClassFromHint(Object o) throws UnmarshallException
  {
    if (o == null)
    {
      return null;
    }
    if (o instanceof JSONObject)
    {
      String className = "(unknown)";
      try
      {
        className = ((JSONObject) o).getString("javaClass");
        return Class.forName(className);
      }
      catch (Exception e)
      {
        throw new UnmarshallException("Class specified in javaClass hint not found: " + className, e);
      }
    }
    if (o instanceof JSONArray)
    {
      JSONArray arr = (JSONArray) o;
      if (arr.length() == 0)
      {
        // assume Object array (best guess)
        return Object[].class;
      }
      // return type of first element
      Class compClazz;
      try
      {
        compClazz = getClassFromHint(arr.get(0));
      }
      catch (JSONException e)
      {
        throw (NoSuchElementException) new NoSuchElementException(e.getMessage()).initCause(e);
      }
      try
      {
        if (compClazz.isArray())
        {
          return Class.forName("[" + compClazz.getName());
        }
        return Class.forName("[L" + compClazz.getName() + ";");
      }
      catch (ClassNotFoundException e)
      {
        throw new UnmarshallException("problem getting array type", e);
      }
    }
    return o.getClass();
  }

  /**
   * Find the serializer for the given Java type and/or JSON type.
   *
   * @param clazz The Java class to lookup.
   * @param jsoClazz The JSON class type to lookup (may be null in the
   *          marshalling case in which case only the class is used to lookup
   *          the serializer).
   * @return The found Serializer for the types specified or null if none could
   *         be found.
   */
  private Serializer getSerializer(Class clazz, Class jsoClazz)
  {
    if (log.isDebugEnabled())
    {
      log.debug("looking for serializer - java:"
          + (clazz == null ? "null" : clazz.getName()) + " json:"
          + (jsoClazz == null ? "null" : jsoClazz.getName()));
    }

    synchronized (serializerSet)
    {
      Serializer s = (Serializer) serializableMap.get(clazz);
      if (s != null && s.canSerialize(clazz, jsoClazz))
      {
        if (log.isDebugEnabled())
        {
          log.debug("direct match serializer " + s.getClass().getName());
        }
        return s;
      }
      Iterator i = serializerList.iterator();
      while (i.hasNext())
      {
        s = (Serializer) i.next();
        if (s.canSerialize(clazz, jsoClazz))
        {
          if (log.isDebugEnabled())
          {
            log.debug("search found serializer " + s.getClass().getName());
          }
          return s;
        }
      }
    }
    return null;
  }

  /**
   * Reads an object, serialising each
   * This is used by the java serialization logic.
   *
   * @param in The stream to take an object to serialise
   * @throws java.io.IOException if the object can't be read from the stream
   * @throws ClassNotFoundException If a class cannot be found for the object to
   *           be read
   *
   * @see java.io.Serializable
   */
  private void readObject(ObjectInputStream in)
      throws IOException, ClassNotFoundException
  {
    in.defaultReadObject();
    serializableMap = new HashMap();
    Iterator i = serializerList.iterator();
    while (i.hasNext())
    {
      Serializer s = (Serializer) i.next();
      Class classes[] = s.getSerializableClasses();
      for (int j = 0; j < classes.length; j++)
      {
        serializableMap.put(classes[j], s);
      }
    }
  }
}
/* @generated */
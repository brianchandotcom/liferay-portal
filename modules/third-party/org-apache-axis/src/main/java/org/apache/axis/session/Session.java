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

package org.apache.axis.session;

import java.util.Enumeration;

/**
 * An abstract interface to provide session storage to Axis services.
 *
 * This is extremely basic at the moment.
 *
 * @author Glen Daniels (gdaniels@apache.org)
 */
public interface Session extends java.io.Serializable
{
    /** Get a property from the session
     *
     * @param key the name of the property desired.
     */
    public Object get(String key);
    
    /** Set a property in the session
     *
     * @param key the name of the property to set.
     * @param value the value of the property.
     */
    public void set(String key, Object value);
    
    /** Remove a property from the session
     *
     * @param key the name of the property desired.
     */
    public void remove(String key);

    /**
     * Get an enumeration of the keys in this session
     */
    public Enumeration getKeys();

    /** Set the session's time-to-live.
     *
     * This is implementation-specific, but basically should be the #
     * of seconds of inactivity which will cause the session to time
     * out and invalidate.  "inactivity" is implementation-specific.
     */
    public void setTimeout(int timeout);
    
    /**
     * Return the sessions' time-to-live.
     * 
     * @return the timeout value for this session.
     */ 
    public int getTimeout();
    
    /**
     * "Touch" the session (mark it recently used)
     */ 
    public void touch();
    
    /**
     * invalidate the session
     */ 
    public void invalidate();
    
    /**
     * Get an Object suitable for synchronizing the session.  This method
     * exists because different session implementations might provide
     * different ways of getting at shared data.  For a simple hashtable-
     * based session, this would just be the hashtable, but for sessions
     * which use database connections, etc. it might be an object wrapping
     * a table ID or somesuch.
     */ 
    public Object getLockObject();
}
/* @generated */
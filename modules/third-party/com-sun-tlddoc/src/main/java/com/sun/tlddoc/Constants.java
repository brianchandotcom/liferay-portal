/*
 * <license>
 * Copyright (c) 2003-2004, Sun Microsystems, Inc.
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright 
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright 
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Sun Microsystems, Inc. nor the names of its 
 *       contributors may be used to endorse or promote products derived from
 *       this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS 
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A 
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * ROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * </license>
 */

package com.sun.tlddoc;

/**
 * Various constants used by TLDDoc
 *
 * @author  mroth
 */
public final class Constants {
    
    /** Private constructor as this is a utility class. */
    private Constants() {
    }
    
    public static final String VERSION = "1.3";
    
    public static final String DEFAULT_WINDOW_TITLE = 
        "Tag Library Documentation Generator - Generated Documentation";
    
    public static final String DEFAULT_DOC_TITLE =
        "Tag Library Documentation Generator - Generated Documentation";
    
    /** Namespace for Java EE */
    public static final String NS_JAVAEE = "http://java.sun.com/xml/ns/javaee";
    
    /** Namespace for J2EE */
    public static final String NS_J2EE = "http://java.sun.com/xml/ns/j2ee";
    
    /** If true, outputs the input to the transform before generation */
    public static final boolean DEBUG_INPUT_DOCUMENT = false;
}

/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.apache.ivy.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Properties;
import java.util.TreeSet;

/**
 * A simple Properties extension easing the loading and saving of data
 */
public class PropertiesFile extends Properties {
    private File file;

    private String header;

    public PropertiesFile(File file, String header) {
        this.file = file;
        this.header = header;
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                load(fis);
            } catch (Exception ex) {
                Message.warn("exception occurred while reading properties file " + file, ex);
            }
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                // ignored
            }
        }
    }

    public void save() {
        FileOutputStream fos = null;
        try {
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(file);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "8859_1"));

			if (header != null) {
				bw.write("#" + header);
			}
			bw.newLine();

			synchronized (this) {
				Iterator itr = new TreeSet(keySet()).iterator();

				while (itr.hasNext()) {
					String key = (String)itr.next();
					String val = (String)get(key);
					key = saveConvert(key, true);
					val = saveConvert(val, false);
					bw.write(key + "=" + val);
					bw.newLine();
				}
			}
			bw.flush();
        } catch (Exception ex) {
            Message.warn("exception occurred while writing properties file " + file, ex);
        }
        try {
            if (fos != null) {
                fos.close();
            }
        } catch (IOException e) {
            // ignored
        }
    }

	/*
     * escapes special characters with a preceding slash
     */
    private String saveConvert(String theString, boolean escapeSpace) {
        int len = theString.length();
        int bufLen = len * 2;
        if (bufLen < 0) {
            bufLen = Integer.MAX_VALUE;
        }
        StringBuffer outBuffer = new StringBuffer(bufLen);

        for(int x=0; x<len; x++) {
            char aChar = theString.charAt(x);
            // Handle common case first, selecting largest block that
            // avoids the specials below
            if ((aChar > 61) && (aChar < 127)) {
                if (aChar == '\\') {
                    outBuffer.append('\\'); outBuffer.append('\\');
                    continue;
                }
                outBuffer.append(aChar);
                continue;
            }
            switch(aChar) {
                case ' ':
                    if (x == 0 || escapeSpace)
                        outBuffer.append('\\');
                    outBuffer.append(' ');
                    break;
                case '\t':outBuffer.append('\\'); outBuffer.append('t');
                          break;
                case '\n':outBuffer.append('\\'); outBuffer.append('n');
                          break;
                case '\r':outBuffer.append('\\'); outBuffer.append('r');
                          break;
                case '\f':outBuffer.append('\\'); outBuffer.append('f');
                          break;
                case '=': // Fall through
                case ':': // Fall through
                case '#': // Fall through
                case '!':
                    outBuffer.append('\\'); outBuffer.append(aChar);
                    break;
                default:
					outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }

}
/* @generated */
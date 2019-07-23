/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.liferay.gradle.plugins.workspace;

import java.util.Collection;

import aQute.bnd.header.Parameters;
import aQute.bnd.make.metatype.MetaTypeReader;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Clazz;
import aQute.bnd.osgi.Clazz.QUERY;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.Jar;
import aQute.bnd.service.AnalyzerPlugin;

 /**
 * This class is responsible for meta type types. It is a plugin that can
 *
 * @author aqute
 */
public class MetatypePlugin implements AnalyzerPlugin {

 	public boolean analyzeJar(Analyzer analyzer) throws Exception {

 		Parameters map = analyzer.parseHeader(analyzer.getProperty(Constants.METATYPE));

 		Jar jar = analyzer.getJar();
		for (String name : map.keySet()) {
			Collection<Clazz> metatypes = analyzer.getClasses("", QUERY.ANNOTATED.toString(),
					"aQute.bnd.annotation.metatype.Meta$OCD", //
					QUERY.NAMED.toString(), name //
			);
			for (Clazz c : metatypes) {
				analyzer.warning(
						"%s annotation used in class %s. Bnd metatype annotations are deprecated as of Bnd 3.2 and support will be removed in Bnd 4.0. Please change to use OSGi Metatype annotations.",
						"aQute.bnd.annotation.metatype.Meta$OCD", c);
				jar.putResource("OSGI-INF/metatype/" + c.getFQN() + ".xml", new MetaTypeReader(c, analyzer));
			}
		}
		return false;
	}

 	@Override
	public String toString() {
		return "MetatypePlugin";
	}

 }
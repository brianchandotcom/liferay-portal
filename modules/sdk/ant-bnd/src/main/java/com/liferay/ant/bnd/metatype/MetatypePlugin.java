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

package com.liferay.ant.bnd.metatype;

import aQute.bnd.header.Parameters;
import aQute.bnd.make.metatype.MetaTypeReader;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Clazz;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.Jar;
import aQute.bnd.service.AnalyzerPlugin;

import java.util.Collection;

/**
 * @author Gregory Amerson
 */
public class MetatypePlugin implements AnalyzerPlugin {

	@Override
	public boolean analyzeJar(Analyzer analyzer) throws Exception {
		Parameters parameters = analyzer.parseHeader(
			analyzer.getProperty(Constants.METATYPE));

		Jar jar = analyzer.getJar();

		for (String name : parameters.keySet()) {
			Collection<Clazz> metatypeClasses = analyzer.getClasses(
				"", Clazz.QUERY.ANNOTATED.toString(),
				"aQute.bnd.annotation.metatype.Meta$OCD",
				Clazz.QUERY.NAMED.toString(), name);

			for (Clazz metatypeClass : metatypeClasses) {
				jar.putResource(
					"OSGI-INF/metatype/" + metatypeClass.getFQN() + ".xml",
					new MetaTypeReader(metatypeClass, analyzer));
			}
		}

		return false;
	}

	@Override
	public String toString() {
		return "MetatypePlugin";
	}

}
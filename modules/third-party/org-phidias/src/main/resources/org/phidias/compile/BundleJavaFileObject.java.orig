/* @generated */
/**
 * Copyright 2012 Liferay Inc.
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
 */

package org.phidias.compile;

import java.io.IOException;
import java.io.InputStream;

import java.net.URI;
import java.net.URL;

import javax.tools.SimpleJavaFileObject;

/**
 * @author Raymond Augé
 */
public class BundleJavaFileObject extends SimpleJavaFileObject {

	public BundleJavaFileObject(URI uri, String className, URL url) {
		super(uri, Kind.CLASS);

		_className = className;
		this.url = url;
	}

	public String inferBinaryName() {
		return _className;
	}

	@Override
	public InputStream openInputStream() throws IOException {
		return url.openStream();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName().concat("[").concat(
			toUri().toString()).concat("]");
	}

	protected URL url;
	private String _className;

}

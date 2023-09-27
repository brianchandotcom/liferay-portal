/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.io;

import com.liferay.petra.lang.ClassLoaderPool;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * @author Shuyang Zhou
 */
public class AnnotatedObjectOutputStream extends ObjectOutputStream {

	public AnnotatedObjectOutputStream(OutputStream outputStream)
		throws IOException {

		super(outputStream);
	}

	@Override
	protected void annotateClass(Class<?> clazz) throws IOException {
		writeUTF(ClassLoaderPool.getContextName(clazz.getClassLoader()));
	}

}
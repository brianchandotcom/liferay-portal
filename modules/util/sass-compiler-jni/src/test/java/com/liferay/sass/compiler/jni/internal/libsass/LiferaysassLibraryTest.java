/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.sass.compiler.jni.internal.libsass;

import com.liferay.sass.compiler.jni.internal.libsass.LiferaysassLibrary.Sass_File_Context;
import com.liferay.sass.compiler.jni.internal.util.test.JniSassCompilerTestUtil;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Gregory Amerson
 */
public class LiferaysassLibraryTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		System.setProperty("jna.nosys", Boolean.TRUE.toString());

		JniSassCompilerTestUtil.addSearchPath();
	}

	@Test
	public void testInstance() throws Exception {
		Assert.assertNotNull(LiferaysassLibrary.INSTANCE);
	}

	@Test
	public void testSassFileContext() throws Exception {
		Sass_File_Context sassFileContext =
			LiferaysassLibrary.INSTANCE.sass_make_file_context("");

		Assert.assertNotNull(sassFileContext);
		Assert.assertNotNull(sassFileContext.getPointer());

		LiferaysassLibrary.INSTANCE.sass_delete_file_context(sassFileContext);
	}

}
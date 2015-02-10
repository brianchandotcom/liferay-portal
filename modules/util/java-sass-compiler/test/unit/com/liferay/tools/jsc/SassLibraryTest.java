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

package com.liferay.tools.jsc;

import static org.junit.Assert.assertNotNull;

import com.liferay.tools.jsc.libsass.SassLibrary;
import com.liferay.tools.jsc.libsass.SassLibrary.Sass_File_Context;

import org.junit.Test;

/**
 * @author Gregory Amerson
 */
public class SassLibraryTest extends BaseTests {

	@Test
	public void testSassContextSharedLibraryLoading() throws Exception {
		Sass_File_Context ctx = SassLibrary.INSTANCE.sass_make_file_context("");
		assertNotNull(ctx);
		assertNotNull(ctx.getPointer());

		SassLibrary.INSTANCE.sass_delete_file_context(ctx);
	}

	@Test
	public void testSharedLibraryLoading() throws Exception {
		assertNotNull(SassLibrary.INSTANCE);
	}

}
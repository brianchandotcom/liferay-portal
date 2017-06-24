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

package com.liferay.portal.template.freemarker.internal;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;

import freemarker.ext.jsp.internal.WriterFactory;

import java.io.Writer;

/**
 * @author Preston Crary
 */
public class UnsyncStringWriterFactory implements WriterFactory {

	@Override
	public Writer createWriter() {
		return new UnsyncStringWriter();
	}

}
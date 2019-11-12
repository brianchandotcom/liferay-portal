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

package com.liferay.document.library.internal.security.io;

import com.liferay.document.library.internal.util.InputStreamUtil;
import com.liferay.document.library.security.io.InputStreamSanitizer;

import java.io.InputStream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(service = InputStreamSanitizer.class)
public class InputStreamSanitizerImpl implements InputStreamSanitizer {

	@Override
	public InputStream sanitize(InputStream inputStream) {
		return new SafePNGInputStream(
			InputStreamUtil.toBufferedInputStream(inputStream));
	}

}
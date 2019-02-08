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

package com.liferay.data.engine.io;

import com.liferay.data.engine.exception.DEDataLayoutDeserializerException;

/**
 * Deserializer interface for {@link com.liferay.data.engine.model.DEDataLayout}
 * @author Jeyvison Nascimento
 * @review
 */
public interface DEDataLayoutDeserializer {

	/**
	 * Performs the deserialization of the pages, pagination mode and default
	 * language, and returns the response object.
	 * @param deDataLayoutDeserializerApplyRequest
	 * @return A {@link DEDataLayoutDeserializerApplyResponse} object
	 * @throws DEDataLayoutException
	 * @review
	 */
	public DEDataLayoutDeserializerApplyResponse apply(
			DEDataLayoutDeserializerApplyRequest
				deDataLayoutDeserializerApplyRequest)
		throws DEDataLayoutDeserializerException;

}
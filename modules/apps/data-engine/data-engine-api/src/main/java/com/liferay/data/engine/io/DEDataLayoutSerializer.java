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

import com.liferay.data.engine.exception.DEDataLayoutSerializerException;

/**
 * Serializer interface for DEDataLayout.
 *
 * @author Jeyvison Nascimento
 */
public interface DEDataLayoutSerializer {

	/**
	 * Performs the serialization and return the content as a response object.
	 * @param deDataLayoutSerializerApplyRequest A {@link DEDataLayoutSerializerApplyRequest}
	 * object containing the {@link com.liferay.data.engine.model.DEDataLayout}
	 * object.
	 * @return A {@link DEDataLayoutSerializerApplyResponse} object containing
	 * the serialized DEDataLayout String.
	 * @throws DEDataLayoutSerializerException Or it's variations. See nested classes.
	 * @review
	 */
	public DEDataLayoutSerializerApplyResponse apply(
			DEDataLayoutSerializerApplyRequest
				deDataLayoutSerializerApplyRequest)
		throws DEDataLayoutSerializerException;

}
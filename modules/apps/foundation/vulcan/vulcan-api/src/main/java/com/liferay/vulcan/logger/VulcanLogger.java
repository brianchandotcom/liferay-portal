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

package com.liferay.vulcan.logger;

import com.liferay.vulcan.result.APIError;

/**
 * Instances of this class allow developers to use its own loggers for Vulcan
 * warnings.
 *
 * @author Alejandro Hernández
 */
public interface VulcanLogger {

	/**
	 * Logs a message in the form of a exception.
	 *
	 * @param apiError the error.
	 */
	public default void error(APIError apiError) {
	}

}
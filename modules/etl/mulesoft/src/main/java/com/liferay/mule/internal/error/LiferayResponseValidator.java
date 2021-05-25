/**
 * Copyright (c) 2000-2021 Liferay, Inc. All rights reserved.
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

package com.liferay.mule.internal.error;

import org.mule.runtime.core.api.util.IOUtils;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.http.api.domain.entity.HttpEntity;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

/**
 * @author Matija Petanjek
 */
public class LiferayResponseValidator {

	public void validate(HttpResponse httpResponse) throws ModuleException {
		if (httpResponse == null) {
			throw new ModuleException(
				"Server returned no response", LiferayError.SERVER_ERROR);
		}
		else if (httpResponse.getStatusCode() >= 400) {
			throw new ModuleException(
				getMessage(httpResponse),
				LiferayError.fromStatus(httpResponse.getStatusCode()));
		}
	}

	private String getMessage(HttpResponse httpResponse) {
		HttpEntity httpEntity = httpResponse.getEntity();

		return String.format(
			"Request failed with status: %d, and message: %s",
			httpResponse.getStatusCode(),
			IOUtils.toString(httpEntity.getContent()));
	}

}
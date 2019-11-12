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

package com.liferay.data.engine.renderer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Gabriel Albuquerque
 */
@ProviderType
public interface DataLayoutRenderer {

	public String render(
			Long dataLayoutId,
			DataLayoutRendererContext dataLayoutRendererContext)
		throws Exception;

	/**
	 * @deprecated As of Mueller (7.2.x), see {@link
	 *             DataLayoutRenderer#render(Long, DataLayoutRendererContext)}
	 */
	@Deprecated
	public default String render(
			Long dataLayoutId, Map<String, Object> dataRecordValues,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		DataLayoutRendererContext dataLayoutRendererContext =
			new DataLayoutRendererContext();

		dataLayoutRendererContext.setDataRecordValues(dataRecordValues);
		dataLayoutRendererContext.setHttpServletRequest(httpServletRequest);
		dataLayoutRendererContext.setHttpServletResponse(httpServletResponse);

		return render(dataLayoutId, dataLayoutRendererContext);
	}

}
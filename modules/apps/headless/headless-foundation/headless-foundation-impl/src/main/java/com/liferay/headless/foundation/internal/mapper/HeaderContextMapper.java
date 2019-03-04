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

package com.liferay.headless.foundation.internal.mapper;

import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.segments.context.Context;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sarai Díaz
 */
@Component(immediate = true, service = HeaderContextMapper.class)
public class HeaderContextMapper {

	public Context create(MultivaluedMap<String, String> requestHeaders) {
		Context context = new Context();

		Set<Map.Entry<String, List<String>>> requestHeadersEntrySet =
			requestHeaders.entrySet();

		for (Map.Entry<String, List<String>> header : requestHeadersEntrySet) {
			List<String> values = header.getValue();

			String contextValue = values.get(0);

			String key = StringUtil.toLowerCase(header.getKey());

			if (key.startsWith("x-")) {
				String contextKey = key.replace("x-", "");

				String contextKeyCamelCase = CamelCaseUtil.toCamelCase(
					contextKey);

				context.put(contextKeyCamelCase, contextValue);
			}
			else if (key.equals("accept-language")) {
				String value = contextValue.replace("-", "_");

				context.put(Context.LANGUAGE_ID, value);
			}
			else if (key.equals("host")) {
				context.put(Context.URL, contextValue);
			}
			else if (key.equals("user-agent")) {
				context.put(Context.USER_AGENT, contextValue);
			}
			else {
				context.put(key, contextValue);
			}
		}

		context.put(Context.LOCAL_DATE, LocalDate.from(ZonedDateTime.now()));

		return context;
	}

}
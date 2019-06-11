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

package com.liferay.talend.runtime.client;

import javax.ws.rs.core.Form;

/**
 * @author Igor Beslic
 */
public class XWWWFormURLEncoder {

	public String encode(String... args) {
		if (args == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < args.length;) {
			sb.append(args[i]);
			sb.append("=");
			sb.append(args[i + 1]);

			i = i + 2;

			if (i < args.length) {
				sb.append("&");
			}
		}

		return sb.toString();
	}

	public Form toForm(String... args) {
		Form form = new Form();

		for (int i = 0; i < args.length;) {
			form.param(args[i], args[i + 1]);

			i = i + 2;
		}

		return form;
	}

}
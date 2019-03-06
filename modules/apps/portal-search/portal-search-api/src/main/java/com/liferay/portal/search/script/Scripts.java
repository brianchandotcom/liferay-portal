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

package com.liferay.portal.search.script;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public interface Scripts {

	public ScriptBuilder builder();

	public ScriptFieldBuilder fieldBuilder();

	public Script inline(String language, String code);

	public Script script(String idOrCode);

	public ScriptField scriptField(String field, Script script);

	public Script stored(String scriptId);

}
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

package com.frontend.js.minifier.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Iván Zaera Avellón
 */
@Meta.OCD(
	id = "com.frontend.js.minifier.configuration.YahooJavaScriptMinifierConfiguration"
)
public interface YahooJavaScriptMinifierConfiguration {

	@Meta.AD(deflt = "1", required = false)
	public int cssLineBreak();

	@Meta.AD(deflt = "false", required = false)
	public boolean jsDisableOptimizations();

	@Meta.AD(deflt = "1", required = false)
	public int jsLineBreak();

	@Meta.AD(deflt = "true", required = false)
	public boolean jsMunge();

	@Meta.AD(deflt = "false", required = false)
	public boolean jsPreserveAllSemicolons();

	@Meta.AD(deflt = "false", required = false)
	public boolean jsVerbose();

}
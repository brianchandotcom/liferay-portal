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

package com.liferay.portalweb.util.block.macro;

import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class BaseMacro {

	public BaseMacro(LiferaySelenium liferaySelenium) {
		this.liferaySelenium = liferaySelenium;
	}

	protected Map<String, String> commandScopeVariables;
	protected Map<String, String> definitionScopeVariables = new HashMap<>();
	protected Map<String, String> executeScopeVariables;
	protected Map<String, String> forScopeVariables;
	protected LiferaySelenium liferaySelenium;

}
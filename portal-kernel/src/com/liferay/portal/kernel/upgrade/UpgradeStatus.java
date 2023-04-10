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

package com.liferay.portal.kernel.upgrade;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Alberto Chaparro
 */
public interface UpgradeStatus {

	public Map<String, Map<String, Integer>> getErrorMessages();

	public String getFinalSchemaVersion(String servletContextName);

	public String getInitialSchemaVersion(String servletContextName);

	public String getStatus();

	public String getType();

	public Map<String, ArrayList<String>> getUpgradeProcessMessages();

	public Map<String, Map<String, Integer>> getWarningMessages();

}
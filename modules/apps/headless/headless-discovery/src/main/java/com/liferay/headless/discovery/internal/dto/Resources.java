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

package com.liferay.headless.discovery.internal.dto;

import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 */
@XmlRootElement
public class Resources {

	public Resources() {
	}

	public Resources(Map<String, Resource> resources) {
		_resources = resources;
	}

	public Map<String, Resource> getResources() {
		return _resources;
	}

	public void setResources(Map<String, Resource> resources) {
		_resources = resources;
	}

	private Map<String, Resource> _resources = new TreeMap<>();

}
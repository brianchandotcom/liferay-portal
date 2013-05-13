/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.ccpp;

import java.util.Set;

import javax.ccpp.Attribute;
import javax.ccpp.Component;
import javax.ccpp.Profile;
import javax.ccpp.ProfileDescription;

/**
 * @author Jorge Ferrer
 */
public class EmptyProfile implements Profile {

	@Override
	public Attribute getAttribute(String name) {
		return null;
	}

	@Override
	public Set<Attribute> getAttributes() {
		return null;
	}

	@Override
	public Component getComponent(String localtype) {
		return null;
	}

	@Override
	public Set<Component> getComponents() {
		return null;
	}

	@Override
	public ProfileDescription getDescription() {
		return null;
	}

}
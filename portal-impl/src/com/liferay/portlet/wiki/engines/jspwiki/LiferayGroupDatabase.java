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

package com.liferay.portlet.wiki.engines.jspwiki;

import com.ecyrd.jspwiki.WikiEngine;
import com.ecyrd.jspwiki.auth.authorize.Group;
import com.ecyrd.jspwiki.auth.authorize.GroupDatabase;

import java.security.Principal;

import java.util.Properties;

/**
 * @author Jorge Ferrer
 */
public class LiferayGroupDatabase implements GroupDatabase {

	@Override
	public void commit() {
	}

	@Override
	public void delete(Group group) {
	}

	@Override
	public Group[] groups() {
		return new Group[0];
	}

	@Override
	public void initialize(WikiEngine engine, Properties props) {
	}

	@Override
	public void save(Group group, Principal modifier) {
	}

}
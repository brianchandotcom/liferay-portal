/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.blocks.base.actions;

/**
 * @author Brian Wing Shun Chan
 */
public interface LiferayActions {

	public void assertChecked(String param1, String param2) throws Exception;

	public void assertElementNotPresent(String param1, String param2)
		throws Exception;

	public void assertElementPresent(String param1, String param2)
		throws Exception;

	public void assertNotChecked(String param1, String param2) throws Exception;

	public void assertTextEquals(String param1, String param2) throws Exception;

	public void assertTextNotEquals(String param1, String param2)
		throws Exception;

	public void assertTextNotPresent(String param1, String param2)
		throws Exception;

	public void assertTextPresent(String param1, String param2)
		throws Exception;

	public void check(String param1, String param2) throws Exception;

	public void click(String param1, String param2) throws Exception;

	public void close(String param1, String param2) throws Exception;

	public void copy(String param1, String param2) throws Exception;

	public void confirm(String param1, String param2) throws Exception;

	public String get(String key) throws Exception;

	public boolean isElementPresent(String param1, String param2)
		throws Exception;

	public void mouseOver(String param1, String param2) throws Exception;

	public void open(String param1, String param2) throws Exception;

	public void paste(String param1, String param2) throws Exception;

	public void select(String param1, String param2) throws Exception;

	public void type(String param1, String param2) throws Exception;

	public void uncheck(String param1, String param2) throws Exception;

}
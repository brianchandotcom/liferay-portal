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

package com.liferay.portal.kernel.portlet;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 */
public interface PortalPreferences extends Serializable {

	public long getUserId();

	public String getValue(String namespace, String key);

	public String getValue(String namespace, String key, String defaultValue);

	public String[] getValues(String namespace, String key);

	public String[] getValues(
		String namespace, String key, String[] defaultValue);

	public boolean isSignedIn();

	public void resetValues(String namespace);

	public void setSignedIn(boolean signedIn);

	public void setUserId(long userId);

	public void setValue(String namespace, String key, String value);

	public void setValues(String namespace, String key, String[] values);

	public int size();

}
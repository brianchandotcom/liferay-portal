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

package com.liferay.portal.security.exportimport;

import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Michael C. Han
 * @author Marcellus Tavares
 */
public interface UserExporter {

	public void exportUser(
			Contact contact, Map<String, Serializable> contactExpandoAttributes)
		throws Exception;

	public void exportUser(
			long userId, long userGroupId, UserOperation userOperation)
		throws Exception;

	public void exportUser(
			User user, Map<String, Serializable> userExpandoAttributes)
		throws Exception;

}
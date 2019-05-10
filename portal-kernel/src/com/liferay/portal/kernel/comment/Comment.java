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

package com.liferay.portal.kernel.comment;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Adolfo Pérez
 */
@ProviderType
public interface Comment {

	public String getBody();

	public String getClassName();

	public long getClassPK();

	public long getCommentId();

	public Date getCreateDate();

	public long getGroupId();

	public Class<?> getModelClass();

	public String getModelClassName();

	public Date getModifiedDate();

	public long getParentCommentId();

	public String getTranslatedBody(String pathThemeImages);

	public User getUser() throws PortalException;

	public long getUserId();

	public String getUserName();

	public String getUuid();

	public boolean isRoot();

}
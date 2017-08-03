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

package com.liferay.sharepoint.connector.operation;

import com.liferay.sharepoint.connector.SharepointConnectionInfo;

import com.microsoft.schemas.sharepoint.soap.CopySoap;
import com.microsoft.schemas.sharepoint.soap.ListsSoap;
import com.microsoft.schemas.sharepoint.soap.VersionsSoap;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public interface Operation {

	public void afterPropertiesSet();

	public void setCopySoap(CopySoap copySoap);

	public void setListsSoap(ListsSoap listsSoap);

	public void setOperations(Map<Class<?>, Operation> operations);

	public void setSharepointConnectionInfo(
		SharepointConnectionInfo sharepointConnectionInfo);

	public void setVersionsSoap(VersionsSoap versionsSoap);

}
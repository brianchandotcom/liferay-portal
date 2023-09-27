/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.operation;

import com.liferay.sharepoint.soap.repository.connector.SharepointConnectionInfo;

import com.microsoft.schemas.sharepoint.soap.CopySoap12Stub;
import com.microsoft.schemas.sharepoint.soap.ListsSoap12Stub;
import com.microsoft.schemas.sharepoint.soap.VersionsSoap12Stub;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public interface Operation {

	public void afterPropertiesSet();

	public void setCopySoap12Stub(CopySoap12Stub copySoap12Stub);

	public void setListsSoap12Stub(ListsSoap12Stub listsSoap12Stub);

	public void setOperations(
		Map<Class<? extends Operation>, Operation> operations);

	public void setSharepointConnectionInfo(
		SharepointConnectionInfo sharepointConnectionInfo);

	public void setVersionsSoap12Stub(VersionsSoap12Stub versionsSoap12Stub);

}
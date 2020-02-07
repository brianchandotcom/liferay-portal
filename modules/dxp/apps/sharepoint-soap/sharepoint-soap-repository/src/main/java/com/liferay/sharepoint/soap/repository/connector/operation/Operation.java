/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.sharepoint.soap.repository.connector.operation;

import com.liferay.sharepoint.soap.repository.connector.SharepointConnectionInfo;

import com.microsoft.schemas.sharepoint.soap.CopyStub;
import com.microsoft.schemas.sharepoint.soap.ListsStub;
import com.microsoft.schemas.sharepoint.soap.VersionsStub;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public interface Operation {

	public void afterPropertiesSet();

	public void setCopyStub(CopyStub copyStub);

	public void setListStub(ListsStub listsStub);

	public void setOperations(Map<Class<?>, Operation> operations);

	public void setSharepointConnectionInfo(
		SharepointConnectionInfo sharepointConnectionInfo);

	public void setVersionsStub(VersionsStub versionsStub);

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.operation;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.sharepoint.soap.repository.connector.SharepointConnectionInfo;
import com.liferay.sharepoint.soap.repository.connector.SharepointObject;

import com.microsoft.schemas.sharepoint.soap.CopySoap12Stub;
import com.microsoft.schemas.sharepoint.soap.ListsSoap12Stub;
import com.microsoft.schemas.sharepoint.soap.VersionsSoap12Stub;

import java.net.URL;

import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseOperation implements Operation {

	@Override
	public void afterPropertiesSet() {
	}

	@Override
	public void setCopySoap12Stub(CopySoap12Stub copySoap12Stub) {
		this.copySoap12Stub = copySoap12Stub;
	}

	@Override
	public void setListsSoap12Stub(ListsSoap12Stub listsSoap12Stub) {
		this.listsSoap12Stub = listsSoap12Stub;
	}

	@Override
	public void setOperations(
		Map<Class<? extends Operation>, Operation> operations) {

		_operations = operations;
	}

	@Override
	public void setSharepointConnectionInfo(
		SharepointConnectionInfo sharepointConnectionInfo) {

		this.sharepointConnectionInfo = sharepointConnectionInfo;
	}

	@Override
	public void setVersionsSoap12Stub(VersionsSoap12Stub versionsSoap12Stub) {
		this.versionsSoap12Stub = versionsSoap12Stub;
	}

	public URL toURL(String path) {
		PathUtil.validatePath(path);

		return URLUtil.toURL(
			StringBundler.concat(
				sharepointConnectionInfo.getServiceURL(),
				sharepointConnectionInfo.getLibraryPath(), path));
	}

	protected <O extends Operation> O getOperation(Class<O> clazz) {
		return (O)_operations.get(clazz);
	}

	protected SharepointObject getSharepointObject(
		List<SharepointObject> sharepointObjects) {

		if (sharepointObjects.isEmpty()) {
			return null;
		}

		return sharepointObjects.get(0);
	}

	protected String toFullPath(String path) {
		PathUtil.validatePath(path);

		StringBundler sb = new StringBundler(4);

		sb.append(sharepointConnectionInfo.getSitePath());
		sb.append(StringPool.SLASH);
		sb.append(sharepointConnectionInfo.getLibraryPath());

		if (!path.equals(StringPool.SLASH)) {
			sb.append(path);
		}

		return sb.toString();
	}

	protected CopySoap12Stub copySoap12Stub;
	protected ListsSoap12Stub listsSoap12Stub;
	protected SharepointConnectionInfo sharepointConnectionInfo;
	protected VersionsSoap12Stub versionsSoap12Stub;

	private Map<Class<? extends Operation>, Operation> _operations;

}
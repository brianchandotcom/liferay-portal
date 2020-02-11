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
	public void setOperations(Map<Class<?>, Operation> operations) {
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

		URL serviceURL = sharepointConnectionInfo.getServiceURL();

		return URLUtil.toURL(
			serviceURL.toString() + sharepointConnectionInfo.getLibraryPath() +
				path);
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

	private Map<Class<?>, Operation> _operations;

}
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

package com.liferay.sharepoint.soap.connector.operation;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.sharepoint.soap.connector.SharepointConnectionInfo;
import com.liferay.sharepoint.soap.connector.SharepointObject;
import com.liferay.sharepoint.soap.connector.schema.XMLHelper;

import com.microsoft.schemas.sharepoint.soap.CopySoap;
import com.microsoft.schemas.sharepoint.soap.ListsSoap;
import com.microsoft.schemas.sharepoint.soap.VersionsSoap;

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
	public void setCopySoap(CopySoap copySoap) {
		this.copySoap = copySoap;
	}

	@Override
	public void setListsSoap(ListsSoap listsSoap) {
		this.listsSoap = listsSoap;
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
	public void setVersionsSoap(VersionsSoap versionsSoap) {
		this.versionsSoap = versionsSoap;
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

	protected static XMLHelper xmlHelper = new XMLHelper();

	protected CopySoap copySoap;
	protected ListsSoap listsSoap;
	protected SharepointConnectionInfo sharepointConnectionInfo;
	protected VersionsSoap versionsSoap;

	private Map<Class<?>, Operation> _operations;

}
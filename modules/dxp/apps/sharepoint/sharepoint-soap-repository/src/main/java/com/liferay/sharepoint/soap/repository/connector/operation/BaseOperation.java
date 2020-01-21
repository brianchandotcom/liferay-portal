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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.sharepoint.soap.repository.connector.SharepointConnectionInfo;
import com.liferay.sharepoint.soap.repository.connector.SharepointObject;
import com.liferay.sharepoint.soap.repository.connector.schema.XMLHelper;

import com.microsoft.schemas.sharepoint.soap.CopyStub;
import com.microsoft.schemas.sharepoint.soap.ListsStub;
import com.microsoft.schemas.sharepoint.soap.VersionsStub;

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
	public void setCopyStub(CopyStub copyStub) {
		this.copyStub = copyStub;
	}

	@Override
	public void setListStub(ListsStub listsStub) {
		this.listsStub = listsStub;
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
	public void setVersionsStub(VersionsStub versionsStub) {
		this.versionsStub = versionsStub;
	}

	public URL toURL(String path) {
		pathHelper.validatePath(path);

		URL serviceURL = sharepointConnectionInfo.getServiceURL();

		return urlHelper.toURL(
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
		pathHelper.validatePath(path);

		StringBundler sb = new StringBundler(4);

		sb.append(sharepointConnectionInfo.getSitePath());
		sb.append(StringPool.SLASH);
		sb.append(sharepointConnectionInfo.getLibraryPath());

		if (!path.equals(StringPool.SLASH)) {
			sb.append(path);
		}

		return sb.toString();
	}

	protected static PathHelper pathHelper = new PathHelper();
	protected static URLHelper urlHelper = new URLHelper();
	protected static XMLHelper xmlHelper = new XMLHelper();

	protected CopyStub copyStub;
	protected ListsStub listsStub;
	protected SharepointConnectionInfo sharepointConnectionInfo;
	protected VersionsStub versionsStub;

	private Map<Class<?>, Operation> _operations;

}
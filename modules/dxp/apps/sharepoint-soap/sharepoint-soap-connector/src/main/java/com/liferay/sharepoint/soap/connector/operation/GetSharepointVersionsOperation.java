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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.sharepoint.soap.connector.SharepointException;
import com.liferay.sharepoint.soap.connector.SharepointObject;
import com.liferay.sharepoint.soap.connector.SharepointVersion;
import com.liferay.sharepoint.soap.connector.internal.util.RemoteExceptionSharepointExceptionMapper;

import com.microsoft.schemas.sharepoint.soap.GetVersionsResponseGetVersionsResult;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Iván Zaera
 */
public final class GetSharepointVersionsOperation extends BaseOperation {

	@Override
	public void afterPropertiesSet() {
		_getSharepointObjectByPathOperation = getOperation(
			GetSharepointObjectByPathOperation.class);
	}

	public List<SharepointVersion> execute(String filePath)
		throws SharepointException {

		try {
			SharepointObject sharepointObject =
				_getSharepointObjectByPathOperation.execute(filePath);

			if (sharepointObject == null) {
				throw new SharepointException(
					"Unable to find Sharepoint object at " + filePath);
			}

			GetVersionsResponseGetVersionsResult
				getVersionsResponseGetVersionsResult = versionsSoap.getVersions(
					toFullPath(filePath));

			Element getVersionsResponseGetVersionsResultElement =
				xmlHelper.getElement(getVersionsResponseGetVersionsResult);

			return _getSharepointVersions(
				sharepointObject.getSharepointObjectId(),
				getVersionsResponseGetVersionsResultElement);
		}
		catch (RemoteException remoteException) {
			throw RemoteExceptionSharepointExceptionMapper.map(remoteException);
		}
	}

	private Date _getDate(String dateString) {
		Calendar calendar = DatatypeConverter.parseDateTime(dateString);

		return calendar.getTime();
	}

	private String _getSharepointVersionId(
		long sharepointObjectId, String version) {

		return sharepointObjectId + StringPool.AT + version;
	}

	private List<SharepointVersion> _getSharepointVersions(
		long sharepointObjectId,
		Element getVersionsResponseGetVersionsResultElement) {

		List<SharepointVersion> sharepointVersions = new ArrayList<>();

		NodeList nodeList =
			getVersionsResponseGetVersionsResultElement.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);

			String localName = node.getLocalName();

			if ((localName == null) ||
				!StringUtil.equalsIgnoreCase(localName, "result")) {

				continue;
			}

			NamedNodeMap namedNodeMap = node.getAttributes();

			Node commentsNode = namedNodeMap.getNamedItem("comments");
			Node createdByNode = namedNodeMap.getNamedItem("createdBy");
			Node createdRawNode = namedNodeMap.getNamedItem("createdRaw");
			Node versionNode = namedNodeMap.getNamedItem("version");
			Node urlNode = namedNodeMap.getNamedItem("url");
			Node sizeNode = namedNodeMap.getNamedItem("size");

			SharepointVersion sharepointVersion = new SharepointVersion(
				commentsNode.getNodeValue(), createdByNode.getNodeValue(),
				_getDate(createdRawNode.getNodeValue()),
				_getSharepointVersionId(
					sharepointObjectId, versionNode.getNodeValue()),
				GetterUtil.getLong(sizeNode.getNodeValue()),
				urlHelper.toURL(urlNode.getNodeValue()),
				_getVersion(versionNode.getNodeValue()));

			sharepointVersions.add(sharepointVersion);
		}

		Collections.sort(sharepointVersions, _comparator);

		return sharepointVersions;
	}

	private String _getVersion(String version) {
		if (version.startsWith(StringPool.AT)) {
			version = version.substring(1);
		}

		return version;
	}

	private static final Comparator<SharepointVersion> _comparator =
		new SharepointVersionComparator();

	private GetSharepointObjectByPathOperation
		_getSharepointObjectByPathOperation;

}
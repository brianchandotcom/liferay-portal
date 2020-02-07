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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.sharepoint.soap.connector.SharepointException;
import com.liferay.sharepoint.soap.connector.SharepointObject;
import com.liferay.sharepoint.soap.connector.internal.util.RemoteExceptionUtil;
import com.liferay.sharepoint.soap.connector.schema.query.Query;
import com.liferay.sharepoint.soap.connector.schema.query.QueryField;
import com.liferay.sharepoint.soap.connector.schema.query.QueryFieldsList;
import com.liferay.sharepoint.soap.connector.schema.query.QueryOptionsList;
import com.liferay.sharepoint.soap.connector.schema.query.option.ExpandUserFieldQueryOption;

import com.microsoft.schemas.sharepoint.soap.GetListItemsQuery;
import com.microsoft.schemas.sharepoint.soap.GetListItemsQueryOptions;
import com.microsoft.schemas.sharepoint.soap.GetListItemsResponseGetListItemsResult;
import com.microsoft.schemas.sharepoint.soap.GetListItemsViewFields;

import java.rmi.RemoteException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.axis.message.MessageElement;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Iván Zaera
 */
public class GetSharepointObjectsByQueryOperation extends BaseOperation {

	@Override
	public void afterPropertiesSet() {
		String libraryPath = sharepointConnectionInfo.getLibraryPath();
		String sitePath = sharepointConnectionInfo.getSitePath();

		_pathPrefixToRemoveLength = libraryPath.length() + sitePath.length();
	}

	public List<SharepointObject> execute(
			Query query, QueryOptionsList queryOptionsList,
			String... queryFieldNames)
		throws SharepointException {

		GetListItemsResponseGetListItemsResult
			getListItemsResponseGetListItemsResult = null;

		GetListItemsQuery getListItemsQuery = getGetListItemsQuery(query);
		GetListItemsViewFields getListItemsViewFields =
			getGetListItemsViewFields(queryFieldNames);

		if (!queryOptionsList.contains(ExpandUserFieldQueryOption.class)) {
			queryOptionsList = queryOptionsList.append(
				new ExpandUserFieldQueryOption(true));
		}

		GetListItemsQueryOptions getListItemsQueryOptions =
			getGetListItemsQueryOptions(queryOptionsList);

		try {
			getListItemsResponseGetListItemsResult = listsSoap.getListItems(
				sharepointConnectionInfo.getLibraryName(),
				SharepointConstants.VIEW_DEFAULT, getListItemsQuery,
				getListItemsViewFields, SharepointConstants.ROW_LIMIT_DEFAULT,
				getListItemsQueryOptions, SharepointConstants.WEB_ID_DEFAULT);
		}
		catch (RemoteException remoteException) {
			RemoteExceptionUtil.handleRemoteException(remoteException);
		}

		log(query, queryOptionsList, getListItemsResponseGetListItemsResult);

		return getSharepointObjects(getListItemsResponseGetListItemsResult);
	}

	protected GetListItemsQuery getGetListItemsQuery(Query query) {
		GetListItemsQuery getListItemsQuery = new GetListItemsQuery();

		Element queryElement = xmlHelper.toElement(query);

		MessageElement queryMessageElement = new MessageElement(queryElement);

		getListItemsQuery.set_any(new MessageElement[] {queryMessageElement});

		return getListItemsQuery;
	}

	protected GetListItemsQueryOptions getGetListItemsQueryOptions(
		QueryOptionsList queryOptionsList) {

		Element queryOptionsListElement = xmlHelper.toElement(queryOptionsList);

		MessageElement queryOptionsListMessageElement = new MessageElement(
			queryOptionsListElement);

		GetListItemsQueryOptions getListItemsQueryOptions =
			new GetListItemsQueryOptions();

		getListItemsQueryOptions.set_any(
			new MessageElement[] {queryOptionsListMessageElement});

		return getListItemsQueryOptions;
	}

	protected GetListItemsViewFields getGetListItemsViewFields(
		String... queryFieldNames) {

		QueryFieldsList queryFieldsList = new QueryFieldsList(
			toQueryFields(queryFieldNames));

		Element queryFieldsListElement = xmlHelper.toElement(queryFieldsList);

		MessageElement queryFieldsListMessageElement = new MessageElement(
			queryFieldsListElement);

		GetListItemsViewFields getListItemsViewFields =
			new GetListItemsViewFields();

		getListItemsViewFields.set_any(
			new MessageElement[] {queryFieldsListMessageElement});

		return getListItemsViewFields;
	}

	protected String getNodeValue(Node node, int index) {
		String nodeValue = node.getNodeValue();

		String[] parts = nodeValue.split(
			SharepointConstants.PATTERN_MULTI_VALUE_SEPARATOR);

		if (index < parts.length) {
			return parts[index];
		}

		return null;
	}

	protected Set<SharepointObject.Permission> getPermissions(
		String permissionsHexMask) {

		Set<SharepointObject.Permission> permissions = EnumSet.noneOf(
			SharepointObject.Permission.class);

		long permisssionsMask = Long.valueOf(
			permissionsHexMask.substring(2), 16);

		for (SharepointObject.Permission permission :
				SharepointObject.Permission.values()) {

			long permissionBit = permisssionsMask & permission.getMask();

			if (permissionBit != 0) {
				permissions.add(permission);
			}
		}

		return permissions;
	}

	protected List<SharepointObject> getSharepointObjects(
		GetListItemsResponseGetListItemsResult
			getListItemsResponseGetListItemsResult) {

		List<SharepointObject> sharepointObjects = new ArrayList<>();

		Element getListItemsResponseGetListItemsResultElement =
			xmlHelper.getElement(getListItemsResponseGetListItemsResult);

		Element dataElement = xmlHelper.getElement(
			"Data", getListItemsResponseGetListItemsResultElement);

		NodeList nodeList = dataElement.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);

			String localName = node.getLocalName();

			if ((localName == null) ||
				!StringUtil.equalsIgnoreCase(localName, "row")) {

				continue;
			}

			NamedNodeMap namedNodeMap = node.getAttributes();

			Node owsFileRefNode = namedNodeMap.getNamedItem("ows_FileRef");

			String path = getNodeValue(owsFileRefNode, 1);

			path = path.substring(_pathPrefixToRemoveLength);

			Node owsAuthorNode = namedNodeMap.getNamedItem("ows_Author");

			Node owsCheckedOutUserIdNode = namedNodeMap.getNamedItem(
				"ows_CheckoutUser");

			if (owsCheckedOutUserIdNode == null) {
				owsCheckedOutUserIdNode = namedNodeMap.getNamedItem(
					"ows_CheckedOutTitle");
			}

			Node owsCreatedX0020DateNode = namedNodeMap.getNamedItem(
				"ows_Created_x0020_Date");
			Node owsFSObjTypeNode = namedNodeMap.getNamedItem("ows_FSObjType");
			Node owsLastX0020ModifiedNode = namedNodeMap.getNamedItem(
				"ows_Last_x0020_Modified");
			Node owsPermMaskNode = namedNodeMap.getNamedItem("ows_PermMask");
			Node owsFileX0020SizeNode = namedNodeMap.getNamedItem(
				"ows_File_x0020_Size");

			SharepointObject sharepointObject = new SharepointObject(
				getNodeValue(owsAuthorNode, 1),
				getNodeValue(owsCheckedOutUserIdNode, 1),
				parseDate(getNodeValue(owsCreatedX0020DateNode, 1)),
				Objects.equals(
					getNodeValue(owsFSObjTypeNode, 1),
					SharepointConstants.FS_OBJ_TYPE_FOLDER),
				parseDate(getNodeValue(owsLastX0020ModifiedNode, 1)), path,
				getPermissions(owsPermMaskNode.getNodeValue()),
				GetterUtil.getLong(getNodeValue(owsFileRefNode, 0)),
				GetterUtil.getLong(getNodeValue(owsFileX0020SizeNode, 1)),
				toURL(path));

			sharepointObjects.add(sharepointObject);
		}

		return sharepointObjects;
	}

	protected void log(
		Query query, QueryOptionsList queryOptionsList,
		GetListItemsResponseGetListItemsResult
			getListItemsResponseGetListItemsResult) {

		if (!_log.isDebugEnabled()) {
			return;
		}

		_log.debug(
			StringBundler.concat(
				"Query: ", String.valueOf(query), "\nQuery options: ",
				String.valueOf(queryOptionsList), "\nResult: ",
				xmlHelper.toString(
					xmlHelper.getElement(
						getListItemsResponseGetListItemsResult))));
	}

	protected Date parseDate(String dateString) {
		try {
			DateFormat dateFormat = new SimpleDateFormat(
				SharepointConstants.SHAREPOINT_OBJECT_DATE_FORMAT_PATTERN);

			dateFormat.setTimeZone(
				SharepointConstants.SHAREPOINT_OBJECT_TIME_ZONE);

			return dateFormat.parse(dateString);
		}
		catch (ParseException parseException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to parse " + dateString +
						" to a Sharepoint object date",
					parseException);
			}

			return new Date(0);
		}
	}

	protected QueryField[] toQueryFields(String[] queryFieldNames) {
		QueryField[] queryFields = new QueryField[queryFieldNames.length];

		for (int i = 0; i < queryFieldNames.length; i++) {
			String queryFieldName = queryFieldNames[i];

			queryFields[i++] = new QueryField(queryFieldName);
		}

		return queryFields;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetSharepointObjectsByQueryOperation.class);

	private int _pathPrefixToRemoveLength;

}
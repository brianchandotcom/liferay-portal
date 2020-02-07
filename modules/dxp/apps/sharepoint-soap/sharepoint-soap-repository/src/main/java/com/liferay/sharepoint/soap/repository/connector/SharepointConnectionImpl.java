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

package com.liferay.sharepoint.soap.repository.connector;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.sharepoint.soap.repository.connector.operation.AddFolderOperation;
import com.liferay.sharepoint.soap.repository.connector.operation.AddOrUpdateFileOperation;
import com.liferay.sharepoint.soap.repository.connector.operation.BatchOperation;
import com.liferay.sharepoint.soap.repository.connector.operation.CancelCheckOutFileOperation;
import com.liferay.sharepoint.soap.repository.connector.operation.CheckInFileOperation;
import com.liferay.sharepoint.soap.repository.connector.operation.CheckOutFileOperation;
import com.liferay.sharepoint.soap.repository.connector.operation.CopySharepointObjectOperation;
import com.liferay.sharepoint.soap.repository.connector.operation.DeleteSharepointObjectOperation;
import com.liferay.sharepoint.soap.repository.connector.operation.GetInputStreamOperation;
import com.liferay.sharepoint.soap.repository.connector.operation.GetSharepointObjectByIdOperation;
import com.liferay.sharepoint.soap.repository.connector.operation.GetSharepointObjectByPathOperation;
import com.liferay.sharepoint.soap.repository.connector.operation.GetSharepointObjectsByFolderOperation;
import com.liferay.sharepoint.soap.repository.connector.operation.GetSharepointObjectsByNameOperation;
import com.liferay.sharepoint.soap.repository.connector.operation.GetSharepointObjectsByQueryOperation;
import com.liferay.sharepoint.soap.repository.connector.operation.GetSharepointVersionsOperation;
import com.liferay.sharepoint.soap.repository.connector.operation.MoveSharepointObjectOperation;
import com.liferay.sharepoint.soap.repository.connector.operation.Operation;
import com.liferay.sharepoint.soap.repository.connector.operation.PathHelper;
import com.liferay.sharepoint.soap.repository.connector.operation.URLHelper;
import com.liferay.sharepoint.soap.repository.connector.schema.query.Query;
import com.liferay.sharepoint.soap.repository.connector.schema.query.QueryOptionsList;

import com.microsoft.schemas.sharepoint.soap.CopyStub;
import com.microsoft.schemas.sharepoint.soap.ListsStub;
import com.microsoft.schemas.sharepoint.soap.VersionsStub;

import java.io.InputStream;

import java.net.URL;

import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.client.Stub;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.impl.httpclient3.HttpTransportPropertiesImpl;
import org.apache.commons.httpclient.auth.AuthPolicy;

/**
 * @author Iván Zaera
 */
public class SharepointConnectionImpl implements SharepointConnection {

	public static final long SHAREPOINT_ROOT_FOLDER_SHAREPOINT_OBJECT_ID = -1;

	public SharepointConnectionImpl(
			SharepointConnection.ServerVersion serverVersion,
			String serverProtocol, String serverAddress, int serverPort,
			String sitePath, String libraryName, String libraryPath,
			String username, String password)
		throws SharepointRuntimeException {

		_sharepointConnectionInfo = new SharepointConnectionInfo(
			serverVersion, serverProtocol, serverAddress, serverPort, sitePath,
			libraryName, libraryPath, username, password);

		initCopyStub();
		initListsStub();
		initSharepointRootFolder();
		initVersionsStub();

		buildOperations();
	}

	@Override
	public void addFile(
			String folderPath, String fileName, String changeLog,
			InputStream inputStream)
		throws SharepointException {

		String filePath = _pathHelper.buildPath(folderPath, fileName);

		changeLog = GetterUtil.getString(changeLog);

		_addOrUpdateFileOperation.execute(filePath, changeLog, inputStream);
	}

	@Override
	public void addFolder(String folderPath, String folderName)
		throws SharepointException {

		_pathHelper.validatePath(folderPath);

		_pathHelper.validateName(folderName);

		_addFolderOperation.execute(folderPath, folderName);
	}

	@Override
	public boolean cancelCheckOutFile(String filePath)
		throws SharepointException {

		_pathHelper.validatePath(filePath);

		return _cancelCheckOutFileOperation.execute(filePath);
	}

	@Override
	public boolean checkInFile(
			String filePath, String comment, CheckInType checkInType)
		throws SharepointException {

		_pathHelper.validatePath(filePath);

		return _checkInFileOperation.execute(filePath, comment, checkInType);
	}

	@Override
	public boolean checkOutFile(String filePath) throws SharepointException {
		_pathHelper.validatePath(filePath);

		return _checkOutFileOperation.execute(filePath);
	}

	@Override
	public void copySharepointObject(String path, String newPath)
		throws SharepointException {

		_pathHelper.validatePath(path);

		_pathHelper.validatePath(newPath);

		_copySharepointObjectOperation.execute(path, newPath);
	}

	@Override
	public void deleteSharepointObject(String path) throws SharepointException {
		_pathHelper.validatePath(path);

		_deleteSharepointObjectOperation.execute(path);
	}

	@Override
	public InputStream getInputStream(SharepointObject sharepointObject)
		throws SharepointException {

		return _getInputStreamOperation.execute(sharepointObject);
	}

	@Override
	public InputStream getInputStream(SharepointVersion sharepointVersion)
		throws SharepointException {

		return _getInputStreamOperation.execute(sharepointVersion);
	}

	@Override
	public SharepointConnectionInfo getSharepointConnectionInfo() {
		return _sharepointConnectionInfo;
	}

	@Override
	public SharepointObject getSharepointObject(long sharepointObjectId)
		throws SharepointException {

		if (sharepointObjectId == SHAREPOINT_ROOT_FOLDER_SHAREPOINT_OBJECT_ID) {
			return _sharepointRootFolder;
		}

		return _getSharepointObjectByIdOperation.execute(sharepointObjectId);
	}

	@Override
	public SharepointObject getSharepointObject(String path)
		throws SharepointException {

		_pathHelper.validatePath(path);

		if (path.equals(StringPool.SLASH)) {
			return _sharepointRootFolder;
		}

		return _getSharepointObjectByPathOperation.execute(path);
	}

	@Override
	public List<SharepointObject> getSharepointObjects(
			Query query, QueryOptionsList queryOptionsList)
		throws SharepointException {

		return _getSharepointObjectsByQueryOperation.execute(
			query, queryOptionsList);
	}

	@Override
	public List<SharepointObject> getSharepointObjects(String name)
		throws SharepointException {

		return _getSharepointObjectsByNameOperation.execute(name);
	}

	@Override
	public List<SharepointObject> getSharepointObjects(
			String folderPath, ObjectTypeFilter objectTypeFilter)
		throws SharepointException {

		_pathHelper.validatePath(folderPath);

		return _getSharepointObjectsByFolderOperation.execute(
			folderPath, objectTypeFilter);
	}

	@Override
	public int getSharepointObjectsCount(
			String folderPath, ObjectTypeFilter objectTypeFilter)
		throws SharepointException {

		List<SharepointObject> sharepointObjects = getSharepointObjects(
			folderPath, objectTypeFilter);

		return sharepointObjects.size();
	}

	@Override
	public List<SharepointVersion> getSharepointVersions(String filePath)
		throws SharepointException {

		_pathHelper.validatePath(filePath);

		return _getSharepointVersionsOperation.execute(filePath);
	}

	public void initSharepointRootFolder() {
		URL serviceURL = _sharepointConnectionInfo.getServiceURL();

		String libraryPath = _sharepointConnectionInfo.getLibraryPath();

		URL libraryURL = _urlHelper.toURL(serviceURL + libraryPath);

		_sharepointRootFolder = new SharepointObject(
			StringPool.BLANK, null, new Date(0), true, new Date(0),
			StringPool.SLASH, EnumSet.allOf(SharepointObject.Permission.class),
			SHAREPOINT_ROOT_FOLDER_SHAREPOINT_OBJECT_ID, 0, libraryURL);
	}

	@Override
	public void moveSharepointObject(String path, String newPath)
		throws SharepointException {

		_pathHelper.validatePath(path);

		_pathHelper.validatePath(newPath);

		_moveSharepointObjectOperation.execute(path, newPath);
	}

	@Override
	public void updateFile(String filePath, InputStream inputStream)
		throws SharepointException {

		_pathHelper.validatePath(filePath);

		_addOrUpdateFileOperation.execute(filePath, null, inputStream);
	}

	protected <O extends Operation> O buildOperation(Class<O> clazz) {
		try {
			O operation = clazz.newInstance();

			operation.setCopyStub(_copyStub);
			operation.setListStub(_listsStub);
			operation.setOperations(_operations);
			operation.setSharepointConnectionInfo(_sharepointConnectionInfo);
			operation.setVersionsStub(_versionsStub);

			_operations.put(clazz, operation);

			return operation;
		}
		catch (Exception exception) {
			throw new SharepointRuntimeException(
				"Unable to initialize operation " + clazz.getName(), exception);
		}
	}

	protected void buildOperations() {
		_addFolderOperation = buildOperation(AddFolderOperation.class);
		_addOrUpdateFileOperation = buildOperation(
			AddOrUpdateFileOperation.class);
		_batchOperation = buildOperation(BatchOperation.class);
		_cancelCheckOutFileOperation = buildOperation(
			CancelCheckOutFileOperation.class);
		_checkInFileOperation = buildOperation(CheckInFileOperation.class);
		_checkOutFileOperation = buildOperation(CheckOutFileOperation.class);
		_copySharepointObjectOperation = buildOperation(
			CopySharepointObjectOperation.class);
		_deleteSharepointObjectOperation = buildOperation(
			DeleteSharepointObjectOperation.class);
		_getInputStreamOperation = buildOperation(
			GetInputStreamOperation.class);
		_getSharepointObjectByIdOperation = buildOperation(
			GetSharepointObjectByIdOperation.class);
		_getSharepointObjectByPathOperation = buildOperation(
			GetSharepointObjectByPathOperation.class);
		_getSharepointObjectsByFolderOperation = buildOperation(
			GetSharepointObjectsByFolderOperation.class);
		_getSharepointObjectsByNameOperation = buildOperation(
			GetSharepointObjectsByNameOperation.class);
		_getSharepointObjectsByQueryOperation = buildOperation(
			GetSharepointObjectsByQueryOperation.class);
		_getSharepointVersionsOperation = buildOperation(
			GetSharepointVersionsOperation.class);
		_moveSharepointObjectOperation = buildOperation(
			MoveSharepointObjectOperation.class);

		Set<Map.Entry<Class<?>, Operation>> set = _operations.entrySet();

		Iterator<Map.Entry<Class<?>, Operation>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<Class<?>, Operation> entry = iterator.next();

			Operation operation = entry.getValue();

			operation.afterPropertiesSet();
		}
	}

	protected void configureStub(Stub stub, URL url) throws Exception {
		ServiceClient serviceClient = stub._getServiceClient();

		Options options = serviceClient.getOptions();

		HttpTransportPropertiesImpl.Authenticator authenticator =
			new HttpTransportPropertiesImpl.Authenticator();

		authenticator.setUsername(_sharepointConnectionInfo.getUsername());
		authenticator.setPassword(_sharepointConnectionInfo.getPassword());
		authenticator.setPort(url.getPort());
		authenticator.setHost(url.getHost());
		authenticator.setAuthSchemes(
			Collections.singletonList(AuthPolicy.NTLM));
		authenticator.setPreemptiveAuthentication(true);

		options.setProperty(HTTPConstants.AUTHENTICATE, authenticator);
	}

	protected URL getServiceURL(String serviceName) {
		URL url = _sharepointConnectionInfo.getServiceURL();

		return _urlHelper.toURL(
			StringBundler.concat(url, "_vti_bin/", serviceName, ".asmx"));
	}

	protected void initCopyStub() {
		URL serviceURL = getServiceURL("copy");

		try {
			_copyStub = new CopyStub(null, serviceURL.toString());

			configureStub(_copyStub, serviceURL);
		}
		catch (Exception exception) {
			throw new SharepointRuntimeException(
				"Unable to configure SOAP endpoint " + serviceURL, exception);
		}
	}

	protected void initListsStub() {
		URL serviceURL = getServiceURL("lists");

		try {
			_listsStub = new ListsStub(null, serviceURL.toExternalForm());

			configureStub(_listsStub, serviceURL);
		}
		catch (Exception exception) {
			throw new SharepointRuntimeException(
				"Unable to configure SOAP endpoint " + serviceURL, exception);
		}
	}

	protected void initVersionsStub() {
		URL serviceURL = getServiceURL("versions");

		try {
			_versionsStub = new VersionsStub(null, serviceURL.toExternalForm());

			configureStub(_versionsStub, serviceURL);
		}
		catch (Exception exception) {
			throw new SharepointRuntimeException(
				"Unable to configure SOAP endpoint " + serviceURL, exception);
		}
	}

	private static final PathHelper _pathHelper = new PathHelper();
	private static final URLHelper _urlHelper = new URLHelper();

	private AddFolderOperation _addFolderOperation;
	private AddOrUpdateFileOperation _addOrUpdateFileOperation;
	private BatchOperation _batchOperation;
	private CancelCheckOutFileOperation _cancelCheckOutFileOperation;
	private CheckInFileOperation _checkInFileOperation;
	private CheckOutFileOperation _checkOutFileOperation;
	private CopySharepointObjectOperation _copySharepointObjectOperation;
	private CopyStub _copyStub;
	private DeleteSharepointObjectOperation _deleteSharepointObjectOperation;
	private GetInputStreamOperation _getInputStreamOperation;
	private GetSharepointObjectByIdOperation _getSharepointObjectByIdOperation;
	private GetSharepointObjectByPathOperation
		_getSharepointObjectByPathOperation;
	private GetSharepointObjectsByFolderOperation
		_getSharepointObjectsByFolderOperation;
	private GetSharepointObjectsByNameOperation
		_getSharepointObjectsByNameOperation;
	private GetSharepointObjectsByQueryOperation
		_getSharepointObjectsByQueryOperation;
	private GetSharepointVersionsOperation _getSharepointVersionsOperation;
	private ListsStub _listsStub;
	private MoveSharepointObjectOperation _moveSharepointObjectOperation;
	private final Map<Class<?>, Operation> _operations = new HashMap<>();
	private final SharepointConnectionInfo _sharepointConnectionInfo;
	private SharepointObject _sharepointRootFolder;
	private VersionsStub _versionsStub;

}
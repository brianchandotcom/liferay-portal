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
import com.liferay.sharepoint.soap.repository.connector.operation.PathUtil;
import com.liferay.sharepoint.soap.repository.connector.operation.URLUtil;
import com.liferay.sharepoint.soap.repository.connector.schema.query.Query;
import com.liferay.sharepoint.soap.repository.connector.schema.query.QueryOptionsList;

import com.microsoft.schemas.sharepoint.soap.CopySoap12Stub;
import com.microsoft.schemas.sharepoint.soap.ListsSoap12Stub;
import com.microsoft.schemas.sharepoint.soap.VersionsSoap12Stub;

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
import org.apache.http.client.config.AuthSchemes;

/**
 * @author Iván Zaera
 */
public class SharepointConnectionImpl implements SharepointConnection {

	public static final long SHAREPOINT_ROOT_FOLDER_SHAREPOINT_OBJECT_ID = -1;

	public SharepointConnectionImpl(
			SharepointConnection.ServerVersion serverVersion,
			String serverProtocol, String serverAddress, int serverPort,
			String sitePath, String libraryName, String libraryPath,
			String userName, String password)
		throws SharepointRuntimeException {

		_sharepointConnectionInfo = new SharepointConnectionInfo(
			serverVersion, serverProtocol, serverAddress, serverPort, sitePath,
			libraryName, libraryPath, userName, password);

		_initCopyStub();
		_initListsStub();
		initSharepointRootFolder();
		_initVersionsStub();

		_buildOperations();
	}

	@Override
	public void addFile(
			String folderPath, String fileName, String changeLog,
			InputStream inputStream)
		throws SharepointException {

		String filePath = PathUtil.buildPath(folderPath, fileName);

		changeLog = GetterUtil.getString(changeLog);

		_addOrUpdateFileOperation.execute(filePath, changeLog, inputStream);
	}

	@Override
	public void addFolder(String folderPath, String folderName)
		throws SharepointException {

		PathUtil.validatePath(folderPath);

		PathUtil.validateName(folderName);

		_addFolderOperation.execute(folderPath, folderName);
	}

	@Override
	public boolean cancelCheckOutFile(String filePath)
		throws SharepointException {

		PathUtil.validatePath(filePath);

		return _cancelCheckOutFileOperation.execute(filePath);
	}

	@Override
	public boolean checkInFile(
			String filePath, String comment, CheckInType checkInType)
		throws SharepointException {

		PathUtil.validatePath(filePath);

		return _checkInFileOperation.execute(filePath, comment, checkInType);
	}

	@Override
	public boolean checkOutFile(String filePath) throws SharepointException {
		PathUtil.validatePath(filePath);

		return _checkOutFileOperation.execute(filePath);
	}

	@Override
	public void copySharepointObject(String path, String newPath)
		throws SharepointException {

		PathUtil.validatePath(path);

		PathUtil.validatePath(newPath);

		_copySharepointObjectOperation.execute(path, newPath);
	}

	@Override
	public void deleteSharepointObject(String path) throws SharepointException {
		PathUtil.validatePath(path);

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

		PathUtil.validatePath(path);

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

		PathUtil.validatePath(folderPath);

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

		PathUtil.validatePath(filePath);

		return _getSharepointVersionsOperation.execute(filePath);
	}

	public void initSharepointRootFolder() {
		URL serviceURL = _sharepointConnectionInfo.getServiceURL();

		String libraryPath = _sharepointConnectionInfo.getLibraryPath();

		URL libraryURL = URLUtil.toURL(serviceURL + libraryPath);

		_sharepointRootFolder = new SharepointObject(
			StringPool.BLANK, null, new Date(0), true, new Date(0),
			StringPool.SLASH, EnumSet.allOf(SharepointObject.Permission.class),
			SHAREPOINT_ROOT_FOLDER_SHAREPOINT_OBJECT_ID, 0, libraryURL);
	}

	@Override
	public void moveSharepointObject(String path, String newPath)
		throws SharepointException {

		PathUtil.validatePath(path);

		PathUtil.validatePath(newPath);

		_moveSharepointObjectOperation.execute(path, newPath);
	}

	@Override
	public void updateFile(String filePath, InputStream inputStream)
		throws SharepointException {

		PathUtil.validatePath(filePath);

		_addOrUpdateFileOperation.execute(filePath, null, inputStream);
	}

	private <O extends Operation> O _buildOperation(Class<O> clazz) {
		try {
			O operation = clazz.newInstance();

			operation.setCopySoap12Stub(_copySoap12Stub);
			operation.setListsSoap12Stub(_listsSoap12Stub);
			operation.setOperations(_operations);
			operation.setSharepointConnectionInfo(_sharepointConnectionInfo);
			operation.setVersionsSoap12Stub(_versionsSoap12Stub);

			_operations.put(clazz, operation);

			return operation;
		}
		catch (Exception exception) {
			throw new SharepointRuntimeException(
				"Unable to initialize operation " + clazz.getName(), exception);
		}
	}

	private void _buildOperations() {
		_addFolderOperation = _buildOperation(AddFolderOperation.class);
		_addOrUpdateFileOperation = _buildOperation(
			AddOrUpdateFileOperation.class);

		_buildOperation(BatchOperation.class);

		_cancelCheckOutFileOperation = _buildOperation(
			CancelCheckOutFileOperation.class);
		_checkInFileOperation = _buildOperation(CheckInFileOperation.class);
		_checkOutFileOperation = _buildOperation(CheckOutFileOperation.class);
		_copySharepointObjectOperation = _buildOperation(
			CopySharepointObjectOperation.class);
		_deleteSharepointObjectOperation = _buildOperation(
			DeleteSharepointObjectOperation.class);
		_getInputStreamOperation = _buildOperation(
			GetInputStreamOperation.class);
		_getSharepointObjectByIdOperation = _buildOperation(
			GetSharepointObjectByIdOperation.class);
		_getSharepointObjectByPathOperation = _buildOperation(
			GetSharepointObjectByPathOperation.class);
		_getSharepointObjectsByFolderOperation = _buildOperation(
			GetSharepointObjectsByFolderOperation.class);
		_getSharepointObjectsByNameOperation = _buildOperation(
			GetSharepointObjectsByNameOperation.class);
		_getSharepointObjectsByQueryOperation = _buildOperation(
			GetSharepointObjectsByQueryOperation.class);
		_getSharepointVersionsOperation = _buildOperation(
			GetSharepointVersionsOperation.class);
		_moveSharepointObjectOperation = _buildOperation(
			MoveSharepointObjectOperation.class);

		Set<Map.Entry<Class<? extends Operation>, Operation>> set =
			_operations.entrySet();

		Iterator<Map.Entry<Class<? extends Operation>, Operation>> iterator =
			set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<Class<? extends Operation>, Operation> entry =
				iterator.next();

			Operation operation = entry.getValue();

			operation.afterPropertiesSet();
		}
	}

	private void _configureStub(Stub stub, URL url) {
		ServiceClient serviceClient = stub._getServiceClient();

		Options options = serviceClient.getOptions();

		HttpTransportPropertiesImpl.Authenticator authenticator =
			new HttpTransportPropertiesImpl.Authenticator();

		authenticator.setAuthSchemes(
			Collections.singletonList(AuthSchemes.NTLM));
		authenticator.setDomain(url.getHost());
		authenticator.setHost(url.getHost());
		authenticator.setPassword(_sharepointConnectionInfo.getPassword());
		authenticator.setPort(url.getPort());
		authenticator.setPreemptiveAuthentication(true);
		authenticator.setUsername(_sharepointConnectionInfo.getUserName());

		options.setProperty(HTTPConstants.AUTHENTICATE, authenticator);
	}

	private URL _getServiceURL(String serviceName) {
		URL url = _sharepointConnectionInfo.getServiceURL();

		return URLUtil.toURL(
			StringBundler.concat(url, "_vti_bin/", serviceName, ".asmx"));
	}

	private void _initCopyStub() {
		URL serviceURL = _getServiceURL("copy");

		try {
			_copySoap12Stub = new CopySoap12Stub(null, serviceURL.toString());

			_configureStub(_copySoap12Stub, serviceURL);
		}
		catch (Exception exception) {
			throw new SharepointRuntimeException(
				"Unable to configure SOAP endpoint " + serviceURL, exception);
		}
	}

	private void _initListsStub() {
		URL serviceURL = _getServiceURL("lists");

		try {
			_listsSoap12Stub = new ListsSoap12Stub(
				null, serviceURL.toExternalForm());

			_configureStub(_listsSoap12Stub, serviceURL);
		}
		catch (Exception exception) {
			throw new SharepointRuntimeException(
				"Unable to configure SOAP endpoint " + serviceURL, exception);
		}
	}

	private void _initVersionsStub() {
		URL serviceURL = _getServiceURL("versions");

		try {
			_versionsSoap12Stub = new VersionsSoap12Stub(
				null, serviceURL.toExternalForm());

			_configureStub(_versionsSoap12Stub, serviceURL);
		}
		catch (Exception exception) {
			throw new SharepointRuntimeException(
				"Unable to configure SOAP endpoint " + serviceURL, exception);
		}
	}

	private AddFolderOperation _addFolderOperation;
	private AddOrUpdateFileOperation _addOrUpdateFileOperation;
	private CancelCheckOutFileOperation _cancelCheckOutFileOperation;
	private CheckInFileOperation _checkInFileOperation;
	private CheckOutFileOperation _checkOutFileOperation;
	private CopySharepointObjectOperation _copySharepointObjectOperation;
	private CopySoap12Stub _copySoap12Stub;
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
	private ListsSoap12Stub _listsSoap12Stub;
	private MoveSharepointObjectOperation _moveSharepointObjectOperation;
	private final Map<Class<? extends Operation>, Operation> _operations =
		new HashMap<>();
	private final SharepointConnectionInfo _sharepointConnectionInfo;
	private SharepointObject _sharepointRootFolder;
	private VersionsSoap12Stub _versionsSoap12Stub;

}
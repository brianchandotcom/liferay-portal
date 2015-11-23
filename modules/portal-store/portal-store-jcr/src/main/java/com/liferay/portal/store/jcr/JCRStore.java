/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.store.jcr;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.store.jcr.configuration.JCRStoreConfiguration;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.NoSuchFileException;
import com.liferay.portlet.documentlibrary.store.BaseStore;
import com.liferay.portlet.documentlibrary.store.Store;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFactory;
import javax.jcr.Workspace;
import javax.jcr.nodetype.NodeType;
import javax.jcr.version.Version;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionIterator;
import javax.jcr.version.VersionManager;

import org.apache.commons.lang.StringUtils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Michael Young
 * @author Brian Wing Shun Chan
 * @author Edward Han
 * @author Manuel de la Peña
 */
@Component(
	configurationPid = "com.liferay.portal.store.jcr.configuration.JCRStoreConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	property = "store.type=com.liferay.portal.store.jcr.JCRStore",
	service = Store.class
)
public class JCRStore extends BaseStore {

	@Override
	public void addDirectory(
		long companyId, long repositoryId, String dirName) {

		Session session = null;

		try {
			session = _jcrFactoryWrapper.createSession();

			Node rootNode = getRootNode(session, companyId);

			Node repositoryNode = getFolderNode(rootNode, repositoryId);

			if (repositoryNode.hasNode(dirName)) {
				return;
			}

			String[] dirNameArray = StringUtil.split(dirName, '/');

			Node dirNode = repositoryNode;

			for (String nodeName : dirNameArray) {
				if (Validator.isNotNull(nodeName)) {
					if (dirNode.hasNode(nodeName)) {
						dirNode = dirNode.getNode(nodeName);
					}
					else {
						dirNode = dirNode.addNode(
							nodeName, JCRConstants.NT_FOLDER);
					}
				}
			}

			session.save();
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			_jcrFactoryWrapper.closeSession(session);
		}
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, InputStream is)
		throws DuplicateFileException {

		Session session = null;

		try {
			session = _jcrFactoryWrapper.createSession();

			Workspace workspace = session.getWorkspace();

			VersionManager versionManager = workspace.getVersionManager();

			Node rootNode = getRootNode(session, companyId);

			Node repositoryNode = getFolderNode(rootNode, repositoryId);

			if (fileName.contains(StringPool.SLASH)) {
				String path = fileName.substring(
					0, fileName.lastIndexOf(StringPool.SLASH));

				fileName = fileName.substring(path.length() + 1);

				repositoryNode = getFolderNode(repositoryNode, path);
			}

			if (repositoryNode.hasNode(fileName)) {
				throw new DuplicateFileException(
					companyId, repositoryId, fileName);
			}

			Node fileNode = repositoryNode.addNode(
				fileName, JCRConstants.NT_FILE);

			Node contentNode = fileNode.addNode(
				JCRConstants.JCR_CONTENT, JCRConstants.NT_RESOURCE);

			contentNode.addMixin(JCRConstants.MIX_VERSIONABLE);
			contentNode.setProperty(
				JCRConstants.JCR_MIME_TYPE, ContentTypes.TEXT_PLAIN);

			ValueFactory valueFactory = session.getValueFactory();

			Binary binary = valueFactory.createBinary(is);

			contentNode.setProperty(JCRConstants.JCR_DATA, binary);

			contentNode.setProperty(
				JCRConstants.JCR_LAST_MODIFIED, Calendar.getInstance());

			session.save();

			Version version = versionManager.checkin(contentNode.getPath());

			VersionHistory versionHistory = versionManager.getVersionHistory(
				contentNode.getPath());

			versionHistory.addVersionLabel(
				version.getName(), VERSION_DEFAULT, false);
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			_jcrFactoryWrapper.closeSession(session);
		}
	}

	@Override
	public void checkRoot(long companyId) {
		Session session = null;

		try {
			session = _jcrFactoryWrapper.createSession();

			getRootNode(session, companyId);

			session.save();
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			_jcrFactoryWrapper.closeSession(session);
		}
	}

	@Override
	public void deleteDirectory(
		long companyId, long repositoryId, String dirName) {

		Session session = null;

		try {
			session = _jcrFactoryWrapper.createSession();

			Node rootNode = getRootNode(session, companyId);

			Node repositoryNode = getFolderNode(rootNode, repositoryId);

			if (dirName.equals(StringPool.SLASH)) {
				dirName = StringPool.PERIOD;
			}

			Node dirNode = repositoryNode.getNode(dirName);

			dirNode.remove();

			session.save();
		}
		catch (PathNotFoundException pnfe) {
			logFailedDeletion(companyId, repositoryId, dirName, pnfe);
		}
		catch (RepositoryException re) {
			String message = GetterUtil.getString(re.getMessage());

			if (message.contains("failed to resolve path")) {
				logFailedDeletion(companyId, repositoryId, dirName, re);
			}
			else {
				throw new SystemException(re);
			}
		}
		finally {
			_jcrFactoryWrapper.closeSession(session);
		}
	}

	@Override
	public void deleteFile(long companyId, long repositoryId, String fileName) {
		Session session = null;

		// A bug in Jackrabbit requires us to create a dummy node and delete the
		// version tree manually to successfully delete a file

		// Create a dummy node

		try {
			session = _jcrFactoryWrapper.createSession();

			Workspace workspace = session.getWorkspace();

			VersionManager versionManager = workspace.getVersionManager();

			Node rootNode = getRootNode(session, companyId);

			Node repositoryNode = getFolderNode(rootNode, repositoryId);

			Node fileNode = repositoryNode.getNode(fileName);

			Node contentNode = fileNode.getNode(JCRConstants.JCR_CONTENT);

			versionManager.checkout(contentNode.getPath());

			contentNode.setProperty(
				JCRConstants.JCR_MIME_TYPE, ContentTypes.TEXT_PLAIN);
			contentNode.setProperty(JCRConstants.JCR_DATA, StringPool.BLANK);
			contentNode.setProperty(
				JCRConstants.JCR_LAST_MODIFIED, Calendar.getInstance());

			session.save();

			Version version = versionManager.checkin(contentNode.getPath());

			VersionHistory versionHistory = versionManager.getVersionHistory(
				contentNode.getPath());

			versionHistory.addVersionLabel(version.getName(), "0.0", false);
		}
		catch (PathNotFoundException pnfe) {
			logFailedDeletion(companyId, repositoryId, fileName, pnfe);

			return;
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			_jcrFactoryWrapper.closeSession(session);
		}

		// Delete version tree

		try {
			session = _jcrFactoryWrapper.createSession();

			Workspace workspace = session.getWorkspace();

			VersionManager versionManager = workspace.getVersionManager();

			Node rootNode = getRootNode(session, companyId);

			Node repositoryNode = getFolderNode(rootNode, repositoryId);

			Node fileNode = repositoryNode.getNode(fileName);

			Node contentNode = fileNode.getNode(JCRConstants.JCR_CONTENT);

			VersionHistory versionHistory = versionManager.getVersionHistory(
				contentNode.getPath());

			VersionIterator itr = versionHistory.getAllVersions();

			while (itr.hasNext()) {
				Version version = itr.nextVersion();

				if (itr.getPosition() == itr.getSize()) {
					break;
				}
				else {
					if (!StringUtils.equals(
							JCRConstants.JCR_ROOT_VERSION, version.getName())) {

						versionHistory.removeVersion(version.getName());
					}
				}
			}

			session.save();
		}
		catch (PathNotFoundException pnfe) {
			logFailedDeletion(companyId, repositoryId, fileName, pnfe);

			return;
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			_jcrFactoryWrapper.closeSession(session);
		}

		// Delete file

		try {
			session = _jcrFactoryWrapper.createSession();

			Node rootNode = getRootNode(session, companyId);

			Node repositoryNode = getFolderNode(rootNode, repositoryId);

			Node fileNode = repositoryNode.getNode(fileName);

			fileNode.remove();

			session.save();
		}
		catch (PathNotFoundException pnfe) {
			logFailedDeletion(companyId, repositoryId, fileName, pnfe);
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			_jcrFactoryWrapper.closeSession(session);
		}
	}

	@Override
	public void deleteFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		Session session = null;

		try {
			session = _jcrFactoryWrapper.createSession();

			Workspace workspace = session.getWorkspace();

			VersionManager versionManager = workspace.getVersionManager();

			Node rootNode = getRootNode(session, companyId);

			Node repositoryNode = getFolderNode(rootNode, repositoryId);

			Node fileNode = repositoryNode.getNode(fileName);

			Node contentNode = fileNode.getNode(JCRConstants.JCR_CONTENT);

			VersionHistory versionHistory = versionManager.getVersionHistory(
				contentNode.getPath());

			if (!versionHistory.hasVersionLabel(versionLabel)) {
				logFailedDeletion(
					companyId, repositoryId, fileName, versionLabel);
			}

			Version version = versionHistory.getVersionByLabel(versionLabel);

			Version linearPredecessorVersion = version.getLinearPredecessor();

			if (version.getLinearSuccessor() == null) {
				Version restoreVersion = linearPredecessorVersion;

				if (Validator.equals(
						JCRConstants.JCR_ROOT_VERSION,
						linearPredecessorVersion.getName())) {

					versionManager.checkout(contentNode.getPath());

					restoreVersion = versionManager.checkin(
						contentNode.getPath());
				}

				versionManager.restore(restoreVersion, true);
			}

			versionHistory.removeVersion(version.getName());

			session.save();
		}
		catch (PathNotFoundException pnfe) {
			logFailedDeletion(
				companyId, repositoryId, fileName, versionLabel, pnfe);
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			_jcrFactoryWrapper.closeSession(session);
		}
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws NoSuchFileException {

		Session session = null;

		try {
			session = _jcrFactoryWrapper.createSession();

			Node contentNode = getFileContentNode(
				session, companyId, repositoryId, fileName, versionLabel);

			Property property = contentNode.getProperty(JCRConstants.JCR_DATA);

			Value value = property.getValue();

			Binary binary = value.getBinary();

			if (session instanceof Map) {
				Map<String, Binary> mapSession = (Map<String, Binary>)session;

				mapSession.put(fileName, binary);
			}

			return binary.getStream();
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			_jcrFactoryWrapper.closeSession(session);
		}
	}

	@Override
	public String[] getFileNames(long companyId, long repositoryId) {
		List<String> fileNames = new ArrayList<>();

		Session session = null;

		try {
			session = _jcrFactoryWrapper.createSession();

			Node rootNode = getRootNode(session, companyId);

			Node repositoryNode = getFolderNode(rootNode, repositoryId);

			NodeIterator itr = repositoryNode.getNodes();

			while (itr.hasNext()) {
				Node node = itr.nextNode();

				NodeType primaryNodeType = node.getPrimaryNodeType();

				String primaryNodeTypeName = primaryNodeType.getName();

				if (primaryNodeTypeName.equals(JCRConstants.NT_FOLDER)) {
					doGetFileNames(fileNames, node.getName(), node);
				}
				else if (primaryNodeTypeName.equals(JCRConstants.NT_FILE)) {
					fileNames.add(node.getName());
				}
			}
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			_jcrFactoryWrapper.closeSession(session);
		}

		return fileNames.toArray(new String[fileNames.size()]);
	}

	@Override
	public String[] getFileNames(
		long companyId, long repositoryId, String dirName) {

		List<String> fileNames = new ArrayList<>();

		Session session = null;

		try {
			session = _jcrFactoryWrapper.createSession();

			Node rootNode = getRootNode(session, companyId);

			Node repositoryNode = getFolderNode(rootNode, repositoryId);

			Node dirNode = repositoryNode.getNode(dirName);

			doGetFileNames(fileNames, dirName, dirNode);
		}
		catch (PathNotFoundException pnfe) {
			return new String[0];
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			_jcrFactoryWrapper.closeSession(session);
		}

		return fileNames.toArray(new String[fileNames.size()]);
	}

	@Override
	public long getFileSize(long companyId, long repositoryId, String fileName)
		throws NoSuchFileException {

		long size = 0;

		Session session = null;

		try {
			session = _jcrFactoryWrapper.createSession();

			Node contentNode = getFileContentNode(
				session, companyId, repositoryId, fileName, StringPool.BLANK);

			Property property = contentNode.getProperty(JCRConstants.JCR_DATA);

			size = property.getLength();
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			_jcrFactoryWrapper.closeSession(session);
		}

		return size;
	}

	@Override
	public boolean hasDirectory(
		long companyId, long repositoryId, String dirName) {

		Session session = null;

		try {
			session = _jcrFactoryWrapper.createSession();

			Node rootNode = getRootNode(session, companyId);

			Node repositoryNode = getFolderNode(rootNode, repositoryId);

			repositoryNode.getNode(dirName);

			return true;
		}
		catch (PathNotFoundException pnfe) {
			return false;
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			_jcrFactoryWrapper.closeSession(session);
		}
	}

	@Override
	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		try {
			getFileContentNode(companyId, repositoryId, fileName, versionLabel);
		}
		catch (NoSuchFileException nsfe) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsfe, nsfe);
			}

			return false;
		}

		return true;
	}

	@Override
	public void move(String srcDir, String destDir) {
		Session session = null;

		try {
			session = _jcrFactoryWrapper.createSession();

			session.move(srcDir, destDir);

			session.save();
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			_jcrFactoryWrapper.closeSession(session);
		}
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName)
		throws DuplicateFileException, NoSuchFileException {

		if (repositoryId == newRepositoryId) {
			throw new DuplicateFileException(
				companyId, newRepositoryId, fileName);
		}

		Session session = null;

		try {
			session = _jcrFactoryWrapper.createSession();

			Node rootNode = getRootNode(session, companyId);

			Node repositoryNode = getFolderNode(rootNode, repositoryId);

			if (fileName.contains(StringPool.SLASH)) {
				String path = fileName.substring(
					0, fileName.lastIndexOf(StringPool.SLASH));

				fileName = fileName.substring(path.length() + 1);

				repositoryNode = getFolderNode(repositoryNode, path);
			}

			Node newRepositoryNode = getFolderNode(rootNode, newRepositoryId);

			if (newRepositoryNode.hasNode(fileName)) {
				throw new DuplicateFileException(
					companyId, newRepositoryId, fileName);
			}

			Node fileNode = repositoryNode.getNode(fileName);

			Node contentNode = fileNode.getNode(JCRConstants.JCR_CONTENT);

			String contentNodePath = contentNode.getPath();

			Node newFileNode = newRepositoryNode.addNode(
				fileName, JCRConstants.NT_FILE);

			String newContentNodePath = newFileNode.getPath().concat(
				StringPool.SLASH).concat(JCRConstants.JCR_CONTENT);

			session.move(contentNodePath, newContentNodePath);

			fileNode.remove();

			session.save();
		}
		catch (PathNotFoundException pnfe) {
			throw new NoSuchFileException(
				companyId, repositoryId, fileName, pnfe);
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			_jcrFactoryWrapper.closeSession(session);
		}
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName)
		throws DuplicateFileException, NoSuchFileException {

		if (fileName.equals(newFileName)) {
			throw new DuplicateFileException(
				companyId, repositoryId, newFileName);
		}

		Session session = null;

		try {
			session = _jcrFactoryWrapper.createSession();

			Node rootNode = getRootNode(session, companyId);

			Node repositoryNode = getFolderNode(rootNode, repositoryId);

			if (fileName.contains(StringPool.SLASH)) {
				String path = fileName.substring(
					0, fileName.lastIndexOf(StringPool.SLASH));

				fileName = fileName.substring(path.length() + 1);

				repositoryNode = getFolderNode(repositoryNode, path);
			}

			if (repositoryNode.hasNode(newFileName)) {
				throw new DuplicateFileException(
					companyId, repositoryId, newFileName);
			}

			Node fileNode = repositoryNode.getNode(fileName);

			Node contentNode = fileNode.getNode(JCRConstants.JCR_CONTENT);

			String contentNodePath = contentNode.getPath();

			Node newFileNode = repositoryNode.addNode(
				newFileName, JCRConstants.NT_FILE);

			String newContentNodePath = newFileNode.getPath().concat(
				StringPool.SLASH).concat(JCRConstants.JCR_CONTENT);

			session.move(contentNodePath, newContentNodePath);

			fileNode.remove();

			session.save();
		}
		catch (PathNotFoundException pnfe) {
			throw new NoSuchFileException(
				companyId, repositoryId, fileName, pnfe);
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			_jcrFactoryWrapper.closeSession(session);
		}
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream is)
		throws DuplicateFileException, NoSuchFileException {

		Session session = null;

		try {
			session = _jcrFactoryWrapper.createSession();

			Workspace workspace = session.getWorkspace();

			VersionManager versionManager = workspace.getVersionManager();

			Node rootNode = getRootNode(session, companyId);

			Node repositoryNode = getFolderNode(rootNode, repositoryId);

			if (fileName.contains(StringPool.SLASH)) {
				String path = fileName.substring(
					0, fileName.lastIndexOf(StringPool.SLASH));

				fileName = fileName.substring(path.length() + 1);

				repositoryNode = getFolderNode(repositoryNode, path);
			}

			Node fileNode = repositoryNode.getNode(fileName);

			Node contentNode = fileNode.getNode(JCRConstants.JCR_CONTENT);

			versionManager.checkout(contentNode.getPath());

			contentNode.setProperty(
				JCRConstants.JCR_MIME_TYPE, ContentTypes.TEXT_PLAIN);

			ValueFactory valueFactory = session.getValueFactory();

			Binary binary = valueFactory.createBinary(is);

			contentNode.setProperty(JCRConstants.JCR_DATA, binary);

			contentNode.setProperty(
				JCRConstants.JCR_LAST_MODIFIED, Calendar.getInstance());

			session.save();

			Version version = versionManager.checkin(contentNode.getPath());

			VersionHistory versionHistory = versionManager.getVersionHistory(
				contentNode.getPath());

			if (versionHistory.hasVersionLabel(versionLabel)) {
				throw new DuplicateFileException(
					companyId, repositoryId, fileName, versionLabel);
			}

			versionHistory.addVersionLabel(
				version.getName(), versionLabel,
				_jcrStoreConfiguration.moveVersionLabels());
		}
		catch (PathNotFoundException pnfe) {
			throw new NoSuchFileException(
				companyId, repositoryId, fileName, versionLabel, pnfe);
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			_jcrFactoryWrapper.closeSession(session);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties)
		throws RepositoryException {

		_jcrStoreConfiguration = Configurable.createConfigurable(
			JCRStoreConfiguration.class, properties);

		try {
			_jcrFactoryWrapper = new JCRFactoryWrapper(_jcrStoreConfiguration);

			_jcrFactoryWrapper.prepare();

			if (_jcrStoreConfiguration.initializeOnStartup()) {
				_jcrFactoryWrapper.initialize();
			}
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@Deactivate
	protected void deactivate() {
		_jcrFactoryWrapper.shutdown();

		_jcrFactoryWrapper = null;
	}

	protected void doGetFileNames(
			List<String> fileNames, String dirName, Node node)
		throws RepositoryException {

		NodeType primaryNodeType = node.getPrimaryNodeType();

		String primaryNodeTypeName = primaryNodeType.getName();

		if (primaryNodeTypeName.equals(JCRConstants.NT_FOLDER)) {
			NodeIterator itr = node.getNodes();

			while (itr.hasNext()) {
				Node curNode = itr.nextNode();

				String subDirName = null;

				if (Validator.isBlank(dirName)) {
					subDirName = curNode.getName();
				}
				else {
					subDirName = dirName + StringPool.SLASH + curNode.getName();
				}

				doGetFileNames(fileNames, subDirName, curNode);
			}
		}
		else if (primaryNodeTypeName.equals(JCRConstants.NT_FILE)) {
			fileNames.add(dirName);
		}
	}

	protected Node getFileContentNode(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws NoSuchFileException {

		Node contentNode = null;

		Session session = null;

		try {
			session = _jcrFactoryWrapper.createSession();

			contentNode = getFileContentNode(
				session, companyId, repositoryId, fileName, versionLabel);
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			_jcrFactoryWrapper.closeSession(session);
		}

		return contentNode;
	}

	protected Node getFileContentNode(
			Session session, long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws NoSuchFileException {

		Node contentNode = null;

		try {
			Workspace workspace = session.getWorkspace();

			VersionManager versionManager = workspace.getVersionManager();

			Node rootNode = getRootNode(session, companyId);

			Node repositoryNode = getFolderNode(rootNode, repositoryId);

			Node fileNode = repositoryNode.getNode(fileName);

			contentNode = fileNode.getNode(JCRConstants.JCR_CONTENT);

			if (Validator.isNotNull(versionLabel)) {
				VersionHistory versionHistory =
					versionManager.getVersionHistory(contentNode.getPath());

				if (!versionHistory.hasVersionLabel(versionLabel)) {
					throw new NoSuchFileException(
						companyId, repositoryId, fileName, versionLabel);
				}

				Version version = versionHistory.getVersionByLabel(
					versionLabel);

				contentNode = version.getNode(JCRConstants.JCR_FROZEN_NODE);
			}
		}
		catch (PathNotFoundException pnfe) {
			throw new NoSuchFileException(
				companyId, repositoryId, fileName, versionLabel);
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}

		return contentNode;
	}

	protected Node getFolderNode(Node node, long name)
		throws RepositoryException {

		return getFolderNode(node, String.valueOf(name));
	}

	protected Node getFolderNode(Node node, String name)
		throws RepositoryException {

		if (name.contains(StringPool.SLASH)) {
			String[] nameParts = name.split(StringPool.SLASH, 2);

			node = getFolderNode(node, nameParts[0]);

			return getFolderNode(node, nameParts[1]);
		}

		Node folderNode = null;

		if (node.hasNode(name)) {
			folderNode = node.getNode(name);
		}
		else {
			folderNode = node.addNode(name, JCRConstants.NT_FOLDER);
		}

		return folderNode;
	}

	protected Node getRootNode(Session session, long companyId)
		throws RepositoryException {

		Node companyNode = getFolderNode(session.getRootNode(), companyId);

		return getFolderNode(
			companyNode, _jcrStoreConfiguration.nodeDocumentlibrary());
	}

	@Modified
	protected void modified(Map<String, Object> properties)
		throws RepositoryException {

		deactivate();

		activate(properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(JCRStore.class);

	private JCRFactoryWrapper _jcrFactoryWrapper;
	private volatile JCRStoreConfiguration _jcrStoreConfiguration;

}
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.apache.directory;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.directory.server.constants.ServerDNConstants;
import org.apache.directory.server.core.DefaultDirectoryService;
import org.apache.directory.server.core.api.CoreSession;
import org.apache.directory.server.core.api.DirectoryService;
import org.apache.directory.server.core.api.InstanceLayout;
import org.apache.directory.server.core.api.partition.Partition;
import org.apache.directory.server.core.api.schema.SchemaPartition;
import org.apache.directory.server.core.partition.impl.btree.jdbm.JdbmIndex;
import org.apache.directory.server.core.partition.impl.btree.jdbm.JdbmPartition;
import org.apache.directory.server.core.partition.ldif.LdifPartition;
import org.apache.directory.server.ldap.LdapServer;
import org.apache.directory.server.protocol.shared.transport.TcpTransport;
import org.apache.directory.server.xdbm.Index;
import org.apache.directory.shared.ldap.model.entry.Entry;
import org.apache.directory.shared.ldap.model.exception.LdapInvalidDnException;
import org.apache.directory.shared.ldap.model.name.Dn;
import org.apache.directory.shared.ldap.model.schema.SchemaManager;
import org.apache.directory.shared.ldap.schemaextractor.SchemaLdifExtractor;
import org.apache.directory.shared.ldap.schemaextractor.impl.DefaultSchemaLdifExtractor;
import org.apache.directory.shared.ldap.schemaloader.LdifSchemaLoader;
import org.apache.directory.shared.ldap.schemamanager.impl.DefaultSchemaManager;

/**
 * @author Manuel de la Peña
 */
public class LiferayEmbeddedApacheDirectoryServer {

	public LiferayEmbeddedApacheDirectoryServer(List<String> partitions)
		throws Exception {

		this(System.getProperty("java.io.tmpdir") + "/apacheds", partitions);
	}

	public LiferayEmbeddedApacheDirectoryServer(
			String workDir, List<String> partitions)
		throws Exception {

		_workDir = new File(workDir);

		FileUtil.mkdirs(workDir);

		_initDirectoryService(partitions);

		_configureServer();
	}

	public void addPartitions(List<String> indexablePartitions)
		throws Exception {

		for (String indexablePartition : indexablePartitions) {
			try {
				_partitions.add(_buildIndexablePartition(indexablePartition));
			}
			catch (LdapInvalidDnException e) {
				throw new RuntimeException(
					"The Apache directory could not be initializated");
			}
		}
	}

	public void removeAllPartitions() throws Exception {
		for (IndexablePartition indexablePartition : _partitions) {
			_service.removePartition(indexablePartition.getPartition());
		}
	}

	public void startServer() throws Exception {
		if (_server.isStarted()) {
			return;
		}

		_server.start();
	}

	public void stopServer() throws Exception {
		if (!_server.isStarted()) {
			return;
		}

		_server.stop();

		_service.shutdown();
	}

	private void _addRootEntries() throws Exception {
		CoreSession adminSession = _service.getAdminSession();

		for (IndexablePartition indexablePartition : _partitions) {

			// Inject the root entry

			if (!adminSession.exists(indexablePartition.getSuffixDn())) {
				Dn dnRoot = new Dn(
					PrefsPropsUtil.getString(PropsKeys.LDAP_BASE_DN));

				Entry rootEntry = _service.newEntry(dnRoot);
				rootEntry.add(
					"objectClass", "top", "domain", "extensibleObject");
				rootEntry.add("dc", "root");
				rootEntry.add("userPassword", "test");

				_service.getAdminSession().add(rootEntry);
			}
		}
	}

	private IndexablePartition _buildIndexablePartition(String stringPartition)
		throws Exception {

		String[] partitionItems = StringUtil.split(
			stringPartition, StringPool.COLON);

		if (partitionItems.length != 3) {
			throw new RuntimeException(
				"Partition " + stringPartition + " has an incorrect format");
		}

		String[] attributes = StringUtil.split(
			partitionItems[2], StringPool.COMMA);
		String partitionDn = partitionItems[1];
		String partitionId = partitionItems[0];

		JdbmPartition partition = new JdbmPartition(
			_service.getSchemaManager());

		partition.setId(partitionId);
		partition.setPartitionPath(new File(_workDir, partitionId).toURI());
		partition.setSuffixDn(new Dn(partitionDn));

		Set<Index<?, Entry, Long>> indexedAttributes =
			new HashSet<Index<?, Entry, Long>>();

		for (String attribute : attributes) {
			indexedAttributes.add(new JdbmIndex<String, Entry>(attribute));
		}

		partition.setIndexedAttributes(indexedAttributes);

		_service.addPartition(partition);

		return new IndexablePartition(partition);
	}

	private void _configureServer() throws SystemException {
		_server = new LdapServer();

		int serverPort = PrefsPropsUtil.getInteger(
			PropsKeys.LDAP_APACHE_DIRECTORY_PORT);

		_server.setTransports(new TcpTransport(serverPort));
		_server.setDirectoryService(_service);
	}

	private Partition _createPartition(String partitionId, String partitionDn)
		throws Exception {

		JdbmPartition partition = new JdbmPartition(
			_service.getSchemaManager());

		partition.setId(partitionId);
		partition.setPartitionPath(new File(_workDir, partitionId).toURI());
		partition.setSuffixDn(new Dn(partitionDn));

		return partition;
	}

	private void _initDirectoryService(List<String> partitions)
		throws Exception {

		// Initialize the LDAP service

		_service = new DefaultDirectoryService();

		_service.setSchemaManager(new DefaultSchemaManager());
		_service.setInstanceLayout(new InstanceLayout(_workDir));

		_initSchemaPartition();

		// Adding this partition is MANDATORY

		Partition systemPartition = _createPartition(
			"system", ServerDNConstants.SYSTEM_DN);

		_service.setSystemPartition(systemPartition);

		_service.getChangeLog().setEnabled(false);
		_service.setDenormalizeOpAttrsEnabled(true);

		addPartitions(partitions);

		_service.startup();

		_addRootEntries();
	}

	private void _initSchemaPartition() throws Exception {
		SchemaManager schemaManager = _service.getSchemaManager();

		SchemaPartition schemaPartition = new SchemaPartition(schemaManager);

		_service.setSchemaPartition(schemaPartition);

		LdifPartition ldifPartition = new LdifPartition(schemaManager);

		File schemaDir = new File(_workDir, "schema");

		ldifPartition.setPartitionPath(schemaDir.toURI());

		// Extract the schema on disk and load the registries

		if (!schemaDir.exists()) {
			SchemaLdifExtractor extractor = new DefaultSchemaLdifExtractor(
				_workDir);

			extractor.extractOrCopy(true);
		}

		schemaPartition.setWrappedPartition(ldifPartition);

		schemaManager.setSchemaLoader(new LdifSchemaLoader(schemaDir));

		// We have to load the schema now, otherwise we won't be able
		// to initialize the Partitions, as we won't be able to parse
		// and normalize their suffix DN

		schemaManager.loadAllEnabled();

		schemaPartition.setSchemaManager(schemaManager);

		List<Throwable> errors = schemaManager.getErrors();

		if (errors.size() != 0) {
			throw new Exception("Schema load failed : " + errors);
		}
	}

	private List<IndexablePartition> _partitions =
		new ArrayList<IndexablePartition>();

	private LdapServer _server = null;
	private DirectoryService _service = null;
	private File _workDir = null;

}
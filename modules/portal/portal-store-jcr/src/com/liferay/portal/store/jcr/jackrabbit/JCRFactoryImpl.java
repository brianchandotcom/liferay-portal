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

package com.liferay.portal.store.jcr.jackrabbit;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.store.jcr.JCRFactory;
import com.liferay.portal.store.jcr.configuration.JCRStoreConfiguration;

import java.io.File;
import java.io.IOException;

import javax.jcr.Credentials;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.apache.jackrabbit.core.TransientRepository;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

/**
 * @author Michael Young
 * @author Manuel de la Peña
 */
@Component(
	configurationPid = "com.liferay.portal.store.jcr.configuration.JCRStoreConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true
)
public class JCRFactoryImpl implements JCRFactory {

	@Override
	public Session createSession(String workspaceName)
		throws RepositoryException {

		char[] credentialsPassword =
			_jcrStoreConfiguration.jackrabbitCredentialsPassword().
				toCharArray();

		Credentials credentials = new SimpleCredentials(
			_jcrStoreConfiguration.jackrabbitCredentialsUsername(),
			credentialsPassword);

		Session session = null;

		try {
			session = _transientRepository.login(credentials, workspaceName);
		}
		catch (RepositoryException re) {
			_log.error("Could not login to the workspace " + workspaceName);

			throw re;
		}

		return session;
	}

	@Override
	public void initialize() throws RepositoryException {
		Session session = null;

		try {
			session = createSession(null);
		}
		catch (RepositoryException re) {
			_log.error("Could not initialize Jackrabbit");

			throw re;
		}
		finally {
			if (session != null) {
				session.logout();
			}
		}

		_initialized = true;
	}

	@Override
	public void prepare() throws RepositoryException {
		try {
			FileUtil.mkdirs(
				_jcrStoreConfiguration.jackrabbitRepositoryRoot());

			File tempFile = new File(
				SystemProperties.get(SystemProperties.TMP_DIR) +
					File.separator + Time.getTimestamp());

			String repositoryXmlPath =
				"com/liferay/portal/store/jcr/jackrabbit/dependencies/" +
					"repository-ext.xml";

			ClassLoader classLoader = getClass().getClassLoader();

			if (classLoader.getResource(repositoryXmlPath) == null) {
				repositoryXmlPath =
					"com/liferay/portal/store/jcr/jackrabbit/dependencies/" +
						"repository.xml";
			}

			FileUtil.write(
				tempFile, classLoader.getResourceAsStream(repositoryXmlPath));

			FileUtil.copyFile(
				tempFile,
				new File(_jcrStoreConfiguration.jackrabbitConfigFilePath()));

			tempFile.delete();
		}
		catch (IOException ioe) {
			_log.error("Could not prepare Jackrabbit directory");

			throw new RepositoryException(ioe);
		}
	}

	@Override
	public void shutdown() {
		if (_initialized) {
			_transientRepository.shutdown();
		}

		_initialized = false;
	}

	protected JCRFactoryImpl() throws Exception {
		String repositoryHome =
			_jcrStoreConfiguration.jackrabbitRepositoryHome();

		try {
			_transientRepository = new TransientRepository(
				_jcrStoreConfiguration.jackrabbitConfigFilePath(),
				repositoryHome);
		}
		catch (Exception e) {
			_log.error("Problem initializing Jackrabbit JCR.", e);

			throw e;
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Jackrabbit JCR intialized with config file path " +
					_jcrStoreConfiguration.jackrabbitConfigFilePath() +
						" and repository home " + repositoryHome);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(JCRFactoryImpl.class);

	private boolean _initialized;
	private volatile JCRStoreConfiguration _jcrStoreConfiguration;
	private final TransientRepository _transientRepository;

}
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
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
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

/**
 * @author Michael Young
 * @author Manuel de la Peña
 */
public class JCRFactoryImpl implements JCRFactory {

	public JCRFactoryImpl(JCRStoreConfiguration jcrStoreConfiguration)
		throws RepositoryException {

		_jcrStoreConfiguration = jcrStoreConfiguration;

		try {
			_transientRepository = new TransientRepository(
				_jcrStoreConfiguration.jackrabbitConfigFilePath(),
				_jcrStoreConfiguration.jackrabbitRepositoryHome());
		}
		catch (Exception e) {
			_log.error("Problem initializing Jackrabbit JCR.", e);

			throw e;
		}

		if (_log.isInfoEnabled()) {
			StringBundler sb = new StringBundler(4);

			sb.append("Jackrabbit JCR intialized with config file path ");
			sb.append(_jcrStoreConfiguration.jackrabbitConfigFilePath());
			sb.append(" and repository home ");
			sb.append(_jcrStoreConfiguration.jackrabbitRepositoryHome());

			_log.info(sb.toString());
		}
	}

	@Override
	public Session createSession(String workspaceName)
		throws RepositoryException {

		String credentialsPassword =
			_jcrStoreConfiguration.jackrabbitCredentialsPassword();

		Credentials credentials = new SimpleCredentials(
			_jcrStoreConfiguration.jackrabbitCredentialsUsername(),
			credentialsPassword.toCharArray());

		Session session = null;

		try {
			session = _transientRepository.login(credentials, workspaceName);
		}
		catch (RepositoryException re) {
			_log.error("Unable to login to the workspace " + workspaceName);

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
			_log.error("Unable to initialize Jackrabbit");

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
			String path = _jcrStoreConfiguration.jackrabbitRepositoryRoot();

			File repositoryRoot = new File(path);

			if (!repositoryRoot.isAbsolute()) {
				path = PropsUtil.get(
					PropsKeys.LIFERAY_HOME) + StringPool.SLASH + path;
			}

			FileUtil.mkdirs(path);

			File tempFile = new File(
				SystemProperties.get(SystemProperties.TMP_DIR) +
					File.separator + Time.getTimestamp());

			String repositoryXmlPath =
				"com/liferay/portal/store/jcr/jackrabbit/dependencies" +
					"/repository-ext.xml";

			ClassLoader classLoader = getClass().getClassLoader();

			if (classLoader.getResource(repositoryXmlPath) == null) {
				repositoryXmlPath =
					"com/liferay/portal/store/jcr/jackrabbit/dependencies" +
						"/repository.xml";
			}

			FileUtil.write(
				tempFile, classLoader.getResourceAsStream(repositoryXmlPath));

			FileUtil.copyFile(
				tempFile,
				new File(_jcrStoreConfiguration.jackrabbitConfigFilePath()));

			tempFile.delete();
		}
		catch (IOException ioe) {
			_log.error("Unable to prepare Jackrabbit directory");

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

	private static final Log _log = LogFactoryUtil.getLog(JCRFactoryImpl.class);

	private boolean _initialized;
	private final JCRStoreConfiguration _jcrStoreConfiguration;
	private final TransientRepository _transientRepository;

}
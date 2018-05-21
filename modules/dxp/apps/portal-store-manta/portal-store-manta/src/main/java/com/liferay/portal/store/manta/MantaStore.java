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

package com.liferay.portal.store.manta;

import com.joyent.manta.client.MantaClient;
import com.joyent.manta.client.MantaObject;
import com.joyent.manta.client.MantaObjectResponse;
import com.joyent.manta.config.ConfigContext;
import com.joyent.manta.config.DefaultsConfigContext;
import com.joyent.manta.config.StandardConfigContext;
import com.joyent.manta.exception.MantaClientHttpResponseException;

import com.liferay.document.library.kernel.exception.NoSuchFileException;
import com.liferay.document.library.kernel.store.BaseStore;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.store.manta.configuration.MantaStoreConfiguration;
import com.liferay.portal.store.manta.internal.uploader.MantaUploader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Map;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

/**
 * @author Ryan Park
 */
@Component(
	configurationPid = "com.liferay.portal.store.manta.configuration.MantaStoreConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	property = "store.type=com.liferay.portal.store.manta.MantaStore",
	service = Store.class
)
public class MantaStore extends BaseStore {

	@Override
	public void addDirectory(
		long companyId, long repositoryId, String dirName) {
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, byte[] bytes)
		throws PortalException {

		updateFile(companyId, repositoryId, fileName, VERSION_DEFAULT, bytes);
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, File file)
		throws PortalException {

		updateFile(companyId, repositoryId, fileName, VERSION_DEFAULT, file);
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, InputStream is)
		throws PortalException {

		updateFile(companyId, repositoryId, fileName, VERSION_DEFAULT, is);
	}

	@Override
	public void checkRoot(long companyId) {
	}

	@Override
	public void deleteDirectory(
		long companyId, long repositoryId, String dirName) {

		deleteFile(companyId, repositoryId, dirName, null);
	}

	@Override
	public void deleteFile(long companyId, long repositoryId, String fileName) {
		deleteFile(companyId, repositoryId, fileName, null);
	}

	@Override
	public void deleteFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		String mantaFileName = getFileName(
			companyId, repositoryId, fileName, null);

		try {
			_mantaClient.deleteRecursive(mantaFileName);
		}
		catch (MantaClientHttpResponseException mchre) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to delete " + mantaFileName, mchre);
			}
		}
		catch (IOException ioe) {
			throw new SystemException("Unable to connect to Manta", ioe);
		}
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		if (Validator.isNull(versionLabel)) {
			versionLabel = getHeadVersionLabel(
				companyId, repositoryId, fileName);
		}

		String mantaFileName = getFileName(
			companyId, repositoryId, fileName, versionLabel);

		try {
			return _mantaClient.getAsInputStream(mantaFileName);
		}
		catch (MantaClientHttpResponseException mchre) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get stream for " + mantaFileName, mchre);
			}

			if (mchre.getStatusCode() == 404) {
				throw new NoSuchFileException(mchre);
			}

			throw new SystemException("Unable to connect to Manta", mchre);
		}
		catch (IOException ioe) {
			throw new SystemException("Unable to connect to Manta", ioe);
		}
	}

	@Override
	public String[] getFileNames(long companyId, long repositoryId) {
		return getFileNames(companyId, repositoryId, null);
	}

	@Override
	public String[] getFileNames(
		long companyId, long repositoryId, String dirName) {

		String mantaFileName = getFileName(
			companyId, repositoryId, dirName, null);

		try {
			Stream<MantaObject> mantaObjects = _mantaClient.listObjects(
				mantaFileName);

			Stream<String> fileNames = mantaObjects.map(
				mantaObject -> {
					String path = mantaObject.getPath();

					int i = path.lastIndexOf(StringPool.SLASH);

					if (i > 0) {
						path = path.substring(i);
					}

					return path;
				});

			return fileNames.toArray(String[]::new);
		}
		catch (MantaClientHttpResponseException mchre) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get file names for " + mantaFileName, mchre);
			}

			return new String[0];
		}
		catch (IOException ioe) {
			throw new SystemException("Unable to connect to Manta", ioe);
		}
	}

	@Override
	public long getFileSize(long companyId, long repositoryId, String fileName)
		throws PortalException {

		String mantaFileName = getFileName(
			companyId, repositoryId, fileName, VERSION_DEFAULT);

		try {
			MantaObjectResponse mantaObjectResponse = _mantaClient.head(
				mantaFileName);

			return mantaObjectResponse.getContentLength();
		}
		catch (MantaClientHttpResponseException mchre) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get stream for " + mantaFileName, mchre);
			}

			if (mchre.getStatusCode() == 404) {
				throw new NoSuchFileException(mchre);
			}

			throw new SystemException("Unable to connect to Manta", mchre);
		}
		catch (IOException ioe) {
			throw new SystemException("Unable to connect to Manta", ioe);
		}
	}

	@Override
	public boolean hasDirectory(
		long companyId, long repositoryId, String dirName) {

		return hasFile(companyId, repositoryId, dirName, null);
	}

	@Override
	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		String mantaFileName = getFileName(
			companyId, repositoryId, fileName, versionLabel);

		return _mantaClient.existsAndIsAccessible(mantaFileName);
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName)
		throws PortalException {

		String mantaFileName = getFileName(
			companyId, repositoryId, fileName, null);
		String mantaNewFileName = getFileName(
			companyId, newRepositoryId, fileName, null);

		try {
			_mantaClient.move(mantaFileName, mantaNewFileName);
		}
		catch (MantaClientHttpResponseException mchre) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to move repository for " + mantaFileName + " to " +
						mantaNewFileName,
					mchre);
			}

			throw new PortalException(mchre);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName)
		throws PortalException {

		String mantaFileName = getFileName(
			companyId, repositoryId, fileName, null);
		String mantaNewFileName = getFileName(
			companyId, repositoryId, newFileName, null);

		try {
			_mantaClient.move(mantaFileName, mantaNewFileName);
		}
		catch (MantaClientHttpResponseException mchre) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to rename " + mantaFileName + " to " +
						mantaNewFileName,
					mchre);
			}

			throw new PortalException(mchre);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, byte[] bytes)
		throws PortalException {

		String mantaFileName = getFileName(
			companyId, repositoryId, fileName, versionLabel);

		try {
			_mantaUploader.put(mantaFileName, bytes);
		}
		catch (MantaClientHttpResponseException mchre) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to update " + mantaFileName, mchre);
			}

			throw new PortalException(mchre);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, File file)
		throws PortalException {

		String mantaFileName = getFileName(
			companyId, repositoryId, fileName, versionLabel);

		try {
			_mantaUploader.put(mantaFileName, file);
		}
		catch (MantaClientHttpResponseException mchre) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to update " + mantaFileName, mchre);
			}

			throw new PortalException(mchre);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream is)
		throws PortalException {

		String mantaFileName = getFileName(
			companyId, repositoryId, fileName, versionLabel);

		try {
			_mantaUploader.put(mantaFileName, is);
		}
		catch (MantaClientHttpResponseException mchre) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to update " + mantaFileName, mchre);
			}

			throw new PortalException(mchre);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) throws IOException {
		_mantaStoreConfiguration = ConfigurableUtil.createConfigurable(
			MantaStoreConfiguration.class, properties);

		_mantaClient = new MantaClient(getConfigContext());

		if (_mantaStoreConfiguration.multipartUploadEnabled()) {
			_mantaUploader = new MantaUploader(_mantaClient);
		}
		else {
			_mantaUploader = new MantaUploader(
				_mantaClient,
				_mantaStoreConfiguration.multipartUploadThreshold());
		}
	}

	protected ConfigContext getConfigContext() {
		StandardConfigContext standardConfigContext =
			new StandardConfigContext();

		standardConfigContext.overwriteWithContext(new DefaultsConfigContext());

		standardConfigContext.setMantaKeyId(
			_mantaStoreConfiguration.mantaKeyId());
		standardConfigContext.setMantaKeyPath(
			_mantaStoreConfiguration.mantaKeyStorePath());
		standardConfigContext.setMantaURL(_mantaStoreConfiguration.mantaURL());
		standardConfigContext.setMantaUser(
			_mantaStoreConfiguration.mantaUser());
		standardConfigContext.setMaximumConnections(
			_mantaStoreConfiguration.httpClientMaxConnections());
		standardConfigContext.setRetries(
			_mantaStoreConfiguration.httpClientMaxErrorRetry());
		standardConfigContext.setTimeout(
			_mantaStoreConfiguration.connectionTimeout());

		return standardConfigContext;
	}

	protected String getFileName(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		StringBundler sb = new StringBundler(9);

		sb.append(_mantaStoreConfiguration.mantaBasePath());
		sb.append(StringPool.SLASH);
		sb.append(companyId);
		sb.append(StringPool.SLASH);
		sb.append(repositoryId);

		if (Validator.isNull(fileName)) {
			return sb.toString();
		}

		sb.append(StringPool.SLASH);
		sb.append(getNormalizedFileName(fileName));

		if (Validator.isNull(versionLabel)) {
			return sb.toString();
		}

		sb.append(StringPool.SLASH);
		sb.append(versionLabel);

		return sb.toString();
	}

	protected String getHeadVersionLabel(
		long companyId, long repositoryId, String fileName) {

		String headVersionLabel = VERSION_DEFAULT;

		String[] versionLabels = getFileNames(
			companyId, repositoryId, fileName);

		for (String versionLabel : versionLabels) {
			if (DLUtil.compareVersions(versionLabel, headVersionLabel) > 0) {
				headVersionLabel = versionLabel;
			}
		}

		return headVersionLabel;
	}

	protected String getNormalizedFileName(String fileName) {
		String normalizedFileName = fileName;

		if (fileName.startsWith(StringPool.SLASH)) {
			normalizedFileName = normalizedFileName.substring(1);
		}

		if (fileName.endsWith(StringPool.SLASH)) {
			normalizedFileName = normalizedFileName.substring(
				0, normalizedFileName.length() - 1);
		}

		return normalizedFileName;
	}

	private static final Log _log = LogFactoryUtil.getLog(MantaStore.class);

	private static volatile MantaStoreConfiguration _mantaStoreConfiguration;

	private MantaClient _mantaClient;
	private MantaUploader _mantaUploader;

}
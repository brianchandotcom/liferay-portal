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

package com.liferay.portlet.documentlibrary.store;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFileException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.model.StorageObject;
import org.jets3t.service.security.AWSCredentials;

/**
 * @author Brian Wing Shun Chan
 * @author Sten Martinez
 * @author Edward Han
 * @author Vilmos Papp
 * @author Mate Thurzo
 */
public class S3Store extends BaseStore {

	public S3Store() {
		try {
			_s3Service = getS3Service();
			_s3Bucket = getS3Bucket();
		}
		catch (S3ServiceException s3se) {
			_log.error(s3se.getMessage());
		}
	}

	@Override
	public void addDirectory(
		long companyId, long repositoryId, String dirName) {
	}

	@Override
	public void addFile(
		long companyId, long repositoryId, String fileName, InputStream is) {

		try {
			S3Object s3Object = new S3Object(
				_s3Bucket,
				getKey(companyId, repositoryId, fileName, VERSION_DEFAULT));

			s3Object.setDataInputStream(is);

			_s3Service.putObject(_s3Bucket, s3Object);

			is.close();
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
		}
	}

	@Override
	public void checkRoot(long companyId) {
	}

	@Override
	public void deleteDirectory(
		long companyId, long repositoryId, String dirName) {

		try {
			S3Object[] s3Objects = _s3Service.listObjects(
				_s3Bucket.getName(), getKey(companyId, repositoryId, dirName),
				null);

			for (S3Object s3Object : s3Objects) {
				_s3Service.deleteObject(_s3Bucket, s3Object.getKey());
			}
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
		}
	}

	@Override
	public void deleteFile(long companyId, long repositoryId, String fileName) {
		try {
			S3Object[] s3Objects = _s3Service.listObjects(
				_s3Bucket.getName(), getKey(companyId, repositoryId, fileName),
				null);

			for (S3Object s3Object : s3Objects) {
				_s3Service.deleteObject(_s3Bucket, s3Object.getKey());
			}
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
		}
	}

	@Override
	public void deleteFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		try {
			_s3Service.deleteObject(
				_s3Bucket,
				getKey(companyId, repositoryId, fileName, versionLabel));
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
		}
	}

	@Override
	public File getFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		try {
			if (Validator.isNull(versionLabel)) {
				versionLabel = getHeadVersionLabel(
					companyId, repositoryId, fileName);
			}

			S3Object s3Object = _s3Service.getObject(
				_s3Bucket.getName(),
				getKey(companyId, repositoryId, fileName, versionLabel));

			File tempFile = getTempFile(s3Object, fileName);

			cleanUpTempFiles();

			return tempFile;
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (ServiceException se) {
			throw new SystemException(se);
		}
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		try {
			if (Validator.isNull(versionLabel)) {
				versionLabel = getHeadVersionLabel(
					companyId, repositoryId, fileName);
			}

			S3Object s3Object = _s3Service.getObject(
				_s3Bucket.getName(),
				getKey(companyId, repositoryId, fileName, versionLabel));

			return s3Object.getDataInputStream();
		}
		catch (ServiceException se) {
			throw new SystemException(se);
		}
	}

	@Override
	public String[] getFileNames(long companyId, long repositoryId) {
		try {
			S3Object[] s3Objects = _s3Service.listObjects(
				_s3Bucket.getName(), getKey(companyId, repositoryId), null);

			return getFileNames(s3Objects);
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
		}
	}

	@Override
	public String[] getFileNames(
		long companyId, long repositoryId, String dirName) {

		try {
			S3Object[] s3Objects = _s3Service.listObjects(
				_s3Bucket.getName(), getKey(companyId, repositoryId, dirName),
				null);

			return getFileNames(s3Objects);
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
		}
	}

	@Override
	public long getFileSize(long companyId, long repositoryId, String fileName)
		throws PortalException {

		try {
			String versionLabel = getHeadVersionLabel(
				companyId, repositoryId, fileName);

			StorageObject storageObject = _s3Service.getObjectDetails(
				_s3Bucket.getName(),
				getKey(companyId, repositoryId, fileName, versionLabel));

			return storageObject.getContentLength();
		}
		catch (ServiceException se) {
			throw new SystemException(se);
		}
	}

	@Override
	public boolean hasDirectory(
		long companyId, long repositoryId, String dirName) {

		return true;
	}

	@Override
	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		try {
			S3Object[] s3Objects = _s3Service.listObjects(
				_s3Bucket.getName(),
				getKey(companyId, repositoryId, fileName, versionLabel), null);

			if (s3Objects.length == 0) {
				return false;
			}
			else {
				return true;
			}
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
		}
	}

	@Override
	public void move(String srcDir, String destDir) {
	}

	@Override
	public void updateFile(
		long companyId, long repositoryId, long newRepositoryId,
		String fileName) {

		try {
			S3Object[] s3Objects = _s3Service.listObjects(
				_s3Bucket.getName(), getKey(companyId, repositoryId, fileName),
				null);

			for (S3Object oldS3Object : s3Objects) {
				String oldKey = oldS3Object.getKey();

				oldS3Object = _s3Service.getObject(_s3Bucket.getName(), oldKey);

				File tempFile = new File(
					SystemProperties.get(SystemProperties.TMP_DIR) +
						File.separator + PortalUUIDUtil.generate());

				FileUtil.write(tempFile, oldS3Object.getDataInputStream());

				InputStream is = new FileInputStream(tempFile);

				String newPrefix = getKey(companyId, newRepositoryId);

				int x = oldKey.indexOf(CharPool.SLASH);

				x = oldKey.indexOf(CharPool.SLASH, x + 1);

				String newKey = newPrefix + oldKey.substring(x);

				S3Object newS3Object = new S3Object(_s3Bucket, newKey);

				newS3Object.setDataInputStream(is);

				_s3Service.putObject(_s3Bucket, newS3Object);
				_s3Service.deleteObject(_s3Bucket, oldKey);

				is.close();

				FileUtil.delete(tempFile);
			}
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (ServiceException se) {
			throw new SystemException(se);
		}
	}

	@Override
	public void updateFile(
		long companyId, long repositoryId, String fileName,
		String newFileName) {

		try {
			S3Object[] s3Objects = _s3Service.listObjects(
				_s3Bucket.getName(), getKey(companyId, repositoryId, fileName),
				null);

			for (S3Object oldS3Object : s3Objects) {
				String oldKey = oldS3Object.getKey();

				oldS3Object = _s3Service.getObject(_s3Bucket.getName(), oldKey);

				File tempFile = new File(
					SystemProperties.get(SystemProperties.TMP_DIR) +
						File.separator + PortalUUIDUtil.generate());

				FileUtil.write(tempFile, oldS3Object.getDataInputStream());

				oldS3Object.closeDataInputStream();

				InputStream is = new FileInputStream(tempFile);

				String newPrefix = getKey(companyId, repositoryId, newFileName);

				int x = oldKey.indexOf(StringPool.SLASH);

				x = oldKey.indexOf(CharPool.SLASH, x + 1);
				x = oldKey.indexOf(CharPool.SLASH, x + 1);

				String newKey = newPrefix + oldKey.substring(x);

				S3Object newS3Object = new S3Object(_s3Bucket, newKey);

				newS3Object.setDataInputStream(is);

				_s3Service.putObject(_s3Bucket, newS3Object);
				_s3Service.deleteObject(_s3Bucket, oldKey);

				newS3Object.closeDataInputStream();

				is.close();

				FileUtil.delete(tempFile);
			}
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (ServiceException se) {
			throw new SystemException(se);
		}
	}

	@Override
	public void updateFile(
		long companyId, long repositoryId, String fileName, String versionLabel,
		InputStream is) {

		try {
			S3Object s3Object = new S3Object(
				_s3Bucket,
				getKey(companyId, repositoryId, fileName, versionLabel));

			s3Object.setDataInputStream(is);

			_s3Service.putObject(_s3Bucket, s3Object);

			is.close();
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
		}
	}

	protected void cleanUpTempFiles() {
		_calledGetFileCount++;

		if (_calledGetFileCount <
				PropsValues.DL_STORE_S3_TEMP_DIR_CLEAN_UP_FREQUENCY) {

			return;
		}

		synchronized (this) {
			if (_calledGetFileCount == 0) {
				return;
			}

			_calledGetFileCount = 0;

			String tempDirName =
				SystemProperties.get(SystemProperties.TMP_DIR) + _TEMP_DIR_NAME;

			File tempDir = new File(tempDirName);

			long lastModified = System.currentTimeMillis();

			lastModified -=
				(PropsValues.DL_STORE_S3_TEMP_DIR_CLEAN_UP_EXPUNGE * Time.DAY);

			cleanUpTempFiles(tempDir, lastModified);
		}
	}

	protected void cleanUpTempFiles(File file, long lastModified) {
		if (!file.isDirectory()) {
			return;
		}

		String[] fileNames = FileUtil.listDirs(file);

		if (fileNames.length == 0) {
			if (file.lastModified() < lastModified) {
				FileUtil.deltree(file);

				return;
			}
		}
		else {
			for (String fileName : fileNames) {
				cleanUpTempFiles(new File(file, fileName), lastModified);
			}

			String[] subfileNames = file.list();

			if (subfileNames.length == 0) {
				FileUtil.deltree(file);

				return;
			}
		}
	}

	protected AWSCredentials getAWSCredentials() throws S3ServiceException {
		if (Validator.isNull(_ACCESS_KEY) || Validator.isNull(_SECRET_KEY)) {
			throw new S3ServiceException(
				"S3 access and secret keys are not set");
		}
		else {
			return new AWSCredentials(_ACCESS_KEY, _SECRET_KEY);
		}
	}

	protected String getFileName(String key) {

		// Convert /${companyId}/${repositoryId}/${dirName}/${fileName}
		// /${versionLabel} to /${dirName}/${fileName}

		int x = key.indexOf(CharPool.SLASH);

		x = key.indexOf(CharPool.SLASH, x + 1);

		int y = key.lastIndexOf(CharPool.SLASH);

		return key.substring(x, y);
	}

	protected String[] getFileNames(S3Object[] s3Objects) {
		List<String> fileNames = new ArrayList<String>();

		for (S3Object s3Object : s3Objects) {
			String fileName = getFileName(s3Object.getKey());

			fileNames.add(fileName);
		}

		return fileNames.toArray(new String[fileNames.size()]);
	}

	protected String getHeadVersionLabel(
			long companyId, long repositoryId, String fileName)
		throws PortalException, S3ServiceException {

		S3Object[] s3Objects = _s3Service.listObjects(
			_s3Bucket.getName(), getKey(companyId, repositoryId, fileName),
			null);

		String[] keys = new String[s3Objects.length];

		for (int i = 0; i < s3Objects.length; i++) {
			S3Object s3Object = s3Objects[i];

			keys[i] = s3Object.getKey();
		}

		if (keys.length > 0) {
			Arrays.sort(keys);

			String headKey = keys[keys.length - 1];

			int x = headKey.lastIndexOf(CharPool.SLASH);

			return headKey.substring(x + 1);
		}
		else {
			throw new NoSuchFileException(fileName);
		}
	}

	protected String getKey(long companyId, long repositoryId) {
		return companyId + StringPool.SLASH + repositoryId;
	}

	protected String getKey(
		long companyId, long repositoryId, String fileName) {

		StringBundler sb = new StringBundler(4);

		sb.append(companyId);
		sb.append(StringPool.SLASH);
		sb.append(repositoryId);
		sb.append(getNormalizedFileName(fileName));

		return sb.toString();
	}

	protected String getKey(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		StringBundler sb = new StringBundler(6);

		sb.append(companyId);
		sb.append(StringPool.SLASH);
		sb.append(repositoryId);
		sb.append(getNormalizedFileName(fileName));
		sb.append(StringPool.SLASH);
		sb.append(versionLabel);

		return sb.toString();
	}

	protected String getNormalizedFileName(String fileName) {
		String normalizedFileName = fileName;

		if (!fileName.startsWith(StringPool.SLASH)) {
			normalizedFileName = StringPool.SLASH + normalizedFileName;
		}

		if (fileName.endsWith(StringPool.SLASH)) {
			normalizedFileName = normalizedFileName.substring(
				0, normalizedFileName.length() - 1);
		}

		return normalizedFileName;
	}

	protected S3Bucket getS3Bucket() throws S3ServiceException {
		if (Validator.isNull(_BUCKET_NAME)) {
			throw new S3ServiceException("S3 bucket name is not set");
		}
		else {
			return getS3Service().getBucket(_BUCKET_NAME);
		}
	}

	protected S3Service getS3Service() throws S3ServiceException {
		AWSCredentials credentials = getAWSCredentials();

		return new RestS3Service(credentials);
	}

	protected File getTempFile(S3Object s3Object, String fileName)
		throws IOException, ServiceException {

		StringBundler sb = new StringBundler(5);

		sb.append(SystemProperties.get(SystemProperties.TMP_DIR));
		sb.append(_TEMP_DIR_NAME);
		sb.append(
			DateUtil.getCurrentDate(
				_TEMP_DIR_PATTERN, LocaleUtil.getDefault()));
		sb.append(getNormalizedFileName(fileName));

		Date lastModifiedDate = s3Object.getLastModifiedDate();

		sb.append(lastModifiedDate.getTime());

		String tempFileName = sb.toString();

		File tempFile = new File(tempFileName);

		if (tempFile.exists() &&
			(tempFile.lastModified() >= lastModifiedDate.getTime())) {

			return tempFile;
		}

		InputStream inputStream = s3Object.getDataInputStream();

		if (inputStream == null) {
			throw new IOException("S3 object input stream is null");
		}

		OutputStream outputStream = null;

		try {
			File parentFile = tempFile.getParentFile();

			parentFile.mkdirs();

			outputStream = new FileOutputStream(tempFile);

			StreamUtil.transfer(inputStream, outputStream);
		}
		finally {
			StreamUtil.cleanUp(inputStream, outputStream);
		}

		return tempFile;
	}

	private static final String _ACCESS_KEY = PropsUtil.get(
		PropsKeys.DL_STORE_S3_ACCESS_KEY);

	private static final String _BUCKET_NAME = PropsUtil.get(
		PropsKeys.DL_STORE_S3_BUCKET_NAME);

	private static final String _SECRET_KEY = PropsUtil.get(
		PropsKeys.DL_STORE_S3_SECRET_KEY);

	private static final String _TEMP_DIR_NAME = "/liferay/s3";

	private static final String _TEMP_DIR_PATTERN = "/yyyy/MM/dd/HH/";

	private static Log _log = LogFactoryUtil.getLog(S3Store.class);

	private int _calledGetFileCount;
	private S3Bucket _s3Bucket;
	private S3Service _s3Service;

}
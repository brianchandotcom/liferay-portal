/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;

/**
 * @author Brian Wing Shun Chan
 * @author Sten Martinez
 * @author Edward Han
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
			long companyId, long repositoryId, String fileName, InputStream is)
		throws SystemException {

		try {
			S3Object s3Object = new S3Object(
				_s3Bucket,
				getKey(companyId, repositoryId, fileName, VERSION_DEFAULT));

			s3Object.setDataInputStream(is);

			_s3Service.putObject(_s3Bucket, s3Object);
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
			long companyId, long repositoryId, String dirName)
		throws SystemException {

		try {
			S3Object[] s3Objects = _s3Service.listObjects(
				_s3Bucket, getKey(companyId, repositoryId, dirName), null);

			for (int i = 0; i < s3Objects.length; i++) {
				S3Object s3Object = s3Objects[i];

				_s3Service.deleteObject(_s3Bucket, s3Object.getKey());
			}
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
		}
	}

	@Override
	public void deleteFile(
			long companyId, long repositoryId, String fileName)
		throws SystemException {

		try {
			S3Object[] s3Objects = _s3Service.listObjects(
				_s3Bucket, getKey(companyId, repositoryId, fileName), null);

			for (int i = 0; i < s3Objects.length; i++) {
				S3Object s3Object = s3Objects[i];

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
			String versionLabel)
		throws SystemException {

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
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		try {
			if (Validator.isNull(versionLabel)) {
				versionLabel = getHeadVersionLabel(
					companyId, repositoryId, fileName);
			}

			S3Object s3Object = _s3Service.getObject(
				_s3Bucket,
				getKey(companyId, repositoryId, fileName, versionLabel));

			return s3Object.getDataInputStream();
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
		}
	}

	public String[] getFileNames(long companyId, long repositoryId)
		throws SystemException {

		List<String> fileNames = new ArrayList<String>();

		try {
			S3Object[] searchObjects = _s3Service.listObjects(
				_s3Bucket, getKey(companyId, repositoryId), null);

			for (int i = 0; i < searchObjects.length; i++) {
				S3Object currentObject = searchObjects[i];

				String fileName = getFileName(currentObject.getKey());

				fileNames.add(fileName);
			}
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
		}

		return fileNames.toArray(new String[0]);
	}

	@Override
	public String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws SystemException {

		try {
			List<String> list = new ArrayList<String>();

			S3Object[] s3Objects = _s3Service.listObjects(
				_s3Bucket, getKey(companyId, repositoryId, dirName), null);

			for (int i = 0; i < s3Objects.length; i++) {
				S3Object s3Object = s3Objects[i];

				// Convert /${companyId}/${repositoryId}/${dirName}/${fileName}
				// /${versionLabel} to /${dirName}/${fileName}

				String key = s3Object.getKey();

				int x = key.indexOf(CharPool.SLASH);

				x = key.indexOf(CharPool.SLASH, x + 1);

				int y = key.lastIndexOf(CharPool.SLASH);

				list.add(key.substring(x, y));
			}

			return list.toArray(new String[list.size()]);
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
		}
	}

	@Override
	public long getFileSize(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		try {
			String versionLabel = getHeadVersionLabel(
				companyId, repositoryId, fileName);

			S3Object objectDetails = _s3Service.getObjectDetails(
				_s3Bucket,
				getKey(companyId, repositoryId, fileName, versionLabel));

			return objectDetails.getContentLength();
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
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
			String versionLabel)
		throws SystemException {

		try {
			S3Object[] s3Objects = _s3Service.listObjects(
				_s3Bucket,
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
			String fileName)
		throws SystemException {

		try {
			S3Object[] s3Objects = _s3Service.listObjects(
				_s3Bucket, getKey(companyId, repositoryId, fileName), null);

			for (int i = 0; i < s3Objects.length; i++) {
				S3Object oldS3Object = s3Objects[i];

				String oldKey = oldS3Object.getKey();

				oldS3Object = _s3Service.getObject(_s3Bucket, oldKey);

				File tempFile = new File(
					SystemProperties.get(SystemProperties.TMP_DIR) +
						File.separator + PortalUUIDUtil.generate());

				FileUtil.write(tempFile, oldS3Object.getDataInputStream());

				InputStream is = new FileInputStream(tempFile);

				String newPrefix = getKey(companyId, newRepositoryId);

				int x = oldKey.indexOf(CharPool.SLASH);

				x = oldKey.indexOf(CharPool.SLASH, x + 1);

				String newKey =
					newPrefix + oldKey.substring(x + 1, oldKey.length());

				S3Object newS3Object = new S3Object(
					_s3Bucket, newKey);

				newS3Object.setDataInputStream(is);

				_s3Service.putObject(_s3Bucket, newS3Object);
				_s3Service.deleteObject(_s3Bucket, oldKey);

				FileUtil.delete(tempFile);
			}
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
		}
	}

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName)
		throws SystemException {

		try {
			S3Object[] s3Objects = _s3Service.listObjects(
				_s3Bucket, getKey(companyId, repositoryId, fileName), null);

			for (int i = 0; i < s3Objects.length; i++) {
				S3Object oldS3Object = s3Objects[i];

				String oldKey = oldS3Object.getKey();

				oldS3Object = _s3Service.getObject(_s3Bucket, oldKey);

				File tempFile = new File(
					SystemProperties.get(SystemProperties.TMP_DIR) +
						File.separator + PortalUUIDUtil.generate());

				FileUtil.write(tempFile, oldS3Object.getDataInputStream());

				InputStream is = new FileInputStream(tempFile);

				String newPrefix = getKey(companyId, repositoryId, newFileName);

				int x = oldKey.indexOf(StringPool.SLASH);

				x = oldKey.indexOf(CharPool.SLASH, x + 1);

				x = oldKey.indexOf(CharPool.SLASH, x + 1);

				String newKey =
					newPrefix + oldKey.substring(x + 1, oldKey.length());

				S3Object newS3Object = new S3Object(
					_s3Bucket, newKey);

				newS3Object.setDataInputStream(is);

				_s3Service.putObject(_s3Bucket, newS3Object);
				_s3Service.deleteObject(_s3Bucket, oldKey);

				FileUtil.delete(tempFile);
			}
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
		}
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream is)
		throws SystemException {

		try {
			S3Object s3Object = new S3Object(
				_s3Bucket,
				getKey(companyId, repositoryId, fileName, versionLabel));

			s3Object.setDataInputStream(is);

			_s3Service.putObject(_s3Bucket, s3Object);
		}
		catch (S3ServiceException s3se) {
			throw new SystemException(s3se);
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
		int x = key.indexOf(CharPool.SLASH);

		x = key.indexOf(CharPool.SLASH, x + 1);

		int y = key.lastIndexOf(CharPool.SLASH);

		return key.substring(x + 1, y);
	}

	protected String getHeadVersionLabel(
			long companyId, long repositoryId, String fileName)
		throws PortalException, S3ServiceException {

		S3Object[] s3Objects = _s3Service.listObjects(
			_s3Bucket, getKey(companyId, repositoryId, fileName), null);

		String[] keys = new String[s3Objects.length];

		for (int i = 0; i < s3Objects.length; i++) {
			S3Object s3Object = s3Objects[i];

			keys[i] = s3Object.getKey();
		}

		if (keys.length > 0) {
			Arrays.sort(keys);

			String headKey = keys[keys.length - 1];

			int x = headKey.lastIndexOf(CharPool.SLASH);

			return headKey.substring(x + 1, headKey.length());
		}
		else {
			throw new NoSuchFileException(fileName);
		}
	}

	protected String getKey(long companyId, long repositoryId) {
		StringBundler sb = new StringBundler(4);

		sb.append(companyId);
		sb.append(StringPool.SLASH);
		sb.append(repositoryId);
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	protected String getKey(
		long companyId, long repositoryId, String fileName) {

		StringBundler sb = new StringBundler(6);

		sb.append(companyId);
		sb.append(StringPool.SLASH);
		sb.append(repositoryId);
		sb.append(StringPool.SLASH);
		sb.append(fileName);
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	protected String getKey(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		StringBundler sb = new StringBundler(7);

		sb.append(companyId);
		sb.append(StringPool.SLASH);
		sb.append(repositoryId);
		sb.append(StringPool.SLASH);
		sb.append(fileName);
		sb.append(StringPool.SLASH);
		sb.append(versionLabel);

		return sb.toString();
	}

	protected S3Bucket getS3Bucket() throws S3ServiceException {
		if (Validator.isNull(_BUCKET_NAME)) {
			throw new S3ServiceException("S3 bucket name is not set");
		}
		else {
			return getS3Service().createBucket(_BUCKET_NAME);
		}
	}

	protected S3Service getS3Service() throws S3ServiceException {
		AWSCredentials credentials = getAWSCredentials();

		return new RestS3Service(credentials);
	}

	private static final String _ACCESS_KEY = PropsUtil.get(
		PropsKeys.DL_STORE_S3_ACCESS_KEY);

	private static final String _BUCKET_NAME = PropsUtil.get(
		PropsKeys.DL_STORE_S3_BUCKET_NAME);

	private static final String _SECRET_KEY = PropsUtil.get(
		PropsKeys.DL_STORE_S3_SECRET_KEY);

	private static Log _log = LogFactoryUtil.getLog(S3Store.class);

	private S3Bucket _s3Bucket;
	private S3Service _s3Service;

}
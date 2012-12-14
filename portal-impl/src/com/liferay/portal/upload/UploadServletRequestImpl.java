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

package com.liferay.portal.upload;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.ByteArrayFileInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.WebKeys;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * @author Brian Wing Shun Chan
 * @author Zongliang Li
 * @author Harry Mark
 * @author Raymond Augé
 */
public class UploadServletRequestImpl
	extends HttpServletRequestWrapper implements UploadServletRequest {

	public static File getTempDir() throws SystemException {
		if (_tempDir == null) {
			_tempDir = new File(
				PrefsPropsUtil.getString(
					PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_TEMP_DIR,
					SystemProperties.get(SystemProperties.TMP_DIR)));
		}

		return _tempDir;
	}

	public static void setTempDir(File tempDir) {
		_tempDir = tempDir;
	}

	public UploadServletRequestImpl(HttpServletRequest request) {
		super(request);

		_fileParams = new LinkedHashMap<String, FileItem[]>();
		_regularParams = new LinkedHashMap<String, List<String>>();

		try {
			ServletFileUpload servletFileUpload = new LiferayFileUpload(
				new LiferayFileItemFactory(getTempDir()), request);

			servletFileUpload.setSizeMax(
				PrefsPropsUtil.getLong(
					PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE));

			_liferayServletRequest = new LiferayServletRequest(request);

			List<LiferayFileItem> liferayFileItemsList =
				servletFileUpload.parseRequest(_liferayServletRequest);

			for (LiferayFileItem liferayFileItem : liferayFileItemsList) {
				if (liferayFileItem.isFormField()) {
					liferayFileItem.setString(request.getCharacterEncoding());

					String fieldName = liferayFileItem.getFieldName();

					if (!_regularParams.containsKey(fieldName)) {
						_regularParams.put(fieldName, new ArrayList<String>());
					}

					List<String> values = _regularParams.get(fieldName);

					values.add(liferayFileItem.getString());

					continue;
				}

				FileItem[] liferayFileItems = _fileParams.get(
					liferayFileItem.getFieldName());

				if (liferayFileItems == null) {
					liferayFileItems = new LiferayFileItem[] {liferayFileItem};
				}
				else {
					LiferayFileItem[] newLiferayFileItems =
						new LiferayFileItem[liferayFileItems.length + 1];

					System.arraycopy(
						liferayFileItems, 0, newLiferayFileItems, 0,
						liferayFileItems.length);

					newLiferayFileItems[newLiferayFileItems.length - 1] =
						liferayFileItem;

					liferayFileItems = newLiferayFileItems;
				}

				_fileParams.put(
					liferayFileItem.getFieldName(), liferayFileItems);
			}
		}
		catch (Exception e) {
			UploadException uploadException = new UploadException(e);

			if (e instanceof FileUploadBase.FileSizeLimitExceededException ||
				e instanceof FileUploadBase.SizeLimitExceededException ) {

				uploadException.setExceededSizeLimit(true);
			}

			request.setAttribute(WebKeys.UPLOAD_EXCEPTION, uploadException);

			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	public UploadServletRequestImpl(
		HttpServletRequest request, Map<String, FileItem[]> fileParams,
		Map<String, List<String>> regularParams) {

		super(request);

		_fileParams = new LinkedHashMap<String, FileItem[]>();
		_regularParams = new LinkedHashMap<String, List<String>>();

		if (fileParams != null) {
			_fileParams.putAll(fileParams);
		}

		if (regularParams != null) {
			_regularParams.putAll(regularParams);
		}
	}

	public void cleanUp() {
		if ((_fileParams != null) && !_fileParams.isEmpty()) {
			for (FileItem[] liferayFileItems : _fileParams.values()) {
				for (FileItem liferayFileItem : liferayFileItems) {
					liferayFileItem.delete();
				}
			}
		}
	}

	public String getContentType(String name) {
		FileItem[] liferayFileItems = _fileParams.get(name);

		if ((liferayFileItems != null) && (liferayFileItems.length > 0)) {
			FileItem liferayFileItem = liferayFileItems[0];

			return liferayFileItem.getContentType();
		}

		return null;
	}

	public File getFile(String name) {
		return getFile(name, false);
	}

	public File getFile(String name, boolean forceCreate) {
		if (getFileName(name) == null) {
			return null;
		}

		FileItem[] liferayFileItems = _fileParams.get(name);

		if ((liferayFileItems == null) || (liferayFileItems.length == 0)) {
			return null;
		}

		FileItem liferayFileItem = liferayFileItems[0];

		long size = liferayFileItem.getSize();

		if ((size > 0) && (size <= liferayFileItem.getSizeThreshold())) {
			forceCreate = true;
		}

		File file = liferayFileItem.getStoreLocation();

		if (liferayFileItem.isInMemory() && forceCreate) {
			try {
				FileUtil.write(file, liferayFileItem.getInputStream());
			}
			catch (IOException ioe) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to write temporary file " +
							file.getAbsolutePath(),
						ioe);
				}
			}
		}

		return file;
	}

	public InputStream getFileAsStream(String name) throws IOException {
		return getFileAsStream(name, true);
	}

	public InputStream getFileAsStream(String name, boolean deleteOnClose)
		throws IOException {

		if (getFileName(name) == null) {
			return null;
		}

		InputStream inputStream = null;

		FileItem[] liferayFileItems = _fileParams.get(name);

		if ((liferayFileItems != null) && (liferayFileItems.length > 0)) {
			FileItem liferayFileItem = liferayFileItems[0];

			inputStream = getInputStream(liferayFileItem, deleteOnClose);
		}

		return inputStream;
	}

	public String getFileName(String name) {
		FileItem[] liferayFileItems = _fileParams.get(name);

		if ((liferayFileItems != null) && (liferayFileItems.length > 0)) {
			FileItem liferayFileItem = liferayFileItems[0];

			return liferayFileItem.getFileName();
		}

		return null;
	}

	public String[] getFileNames(String name) {
		FileItem[] liferayFileItems = _fileParams.get(name);

		if ((liferayFileItems != null) && (liferayFileItems.length > 0)) {
			String[] fileNames = new String[liferayFileItems.length];

			for (int i = 0; i < liferayFileItems.length; i++) {
				FileItem liferayFileItem = liferayFileItems[i];

				fileNames[i] = liferayFileItem.getFileName();
			}

			return fileNames;
		}

		return null;
	}

	public File[] getFiles(String name) {
		String[] fileNames = getFileNames(name);

		if (fileNames == null) {
			return null;
		}

		FileItem[] liferayFileItems = _fileParams.get(name);

		if ((liferayFileItems != null) && (liferayFileItems.length > 0)) {
			File[] files = new File[liferayFileItems.length];

			for (int i = 0; i < liferayFileItems.length; i++) {
				FileItem liferayFileItem = liferayFileItems[i];

				if (Validator.isNotNull(liferayFileItem.getFileName())) {
					files[i] = liferayFileItem.getStoreLocation();
				}
			}

			return files;
		}

		return null;
	}

	public InputStream[] getFilesAsStream(String name) throws IOException {
		return getFilesAsStream(name, true);
	}

	public InputStream[] getFilesAsStream(String name, boolean deleteOnClose)
		throws IOException {

		String[] fileNames = getFileNames(name);

		if (fileNames == null) {
			return null;
		}

		InputStream[] inputStreams = null;

		FileItem[] liferayFileItems = _fileParams.get(name);

		if ((liferayFileItems != null) && (liferayFileItems.length > 0)) {
			inputStreams = new InputStream[liferayFileItems.length];

			for (int i = 0; i < liferayFileItems.length; i++) {
				FileItem liferayFileItem = liferayFileItems[i];

				if (Validator.isNotNull(liferayFileItem.getFileName())) {
					inputStreams[i] = getInputStream(
						liferayFileItem, deleteOnClose);
				}
			}
		}

		return inputStreams;
	}

	public String getFullFileName(String name) {
		FileItem[] liferayFileItems = _fileParams.get(name);

		if ((liferayFileItems != null) && (liferayFileItems.length > 0)) {
			FileItem liferayFileItem = liferayFileItems[0];

			return liferayFileItem.getFullFileName();
		}

		return null;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return _liferayServletRequest.getInputStream();
	}

	public Map<String, FileItem[]> getMultipartParameterMap() {
		return _fileParams;
	}

	@Override
	public String getParameter(String name) {
		List<String> values = _regularParams.get(name);

		if ((values != null) && !values.isEmpty()) {
			return values.get(0);
		}

		return super.getParameter(name);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> map = new HashMap<String, String[]>();

		Enumeration<String> enu = getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			map.put(name, getParameterValues(name));
		}

		return map;
	}

	@Override
	public Enumeration<String> getParameterNames() {
		Set<String> parameterNames = new LinkedHashSet<String>();

		Enumeration<String> enu = super.getParameterNames();

		while (enu.hasMoreElements()) {
			parameterNames.add(enu.nextElement());
		}

		parameterNames.addAll(_regularParams.keySet());
		parameterNames.addAll(_fileParams.keySet());

		return Collections.enumeration(parameterNames);
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] parameterValues = null;

		List<String> values = _regularParams.get(name);

		if (values != null) {
			parameterValues = values.toArray(new String[values.size()]);
		}

		String[] parentParameterValues = super.getParameterValues(name);

		if (parameterValues == null) {
			return parentParameterValues;
		}
		else if (parentParameterValues == null) {
			return parameterValues;
		}

		return ArrayUtil.append(parameterValues, parentParameterValues);
	}

	public Map<String, List<String>> getRegularParameterMap() {
		return _regularParams;
	}

	public Long getSize(String name) {
		FileItem[] liferayFileItems = _fileParams.get(name);

		if ((liferayFileItems != null) && (liferayFileItems.length > 0)) {
			FileItem liferayFileItem = liferayFileItems[0];

			return new Long(liferayFileItem.getSize());
		}

		return null;
	}

	public Boolean isFormField(String name) {
		FileItem[] liferayFileItems = _fileParams.get(name);

		if ((liferayFileItems != null) && (liferayFileItems.length > 0)) {
			FileItem liferayFileItem = liferayFileItems[0];

			return new Boolean(liferayFileItem.isFormField());
		}

		return null;
	}

	protected InputStream getInputStream(
			FileItem liferayFileItem, boolean deleteOnClose)
		throws IOException {

		InputStream inputStream = null;

		if (liferayFileItem.isInMemory() && (liferayFileItem.getSize() > 0)) {
			inputStream = liferayFileItem.getInputStream();
		}
		else if (!liferayFileItem.isInMemory()) {
			inputStream = new ByteArrayFileInputStream(
				liferayFileItem.getStoreLocation(),
				liferayFileItem.getSizeThreshold(), deleteOnClose);
		}

		return inputStream;
	}

	private static Log _log = LogFactoryUtil.getLog(
		UploadServletRequestImpl.class);

	private static File _tempDir;

	private Map<String, FileItem[]> _fileParams;
	private LiferayServletRequest _liferayServletRequest;
	private Map<String, List<String>> _regularParams;

}
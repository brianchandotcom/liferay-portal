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

package com.liferay.portal.upload;

import com.liferay.petra.memory.DeleteFileFinalizeAction;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.util.PropsUtil;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.fileupload.FileItemHeaders;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.output.DeferredFileOutputStream;

/**
 * @author Brian Wing Shun Chan
 * @author Zongliang Li
 * @author Harry Mark
 * @author Neil Griffin
 */
public class LiferayFileItem extends DiskFileItem implements FileItem {

	public static final long THRESHOLD_SIZE = GetterUtil.getLong(
		PropsUtil.get(LiferayFileItem.class.getName() + ".threshold.size"));

	public LiferayFileItem(
		String fieldName, String contentType, boolean formField,
		String fileName, int sizeThreshold, File repository) {

		super(
			fieldName, contentType, formField, fileName, sizeThreshold,
			repository);

		_fileName = fileName;
		_sizeThreshold = sizeThreshold;
		_repository = repository;
	}

	@Override
	public String getContentType() {
		try {
			return MimeTypesUtil.getContentType(
				getInputStream(), getFileName());
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException);
			}

			return ContentTypes.APPLICATION_OCTET_STREAM;
		}
	}

	@Override
	public String getEncodedString() {
		return _encodedString;
	}

	@Override
	public String getFileName() {
		if (_fileName == null) {
			return null;
		}

		int pos = _fileName.lastIndexOf("/");

		if (pos == -1) {
			pos = _fileName.lastIndexOf("\\");
		}

		if (pos == -1) {
			return _fileName;
		}

		return _fileName.substring(pos + 1);
	}

	@Override
	public String getFileNameExtension() {
		return FileUtil.getExtension(_fileName);
	}

	@Override
	public String getFullFileName() {
		return _fileName;
	}

	@Override
	public String getHeader(String name) {
		FileItemHeaders fileItemHeaders = getHeaders();

		Iterator<String> iterator = fileItemHeaders.getHeaders(name);

		if (iterator.hasNext()) {
			return iterator.next();
		}

		return null;
	}

	@Override
	public Collection<String> getHeaderNames() {
		List<String> headerNames = new ArrayList<>();

		FileItemHeaders fileItemHeaders = getHeaders();

		Iterator<String> iterator = fileItemHeaders.getHeaderNames();

		iterator.forEachRemaining(headerNames::add);

		return headerNames;
	}

	@Override
	public Collection<String> getHeaders(String name) {
		List<String> headers = new ArrayList<>();

		FileItemHeaders fileItemHeaders = getHeaders();

		Iterator<String> iterator = fileItemHeaders.getHeaders(name);

		iterator.forEachRemaining(headers::add);

		return headers;
	}

	@Override
	public int getSizeThreshold() {
		return _sizeThreshold;
	}

	@Override
	public File getStoreLocation() {
		if (!ServerDetector.isWebLogic()) {
			return super.getStoreLocation();
		}

		try {
			DeferredFileOutputStream deferredFileOutputStream =
				(DeferredFileOutputStream)getOutputStream();

			return deferredFileOutputStream.getFile();
		}
		catch (IOException ioException) {
			_log.error(ioException);

			return null;
		}
	}

	@Override
	public String getString() {

		// Prevent serialization of uploaded content

		if (getSize() > THRESHOLD_SIZE) {
			return StringPool.BLANK;
		}

		if (_encodedString == null) {
			return super.getString();
		}

		return _encodedString;
	}

	@Override
	public void setString(String encode) {
		try {
			_encodedString = getString(encode);
		}
		catch (UnsupportedEncodingException unsupportedEncodingException) {
			_log.error(unsupportedEncodingException);
		}
	}

	@Override
	protected File getTempFile() {
		String tempFileName = "upload_" + _getUniqueId();

		String extension = getFileNameExtension();

		if (extension != null) {
			tempFileName += "." + extension;
		}

		File tempFile = new File(_repository, tempFileName);

		FinalizeManager.register(
			tempFile, new DeleteFileFinalizeAction(tempFile.getAbsolutePath()),
			FinalizeManager.PHANTOM_REFERENCE_FACTORY);

		return tempFile;
	}

	private String _getUniqueId() {
		int current = 0;

		synchronized (LiferayFileItem.class) {
			current = _counter++;
		}

		String id = String.valueOf(current);

		if (current < 100000000) {
			return "00000000".substring(id.length()) + id;
		}

		return id;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayFileItem.class);

	private static int _counter;

	private String _encodedString;
	private String _fileName;
	private final File _repository;
	private final int _sizeThreshold;

}
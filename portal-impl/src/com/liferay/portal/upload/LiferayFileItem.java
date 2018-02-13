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

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.output.DeferredFileOutputStream;

/**
 * @author Brian Wing Shun Chan
 * @author Zongliang Li
 * @author Harry Mark
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
		catch (IOException ioe) {
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
		else {
			return _fileName.substring(pos + 1);
		}
	}

	@Override
	public String getFileNameExtension() {
		return FileUtil.getExtension(_fileName);
	}

	@Override
	public String getFullFileName() {
		return _fileName;
	}

	public long getItemSize() {
		long size = getSize();

		String contentType = getContentType();

		if (contentType != null) {
			byte[] bytes = contentType.getBytes();

			size += bytes.length;
		}

		String fieldName = getFieldName();

		if (fieldName != null) {
			byte[] bytes = fieldName.getBytes();

			size += bytes.length;
		}

		String fileName = getFileName();

		if (fileName != null) {
			byte[] bytes = fileName.getBytes();

			size += bytes.length;
		}

		return size;
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
			DeferredFileOutputStream dfos =
				(DeferredFileOutputStream)getOutputStream();

			return dfos.getFile();
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);

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
		else {
			return _encodedString;
		}
	}

	@Override
	public void setString(String encode) {
		try {
			_encodedString = getString(encode);
		}
		catch (UnsupportedEncodingException uee) {
			_log.error(uee, uee);
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

	private static String _getUniqueId() {
		int current = 0;

		synchronized (LiferayFileItem.class) {
			current = _counter++;
		}

		String id = String.valueOf(current);

		if (current < 100000000) {
			id = ("00000000" + id).substring(id.length());
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
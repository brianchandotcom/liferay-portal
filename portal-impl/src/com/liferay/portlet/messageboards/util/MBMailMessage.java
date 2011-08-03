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

package com.liferay.portlet.messageboards.util;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jorge Ferrer
 */
public class MBMailMessage {

	public void addFile(String fileName, byte[] bytes) {
		_files.add(new ObjectValuePair<String, byte[]>(fileName, bytes));
	}

	public List<ObjectValuePair<String, File>> getFiles() throws IOException {
		List<File> tempFiles = new ArrayList<File>(_files.size());

		try {
			List<ObjectValuePair<String, File>> files =
					new ArrayList<ObjectValuePair<String, File>>(_files.size());

			for (ObjectValuePair<String, byte[]> ovp : _files) {
				String key = ovp.getKey();
				byte[] bytes = ovp.getValue();

				File file = FileUtil.createTempFile(FileUtil.getExtension(key));

				tempFiles.add(file);

				FileUtil.write(file, bytes);

				files.add(new ObjectValuePair<String, File>(key, file));
			}

			return files;
		}
		catch (IOException ioe) {
			for (File tempFile : tempFiles) {
				FileUtil.delete(tempFile);
			}

			throw ioe;
		}
	}

	public String getHtmlBody() {
		return _htmlBody;
	}

	public void setHtmlBody(String htmlBody) {
		_htmlBody = htmlBody;
	}

	public String getPlainBody() {
		return _plainBody;
	}

	public void setPlainBody(String plainBody) {
		_plainBody = plainBody;
	}

	public String getBody() {
		if (Validator.isNotNull(_plainBody)) {
			return GetterUtil.getString(_plainBody);
		}
		else if (Validator.isNotNull(_htmlBody)) {
			return HtmlUtil.extractText(_htmlBody);
		}
		else {
			return "-";
		}
	}

	private String _htmlBody;
	private String _plainBody;
	private List<ObjectValuePair<String, byte[]>> _files =
		new ArrayList<ObjectValuePair<String, byte[]>>();

}
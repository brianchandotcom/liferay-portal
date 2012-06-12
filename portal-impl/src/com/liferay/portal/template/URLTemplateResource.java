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

package com.liferay.portal.template;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateResource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import java.net.URL;
import java.net.URLConnection;

/**
 * @author Tina Tian
 */
public class URLTemplateResource implements TemplateResource {

	public URLTemplateResource(String templateId, URL templateContent) {

		_templateId = templateId;
		_templateContent = templateContent;
	}

	public long getLastModified() {
		URLConnection urlConnection = null;

		try {
			urlConnection = _templateContent.openConnection();

			long lastModified = urlConnection.getLastModified();

			return lastModified;
		}

		catch(Exception e) {
			_log.error(
				"Unable to get last modified time for template " + _templateId,
				e);

			return 0;
		}
		finally {
			try {
				urlConnection.getInputStream().close();
			}
			catch (Exception e) {
			}
			finally {
				urlConnection = null;
			}
		}
	}

	public Reader getReader() throws IOException {
		if (_templateContent == null) {
			return null;
		}

		URLConnection urlConnection = _templateContent.openConnection();

		return new UnsyncBufferedReader(
			new InputStreamReader(
				urlConnection.getInputStream(), DEFAUT_ENCODING));
	}

	public String getTemplateId() {
		return _templateId;
	}

	private static Log _log = LogFactoryUtil.getLog(URLTemplateResource.class);

	private URL _templateContent;
	private String _templateId;

}
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.nio.channels.Channels;
import java.nio.channels.FileChannel;

import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletResponseUtil {

	/**
	 * @deprecated As of 6.1.0
	 */
	public static void sendFile(
			MimeResponse mimeResponse, String fileName, byte[] bytes)
		throws IOException {

		sendFile(null, mimeResponse, fileName, bytes);
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	public static void sendFile(
			MimeResponse mimeResponse, String fileName, byte[] bytes,
			String contentType)
		throws IOException {

		sendFile(null, mimeResponse, fileName, bytes, contentType);
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	public static void sendFile(
			MimeResponse mimeResponse, String fileName, InputStream is)
		throws IOException {

		sendFile(null, mimeResponse, fileName, is);
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	public static void sendFile(
			MimeResponse mimeResponse, String fileName, InputStream is,
			int contentLength, String contentType)
		throws IOException {

		sendFile(null, mimeResponse, fileName, is, contentLength, contentType);
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	public static void sendFile(
			MimeResponse mimeResponse, String fileName, InputStream is,
			String contentType)
		throws IOException {

		sendFile(null, mimeResponse, fileName, is, contentType);
	}

	public static void sendFile(
			PortletRequest portletRequest, MimeResponse mimeResponse,
			String fileName, byte[] bytes)
		throws IOException {

		sendFile(portletRequest, mimeResponse, fileName, bytes, null);
	}

	public static void sendFile(
			PortletRequest portletRequest, MimeResponse mimeResponse,
			String fileName, byte[] bytes, String contentType)
		throws IOException {

		sendFile(
			portletRequest, mimeResponse, fileName, bytes, contentType, null);
	}

	public static void sendFile(
			PortletRequest portletRequest, MimeResponse mimeResponse,
			String fileName, byte[] bytes, String contentType,
			String contentDispositionType)
		throws IOException {

		setHeaders(
			portletRequest, mimeResponse, fileName, contentType,
			contentDispositionType);

		write(mimeResponse, bytes);
	}

	public static void sendFile(
			PortletRequest portletRequest, MimeResponse mimeResponse,
			String fileName, InputStream is)
		throws IOException {

		sendFile(portletRequest, mimeResponse, fileName, is, null);
	}

	public static void sendFile(
			PortletRequest portletRequest, MimeResponse mimeResponse,
			String fileName, InputStream is, int contentLength,
			String contentType)
		throws IOException {

		sendFile(
			portletRequest, mimeResponse, fileName, is, 0, contentType, null);
	}

	public static void sendFile(
			PortletRequest portletRequest, MimeResponse mimeResponse,
			String fileName, InputStream is, int contentLength,
			String contentType, String contentDispositionType)
		throws IOException {

		setHeaders(
			portletRequest, mimeResponse, fileName, contentType,
			contentDispositionType);

		write(mimeResponse, is, contentLength);
	}

	public static void sendFile(
			PortletRequest portletRequest, MimeResponse mimeResponse,
			String fileName, InputStream is, String contentType)
		throws IOException {

		sendFile(portletRequest, mimeResponse, fileName, is, 0, contentType);
	}

	public static void write(MimeResponse mimeResponse, byte[] bytes)
		throws IOException {

		write(mimeResponse, bytes, 0, 0);
	}

	public static void write(
			MimeResponse mimeResponse, byte[] bytes, int offset,
			int contentLength)
		throws IOException {

		// LEP-3122

		if (!mimeResponse.isCommitted()) {

			// LEP-536

			if (contentLength == 0) {
				contentLength = bytes.length;
			}

			if (mimeResponse instanceof ResourceResponse) {
				ResourceResponse resourceResponse =
					(ResourceResponse)mimeResponse;

				resourceResponse.setContentLength(contentLength);
			}

			OutputStream outputStream = mimeResponse.getPortletOutputStream();

			outputStream.write(bytes, offset, contentLength);
		}
	}

	public static void write(MimeResponse mimeResponse, byte[][] bytesArray)
		throws IOException {

		// LEP-3122

		if (!mimeResponse.isCommitted()) {

			// LEP-536

			int contentLength = 0;

			for (byte[] bytes : bytesArray) {
				contentLength += bytes.length;
			}

			if (mimeResponse instanceof ResourceResponse) {
				ResourceResponse resourceResponse =
					(ResourceResponse)mimeResponse;

				resourceResponse.setContentLength(contentLength);
			}

			OutputStream outputStream = mimeResponse.getPortletOutputStream();

			for (byte[] bytes : bytesArray) {
				outputStream.write(bytes);
			}
		}
	}

	public static void write(MimeResponse mimeResponse, File file)
		throws IOException {

		FileInputStream fileInputStream = new FileInputStream(file);

		FileChannel fileChannel = fileInputStream.getChannel();

		try {
			int contentLength = (int)fileChannel.size();

			if (mimeResponse instanceof ResourceResponse) {
				ResourceResponse resourceResponse =
					(ResourceResponse)mimeResponse;

				resourceResponse.setContentLength(contentLength);
			}

			fileChannel.transferTo(
				0, contentLength,
				Channels.newChannel(mimeResponse.getPortletOutputStream()));
		}
		finally {
			fileChannel.close();
		}
	}

	public static void write(MimeResponse mimeResponse, InputStream is)
		throws IOException {

		write(mimeResponse, is, 0);
	}

	public static void write(
			MimeResponse mimeResponse, InputStream is, int contentLength)
		throws IOException {

		if (mimeResponse.isCommitted()) {
			return;
		}

		if (contentLength > 0) {
			if (mimeResponse instanceof ResourceResponse) {
				ResourceResponse resourceResponse =
					(ResourceResponse)mimeResponse;

				resourceResponse.setContentLength(contentLength);
			}
		}

		StreamUtil.transfer(is, mimeResponse.getPortletOutputStream());
	}

	public static void write(MimeResponse mimeResponse, String s)
		throws IOException {

		write(mimeResponse, s.getBytes(StringPool.UTF8));
	}

	protected static void setHeaders(
		PortletRequest portletRequest, MimeResponse mimeResponse,
		String fileName, String contentType, String contentDispositionType) {

		if (_log.isDebugEnabled()) {
			_log.debug("Sending file of type " + contentType);
		}

		// LEP-2201

		if (Validator.isNotNull(contentType)) {
			mimeResponse.setContentType(contentType);
		}

		mimeResponse.setProperty(
			HttpHeaders.CACHE_CONTROL, HttpHeaders.CACHE_CONTROL_PRIVATE_VALUE);

		if (Validator.isNotNull(fileName)) {
			String contentDispositionFilename = "filename=\"" + fileName + "\"";

			// If necessary for non-ASCII characters, encode based on RFC 2184.
			// However, not all browsers support RFC 2184. See LEP-3127.

			boolean ascii = true;

			for (int i = 0; i < fileName.length(); i++) {
				if (!Validator.isAscii(fileName.charAt(i))) {
					ascii = false;

					break;
				}
			}

			try {
				if (!ascii) {
					String encodedFileName = HttpUtil.encodeURL(fileName, true);

					HttpServletRequest request =
						PortalUtil.getHttpServletRequest(portletRequest);

					if (BrowserSnifferUtil.isIe(request)) {
						contentDispositionFilename =
							"filename=\"" + encodedFileName + "\"";
					}
					else {
						contentDispositionFilename =
							"filename*=UTF-8''" + encodedFileName;
					}
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e);
				}
			}

			if (Validator.isNull(contentDispositionType)) {
				String extension = GetterUtil.getString(
					FileUtil.getExtension(fileName)).toLowerCase();

				String[] mimeTypesContentDispositionInline = null;

				try {
					mimeTypesContentDispositionInline = PropsUtil.getArray(
						"mime.types.content.disposition.inline");
				}
				catch (Exception e) {
					mimeTypesContentDispositionInline = new String[0];
				}

				if (ArrayUtil.contains(
						mimeTypesContentDispositionInline, extension)) {

					contentDispositionType =
						HttpHeaders.CONTENT_DISPOSITION_INLINE;
				}
				else {
					contentDispositionType =
						HttpHeaders.CONTENT_DISPOSITION_ATTACHMENT;
				}
			}

			StringBundler sb = new StringBundler(4);

			sb.append(contentDispositionType);
			sb.append(StringPool.SEMICOLON);
			sb.append(StringPool.SPACE);
			sb.append(contentDispositionFilename);

			mimeResponse.setProperty(
				HttpHeaders.CONTENT_DISPOSITION, sb.toString());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PortletResponseUtil.class);

}
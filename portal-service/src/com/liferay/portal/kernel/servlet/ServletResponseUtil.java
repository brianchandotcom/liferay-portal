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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.nio.charset.CharsetEncoderUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.RandomAccessInputStream;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.SocketException;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ServletResponseUtil {

	public static List<Range> getRanges(
			HttpServletRequest request, HttpServletResponse response,
			long length)
		throws IOException {

		String rangeString = request.getHeader(HttpHeaders.RANGE);

		if (Validator.isNull(rangeString)) {
			return Collections.emptyList();
		}

		if (!rangeString.matches(_RANGE_REGEX)) {
			throw new IOException(
				"Range header does not match regular expression " +
				rangeString);
		}

		List<Range> ranges = new ArrayList<Range>();

		String[] rangeFields = StringUtil.split(rangeString.substring(6));

		if (rangeFields.length > _MAX_RANGE_FIELDS) {
			StringBundler sb = new StringBundler(8);

			sb.append("Request range ");
			sb.append(rangeString);
			sb.append(" with ");
			sb.append(rangeFields.length);
			sb.append(" range fields has exceeded maximum allowance as ");
			sb.append("specified by the property \"");
			sb.append(PropsKeys.WEB_SERVER_SERVLET_MAX_RANGE_FIELDS);
			sb.append("\"");

			throw new IOException(sb.toString());
		}

		for (String rangeField : rangeFields) {
			int index = rangeField.indexOf(StringPool.DASH);

			long start = GetterUtil.getLong(rangeField.substring(0, index), -1);
			long end = GetterUtil.getLong(
				rangeField.substring(index + 1, rangeField.length()), -1);

			if (start == -1) {
				start = length - end;
				end = length - 1;
			}
			else if ((end == -1) || (end > (length - 1))) {
				end = length - 1;
			}

			if (start > end) {
				throw new IOException(
					"Range start " + start + " is greater than end " + end);
			}

			Range range = new Range(start, end, length);

			ranges.add(range);
		}

		return ranges;
	}

	public static void sendFile(
			HttpServletRequest request, HttpServletResponse response,
			String fileName, byte[] bytes)
		throws IOException {

		sendFile(request, response, fileName, bytes, null);
	}

	public static void sendFile(
			HttpServletRequest request, HttpServletResponse response,
			String fileName, byte[] bytes, String contentType)
		throws IOException {

		setHeaders(request, response, fileName, contentType);

		write(response, bytes);
	}

	public static void sendFile(
			HttpServletRequest request, HttpServletResponse response,
			String fileName, InputStream is)
		throws IOException {

		sendFile(request, response, fileName, is, null);
	}

	public static void sendFile(
			HttpServletRequest request, HttpServletResponse response,
			String fileName, InputStream is, long contentLength,
			String contentType)
		throws IOException {

		setHeaders(request, response, fileName, contentType);

		write(response, is, contentLength);
	}

	public static void sendFile(
			HttpServletRequest request, HttpServletResponse response,
			String fileName, InputStream is, String contentType)
		throws IOException {

		sendFile(request, response, fileName, is, 0, contentType);
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	public static void sendFile(
			HttpServletResponse response, String fileName, byte[] bytes)
		throws IOException {

		sendFile(null, response, fileName, bytes);
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	public static void sendFile(
			HttpServletResponse response, String fileName, byte[] bytes,
			String contentType)
		throws IOException {

		sendFile(null, response, fileName, bytes, contentType);
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	public static void sendFile(
			HttpServletResponse response, String fileName, InputStream is)
		throws IOException {

		sendFile(null, response, fileName, is);
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	public static void sendFile(
			HttpServletResponse response, String fileName, InputStream is,
			int contentLength, String contentType)
		throws IOException {

		sendFile(null, response, fileName, is, contentLength, contentType);
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	public static void sendFile(
			HttpServletResponse response, String fileName, InputStream is,
			String contentType)
		throws IOException {

		sendFile(null, response, fileName, is, contentType);
	}

	public static void write(
			HttpServletRequest request, HttpServletResponse response,
			String fileName, List<Range> ranges, InputStream inputStream,
			long fullLength, String contentType)
		throws IOException {

		OutputStream outputStream = null;

		try {
			outputStream = response.getOutputStream();

			Range fullRange = new Range(0, fullLength - 1, fullLength);

			Range firstRange = null;

			if (!ranges.isEmpty()) {
				firstRange = ranges.get(0);
			}

			if ((firstRange == null) || firstRange.equals(fullRange)) {
				if (_log.isDebugEnabled()) {
					_log.debug("Writing full range");
				}

				response.setContentType(contentType);

				setHeaders(request, response, fileName, contentType, fullRange);

				copyRange(
					inputStream, outputStream, fullRange.getStart(),
					fullRange.getLength());
			}
			else if (ranges.size() == 1) {
				if (_log.isDebugEnabled()) {
					_log.debug("Attempting to write a single range");
				}

				Range range = ranges.get(0);

				response.setContentType(contentType);

				setHeaders(request, response, fileName, contentType, range);

				response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);

				copyRange(
					inputStream, outputStream, range.getStart(),
					range.getLength());
			}
			else if (ranges.size() > 1 ) {
				if (_log.isDebugEnabled()) {
					_log.debug("Attempting to write multiple ranges");
				}

				ServletOutputStream servletOutputStream =
					(ServletOutputStream)outputStream;

				String boundary =
					"liferay-multipart-boundary-" + System.currentTimeMillis();

				String multipartContentType =
					"multipart/byteranges; boundary=" + boundary;

				response.setContentType(multipartContentType);

				setHeaders(
					request, response, fileName, multipartContentType, null);

				response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);

				for (int i = 0; i < ranges.size(); i++) {
					Range range = ranges.get(i);

					servletOutputStream.println();
					servletOutputStream.println(
						StringPool.DOUBLE_DASH + boundary);
					servletOutputStream.println(
						HttpHeaders.CONTENT_TYPE + ": " + contentType);
					servletOutputStream.println(
						HttpHeaders.CONTENT_RANGE + ": " +
							range.getContentRange());
					servletOutputStream.println();

					copyRange(
						inputStream, outputStream, range.getStart(),
						range.getLength());
				}

				servletOutputStream.println();
				servletOutputStream.println(
					StringPool.DOUBLE_DASH + boundary + StringPool.DOUBLE_DASH);
			}
		}
		finally {
			try {
				inputStream.close();
			}
			catch (IOException ioe) {
			}
		}
	}

	public static void write(
			HttpServletResponse response,
			BufferCacheServletResponse bufferCacheServletResponse)
		throws IOException {

		if (bufferCacheServletResponse.isByteMode()) {
			write(response, bufferCacheServletResponse.getByteBuffer());
		}
		else if (bufferCacheServletResponse.isCharMode()) {
			write(response, bufferCacheServletResponse.getCharBuffer());
		}
	}

	public static void write(HttpServletResponse response, byte[] bytes)
		throws IOException {

		write(response, bytes, 0, 0);
	}

	public static void write(
			HttpServletResponse response, byte[] bytes, int offset,
			int contentLength)
		throws IOException {

		try {

			// LEP-3122

			if (!response.isCommitted()) {

				// LEP-536

				if (contentLength == 0) {
					contentLength = bytes.length;
				}

				response.setContentLength(contentLength);

				response.flushBuffer();

				if (response instanceof BufferCacheServletResponse) {
					BufferCacheServletResponse bufferCacheServletResponse =
						(BufferCacheServletResponse)response;

					bufferCacheServletResponse.setByteBuffer(
						ByteBuffer.wrap(bytes, offset, contentLength));
				}
				else {
					ServletOutputStream servletOutputStream =
						response.getOutputStream();

					if ((contentLength == 0) && ServerDetector.isJetty()) {
					}
					else {
						servletOutputStream.write(bytes, offset, contentLength);
					}
				}
			}
		}
		catch (IOException ioe) {
			if ((ioe instanceof SocketException) ||
				isClientAbortException(ioe)) {

				if (_log.isWarnEnabled()) {
					_log.warn(ioe);
				}
			}
			else {
				throw ioe;
			}
		}
	}

	public static void write(HttpServletResponse response, byte[][] bytesArray)
		throws IOException {

		try {

			// LEP-3122

			if (!response.isCommitted()) {
				int contentLength = 0;

				for (byte[] bytes : bytesArray) {
					contentLength += bytes.length;
				}

				response.setContentLength(contentLength);

				response.flushBuffer();

				ServletOutputStream servletOutputStream =
					response.getOutputStream();

				for (byte[] bytes : bytesArray) {
					servletOutputStream.write(bytes);
				}
			}
		}
		catch (IOException ioe) {
			if ((ioe instanceof SocketException) ||
				isClientAbortException(ioe)) {

				if (_log.isWarnEnabled()) {
					_log.warn(ioe);
				}
			}
			else {
				throw ioe;
			}
		}
	}

	public static void write(
			HttpServletResponse response, ByteBuffer byteBuffer)
		throws IOException {

		if (response instanceof BufferCacheServletResponse) {
			BufferCacheServletResponse bufferCacheServletResponse =
				(BufferCacheServletResponse)response;

			bufferCacheServletResponse.setByteBuffer(byteBuffer);
		}
		else {
			write(
				response, byteBuffer.array(), byteBuffer.position(),
				byteBuffer.limit());
		}
	}

	public static void write(
			HttpServletResponse response, CharBuffer charBuffer)
		throws IOException {

		if (response instanceof BufferCacheServletResponse) {
			BufferCacheServletResponse bufferCacheServletResponse =
				(BufferCacheServletResponse)response;

			bufferCacheServletResponse.setCharBuffer(charBuffer);
		}
		else {
			ByteBuffer byteBuffer = CharsetEncoderUtil.encode(
				StringPool.UTF8, charBuffer);

			write(response, byteBuffer);
		}
	}

	public static void write(HttpServletResponse response, File file)
		throws IOException {

		if (response instanceof BufferCacheServletResponse) {
			BufferCacheServletResponse bufferCacheServletResponse =
				(BufferCacheServletResponse)response;

			ByteBuffer byteBuffer = ByteBuffer.wrap(FileUtil.getBytes(file));

			bufferCacheServletResponse.setByteBuffer(byteBuffer);
		}
		else {
			FileInputStream fileInputStream = new FileInputStream(file);

			FileChannel fileChannel = fileInputStream.getChannel();

			try {
				int contentLength = (int)fileChannel.size();

				response.setContentLength(contentLength);

				response.flushBuffer();

				fileChannel.transferTo(
					0, contentLength,
					Channels.newChannel(response.getOutputStream()));
			}
			finally {
				fileChannel.close();
			}
		}
	}

	public static void write(HttpServletResponse response, InputStream is)
		throws IOException {

		write(response, is, 0);
	}

	public static void write(
			HttpServletResponse response, InputStream is, long contentLength)
		throws IOException {

		if (response.isCommitted()) {
			return;
		}

		if (contentLength > 0) {
			response.setHeader(
				HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength));
		}

		response.flushBuffer();

		StreamUtil.transfer(is, response.getOutputStream());
	}

	public static void write(HttpServletResponse response, String s)
		throws IOException {

		if (response instanceof BufferCacheServletResponse) {
			BufferCacheServletResponse bufferCacheServletResponse =
				(BufferCacheServletResponse)response;

			bufferCacheServletResponse.setString(s);
		}
		else {
			ByteBuffer byteBuffer = CharsetEncoderUtil.encode(
				StringPool.UTF8, s);

			write(response, byteBuffer);
		}
	}

	protected static void copyRange(
			InputStream inputStream, OutputStream outputStream, long start,
			long length)
		throws IOException {

		if (inputStream instanceof FileInputStream) {
			FileInputStream fileInputStream = (FileInputStream)inputStream;

			FileChannel fileChannel = fileInputStream.getChannel();

			fileChannel.transferTo(
				start, length, Channels.newChannel(outputStream));
		}
		else if (inputStream instanceof ByteArrayInputStream) {
			ByteArrayInputStream byteArrayInputStream =
				(ByteArrayInputStream)inputStream;

			byteArrayInputStream.skip(start);

			StreamUtil.transfer(byteArrayInputStream, outputStream, length);
		}
		else {
			RandomAccessInputStream randomAccessInputStream =
				new RandomAccessInputStream(inputStream);

			randomAccessInputStream.seek(start);

			StreamUtil.transfer(randomAccessInputStream, outputStream, length);
		}
	}

	protected static boolean isClientAbortException(IOException ioe) {
		Class<?> clazz = ioe.getClass();

		String className = clazz.getName();

		if (className.equals(_CLIENT_ABORT_EXCEPTION)) {
			return true;
		}
		else {
			return false;
		}
	}

	protected static void setHeaders(
		HttpServletRequest request, HttpServletResponse response,
		String fileName, String contentType) {

		if (_log.isDebugEnabled()) {
			_log.debug("Sending file of type " + contentType);
		}

		// LEP-2201

		if (Validator.isNotNull(contentType)) {
			response.setContentType(contentType);
		}

		if (!response.containsHeader(HttpHeaders.CACHE_CONTROL)) {
			response.setHeader(
				HttpHeaders.CACHE_CONTROL,
				HttpHeaders.CACHE_CONTROL_PRIVATE_VALUE);
		}

		if (Validator.isNotNull(fileName)) {
			String contentDisposition =
				"attachment; filename=\"" + fileName + "\"";

			// If necessary for non-ASCII characters, encode based on RFC 2184.
			// However, not all browsers support RFC 2184. See LEP-3127.

			boolean ascii = true;

			for (int i = 0; i < fileName.length(); i++) {
				if (!Validator.isAscii(fileName.charAt(i))) {
					ascii = false;

					break;
				}
			}

			if (!ascii) {
				String encodedFileName = HttpUtil.encodeURL(fileName, true);

				if (BrowserSnifferUtil.isIe(request)) {
					contentDisposition =
						"attachment; filename=\"" + encodedFileName + "\"";
				}
				else {
					contentDisposition =
						"attachment; filename*=UTF-8''" + encodedFileName;
				}
			}

			String extension = GetterUtil.getString(
				FileUtil.getExtension(fileName)).toLowerCase();

			String[] mimeTypesContentDispositionInline = null;

			try {
				mimeTypesContentDispositionInline = PropsUtil.getArray(
					PropsKeys.MIME_TYPES_CONTENT_DISPOSITION_INLINE);
			}
			catch (Exception e) {
				mimeTypesContentDispositionInline = new String[0];
			}

			if (ArrayUtil.contains(
					mimeTypesContentDispositionInline, extension)) {

				contentDisposition = StringUtil.replace(
					contentDisposition, "attachment; ", "inline; ");
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Setting content disposition header " + contentDisposition);
			}

			response.setHeader(
				HttpHeaders.CONTENT_DISPOSITION, contentDisposition);
		}
	}

	protected static void setHeaders(
		HttpServletRequest request, HttpServletResponse response,
		String fileName, String contentType, Range range) {

		setHeaders(request, response, fileName, contentType);

		if (range != null) {
			response.setHeader(
				HttpHeaders.CONTENT_RANGE, range.getContentRange());

			response.setHeader(
				HttpHeaders.CONTENT_LENGTH, String.valueOf(range.getLength()));
		}
	}

	private static final String _CLIENT_ABORT_EXCEPTION =
		"org.apache.catalina.connector.ClientAbortException";

	private static final int _MAX_RANGE_FIELDS = GetterUtil.getInteger(
		PropsUtil.get(PropsKeys.WEB_SERVER_SERVLET_MAX_RANGE_FIELDS));

	private static final String _RANGE_REGEX =
		"^bytes=\\d*-\\d*(,\\s?\\d*-\\d*)*$";

	private static Log _log = LogFactoryUtil.getLog(ServletResponseUtil.class);

}
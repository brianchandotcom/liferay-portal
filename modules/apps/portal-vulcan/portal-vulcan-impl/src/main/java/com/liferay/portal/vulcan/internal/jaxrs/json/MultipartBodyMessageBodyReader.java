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

package com.liferay.portal.vulcan.internal.jaxrs.json;

import static org.apache.commons.fileupload.servlet.ServletFileUpload.isMultipartContent;

import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;

import java.io.IOException;
import java.io.InputStream;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

/**
 * @author Alejandro Hernández
 * @author Javier Gamarra
 */
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Provider
public class MultipartBodyMessageBodyReader
	implements MessageBodyReader<MultipartBody> {

	@Override
	public boolean isReadable(
		Class<?> clazz, Type genericType, Annotation[] annotations,
		MediaType mediaType) {

		return true;
	}

	@Override
	public MultipartBody readFrom(
			Class<MultipartBody> clazz, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders,
			InputStream entityStream)
		throws IOException {

		if (!isMultipartContent(_httpServletRequest)) {
			throw new BadRequestException(
				"Request body is not a valid multipart form");
		}

		FileItemFactory fileItemFactory = new DiskFileItemFactory();

		ServletFileUpload servletFileUpload = new ServletFileUpload(
			fileItemFactory);

		try {
			List<FileItem> fileItems = servletFileUpload.parseRequest(
				_httpServletRequest);

			Iterator<FileItem> iterator = fileItems.iterator();

			Map<String, String> values = new HashMap<>();
			Map<String, BinaryFile> binaryFiles = new HashMap<>();

			while (iterator.hasNext()) {
				FileItem fileItem = iterator.next();

				String name = fileItem.getFieldName();

				if (fileItem.isFormField()) {
					InputStream stream = fileItem.getInputStream();

					values.put(name, Streams.asString(stream));
				}
				else {
					BinaryFile binaryFile = new BinaryFile(
						fileItem.getInputStream(), fileItem.getSize(),
						fileItem.getContentType(), fileItem.getName());

					binaryFiles.put(name, binaryFile);
				}
			}

			return MultipartBody.of(values, binaryFiles);
		}
		catch (FileUploadException fue) {
			throw new BadRequestException(
				"Request body is not a valid multipart form", fue);
		}
	}

	@Context
	private HttpServletRequest _httpServletRequest;

}
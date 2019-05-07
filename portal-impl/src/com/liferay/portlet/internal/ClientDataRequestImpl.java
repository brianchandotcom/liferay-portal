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

package com.liferay.portlet.internal;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.ClientDataRequest;
import javax.portlet.PortletException;
import javax.portlet.RenderParameters;

import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 * @author Brian Wing Shun Chan
 * @author Neil Griffin
 */
public abstract class ClientDataRequestImpl
	extends PortletRequestImpl implements ClientDataRequest {

	@Override
	public String getCharacterEncoding() {
		return getHttpServletRequest().getCharacterEncoding();
	}

	@Override
	public int getContentLength() {
		return getHttpServletRequest().getContentLength();
	}

	@Override
	public long getContentLengthLong() {
		return getHttpServletRequest().getContentLengthLong();
	}

	@Override
	public String getContentType() {
		return getHttpServletRequest().getContentType();
	}

	@Override
	public String getMethod() {
		return getHttpServletRequest().getMethod();
	}

	@Override
	public Part getPart(String name) throws IOException, PortletException {
		UploadRequest uploadRequest = _getUploadRequest(
			getHttpServletRequest());

		if (uploadRequest == null) {
			return null;
		}

		Map<String, FileItem[]> multipartParameterMap =
			uploadRequest.getMultipartParameterMap();

		FileItem[] fileItems = multipartParameterMap.get(name);

		if ((fileItems == null) || (fileItems.length == 0)) {
			return null;
		}

		Portlet portlet = getPortlet();

		return new PartImpl(fileItems[0], portlet.getMultipartLocation());
	}

	@Override
	public Collection<Part> getParts() throws IOException, PortletException {
		UploadRequest uploadRequest = _getUploadRequest(
			getHttpServletRequest());

		if (uploadRequest == null) {
			return Collections.emptySet();
		}

		List<Part> parts = new ArrayList<>();

		Map<String, FileItem[]> multipartParameterMap =
			uploadRequest.getMultipartParameterMap();

		Portlet portlet = getPortlet();

		for (Map.Entry<String, FileItem[]> entry :
				multipartParameterMap.entrySet()) {

			FileItem[] fileItems = entry.getValue();

			for (FileItem fileItem : fileItems) {
				parts.add(
					new PartImpl(fileItem, portlet.getMultipartLocation()));
			}
		}

		return parts;
	}

	@Override
	public InputStream getPortletInputStream() throws IOException {
		_checkContentType();

		return getHttpServletRequest().getInputStream();
	}

	@Override
	public BufferedReader getReader()
		throws IOException, UnsupportedEncodingException {

		_calledGetReader = true;

		_checkContentType();

		return getHttpServletRequest().getReader();
	}

	@Override
	public void setCharacterEncoding(String enc)
		throws UnsupportedEncodingException {

		if (_calledGetReader) {
			throw new IllegalStateException();
		}

		getHttpServletRequest().setCharacterEncoding(enc);
	}

	protected Map<String, String[]> getPortletParameterMap(
		HttpServletRequest httpServletRequest, String portletNamespace) {

		Map<String, String[]> portletParameterMap = new LinkedHashMap<>();
		Map<String, String[]> parameterMap = getParameterMap();
		Map<String, String[]> servletRequestParameterMap =
			httpServletRequest.getParameterMap();

		RenderParameters renderParameters = getRenderParameters();

		Set<String> renderParameterNames = renderParameters.getNames();

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String name = entry.getKey();

			// If the parameter name is not a public/private render parameter,
			// then regard it as an action parameter (during an ActionRequest)
			// or as a resource parameter (during a ResourceRequest). Also,
			// if the parameter name is prefixed with the portlet namespace in
			// the original request, then regard it as an action or resource
			// parameter (even if it has the same name as a public render
			// parameter). See: TCK V3PortletParametersTests_SPEC11_3_getNames
			// and V3PortletParametersTests_SPEC11_4_getNames.

			if (renderParameterNames.contains(name)) {
				String[] values = servletRequestParameterMap.get(
					portletNamespace.concat(name));

				if (values != null) {
					portletParameterMap.put(name, values);
				}
			}
			else {
				portletParameterMap.put(name, entry.getValue());
			}
		}

		return portletParameterMap;
	}

	private void _checkContentType() {
		if (StringUtil.equalsIgnoreCase(getMethod(), HttpMethods.POST) &&
			StringUtil.equalsIgnoreCase(
				getContentType(),
				ContentTypes.APPLICATION_X_WWW_FORM_URLENCODED)) {

			throw new IllegalStateException();
		}
	}

	private UploadRequest _getUploadRequest(Object request) {
		while (true) {
			if (request instanceof UploadRequest) {
				return (UploadRequest)request;
			}

			if (request instanceof ServletRequestWrapper) {
				ServletRequestWrapper servletRequestWrapper =
					(ServletRequestWrapper)request;

				request = servletRequestWrapper.getRequest();
			}
			else {
				return null;
			}
		}
	}

	private boolean _calledGetReader;

	private static final class PartImpl implements Part {

		@Override
		public void delete() throws IOException {
			_fileItem.delete();
		}

		@Override
		public String getContentType() {
			return _fileItem.getContentType();
		}

		@Override
		public String getHeader(String name) {
			return _fileItem.getHeader(name);
		}

		@Override
		public Collection<String> getHeaderNames() {
			return _fileItem.getHeaderNames();
		}

		@Override
		public Collection<String> getHeaders(String name) {
			return _fileItem.getHeaders(name);
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return _fileItem.getInputStream();
		}

		@Override
		public String getName() {
			return _fileItem.getFieldName();
		}

		@Override
		public long getSize() {
			return _fileItem.getSize();
		}

		@Override
		public String getSubmittedFileName() {
			return _fileItem.getFileName();
		}

		@Override
		public void write(String fileName) throws IOException {
			if (Validator.isNull(fileName)) {
				throw new IOException("Invalid file name");
			}

			try {
				File file = new File(fileName);

				if (!file.isAbsolute() &&
					Validator.isNotNull(_multipartLocation)) {

					File multipartLocation = new File(_multipartLocation);

					if (multipartLocation.isDirectory()) {
						file = new File(multipartLocation, fileName);
					}
				}

				_fileItem.write(file);
			}
			catch (Exception e) {
				throw new IOException(e);
			}
		}

		private PartImpl(FileItem fileItem, String multipartLocation) {
			_fileItem = fileItem;
			_multipartLocation = multipartLocation;
		}

		private final FileItem _fileItem;
		private final String _multipartLocation;

	}

}
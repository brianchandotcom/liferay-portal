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

package com.liferay.portal.sharepoint;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StreamUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Bruno Farache
 */
public class SharepointRequest {

	public SharepointRequest(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, User user)
		throws SharepointException {

		this(httpServletRequest, httpServletResponse, user, StringPool.BLANK);
	}

	public SharepointRequest(String rootPath) throws SharepointException {
		this(null, null, null, rootPath);
	}

	public void addParam(String key, String value) {
		_params.put(key, new String[] {value});
	}

	public byte[] getBytes() {
		return _bytes;
	}

	public long getCompanyId() {
		return _user.getCompanyId();
	}

	public HttpServletRequest getHttpServletRequest() {
		return _httpServletRequest;
	}

	public HttpServletResponse getHttpServletResponse() {
		return _httpServletResponse;
	}

	public String getParameterValue(String name) {
		String[] values = _params.get(name);

		if (ArrayUtil.isNotEmpty(values)) {
			return GetterUtil.getString(_params.get(name)[0]);
		}

		return StringPool.BLANK;
	}

	public String getRootPath() {
		return _rootPath;
	}

	public SharepointStorage getSharepointStorage() {
		return _storage;
	}

	public User getUser() {
		return _user;
	}

	public long getUserId() {
		return _user.getUserId();
	}

	public void setBytes(byte[] bytes) {
		_bytes = bytes;
	}

	public void setRootPath(String rootPath) {
		_rootPath = SharepointUtil.replaceBackSlashes(rootPath);
	}

	public void setSharepointStorage(SharepointStorage storage) {
		_storage = storage;
	}

	protected void addParams() throws SharepointException {
		String contentType = _httpServletRequest.getContentType();

		if (!contentType.equals(SharepointUtil.VEERMER_URLENCODED)) {
			return;
		}

		try {
			InputStream is = _httpServletRequest.getInputStream();

			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();

			StreamUtil.transfer(is, unsyncByteArrayOutputStream);

			byte[] bytes = unsyncByteArrayOutputStream.toByteArray();

			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(
					new InputStreamReader(new ByteArrayInputStream(bytes)));

			String url = unsyncBufferedReader.readLine();

			String[] params = url.split(StringPool.AMPERSAND);

			for (String param : params) {
				String[] kvp = param.split(StringPool.EQUAL);

				String key = HttpUtil.decodeURL(kvp[0]);

				String value = StringPool.BLANK;

				if (kvp.length > 1) {
					value = HttpUtil.decodeURL(kvp[1]);
				}

				addParam(key, value);
			}

			bytes = ArrayUtil.subset(bytes, url.length() + 1, bytes.length);

			setBytes(bytes);
		}
		catch (Exception e) {
			throw new SharepointException(e);
		}
	}

	private SharepointRequest(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, User user, String rootPath)
		throws SharepointException {

		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;
		_user = user;
		_rootPath = rootPath;

		_params.putAll(httpServletRequest.getParameterMap());

		addParams();
	}

	private byte[] _bytes;
	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private final Map<String, String[]> _params = new HashMap<>();
	private String _rootPath = StringPool.BLANK;
	private SharepointStorage _storage;
	private final User _user;

}
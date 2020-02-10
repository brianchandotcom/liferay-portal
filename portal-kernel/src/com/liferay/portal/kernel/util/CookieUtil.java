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

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;

import java.nio.ByteBuffer;

import java.util.Objects;

import javax.servlet.http.Cookie;

/**
 * @author Shuyang Zhou
 */
public class CookieUtil {

	public static Cookie deserialize(byte[] bytes) {
		Deserializer deserializer = new Deserializer(ByteBuffer.wrap(bytes));

		String comment = deserializer.readString();
		String domain = deserializer.readString();
		boolean httpOnly = deserializer.readBoolean();
		int maxAge = deserializer.readInt();
		String name = deserializer.readString();
		String path = deserializer.readString();
		boolean secure = deserializer.readBoolean();

		String value = deserializer.readString();

		if (value.isEmpty()) {
			value = null;
		}

		int version = deserializer.readInt();

		Cookie cookie = new Cookie(name, value);

		if (!comment.isEmpty()) {
			cookie.setComment(comment);
		}

		if (!domain.isEmpty()) {
			cookie.setDomain(domain);
		}

		cookie.setHttpOnly(httpOnly);
		cookie.setMaxAge(maxAge);

		if (!path.isEmpty()) {
			cookie.setPath(path);
		}

		cookie.setSecure(secure);
		cookie.setVersion(version);

		return cookie;
	}

	public static boolean equals(Cookie cookie1, Cookie cookie2) {
		if (!Objects.equals(cookie1.getComment(), cookie2.getComment())) {
			return false;
		}

		if (!Objects.equals(cookie1.getDomain(), cookie2.getDomain())) {
			return false;
		}

		if (cookie1.getMaxAge() != cookie2.getMaxAge()) {
			return false;
		}

		if (!Objects.equals(cookie1.getName(), cookie2.getName())) {
			return false;
		}

		if (!Objects.equals(cookie1.getPath(), cookie2.getPath())) {
			return false;
		}

		if (cookie1.getSecure() != cookie2.getSecure()) {
			return false;
		}

		if (!Objects.equals(cookie1.getValue(), cookie2.getValue())) {
			return false;
		}

		if (cookie1.getVersion() != cookie2.getVersion()) {
			return false;
		}

		if (cookie1.isHttpOnly() != cookie2.isHttpOnly()) {
			return false;
		}

		return true;
	}

	public static byte[] serialize(Cookie cookie) {
		Serializer serializer = new Serializer();

		String comment = cookie.getComment();

		if (comment == null) {
			comment = StringPool.BLANK;
		}

		serializer.writeString(comment);

		String domain = cookie.getDomain();

		if (domain == null) {
			domain = StringPool.BLANK;
		}

		serializer.writeString(domain);

		serializer.writeBoolean(cookie.isHttpOnly());
		serializer.writeInt(cookie.getMaxAge());
		serializer.writeString(cookie.getName());

		String path = cookie.getPath();

		if (path == null) {
			path = StringPool.BLANK;
		}

		serializer.writeString(path);

		serializer.writeBoolean(cookie.getSecure());

		String value = cookie.getValue();

		if (value == null) {
			value = StringPool.BLANK;
		}

		serializer.writeString(value);

		serializer.writeInt(cookie.getVersion());

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		return byteBuffer.array();
	}

	public static String toString(Cookie cookie) {
		StringBundler sb = new StringBundler(19);

		sb.append("{comment=");
		sb.append(cookie.getComment());
		sb.append(", domain=");
		sb.append(cookie.getDomain());
		sb.append(", httpOnly=");
		sb.append(cookie.isHttpOnly());
		sb.append(", maxAge=");
		sb.append(cookie.getMaxAge());
		sb.append(", name=");
		sb.append(cookie.getName());
		sb.append(", path=");
		sb.append(cookie.getPath());
		sb.append(", secure=");
		sb.append(cookie.getSecure());
		sb.append(", value=");
		sb.append(cookie.getValue());
		sb.append(", version=");
		sb.append(cookie.getVersion());
		sb.append("}");

		return sb.toString();
	}

}
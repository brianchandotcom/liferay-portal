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

package com.liferay.portal.kernel.xmlrpc;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public interface XmlRpcConstants {

	public static final int APPLICATION_ERROR = -32500;

	public static final int INTERNAL_XMLRPC_ERROR = -32603;

	public static final int INVALID_CHARACTER_FOR_ENCODING = -32702;

	public static final int INVALID_METHOD_PARAMETERS = -32602;

	public static final int INVALID_XMLRPC = -32600;

	public static final int NOT_WELL_FORMED = -32700;

	public static final int REQUESTED_METHOD_NOT_FOUND = -32601;

	public static final int SYSTEM_ERROR = -32400;

	public static final int TRANSPORT_ERROR = -32300;

	public static final int UNSUPPORTED_ENCODING = -32701;

}
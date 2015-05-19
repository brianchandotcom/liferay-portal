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

package com.liferay.portal.store.jcr;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

/**
 * @author Michael Young
 */
public interface JCRFactory {

	public static final String NODE_DOCUMENTLIBRARY = PropsUtil.get(
		PropsKeys.JCR_NODE_DOCUMENTLIBRARY);

	public static final String WORKSPACE_NAME = PropsUtil.get(
		PropsKeys.JCR_WORKSPACE_NAME);

	public Session createSession(String workspaceName)
		throws RepositoryException;

	public void initialize() throws RepositoryException;

	public void prepare() throws RepositoryException;

	public void shutdown();

}
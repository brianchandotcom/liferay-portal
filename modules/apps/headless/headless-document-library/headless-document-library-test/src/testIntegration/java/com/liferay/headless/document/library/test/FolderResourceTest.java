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

package com.liferay.headless.document.library.test;

import com.liferay.headless.document.library.dto.v1_0.Folder;
import com.liferay.portal.kernel.util.StringUtil;

import javax.annotation.Generated;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;

import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@Generated("")
@RunAsClient
@RunWith(Arquillian.class)
public class FolderResourceTest extends BaseFolderResourceTestCase {

	@Override
	protected Folder getFolder() {
		Folder folder = new Folder();

		folder.setDescription(StringUtil.randomString(10));
		folder.setDocumentsRepositoryId(getGroupId());
		folder.setName(StringUtil.randomString(10));

		return folder;
	}

}
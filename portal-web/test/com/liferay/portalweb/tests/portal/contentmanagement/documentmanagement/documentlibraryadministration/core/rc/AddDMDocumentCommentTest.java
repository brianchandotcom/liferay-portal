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

package com.liferay.portalweb.tests.portal.contentmanagement.documentmanagement.documentlibraryadministration.core.rc;

import com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.macros.CPDocumentsAndMediaCommentMacros;
import com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.macros.CPDocumentsAndMediaDocumentMacros;
import com.liferay.portalweb.blocks.portal.recyclebin.controlpanel.macros.CPRecycleBinMacros;
import com.liferay.portalweb.blocks.portal.signin.page.macros.SignInUserMacros;
import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.SeleniumUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class AddDMDocumentCommentTest extends BaseTestCase {
	@Override
	public void setUp() throws Exception {
		selenium = SeleniumUtil.getSelenium();

		CPDocumentsAndMediaDocumentMacros cPDocumentsAndMediaDocumentMacros = new CPDocumentsAndMediaDocumentMacros(selenium);
		SignInUserMacros signInUserMacros = new SignInUserMacros(selenium);

		signInUserMacros.signIn("test@liferay.com", "test");
		cPDocumentsAndMediaDocumentMacros.add("DM Document Title",
			"Document_1.doc");
	}

	public void test() throws Exception {
		CPDocumentsAndMediaCommentMacros cPDocumentsAndMediaCommentMacros = new CPDocumentsAndMediaCommentMacros(selenium);

		cPDocumentsAndMediaCommentMacros.add("DM Document Title",
			"DM Document Comment");
	}

	@Override
	public void tearDown() throws Exception {
		CPDocumentsAndMediaDocumentMacros cPDocumentsAndMediaDocumentMacros = new CPDocumentsAndMediaDocumentMacros(selenium);
		CPRecycleBinMacros cPRecycleBinMacros = new CPRecycleBinMacros(selenium);
		SignInUserMacros signInUserMacros = new SignInUserMacros(selenium);

		cPDocumentsAndMediaDocumentMacros.tearDown();
		cPRecycleBinMacros.empty();
		signInUserMacros.signOut();
	}
}
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

package com.liferay.portal.verify;

/**
 * @author Alexander Chow
 */
public class VerifyProcessSuite extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verify(new VerifyProperties());

		verify(new VerifyDB2());
		verify(new VerifyMySQL());
		verify(new VerifyOracle());
		verify(new VerifySQLServer());

		verify(new VerifyUUID());

		verify(new VerifyPermission());
		verify(new VerifyGroup());
		verify(new VerifyRole());

		verify(new VerifyAsset());
		verify(new VerifyAuditedModel());
		verify(new VerifyBlogs());
		verify(new VerifyBookmarks());
		verify(new VerifyDocumentLibrary());
		verify(new VerifyDynamicDataMapping());
		verify(new VerifyGroupId());
		verify(new VerifyJournal());
		verify(new VerifyLayout());
		verify(new VerifyMessageBoards());
		verify(new VerifyOrganization());
		verify(new VerifyResourcePermissions());
		verify(new VerifyUser());
		verify(new VerifyWiki());
		verify(new VerifyWorkflow());

		// VerifyBlogsTrackbacks looks at every blog comment to see if it is a
		// trackback and verifies that the source URL is a valid URL.

		//verify(new VerifyBlogsTrackbacks());
	}

}
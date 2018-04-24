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

package com.liferay.document.library.search.test;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.search.test.util.IndexedFieldsFixture;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wade Cao
 * @author Eric Yan
 */
public abstract class BaseDLIndexerTestCase {

	public void setUp() throws Exception {
		dlFixture = createDLFixture();

		dlFixture.setUp();

		dlSearchFixture = createDLSearchFixture();
		indexedFieldsFixture = createIndexedFieldsFixture();
	}

	protected DLFixture createDLFixture() {
		return new DLFixture(dlAppLocalService, _groups, _users);
	}

	protected DLSearchFixture createDLSearchFixture() {
		return new DLSearchFixture(indexerRegistry);
	}

	protected IndexedFieldsFixture createIndexedFieldsFixture() {
		return new IndexedFieldsFixture(
			resourcePermissionLocalService, searchEngineHelper);
	}

	protected void setGroup(Group group) {
		dlFixture.setGroup(group);
		dlSearchFixture.setGroup(group);
	}

	protected void setIndexerClass(Class<?> clazz) {
		dlSearchFixture.setIndexerClass(clazz);
	}

	protected void setUser(User user) {
		dlFixture.setUser(user);
		dlSearchFixture.setUser(user);
	}

	@Inject
	protected DLAppLocalService dlAppLocalService;

	@Inject
	protected DLFileEntryMetadataLocalService dlFileEntryMetadataLocalService;

	protected DLFixture dlFixture;
	protected DLSearchFixture dlSearchFixture;
	protected IndexedFieldsFixture indexedFieldsFixture;

	@Inject
	protected IndexerRegistry indexerRegistry;

	@Inject
	protected ResourcePermissionLocalService resourcePermissionLocalService;

	@Inject
	protected SearchEngineHelper searchEngineHelper;

	@DeleteAfterTestRun
	private final List<Group> _groups = new ArrayList<>(1);

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>(1);

}
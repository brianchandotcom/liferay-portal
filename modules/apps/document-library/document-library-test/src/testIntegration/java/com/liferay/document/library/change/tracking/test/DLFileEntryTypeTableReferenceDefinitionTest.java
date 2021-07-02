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

package com.liferay.document.library.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseTableReferenceDefinitionTestCase;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.HashMap;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Cheryl Tang
 */
@RunWith(Arquillian.class)
public class DLFileEntryTypeTableReferenceDefinitionTest
	extends BaseTableReferenceDefinitionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		DDMStructure ddmStructure = _ddmStructureLocalService.addStructure(
			group.getCreatorUserId(), group.getGroupId(),
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			PortalUtil.getClassNameId(DLFileEntryMetadata.class),
			StringPool.BLANK,
			HashMapBuilder.put(
				LocaleUtil.getDefault(),
				DLFileEntryMetadata.class.getSimpleName()
			).build(),
			new HashMap<>(), StringPool.BLANK, StorageType.DEFAULT.toString(),
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		return _dlFileEntryTypeLocalService.addFileEntryType(
			group.getCreatorUserId(), group.getGroupId(),
			ddmStructure.getStructureId(),
			DLFileEntryTypeTableReferenceDefinitionTest.class.getSimpleName(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(),
				DLFileEntryTypeTableReferenceDefinitionTest.class.
					getSimpleName()
			).build(),
			new HashMap<>(),
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_SCOPE_DEFAULT,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	@Inject
	private static DDMStructureLocalService _ddmStructureLocalService;

	@Inject
	private static DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

}
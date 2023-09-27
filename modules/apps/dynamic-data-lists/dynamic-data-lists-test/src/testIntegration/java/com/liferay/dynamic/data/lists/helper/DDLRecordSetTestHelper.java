/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.helper;

import com.liferay.dynamic.data.lists.constants.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Marcellus Tavares
 * @author André de Oliveira
 */
public class DDLRecordSetTestHelper {

	public DDLRecordSetTestHelper(Group group) throws Exception {
		_userId = TestPropsValues.getUserId();

		_group = group;
	}

	public DDLRecordSetTestHelper(Group group, long userId) throws Exception {
		_group = group;
		_userId = userId;
	}

	public DDLRecordSet addRecordSet(DDMForm ddmForm) throws Exception {
		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(DDLRecordSet.class), _group);

		DDMStructure ddmStructure = ddmStructureTestHelper.addStructure(
			ddmForm, StorageType.DEFAULT.toString());

		return addRecordSet(ddmStructure);
	}

	public DDLRecordSet addRecordSet(DDMStructure ddmStructure)
		throws Exception {

		return addRecordSet(
			ddmStructure, DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS);
	}

	public DDLRecordSet addRecordSet(DDMStructure ddmStructure, int scope)
		throws Exception {

		return DDLRecordSetLocalServiceUtil.addRecordSet(
			_userId, _group.getGroupId(), ddmStructure.getStructureId(), null,
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			null, DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT, scope,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	public DDLRecordSet updateRecordSet(
			long recordSetId, DDMFormValues settingsDDMFormValues)
		throws PortalException {

		return DDLRecordSetLocalServiceUtil.updateRecordSet(
			recordSetId, settingsDDMFormValues);
	}

	public DDLRecordSet updateRecordSet(
			long recordSetId, DDMStructure ddmStructure)
		throws Exception {

		return DDLRecordSetLocalServiceUtil.updateRecordSet(
			recordSetId, ddmStructure.getStructureId(),
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			null, DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	private final Group _group;
	private final long _userId;

}
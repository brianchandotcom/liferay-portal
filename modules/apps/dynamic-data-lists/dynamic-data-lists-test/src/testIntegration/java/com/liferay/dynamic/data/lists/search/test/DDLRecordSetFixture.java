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

package com.liferay.dynamic.data.lists.search.test;

import com.liferay.dynamic.data.lists.helper.DDLRecordSetTestHelper;
import com.liferay.dynamic.data.lists.helper.DDLRecordTestHelper;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Luan Maoski
 * @author Lucas Marques
 */
public class DDLRecordSetFixture {

	public DDLRecordSetFixture(Group group, User user) throws Exception {
		_group = group;
		_user = user;

		setUpDdlRecordTestHelper();
	}

	public DDLRecordSet createAnDDLRecordSet() throws Exception {
		DDLRecordSet ddlRecordSet = addRecordSet();

		_ddlRecordSets.add(ddlRecordSet);

		return ddlRecordSet;
	}

	public DDLRecordSet getDdlRecordSet() {
		return _ddlRecordSet;
	}

	public List<DDLRecordSet> getDdlRecordSets() {
		return _ddlRecordSets;
	}

	protected DDLRecordSet addRecordSet() throws Exception {
		DDLRecordSetTestHelper recordSetTestHelper = new DDLRecordSetTestHelper(
			_group, _user);

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(DDLRecordSet.class), _group);

		DDMStructure ddmStructure = ddmStructureTestHelper.addStructure(
			createDDMForm(LocaleUtil.US), StorageType.JSON.toString());

		return recordSetTestHelper.addRecordSet(ddmStructure);
	}

	protected DDMForm createDDMForm(Locale... locales) {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			DDMFormTestUtil.createAvailableLocales(locales), locales[0]);

		DDMFormField nameDDMFormField = DDMFormTestUtil.createTextDDMFormField(
			RandomTestUtil.randomString(), true, false, false);

		nameDDMFormField.setIndexType("keyword");

		ddmForm.addDDMFormField(nameDDMFormField);

		DDMFormField descriptionDDMFormField =
			DDMFormTestUtil.createTextDDMFormField(
				RandomTestUtil.randomString(), true, false, false);

		descriptionDDMFormField.setIndexType("text");

		ddmForm.addDDMFormField(descriptionDDMFormField);

		return ddmForm;
	}

	protected void setUpDdlRecordTestHelper() throws Exception {
		_ddlRecordSet = createAnDDLRecordSet();

		_recordTestHelper = new DDLRecordTestHelper(
			_group, _user, _ddlRecordSet);
	}

	private DDLRecordSet _ddlRecordSet;
	private final List<DDLRecordSet> _ddlRecordSets = new ArrayList<>();
	private final Group _group;
	private DDLRecordTestHelper _recordTestHelper;
	private final User _user;

}
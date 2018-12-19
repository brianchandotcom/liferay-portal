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

package com.liferay.dynamic.data.mapping.search.test;

import com.liferay.dynamic.data.mapping.helper.DDMFormInstanceRecordTestHelper;
import com.liferay.dynamic.data.mapping.helper.DDMFormInstanceTestHelper;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Luan Maoski
 * @author Lucas Marques
 */
public class DDMFormInstanceRecordFixture {

	public DDMFormInstanceRecordFixture(Group group, User user)
		throws Exception {

		_group = group;
		_user = user;

		_setUpDDMFormInstanceRecordTestHelper();
	}

	public DDMFormInstanceRecord createDDMFormInstanceRecord()
		throws Exception {

		DDMFormInstanceRecord ddmFormInstanceRecord = addDDMFormInstanceRecord(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		_ddmFormInstanceRecords.add(ddmFormInstanceRecord);

		return ddmFormInstanceRecord;
	}

	public List<DDMFormInstanceRecord> getDdmFormInstanceRecords() {
		return _ddmFormInstanceRecords;
	}

	public DDMStructure getDdmStructure() {
		return _ddmStructure;
	}

	public ServiceContext getServiceContext() throws Exception {
		return ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), _user.getUserId());
	}

	protected DDMFormInstanceRecord addDDMFormInstanceRecord(
			String name, String description)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.US, name);

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(LocaleUtil.US, description);

		Set<Locale> localesSet = nameMap.keySet();

		Locale[] locales = new Locale[nameMap.size()];

		localesSet.toArray(locales);

		DDMFormValues ddmFormValues = createDDMFormValues(locales);

		DDMFormFieldValue nameDDMFormFieldValue =
			createLocalizedDDMFormFieldValue("name", nameMap);

		ddmFormValues.addDDMFormFieldValue(nameDDMFormFieldValue);

		DDMFormFieldValue descriptionDDMFormFieldValue =
			createLocalizedDDMFormFieldValue("description", descriptionMap);

		ddmFormValues.addDDMFormFieldValue(descriptionDDMFormFieldValue);

		return _ddmFormInstanceRecordTestHelper.addDDMFormInstanceRecord(
			ddmFormValues);
	}

	protected DDMFormValues createDDMFormValues(Locale... locales)
		throws Exception {

		DDMFormInstance ddmFormInstance =
			_ddmFormInstanceRecordTestHelper.getDDMFormInstance();

		DDMStructure ddmStructure = ddmFormInstance.getStructure();

		return DDMFormValuesTestUtil.createDDMFormValues(
			ddmStructure.getDDMForm(),
			DDMFormValuesTestUtil.createAvailableLocales(locales), locales[0]);
	}

	protected DDMFormFieldValue createLocalizedDDMFormFieldValue(
		String name, Map<Locale, String> values) {

		Value localizedValue = new LocalizedValue(LocaleUtil.US);

		for (Map.Entry<Locale, String> value : values.entrySet()) {
			localizedValue.addString(value.getKey(), value.getValue());
		}

		return DDMFormValuesTestUtil.createDDMFormFieldValue(
			name, localizedValue);
	}

	private DDMFormInstance _addDDMFormInstance(DDMStructure ddmStructure)
		throws Exception {

		DDMFormInstanceTestHelper ddmFormInstanceTestHelper =
			new DDMFormInstanceTestHelper(_group);

		return ddmFormInstanceTestHelper.addDDMFormInstance(ddmStructure);
	}

	private void _setUpDDMFormInstanceRecordTestHelper() throws Exception {
		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(DDMFormInstance.class), _group);

		_ddmStructure = ddmStructureTestHelper.addStructure(
			DDMFormFixture.createDDMForm(LocaleUtil.US),
			StorageType.JSON.toString());

		DDMFormInstance ddmFormInstance = _addDDMFormInstance(_ddmStructure);

		_ddmFormInstanceRecordTestHelper = new DDMFormInstanceRecordTestHelper(
			_group, _user, ddmFormInstance);
	}

	private final List<DDMFormInstanceRecord> _ddmFormInstanceRecords =
		new ArrayList<>();
	private DDMFormInstanceRecordTestHelper _ddmFormInstanceRecordTestHelper;
	private DDMStructure _ddmStructure;
	private final Group _group;
	private final User _user;

}
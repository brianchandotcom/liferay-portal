/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseTableReferenceDefinitionTestCase;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMField;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMFieldLocalService;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Gislayne Vitorino
 */
@RunWith(Arquillian.class)
public class DDMFieldTableReferenceDefinitionTest
	extends BaseTableReferenceDefinitionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), DLFileEntryMetadata.class.getName());

		_ddmStructureVersion = ddmStructure.getStructureVersion();
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			DDMFormFieldTypeConstants.TEXT, "string", false, false, false);

		DDMField ddmField = _ddmFieldLocalService.createDDMField(
			_counterLocalService.increment());

		ddmField.setParentFieldId(0);
		ddmField.setStorageId(0);
		ddmField.setStructureVersionId(
			_ddmStructureVersion.getStructureVersionId());
		ddmField.setFieldName(ddmFormField.getName());
		ddmField.setFieldType(ddmFormField.getType());
		ddmField.setInstanceId(RandomTestUtil.randomString(8));
		ddmField.setLocalizable(ddmFormField.isLocalizable());
		ddmField.setPriority(RandomTestUtil.randomInt());

		return _ddmFieldLocalService.addDDMField(ddmField);
	}

	@Inject
	private CounterLocalService _counterLocalService;

	@Inject
	private DDMFieldLocalService _ddmFieldLocalService;

	private DDMStructureVersion _ddmStructureVersion;

}
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v6_1_11.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.document.library.test.util.DLAppTestUtil;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFieldAttribute;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMFieldLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Ben Demetrius
 */
@RunWith(Arquillian.class)
public class DocumentLibraryDDMFieldAttributeCTUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_fileEntry = DLAppTestUtil.addFileEntry(_group.getGroupId());

		_ctCollection = _ctCollectionService.addCTCollection(
			null, 0, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		_productionStorageId = RandomTestUtil.randomLong();
		_publicationStorageId = RandomTestUtil.randomLong();
	}

	@After
	public void tearDown() throws Exception {
		_ddmFieldLocalService.deleteDDMFormValues(_productionStorageId);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			_ddmFieldLocalService.deleteDDMFormValues(_publicationStorageId);
		}
	}

	@Test
	@TestInfo("LPD-104003")
	public void testUpgradeSeparatesProductionAndPublication()
		throws Exception {

		DDMStructure ddmStructure = _addDDMStructure();

		_addDDMFormValues(
			ddmStructure, CTCollectionThreadLocal.CT_COLLECTION_ID_PRODUCTION,
			_productionStorageId, _getImageGalleryURL(_fileEntry.getUuid()));
		_addDDMFormValues(
			ddmStructure, _ctCollection.getCtCollectionId(),
			_publicationStorageId, _getImageGalleryURL(_fileEntry.getUuid()));

		_runUpgrade();

		_assertDocumentLibraryValue(
			ddmStructure, _productionStorageId,
			_ddmFieldLocalService.getDDMFieldAttributes(
				_productionStorageId, "uuid"));

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			_assertDocumentLibraryValue(
				ddmStructure, _publicationStorageId,
				_ddmFieldLocalService.getDDMFieldAttributes(
					_publicationStorageId, "uuid"));
		}
	}

	private void _addDDMFormValues(
			DDMStructure ddmStructure, long ctCollectionId, long storageId,
			String value)
		throws Exception {

		DDMForm ddmForm = ddmStructure.getDDMForm();

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm, ddmForm.getAvailableLocales(), LocaleUtil.US);

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.US, value);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				_FIELD_NAME, localizedValue));

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollectionId)) {

			_ddmFieldLocalService.updateDDMFormValues(
				ddmStructure.getStructureId(), storageId, ddmFormValues);
		}
	}

	private DDMStructure _addDDMStructure() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			DDMFormTestUtil.createAvailableLocales(LocaleUtil.US),
			LocaleUtil.US);

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				_FIELD_NAME, _FIELD_NAME,
				DDMFormFieldTypeConstants.DOCUMENT_LIBRARY,
				DDMFormFieldTypeConstants.DOCUMENT_LIBRARY, true, false, false,
				LocaleUtil.US));

		return DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName(), ddmForm);
	}

	private void _assertDocumentLibraryValue(
			DDMStructure ddmStructure, long storageId,
			List<DDMFieldAttribute> ddmFieldAttributes)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_getValueString(ddmStructure, storageId));

		Assert.assertEquals(
			_fileEntry.getGroupId(), jsonObject.getLong("groupId"));
		Assert.assertEquals(
			_fileEntry.getTitle(), jsonObject.getString("title"));
		Assert.assertEquals("document", jsonObject.getString("type"));
		Assert.assertEquals(_fileEntry.getUuid(), jsonObject.getString("uuid"));

		Assert.assertEquals(
			ddmFieldAttributes.toString(), 1, ddmFieldAttributes.size());
	}

	private String _getImageGalleryURL(String uuid) {
		return StringBundler.concat(
			"/image/image_gallery?uuid=", uuid, "&groupId=",
			_fileEntry.getGroupId(), "&t=", RandomTestUtil.randomLong());
	}

	private String _getValueString(DDMStructure ddmStructure, long storageId) {
		DDMFormValues ddmFormValues = _ddmFieldLocalService.getDDMFormValues(
			ddmStructure.getDDMForm(), storageId);

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap(false);

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			_FIELD_NAME);

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		LocalizedValue localizedValue =
			(LocalizedValue)ddmFormFieldValue.getValue();

		return localizedValue.getString(LocaleUtil.US);
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.journal.internal.upgrade.v6_1_11." +
				"DocumentLibraryDDMFieldAttributeUpgradeProcess");

		upgradeProcess.upgrade();

		_entityCache.clearCache();

		_multiVMPool.clear();
	}

	private static final String _FIELD_NAME = "DocumentLibrary";

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	@Inject
	private CTCollectionService _ctCollectionService;

	@Inject
	private DDMFieldLocalService _ddmFieldLocalService;

	@Inject
	private EntityCache _entityCache;

	private FileEntry _fileEntry;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MultiVMPool _multiVMPool;

	private long _productionStorageId;
	private long _publicationStorageId;

	@Inject(
		filter = "(&(component.name=com.liferay.journal.internal.upgrade.registry.JournalServiceUpgradeStepRegistrator))"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}
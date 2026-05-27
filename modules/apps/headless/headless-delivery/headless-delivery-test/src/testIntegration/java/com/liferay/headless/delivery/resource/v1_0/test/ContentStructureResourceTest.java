/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.headless.delivery.client.dto.v1_0.ContentStructure;
import com.liferay.headless.delivery.client.dto.v1_0.ContentStructureField;
import com.liferay.headless.delivery.client.dto.v1_0.Option;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.io.InputStream;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class ContentStructureResourceTest
	extends BaseContentStructureResourceTestCase {

	@Override
	@Test
	public void testGetContentStructure() throws Exception {
		super.testGetContentStructure();

		_testGetContentStructureWithOptionReference();
	}

	@Test
	public void testGetSiteContentStructuresPageSearch() throws Exception {
		String name = RandomTestUtil.randomString();

		DDMStructure ddmStructure = _addDDMStructure(
			_portal.getClassNameId(JournalArticle.class), testGroup, name);

		Page<ContentStructure> page =
			contentStructureResource.getSiteContentStructuresPage(
				testGroup.getGroupId(), ddmStructure.getName("en_US"), null,
				null, null, null);

		Assert.assertEquals(1, page.getTotalCount());

		_addDDMStructure(
			_portal.getClassNameId(DDMFormInstance.class), testGroup, name);

		page = contentStructureResource.getSiteContentStructuresPage(
			testGroup.getGroupId(), name, null, null, null, null);

		Assert.assertEquals(1, page.getTotalCount());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	@Override
	protected ContentStructure
			testGetAssetLibraryContentStructurePermissionsPage_addContentStructure()
		throws Exception {

		return testGetContentStructure_addContentStructure();
	}

	@Override
	protected ContentStructure
			testGetAssetLibraryContentStructuresPage_addContentStructure(
				Long assetLibraryId, ContentStructure contentStructure)
		throws Exception {

		DepotEntry depotEntry = DepotEntryLocalServiceUtil.getDepotEntry(
			assetLibraryId);

		return _toContentStructure(
			_addDDMStructure(
				_portal.getClassNameId(JournalArticle.class),
				depotEntry.getGroup(), contentStructure.getName()));
	}

	@Override
	protected ContentStructure testGetContentStructure_addContentStructure()
		throws Exception {

		return _toContentStructure(
			_addDDMStructure(
				_portal.getClassNameId(JournalArticle.class), testGroup,
				RandomTestUtil.randomString()));
	}

	@Override
	protected ContentStructure
			testGetContentStructurePermissionsPage_addContentStructure()
		throws Exception {

		return testGetContentStructure_addContentStructure();
	}

	@Override
	protected ContentStructure
			testGetSiteContentStructurePermissionsPage_addContentStructure()
		throws Exception {

		return testGetContentStructure_addContentStructure();
	}

	@Override
	protected ContentStructure
			testGetSiteContentStructuresPage_addContentStructure(
				Long siteId, ContentStructure contentStructure)
		throws Exception {

		return _toContentStructure(
			_addDDMStructure(
				_portal.getClassNameId(JournalArticle.class),
				GroupLocalServiceUtil.getGroup(siteId),
				contentStructure.getName()));
	}

	@Override
	protected ContentStructure testGraphQLContentStructure_addContentStructure()
		throws Exception {

		return testGetContentStructure_addContentStructure();
	}

	@Override
	protected ContentStructure
			testPutAssetLibraryContentStructurePermissionsPage_addContentStructure()
		throws Exception {

		return testGetContentStructure_addContentStructure();
	}

	@Override
	protected ContentStructure
			testPutContentStructurePermissionsPage_addContentStructure()
		throws Exception {

		return testGetContentStructure_addContentStructure();
	}

	@Override
	protected ContentStructure
			testPutSiteContentStructurePermissionsPage_addContentStructure()
		throws Exception {

		return testGetContentStructure_addContentStructure();
	}

	private DDMStructure _addDDMStructure(
			long classNameId, Group group, String name)
		throws Exception {

		return _addDDMStructure(
			classNameId, "test-ddm-structure.json", group, name);
	}

	private DDMStructure _addDDMStructure(
			long classNameId, String fileName, Group group, String name)
		throws Exception {

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(classNameId, group);

		return ddmStructureTestHelper.addStructure(
			classNameId, RandomTestUtil.randomString(), name,
			RandomTestUtil.randomString(), _deserialize(_read(fileName)),
			StorageType.DEFAULT.getValue(), DDMStructureConstants.TYPE_DEFAULT);
	}

	private DDMForm _deserialize(String content) {
		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(content);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_jsonDDMFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	private Option[] _getOptions(ContentStructure contentStructure) {
		ContentStructureField[] contentStructureFields =
			contentStructure.getContentStructureFields();

		for (ContentStructureField contentStructureField :
				contentStructureFields) {

			Option[] options = contentStructureField.getOptions();

			if (ArrayUtil.isNotEmpty(options)) {
				return options;
			}
		}

		return new Option[0];
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private void _testGetContentStructureWithOptionReference()
		throws Exception {

		DDMStructure ddmStructure = _addDDMStructure(
			_portal.getClassNameId(JournalArticle.class),
			"test-ddm-structure-radio.json", testGroup,
			RandomTestUtil.randomString());

		Option[] options = _getOptions(
			contentStructureResource.getContentStructure(
				ddmStructure.getStructureId()));

		Assert.assertEquals(Arrays.toString(options), 3, options.length);
		Assert.assertEquals("OptionReference1", options[0].getValue());
		Assert.assertEquals("OptionReference2", options[1].getValue());
		Assert.assertEquals("OptionReference3", options[2].getValue());
	}

	private ContentStructure _toContentStructure(DDMStructure structure) {
		return new ContentStructure() {
			{
				dateCreated = structure.getCreateDate();
				dateModified = structure.getModifiedDate();
				id = structure.getStructureId();
				name = structure.getName(LocaleUtil.getDefault());
				siteId = structure.getGroupId();
			}
		};
	}

	@Inject(filter = "ddm.form.deserializer.type=json")
	private DDMFormDeserializer _jsonDDMFormDeserializer;

	@Inject
	private Portal _portal;

}
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

package com.liferay.info.request.struts.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.exception.InfoFormException;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upload.UploadPortletRequestImpl;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.PropsValues;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.io.Serializable;

import java.text.DecimalFormat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartHttpServletRequest;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class AddInfoItemStrutsActionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.getAdminUser(_group.getCompanyId());

		UserTestUtil.setUser(_user);

		_objectDefinition = _addObjectDefinition();

		_classNameId = String.valueOf(
			_portal.getClassNameId(
				"com.liferay.object.model.ObjectDefinition#" +
					_objectDefinition.getObjectDefinitionId()));

		_layout = _addLayout();
	}

	@Test
	public void testAddInfoItemInvalidBigDecimalTooBig() throws Exception {
		_testAddInfoItemBigDecimal("100000000000000", null, true);
	}

	@Test
	public void testAddInfoItemInvalidBigDecimalTooSmall() throws Exception {
		_testAddInfoItemBigDecimal("-100000000000000", null, true);
	}

	@Test
	public void testAddInfoItemInvalidIntegerTooBig() throws Exception {
		_testAddInfoItemInteger("2147483648", true);
	}

	@Test
	public void testAddInfoItemInvalidIntegerTooSmall() throws Exception {
		_testAddInfoItemInteger("-2147483649", true);
	}

	@Test
	public void testAddInfoItemInvalidLongTooBig() throws Exception {
		_testAddInfoItemLong("9007199254740992", true);
	}

	@Test
	public void testAddInfoItemInvalidLongTooSmall() throws Exception {
		_testAddInfoItemLong("-9007199254740992", true);
	}

	@Test
	public void testAddInfoItemMaxValues() throws Exception {
		_testAddInfoItem(
			"99999999999999.9999999999999999", "9999999999", "999999999",
			"9007199254740991", RandomTestUtil.randomString());
	}

	@Test
	public void testAddInfoItemMinValues() throws Exception {
		_testAddInfoItem(
			"-99999999999999.9999999999999999", "-9999999999", "-999999999",
			"-9007199254740991", RandomTestUtil.randomString());
	}

	@Test
	public void testAddInfoItemRoundedBigDecimalTooLong() throws Exception {
		_testAddInfoItem(
			"99999999999999.99999999999999991",
			"99999999999999.9999999999999999", null, null, null, null, null,
			null, null, false);
	}

	@Test
	public void testAddInfoItemRoundedDoubleTooLong() throws Exception {
		_testAddInfoItemDouble(
			"999.99999999999991", "999.9999999999999", false);
	}

	private Layout _addLayout() throws Exception {
		Layout layout = _layoutLocalService.addLayout(
			_user.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, 0, 0,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), Collections.emptyMap(),
			Collections.emptyMap(), Collections.emptyMap(),
			LayoutConstants.TYPE_CONTENT,
			UnicodePropertiesBuilder.put(
				"published", "true"
			).buildString(),
			false, false, Collections.emptyMap(), 0,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId()));

		_defaultSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid());

		LayoutStructure layoutStructure = new LayoutStructure();

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.addRootLayoutStructureItem();

		LayoutStructureItem formStyledLayoutStructureItem =
			layoutStructure.addFormStyledLayoutStructureItem(
				rootLayoutStructureItem.getItemId(), 0);

		_formItemId = formStyledLayoutStructureItem.getItemId();

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				_group.getGroupId(), layout.getPlid(),
				_defaultSegmentsExperienceId, layoutStructure.toString());

		return layout;
	}

	private ObjectDefinition _addObjectDefinition() throws Exception {
		List<ObjectField> objectFields = Arrays.asList(
			ObjectFieldUtil.createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_DECIMAL,
				ObjectFieldConstants.DB_TYPE_DOUBLE,
				RandomTestUtil.randomString(), "myDecimal", false),
			ObjectFieldUtil.createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_INTEGER,
				ObjectFieldConstants.DB_TYPE_INTEGER,
				RandomTestUtil.randomString(), "myInteger", false),
			ObjectFieldUtil.createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER,
				ObjectFieldConstants.DB_TYPE_LONG,
				RandomTestUtil.randomString(), "myLongInteger", false),
			ObjectFieldUtil.createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_PRECISION_DECIMAL,
				ObjectFieldConstants.DB_TYPE_BIG_DECIMAL,
				RandomTestUtil.randomString(), "myPrecisionDecimal", false));

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				_user.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT, objectFields);

		ObjectField objectField = _objectFieldLocalService.addCustomObjectField(
			_user.getUserId(), 0, objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, null, true, true, null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			"myText", false, false, Collections.emptyList());

		objectDefinition.setTitleObjectFieldId(objectField.getObjectFieldId());

		objectDefinition = _objectDefinitionLocalService.updateObjectDefinition(
			objectDefinition);

		return _objectDefinitionLocalService.publishCustomObjectDefinition(
			_user.getUserId(), objectDefinition.getObjectDefinitionId());
	}

	private void _processEvents(
			UploadPortletRequest uploadPortletRequest,
			MockHttpServletResponse mockHttpServletResponse, User user)
		throws Exception {

		uploadPortletRequest.setAttribute(
			WebKeys.CURRENT_URL, "/portal/add_info_item");

		uploadPortletRequest.setAttribute(WebKeys.USER, user);

		EventsProcessorUtil.process(
			PropsKeys.SERVLET_SERVICE_EVENTS_PRE,
			PropsValues.SERVLET_SERVICE_EVENTS_PRE, uploadPortletRequest,
			mockHttpServletResponse);
	}

	private void _testAddInfoItem(
			String bigDecimalValue, String doubleValue, String integerValue,
			String longValue, String stringValue)
		throws Exception {

		_testAddInfoItem(
			bigDecimalValue, bigDecimalValue, doubleValue, doubleValue,
			integerValue, integerValue, longValue, longValue, stringValue,
			false);
	}

	private void _testAddInfoItem(
			String bigDecimalValueInput, String bigDecimalValueExpected,
			String doubleValueInput, String doubleValueExpected,
			String integerValueInput, String integerValueExpected,
			String longValueInput, String longValueExpected, String stringValue,
			boolean errorExpected)
		throws Exception {

		MockMultipartHttpServletRequest mockMultipartHttpServletRequest =
			new MockMultipartHttpServletRequest();

		mockMultipartHttpServletRequest.addHeader(
			HttpHeaders.REFERER, "https://example.com/error");

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					mockMultipartHttpServletRequest, null,
					HashMapBuilder.put(
						"classNameId", Collections.singletonList(_classNameId)
					).put(
						"classTypeId", Collections.singletonList("0")
					).put(
						"formItemId", Collections.singletonList(_formItemId)
					).put(
						"groupId",
						Collections.singletonList(
							String.valueOf(_group.getGroupId()))
					).put(
						"myDecimal",
						() -> {
							if (doubleValueInput == null) {
								return null;
							}

							return Collections.singletonList(doubleValueInput);
						}
					).put(
						"myInteger",
						() -> {
							if (integerValueInput == null) {
								return null;
							}

							return Collections.singletonList(integerValueInput);
						}
					).put(
						"myLongInteger",
						() -> {
							if (longValueInput == null) {
								return null;
							}

							return Collections.singletonList(longValueInput);
						}
					).put(
						"myPrecisionDecimal",
						() -> {
							if (bigDecimalValueInput == null) {
								return null;
							}

							return Collections.singletonList(
								bigDecimalValueInput);
						}
					).put(
						"myText", Collections.singletonList(stringValue)
					).put(
						"p_l_mode", Collections.singletonList(Constants.VIEW)
					).put(
						"plid",
						Collections.singletonList(
							String.valueOf(_layout.getPlid()))
					).put(
						"redirect",
						Collections.singletonList("https://example.com/")
					).put(
						"segmentsExperienceId",
						Collections.singletonList(
							String.valueOf(_defaultSegmentsExperienceId))
					).build()),
				null, RandomTestUtil.randomString());

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			mockHttpServletResponse, unsyncStringWriter);

		_processEvents(uploadPortletRequest, mockHttpServletResponse, _user);

		_addInfoItemStrutsAction.execute(
			uploadPortletRequest, pipingServletResponse);

		List<ObjectEntry> objectEntries =
			_objectEntryLocalService.getObjectEntries(
				0, _objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Object object = SessionErrors.get(uploadPortletRequest, _formItemId);

		if (errorExpected) {
			Assert.assertNotNull(object);

			Assert.assertTrue(object instanceof InfoFormException);

			Assert.assertEquals(
				objectEntries.toString(), 0, objectEntries.size());

			return;
		}

		Assert.assertNull(object);

		Assert.assertEquals(objectEntries.toString(), 1, objectEntries.size());

		ObjectEntry objectEntry = objectEntries.get(0);

		Map<String, Serializable> values = objectEntry.getValues();

		if (doubleValueInput != null) {
			DecimalFormat decimalFormat = new DecimalFormat("0");

			decimalFormat.setMaximumFractionDigits(16);

			Assert.assertEquals(
				doubleValueExpected,
				decimalFormat.format(values.get("myDecimal")));
		}

		if (integerValueInput != null) {
			Assert.assertEquals(
				integerValueExpected, String.valueOf(values.get("myInteger")));
		}

		if (longValueInput != null) {
			Assert.assertEquals(
				longValueExpected, String.valueOf(values.get("myLongInteger")));
		}

		if (bigDecimalValueInput != null) {
			Assert.assertEquals(
				bigDecimalValueExpected,
				String.valueOf(values.get("myPrecisionDecimal")));
		}

		if (stringValue != null) {
			Assert.assertEquals(stringValue, values.get("myText"));
		}
	}

	private void _testAddInfoItemBigDecimal(
			String bigDecimalValueInput, String bigDecimalValueExpected,
			boolean errorExpected)
		throws Exception {

		_testAddInfoItem(
			bigDecimalValueInput, bigDecimalValueExpected, null, null, null,
			null, null, null, null, errorExpected);
	}

	private void _testAddInfoItemDouble(
			String doubleValueInput, String doubleValueExpected,
			boolean errorExpected)
		throws Exception {

		_testAddInfoItem(
			null, null, doubleValueInput, doubleValueExpected, null, null, null,
			null, null, errorExpected);
	}

	private void _testAddInfoItemInteger(
			String integerValueInput, boolean errorExpected)
		throws Exception {

		_testAddInfoItem(
			null, null, null, null, integerValueInput, null, null, null, null,
			errorExpected);
	}

	private void _testAddInfoItemLong(
			String longValueInput, boolean errorExpected)
		throws Exception {

		_testAddInfoItem(
			null, null, null, null, null, null, longValueInput, null, null,
			errorExpected);
	}

	@Inject(filter = "component.name=*.AddInfoItemStrutsAction")
	private StrutsAction _addInfoItemStrutsAction;

	private String _classNameId;
	private long _defaultSegmentsExperienceId;
	private String _formItemId;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private User _user;

}
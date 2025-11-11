/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.data.handler;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.staging.StagingGroupHelper;

import java.io.Serializable;

import java.text.DateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Petteri Karttunen
 */
public class BatchEnginePortletDataHandlerUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		_fastDateFormatFactoryUtilMockedStatic = Mockito.mockStatic(
			FastDateFormatFactoryUtil.class);

		Mockito.when(
			FastDateFormatFactoryUtil.getSimpleDateFormat(Mockito.anyString())
		).thenReturn(
			_dateFormat
		);
	}

	@After
	public void tearDown() {
		_fastDateFormatFactoryUtilMockedStatic.close();
	}

	@Test
	public void testBuildExportParametersWithEndDate() {
		Date endDate = _getDate(0);

		Map<String, Serializable> parameters =
			BatchEnginePortletDataHandlerUtil.buildExportParameters(
				_mockExportImportDescriptor(), null,
				_mockPortletDataContext(endDate, null, null),
				_getStagingGroupHelper(false));

		Assert.assertEquals(
			"dateModified le " + _dateFormat.format(endDate),
			parameters.get("filter"));
	}

	@Test
	public void testBuildExportParametersWithEndDateAndStartDate() {
		Date endDate = _getDate(0);
		Date startDate = _getDate(-1);

		Map<String, Serializable> parameters =
			BatchEnginePortletDataHandlerUtil.buildExportParameters(
				_mockExportImportDescriptor(), null,
				_mockPortletDataContext(endDate, null, startDate),
				_getStagingGroupHelper(false));

		Assert.assertEquals(
			StringBundler.concat(
				"dateModified le ", _dateFormat.format(endDate),
				" and dateModified ge ", _dateFormat.format(startDate)),
			parameters.get("filter"));
	}

	@Test
	public void testBuildExportParametersWithModelClassName() {
		String modelClassName = RandomTestUtil.randomString();

		Map<String, Serializable> parameters =
			BatchEnginePortletDataHandlerUtil.buildExportParameters(
				_mockExportImportDescriptor(modelClassName, null, null, null),
				null, _mockPortletDataContext(), _getStagingGroupHelper(false));

		Assert.assertEquals(modelClassName, parameters.get("modelClassName"));
	}

	@Test
	public void testBuildExportParametersWithModelName() {
		String modelName = RandomTestUtil.randomString();

		Map<String, Serializable> parameters =
			BatchEnginePortletDataHandlerUtil.buildExportParameters(
				_mockExportImportDescriptor(null, modelName, null, null), null,
				_mockPortletDataContext(), _getStagingGroupHelper(false));

		Assert.assertEquals(modelName, parameters.get("modelName"));
	}

	@Test
	public void testBuildExportParametersWithNestedFields() {
		Map<String, Serializable> parameters =
			BatchEnginePortletDataHandlerUtil.buildExportParameters(
				_mockExportImportDescriptor(
					null, null, List.of("nestedField1", "nestedField2"), null),
				null, _mockPortletDataContext(), _getStagingGroupHelper(false));

		Assert.assertEquals(
			"customFields.attributeType,nestedField1,nestedField2",
			parameters.get("batchNestedFields"));

		parameters = BatchEnginePortletDataHandlerUtil.buildExportParameters(
			_mockExportImportDescriptor(
				null, null, List.of("nestedField1", "nestedField2"), null),
			null,
			_mockPortletDataContext(
				null,
				HashMapBuilder.put(
					PortletDataHandlerKeys.PERMISSIONS, new String[] {"true"}
				).build(),
				null),
			_getStagingGroupHelper(false));

		Assert.assertEquals(
			"customFields.attributeType,permissions,nestedField1,nestedField2",
			parameters.get("batchNestedFields"));
	}

	@Test
	public void testBuildExportParametersWithNoDates() {
		Map<String, Serializable> parameters =
			BatchEnginePortletDataHandlerUtil.buildExportParameters(
				_mockExportImportDescriptor(), null, _mockPortletDataContext(),
				_getStagingGroupHelper(false));

		Assert.assertNull(parameters.get("filter"));
	}

	@Test
	public void testBuildExportParametersWithParameters() {
		Map<String, Serializable> parameters =
			BatchEnginePortletDataHandlerUtil.buildExportParameters(
				_mockExportImportDescriptor(
					null, null, null,
					HashMapBuilder.<String, Serializable>put(
						"param1", "value1"
					).put(
						"param2", "value2"
					).build()),
				null, _mockPortletDataContext(), _getStagingGroupHelper(false));

		Assert.assertEquals("value1", parameters.get("param1"));
		Assert.assertEquals("value2", parameters.get("param2"));
	}

	@Test
	public void testBuildExportParametersWithStartDate() {
		Date startDate = _getDate(-1);

		Map<String, Serializable> parameters =
			BatchEnginePortletDataHandlerUtil.buildExportParameters(
				_mockExportImportDescriptor(), null,
				_mockPortletDataContext(null, null, startDate),
				_getStagingGroupHelper(false));

		Assert.assertEquals(
			"dateModified ge " + _dateFormat.format(startDate),
			parameters.get("filter"));
	}

	@Test
	public void testBuildImportParametersWithModelClassName() {
		String modelClassName = RandomTestUtil.randomString();

		Map<String, Serializable> parameters =
			BatchEnginePortletDataHandlerUtil.buildImportParameters(
				_mockExportImportDescriptor(modelClassName, null, null, null),
				null, _mockPortletDataContext(), _getStagingGroupHelper(false));

		Assert.assertEquals(modelClassName, parameters.get("modelClassName"));
	}

	@Test
	public void testBuildImportParametersWithModelName() {
		String modelName = RandomTestUtil.randomString();

		Map<String, Serializable> parameters =
			BatchEnginePortletDataHandlerUtil.buildImportParameters(
				_mockExportImportDescriptor(null, modelName, null, null), null,
				_mockPortletDataContext(), _getStagingGroupHelper(false));

		Assert.assertEquals(modelName, parameters.get("modelName"));
	}

	@Test
	public void testBuildParametersWithExportImportGroup() {
		Group group = _mockGroup(
			RandomTestUtil.randomString(), RandomTestUtil.randomLong());

		Map<String, Serializable> parameters =
			BatchEnginePortletDataHandlerUtil.buildExportParameters(
				_mockExportImportDescriptor(), group, _mockPortletDataContext(),
				_getStagingGroupHelper(true));

		Assert.assertNull(parameters.get("siteExternalReferenceCode"));
		Assert.assertNull(parameters.get("siteId"));

		parameters = BatchEnginePortletDataHandlerUtil.buildImportParameters(
			_mockExportImportDescriptor(), group, _mockPortletDataContext(),
			_getStagingGroupHelper(true));

		Assert.assertNull(parameters.get("siteExternalReferenceCode"));
		Assert.assertNull(parameters.get("siteId"));
	}

	@Test
	public void testBuildParametersWithGroup() {
		String siteExternalReferenceCode = RandomTestUtil.randomString();

		long siteId = RandomTestUtil.randomLong();

		Group group = _mockGroup(siteExternalReferenceCode, siteId);

		Map<String, Serializable> parameters =
			BatchEnginePortletDataHandlerUtil.buildExportParameters(
				_mockExportImportDescriptor(), group, _mockPortletDataContext(),
				_getStagingGroupHelper(false));

		Assert.assertEquals(
			parameters.get("siteExternalReferenceCode"),
			siteExternalReferenceCode);
		Assert.assertEquals(parameters.get("siteId"), siteId);

		parameters = BatchEnginePortletDataHandlerUtil.buildImportParameters(
			_mockExportImportDescriptor(), group, _mockPortletDataContext(),
			_getStagingGroupHelper(false));

		Assert.assertEquals(
			parameters.get("siteExternalReferenceCode"),
			siteExternalReferenceCode);
		Assert.assertEquals(parameters.get("siteId"), siteId);
	}

	private Date _getDate(int days) {
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DATE, days);

		return calendar.getTime();
	}

	private StagingGroupHelper _getStagingGroupHelper(boolean companyGroup) {
		StagingGroupHelper stagingGroupHelper = Mockito.mock(
			StagingGroupHelper.class);

		Mockito.when(
			stagingGroupHelper.isCompanyGroup(Mockito.any(Group.class))
		).thenReturn(
			companyGroup
		);

		return stagingGroupHelper;
	}

	private ExportImportVulcanBatchEngineTaskItemDelegate.ExportImportDescriptor
		_mockExportImportDescriptor() {

		return Mockito.mock(
			ExportImportVulcanBatchEngineTaskItemDelegate.
				ExportImportDescriptor.class);
	}

	private ExportImportVulcanBatchEngineTaskItemDelegate.ExportImportDescriptor
		_mockExportImportDescriptor(
			String modelClassName, String modelName, List<String> nestedFields,
			Map<String, Serializable> parameters) {

		ExportImportVulcanBatchEngineTaskItemDelegate.ExportImportDescriptor
			exportImportDescriptor = _mockExportImportDescriptor();

		Mockito.when(
			exportImportDescriptor.getModelClassName()
		).thenReturn(
			modelClassName
		);

		Mockito.when(
			exportImportDescriptor.getModelName()
		).thenReturn(
			modelName
		);

		Mockito.when(
			exportImportDescriptor.getNestedFields()
		).thenReturn(
			nestedFields
		);

		Mockito.when(
			exportImportDescriptor.getParameters(Mockito.any())
		).thenReturn(
			parameters
		);

		return exportImportDescriptor;
	}

	private Group _mockGroup(String groupExternalReferenceCode, long groupId) {
		Group group = Mockito.mock(Group.class);

		Mockito.doReturn(
			groupExternalReferenceCode
		).when(
			group
		).getExternalReferenceCode();

		Mockito.doReturn(
			groupId
		).when(
			group
		).getGroupId();

		return group;
	}

	private PortletDataContext _mockPortletDataContext() {
		return Mockito.mock(PortletDataContext.class);
	}

	private PortletDataContext _mockPortletDataContext(
		Date endDate, Map<String, String[]> parameterMap, Date startDate) {

		PortletDataContext portletDataContext = _mockPortletDataContext();

		Mockito.when(
			portletDataContext.getEndDate()
		).thenReturn(
			endDate
		);

		Mockito.when(
			portletDataContext.getParameterMap()
		).thenReturn(
			(parameterMap == null) ? new HashMap<>() : parameterMap
		);

		Mockito.when(
			portletDataContext.getStartDate()
		).thenReturn(
			startDate
		);

		return portletDataContext;
	}

	private DateFormat _dateFormat;
	private MockedStatic<FastDateFormatFactoryUtil>
		_fastDateFormatFactoryUtilMockedStatic;

}
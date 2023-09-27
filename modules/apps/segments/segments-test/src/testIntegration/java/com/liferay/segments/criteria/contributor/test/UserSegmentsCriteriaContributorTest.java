/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.criteria.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.test.util.ExpandoTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.odata.entity.ComplexEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.normalizer.Normalizer;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockPortletRequest;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.field.Field;

import java.io.Serializable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.util.tracker.ServiceTracker;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Eduardo García
 */
@RunWith(Arquillian.class)
public class UserSegmentsCriteriaContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws InvalidSyntaxException {
		Bundle bundle = FrameworkUtil.getBundle(
			UserSegmentsCriteriaContributorTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_entityModelServiceTracker = new ServiceTracker<>(
			bundleContext,
			bundleContext.createFilter(
				"(&(entity.model.name=User)(objectClass=" +
					EntityModel.class.getName() + "))"),
			null);

		_entityModelServiceTracker.open();

		_segmentsCriteriaContributorServiceTracker = new ServiceTracker<>(
			bundleContext,
			bundleContext.createFilter(
				"(&(objectClass=" +
					SegmentsCriteriaContributor.class.getName() +
						")(segments.criteria.contributor.key=user))"),
			null);

		_segmentsCriteriaContributorServiceTracker.open();
	}

	@AfterClass
	public static void tearDownClass() {
		_entityModelServiceTracker.close();

		_segmentsCriteriaContributorServiceTracker.close();
	}

	@Before
	public void setUp() throws Exception {
		_expandoTable = ExpandoTestUtil.addTable(
			PortalUtil.getClassNameId(User.class), "CUSTOM_FIELDS");

		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetCriteriaJSONObject() throws Exception {
		SegmentsCriteriaContributor segmentsCriteriaContributor =
			_getSegmentsCriteriaContributor();

		Criteria criteria = new Criteria();

		segmentsCriteriaContributor.contribute(
			criteria, "(lastName eq 'Xing')", Criteria.Conjunction.AND);

		JSONObject jsonObject =
			segmentsCriteriaContributor.getCriteriaJSONObject(criteria);

		Assert.assertEquals(
			String.valueOf(Criteria.Conjunction.AND),
			jsonObject.getString("conjunctionName"));

		Assert.assertEquals(
			JSONUtil.put(
				"conjunctionName", String.valueOf(Criteria.Conjunction.AND)
			).put(
				"groupId", "group_0"
			).put(
				"items",
				JSONUtil.putAll(
					JSONUtil.put(
						"operatorName", "eq"
					).put(
						"propertyName", "lastName"
					).put(
						"value", "Xing"
					))
			).toString(),
			String.valueOf(jsonObject.getString("query")));
	}

	@Test
	public void testGetFieldsWithComplexEntity() throws Exception {
		_addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.STRING,
			ExpandoColumnConstants.INDEX_TYPE_KEYWORD, null);

		EntityModel entityModel = _getEntityModel();

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		ComplexEntityField complexEntityField =
			(ComplexEntityField)entityFieldsMap.get("customField");

		Assert.assertNotNull(complexEntityField);

		SegmentsCriteriaContributor segmentsCriteriaContributor =
			_getSegmentsCriteriaContributor();

		List<Field> fields = segmentsCriteriaContributor.getFields(
			_getMockPortletRequest());

		Set<String> complexEntityFieldNames = new HashSet<>();

		for (Field field : fields) {
			if (StringUtil.startsWith(field.getName(), "customField/")) {
				complexEntityFieldNames.add(
					StringUtil.removeSubstring(
						field.getName(), "customField/"));
			}
		}

		Assert.assertFalse(complexEntityFieldNames.isEmpty());

		Map<String, EntityField> complexEntityFieldsMap =
			complexEntityField.getEntityFieldsMap();

		Assert.assertEquals(
			complexEntityFieldsMap.keySet(), complexEntityFieldNames);
	}

	@Test
	public void testGetFieldsWithOptions() throws Exception {
		String[] defaultValue = {"one", "two", "three"};

		ExpandoColumn expandoColumn = _addExpandoColumn(
			_expandoTable, RandomTestUtil.randomString(),
			ExpandoColumnConstants.STRING_ARRAY,
			ExpandoColumnConstants.INDEX_TYPE_KEYWORD, defaultValue);

		SegmentsCriteriaContributor segmentsCriteriaContributor =
			_getSegmentsCriteriaContributor();

		Field field = null;

		for (Field curField :
				segmentsCriteriaContributor.getFields(
					_getMockPortletRequest())) {

			if (StringUtil.endsWith(
					curField.getName(),
					Normalizer.normalizeIdentifier(expandoColumn.getName()))) {

				field = curField;

				break;
			}
		}

		Assert.assertNotNull(field);

		Assert.assertEquals(
			Arrays.asList(defaultValue),
			TransformUtil.transform(
				field.getOptions(), Field.Option::getValue));
	}

	@Test
	public void testGetFieldsWithSelectEntity() throws Exception {
		Field field = null;

		SegmentsCriteriaContributor segmentsCriteriaContributor =
			_getSegmentsCriteriaContributor();

		for (Field curField :
				segmentsCriteriaContributor.getFields(
					_getMockPortletRequest())) {

			if (Objects.equals(curField.getName(), "groupIds")) {
				field = curField;
			}
		}

		Assert.assertNotNull(field);

		Assert.assertEquals("id", field.getType());

		Assert.assertNotNull(
			"ID type fields must contain a select entity,",
			field.getSelectEntity());
	}

	private ExpandoColumn _addExpandoColumn(
			ExpandoTable expandoTable, String columnName, int columnType,
			int indexType, Serializable defaultValue)
		throws Exception {

		ExpandoColumn expandoColumn = ExpandoTestUtil.addColumn(
			expandoTable, columnName, columnType);

		if (defaultValue != null) {
			ExpandoValue expandoValue = ExpandoTestUtil.addValue(
				_expandoTable, expandoColumn, defaultValue);

			expandoColumn.setDefaultData(expandoValue.getData());
		}

		UnicodeProperties unicodeProperties =
			expandoColumn.getTypeSettingsProperties();

		unicodeProperties.setProperty(
			ExpandoColumnConstants.INDEX_TYPE, String.valueOf(indexType));

		expandoColumn.setTypeSettingsProperties(unicodeProperties);

		return _expandoColumnLocalService.updateExpandoColumn(expandoColumn);
	}

	private EntityModel _getEntityModel() {
		return _entityModelServiceTracker.getService();
	}

	private MockPortletRequest _getMockPortletRequest() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		themeDisplay.setLayout(layout);
		themeDisplay.setLayoutSet(layout.getLayoutSet());

		themeDisplay.setLocale(LocaleUtil.getDefault());
		themeDisplay.setPlid(layout.getPlid());
		themeDisplay.setPortalURL("http://localhost:8080");
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		MockPortletRequest mockPortletRequest = new MockPortletRequest();

		mockPortletRequest.setAttribute(
			PortletServlet.PORTLET_SERVLET_REQUEST, mockHttpServletRequest);

		return mockPortletRequest;
	}

	private SegmentsCriteriaContributor _getSegmentsCriteriaContributor() {
		return _segmentsCriteriaContributorServiceTracker.getService();
	}

	private static ServiceTracker<EntityModel, EntityModel>
		_entityModelServiceTracker;
	private static ServiceTracker
		<SegmentsCriteriaContributor, SegmentsCriteriaContributor>
			_segmentsCriteriaContributorServiceTracker;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@DeleteAfterTestRun
	private ExpandoTable _expandoTable;

	@DeleteAfterTestRun
	private Group _group;

}
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

package com.liferay.layout.util.template.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItemUtil;
import com.liferay.layout.util.template.LayoutConverter;
import com.liferay.layout.util.template.LayoutConverterRegistry;
import com.liferay.layout.util.template.LayoutData;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TreeMapBuilder;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class LayoutConverterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId()));
	}

	@After
	public void tearDown() throws Exception {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testConvertOneColumnMultiplePortlets() throws Exception {
		Map<String, String[]> portletIdsMap = HashMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_hello_velocity_web_portlet_HelloVelocityPortlet",
				"com_liferay_hello_world_web_portlet_HelloWorldPortlet",
				"com_liferay_announcements_web_portlet_AnnouncementsPortlet"
			}
		).build();

		List<Map<String, String[]>> portletIdsMaps =
			new ArrayList<Map<String, String[]>>() {
				{
					add(portletIdsMap);
				}
			};

		_testConvert("1_column", portletIdsMaps, false);
	}

	@Test
	public void testConvertOneColumnNoPortlets() throws Exception {
		_testConvertNoPortlets("1_column");
	}

	@Test
	public void testConvertOneColumnSinglePortlet() throws Exception {
		Map<String, String[]> portletIdsMap = HashMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_hello_world_web_portlet_HelloWorldPortlet"
			}
		).build();

		List<Map<String, String[]>> portletIdsMaps =
			new ArrayList<Map<String, String[]>>() {
				{
					add(portletIdsMap);
				}
			};

		_testConvert("1_column", portletIdsMaps, true);
	}

	@Test
	public void testConvertOneThreeOneColumnsMultiplePortlets()
		throws Exception {

		Map<String, String[]> portletIdsMap1 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();
		Map<String, String[]> portletIdsMap2 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_hello_velocity_web_portlet_HelloVelocityPortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_hello_world_web_portlet_HelloWorldPortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).put(
			"column-3",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();
		Map<String, String[]> portletIdsMap3 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();

		List<Map<String, String[]>> portletIdsMaps =
			new ArrayList<Map<String, String[]>>() {
				{
					add(portletIdsMap1);
					add(portletIdsMap2);
					add(portletIdsMap3);
				}
			};

		_testConvert("1_3_1_columns", portletIdsMaps, false);
	}

	@Test
	public void testConvertOneThreeOneColumnsNoPortlets() throws Exception {
		_testConvertNoPortlets("1_3_1_columns");
	}

	@Test
	public void testConvertOneThreeOneColumnsSinglePortlet() throws Exception {
		Map<String, String[]> portletIdsMap1 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();
		Map<String, String[]> portletIdsMap2 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_hello_velocity_web_portlet_HelloVelocityPortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_hello_world_web_portlet_HelloWorldPortlet"
			}
		).put(
			"column-3",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();
		Map<String, String[]> portletIdsMap3 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();

		List<Map<String, String[]>> portletIdsMaps =
			new ArrayList<Map<String, String[]>>() {
				{
					add(portletIdsMap1);
					add(portletIdsMap2);
					add(portletIdsMap3);
				}
			};

		_testConvert("1_3_1_columns", portletIdsMaps, true);
	}

	@Test
	public void testConvertOneThreeTwoColumnsMultiplePortlets()
		throws Exception {

		Map<String, String[]> portletIdsMap1 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();
		Map<String, String[]> portletIdsMap2 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_hello_velocity_web_portlet_HelloVelocityPortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_hello_world_web_portlet_HelloWorldPortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).put(
			"column-3",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();
		Map<String, String[]> portletIdsMap3 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();

		List<Map<String, String[]>> portletIdsMaps =
			new ArrayList<Map<String, String[]>>() {
				{
					add(portletIdsMap1);
					add(portletIdsMap2);
					add(portletIdsMap3);
				}
			};

		_testConvert("1_3_2_columns", portletIdsMaps, false);
	}

	@Test
	public void testConvertOneThreeTwoColumnsNoPortlets() throws Exception {
		_testConvertNoPortlets("1_3_2_columns");
	}

	@Test
	public void testConvertOneThreeTwoColumnsSinglePortlet() throws Exception {
		Map<String, String[]> portletIdsMap1 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();
		Map<String, String[]> portletIdsMap2 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_hello_velocity_web_portlet_HelloVelocityPortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_hello_world_web_portlet_HelloWorldPortlet"
			}
		).put(
			"column-3",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();
		Map<String, String[]> portletIdsMap3 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();

		List<Map<String, String[]>> portletIdsMaps =
			new ArrayList<Map<String, String[]>>() {
				{
					add(portletIdsMap1);
					add(portletIdsMap2);
					add(portletIdsMap3);
				}
			};

		_testConvert("1_3_2_columns", portletIdsMaps, true);
	}

	@Test
	public void testConvertOneTwoColumnsIIMultiplePortlets() throws Exception {
		_testConvertOneTwoColumnsMultiplePortlets("1_2_columns_ii");
	}

	@Test
	public void testConvertOneTwoColumnsIINoPortlets() throws Exception {
		_testConvertNoPortlets("1_2_columns_ii");
	}

	@Test
	public void testConvertOneTwoColumnsIISinglePortlet() throws Exception {
		_testConvertOneTwoColumnsSinglePortlet("1_2_columns_ii");
	}

	@Test
	public void testConvertOneTwoColumnsIMultiplePortlets() throws Exception {
		_testConvertOneTwoColumnsMultiplePortlets("1_2_columns_i");
	}

	@Test
	public void testConvertOneTwoColumnsINoPortlets() throws Exception {
		_testConvertNoPortlets("1_2_columns_i");
	}

	@Test
	public void testConvertOneTwoColumnsISinglePortlet() throws Exception {
		_testConvertOneTwoColumnsSinglePortlet("1_2_columns_i");
	}

	@Test
	public void testConvertOneTwoOneColumnsIIMultiplePortlets()
		throws Exception {

		_testConvertOneTwoOneColumnsMultiplePortlets("1_2_1_columns_ii");
	}

	@Test
	public void testConvertOneTwoOneColumnsIINoPortlets() throws Exception {
		_testConvertNoPortlets("1_2_1_columns_ii");
	}

	@Test
	public void testConvertOneTwoOneColumnsIISinglePortlet() throws Exception {
		_testConvertOneTwoOneColumnsSinglePortlet("1_2_1_columns_ii");
	}

	@Test
	public void testConvertOneTwoOneColumnsIMultiplePortlets()
		throws Exception {

		_testConvertOneTwoOneColumnsMultiplePortlets("1_2_1_columns_i");
	}

	@Test
	public void testConvertOneTwoOneColumnsINoPortlets() throws Exception {
		_testConvertNoPortlets("1_2_1_columns_i");
	}

	@Test
	public void testConvertOneTwoOneColumnsISinglePortlet() throws Exception {
		_testConvertOneTwoOneColumnsSinglePortlet("1_2_1_columns_i");
	}

	@Test
	public void testConvertThreeColumnsMultiplePortlets() throws Exception {
		Map<String, String[]> portletIdsMap = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_hello_velocity_web_portlet_HelloVelocityPortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_hello_world_web_portlet_HelloWorldPortlet",
				"com_liferay_announcements_web_portlet_AnnouncementsPortlet"
			}
		).put(
			"column-3",
			new String[] {
				"com_liferay_clay_sample_web_portlet_ClaySamplePortlet",
				"com_liferay_clay_sample_web_portlet_ClaySamplePortlet"
			}
		).build();

		_testConvert(
			"3_columns", Collections.singletonList(portletIdsMap), false);
	}

	@Test
	public void testConvertThreeColumnsNoPortlets() throws Exception {
		_testConvertNoPortlets("3_columns");
	}

	@Test
	public void testConvertThreeColumnsSinglePortlet() throws Exception {
		Map<String, String[]> portletIdsMap = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_hello_velocity_web_portlet_HelloVelocityPortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_hello_world_web_portlet_HelloWorldPortlet"
			}
		).put(
			"column-3",
			new String[] {
				"com_liferay_announcements_web_portlet_AnnouncementsPortlet"
			}
		).build();

		_testConvert(
			"3_columns", Collections.singletonList(portletIdsMap), true);
	}

	@Test
	public void testConvertThreeTwoThreeColumnsMultiplePortlets()
		throws Exception {

		Map<String, String[]> portletIdsMap1 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).put(
			"column-3",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();
		Map<String, String[]> portletIdsMap2 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_hello_velocity_web_portlet_HelloVelocityPortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_hello_world_web_portlet_HelloWorldPortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();
		Map<String, String[]> portletIdsMap3 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).put(
			"column-3|",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();

		List<Map<String, String[]>> portletIdsMaps =
			new ArrayList<Map<String, String[]>>() {
				{
					add(portletIdsMap1);
					add(portletIdsMap2);
					add(portletIdsMap3);
				}
			};

		_testConvert("3_2_3_columns", portletIdsMaps, false);
	}

	@Test
	public void testConvertThreeTwoThreeColumnsNoPortlets() throws Exception {
		_testConvertNoPortlets("3_2_3_columns");
	}

	@Test
	public void testConvertThreeTwoThreeColumnsSinglePortlet()
		throws Exception {

		Map<String, String[]> portletIdsMap1 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).put(
			"column-3",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();
		Map<String, String[]> portletIdsMap2 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_hello_velocity_web_portlet_HelloVelocityPortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_hello_world_web_portlet_HelloWorldPortlet"
			}
		).build();
		Map<String, String[]> portletIdsMap3 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).put(
			"column-3|",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();

		List<Map<String, String[]>> portletIdsMaps =
			new ArrayList<Map<String, String[]>>() {
				{
					add(portletIdsMap1);
					add(portletIdsMap2);
					add(portletIdsMap3);
				}
			};

		_testConvert("3_2_3_columns", portletIdsMaps, true);
	}

	@Test
	public void testConvertTwoColumnsIIIMultiplePortlets() throws Exception {
		_testConvertTwoColumnsMultiplePortlets("2_columns_iii");
	}

	@Test
	public void testConvertTwoColumnsIIINoPortlets() throws Exception {
		_testConvertNoPortlets("2_columns_iii");
	}

	@Test
	public void testConvertTwoColumnsIIISinglePortlet() throws Exception {
		_testConvertTwoColumnsSinglePortlet("2_columns_iii");
	}

	@Test
	public void testConvertTwoColumnsIIMultiplePortlets() throws Exception {
		_testConvertTwoColumnsMultiplePortlets("2_columns_ii");
	}

	@Test
	public void testConvertTwoColumnsIINoPortlets() throws Exception {
		_testConvertNoPortlets("2_columns_ii");
	}

	@Test
	public void testConvertTwoColumnsIISinglePortlet() throws Exception {
		_testConvertTwoColumnsSinglePortlet("2_columns_ii");
	}

	@Test
	public void testConvertTwoColumnsIMultiplePortlets() throws Exception {
		_testConvertTwoColumnsMultiplePortlets("2_columns_i");
	}

	@Test
	public void testConvertTwoColumnsINoPortlets() throws Exception {
		_testConvertNoPortlets("2_columns_i");
	}

	@Test
	public void testConvertTwoColumnsISinglePortlet() throws Exception {
		_testConvertTwoColumnsSinglePortlet("2_columns_i");
	}

	@Test
	public void testConvertTwoOneTwoColumnsMultiplePortlets() throws Exception {
		Map<String, String[]> portletIdsMap1 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();
		Map<String, String[]> portletIdsMap2 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_hello_velocity_web_portlet_HelloVelocityPortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();
		Map<String, String[]> portletIdsMap3 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_hello_world_web_portlet_HelloWorldPortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();

		List<Map<String, String[]>> portletIdsMaps =
			new ArrayList<Map<String, String[]>>() {
				{
					add(portletIdsMap1);
					add(portletIdsMap2);
					add(portletIdsMap3);
				}
			};

		_testConvert("2_1_2_columns", portletIdsMaps, false);
	}

	@Test
	public void testConvertTwoOneTwoColumnsNoPortlets() throws Exception {
		_testConvertNoPortlets("2_1_2_columns");
	}

	@Test
	public void testConvertTwoOneTwoColumnsSinglePortlet() throws Exception {
		Map<String, String[]> portletIdsMap1 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();
		Map<String, String[]> portletIdsMap2 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_hello_velocity_web_portlet_HelloVelocityPortlet"
			}
		).build();
		Map<String, String[]> portletIdsMap3 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_hello_world_web_portlet_HelloWorldPortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();

		List<Map<String, String[]>> portletIdsMaps =
			new ArrayList<Map<String, String[]>>() {
				{
					add(portletIdsMap1);
					add(portletIdsMap2);
					add(portletIdsMap3);
				}
			};

		_testConvert("2_1_2_columns", portletIdsMaps, true);
	}

	@Test
	public void testConvertTwoTwoColumnsMultiplePortlets() throws Exception {
		Map<String, String[]> portletIdsMap1 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();
		Map<String, String[]> portletIdsMap2 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_hello_velocity_web_portlet_HelloVelocityPortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_hello_world_web_portlet_HelloWorldPortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();

		List<Map<String, String[]>> portletIdsMaps =
			new ArrayList<Map<String, String[]>>() {
				{
					add(portletIdsMap1);
					add(portletIdsMap2);
				}
			};

		_testConvert("2_2_columns", portletIdsMaps, false);
	}

	@Test
	public void testConvertTwoTwoColumnsNoPortlets() throws Exception {
		_testConvertNoPortlets("2_2_columns");
	}

	@Test
	public void testConvertTwoTwoColumnsSinglePortlet() throws Exception {
		Map<String, String[]> portletIdsMap1 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();
		Map<String, String[]> portletIdsMap2 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_hello_velocity_web_portlet_HelloVelocityPortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_hello_world_web_portlet_HelloWorldPortlet"
			}
		).build();

		List<Map<String, String[]>> portletIdsMaps =
			new ArrayList<Map<String, String[]>>() {
				{
					add(portletIdsMap1);
					add(portletIdsMap2);
				}
			};

		_testConvert("2_2_columns", portletIdsMaps, true);
	}

	@Test
	public void testIsConvertibleTrue() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(_group.getGroupId());

		LayoutConverter layoutConverter =
			_layoutConverterRegistry.getLayoutConverter(
				_getLayoutTemplateId(layout));

		Assert.assertTrue(layoutConverter.isConvertible(layout));
	}

	@Test
	public void testIsConvertibleTrueWidgetPageCustomizable() throws Exception {
		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.setProperty(
			LayoutConstants.CUSTOMIZABLE_LAYOUT, Boolean.TRUE.toString());

		Layout layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), typeSettingsProperties.toString());

		LayoutConverter layoutConverter =
			_layoutConverterRegistry.getLayoutConverter(
				_getLayoutTemplateId(layout));

		Assert.assertTrue(layoutConverter.isConvertible(layout));
	}

	@Test
	public void testIsConvertibleTrueWidgetPageWithNestedApplicationsWidget()
		throws Exception {

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.put(
			LayoutTypePortletConstants.NESTED_COLUMN_IDS,
			StringUtil.randomString());

		Layout layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), typeSettingsProperties.toString());

		LayoutConverter layoutConverter =
			_layoutConverterRegistry.getLayoutConverter(
				_getLayoutTemplateId(layout));

		Assert.assertTrue(layoutConverter.isConvertible(layout));
	}

	private LayoutStructure _convertToReadableItemIds(
		LayoutStructure layoutStructure) {

		LayoutStructure newLayoutStructure = new LayoutStructure();

		Map<String, String> itemIds = new HashMap<>();

		for (LayoutStructureItem layoutStructureItem :
				layoutStructure.getLayoutStructureItems()) {

			String parentItemId = StringPool.BLANK;

			if (Validator.isNotNull(layoutStructureItem.getParentItemId())) {
				LayoutStructureItem parentLayoutStructureItem =
					layoutStructure.getLayoutStructureItem(
						layoutStructureItem.getParentItemId());

				parentItemId = itemIds.computeIfAbsent(
					parentLayoutStructureItem.getItemId(),
					itemId -> _getReadableItemId(
						layoutStructure, parentLayoutStructureItem));
			}

			LayoutStructureItem newLayoutStructureItem =
				LayoutStructureItemUtil.create(
					layoutStructureItem.getItemType(), parentItemId);

			String newItemId = itemIds.computeIfAbsent(
				layoutStructureItem.getItemId(),
				itemId -> _getReadableItemId(
					layoutStructure, layoutStructureItem));

			newLayoutStructureItem.setItemId(newItemId);

			List<String> newChildrenItemIds = new ArrayList<>();

			for (String childrenItemId :
					layoutStructureItem.getChildrenItemIds()) {

				LayoutStructureItem childrenLayoutStructureItem =
					layoutStructure.getLayoutStructureItem(childrenItemId);

				String newChildrenItemId = itemIds.computeIfAbsent(
					childrenItemId,
					itemId -> _getReadableItemId(
						layoutStructure, childrenLayoutStructureItem));

				newChildrenItemIds.add(newChildrenItemId);
			}

			newLayoutStructureItem.setChildrenItemIds(newChildrenItemIds);

			newLayoutStructureItem.updateItemConfig(
				layoutStructureItem.getItemConfigJSONObject());

			newLayoutStructure.addLayoutStructureItem(newLayoutStructureItem);
		}

		String mainItemId = itemIds.computeIfAbsent(
			layoutStructure.getMainItemId(),
			itemId -> _getReadableItemId(
				layoutStructure, layoutStructure.getMainLayoutStructureItem()));

		newLayoutStructure.setMainItemId(mainItemId);

		return newLayoutStructure;
	}

	private String _getLayoutTemplateId(Layout layout) {
		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		return layoutTypePortlet.getLayoutTemplateId();
	}

	private String _getReadableItemId(
		LayoutStructure layoutStructure,
		LayoutStructureItem layoutStructureItem) {

		String parentItemId = layoutStructureItem.getParentItemId();

		if (Validator.isNull(parentItemId)) {
			return StringUtil.toUpperCase(layoutStructureItem.getItemType());
		}

		StringBundler sb = new StringBundler(4);

		LayoutStructureItem parentLayoutStructureItem =
			layoutStructure.getLayoutStructureItem(parentItemId);

		String uuid = _getReadableItemId(
			layoutStructure, parentLayoutStructureItem);

		sb.append(uuid);

		sb.append(StringPool.DASH);
		sb.append(StringUtil.toUpperCase(layoutStructureItem.getItemType()));

		List<String> childrenItemIds =
			parentLayoutStructureItem.getChildrenItemIds();

		int position = childrenItemIds.indexOf(layoutStructureItem.getItemId());

		sb.append(position);

		return sb.toString();
	}

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	private void _testConvert(
			String layoutTemplateId, List<Map<String, String[]>> portletIdsMaps,
			boolean singlePortlet)
		throws Exception {

		int columnId = 0;
		List<Map<String, List<String>>> encodedPortletIdsMaps =
			new ArrayList<>();

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.setProperty(
			LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID, layoutTemplateId);

		Layout layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), typeSettingsProperties.toString());

		for (Map<String, String[]> portletIdsMap : portletIdsMaps) {
			Set<Map.Entry<String, String[]>> entries = portletIdsMap.entrySet();

			Map<String, List<String>> encodedPortletIdsMap = new TreeMap<>();

			for (Map.Entry<String, String[]> entry : entries) {
				columnId++;

				encodedPortletIdsMap.put(entry.getKey(), new ArrayList<>());

				List<String> encodedPortletIds = encodedPortletIdsMap.get(
					entry.getKey());

				for (String portletId : entry.getValue()) {
					Portlet portlet = _portletLocalService.getPortletById(
						_group.getCompanyId(), portletId);

					String encodedPortletId = portletId;

					if (portlet.isInstanceable()) {
						encodedPortletId = PortletIdCodec.encode(portletId);
					}

					LayoutTestUtil.addPortletToLayout(
						TestPropsValues.getUserId(), layout, encodedPortletId,
						"column-" + columnId, new HashMap<>());

					encodedPortletIds.add(encodedPortletId);
				}
			}

			encodedPortletIdsMaps.add(encodedPortletIdsMap);
		}

		LayoutConverter layoutConverter =
			_layoutConverterRegistry.getLayoutConverter(
				_getLayoutTemplateId(layout));

		LayoutData layoutData = layoutConverter.convert(layout);

		JSONObject layoutDataJSONObject = layoutData.getLayoutDataJSONObject();

		LayoutStructure actualLayoutStructure = LayoutStructure.of(
			layoutDataJSONObject.toString());

		actualLayoutStructure = _convertToReadableItemIds(
			actualLayoutStructure);

		String format = "expected_layout_data_%s_multiple_portlets.json";

		if (singlePortlet) {
			format = "expected_layout_data_%s_single_portlet.json";
		}

		String expectedLayoutData = _read(
			String.format(format, layoutTemplateId));

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinks(
				_group.getGroupId(),
				_portal.getClassNameId(Layout.class.getName()),
				layout.getPlid());

		List<FragmentEntryLink> sortedFragmentEntryLinks = ListUtil.sort(
			fragmentEntryLinks,
			Comparator.comparing(FragmentEntryLink::getFragmentEntryLinkId));

		int fromIndex = 0;

		for (Map<String, List<String>> encodedPortletIdsMap :
				encodedPortletIdsMaps) {

			for (Map.Entry<String, List<String>> entry :
					encodedPortletIdsMap.entrySet()) {

				List<String> portletIds = entry.getValue();

				int numberOfPortletsInColumn = portletIds.size();

				List<FragmentEntryLink> fragmentEntryLinksInColumn =
					sortedFragmentEntryLinks.subList(
						fromIndex, fromIndex + numberOfPortletsInColumn);

				fromIndex = fromIndex + numberOfPortletsInColumn;

				Set<String> existingPortletIds = new HashSet<>();
				List<String> fragmentEntryLinkIdsInColumn = new ArrayList<>();

				for (FragmentEntryLink fragmentEntryLink :
						fragmentEntryLinksInColumn) {

					fragmentEntryLinkIdsInColumn.add(
						String.format(
							"\"%s\"",
							fragmentEntryLink.getFragmentEntryLinkId()));

					JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
						fragmentEntryLink.getEditableValues());

					String portletId = jsonObject.getString("portletId");

					String instanceId = jsonObject.getString("instanceId");

					if (Validator.isNotNull(instanceId)) {
						portletId = PortletIdCodec.encode(
							portletId, instanceId);
					}

					existingPortletIds.add(portletId);
				}

				Assert.assertEquals(
					fragmentEntryLinkIdsInColumn.toString(), portletIds.size(),
					fragmentEntryLinkIdsInColumn.size());

				for (String portletId : portletIds) {
					Assert.assertTrue(existingPortletIds.contains(portletId));
				}

				String fragmentEntryLinkIdsJoined = StringUtil.merge(
					fragmentEntryLinkIdsInColumn, StringPool.COMMA_AND_SPACE);

				expectedLayoutData = StringUtil.replaceFirst(
					expectedLayoutData, "[]",
					String.format("[%s]", fragmentEntryLinkIdsJoined));
			}
		}

		LayoutStructure expectedLayoutStructure = LayoutStructure.of(
			expectedLayoutData);

		Assert.assertEquals(expectedLayoutStructure, actualLayoutStructure);
	}

	private void _testConvertNoPortlets(String layoutTemplateId)
		throws Exception {

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.setProperty(
			LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID, layoutTemplateId);

		Layout layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), typeSettingsProperties.toString());

		LayoutConverter layoutConverter =
			_layoutConverterRegistry.getLayoutConverter(
				_getLayoutTemplateId(layout));

		LayoutData layoutData = layoutConverter.convert(layout);

		JSONObject layoutDataJSONObject = layoutData.getLayoutDataJSONObject();

		LayoutStructure actualLayoutStructure = LayoutStructure.of(
			layoutDataJSONObject.toString());

		actualLayoutStructure = _convertToReadableItemIds(
			actualLayoutStructure);

		String expectedLayoutData = _read(
			String.format(
				"expected_layout_data_%s_no_portlets.json", layoutTemplateId));

		LayoutStructure expectedLayoutStructure = LayoutStructure.of(
			expectedLayoutData);

		Assert.assertEquals(expectedLayoutStructure, actualLayoutStructure);
	}

	private void _testConvertOneTwoColumnsMultiplePortlets(
			String layoutTemplateId)
		throws Exception {

		Map<String, String[]> portletIdsMap1 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();
		Map<String, String[]> portletIdsMap2 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_hello_velocity_web_portlet_HelloVelocityPortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_hello_world_web_portlet_HelloWorldPortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();

		List<Map<String, String[]>> portletIdsMaps =
			new ArrayList<Map<String, String[]>>() {
				{
					add(portletIdsMap1);
					add(portletIdsMap2);
				}
			};

		_testConvert(layoutTemplateId, portletIdsMaps, false);
	}

	private void _testConvertOneTwoColumnsSinglePortlet(String layoutTemplateId)
		throws Exception {

		Map<String, String[]> portletIdsMap1 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();
		Map<String, String[]> portletIdsMap2 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_hello_velocity_web_portlet_HelloVelocityPortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_hello_world_web_portlet_HelloWorldPortlet"
			}
		).build();

		List<Map<String, String[]>> portletIdsMaps =
			new ArrayList<Map<String, String[]>>() {
				{
					add(portletIdsMap1);
					add(portletIdsMap2);
				}
			};

		_testConvert(layoutTemplateId, portletIdsMaps, true);
	}

	private void _testConvertOneTwoOneColumnsMultiplePortlets(
			String layoutTemplateId)
		throws Exception {

		Map<String, String[]> portletIdsMap1 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();
		Map<String, String[]> portletIdsMap2 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_hello_velocity_web_portlet_HelloVelocityPortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_hello_world_web_portlet_HelloWorldPortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();
		Map<String, String[]> portletIdsMap3 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();

		List<Map<String, String[]>> portletIdsMaps =
			new ArrayList<Map<String, String[]>>() {
				{
					add(portletIdsMap1);
					add(portletIdsMap2);
					add(portletIdsMap3);
				}
			};

		_testConvert(layoutTemplateId, portletIdsMaps, false);
	}

	private void _testConvertOneTwoOneColumnsSinglePortlet(
			String layoutTemplateId)
		throws Exception {

		Map<String, String[]> portletIdsMap1 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();
		Map<String, String[]> portletIdsMap2 = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_hello_velocity_web_portlet_HelloVelocityPortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_hello_world_web_portlet_HelloWorldPortlet"
			}
		).build();
		Map<String, String[]> portletIdsMap3 = TreeMapBuilder.put(
			"column-3",
			new String[] {
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).build();

		List<Map<String, String[]>> portletIdsMaps =
			new ArrayList<Map<String, String[]>>() {
				{
					add(portletIdsMap1);
					add(portletIdsMap2);
					add(portletIdsMap3);
				}
			};

		_testConvert(layoutTemplateId, portletIdsMaps, true);
	}

	private void _testConvertTwoColumnsMultiplePortlets(String layoutTemplateId)
		throws Exception {

		Map<String, String[]> portletIdsMap = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_hello_velocity_web_portlet_HelloVelocityPortlet",
				"com_liferay_chart_sample_web_portlet_ChartSamplePortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_hello_world_web_portlet_HelloWorldPortlet",
				"com_liferay_announcements_web_portlet_AnnouncementsPortlet"
			}
		).build();

		List<Map<String, String[]>> portletIdsMaps =
			new ArrayList<Map<String, String[]>>() {
				{
					add(portletIdsMap);
				}
			};

		_testConvert(layoutTemplateId, portletIdsMaps, false);
	}

	private void _testConvertTwoColumnsSinglePortlet(String layoutTemplateId)
		throws Exception {

		Map<String, String[]> portletIdsMap = TreeMapBuilder.put(
			"column-1",
			new String[] {
				"com_liferay_hello_velocity_web_portlet_HelloVelocityPortlet"
			}
		).put(
			"column-2",
			new String[] {
				"com_liferay_hello_world_web_portlet_HelloWorldPortlet"
			}
		).build();

		List<Map<String, String[]>> portletIdsMaps =
			new ArrayList<Map<String, String[]>>() {
				{
					add(portletIdsMap);
				}
			};

		_testConvert(layoutTemplateId, portletIdsMaps, true);
	}

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutConverterRegistry _layoutConverterRegistry;

	@Inject
	private Portal _portal;

	@Inject
	private PortletLocalService _portletLocalService;

}
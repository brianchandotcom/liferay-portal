/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.set.prototype.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.test.util.lar.BasePortletExportImportTestCase;
import com.liferay.layout.set.prototype.constants.LayoutSetPrototypePortletKeys;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo García
 */
@RunWith(Arquillian.class)
public class LayoutSetPrototypeExportImportTest
	extends BasePortletExportImportTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public String getNamespace() {
		return "layout_set_prototypes";
	}

	@Override
	public String getPortletId() {
		return LayoutSetPrototypePortletKeys.LAYOUT_SET_PROTOTYPE;
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		UserTestUtil.setUser(TestPropsValues.getUser());
	}

	@Override
	@Test
	public void testExportImportAssetLinks() throws Exception {
	}

	@Test
	public void testExportImportLayoutSetPrototype() throws Exception {
		exportImportLayoutSetPrototype(false);
	}

	@Test
	public void testExportImportLayoutSetPrototypeWithLayoutPrototype()
		throws Exception {

		exportImportLayoutSetPrototype(true);
	}

	protected void exportImportLayoutSetPrototype(boolean layoutPrototype)
		throws Exception {

		// Exclude default site templates

		LayoutSetPrototypeLocalServiceUtil.deleteLayoutSetPrototypes();

		LayoutSetPrototype exportedLayoutSetPrototype =
			LayoutTestUtil.addLayoutSetPrototype(RandomTestUtil.randomString());

		Group exportedLayoutSetPrototypeGroup =
			exportedLayoutSetPrototype.getGroup();

		if (layoutPrototype) {
			LayoutPrototype exportedLayoutPrototype =
				LayoutTestUtil.addLayoutPrototype(
					RandomTestUtil.randomString());

			LayoutTestUtil.addTypePortletLayout(
				exportedLayoutSetPrototypeGroup, true, exportedLayoutPrototype,
				true);
		}
		else {
			LayoutTestUtil.addTypePortletLayout(
				exportedLayoutSetPrototypeGroup, true);
		}

		exportImportPortlet(LayoutSetPrototypePortletKeys.LAYOUT_SET_PROTOTYPE);

		LayoutSetPrototype importedLayoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.
				getLayoutSetPrototypeByUuidAndCompanyId(
					exportedLayoutSetPrototype.getUuid(),
					exportedLayoutSetPrototype.getCompanyId());

		Group importedLayoutSetPrototypeGroup =
			importedLayoutSetPrototype.getGroup();

		Assert.assertEquals(
			exportedLayoutSetPrototypeGroup.getPrivateLayoutsPageCount(),
			importedLayoutSetPrototypeGroup.getPrivateLayoutsPageCount());

		LayoutSetPrototypeLocalServiceUtil.deleteLayoutSetPrototype(
			exportedLayoutSetPrototype);
	}

	@Override
	protected Map<String, String[]> getExportParameterMap() throws Exception {
		Map<String, String[]> parameterMap = super.getExportParameterMap();

		addParameter(parameterMap, "page-templates", true);

		return parameterMap;
	}

	@Override
	protected Map<String, String[]> getImportParameterMap() throws Exception {
		Map<String, String[]> parameterMap = super.getExportParameterMap();

		addParameter(parameterMap, "page-templates", true);

		return parameterMap;
	}

	@Override
	protected void testExportImportDisplayStyle(long groupId, String scopeType)
		throws Exception {
	}

}
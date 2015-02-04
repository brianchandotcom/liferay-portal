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

package com.liferay.portlet.layoutsetprototypes.lar;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.lar.test.BasePortletExportImportTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Eduardo Garcia
 */
@Sync
public class LayoutSetPrototypeExportImportTest
	extends BasePortletExportImportTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	public String getNamespace() {
		return LayoutSetPrototypePortletDataHandler.NAMESPACE;
	}

	@Override
	public String getPortletId() {
		return PortletKeys.LAYOUT_SET_PROTOTYPE;
	}

	@Ignore
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

			LayoutTestUtil.addLayout(
				exportedLayoutSetPrototypeGroup, true, exportedLayoutPrototype,
				true);
		}
		else {
			LayoutTestUtil.addLayout(exportedLayoutSetPrototypeGroup, true);
		}

		exportImportPortlet(PortletKeys.LAYOUT_SET_PROTOTYPE);

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
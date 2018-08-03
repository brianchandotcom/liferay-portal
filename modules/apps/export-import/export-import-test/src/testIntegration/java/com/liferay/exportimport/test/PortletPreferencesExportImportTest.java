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

package com.liferay.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.test.util.lar.BaseExportImportTestCase;
import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class PortletPreferencesExportImportTest
	extends BaseExportImportTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		ServiceTestUtil.setUser(TestPropsValues.getUser());
	}

	@Test
	public void testExportImportGroupEmbeddedPortletPreferences()
		throws Exception {

		String portletInstanceId = PortletIdCodec.encode(
			JournalContentPortletKeys.JOURNAL_CONTENT, "1234");

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			JournalContentPortletKeys.JOURNAL_CONTENT);

		String portletPreferencesXML = _getPortletPreferencesXML(
			"name", new String[] {"value"});

		_addGroupEmbeddedPortlet(
			portletInstanceId, portlet, portletPreferencesXML);

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				importedGroup.getCompanyId(), importedGroup.getGroupId(),
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
				PortletKeys.PREFS_PLID_SHARED, portletInstanceId,
				PortletConstants.DEFAULT_PREFERENCES);

		Assert.assertEquals(
			"value", portletPreferences.getValue("name", StringPool.BLANK));
	}

	private void _addGroupEmbeddedPortlet(
		String portletInstanceId, Portlet portlet, String portletPreferences) {

		PortletPreferencesLocalServiceUtil.addPortletPreferences(
			group.getCompanyId(), group.getGroupId(),
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, PortletKeys.PREFS_PLID_SHARED,
			portletInstanceId, portlet, portletPreferences);

		PortletPreferencesLocalServiceUtil.addPortletPreferences(
			group.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
			portletInstanceId, portlet, PortletConstants.DEFAULT_PREFERENCES);
	}

	private String _getPortletPreferencesXML(String name, String[] values) {
		StringBundler sb = new StringBundler();

		sb.append("<portlet-preferences>");

		if ((name != null) || (values != null)) {
			sb.append("<preference>");

			if (name != null) {
				sb.append("<name>");
				sb.append(name);
				sb.append("</name>");
			}

			if (values != null) {
				for (String value : values) {
					sb.append("<value>");
					sb.append(value);
					sb.append("</value>");
				}
			}

			sb.append("</preference>");
		}

		sb.append("</portlet-preferences>");

		return sb.toString();
	}

}
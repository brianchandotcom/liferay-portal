/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.portlet.preferences.processor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessorHelper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import jakarta.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gabor Komaromi
 */
@RunWith(Arquillian.class)
public class ExportImportPortletPreferencesProcessorHelperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_companyGroup = _groupLocalService.getCompanyGroup(
			TestPropsValues.getCompanyId());

		_group = GroupTestUtil.addGroup();
	}

	@Test
	@TestInfo("LPD-109596")
	public void testGetGroupExportPortletPreferencesExternalReferenceCodeWithCompanyGroup()
		throws Exception {

		ExportImportThreadLocal.setPortletStagingInProcess(true);

		try {
			Assert.assertEquals(
				_COMPANY_GROUP_EXTERNAL_REFERENCE_CODE,
				_exportImportPortletPreferencesProcessorHelper.
					getGroupExportPortletPreferencesExternalReferenceCode(
						TestPropsValues.getCompanyId(),
						_companyGroup.getExternalReferenceCode()));
		}
		finally {
			ExportImportThreadLocal.setPortletStagingInProcess(false);
		}
	}

	@Test
	@TestInfo("LPD-109596")
	public void testGetGroupExportPortletPreferencesExternalReferenceCodeWithCompanyGroupWithoutStaging()
		throws Exception {

		Assert.assertEquals(
			_companyGroup.getExternalReferenceCode(),
			_exportImportPortletPreferencesProcessorHelper.
				getGroupExportPortletPreferencesExternalReferenceCode(
					TestPropsValues.getCompanyId(),
					_companyGroup.getExternalReferenceCode()));
	}

	@Test
	@TestInfo("LPD-109596")
	public void testGetGroupExportPortletPreferencesExternalReferenceCodeWithSiteGroup()
		throws Exception {

		ExportImportThreadLocal.setPortletStagingInProcess(true);

		try {
			Assert.assertEquals(
				_group.getExternalReferenceCode(),
				_exportImportPortletPreferencesProcessorHelper.
					getGroupExportPortletPreferencesExternalReferenceCode(
						TestPropsValues.getCompanyId(),
						_group.getExternalReferenceCode()));
		}
		finally {
			ExportImportThreadLocal.setPortletStagingInProcess(false);
		}
	}

	@Test
	@TestInfo("LPD-109596")
	public void testGetGroupImportPortletPreferencesExternalReferenceCode()
		throws Exception {

		Assert.assertEquals(
			_companyGroup.getExternalReferenceCode(),
			_exportImportPortletPreferencesProcessorHelper.
				getGroupImportPortletPreferencesExternalReferenceCode(
					TestPropsValues.getCompanyId(),
					_COMPANY_GROUP_EXTERNAL_REFERENCE_CODE));
		Assert.assertEquals(
			_group.getExternalReferenceCode(),
			_exportImportPortletPreferencesProcessorHelper.
				getGroupImportPortletPreferencesExternalReferenceCode(
					TestPropsValues.getCompanyId(),
					_group.getExternalReferenceCode()));
	}

	@Test
	@TestInfo("LPD-109596")
	public void testUpdateGroupExportPortletPreferencesExternalReferenceCode()
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromDefaultXML(null);

		portletPreferences.setValue(
			_PREFERENCE_KEY, _companyGroup.getExternalReferenceCode());

		ExportImportThreadLocal.setPortletStagingInProcess(true);

		try {
			_exportImportPortletPreferencesProcessorHelper.
				updateGroupExportPortletPreferencesExternalReferenceCode(
					TestPropsValues.getCompanyId(), _PREFERENCE_KEY,
					portletPreferences);
		}
		finally {
			ExportImportThreadLocal.setPortletStagingInProcess(false);
		}

		Assert.assertEquals(
			_COMPANY_GROUP_EXTERNAL_REFERENCE_CODE,
			portletPreferences.getValue(_PREFERENCE_KEY, null));
	}

	@Test
	@TestInfo("LPD-109596")
	public void testUpdateGroupImportPortletPreferencesExternalReferenceCode()
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromDefaultXML(null);

		portletPreferences.setValue(
			_PREFERENCE_KEY, _COMPANY_GROUP_EXTERNAL_REFERENCE_CODE);

		_exportImportPortletPreferencesProcessorHelper.
			updateGroupImportPortletPreferencesExternalReferenceCode(
				TestPropsValues.getCompanyId(), _PREFERENCE_KEY,
				portletPreferences);

		Assert.assertEquals(
			_companyGroup.getExternalReferenceCode(),
			portletPreferences.getValue(_PREFERENCE_KEY, null));
	}

	private static final String _COMPANY_GROUP_EXTERNAL_REFERENCE_CODE =
		ExportImportPortletPreferencesProcessorHelper.
			COMPANY_GROUP_EXTERNAL_REFERENCE_CODE;

	private static final String _PREFERENCE_KEY = "groupExternalReferenceCode";

	private Group _companyGroup;

	@Inject
	private ExportImportPortletPreferencesProcessorHelper
		_exportImportPortletPreferencesProcessorHelper;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

}
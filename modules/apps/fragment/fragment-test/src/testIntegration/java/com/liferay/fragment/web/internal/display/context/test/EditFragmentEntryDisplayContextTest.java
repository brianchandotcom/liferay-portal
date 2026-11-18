/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.display.context.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.test.util.FragmentEntryTestUtil;
import com.liferay.fragment.test.util.FragmentTestUtil;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.util.BundleUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import jakarta.portlet.RenderResponse;

import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Constructor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class EditFragmentEntryDisplayContextTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	@TestInfo("LPD-111343")
	public void testGetFragmentCollectionId() throws Exception {
		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "A" + RandomTestUtil.randomString());

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "B" + RandomTestUtil.randomString());

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection.getFragmentCollectionId());

		Assert.assertEquals(
			fragmentCollection.getFragmentCollectionId(),
			(long)ReflectionTestUtil.invoke(
				_getEditFragmentEntryDisplayContext(
					fragmentEntry.getExternalReferenceCode()),
				"getFragmentCollectionId", new Class<?>[0]));
	}

	@Test
	@TestInfo("LPD-111343")
	public void testGetFragmentEntryId() throws Exception {
		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentEntry approvedFragmentEntry =
			FragmentEntryTestUtil.addFragmentEntry(
				fragmentCollection.getFragmentCollectionId());

		_testGetFragmentEntryId(
			approvedFragmentEntry.getExternalReferenceCode());

		FragmentEntry draftFragmentEntry =
			FragmentEntryTestUtil.addFragmentEntryByStatus(
				fragmentCollection.getFragmentCollectionId(),
				WorkflowConstants.STATUS_DRAFT);

		_testGetFragmentEntryId(draftFragmentEntry.getExternalReferenceCode());
	}

	private Object _getEditFragmentEntryDisplayContext(
			String externalReferenceCode)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY,
			ContentLayoutTestUtil.getThemeDisplay(
				_companyLocalService.getCompany(_group.getCompanyId()), _group,
				LayoutTestUtil.addTypePortletLayout(_group)));
		mockHttpServletRequest.setParameter(
			"externalReferenceCode", externalReferenceCode);
		mockHttpServletRequest.setParameter(
			"redirect", RandomTestUtil.randomString());

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		bundle = BundleUtil.getBundle(
			bundle.getBundleContext(), "com.liferay.fragment.web");

		Class<?> clazz = bundle.loadClass(
			"com.liferay.fragment.web.internal.display.context." +
				"EditFragmentEntryDisplayContext");

		Constructor<?> constructor = clazz.getConstructor(
			HttpServletRequest.class, RenderResponse.class);

		return constructor.newInstance(
			mockHttpServletRequest, new MockLiferayPortletRenderResponse());
	}

	private void _testGetFragmentEntryId(String externalReferenceCode)
		throws Exception {

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.getFragmentEntry(
				ReflectionTestUtil.invoke(
					_getEditFragmentEntryDisplayContext(externalReferenceCode),
					"getFragmentEntryId", new Class<?>[0]));

		Assert.assertEquals(
			externalReferenceCode, fragmentEntry.getExternalReferenceCode());
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

}
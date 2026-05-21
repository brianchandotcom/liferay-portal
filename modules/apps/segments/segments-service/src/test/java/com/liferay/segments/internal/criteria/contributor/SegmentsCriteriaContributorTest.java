/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.criteria.contributor;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;

import jakarta.portlet.PortletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Eudaldo Alonso
 */
public class SegmentsCriteriaContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_portletRequest = Mockito.mock(PortletRequest.class);
	}

	@Test
	@TestInfo("LPD-91094")
	public void testContextContributorIsAlwaysEnabled() {
		SegmentsCriteriaContributor contextSegmentsCriteriaContributor =
			new ContextSegmentsCriteriaContributor();

		Assert.assertFalse(
			contextSegmentsCriteriaContributor.isDisabled(_portletRequest));
	}

	@Test
	@TestInfo("LPD-91094")
	public void testSegmentsEntryContributorIsDisabledOnAudiencesPortlet() {
		_assertIsDisabledOnAudiencesPortlet(
			new SegmentsEntrySegmentsCriteriaContributor());
	}

	@Test
	@TestInfo("LPD-91094")
	public void testUserContributorIsDisabledOnAudiencesPortlet() {
		_assertIsDisabledOnAudiencesPortlet(
			new UserSegmentsCriteriaContributor());
	}

	@Test
	@TestInfo("LPD-91094")
	public void testUserOrganizationContributorIsDisabledOnAudiencesPortlet() {
		_assertIsDisabledOnAudiencesPortlet(
			new UserOrganizationSegmentsCriteriaContributor());
	}

	private void _assertIsDisabledOnAudiencesPortlet(
		SegmentsCriteriaContributor segmentsCriteriaContributor) {

		Portal portal = Mockito.mock(Portal.class);

		ReflectionTestUtil.setFieldValue(
			segmentsCriteriaContributor, "_portal", portal);

		Mockito.when(
			portal.getPortletId(_portletRequest)
		).thenReturn(
			SegmentsPortletKeys.AUDIENCES
		);

		Assert.assertTrue(
			segmentsCriteriaContributor.isDisabled(_portletRequest));

		Mockito.when(
			portal.getPortletId(_portletRequest)
		).thenReturn(
			SegmentsPortletKeys.SEGMENTS
		);

		Assert.assertFalse(
			segmentsCriteriaContributor.isDisabled(_portletRequest));
	}

	private PortletRequest _portletRequest;

}
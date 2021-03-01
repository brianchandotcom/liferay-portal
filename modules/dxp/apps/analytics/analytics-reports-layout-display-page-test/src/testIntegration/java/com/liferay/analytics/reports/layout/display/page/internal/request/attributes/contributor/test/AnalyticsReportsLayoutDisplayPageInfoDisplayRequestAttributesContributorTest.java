/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.analytics.reports.layout.display.page.internal.request.attributes.contributor.test;

import com.liferay.analytics.reports.constants.AnalyticsReportsWebKeys;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.display.request.attributes.contributor.InfoDisplayRequestAttributesContributor;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Locale;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class
	AnalyticsReportsLayoutDisplayPageInfoDisplayRequestAttributesContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddAttributes() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		_infoDisplayRequestAttributesContributor.addAttributes(
			mockHttpServletRequest);

		Assert.assertNull(
			mockHttpServletRequest.getAttribute(
				AnalyticsReportsWebKeys.INFO_ITEM_REFERENCE));
	}

	@Test
	public void testAddAttributesWithLayoutDisplayPageObjectProvider() {
		ClassName className = _classNameLocalService.addClassName(
			MockObject.class.getName());

		try {
			MockHttpServletRequest mockHttpServletRequest =
				new MockHttpServletRequest();

			mockHttpServletRequest.setAttribute(
				LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER,
				new LayoutDisplayPageObjectProvider<MockObject>() {

					@Override
					public long getClassNameId() {
						return className.getClassNameId();
					}

					@Override
					public long getClassPK() {
						return 0;
					}

					@Override
					public long getClassTypeId() {
						return 0;
					}

					@Override
					public String getDescription(Locale locale) {
						return null;
					}

					@Override
					public MockObject getDisplayObject() {
						return new MockObject();
					}

					@Override
					public long getGroupId() {
						return 0;
					}

					@Override
					public String getKeywords(Locale locale) {
						return null;
					}

					@Override
					public String getTitle(Locale locale) {
						return null;
					}

					@Override
					public String getURLTitle(Locale locale) {
						return null;
					}

				});

			_infoDisplayRequestAttributesContributor.addAttributes(
				mockHttpServletRequest);

			InfoItemReference infoItemReference =
				(InfoItemReference)mockHttpServletRequest.getAttribute(
					AnalyticsReportsWebKeys.INFO_ITEM_REFERENCE);

			Assert.assertEquals(
				MockObject.class.getName(), infoItemReference.getClassName());
		}
		finally {
			_classNameLocalService.deleteClassName(className);
		}
	}

	public static class MockObject {
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject(
		filter = "component.name=*.AnalyticsReportsLayoutDisplayPageInfoDisplayRequestAttributesContributor"
	)
	private InfoDisplayRequestAttributesContributor
		_infoDisplayRequestAttributesContributor;

}
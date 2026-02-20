/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.flags.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.captcha.configuration.CaptchaConfiguration;
import com.liferay.flags.service.FlagsEntryService;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBCategoryLocalServiceUtil;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Ankita Malik
 */
@RunWith(Arquillian.class)
public class EditEntryMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testIgnoreTamperedReporterAndReportedUser() throws Exception {
		User reportedUser = UserTestUtil.addUser();

		long groupId = TestPropsValues.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		MBCategory mbCategory = MBCategoryLocalServiceUtil.addCategory(
			null, reportedUser.getUserId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		MBMessage mbMessage = _mbMessageLocalService.addMessage(
			reportedUser.getUserId(), reportedUser.getFullName(), groupId,
			mbCategory.getCategoryId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), MBMessageConstants.DEFAULT_FORMAT,
			Collections.emptyList(), false, 0, false, serviceContext);

		User reporterUser = TestPropsValues.getUser();

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest(
				_getThemeDisplay(groupId, reporterUser), mbMessage);

		AtomicReference<String> reporterEmailAddressRef =
			new AtomicReference<>();
		AtomicLong reportedUserIdRef = new AtomicLong();

		FlagsEntryService flagsEntryService = new FlagsEntryService() {

			@Override
			public void addEntry(
				String className, long classPK, String reporterEmailAddress,
				long reportedUserId, String contentTitle, String contentURL,
				String reason, ServiceContext serviceContext) {

				reporterEmailAddressRef.set(reporterEmailAddress);
				reportedUserIdRef.set(reportedUserId);
			}

			@Override
			public String getOSGiServiceIdentifier() {
				return "";
			}

		};

		try (AutoCloseable autoCloseable =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					_mvcActionCommand, "_flagsEntryService", flagsEntryService);
			CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						CaptchaConfiguration.class.getName(),
						HashMapDictionaryBuilder.<String, Object>put(
							"maxChallenges", "-1"
						).build());
			ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					CaptchaConfiguration.class.getName(),
					HashMapDictionaryBuilder.<String, Object>put(
						"maxChallenges", "-1"
					).build())) {

			ReflectionTestUtil.invoke(
				_mvcActionCommand, "doProcessAction",
				new Class<?>[] {ActionRequest.class, ActionResponse.class},
				mockLiferayPortletActionRequest,
				new MockLiferayPortletActionResponse());
		}

		Assert.assertEquals(
			reporterUser.getEmailAddress(), reporterEmailAddressRef.get());

		Assert.assertEquals(
			reportedUser.getUserId(), reportedUserIdRef.longValue());
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
		ThemeDisplay themeDisplay, MBMessage mbMessage) {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockLiferayPortletActionRequest.setParameter(
			"className", MBMessage.class.getName());
		mockLiferayPortletActionRequest.setParameter(
			"classPK", String.valueOf(mbMessage.getMessageId()));
		mockLiferayPortletActionRequest.setParameter(
			"contentTitle", mbMessage.getSubject());
		mockLiferayPortletActionRequest.setParameter(
			"contentURL", "http://localhost/test");
		mockLiferayPortletActionRequest.setParameter(
			"reason", StringUtil.randomString());
		mockLiferayPortletActionRequest.setParameter(
			"redirect", "http://localhost");
		mockLiferayPortletActionRequest.setParameter(
			"reporterEmailAddress", "randomUser@liferay.com");
		mockLiferayPortletActionRequest.setParameter(
			"reportedUserId", String.valueOf(RandomTestUtil.nextLong()));

		return mockLiferayPortletActionRequest;
	}

	private ThemeDisplay _getThemeDisplay(long groupId, User user)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.fetchCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setScopeGroupId(groupId);
		themeDisplay.setSignedIn(true);
		themeDisplay.setUser(user);

		return themeDisplay;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private MBMessageLocalService _mbMessageLocalService;

	@Inject(
		filter = "component.name=com.liferay.flags.web.internal.portlet.action.EditEntryMVCActionCommand"
	)
	private MVCActionCommand _mvcActionCommand;

}
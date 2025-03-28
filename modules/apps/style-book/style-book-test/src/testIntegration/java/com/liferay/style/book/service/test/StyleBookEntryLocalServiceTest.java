/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.service.ClientExtensionEntryLocalService;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.style.book.exception.DuplicateStyleBookEntryExternalReferenceCodeException;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import java.util.Collections;

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
public class StyleBookEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, TestPropsValues.getUserId());
	}

	@Test
	public void testAddStyleBookEntry() throws Exception {
		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), false, null, RandomTestUtil.randomString(),
				null, RandomTestUtil.randomString(), _serviceContext);

		Assert.assertTrue(
			Validator.isNotNull(styleBookEntry.getExternalReferenceCode()));
	}

	@Test(
		expected = DuplicateStyleBookEntryExternalReferenceCodeException.class
	)
	public void testAddStyleBookEntryWithExistingExternalReferenceCode()
		throws Exception {

		String externalReferenceCode = RandomTestUtil.randomString();

		_styleBookEntryLocalService.addStyleBookEntry(
			externalReferenceCode, TestPropsValues.getUserId(),
			_group.getGroupId(), false, null, RandomTestUtil.randomString(),
			null, RandomTestUtil.randomString(), _serviceContext);
		_styleBookEntryLocalService.addStyleBookEntry(
			externalReferenceCode, TestPropsValues.getUserId(),
			_group.getGroupId(), false, null, RandomTestUtil.randomString(),
			null, RandomTestUtil.randomString(), _serviceContext);
	}

	@FeatureFlags(enable = false, value = "LPD-13311")
	@Test
	public void testAddStyleBookEntryWithFrontendTokensFromClientExtension()
		throws Exception {

		_clientExtensionEntry =
			_clientExtensionEntryLocalService.addClientExtensionEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				StringPool.BLANK,
				Collections.singletonMap(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()),
				StringPool.BLANK, StringPool.BLANK,
				ClientExtensionEntryConstants.TYPE_THEME_CSS,
				UnicodePropertiesBuilder.create(
					true
				).put(
					"clayRTLURL", _URL_CLAY_RTL_CSS
				).put(
					"clayURL", _URL_CLAY_CSS
				).put(
					"frontendTokenDefinitionJSON", "{}"
				).put(
					"mainRTLURL", _URL_MAIN_RTL_CSS
				).put(
					"mainURL", _URL_MAIN_CSS
				).buildString());

		LayoutSet publicLayoutSet = _group.getPublicLayoutSet();

		_clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_portal.getClassNameId(LayoutSet.class),
			publicLayoutSet.getLayoutSetId(),
			_clientExtensionEntry.getExternalReferenceCode(),
			ClientExtensionEntryConstants.TYPE_THEME_CSS, StringPool.BLANK,
			_serviceContext);

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), false, null, RandomTestUtil.randomString(),
				null, null, _serviceContext);

		Assert.assertEquals(
			_clientExtensionEntry.getExternalReferenceCode(),
			styleBookEntry.getThemeId());
	}

	@Test
	public void testDeleteGroup() throws Exception {
		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), false, null, RandomTestUtil.randomString(),
				null, RandomTestUtil.randomString(), _serviceContext);

		StyleBookEntry draftStyleBookEntry =
			_styleBookEntryLocalService.getDraft(styleBookEntry);

		_groupLocalService.deleteGroup(_group);

		Assert.assertNull(
			_styleBookEntryLocalService.fetchStyleBookEntry(
				styleBookEntry.getStyleBookEntryId()));
		Assert.assertNull(
			_styleBookEntryLocalService.fetchStyleBookEntry(
				draftStyleBookEntry.getStyleBookEntryId()));
	}

	@Test
	public void testDeleteStyleBookEntryByExternalReferenceCode()
		throws Exception {

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), false, null, RandomTestUtil.randomString(),
				null, RandomTestUtil.randomString(), _serviceContext);

		_styleBookEntryLocalService.deleteStyleBookEntry(
			styleBookEntry.getExternalReferenceCode(),
			styleBookEntry.getGroupId());

		Assert.assertNull(
			_styleBookEntryLocalService.fetchStyleBookEntry(
				styleBookEntry.getStyleBookEntryId()));
	}

	private static final String _URL_CLAY_CSS =
		"http://" + RandomTestUtil.randomString() + ".com/styles.css";

	private static final String _URL_CLAY_RTL_CSS =
		"http://" + RandomTestUtil.randomString() + ".com/styles_rtl.css";

	private static final String _URL_MAIN_CSS =
		"http://" + RandomTestUtil.randomString() + ".com/main.css";

	private static final String _URL_MAIN_RTL_CSS =
		"http://" + RandomTestUtil.randomString() + ".com/main_rtl.css";

	private ClientExtensionEntry _clientExtensionEntry;

	@Inject
	private ClientExtensionEntryLocalService _clientExtensionEntryLocalService;

	@Inject
	private ClientExtensionEntryRelLocalService
		_clientExtensionEntryRelLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;

	@Inject
	private StyleBookEntryLocalService _styleBookEntryLocalService;

}
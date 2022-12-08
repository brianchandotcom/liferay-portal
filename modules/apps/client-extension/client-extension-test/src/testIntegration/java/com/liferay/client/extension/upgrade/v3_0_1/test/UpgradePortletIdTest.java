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

package com.liferay.client.extension.upgrade.v3_0_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.service.ClientExtensionEntryLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.persistence.PortletPreferencesPersistence;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

import javax.portlet.Portlet;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Binh Tran
 */
@RunWith(Arquillian.class)
public class UpgradePortletIdTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Ignore
	@Test
	public void testDoUpgrade() throws Exception {
		//UserTestUtil.setUser(TestPropsValues.getUser());

		Group group = GroupTestUtil.addGroup();

		_clientExtensionEntryLocalService.addClientExtensionEntry(
			"3b2b53fb-f264-f234-49d8-8d434d048e75-TEST",
			TestPropsValues.getUserId(), StringPool.BLANK,
			Collections.singletonMap(
				LocaleUtil.fromLanguageId(
					UpgradeProcessUtil.getDefaultLanguageId(
						group.getCompanyId())),
				RandomTestUtil.randomString()),
			StringPool.BLANK, StringPool.BLANK,
			ClientExtensionEntryConstants.TYPE_CUSTOM_ELEMENT,
			UnicodePropertiesBuilder.create(
				true
			).put(
				"htmlElementName", "valid-html-element-name"
			).put(
				"instanceable", false
			).put(
				"urls", "http://" + RandomTestUtil.randomString() + ".com"
			).buildString());

		String normalizedExternalReferenceCode =
			"3b2b53fb_f264_f234_49d8_8d434d048e75_TEST";

		String oldPortletId =
			_PORTLET_ID_PREFIX + normalizedExternalReferenceCode;

		ServiceRegistration<Portlet> serviceRegistration = _register(
			oldPortletId);

		try {
			Layout layout = LayoutTestUtil.addTypePortletLayout(
				group.getGroupId());

			LayoutTestUtil.addPortletToLayout(
				TestPropsValues.getUserId(), layout, oldPortletId, "column-1",
				new HashMap<>());

			PortletPreferences oldPortletPreferences =
				_portletPreferencesLocalService.fetchPortletPreferences(
					PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
					oldPortletId);

			Assert.assertNotNull(oldPortletPreferences);
			Assert.assertEquals(
				oldPortletId, oldPortletPreferences.getPortletId());

			UpgradeProcess upgradeProcess = _getUpgradeProcess();

			upgradeProcess.upgrade();

			_portletPreferencesPersistence.clearCache();

			Assert.assertNull(
				_portletPreferencesLocalService.fetchPortletPreferences(
					PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
					oldPortletId));

			String newPortletId = StringBundler.concat(
				_PORTLET_ID_PREFIX, group.getCompanyId(), StringPool.UNDERLINE,
				normalizedExternalReferenceCode);

			PortletPreferences newPortletPreferences =
				_portletPreferencesLocalService.fetchPortletPreferences(
					PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
					newPortletId);

			Assert.assertNotNull(newPortletPreferences);
			Assert.assertEquals(
				newPortletId, newPortletPreferences.getPortletId());
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	private UpgradeProcess _getUpgradeProcess() {
		UpgradeProcess[] upgradeProcesses = new UpgradeProcess[1];

		_upgradeStepRegistrator.register(
			(fromSchemaVersionString, toSchemaVersionString, upgradeSteps) -> {
				for (UpgradeStep upgradeStep : upgradeSteps) {
					Class<? extends UpgradeStep> clazz = upgradeStep.getClass();

					if (Objects.equals(
							clazz.getName(),
							"com.liferay.client.extension.web.internal." +
								"upgrade.v3_0_1.UpgradePortletId")) {

						upgradeProcesses[0] = (UpgradeProcess)upgradeStep;

						break;
					}
				}
			});

		return upgradeProcesses[0];
	}

	private ServiceRegistration<Portlet> _register(String oldPortletId) {
		Bundle bundle = FrameworkUtil.getBundle(UpgradePortletIdTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		return bundleContext.registerService(
			Portlet.class, new MVCPortlet(),
			HashMapDictionaryBuilder.<String, Object>put(
				"com.liferay.portlet.preferences-company-wide", "false"
			).put(
				"com.liferay.portlet.preferences-owned-by-group", false
			).put(
				"com.liferay.portlet.preferences-unique-per-layout", true
			).put(
				"javax.portlet.name", oldPortletId
			).build());
	}

	private static final String _PORTLET_ID_PREFIX =
		"com_liferay_client_extension_web_internal_portlet_" +
			"ClientExtensionEntryPortlet_";

	@Inject
	private ClientExtensionEntryLocalService _clientExtensionEntryLocalService;

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Inject
	private PortletPreferencesPersistence _portletPreferencesPersistence;

	@Inject(
		filter = "component.name=com.liferay.client.extension.web.internal.upgrade.registry.ClientExtensionWebUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}
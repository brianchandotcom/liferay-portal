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

package com.liferay.module.configuration.localization.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.definitions.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.definitions.ExtendedMetaTypeInformation;
import com.liferay.portal.configuration.metatype.definitions.ExtendedMetaTypeService;
import com.liferay.portal.configuration.metatype.definitions.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.resource.bundle.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Lance Ji
 */
@RunWith(Arquillian.class)
public class ModuleConfigurationLocalizationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testConfigurationLocalization() {
		Bundle currentBundle = FrameworkUtil.getBundle(
			ModuleConfigurationLocalizationTest.class);

		BundleContext bundleContext = currentBundle.getBundleContext();

		StringBundler sb = new StringBundler();

		for (Bundle bundle : bundleContext.getBundles()) {
			String bundleError = _collectBundleError(bundle);

			if (bundleError.isEmpty()) {
				continue;
			}

			sb.append(StringPool.NEW_LINE);
			sb.append("Bundle {id: ");
			sb.append(bundle.getBundleId());
			sb.append(", name: ");
			sb.append(bundle.getSymbolicName());
			sb.append(", version: ");
			sb.append(bundle.getVersion());
			sb.append(StringPool.CLOSE_CURLY_BRACE);
			sb.append(bundleError);
			sb.append(StringPool.NEW_LINE);
		}

		String message = sb.toString();

		Assert.assertTrue("Test failed due to: " + message, message.isEmpty());
	}

	private String _collectBundleError(Bundle bundle) {
		ExtendedMetaTypeInformation extendedMetaTypeInformation =
			_extendedMetaTypeService.getMetaTypeInformation(bundle);

		List<String> pids = new ArrayList<>();

		Collections.addAll(pids, extendedMetaTypeInformation.getFactoryPids());
		Collections.addAll(pids, extendedMetaTypeInformation.getPids());

		String bundleName = bundle.getSymbolicName();

		if (pids.isEmpty() || !bundleName.startsWith("com.liferay")) {
			return StringPool.BLANK;
		}

		Locale locale = LocaleUtil.getDefault();

		Iterator<String> iterator = pids.iterator();

		while (iterator.hasNext()) {
			ExtendedObjectClassDefinition extendedObjectClassDefinition =
				extendedMetaTypeInformation.getObjectClassDefinition(
					iterator.next(), locale.getLanguage());

			if (!_isGenerateUI(extendedObjectClassDefinition)) {
				iterator.remove();
			}
		}

		if (pids.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		ResourceBundleLoader resourceBundleLoader =
			ResourceBundleLoaderUtil.
				getResourceBundleLoaderByBundleSymbolicName(
					bundle.getSymbolicName());

		if (resourceBundleLoader == null) {
			sb.append(
				"\n\tMissing default language file for configuration pids: ");

			for (String pid : pids) {
				sb.append(pid);
				sb.append(StringPool.COMMA);
			}

			sb.setIndex(sb.index() - 1);

			return sb.toString();
		}

		ResourceBundleLoader aggregateResourceBundleLoader =
			new AggregateResourceBundleLoader(
				resourceBundleLoader,
				ResourceBundleLoaderUtil.getPortalResourceBundleLoader());

		ResourceBundle resourceBundle =
			aggregateResourceBundleLoader.loadResourceBundle(
				LocaleUtil.getDefault());

		for (String pid : pids) {
			String configurationError = _collectConfigurationError(
				pid, extendedMetaTypeInformation, resourceBundle);

			if (configurationError.isEmpty()) {
				continue;
			}

			sb.append("\n\tConfiguration {pid:");
			sb.append(pid);
			sb.append(StringPool.CLOSE_CURLY_BRACE);
			sb.append(configurationError);
		}

		return sb.toString();
	}

	private String _collectConfigurationError(
		String pid, ExtendedMetaTypeInformation extendedMetaTypeInformation,
		ResourceBundle resourceBundle) {

		Locale locale = LocaleUtil.getDefault();

		ExtendedObjectClassDefinition extendedObjectClassDefinition =
			extendedMetaTypeInformation.getObjectClassDefinition(
				pid, locale.getLanguage());

		StringBundler sb = new StringBundler();

		String extendedObjectClassDefinitionName = ResourceBundleUtil.getString(
			resourceBundle, extendedObjectClassDefinition.getName());

		if (extendedObjectClassDefinitionName == null) {
			sb.append("\n\t\tMissing localization for configuration pid: ");
			sb.append(extendedObjectClassDefinition.getID());
		}

		List<String> missingAttributeNames = new ArrayList<>();

		for (ExtendedAttributeDefinition extendedAttributeDefinition :
				extendedObjectClassDefinition.getAttributeDefinitions(
					ExtendedObjectClassDefinition.ALL)) {

			String extendedAttributeDefinitionName =
				ResourceBundleUtil.getString(
					resourceBundle, extendedAttributeDefinition.getName());

			if (extendedAttributeDefinitionName == null) {
				missingAttributeNames.add(extendedAttributeDefinition.getID());
			}
		}

		if (!missingAttributeNames.isEmpty()) {
			sb.append("\n\t\tMissing localization for attributes: ");

			for (String attributeName : missingAttributeNames) {
				sb.append(attributeName);
				sb.append(StringPool.COMMA);
			}

			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private boolean _isGenerateUI(
		ExtendedObjectClassDefinition extendedObjectClassDefinition) {

		for (String extensionUri :
				extendedObjectClassDefinition.getExtensionUris()) {

			Map<String, String> extensionAttributes =
				extendedObjectClassDefinition.getExtensionAttributes(
					extensionUri);

			boolean generateUI = GetterUtil.getBoolean(
				extensionAttributes.get("generateUI"), true);

			if (!generateUI) {
				return false;
			}
		}

		return true;
	}

	@Inject
	private static ExtendedMetaTypeService _extendedMetaTypeService;

}
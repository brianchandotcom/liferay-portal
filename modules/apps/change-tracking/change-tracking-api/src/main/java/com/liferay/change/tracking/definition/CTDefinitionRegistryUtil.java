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

package com.liferay.change.tracking.definition;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Máté Thurzó
 */
public class CTDefinitionRegistryUtil {

	public static List<String> getContentTypeLanguageKeys() {
		List<String> contentTypeLanguageKeys = new ArrayList<>();

		List<CTDefinition<?, ?>> ctConfigurations =
			_getCTConfigurationRegistry().getAllCTDefinitions();

		for (CTDefinition ctConfiguration : ctConfigurations) {
			contentTypeLanguageKeys.add(
				ctConfiguration.getContentTypeLanguageKey());
		}

		return contentTypeLanguageKeys;
	}

	public static String getVersionEntityContentTypeLanguageKey(
		long classNameId) {

		CTDefinition<?, ?> ctConfiguration = _getCTConfiguration(classNameId);

		return ctConfiguration.getContentTypeLanguageKey();
	}

	@SuppressWarnings("unchecked")
	public static long getVersionEntityGroupId(long classNameId, long classPK) {
		CTDefinition<?, ?> ctConfiguration = _getCTConfiguration(classNameId);

		Function versionEntityByVersionEntityIdFunction =
			ctConfiguration.getVersionEntityByVersionEntityIdFunction();

		Object versionEntity = versionEntityByVersionEntityIdFunction.apply(
			classPK);

		if (versionEntity instanceof GroupedModel) {
			GroupedModel groupedModel = (GroupedModel)versionEntity;

			return groupedModel.getGroupId();
		}

		return BeanPropertiesUtil.getLongSilent(versionEntity, "groupId");
	}

	@SuppressWarnings("unchecked")
	public static String getVersionEntitySiteName(
		long classNameId, long classPK) {

		CTDefinition<?, ?> ctConfiguration = _getCTConfiguration(classNameId);

		Function versionEntityByVersionEntityIdFunction =
			ctConfiguration.getVersionEntityByVersionEntityIdFunction();

		Function versionEntitySiteNameFunction =
			ctConfiguration.getVersionEntitySiteNameFunction();

		return (String)versionEntitySiteNameFunction.compose(
			versionEntityByVersionEntityIdFunction
		).apply(
			classPK
		);
	}

	@SuppressWarnings("unchecked")
	public static String getVersionEntityTitle(long classNameId, long classPK) {
		CTDefinition<?, ?> ctConfiguration = _getCTConfiguration(classNameId);

		Function versionEntityByVersionEntityIdFunction =
			ctConfiguration.getVersionEntityByVersionEntityIdFunction();

		Function versionEntityTitleFunction =
			ctConfiguration.getVersionEntityTitleFunction();

		return (String)versionEntityTitleFunction.compose(
			versionEntityByVersionEntityIdFunction
		).apply(
			classPK
		);
	}

	@SuppressWarnings("unchecked")
	public static Serializable getVersionEntityVersion(
		long classNameId, long classPK) {

		CTDefinition<?, ?> ctConfiguration = _getCTConfiguration(classNameId);

		Function versionEntityByVersionEntityIdFunction =
			ctConfiguration.getVersionEntityByVersionEntityIdFunction();

		Function versionEntityVersionFunction =
			ctConfiguration.getVersionEntityVersionFunction();

		return (Serializable)versionEntityVersionFunction.compose(
			versionEntityByVersionEntityIdFunction
		).apply(
			classPK
		);
	}

	private static CTDefinition<?, ?> _getCTConfiguration(long classNameId) {
		Optional<CTDefinition<?, ?>> ctConfigurationOptional =
			_getCTConfigurationRegistry().
				getCTDefinitionOptionalByVersionClassName(
					PortalUtil.getClassName(classNameId));

		return ctConfigurationOptional.get();
	}

	private static CTDefinitionRegistry _getCTConfigurationRegistry() {
		return _serviceTracker.getService();
	}

	private static final ServiceTracker
		<CTDefinitionRegistry, CTDefinitionRegistry> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CTDefinitionRegistry.class);

		ServiceTracker<CTDefinitionRegistry, CTDefinitionRegistry>
			serviceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), CTDefinitionRegistry.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
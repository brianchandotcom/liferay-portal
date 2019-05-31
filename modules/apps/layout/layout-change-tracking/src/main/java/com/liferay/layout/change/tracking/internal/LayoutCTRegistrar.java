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

package com.liferay.layout.change.tracking.internal;

import com.liferay.change.tracking.definition.CTDefinitionRegistrar;
import com.liferay.change.tracking.definition.builder.CTDefinitionBuilder;
import com.liferay.change.tracking.function.CTFunctions;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutVersion;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Gergely Mathe
 */
@Component(immediate = true, service = {})
public class LayoutCTRegistrar {

	@Activate
	public void activate() {
		_ctDefinitionRegistrar.register(
			_builder.setContentType(
				"Page"
			).setContentTypeLanguageKey(
				"layout"
			).setEntityClasses(
				Layout.class, LayoutVersion.class
			).setResourceEntitiesByCompanyIdFunction(
				_layoutLocalService::getLayouts
			).setResourceEntityByResourceEntityIdFunction(
				_layoutLocalService::fetchLayout
			).setEntityIdsFromResourceEntityFunctions(
				Layout::getPlid, this::_fetchLatestVersionId
			).setVersionEntitiesFromResourceEntityFunction(
				_layoutLocalService::getVersions
			).setVersionEntityByVersionEntityIdFunction(
				_layoutLocalService::fetchLayoutVersion
			).setVersionEntityDetails(
				Collections.emptyList(), CTFunctions.getFetchSiteNameFunction(),
				LayoutVersion::getName, LayoutVersion::getVersion
			).setEntityIdsFromVersionEntityFunctions(
				LayoutVersion::getPlid, LayoutVersion::getLayoutVersionId
			).setVersionEntityStatusInfo(
				new Integer[] {WorkflowConstants.STATUS_APPROVED},
				layoutVersion -> WorkflowConstants.STATUS_APPROVED
			).build());
	}

	private long _fetchLatestVersionId(Layout layout) {
		LayoutVersion layoutVersion = _layoutLocalService.fetchLatestVersion(
			layout);

		return layoutVersion.getLayoutVersionId();
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private CTDefinitionBuilder<Layout, LayoutVersion> _builder;

	@Reference
	private CTDefinitionRegistrar _ctDefinitionRegistrar;

	@Reference
	private LayoutLocalService _layoutLocalService;

}
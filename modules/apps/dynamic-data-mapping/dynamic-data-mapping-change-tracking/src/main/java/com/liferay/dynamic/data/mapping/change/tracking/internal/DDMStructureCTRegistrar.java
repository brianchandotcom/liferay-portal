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

package com.liferay.dynamic.data.mapping.change.tracking.internal;

import com.liferay.change.tracking.definition.CTDefinitionRegistrar;
import com.liferay.change.tracking.definition.builder.CTDefinitionBuilder;
import com.liferay.change.tracking.function.CTFunctions;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Máté Thurzó
 */
@Component(immediate = true, service = {})
public class DDMStructureCTRegistrar {

	@Activate
	public void activate() {
		_ctDefinitionRegistrar.register(
			_builder.setContentType(
				"Dynamic Data Mapping Structure"
			).setContentTypeLanguageKey(
				"dynamic-data-mapping-structure"
			).setEntityClasses(
				DDMStructure.class, DDMStructureVersion.class
			).setResourceEntitiesByCompanyIdFunction(
				this::_fetchDDMStructures
			).setResourceEntityByResourceEntityIdFunction(
				_ddmStructureLocalService::fetchStructure
			).setEntityIdsFromResourceEntityFunctions(
				DDMStructure::getStructureId,
				this::_fetchLatestStructureVersionId
			).setVersionEntitiesFromResourceEntityFunction(
				ddmStructure ->
					_ddmStructureVersionLocalService.getStructureVersions(
						ddmStructure.getStructureId())
			).setVersionEntityByVersionEntityIdFunction(
				_ddmStructureVersionLocalService::fetchDDMStructureVersion
			).setVersionEntityDetails(
				null, CTFunctions.getFetchSiteNameFunction(),
				ddmStructureVersion -> ddmStructureVersion.getName(
					LocaleUtil.getMostRelevantLocale()),
				DDMStructureVersion::getVersion
			).setEntityIdsFromVersionEntityFunctions(
				DDMStructureVersion::getStructureId,
				DDMStructureVersion::getStructureVersionId
			).setVersionEntityStatusInfo(
				new Integer[] {
					WorkflowConstants.STATUS_APPROVED,
					WorkflowConstants.STATUS_DRAFT
				},
				DDMStructureVersion::getStatus
			).build());
	}

	private List<DDMStructure> _fetchDDMStructures(long companyId) {
		DynamicQuery dynamicQuery = _ddmStructureLocalService.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(companyId));

		return _ddmStructureLocalService.dynamicQuery(dynamicQuery);
	}

	private Serializable _fetchLatestStructureVersionId(
		DDMStructure ddmStructure) {

		try {
			DDMStructureVersion ddmStructureVersion =
				ddmStructure.getStructureVersion();

			return ddmStructureVersion.getStructureVersionId();
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"An error occured when getting structure version ID", pe);
			}

			return 0;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMStructureCTRegistrar.class);

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private CTDefinitionBuilder<DDMStructure, DDMStructureVersion> _builder;

	@Reference
	private CTDefinitionRegistrar _ctDefinitionRegistrar;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

}
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

package com.liferay.document.library.change.tracking.service.internal.configuration;

import com.liferay.change.tracking.configuration.CTConfigurationRegistrar;
import com.liferay.change.tracking.configuration.builder.CTConfigurationBuilder;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

import java.util.List;

/**
 * @author Luiz Marins
 */
@Component(immediate = true, service = {})
public class DLFileEntryCTConfigurationRegistrar {

	@Activate
	public void activate() {
		_ctConfigurationRegistrar.register(
			_builder.setContentType(
				"Document and Media"
			).setContentTypeLanguageKey(
				"document"
			).setEntityClasses(
				DLFileEntry.class, DLFileVersion.class
			).setResourceEntitiesByCompanyIdFunction(
				this::_fetchDLFileEntries
			).setResourceEntityByResourceEntityIdFunction(
				_dlFileEntryLocalService::fetchDLFileEntry
			).setEntityIdsFromResourceEntityFunctions(
				DLFileEntry::getFileEntryId,
				this::_versionEntityIdFromResourceEntityFunction
			).setVersionEntitiesFromResourceEntityFunction(
				dlFileEntry ->
					_dlFileVersionLocalService.getFileVersions(
						dlFileEntry.getFileEntryId(),
						WorkflowConstants.STATUS_ANY)
			).setVersionEntityByVersionEntityIdFunction(
				_dlFileVersionLocalService::fetchDLFileVersion
			).setVersionEntityDetails(
				null, this::_fetchGroupName, DLFileVersion::getTitle,
				DLFileVersion::getVersion
			).setEntityIdsFromVersionEntityFunctions(
				DLFileVersion::getFileEntryId, DLFileVersion::getFileVersionId
			).setVersionEntityStatusInfo(
				new Integer[] {
					WorkflowConstants.STATUS_APPROVED,
					WorkflowConstants.STATUS_DRAFT
				},
				DLFileVersion::getStatus
			).build());
	}

	private List<DLFileEntry> _fetchDLFileEntries(long companyId) {
		DynamicQuery dynamicQuery = _dlFileEntryLocalService.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(companyId));

		return _dlFileEntryLocalService.dynamicQuery(dynamicQuery);
	}

	private String _fetchGroupName(DLFileVersion dlFileVersion) {
		Group group = _groupLocalService.fetchGroup(dlFileVersion.getGroupId());

		try {
			return group.getDescriptiveName();
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return StringPool.BLANK;
		}
	}

	private long _versionEntityIdFromResourceEntityFunction(
		DLFileEntry dlFileEntry) {

		try {
			DLFileVersion fileVersion = dlFileEntry.getFileVersion();

			return fileVersion.getFileVersionId();
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return 0L;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryCTConfigurationRegistrar.class);

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private CTConfigurationBuilder<DLFileEntry, DLFileVersion> _builder;

	@Reference
	private CTConfigurationRegistrar _ctConfigurationRegistrar;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFileVersionLocalService _dlFileVersionLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}
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

package com.liferay.dynamic.data.mapping.change.tracking.internal.configuration;

import com.liferay.change.tracking.configuration.CTConfigurationRegistrar;
import com.liferay.change.tracking.configuration.builder.CTConfigurationBuilder;
import com.liferay.change.tracking.function.CTFunctions;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateVersion;
import com.liferay.dynamic.data.mapping.model.DDMTemplateVersionModel;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateVersionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Máté Thurzó
 */
@Component(immediate = true, service = {})
public class DDMTemplateCTConfigurationRegistrar {

	@Activate
	public void activate() {
		_ctConfigurationRegistrar.register(
			_builder.setContentType(
				"Dynamic Data Mapping Template"
			).setContentTypeLanguageKey(
				"dynamic-data-mapping"
			).setEntityClasses(
				DDMTemplate.class, DDMTemplateVersion.class
			).setResourceEntityByResourceEntityIdFunction(
				_ddmTemplateLocalService::fetchTemplate
			).setEntityIdsFromResourceEntityFunctions(
				DDMTemplate::getTemplateId, this::_fetchLatestTemplateVersionId
			).setVersionEntityByVersionEntityIdFunction(
				_ddmTemplateVersionLocalService::fetchDDMTemplateVersion
			).setVersionEntityDetails(
				CTFunctions.getFetchSiteNameFunction(),
				ddmStructureVersion -> ddmStructureVersion.getName(
					LocaleUtil.getMostRelevantLocale()),
				DDMTemplateVersion::getVersion
			).setEntityIdsFromVersionEntityFunctions(
				DDMTemplateVersion::getTemplateId,
				DDMTemplateVersionModel::getTemplateVersionId
			).setVersionEntityStatusInfo(
				new Integer[] {WorkflowConstants.STATUS_APPROVED},
				ddmTemplateVersion -> WorkflowConstants.STATUS_APPROVED
			).build());
	}

	private Serializable _fetchLatestTemplateVersionId(
		DDMTemplate ddmTemplate) {

		try {
			DDMTemplateVersion ddmTemplateVersion =
				ddmTemplate.getLatestTemplateVersion();

			return ddmTemplateVersion.getTemplateVersionId();
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"An error occured when getting template version id", pe);
			}

			return 0;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMTemplateCTConfigurationRegistrar.class);

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private CTConfigurationBuilder<DDMTemplate, DDMTemplateVersion> _builder;

	@Reference
	private CTConfigurationRegistrar _ctConfigurationRegistrar;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private DDMTemplateVersionLocalService _ddmTemplateVersionLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}
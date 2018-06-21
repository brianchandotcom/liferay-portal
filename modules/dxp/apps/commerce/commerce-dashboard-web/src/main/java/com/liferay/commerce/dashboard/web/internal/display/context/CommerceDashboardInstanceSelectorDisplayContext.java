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

package com.liferay.commerce.dashboard.web.internal.display.context;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.util.comparator.CPInstanceSkuComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import javax.portlet.RenderRequest;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceDashboardInstanceSelectorDisplayContext
	extends CommerceDashboardDisplayContext {

	public CommerceDashboardInstanceSelectorDisplayContext(
			ConfigurationProvider configurationProvider,
			CPInstanceService cpInstanceService, RenderRequest renderRequest)
		throws PortalException {

		super(configurationProvider, renderRequest);

		_cpInstanceService = cpInstanceService;
	}

	public CPInstance getCPInstance(long cpInstanceId) {
		try {
			return _cpInstanceService.getCPInstance(cpInstanceId);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}

			return null;
		}
	}

	public String getCPInstanceLabel(CPInstance cpInstance)
		throws PortalException {

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		String languageId = LanguageUtil.getLanguageId(
			commerceDashboardRequestHelper.getLocale());

		return cpInstance.getSku() + " - " + cpDefinition.getName(languageId);
	}

	public List<CPInstance> getCPInstances() throws PortalException {
		return _cpInstanceService.getCPInstances(
			commerceDashboardRequestHelper.getScopeGroupId(),
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new CPInstanceSkuComparator(true));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDashboardInstanceSelectorDisplayContext.class);

	private final CPInstanceService _cpInstanceService;

}
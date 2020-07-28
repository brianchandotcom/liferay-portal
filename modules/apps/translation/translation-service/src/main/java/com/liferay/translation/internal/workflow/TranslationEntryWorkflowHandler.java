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

package com.liferay.translation.internal.workflow;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.translation.model.TranslationEntry;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alicia Garcia
 */
@Component(
	property = "model.class.name=com.liferay.translation.model.TranslationEntry",
	service = WorkflowHandler.class
)
public class TranslationEntryWorkflowHandler
	extends BaseWorkflowHandler<TranslationEntry> {

	@Override
	public AssetRendererFactory<TranslationEntry> getAssetRendererFactory() {
		return (AssetRendererFactory<TranslationEntry>)
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				getClassName());
	}

	@Override
	public String getClassName() {
		return TranslationEntry.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return ResourceActionsUtil.getModelResource(locale, getClassName());
	}

	@Override
	public boolean isVisible() {
		return _VISIBLE;
	}

	@Override
	public TranslationEntry updateStatus(
			int status, Map<String, Serializable> workflowContext)
		throws PortalException {

		return null;
	}

	private static final boolean _VISIBLE = true;

}
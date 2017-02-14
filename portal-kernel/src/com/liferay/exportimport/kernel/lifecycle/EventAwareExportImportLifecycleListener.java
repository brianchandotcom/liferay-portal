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

package com.liferay.exportimport.kernel.lifecycle;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.portal.kernel.model.StagedModel;

/**
 * @author Daniel Kocsis
 */
public interface EventAwareExportImportLifecycleListener
	extends ExportImportLifecycleListener {

	@Override
	public default void onExportImportLifecycleEvent(
			ExportImportLifecycleEvent exportImportLifecycleEvent)
		throws Exception {
	}

	public void onLayoutExportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception;

	public void onLayoutExportStarted(PortletDataContext portletDataContext)
		throws Exception;

	public void onLayoutExportSucceeded(PortletDataContext portletDataContext)
		throws Exception;

	public void onLayoutImportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception;

	public void onLayoutImportProcessFinished(
			PortletDataContext portletDataContext)
		throws Exception;

	public void onLayoutImportStarted(PortletDataContext portletDataContext)
		throws Exception;

	public void onLayoutImportSucceeded(PortletDataContext portletDataContext)
		throws Exception;

	public void onLayoutLocalPublicationFailed(
			ExportImportConfiguration exportImportConfiguration,
			Throwable throwable)
		throws Exception;

	public void onLayoutLocalPublicationStarted(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception;

	public void onLayoutLocalPublicationSucceeded(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception;

	public void onLayoutRemotePublicationFailed(
			ExportImportConfiguration exportImportConfiguration,
			Throwable throwable)
		throws Exception;

	public void onLayoutRemotePublicationStarted(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception;

	public void onLayoutRemotePublicationSucceeded(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception;

	public void onPortletExportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception;

	public void onPortletExportStarted(PortletDataContext portletDataContext)
		throws Exception;

	public void onPortletExportSucceeded(PortletDataContext portletDataContext)
		throws Exception;

	public void onPortletImportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception;

	public void onPortletImportProcessFinished(
			PortletDataContext portletDataContext)
		throws Exception;

	public void onPortletImportStarted(PortletDataContext portletDataContext)
		throws Exception;

	public void onPortletImportSucceeded(PortletDataContext portletDataContext)
		throws Exception;

	public void onPortletPublicationFailed(
			ExportImportConfiguration exportImportConfiguration,
			Throwable throwable)
		throws Exception;

	public void onPortletPublicationStarted(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception;

	public void onPortletPublicationSucceeded(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception;

	public void onStagedModelExportFailed(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			Throwable throwable)
		throws Exception;

	public void onStagedModelExportStarted(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception;

	public void onStagedModelExportSucceeded(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception;

	public void onStagedModelImportFailed(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			Throwable throwable)
		throws Exception;

	public void onStagedModelImportStarted(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception;

	public void onStagedModelImportSucceeded(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.lifecycle;

import com.liferay.exportimport.kernel.lar.ExportImportClassedModelUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lifecycle.EventAwareExportImportLifecycleListener;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleEvent;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleListener;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.StagedGroupedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.util.MapUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Daniel Kocsis
 */
@Component(service = ExportImportLifecycleListener.class)
public class LoggerExportImportLifecycleListener
	implements EventAwareExportImportLifecycleListener {

	public String getStagedModelLogFragment(StagedModel stagedModel) {
		StringBundler sb = new StringBundler(7);

		sb.append("{class: ");
		sb.append(ExportImportClassedModelUtil.getClassName(stagedModel));

		if (stagedModel instanceof StagedGroupedModel) {
			StagedGroupedModel stagedGroupedModel =
				(StagedGroupedModel)stagedModel;

			sb.append(", groupId: ");
			sb.append(stagedGroupedModel.getGroupId());
		}

		sb.append(", uuid: ");
		sb.append(stagedModel.getUuid());
		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}

	@Override
	public boolean isParallel() {
		return false;
	}

	@Override
	public void onExportImportLifecycleEvent(
			ExportImportLifecycleEvent exportImportLifecycleEvent)
		throws Exception {
	}

	@Override
	public void onLayoutExportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception {

		_log.error(
			"Layout export failed for group " + portletDataContext.getGroupId(),
			throwable);
	}

	@Override
	public void onLayoutExportStarted(PortletDataContext portletDataContext)
		throws Exception {

		if (!_log.isDebugEnabled()) {
			return;
		}

		_log.debug(
			"Layout export started for group " +
				portletDataContext.getGroupId());
	}

	@Override
	public void onLayoutExportSucceeded(PortletDataContext portletDataContext)
		throws Exception {

		if (!_log.isDebugEnabled()) {
			return;
		}

		_log.debug(
			"Layout export succeeded for group " +
				portletDataContext.getGroupId());
	}

	@Override
	public void onLayoutImportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception {

		_log.error(
			"Layout import failed for group " + portletDataContext.getGroupId(),
			throwable);
	}

	@Override
	public void onLayoutImportProcessFinished(
			PortletDataContext portletDataContext)
		throws Exception {
	}

	@Override
	public void onLayoutImportStarted(PortletDataContext portletDataContext)
		throws Exception {

		if (!_log.isDebugEnabled()) {
			return;
		}

		_log.debug(
			"Layout import started for group " +
				portletDataContext.getGroupId());
	}

	@Override
	public void onLayoutImportSucceeded(PortletDataContext portletDataContext)
		throws Exception {

		if (!_log.isDebugEnabled()) {
			return;
		}

		_log.debug(
			"Layout import succeeded for group " +
				portletDataContext.getGroupId());
	}

	@Override
	public void onLayoutLocalPublicationFailed(
			ExportImportConfiguration exportImportConfiguration,
			Throwable throwable)
		throws Exception {

		_log.error(
			"Layout publication failed for group " +
				exportImportConfiguration.getGroupId(),
			throwable);
	}

	@Override
	public void onLayoutLocalPublicationStarted(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {

		if (!_log.isDebugEnabled()) {
			return;
		}

		_log.debug(
			"Layout publication started for group " +
				exportImportConfiguration.getGroupId());
	}

	@Override
	public void onLayoutLocalPublicationSucceeded(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {

		if (!_log.isDebugEnabled()) {
			return;
		}

		_log.debug(
			"Layout publication succeeded for group " +
				exportImportConfiguration.getGroupId());
	}

	@Override
	public void onLayoutRemotePublicationFailed(
			ExportImportConfiguration exportImportConfiguration,
			Throwable throwable)
		throws Exception {

		_log.error(
			"Layout remote publication failed for group " +
				exportImportConfiguration.getGroupId(),
			throwable);
	}

	@Override
	public void onLayoutRemotePublicationStarted(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {

		if (!_log.isDebugEnabled()) {
			return;
		}

		_log.debug(
			"Layout publication started for group " +
				exportImportConfiguration.getGroupId());
	}

	@Override
	public void onLayoutRemotePublicationSucceeded(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {

		if (!_log.isDebugEnabled()) {
			return;
		}

		_log.debug(
			"Layout remote publication succeeded for group " +
				exportImportConfiguration.getGroupId());
	}

	@Override
	public void onPortletExportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception {

		_log.error(
			"Portlet export failed for portlet " +
				portletDataContext.getPortletId(),
			throwable);
	}

	@Override
	public void onPortletExportStarted(PortletDataContext portletDataContext)
		throws Exception {

		if (!_log.isDebugEnabled()) {
			return;
		}

		_log.debug(
			"Portlet export started for portlet " +
				portletDataContext.getPortletId());
	}

	@Override
	public void onPortletExportSucceeded(PortletDataContext portletDataContext)
		throws Exception {

		if (!_log.isDebugEnabled()) {
			return;
		}

		_log.debug(
			"Portlet export succeeded for portlet " +
				portletDataContext.getPortletId());
	}

	@Override
	public void onPortletImportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception {

		_log.error(
			"Portlet import failed for portlet " +
				portletDataContext.getPortletId(),
			throwable);
	}

	@Override
	public void onPortletImportProcessFinished(
			PortletDataContext portletDataContext)
		throws Exception {
	}

	@Override
	public void onPortletImportStarted(PortletDataContext portletDataContext)
		throws Exception {

		if (!_log.isDebugEnabled()) {
			return;
		}

		_log.debug(
			"Portlet import started for portlet " +
				portletDataContext.getPortletId());
	}

	@Override
	public void onPortletImportSucceeded(PortletDataContext portletDataContext)
		throws Exception {

		if (!_log.isDebugEnabled()) {
			return;
		}

		_log.debug(
			"Portlet import succeeded for portlet " +
				portletDataContext.getPortletId());
	}

	@Override
	public void onPortletPublicationFailed(
			ExportImportConfiguration exportImportConfiguration,
			Throwable throwable)
		throws Exception {

		String portletId = MapUtil.getString(
			exportImportConfiguration.getSettingsMap(), "portletId");

		_log.error(
			"Portlet publication failed for portlet " + portletId, throwable);
	}

	@Override
	public void onPortletPublicationStarted(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {

		if (!_log.isDebugEnabled()) {
			return;
		}

		String portletId = MapUtil.getString(
			exportImportConfiguration.getSettingsMap(), "portletId");

		_log.debug("Portlet publication started for portlet " + portletId);
	}

	@Override
	public void onPortletPublicationSucceeded(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {

		if (!_log.isDebugEnabled()) {
			return;
		}

		String portletId = MapUtil.getString(
			exportImportConfiguration.getSettingsMap(), "portletId");

		_log.debug("Portlet publication succeeded for portlet " + portletId);
	}

	@Override
	public void onStagedModelExportFailed(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			Throwable throwable)
		throws Exception {

		_log.error(
			"Staged model " + getStagedModelLogFragment(stagedModel) +
				" export failed",
			throwable);
	}

	@Override
	public void onStagedModelExportStarted(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {

		if (!_log.isDebugEnabled()) {
			return;
		}

		_log.debug(
			"Staged model " + getStagedModelLogFragment(stagedModel) +
				" export started");
	}

	@Override
	public void onStagedModelExportSucceeded(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {

		if (!_log.isDebugEnabled()) {
			return;
		}

		_log.debug(
			"Staged model " + getStagedModelLogFragment(stagedModel) +
				" export succeeded");
	}

	@Override
	public void onStagedModelImportFailed(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			Throwable throwable)
		throws Exception {

		_log.error(
			"Staged model " + getStagedModelLogFragment(stagedModel) +
				" import failed",
			throwable);
	}

	@Override
	public void onStagedModelImportStarted(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {

		if (!_log.isDebugEnabled()) {
			return;
		}

		_log.debug(
			"Staged model " + getStagedModelLogFragment(stagedModel) +
				" import started");
	}

	@Override
	public void onStagedModelImportSucceeded(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {

		if (!_log.isDebugEnabled()) {
			return;
		}

		_log.debug(
			"Staged model " + getStagedModelLogFragment(stagedModel) +
				" import succeeded");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LoggerExportImportLifecycleListener.class);

}
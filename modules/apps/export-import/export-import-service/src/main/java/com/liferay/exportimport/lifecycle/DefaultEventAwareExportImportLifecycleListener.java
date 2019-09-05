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

package com.liferay.exportimport.lifecycle;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lifecycle.EventAwareExportImportLifecycleListener;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleEvent;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleListener;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.util.TransientValue;

import java.io.Serializable;

import java.util.List;

/**
 * @author Daniel Kocsis
 */
public class DefaultEventAwareExportImportLifecycleListener
	implements ExportImportLifecycleListener {

	public DefaultEventAwareExportImportLifecycleListener(
		EventAwareExportImportLifecycleListener lifecycleListener) {

		_lifecycleListener = lifecycleListener;
	}

	@Override
	public boolean isParallel() {
		return _lifecycleListener.isParallel();
	}

	@Override
	public void onExportImportLifecycleEvent(
			ExportImportLifecycleEvent exportImportLifecycleEvent)
		throws Exception {

		_lifecycleListener.onExportImportLifecycleEvent(
			exportImportLifecycleEvent);

		callEventHandlers(exportImportLifecycleEvent);
	}

	protected void callEventHandlers(
			ExportImportLifecycleEvent exportImportLifecycleEvent)
		throws Exception {

		List<Serializable> attributes =
			exportImportLifecycleEvent.getAttributes();

		int code = exportImportLifecycleEvent.getCode();
		int processFlag = exportImportLifecycleEvent.getProcessFlag();

		if (code == ExportImportLifecycleConstants.EVENT_LAYOUT_EXPORT_FAILED) {
			onLayoutExportFailed(
				getPortletDataContextAttribute(attributes),
				getThrowableAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_LAYOUT_EXPORT_STARTED) {

			onLayoutExportStarted(getPortletDataContextAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_LAYOUT_EXPORT_SUCCEEDED) {

			onLayoutExportSucceeded(getPortletDataContextAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_FAILED) {

			onLayoutImportFailed(
				getPortletDataContextAttribute(attributes),
				getThrowableAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_LAYOUT_IMPORT_STARTED) {

			onLayoutImportStarted(getPortletDataContextAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_LAYOUT_IMPORT_SUCCEEDED) {

			if ((processFlag ==
					ExportImportLifecycleConstants.
						PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS) ||
				(processFlag ==
					ExportImportLifecycleConstants.
						PROCESS_FLAG_LAYOUT_STAGING_IN_PROCESS)) {

				onLayoutImportProcessFinished(
					getPortletDataContextAttribute(attributes));
			}
			else {
				onLayoutImportSucceeded(
					getPortletDataContextAttribute(attributes));
			}
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PORTLET_EXPORT_FAILED) {

			onPortletExportFailed(
				getPortletDataContextAttribute(attributes),
				getThrowableAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PORTLET_EXPORT_STARTED) {

			onPortletExportStarted(getPortletDataContextAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PORTLET_EXPORT_SUCCEEDED) {

			onPortletExportSucceeded(
				getPortletDataContextAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PORTLET_IMPORT_FAILED) {

			onPortletImportFailed(
				getPortletDataContextAttribute(attributes),
				getThrowableAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PORTLET_IMPORT_STARTED) {

			onPortletImportStarted(getPortletDataContextAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PORTLET_IMPORT_SUCCEEDED) {

			if ((processFlag ==
					ExportImportLifecycleConstants.
						PROCESS_FLAG_PORTLET_IMPORT_IN_PROCESS) ||
				(processFlag ==
					ExportImportLifecycleConstants.
						PROCESS_FLAG_PORTLET_STAGING_IN_PROCESS)) {

				onPortletImportProcessFinished(
					getPortletDataContextAttribute(attributes));
			}
			else {
				onPortletImportSucceeded(
					getPortletDataContextAttribute(attributes));
			}
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PUBLICATION_LAYOUT_LOCAL_FAILED) {

			onLayoutLocalPublicationFailed(
				getExportImportConfigurationAttribute(attributes),
				getThrowableAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PUBLICATION_LAYOUT_LOCAL_STARTED) {

			onLayoutLocalPublicationStarted(
				getExportImportConfigurationAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PUBLICATION_LAYOUT_LOCAL_SUCCEEDED) {

			onLayoutLocalPublicationSucceeded(
				getExportImportConfigurationAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PUBLICATION_LAYOUT_REMOTE_FAILED) {

			onLayoutRemotePublicationFailed(
				getExportImportConfigurationAttribute(attributes),
				getThrowableAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PUBLICATION_LAYOUT_REMOTE_STARTED) {

			onLayoutRemotePublicationStarted(
				getExportImportConfigurationAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PUBLICATION_LAYOUT_REMOTE_SUCCEEDED) {

			onLayoutRemotePublicationSucceeded(
				getExportImportConfigurationAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PUBLICATION_PORTLET_LOCAL_FAILED) {

			onPortletPublicationFailed(
				getExportImportConfigurationAttribute(attributes),
				getThrowableAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PUBLICATION_PORTLET_LOCAL_STARTED) {

			onPortletPublicationStarted(
				getExportImportConfigurationAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_PUBLICATION_PORTLET_LOCAL_SUCCEEDED) {

			onPortletPublicationSucceeded(
				getExportImportConfigurationAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_STAGED_MODEL_EXPORT_FAILED) {

			onStagedModelExportFailed(
				getPortletDataContextAttribute(attributes),
				getStagedModelAttribute(attributes),
				getThrowableAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_STAGED_MODEL_EXPORT_STARTED) {

			onStagedModelExportStarted(
				getPortletDataContextAttribute(attributes),
				getStagedModelAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_STAGED_MODEL_EXPORT_SUCCEEDED) {

			onStagedModelExportSucceeded(
				getPortletDataContextAttribute(attributes),
				getStagedModelAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_STAGED_MODEL_IMPORT_FAILED) {

			onStagedModelImportFailed(
				getPortletDataContextAttribute(attributes),
				getStagedModelAttribute(attributes),
				getThrowableAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_STAGED_MODEL_IMPORT_STARTED) {

			onStagedModelImportStarted(
				getPortletDataContextAttribute(attributes),
				getStagedModelAttribute(attributes));
		}
		else if (code ==
					ExportImportLifecycleConstants.
						EVENT_STAGED_MODEL_IMPORT_SUCCEEDED) {

			onStagedModelImportSucceeded(
				getPortletDataContextAttribute(attributes),
				getStagedModelAttribute(attributes));
		}
	}

	protected <T> T getAttributeByType(
		List<Serializable> attributes, Class<T> clazz) {

		for (Serializable attribute : attributes) {
			if (clazz.isInstance(attribute)) {
				return clazz.cast(attribute);
			}
		}

		return null;
	}

	protected ExportImportConfiguration getExportImportConfigurationAttribute(
		List<Serializable> attributes) {

		return getAttributeByType(attributes, ExportImportConfiguration.class);
	}

	protected PortletDataContext getPortletDataContextAttribute(
		List<Serializable> attributes) {

		return getAttributeByType(attributes, PortletDataContext.class);
	}

	protected StagedModel getStagedModelAttribute(
		List<Serializable> attributes) {

		TransientValue<Object> transientValue = getAttributeByType(
			attributes, TransientValue.class);

		Object value = transientValue.getValue();

		if (value instanceof StagedModel) {
			return (StagedModel)value;
		}

		return null;
	}

	protected Throwable getThrowableAttribute(List<Serializable> attributes) {
		return getAttributeByType(attributes, Throwable.class);
	}

	protected void onLayoutExportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception {

		_lifecycleListener.onLayoutExportFailed(portletDataContext, throwable);
	}

	protected void onLayoutExportStarted(PortletDataContext portletDataContext)
		throws Exception {

		_lifecycleListener.onLayoutExportStarted(portletDataContext);
	}

	protected void onLayoutExportSucceeded(
			PortletDataContext portletDataContext)
		throws Exception {

		_lifecycleListener.onLayoutExportSucceeded(portletDataContext);
	}

	protected void onLayoutImportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception {

		_lifecycleListener.onLayoutImportFailed(portletDataContext, throwable);
	}

	protected void onLayoutImportProcessFinished(
			PortletDataContext portletDataContext)
		throws Exception {

		_lifecycleListener.onLayoutImportProcessFinished(portletDataContext);
	}

	protected void onLayoutImportStarted(PortletDataContext portletDataContext)
		throws Exception {

		_lifecycleListener.onLayoutImportStarted(portletDataContext);
	}

	protected void onLayoutImportSucceeded(
			PortletDataContext portletDataContext)
		throws Exception {

		_lifecycleListener.onLayoutImportSucceeded(portletDataContext);
	}

	protected void onLayoutLocalPublicationFailed(
			ExportImportConfiguration exportImportConfiguration,
			Throwable throwable)
		throws Exception {

		_lifecycleListener.onLayoutLocalPublicationFailed(
			exportImportConfiguration, throwable);
	}

	protected void onLayoutLocalPublicationStarted(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {

		_lifecycleListener.onLayoutLocalPublicationStarted(
			exportImportConfiguration);
	}

	protected void onLayoutLocalPublicationSucceeded(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {

		_lifecycleListener.onLayoutLocalPublicationSucceeded(
			exportImportConfiguration);
	}

	protected void onLayoutRemotePublicationFailed(
			ExportImportConfiguration exportImportConfiguration,
			Throwable throwable)
		throws Exception {

		_lifecycleListener.onLayoutRemotePublicationFailed(
			exportImportConfiguration, throwable);
	}

	protected void onLayoutRemotePublicationStarted(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {

		_lifecycleListener.onLayoutRemotePublicationStarted(
			exportImportConfiguration);
	}

	protected void onLayoutRemotePublicationSucceeded(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {

		_lifecycleListener.onLayoutRemotePublicationSucceeded(
			exportImportConfiguration);
	}

	protected void onPortletExportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception {

		_lifecycleListener.onPortletExportFailed(portletDataContext, throwable);
	}

	protected void onPortletExportStarted(PortletDataContext portletDataContext)
		throws Exception {

		_lifecycleListener.onPortletExportStarted(portletDataContext);
	}

	protected void onPortletExportSucceeded(
			PortletDataContext portletDataContext)
		throws Exception {

		_lifecycleListener.onPortletExportSucceeded(portletDataContext);
	}

	protected void onPortletImportFailed(
			PortletDataContext portletDataContext, Throwable throwable)
		throws Exception {

		_lifecycleListener.onPortletImportFailed(portletDataContext, throwable);
	}

	protected void onPortletImportProcessFinished(
			PortletDataContext portletDataContext)
		throws Exception {

		_lifecycleListener.onPortletImportProcessFinished(portletDataContext);
	}

	protected void onPortletImportStarted(PortletDataContext portletDataContext)
		throws Exception {

		_lifecycleListener.onPortletImportStarted(portletDataContext);
	}

	protected void onPortletImportSucceeded(
			PortletDataContext portletDataContext)
		throws Exception {

		_lifecycleListener.onPortletImportSucceeded(portletDataContext);
	}

	protected void onPortletPublicationFailed(
			ExportImportConfiguration exportImportConfiguration,
			Throwable throwable)
		throws Exception {

		_lifecycleListener.onPortletPublicationFailed(
			exportImportConfiguration, throwable);
	}

	protected void onPortletPublicationStarted(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {

		_lifecycleListener.onPortletPublicationStarted(
			exportImportConfiguration);
	}

	protected void onPortletPublicationSucceeded(
			ExportImportConfiguration exportImportConfiguration)
		throws Exception {

		_lifecycleListener.onPortletPublicationSucceeded(
			exportImportConfiguration);
	}

	protected void onStagedModelExportFailed(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			Throwable throwable)
		throws Exception {

		_lifecycleListener.onStagedModelExportFailed(
			portletDataContext, stagedModel, throwable);
	}

	protected void onStagedModelExportStarted(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {

		_lifecycleListener.onStagedModelExportStarted(
			portletDataContext, stagedModel);
	}

	protected void onStagedModelExportSucceeded(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {

		_lifecycleListener.onStagedModelExportSucceeded(
			portletDataContext, stagedModel);
	}

	protected void onStagedModelImportFailed(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			Throwable throwable)
		throws Exception {

		_lifecycleListener.onStagedModelImportFailed(
			portletDataContext, stagedModel, throwable);
	}

	protected void onStagedModelImportStarted(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {

		_lifecycleListener.onStagedModelImportStarted(
			portletDataContext, stagedModel);
	}

	protected void onStagedModelImportSucceeded(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {

		_lifecycleListener.onStagedModelImportSucceeded(
			portletDataContext, stagedModel);
	}

	private final EventAwareExportImportLifecycleListener _lifecycleListener;

}
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

import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_LAYOUT_EXPORT_FAILED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_LAYOUT_EXPORT_STARTED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_LAYOUT_EXPORT_SUCCEEDED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_FAILED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_STARTED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_SUCCEEDED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_FAILED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_STARTED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_SUCCEEDED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_IMPORT_FAILED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_IMPORT_STARTED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_IMPORT_SUCCEEDED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PUBLICATION_LAYOUT_LOCAL_FAILED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PUBLICATION_LAYOUT_LOCAL_STARTED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PUBLICATION_LAYOUT_LOCAL_SUCCEEDED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PUBLICATION_LAYOUT_REMOTE_FAILED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PUBLICATION_LAYOUT_REMOTE_STARTED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PUBLICATION_LAYOUT_REMOTE_SUCCEEDED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PUBLICATION_PORTLET_LOCAL_FAILED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PUBLICATION_PORTLET_LOCAL_STARTED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PUBLICATION_PORTLET_LOCAL_SUCCEEDED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.PROCESS_FLAG_LAYOUT_EXPORT_IN_PROCESS;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.PROCESS_FLAG_LAYOUT_STAGING_IN_PROCESS;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.PROCESS_FLAG_PORTLET_EXPORT_IN_PROCESS;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.PROCESS_FLAG_PORTLET_IMPORT_IN_PROCESS;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.PROCESS_FLAG_PORTLET_STAGING_IN_PROCESS;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleEvent;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleListener;
import com.liferay.exportimport.kernel.lifecycle.ProcessAwareExportImportLifecycleListener;

import java.io.Serializable;

import java.util.List;

/**
 * @author Daniel Kocsis
 */
@ProviderType
public class DefaultProcessAwareExportImportLifecycleListener
	implements ExportImportLifecycleListener {

	public DefaultProcessAwareExportImportLifecycleListener(
		ProcessAwareExportImportLifecycleListener lifecycleListener) {

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

		callProcessHandlers(exportImportLifecycleEvent);
	}

	protected void callProcessHandlers(
			ExportImportLifecycleEvent exportImportLifecycleEvent)
		throws Exception {

		int code = exportImportLifecycleEvent.getCode();
		int processFlag = exportImportLifecycleEvent.getProcessFlag();

		if (processFlag == PROCESS_FLAG_LAYOUT_EXPORT_IN_PROCESS) {
			if (code == EVENT_LAYOUT_EXPORT_FAILED) {
				onProcessFailed(exportImportLifecycleEvent.getAttributes());
			}
			else if (code == EVENT_LAYOUT_EXPORT_STARTED) {
				onProcessStarted(exportImportLifecycleEvent.getAttributes());
			}
			else if (code == EVENT_LAYOUT_EXPORT_SUCCEEDED) {
				onProcessSucceeded(exportImportLifecycleEvent.getAttributes());
			}
		}
		else if (processFlag == PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS) {
			if (code == EVENT_LAYOUT_IMPORT_FAILED) {
				onProcessFailed(exportImportLifecycleEvent.getAttributes());
			}
			else if (code == EVENT_LAYOUT_IMPORT_STARTED) {
				onProcessStarted(exportImportLifecycleEvent.getAttributes());
			}
			else if (code == EVENT_LAYOUT_IMPORT_SUCCEEDED) {
				onProcessSucceeded(exportImportLifecycleEvent.getAttributes());
			}
		}
		else if (processFlag == PROCESS_FLAG_LAYOUT_STAGING_IN_PROCESS) {
			if ((code == EVENT_PUBLICATION_LAYOUT_LOCAL_FAILED) ||
				(code == EVENT_PUBLICATION_LAYOUT_REMOTE_FAILED)) {

				onProcessFailed(exportImportLifecycleEvent.getAttributes());
			}
			else if ((code == EVENT_PUBLICATION_LAYOUT_LOCAL_STARTED) ||
					 (code == EVENT_PUBLICATION_LAYOUT_REMOTE_STARTED)) {

				onProcessStarted(exportImportLifecycleEvent.getAttributes());
			}
			else if ((code == EVENT_PUBLICATION_LAYOUT_LOCAL_SUCCEEDED) ||
					 (code == EVENT_PUBLICATION_LAYOUT_REMOTE_SUCCEEDED)) {

				onProcessSucceeded(exportImportLifecycleEvent.getAttributes());
			}
		}
		else if (processFlag == PROCESS_FLAG_PORTLET_EXPORT_IN_PROCESS) {
			if (code == EVENT_PORTLET_EXPORT_FAILED) {
				onProcessFailed(exportImportLifecycleEvent.getAttributes());
			}
			else if (code == EVENT_PORTLET_EXPORT_STARTED) {
				onProcessStarted(exportImportLifecycleEvent.getAttributes());
			}
			else if (code == EVENT_PORTLET_EXPORT_SUCCEEDED) {
				onProcessSucceeded(exportImportLifecycleEvent.getAttributes());
			}
		}
		else if (processFlag == PROCESS_FLAG_PORTLET_IMPORT_IN_PROCESS) {
			if (code == EVENT_PORTLET_IMPORT_FAILED) {
				onProcessFailed(exportImportLifecycleEvent.getAttributes());
			}
			else if (code == EVENT_PORTLET_IMPORT_STARTED) {
				onProcessStarted(exportImportLifecycleEvent.getAttributes());
			}
			else if (code == EVENT_PORTLET_IMPORT_SUCCEEDED) {
				onProcessSucceeded(exportImportLifecycleEvent.getAttributes());
			}
		}
		else if (processFlag == PROCESS_FLAG_PORTLET_STAGING_IN_PROCESS) {
			if (code == EVENT_PUBLICATION_PORTLET_LOCAL_FAILED) {
				onProcessFailed(exportImportLifecycleEvent.getAttributes());
			}
			else if (code == EVENT_PUBLICATION_PORTLET_LOCAL_STARTED) {
				onProcessStarted(exportImportLifecycleEvent.getAttributes());
			}
			else if (code == EVENT_PUBLICATION_PORTLET_LOCAL_SUCCEEDED) {
				onProcessSucceeded(exportImportLifecycleEvent.getAttributes());
			}
		}
	}

	protected void onProcessFailed(List<Serializable> attributes)
		throws Exception {

		_lifecycleListener.onProcessFailed(attributes);
	}

	protected void onProcessStarted(List<Serializable> attributes)
		throws Exception {

		_lifecycleListener.onProcessStarted(attributes);
	}

	protected void onProcessSucceeded(List<Serializable> attributes)
		throws Exception {

		_lifecycleListener.onProcessSucceeded(attributes);
	}

	private final ProcessAwareExportImportLifecycleListener _lifecycleListener;

}
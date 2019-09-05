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

import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleEvent;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleListener;
import com.liferay.exportimport.kernel.lifecycle.ProcessAwareExportImportLifecycleListener;

/**
 * @author Daniel Kocsis
 */
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

		if (processFlag ==
				ExportImportLifecycleConstants.
					PROCESS_FLAG_LAYOUT_EXPORT_IN_PROCESS) {

			if (code ==
					ExportImportLifecycleConstants.EVENT_LAYOUT_EXPORT_FAILED) {

				onProcessFailed(exportImportLifecycleEvent);
			}
			else if (code ==
						ExportImportLifecycleConstants.
							EVENT_LAYOUT_EXPORT_STARTED) {

				onProcessStarted(exportImportLifecycleEvent);
			}
			else if (code ==
						ExportImportLifecycleConstants.
							EVENT_LAYOUT_EXPORT_SUCCEEDED) {

				onProcessSucceeded(exportImportLifecycleEvent);
			}
		}
		else if (processFlag ==
					ExportImportLifecycleConstants.
						PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS) {

			if (code ==
					ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_FAILED) {

				onProcessFailed(exportImportLifecycleEvent);
			}
			else if (code ==
						ExportImportLifecycleConstants.
							EVENT_LAYOUT_IMPORT_STARTED) {

				onProcessStarted(exportImportLifecycleEvent);
			}
			else if (code ==
						ExportImportLifecycleConstants.
							EVENT_LAYOUT_IMPORT_SUCCEEDED) {

				onProcessSucceeded(exportImportLifecycleEvent);
			}
		}
		else if (processFlag ==
					ExportImportLifecycleConstants.
						PROCESS_FLAG_LAYOUT_STAGING_IN_PROCESS) {

			if ((code ==
					ExportImportLifecycleConstants.
						EVENT_PUBLICATION_LAYOUT_LOCAL_FAILED) ||
				(code ==
					ExportImportLifecycleConstants.
						EVENT_PUBLICATION_LAYOUT_REMOTE_FAILED)) {

				onProcessFailed(exportImportLifecycleEvent);
			}
			else if ((code ==
						ExportImportLifecycleConstants.
							EVENT_PUBLICATION_LAYOUT_LOCAL_STARTED) ||
					 (code ==
						 ExportImportLifecycleConstants.
							 EVENT_PUBLICATION_LAYOUT_REMOTE_STARTED)) {

				onProcessStarted(exportImportLifecycleEvent);
			}
			else if ((code ==
						ExportImportLifecycleConstants.
							EVENT_PUBLICATION_LAYOUT_LOCAL_SUCCEEDED) ||
					 (code ==
						 ExportImportLifecycleConstants.
							 EVENT_PUBLICATION_LAYOUT_REMOTE_SUCCEEDED)) {

				onProcessSucceeded(exportImportLifecycleEvent);
			}
		}
		else if (processFlag ==
					ExportImportLifecycleConstants.
						PROCESS_FLAG_PORTLET_EXPORT_IN_PROCESS) {

			if (code ==
					ExportImportLifecycleConstants.
						EVENT_PORTLET_EXPORT_FAILED) {

				onProcessFailed(exportImportLifecycleEvent);
			}
			else if (code ==
						ExportImportLifecycleConstants.
							EVENT_PORTLET_EXPORT_STARTED) {

				onProcessStarted(exportImportLifecycleEvent);
			}
			else if (code ==
						ExportImportLifecycleConstants.
							EVENT_PORTLET_EXPORT_SUCCEEDED) {

				onProcessSucceeded(exportImportLifecycleEvent);
			}
		}
		else if (processFlag ==
					ExportImportLifecycleConstants.
						PROCESS_FLAG_PORTLET_IMPORT_IN_PROCESS) {

			if (code ==
					ExportImportLifecycleConstants.
						EVENT_PORTLET_IMPORT_FAILED) {

				onProcessFailed(exportImportLifecycleEvent);
			}
			else if (code ==
						ExportImportLifecycleConstants.
							EVENT_PORTLET_IMPORT_STARTED) {

				onProcessStarted(exportImportLifecycleEvent);
			}
			else if (code ==
						ExportImportLifecycleConstants.
							EVENT_PORTLET_IMPORT_SUCCEEDED) {

				onProcessSucceeded(exportImportLifecycleEvent);
			}
		}
		else if (processFlag ==
					ExportImportLifecycleConstants.
						PROCESS_FLAG_PORTLET_STAGING_IN_PROCESS) {

			if (code ==
					ExportImportLifecycleConstants.
						EVENT_PUBLICATION_PORTLET_LOCAL_FAILED) {

				onProcessFailed(exportImportLifecycleEvent);
			}
			else if (code ==
						ExportImportLifecycleConstants.
							EVENT_PUBLICATION_PORTLET_LOCAL_STARTED) {

				onProcessStarted(exportImportLifecycleEvent);
			}
			else if (code ==
						ExportImportLifecycleConstants.
							EVENT_PUBLICATION_PORTLET_LOCAL_SUCCEEDED) {

				onProcessSucceeded(exportImportLifecycleEvent);
			}
		}
	}

	protected void onProcessFailed(
			ExportImportLifecycleEvent exportImportLifecycleEvent)
		throws Exception {

		_lifecycleListener.onProcessFailed(exportImportLifecycleEvent);
	}

	protected void onProcessStarted(
			ExportImportLifecycleEvent exportImportLifecycleEvent)
		throws Exception {

		_lifecycleListener.onProcessStarted(exportImportLifecycleEvent);
	}

	protected void onProcessSucceeded(
			ExportImportLifecycleEvent exportImportLifecycleEvent)
		throws Exception {

		_lifecycleListener.onProcessSucceeded(exportImportLifecycleEvent);
	}

	private final ProcessAwareExportImportLifecycleListener _lifecycleListener;

}
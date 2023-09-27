/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.lifecycle;

import com.liferay.exportimport.kernel.lifecycle.EventAwareExportImportLifecycleListener;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleListener;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleListenerFactory;
import com.liferay.exportimport.kernel.lifecycle.ProcessAwareExportImportLifecycleListener;

import org.osgi.service.component.annotations.Component;

/**
 * @author Daniel Kocsis
 */
@Component(service = ExportImportLifecycleListenerFactory.class)
public class DefaultExportImportLifecycleListernerFactory
	implements ExportImportLifecycleListenerFactory {

	@Override
	public ExportImportLifecycleListener create(
		EventAwareExportImportLifecycleListener lifecycleListener) {

		return new DefaultEventAwareExportImportLifecycleListener(
			lifecycleListener);
	}

	@Override
	public ExportImportLifecycleListener create(
		ProcessAwareExportImportLifecycleListener lifecycleListener) {

		return new DefaultProcessAwareExportImportLifecycleListener(
			lifecycleListener);
	}

}
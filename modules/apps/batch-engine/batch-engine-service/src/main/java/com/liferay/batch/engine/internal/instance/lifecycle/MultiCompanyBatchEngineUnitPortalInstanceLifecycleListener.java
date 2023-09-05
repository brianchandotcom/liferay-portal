/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.instance.lifecycle;

import com.liferay.batch.engine.internal.unit.MultiCompanyBatchEngineUnitProcessor;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;

import java.util.concurrent.CompletableFuture;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class MultiCompanyBatchEngineUnitPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		CompletableFuture<Void> completableFuture =
			_multiCompanyBatchEngineUnitProcessor.processBatchEngineUnits(
				company);

		completableFuture.get();
	}

	@Override
	public void portalInstanceUnregistered(Company company) {
		_multiCompanyBatchEngineUnitProcessor.unregister(company);
	}

	@Reference
	private MultiCompanyBatchEngineUnitProcessor
		_multiCompanyBatchEngineUnitProcessor;

}
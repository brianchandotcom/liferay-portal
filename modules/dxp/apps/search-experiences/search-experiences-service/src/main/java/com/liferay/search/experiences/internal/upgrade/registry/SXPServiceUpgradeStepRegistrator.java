/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.upgrade.registry;

import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.BaseExternalReferenceCodeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.search.experiences.internal.model.listener.CompanyModelListener;
import com.liferay.search.experiences.internal.upgrade.v1_0_0.SXPElementUpgradeProcess;
import com.liferay.search.experiences.service.SXPElementLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	enabled = true, immediate = true, service = UpgradeStepRegistrator.class
)
public class SXPServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialDeploymentUpgradeSteps(
			new SXPElementUpgradeProcess(
				_companyLocalService, _companyModelListener,
				_sxpElementLocalService));

		registry.register(
			"1.0.0", "1.1.0",
			UpgradeProcessFactory.addColumns(
				"SXPElement", "key_ VARCHAR(75) null",
				"version VARCHAR(75) null"),
			UpgradeProcessFactory.addColumns(
				"SXPBlueprint", "key_ VARCHAR(75) null",
				"version VARCHAR(75) null"));

		registry.register(
			"1.1.0", "1.2.0",
			new BaseExternalReferenceCodeUpgradeProcess() {

				@Override
				protected String[][] getTableAndPrimaryKeyColumnNames() {
					return new String[][] {
						{"SXPBlueprint", "sxpBlueprintId"},
						{"SXPElement", "sxpElementId"}
					};
				}

			});

		registry.register(
			"1.2.0", "1.3.0",
			new com.liferay.search.experiences.internal.upgrade.v1_3_0.
				SXPBlueprintUpgradeProcess());

		registry.register(
			"1.3.0", "1.3.1",
			new com.liferay.search.experiences.internal.upgrade.v1_3_1.
				SXPBlueprintUpgradeProcess());
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CompanyModelListener _companyModelListener;

	@Reference
	private SXPElementLocalService _sxpElementLocalService;

}
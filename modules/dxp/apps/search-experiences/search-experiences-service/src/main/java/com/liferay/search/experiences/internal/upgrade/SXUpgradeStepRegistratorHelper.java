package com.liferay.search.experiences.internal.upgrade;

import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.search.experiences.internal.model.listener.CompanyModelListener;
import com.liferay.search.experiences.internal.search.SXPElementSearchRegistrar;
import com.liferay.search.experiences.service.SXPElementLocalService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = SXUpgradeStepRegistratorHelper.class)
public class SXUpgradeStepRegistratorHelper {

	public void registerInitialUpgradeSteps(UpgradeStepRegistrator.Registry registry) {

		registry.registerInitialUpgradeSteps(
			new com.liferay.search.experiences.internal.upgrade.v1_0_0.
				SXPElementUpgradeProcess(
				_companyLocalService, _companyModelListener,
				_sxpElementLocalService));
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CompanyModelListener _companyModelListener;

	@Reference
	private SXPElementLocalService _sxpElementLocalService;

	@Reference
	private SXPElementSearchRegistrar _sxpElementSearchRegistrar;
}
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

package com.liferay.search.experiences.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.search.experiences.service.SXPElementLocalService;
import com.liferay.search.experiences.service.persistence.impl.constants.SXPPersistenceConstants;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(enabled = true, service = {})
public class SXPElementUpgradeProcess {

	@Activate
	protected void activate() throws PortalException {
		if (_createData) {
			_companyLocalService.forEachCompany(
				company -> SXPElementDataUtil.addSXPElements(
					_sxpElementLocalService, company));
		}
	}

	@Reference(
		target = "(release.bundle.symbolic.name=" + SXPPersistenceConstants.BUNDLE_SYMBOLIC_NAME + ")",
		unbind = "-"
	)
	protected void setRelease(Release release, Map<String, Object> properties) {
		if (Objects.equals(release.getSchemaVersion(), "1.0.0") &&
			GetterUtil.getBoolean(properties.get("release.new"))) {

			_createData = true;
		}
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	private boolean _createData;

	@Reference
	private SXPElementLocalService _sxpElementLocalService;

}
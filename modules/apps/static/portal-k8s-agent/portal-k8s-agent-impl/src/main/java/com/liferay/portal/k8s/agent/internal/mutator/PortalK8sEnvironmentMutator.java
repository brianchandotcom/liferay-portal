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

package com.liferay.portal.k8s.agent.internal.mutator;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.k8s.agent.mutator.PortalK8sConfigurationPropertiesMutator;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	immediate = true, service = PortalK8sConfigurationPropertiesMutator.class
)
public class PortalK8sEnvironmentMutator
	implements PortalK8sConfigurationPropertiesMutator {

	@Activate
	public PortalK8sEnvironmentMutator(
		@Reference CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Override
	public void mutateConfigurationProperties(
		Map<String, String> annotations, Map<String, String> labels,
		Dictionary<String, Object> properties) {

		String environment = GetterUtil.getString(
			properties.get("lxc.environment"), "default");

		if (Validator.isNotNull(environment)) {
			try {
				Company company = _getCompanyIdByEnvironment(environment);

				properties.put("companyId", company.getCompanyId());
			}
			catch (PortalException portalException) {
				_log.error(portalException);
			}
		}
	}

	private Company _getCompanyIdByEnvironment(String environment)
		throws PortalException {

		if (Objects.equals("default", environment)) {
			return _companyLocalService.getCompanyByWebId(
				PropsValues.COMPANY_DEFAULT_WEB_ID);
		}

		DynamicQuery dynamicQuery = _companyLocalService.dynamicQuery();

		Property webIdProperty = PropertyFactoryUtil.forName("webId");

		// TODO: rationalize this against the scheme for mapping environments to
		// virtual instances

		String webId = environment;

		dynamicQuery.add(webIdProperty.eq(webId));

		List<Company> companies = _companyLocalService.dynamicQuery(
			dynamicQuery);

		if (!companies.isEmpty()) {
			return companies.get(0);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Could not locate a company by webId: ", webId,
					". Using the default."));
		}

		return _companyLocalService.getCompanyByWebId(
			PropsValues.COMPANY_DEFAULT_WEB_ID);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalK8sEnvironmentMutator.class);

	private final CompanyLocalService _companyLocalService;

}
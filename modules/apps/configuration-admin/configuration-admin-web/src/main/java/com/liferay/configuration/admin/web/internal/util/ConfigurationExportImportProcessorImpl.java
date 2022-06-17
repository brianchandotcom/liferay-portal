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

package com.liferay.configuration.admin.web.internal.util;

import com.liferay.configuration.admin.exportimport.ConfigurationExportImportProcessor;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.Dictionary;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = ConfigurationExportImportProcessor.class)
public class ConfigurationExportImportProcessorImpl
	implements ConfigurationExportImportProcessor {

	public boolean prepareForExport(
			String pid, Dictionary<String, Object> properties)
		throws PortalException {

		for (ExtendedObjectClassDefinition.Scope scope :
				ExtendedObjectClassDefinition.Scope.values()) {

			String propertyKey = scope.getPropertyKey();

			if (propertyKey == null) {
				continue;
			}

			Object internalIdentifier = properties.remove(propertyKey);

			if (internalIdentifier == null) {
				continue;
			}

			Serializable portableIdentifier = _getPortableIdentifier(
				scope, (Serializable)internalIdentifier);

			if (portableIdentifier != null) {
				if (_log.isInfoEnabled()) {
					_log.info(
						String.format(
							"For pid %s: replacing internal identifier %s " +
								"with portable identifier %s",
							pid, internalIdentifier, portableIdentifier));
				}

				properties.put(
					scope.getPortablePropertyKey(), portableIdentifier);

				return true;
			}
		}

		return false;
	}

	public boolean prepareForImport(
			String pid, Dictionary<String, Object> properties)
		throws PortalException {

		for (ExtendedObjectClassDefinition.Scope scope :
				ExtendedObjectClassDefinition.Scope.values()) {

			String portablePropertyKey = scope.getPortablePropertyKey();

			if (portablePropertyKey == null) {
				continue;
			}

			Object portableIdentifier = properties.remove(portablePropertyKey);

			if (portableIdentifier == null) {
				continue;
			}

			Serializable internalIdentifier = _getInternalIdentifier(
				scope, (Serializable)portableIdentifier);

			if (internalIdentifier != null) {
				if (_log.isInfoEnabled()) {
					_log.info(
						String.format(
							"For pid %s: replacing portable identifier %s " +
								"with internal identifier %s",
							pid, portableIdentifier, internalIdentifier));
				}

				properties.put(scope.getPropertyKey(), internalIdentifier);

				return true;
			}
		}

		return false;
	}

	private Serializable _getInternalIdentifier(
			ExtendedObjectClassDefinition.Scope scope,
			Serializable portableIdentifier)
		throws PortalException {

		if (scope.equals(ExtendedObjectClassDefinition.Scope.COMPANY)) {
			Company company = _companyLocalService.getCompanyByWebId(
				(String)portableIdentifier);

			return company.getCompanyId();
		}

		if (scope.equals(ExtendedObjectClassDefinition.Scope.GROUP)) {
			String[] parts = StringUtil.split(
				(String)portableIdentifier, _SEPARATOR);

			String webId = parts[0];

			long companyId = GetterUtil.getLong(
				_getInternalIdentifier(
					ExtendedObjectClassDefinition.Scope.COMPANY, webId));

			if (companyId == 0L) {
				return null;
			}

			String groupKey = parts[1];

			Group group = _groupLocalService.getGroup(companyId, groupKey);

			return group.getGroupId();
		}

		return null;
	}

	private Serializable _getPortableIdentifier(
			ExtendedObjectClassDefinition.Scope scope, Serializable scopePK)
		throws PortalException {

		if (scope.equals(ExtendedObjectClassDefinition.Scope.COMPANY)) {
			Company company = _companyLocalService.getCompany((long)scopePK);

			return company.getWebId();
		}

		if (scope.equals(ExtendedObjectClassDefinition.Scope.GROUP)) {
			Group group = _groupLocalService.getGroup((long)scopePK);

			return StringBundler.concat(
				_getPortableIdentifier(
					ExtendedObjectClassDefinition.Scope.COMPANY,
					group.getCompanyId()),
				_SEPARATOR, group.getGroupKey());
		}

		return null;
	}

	private static final String _SEPARATOR = "--";

	private static final Log _log = LogFactoryUtil.getLog(
		ConfigurationExportImportProcessorImpl.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}
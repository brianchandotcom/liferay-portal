/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.preview.pdf.internal.configuration.admin.service;

import com.liferay.document.library.preview.pdf.internal.configuration.PDFPreviewConfiguration;
import com.liferay.document.library.preview.pdf.internal.configuration.admin.service.helper.PDFPreviewConfigurationHelper;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Constants;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(
	property = Constants.SERVICE_PID + "=com.liferay.document.library.preview.pdf.internal.configuration.PDFPreviewConfiguration.scoped",
	service = ManagedServiceFactory.class
)
public class PDFPreviewManagedServiceFactory implements ManagedServiceFactory {

	@Override
	public void deleted(String pid) {
		_unmapPid(pid);
	}

	@Override
	public String getName() {
		return "com.liferay.document.library.preview.pdf.internal." +
			"configuration.PDFPreviewConfiguration.scoped";
	}

	@Override
	public void updated(String pid, Dictionary<String, ?> dictionary)
		throws ConfigurationException {

		_unmapPid(pid);

		long companyId = GetterUtil.getLong(
			dictionary.get("companyId"), CompanyConstants.SYSTEM);

		if (companyId != CompanyConstants.SYSTEM) {
			_updateCompanyConfiguration(companyId, pid, dictionary);
		}

		long groupId = GetterUtil.getLong(
			dictionary.get("groupId"), GroupConstants.DEFAULT_PARENT_GROUP_ID);

		if (groupId != GroupConstants.DEFAULT_PARENT_GROUP_ID) {
			_updateGroupConfiguration(groupId, pid, dictionary);
		}
	}

	private void _unmapPid(String pid) {
		Long companyId = _companyIds.remove(pid);

		if (companyId != null) {
			_pdfPreviewConfigurationHelper.removeCompanyConfigurationBeans(
				companyId);
		}

		Long groupId = _groupIds.remove(pid);

		if (groupId != null) {
			_pdfPreviewConfigurationHelper.removeGroupConfigurationBeans(
				groupId);
		}
	}

	private void _updateCompanyConfiguration(
		long companyId, String pid, Dictionary<String, ?> dictionary) {

		_pdfPreviewConfigurationHelper.updateCompanyConfigurationBeans(
			companyId,
			ConfigurableUtil.createConfigurable(
				PDFPreviewConfiguration.class, dictionary));
		_companyIds.put(pid, companyId);
	}

	private void _updateGroupConfiguration(
		long groupId, String pid, Dictionary<String, ?> dictionary) {

		_pdfPreviewConfigurationHelper.updateGroupConfigurationBeans(
			groupId,
			ConfigurableUtil.createConfigurable(
				PDFPreviewConfiguration.class, dictionary));
		_groupIds.put(pid, groupId);
	}

	private final Map<String, Long> _companyIds = new ConcurrentHashMap<>();
	private final Map<String, Long> _groupIds = new ConcurrentHashMap<>();

	@Reference
	private PDFPreviewConfigurationHelper _pdfPreviewConfigurationHelper;

}
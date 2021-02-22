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

package com.liferay.commerce.salesforce.connector.talend.job.deployer.internal;

import com.liferay.commerce.talend.job.deployer.TalendJobFileProvider;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.repository.DispatchFileRepository;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.File;
import java.io.InputStream;

import java.net.URL;

import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(
	enabled = false, immediate = true,
	service = CommerceSalesforceConnectorTalendJobDeployer.class
)
public class CommerceSalesforceConnectorTalendJobDeployer {

	@Activate
	protected void activate() throws Exception {
		List<URL> jobFileURLs = _talendJobFileProvider.getJobFileURLs();

		for (URL jobFileURL : jobFileURLs) {
			String fileName = StringUtil.extractLast(
				jobFileURL.getFile(), StringPool.FORWARD_SLASH);

			try {
				_companyLocalService.forEachCompanyId(
					companyId -> _deployJob(companyId, fileName, jobFileURL));
			}
			catch (Exception exception) {
				_log.error("Unable to deploy job " + fileName, exception);
			}
		}
	}

	private void _deployJob(long companyId, String fileName, URL jobFileURL)
		throws Exception {

		long userId = _userLocalService.getDefaultUserId(companyId);

		DispatchTrigger dispatchTrigger =
			_dispatchTriggerLocalService.fetchDispatchTrigger(
				companyId, fileName);

		if (dispatchTrigger == null) {
			UnicodeProperties typeSettingsUnicodeProperties =
				_getDefaultTypeSettingsUnicodeProperties(
					jobFileURL.openStream());

			dispatchTrigger = _dispatchTriggerLocalService.addDispatchTrigger(
				userId, "talend", typeSettingsUnicodeProperties, fileName,
				false);
		}

		File tempFile = FileUtil.createTempFile(jobFileURL.openStream());

		_dispatchFileRepository.addFileEntry(
			userId, dispatchTrigger.getDispatchTriggerId(), fileName,
			tempFile.length(),
			MimeTypesUtil.getContentType(jobFileURL.getFile()),
			jobFileURL.openStream());
	}

	private UnicodeProperties _getDefaultTypeSettingsUnicodeProperties(
			InputStream inputStream)
		throws Exception {

		UnicodeProperties unicodeProperties = new UnicodeProperties(true);

		File tempFile = FileUtil.createTempFile(inputStream);
		File tempFolder = FileUtil.createTempFolder();

		FileUtil.unzip(tempFile, tempFolder);

		String[] defaultProperties = FileUtil.find(
			tempFolder.getAbsolutePath(), "**\\Default.properties", null);

		if (defaultProperties.length > 0) {
			unicodeProperties.fastLoad(FileUtil.read(defaultProperties[0]));
		}

		return unicodeProperties;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceSalesforceConnectorTalendJobDeployer.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DispatchFileRepository _dispatchFileRepository;

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	@Reference
	private TalendJobFileProvider _talendJobFileProvider;

	@Reference
	private UserLocalService _userLocalService;

}
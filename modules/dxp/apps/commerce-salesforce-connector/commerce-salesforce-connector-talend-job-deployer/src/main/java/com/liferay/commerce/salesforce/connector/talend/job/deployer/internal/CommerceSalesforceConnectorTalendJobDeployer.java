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
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Portal;
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
				long[] companyIds = _portal.getCompanyIds();

				for (long companyId : companyIds) {
					long userId = _userLocalService.getDefaultUserId(companyId);

					DispatchTrigger dispatchTrigger =
						_dispatchTriggerLocalService.fetchDispatchTrigger(
							companyId, fileName);

					if (dispatchTrigger == null) {
						UnicodeProperties typeSettingsUnicodeProperties =
							_getDefaultTypeSettingsUnicodeProperties(
								jobFileURL.openStream());

						dispatchTrigger =
							_dispatchTriggerLocalService.addDispatchTrigger(
								userId, _TALEND, typeSettingsUnicodeProperties,
								fileName, false);
					}

					File tempFile = FileUtil.createTempFile(
						jobFileURL.openStream());

					String contentType = MimeTypesUtil.getContentType(
						jobFileURL.getFile());

					_dispatchFileRepository.addFileEntry(
						userId, dispatchTrigger.getDispatchTriggerId(),
						fileName, tempFile.length(), contentType,
						jobFileURL.openStream());
				}
			}
			catch (Exception exception) {
				_log.error("Failed to deploy job " + fileName);
				_log.error(exception, exception);
			}
		}
	}

	private UnicodeProperties _getDefaultTypeSettingsUnicodeProperties(
			InputStream inputStream)
		throws Exception {

		UnicodeProperties unicodeProperties = new UnicodeProperties(true);

		File tempFile = FileUtil.createTempFile(inputStream);

		File tempFolder = FileUtil.createTempFolder();

		FileUtil.unzip(tempFile, tempFolder);

		String[] defaultProperties = FileUtil.find(
			tempFolder.getAbsolutePath(), _DEFAULT_PROPERTIES, null);

		if (defaultProperties.length > 0) {
			unicodeProperties.fastLoad(FileUtil.read(defaultProperties[0]));
		}

		return unicodeProperties;
	}

	private static final String _DEFAULT_PROPERTIES = "**\\Default.properties";

	private static final String _TALEND = "talend";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceSalesforceConnectorTalendJobDeployer.class);

	@Reference
	private DispatchFileRepository _dispatchFileRepository;

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private TalendJobFileProvider _talendJobFileProvider;

	@Reference
	private UserLocalService _userLocalService;

}
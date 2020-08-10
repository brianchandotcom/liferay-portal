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

package com.liferay.commerce.talend.job.deployer.salesforce.internal;

import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcess;
import com.liferay.commerce.data.integration.service.CommerceDataIntegrationProcessLocalService;
import com.liferay.commerce.data.integration.talend.TalendProcessTypeHelper;
import com.liferay.commerce.talend.job.deployer.TalendJobFileProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.util.ContentTypeUtil;

import java.io.File;
import java.io.InputStream;

import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(immediate = true, service = SalesforceTalendJobDeployer.class)
public class SalesforceTalendJobDeployer {

	@Activate
	protected void activate() throws Exception {
		List<InputStream> inputStreams =
			_talendJobFileProvider.getJobFileInputStreams();

		for (InputStream inputStream : inputStreams) {
			try {
				File tempFile = FileUtil.createTempFile(inputStream);

				ZipFile zipFile = new ZipFile(tempFile);

				Enumeration<? extends ZipEntry> entries = zipFile.entries();

				while (entries.hasMoreElements()) {
					try {
						ZipEntry zipEntry = entries.nextElement();

						long[] companyIds = _portal.getCompanyIds();

						for (long companyId : companyIds) {
							long userId = _userLocalService.getDefaultUserId(
								companyId);

							String fileName = zipEntry.getName();

							if (!fileName.endsWith(".zip")) {
								continue;
							}

							CommerceDataIntegrationProcess
								commerceDataIntegrationProcess =
									_commerceDataIntegrationProcessLocalService.
										fetchCommerceDataIntegrationProcess(
											companyId, fileName);

							if (commerceDataIntegrationProcess == null) {
								UnicodeProperties typeSettingsProperties =
									_getDefaultTypeSettingsProperties(
										zipFile.getInputStream(zipEntry));

								commerceDataIntegrationProcess =
									_commerceDataIntegrationProcessLocalService.
										addCommerceDataIntegrationProcess(
											userId, fileName, _TALEND,
											typeSettingsProperties, false);
							}

							String contentType = ContentTypeUtil.getContentType(
								zipEntry.getName());

							_talendProcessTypeHelper.addFileEntry(
								companyId, userId,
								commerceDataIntegrationProcess.
									getCommerceDataIntegrationProcessId(),
								fileName, zipEntry.getSize(), contentType,
								zipFile.getInputStream(zipEntry));
						}
					}
					catch (Exception e) {
						ZipEntry zipEntry = entries.nextElement();

						_log.error(
							"Failed to deploy job " + zipEntry.getName());
					}
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	private UnicodeProperties _getDefaultTypeSettingsProperties(
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

	private static final String _TALEND = "TALEND";

	private static final Log _log = LogFactoryUtil.getLog(
		SalesforceTalendJobDeployer.class);

	@Reference
	private CommerceDataIntegrationProcessLocalService
		_commerceDataIntegrationProcessLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private TalendJobFileProvider _talendJobFileProvider;

	@Reference
	private TalendProcessTypeHelper _talendProcessTypeHelper;

	@Reference
	private UserLocalService _userLocalService;

}
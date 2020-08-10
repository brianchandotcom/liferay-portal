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

package com.liferay.commerce.data.integration.talend.internal.process.type;

import com.liferay.commerce.data.integration.constants.CommerceDataIntegrationPortletKeys;
import com.liferay.commerce.data.integration.exception.FileEntryValidationException;
import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcess;
import com.liferay.commerce.data.integration.service.CommerceDataIntegrationProcessLocalService;
import com.liferay.commerce.data.integration.talend.TalendProcessTypeHelper;
import com.liferay.commerce.data.integration.talend.internal.configuration.CommerceDataIntegrationProcessConfiguration;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.InputStream;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	configurationPid = "com.liferay.commerce.data.integration.talend.internal.configuration.CommerceDataIntegrationProcessConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL,
	service = TalendProcessTypeHelper.class
)
public class TalendProcessTypeHelperImpl implements TalendProcessTypeHelper {

	@Override
	public FileEntry addFileEntry(
			long companyId, long userId, long commerceDataIntegrationProcessId,
			String fileName, long size, String contentType,
			InputStream inputStream)
		throws Exception {

		_validateFile(fileName, size);

		Company company = _companyLocalService.getCompany(companyId);

		return _addFileEntry(
			company.getGroupId(), userId, commerceDataIntegrationProcessId,
			contentType, inputStream);
	}

	@Override
	public FileEntry getFileEntry(long commerceDataIntegrationProcessId)
		throws Exception {

		CommerceDataIntegrationProcess commerceDataIntegrationProcess =
			_commerceDataIntegrationProcessLocalService.
				getCommerceDataIntegrationProcess(
					commerceDataIntegrationProcessId);

		Company company = _companyLocalService.getCompany(
			commerceDataIntegrationProcess.getCompanyId());

		Folder folder = _getFolder(
			company.getGroupId(), commerceDataIntegrationProcess.getUserId());

		return PortletFileRepositoryUtil.fetchPortletFileEntry(
			company.getGroupId(), folder.getFolderId(),
			String.valueOf(commerceDataIntegrationProcessId));
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_commerceDataIntegrationProcessConfiguration =
			ConfigurableUtil.createConfigurable(
				CommerceDataIntegrationProcessConfiguration.class, properties);
	}

	private FileEntry _addFileEntry(
			long groupId, long userId, long commerceDataIntegrationProcessId,
			String contentType, InputStream inputStream)
		throws PortalException {

		Folder folder = _getFolder(groupId, userId);

		FileEntry fileEntry = PortletFileRepositoryUtil.fetchPortletFileEntry(
			groupId, folder.getFolderId(),
			String.valueOf(commerceDataIntegrationProcessId));

		if (fileEntry != null) {
			PortletFileRepositoryUtil.deletePortletFileEntry(
				fileEntry.getFileEntryId());
		}

		return PortletFileRepositoryUtil.addPortletFileEntry(
			groupId, userId, CommerceDataIntegrationProcess.class.getName(),
			commerceDataIntegrationProcessId,
			CommerceDataIntegrationPortletKeys.COMMERCE_DATA_INTEGRATION,
			folder.getFolderId(), inputStream,
			String.valueOf(commerceDataIntegrationProcessId), contentType,
			false);
	}

	private Folder _getFolder(long groupId, long userId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Repository repository = PortletFileRepositoryUtil.addPortletRepository(
			groupId,
			CommerceDataIntegrationPortletKeys.COMMERCE_DATA_INTEGRATION,
			serviceContext);

		return PortletFileRepositoryUtil.addPortletFolder(
			userId, repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, TalendProcessType.KEY,
			serviceContext);
	}

	private void _validateFile(String fileName, long size) throws Exception {
		if ((_commerceDataIntegrationProcessConfiguration.imageMaxSize() > 0) &&
			(size >
				_commerceDataIntegrationProcessConfiguration.imageMaxSize())) {

			throw new FileEntryValidationException(
				"File size exceeds configured limit");
		}

		String extension = FileUtil.getExtension(fileName);

		String[] imageExtensions =
			_commerceDataIntegrationProcessConfiguration.imageExtensions();

		for (String imageExtension : imageExtensions) {
			if (StringPool.STAR.equals(imageExtension) ||
				imageExtension.equals(StringPool.PERIOD + extension)) {

				return;
			}
		}

		throw new FileEntryValidationException(
			"Invalid image for file name " + fileName);
	}

	private volatile CommerceDataIntegrationProcessConfiguration
		_commerceDataIntegrationProcessConfiguration;

	@Reference
	private CommerceDataIntegrationProcessLocalService
		_commerceDataIntegrationProcessLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Portal _portal;

}
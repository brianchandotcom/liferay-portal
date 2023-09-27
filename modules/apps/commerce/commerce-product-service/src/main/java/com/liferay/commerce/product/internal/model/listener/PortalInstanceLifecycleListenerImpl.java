/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.model.listener;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.instance.lifecycle.InitialRequestPortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.image.ImageTool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.repository.portletrepository.PortletRepository;

import java.io.File;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class PortalInstanceLifecycleListenerImpl
	extends InitialRequestPortalInstanceLifecycleListener {

	@Override
	protected void doPortalInstanceRegistered(long companyId) throws Exception {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(null);

		try {
			List<CommerceCatalog> commerceCatalogs =
				_commerceCatalogLocalService.getCommerceCatalogs(
					companyId, true);

			if (commerceCatalogs.isEmpty()) {
				Message message = new Message();

				message.setPayload(
					JSONUtil.put(
						"commerceCatalogId",
						() -> {
							CommerceCatalog commerceCatalog =
								_commerceCatalogLocalService.
									addDefaultCommerceCatalog(companyId);

							return commerceCatalog.getCommerceCatalogId();
						}));

				MessageBusUtil.sendMessage(
					DestinationNames.COMMERCE_BASE_PRICE_LIST, message);
			}

			Company company = _companyLocalService.getCompany(companyId);

			FileEntry fileEntry =
				_dlAppLocalService.fetchFileEntryByExternalReferenceCode(
					company.getGroupId(), PropsKeys.IMAGE_DEFAULT_COMPANY_LOGO);

			if (fileEntry == null) {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setCompanyId(company.getCompanyId());
				serviceContext.setScopeGroupId(company.getGroupId());

				User user = company.getGuestUser();

				serviceContext.setUserId(user.getCompanyId());

				Repository repository = _repositoryLocalService.addRepository(
					user.getUserId(), company.getGroupId(),
					_portal.getClassNameId(PortletRepository.class.getName()),
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
					PropsKeys.IMAGE_DEFAULT_COMPANY_LOGO, null,
					CPConstants.SERVICE_NAME_PRODUCT, new UnicodeProperties(),
					true, serviceContext);

				Image image = _imageTool.getDefaultCompanyLogo();

				File file = _file.createTempFile(image.getTextObj());

				try {
					String mimeType = MimeTypesUtil.getContentType(file);

					_dlAppLocalService.addFileEntry(
						PropsKeys.IMAGE_DEFAULT_COMPANY_LOGO, user.getUserId(),
						repository.getRepositoryId(),
						DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
						"company_logo.png", mimeType, image.getTextObj(), null,
						null, serviceContext);
				}
				finally {
					_file.delete(file);
				}
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalInstanceLifecycleListenerImpl.class);

	@Reference
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private com.liferay.portal.kernel.util.File _file;

	@Reference
	private ImageTool _imageTool;

	@Reference
	private Portal _portal;

	@Reference
	private RepositoryLocalService _repositoryLocalService;

}
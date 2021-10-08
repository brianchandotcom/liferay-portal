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

package com.liferay.commerce.product.internal.model.listener;

import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.image.ImageToolUtil;
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
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.repository.portletrepository.PortletRepository;
import com.liferay.portlet.documentlibrary.store.StoreFactory;

import java.io.File;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	enabled = false, immediate = true,
	service = PortalInstanceLifecycleListener.class
)
public class PortalInstanceLifecycleListenerImpl
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		try {
			List<CommerceCatalog> commerceCatalogs =
				_commerceCatalogLocalService.getCommerceCatalogs(
					company.getCompanyId(), true);

			if (commerceCatalogs.isEmpty()) {
				Message message = new Message();

				message.setPayload(
					JSONUtil.put(
						"commerceCatalogId",
						() -> {
							CommerceCatalog commerceCatalog =
								_commerceCatalogLocalService.
									addDefaultCommerceCatalog(
										company.getCompanyId());

							return commerceCatalog.getCommerceCatalogId();
						}));

				MessageBusUtil.sendMessage(
					DestinationNames.COMMERCE_BASE_PRICE_LIST, message);
			}

			FileEntry fileEntry =
				_dlAppLocalService.fetchFileEntryByExternalReferenceCode(
					company.getGroupId(), PropsKeys.IMAGE_DEFAULT_COMPANY_LOGO);

			if (fileEntry == null) {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setCompanyId(company.getCompanyId());
				serviceContext.setScopeGroupId(company.getGroupId());

				User user = company.getDefaultUser();

				serviceContext.setUserId(user.getCompanyId());

				Repository repository = _repositoryLocalService.addRepository(
					user.getUserId(), company.getGroupId(),
					_portal.getClassNameId(PortletRepository.class.getName()),
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
					PropsKeys.IMAGE_DEFAULT_COMPANY_LOGO, null, null,
					new UnicodeProperties(), true, serviceContext);

				Image image = ImageToolUtil.getDefaultCompanyLogo();

				File file = FileUtil.createTempFile(image.getTextObj());

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
					FileUtil.delete(file);
				}
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalInstanceLifecycleListenerImpl.class);

	@Reference
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private RepositoryLocalService _repositoryLocalService;

	@Reference(target = "(dl.store.impl.enabled=true)")
	private StoreFactory _storeFactory;

}
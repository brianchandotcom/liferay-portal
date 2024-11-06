/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.headless.admin.site.dto.v1_0.DisplayPageTemplateFolder;
import com.liferay.headless.admin.site.resource.v1_0.DisplayPageTemplateFolderResource;
import com.liferay.headless.common.spi.service.context.ServiceContextBuilder;
import com.liferay.layout.page.template.constants.LayoutPageTemplateCollectionTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.exception.NoSuchPageTemplateCollectionException;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rubén Pulido
 * @author Bárbara Cabrera
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/display-page-template-folder.properties",
	scope = ServiceScope.PROTOTYPE,
	service = DisplayPageTemplateFolderResource.class
)
public class DisplayPageTemplateFolderResourceImpl
	extends BaseDisplayPageTemplateFolderResourceImpl {

	@Override
	public void deleteSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
			String siteExternalReferenceCode,
			String displayPageTemplateFolderExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = _groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		_layoutPageTemplateCollectionService.deleteLayoutPageTemplateCollection(
			displayPageTemplateFolderExternalReferenceCode, group.getGroupId());
	}

	@Override
	public DisplayPageTemplateFolder
			getSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
				String siteExternalReferenceCode,
				String displayPageTemplateFolderExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = _groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionService.
				fetchLayoutPageTemplateCollection(
					displayPageTemplateFolderExternalReferenceCode,
					group.getGroupId());

		if (layoutPageTemplateCollection == null) {
			throw new NoSuchPageTemplateCollectionException();
		}

		return _toDisplayPageTemplateFolder(layoutPageTemplateCollection);
	}

	@Override
	public DisplayPageTemplateFolder
			postSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
				String siteExternalReferenceCode,
				DisplayPageTemplateFolder displayPageTemplateFolder)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = _groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		return _addDisplayPageTemplateFolder(displayPageTemplateFolder, group);
	}

	@Override
	public DisplayPageTemplateFolder
			putSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
				String siteExternalReferenceCode,
				String displayPageTemplateFolderExternalReferenceCode,
				DisplayPageTemplateFolder displayPageTemplateFolder)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = _groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionService.
				fetchLayoutPageTemplateCollection(
					displayPageTemplateFolderExternalReferenceCode,
					group.getGroupId());

		if (layoutPageTemplateCollection == null) {
			return _addDisplayPageTemplateFolder(
				displayPageTemplateFolder, group);
		}

		long parentLayoutPageTemplateCollectionId =
			_getParentLayoutPageTemplateCollectionId(
				displayPageTemplateFolder, group);

		if (Validator.isNotNull(
				displayPageTemplateFolder.
					getParentDisplayPageTemplateFolderExternalReferenceCode()) &&
			!Objects.equals(
				layoutPageTemplateCollection.
					getParentLayoutPageTemplateCollectionId(),
				parentLayoutPageTemplateCollectionId)) {

			layoutPageTemplateCollection =
				_layoutPageTemplateCollectionService.
					moveLayoutPageTemplateCollection(
						layoutPageTemplateCollection.
							getLayoutPageTemplateCollectionId(),
						parentLayoutPageTemplateCollectionId);
		}

		return _toDisplayPageTemplateFolder(
			_layoutPageTemplateCollectionService.
				updateLayoutPageTemplateCollection(
					layoutPageTemplateCollection.
						getLayoutPageTemplateCollectionId(),
					displayPageTemplateFolder.getName(),
					displayPageTemplateFolder.getDescription()));
	}

	private DisplayPageTemplateFolder _addDisplayPageTemplateFolder(
			DisplayPageTemplateFolder displayPageTemplateFolder, Group group)
		throws Exception {

		return _toDisplayPageTemplateFolder(
			_layoutPageTemplateCollectionService.
				addLayoutPageTemplateCollection(
					displayPageTemplateFolder.getExternalReferenceCode(),
					group.getGroupId(),
					_getParentLayoutPageTemplateCollectionId(
						displayPageTemplateFolder, group),
					displayPageTemplateFolder.getName(),
					displayPageTemplateFolder.getDescription(),
					LayoutPageTemplateCollectionTypeConstants.DISPLAY_PAGE,
					_getServiceContext(displayPageTemplateFolder, group)));
	}

	private long _getParentLayoutPageTemplateCollectionId(
			DisplayPageTemplateFolder displayPageTemplateFolder, Group group)
		throws Exception {

		long parentLayoutPageTemplateCollectionId =
			LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT;

		if (Validator.isNull(
				displayPageTemplateFolder.
					getParentDisplayPageTemplateFolderExternalReferenceCode())) {

			return parentLayoutPageTemplateCollectionId;
		}

		LayoutPageTemplateCollection parentLayoutPageTemplateCollection =
			_layoutPageTemplateCollectionService.
				fetchLayoutPageTemplateCollection(
					displayPageTemplateFolder.
						getParentDisplayPageTemplateFolderExternalReferenceCode(),
					group.getGroupId());

		if (parentLayoutPageTemplateCollection != null) {
			parentLayoutPageTemplateCollectionId =
				parentLayoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId();
		}

		return parentLayoutPageTemplateCollectionId;
	}

	private ServiceContext _getServiceContext(
		DisplayPageTemplateFolder displayPageTemplateFolder, Group group) {

		ServiceContext serviceContext = ServiceContextBuilder.create(
			group.getGroupId(), contextHttpServletRequest, null
		).build();

		serviceContext.setCreateDate(
			displayPageTemplateFolder.getDateCreated());
		serviceContext.setModifiedDate(
			displayPageTemplateFolder.getDateModified());
		serviceContext.setUuid(displayPageTemplateFolder.getUuid());

		return serviceContext;
	}

	private DisplayPageTemplateFolder _toDisplayPageTemplateFolder(
			LayoutPageTemplateCollection layoutPageTemplateCollection)
		throws Exception {

		return _displayPageTemplateFolderDTOConverter.toDTO(
			layoutPageTemplateCollection);
	}

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.DisplayPageTemplateFolderDTOConverter)"
	)
	private DTOConverter
		<LayoutPageTemplateCollection, DisplayPageTemplateFolder>
			_displayPageTemplateFolderDTOConverter;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutPageTemplateCollectionService
		_layoutPageTemplateCollectionService;

}
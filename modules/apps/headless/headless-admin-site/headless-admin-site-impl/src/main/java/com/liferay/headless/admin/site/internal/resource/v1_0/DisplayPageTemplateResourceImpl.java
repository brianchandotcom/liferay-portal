/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.headless.admin.site.dto.v1_0.ClassSubtypeReference;
import com.liferay.headless.admin.site.dto.v1_0.DisplayPageTemplate;
import com.liferay.headless.admin.site.dto.v1_0.DisplayPageTemplateFolder;
import com.liferay.headless.admin.site.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.resource.v1_0.DisplayPageTemplateResource;
import com.liferay.headless.common.spi.service.context.ServiceContextBuilder;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rubén Pulido
 * @author Lourdes Fernández Besada
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/display-page-template.properties",
	scope = ServiceScope.PROTOTYPE, service = DisplayPageTemplateResource.class
)
public class DisplayPageTemplateResourceImpl
	extends BaseDisplayPageTemplateResourceImpl {

	@Override
	public void deleteSiteSiteByExternalReferenceCodeDisplayPageTemplate(
			String siteExternalReferenceCode,
			String displayPageTemplateExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		_layoutPageTemplateEntryService.deleteLayoutPageTemplateEntry(
			displayPageTemplateExternalReferenceCode, group.getGroupId());
	}

	@Override
	public DisplayPageTemplate
			getSiteSiteByExternalReferenceCodeDisplayPageTemplate(
				String siteExternalReferenceCode,
				String displayPageTemplateExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		return _displayPageTemplateDTOConverter.toDTO(
			_layoutPageTemplateEntryService.
				getLayoutPageTemplateEntryByExternalReferenceCode(
					displayPageTemplateExternalReferenceCode,
					group.getGroupId()));
	}

	@Override
	public Page<DisplayPageTemplate>
			getSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplatesPage(
				String siteExternalReferenceCode,
				String displayPageTemplateFolderExternalReferenceCode,
				Boolean flatten)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionService.
				getLayoutPageTemplateCollection(
					displayPageTemplateFolderExternalReferenceCode,
					group.getGroupId());

		return Page.of(
			transform(
				_layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
					group.getGroupId(),
					layoutPageTemplateCollection.
						getLayoutPageTemplateCollectionId(),
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null),
				layoutPageTemplateEntry ->
					_displayPageTemplateDTOConverter.toDTO(
						layoutPageTemplateEntry)));
	}

	@Override
	public Page<DisplayPageTemplate>
			getSiteSiteByExternalReferenceCodeDisplayPageTemplatesPage(
				String siteExternalReferenceCode, String search,
				Aggregation aggregation, Filter filter, Pagination pagination,
				Sort[] sorts)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		return Page.of(
			transform(
				_layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
					group.getGroupId(),
					LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null),
				layoutPageTemplateEntry ->
					_displayPageTemplateDTOConverter.toDTO(
						layoutPageTemplateEntry)));
	}

	@Override
	public DisplayPageTemplate
			postSiteSiteByExternalReferenceCodeDisplayPageTemplate(
				String siteExternalReferenceCode,
				DisplayPageTemplate displayPageTemplate)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = _groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		return _addDisplayPageTemplate(
			displayPageTemplate, group,
			_getLayoutPageTemplateCollectionId(displayPageTemplate, group));
	}

	@Override
	public DisplayPageTemplate
			postSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplate(
				String siteExternalReferenceCode,
				String displayPageTemplateFolderExternalReferenceCode,
				DisplayPageTemplate displayPageTemplate)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionService.
				getLayoutPageTemplateCollection(
					displayPageTemplateFolderExternalReferenceCode,
					group.getGroupId());

		return _addDisplayPageTemplate(
			displayPageTemplate, group,
			layoutPageTemplateCollection.getLayoutPageTemplateCollectionId());
	}

	@Override
	public DisplayPageTemplate
			putSiteSiteByExternalReferenceCodeDisplayPageTemplate(
				String siteExternalReferenceCode,
				String displayPageTemplateExternalReferenceCode,
				DisplayPageTemplate displayPageTemplate)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryService.
				fetchLayoutPageTemplateEntryByExternalReferenceCode(
					displayPageTemplateExternalReferenceCode,
					group.getGroupId());

		if (layoutPageTemplateEntry == null) {
			return _addDisplayPageTemplate(
				displayPageTemplate, group,
				_getLayoutPageTemplateCollectionId(displayPageTemplate, group));
		}

		long layoutPageTemplateCollectionId =
			_getLayoutPageTemplateCollectionId(displayPageTemplate, group);

		if (Validator.isNotNull(displayPageTemplate.getParentFolder()) &&
			!Objects.equals(
				layoutPageTemplateEntry.getLayoutPageTemplateCollectionId(),
				layoutPageTemplateCollectionId)) {

			layoutPageTemplateEntry =
				_layoutPageTemplateEntryService.moveLayoutPageTemplateEntry(
					layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
					layoutPageTemplateCollectionId);
		}

		if (Validator.isNotNull(displayPageTemplate.getMarkedAsDefault()) &&
			!Objects.equals(
				GetterUtil.getBoolean(displayPageTemplate.getMarkedAsDefault()),
				layoutPageTemplateEntry.isDefaultTemplate())) {

			layoutPageTemplateEntry =
				_layoutPageTemplateEntryService.updateLayoutPageTemplateEntry(
					layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
					GetterUtil.getBoolean(
						displayPageTemplate.getMarkedAsDefault()));
		}

		return _displayPageTemplateDTOConverter.toDTO(
			_layoutPageTemplateEntryService.updateLayoutPageTemplateEntry(
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
				displayPageTemplate.getName()));
	}

	@Override
	protected void preparePatch(
		DisplayPageTemplate displayPageTemplate,
		DisplayPageTemplate existingDisplayPageTemplate) {

		if (displayPageTemplate.getParentFolder() != null) {
			existingDisplayPageTemplate.setParentFolder(
				displayPageTemplate::getParentFolder);
		}
	}

	private DisplayPageTemplate _addDisplayPageTemplate(
			DisplayPageTemplate displayPageTemplate, Group group,
			long layoutPageTemplateCollectionId)
		throws Exception {

		ClassSubtypeReference contentTypeReference =
			displayPageTemplate.getContentTypeReference();

		return _displayPageTemplateDTOConverter.toDTO(
			_layoutPageTemplateEntryService.addLayoutPageTemplateEntry(
				displayPageTemplate.getExternalReferenceCode(),
				group.getGroupId(), layoutPageTemplateCollectionId,
				_portal.getClassNameId(contentTypeReference.getClassName()),
				_getClassTypeId(contentTypeReference, group.getGroupId()),
				displayPageTemplate.getName(), 0L,
				WorkflowConstants.STATUS_DRAFT,
				_getServiceContext(displayPageTemplate, group)));
	}

	private long _getClassTypeId(
		ClassSubtypeReference contentTypeReference, long groupId) {

		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class,
				contentTypeReference.getClassName());

		if (infoItemFormVariationsProvider == null) {
			return 0;
		}

		ItemExternalReference itemExternalReference =
			contentTypeReference.getSubTypeExternalReference();

		if (itemExternalReference == null) {
			return -1;
		}

		InfoItemFormVariation infoItemFormVariation =
			infoItemFormVariationsProvider.
				getInfoItemFormVariationByExternalReferenceCode(
					itemExternalReference.getExternalReferenceCode(), groupId);

		if (infoItemFormVariation != null) {
			return GetterUtil.getLong(infoItemFormVariation.getKey());
		}

		return -1;
	}

	private long _getLayoutPageTemplateCollectionId(
			DisplayPageTemplate displayPageTemplate, Group group)
		throws Exception {

		DisplayPageTemplateFolder displayPageTemplateFolder =
			displayPageTemplate.getParentFolder();

		if (displayPageTemplateFolder == null) {
			return LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT;
		}

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionService.
				fetchLayoutPageTemplateCollection(
					displayPageTemplateFolder.getExternalReferenceCode(),
					group.getGroupId());

		if (layoutPageTemplateCollection == null) {
			return LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT;
		}

		return layoutPageTemplateCollection.getLayoutPageTemplateCollectionId();
	}

	private ServiceContext _getServiceContext(
		DisplayPageTemplate displayPageTemplate, Group group) {

		ServiceContext serviceContext = ServiceContextBuilder.create(
			group.getGroupId(), contextHttpServletRequest, null
		).build();

		serviceContext.setCreateDate(displayPageTemplate.getDateCreated());
		serviceContext.setModifiedDate(displayPageTemplate.getDateModified());
		serviceContext.setUuid(displayPageTemplate.getUuid());

		return serviceContext;
	}

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.DisplayPageTemplateDTOConverter)"
	)
	private DTOConverter<LayoutPageTemplateEntry, DisplayPageTemplate>
		_displayPageTemplateDTOConverter;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private LayoutPageTemplateCollectionService
		_layoutPageTemplateCollectionService;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private Portal _portal;

}
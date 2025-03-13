/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.headless.admin.site.dto.v1_0.ContentPageSpecification;
import com.liferay.headless.admin.site.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.MasterPage;
import com.liferay.headless.admin.site.dto.v1_0.PageExperience;
import com.liferay.headless.admin.site.dto.v1_0.PageSpecification;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.GroupUtil;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.LayoutUtil;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.ServiceContextUtil;
import com.liferay.headless.admin.site.resource.v1_0.MasterPageResource;
import com.liferay.headless.common.spi.service.context.ServiceContextBuilder;
import com.liferay.layout.constants.LayoutTypeSettingsConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rubén Pulido
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/master-page.properties",
	scope = ServiceScope.PROTOTYPE, service = MasterPageResource.class
)
public class MasterPageResourceImpl extends BaseMasterPageResourceImpl {

	@Override
	public void deleteSiteSiteByExternalReferenceCodeMasterPage(
			String siteExternalReferenceCode,
			String masterPageExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		_layoutPageTemplateEntryService.deleteLayoutPageTemplateEntry(
			masterPageExternalReferenceCode,
			GroupUtil.getGroupId(
				false, contextCompany.getCompanyId(),
				siteExternalReferenceCode));
	}

	@Override
	public MasterPage getSiteSiteByExternalReferenceCodeMasterPage(
			String siteExternalReferenceCode,
			String masterPageExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryService.
				getLayoutPageTemplateEntryByExternalReferenceCode(
					masterPageExternalReferenceCode,
					GroupUtil.getGroupId(
						true, contextCompany.getCompanyId(),
						siteExternalReferenceCode));

		if (!Objects.equals(
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT,
				layoutPageTemplateEntry.getType())) {

			throw new UnsupportedOperationException();
		}

		return _masterPageDTOConverter.toDTO(layoutPageTemplateEntry);
	}

	@Override
	public Page<MasterPage> getSiteSiteByExternalReferenceCodeMasterPagesPage(
			String siteExternalReferenceCode, String search,
			Aggregation aggregation, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		long groupId = GroupUtil.getGroupId(
			true, contextCompany.getCompanyId(), siteExternalReferenceCode);

		if (Validator.isNull(search)) {
			return Page.of(
				transform(
					_layoutPageTemplateEntryService.
						getLayoutPageTemplateEntries(
							groupId,
							LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT,
							pagination.getStartPosition(),
							pagination.getEndPosition(), null),
					layoutPageTemplateEntry -> _masterPageDTOConverter.toDTO(
						layoutPageTemplateEntry)),
				pagination,
				_layoutPageTemplateEntryService.
					getLayoutPageTemplateEntriesCount(
						groupId,
						LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT));
		}

		return Page.of(
			transform(
				_layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
					groupId, 0, 0, search,
					LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				layoutPageTemplateEntry -> _masterPageDTOConverter.toDTO(
					layoutPageTemplateEntry)),
			pagination,
			_layoutPageTemplateEntryService.getLayoutPageTemplateEntriesCount(
				groupId, 0, 0, search,
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT));
	}

	@Override
	public MasterPage postSiteSiteByExternalReferenceCodeMasterPage(
			String siteExternalReferenceCode, MasterPage masterPage)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		return _addMasterPage(
			GroupUtil.getGroupId(
				false, contextCompany.getCompanyId(),
				siteExternalReferenceCode),
			masterPage);
	}

	@Override
	public ContentPageSpecification
			postSiteSiteByExternalReferenceCodeMasterPagePageSpecification(
				String siteExternalReferenceCode,
				String pageTemplateExternalReferenceCode,
				ContentPageSpecification contentPageSpecification)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryService.
				fetchLayoutPageTemplateEntryByExternalReferenceCode(
					pageTemplateExternalReferenceCode,
					GroupUtil.getGroupId(
						false, contextCompany.getCompanyId(),
						siteExternalReferenceCode));

		if (!Objects.equals(
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT,
				layoutPageTemplateEntry.getType())) {

			throw new UnsupportedOperationException();
		}

		return (ContentPageSpecification)_pageSpecificationDTOConverter.toDTO(
			LayoutUtil.addDraftToLayout(
				contentPageSpecification,
				_layoutLocalService.getLayout(
					layoutPageTemplateEntry.getPlid()),
				ServiceContextUtil.createServiceContext(
					layoutPageTemplateEntry.getGroupId(),
					contextHttpServletRequest, contextUser.getUserId())));
	}

	@Override
	public MasterPage putSiteSiteByExternalReferenceCodeMasterPage(
			String siteExternalReferenceCode,
			String masterPageExternalReferenceCode, MasterPage masterPage)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		long groupId = GroupUtil.getGroupId(
			false, contextCompany.getCompanyId(), siteExternalReferenceCode);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryService.
				fetchLayoutPageTemplateEntryByExternalReferenceCode(
					masterPageExternalReferenceCode, groupId);

		if (layoutPageTemplateEntry == null) {
			return _addMasterPage(groupId, masterPage);
		}

		if (Validator.isNotNull(masterPage.getMarkedAsDefault()) &&
			!Objects.equals(
				GetterUtil.getBoolean(masterPage.getMarkedAsDefault()),
				layoutPageTemplateEntry.isDefaultTemplate())) {

			layoutPageTemplateEntry =
				_layoutPageTemplateEntryService.updateLayoutPageTemplateEntry(
					layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
					GetterUtil.getBoolean(masterPage.getMarkedAsDefault()));
		}

		long previewFileEntryId = _getPreviewFileEntryId(groupId, masterPage);

		if (previewFileEntryId !=
				layoutPageTemplateEntry.getPreviewFileEntryId()) {

			layoutPageTemplateEntry =
				_layoutPageTemplateEntryService.updateLayoutPageTemplateEntry(
					layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
					previewFileEntryId);
		}

		PageSpecification[] pageSpecifications =
			masterPage.getPageSpecifications();

		if (pageSpecifications != null) {
			if (pageSpecifications.length != 2) {
				throw new UnsupportedOperationException();
			}

			Layout layout = _layoutLocalService.getLayout(
				layoutPageTemplateEntry.getPlid());

			ContentPageSpecification draftContentPageSpecification = null;
			ContentPageSpecification publishedContentPageSpecification =
				(ContentPageSpecification)pageSpecifications[0];

			if (!Objects.equals(
					layout.getExternalReferenceCode(),
					publishedContentPageSpecification.
						getExternalReferenceCode())) {

				draftContentPageSpecification =
					publishedContentPageSpecification;
				publishedContentPageSpecification =
					(ContentPageSpecification)pageSpecifications[1];
			}
			else {
				draftContentPageSpecification =
					(ContentPageSpecification)pageSpecifications[1];
			}

			Layout draftLayout = layout.fetchDraftLayout();

			if (!Objects.equals(
					draftLayout.getExternalReferenceCode(),
					draftContentPageSpecification.getExternalReferenceCode()) ||
				!Objects.equals(
					layout.getExternalReferenceCode(),
					publishedContentPageSpecification.
						getExternalReferenceCode()) ||
				!Objects.equals(
					publishedContentPageSpecification.
						getDraftContentPageSpecificationExternalReferenceCode(),
					draftContentPageSpecification.getExternalReferenceCode())) {

				throw new UnsupportedOperationException();
			}

			int status = WorkflowConstants.STATUS_APPROVED;

			if (Objects.equals(
					draftContentPageSpecification.getStatus(),
					PageSpecification.Status.DRAFT)) {

				status = WorkflowConstants.STATUS_DRAFT;
			}

			ServiceContext serviceContext = _getServiceContext(
				groupId, masterPage);

			serviceContext.setAttribute(
				"published",
				Objects.equals(
					publishedContentPageSpecification.getStatus(),
					PageSpecification.Status.APPROVED));

			LayoutUtil.updateLayout(
				draftContentPageSpecification, draftLayout, status,
				serviceContext);

			layout = LayoutUtil.updateLayout(
				publishedContentPageSpecification, layout,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

			if (!layoutPageTemplateEntry.isApproved() && layout.isPublished()) {
				layoutPageTemplateEntry =
					_layoutPageTemplateEntryService.updateStatus(
						layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
						WorkflowConstants.STATUS_APPROVED);
			}
		}

		return _masterPageDTOConverter.toDTO(
			_layoutPageTemplateEntryService.updateLayoutPageTemplateEntry(
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
				masterPage.getName()));
	}

	@Override
	protected void preparePatch(
		MasterPage masterPage, MasterPage existingMasterPage) {

		if (masterPage.getPageSpecifications() != null) {
			existingMasterPage.setPageSpecifications(
				masterPage::getPageSpecifications);
		}

		if (masterPage.getThumbnail() != null) {
			existingMasterPage.setThumbnail(masterPage::getThumbnail);
		}
	}

	private MasterPage _addMasterPage(long groupId, MasterPage masterPage)
		throws Exception {

		boolean defaultTemplate = false;
		int status = WorkflowConstants.STATUS_DRAFT;

		if (GetterUtil.getBoolean(masterPage.getMarkedAsDefault())) {
			defaultTemplate = true;
			status = WorkflowConstants.STATUS_APPROVED;
		}
		else if (_isPublishedLayout(masterPage.getPageSpecifications())) {
			status = WorkflowConstants.STATUS_APPROVED;
		}

		ServiceContext serviceContext = _getServiceContext(groupId, masterPage);

		return _masterPageDTOConverter.toDTO(
			_layoutPageTemplateEntryService.addLayoutPageTemplateEntry(
				masterPage.getExternalReferenceCode(), groupId,
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				masterPage.getKey(), 0, 0, masterPage.getName(),
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT,
				_getPreviewFileEntryId(groupId, masterPage), defaultTemplate, 0,
				_getLayoutPlid(groupId, masterPage, serviceContext), 0, status,
				serviceContext));
	}

	private Serializable _getDefaultSegmentsExperienceExternalReferenceCode(
		PageExperience[] pageExperiences) {

		if (ArrayUtil.isEmpty(pageExperiences)) {
			throw new UnsupportedOperationException();
		}

		for (PageExperience pageExperience : pageExperiences) {
			if (Objects.equals(
					pageExperience.getKey(),
					SegmentsExperienceConstants.KEY_DEFAULT)) {

				return pageExperience.getExternalReferenceCode();
			}
		}

		throw new UnsupportedOperationException();
	}

	private long _getLayoutPlid(
			long groupId, MasterPage masterPage, ServiceContext serviceContext)
		throws Exception {

		PageSpecification[] pageSpecifications =
			masterPage.getPageSpecifications();

		if (pageSpecifications == null) {
			return 0;
		}

		if (pageSpecifications.length != 2) {
			throw new UnsupportedOperationException();
		}

		ContentPageSpecification draftContentPageSpecification = null;
		ContentPageSpecification publishedContentPageSpecification =
			(ContentPageSpecification)pageSpecifications[0];

		if (Validator.isNull(
				publishedContentPageSpecification.
					getDraftContentPageSpecificationExternalReferenceCode())) {

			draftContentPageSpecification = publishedContentPageSpecification;
			publishedContentPageSpecification =
				(ContentPageSpecification)pageSpecifications[1];
		}
		else {
			draftContentPageSpecification =
				(ContentPageSpecification)pageSpecifications[1];
		}

		if (Validator.isNull(
				publishedContentPageSpecification.
					getDraftContentPageSpecificationExternalReferenceCode()) ||
			!Objects.equals(
				draftContentPageSpecification.getExternalReferenceCode(),
				publishedContentPageSpecification.
					getDraftContentPageSpecificationExternalReferenceCode())) {

			throw new UnsupportedOperationException();
		}

		Map<Locale, String> titleMap = Collections.singletonMap(
			_portal.getSiteDefaultLocale(groupId), masterPage.getName());

		String published;

		if (Objects.equals(
				publishedContentPageSpecification.getStatus(),
				PageSpecification.Status.APPROVED)) {

			published = "true";
		}
		else {
			published = null;
		}

		serviceContext.setAttribute(
			"defaultSegmentsExperienceExternalReferenceCode",
			_getDefaultSegmentsExperienceExternalReferenceCode(
				publishedContentPageSpecification.getPageExperiences()));
		serviceContext.setAttribute(
			"draftLayoutDefaultSegmentsExperienceExternalReferenceCode",
			_getDefaultSegmentsExperienceExternalReferenceCode(
				draftContentPageSpecification.getPageExperiences()));
		serviceContext.setAttribute(
			"draftLayoutExternalReferenceCode",
			draftContentPageSpecification.getExternalReferenceCode());
		serviceContext.setAttribute(
			"layout.instanceable.allowed", Boolean.TRUE);
		serviceContext.setAttribute(
			"layout.page.template.entry.type",
			LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT);
		serviceContext.setAttribute(
			"published", GetterUtil.getBoolean(published));

		Layout layout = _layoutLocalService.addLayout(
			publishedContentPageSpecification.getExternalReferenceCode(),
			contextUser.getUserId(), groupId, true, 0, 0, 0, titleMap, titleMap,
			null, null, null, LayoutConstants.TYPE_CONTENT,
			UnicodePropertiesBuilder.create(
				true
			).setProperty(
				LayoutTypeSettingsConstants.KEY_PUBLISHED, () -> published
			).setProperty(
				"lfr-theme:regular:show-footer", Boolean.FALSE.toString()
			).setProperty(
				"lfr-theme:regular:show-header", Boolean.FALSE.toString()
			).setProperty(
				"lfr-theme:regular:show-header-search", Boolean.FALSE.toString()
			).setProperty(
				"lfr-theme:regular:wrap-widget-page-content",
				Boolean.FALSE.toString()
			).buildString(),
			true, true, new HashMap<>(), 0, serviceContext);

		int status = WorkflowConstants.STATUS_APPROVED;

		if (Objects.equals(
				draftContentPageSpecification.getStatus(),
				PageSpecification.Status.DRAFT)) {

			status = WorkflowConstants.STATUS_DRAFT;
		}

		LayoutUtil.updateLayout(
			draftContentPageSpecification, layout.fetchDraftLayout(), status,
			serviceContext);

		layout = LayoutUtil.updateLayout(
			publishedContentPageSpecification, layout,
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		return layout.getPlid();
	}

	private long _getPreviewFileEntryId(long groupId, MasterPage masterPage)
		throws Exception {

		ItemExternalReference itemExternalReference = masterPage.getThumbnail();

		if ((itemExternalReference == null) ||
			Validator.isNull(
				itemExternalReference.getExternalReferenceCode())) {

			return 0;
		}

		FileEntry fileEntry =
			_portletFileRepository.getPortletFileEntryByExternalReferenceCode(
				itemExternalReference.getExternalReferenceCode(), groupId);

		return fileEntry.getFileEntryId();
	}

	private ServiceContext _getServiceContext(
		long groupId, MasterPage masterPage) {

		ServiceContext serviceContext = ServiceContextBuilder.create(
			groupId, contextHttpServletRequest, null
		).build();

		serviceContext.setCreateDate(masterPage.getDateCreated());
		serviceContext.setModifiedDate(masterPage.getDateModified());
		serviceContext.setUserId(contextUser.getUserId());
		serviceContext.setUuid(masterPage.getUuid());

		return serviceContext;
	}

	private boolean _isPublishedLayout(PageSpecification[] pageSpecifications) {
		if (pageSpecifications == null) {
			return false;
		}

		if (pageSpecifications.length != 2) {
			throw new UnsupportedOperationException();
		}

		ContentPageSpecification publishedContentPageSpecification =
			(ContentPageSpecification)pageSpecifications[0];

		if (Validator.isNull(
				publishedContentPageSpecification.
					getDraftContentPageSpecificationExternalReferenceCode())) {

			publishedContentPageSpecification =
				(ContentPageSpecification)pageSpecifications[1];
		}

		if (Objects.equals(
				publishedContentPageSpecification.getStatus(),
				PageSpecification.Status.APPROVED)) {

			return true;
		}

		return false;
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.MasterPageDTOConverter)"
	)
	private DTOConverter<LayoutPageTemplateEntry, MasterPage>
		_masterPageDTOConverter;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.PageSpecificationDTOConverter)"
	)
	private DTOConverter<Layout, PageSpecification>
		_pageSpecificationDTOConverter;

	@Reference
	private Portal _portal;

	@Reference
	private PortletFileRepository _portletFileRepository;

}
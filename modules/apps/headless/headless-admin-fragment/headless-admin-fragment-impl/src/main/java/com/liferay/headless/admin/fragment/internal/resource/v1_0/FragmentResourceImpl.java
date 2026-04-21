/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.internal.resource.v1_0;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.exception.RequiredFragmentEntryVersionException;
import com.liferay.fragment.exception.UnsupportedUnpublishFragmentEntryOperationException;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.headless.admin.fragment.dto.v1_0.Fragment;
import com.liferay.headless.admin.fragment.dto.v1_0.FragmentVersion;
import com.liferay.headless.admin.fragment.internal.util.EnabledUtil;
import com.liferay.headless.admin.fragment.resource.v1_0.FragmentResource;
import com.liferay.headless.common.spi.service.context.ServiceContextBuilder;
import com.liferay.headless.common.spi.util.GroupUtil;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rubén Pulido
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/fragment.properties",
	scope = ServiceScope.PROTOTYPE, service = FragmentResource.class
)
public class FragmentResourceImpl extends BaseFragmentResourceImpl {

	@Override
	public void deleteSiteFragment(
			String siteExternalReferenceCode,
			String fragmentExternalReferenceCode)
		throws Exception {

		EnabledUtil.checkEnabled(contextCompany);

		_fragmentEntryService.deleteFragmentEntry(
			fragmentExternalReferenceCode,
			GroupUtil.getStagingAwareGroupId(
				true, contextCompany.getCompanyId(),
				siteExternalReferenceCode));
	}

	@Override
	public Fragment getSiteFragment(
			String siteExternalReferenceCode,
			String fragmentExternalReferenceCode)
		throws Exception {

		EnabledUtil.checkEnabled(contextCompany);

		long groupId = GroupUtil.getGroupId(
			true, true, contextCompany.getCompanyId(),
			siteExternalReferenceCode);

		try {
			return _toFragment(
				_fragmentEntryService.getFragmentEntryByExternalReferenceCode(
					fragmentExternalReferenceCode, groupId));
		}
		catch (NoSuchModelException noSuchModelException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchModelException);
			}

			FragmentEntry draftFragmentEntry =
				_fragmentEntryLocalService.
					fetchFragmentEntryByExternalReferenceCode(
						fragmentExternalReferenceCode, groupId, false);

			if (draftFragmentEntry == null) {
				throw noSuchModelException;
			}

			return _toFragment(draftFragmentEntry);
		}
	}

	@Override
	public Page<Fragment> getSiteFragmentSetFragmentsPage(
			String siteExternalReferenceCode,
			String fragmentSetExternalReferenceCode, Pagination pagination)
		throws Exception {

		EnabledUtil.checkEnabled(contextCompany);

		long groupId = GroupUtil.getGroupId(
			true, true, contextCompany.getCompanyId(),
			siteExternalReferenceCode);

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.
				getFragmentCollectionByExternalReferenceCode(
					fragmentSetExternalReferenceCode, groupId);

		long fragmentCollectionId =
			fragmentCollection.getFragmentCollectionId();

		return Page.of(
			transform(
				_fragmentEntryService.getFragmentCompositionsAndFragmentEntries(
					groupId, fragmentCollectionId, WorkflowConstants.STATUS_ANY,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				object -> {
					if (object instanceof FragmentEntry) {
						return _toFragment((FragmentEntry)object);
					}

					return null;
				}),
			pagination,
			_fragmentEntryService.
				getFragmentCompositionsAndFragmentEntriesCount(
					groupId, fragmentCollectionId,
					WorkflowConstants.STATUS_ANY));
	}

	@Override
	public Fragment postSiteFragmentSetFragment(
			String siteExternalReferenceCode,
			String fragmentSetExternalReferenceCode, Fragment fragment)
		throws Exception {

		EnabledUtil.checkEnabled(contextCompany);

		fragment.setFragmentSetExternalReferenceCode(
			() -> fragmentSetExternalReferenceCode);

		return _addFragmentEntry(
			fragment.getExternalReferenceCode(),
			GroupUtil.getStagingAwareGroupId(
				true, contextCompany.getCompanyId(), siteExternalReferenceCode),
			fragment);
	}

	@Override
	public Fragment putSiteFragment(
			String siteExternalReferenceCode,
			String fragmentExternalReferenceCode, Fragment fragment)
		throws Exception {

		EnabledUtil.checkEnabled(contextCompany);

		long groupId = GroupUtil.getStagingAwareGroupId(
			true, contextCompany.getCompanyId(), siteExternalReferenceCode);

		try {
			FragmentEntry fragmentEntry =
				_fragmentEntryService.getFragmentEntryByExternalReferenceCode(
					fragmentExternalReferenceCode, groupId);

			return _updateFragmentEntry(fragment, fragmentEntry, groupId);
		}
		catch (NoSuchModelException noSuchModelException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchModelException);
			}

			FragmentEntry draftFragmentEntry =
				_fragmentEntryLocalService.
					fetchFragmentEntryByExternalReferenceCode(
						fragmentExternalReferenceCode, groupId, false);

			if (draftFragmentEntry != null) {
				return _updateFragmentEntry(
					fragment, draftFragmentEntry, groupId);
			}

			return _addFragmentEntry(
				fragmentExternalReferenceCode, groupId, fragment);
		}
	}

	private Fragment _addFragmentEntry(
			String externalReferenceCode, long groupId, Fragment fragment)
		throws Exception {

		if (fragment.getFragmentSetExternalReferenceCode() == null) {
			throw new IllegalArgumentException(
				_language.get(
					contextAcceptLanguage.getPreferredLocale(),
					"a-fragment-set-external-reference-code-is-required-to-" +
						"create-a-new-fragment"));
		}

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.
				getFragmentCollectionByExternalReferenceCode(
					fragment.getFragmentSetExternalReferenceCode(), groupId);

		FragmentVersion approvedFragmentVersion = _getFragmentVersion(
			fragment, FragmentVersion.Status.APPROVED);
		FragmentVersion draftFragmentVersion = _getFragmentVersion(
			fragment, FragmentVersion.Status.DRAFT);

		FragmentEntry fragmentEntry;

		if (approvedFragmentVersion != null) {
			fragmentEntry = _fragmentEntryService.addFragmentEntry(
				externalReferenceCode, groupId,
				fragmentCollection.getFragmentCollectionId(), fragment.getKey(),
				fragment.getName(), approvedFragmentVersion.getCss(),
				approvedFragmentVersion.getHtml(),
				approvedFragmentVersion.getJs(),
				GetterUtil.getBoolean(fragment.getCacheable()),
				approvedFragmentVersion.getConfiguration(), fragment.getIcon(),
				0L, GetterUtil.getBoolean(fragment.getMarketplace()),
				GetterUtil.getBoolean(fragment.getReadOnly()),
				FragmentConstants.TYPE_COMPONENT, null,
				WorkflowConstants.STATUS_APPROVED,
				_getServiceContext(groupId, fragment));

			if (draftFragmentVersion != null) {
				_updateDraft(
					fragmentEntry.getFragmentEntryId(), draftFragmentVersion);
			}
		}
		else if (draftFragmentVersion != null) {
			fragmentEntry = _fragmentEntryService.addFragmentEntry(
				externalReferenceCode, groupId,
				fragmentCollection.getFragmentCollectionId(), fragment.getKey(),
				fragment.getName(), draftFragmentVersion.getCss(),
				draftFragmentVersion.getHtml(), draftFragmentVersion.getJs(),
				GetterUtil.getBoolean(fragment.getCacheable()),
				draftFragmentVersion.getConfiguration(), fragment.getIcon(), 0L,
				GetterUtil.getBoolean(fragment.getMarketplace()),
				GetterUtil.getBoolean(fragment.getReadOnly()),
				FragmentConstants.TYPE_COMPONENT, null,
				WorkflowConstants.STATUS_DRAFT,
				_getServiceContext(groupId, fragment));
		}
		else {
			fragmentEntry = _fragmentEntryService.addFragmentEntry(
				externalReferenceCode, groupId,
				fragmentCollection.getFragmentCollectionId(), fragment.getKey(),
				fragment.getName(), null, null, null,
				GetterUtil.getBoolean(fragment.getCacheable()), null, null, 0L,
				GetterUtil.getBoolean(fragment.getMarketplace()),
				GetterUtil.getBoolean(fragment.getReadOnly()),
				FragmentConstants.TYPE_COMPONENT, null,
				WorkflowConstants.STATUS_DRAFT,
				_getServiceContext(groupId, fragment));
		}

		return _toFragment(fragmentEntry);
	}

	private FragmentVersion _getFragmentVersion(
		Fragment fragment, FragmentVersion.Status status) {

		FragmentVersion[] fragmentVersions = fragment.getFragmentVersions();

		if (fragmentVersions == null) {
			return null;
		}

		for (FragmentVersion fragmentVersion : fragmentVersions) {
			if (status == fragmentVersion.getStatus()) {
				return fragmentVersion;
			}
		}

		return null;
	}

	private ServiceContext _getServiceContext(long groupId, Fragment fragment) {
		ServiceContext serviceContext = ServiceContextBuilder.create(
			groupId, contextHttpServletRequest, null
		).build();

		serviceContext.setCompanyId(contextCompany.getCompanyId());
		serviceContext.setCreateDate(fragment.getDateCreated());
		serviceContext.setModifiedDate(fragment.getDateModified());

		return serviceContext;
	}

	private Fragment _toFragment(FragmentEntry fragmentEntry) throws Exception {
		return _fragmentDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				false, null, _dtoConverterRegistry, contextHttpServletRequest,
				fragmentEntry.getFragmentEntryId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			fragmentEntry);
	}

	private void _updateDraft(
			long fragmentEntryId, FragmentVersion fragmentVersion)
		throws Exception {

		FragmentEntry draftFragmentEntry = _fragmentEntryService.getDraft(
			fragmentEntryId);

		draftFragmentEntry.setCss(fragmentVersion.getCss());
		draftFragmentEntry.setHtml(fragmentVersion.getHtml());
		draftFragmentEntry.setJs(fragmentVersion.getJs());
		draftFragmentEntry.setConfiguration(fragmentVersion.getConfiguration());

		_fragmentEntryService.updateDraft(draftFragmentEntry);
	}

	private Fragment _updateFragmentEntry(
			Fragment fragment, FragmentEntry fragmentEntry, long groupId)
		throws Exception {

		FragmentVersion approvedFragmentVersion = _getFragmentVersion(
			fragment, FragmentVersion.Status.APPROVED);
		FragmentVersion draftFragmentVersion = _getFragmentVersion(
			fragment, FragmentVersion.Status.DRAFT);

		if ((approvedFragmentVersion == null) &&
			(draftFragmentVersion == null)) {

			throw new RequiredFragmentEntryVersionException();
		}

		if ((approvedFragmentVersion == null) && fragmentEntry.isHead()) {
			throw new UnsupportedUnpublishFragmentEntryOperationException();
		}

		long fragmentCollectionId = fragmentEntry.getFragmentCollectionId();

		String fragmentSetExternalReferenceCode =
			fragment.getFragmentSetExternalReferenceCode();

		if (fragmentSetExternalReferenceCode != null) {
			FragmentCollection fragmentCollection =
				_fragmentCollectionService.
					getFragmentCollectionByExternalReferenceCode(
						fragmentSetExternalReferenceCode, groupId);

			fragmentCollectionId = fragmentCollection.getFragmentCollectionId();
		}

		long fragmentEntryId = fragmentEntry.getFragmentEntryId();

		if (fragmentEntry.isHead()) {
			FragmentEntry draftFragmentEntry = _fragmentEntryService.getDraft(
				fragmentEntry.getFragmentEntryId());

			fragmentEntryId = draftFragmentEntry.getFragmentEntryId();
		}

		FragmentEntry updatedFragmentEntry;

		if (approvedFragmentVersion != null) {
			updatedFragmentEntry = _fragmentEntryService.updateFragmentEntry(
				fragmentEntryId, fragmentCollectionId, fragment.getName(),
				approvedFragmentVersion.getCss(),
				approvedFragmentVersion.getHtml(),
				approvedFragmentVersion.getJs(),
				GetterUtil.getBoolean(fragment.getCacheable()),
				approvedFragmentVersion.getConfiguration(), fragment.getIcon(),
				fragmentEntry.getPreviewFileEntryId(),
				GetterUtil.getBoolean(fragment.getReadOnly()),
				fragmentEntry.getTypeOptions(),
				WorkflowConstants.STATUS_APPROVED);

			if (draftFragmentVersion != null) {
				_updateDraft(
					updatedFragmentEntry.getFragmentEntryId(),
					draftFragmentVersion);
			}
		}
		else {
			updatedFragmentEntry = _fragmentEntryService.updateFragmentEntry(
				fragmentEntryId, fragmentCollectionId, fragment.getName(),
				draftFragmentVersion.getCss(), draftFragmentVersion.getHtml(),
				draftFragmentVersion.getJs(),
				GetterUtil.getBoolean(fragment.getCacheable()),
				draftFragmentVersion.getConfiguration(), fragment.getIcon(),
				fragmentEntry.getPreviewFileEntryId(),
				GetterUtil.getBoolean(fragment.getReadOnly()),
				fragmentEntry.getTypeOptions(), WorkflowConstants.STATUS_DRAFT);

			updatedFragmentEntry = _fragmentEntryService.updateDraft(
				updatedFragmentEntry);
		}

		return _toFragment(updatedFragmentEntry);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentResourceImpl.class);

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private FragmentCollectionService _fragmentCollectionService;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.fragment.internal.dto.v1_0.converter.FragmentDTOConverter)"
	)
	private DTOConverter<FragmentEntry, Fragment> _fragmentDTOConverter;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private FragmentEntryService _fragmentEntryService;

	@Reference
	private Language _language;

}
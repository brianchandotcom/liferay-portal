/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.object.internal.resource.v1_0;

import com.liferay.headless.object.dto.v1_0.Collaborator;
import com.liferay.headless.object.resource.v1_0.CollaboratorResource;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.GroupUtil;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.sharing.service.SharingEntryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alicia García
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/collaborator.properties",
	scope = ServiceScope.PROTOTYPE, service = CollaboratorResource.class
)
public class CollaboratorResourceImpl extends BaseCollaboratorResourceImpl {

	@Override
	public Page<Collaborator> getObjectEntryFolderCollaboratorsPage(
			Long objectEntryFolderId, Pagination pagination)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-17564")) {
			throw new UnsupportedOperationException();
		}

		return _getObjectEntryFolderCollaboratorsPage(
			_objectEntryFolderLocalService.getObjectEntryFolder(
				objectEntryFolderId),
			pagination);
	}

	@Override
	public Page<Collaborator>
			getScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorsPage(
				String scopeKey, String externalReferenceCode,
				Pagination pagination)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-17564")) {
			throw new UnsupportedOperationException();
		}

		return _getObjectEntryFolderCollaboratorsPage(
			_objectEntryFolderLocalService.
				getObjectEntryFolderByExternalReferenceCode(
					externalReferenceCode, _getGroupId(scopeKey),
					contextCompany.getCompanyId()),
			pagination);
	}

	@Override
	public Page<Collaborator> postObjectEntryFolderCollaboratorsPage(
			Long objectEntryFolderId, Collaborator[] collaborators)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-17564")) {
			throw new UnsupportedOperationException();
		}

		return _postObjectEntryFolderCollaboratorsPage(
			collaborators,
			_objectEntryFolderLocalService.getObjectEntryFolder(
				objectEntryFolderId));
	}

	@Override
	public Page<Collaborator>
			postScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorsPage(
				String scopeKey, String externalReferenceCode,
				Collaborator[] collaborators)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-17564")) {
			throw new UnsupportedOperationException();
		}

		return _postObjectEntryFolderCollaboratorsPage(
			collaborators,
			_objectEntryFolderLocalService.
				getObjectEntryFolderByExternalReferenceCode(
					externalReferenceCode, _getGroupId(scopeKey),
					contextCompany.getCompanyId()));
	}

	private SharingEntry _addOrUpdateSharingEntry(
			long classNameId, long classPK, Collaborator collaborator,
			long groupId)
		throws Exception {

		long toUserId = 0;
		long toUserGroupId = 0;

		if (Objects.equals(Collaborator.Type.USER, collaborator.getType())) {
			User user = _userLocalService.getUserByExternalReferenceCode(
				collaborator.getExternalReferenceCode(),
				contextCompany.getCompanyId());

			toUserId = user.getUserId();
		}
		else {
			UserGroup userGroup =
				_userGroupLocalService.getUserGroupByExternalReferenceCode(
					collaborator.getExternalReferenceCode(),
					contextCompany.getCompanyId());

			toUserGroupId = userGroup.getUserGroupId();
		}

		boolean shareable = false;

		if (collaborator.getShare() != null) {
			shareable = collaborator.getShare();
		}

		return _sharingEntryService.addOrUpdateSharingEntry(
			null, toUserGroupId, toUserId, classNameId, classPK, groupId,
			shareable,
			transformToList(
				collaborator.getActionIds(),
				SharingEntryAction::parseFromActionId),
			collaborator.getDateExpired(), new ServiceContext());
	}

	private long _getGroupId(String scopeKey) throws Exception {
		Long groupId = GroupUtil.getGroupId(
			contextCompany.getCompanyId(), scopeKey, _groupLocalService);

		if (groupId != null) {
			return groupId;
		}

		if (Objects.equals(scopeKey, "0")) {
			return 0;
		}

		throw new NoSuchGroupException();
	}

	private Page<Collaborator> _getObjectEntryFolderCollaboratorsPage(
			ObjectEntryFolder objectEntryFolder, Pagination pagination)
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			ObjectEntryFolder.class.getName());

		return Page.of(
			transform(
				_sharingEntryService.getSharingEntries(
					classNameId, objectEntryFolder.getObjectEntryFolderId(),
					objectEntryFolder.getGroupId(),
					pagination.getStartPosition(), pagination.getEndPosition()),
				this::_toCollaborator),
			pagination,
			_sharingEntryLocalService.getSharingEntriesCount(
				classNameId, objectEntryFolder.getObjectEntryFolderId()));
	}

	private Page<Collaborator> _postObjectEntryFolderCollaboratorsPage(
			Collaborator[] collaborators, ObjectEntryFolder objectEntryFolder)
		throws Exception {

		return _postObjectEntryFolderCollaboratorsPage(
			_classNameLocalService.getClassNameId(
				ObjectEntryFolder.class.getName()),
			objectEntryFolder.getObjectEntryFolderId(), collaborators,
			objectEntryFolder.getGroupId());
	}

	private Page<Collaborator> _postObjectEntryFolderCollaboratorsPage(
			long classNameId, long classPK, Collaborator[] collaborators,
			long groupId)
		throws Exception {

		List<SharingEntry> oldSharingEntries =
			_sharingEntryService.getSharingEntries(
				classNameId, classPK, groupId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		List<SharingEntry> newSharingEntries = new ArrayList<>();

		List<Long> sharingEntriesIds = new ArrayList<>();

		for (Collaborator collaborator : collaborators) {
			SharingEntry sharingEntry = _addOrUpdateSharingEntry(
				classNameId, classPK, collaborator, groupId);

			newSharingEntries.add(sharingEntry);
			sharingEntriesIds.add(sharingEntry.getSharingEntryId());
		}

		for (SharingEntry sharingEntry : oldSharingEntries) {
			if (!sharingEntriesIds.contains(sharingEntry.getSharingEntryId())) {
				_sharingEntryService.deleteSharingEntry(sharingEntry);
			}
		}

		return Page.of(transform(newSharingEntries, this::_toCollaborator));
	}

	private Collaborator _toCollaborator(SharingEntry sharingEntry)
		throws Exception {

		return _collaboratorDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), new HashMap<>(),
				_dtoConverterRegistry, sharingEntry.getSharingEntryId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			sharingEntry);
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference(
		target = "(component.name=com.liferay.headless.object.internal.dto.v1_0.converter.CollaboratorDTOConverter)"
	)
	private DTOConverter<SharingEntry, Collaborator> _collaboratorDTOConverter;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

	@Reference
	private SharingEntryService _sharingEntryService;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
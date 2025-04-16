/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.resource.v1_0;

import com.liferay.headless.object.dto.v1_0.Collaborator;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
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

/**
 * @author Mikel Lorza
 */
public class CollaboratorResourceImpl extends BaseCollaboratorResourceImpl {

	public CollaboratorResourceImpl(
		ClassNameLocalService classNameLocalService,
		DTOConverter<SharingEntry, Collaborator> collaboratorDTOConverter,
		DTOConverterRegistry dtoConverterRegistry,
		GroupLocalService groupLocalService,
		ObjectEntryLocalService objectEntryLocalService,
		SharingEntryService sharingEntryService,
		SharingEntryLocalService sharingEntryLocalService,
		UserGroupLocalService userGroupLocalService,
		UserLocalService userLocalService) {

		_classNameLocalService = classNameLocalService;
		_collaboratorDTOConverter = collaboratorDTOConverter;
		_dtoConverterRegistry = dtoConverterRegistry;
		_groupLocalService = groupLocalService;
		_objectEntryLocalService = objectEntryLocalService;
		_sharingEntryService = sharingEntryService;
		_sharingEntryLocalService = sharingEntryLocalService;
		_userGroupLocalService = userGroupLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	public Page<Collaborator> getObjectEntryCollaboratorsPage(
			Long objectEntryId, Pagination pagination)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-17564")) {
			throw new UnsupportedOperationException();
		}

		return _getObjectEntryCollaboratorsPage(
			_objectEntryLocalService.getObjectEntry(objectEntryId), pagination);
	}

	@Override
	public Page<Collaborator>
			getScopeScopeKeyByExternalReferenceCodeCollaboratorsPage(
				String scopeKey, String externalReferenceCode,
				Pagination pagination)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-17564")) {
			throw new UnsupportedOperationException();
		}

		return _getObjectEntryCollaboratorsPage(
			_objectEntryLocalService.getObjectEntry(
				externalReferenceCode, contextCompany.getCompanyId(),
				_getGroupId(scopeKey)),
			pagination);
	}

	@Override
	public Page<Collaborator> postObjectEntryCollaboratorsPage(
			Long objectEntryId, Collaborator[] collaborators)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-17564")) {
			throw new UnsupportedOperationException();
		}

		return _postObjectEntryCollaboratorsPage(
			collaborators,
			_objectEntryLocalService.getObjectEntry(objectEntryId));
	}

	@Override
	public Page<Collaborator>
			postScopeScopeKeyByExternalReferenceCodeCollaboratorsPage(
				String scopeKey, String externalReferenceCode,
				Collaborator[] collaborators)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-17564")) {
			throw new UnsupportedOperationException();
		}

		return _postObjectEntryCollaboratorsPage(
			collaborators,
			_objectEntryLocalService.getObjectEntry(
				externalReferenceCode, contextCompany.getCompanyId(),
				_getGroupId(scopeKey)));
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

	private Page<Collaborator> _getObjectEntryCollaboratorsPage(
		ObjectEntry objectEntry, Pagination pagination) {

		long classNameId = _classNameLocalService.getClassNameId(
			objectEntry.getModelClassName());

		return Page.of(
			transform(
				_sharingEntryLocalService.getSharingEntries(
					classNameId, objectEntry.getObjectEntryId(),
					pagination.getStartPosition(), pagination.getEndPosition()),
				this::_toCollaborator),
			pagination,
			_sharingEntryLocalService.getSharingEntriesCount(
				classNameId, objectEntry.getObjectEntryId()));
	}

	private Page<Collaborator> _postObjectEntryCollaboratorsPage(
			Collaborator[] collaborators, ObjectEntry objectEntry)
		throws Exception {

		return _postObjectEntryCollaboratorsPage(
			_classNameLocalService.getClassNameId(
				objectEntry.getModelClassName()),
			objectEntry.getObjectEntryId(), collaborators,
			objectEntry.getGroupId());
	}

	private Page<Collaborator> _postObjectEntryCollaboratorsPage(
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

	private final ClassNameLocalService _classNameLocalService;
	private final DTOConverter<SharingEntry, Collaborator>
		_collaboratorDTOConverter;
	private final DTOConverterRegistry _dtoConverterRegistry;
	private final GroupLocalService _groupLocalService;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final SharingEntryLocalService _sharingEntryLocalService;
	private final SharingEntryService _sharingEntryService;
	private final UserGroupLocalService _userGroupLocalService;
	private final UserLocalService _userLocalService;

}
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.asset.library.internal.resource.v1_0;

import com.liferay.headless.asset.library.dto.v1_0.UserGroup;
import com.liferay.headless.asset.library.resource.v1_0.UserGroupResource;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.NoSuchUserGroupException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserGroupService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Roberto Díaz
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/user-group.properties",
	scope = ServiceScope.PROTOTYPE, service = UserGroupResource.class
)
public class UserGroupResourceImpl extends BaseUserGroupResourceImpl {

	@Override
	public void deleteAssetLibraryUserGroup(
			String assetLibraryExternalReferenceCode,
			String userGroupExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-17564")) {
			throw new UnsupportedOperationException();
		}

		Group group = _getGroup(assetLibraryExternalReferenceCode);
		com.liferay.portal.kernel.model.UserGroup userGroup =
			_userGroupService.getUserGroupByExternalReferenceCode(
				userGroupExternalReferenceCode, contextCompany.getCompanyId());

		_userGroupService.unsetGroupUserGroups(
			group.getGroupId(), new long[] {userGroup.getUserGroupId()});
	}

	@Override
	public UserGroup getAssetLibraryUserGroup(
			String assetLibraryExternalReferenceCode,
			String userGroupExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-17564")) {
			throw new UnsupportedOperationException();
		}

		Group group = _getGroup(assetLibraryExternalReferenceCode);
		com.liferay.portal.kernel.model.UserGroup userGroup =
			_userGroupService.getUserGroupByExternalReferenceCode(
				userGroupExternalReferenceCode, contextCompany.getCompanyId());

		_checkGroupUserGroup(group.getGroupId(), userGroup.getUserGroupId());

		return _toUserGroup(userGroup);
	}

	@Override
	public Page<UserGroup> getAssetLibraryUserGroupsPage(
			String assetLibraryExternalReferenceCode, String keywords,
			String search, Pagination pagination, Sort[] sorts)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-17564")) {
			throw new UnsupportedOperationException();
		}

		Group group = _getGroup(assetLibraryExternalReferenceCode);

		return _getUserGroupPage(
			group.getGroupId(), keywords, search, pagination, sorts);
	}

	@Override
	public UserGroup putAssetLibraryUserGroup(
			String assetLibraryExternalReferenceCode,
			String userGroupExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-17564")) {
			throw new UnsupportedOperationException();
		}

		Group group = _getGroup(assetLibraryExternalReferenceCode);

		com.liferay.portal.kernel.model.UserGroup userGroup =
			_userGroupService.getUserGroupByExternalReferenceCode(
				userGroupExternalReferenceCode, contextCompany.getCompanyId());

		_userGroupService.addGroupUserGroups(
			group.getGroupId(), new long[] {userGroup.getUserGroupId()});

		return _toUserGroup(
			_userGroupService.getUserGroup(userGroup.getUserGroupId()));
	}

	private void _checkGroupUserGroup(Long assetLibraryId, Long userGroupId)
		throws Exception {

		if (!_userGroupLocalService.hasGroupUserGroup(
				assetLibraryId, userGroupId)) {

			throw new NoSuchUserGroupException(
				"No user group exists with user group ID " + userGroupId);
		}
	}

	private Group _getGroup(String externalReferenceCode) throws Exception {
		Group group = _groupService.fetchGroupByExternalReferenceCode(
			externalReferenceCode, contextCompany.getCompanyId());

		if (group == null) {
			throw new NoSuchGroupException(
				"No group exists with external reference code " +
					externalReferenceCode);
		}

		return group;
	}

	private Page<UserGroup> _getUserGroupPage(
			long groupId, String keywords, String search, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			HashMapBuilder.put(
				"create",
				addAction(
					ActionKeys.ASSIGN_MEMBERS, groupId,
					"putAssetLibraryUserGroup", _groupModelResourcePermission)
			).put(
				"delete",
				addAction(
					ActionKeys.ASSIGN_MEMBERS, groupId,
					"deleteAssetLibraryUserGroup",
					_groupModelResourcePermission)
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, 0L, "getAssetLibraryUserGroupsPage",
					_groupModelResourcePermission)
			).build(),
			booleanQuery -> booleanQuery.add(
				new TermQueryImpl("groupIds", String.valueOf(groupId)),
				BooleanClauseOccur.MUST),
			null, com.liferay.portal.kernel.model.UserGroup.class.getName(),
			keywords, pagination,
			queryConfig -> {
			},
			searchContext -> {
				searchContext.setCompanyId(contextCompany.getCompanyId());

				if (Validator.isNotNull(search)) {
					searchContext.setKeywords(search);
				}
			},
			sorts,
			document -> _toUserGroup(
				_userGroupService.getUserGroup(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	private UserGroup _toUserGroup(
			com.liferay.portal.kernel.model.UserGroup userGroup)
		throws Exception {

		return _userGroupDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				HashMapBuilder.put(
					"delete",
					addAction(
						ActionKeys.ASSIGN_MEMBERS, userGroup.getGroupId(),
						"deleteAssetLibraryUserGroup",
						_groupModelResourcePermission)
				).put(
					"get",
					addAction(
						ActionKeys.VIEW, userGroup.getUserGroupId(),
						"getAssetLibraryUserGroup",
						_userGroupModelResourcePermission)
				).build(),
				_dtoConverterRegistry, contextHttpServletRequest,
				userGroup.getUserGroupId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.model.Group)"
	)
	private ModelResourcePermission<Group> _groupModelResourcePermission;

	@Reference
	private GroupService _groupService;

	@Reference(
		target = "(component.name=com.liferay.headless.asset.library.internal.dto.v1_0.converter.UserGroupDTOConverter)"
	)
	private DTOConverter<com.liferay.portal.kernel.model.UserGroup, UserGroup>
		_userGroupDTOConverter;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.model.UserGroup)"
	)
	private ModelResourcePermission<com.liferay.portal.kernel.model.UserGroup>
		_userGroupModelResourcePermission;

	@Reference
	private UserGroupService _userGroupService;

}
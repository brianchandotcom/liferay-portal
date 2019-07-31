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

package com.liferay.vldap.server.internal.directory;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.NoSuchCompanyException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.comparator.UserScreenNameComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.vldap.server.internal.directory.builder.CommunitiesBuilder;
import com.liferay.vldap.server.internal.directory.builder.CommunityBuilder;
import com.liferay.vldap.server.internal.directory.builder.CompanyBuilder;
import com.liferay.vldap.server.internal.directory.builder.DirectoryBuilder;
import com.liferay.vldap.server.internal.directory.builder.OrganizationBuilder;
import com.liferay.vldap.server.internal.directory.builder.OrganizationsBuilder;
import com.liferay.vldap.server.internal.directory.builder.RoleBuilder;
import com.liferay.vldap.server.internal.directory.builder.RolesBuilder;
import com.liferay.vldap.server.internal.directory.builder.RootBuilder;
import com.liferay.vldap.server.internal.directory.builder.SambaGroupBuilder;
import com.liferay.vldap.server.internal.directory.builder.SambaMachineBuilder;
import com.liferay.vldap.server.internal.directory.builder.SchemaBuilder;
import com.liferay.vldap.server.internal.directory.builder.TopBuilder;
import com.liferay.vldap.server.internal.directory.builder.UserBuilder;
import com.liferay.vldap.server.internal.directory.builder.UserGroupBuilder;
import com.liferay.vldap.server.internal.directory.builder.UserGroupsBuilder;
import com.liferay.vldap.server.internal.directory.builder.UsersBuilder;
import com.liferay.vldap.server.internal.directory.ldap.CommunitiesDirectory;
import com.liferay.vldap.server.internal.directory.ldap.CommunityDirectory;
import com.liferay.vldap.server.internal.directory.ldap.CompanyDirectory;
import com.liferay.vldap.server.internal.directory.ldap.Directory;
import com.liferay.vldap.server.internal.directory.ldap.OrganizationDirectory;
import com.liferay.vldap.server.internal.directory.ldap.OrganizationsDirectory;
import com.liferay.vldap.server.internal.directory.ldap.RoleDirectory;
import com.liferay.vldap.server.internal.directory.ldap.RolesDirectory;
import com.liferay.vldap.server.internal.directory.ldap.RootDirectory;
import com.liferay.vldap.server.internal.directory.ldap.SchemaDirectory;
import com.liferay.vldap.server.internal.directory.ldap.TopDirectory;
import com.liferay.vldap.server.internal.directory.ldap.UserDirectory;
import com.liferay.vldap.server.internal.directory.ldap.UserGroupDirectory;
import com.liferay.vldap.server.internal.directory.ldap.UserGroupsDirectory;
import com.liferay.vldap.server.internal.directory.ldap.UsersDirectory;
import com.liferay.vldap.server.internal.util.LdapUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.directory.api.ldap.model.entry.Value;
import org.apache.directory.api.ldap.model.filter.AndNode;
import org.apache.directory.api.ldap.model.filter.BranchNode;
import org.apache.directory.api.ldap.model.filter.EqualityNode;
import org.apache.directory.api.ldap.model.filter.ExprNode;
import org.apache.directory.api.ldap.model.filter.GreaterEqNode;
import org.apache.directory.api.ldap.model.filter.LeafNode;
import org.apache.directory.api.ldap.model.filter.LessEqNode;
import org.apache.directory.api.ldap.model.filter.NotNode;
import org.apache.directory.api.ldap.model.filter.OrNode;
import org.apache.directory.api.ldap.model.filter.PresenceNode;
import org.apache.directory.api.ldap.model.filter.SubstringNode;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.api.ldap.model.name.Dn;

/**
 * @author Jonathan Potter
 */
public class DirectoryTree {

	public DirectoryTree() {
		_rootBuilder.addDirectoryBuilder(_topBuilder);
		_topBuilder.addDirectoryBuilder(_companyBuilder);

		_companyBuilder.addDirectoryBuilder(_communitiesBuilder);
		_companyBuilder.addDirectoryBuilder(_organizationsBuilder);
		_companyBuilder.addDirectoryBuilder(_rolesBuilder);
		_companyBuilder.addDirectoryBuilder(_userGroupsBuilder);
		_companyBuilder.addDirectoryBuilder(_usersBuilder);

		_communitiesBuilder.addDirectoryBuilder(_communityBuilder);
		_organizationsBuilder.addDirectoryBuilder(_organizationBuilder);
		_rolesBuilder.addDirectoryBuilder(_roleBuilder);
		_userGroupsBuilder.addDirectoryBuilder(_userGroupBuilder);
		_usersBuilder.addDirectoryBuilder(_userBuilder);

		_communityBuilder.addDirectoryBuilder(_userBuilder);
		_organizationBuilder.addDirectoryBuilder(_userBuilder);
		_organizationBuilder.addDirectoryBuilder(_sambaMachineBuilder);
		_organizationBuilder.addDirectoryBuilder(_sambaGroupBuilder);
		_roleBuilder.addDirectoryBuilder(_userBuilder);
		_userGroupBuilder.addDirectoryBuilder(_userBuilder);
	}

	public List<Directory> getDirectories(
		SearchBase searchBase, ExprNode exprNode, SearchScope searchScope) {

		List<Directory> directories = new ArrayList<>();

		DirectoryBuilder searchBaseDirectoryBuilder =
			searchBase.getDirectoryBuilder();

		if (searchBaseDirectoryBuilder == null) {
			return directories;
		}

		List<FilterConstraint> filterConstraints = toFilterConstraints(
			exprNode);

		boolean subtree = searchScope.equals(SearchScope.SUBTREE);

		for (DirectoryBuilder directoryBuilder :
				searchBaseDirectoryBuilder.getDirectoryBuilders()) {

			directories.addAll(
				directoryBuilder.buildDirectories(
					searchBase, filterConstraints, subtree));
		}

		return directories;
	}

	public SearchBase getSearchBase(Dn dn, long sizeLimit)
		throws PortalException {

		if (dn == null) {
			return null;
		}

		String top = LdapUtil.getRdnValue(dn, 0);

		if (Validator.isNull(top)) {
			return new SearchBase(
				new RootDirectory(), _rootBuilder, TopDirectory.NAME);
		}

		if (StringUtil.equalsIgnoreCase(top, SchemaDirectory.COMMON_NAME)) {
			return new SearchBase(
				new SchemaDirectory(), _schemaBuilder, TopDirectory.NAME);
		}

		if (!StringUtil.equalsIgnoreCase(top, TopDirectory.NAME)) {
			return null;
		}

		String companyWebId = LdapUtil.getRdnValue(dn, 1);

		if (companyWebId == null) {
			return new SearchBase(new TopDirectory(top), _topBuilder, top);
		}

		Company company = null;

		try {
			company = CompanyLocalServiceUtil.getCompanyByWebId(companyWebId);
		}
		catch (NoSuchCompanyException nsce) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get company with web ID " + companyWebId, nsce);
			}

			return null;
		}

		String type = LdapUtil.getRdnValue(dn, 2);

		if (type == null) {
			return new SearchBase(
				new CompanyDirectory(top, company), _companyBuilder, top,
				company);
		}

		String typeValue = LdapUtil.getRdnValue(dn, 3);
		List<Identifier> identifiers = getIdentifiers(dn);

		if (StringUtil.equalsIgnoreCase(type, "Communities")) {
			return getCommunitiesSearchBase(
				top, typeValue, sizeLimit, company, identifiers);
		}
		else if (StringUtil.equalsIgnoreCase(type, "Organizations")) {
			return getOrganizationsSearchBase(
				top, typeValue, sizeLimit, company, identifiers);
		}
		else if (StringUtil.equalsIgnoreCase(type, "Roles")) {
			return getRolesSearchBase(
				top, typeValue, sizeLimit, company, identifiers);
		}
		else if (StringUtil.equalsIgnoreCase(type, "User Groups")) {
			return getUserGroupsSearchBase(
				top, typeValue, sizeLimit, company, identifiers);
		}
		else if (StringUtil.equalsIgnoreCase(type, "Users")) {
			return getUsersSearchBase(
				top, typeValue, sizeLimit, company, identifiers);
		}

		return null;
	}

	protected SearchBase getCommunitiesSearchBase(
			String top, String typeValue, long sizeLimit, Company company,
			List<Identifier> identifiers)
		throws PortalException {

		if (typeValue == null) {
			return new SearchBase(
				new CommunitiesDirectory(top, company), _communitiesBuilder,
				top, company);
		}

		Group community = GroupLocalServiceUtil.fetchGroup(
			company.getCompanyId(), typeValue);

		if (community == null) {
			Organization organization =
				OrganizationLocalServiceUtil.fetchOrganization(
					company.getCompanyId(), typeValue);

			if (organization != null) {
				community = GroupLocalServiceUtil.fetchGroup(
					organization.getGroupId());
			}
		}

		if (community == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Group with name ", typeValue, " does not exist for ",
						"company ", company.getCompanyId()));
			}

			return null;
		}

		if (identifiers.isEmpty()) {
			return new SearchBase(
				new CommunityDirectory(top, company, community),
				_communityBuilder, top, company, community);
		}

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("usersGroups", community.getGroupId());

		return getSearchBase(
			top, sizeLimit, params, identifiers, null, company);
	}

	protected List<Identifier> getIdentifiers(Dn dn) {
		List<Identifier> identifiers = new ArrayList<>();

		for (int i = 4; i < dn.size(); i++) {
			String rdnType = LdapUtil.getRdnType(dn, i);
			String rdnValue = LdapUtil.getRdnValue(dn, i);

			if ((rdnType == null) || (rdnValue == null)) {
				break;
			}

			Identifier identifier = new Identifier(rdnType, rdnValue);

			identifiers.add(identifier);
		}

		return identifiers;
	}

	protected SearchBase getOrganizationsSearchBase(
			String top, String typeValue, long sizeLimit, Company company,
			List<Identifier> identifiers)
		throws PortalException {

		if (typeValue == null) {
			return new SearchBase(
				new OrganizationsDirectory(top, company), _organizationsBuilder,
				top, company);
		}

		Organization organization =
			OrganizationLocalServiceUtil.fetchOrganization(
				company.getCompanyId(), typeValue);

		if (organization == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Organization with name ", typeValue,
						" does not exist for company ",
						company.getCompanyId()));
			}

			return null;
		}

		if (identifiers.isEmpty()) {
			return new SearchBase(
				new OrganizationDirectory(top, company, organization),
				_organizationBuilder, top, company, organization);
		}

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("usersOrgs", organization.getOrganizationId());

		return getSearchBase(
			top, sizeLimit, params, identifiers, organization, company);
	}

	protected SearchBase getRolesSearchBase(
			String top, String typeValue, long sizeLimit, Company company,
			List<Identifier> identifiers)
		throws PortalException {

		if (typeValue == null) {
			return new SearchBase(
				new RolesDirectory(top, company), _rolesBuilder, top, company);
		}

		Role role = RoleLocalServiceUtil.fetchRole(
			company.getCompanyId(), typeValue);

		if (role == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Role with name ", typeValue, " does not exist for ",
						"company ", company.getCompanyId()));
			}

			return null;
		}

		if (identifiers.isEmpty()) {
			return new SearchBase(
				new RoleDirectory(top, company, role), _roleBuilder, top,
				company, role);
		}

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("usersRoles", role.getRoleId());

		return getSearchBase(
			top, sizeLimit, params, identifiers, null, company);
	}

	protected SearchBase getSambaMachinesSearchBase(
		String top, Company company, Organization organization,
		List<Identifier> identifiers) {

		if ((identifiers.size() > 2) || (organization == null)) {
			return null;
		}

		Identifier subidentifier = identifiers.get(1);

		String subidentifierRdnType = subidentifier.getRdnType();

		if (!StringUtil.equalsIgnoreCase(
				subidentifierRdnType, "sambaDomainName")) {

			return null;
		}

		String sambaDomainName = subidentifier.getRdnValue();

		List<Directory> directories = _sambaMachineBuilder.buildDirectories(
			top, company, organization, sambaDomainName);

		if (directories.isEmpty()) {
			return null;
		}

		return new SearchBase(directories.get(0), null);
	}

	protected SearchBase getSearchBase(
			String top, long sizeLimit, LinkedHashMap<String, Object> params,
			List<Identifier> identifiers, Organization organization,
			Company company)
		throws PortalException {

		if (identifiers.isEmpty()) {
			throw new IllegalArgumentException("There are no identifiers");
		}

		Identifier identifier = identifiers.get(0);

		String rdnType = identifier.getRdnType();
		String rdnValue = identifier.getRdnValue();

		if (identifiers.size() == 1) {
			return getUsersSearchBase(
				top, rdnType, rdnValue, params, sizeLimit, company);
		}
		else if (StringUtil.equalsIgnoreCase(rdnType, "ou") &&
				 StringUtil.equalsIgnoreCase(rdnValue, "Samba Machines")) {

			return getSambaMachinesSearchBase(
				top, company, organization, identifiers);
		}

		return null;
	}

	protected SearchBase getUserGroupsSearchBase(
			String top, String typeValue, long sizeLimit, Company company,
			List<Identifier> identifiers)
		throws PortalException {

		if (typeValue == null) {
			return new SearchBase(
				new UserGroupsDirectory(top, company), _userGroupsBuilder, top,
				company);
		}

		UserGroup userGroup = UserGroupLocalServiceUtil.fetchUserGroup(
			company.getCompanyId(), typeValue);

		if (userGroup == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"User group with name ", typeValue, " does not exist ",
						"for company ", company.getCompanyId()));
			}

			return null;
		}

		if (identifiers.isEmpty()) {
			return new SearchBase(
				new UserGroupDirectory(top, company, userGroup),
				_userGroupBuilder, top, company, userGroup);
		}

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("usersUserGroups", userGroup.getUserGroupId());

		return getSearchBase(
			top, sizeLimit, params, identifiers, null, company);
	}

	protected SearchBase getUsersSearchBase(
			String top, String typeValue, long sizeLimit, Company company,
			List<Identifier> identifiers)
		throws PortalException {

		if (typeValue == null) {
			return new SearchBase(
				new UsersDirectory(top, company), _usersBuilder, top, company);
		}

		if (identifiers.isEmpty()) {
			User user = UserLocalServiceUtil.fetchUserByScreenName(
				company.getCompanyId(), typeValue);

			if (user == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"User with screen name ", typeValue,
							" does not exist for company ",
							company.getCompanyId()));
				}

				return null;
			}

			return new SearchBase(
				new UserDirectory(top, company, user), _userBuilder, top,
				company, user);
		}

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		return getSearchBase(
			top, sizeLimit, params, identifiers, null, company);
	}

	protected SearchBase getUsersSearchBase(
			String top, String rdnType, String rdnValue,
			LinkedHashMap<String, Object> params, long sizeLimit,
			Company company)
		throws PortalException {

		if (!StringUtil.equalsIgnoreCase(rdnType, "cn") &&
			!StringUtil.equalsIgnoreCase(rdnType, "uid")) {

			return null;
		}

		String screenName = rdnValue;

		List<User> users = UserLocalServiceUtil.search(
			company.getCompanyId(), null, null, null, screenName, null,
			WorkflowConstants.STATUS_APPROVED, params, true, 0, (int)sizeLimit,
			new UserScreenNameComparator());

		if (users.isEmpty()) {
			return null;
		}

		return new SearchBase(
			new UserDirectory(top, company, users.get(0)), _userBuilder, top,
			company, users.get(0));
	}

	protected List<FilterConstraint> toFilterConstraints(ExprNode exprNode) {
		if (exprNode == null) {
			return new ArrayList<>();
		}
		else if (exprNode.isLeaf()) {
			LeafNode leafNode = (LeafNode)exprNode;

			return toFilterConstraintsFromLeafNode(leafNode);
		}
		else {
			BranchNode branchNode = (BranchNode)exprNode;

			return toFilterConstraintsFromBranchNode(branchNode);
		}
	}

	protected List<FilterConstraint> toFilterConstraintsFromBranchNode(
		BranchNode branchNode) {

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		if (branchNode instanceof AndNode) {
			for (ExprNode exprNode : branchNode.getChildren()) {
				List<FilterConstraint> childFilterConstraints =
					toFilterConstraints(exprNode);

				if (childFilterConstraints.isEmpty()) {
					continue;
				}

				if (filterConstraints.isEmpty()) {
					filterConstraints = childFilterConstraints;
				}
				else {
					filterConstraints = FilterConstraint.getCombinations(
						filterConstraints, childFilterConstraints);
				}
			}

			return filterConstraints;
		}
		else if (branchNode instanceof NotNode) {
		}
		else if (branchNode instanceof OrNode) {
			for (ExprNode exprNode : branchNode.getChildren()) {
				List<FilterConstraint> childFilterConstraints =
					toFilterConstraints(exprNode);

				if (childFilterConstraints.isEmpty()) {
					continue;
				}

				filterConstraints.addAll(childFilterConstraints);
			}

			return filterConstraints;
		}

		if (_log.isWarnEnabled()) {
			_log.warn("Unsupported expression " + branchNode);
		}

		return filterConstraints;
	}

	protected List<FilterConstraint> toFilterConstraintsFromLeafNode(
		LeafNode leafNode) {

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		if (leafNode instanceof EqualityNode<?>) {
			EqualityNode<?> equalityNode = (EqualityNode<?>)leafNode;

			FilterConstraint filterConstraint = new FilterConstraint();

			Value value = equalityNode.getValue();

			filterConstraint.addAttribute(
				equalityNode.getAttribute(), value.getString());

			filterConstraints.add(filterConstraint);

			return filterConstraints;
		}
		else if (leafNode instanceof GreaterEqNode<?>) {
		}
		else if (leafNode instanceof LessEqNode<?>) {
		}
		else if (leafNode instanceof PresenceNode) {
			PresenceNode presenceNode = (PresenceNode)leafNode;

			FilterConstraint filterConstraint = new FilterConstraint();

			filterConstraint.addAttribute(presenceNode.getAttribute(), "*");

			filterConstraints.add(filterConstraint);

			return filterConstraints;
		}
		else if (leafNode instanceof SubstringNode) {
		}

		if (_log.isWarnEnabled()) {
			_log.warn("Unsupported expression " + leafNode);
		}

		return filterConstraints;
	}

	private static final Log _log = LogFactoryUtil.getLog(DirectoryTree.class);

	private final CommunitiesBuilder _communitiesBuilder =
		new CommunitiesBuilder();
	private final CommunityBuilder _communityBuilder = new CommunityBuilder();
	private final CompanyBuilder _companyBuilder = new CompanyBuilder();
	private final OrganizationBuilder _organizationBuilder =
		new OrganizationBuilder();
	private final OrganizationsBuilder _organizationsBuilder =
		new OrganizationsBuilder();
	private final RoleBuilder _roleBuilder = new RoleBuilder();
	private final RolesBuilder _rolesBuilder = new RolesBuilder();
	private final RootBuilder _rootBuilder = new RootBuilder();
	private final SambaGroupBuilder _sambaGroupBuilder =
		new SambaGroupBuilder();
	private final SambaMachineBuilder _sambaMachineBuilder =
		new SambaMachineBuilder();
	private final SchemaBuilder _schemaBuilder = new SchemaBuilder();
	private final TopBuilder _topBuilder = new TopBuilder();
	private final UserBuilder _userBuilder = new UserBuilder();
	private final UserGroupBuilder _userGroupBuilder = new UserGroupBuilder();
	private final UserGroupsBuilder _userGroupsBuilder =
		new UserGroupsBuilder();
	private final UsersBuilder _usersBuilder = new UsersBuilder();

}
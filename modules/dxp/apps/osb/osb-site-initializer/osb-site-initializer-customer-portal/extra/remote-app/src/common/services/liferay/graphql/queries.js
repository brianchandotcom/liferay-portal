/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {gql} from '@apollo/client';

export const getDXPCloudPageInfo = gql`
	query getDXPCloudPageInfo($accountSubscriptionsFilter: String) {
		c {
			accountSubscriptions(filter: $accountSubscriptionsFilter) {
				items {
					accountKey
					hasDisasterDataCenterRegion
					name
				}
			}
			dXPCDataCenterRegions {
				items {
					dxpcDataCenterRegionId
					name
					value
				}
			}
		}
	}
`;

export const getAccountSubscriptionsTerms = gql`
	query getAccountSubscriptionsTerms(
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
	) {
		c {
			accountSubscriptionTerms(
				filter: $filter
				page: $page
				pageSize: $pageSize
			) {
				items {
					accountKey
					accountSubscriptionERC
					accountSubscriptionGroupERC
					accountSubscriptionTermId
					c_accountSubscriptionTermId
					endDate
					instanceSize
					provisioned
					quantity
					startDate
					subscriptionTermStatus
				}
				totalCount
			}
		}
	}
`;

export const getStructuredContentFolders = gql`
	query getStructuredContentFolders($siteKey: String!, $filter: String) {
		structuredContentFolders(siteKey: $siteKey, filter: $filter) {
			items {
				id
				name
				structuredContents {
					items {
						friendlyUrlPath
						id
						key
					}
				}
			}
		}
	}
`;

export const getAccountSubscriptions = gql`
	query getAccountSubscriptions($filter: String) {
		c {
			accountSubscriptions(filter: $filter) {
				items {
					accountKey
					accountSubscriptionGroupERC
					accountSubscriptionId
					c_accountSubscriptionId
					endDate
					instanceSize
					name
					quantity
					startDate
					subscriptionStatus
				}
			}
		}
	}
`;

export const addAccountFlag = gql`
	mutation addAccountFlag($accountFlag: InputC_AccountFlag!) {
		c {
			createAccountFlag(AccountFlag: $accountFlag) {
				accountKey
				name
				finished
			}
		}
	}
`;

export const getBannedEmailDomains = gql`
	query getBannedEmailDomains(
		$aggregation: [String]
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
		$search: String
		$sort: String
	) {
		c {
			bannedEmailDomains(
				aggregation: $aggregation
				filter: $filter
				page: $page
				pageSize: $pageSize
				search: $search
				sort: $sort
			) {
				items {
					bannedEmailDomainId
					domain
				}
			}
		}
	}
`;

export const addDXPCloudEnvironment = gql`
	mutation addDXPCloudEnvironment(
		$scopeKey: String!
		$DXPCloudEnvironment: InputC_DXPCloudEnvironment!
	) {
		c {
			createDXPCloudEnvironment(
				scopeKey: $scopeKey
				DXPCloudEnvironment: $DXPCloudEnvironment
			) {
				dxpCloudEnvironmentId
				accountKey
				dataCenterRegion
				disasterDataCenterRegion
				projectId
			}
		}
	}
`;

export const getDXPCloudEnvironment = gql`
	query getDXPCloudEnvironment($scopeKey: String, $filter: String) {
		c {
			dXPCloudEnvironments(filter: $filter, scopeKey: $scopeKey) {
				items {
					projectId
				}
			}
		}
	}
`;

export const getAnalyticsCloudWorkspace = gql`
	query getAnalyticsCloudWorkspace($scopeKey: String, $filter: String) {
		c {
			analyticsCloudWorkspaces(filter: $filter, scopeKey: $scopeKey) {
				items {
					workspaceGroupId
				}
			}
		}
	}
`;

export const addAdminDXPCloud = gql`
	mutation addAdminDXPCloud(
		$scopeKey: String!
		$AdminDXPCloud: InputC_AdminDXPCloud!
	) {
		c {
			createAdminDXPCloud(
				scopeKey: $scopeKey
				AdminDXPCloud: $AdminDXPCloud
			) {
				emailAddress
				firstName
				githubUsername
				lastName
				dxpCloudEnvironmentId
			}
		}
	}
`;

export const addTeamMembersInvitation = gql`
	mutation addTeamMembersInvitation(
		$scopeKey: String!
		$TeamMembersInvitation: InputC_TeamMembersInvitation!
	) {
		c {
			createTeamMembersInvitation(
				scopeKey: $scopeKey
				TeamMembersInvitation: $TeamMembersInvitation
			) {
				email
				role
			}
		}
	}
`;

export const associateUserAccountWithAccountAndAccountRole = gql`
	mutation associateUserAccountWithAccountAndAccountRole(
		$emailAddress: String!
		$accountKey: String!
		$accountRoleId: Long!
	) {
		createAccountUserAccountByExternalReferenceCodeByEmailAddress(
			emailAddress: $emailAddress
			externalReferenceCode: $accountKey
		)
		createAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress(
			accountRoleId: $accountRoleId
			emailAddress: $emailAddress
			externalReferenceCode: $accountKey
		)
	}
`;

export const getAccountFlags = gql`
	query getAccountFlags($filter: String) {
		c {
			accountFlags(filter: $filter) {
				items {
					accountKey
					name
					finished
				}
			}
		}
	}
`;

export const getAccountRoles = gql`
	query getAccountRoles($accountId: Long!) {
		accountAccountRoles(accountId: $accountId) {
			items {
				id
				name
			}
		}
	}
`;

export const getAccountSubscriptionGroups = gql`
	query getAccountSubscriptionGroups(
		$aggregation: [String]
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
		$search: String
		$sort: String
	) {
		c {
			accountSubscriptionGroups(
				aggregation: $aggregation
				filter: $filter
				page: $page
				pageSize: $pageSize
				search: $search
				sort: $sort
			) {
				items {
					accountSubscriptionGroupId
					accountKey
					activationStatus
					name
				}
			}
		}
	}
`;

export const getKoroneikiAccounts = gql`
	query getKoroneikiAccounts($filter: String, $pageSize: Int = 20) {
		c {
			koroneikiAccounts(filter: $filter, pageSize: $pageSize) {
				items {
					accountKey
					code
					dxpVersion
					liferayContactEmailAddress
					liferayContactName
					liferayContactRole
					maxRequestors
					partner
					region
					slaCurrent
					slaCurrentEndDate
					slaCurrentStartDate
					slaExpired
					slaExpiredEndDate
					slaExpiredStartDate
					slaFuture
					slaFutureEndDate
					slaFutureStartDate
				}
			}
		}
	}
`;

export const getListTypeDefinitions = gql`
	query getListTypeDefinitions($filter: String) {
		listTypeDefinitions(filter: $filter) {
			items {
				listTypeEntries {
					key
					name
				}
			}
		}
	}
`;

export const getAccounts = gql`
	query getAccounts($pageSize: Int = 20) {
		accounts(pageSize: $pageSize) {
			items {
				externalReferenceCode
				name
			}
		}
	}
`;

export const getAccountByExternalReferenceCode = gql`
	query getAccountByExternalReferenceCode($externalReferenceCode: String!) {
		accountByExternalReferenceCode(
			externalReferenceCode: $externalReferenceCode
		) {
			id
			name
		}
	}
`;

export const getAccountUserAccountsByExternalReferenceCode = gql`
	query getAccountUserAccountsByExternalReferenceCode(
		$externalReferenceCode: String!
		$pageSize: Int = 20
	) {
		accountUserAccountsByExternalReferenceCode(
			externalReferenceCode: $externalReferenceCode
			pageSize: $pageSize
		) {
			items {
				id
				emailAddress
				lastLoginDate
				name
				accountBriefs {
					name
					externalReferenceCode
					roleBriefs {
						id
						name
					}
				}
			}
		}
	}
`;

export const getUserAccount = gql`
	query getUserAccount($id: Long!) {
		userAccount(userAccountId: $id) {
			accountBriefs {
				externalReferenceCode
				id
				name
				roleBriefs {
					id
					name
				}
			}
			externalReferenceCode
			id
			image
			name
			roleBriefs {
				name
			}
		}
	}
`;

export const updateAccountSubscriptionGroups = gql`
	mutation putAccountSubscriptionGroups(
		$id: Long!
		$accountSubscriptionGroup: InputC_AccountSubscriptionGroup!
	) {
		c {
			updateAccountSubscriptionGroup(
				accountSubscriptionGroupId: $id
				AccountSubscriptionGroup: $accountSubscriptionGroup
			) {
				accountSubscriptionGroupId
				accountKey
				activationStatus
				name
			}
		}
	}
`;

export const deleteAccountUserRoles = gql`
	mutation deleteAccountUserRoles(
		$accountRoleId: Long!
		$emailAddress: String!
		$accountKey: String!
	) {
		deleteAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress(
			accountRoleId: $accountRoleId
			emailAddress: $emailAddress
			externalReferenceCode: $accountKey
		)
	}
`;

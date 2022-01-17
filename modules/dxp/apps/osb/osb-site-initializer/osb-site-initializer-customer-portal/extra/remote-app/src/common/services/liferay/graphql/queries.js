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

export const getSetupDXPCloudInfo = gql`
	query getSetupDXPCloudInfo($accountSubscriptionsFilter: String) {
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

export const addSetupDXPCloud = gql`
	mutation addSetupDXPCloud(
		$scopeKey: String
		$SetupDXPCloud: InputC_SetupDXPCloud!
	) {
		c {
			createSetupDXPCloud(
				scopeKey: $scopeKey
				SetupDXPCloud: $SetupDXPCloud
			) {
				admins
				dataCenterRegion
				disasterDataCenterRegion
				projectId
			}
		}
	}
`;

export const addTeamMembersInvitation = gql`
	mutation addTeamMembersInvitation(
		$scopeKey: String
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
	query getAccountRoles($filter: Long!) {
		accountAccountRoles(accountId: $filter) {
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
					accountKey
					activationStatus
					name
				}
			}
		}
	}
`;

export const getKoroneikiAccounts = gql`
	query getKoroneikiAccounts($filter: String) {
		c {
			koroneikiAccounts(filter: $filter) {
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
					slaExpired
					slaFuture
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
			}
			externalReferenceCode
			id
			image
			name
		}
	}
`;

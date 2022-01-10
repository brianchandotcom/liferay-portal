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

export const getAccountRolesAndAccountFlags = gql`
	query getAccountRolesAndAccountFlags(
		$accountFlagsFilter: String
		$accountId: Long!
	) {
		accountAccountRoles(accountId: $accountId) {
			items {
				id
				name
			}
		}
		c {
			accountFlags(filter: $accountFlagsFilter) {
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
					accountKey
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

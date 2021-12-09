import {gql} from '@apollo/client';

export const getSetupDXPCloudInfo = gql`
	query getSetupDXPCloudInfo(
		$accountSubscriptionsFilter: String
		$koroneikiAccountsFilter: String
	) {
		c {
			dXPCDataCenterRegions {
				items {
					dxpcDataCenterRegionId
					name
					value
				}
			}
			accountSubscriptions(filter: $accountSubscriptionsFilter) {
				items {
					accountKey
					name
					hasDisasterDataCenterRegion
				}
			}
			koroneikiAccounts(filter: $koroneikiAccountsFilter) {
				items {
					accountKey
					code
					dxpVersion
					liferayContactEmailAddress
					liferayContactName
					liferayContactRole
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

export const getAccountSubscriptionsTerms = gql`
	query getAccountSubscriptionsTerms(
		$filter: String
		$page: Int
		$pageSize: Int
	) {
		c {
			accountSubscriptionTerms(
				filter: $filter
				page: $page
				pageSize: $pageSize
			) {
				totalCount
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
					accountSubscriptionId
					c_accountSubscriptionId
					endDate
					instanceSize
					quantity
					startDate
					name
					accountSubscriptionGroupERC
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
				accountFlagId
				accountKey
				name
				userUuid
				value
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
					accountKey
					code
					dxpVersion
					slaCurrent
					slaExpired
					slaFuture
					slaCurrentEndDate
					region
					liferayContactName
					liferayContactRole
					liferayContactEmailAddress
				}
			}
		}
	}
`;

export const addSetupDXPCloud = gql`
	mutation addSetupDXPCloud(
		$SetupDXPCloud: InputC_SetupDXPCloud!
		$scopeKey: String
	) {
		c {
			createSetupDXPCloud(
				SetupDXPCloud: $SetupDXPCloud
				scopeKey: $scopeKey
			) {
				admins
				dataCenterRegion
				disasterDataCenterRegion
				projectId
			}
		}
	}
`;

export const getAccountRolesAndAccountFlags = gql`
	query getAccountRolesAndAccountFlags(
		$accountId: Long!
		$accountFlagsFilter: String
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
					userUuid
				}
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
				id
				externalReferenceCode
				name
			}
			id
			externalReferenceCode
			image
			name
		}
	}
`;

export const getAccountSubscriptionsGroups = gql`
	query getAccountSubscriptionGroups($accountSubscriptionGroupERC: String) {
		c {
			accountSubscriptions(filter: $accountSubscriptionGroupERC) {
				items {
					name
					accountSubscriptionGroupERC
				}
			}
		}
	}
`;

import {gql} from '@apollo/client';

export const getSetupDXPInfo = gql`
	query getSetupDXPInfo(
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
				}
			}
			koroneikiAccounts(filter: $koroneikiAccountsFilter) {
				items {
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

export const getAccountSubscriptions = gql`
	query getAccountSubscriptions(
		$aggregation: [String]
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
		$search: String
		$sort: String
	) {
		c {
			accountSubscriptions(
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
				}
			}
		}
	}
`;

export const addSetupDXP = gql`
	mutation addSetupDXP($SetupDXP: InputC_SetupDXP!, $scopeKey: String) {
		c {
			createSetupDXP(SetupDXP: $SetupDXP, scopeKey: $scopeKey) {
				admins
				disasterDataCenterRegion
				dataCenterRegion
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
	query accountSubscriptionGroups(
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

export const getDXPCDataCenterRegions = gql`
	query getDXPCDataCenterRegions {
		c {
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

export const getKoroneikiAccounts = gql`
	query getKoroneikiAccounts($filter: String) {
		c {
			koroneikiAccounts(filter: $filter) {
				items {
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

export const getUserAccount = gql`
	query getUserAccount($id: Long!) {
		userAccount(userAccountId: $id) {
			id
			name
			image
			externalReferenceCode
			accountBriefs {
				id
				name
				externalReferenceCode
			}
		}
	}
`;

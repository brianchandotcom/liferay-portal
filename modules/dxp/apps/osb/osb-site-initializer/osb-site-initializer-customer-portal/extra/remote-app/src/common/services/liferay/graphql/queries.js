import {gql} from '@apollo/client';

export const accountSubscription = gql`
	query accountSubscriptions(
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
	mutation addAccountFlag($accountFlag: InputC_AccountFlag!){
  		c {
			createAccountFlag(
				AccountFlag: $accountFlag
			){
				accountFlagId
				accountKey
				name
				userUuid
				value
			}
		}
	}
`;

export const bannedEmailDomains = gql`
	query bannedEmailDomains(
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

export const createSetupDXP = gql`
	mutation createSetupDXP($SetupDXP: InputC_SetupDXP!, $scopeKey: String) {
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


export const getAccountSubscriptionGroupsByFilter = gql`
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
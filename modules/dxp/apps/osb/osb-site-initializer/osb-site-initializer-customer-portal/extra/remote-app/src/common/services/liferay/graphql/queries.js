import {gql} from '@apollo/client';

export const getUserAccountById = gql`
	query userAccount($userAccountId: Long) {
		userAccount(userAccountId: $userAccountId) {
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

export const getDXPCDataCenterRegions = gql`
	query dXPCDataCenterRegions {
		c {
			dXPCDataCenterRegions {
				items {
					dxpcDataCenterRegionId
					name
				}
			}
		}
	}
`;

export const getKoroneikiAccounts = gql`
	query koroneikiAccounts(
		$aggregation: [String]
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
		$search: String
		$sort: String
	) {
		c {
			koroneikiAccounts(
				aggregation: $aggregation
				filter: $filter
				page: $page
				pageSize: $pageSize
				search: $search
				sort: $sort
			) {
				items {
					accountKey
					code
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

export const pageGuard = gql`
	query accountRolesAndAccountFlags(
		$accountId: Long
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

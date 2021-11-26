import { gql } from '@apollo/client';

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

export const getDXPCDataCenterRegions = gql`
	query getDXPCDataCenterRegions {
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
	query getKoroneikiAccounts($filter: String) {
		c {
			koroneikiAccounts(filter: $filter) {
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
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

import {gql, useQuery} from '@apollo/client';
import {CORE_KORONEIKI_ACCOUNT_FIELDS} from '../fragments/coreKoroneikiAccountFields';

const GET_KORONEIKI_ACCOUNT_BY_EXTERNAL_REFERENCE_CODE = gql`
	${CORE_KORONEIKI_ACCOUNT_FIELDS}
	query getKoroneikiAccountByExternalReferenceCode(
		$externalReferenceCode: String
	) {
		koroneikiAccountByExternalReferenceCode(
			externalReferenceCode: $externalReferenceCode
		)
			@rest(
				type: "C_KoroneikiAccount"
				path: "/c/koroneikiaccounts/by-external-reference-code/{args.externalReferenceCode}"
			) {
			...CoreKoroneikiAccountFields
			r_accountEntryToKoroneikiAccount_accountEntryId
		}
	}
`;

export function useGetKoroneikiAccountByExternalReferenceCode(
	externalReferenceCode,
	options = {
		notifyOnNetworkStatusChange: false,
		skip: false,
	}
) {
	return useQuery(GET_KORONEIKI_ACCOUNT_BY_EXTERNAL_REFERENCE_CODE, {
		context: {
			type: 'liferay-rest',
		},
		fetchPolicy: 'cache-and-network',
		nextFetchPolicy: 'cache-first',
		notifyOnNetworkStatusChange: options.notifyOnNetworkStatusChange,
		skip: options.skip,
		variables: {
			externalReferenceCode,
		},
	});
}

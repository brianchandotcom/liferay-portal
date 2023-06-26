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

import {gql, useMutation} from '@apollo/client';

const UPDATE_ROLES_BY_CONTACT_EMAIL_ADDRESS = gql`
	mutation updateRolesByContactEmailAddress(
		$externalReferenceCode: String!
		$contactEmail: String!
		$contactRoleName: String!
	) {
		updateRolesByContactEmailAddress(
			externalReferenceCode: $externalReferenceCode
			contactEmail: $contactEmail
			contactRoleName: $contactRoleName
			input: {}
		)
			@rest(
				type: "R_ContactRole"
				path: "/accounts/{args.externalReferenceCode}/contacts/by-email-address/{args.contactEmail}/roles?{args.contactRoleName}"
				method: "PUT"
			) {
			NoResponse
		}
	}
`;

export function useUpdateRolesByContactEmailAddress() {
	return useMutation(UPDATE_ROLES_BY_CONTACT_EMAIL_ADDRESS, {
		context: {
			displayServerError: true,
			displaySuccess: false,
			type: 'raysource-rest',
		},
	});
}

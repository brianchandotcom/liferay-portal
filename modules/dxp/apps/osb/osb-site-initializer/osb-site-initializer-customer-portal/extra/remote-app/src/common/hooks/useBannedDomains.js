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

import {useLazyQuery} from '@apollo/client';
import {useEffect, useState} from 'react';
import {getBannedEmailDomains} from '../services/liferay/graphql/queries';
import useDebounce from './useDebounce';

export default function useBannedDomains(email) {
	const debouncedEmail = useDebounce(email, 500);
	const [bannedDomains, setBannedDomains] = useState(debouncedEmail);

	const [fetchBannedDomain, {data}] = useLazyQuery(getBannedEmailDomains);
	const bannedDomainsItems = data?.c?.bannedEmailDomains?.items;

	useEffect(() => {
		const [, emailDomain] = debouncedEmail?.split('@');

		if (emailDomain) {
			fetchBannedDomain({
				variables: {
					filter: `domain eq '${emailDomain}'`,
				},
			});

			if (bannedDomainsItems?.length) {
				setBannedDomains(bannedDomainsItems[0].domain);
			}
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [bannedDomainsItems, debouncedEmail]);

	return bannedDomains;
}

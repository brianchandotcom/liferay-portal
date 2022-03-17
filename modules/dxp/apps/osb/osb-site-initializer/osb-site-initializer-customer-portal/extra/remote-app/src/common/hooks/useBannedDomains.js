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

export default function useBannedDomains(value) {
	const debouncedValue = useDebounce(value, 500);
	const [bannedDomains, setBannedDomains] = useState([]);

	const [fetchBannedDomain, {data}] = useLazyQuery(getBannedEmailDomains);
	const bannedDomainsItems = data?.c?.bannedEmailDomains?.items;

	useEffect(() => {
		let filterDomains = '';
		const splittedDomains = debouncedValue.split(',');

		if (splittedDomains.length > 1) {
			filterDomains = splittedDomains.reduce(
				(accumulatorFilter, domain, index) => {
					return `${accumulatorFilter}${
						index > 0 ? ' or ' : ''
					}domain eq '${domain.replace('@', '').trim()}'`;
				},
				''
			);
		}
		else {
			const [, emailDomain] = debouncedValue?.split('@');

			if (emailDomain) {
				filterDomains = `domain eq '${emailDomain}'`;
			}
		}

		if (filterDomains) {
			fetchBannedDomain({
				variables: {
					filter: filterDomains,
				},
			});
		}
		else {
			setBannedDomains([]);
		}

		setBannedDomains(bannedDomainsItems?.map((item) => item.domain) || []);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [bannedDomainsItems, debouncedValue, fetchBannedDomain]);

	return bannedDomains;
}

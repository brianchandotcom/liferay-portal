/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useContext} from 'react';
import {Outlet, useParams} from 'react-router-dom';
import {KeyedMutator} from 'swr';
import PageRenderer from '~/components/PageRenderer';

import {TestrayContext} from '../../../context/TestrayContext';
import {useFetch} from '../../../hooks/useFetch';
import {Liferay} from '../../../services/liferay';
import {UserAccount, liferayUserAccountsImpl} from '../../../services/rest';

const UserOutlet = () => {
	const {userId} = useParams();

	const [{myUserAccount}, , mutateMyUserAccount] = useContext(TestrayContext);

	const {data: userAccount, error, isValidating, loading, mutate} = useFetch(
		liferayUserAccountsImpl.getResource(userId as string),
		{
			swrConfig: {shouldFetch: !!userId},
		}
	);

	return (
		<PageRenderer error={error} loading={isValidating || loading}>
			<Outlet
				context={{
					actions: userAccount?.actions
						? {
								...userAccount?.actions,
								replace:
									userAccount?.actions['patch-user-account'],
								update:
									userAccount?.actions['put-user-account'],
						  }
						: {
								replace: true,
						  },
					mutateUser: userId
						? userId === Liferay.ThemeDisplay.getUserId()
							? (response: KeyedMutator<UserAccount>) => {
									(mutateMyUserAccount as any)(response);
									mutate(response);
							  }
							: mutate
						: mutateMyUserAccount,
					userAccount: userId ? userAccount : myUserAccount,
				}}
			/>
		</PageRenderer>
	);
};
export default UserOutlet;

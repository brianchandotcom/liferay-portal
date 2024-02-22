/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import {ApolloClient} from '@apollo/client';
import {useMemo} from 'react';
import useSWR from 'swr';
import i18n from '~/common/I18n';
import {useAppPropertiesContext} from '~/common/contexts/AppPropertiesContext';
import SearchBuilder from '~/common/core/SearchBuilder';
import {Liferay} from '~/common/services/liferay';
import {getOrganizations} from '~/common/services/liferay/graphql/queries';

type Account = {
	externalReferenceCode: string;
	id: string;
	name: string;
};

const getMyUserAccount = async () => {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`/o/headless-admin-user/v1.0/my-user-account`,
		{
			headers: {
				'x-csrf-token': Liferay.authToken,
			},
		}
	);

	const data = await response.json();

	return data as Account & {
		accountBriefs: Account &
			{
				externalReferenceCode: string;
				roleBriefs: {externalReferenceCode: string; name: string}[];
			}[];
		organizationBriefs: Account[];
	};
};

const getFLSOrganizationsAccounts = (client: ApolloClient<any>) => async ({
	organizationIds,
}: {
	organizationIds: number[];
}) => {
	if (!organizationIds.length) {
		return [];
	}

	const response = await client.query({
		query: getOrganizations,
		variables: {
			filter: SearchBuilder.in('id', organizationIds),
		},
	});

	const organizations = (response.data?.organizations?.items ?? []) as {
		accounts: {
			items: Account[];
		};
	}[];

	const organizationAccounts = [
		...new Set(organizations.map(({accounts}) => accounts.items).flat()),
	];

	return organizationAccounts as Account[];
};

const useProjectCategoryItems = () => {
	const {client} = useAppPropertiesContext();

	const {
		data: myUserAccount = {accountBriefs: [], organizationBriefs: []},
	} = useSWR({key: '/projects'}, getMyUserAccount);

	const myFLSOrganizationBriefIds = useMemo(
		() =>
			myUserAccount?.organizationBriefs
				?.filter(({name}) => name.includes('FLS'))
				.map(({id}) => id),
		[myUserAccount?.organizationBriefs]
	);

	const {data: organizations = []} = useSWR(
		{
			key: '/organizations',
			organizationIds: myFLSOrganizationBriefIds,
		},
		myUserAccount ? getFLSOrganizationsAccounts(client) : null
	);

	const projectCategoryItems = useMemo(() => {
		const teamMembersERC = myUserAccount?.accountBriefs?.map(
			({externalReferenceCode}: any) => externalReferenceCode
		);

		const liferayContactERC = myUserAccount.accountBriefs
			?.filter(({roleBriefs}) =>
				roleBriefs.some(
					(roleBrief) => roleBrief.name === 'Provisioning'
				)
			)
			.map(({externalReferenceCode}) => externalReferenceCode);

		const organizationProjectsERC = organizations.map(
			({externalReferenceCode}) => externalReferenceCode
		);

		return [
			{
				disabled: !teamMembersERC?.length,
				filter: (searchBuilder: SearchBuilder) =>
					searchBuilder.in('externalReferenceCode', teamMembersERC),
				key: 'team-member',
				label: i18n.translate('team-member'),
			},
			{
				disabled: !liferayContactERC?.length,
				filter: (searchBuilder: SearchBuilder) =>
					searchBuilder.in(
						'externalReferenceCode',
						liferayContactERC
					),
				key: 'liferay-contact',
				label: i18n.translate('liferay-contact'),
			},
			{
				disabled: !organizationProjectsERC.length,
				filter: (searchBuilder: SearchBuilder) =>
					searchBuilder.in(
						'externalReferenceCode',
						organizationProjectsERC
					),
				key: 'fls-partner',
				label: i18n.translate('fls-partner'),
			},
			{
				disabled: false,
				filter: (searchBuilder: SearchBuilder) => searchBuilder,
				key: 'all-projects',
				label: i18n.translate('all-projects'),
			},
		].filter(({disabled}) => !disabled);
	}, [myUserAccount, organizations]);

	return projectCategoryItems;
};

export default useProjectCategoryItems;

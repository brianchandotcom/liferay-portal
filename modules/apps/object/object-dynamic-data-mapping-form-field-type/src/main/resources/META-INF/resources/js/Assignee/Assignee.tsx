/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Autocomplete from '@clayui/autocomplete';
import {FetchPolicy, useResource} from '@clayui/data-provider';
import {useConfig} from 'data-engine-js-components-web';
import {ReactFieldBase as FieldBase} from 'dynamic-data-mapping-form-field-type/api';
import React, {useState} from 'react';

import Option from './Option';

import './Assignee.scss';

interface AssigneeValue {
	externalReferenceCode: string;
	name: string;
	type: string;
}

interface Assignee {
	label: string;
	name: string;
	onChange: (event: {target: {value: any}}) => void;
	readOnly?: boolean;
	searchURL: string;
	value?: AssigneeValue;
}

export default function Assignee({
	label,
	name,
	onChange,
	readOnly,
	searchURL,
	value,
	...otherProps
}: Assignee) {
	const {portletNamespace} = useConfig();

	const [networkStatus, setNetworkStatus] = useState(4);
	const [search, setSearch] = useState(value?.name ?? '');

	const {resource} = useResource({
		fetchOptions: {
			credentials: 'include',
			headers: new Headers({'x-csrf-token': Liferay.authToken}),
			method: 'GET',
		},
		fetchPolicy: FetchPolicy.CacheFirst,
		link: searchURL,
		onNetworkStatusChange: setNetworkStatus,
		variables: {
			[`${portletNamespace}search`]: search,
		},
	});

	return (
		<FieldBase
			accessible={false}
			label={label}
			readOnly={readOnly}
			{...otherProps}
		>
			<Autocomplete
				aria-label={label}
				defaultValue={value?.name ?? ''}
				disabled={readOnly}
				items={
					resource
						? resource.items.filter((item: any) => !!item.embedded)
						: []
				}
				loadingState={networkStatus}
				menuTrigger="focus"
				messages={{
					loading: Liferay.Language.get('loading...'),
					notFound: Liferay.Language.get('no-results-found'),
				}}
				onChange={(item: string) => {
					setSearch(item);
				}}
				onItemsChange={() => {}}
				value={search}
			>
				{(item: {
					embedded: {
						externalReferenceCode: string;
						image?: string;
						name: string;
					};
					entryClassName: string;
				}) => (
					<Autocomplete.Item
						key={item.embedded.name}
						onClick={() => {
							onChange({
								target: {
									value: {
										externalReferenceCode:
											item.embedded.externalReferenceCode,
										name: item.embedded.name,
										type: item.entryClassName
											.split('.')
											.pop(),
									},
								},
							});
						}}
						textValue={item.embedded.name}
					>
						<Option
							image={item.embedded.image}
							name={item.embedded.name}
						/>
					</Autocomplete.Item>
				)}
			</Autocomplete>

			<input name={name} type="hidden" value={JSON.stringify(value)} />
		</FieldBase>
	);
}

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
	onChange?: (event: {target: {value: any}}) => void;
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
	value: initialValue,
	...otherProps
}: Assignee) {
	const {portletNamespace} = useConfig();

	const [networkStatus, setNetworkStatus] = useState(4);
	const [search, setSearch] = useState(initialValue?.name ?? '');
	const [value, setValue] = useState<AssigneeValue | null>(
		initialValue ?? null
	);

	const {
		resource,
	}: {
		resource: {
			items: {
				externalReferenceCode: string;
				image?: string;
				name: string;
				type: string;
			}[];
		};
	} = useResource({
		fetchOptions: {
			credentials: 'include',
			headers: new Headers({'x-csrf-token': Liferay.authToken}),
			method: 'GET',
		},
		fetchPolicy: FetchPolicy.CacheFirst,
		link: searchURL,
		onNetworkStatusChange: setNetworkStatus,
		variables: {
			[`${portletNamespace ?? ''}search`]: search,
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
				filterKey="name"
				items={resource ? resource.items : []}
				loadingState={networkStatus}
				menuTrigger="focus"
				messages={{
					loading: Liferay.Language.get('loading...'),
					notFound: Liferay.Language.get('no-results-found'),
				}}
				onChange={(item: string) => {
					if (!item && onChange) {
						onChange({
							target: {
								value: null,
							},
						});
					}

					setSearch(item);
				}}
				onItemsChange={() => {}}
				value={search}
			>
				{({externalReferenceCode, image, name, type}) => (
					<Autocomplete.Item
						key={name}
						onClick={() => {
							if (onChange) {
								onChange({
									target: {
										value: {
											externalReferenceCode,
											name,
											type,
										},
									},
								});
							}

							setValue({
								externalReferenceCode,
								name,
								type,
							});
						}}
						textValue={name}
					>
						<Option image={image} name={name} />
					</Autocomplete.Item>
				)}
			</Autocomplete>

			<input name={name} type="hidden" value={JSON.stringify(value)} />
		</FieldBase>
	);
}

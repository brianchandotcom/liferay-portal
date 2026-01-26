/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Autocomplete from '@clayui/autocomplete';
import {FetchPolicy, useResource} from '@clayui/data-provider';
import {useConfig} from 'data-engine-js-components-web';
import {ReactFieldBase as FieldBase} from 'dynamic-data-mapping-form-field-type/api';
import React, {useMemo, useState} from 'react';

import './Assignee.scss';
import AssigneeTrigger, {AssigneeTriggerProps} from './AssigneeTrigger';
import Option from './Option';

export interface AssigneeValue {
	externalReferenceCode: string;
	image?: string;
	name: string;
	type: string;
}

interface AssigneeProps {
	customClasses?: string;
	label?: string;
	name: string;
	onChange?: (value: AssigneeValue | {}) => void;
	readOnly?: boolean;
	searchURL: string;
	showLabel?: boolean;
	triggerComponent?: React.ComponentType<AssigneeTriggerProps>;
	value?: AssigneeValue | null | {};
	visible?: boolean;
}

export default function Assignee({
	customClasses,
	label,
	name,
	onChange,
	readOnly,
	searchURL,
	triggerComponent: CustomTrigger,
	value: initialValue,
	...otherProps
}: AssigneeProps) {
	const {portletNamespace} = useConfig();

	const [networkStatus, setNetworkStatus] = useState(4);
	const [search, setSearch] = useState(
		initialValue && 'name' in initialValue ? initialValue.name : ''
	);
	const [value, setValue] = useState<AssigneeValue | null | {}>(
		initialValue ?? null
	);

	const {resource}: any = useResource({
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

	const TargetTrigger = CustomTrigger || AssigneeTrigger;

	const TriggerWrapper = useMemo(
		() =>
			React.forwardRef((props: any, ref) => (
				<TargetTrigger {...props} ref={ref} selectedItem={value} />
			)),
		[value, TargetTrigger]
	);

	return (
		<FieldBase
			accessible={false}
			hideEditedFlag
			label={label}
			readOnly={readOnly}
			{...otherProps}
		>
			<Autocomplete
				{...otherProps}
				aria-label={label}
				as={TriggerWrapper}
				customClasses={customClasses}
				disabled={readOnly}
				filterKey="name"
				items={resource?.items ?? []}
				loadingState={networkStatus}
				menuTrigger="focus"
				messages={{
					loading: Liferay.Language.get('loading...'),
					notFound: Liferay.Language.get('no-results-found'),
				}}
				onBlur={() => {
					if (!search && value && 'name' in value) {
						setValue({});

						if (onChange) {
							onChange({});
						}
					}
				}}
				onChange={(item: string) => {
					setSearch(item);
				}}
				onItemsChange={() => {}}
				value={search}
			>
				{(item: {
					externalReferenceCode: string;
					image: string;
					name: string;
					type: string;
				}) => {
					return (
						<Autocomplete.Item
							key={item.name}
							onClick={() => {
								if (onChange) {
									onChange(item);
								}

								setValue(item);
								setSearch(item.name);
							}}
							textValue={item.name}
						>
							<Option image={item.image} name={item.name} />
						</Autocomplete.Item>
					);
				}}
			</Autocomplete>

			<input name={name} type="hidden" value={JSON.stringify(value)} />
		</FieldBase>
	);
}

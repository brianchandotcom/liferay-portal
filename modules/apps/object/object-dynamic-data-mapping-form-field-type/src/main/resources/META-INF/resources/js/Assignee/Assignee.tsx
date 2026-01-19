/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Autocomplete from '@clayui/autocomplete';
import {FetchPolicy, useResource} from '@clayui/data-provider';
import {useConfig} from 'data-engine-js-components-web';
import {ReactFieldBase as FieldBase} from 'dynamic-data-mapping-form-field-type/api';
import React, {Ref, useMemo, useState} from 'react';

import './Assignee.scss';
import Option from './Option';

export interface AssigneeValue {
	externalReferenceCode: string;
	image?: string;
	name: string;
	type: string;
}

export interface AssigneeTriggerProps
	extends Omit<React.InputHTMLAttributes<HTMLInputElement>, 'ref'> {
	className?: string;
	ref: Ref<HTMLInputElement>;
	selectedItem?: AssigneeValue | null | {};
}

interface AssigneeProps {
	label?: string;
	name: string;
	onChange?: (event: {target: {value: AssigneeValue | {}}}) => void;
	readOnly?: boolean;
	searchURL: string;
	showLabel?: boolean;
	triggerClassName?: string;
	triggerComponent?: React.ComponentType<AssigneeTriggerProps>;
	value?: AssigneeValue | null | {};
	visible?: boolean;
}

export default function Assignee({
	label,
	name,
	onChange,
	readOnly,
	searchURL,
	triggerClassName,
	triggerComponent: AssigneeTrigger,
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

	const TriggerWrapper = useMemo(() => {
		if (!AssigneeTrigger) {
			return undefined;
		}

		return React.forwardRef(
			(props: AssigneeTriggerProps, ref: Ref<HTMLInputElement>) => (
				<AssigneeTrigger
					{...props}
					className={triggerClassName}
					ref={ref}
					selectedItem={value}
				/>
			)
		);
	}, [AssigneeTrigger, triggerClassName, value]);

	return (
		<FieldBase
			accessible={false}
			hideEditedFlag
			label={label}
			name={name}
			readOnly={readOnly}
			{...otherProps}
		>
			<Autocomplete
				{...(TriggerWrapper && {
					as: TriggerWrapper,
				})}
				aria-label={label}
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
							onChange({target: {value: {}}});
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
					image?: string;
					name: string;
					type: string;
				}) => {
					return (
						<Autocomplete.Item
							key={item.name}
							onClick={() => {
								if (onChange) {
									onChange({
										target: {
											value: {
												externalReferenceCode:
													item.externalReferenceCode,
												name: item.name,
												type: item.type,
											},
										},
									});
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

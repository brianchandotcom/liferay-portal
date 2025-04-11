/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Autocomplete from '@clayui/autocomplete';
import ClayForm from '@clayui/form';
import {openToast} from 'frontend-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useMemo, useState} from 'react';

interface IListTypeEntry {
	id: number;
	key: string;
	name: string;
}

interface IProps {
	addressSubtypeConfiguration: {
		billing: string;
		billingAndShipping: string;
		shipping: string;
	};
	initialAddressType: string;
	initialValue: string;
	namespace: string;
}

function AddressSubtypeAutocomplete({
	addressSubtypeConfiguration = {
		billing: '',
		billingAndShipping: '',
		shipping: '',
	},
	initialAddressType,
	initialValue = '',
	namespace,
}: IProps) {
	const [addressType, setAddressType] = useState(initialAddressType);
	const [currentValue, setCurrentValue] = useState(initialValue);
	const [externalReferenceCode, setExternalReferenceCode] =
		useState<string>();
	const [subtypes, setSubtypes] = useState<Array<IListTypeEntry>>([]);

	useEffect(() => {
		if (addressType === 'billing') {
			setExternalReferenceCode(addressSubtypeConfiguration.billing);

			return;
		}
		else if (addressType === 'shipping') {
			setExternalReferenceCode(addressSubtypeConfiguration.shipping);

			return;
		}

		setExternalReferenceCode(
			addressSubtypeConfiguration.billingAndShipping
		);
	}, [
		addressSubtypeConfiguration.billing,
		addressSubtypeConfiguration.billingAndShipping,
		addressSubtypeConfiguration.shipping,
		addressType,
	]);

	useEffect(() => {
		if (!externalReferenceCode) {
			setSubtypes([]);

			return;
		}

		fetch(
			`/o/headless-admin-list-type/v1.0/list-type-definitions/by-external-reference-code/${externalReferenceCode}/list-type-entries?pageSize=-1`,
			{
				method: 'GET',
			}
		)
			.then(async (data: Response) => {
				setSubtypes((await data.json()).items as Array<IListTypeEntry>);
			})
			.catch((error: any) => {
				setCurrentValue('');
				setSubtypes([]);

				openToast({
					message:
						error.detail ||
						error.errorDescription ||
						Liferay.Language.get(
							'an-unexpected-system-error-occurred'
						),
					type: 'danger',
				});
			});
	}, [externalReferenceCode]);

	useEffect(() => {
		const addressTypeSelect = document.getElementById(
			`${namespace}addressListTypeId`
		) as HTMLSelectElement;

		const onAddressTypeChange = (event: Event) => {
			setAddressType(
				Array.from(
					(event.target as any).children as HTMLOptionsCollection
				).filter(
					(item) => item.value === (event.target as any).value
				)[0].dataset.listtypekey as string
			);
			setCurrentValue('');
		};

		if (addressTypeSelect) {
			addressTypeSelect.addEventListener('change', onAddressTypeChange);
		}

		return () => {
			addressTypeSelect.removeEventListener(
				'change',
				onAddressTypeChange
			);
		};
	}, [namespace]);

	const filteredSubtypeItems = useMemo(
		() =>
			subtypes.filter(
				(item) =>
					item.name.match(new RegExp(currentValue, 'i')) !== null
			),
		[currentValue, subtypes]
	);

	return addressSubtypeConfiguration.billing ||
		addressSubtypeConfiguration.billingAndShipping ||
		addressSubtypeConfiguration.shipping ? (
		<ClayForm.Group>
			<label htmlFor="infoBoxModalAddressSubtypeInput">
				{Liferay.Language.get('subtype')}
			</label>

			<input
				name={`${namespace}subtype`}
				type="hidden"
				value={currentValue}
			/>

			<Autocomplete
				aria-label={Liferay.Language.get('subtype')}
				className="mb-3"
				defaultValue={initialValue || ''}
				disabled={!externalReferenceCode}
				filterKey="name"
				id="addressSubtypeAutocomplete"
				items={filteredSubtypeItems}
				menuTrigger="focus"
				name={`${namespace}addressSubtype`}
				onChange={(value: string) => {
					setCurrentValue(value);
				}}
				onItemsChange={() => {}}
				placeholder={Liferay.Language.get('subtype')}
				value={
					subtypes.find((item) => item.key === currentValue)?.name ||
					currentValue
				}
			>
				{(item) => (
					<Autocomplete.Item key={item.key} textValue={item.key}>
						<div>{item.name}</div>
					</Autocomplete.Item>
				)}
			</Autocomplete>
		</ClayForm.Group>
	) : null;
}

export default AddressSubtypeAutocomplete;

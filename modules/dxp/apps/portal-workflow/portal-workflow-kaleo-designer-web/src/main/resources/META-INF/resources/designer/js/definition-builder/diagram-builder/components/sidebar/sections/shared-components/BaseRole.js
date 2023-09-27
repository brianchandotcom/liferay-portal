/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAutocomplete from '@clayui/autocomplete';
import {useResource} from '@clayui/data-provider';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {contextUrl} from '../../../../../constants';
import {headers, userBaseURL} from '../../../../../util/fetchUtil';

export default function BaseRole({
	defaultFieldValue = {id: '', name: ''},
	inputLabel,
	selectLabel,
	updateSelectedItem,
}) {
	const [active, setActive] = useState(false);
	const [filter, setFilter] = useState(true);
	const [fieldValues, setFieldValues] = useState(defaultFieldValue);
	const [networkStatus, setNetworkStatus] = useState(4);
	const {resource} = useResource({
		fetchOptions: {
			headers: {
				...headers,
				'accept': `application/json`,
				'x-csrf-token': Liferay.authToken,
			},
		},
		fetchPolicy: 'cache-first',
		link: `${window.location.origin}${contextUrl}${userBaseURL}/roles`,
		onNetworkStatusChange: setNetworkStatus,
		variables: {
			pageSize: -1,
		},
	});

	useEffect(() => {
		if (defaultFieldValue.name !== '') {
			setFieldValues((previousValues) => {
				const updatedValues = {...previousValues};
				updatedValues.name = defaultFieldValue.name;

				return updatedValues;
			});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [defaultFieldValue]);

	const loading = networkStatus < 4;
	const error = networkStatus === 5;

	const handleInputFocus = () => {
		setFilter(fieldValues.name === '');
		setActive(true);
	};

	const handleInputChange = (event) => {
		event.persist();
		setFilter(true);
		setFieldValues((previousValues) => ({
			...previousValues,
			name: event.target.value,
		}));
	};

	const filterItems = () =>
		resource.items.filter((item) =>
			!filter
				? item
				: item.name.toLowerCase().match(fieldValues.name.toLowerCase())
		);

	const handleItemClick = (item) => {
		setFieldValues({id: item.id, name: item.name});
		setActive(false);

		updateSelectedItem(item);
	};

	return (
		<>
			<ClayForm.Group>
				<ClayAutocomplete>
					<label htmlFor="role-name">{selectLabel}</label>

					<ClayAutocomplete.Input
						autoComplete="off"
						id="role-name"
						onChange={(event) => handleInputChange(event)}
						onFocus={() => handleInputFocus()}
						placeholder="Search"
						value={fieldValues.name}
					/>

					<ClayAutocomplete.DropDown
						active={!!resource && active}
						closeOnClickOutside
						onSetActive={setActive}
					>
						<ClayDropDown.ItemList>
							{(error || (resource && resource.error)) && (
								<ClayDropDown.Item className="disabled">
									{Liferay.Language.get('no-results-found')}
								</ClayDropDown.Item>
							)}

							{!error &&
								resource?.items &&
								filterItems().map((item) => (
									<ClayAutocomplete.Item
										key={item.id}
										onClickCapture={() => {
											handleItemClick(item);
										}}
										value={item.name}
									/>
								))}
						</ClayDropDown.ItemList>
					</ClayAutocomplete.DropDown>

					{loading && <ClayAutocomplete.LoadingIndicator />}
				</ClayAutocomplete>
			</ClayForm.Group>
			<ClayForm.Group>
				<label htmlFor="role-id">
					{inputLabel}

					<span className="ml-1 mr-1 text-warning">*</span>
				</label>

				<ClayInput
					component="input"
					disabled
					id="role-id"
					type="text"
					value={fieldValues.id}
				/>
			</ClayForm.Group>
		</>
	);
}

BaseRole.propTypes = {
	inputLabel: PropTypes.string.isRequired,
	selectLabel: PropTypes.string.isRequired,
};

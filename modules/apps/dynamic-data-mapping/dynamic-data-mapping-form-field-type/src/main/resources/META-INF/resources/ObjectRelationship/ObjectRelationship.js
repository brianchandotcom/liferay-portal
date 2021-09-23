/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayAutocomplete from '@clayui/autocomplete';
import {useResource} from '@clayui/data-provider';
import ClayDropDown from '@clayui/drop-down';
import {useDebounce} from '@clayui/shared';
import {FieldBase} from 'dynamic-data-mapping-form-field-type/FieldBase/ReactFieldBase.es';
import {fetch} from 'frontend-js-web';
import React from 'react';

const NETWORK_STATUS_LOADING = 1;
const NETWORK_STATUS_UNUSED = 4;

const LoadingWithDebounce = ({loading, render}) => {
	const debouncedLoadingChange = useDebounce(loading, 500);

	if (loading || debouncedLoadingChange) {
		return (
			<ClayDropDown.Item className="disabled">
				{Liferay.Language.get('loading')}
			</ClayDropDown.Item>
		);
	}

	return render;
};

export function ObjectRelationship({
	apiURL,
	inputName,
	labelKey = 'label',
	initialLabel,
	initialValue,
	name,
	onBlur = () => {},
	onChange,
	onFocus = () => {},
	placeholder = Liferay.Language.get('search'),
	predefinedValue,
	readOnly,
	required,
	value,
	valueKey = 'value',
	...otherProps
}) {
	const mutatedRef = React.useRef(false);

	const [networkStatus, setNetworkStatus] = React.useState(
		NETWORK_STATUS_LOADING
	);

	const {resource} = useResource({
		fetch,
		fetchPolicy: 'cache-first',
		link: apiURL,
		onNetworkStatusChange: setNetworkStatus,
		variables: {
			page: 1,
			pageSize: 10,
			search: value,
		},
	});

	const loading = networkStatus < NETWORK_STATUS_UNUSED;

	value = mutatedRef.current ? value : initialValue || predefinedValue;

	return (
		<FieldBase
			name={name}
			onChange={onChange}
			readOnly={readOnly}
			required={required}
			value={value}
			{...otherProps}
		>
			<ClayAutocomplete>
				<ClayAutocomplete.Input
					name={inputName}
					onBlur={onBlur}
					onChange={(event) => {
						mutatedRef.current = true;

						onChange(event, event.target.value);
					}}
					onFocus={onFocus}
					placeholder={placeholder}
					readOnly={readOnly}
					required={required}
					value={mutatedRef.current ? value : initialLabel}
				/>

				<ClayAutocomplete.DropDown
					active={readOnly ? false : !!resource && !!value}
				>
					<ClayDropDown.ItemList>
						<LoadingWithDebounce
							loading={loading}
							render={
								<>
									{resource?.items?.length === 0 && (
										<ClayDropDown.Item className="disabled">
											{Liferay.Language.get(
												'no-results-found'
											)}
										</ClayDropDown.Item>
									)}
									{resource?.items?.map((item) => (
										<ClayAutocomplete.Item
											key={item.id}
											match={String(value)}
											onClick={(event) =>
												onChange(
													event,
													String(item[valueKey])
												)
											}
											value={String(item[labelKey])}
										/>
									))}
								</>
							}
						/>
					</ClayDropDown.ItemList>
				</ClayAutocomplete.DropDown>
				{loading && <ClayAutocomplete.LoadingIndicator />}
			</ClayAutocomplete>
		</FieldBase>
	);
}

export default ObjectRelationship;

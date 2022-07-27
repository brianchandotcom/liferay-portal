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
import ClayDropDown from '@clayui/drop-down';
import {useEffect, useMemo, useState} from 'react';

import useDebounce from '../../../hooks/useDebounce';
import {useFetch} from '../../../hooks/useFetch';

export type AutoCompleteProps = {
	gqlVariables?: any;
	label?: string;
	objectName: string;
	onSearch: (keyword: string) => any;
	resource: string;
	transformData?: (item: any) => any;
};

const AutoComplete: React.FC<AutoCompleteProps> = ({
	label,
	onSearch,
	resource,
	transformData,
}) => {
	const [showValue, setShowValue] = useState('');
	const [value, setValue] = useState('');
	const [active, setActive] = useState(false);

	const [data, setTest] = useState();

	const debouncedValue = useDebounce(value, 1000);

	const url = `${resource}/?filter=${onSearch(debouncedValue)}`;

	const {error, loading, mutate} = useFetch(url);

	const {items} = useMemo(() => {
		if (transformData) {
			return transformData(data);
		}
	}, [data, transformData]);

	useEffect(() => {
		if (debouncedValue) {
			mutate(url).then((response) => {
				setTest(response);
				setActive(true);
			});
		}
	}, [debouncedValue, mutate, onSearch, resource, url]);

	const onClickItem = (name: string) => {
		setShowValue(name);
		setActive(false);
	};

	return (
		<ClayAutocomplete className="mb-4">
			<label>{label}</label>

			<ClayAutocomplete.Input
				onChange={(event) => {
					setValue(event.target.value);
					setShowValue(event.target.value);
				}}
				placeholder="Type here"
				value={showValue || value}
			/>

			<ClayAutocomplete.DropDown active={active}>
				<ClayDropDown.ItemList>
					{(error || (items && !items.length)) && (
						<ClayDropDown.Item className="disabled">
							No Results Found
						</ClayDropDown.Item>
					)}

					{!error &&
						items?.map((item: any) => (
							<ClayAutocomplete.Item
								key={item.id}
								match={value}
								onClick={() => onClickItem(item.name)}
								value={item.name}
							/>
						))}
				</ClayDropDown.ItemList>
			</ClayAutocomplete.DropDown>

			{loading && <ClayAutocomplete.LoadingIndicator />}
		</ClayAutocomplete>
	);
};

export default AutoComplete;

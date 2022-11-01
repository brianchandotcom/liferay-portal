/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayAutocomplete from '@clayui/autocomplete';
import {useResource} from '@clayui/data-provider';
import ClayDropDown from '@clayui/drop-down';
import {FocusScope} from '@clayui/shared';
import React, {useState} from 'react';

/**
 * When Hugging Face is selected as the Sentence Transform Provider, this input
 * will query for applicable models.
 * This can be found on: System Settings > Search Experiences > Semantic Search
 */
function ModelAutocomplete({
	label,
	name,
	onBlur,
	onChange,
	required,
	value = '',
}) {
	const [autocompleteSearchValue, setAutocompleteSearchValue] = useState(
		value
	);
	const [networkState, setNetworkState] = useState(() => ({
		error: false,
		loading: false,
		networkStatus: 4,
	}));
	const [showDropDown, setShowDropDown] = useState(false);

	const {resource} = useResource({
		fetchPolicy: 'cache-first',
		link: `${window.location.origin}${Liferay.ThemeDisplay.getPathContext()}
		/o/search-experiences-rest/v1.0/sentence-transformer/ml-models`,
		onNetworkStatusChange: (status) => {
			setNetworkState({
				error: status === 5,
				loading: status > 1 && status < 4,
				networkStatus: status,
			});
		},
		variables: {
			limit: 20,
			pipeline_tag: 'feature-extraction',
			query: autocompleteSearchValue,
		},
	});

	const _handleBlur = () => {
		if (!autocompleteSearchValue) {
			onChange('');
		}
		else if (value !== autocompleteSearchValue) {
			setAutocompleteSearchValue(value);
		}

		onBlur();
	};

	const _handleFocus = () => {
		setShowDropDown(true);
	};

	const _handleInputChange = (event) => {
		setShowDropDown(true);
		setAutocompleteSearchValue(event.target.value);
	};

	const _handleItemChange = (item) => {
		setAutocompleteSearchValue(item);
		onChange(item);
		setShowDropDown(false);
	};

	return (
		<FocusScope>
			<ClayAutocomplete>
				<ClayAutocomplete.Input
					aria-label={label}
					id={name}
					name={name}
					onBlur={_handleBlur}
					onChange={_handleInputChange}
					onFocus={_handleFocus}
					required={required}
					value={autocompleteSearchValue}
				/>

				<ClayAutocomplete.DropDown
					active={showDropDown}
					onSetActive={setShowDropDown}
				>
					<ClayDropDown.ItemList>
						{(resource?.items || []).map(({modelId}) => (
							<ClayDropDown.Item
								key={modelId}
								onClick={() => _handleItemChange(modelId)}
							>
								{modelId}
							</ClayDropDown.Item>
						))}

						{!resource?.items?.length && (
							<ClayDropDown.Item>
								{networkState.loading ? (
									<ClayAutocomplete.LoadingIndicator />
								) : (
									Liferay.Language.get('no-results-found')
								)}
							</ClayDropDown.Item>
						)}
					</ClayDropDown.ItemList>
				</ClayAutocomplete.DropDown>
			</ClayAutocomplete>
		</FocusScope>
	);
}

export default ModelAutocomplete;

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

import ClayButton from '@clayui/button';
import {useResource} from '@clayui/data-provider';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayMultiSelect, {itemLabelFilter} from '@clayui/multi-select';
import {usePrevious} from '@liferay/frontend-js-react-web';
import {openSelectionModal} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect} from 'react';

const noop = () => {};

function AssetTagsSelector({
	addCallback,
	groupIds = [],
	id,
	inputName,
	inputValue,
	label,
	onInputValueChange = noop,
	onSelectedItemsChange = noop,
	portletURL,
	removeCallback,
	selectedItems = [],
	showSelectButton,
}) {
	const {refetch, resource} = useResource({
		fetchOptions: {
			body: Liferay.Util.objectToFormData({
				cmd: JSON.stringify({
					'/assettag/search': {
						end: 20,
						groupIds,
						name: `%${inputValue === '*' ? '' : inputValue}%`,
						start: 0,
						tagProperties: '',
					},
				}),
				p_auth: Liferay.authToken,
			}),
			credentials: 'include',
			method: 'POST',
			'x-csrf-token': Liferay.authToken,
		},
		link: `${window.location.origin}${themeDisplay.getPathContext()}
				/api/jsonws/invoke`,
	});

	const previousInputValue = usePrevious(inputValue);

	useEffect(() => {
		if (inputValue && inputValue !== previousInputValue) {
			refetch();
		}
	}, [inputValue, previousInputValue, refetch]);

	const callGlobalCallback = (callback, item) => {
		if (callback && typeof window[callback] === 'function') {
			window[callback](item);
		}
	};

	const handleInputBlur = () => {
		const filteredItems = resource && resource.map((tag) => tag.value);

		if (!filteredItems || !filteredItems.length) {
			if (inputValue) {
				if (!selectedItems.find((item) => item.label === inputValue)) {
					onSelectedItemsChange(
						selectedItems.concat({
							label: inputValue,
							value: inputValue,
						})
					);
				}
				onInputValueChange('');
			}
		}
	};

	const handleItemsChange = (items) => {
		const addedItems = items.filter(
			(item) =>
				!selectedItems.find(
					(selectedItem) => selectedItem.value === item.value
				)
		);

		const removedItems = selectedItems.filter(
			(selectedItem) =>
				!items.find((item) => item.value === selectedItem.value)
		);

		const current = [...selectedItems, ...addedItems].filter(
			(item) =>
				!removedItems.find(
					(removedItem) => removedItem.value === item.value
				)
		);

		onSelectedItemsChange(current);

		addedItems.forEach((item) => callGlobalCallback(addCallback, item));

		removedItems.forEach((item) =>
			callGlobalCallback(removeCallback, item)
		);
	};

	const handleSelectButtonClick = () => {
		const sub = (str, obj) => str.replace(/\{([^}]+)\}/g, (_, m) => obj[m]);

		const url = sub(decodeURIComponent(portletURL), {
			selectedTagNames: selectedItems.map((item) => item.value).join(),
		});

		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('done'),
			multiple: true,
			onSelect: (dialogSelectedItems) => {
				if (!dialogSelectedItems?.length) {
					return;
				}

				const newValues = dialogSelectedItems.map((item) => {
					return {
						label: item.value,
						value: item.value,
					};
				});

				const addedItems = newValues.filter(
					(newValue) =>
						!selectedItems.find(
							(selectedItem) =>
								selectedItem.label === newValue.label
						)
				);

				const removedItems = selectedItems.filter(
					(selectedItem) =>
						!newValues.find(
							(newValue) => newValue.label === selectedItem.label
						)
				);

				onSelectedItemsChange(newValues);

				addedItems.forEach((item) =>
					callGlobalCallback(addCallback, item)
				);

				removedItems.forEach((item) =>
					callGlobalCallback(removeCallback, item)
				);
			},
			title: Liferay.Language.get('tags'),
			url,
		});
	};

	return (
		<div className="lfr-tags-selector-content" id={id}>
			<ClayForm.Group>
				<label>{label || Liferay.Language.get('tags')}</label>

				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayMultiSelect
							inputName={inputName}
							inputValue={inputValue}
							items={selectedItems}
							onBlur={handleInputBlur}
							onChange={onInputValueChange}
							onItemsChange={handleItemsChange}
							sourceItems={
								resource
									? itemLabelFilter(
											resource.map((tag) => {
												return {
													label: tag.text,
													value: tag.value,
												};
											}),
											inputValue
									  )
									: []
							}
						/>
					</ClayInput.GroupItem>

					{showSelectButton && (
						<ClayInput.GroupItem shrink>
							<ClayButton
								displayType="secondary"
								onClick={handleSelectButtonClick}
							>
								{Liferay.Language.get('select')}
							</ClayButton>
						</ClayInput.GroupItem>
					)}
				</ClayInput.Group>
			</ClayForm.Group>
		</div>
	);
}

AssetTagsSelector.propTypes = {
	addCallback: PropTypes.string,
	groupIds: PropTypes.array,
	id: PropTypes.string,
	inputName: PropTypes.string,
	inputValue: PropTypes.string,
	label: PropTypes.string,
	onInputValueChange: PropTypes.func,
	onSelectedItemsChange: PropTypes.func,
	portletURL: PropTypes.string,
	removeCallback: PropTypes.string,
	selectedItems: PropTypes.array,
};

export default AssetTagsSelector;

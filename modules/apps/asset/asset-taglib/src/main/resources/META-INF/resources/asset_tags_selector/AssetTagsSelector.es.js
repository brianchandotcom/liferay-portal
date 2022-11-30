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
import ClayForm, {ClayInput} from '@clayui/form';
import ClayMultiSelect, {itemLabelFilter} from '@clayui/multi-select';
import {usePrevious} from '@liferay/frontend-js-react-web';
import {fetch, openSelectionModal, sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

const noop = () => {};

function AssetTagsSelector({
	addCallback,
	groupIds = [],
	helpText = '',
	id,
	inputName,
	inputValue,
	label = Liferay.Language.get('tags'),
	onInputValueChange = noop,
	onSelectedItemsChange = noop,
	portletURL,
	removeCallback,
	selectedItems = [],
	showLabel = true,
	showSelectButton,
}) {
	const selectButtonRef = useRef();

	const [resource, setResource] = useState([]);

	const previousInputValue = usePrevious(inputValue);

	useEffect(() => {
		if (inputValue && inputValue !== previousInputValue) {
			fetch(
				`${
					window.location.origin
				}${themeDisplay.getPathContext()}/api/jsonws/invoke`,
				{
					body: new URLSearchParams({
						cmd: JSON.stringify({
							'/assettag/search': {
								end: 20,
								groupIds,
								name: `%${
									inputValue === '*' ? '' : inputValue
								}%`,
								start: 0,
								tagProperties: '',
							},
						}),
						p_auth: Liferay.authToken,
					}),
					method: 'POST',
				}
			)
				.then((response) => response.json())
				.then((response) => setResource(response));
		}
	}, [groupIds, inputValue, previousInputValue]);

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
		const sub = (str, object) =>
			str.replace(/\{([^}]+)\}/g, (_, m) => object[m]);

		const url = sub(decodeURIComponent(portletURL), {
			selectedTagNames: selectedItems.map((item) => item.value).join(),
		});

		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('done'),
			getSelectedItemsOnly: false,
			multiple: true,
			onClose: () => {
				selectButtonRef.current?.focus();
			},
			onSelect: (dialogSelectedItems) => {
				if (!dialogSelectedItems?.length) {
					return;
				}

				let [newValues, removedValues] = dialogSelectedItems.reduce(
					([checked, unchecked], item) => {
						if (item.checked) {
							return [
								[
									...checked,
									{
										label: item.value,
										value: item.value,
									},
								],
								unchecked,
							];
						}
						else {
							return [
								checked,
								[
									...unchecked,
									{
										label: item.value,
										value: item.value,
									},
								],
							];
						}
					},
					[[], []]
				);

				newValues = newValues.filter(
					(newValue) =>
						!selectedItems.find(
							(selectedItem) =>
								selectedItem.label === newValue.label
						)
				);

				removedValues = selectedItems.filter((selectedItem) =>
					removedValues.find(
						(removedValue) =>
							removedValue.label === selectedItem.label
					)
				);

				const allSelectedItems = selectedItems
					.concat(newValues)
					.filter((item) => !removedValues.includes(item));

				onSelectedItemsChange(allSelectedItems);

				newValues.forEach((item) =>
					callGlobalCallback(addCallback, item)
				);

				removedValues.forEach((item) =>
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
				<label
					className={showLabel ? '' : 'sr-only'}
					htmlFor={inputName + '_MultiSelect'}
				>
					{label}
				</label>

				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayMultiSelect
							aria-describedby={
								helpText
									? `${inputName}_MultiSelectHelpText`
									: undefined
							}
							id={inputName + '_MultiSelect'}
							inputName={inputName}
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
							value={inputValue}
						/>
					</ClayInput.GroupItem>

					{showSelectButton && (
						<ClayInput.GroupItem shrink>
							<ClayButton
								aria-haspopup="dialog"
								aria-label={sub(
									Liferay.Language.get('select-x'),
									label
								)}
								displayType="secondary"
								onClick={handleSelectButtonClick}
								ref={selectButtonRef}
							>
								{Liferay.Language.get('select')}
							</ClayButton>
						</ClayInput.GroupItem>
					)}
				</ClayInput.Group>

				{helpText ? (
					<p
						className="m-0 mt-1 small text-secondary"
						id={`${inputName}_MultiSelectHelpText`}
					>
						{helpText}
					</p>
				) : null}
			</ClayForm.Group>
		</div>
	);
}

AssetTagsSelector.propTypes = {
	addCallback: PropTypes.string,
	groupIds: PropTypes.array,
	helpText: PropTypes.string,
	id: PropTypes.string,
	inputName: PropTypes.string,
	inputValue: PropTypes.string,
	label: PropTypes.string,
	onInputValueChange: PropTypes.func,
	onSelectedItemsChange: PropTypes.func,
	portletURL: PropTypes.string,
	removeCallback: PropTypes.string,
	selectedItems: PropTypes.array,
	showLabel: PropTypes.bool,
};

export default AssetTagsSelector;

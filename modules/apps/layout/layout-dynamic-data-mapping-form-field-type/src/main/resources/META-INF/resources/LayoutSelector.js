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
import {ReactFieldBase} from 'dynamic-data-mapping-form-field-type';
import {openSelectionModal} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

const LayoutSelector = ({
	disabled,
	inputValue,
	itemSelectorURL,
	name,
	onChange,
	portletNamespace,
}) => {
	const [layout, setLayout] = useState(() => JSON.parse(inputValue || '{}'));

	useEffect(() => {
		setLayout(JSON.parse(inputValue || '{}'));
	}, [inputValue]);

	const handleClearClick = () => {
		setLayout({});
		onChange('');
	};

	const handleFieldChanged = (event) => {
		const selectedItem = event.selectedItem;

		if (selectedItem && selectedItem.layoutId) {
			setLayout(selectedItem);
			onChange(JSON.stringify(selectedItem));
		}
	};

	const handleItemSelectorTriggerClick = (event) => {
		event.preventDefault();

		openSelectionModal({
			onSelect: handleFieldChanged,
			selectEventName: `${portletNamespace}selectLayout`,
			title: Liferay.Language.get('page'),
			url: itemSelectorURL,
		});
	};

	return (
		<ClayForm.Group style={{marginBottom: '0.5rem'}}>
			<ClayInput.Group>
				<ClayInput.GroupItem className="d-none d-sm-block" prepend>
					<input
						name={name}
						type="hidden"
						value={JSON.stringify(layout)}
					/>

					<ClayInput
						className="bg-light"
						disabled={disabled}
						onClick={handleItemSelectorTriggerClick}
						readOnly
						type="text"
						value={layout.name || ''}
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem append shrink>
					<ClayButton
						disabled={disabled}
						displayType="secondary"
						onClick={handleItemSelectorTriggerClick}
						type="button"
					>
						{Liferay.Language.get('select')}
					</ClayButton>
				</ClayInput.GroupItem>

				{layout.layoutId && (
					<ClayInput.GroupItem shrink>
						<ClayButton
							disabled={disabled}
							displayType="secondary"
							onClick={handleClearClick}
							type="button"
						>
							{Liferay.Language.get('clear')}
						</ClayButton>
					</ClayInput.GroupItem>
				)}
			</ClayInput.Group>
		</ClayForm.Group>
	);
};

const Main = ({
	itemSelectorURL,
	name,
	onChange,
	portletNamespace,
	predefinedValue,
	readOnly,
	value,
	...otherProps
}) => (
	<ReactFieldBase {...otherProps} name={name} readOnly={readOnly}>
		<LayoutSelector
			disabled={readOnly}
			inputValue={value && value !== '' ? value : predefinedValue}
			itemSelectorURL={itemSelectorURL}
			name={name}
			onChange={(value) => onChange({}, value)}
			portletNamespace={portletNamespace}
		/>
	</ReactFieldBase>
);

Main.displayName = 'LayoutSelector';

export default Main;

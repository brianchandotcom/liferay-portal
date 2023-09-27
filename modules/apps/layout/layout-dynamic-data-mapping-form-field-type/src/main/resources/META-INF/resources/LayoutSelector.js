/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import {FieldBase} from 'dynamic-data-mapping-form-field-type/FieldBase/ReactFieldBase.es';
import {openSelectionModal} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

function getInputValue(value, predefinedValue) {
	if (!value || value === '') {
		return predefinedValue;
	}

	if (value && typeof value !== 'string') {
		try {
			return JSON.stringify(value);
		}
		catch (error) {}
	}

	return value;
}

const LayoutSelector = ({
	disabled,
	editingLanguageId,
	inputValue,
	itemSelectorURL,
	name,
	onChange,
	portletNamespace,
}) => {
	const [layout, setLayout] = useState(() => JSON.parse(inputValue || '{}'));

	useEffect(() => {
		setLayout(JSON.parse(getInputValue(inputValue, '{}')));
	}, [inputValue]);

	const handleClearClick = () => {
		setLayout({});
		onChange('');
	};

	const handleFieldChanged = (selectedItem) => {
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
						dir={Liferay.Language.direction[editingLanguageId]}
						disabled={disabled}
						lang={editingLanguageId}
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
	editingLanguageId,
	itemSelectorURL,
	name,
	onChange,
	portletNamespace,
	predefinedValue,
	readOnly,
	value,
	...otherProps
}) => (
	<FieldBase {...otherProps} name={name} readOnly={readOnly}>
		<LayoutSelector
			disabled={readOnly}
			editingLanguageId={editingLanguageId}
			inputValue={getInputValue(value, predefinedValue)}
			itemSelectorURL={itemSelectorURL}
			name={name}
			onChange={(value) => onChange({}, value)}
			portletNamespace={portletNamespace}
		/>
	</FieldBase>
);

Main.displayName = 'LayoutSelector';

export default Main;

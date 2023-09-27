/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import ClayMultiSelect from '@clayui/multi-select';
import React, {useState} from 'react';

export default function Multiselect({
	additionalProps: _additionalProps,
	clearAllTitle,
	componentId: _componentId,
	cssClass,
	disabled,
	disabledClearAll,
	helpText,
	id,
	inputName,
	inputValue: _inputValue,
	isValid,
	label,
	locale: _locale,
	multiselectLocator,
	portletId: _portletId,
	portletNamespace: _portletNamespace,
	selectedItems: _selectedItems = [],
	sourceItems,
	...otherProps
}) {
	const [inputValue, setInputValue] = useState(_inputValue ?? '');

	const [selectedItems, setSelectedItems] = useState(_selectedItems);

	return (
		<ClayForm.Group className={!isValid ? 'has-error' : ''}>
			{label && <label htmlFor={id}>{label}</label>}

			<ClayInput.Group>
				<ClayInput.GroupItem>
					<ClayMultiSelect
						className={cssClass}
						clearAllTitle={clearAllTitle}
						closeButtonAriaLabel={Liferay.Language.get('remove-x')}
						disabled={disabled}
						disabledClearAll={disabledClearAll}
						id={id}
						inputName={inputName}
						isValid={isValid}
						items={selectedItems}
						locator={multiselectLocator}
						onChange={setInputValue}
						onItemsChange={setSelectedItems}
						sourceItems={sourceItems}
						value={inputValue}
						{...otherProps}
					/>

					{helpText && (
						<ClayForm.FeedbackGroup>
							<ClayForm.Text>{helpText}</ClayForm.Text>
						</ClayForm.FeedbackGroup>
					)}
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayForm.Group>
	);
}

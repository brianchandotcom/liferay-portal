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

import ClayForm, {ClayInput} from '@clayui/form';
import ClayMultiSelect, {itemLabelFilter} from '@clayui/multi-select';
import {ClayTooltipProvider} from '@clayui/tooltip';
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
	const [inputValue, setInputValue] = useState(_inputValue);

	const [selectedItems, setSelectedItems] = useState(_selectedItems);

	return (
		<ClayTooltipProvider>
			<ClayForm.Group className={!isValid ? 'has-error' : ''}>
				{label && <label htmlFor={id}>{label}</label>}

				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayMultiSelect
							className={cssClass}
							clearAllTitle={clearAllTitle}
							closeButtonAriaLabel={Liferay.Language.get(
								'remove-x'
							)}
							disabled={disabled}
							disabledClearAll={disabledClearAll}
							id={id}
							inputName={inputName}
							inputValue={inputValue}
							isValid={isValid}
							items={selectedItems}
							locator={multiselectLocator}
							onChange={setInputValue}
							onItemsChange={setSelectedItems}
							sourceItems={itemLabelFilter(
								sourceItems,
								inputValue
							)}
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
		</ClayTooltipProvider>
	);
}

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
import {getConnectedReactComponentAdapter} from 'dynamic-data-mapping-form-field-type';
import {ItemSelectorDialog} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import templates from './LayoutSelectorAdapter.soy';

const LayoutSelectorWithState = ({
	dispatch,
	inputValue,
	itemSelectorURL,
	name,
	portletNamespace,
	readOnly,
}) => {
	const [layout, setLayout] = useState({});

	useEffect(() => {
		setLayout({
			...JSON.parse(inputValue || '{}'),
		});
	}, [inputValue]);

	const _dispatchValue = (value, clear) => {
		setLayout(() => {
			dispatch({
				payload: clear ? '' : JSON.stringify(value),
				type: 'value',
			});

			return value;
		});
	};

	const _handleClearClick = () => {
		_dispatchValue({}, true);
	};

	const _handleFieldChanged = event => {
		const selectedItem = event.selectedItem;

		if (selectedItem && selectedItem.layoutId) {
			setLayout(() => {
				dispatch({
					payload: JSON.stringify(selectedItem),
					type: 'value',
				});

				return selectedItem;
			});
		}
	};

	const _handleItemSelectorTriggerClick = event => {
		event.preventDefault();

		const itemSelectorDialog = new ItemSelectorDialog({
			eventName: `${portletNamespace}selectLayout`,
			singleSelect: true,
			title: Liferay.Language.get('page'),
			url: itemSelectorURL,
		});

		itemSelectorDialog.on('selectedItemChange', _handleFieldChanged);

		itemSelectorDialog.open();
	};

	return (
		<>
			<ClayForm.Group style={{marginBottom: '0.5rem'}}>
				<ClayInput.Group>
					<ClayInput.GroupItem className="d-none d-sm-block" prepend>
						<input name={name} type="hidden" value={inputValue} />

						<ClayInput
							className="bg-light"
							disabled={readOnly}
							onClick={_handleItemSelectorTriggerClick}
							readOnly
							type="text"
							value={layout.name || ''}
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem append shrink>
						<ClayButton
							disabled={readOnly}
							displayType="secondary"
							onClick={_handleItemSelectorTriggerClick}
							type="button"
						>
							{Liferay.Language.get('select')}
						</ClayButton>
					</ClayInput.GroupItem>

					{layout.layoutId && (
						<ClayInput.GroupItem shrink>
							<ClayButton
								disabled={readOnly}
								displayType="secondary"
								onClick={_handleClearClick}
								type="button"
							>
								{Liferay.Language.get('clear')}
							</ClayButton>
						</ClayInput.GroupItem>
					)}
				</ClayInput.Group>
			</ClayForm.Group>
		</>
	);
};

const ReactLayoutSelectorAdapter = getConnectedReactComponentAdapter(
	LayoutSelectorWithState,
	templates
);

export {ReactLayoutSelectorAdapter};
export default ReactLayoutSelectorAdapter;

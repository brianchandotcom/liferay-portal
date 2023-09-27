/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {openSelectionModal} from 'frontend-js-web';
import React from 'react';

function ItemSelectorInput({
	disabled,
	entityJSON,
	id,
	itemType,
	label,
	name,
	setFieldTouched,
	setFieldValue,
	value,
}) {
	const _handleKeyDown = (event) => {
		if (event.key === 'Enter') {
			event.preventDefault();
		}
	};

	const _handleMultipleEntitySelect = (itemType) => {
		if (entityJSON[itemType].multiple) {
			openSelectionModal({
				buttonAddLabel: Liferay.Language.get('select'),
				multiple: true,
				onSelect: (selectedItems) => {
					if (selectedItems) {
						setFieldValue(
							name,
							selectedItems.map((item) => ({
								label: item.name,
								value: item.id,
							}))
						);
					}
				},
				selectEventName: 'selectEntity',
				title: entityJSON[itemType].title,
				url: entityJSON[itemType].url,
			});
		}
		else {
			openSelectionModal({
				buttonAddLabel: Liferay.Language.get('select'),
				onSelect: (event) => {
					setFieldValue(name, [
						{
							label: event.entityname,
							value: event.entityid,
						},
					]);
				},
				selectEventName: 'selectEntity',
				title: entityJSON[itemType].title,
				url: entityJSON[itemType].url,
			});
		}
	};

	return (
		<ClayInput.Group onBlur={() => setFieldTouched(name)} small>
			<ClayInput.GroupItem>
				<ClayInput
					aria-label={label}
					disabled={disabled}
					id={id}
					name={name}
					onKeyDown={_handleKeyDown}
					readOnly
					type="text"
					value={
						value && !!value.length
							? value.map((item) => item.label).join(', ')
							: ''
					}
				/>
			</ClayInput.GroupItem>

			{value && !!value.length && (
				<ClayInput.GroupItem shrink>
					<ClayButton
						aria-label={Liferay.Language.get('delete')}
						className="component-action"
						disabled={disabled}
						displayType="unstyled"
						onClick={() => setFieldValue(name, [])}
					>
						<ClayIcon symbol="times-circle" />
					</ClayButton>
				</ClayInput.GroupItem>
			)}

			<ClayInput.GroupItem shrink>
				<ClayButton
					aria-label={Liferay.Language.get('select')}
					disabled={disabled || !entityJSON}
					displayType="secondary"
					onClick={() => {
						if (entityJSON) {
							_handleMultipleEntitySelect(itemType);
						}
					}}
					small
				>
					{Liferay.Language.get('select')}
				</ClayButton>
			</ClayInput.GroupItem>
		</ClayInput.Group>
	);
}

export default ItemSelectorInput;

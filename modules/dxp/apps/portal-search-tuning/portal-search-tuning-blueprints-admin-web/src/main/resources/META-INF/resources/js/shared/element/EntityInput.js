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

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {openSelectionModal} from 'frontend-js-web';
import React from 'react';

function EntityInput({
	className,
	configKey,
	disabled,
	entityJSON,
	label,
	onChange,
	value,
}) {
	const _handleMultipleEntitySelect = (key, className) => {
		if (entityJSON[className].multiple) {
			openSelectionModal({
				buttonAddLabel: Liferay.Language.get('select'),
				multiple: true,
				onSelect: (selectedItems) => {
					if (selectedItems) {
						onChange(key, selectedItems);
					}
				},
				selectEventName: 'selectEntity',
				title: entityJSON[className].title,
				url: entityJSON[className].url,
			});
		}
		else {
			openSelectionModal({
				buttonAddLabel: Liferay.Language.get('select'),
				onSelect: (event) => {
					onChange(key, [
						{
							label: event.entityname,
							value: event.entityid,
						},
					]);
				},
				selectEventName: 'selectEntity',
				title: entityJSON[className].title,
				url: entityJSON[className].url,
			});
		}
	};

	return (
		<ClayInput.Group small>
			<ClayInput.GroupItem>
				<ClayInput
					aria-label={label}
					disabled={disabled}
					id={configKey}
					readOnly
					type="text"
					value={
						value.length > 0
							? value.map((item) => item.label).join(', ')
							: ''
					}
				/>
			</ClayInput.GroupItem>

			{value.length > 0 && (
				<ClayInput.GroupItem shrink>
					<ClayButton
						aria-label={Liferay.Language.get('delete')}
						className="component-action"
						disabled={disabled}
						displayType="unstyled"
						onClick={() => onChange(configKey, [])}
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
							_handleMultipleEntitySelect(configKey, className);
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

export default EntityInput;

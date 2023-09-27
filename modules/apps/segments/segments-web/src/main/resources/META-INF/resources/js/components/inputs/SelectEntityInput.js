/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import classNames from 'classnames';
import {openSelectionModal} from 'frontend-js-web';
import propTypes from 'prop-types';
import React from 'react';

function SelectEntityInput({
	disabled,
	displayValue,
	onChange,
	propertyLabel,
	renderEmptyValueErrors,
	selectEntity,
	value,
}) {

	/**
	 * Opens a modal for selecting entities. Uses different methods for
	 * selecting multiple entities versus single because of the way the event
	 * and data is submitted.
	 */
	const onSelectEntity = () => {
		const {id, multiple, title, uri} = selectEntity;

		if (multiple) {
			openSelectionModal({
				buttonAddLabel: Liferay.Language.get('select'),
				multiple: true,
				onSelect: (event) => {
					if (event) {
						if (Array.isArray(event)) {
							const selectedValues = event.map((item) => ({
								displayValue: item.name,
								value: item.id,
							}));

							onChange(selectedValues);
						}
						else {
							const selectedItems = event.value;

							const selectedValues = selectedItems.map((item) => {
								const selectedValue = JSON.parse(item);

								return {
									displayValue: selectedValue.name,
									value:
										selectedValue.id?.toString() ||
										selectedValue.organizationId,
								};
							});

							onChange(selectedValues);
						}
					}
				},
				selectEventName: id,
				title,
				url: uri,
			});
		}
		else {
			openSelectionModal({
				onSelect: (event) => {
					try {
						const valueJSON = JSON.parse(event.value);

						onChange({
							displayValue:
								valueJSON.name ||
								valueJSON.segmentsEntryName ||
								valueJSON.tagName,
							value:
								valueJSON.segmentsEntryId ||
								valueJSON.teamId ||
								valueJSON.userGroupId ||
								valueJSON.tagId,
						});
					}
					catch {
						if (event.entityname && event.entityid) {
							onChange({
								displayValue: event.entityname,
								value: event.entityid,
							});
						}
						else {
							const category = event
								? event[Object.keys(event)[0]]
								: null;

							if (category) {
								onChange({
									displayValue: category.title,
									value: category.categoryId,
								});
							}
						}
					}
				},
				selectEventName: id,
				title,
				url: uri,
			});
		}
	};

	return (
		<>
			<div className="criterion-input input-group select-entity-input">
				<div className="input-group-item input-group-prepend">
					<ClayInput
						data-testid="entity-select-input"
						disabled={disabled}
						type="hidden"
						value={value}
					/>

					<ClayInput
						aria-label={`${propertyLabel}: ${Liferay.Language.get(
							'select-option'
						)}`}
						className={classNames('form-control', {
							'criterion-input--error':
								!value && renderEmptyValueErrors,
						})}
						disabled={disabled}
						readOnly
						value={displayValue}
					/>
				</div>

				<span className="input-group-append input-group-item input-group-item-shrink">
					<ClayButton
						className={classNames(
							'input-group-append input-group-item input-group-item-shrink',
							{
								'criterion-input--error':
									!value && renderEmptyValueErrors,
							}
						)}
						disabled={disabled}
						displayType="secondary"
						onClick={onSelectEntity}
					>
						{Liferay.Language.get('select')}
					</ClayButton>
				</span>
			</div>
		</>
	);
}

SelectEntityInput.propTypes = {
	disabled: propTypes.bool,
	displayValue: propTypes.oneOfType([propTypes.string, propTypes.number]),
	onChange: propTypes.func.isRequired,
	propertyLabel: propTypes.string.isRequired,
	renderEmptyValueErrors: propTypes.bool,
	selectEntity: propTypes.shape({
		id: propTypes.string,
		multiple: propTypes.bool,
		title: propTypes.string,
		uri: propTypes.string,
	}),
	value: propTypes.oneOfType([propTypes.string, propTypes.number]),
};

export default SelectEntityInput;

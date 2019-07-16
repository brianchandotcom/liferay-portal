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
import propTypes from 'prop-types';
import React from 'react';

class SelectEntityInput extends React.Component {
	static propTypes = {
		disabled: propTypes.bool,
		displayValue: propTypes.oneOfType([propTypes.string, propTypes.number]),
		onChange: propTypes.func.isRequired,
		selectEntity: propTypes.shape({
			id: propTypes.string,
			multiple: propTypes.bool,
			title: propTypes.string,
			uri: propTypes.string
		}),
		value: propTypes.oneOfType([propTypes.string, propTypes.number])
	};

	/**
	 * Opens a modal for selecting entities. Uses different methods for
	 * selecting multiple entities versus single because of the way the event
	 * and data is submitted.
	 */
	_handleSelectEntity = () => {
		const {
			onChange,
			selectEntity: {id, multiple, title, uri}
		} = this.props;

		if (multiple) {
			AUI().use('liferay-item-selector-dialog', A => {
				const itemSelectorDialog = new A.LiferayItemSelectorDialog({
					eventName: id,
					on: {
						selectedItemChange: event => {
							const newVal = event.newVal;

							if (newVal) {
								const selectedValues = event.newVal.map(
									item => ({
										displayValue: item.name,
										value: item.id
									})
								);

								onChange(selectedValues);
							}
						}
					},
					strings: {
						add: Liferay.Language.get('select'),
						cancel: Liferay.Language.get('cancel')
					},
					title,
					url: uri
				});

				itemSelectorDialog.open();
			});
		} else {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						destroyOnHide: true,
						modal: true
					},
					id,
					title,
					uri
				},
				event => {
					onChange({
						displayValue: event.entityname,
						value: event.entityid
					});
				}
			);
		}
	};

	render() {
		const {disabled, displayValue, value} = this.props;

		return (
			<div className="criterion-input input-group select-entity-input">
				<div className="input-group-item input-group-prepend">
					<input
						data-testid="entity-select-input"
						disabled={disabled}
						type="hidden"
						value={value}
					/>

					<input
						className="form-control"
						disabled={disabled}
						readOnly
						value={displayValue}
					/>
				</div>

				<span className="input-group-append input-group-item input-group-item-shrink">
					<ClayButton
						disabled={disabled}
						displayType="secondary"
						onClick={this._handleSelectEntity}
					>
						{Liferay.Language.get('select')}
					</ClayButton>
				</span>
			</div>
		);
	}
}

export default SelectEntityInput;

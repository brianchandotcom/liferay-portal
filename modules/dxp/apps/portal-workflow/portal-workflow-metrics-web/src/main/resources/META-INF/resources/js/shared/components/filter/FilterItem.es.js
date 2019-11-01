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

import getClassName from 'classnames';
import React from 'react';

export default class FilterItem extends React.Component {
	onChange(event) {
		const {multiple, onChange} = this.props;

		onChange(event);

		if (!multiple) {
			document.dispatchEvent(new Event('mousedown'));
		}
	}

	render() {
		const {
			active,
			description,
			dividerAfter,
			hideControl,
			itemKey,
			multiple,
			name,
			onClick
		} = this.props;

		const controlClassName = getClassName(
			'custom-control',
			multiple ? 'custom-checkbox' : 'custom-radio'
		);

		const dropDownClassName = getClassName(
			'dropdown-item',
			active && 'active',
			description && 'with-description',
			hideControl && 'control-hidden'
		);

		const inputProps = {
			type: 'checkbox'
		};

		if (!multiple) {
			inputProps.name = 'filter-item-radio-group';
			inputProps.type = 'radio';
		}

		return (
			<>
				<li className={dropDownClassName}>
					<label className={controlClassName}>
						<input
							{...inputProps}
							checked={!!active}
							className="custom-control-input"
							data-key={itemKey}
							data-testid="filterItemInput"
							onChange={this.onChange.bind(this)}
							onClick={onClick}
						/>

						<span className="custom-control-label">
							<span
								className="custom-control-label-text"
								data-testid="filterItemName"
							>
								{name}
							</span>

							{description && (
								<span className="custom-control-label-text dropdown-item-description">
									{description}
								</span>
							)}
						</span>
					</label>
				</li>

				{dividerAfter && <li className="dropdown-divider" />}
			</>
		);
	}
}

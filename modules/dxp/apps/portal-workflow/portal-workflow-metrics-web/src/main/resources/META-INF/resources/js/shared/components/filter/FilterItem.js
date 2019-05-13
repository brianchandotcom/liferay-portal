import getClassName from 'classnames';
import React from 'react';

export default class FilterItem extends React.Component {
	render() {
		const {
			active,
			description,
			hideControl,
			itemKey,
			multiple,
			name,
			onChange
		} = this.props;

		const controlClassName = getClassName(
			'custom-control',
			multiple ? 'custom-checkbox' : 'custom-radio'
		);

		const dropDownClassName = getClassName(
			'dropdown-item',
			active && 'active',
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
			<li className={dropDownClassName}>
				<label className={controlClassName}>
					<input
						{...inputProps}
						checked={!!active}
						className="custom-control-input"
						data-key={itemKey}
						onChange={onChange}
					/>

					<span className="custom-control-label">
						<span className="custom-control-label-text">{name}</span>

						{description && (
							<span className="custom-control-label-description">
								{description}
							</span>
						)}
					</span>
				</label>
			</li>
		);
	}
}
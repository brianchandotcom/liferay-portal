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

import React from 'react';

const getClassNameBasedOnDirection = (direction) => {
	return direction?.includes('horizontal') ? 'col-md-6' : 'col-md-12';
};

const getConfirmationLabel = (confirmationLabel, label) => {
	if (confirmationLabel && label) {
		return [confirmationLabel, label].join(' ');
	}

	return confirmationLabel || label;
};

const getLabel = (confirmationLabel, label) => {
	if (confirmationLabel && !label) {
		return ' ';
	}

	return label;
};

export default (Component) => {
	const Wrapper = ({requireConfirmation, ...otherProps}) => {
		if (!requireConfirmation) {
			return <Component {...otherProps} />;
		}

		const {
			confirmationErrorMessage,
			confirmationLabel,
			confirmationValue,
			direction,
			errorMessage,
			label,
			name,
			onChange,
			valid,
		} = otherProps;

		const className = getClassNameBasedOnDirection(direction);

		const isConfirmationFieldValid = valid || errorMessage?.length > 0;

		return (
			<div className="row">
				<div className={className}>
					<Component
						{...otherProps}
						label={getLabel(confirmationLabel, label)}
					/>
				</div>
				<div className={className}>
					<Component
						{...otherProps}
						errorMessage={confirmationErrorMessage}
						id={`${name}confirmationField`}
						label={getConfirmationLabel(confirmationLabel, label)}
						localizable={false}
						localizedValue={{}}
						name={`${name}confirmationField`}
						onBlur={() => {}}
						onChange={(event) => {
							onChange(
								event,
								event.target.value,
								'confirmationValue'
							);
						}}
						onFocus={() => {}}
						placeholder=""
						predefinedValue=""
						repeatable={false}
						tip=""
						valid={isConfirmationFieldValid}
						value={confirmationValue}
					/>
				</div>
			</div>
		);
	};

	return Wrapper;
};

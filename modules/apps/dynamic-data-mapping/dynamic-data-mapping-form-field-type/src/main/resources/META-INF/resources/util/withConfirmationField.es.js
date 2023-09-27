/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

export default function withConfirmationField(Component) {
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
}

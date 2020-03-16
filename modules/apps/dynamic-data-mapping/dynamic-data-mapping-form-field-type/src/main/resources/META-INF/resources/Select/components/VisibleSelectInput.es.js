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

import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import classNames from 'classnames';
import React, {forwardRef} from 'react';

const LabelOptionListItem = ({onCloseButtonClicked, option}) => (
	<li>
		<ClayLabel
			className="ddm-select-option-label text-truncate"
			closeButtonProps={{
				onClick: event => {
					event.preventDefault();
					event.stopPropagation();

					onCloseButtonClicked({event, value: option.value});
				},
			}}
			value={option.value}
		>
			{option.label}
		</ClayLabel>
	</li>
);

const OptionSelected = ({isPlaceholder, label}) => (
	<div
		className={classNames('option-selected', {
			'option-selected-placeholder': isPlaceholder,
		})}
	>
		{label}
	</div>
);

const VisibleSelectInput = forwardRef(
	(
		{
			className,
			id,
			multiple,
			onClick,
			onCloseButtonClicked,
			options,
			readOnly,
			value,
		},
		ref
	) => {
		const triggerPlaceholder = multiple
			? Liferay.Language.get('choose-options')
			: Liferay.Language.get('choose-an-option');

		return (
			<div
				className={classNames(
					className,
					'form-builder-select-field input-group-container'
				)}
				onClick={onClick}
				ref={ref}
			>
				<ul
					className={classNames(
						'form-control results-chosen select-field-trigger',
						{
							'multiple-label-list': multiple,
						}
					)}
					disabled={readOnly}
					id={id}
				>
					{value.length === 0 ? (
						<OptionSelected
							isPlaceholder={true}
							label={triggerPlaceholder}
						/>
					) : (
						value.map(currentValue => {
							return options.map(option => {
								if (currentValue === option.value) {
									if (multiple) {
										return (
											<LabelOptionListItem
												key={`${currentValue}-${option.label}`}
												onCloseButtonClicked={
													onCloseButtonClicked
												}
												option={option}
											/>
										);
									}

									return (
										<OptionSelected
											key={`option-selected-${option.label}`}
											label={option.label}
										/>
									);
								}
							});
						})
					)}

					<a className="select-arrow-down-container">
						<ClayIcon symbol="caret-double" />
					</a>
				</ul>
			</div>
		);
	}
);

export default VisibleSelectInput;

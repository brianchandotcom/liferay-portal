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

import './VisibleSelectInput.scss';

import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import classNames from 'classnames';
import React, {forwardRef} from 'react';

const LabelOptionListItem = ({onCloseButtonClicked, option, readOnly}) => (
	<li>
		<ClayLabel
			className="ddm-select-option-label"
			closeButtonProps={{
				'data-testid': `closeButton${option.value}`,
				'onClick': (event) => {
					event.preventDefault();
					event.stopPropagation();

					onCloseButtonClicked({event, value: option.value});
				},
			}}
			value={option.value}
			withClose={!readOnly}
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
			onKeyDown,
			options,
			readOnly,
			value,
		},
		ref
	) => {
		const triggerPlaceholder = multiple
			? Liferay.Language.get('choose-options')
			: Liferay.Language.get('choose-an-option');

		const isValueEmpty = !value.length;

		const selectedLabel = () => {
			if (isValueEmpty) {
				return triggerPlaceholder;
			}

			const selectedOption = options.find(
				(option) => option.value === value[0]
			);

			return selectedOption ? selectedOption.label : triggerPlaceholder;
		};

		return (
			<div
				className={classNames(
					className,
					'form-builder-select-field input-group-container'
				)}
				onClick={onClick}
				onKeyDown={onKeyDown}
				ref={ref}
			>
				<div
					className={classNames(
						'form-control results-chosen select-field-trigger',
						{
							'disabled': readOnly,
							'multiple-label-list': multiple,
						}
					)}
					disabled={readOnly}
					id={id}
					tabIndex="0"
				>
					{isValueEmpty || (value.length === 1 && !multiple) ? (
						<OptionSelected
							isPlaceholder={isValueEmpty}
							label={selectedLabel()}
						/>
					) : (
						<ul className="ddm-multiple-select-list">
							{value.map((item) => {
								const option = options.find(
									(option) => option.value === item
								);

								return (
									<>
										{option && (
											<LabelOptionListItem
												key={`${option.value}-${option.label}`}
												onCloseButtonClicked={
													onCloseButtonClicked
												}
												option={option}
												readOnly={readOnly}
											/>
										)}
									</>
								);
							})}
						</ul>
					)}

					<a className="select-arrow-down-container">
						<ClayIcon symbol="caret-double" />
					</a>
				</div>
			</div>
		);
	}
);

export default VisibleSelectInput;

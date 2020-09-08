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

import DropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import {FocusScope} from '@clayui/shared';
import classNames from 'classnames';
import React, {useRef, useState} from 'react';

const ColorPicker = ({
	colors,
	disabled,
	onValueChange = () => {},
	small,
	value = '#FFFFFF',
}) => {
	const triggerElementRef = useRef(null);
	const splotchRef = useRef(null);
	const dropdownContainerRef = useRef(null);

	const [active, setActive] = useState(false);

	return (
		<FocusScope arrowKeysUpDown={false}>
			<div className="clay-color-picker">
				<ClayInput.Group
					className="clay-color"
					ref={triggerElementRef}
					small={small}
				>
					<ClayInput.GroupItem shrink>
						<ClayInput.GroupText>
							<Splotch
								className="dropdown-toggle"
								disabled={disabled}
								onClick={() => {
									setActive((active) => !active);

									if (splotchRef.current) {
										splotchRef.current.focus();
									}
								}}
								ref={splotchRef}
								value={value}
							/>
						</ClayInput.GroupText>
					</ClayInput.GroupItem>

					<DropDown.Menu
						active={active}
						alignElementRef={triggerElementRef}
						className="clay-color-dropdown-menu"
						focusRefOnEsc={splotchRef}
						onSetActive={setActive}
						ref={dropdownContainerRef}
					>
						<div className="clay-color-swatch">
							{colors.map(({label, name, value}, i) => (
								<div className="clay-color-swatch-item" key={i}>
									<Splotch
										onClick={() => {
											onValueChange({
												label,
												name,
												value,
											});
											setActive((active) => !active);

											if (splotchRef.current) {
												splotchRef.current.focus();
											}
										}}
										title={label}
										value={value}
									/>
								</div>
							))}
						</div>
					</DropDown.Menu>
				</ClayInput.Group>
			</div>
		</FocusScope>
	);
};

const Splotch = React.forwardRef(
	({active, className, onClick, size, title, value}, ref) => {
		return (
			<button
				className={classNames(
					'btn clay-color-btn clay-color-btn-bordered',
					{
						active,
						[className]: !!className,
					}
				)}
				onClick={onClick}
				ref={ref}
				style={{
					background: `${value}`,
					height: size,
					width: size,
				}}
				title={title}
				type="button"
			/>
		);
	}
);

export default ColorPicker;

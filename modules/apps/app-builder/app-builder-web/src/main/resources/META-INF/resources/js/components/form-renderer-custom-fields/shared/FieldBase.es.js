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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayPopover from '@clayui/popover';
import classNames from 'classnames';
import React, {useRef, useState} from 'react';

import useClickOutside from '../../../hooks/useClickOutside.es';

export default function FieldBase({
	children,
	className,
	disableDropdownButton,
	onSelectedValueChange,
	options,
	selectedValue,
	showPopover = false,
	title,
}) {
	const [show, setShow] = useState(showPopover);
	const [popoverRef, triggerRef] = useClickOutside(
		setShow,
		useRef(null),
		useRef(null)
	);

	return (
		<div
			className={classNames(
				className,
				'd-flex',
				'justify-content-between'
			)}
		>
			<ClayForm.Group className={`${className}__input`}>
				{children}
			</ClayForm.Group>

			<ClayButtonWithIcon
				borderless
				className={`${className}__button`}
				disabled={disableDropdownButton}
				displayType="secondary"
				onClick={() => setShow(!show)}
				ref={triggerRef}
				small
				symbol="ellipsis-v"
				title={title}
			/>

			<ClayPopover
				alignPosition="bottom-right"
				className={`${className}__popover`}
				header={title}
				onShowChange={setShow}
				ref={popoverRef}
				show={show}
			>
				<div className="mt-2">
					<ClayRadioGroup
						onSelectedValueChange={onSelectedValueChange}
						selectedValue={selectedValue}
					>
						{Object.keys(options).map((key) => (
							<ClayRadio
								key={key}
								label={options[key].label}
								value={key}
							/>
						))}
					</ClayRadioGroup>
				</div>
			</ClayPopover>
		</div>
	);
}

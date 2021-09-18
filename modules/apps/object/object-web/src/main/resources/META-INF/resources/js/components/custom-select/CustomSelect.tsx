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
import React from 'react';

import './CustomSelect.scss';

interface ICustomSelectProps extends React.HTMLAttributes<HTMLElement> {
	contentRight: React.ReactNode;
	value?: string;
}

const CustomSelect: React.ForwardRefExoticComponent<
	ICustomSelectProps & React.RefAttributes<HTMLDivElement>
> = React.forwardRef(
	(
		{contentRight, onClick, placeholder, value, ...otherProps},
		forwardRef
	) => {
		return (
			<div
				{...otherProps}
				className="custom-select__content form-control"
				onClick={onClick}
				ref={forwardRef}
				tabIndex={0}
			>
				{value ? (
					<span>{value}</span>
				) : (
					<span className="text-secondary">{placeholder}</span>
				)}

				<div>
					{contentRight}

					<a className="custom-select__icon">
						<ClayIcon symbol="caret-double" />
					</a>
				</div>
			</div>
		);
	}
);

export default CustomSelect;

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

import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React from 'react';
import {InputAreaWithError} from '../InputArea/WithError';
import {Label} from '../Label';

export const SearchInput = React.forwardRef(
	(
		{
			children,
			error,
			isMobileDevice = false,
			label,
			name,
			renderActions,
			required = false,
			...props
		},
		ref
	) => {
		return (
			<>
				{label && (
					<div className="mb-4 mb-lg-2 mb-md-2 mb-sm-4">
						<Label
							className={isMobileDevice ? 'h4 text-center' : null}
							label={label}
							name={name}
							required={required}
						>
							{renderActions}
						</Label>
					</div>
				)}
				<div className="d-flex flex-row position-relative">
					<InputAreaWithError className="col px-0" error={error}>
						<ClayInput
							{...props}
							id={name}
							maxLength={255}
							name={name}
							onKeyPress={(event) => {
								if (event.key === 'Enter') {
									event.preventDefault();
								}
							}}
							ref={ref}
							required={required}
						/>

						{isMobileDevice && (
							<ClayIcon
								className="bussiness-type-search-icon position-absolute text-neutral-6"
								symbol="search"
							/>
						)}
					</InputAreaWithError>

					{children}
				</div>
			</>
		);
	}
);

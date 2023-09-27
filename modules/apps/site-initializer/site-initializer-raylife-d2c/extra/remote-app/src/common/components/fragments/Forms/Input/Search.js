/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

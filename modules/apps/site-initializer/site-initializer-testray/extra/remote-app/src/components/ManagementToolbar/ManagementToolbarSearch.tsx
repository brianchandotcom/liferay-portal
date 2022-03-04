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
import {ClayInput} from '@clayui/form';
import ClayManagementToolbar from '@clayui/management-toolbar';
import React, {Dispatch, useEffect, useState} from 'react';

import i18n from '../../i18n';

type ManagementToolbarSearchProps = {
	disabled: boolean;
	onSubmit: (value: string) => void;
	searchText?: string;
	setShowMobile: Dispatch<boolean>;
	showMobile: boolean;
};

const ManagementToolbarSearch: React.FC<ManagementToolbarSearchProps> = ({
	disabled,
	onSubmit,
	searchText = '',
	setShowMobile,
	showMobile,
	...restProps
}) => {
	const [value, setValue] = useState(searchText);

	useEffect(() => {
		setValue(searchText);
	}, [searchText]);

	return (
		<ClayManagementToolbar.Search
			onSubmit={(event) => {
				event.preventDefault();
				onSubmit(value.trim());
			}}
			showMobile={showMobile}
		>
			<ClayInput.Group>
				<ClayInput.GroupItem>
					<ClayInput
						aria-label={i18n.translate('search')}
						className="input-group-inset input-group-inset-after"
						disabled={disabled}
						onChange={({target: {value}}) => setValue(value)}
						placeholder={`${i18n.translate('search')}...`}
						type="text"
						value={value}
						{...restProps}
					/>

					<ClayInput.GroupInsetItem after tag="span">
						<ClayButtonWithIcon
							className="navbar-breakpoint-d-none"
							disabled={disabled}
							displayType="unstyled"
							onClick={() => setShowMobile(false)}
							symbol="times"
						/>

						<ClayButtonWithIcon
							disabled={disabled}
							displayType="unstyled"
							symbol="search"
							type="submit"
						/>
					</ClayInput.GroupInsetItem>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayManagementToolbar.Search>
	);
};

export default ManagementToolbarSearch;

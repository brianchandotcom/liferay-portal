/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import {ManagementToolbar} from 'frontend-js-components-web';
import React, {useEffect, useState} from 'react';

export default function ManagementToolbarSearch({
	disabled,
	onSubmit,
	searchText = '',
	setShowMobile,
	showMobile,
	...restProps
}) {
	const [value, setValue] = useState(searchText);

	useEffect(() => {
		setValue(searchText);
	}, [searchText]);

	return (
		<ManagementToolbar.Search
			onSubmit={(event) => {
				event.preventDefault();
				onSubmit(value.trim());
			}}
			showMobile={showMobile}
		>
			<ClayInput.Group>
				<ClayInput.GroupItem>
					<ClayInput
						aria-label={Liferay.Language.get('search')}
						className="input-group-inset input-group-inset-after"
						disabled={disabled}
						onChange={({target: {value}}) => setValue(value)}
						placeholder={`${Liferay.Language.get('search')}...`}
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
		</ManagementToolbar.Search>
	);
}

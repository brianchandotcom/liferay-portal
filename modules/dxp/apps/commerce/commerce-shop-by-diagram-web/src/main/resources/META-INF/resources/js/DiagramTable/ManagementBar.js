/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import {ManagementToolbar} from 'frontend-js-components-web';
import {debounce} from 'frontend-js-web';
import React, {useRef, useState} from 'react';

function ManagementBar({updateQuery}) {
	const [inputValue, setInputValue] = useState('');
	const debouncedUpdateQueryRef = useRef(debounce(updateQuery, 500));

	function handleInputChange({target}) {
		setInputValue(target.value);

		debouncedUpdateQueryRef.current(target.value);
	}

	return (
		<ManagementToolbar.Container className="border-bottom">
			<ManagementToolbar.Search
				onSubmit={(event) => event.preventDefault()}
			>
				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayInput
							aria-label={Liferay.Language.get('search')}
							className="form-control input-group-inset input-group-inset-after"
							onChange={handleInputChange}
							type="text"
							value={inputValue}
						/>

						<ClayInput.GroupInsetItem after tag="span">
							<ClayButtonWithIcon
								displayType="unstyled"
								symbol="search"
								type="submit"
							/>
						</ClayInput.GroupInsetItem>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ManagementToolbar.Search>
		</ManagementToolbar.Container>
	);
}

export default ManagementBar;

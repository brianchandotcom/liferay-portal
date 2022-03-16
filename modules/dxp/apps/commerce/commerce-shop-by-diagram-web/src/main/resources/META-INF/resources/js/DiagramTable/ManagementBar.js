/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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

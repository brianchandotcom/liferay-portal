/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Form, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {InternalDispatch, useDebounce, useIsFirstRender} from '@clayui/shared';
import React, {useEffect, useState} from 'react';

function SideNavigationSearchInput({
	onChange,
}: {
	onChange?: InternalDispatch<string>;
}) {
	const [query, setQuery] = useState('');

	const debouncedQuery = useDebounce(query, 300);
	const isFirstRender = useIsFirstRender();

	useEffect(() => {
		if (!isFirstRender && onChange) {
			onChange(debouncedQuery);
		}
	}, [debouncedQuery, isFirstRender, onChange]);

	return (
		<Form.Group>
			<ClayInput.Group>
				<ClayInput.GroupItem>
					<ClayInput
						aria-label={Liferay.Language.get('search')}
						id="com_liferay_application_list_taglib_side_navigation_search_input"
						insetBefore
						onChange={(event) => setQuery(event.target.value)}
						placeholder={Liferay.Language.get('search')}
						type="text"
						value={query}
					/>

					<ClayInput.GroupInsetItem before>
						<ClayIcon className="c-ml-3 c-mr-1" symbol="search" />
					</ClayInput.GroupInsetItem>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</Form.Group>
	);
}

export default SideNavigationSearchInput;

/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Form, {ClayInput} from '@clayui/form';
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
		<Form.Group className="c-mx-1 c-px-2">
			<ClayInput
				aria-label={Liferay.Language.get('search')}
				className="c-pl-3"
				onChange={(event) => setQuery(event.target.value)}
				placeholder={Liferay.Language.get('search')}
				type="search"
				value={query}
			/>
		</Form.Group>
	);
}

export default SideNavigationSearchInput;

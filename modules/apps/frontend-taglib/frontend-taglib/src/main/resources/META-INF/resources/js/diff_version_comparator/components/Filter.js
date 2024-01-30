/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useEffect, useRef} from 'react';

export default function Filter({onChange, query}) {
	const inputRef = useRef();

	useEffect(() => {
		inputRef.current.focus();
	}, [query]);

	return (
		<ClayForm.Group>
			<ClayInput.Group>
				<ClayInput.GroupItem prepend>
					<ClayInput
						onChange={onChange}
						placeholder={Liferay.Language.get('search')}
						ref={inputRef}
						type="text"
						value={query}
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem append shrink>
					<ClayInput.GroupText>
						<ClayIcon symbol="search" />
					</ClayInput.GroupText>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayForm.Group>
	);
}

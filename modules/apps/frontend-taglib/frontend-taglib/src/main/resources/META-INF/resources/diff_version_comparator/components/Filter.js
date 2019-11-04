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

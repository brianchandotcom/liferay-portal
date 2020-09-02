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
import React, {useContext, useEffect, useState} from 'react';

import DataSetDisplayContext from '../../DataSetDisplayContext';

function MainSearch() {
	const {searchParam, updateSearchParam} = useContext(DataSetDisplayContext);

	const [inputValue, updateInputValue] = useState(searchParam);

	useEffect(() => {
		updateInputValue(searchParam || '');
	}, [searchParam]);

	function handleKeyDown(event) {
		if (event.keyCode === 13) {
			event.preventDefault();

			return updateSearchParam(inputValue);
		}
	}

	return (
		<ClayInput.Group>
			<ClayInput.GroupItem>
				<ClayInput
					className="input-group-inset input-group-inset-after"
					onChange={(event) => updateInputValue(event.target.value)}
					onKeyDown={handleKeyDown}
					placeholder={Liferay.Language.get('search')}
					value={inputValue}
				/>
				<ClayInput.GroupInsetItem after tag="div">
					<ClayButtonWithIcon
						disabled={!inputValue.length}
						displayType="unstyled"
						onClick={(event) => {
							event.preventDefault();
							
							updateInputValue('');

							updateSearchParam('');
						}}
						style={{
							opacity: !inputValue.length ? 0 : 1,
							pointerEvents: !inputValue.length ? 'none' : 'auto',
						}}
						symbol="times-circle"
					/>
					<ClayButtonWithIcon
						displayType="unstyled"
						onClick={(event) => {
							event.preventDefault();

							updateSearchParam(inputValue);
						}}
						symbol="search"
					/>
				</ClayInput.GroupInsetItem>
			</ClayInput.GroupItem>
		</ClayInput.Group>
	);
}

export default MainSearch;

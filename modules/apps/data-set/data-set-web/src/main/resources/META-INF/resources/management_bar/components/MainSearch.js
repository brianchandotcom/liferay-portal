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

function MainSearch({setShowMobile}) {
	const {searchParam, updateSearchParam} = useContext(DataSetDisplayContext);

	const [inputValue, updateInputValue] = useState(searchParam);

	useEffect(() => {
		updateInputValue(searchParam || '');
	}, [searchParam]);

	function handleKeyDown(event) {
		if (event.key === 'Enter') {
			event.preventDefault();

			return updateSearchParam(inputValue);
		}
	}

	return (
		<ClayInput.Group>
			<ClayInput.GroupItem>
				<ClayInput
					aria-label={Liferay.Language.get('search')}
					className="input-group-inset input-group-inset-after"
					onChange={(event) => updateInputValue(event.target.value)}
					onKeyDown={handleKeyDown}
					placeholder={Liferay.Language.get('search')}
					value={inputValue}
				/>
				<ClayInput.GroupInsetItem after tag="div">
					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('clear')}
						className="navbar-breakpoint-d-none"
						disabled={!inputValue.length}
						displayType="unstyled"
						monospaced={false}
						onClick={(event) => {
							event.preventDefault();

							updateInputValue('');
							setShowMobile(false);

							updateSearchParam('');
						}}
						style={{
							opacity: !inputValue.length ? 0 : 1,
							pointerEvents: !inputValue.length ? 'none' : 'auto',
						}}
						symbol="times-circle"
					/>
					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('search')}
						displayType="unstyled"
						monospaced={false}
						onClick={(event) => {
							event.preventDefault();

							updateSearchParam(inputValue);
						}}
						symbol="search"
						type="submit"
					/>
				</ClayInput.GroupInsetItem>
			</ClayInput.GroupItem>
		</ClayInput.Group>
	);
}

export default MainSearch;

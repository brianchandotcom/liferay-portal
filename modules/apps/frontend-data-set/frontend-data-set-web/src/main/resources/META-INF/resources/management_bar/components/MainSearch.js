/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import React, {useContext, useEffect, useState} from 'react';

import FrontendDataSetContext from '../../FrontendDataSetContext';

function MainSearch({setShowMobile}) {
	const {searchParam, updateSearchParam} = useContext(FrontendDataSetContext);

	const [inputValue, setInputValue] = useState(searchParam);

	useEffect(() => {
		setInputValue(searchParam || '');
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
					onChange={(event) => setInputValue(event.target.value)}
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

							setInputValue('');
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

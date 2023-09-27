/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import {memo, useEffect, useState} from 'react';
import i18n from '../../../../common/I18n';

const SearchBar = ({clearSearchTerm, onSearchSubmit}) => {
	const [term, setTerm] = useState('');
	const [searching, setSearching] = useState(true);

	const handleSearchSubmit = () => {
		if (searching) {
			onSearchSubmit(term);
			setSearching(false);

			return;
		}

		setTerm('');
		onSearchSubmit('');
		setSearching(true);
	};

	useEffect(() => {
		if (clearSearchTerm) {
			setTerm('');
			onSearchSubmit('');
			setSearching(true);
		}
	}, [clearSearchTerm, onSearchSubmit]);

	return (
		<ClayInput.Group className="m-0 mr-2">
			<ClayInput.GroupItem>
				<ClayInput
					className="form-control input-group-inset input-group-inset-after"
					onChange={(event) => {
						setTerm(event.target.value);
						setSearching(true);
					}}
					onKeyPress={(event) => {
						if (event.key === 'Enter') {
							handleSearchSubmit();
						}
					}}
					placeholder={i18n.translate('search')}
					type="text"
					value={term}
				/>

				<ClayInput.GroupInsetItem after tag="span">
					{searching || !term ? (
						<ClayButtonWithIcon
							displayType="unstyled"
							onClick={() => handleSearchSubmit()}
							symbol="search"
						/>
					) : (
						<ClayButtonWithIcon
							className="navbar-breakpoint-d-none"
							displayType="unstyled"
							onClick={() => handleSearchSubmit()}
							symbol="times"
						/>
					)}
				</ClayInput.GroupInsetItem>
			</ClayInput.GroupItem>
		</ClayInput.Group>
	);
};
export default memo(SearchBar);

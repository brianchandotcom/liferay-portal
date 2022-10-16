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
import {memo, useState} from 'react';
import i18n from '../../../../common/I18n';

const SearchBar = ({onSearchSubmit}) => {
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

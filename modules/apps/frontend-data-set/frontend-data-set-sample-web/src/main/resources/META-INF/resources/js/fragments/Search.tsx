/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {IFDSState} from '@liferay/frontend-data-set-web';
import {useLiferayState} from '@liferay/frontend-js-state-web/react';
import React, {useEffect, useState} from 'react';

import {advancedFDSAtom} from '../utils/atoms';

const Search = () => {
	const [advancedFDSState, setAdvancedFDSState] =
		useLiferayState<IFDSState>(advancedFDSAtom);

	const [query, setQuery] = useState(advancedFDSState.search.query ?? '');

	useEffect(() => {
		setQuery(advancedFDSState.search.query);
	}, [advancedFDSState]);

	return (
		<ClayLayout.ContainerFluid>
			<ClayInput.Group className="pt-2">
				<ClayInput.GroupItem>
					<ClayInput
						className="form-control"
						component="input"
						onChange={({target: {value}}) => setQuery(value)}
						placeholder="Search in Advanced tab of Frontend Data Set Sample"
						value={query}
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem>
					<ClayButton
						data-qa-id="searchFDSSampleButton"
						onClick={() => {
							setAdvancedFDSState({
								...(advancedFDSState as IFDSState),
								search: {query},
							});
						}}
					>
						Search
					</ClayButton>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayLayout.ContainerFluid>
	);
};

export default Search;

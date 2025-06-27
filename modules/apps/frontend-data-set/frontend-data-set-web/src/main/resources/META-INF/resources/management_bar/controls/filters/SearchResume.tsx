/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React, {useContext} from 'react';

import FrontendDataSetContext from '../../../FrontendDataSetContext';

function SearchResume() {
	const {onSearch, searchParam} = useContext(FrontendDataSetContext);

	const handleCloseClick = () => {
		onSearch({query: ''});
	};

	return (
		<ClayButton.Group>
			<ClayButton
				className="component-label tbar-label"
				displayType="secondary"
				size="sm"
			>
				<span className="mr-1">
					{Liferay.Language.get('search-colon')}
				</span>

				<strong>{searchParam}</strong>
			</ClayButton>

			<ClayButton
				aria-label={Liferay.Language.get('clear-search')}
				displayType="secondary"
				monospaced
				onClick={handleCloseClick}
				size="sm"
				title={Liferay.Language.get('clear-search')}
			>
				<ClayIcon symbol="times-small" />
			</ClayButton>
		</ClayButton.Group>
	);
}

export default SearchResume;

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React from 'react';

function SuggestionContributorAddButton({children}) {
	return (
		<ClayDropDown
			closeOnClick
			menuWidth="sm"
			trigger={
				<ClayButton
					aria-label={Liferay.Language.get('suggestion-contributor')}
					displayType="secondary"
				>
					<span className="inline-item inline-item-before">
						<ClayIcon symbol="plus" />
					</span>

					{Liferay.Language.get('add-suggestions')}
				</ClayButton>
			}
		>
			{children}
		</ClayDropDown>
	);
}

export default SuggestionContributorAddButton;

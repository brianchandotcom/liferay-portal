/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';
import PropTypes from 'prop-types';
import React from 'react';

export default function SuggestionsPanel({items}) {
	return items?.length ? null : (
		<ClayEmptyState
			description=""
			imgSrc={`${themeDisplay.getPathThemeImages()}/states/empty_state.gif`}
			small
			title={Liferay.Language.get('there-are-no-suggestions')}
		/>
	);
}

SuggestionsPanel.propTypes = {
	items: PropTypes.array,
};

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import ClayTable from '@clayui/table';
import PropTypes from 'prop-types';
import React from 'react';

const LanguageListItem = ({displayName, isDefault}) => (
	<ClayTable.Row>
		<ClayTable.Cell expanded>
			{displayName}

			{isDefault && (
				<ClayLabel className="ml-3" displayType="info">
					{Liferay.Language.get('default')}
				</ClayLabel>
			)}
		</ClayTable.Cell>
	</ClayTable.Row>
);

LanguageListItem.propTypes = {
	isDefault: PropTypes.bool.isRequired,
};

export default LanguageListItem;

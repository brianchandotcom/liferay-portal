/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import PropTypes from 'prop-types';
import React from 'react';

import DefaultContent from './DefaultRenderer';

function LinkRenderer({value}) {
	return (
		<div className="table-list-title">
			<ClayLink href={value?.href}>
				<DefaultContent value={value?.label} />
			</ClayLink>
		</div>
	);
}

LinkRenderer.propTypes = {
	value: PropTypes.shape({
		href: PropTypes.string,
		label: PropTypes.string,
	}),
};

export default LinkRenderer;

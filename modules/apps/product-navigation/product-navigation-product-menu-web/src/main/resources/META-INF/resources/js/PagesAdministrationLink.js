/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import PropTypes from 'prop-types';
import React from 'react';

export default function PagesAdministrationLink({
	administrationPortletURL,
	hasAdministrationPortletPermission,
}) {
	return (
		hasAdministrationPortletPermission && (
			<div className="pages-administration-link">
				<ClayLink className="ml-2" href={administrationPortletURL}>
					{Liferay.Language.get('go-to-pages-administration')}
				</ClayLink>
			</div>
		)
	);
}

PagesAdministrationLink.propTypes = {
	administrationPortletURL: PropTypes.array.isRequired,
	hasAdministrationPortletPermission: PropTypes.bool.isRequired,
};

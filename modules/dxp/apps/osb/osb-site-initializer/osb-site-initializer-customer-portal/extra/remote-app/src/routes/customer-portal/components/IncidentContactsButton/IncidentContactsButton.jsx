/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';

const IncidentContactsButton = ({href, title}) => (
	<a
		className="align-items-center border border-secondary btn cp-manage-users-button d-flex mr-3 p-2 text-neutral-10 text-nowrap"
		href={href}
		rel="noopener noreferrer"
		target="_blank"
	>
		<h6 className="font-weight-semi-bold m-0 pr-1">{title}</h6>

		<span className="inline-item inline-item-after mt-0">
			<ClayIcon className="cp-manage-users-icon" symbol="shortcut" />
		</span>
	</a>
);

export default IncidentContactsButton;

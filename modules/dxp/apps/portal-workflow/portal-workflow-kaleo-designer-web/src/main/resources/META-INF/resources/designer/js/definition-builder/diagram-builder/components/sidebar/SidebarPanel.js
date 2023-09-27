/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

export default function SidebarPanel({children, panelTitle}) {
	const [panelCollapsed, setPanelCollapsed] = useState(false);

	return (
		<div className="panel-group panel-group-flush" role="tablist">
			<div className="panel">
				<div
					className="sheet-subtitle"
					onClick={() => setPanelCollapsed(!panelCollapsed)}
					role="tab"
				>
					<span>{panelTitle}</span>

					{panelCollapsed ? (
						<ClayIcon symbol="angle-right" />
					) : (
						<ClayIcon symbol="angle-down" />
					)}
				</div>

				<div
					className={`panel-collapse ${panelCollapsed && 'collapse'}`}
					role="tabpanel"
				>
					<div className="panel-body">{children}</div>
				</div>
			</div>
		</div>
	);
}

SidebarPanel.propTypes = {
	children: PropTypes.any,
	panelTitle: PropTypes.string.isRequired,
};

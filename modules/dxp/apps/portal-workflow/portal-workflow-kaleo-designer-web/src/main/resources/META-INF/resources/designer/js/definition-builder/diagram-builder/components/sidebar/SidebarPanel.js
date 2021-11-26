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

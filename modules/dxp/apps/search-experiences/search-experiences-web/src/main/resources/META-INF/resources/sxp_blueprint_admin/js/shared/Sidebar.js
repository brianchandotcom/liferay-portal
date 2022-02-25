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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import React from 'react';

function Sidebar({children, className, onClose, title, visible}) {
	return (
		<div
			className={getCN(className, 'sidebar', 'sidebar-light', {
				open: visible,
			})}
		>
			<div className="sidebar-header">
				<h4 className="component-title">
					<span className="text-truncate-inline">
						<span className="text-truncate">{title}</span>
					</span>
				</h4>

				<span>
					<ClayButton
						aria-label={Liferay.Language.get('close')}
						borderless
						displayType="secondary"
						monospaced
						onClick={onClose}
						small
					>
						<ClayIcon symbol="times" />
					</ClayButton>
				</span>
			</div>

			{children}
		</div>
	);
}

export default React.memo(Sidebar);

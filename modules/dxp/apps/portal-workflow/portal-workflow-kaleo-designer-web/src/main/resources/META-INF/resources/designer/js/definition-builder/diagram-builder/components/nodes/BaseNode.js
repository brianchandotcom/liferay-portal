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
import React from 'react';

export default function BaseNode({className, description, icon, label}) {
	return (
		<div className={`node ${className}`}>
			<div className="mr-2 node-icon">
				<ClayIcon symbol={icon} />
			</div>

			<div className="node-info">
				<span className="node-label">{label}</span>

				<span className="node-description text-muted">
					{description}
				</span>
			</div>
		</div>
	);
}

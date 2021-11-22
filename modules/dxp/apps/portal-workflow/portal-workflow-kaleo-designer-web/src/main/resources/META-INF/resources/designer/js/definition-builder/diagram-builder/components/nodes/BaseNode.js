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
import React, {useContext} from 'react';

import {DiagramBuilderContext} from '../../DiagramBuilderContext';
import {nodeDescription} from './utils';

export default function BaseNode({
	className,
	description,
	descriptionSidebar,
	icon,
	label,
	type,
	...otherProps
}) {
	const {availableArea} = useContext(DiagramBuilderContext);

	const borderAreaColor = availableArea ? 'blue' : 'red';
	const displayBorderArea = !descriptionSidebar && availableArea !== null;

	const descriptionColor = descriptionSidebar
		? 'text-secondary'
		: 'text-muted';

	if (!description) {
		description = nodeDescription[type];
	}

	return (
		<div style={{position: 'relative'}}>
			{displayBorderArea && (
				<div className={`node-border-area ${borderAreaColor}`} />
			)}

			<div
				className={`node ${className}`}
				style={{
					position: displayBorderArea ? 'absolute' : 'unset',
				}}
				{...otherProps}
			>
				{descriptionSidebar && (
					<ClayIcon className="mr-4 text-secondary" symbol="drag" />
				)}

				<div className="mr-3 node-icon">
					<ClayIcon symbol={icon} />
				</div>

				<div className="node-info">
					<span
						className="node-label truncate-container"
						title={label}
					>
						{label}
					</span>

					<span
						className={`node-description truncate-container ${descriptionColor}`}
						title={descriptionSidebar ?? description}
					>
						{descriptionSidebar ?? description}
					</span>
				</div>
			</div>
		</div>
	);
}

BaseNode.propTypes = {
	className: PropTypes.string,
	description: PropTypes.string,
	descriptionSidebar: PropTypes.string,
	icon: PropTypes.string.isRequired,
	label: PropTypes.string,
	type: PropTypes.string.isRequired,
};

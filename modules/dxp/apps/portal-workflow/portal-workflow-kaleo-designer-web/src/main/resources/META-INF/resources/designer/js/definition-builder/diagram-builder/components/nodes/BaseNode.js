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

import {DefinitionBuilderContext} from '../../../DefinitionBuilderContext';
import {DiagramBuilderContext} from '../../DiagramBuilderContext';
import {nodeDescription} from './utils';

export default function BaseNode({
	className,
	description,
	descriptionSidebar,
	icon,
	id,
	label,
	type,
	...otherProps
}) {
	const {defaultLanguageId, selectedLanguageId} = useContext(
		DefinitionBuilderContext
	);
	const {availableArea, selectedNode, setSelectedNode} = useContext(
		DiagramBuilderContext
	);

	const borderAreaColor = availableArea ? 'blue' : 'red';
	const displayBorderArea = !descriptionSidebar && availableArea !== null;

	const descriptionColor = descriptionSidebar
		? 'text-secondary'
		: 'text-muted';

	if (!description) {
		description = nodeDescription[type];
	}

	if (selectedNode?.id === id) {
		className = `${className} selected`;
	}

	let nodeLabel;

	if (selectedLanguageId) {
		if (!label[selectedLanguageId]) {
			nodeLabel = label[defaultLanguageId];
		}
		else {
			nodeLabel = label[selectedLanguageId];
		}
	}
	else {
		nodeLabel = label[defaultLanguageId];
	}

	return (
		<div className="base-node">
			{displayBorderArea && (
				<div className={`node-border-area ${borderAreaColor}`} />
			)}

			<div
				className={`node ${className}`}
				onClick={() => {
					if (!descriptionSidebar) {
						setSelectedNode({
							data: {
								description,
								label,
							},
							id,
							type,
						});
					}
				}}
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
						title={nodeLabel}
					>
						{nodeLabel}
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
	id: PropTypes.string.isRequired,
	label: PropTypes.object,
	type: PropTypes.string.isRequired,
};

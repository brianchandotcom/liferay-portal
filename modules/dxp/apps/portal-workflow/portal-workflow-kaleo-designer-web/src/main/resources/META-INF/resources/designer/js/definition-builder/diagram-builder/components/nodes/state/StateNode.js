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

import PropTypes from 'prop-types';
import React from 'react';

import {defaultLanguageId} from '../../../../constants';
import BaseNode from '../BaseNode';

export default function StateNode({
	data: {actions, description, label, newNode, notifications} = {},
	descriptionSidebar,
	id,
	...otherProps
}) {
	if (!label || !label[defaultLanguageId]) {
		label = {
			[defaultLanguageId]: Liferay.Language.get('state'),
		};
	}

	return (
		<BaseNode
			actions={actions}
			className="state-node"
			description={description}
			descriptionSidebar={descriptionSidebar}
			icon="circle"
			id={id}
			label={label}
			newNode={newNode}
			notifications={notifications}
			type="state"
			{...otherProps}
		/>
	);
}

StateNode.propTypes = {
	data: PropTypes.object,
	descriptionSidebar: PropTypes.string,
	id: PropTypes.string,
};

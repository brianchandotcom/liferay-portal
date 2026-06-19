/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import {defaultLanguageId} from '../../../constants';
import BaseNode from './BaseNode';

export default function HTTPRequestNode({
	data: {
		description,
		httpMethod,
		inputVariables,
		label,
		newNode,
		outputVariables,
		requestBody,
		timeout,
		url,
	} = {},
	descriptionSidebar,
	id,
	...otherProps
}) {
	if (!label || !label[defaultLanguageId]) {
		label = {
			[defaultLanguageId]: Liferay.Language.get('http-request'),
		};
	}

	return (
		<BaseNode
			description={description}
			descriptionSidebar={descriptionSidebar}
			httpMethod={httpMethod}
			icon="globe"
			id={id}
			inputVariables={inputVariables}
			label={label}
			newNode={newNode}
			nodeTypeClassName="http-request-node"
			outputVariables={outputVariables}
			requestBody={requestBody}
			timeout={timeout}
			type="http-request"
			url={url}
			{...otherProps}
		/>
	);
}

HTTPRequestNode.propTypes = {
	data: PropTypes.object,
	descriptionSidebar: PropTypes.string,
	id: PropTypes.string,
};

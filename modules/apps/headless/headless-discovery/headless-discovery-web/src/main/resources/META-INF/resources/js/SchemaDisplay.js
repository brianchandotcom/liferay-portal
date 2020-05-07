/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayPanel from '@clayui/panel';
import React from 'react';

import {spritemap} from './Icon';

const style = {
	overflow: 'visible',
	tabSize: 4,
};

const SchemaDisplay = ({name, schema}) => {
	return (
		<ClayPanel
			className="schema-display-root"
			collapsable
			displayTitle={name}
			displayType="secondary"
			showCollapseIcon={true}
			spritemap={spritemap}
		>
			<ClayPanel.Body className="overflow-auto">
				<pre style={style}>{JSON.stringify(schema, null, 4)}</pre>
			</ClayPanel.Body>
		</ClayPanel>
	);
};

export default SchemaDisplay;

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

import React from 'react';

import SchemaDisplay from './SchemaDisplay';

const style = {
	lineHeight: '1em',
};

const SchemaExplorer = ({category, schemas}) => {
	return (
		<div className="schema-explorer-root">
			<div className="align-items-end d-flex justify-content-between mb-2">
				<h1 style={style}>{'Schemas:'}</h1>

				<h3 style={style}>
					<em>{category}</em>
				</h3>
			</div>

			{Object.keys(schemas).map((key) => (
				<SchemaDisplay key={key} name={key} schema={schemas[key]} />
			))}
		</div>
	);
};

export default SchemaExplorer;

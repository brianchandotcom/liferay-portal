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

import React from 'react';

const PrettyPrintArray = ({value}) => (
	<span>
		{'['}

		{value.map((item, i) => (
			<React.Fragment key={i}>
				<span className="badge badge-pill badge-secondary">{item}</span>

				{i + 1 !== value.length && ', '}
			</React.Fragment>
		))}

		{']'}
	</span>
);

/**
 * Used for displaying hidden values for easier debugging. This component
 * should only be used during development.
 */
const FormValueDebugger = ({values}) => (
	<div
		className="alert alert-dark"
		style={{
			margin: '0 auto',
			maxWidth: '1000px'
		}}
	>
		<p>
			<strong>{'Form hidden values for debugging'}</strong>

			{
				' (Only the values from the frontend component. There are others defined in the JSP)'
			}
		</p>

		<table className="table table-bordered table-striped">
			<thead>
				<tr>
					<th>{'Name'}</th>
					<th>{'Value'}</th>
				</tr>
			</thead>

			<tbody>
				{values.map(({name, value}) => (
					<tr key={name}>
						<td>{name}</td>
						<td>
							{Array.isArray(value) ? (
								<PrettyPrintArray value={value} />
							) : (
								value
							)}
						</td>
					</tr>
				))}
			</tbody>
		</table>
	</div>
);

export default FormValueDebugger;

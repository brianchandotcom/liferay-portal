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

import getCN from 'classnames';

const CLASSNAME = 'workflow-tooltip-chart';

const Body = (...children) =>
	`<tbody class="${CLASSNAME}-body">${children.join('')}</tbody>`;

const Column = ({align, children, className = '', weight}) => {
	const classes = getCN(`${CLASSNAME}-column`, className, {
		[`font-weight-${weight}`]: weight,
		[`text-${align}`]: align
	});

	return `
		<td>
			<div class="${CLASSNAME}-content ${classes}">
				${children}					
			</div>
		</td>
		`;
};

const Header = (...children) =>
	`<thead class="${CLASSNAME}-header">${children.join('')}</thead>`;

const Row = (...children) =>
	`<tr class="${CLASSNAME}-row">${children.join('')}</tr>`;

const TooltipChart = ({header, rows}) => {
	const columnRows = rows.map(({columns}) => columns[0]);

	return `
		<table class="${CLASSNAME}">
			${Header(
				Row(
					header.map(({label, weight, width}) =>
						Column({
							children: label,
							weight,
							width
						})
					)
				)
			)}

			${Body(
				Row(
					...columnRows.map(({label, weight}) =>
						Column({
							children: label,
							weight
						})
					)
				)
			)}
		</table>
	`;
};

export default TooltipChart;

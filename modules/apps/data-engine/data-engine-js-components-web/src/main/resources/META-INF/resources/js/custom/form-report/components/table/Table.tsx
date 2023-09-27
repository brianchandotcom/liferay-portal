/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import React from 'react';

import {
	getPercentage,
	removeEmptyValues,
	roundPercentage,

	// @ts-ignore

} from '../../utils/data';

interface IProps {
	data: Item[];
	totalEntries: boolean;
}

interface Item {
	count: number;
	label: string;
}

export default function Table({data, totalEntries}: IProps) {
	data = removeEmptyValues(data);

	return (
		<ClayTable>
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell expanded headingCell>
						{Liferay.Language.get('options')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell>%</ClayTable.Cell>

					<ClayTable.Cell headingCell>
						{Liferay.Language.get('votes')}
					</ClayTable.Cell>
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body>
				{Array.isArray(data) &&
					data.map(({count, label}, index) => {
						return (
							<ClayTable.Row key={index}>
								<ClayTable.Cell>{label}</ClayTable.Cell>

								<ClayTable.Cell>
									{roundPercentage(
										getPercentage(count, totalEntries)
									)}
								</ClayTable.Cell>

								<ClayTable.Cell>{count}</ClayTable.Cell>
							</ClayTable.Row>
						);
					})}
			</ClayTable.Body>
		</ClayTable>
	);
}

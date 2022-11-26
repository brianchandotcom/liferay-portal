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

import ClayButton from '@clayui/button';
import {ClayToggle} from '@clayui/form';
import ClayTable from '@clayui/table';
import React, {Fragment} from 'react';

import {TProperty} from './Properties';

interface IPropertiesTable {
	onAssign: (index: number) => void;
	onCommerceSwitchChange: (index: number) => void;
	properties: TProperty[];
}

const PropertiesTable: React.FC<IPropertiesTable> = ({
	onAssign,
	onCommerceSwitchChange,
	properties,
}) => {
	return (
		<ClayTable className="mt-4">
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell expanded headingCell>
						{Liferay.Language.get('available-properties')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell>
						{Liferay.Language.get('channel')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell>
						{Liferay.Language.get('sites')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell>
						{Liferay.Language.get('commerce')}
					</ClayTable.Cell>
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body>
				{properties.map(
					(
						{
							commerceSyncEnabled,
							dataSources: [
								{commerceChannelIds = [], siteIds = []} = {},
							],
							name,
						},
						index
					) => (
						<Fragment key={index}>
							<ClayTable.Row>
								<ClayTable.Cell className="table-cell-expand">
									{name}
								</ClayTable.Cell>

								<ClayTable.Cell
									className="mr-2"
									columnTextAlignment="end"
								>
									{commerceSyncEnabled
										? commerceChannelIds.length
										: '-'}
								</ClayTable.Cell>

								<ClayTable.Cell
									className="mr-2"
									columnTextAlignment="end"
								>
									{siteIds.length}
								</ClayTable.Cell>

								<ClayTable.Cell
									className="mr-2"
									columnTextAlignment="end"
								>
									<ClayToggle
										onToggle={() =>
											onCommerceSwitchChange(index)
										}
										toggled={commerceSyncEnabled}
										value="toggle"
									/>
								</ClayTable.Cell>

								<ClayTable.Cell columnTextAlignment="end">
									<ClayButton
										displayType="secondary"
										onClick={() => onAssign(index)}
									>
										{Liferay.Language.get('assign')}
									</ClayButton>
								</ClayTable.Cell>
							</ClayTable.Row>
						</Fragment>
					)
				)}
			</ClayTable.Body>
		</ClayTable>
	);
};

export default PropertiesTable;

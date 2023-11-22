/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayTable from '@clayui/table';
import {sub} from 'frontend-js-web';
import React from 'react';

import {DDMStructure} from './HighlightedDDMStructuresConfiguration';

interface Props {
	onRemoveStructure: (nextStructures: DDMStructure[]) => void;
	structures: DDMStructure[];
}

export function StructureList({onRemoveStructure, structures}: Props) {
	return structures.length ? (
		<ClayTable className="c-my-3">
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell headingCell>
						{Liferay.Language.get('title')}
					</ClayTable.Cell>

					<ClayTable.Cell expanded headingCell>
						{Liferay.Language.get('scope')}
					</ClayTable.Cell>

					<ClayTable.Cell headingCell>
						<span className="sr-only">
							{sub(
								Liferay.Language.get('remove-x'),
								Liferay.Language.get('structure')
							)}
						</span>
					</ClayTable.Cell>
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body>
				{structures.map(({ddmStructureId, name, scope}) => (
					<ClayTable.Row key={ddmStructureId}>
						<ClayTable.Cell expanded>{name}</ClayTable.Cell>

						<ClayTable.Cell expanded>{scope}</ClayTable.Cell>

						<ClayTable.Cell>
							<ClayButtonWithIcon
								aria-label={sub(
									Liferay.Language.get('remove-x'),
									name
								)}
								displayType="unstyled"
								onClick={() =>
									onRemoveStructure(
										structures.filter(
											(structure) =>
												structure.ddmStructureId !==
												ddmStructureId
										)
									)
								}
								symbol="times-circle"
								title={Liferay.Language.get('remove')}
							/>
						</ClayTable.Cell>
					</ClayTable.Row>
				))}
			</ClayTable.Body>
		</ClayTable>
	) : (
		<p className="c-py-3 text-secondary">
			{sub(
				Liferay.Language.get('no-x-selected'),
				Liferay.Language.get('structures')
			)}
		</p>
	);
}

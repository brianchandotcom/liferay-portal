/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import ClayTabs from '@clayui/tabs';
import React, {ComponentType, useState} from 'react';

import {IFDSViewSectionProps} from '../../FDSView';
import List, {IList} from './List';
import Table, {ITable} from './Table';

export interface IBaseVisualizationMode<Type extends string> {
	label: string;
	type: Type;
	visualizationModeId: string;
}

type TVisualizationMode = ITable | IList;

const DEFAULT_VISUALIZATION_MODES: TVisualizationMode[] = [
	{
		label: Liferay.Language.get('table'),
		type: 'table',
		visualizationModeId: 'defaultTable',
	},
	{
		label: Liferay.Language.get('list'),
		type: 'list',
		visualizationModeId: 'defaultList',
	},
];

const VISUALIZATION_MODE_COMPONENT_MAP: {
	[key in TVisualizationMode['type']]: ComponentType<IFDSViewSectionProps>;
} = {
	list: List,
	table: Table,
};

export default function VisualizationModes(props: IFDSViewSectionProps) {
	const [
		activeVisualizationModeIndex,
		setActiveVisualizationModeIndex,
	] = useState(0);

	return (
		<ClayLayout.ContainerFluid>
			<ClayLayout.ContainerFluid className="bg-white mb-4 p-0 rounded-sm">
				<h2 className="mb-0 p-4">
					{Liferay.Language.get('visualization-modes')}
				</h2>

				<ClayTabs
					activation="automatic"
					active={activeVisualizationModeIndex}
					onActiveChange={setActiveVisualizationModeIndex}
				>
					{DEFAULT_VISUALIZATION_MODES.map((visualizationMode) => (
						<ClayTabs.Item
							key={visualizationMode.visualizationModeId}
						>
							{visualizationMode.label}
						</ClayTabs.Item>
					))}
				</ClayTabs>

				<ClayTabs.Content active={activeVisualizationModeIndex} fade>
					{DEFAULT_VISUALIZATION_MODES.map((visualizationMode) => {
						const Component =
							VISUALIZATION_MODE_COMPONENT_MAP[
								visualizationMode.type
							];

						return (
							<ClayTabs.TabPane
								key={visualizationMode.visualizationModeId}
							>
								<Component {...props} />
							</ClayTabs.TabPane>
						);
					})}
				</ClayTabs.Content>
			</ClayLayout.ContainerFluid>
		</ClayLayout.ContainerFluid>
	);
}

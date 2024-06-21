/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import ClayTabs from '@clayui/tabs';
import React, {ComponentType, useState} from 'react';

import {DEFAULT_VISUALIZATION_MODES} from '../../utils/constants';
import {TVisualizationMode} from '../../utils/types';
import {IDataSetSectionProps} from '../DataSet';
import Cards from './modes/Cards';
import List from './modes/List';
import Table from './modes/Table';

const VISUALIZATION_MODE_COMPONENT_MAP: {
	[key in TVisualizationMode['mode']]: ComponentType<IDataSetSectionProps>;
} = {
	cards: Cards,
	list: List,
	table: Table,
};

const ORDERED_DEFAULT_VISUALIZATION_MODES = [
	...DEFAULT_VISUALIZATION_MODES,
].reverse();

export default function VisualizationModes(props: IDataSetSectionProps) {
	const [activeVisualizationModeIndex, setActiveVisualizationModeIndex] =
		useState(0);

	return (
		<ClayLayout.ContainerFluid className="mt-3 visualization-modes">
			<ClayLayout.Sheet>
				<ClayLayout.SheetHeader className="mb-4">
					<h2 className="mb-0">
						{Liferay.Language.get('visualization-modes')}
					</h2>
				</ClayLayout.SheetHeader>

				<ClayTabs
					activation="automatic"
					active={activeVisualizationModeIndex}
					onActiveChange={setActiveVisualizationModeIndex}
				>
					{ORDERED_DEFAULT_VISUALIZATION_MODES.map(
						(visualizationMode) => (
							<ClayTabs.Item
								key={visualizationMode.visualizationModeId}
							>
								{visualizationMode.label}
							</ClayTabs.Item>
						)
					)}
				</ClayTabs>

				<ClayTabs.Content active={activeVisualizationModeIndex} fade>
					{ORDERED_DEFAULT_VISUALIZATION_MODES.map(
						(visualizationMode) => {
							const Component =
								VISUALIZATION_MODE_COMPONENT_MAP[
									visualizationMode.mode as TVisualizationMode['mode']
								];

							return (
								<ClayTabs.TabPane
									className="px-0"
									key={visualizationMode.visualizationModeId}
								>
									<Component {...props} />
								</ClayTabs.TabPane>
							);
						}
					)}
				</ClayTabs.Content>
			</ClayLayout.Sheet>
		</ClayLayout.ContainerFluid>
	);
}

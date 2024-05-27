/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayNavigationBar from '@clayui/navigation-bar';
import {IClientExtensionRenderer} from '@liferay/frontend-data-set-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {IDataSet} from '../DataSets';
import {FDSViewType} from '../FDSViews';
import {API_URL, OBJECT_RELATIONSHIP} from '../utils/constants';
import getFields from '../utils/getFields';
import openDefaultFailureToast from '../utils/openDefaultFailureToast';
import {IFieldTreeItem} from '../utils/types';
import Actions from './actions/Actions';
import Details from './details/Details';
import Filters from './filters/Filters';
import Pagination from './pagination/Pagination';
import Settings from './settings/Settings';
import Sorting from './sorting/Sorting';
import SortingDeprecated from './sorting/SortingDeprecated';
import VisualizationModes from './visualization_modes/VisualizationModes';

const NAVIGATION_BAR_ITEMS = [
	{
		Component: Details,
		label: Liferay.Language.get('details'),
	},
	{
		Component: VisualizationModes,
		label: Liferay.Language.get('visualization-modes'),
	},
	{
		Component: Filters,
		label: Liferay.Language.get('filters'),
	},
	Liferay.FeatureFlags['LPD-19465']
		? {
				Component: Sorting,
				label: Liferay.Language.get('sorting'),
		  }
		: {
				Component: SortingDeprecated,
				label: Liferay.Language.get('sorting'),
		  },
	{
		Component: Actions,
		label: Liferay.Language.get('actions'),
	},
	{
		Component: Pagination,
		label: Liferay.Language.get('pagination'),
	},
	{
		Component: Settings,
		label: Liferay.Language.get('settings'),
	},
];

export interface IDataSetSectionProps {
	backURL: string;
	dataSet: IDataSet | FDSViewType;
	fdsClientExtensionCellRenderers: IClientExtensionRenderer[];
	fdsFilterClientExtensions: IClientExtensionRenderer[];
	fieldTreeItems: Array<IFieldTreeItem>;
	namespace: string;
	onActiveSectionChange: (section: number) => void;
	onDataSetUpdate: (data: FDSViewType) => void;
	saveFDSFieldsURL: string;
	spritemap: string;
}

const DataSet = ({
	backURL,
	dataSetERC,
	fdsClientExtensionCellRenderers,
	fdsFilterClientExtensions,
	fdsViewId,
	namespace,
	saveFDSFieldsURL,
	spritemap,
}: {
	backURL: string;
	dataSetERC: string;
	fdsClientExtensionCellRenderers: IClientExtensionRenderer[];
	fdsFilterClientExtensions: IClientExtensionRenderer[];
	fdsViewId: string;
	namespace: string;
	saveFDSFieldsURL: string;
	spritemap: string;
}) => {
	const [activeIndex, setActiveIndex] = useState(0);
	const [dataSet, setDataSet] = useState<IDataSet>();
	const [fieldTreeItems, setFieldTreeItems] = useState<Array<IFieldTreeItem>>(
		[]
	);
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		const getDataSet = async () => {
			const url = Liferay.FeatureFlags['LPD-15729']
				? `${API_URL.DATA_SETS}/by-external-reference-code/${dataSetERC}`
				: `${API_URL.DATA_SETS}/${fdsViewId}?nestedFields=${OBJECT_RELATIONSHIP.FDS_ENTRY_FDS_VIEW}`;

			const response = await fetch(url, {
				headers: {
					Accept: 'application/json',
				},
			});

			const responseJSON = await response.json();

			if (responseJSON?.id) {
				setDataSet(responseJSON);

				const {restApplication, restSchema} = Liferay.FeatureFlags[
					'LPD-15729'
				]
					? responseJSON
					: responseJSON[OBJECT_RELATIONSHIP.FDS_ENTRY_FDS_VIEW];

				getFields({restApplication, restSchema}).then((fields) => {
					setFieldTreeItems(fields);

					setLoading(false);
				});
			}
			else {
				openDefaultFailureToast();
			}
		};

		getDataSet();
	}, [dataSetERC, fdsViewId]);

	const Content = NAVIGATION_BAR_ITEMS[activeIndex].Component;

	return (
		<div className="cadmin fds-view">
			<ClayNavigationBar
				triggerLabel={NAVIGATION_BAR_ITEMS[activeIndex].label}
			>
				{NAVIGATION_BAR_ITEMS.map((item, index) => (
					<ClayNavigationBar.Item
						active={index === activeIndex}
						key={index}
					>
						<ClayButton onClick={() => setActiveIndex(index)}>
							{item.label}
						</ClayButton>
					</ClayNavigationBar.Item>
				))}
			</ClayNavigationBar>

			{loading ? (
				<ClayLoadingIndicator />
			) : (
				dataSet && (
					<Content
						backURL={backURL}
						dataSet={dataSet}
						fdsClientExtensionCellRenderers={
							fdsClientExtensionCellRenderers
						}
						fdsFilterClientExtensions={fdsFilterClientExtensions}
						fieldTreeItems={fieldTreeItems}
						namespace={namespace}
						onActiveSectionChange={(tab) => setActiveIndex(tab)}
						onDataSetUpdate={(updatedDataSet) => {
							setDataSet({...dataSet, ...updatedDataSet});
						}}
						saveFDSFieldsURL={saveFDSFieldsURL}
						spritemap={spritemap}
					/>
				)
			)}
		</div>
	);
};

export default DataSet;

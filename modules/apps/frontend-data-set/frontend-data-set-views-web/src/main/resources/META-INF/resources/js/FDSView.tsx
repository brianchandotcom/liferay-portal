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

import {FDSViewType} from './FDSViews';
import Actions from './fds_view/Actions';
import Details from './fds_view/Details';
import Pagination from './fds_view/Pagination';
import Settings from './fds_view/Settings';
import Sorting from './fds_view/Sorting';
import SortingDeprecated from './fds_view/SortingDeprecated';
import Filters from './fds_view/filters/Filters';
import VisualizationModes from './fds_view/visualization_modes/VisualizationModes';
import {API_URL, OBJECT_RELATIONSHIP} from './utils/constants';
import openDefaultFailureToast from './utils/openDefaultFailureToast';

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

interface IFDSViewSectionProps {
	fdsClientExtensionCellRenderers: IClientExtensionRenderer[];
	fdsFilterClientExtensions: IClientExtensionRenderer[];
	fdsView: FDSViewType;
	fdsViewsURL: string;
	namespace: string;
	onActiveSectionChange: (section: number) => void;
	onFDSViewUpdate: (data: FDSViewType) => void;
	saveFDSFieldsURL: string;
	spritemap: string;
}

interface IFDSViewProps {
	fdsClientExtensionCellRenderers: IClientExtensionRenderer[];
	fdsFilterClientExtensions: IClientExtensionRenderer[];
	fdsViewId: string;
	fdsViewsURL: string;
	namespace: string;
	saveFDSFieldsURL: string;
	spritemap: string;
}

const FDSView = ({
	fdsClientExtensionCellRenderers,
	fdsFilterClientExtensions,
	fdsViewId,
	fdsViewsURL,
	namespace,
	saveFDSFieldsURL,
	spritemap,
}: IFDSViewProps) => {
	const [activeIndex, setActiveIndex] = useState(0);
	const [fdsView, setFDSView] = useState<FDSViewType>();
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		const getFDSView = async () => {
			const response = await fetch(
				`${API_URL.FDS_VIEWS}/${fdsViewId}?nestedFields=${OBJECT_RELATIONSHIP.FDS_ENTRY_FDS_VIEW}`,
				{
					headers: {
						Accept: 'application/json',
					},
				}
			);

			const responseJSON = await response.json();

			if (responseJSON?.id) {
				setFDSView(responseJSON);

				setLoading(false);
			}
			else {
				openDefaultFailureToast();
			}
		};

		getFDSView();
	}, [fdsViewId]);

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
				fdsView && (
					<Content
						fdsClientExtensionCellRenderers={
							fdsClientExtensionCellRenderers
						}
						fdsFilterClientExtensions={fdsFilterClientExtensions}
						fdsView={fdsView}
						fdsViewsURL={fdsViewsURL}
						namespace={namespace}
						onActiveSectionChange={(tab) => setActiveIndex(tab)}
						onFDSViewUpdate={(updatedFdsViewData) => {
							setFDSView({...fdsView, ...updatedFdsViewData});
						}}
						saveFDSFieldsURL={saveFDSFieldsURL}
						spritemap={spritemap}
					/>
				)
			)}
		</div>
	);
};

export {IFDSViewSectionProps};
export default FDSView;

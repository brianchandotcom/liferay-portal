/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayNavigationBar from '@clayui/navigation-bar';
import {IClientExtensionRenderer, fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {API_URL, OBJECT_RELATIONSHIP} from './Constants';
import {FDSViewType} from './FDSViews';
import Actions from './fds_view/Actions';
import Details from './fds_view/Details';
import Fields from './fds_view/Fields';
import Filters from './fds_view/Filters';
import Pagination from './fds_view/Pagination';
import Sorting from './fds_view/Sorting';
import openDefaultFailureToast from './utils/openDefaultFailureToast';

let NAVIGATION_BAR_ITEMS = [
	{
		Component: Details,
		label: Liferay.Language.get('details'),
	},
	{
		Component: Fields,
		label: Liferay.Language.get('fields'),
	},
];

if (Liferay.FeatureFlags['LPS-188645']) {
	NAVIGATION_BAR_ITEMS = [
		...NAVIGATION_BAR_ITEMS,
		{
			Component: Filters,
			label: Liferay.Language.get('filters'),
		},
		{
			Component: Sorting,
			label: Liferay.Language.get('sorting'),
		},
	];
}

if (Liferay.FeatureFlags['LPS-192282']) {
	NAVIGATION_BAR_ITEMS = [
		...NAVIGATION_BAR_ITEMS,
		{
			Component: Actions,
			label: Liferay.Language.get('actions'),
		},
	];
}

NAVIGATION_BAR_ITEMS = [
	...NAVIGATION_BAR_ITEMS,
	{
		Component: Pagination,
		label: Liferay.Language.get('pagination'),
	},
];

interface IFDSViewSectionProps {
	fdsClientExtensionCellRenderers: IClientExtensionRenderer[];
	fdsFilterClientExtensions: IClientExtensionRenderer[];
	fdsView: FDSViewType;
	fdsViewsURL: string;
	namespace: string;
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
		<>
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
						onFDSViewUpdate={(updatedFdsViewData) => {
							setFDSView({...fdsView, ...updatedFdsViewData});
						}}
						saveFDSFieldsURL={saveFDSFieldsURL}
						spritemap={spritemap}
					/>
				)
			)}
		</>
	);
};

export {IFDSViewSectionProps};
export default FDSView;

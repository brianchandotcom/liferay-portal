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
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayNavigationBar from '@clayui/navigation-bar';
import {fetch, openToast} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import '../css/FDSView.scss';
import {API_URL, OBJECT_RELATIONSHIP} from './Constants';
import {FDSViewType} from './FDSViews';
import Details from './fds_view/Details';
import Fields from './fds_view/Fields';
import Filters from './fds_view/Filters';
import Pagination from './fds_view/Pagination';
import Sorting from './fds_view/Sorting';

const NAVIGATION_BAR_ITEMS = [
	{
		Component: Details,
		label: Liferay.Language.get('details'),
	},
	{
		Component: Fields,
		label: Liferay.Language.get('fields'),
	},
	{
		Component: Sorting,
		label: Liferay.Language.get('sorting'),
	},
	{
		Component: Filters,
		label: Liferay.Language.get('filters'),
	},
	{
		Component: Pagination,
		label: Liferay.Language.get('pagination'),
	},
];

interface IFDSViewSectionInterface {
	fdsView: FDSViewType;
	fdsViewsURL: string;
	namespace: string;
	saveFDSFieldsURL: string;
}

interface IFDSViewInterface {
	fdsViewId: string;
	fdsViewsURL: string;
	namespace: string;
	saveFDSFieldsURL: string;
}

const FDSView = ({
	fdsViewId,
	fdsViewsURL,
	namespace,
	saveFDSFieldsURL,
}: IFDSViewInterface) => {
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
				openToast({
					message: Liferay.Language.get(
						'your-request-failed-to-complete'
					),
					type: 'danger',
				});
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
						fdsView={fdsView}
						fdsViewsURL={fdsViewsURL}
						namespace={namespace}
						saveFDSFieldsURL={saveFDSFieldsURL}
					/>
				)
			)}
		</>
	);
};

export {IFDSViewSectionInterface as FDSViewSectionInterface};
export default FDSView;

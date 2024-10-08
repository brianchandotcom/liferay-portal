/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayTabs from '@clayui/tabs';
import {useId} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useState} from 'react';

import {HIGHLIGHTED_CATEGORY_ID} from '../../../../app/config/constants/highlightedCategoryId';
import {HIGHLIGHTED_COLLECTION_ID} from '../../../../app/config/constants/highlightedCollectionId';
import {useSelector} from '../../../../app/contexts/StoreContext';
import {useLoadWidgets} from '../../../../app/contexts/WidgetsContext';
import {TABS_IDS} from '../../config/constants/tabsIds';
import {ItemList} from './ItemList';

export function Tabs({updateLists}) {
	const namespace = useId();

	const getTabId = (id) => `${namespace}tab${id}`;
	const getTabPanelId = (tabId) => `${namespace}tabPanel${tabId}`;

	const [activeTabId, setActiveTabId] = useState(TABS_IDS.fragments);

	const loadWidgets = useLoadWidgets();

	const fragments = useSelector((state) =>
		state.fragments
			.filter(
				({fragmentCollectionId}) =>
					fragmentCollectionId !== HIGHLIGHTED_COLLECTION_ID
			)
			.map(({fragmentCollectionId, name}) => ({
				id: fragmentCollectionId,
				name,
			}))
	);

	const widgets = useSelector((state) =>
		state.widgets
			? state.widgets
					.filter(({path}) => path !== HIGHLIGHTED_CATEGORY_ID)
					.map(({path, title}) => ({
						id: path,
						name: title,
					}))
			: null
	);

	const tabs = useMemo(
		() => [
			{
				id: TABS_IDS.fragments,
				items: fragments,
				label: Liferay.Language.get('fragments'),
			},
			{
				id: TABS_IDS.widgets,
				items: widgets,
				label: Liferay.Language.get('widgets'),
			},
		],
		[fragments, widgets]
	);

	useEffect(() => {
		if (activeTabId === TABS_IDS.widgets && !widgets) {
			loadWidgets();
		}
	}, [activeTabId, loadWidgets, widgets]);

	return (
		<>
			<ClayTabs
				activation="automatic"
				active={activeTabId}
				onActiveChange={setActiveTabId}
			>
				{tabs.map(({id, label}) => (
					<ClayTabs.Item
						innerProps={{
							'aria-controls': getTabPanelId(id),
							'id': getTabId(id),
						}}
						key={id}
					>
						{label}
					</ClayTabs.Item>
				))}
			</ClayTabs>

			<ClayTabs.Content activeIndex={activeTabId} fade>
				{tabs.map(({id, items}) => (
					<ClayTabs.TabPane
						aria-labelledby={getTabId(id)}
						className="p-0"
						id={getTabPanelId(id)}
						key={id}
						tabIndex={0}
					>
						{items ? (
							<ItemList
								items={items}
								listId={id}
								updateLists={updateLists}
							/>
						) : (
							<ClayLoadingIndicator size="sm" />
						)}
					</ClayTabs.TabPane>
				))}
			</ClayTabs.Content>
		</>
	);
}

Tabs.propTypes = {
	updateLists: PropTypes.func.isRequired,
};

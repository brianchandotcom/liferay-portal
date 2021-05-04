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

import ClayAlert from '@clayui/alert';
import ClayTabs from '@clayui/tabs';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useState} from 'react';

import {useCollectionActiveItemContext} from '../../../../../app/contexts/CollectionActiveItemContext';
import {CollectionItemContext} from '../../../../../app/contexts/CollectionItemContext';
import {
	useActiveItemId,
	useActiveItemType,
} from '../../../../../app/contexts/ControlsContext';
import {useSelectorCallback} from '../../../../../app/contexts/StoreContext';
import {deepEqual} from '../../../../../app/utils/checkDeepEqual';
import {useId} from '../../../../../app/utils/useId';
import {PANELS, selectPanels} from '../selectors/selectPanels';
import PageStructureSidebarSection from './PageStructureSidebarSection';

export default function ItemConfiguration() {
	const collectionContext = useCollectionActiveItemContext();

	return (
		<CollectionItemContext.Provider value={collectionContext}>
			<ItemConfigurationContent />
		</CollectionItemContext.Provider>
	);
}

function ItemConfigurationContent() {
	const activeItemId = useActiveItemId();
	const activeItemType = useActiveItemType();
	const [activePanelId, setActivePanelId] = useState(null);
	const tabIdPrefix = useId();
	const panelIdPrefix = useId();

	const {activeItem, panelsIds} = useSelectorCallback(
		(state) => selectPanels(activeItemId, activeItemType, state),
		[activeItemId, activeItemType],
		deepEqual
	);

	const panels = useMemo(
		() =>
			Object.entries(panelsIds)
				.filter(([, show]) => show)
				.map(([key]) => ({...PANELS[key], panelId: key}))
				.sort((panelA, panelB) => panelB.priority - panelA.priority),
		[panelsIds]
	);

	useEffect(() => {
		setActivePanelId((panelId) => {
			if (panels.find((panel) => panel.panelId === panelId)) {
				return panelId;
			}

			return panels[0]?.panelId ?? '';
		});
	}, [panels]);

	if (!activeItem || !panels.length) {
		return (
			<PageStructureSidebarSection resizable size={0.6}>
				<p className="bg-light m-3 p-5 rounded small text-center text-secondary">
					{Liferay.Language.get(
						'select-an-element-of-the-page-to-show-this-panel'
					)}
				</p>
			</PageStructureSidebarSection>
		);
	}

	return (
		<PageStructureSidebarSection resizable size={1.5}>
			<div className="page-editor__page-structure__item-configuration">
				<ClayTabs className="pt-2 px-3" modern>
					{panels.map((panel) => (
						<ClayTabs.Item
							active={panel.panelId === activePanelId}
							innerProps={{
								'aria-controls': `${panelIdPrefix}-${panel.panelId}`,
								id: `${tabIdPrefix}-${panel.panelId}`,
							}}
							key={panel.panelId}
							onClick={() => setActivePanelId(panel.panelId)}
						>
							<span
								className="c-inner page-editor__page-structure__item-configuration-tab"
								tabIndex="-1"
							>
								{panel.label}
							</span>
						</ClayTabs.Item>
					))}
				</ClayTabs>

				<ClayTabs.Content
					activeIndex={panels.findIndex(
						(panel) => panel.panelId === activePanelId
					)}
				>
					{panels.map((panel) => (
						<ClayTabs.TabPane
							aria-labelledby={`${tabIdPrefix}-${panel.panelId}`}
							className="p-3"
							id={`${panelIdPrefix}-${panel.panelId}`}
							key={panel.panelId}
						>
							{panel.panelId === activePanelId && (
								<ItemConfigurationComponent
									Component={panel.component}
									item={activeItem}
								/>
							)}
						</ClayTabs.TabPane>
					))}
				</ClayTabs.Content>
			</div>
		</PageStructureSidebarSection>
	);
}

class ItemConfigurationComponent extends React.Component {
	static getDerivedStateFromError(error) {
		return {error};
	}

	constructor(props) {
		super(props);

		this.state = {
			error: null,
		};
	}

	render() {
		const {Component, item} = this.props;

		return this.state.error ? (
			<ClayAlert
				displayType="danger"
				title={Liferay.Language.get('error')}
			>
				{Liferay.Language.get(
					'an-unexpected-error-occurred-while-rendering-this-item'
				)}
			</ClayAlert>
		) : (
			<Component item={item} key={item.itemId} />
		);
	}
}

ItemConfigurationComponent.propTypes = {
	Component: PropTypes.func.isRequired,
	item: PropTypes.object.isRequired,
};

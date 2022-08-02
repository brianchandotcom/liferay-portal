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
import {
	useSelector,
	useSelectorCallback,
} from '../../../../../app/contexts/StoreContext';
import selectCanViewItemConfiguration from '../../../../../app/selectors/selectCanViewItemConfiguration';
import {deepEqual} from '../../../../../app/utils/checkDeepEqual';
import {useId} from '../../../../../app/utils/useId';
import {PANELS, selectPanels} from '../selectors/selectPanels';
import PageStructureSidebarSection from './PageStructureSidebarSection';

export default function ItemConfiguration() {
	const collectionContext = useCollectionActiveItemContext();

	const canViewItemConfiguration = useSelector(
		selectCanViewItemConfiguration
	);

	const [activePanel, setActivePanel] = useState({});

	return canViewItemConfiguration ? (
		<CollectionItemContext.Provider value={collectionContext}>
			<ItemConfigurationContent
				activePanel={activePanel}
				setActivePanel={setActivePanel}
			/>
		</CollectionItemContext.Provider>
	) : null;
}

function ItemConfigurationContent({activePanel, setActivePanel}) {
	const activeItemId = useActiveItemId();
	const activeItemType = useActiveItemType();

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
		if (
			!panels.length ||
			panels.some((panel) => panel.panelId === activePanel.id)
		) {
			return;
		}

		let nextActivePanelId = activePanel.id;
		let nextActivePanelType = activePanel.type;

		const sameTypePanel = panels.find(
			(panel) => panel.type === activePanel.type
		);

		if (sameTypePanel) {
			nextActivePanelId = sameTypePanel.panelId;
		}
		else {
			nextActivePanelId = panels[0]?.panelId;
			nextActivePanelType = panels[0]?.type;
		}

		setActivePanel({id: nextActivePanelId, type: nextActivePanelType});
	}, [panels, activePanel, setActivePanel]);

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
			<div className="page-editor__page-structure__item-configuration w-100">
				<ClayTabs className="flex-nowrap pt-2 px-3">
					{panels.map((panel) => (
						<ClayTabs.Item
							active={panel.panelId === activePanel.id}
							innerProps={{
								'aria-controls': `${panelIdPrefix}-${panel.panelId}`,
								'id': `${tabIdPrefix}-${panel.panelId}`,
							}}
							key={panel.panelId}
							onClick={() => {
								setActivePanel({
									id: panel.panelId,
									type: panel.type || null,
								});
							}}
						>
							<span
								className="c-inner page-editor__page-structure__item-configuration-tab text-truncate"
								data-tooltip-align="top"
								tabIndex="-1"
								title={panel.label}
							>
								{panel.label}
							</span>
						</ClayTabs.Item>
					))}
				</ClayTabs>

				<ClayTabs.Content
					activeIndex={panels.findIndex(
						(panel) => panel.panelId === activePanel.id
					)}
				>
					{panels.map((panel) => (
						<ClayTabs.TabPane
							aria-labelledby={`${tabIdPrefix}-${panel.panelId}`}
							className="pb-3 pt-4 px-3"
							id={`${panelIdPrefix}-${panel.panelId}`}
							key={panel.panelId}
						>
							{panel.panelId === activePanel.id && (
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

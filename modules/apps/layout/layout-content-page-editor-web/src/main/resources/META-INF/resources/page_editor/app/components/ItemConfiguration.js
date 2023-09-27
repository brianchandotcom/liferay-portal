/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayTabs from '@clayui/tabs';
import classNames from 'classnames';
import {useId} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useState} from 'react';

import SidebarPanelHeader from '../../common/components/SidebarPanelHeader';
import {
	PANELS,
	selectPanels,
} from '../../plugins/browser/components/page_structure/selectors/selectPanels';
import {ITEM_TYPES} from '../config/constants/itemTypes';
import {useCollectionActiveItemContext} from '../contexts/CollectionActiveItemContext';
import {CollectionItemContext} from '../contexts/CollectionItemContext';
import {useSelectItem} from '../contexts/ControlsContext';
import {useSelector, useSelectorCallback} from '../contexts/StoreContext';
import selectCanUpdateItemConfiguration from '../selectors/selectCanUpdateItemConfiguration';
import selectCanViewItemConfiguration from '../selectors/selectCanViewItemConfiguration';
import {deepEqual} from '../utils/checkDeepEqual';

export default function ItemConfiguration({activeItemId, activeItemType}) {
	const collectionContext = useCollectionActiveItemContext();

	const canViewItemConfiguration = useSelector(
		selectCanViewItemConfiguration
	);

	const [activePanel, setActivePanel] = useState({});

	return canViewItemConfiguration ? (
		<CollectionItemContext.Provider value={collectionContext}>
			<ItemConfigurationContent
				activeItemId={activeItemId}
				activeItemType={activeItemType}
				activePanel={activePanel}
				setActivePanel={setActivePanel}
			/>
		</CollectionItemContext.Provider>
	) : null;
}

function ItemConfigurationContent({
	activeItemId,
	activeItemType,
	activePanel,
	setActivePanel,
}) {
	const tabIdPrefix = useId();
	const panelIdPrefix = useId();
	const selectItem = useSelectItem();

	const {activeItem, panelsIds} = useSelectorCallback(
		(state) => selectPanels(activeItemId, activeItemType, state),
		[activeItemId, activeItemType],
		deepEqual
	);

	const canUpdateItemConfiguration = useSelector(
		selectCanUpdateItemConfiguration
	);

	const [previousPanel, setPreviousPanel] = useState({});

	const panels = useMemo(
		() =>
			Object.entries(panelsIds)
				.filter(([, show]) => show)
				.map(([key]) => ({...PANELS[key], panelId: key}))
				.sort((panelA, panelB) => panelB.priority - panelA.priority),
		[panelsIds]
	);

	useEffect(() => {

		// Keep current panel if it's available

		if (
			!panels.length ||
			panels.some((panel) => panel.panelId === activePanel.id)
		) {
			return;
		}

		// Restore previous panel if it's available

		if (panels.some((panel) => panel.panelId === previousPanel.id)) {
			setActivePanel(previousPanel);

			return;
		}

		// Look for a panel of the same type than the current one

		let nextActivePanelId = activePanel.id;
		let nextActivePanelType = activePanel.type;

		const sameTypePanel = panels.find(
			(panel) => panel.type === activePanel.type
		);

		if (sameTypePanel) {
			nextActivePanelId = sameTypePanel.panelId;
		}

		// Select the first of available panels

		else {
			nextActivePanelId = panels[0]?.panelId;
			nextActivePanelType = panels[0]?.type;
		}

		setPreviousPanel(activePanel);
		setActivePanel({id: nextActivePanelId, type: nextActivePanelType});
	}, [panels, activePanel, setActivePanel, previousPanel]);

	return (
		<div className="page-editor__page-structure__item-configuration">
			{activeItemType === ITEM_TYPES.editable && (
				<SidebarPanelHeader
					iconLeft={
						canUpdateItemConfiguration && (
							<ClayButton
								aria-label={Liferay.Language.get(
									'back-to-parent-configuration'
								)}
								borderless
								className="mb-0 mr-3 p-0"
								displayType="secondary"
								onClick={() => selectItem(activeItem.parentId)}
								size="sm"
								title={Liferay.Language.get(
									'back-to-parent-configuration'
								)}
							>
								<ClayIcon symbol="angle-left" />
							</ClayButton>
						)
					}
					showCloseButton={false}
				>
					{activeItem.editableId}
				</SidebarPanelHeader>
			)}

			{!panels.length ? (
				<ClayAlert
					className="m-3"
					displayType="info"
					title={Liferay.Language.get('info')}
				>
					{Liferay.Language.get(
						'this-page-element-does-not-have-any-available-configurations'
					)}
				</ClayAlert>
			) : (
				<>
					<ClayTabs
						activation="automatic"
						active={panels.findIndex(
							(panel) => panel.panelId === activePanel.id
						)}
						className={classNames(
							'flex-nowrap flex-shrink-0 px-3',
							{
								'pt-2': activeItemType !== ITEM_TYPES.editable,
							}
						)}
						onActiveChange={(activeIndex) => {
							const panel = panels[activeIndex];

							setActivePanel({
								id: panel.panelId,
								type: panel.type || null,
							});
						}}
					>
						{panels.map((panel) => (
							<ClayTabs.Item
								innerProps={{
									'aria-controls': `${panelIdPrefix}-${panel.panelId}`,
									'id': `${tabIdPrefix}-${panel.panelId}`,
								}}
								key={panel.panelId}
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
				</>
			)}
		</div>
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

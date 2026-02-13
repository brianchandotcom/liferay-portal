/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SidePanel} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import {ClayVerticalNav} from '@clayui/nav';
import ClaySticker from '@clayui/sticker';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import SideNavigationSiteSelector from './SideNavigationSiteSelector';

interface SideNavigationItem {
	href?: string;
	id: string;
	items?: Array<SideNavigationItem>;
	label: string;
	leadingIcon?: string;
}

interface Props {
	categoryImageUrl: string;
	expandedKeys: Array<React.Key>;
	expandedKeysSessionKey: string;
	items: Array<SideNavigationItem>;
	label: string;
	portletId: string;
	siteAdministrationItemSelectedEventName: string;
	siteAdministrationItemSelectorUrl: string;
	visible: boolean;
	visibleSessionKey: string;
}

function SideNavigation({
	categoryImageUrl,
	expandedKeys: initialExpandedKeys,
	expandedKeysSessionKey,
	items,
	label,
	portletId,
	siteAdministrationItemSelectedEventName,
	siteAdministrationItemSelectorUrl,
	visible: initialVisible,
	visibleSessionKey,
}: Props) {
	const containerRef = useRef<HTMLElement | null>(
		document.getElementById(
			'com_liferay_application_list_taglib_side_navigation_container'
		)
	);

	const [visible, setVisible] = useState(initialVisible);
	const [expandedKeys, setExpandedKeys] = useState<Set<React.Key>>(
		() => new Set(initialExpandedKeys)
	);

	const updateExpandedKeys = useCallback(
		async (expandedKeys: Set<React.Key>) => {
			await Liferay.Util.Session.set(
				expandedKeysSessionKey,
				Array.from(expandedKeys).join(',')
			);

			setExpandedKeys(expandedKeys);
		},
		[expandedKeysSessionKey]
	);

	const updateVisible = useCallback(
		async (visible: boolean) => {
			await Liferay.Util.Session.set(
				visibleSessionKey,
				visible ? 'visible' : 'hidden'
			);

			setVisible(visible);

			Liferay.fire('sideNavigationStateChanged', {visible});
		},
		[visibleSessionKey]
	);

	useEffect(
		function setupVisibilityRequestHandler() {
			async function handleStateRequest({visible}: {visible: boolean}) {
				await updateVisible(visible);
			}

			Liferay.on('sideNavigationStateRequested', handleStateRequest);

			return () =>
				Liferay.detach(
					'sideNavigationStateRequested',
					handleStateRequest
				);
		},
		[updateVisible]
	);

	return (
		<SidePanel
			as="section"
			containerRef={containerRef}
			data-qa-id="sideNavigation"
			defaultOpen={initialVisible}
			direction="left"
			id="com_liferay_application_list_taglib_side_navigation"
			onOpenChange={updateVisible}
			open={visible}
			panelWidth={320}
			position="fixed"
		>
			<SidePanel.Header
				data-qa-id="sideNavigationHeader"
				messages={{
					backAriaLabel: Liferay.Language.get('go-back'),
					closeAriaLabel: Liferay.Language.get('close-product-menu'),
				}}
			>
				<div className="align-items-center d-flex flex-row">
					<ClaySticker borderless displayType="outline">
						<img
							alt=""
							className="c-mx-1"
							data-qa-id="sideNavigationProductIcon"
							src={categoryImageUrl}
						/>
					</ClaySticker>

					<SidePanel.Title className="c-px-2 flex-grow-1">
						<span data-qa-id="sideNavigationLabel">{label}</span>
					</SidePanel.Title>

					<SideNavigationSiteSelector
						eventName={siteAdministrationItemSelectedEventName}
						url={siteAdministrationItemSelectorUrl}
					/>
				</div>
			</SidePanel.Header>

			<SidePanel.Body className="c-px-0">
				<ClayVerticalNav
					active={portletId}
					displayType="primary"
					expandedKeys={expandedKeys}
					itemAriaCurrent={true}
					items={items}
					onExpandedChange={updateExpandedKeys}
				>
					{(item) => {
						if (typeof item === 'string') {
							return <span>{item}</span>;
						}

						return (
							<ClayVerticalNav.Item
								href={item.href}
								items={item.items}
								key={item.id}
								textValue={item.label}
							>
								{item.leadingIcon && (
									<ClayIcon
										className="c-mr-2"
										key={item.leadingIcon}
										symbol={item.leadingIcon}
									/>
								)}

								{item.label}
							</ClayVerticalNav.Item>
						);
					}}
				</ClayVerticalNav>
			</SidePanel.Body>
		</SidePanel>
	);
}

export default SideNavigation;

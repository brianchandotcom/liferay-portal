/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SidePanel} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import {ClayVerticalNav} from '@clayui/nav';
import React, {useCallback, useEffect, useRef, useState} from 'react';

type VerticalNavItem = React.ComponentProps<typeof ClayVerticalNav>['items'][0];

interface ApplicationsMenuItem {
	href?: string;
	id: string;
	items?: Array<ApplicationsMenuItem>;
	label: string;
	leadingIcon?: string;
}

interface Props {
	expandedKeys: Array<React.Key>;
	expandedKeysSessionKey: string;
	items: Array<ApplicationsMenuItem>;
	label: string;
	portletId: string;
	visible: boolean;
	visibleSessionKey: string;
}

function ApplicationsMenu({
	expandedKeys: initialExpandedKeys,
	expandedKeysSessionKey,
	items,
	label,
	portletId,
	visible: initialVisible,
	visibleSessionKey,
}: Props) {
	const containerRef = useRef<HTMLElement | null>(
		document.getElementById(
			'com_liferay_application_list_taglib_applications_menu_container'
		)
	);

	const [visible, setVisible] = useState(initialVisible);
	const [expandedKeys, setExpandedKeys] = useState<Set<React.Key>>(
		() => new Set(initialExpandedKeys)
	);

	function buildNavigationItem(item: ApplicationsMenuItem): VerticalNavItem {
		return {
			...item,
			...(item.items && {
				items: item.items.map((child) => buildNavigationItem(child)),
			}),
			...(item.leadingIcon && {
				label: (
					<div className="align-items-center d-flex flex-row">
						<ClayIcon symbol={item.leadingIcon} />

						<span className="c-px-2">{item.label}</span>
					</div>
				),
			}),
			textValue: item.label,
		};
	}

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

			Liferay.fire('applicationsMenuStateChanged', {visible});
		},
		[visibleSessionKey]
	);

	useEffect(() => {
		async function handleStateRequest({visible}: {visible: boolean}) {
			await updateVisible(visible);
		}

		Liferay.on('applicationsMenuStateRequested', handleStateRequest);

		return () =>
			Liferay.detach(
				'applicationsMenuStateRequested',
				handleStateRequest
			);
	}, [updateVisible]);

	return (
		<SidePanel
			as="section"
			containerRef={containerRef}
			defaultOpen={initialVisible}
			direction="left"
			id="com_liferay_application_list_taglib_applications_menu"
			onOpenChange={updateVisible}
			open={visible}
			panelWidth={320}
			position="fixed"
		>
			<SidePanel.Header>
				<SidePanel.Title>
					<ClayIcon symbol="grid" />

					<span className="c-px-2">{label}</span>
				</SidePanel.Title>
			</SidePanel.Header>

			<SidePanel.Body>
				<ClayVerticalNav
					active={portletId}
					expandedKeys={expandedKeys}
					itemAriaCurrent={true}
					items={items.map(buildNavigationItem)}
					onExpandedChange={updateExpandedKeys}
				/>
			</SidePanel.Body>
		</SidePanel>
	);
}

export default ApplicationsMenu;

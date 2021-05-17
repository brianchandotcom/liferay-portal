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

import {useEventListener} from '@liferay/frontend-js-react-web';
import React, {useContext, useEffect, useState} from 'react';

import LayoutReports from './components/LayoutReports';
import {
	StoreContextProvider,
	StoreDispatchContext,
	StoreStateContext,
} from './context/StoreContext';

import '../css/main.scss';

import {ClayButtonWithIcon} from '@clayui/button';

import {
	ConstantsContext,
	ConstantsContextProvider,
} from './context/ConstantsContext';
import loadIssues from './utils/loadIssues';

export default function (props) {
	const {portletNamespace} = props;

	const layoutReportsPanelToggle = document.getElementById(
		`${portletNamespace}layoutReportsPanelToggleId`
	);

	useEffect(() => {
		const sidenavInstance = Liferay.SideNavigation.instance(
			layoutReportsPanelToggle
		);

		sidenavInstance.on('open.lexicon.sidenav', () => {
			Liferay.Util.Session.set(
				'com.liferay.layout.reports.web_layoutReportsPanelState',
				'open'
			);
		});

		sidenavInstance.on('closed.lexicon.sidenav', () => {
			Liferay.Util.Session.set(
				'com.liferay.layout.reports.web_layoutReportsPanelState',
				'closed'
			);
		});

		Liferay.once('screenLoad', () => {
			Liferay.SideNavigation.destroy(layoutReportsPanelToggle);
		});
	}, [layoutReportsPanelToggle, portletNamespace]);

	const [eventTriggered, setEventTriggered] = useState(false);

	useEventListener(
		'mouseenter',
		() => setEventTriggered(true),
		{once: true},
		layoutReportsPanelToggle
	);

	useEventListener(
		'focus',
		() => setEventTriggered(true),
		{once: true},
		layoutReportsPanelToggle
	);

	return (
		<ConstantsContextProvider constants={props}>
			<StoreContextProvider>
				<SidebarHeader />
				<SidebarBody>
					<LayoutReports eventTriggered={eventTriggered} />
				</SidebarBody>
			</StoreContextProvider>
		</ConstantsContextProvider>
	);
}

const SidebarHeader = () => {
	const {data, languageId, loading} = useContext(StoreStateContext);
	const {portletNamespace} = useContext(ConstantsContext);
	const dispatch = useContext(StoreDispatchContext);

	return (
		<div className="d-flex justify-content-between sidebar-header">
			<span>{Liferay.Language.get('page-audit')}</span>
			<div>
				{data?.validConnection && (
					<ClayButtonWithIcon
						className="sidenav-relaunch"
						disabled={loading}
						displayType="unstyled"
						onClick={() => {
							const url = data.canonicalURLs.find(
								(canonicalURL) =>
									canonicalURL.languageId ===
									(languageId || data.defaultLanguageId)
							);

							loadIssues({
								dispatch,
								portletNamespace,
								url,
							});
						}}
						symbol="reload"
						title={Liferay.Language.get('relaunch')}
					/>
				)}

				<ClayButtonWithIcon
					className="sidenav-close"
					displayType="unstyled"
					symbol="times"
					title={Liferay.Language.get('close')}
				/>
			</div>
		</div>
	);
};

const SidebarBody = ({children}) => (
	<div className="sidebar-body">{children}</div>
);

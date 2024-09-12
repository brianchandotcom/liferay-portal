/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {setSessionValue} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import PageAudit from './components/PageAudit';
import {StoreContextProvider} from './context/StoreContext';

import '../css/main.scss';
import {ConstantsContextProvider} from './context/ConstantsContext';

export function App(props) {
	const {isPanelStateOpen} = props;
	const [panelIsOpen, setPanelIsOpen] = useState(isPanelStateOpen);

	const layoutReportsPanelToggle = document.getElementById(
		`layoutReportsPanelToggleId`
	);

	const layoutReportsPanelId =
		document.getElementById(`layoutReportsPanelId`);

	const sidenavInstance = Liferay.SideNavigation.instance(
		layoutReportsPanelToggle
	);

	useEffect(() => {
		sidenavInstance.on('open.lexicon.sidenav', () => {
			setSessionValue(
				'com.liferay.layout.reports.web_layoutReportsPanelState',
				'open'
			);

			setPanelIsOpen(true);
		});

		sidenavInstance.on('closed.lexicon.sidenav', () => {
			setSessionValue(
				'com.liferay.layout.reports.web_layoutReportsPanelState',
				'closed'
			);

			setPanelIsOpen(false);
		});

		Liferay.once('screenLoad', () => {
			Liferay.SideNavigation.destroy(layoutReportsPanelToggle);
		});
	}, [layoutReportsPanelToggle, layoutReportsPanelId, sidenavInstance]);

	useEffect(() => {
		if (panelIsOpen) {
			Liferay.fire('PageAuditMenu:openPageAuditPanel');
		}
		else {
			Liferay.fire('PageAuditMenu:closePageAuditPanel');
		}

		layoutReportsPanelToggle.setAttribute('aria-pressed', panelIsOpen);
	}, [panelIsOpen, layoutReportsPanelToggle]);

	return (
		<ConstantsContextProvider constants={props}>
			<StoreContextProvider>
				<PageAudit panelIsOpen={panelIsOpen} />
			</StoreContextProvider>
		</ConstantsContextProvider>
	);
}

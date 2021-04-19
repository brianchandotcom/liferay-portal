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
import React, {useEffect, useState} from 'react';

import LayoutReports from './components/LayoutReports';

export default function ({
	isPanelStateOpen,
	layoutReportsDataURL,
	portletNamespace,
}) {
	const layoutReportsPanelToggle = document.getElementById(
		`${portletNamespace}layoutReportsPanelToggleId`
	);

	useEffect(() => {
		const sidenavInstance = Liferay.SideNavigation.initialize(
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
		<LayoutReports
			eventTriggered={eventTriggered}
			isPanelStateOpen={isPanelStateOpen}
			layoutReportsDataURL={layoutReportsDataURL}
		/>
	);
}

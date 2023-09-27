/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEventListener} from '@liferay/frontend-js-react-web';
import {setSessionValue} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import AnalyticsReports from './components/AnalyticsReports';

import '../css/main.scss';

export default function AnalyticsReportsApp({context, portletNamespace}) {
	const {analyticsReportsDataURL, isPanelStateOpen} = context;
	const [
		hoverOrFocusEventTriggered,
		setHoverOrFocusEventTriggered,
	] = useState(false);

	const analyticsReportsPanelToggle = document.getElementById(
		`${portletNamespace}analyticsReportsPanelToggleId`
	);

	useEffect(() => {
		if (analyticsReportsPanelToggle) {
			const sidenavInstance = Liferay.SideNavigation.instance(
				analyticsReportsPanelToggle
			);

			sidenavInstance.on('open.lexicon.sidenav', () => {
				setSessionValue(
					'com.liferay.analytics.reports.web_panelState',
					'open'
				);

				const analyticsReportsPanel = document.getElementById(
					`${portletNamespace}analyticsReportsPanelId`
				);

				analyticsReportsPanel.focus();
			});

			sidenavInstance.on('closed.lexicon.sidenav', () => {
				setSessionValue(
					'com.liferay.analytics.reports.web_panelState',
					'closed'
				);
			});

			Liferay.once('screenLoad', () => {
				Liferay.SideNavigation.destroy(analyticsReportsPanelToggle);
			});
		}
	}, [analyticsReportsPanelToggle, portletNamespace]);

	useEventListener(
		'mouseenter',
		() => setHoverOrFocusEventTriggered(true),
		{once: true},
		analyticsReportsPanelToggle
	);

	useEventListener(
		'focus',
		() => setHoverOrFocusEventTriggered(true),
		{once: true},
		analyticsReportsPanelToggle
	);

	const visualizingAPage = analyticsReportsPanelToggle;

	return (
		<div id={`${portletNamespace}-analytics-reports-root`}>
			<AnalyticsReports
				analyticsReportsDataURL={analyticsReportsDataURL}
				hoverOrFocusEventTriggered={hoverOrFocusEventTriggered}
				isPanelStateOpen={visualizingAPage ? isPanelStateOpen : true}
			/>
		</div>
	);
}

AnalyticsReportsApp.propTypes = {
	context: PropTypes.shape({
		analyticsReportsDataURL: PropTypes.string.isRequired,
		isPanelStateOpen: PropTypes.bool.isRequired,
	}).isRequired,
	portletNamespace: PropTypes.string.isRequired,
};

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEventListener} from '@liferay/frontend-js-react-web';
import {setSessionValue} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import SegmentsExperimentsMain from './components/SegmentsExperimentsMain.es';

import '../css/main.scss';

const SEGMENTS_EXPERIMENT_CLOSED_PANEL_VALUE = 'closed';
const SEGMENTS_EXPERIMENT_OPEN_PANEL_VALUE = 'open';
const SEGMENTS_EXPERIMENT_PANEL_ID =
	'com.liferay.segments.experiment.web_panelState';

export default function SegmentsExperimentApp({context}) {
	const [eventTriggered, setEventTriggered] = useState(false);

	const {isPanelStateOpen, namespace, segmentExperimentDataURL} = context;

	const segmentsExperimentPanelToggle = document.getElementById(
		`${namespace}segmentsExperimentPanelToggleId`
	);

	useEffect(() => {
		if (segmentsExperimentPanelToggle) {
			const sidenavInstance = Liferay.SideNavigation.instance(
				segmentsExperimentPanelToggle
			);

			sidenavInstance.on('closed.lexicon.sidenav', () => {
				setSessionValue(
					SEGMENTS_EXPERIMENT_PANEL_ID,
					SEGMENTS_EXPERIMENT_CLOSED_PANEL_VALUE
				);
			});

			sidenavInstance.on('open.lexicon.sidenav', () => {
				setSessionValue(
					SEGMENTS_EXPERIMENT_PANEL_ID,
					SEGMENTS_EXPERIMENT_OPEN_PANEL_VALUE
				);

				const segmentsExperimentPanel = document.getElementById(
					`${namespace}segmentsExperimentPanelId`
				);

				segmentsExperimentPanel.focus();
			});

			Liferay.once('screenLoad', () => {
				Liferay.SideNavigation.destroy(segmentsExperimentPanelToggle);
			});
		}
	}, [namespace, segmentsExperimentPanelToggle]);

	useEventListener(
		'mouseenter',
		() => setEventTriggered(true),
		{once: true},
		segmentsExperimentPanelToggle
	);

	useEventListener(
		'focus',
		() => setEventTriggered(true),
		{once: true},
		segmentsExperimentPanelToggle
	);

	return (
		<div id={`${namespace}-segments-experiment-root`}>
			<SegmentsExperimentsMain
				eventTriggered={eventTriggered}
				fetchDataURL={segmentExperimentDataURL}
				isPanelStateOpen={isPanelStateOpen}
			/>
		</div>
	);
}

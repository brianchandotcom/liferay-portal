/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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

			sidenavInstance.on('closed.lexicon.sidenav', () => {
				setSessionValue(
					SEGMENTS_EXPERIMENT_PANEL_ID,
					SEGMENTS_EXPERIMENT_CLOSED_PANEL_VALUE
				);
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

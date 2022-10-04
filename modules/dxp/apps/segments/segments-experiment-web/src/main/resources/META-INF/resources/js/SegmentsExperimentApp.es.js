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

import {getSessionValue, setSessionValue} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import SegmentsExperimentsMain from './components/SegmentsExperimentsMain.es';

import '../css/main.scss';

const SEGMENTS_EXPERIMENT_CLOSED_PANEL_VALUE = 'closed';
const SEGMENTS_EXPERIMENT_OPEN_PANEL_VALUE = 'open';
const SEGMENTS_EXPERIMENT_PANEL_ID =
	'com.liferay.segments.experiment.web_panelState';

const useInitialPanelState = () => {
	const [panelState, setPanelState] = useState(
		SEGMENTS_EXPERIMENT_CLOSED_PANEL_VALUE
	);

	useEffect(() => {
		getSessionValue(SEGMENTS_EXPERIMENT_PANEL_ID).then((value) => {
			setPanelState(value);
		});
	}, []);

	return [panelState, setPanelState];
};

export default function SegmentsExperimentApp({context, props}) {
	const [panelState, setPanelState] = useInitialPanelState();

	const {namespace} = context;
	const {segmentExperimentDataURL} = props;

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
				setPanelState(SEGMENTS_EXPERIMENT_OPEN_PANEL_VALUE);
			});

			sidenavInstance.on('closed.lexicon.sidenav', () => {
				setSessionValue(
					SEGMENTS_EXPERIMENT_PANEL_ID,
					SEGMENTS_EXPERIMENT_CLOSED_PANEL_VALUE
				);
				setPanelState(SEGMENTS_EXPERIMENT_CLOSED_PANEL_VALUE);
			});

			Liferay.once('screenLoad', () => {
				Liferay.SideNavigation.destroy(segmentsExperimentPanelToggle);
			});
		}
	}, [segmentsExperimentPanelToggle, setPanelState]);

	return (
		<div id={`${namespace}-segments-experiment-root`}>
			<SegmentsExperimentsMain
				fetchDataURL={segmentExperimentDataURL}
				isPanelStateOpen={
					panelState === SEGMENTS_EXPERIMENT_OPEN_PANEL_VALUE
				}
			/>
		</div>
	);
}

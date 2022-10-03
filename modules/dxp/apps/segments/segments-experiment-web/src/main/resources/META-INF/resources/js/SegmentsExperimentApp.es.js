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

import {setSessionValue} from 'frontend-js-web';
import React, {useEffect} from 'react';

import SegmentsExperimentsMain from './components/SegmentsExperimentsMain.es';

import '../css/main.scss';

export default function ({context, props}) {
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
					'com.liferay.segments.experiment.web_panelState',
					'open'
				);
			});

			sidenavInstance.on('closed.lexicon.sidenav', () => {
				setSessionValue(
					'com.liferay.segments.experiment.web_panelState',
					'closed'
				);
			});

			Liferay.once('screenLoad', () => {
				Liferay.SideNavigation.destroy(segmentsExperimentPanelToggle);
			});
		}
	}, [segmentsExperimentPanelToggle]);

	return (
		<div id={`${namespace}-segments-experiment-root`}>
			<SegmentsExperimentsMain fetchDataURL={segmentExperimentDataURL} />
		</div>
	);
}

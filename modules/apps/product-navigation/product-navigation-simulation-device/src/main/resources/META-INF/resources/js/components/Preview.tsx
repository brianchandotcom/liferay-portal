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

import ClayAlert from '@clayui/alert';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {SIZES, Size} from '../constants/sizes';

interface IPreviewProps {
	activeSize: Size;
	previewRef: React.RefObject<HTMLDivElement>;
}

const SEGMENT_SIMULATION_EVENT = 'SegmentSimulation:changeSegment';

export default function Preview({activeSize, previewRef}: IPreviewProps) {
	const [visible, setVisible] = useState<boolean>(true);
	const [segmentMessage, setSegmentMessage] = useState<string | null>(null);

	useEffect(() => {
		const wrapper = document.getElementById('wrapper');

		const onCloseSimulationPanel = () => {
			setVisible(false);

			if (wrapper) {
				wrapper.removeAttribute('inert');
			}
		};
		const onOpenSimulationPanel = () => {
			setVisible(true);

			if (wrapper) {
				wrapper.setAttribute('inert', '');
			}
		};

		const handleSegmentChange = ({message}: {message: string}) => {
			setSegmentMessage(message);
		};

		Liferay.on(
			'SimulationMenu:closeSimulationPanel',
			onCloseSimulationPanel
		);
		Liferay.on('SimulationMenu:openSimulationPanel', onOpenSimulationPanel);
		Liferay.on(SEGMENT_SIMULATION_EVENT, handleSegmentChange);

		return () => {
			Liferay.detach(
				'SimulationMenu:closeSimulationPanel',
				onCloseSimulationPanel
			);
			Liferay.detach(
				'SimulationMenu:openSimulationPanel',
				onOpenSimulationPanel
			);
			Liferay.detach(SEGMENT_SIMULATION_EVENT);
		};
	}, []);

	if (!visible || activeSize.id === SIZES.autosize.id) {
		return null;
	}

	return (
		<div className="d-flex flex-column simulation-preview">
			{Liferay.FeatureFlags['LPS-186558'] && segmentMessage && (
				<ClayAlert
					className="c-m-3"
					displayType="info"
					title={`${Liferay.Language.get('info')}:`}
				>
					{segmentMessage}
				</ClayAlert>
			)}

			<div
				className={classNames(
					'device position-absolute align-self-center',
					activeSize.cssClass,
					{
						'device--with-alert':
							Liferay.FeatureFlags['LPS-186558'] &&
							segmentMessage,
						'resizable': activeSize.id === SIZES.custom.id,
					}
				)}
				ref={previewRef}
				style={activeSize.screenSize}
			>
				<iframe
					className="border-0 h-100 w-100"
					src={createIframeURL()}
				/>
			</div>
		</div>
	);
}

Preview.propTypes = {
	activeSize: PropTypes.object.isRequired,
	previewRef: PropTypes.object.isRequired,
};

function createIframeURL() {
	const url = new URL(location.href);
	const searchParams = new URLSearchParams(url.search);

	if (searchParams.has('segmentsExperienceId')) {
		searchParams.delete('segmentsExperienceId');
	}

	searchParams.append('p_l_mode', 'preview');

	return `${url.origin}${url.pathname}?${searchParams.toString()}`;
}

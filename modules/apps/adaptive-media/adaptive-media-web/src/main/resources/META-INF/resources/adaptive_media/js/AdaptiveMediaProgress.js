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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayProgressBar from '@clayui/progress-bar';
import {useIsMounted, useTimeout} from '@liferay/frontend-js-react-web';
import {fetch, sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useState} from 'react';

import {disableEntryIcon, enableEntryIcon} from './utils/entryIcons';

const AdaptiveMediaProgress = ({
	adaptedImages,
	adaptiveMediaProgressComponentId,
	autoStartProgress = false,
	disabled = false,
	intervalSpeed = 1000,
	namespace,
	percentageUrl,
	tooltip,
	totalImages,
	uuid,
}) => {
	const delay = useTimeout();
	const isMounted = useIsMounted();

	const [showLoadingIndicator, setShowLoadingIndicator] = useState(
		autoStartProgress
	);
	const [percentage, setPercentage] = useState(
		Math.ceil((adaptedImages / totalImages) * 100) || 0
	);
	const [progressBarTooltip, setProgressBarTooltip] = useState(
		adaptedImages + '/' + totalImages
	);

	const [imagesFailed, setImagesFailed] = useState(0);

	const updateProgress = useCallback(() => {
		fetch(percentageUrl)
			.then((res) => res.json())
			.then(({adaptedImages, errors, totalImages}) => {
				if (isMounted()) {
					setImagesFailed(errors);

					setPercentage(
						Math.ceil((adaptedImages / totalImages) * 100) || 0
					);

					setProgressBarTooltip(
						tooltip ? tooltip : adaptedImages + '/' + totalImages
					);
				}

				if (adaptedImages + errors === totalImages) {
					if (isMounted()) {
						setShowLoadingIndicator(false);
					}

					enableEntryIcon(
						document.getElementById(
							`${namespace}icon-disable-${uuid}`
						)
					);
				}
				else {
					delay(updateProgress, intervalSpeed);
				}
			});
	}, [
		delay,
		intervalSpeed,
		isMounted,
		namespace,
		percentageUrl,
		tooltip,
		uuid,
	]);

	const startProgress = useCallback(
		(backgroundTaskUrl) => {
			fetch(backgroundTaskUrl);

			if (isMounted()) {
				setShowLoadingIndicator(true);
			}

			disableEntryIcon(
				document.getElementById(
					`${namespace}icon-adapt-remaining${uuid}`
				)
			);

			disableEntryIcon(
				document.getElementById(`${namespace}icon-disable-${uuid}`)
			);

			return delay(updateProgress, intervalSpeed);
		},
		[delay, intervalSpeed, isMounted, namespace, updateProgress, uuid]
	);

	const onClickRetry = () => {
		setImagesFailed(0);

		startProgress();
	};

	useEffect(() => {
		if (autoStartProgress) {
			updateProgress();
		}
	}, [autoStartProgress, updateProgress]);

	if (!Liferay.component(adaptiveMediaProgressComponentId)) {
		Liferay.component(
			adaptiveMediaProgressComponentId,
			{
				startProgress,
			},
			{
				destroyOnNavigate: true,
			}
		);
	}

	return imagesFailed > 0 ? (
		<div className="progress-error-container">
			<span className="text-danger">
				<ClayIcon symbol="exclamation-full" />

				<span>
					<strong>{Liferay.Language.get('error')}: </strong>

					{imagesFailed === 1
						? Liferay.Language.get('1-image-failed-process')
						: sub(
								Liferay.Language.get('x-images-failed-process'),
								imagesFailed
						  )}
				</span>
			</span>

			<ClayButton
				borderless
				className="text-danger"
				onClick={onClickRetry}
				small
			>
				{Liferay.Language.get('retry')}
			</ClayButton>
		</div>
	) : (
		<>
			<div
				className={`progress-container ${disabled ? 'disabled' : ''}`}
				data-percentage={percentage}
				data-title={progressBarTooltip}
			>
				<ClayProgressBar value={percentage} />
			</div>

			<span
				className={`${
					showLoadingIndicator ? '' : 'hide '
				}loading-animation loading-animation-sm`}
			></span>
		</>
	);
};

AdaptiveMediaProgress.propTypes = {
	adaptedImages: PropTypes.number,
	adaptiveMediaProgressComponentId: PropTypes.string,
	autoStartProgress: PropTypes.bool,
	disabled: PropTypes.bool,
	intervalSpeed: PropTypes.number,
	namespace: PropTypes.string,
	percentageUrl: PropTypes.string,
	progressBarTooltip: PropTypes.string,
	showLoadingIndicator: PropTypes.bool,
	tooltip: PropTypes.string,
	totalImages: PropTypes.number,
	uuid: PropTypes.string,
};

export default AdaptiveMediaProgress;

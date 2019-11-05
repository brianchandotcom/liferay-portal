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

import ClayProgressBar from '@clayui/progress-bar';
import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

/**
 * Handles the actions of the configuration entry's progressbar.
 *
 * @review
 */

const AdaptiveMediaProgress = ({
	adaptedImages,
	autoStartProgress = false,
	disabled = false,
	intervalSpeed = 1000,
	namespace,
	optimizeImagesURL,
	percentageUrl,
	tooltip,
	totalImages,
	uuid
}) => {
	const [showLoadingIndicator, setShowLoadingIndicator] = useState(false);
	const [percentage, setPercentage] = useState(0);
	const [progressBarTooltip, setProgressBarTooltip] = useState('');

	let adaptRemainingIcon = useRef();
	let disableIcon = useRef();
	const progressContainer = useRef();

	adaptRemainingIcon = document.getElementById(
		`${namespace}icon-adapt-remaining-${uuid}`
	);
	disableIcon = document.getElementById(`${namespace}icon-disable-${uuid}`);

	const disableEntryIcon = element => {
		if (!element) {
			return;
		}

		element.parentElement.classList.add('disabled');

		element.setAttribute('data-href', element.getAttribute('href'));
		element.setAttribute('data-onclick', element.getAttribute('onclick'));

		element.removeAttribute('href');
		element.removeAttribute('onclick');
	};

	const enableEntryIcon = element => {
		if (!element) {
			return;
		}

		element.parentElement.classList.remove('disabled');

		element.setAttribute('href', element.getAttribute('data-href'));
		element.setAttribute('onclick', element.getAttribute('data-onclick'));

		element.removeAttribute('data-href');
		element.removeAttribute('data-onclick');
	};

	const updateProgressBar = (adaptedImages, totalImages) => {
		setPercentage(Math.round((adaptedImages / totalImages) * 100) || 0);

		setProgressBarTooltip(
			tooltip ? tooltip : adaptedImages + '/' + totalImages
		);
	};

	let intervalId;

	const resetInterval = () => {
		if (intervalId) {
			clearInterval(intervalId);
		}
	};

	const onFinish = () => {
		enableEntryIcon(disableIcon.current);
	};

	const onProgressBarComplete = () => {
		setShowLoadingIndicator(false);

		resetInterval();

		onFinish();
	};

	const getAdaptedImagesPercentage = () => {
		fetch(percentageUrl)
			.then(res => res.json())
			.then(json => {
				updateProgressBar(json.adaptedImages, json.totalImages);

				if (progressContainer.current.dataset.percentage >= 100) {
					onProgressBarComplete();
				} else {
					clearInterval(intervalId);
				}
			})
			.catch(() => {
				clearInterval(intervalId);
			});
	};

	const onStart = () => {
		disableEntryIcon(adaptRemainingIcon.current);
		disableEntryIcon(disableIcon.current);
	};

	const startProgress = () => {
		const progressContainerElement = progressContainer.current;

		if (
			progressContainerElement.dataset.percentage >= 100 ||
			totalImages === 0 ||
			progressContainerElement.classList.contains('disabled')
		) {
			return;
		}
		if (optimizeImagesURL) {
			fetch(optimizeImagesURL);
		}

		resetInterval();

		intervalId = setInterval(getAdaptedImagesPercentage, intervalSpeed);

		setShowLoadingIndicator(true);

		onStart();
	};

	useEffect(() => {
		updateProgressBar(adaptedImages, totalImages);

		if (autoStartProgress) {
			startProgress();
		}

		if (adaptRemainingIcon) {
			adaptRemainingIcon.addEventListener('click', () => startProgress());
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<>
			<div
				className={`progress-container ${disabled ? 'disabled' : ''}`}
				data-percentage={percentage}
				data-title={progressBarTooltip}
				ref={progressContainer}
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
	autoStartProgress: PropTypes.boolean,
	disabled: PropTypes.boolean,
	intervalSpeed: PropTypes.number,
	namespace: PropTypes.string,
	optimizeImagesURL: PropTypes.string,
	percentage: PropTypes.number,
	percentageUrl: PropTypes.string,
	progressBarTooltip: PropTypes.string,
	showLoadingIndicator: PropTypes.boolean,
	tooltip: PropTypes.string,
	totalImages: PropTypes.number,
	uuid: PropTypes.string
};

export default function(props) {
	return <AdaptiveMediaProgress {...props} />;
}

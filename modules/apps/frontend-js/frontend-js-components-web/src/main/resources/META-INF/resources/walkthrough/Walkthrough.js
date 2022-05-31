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

import './Walkthrough.scss';

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {OverlayMask} from '@clayui/core';
import {ClayCheckbox} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayPopover from '@clayui/popover';
import {ReactPortal, usePrevious} from '@liferay/frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {
	forwardRef,
	useCallback,
	useEffect,
	useMemo,
	useRef,
	useState,
} from 'react';

import {doAlign} from './doAlign';
import {useObserveRect} from './useObserveRect';

const Hotspot = forwardRef(({onHotspotClick, trigger}, ref) => {
	const align = useCallback(() => {
		if (trigger && ref?.current) {
			doAlign({
				points: ['cc', 'tl'],
				sourceElement: ref.current,
				targetElement: trigger,
			});
		}
	}, [ref, trigger]);

	useEffect(() => {
		align();
	}, [align]);

	useObserveRect(align, trigger);

	return (
		<div
			className="lfr-walkthrough-hotspot"
			onClick={onHotspotClick}
			ref={ref}
		>
			<div className="lfr-walkthrough-hotspot-inner" />
		</div>
	);
});

const Walkthrough = ({closeOnClickOutside, closeable, skippable, steps}) => {
	const [currentStepIndex, setCurrentStepIndex] = useState(0);

	const memoizedTriggerReference = useMemo(() => {
		const currentNode = steps[currentStepIndex].nodeToHighlight;

		if (currentNode) {
			return document.querySelector(currentNode);
		}
	}, [steps, currentStepIndex]);

	const previousTrigger = usePrevious(memoizedTriggerReference);

	return (
		<>
			<WalkthroughStep
				closeOnClickOutside={closeOnClickOutside}
				closeable={closeable}
				onNext={() => {
					const maybeNextIndex = currentStepIndex + 1;

					if (steps[maybeNextIndex]) {
						setCurrentStepIndex(maybeNextIndex);
					}
				}}
				onPrevious={() => {
					const maybePreviousIndex = currentStepIndex - 1;

					if (steps[maybePreviousIndex]) {
						setCurrentStepIndex(maybePreviousIndex);
					}
				}}
				previousTrigger={previousTrigger}
				skippable={skippable}
				stepIndex={currentStepIndex}
				stepLength={steps.length}
				trigger={memoizedTriggerReference}
				{...steps[currentStepIndex]}
			/>
		</>
	);
};

/**
 * This map humanize tuples received from dom-align
 * library to be passed in a format that ClayPopover allows
 */
const ALIGNMENTS_MAP = {
	'bottom': ['tc', 'bc'],
	'bottom-left': ['tl', 'bl'],
	'bottom-right': ['tr', 'br'],
	'left': ['cr', 'cl'],
	'left-bottom': ['br', 'bl'],
	'left-top': ['tr', 'tl'],
	'right': ['cl', 'cr'],
	'right-bottom': ['bl', 'br'],
	'right-top': ['tl', 'tr'],
	'top': ['bc', 'tc'],
	'top-left': ['bl', 'tl'],
	'top-right': ['br', 'tr'],
};

Walkthrough.propTypes = {
	closeOnClickOutside: PropTypes.bool,
	closeable: PropTypes.bool,
	darkbg: PropTypes.bool,
	skippable: PropTypes.bool,
	steps: PropTypes.arrayOf(
		PropTypes.shape({
			content: PropTypes.string,
			nodeToHighlight: PropTypes.string.isRequired,
			positioning: PropTypes.oneOf(Object.keys(ALIGNMENTS_MAP)),
			title: PropTypes.string,
		})
	),
};

export default Walkthrough;

const OVERLAY_OFFSET_X = 15;
const OVERLAY_OFFSET_Y = -10;

/**
 * Since we can't set tuples as keys for literal dictionaries
 * and maps where are some errors with references like a.get(['bc','tc']) => undefined.
 * We are joining tuples to string to map there and use to lookup in some usages.
 */
const ALIGNMENTS_INVERSE_MAP = {
	bctc: 'top',
	blbr: 'right-bottom',
	bltl: 'top-left',
	brbl: 'left-bottom',
	brtr: 'top-right',
	clcr: 'right',
	crcl: 'left',
	tcbc: 'bottom',
	tlbl: 'bottom-left',
	tltr: 'right-top',
	trbr: 'bottom-right',
	trtl: 'left-top',
};

/**
 * This map matches with new positions when the positioning has been given by the user or the default positioning fails.
 *
 * For example, if we receive on ALIGNMENTS_INVERSE_MAP, a lookup for brtr,
 * it points to 'top-right'. Here, at the ALIGNMENTS_GUESS_MAP,
 * we should make corrections if the positioning cannot be achieved in this case, for brtr.
 *
 * A general rule was applied:
 * If the received INVERSE value is top or top something, we should place it to bottom, and vice-versa for bottom and bottom something.
 * If the received INVERSE value is right, it should place to left, and vice-versa.
 * We should remove guesses from the top since people will not place things on top that will collide at the beginning of the viewport.
 */
const ALIGNMENTS_GUESS_MAP = {
	...ALIGNMENTS_INVERSE_MAP,
	blbr: 'top',
	brbl: 'top',
	clcr: 'left',
	crcl: 'right',
	tcbc: 'top',
	tlbl: 'top',
	trbr: 'top',
};

const WalkthroughOverlay = ({popoverVisible, trigger}) => {
	const [overlayBounds, setOverlayBounds] = useState({
		height: 0,
		width: 0,
		x: 0,
		y: 0,
	});

	const updateOverlayBounds = useCallback(
		(overlayBounds) => {
			overlayBounds = overlayBounds
				? overlayBounds
				: trigger.getBoundingClientRect();

			setOverlayBounds({
				height: overlayBounds.height,
				width: overlayBounds.width,
				x: overlayBounds.x,
				y: overlayBounds.y,
			});
		},
		[trigger]
	);

	useEffect(() => {
		updateOverlayBounds();
	}, [updateOverlayBounds]);

	useObserveRect(updateOverlayBounds, trigger);

	return (
		<OverlayMask
			bounds={overlayBounds}
			onBoundsChange={setOverlayBounds}
			visible={popoverVisible}
		/>
	);
};

const WalkthroughStep = ({
	closeOnClickOutside,
	closeable,
	content,
	darkbg,
	onNext,
	onPrevious,
	positioning: defaultPositioning = 'right-top',
	previousTrigger,
	skippable,
	stepIndex,
	stepLength,
	title,
	trigger,
}) => {
	const popoverRef = useRef(null);

	const hotspotRef = useRef(null);

	const [popoverVisible, setPopoverVisible] = useState(false);

	const [currentAlignment, setCurrentAlignment] = useState(
		defaultPositioning
	);

	/**
	 * This useEffect was necessary because Walkthrough(Step) components
	 * are always mounted. So, currentAligment will not be updated when
	 * the component will be updated.
	 */
	useEffect(() => {
		setCurrentAlignment(defaultPositioning);
	}, [defaultPositioning]);

	const align = useCallback(() => {
		if (popoverVisible && popoverRef.current) {
			const points = ALIGNMENTS_MAP[currentAlignment];

			const alignment = doAlign({
				offset: [OVERLAY_OFFSET_X, OVERLAY_OFFSET_Y],
				overflow: {
					adjustX: true,
					adjustY: true,
				},
				points,
				sourceElement: popoverRef.current,
				targetElement: trigger,
			});

			const alignmentString = alignment.points.join('');

			const pointsString = points.join('');

			if (alignment.overflow.adjustX) {
				setCurrentAlignment(ALIGNMENTS_GUESS_MAP[alignmentString]);
			}
			else if (pointsString !== alignmentString) {
				setCurrentAlignment(ALIGNMENTS_INVERSE_MAP[alignmentString]);
			}

			if (!darkbg && previousTrigger !== trigger) {
				trigger.classList.add('lfr-walkthrough-element-shadow');

				previousTrigger.classList.remove(
					'lfr-walkthrough-element-shadow'
				);
			}
		}
	}, [
		currentAlignment,
		darkbg,
		popoverRef,
		popoverVisible,
		previousTrigger,
		trigger,
	]);

	useEffect(() => {
		align();
	}, [align]);

	useObserveRect(align, popoverRef?.current);

	return (
		<>
			{!popoverVisible && (
				<Hotspot
					onHotspotClick={() => setPopoverVisible(true)}
					ref={hotspotRef}
					trigger={trigger}
				/>
			)}

			{darkbg && (
				<WalkthroughOverlay
					popoverVisible={popoverVisible}
					trigger={trigger}
				/>
			)}

			{popoverVisible && (
				<ReactPortal>
					<ClayPopover
						alignPosition={currentAlignment}
						closeOnClickOutside={closeOnClickOutside}
						displayType="secondary"
						header={
							<ClayLayout.ContentRow
								noGutters
								verticalAlign="center"
							>
								<ClayLayout.ContentCol expand>
									<span>{`Step ${
										stepIndex + 1
									} of ${stepLength}: ${title}`}</span>
								</ClayLayout.ContentCol>

								{closeable && (
									<ClayLayout.ContentCol>
										<ClayButtonWithIcon
											aria-label={Liferay.Language.get(
												'close'
											)}
											className="close"
											displayType="unstyled"
											onClick={() =>
												setPopoverVisible(false)
											}
											small
											symbol="times"
										/>
									</ClayLayout.ContentCol>
								)}
							</ClayLayout.ContentRow>
						}
						onShowChange={setPopoverVisible}
						ref={popoverRef}
						show={popoverVisible}
						size="lg"
					>
						<div
							dangerouslySetInnerHTML={{
								__html: content,
							}}
						></div>

						<ClayLayout.ContentRow noGutters verticalAlign="center">
							{skippable && (
								<ClayLayout.ContentCol expand>
									<ClayCheckbox
										label={Liferay.Language.get(
											'do-not-show-me-this-again'
										)}
									/>
								</ClayLayout.ContentCol>
							)}

							<ClayLayout.ContentCol>
								<ClayButton.Group spaced>
									{stepIndex > 0 && (
										<ClayButton
											displayType="secondary"
											onClick={() => onPrevious()}
											small
										>
											{Liferay.Language.get('previous')}
										</ClayButton>
									)}

									{stepIndex + 1 !== stepLength ? (
										<ClayButton
											onClick={() => onNext()}
											small
										>
											{Liferay.Language.get('ok')}
										</ClayButton>
									) : (
										<ClayButton
											onClick={() =>
												setPopoverVisible(false)
											}
											small
										>
											{Liferay.Language.get('close')}
										</ClayButton>
									)}
								</ClayButton.Group>
							</ClayLayout.ContentCol>
						</ClayLayout.ContentRow>
					</ClayPopover>
				</ReactPortal>
			)}
		</>
	);
};

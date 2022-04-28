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
import observeRect from './observeRect';

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

	useEffect(() => {
		if (trigger) {
			return observeRect(trigger, align);
		}
	}, [align, trigger]);

	return (
		<div className="hotspot" onClick={onHotspotClick} ref={ref}>
			<div className="hotspot-inner" />
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

Walkthrough.propTypes = {
	closeOnClickOutside: PropTypes.bool,
	closeable: PropTypes.bool,
	skippable: PropTypes.bool,
	steps: PropTypes.arrayOf(
		PropTypes.shape({
			content: PropTypes.string,
			nodeToHighlight: PropTypes.string,
			title: PropTypes.string,
		})
	),
};

export default Walkthrough;

const OVERLAY_OFFSET_X = 15;
const OVERLAY_OFFSET_Y = -10;

const WalkthroughStep = ({
	closeOnClickOutside,
	closeable,
	content,
	darkbg,
	onNext,
	onPrevious,
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

	const [bounds, setBounds] = useState({
		height: 0,
		width: 0,
		x: 0,
		y: 0,
	});

	useEffect(() => {
		if (popoverRef?.current) {
			return observeRect(trigger, ({height, width, x, y}) =>
				setBounds({height, width, x, y})
			);
		}
	}, [trigger, popoverVisible, stepIndex]);

	const align = useCallback(() => {
		if (trigger && popoverRef?.current) {
			if (!darkbg && previousTrigger !== trigger) {
				trigger.classList.add('walkthrough-element-shadow');

				previousTrigger.classList.remove('walkthrough-element-shadow');
			}

			doAlign({
				offset: [OVERLAY_OFFSET_X, OVERLAY_OFFSET_Y],
				points: ['tl', 'tr'],
				sourceElement: popoverRef.current,
				targetElement: trigger,
			});
		}
	}, [popoverRef, trigger, darkbg, previousTrigger]);

	useEffect(() => {
		if (popoverRef?.current) {
			return observeRect(popoverRef.current, align);
		}
	}, [align, popoverRef, popoverVisible, stepIndex]);

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
				<OverlayMask
					bounds={bounds}
					onBoundsChange={setBounds}
					visible={popoverVisible}
				/>
			)}

			{popoverVisible && (
				<ReactPortal>
					<ClayPopover
						alignPosition="right-top"
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
											'do-not-show-this-again'
										)}
									/>
								</ClayLayout.ContentCol>
							)}

							<ClayLayout.ContentCol>
								<ClayButton.Group spaced>
									<ClayButton
										displayType="secondary"
										onClick={() => onPrevious()}
										small
									>
										{Liferay.Language.get('previous')}
									</ClayButton>

									{stepIndex + 1 !== stepLength ? (
										<ClayButton
											onClick={() => onNext()}
											small
										>
											{Liferay.Language.get('got-it')}
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

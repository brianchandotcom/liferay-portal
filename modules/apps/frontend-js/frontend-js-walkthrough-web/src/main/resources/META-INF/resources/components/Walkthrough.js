/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './Walkthrough.scss';

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayPopover from '@clayui/popover';
import {ReactPortal, usePrevious} from '@liferay/frontend-js-react-web';
import {localStorage, navigate} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';

import {Hotspot} from '../components/Hotspot';
import {Overlay} from '../components/Overlay';
import {useClickOutside} from '../hooks/useClickOutside';
import {useLocalStorage} from '../hooks/useLocalStorage';
import {useObserveRect} from '../hooks/useObserveRect';
import {LOCAL_STORAGE_KEYS, doAlign} from '../utils';

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

/**
 * Checks if a determining element is inside the viewport
 * @param {Node} element Element to be checked
 * @param {ClientRect} maybeRect boundingClientRect
 * of the element for avoiding calling getBoundingClientRect again.
 * This operation is very costly for browsers.
 * @returns {boolean}
 */
function isVisibleInViewport(element, maybeRect) {
	let boundingRect = maybeRect;

	if (!boundingRect) {
		boundingRect = element.getBoundingClientRect();
	}

	return (
		boundingRect.top >= 0 &&
		boundingRect.left >= 0 &&
		boundingRect.bottom <=
			(window.innerHeight || document.documentElement.clientHeight) &&
		boundingRect.right <=
			(window.innerWidth || document.documentElement.clientWidth)
	);
}

/**
 * Removes search params on a given url
 * @param {String} url
 * @returns {String} url without the search params
 */
function stripSearchParams(url) {
	return url.split('?')[0];
}

/**
 * Given a `layoutRelativeURL,` this function will match the paths
 * of the pages contained in it and return the longest path.
 * @param {String} currentLayoutRelativeURL
 * @param {Array<String>} pagesArray
 * @returns {String} longest path
 */
function findLongestMatch(currentLayoutRelativeURL, pagesArray) {
	return pagesArray
		.filter((pagePath) =>
			stripSearchParams(currentLayoutRelativeURL).endsWith(pagePath)
		)
		.sort((a, b) => (a.length > b.length ? -1 : 1))[0];
}

function removeStartingSlash(path) {
	return path.replace('/', '');
}

/**
 * When receiving layoutRelativeURL, it contains parts we should use later, like the following example:
 * > themeDisplay.getLayoutRelativeURL()
 * > '/web/fiona/home'
 * We should take `'/web/fiona/'` from this URL because it can be used to be added as
 * a prefix for navigation stuff like `next` and `previous` and when matching the page.
 * So, people can provide paths in JSON without needing to specify the site name(/web/fiona in this case).
 * @param {String} currentPage
 * @returns {String} a string containg the path without the page URL
 */
function getSitePrefix(currentPage) {
	const currentDXPLayoutRelativeURL = removeStartingSlash(
		themeDisplay.getLayoutRelativeURL()
	).split('/');

	const firstPagePath = removeStartingSlash(currentPage.split('/')[0]);

	return currentDXPLayoutRelativeURL
		.slice(
			0,
			currentDXPLayoutRelativeURL.findIndex(
				(path) => path === firstPagePath
			)
		)
		.join('/');
}

const Step = ({
	closeOnClickOutside,
	closeable,
	currentPage,
	currentStep,
	memoizedTrigger,
	onCurrentStep,
	onPopoverVisible,
	pages,
	popoverVisible,
	skippable,
	steps,
}) => {
	const popoverRef = useRef(null);

	const hotspotRef = useRef(null);

	const {
		content,
		darkbg,
		next,
		positioning: defaultPositioning = 'right-top',
		previous,
		title,
	} = steps[currentStep];

	const [currentAlignment, setCurrentAlignment] = useState(
		defaultPositioning
	);

	const [checkboxValue, setCheckboxValue] = useState(false);

	const previousTrigger = usePrevious(memoizedTrigger);

	const changeStep = useCallback(
		(action, isNext, index) => {
			const hasElement = document.querySelector(
				steps[index].nodeToHighlight
			);

			if (
				hasElement ||
				(action && pages[action].includes(steps[index].id))
			) {
				onCurrentStep(index);
			}
			else {
				console.error(
					`Walkthrough Exception: ${steps[index].nodeToHighlight} element for highlight does not exist in DOM`
				);

				onCurrentStep(
					index === steps.length - 1
						? index - 1
						: isNext
						? index + 1
						: index - 1
				);
				onPopoverVisible(false);
			}
		},
		[pages, steps, onCurrentStep, onPopoverVisible]
	);

	const onNext = useCallback(
		(action) => {
			const maybeNextIndex = currentStep + 1;

			if (steps[maybeNextIndex]) {
				changeStep(action, true, maybeNextIndex);
			}
		},
		[changeStep, currentStep, steps]
	);

	const onPrevious = useCallback(
		(action) => {
			const maybePreviousIndex = currentStep - 1;

			if (steps[maybePreviousIndex]) {
				changeStep(action, false, maybePreviousIndex);
			}
		},
		[changeStep, currentStep, steps]
	);

	/**
	 * This useEffect was necessary because Walkthrough(Step) components
	 * are always mounted. So, currentAligment will not be updated when
	 * the component will be updated.
	 */
	useEffect(() => {
		setCurrentAlignment(defaultPositioning);
	}, [defaultPositioning]);

	const align = useCallback(
		(boundingRect) => {
			if (popoverVisible && popoverRef.current && memoizedTrigger) {
				const points = ALIGNMENTS_MAP[currentAlignment];

				const alignment = doAlign({
					offset: [OVERLAY_OFFSET_X, OVERLAY_OFFSET_Y],
					overflow: {
						adjustX: true,
						adjustY: true,
					},
					points,
					sourceElement: popoverRef.current,
					targetElement: memoizedTrigger,
				});

				const alignmentString = alignment.points.join('');

				const pointsString = points.join('');

				if (alignment.overflow.adjustX) {
					setCurrentAlignment(ALIGNMENTS_GUESS_MAP[alignmentString]);
				}
				else if (pointsString !== alignmentString) {
					setCurrentAlignment(
						ALIGNMENTS_INVERSE_MAP[alignmentString]
					);
				}

				if (!darkbg) {
					memoizedTrigger?.classList.add(
						'lfr-walkthrough-element-shadow'
					);

					if (
						previousTrigger &&
						memoizedTrigger !== previousTrigger
					) {
						previousTrigger?.classList.remove(
							'lfr-walkthrough-element-shadow'
						);
					}
				}

				if (!isVisibleInViewport(popoverRef.current, boundingRect)) {
					popoverRef.current.scrollIntoView();
				}
			}
		},
		[
			currentAlignment,
			darkbg,
			popoverRef,
			popoverVisible,
			previousTrigger,
			memoizedTrigger,
		]
	);

	useEffect(() => {
		align();
	}, [align]);

	useObserveRect(align, popoverRef?.current);

	useClickOutside(
		['.lfr-walkthrough-popover', '.lfr-walkthrough-hotspot'],
		() => {
			if (closeOnClickOutside) {
				onPopoverVisible(false);
			}
		}
	);

	const SITE_PREFIX_PATH = `/${getSitePrefix(currentPage)}`;

	return (
		<>
			{!popoverVisible &&
				currentStep !== steps.length &&
				!localStorage.getItem(
					LOCAL_STORAGE_KEYS.SKIPPABLE,
					localStorage.TYPES.NECESSARY
				) && (
					<Hotspot
						onHotspotClick={() => onPopoverVisible(true)}
						ref={hotspotRef}
						trigger={memoizedTrigger}
					/>
				)}

			{darkbg && (
				<Overlay
					popoverVisible={popoverVisible}
					trigger={memoizedTrigger}
				/>
			)}

			{popoverVisible && (
				<ReactPortal>
					<ClayPopover
						alignPosition={currentAlignment}
						className="lfr-walkthrough-popover"
						displayType="secondary"
						header={
							<ClayLayout.ContentRow
								noGutters
								verticalAlign="center"
							>
								<ClayLayout.ContentCol expand>
									<span>{`Step ${currentStep + 1} of ${
										steps.length
									}: ${title}`}</span>
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
												onPopoverVisible(false)
											}
											small
											symbol="times"
										/>
									</ClayLayout.ContentCol>
								)}
							</ClayLayout.ContentRow>
						}
						onShowChange={onPopoverVisible}
						ref={popoverRef}
						show={popoverVisible}
						size="lg"
					>
						<div
							dangerouslySetInnerHTML={{
								__html: content,
							}}
						/>

						<ClayLayout.ContentRow noGutters verticalAlign="center">
							{skippable && (
								<ClayLayout.ContentCol expand>
									<ClayCheckbox
										checked={checkboxValue}
										label={Liferay.Language.get(
											'do-not-show-me-this-again'
										)}
										onChange={() => {
											setCheckboxValue(!checkboxValue);

											localStorage.setItem(
												LOCAL_STORAGE_KEYS.SKIPPABLE,
												!checkboxValue,
												localStorage.TYPES.NECESSARY
											);
										}}
									/>
								</ClayLayout.ContentCol>
							)}

							<ClayLayout.ContentCol>
								<ClayButton.Group spaced>
									{currentStep > 0 && (
										<ClayButton
											displayType="secondary"
											onClick={() => {
												onPrevious(previous);

												if (previous) {
													navigate(
														SITE_PREFIX_PATH === '/'
															? previous
															: SITE_PREFIX_PATH.concat(
																	previous
															  )
													);
												}
											}}
											small
										>
											{Liferay.Language.get('previous')}
										</ClayButton>
									)}

									{currentStep + 1 !== steps.length ? (
										<ClayButton
											onClick={() => {
												onNext(next);

												if (next) {
													navigate(
														SITE_PREFIX_PATH === '/'
															? next
															: SITE_PREFIX_PATH.concat(
																	next
															  )
													);
												}
											}}
											small
										>
											{Liferay.Language.get('ok')}
										</ClayButton>
									) : (
										<ClayButton
											onClick={() => {
												onPopoverVisible(false);
												onCurrentStep(0);
											}}
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

const Walkthrough = ({
	closeOnClickOutside,
	closeable = true,
	pages = {},
	skippable = true,
	steps = [],
}) => {
	const [
		currentStepIndex,
		setCurrentStepIndex,
	] = useLocalStorage(LOCAL_STORAGE_KEYS.CURRENT_STEP, () =>
		!steps.length ? null : 0
	);

	const [popoverVisible, setPopoverVisible] = useLocalStorage(
		LOCAL_STORAGE_KEYS.POPOVER_VISIBILITY,
		false
	);

	const currentLayoutRelativeURL = themeDisplay.getLayoutRelativeURL();

	const currentPage = useMemo(
		() => findLongestMatch(currentLayoutRelativeURL, Object.keys(pages)),
		[pages, currentLayoutRelativeURL]
	);

	const memoizedTrigger = useMemo(() => {
		const trigger = steps[currentStepIndex].nodeToHighlight;

		if (trigger) {
			const currentNode = document.querySelector(trigger);

			if (currentNode) {
				return currentNode;
			}

			console.error(
				`Walkthrough Exception: ${trigger} element for highlight does not exist in DOM`
			);
		}
	}, [steps, currentStepIndex]);

	if (
		currentStepIndex === null ||
		!pages[currentPage]?.includes(steps[currentStepIndex].id)
	) {
		return null;
	}

	return (
		<Step
			closeOnClickOutside={closeOnClickOutside}
			closeable={closeable}
			currentPage={currentPage}
			currentStep={currentStepIndex}
			memoizedTrigger={memoizedTrigger}
			onCurrentStep={setCurrentStepIndex}
			onPopoverVisible={setPopoverVisible}
			pages={pages}
			popoverVisible={popoverVisible}
			skippable={skippable}
			steps={steps}
		/>
	);
};

Walkthrough.propTypes = {
	closeOnClickOutside: PropTypes.bool,
	closeable: PropTypes.bool,
	skippable: PropTypes.bool,
	steps: PropTypes.arrayOf(
		PropTypes.shape({
			content: PropTypes.string,
			darkbg: PropTypes.bool,
			nodeToHighlight: PropTypes.string.isRequired,
			positioning: PropTypes.oneOf(Object.keys(ALIGNMENTS_MAP)),
			title: PropTypes.string,
		})
	),
};

export default Walkthrough;

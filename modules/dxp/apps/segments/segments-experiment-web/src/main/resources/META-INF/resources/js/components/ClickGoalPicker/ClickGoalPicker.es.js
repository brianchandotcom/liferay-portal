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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import {useEventListener} from 'frontend-js-react-web';
import {throttle} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React from 'react';
import ReactDOM from 'react-dom';

import {StateContext as GlobalStateContext} from './../../state/context.es';
import {StateContext, getInitialState, reducer} from './reducer.es';
import {
	GeometryType,
	getElementGeometry,
	getRootElementGeometry,
	getTargetableElements,
	stopImmediatePropagation,
} from './utils.es';

const {
	useCallback,
	useContext,
	useEffect,
	useLayoutEffect,
	useReducer,
	useRef,
	useState,
} = React;

const ESCAPE_KEYS = [
	'Escape', // Most browsers.
	'Esc', // IE and Edge.
];

const POPOVER_PADDING = 16;

const THROTTLE_INTERVAL_MS = 100;

const DispatchContext = React.createContext();

/**
 * Top-level entry point for displaying, selecting, editing and removing click
 * goal targets.
 */
function ClickGoalPicker({allowEdit = true, onSelectClickGoalTarget, target}) {
	const [state, dispatch] = useReducer(reducer, target, getInitialState);

	const {selectedTarget} = state;

	const {errors} = useContext(GlobalStateContext);

	const ref = useRef(state.selectedTarget);

	useEffect(() => {
		ref.current = selectedTarget;
	}, [selectedTarget]);

	const previousTarget = ref.current;

	if (selectedTarget != previousTarget) {
		if (onSelectClickGoalTarget) {
			onSelectClickGoalTarget(selectedTarget);
		}
	}

	if (process.env.NODE_ENV === 'test') {
		if (!document.getElementById('content')) {
			const content = document.createElement('div');

			content.id = 'content';

			document.body.appendChild(content);
		}
	}

	const root = document.getElementById('content');

	if (!root) {
		console.error(
			'Cannot render <SegmentsExperimentsClickGoal /> without #content element',
			document.querySelectorAll('section').length
		);

		return null;
	}

	const scrollIntoView = (event) => {
		const target = document.querySelector(state.selectedTarget);

		if (target) {
			target.scrollIntoView();

			// Make sure nothing slides under the top nav.

			window.scrollBy(0, -100);
		}

		event.preventDefault();

		dispatch({type: 'activate'});
	};

	return (
		<DispatchContext.Provider value={dispatch}>
			<StateContext.Provider value={state}>
				<h4 className="mb-3 mt-4 sheet-subtitle">
					{Liferay.Language.get('click-goal')}
					<ClayIcon
						className="lexicon-icon-sm ml-1 reference-mark text-warning"
						style={{verticalAlign: 'super'}}
						symbol="asterisk"
					/>
				</h4>

				{state.selectedTarget && (
					<ClayLayout.ContentRow containerElement="dl">
						<ClayLayout.ContentCol containerElement="dt">
							{Liferay.Language.get('element')}:
						</ClayLayout.ContentCol>

						<ClayLayout.ContentCol
							className="mb-0 ml-2 text-truncate-inline"
							containerElement="dd"
							expand
						>
							<ClayLink
								className="text-truncate"
								href={state.selectedTarget}
								onClick={scrollIntoView}
								title={state.selectedTarget}
							>
								{state.selectedTarget}
							</ClayLink>
						</ClayLayout.ContentCol>
					</ClayLayout.ContentRow>
				)}

				{!state.selectedTarget && (
					<dl>
						<dt className="d-inline">
							{Liferay.Language.get('element')}:
						</dt>
						<dd className="d-inline ml-2 text-secondary">
							{Liferay.Language.get(
								'a-clickable-element-on-the-page-must-be-selected-to-be-measured'
							)}
							{errors.clickTargetError && (
								<div className="font-weight-bold mt-2 text-danger">
									<ClayIcon
										className="mr-2"
										symbol="exclamation-full"
									/>
									{Liferay.Language.get(
										'an-element-needs-to-be-set'
									)}
								</div>
							)}
						</dd>
					</dl>
				)}

				{allowEdit && (
					<ClayButton
						displayType="secondary"
						onClick={() => dispatch({type: 'activate'})}
						small
					>
						{state.selectedTarget
							? Liferay.Language.get('edit-element')
							: Liferay.Language.get('set-element')}
					</ClayButton>
				)}

				{state.mode === 'active' ? (
					<ClickGoalPicker.OverlayContainer
						allowEdit={allowEdit}
						root={root}
					/>
				) : null}
			</StateContext.Provider>
		</DispatchContext.Provider>
	);
}

ClickGoalPicker.propTypes = {
	allowEdit: PropTypes.bool,
	onSelectClickGoalTarget: PropTypes.func,
	target: PropTypes.string,
};

/**
 * Responsible for performing the "full-screen takeover" and mounting the
 * <Overlay /> component when active.
 */
function OverlayContainer({allowEdit, root}) {
	const cssId = 'segments-experiments-click-goal-css-overrides';

	const dispatch = useContext(DispatchContext);

	const targetableElements = useRef();

	// Before mount.

	if (!targetableElements.current) {

		// Apply CSS overrides.

		const css = `
			#banner {
				cursor: not-allowed;
			}
			#banner a, #banner button {
				cursor: not-allowed;
				pointer-events: none;
			}
			#content {
				position: relative;
				cursor: not-allowed;
			}

			.portlet-content-editable {
				border-color: transparent;
			}

			.portlet-topper {
				visibility: hidden;
			}
		`;
		const head = document.head;
		const style = document.createElement('style');

		style.id = cssId;
		style.type = 'text/css';
		style.appendChild(document.createTextNode(css));

		head.appendChild(style);

		// This must happen after hiding the toppers.

		targetableElements.current = getTargetableElements(root);
	}

	// On unmount.

	useEffect(() => {
		return () => {

			// Remove CSS overrides.

			const style = document.getElementById(cssId);

			if (style) {
				style.parentNode.removeChild(style);
			}
		};
	}, []);

	const handleKeydown = useCallback(
		(event) => {
			if (ESCAPE_KEYS.includes(event.key)) {
				dispatch({type: 'deactivate'});
				event.preventDefault();
				stopImmediatePropagation(event);
			}
		},
		[dispatch]
	);

	const handleClick = useCallback(
		(event) => {

			// Clicking anywhere other than a target aborts target selection.

			event.preventDefault();
			stopImmediatePropagation(event);
			dispatch({type: 'deactivate'});
		},
		[dispatch]
	);

	useEventListener('keydown', handleKeydown, true, document);

	useEventListener('click', handleClick, false, document);

	return ReactDOM.createPortal(
		<ClickGoalPicker.Overlay
			allowEdit={allowEdit}
			root={root}
			targetableElements={targetableElements.current}
		/>,
		root
	);
}

OverlayContainer.propTypes = {
	allowEdit: PropTypes.bool,
	root: PropTypes.instanceOf(Element).isRequired,
};

/**
 * Covers the main content surface of the page (the "#content" element)
 * and draws UI over each targetable element.
 */
function Overlay({allowEdit, root, targetableElements}) {
	const {editingTarget, selectedTarget} = useContext(StateContext);

	const [geometry, setGeometry] = useState(getRootElementGeometry(root));

	const handleResize = useCallback(
		throttle(() => {
			setGeometry(getRootElementGeometry(root));
		}, THROTTLE_INTERVAL_MS),
		[root]
	);

	// For now, treat scrolling just like resizing.

	const handleScroll = handleResize;

	useEventListener('resize', handleResize, false, window);

	// TODO: also consider scrolling of elements with "overflow: auto/scroll";

	useEventListener('scroll', handleScroll, false, window);

	return (
		<div className="lfr-segments-experiment-click-goal-root">
			{targetableElements
				.filter((element) => {
					if (allowEdit === true) {
						return true;
					}
					if ('#' + element.id === selectedTarget) {
						return true;
					}

					return false;
				})
				.map((element) => {
					const selector = `#${element.id}`;

					const mode =
						editingTarget === selector && allowEdit
							? 'editing'
							: selectedTarget === selector
							? 'selected'
							: 'inactive';

					return (
						<ClickGoalPicker.Target
							allowEdit={allowEdit}
							element={element}
							geometry={geometry}
							key={selector}
							mode={mode}
							selector={selector}
						/>
					);
				})}
		</div>
	);
}

Overlay.propTypes = {
	allowEdit: PropTypes.bool,
	root: PropTypes.instanceOf(Element).isRequired,
	targetableElements: PropTypes.arrayOf(PropTypes.instanceOf(Element))
		.isRequired,
};

/**
 * Draws three controls above/on-top-of/below a potential click goal target:
 *
 * - A <TargetTopper />, above the target, which labels the target and can be
 *   used to undo the selection of that target.
 * - An rectangle on-top-of the target which can be clicked on to select it.
 * - A <TargetPopover />, below the target, that provides contextual information
 *   and a button to confirm the selection of that target.
 */
function Target({allowEdit, element, geometry, mode, selector}) {
	const dispatch = useContext(DispatchContext);

	const {bottom, height, left, right, top, width} = getElementGeometry(
		element
	);

	if (!bottom && !top && !right && !left) {
		return null;
	}

	const handleClick = (event) => {
		dispatch({
			selector,
			type: 'editTarget',
		});

		stopImmediatePropagation(event);
	};

	// At this point we don't know the dimensions of our children, but we do
	// know whether we have more space on the left or right of our target, so we
	// flip based on that.

	const spaceOnLeft = left - geometry.left;
	const spaceOnRight = geometry.right - right;
	const spaceOnTop = top - geometry.top;
	const align = spaceOnRight > spaceOnLeft ? 'left' : 'right';

	// TODO: make tooltip match mock and switch to Clay v3 tooltips directly
	// instead of using lfr-portal-tooltip.

	return (
		<div
			className="lfr-segments-experiment-click-goal-target"
			style={{
				alignItems: align === 'left' ? 'flex-start' : 'flex-end',
				left: align === 'left' ? spaceOnLeft : null,
				right: align === 'right' ? spaceOnRight : null,
				top: spaceOnTop,
			}}
		>
			<ClayTooltipProvider>
				<div
					className={classNames(
						'lfr-segments-experiment-click-goal-target-overlay',
						{
							'lfr-segments-experiment-click-goal-target-overlay-editing':
								mode === 'editing',
							'lfr-segments-experiment-click-goal-target-overlay-selected':
								mode === 'selected',
						}
					)}
					data-target-selector={selector}
					data-tooltip-align="bottom-left"
					onClick={handleClick}
					style={{height, width}}
					title={
						mode === 'inactive'
							? Liferay.Language.get(
									'click-element-to-set-as-click-target-for-your-goal'
							  )
							: ''
					}
				></div>
			</ClayTooltipProvider>
			{mode !== 'inactive' && (
				<ClickGoalPicker.TargetTopper
					allowEdit={allowEdit}
					element={element}
					geometry={geometry}
					isEditing={mode === 'editing'}
					selector={selector}
				/>
			)}
			{mode === 'editing' && (
				<ClickGoalPicker.TargetPopover selector={selector} />
			)}
		</div>
	);
}

Target.propTypes = {
	allowEdit: PropTypes.bool,
	element: PropTypes.instanceOf(Element).isRequired,
	geometry: GeometryType.isRequired,
	mode: PropTypes.oneOf(['inactive', 'selected', 'editing']).isRequired,
	selector: PropTypes.string.isRequired,
};

/**
 * Drawn above a click target, and includes a button to undo the selection of
 * that target.
 */
function TargetTopper({allowEdit, geometry, isEditing, selector}) {
	const dispatch = useContext(DispatchContext);

	const topperRef = useRef();

	const [top, setTop] = useState(0);

	const [width, setWidth] = useState(null);

	useLayoutEffect(() => {
		if (topperRef.current) {
			const {
				height,
				left,
				width,
			} = topperRef.current.getBoundingClientRect();

			setTop(-height);

			setWidth(Math.min(geometry.width - (left - geometry.left), width));
		}
	}, [geometry.left, geometry.width]);

	const handleClick = (event) => {
		stopImmediatePropagation(event);

		dispatch({
			selector: '',
			type: 'selectTarget',
		});
	};

	return (
		<div
			className={classNames({
				'd-flex': true,
				'lfr-segments-experiment-click-goal-target-topper': true,
				'lfr-segments-experiment-click-goal-target-topper-editing': isEditing,
				'px-2': true,
				small: true,
				'text-white': true,
			})}
			onClick={stopImmediatePropagation}
			ref={topperRef}
			style={{
				maxWidth: width !== null ? `${width}px` : null,
				top: `${top}px`,
			}}
		>
			<span className="mr-2 text-truncate">
				{isEditing ? selector : Liferay.Language.get('target')}
			</span>
			{allowEdit && (
				<ClayButton
					className="lfr-segments-experiment-click-goal-target-delete small text-white"
					displayType="unstyled"
					onClick={handleClick}
				>
					<ClayIcon symbol="times-circle" />
				</ClayButton>
			)}
		</div>
	);
}

TargetTopper.propTypes = {
	allowEdit: PropTypes.bool,
	geometry: GeometryType.isRequired,
	isEditing: PropTypes.bool.isRequired,
	selector: PropTypes.string.isRequired,
};

/**
 * Drawn below a click target, and provides contextual information and a button
 * to confirm the selection of that target.
 */
function TargetPopover({selector}) {
	const dispatch = useContext(DispatchContext);

	const buttonRef = useRef();

	const [buttonWidth, setButtonWidth] = useState(null);

	useLayoutEffect(() => {
		if (buttonRef.current) {
			setButtonWidth(buttonRef.current.offsetWidth);
		}
	}, []);

	// The +1 here is to avoid unwanted wrapping of the button.

	const maxWidth = buttonWidth
		? `${buttonWidth + POPOVER_PADDING * 2 + 1}px`
		: 'none';

	const handleClick = () => {
		dispatch({
			selector,
			type: 'selectTarget',
		});
	};

	return (
		<div
			className="lfr-segments-experiment-click-goal-target-popover p-3"
			onClick={stopImmediatePropagation}
			style={{maxWidth}}
		>
			<div className="mb-2 text-secondary text-truncate" title={selector}>
				{selector}
			</div>
			<ClayButton onClick={handleClick} ref={buttonRef}>
				{Liferay.Language.get('set-element-as-click-target')}
			</ClayButton>
		</div>
	);
}

TargetPopover.propTypes = {
	selector: PropTypes.string.isRequired,
};

ClickGoalPicker.Overlay = Overlay;
ClickGoalPicker.OverlayContainer = OverlayContainer;
ClickGoalPicker.Target = Target;
ClickGoalPicker.TargetPopover = TargetPopover;
ClickGoalPicker.TargetTopper = TargetTopper;

export default ClickGoalPicker;

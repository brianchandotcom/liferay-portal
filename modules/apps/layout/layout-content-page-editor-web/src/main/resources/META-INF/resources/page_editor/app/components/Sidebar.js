/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon, default as ClayButton} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {
	ReactPortal,
	useIsMounted,
	useStateSafe,
} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {useId, useSessionState} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useRef} from 'react';

import useLazy from '../../common/hooks/useLazy';
import useLoad from '../../common/hooks/useLoad';
import usePlugins from '../../common/hooks/usePlugins';
import * as Actions from '../actions/index';
import {config} from '../config/index';
import {useSelectItem} from '../contexts/ControlsContext';
import {useDispatch, useSelector} from '../contexts/StoreContext';
import selectAvailablePanels from '../selectors/selectAvailablePanels';
import selectItemConfigurationOpen from '../selectors/selectItemConfigurationOpen';
import selectSidebarIsOpened from '../selectors/selectSidebarIsOpened';
import switchSidebarPanel from '../thunks/switchSidebarPanel';
import {useDropClear} from '../utils/drag_and_drop/useDragAndDrop';

const {Suspense, useCallback, useEffect} = React;

/**
 * Failure to preload is a non-critical failure, so we'll use this to swallow
 * rejected promises silently.
 */
const swallow = [(value) => value, (_error) => undefined];

/**
 * Load the first available panel if the selected sidebar panel ID is not found.
 * This may happen because the list of panels is modified depending on the user permissions.
 *
 * @param {string} panelId
 * @param {Array} panels
 * @param {object} sidebarPanels
 */
const getActivePanelData = ({panelId, panels, sidebarPanels}) => {
	let sidebarPanelId = panelId;

	let panel = panels.some((panel) => panel.includes(sidebarPanelId))
		? sidebarPanels[sidebarPanelId]
		: null;

	if (!panel) {
		sidebarPanelId = panels[0][0];
		panel = sidebarPanels[sidebarPanelId];
	}

	return {panel, sidebarPanelId};
};

export const MAX_SIDEBAR_WIDTH = 500;
export const MIN_SIZEBAR_WIDTH = 280;
export const SIDEBAR_WIDTH_RESIZE_STEP = 20;

export default function Sidebar() {
	const dropClearRef = useDropClear();
	const [hasError, setHasError] = useStateSafe(false);
	const {getInstance, register} = usePlugins();
	const dispatch = useDispatch();
	const isMounted = useIsMounted();
	const load = useLoad();
	const [resizing, setResizing] = useStateSafe(false);
	const selectItem = useSelectItem();
	const separatorRef = useRef();
	const sidebarContentId = useId();
	const sidebarId = useId();
	const store = useSelector((state) => state);

	const [sidebarWidth, setSidebarWidth] = useSessionState(
		`${config.portletNamespace}_sidebar-width`,
		MIN_SIZEBAR_WIDTH
	);

	const sidebarWidthRef = useRef(sidebarWidth);
	sidebarWidthRef.current = sidebarWidth;

	const sidebarContentRef = useRef();
	const tabListRef = useRef();

	const panels = useSelector(selectAvailablePanels(config.panels));
	const sidebarHidden = store.sidebar.hidden;
	const sidebarOpen = selectSidebarIsOpened(store);
	const itemConfigurationOpen = selectItemConfigurationOpen(store);
	const {panel, sidebarPanelId} = getActivePanelData({
		panelId: store.sidebar.panelId,
		panels,
		sidebarPanels: config.sidebarPanels,
	});

	const promise = panel
		? load(sidebarPanelId, panel.pluginEntryPoint)
		: Promise.resolve();

	const app = {
		Actions,
		config,
		dispatch,
		store,
	};

	let registerPanel;

	if (sidebarPanelId && panel) {
		registerPanel = register(sidebarPanelId, promise, {app, panel});
	}

	const togglePlugin = () => {
		if (hasError) {
			setHasError(false);
		}

		if (registerPanel) {
			registerPanel.then((plugin) => {
				if (
					plugin &&
					typeof plugin.activate === 'function' &&
					isMounted()
				) {
					plugin.activate();
				}
				else if (!plugin) {
					setHasError(true);
				}
			});
		}
	};

	useEffect(
		() => {
			if (panel) {
				togglePlugin(panel);
			}
			else if (sidebarPanelId) {
				dispatch(
					switchSidebarPanel({
						sidebarOpen: false,
						sidebarPanelId: null,
					})
				);
			}
		},
		/* eslint-disable react-hooks/exhaustive-deps */
		[panel, sidebarOpen, sidebarPanelId]
	);

	useEffect(() => {
		const wrapper = document.getElementById('wrapper');

		if (!wrapper) {
			return;
		}

		wrapper.classList.add('page-editor__wrapper');

		wrapper.classList.toggle(
			'page-editor__wrapper--padded-start',
			sidebarOpen
		);

		wrapper.classList.toggle(
			'page-editor__wrapper--sidebar--hidden',
			sidebarHidden
		);

		wrapper.classList.toggle(
			'page-editor__wrapper--padded-end',
			itemConfigurationOpen
		);

		return () => {
			wrapper.classList.remove('page-editor__wrapper');
			wrapper.classList.remove('page-editor__wrapper--padded-start');
			wrapper.classList.remove('page-editor__wrapper--padded-end');
		};
	}, [sidebarOpen, itemConfigurationOpen]);

	useEffect(() => {
		const separatorElement = separatorRef.current;

		if (!separatorElement) {
			return;
		}

		let initialSidebarWidth;
		let initialCursorPosition;

		const handleMouseMove = (event) => {
			const cursorDelta = event.clientX - initialCursorPosition;

			if (
				Liferay.Language.direction?.[themeDisplay?.getLanguageId()] ===
				'rtl'
			) {
				setSidebarWidth(
					Math.min(
						MAX_SIDEBAR_WIDTH,
						Math.max(
							MIN_SIZEBAR_WIDTH,
							initialSidebarWidth - cursorDelta
						)
					)
				);
			}
			else {
				setSidebarWidth(
					Math.min(
						MAX_SIDEBAR_WIDTH,
						Math.max(
							MIN_SIZEBAR_WIDTH,
							initialSidebarWidth + cursorDelta
						)
					)
				);
			}
		};

		const stopResizing = () => {
			setResizing(false);
			document.body.removeEventListener('mousemove', handleMouseMove);
			document.body.removeEventListener('mouseleave', stopResizing);
			document.body.removeEventListener('mouseup', stopResizing);
		};

		const handleMouseDown = (event) => {
			setResizing(true);

			event.preventDefault();

			initialSidebarWidth = sidebarWidthRef.current;
			initialCursorPosition = event.clientX;

			document.body.addEventListener('mousemove', handleMouseMove);
			document.body.addEventListener('mouseleave', stopResizing);
			document.body.addEventListener('mouseup', stopResizing);
		};

		separatorElement.addEventListener('mousedown', handleMouseDown);

		return () => {
			stopResizing();
			separatorElement.removeEventListener('mousedown', handleMouseDown);
		};
	}, [separatorRef, setResizing, setSidebarWidth, sidebarWidthRef]);

	const SidebarPanel = useLazy(
		useCallback(({instance}) => {
			if (typeof instance.renderSidebar === 'function') {
				return instance.renderSidebar();
			}
			else {
				return null;
			}
		}, [])
	);

	const deselectItem = (event) => {
		if (event.target === event.currentTarget) {
			selectItem(null);
		}
	};

	const handleClick = (panel) => {
		const open =
			panel.sidebarPanelId === sidebarPanelId ? !sidebarOpen : true;

		dispatch(
			switchSidebarPanel({
				sidebarOpen: open,
				sidebarPanelId: panel.sidebarPanelId,
			})
		);

		if (open) {
			sidebarContentRef.current?.focus();
		}
	};

	const handleTabKeyDown = (event) => {
		if (event.key === 'ArrowUp' || event.key === 'ArrowDown') {
			const tabs = Array.from(
				tabListRef.current.querySelectorAll('button')
			);

			const positionActiveTab = tabs.indexOf(document.activeElement);

			const activeTab =
				tabs[
					event.key === 'ArrowUp'
						? positionActiveTab - 1
						: positionActiveTab + 1
				];

			if (activeTab) {
				activeTab.focus();
			}
		}
	};

	const handleSeparatorKeyDown = (event) => {
		if (
			Liferay.Language.direction?.[themeDisplay?.getLanguageId()] ===
			'rtl'
		) {
			if (event.key === 'ArrowLeft') {
				setSidebarWidth(
					Math.min(
						MAX_SIDEBAR_WIDTH,
						sidebarWidth + SIDEBAR_WIDTH_RESIZE_STEP
					)
				);
			}
			else if (event.key === 'ArrowRight') {
				setSidebarWidth(
					Math.max(
						MIN_SIZEBAR_WIDTH,
						sidebarWidth - SIDEBAR_WIDTH_RESIZE_STEP
					)
				);
			}
			else if (event.key === 'Home') {
				setSidebarWidth(MIN_SIZEBAR_WIDTH);
			}
			else if (event.key === 'End') {
				setSidebarWidth(MAX_SIDEBAR_WIDTH);
			}
		}
		else {
			if (event.key === 'ArrowLeft') {
				setSidebarWidth(
					Math.max(
						MIN_SIZEBAR_WIDTH,
						sidebarWidth - SIDEBAR_WIDTH_RESIZE_STEP
					)
				);
			}
			else if (event.key === 'ArrowRight') {
				setSidebarWidth(
					Math.min(
						MAX_SIDEBAR_WIDTH,
						sidebarWidth + SIDEBAR_WIDTH_RESIZE_STEP
					)
				);
			}
			else if (event.key === 'Home') {
				setSidebarWidth(MIN_SIZEBAR_WIDTH);
			}
			else if (event.key === 'End') {
				setSidebarWidth(MAX_SIDEBAR_WIDTH);
			}
		}
	};

	return (
		<ReactPortal className="cadmin">
			<div
				className={classNames(
					'page-editor__sidebar page-editor__theme-adapter-forms'
				)}
				ref={dropClearRef}
				style={{'--sidebar-content-width': `${sidebarWidth}px`}}
			>
				<div
					aria-orientation="vertical"
					className={classNames('page-editor__sidebar__buttons', {
						'light': true,
						'page-editor__sidebar__buttons--hidden': sidebarHidden,
					})}
					onClick={deselectItem}
					onKeyDown={handleTabKeyDown}
					ref={tabListRef}
					role="tablist"
				>
					{panels.reduce((elements, group, groupIndex) => {
						const buttons = group.map((panelId) => {
							const panel = config.sidebarPanels[panelId];

							const active =
								sidebarOpen && sidebarPanelId === panelId;
							const {
								icon,
								isLink,
								label,
								pluginEntryPoint,
								url,
							} = panel;

							if (isLink) {
								return (
									<a
										className={classNames({active})}
										data-tooltip-align="left"
										href={url}
										key={panel.sidebarPanelId}
										title={label}
									>
										<ClayIcon symbol={icon} />
									</a>
								);
							}

							const prefetch = () =>
								load(
									panel.sidebarPanelId,
									pluginEntryPoint
								).then(...swallow);

							return (
								<ClayButtonWithIcon
									aria-controls={sidebarContentId}
									aria-label={Liferay.Language.get(
										panel.label
									)}
									aria-selected={active}
									className={classNames({active})}
									data-panel-id={panel.sidebarPanelId}
									data-tooltip-align="left"
									displayType="unstyled"
									id={`${sidebarId}${panel.sidebarPanelId}`}
									key={panel.sidebarPanelId}
									onClick={() => handleClick(panel)}
									onFocus={prefetch}
									onMouseEnter={prefetch}
									role="tab"
									size="sm"
									symbol={icon}
									tabIndex={
										sidebarPanelId !== panelId ? '-1' : null
									}
									title={label}
								/>
							);
						});

						// Add separator between groups.

						if (groupIndex === panels.length - 1) {
							return elements.concat(buttons);
						}
						else {
							return elements.concat([
								...buttons,
								<hr key={`separator-${groupIndex}`} />,
							]);
						}
					}, [])}
				</div>

				<div
					aria-label={sub(
						Liferay.Language.get('x-panel'),
						panel.label
					)}
					className={classNames({
						'page-editor__sidebar__content': true,
						'page-editor__sidebar__content--open': sidebarOpen,
						'rtl':
							Liferay.Language.direction?.[
								themeDisplay?.getLanguageId()
							] === 'rtl',
					})}
					id={sidebarContentId}
					onClick={deselectItem}
					ref={(ref) => {
						sidebarContentRef.current = ref;

						if (sidebarOpen) {
							ref?.removeAttribute('inert');
						}
						else {
							ref?.setAttribute('inert', '');
						}
					}}
					role="tabpanel"
					tabIndex="-1"
				>
					{hasError ? (
						<div>
							<ClayButton
								block
								displayType="secondary"
								onClick={() => {
									dispatch(
										switchSidebarPanel({
											sidebarOpen: false,
											sidebarPanelId:
												panels[0] && panels[0][0],
										})
									);
									setHasError(false);
								}}
								size="sm"
							>
								{Liferay.Language.get('refresh')}
							</ClayButton>
						</div>
					) : (
						<ErrorBoundary
							handleError={() => {
								setHasError(true);
							}}
						>
							<Suspense
								fallback={
									<ClayLoadingIndicator
										className="my-4"
										size="sm"
									/>
								}
							>
								<SidebarPanel
									getInstance={getInstance}
									pluginId={sidebarPanelId}
								/>
							</Suspense>
						</ErrorBoundary>
					)}

					<div
						aria-controls={sidebarContentId}
						aria-label={Liferay.Language.get('resize-sidebar')}
						aria-orientation="vertical"
						aria-valuemax={MAX_SIDEBAR_WIDTH}
						aria-valuemin={MIN_SIZEBAR_WIDTH}
						aria-valuenow={sidebarWidth}
						className={classNames('page-editor__sidebar__resizer', {
							'page-editor__sidebar__resizer--resizing': resizing,
						})}
						onKeyDown={handleSeparatorKeyDown}
						ref={separatorRef}
						role="separator"
						tabIndex={0}
					/>
				</div>
			</div>
		</ReactPortal>
	);
}

class ErrorBoundary extends React.Component {
	static getDerivedStateFromError(_error) {
		return {hasError: true};
	}

	constructor(props) {
		super(props);

		this.state = {hasError: false};
	}

	componentDidCatch(error) {
		if (this.props.handleError) {
			this.props.handleError(error);
		}
	}

	render() {
		if (this.state.hasError) {
			return null;
		}
		else {
			return this.props.children;
		}
	}
}

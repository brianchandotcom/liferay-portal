/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon, default as ClayButton} from '@clayui/button';
import {
	ReactDOMServer,
	ReactPortal,
	useStateSafe,
} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {useId, useSessionState} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useRef, useState} from 'react';

import BrowserSidebar from '../../plugins/browser/components/BrowserSidebar';
import CommentsSidebar from '../../plugins/comments/components/CommentsSidebar';
import FragmentsSidebar from '../../plugins/fragments_and_widgets/components/FragmentsSidebar';
import MappingSidebar from '../../plugins/mapping/components/MappingSidebar';
import ContentsSidebar from '../../plugins/page_content/components/ContentsSidebar';
import PageDesignOptionsSidebar from '../../plugins/page_design_options/components/PageDesignOptionsSidebar';
import RulesSidebar from '../../plugins/page_rules/components/RulesSidebar';
import {openKeyboardShortcutsModal} from '../actions';
import {config} from '../config/index';
import {useSelectItem} from '../contexts/ControlsContext';
import {useDispatch, useSelector} from '../contexts/StoreContext';
import selectAvailablePanels from '../selectors/selectAvailablePanels';
import selectItemConfigurationOpen from '../selectors/selectItemConfigurationOpen';
import selectSidebarIsOpened from '../selectors/selectSidebarIsOpened';
import switchSidebarPanel from '../thunks/switchSidebarPanel';
import {useDropClear} from '../utils/drag_and_drop/useDragAndDrop';
import isSmallResolution from '../utils/isSmallResolution';
import ShortcutModal from './ShortcutModal';

const {useEffect} = React;

export const MAX_SIDEBAR_WIDTH = 500;
export const MIN_SIDEBAR_WIDTH = 280;
export const SIDEBAR_WIDTH_RESIZE_STEP = 20;

function getActiveSidebarPanel({
	sidebarPanelId,
	sidebarPanels,
	sidebarPanelsMap,
}) {
	if (sidebarPanelsMap[sidebarPanelId]) {
		return {sidebarPanel: sidebarPanelsMap[sidebarPanelId], sidebarPanelId};
	}

	const panel = sidebarPanels[0];

	return {sidebarPanel: panel, sidebarPanelId: panel.sidebarPanelId};
}

const getOpenShortcutModalTooltip = () => (
	<>
		<div>{Liferay.Language.get('open-keyboard-shortcuts')}</div>
		<kbd className="c-kbd c-kbd-dark mt-1">
			<kbd className="c-kbd">⇧</kbd>

			<span className="c-kbd-separator">+</span>

			<kbd className="c-kbd">?</kbd>
		</kbd>
	</>
);

export default function Sidebar() {
	const dropClearRef = useDropClear();
	const [hasError, setHasError] = useStateSafe(false);
	const dispatch = useDispatch();
	const [resizing, setResizing] = useStateSafe(false);
	const selectItem = useSelectItem();
	const separatorRef = useRef();
	const shortcutButtonTitleId = useId();
	const sidebarContentId = useId();
	const sidebarId = useId();
	const sidebar = useSelector((state) => state.sidebar);

	const [sidebarWidth, setSidebarWidth] = useSessionState(
		`${config.portletNamespace}_sidebar-width`,
		MIN_SIDEBAR_WIDTH
	);

	const sidebarWidthRef = useRef(sidebarWidth);
	sidebarWidthRef.current = sidebarWidth;

	const sidebarContentRef = useRef();
	const tabListRef = useRef();

	const sidebarPanels = useSelector(
		selectAvailablePanels(config.sidebarPanels)
	);
	const sidebarHidden = sidebar.hidden;
	const sidebarOpen = selectSidebarIsOpened({sidebar});
	const itemConfigurationOpen = selectItemConfigurationOpen({sidebar});

	const {sidebarPanel, sidebarPanelId} = getActiveSidebarPanel({
		sidebarPanelId: sidebar.panelId,
		sidebarPanels,
		sidebarPanelsMap: config.sidebarPanelsMap,
	});

	const [openShortcutModal, setOpenShorcutModal] = useState(false);

	const toggleShortcutModalAction = (isOpen = false) => {
		setOpenShorcutModal(isOpen);
		dispatch(
			openKeyboardShortcutsModal({
				isOpen,
			})
		);
	};

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
	}, [sidebarHidden, sidebarOpen, itemConfigurationOpen]);

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
							MIN_SIDEBAR_WIDTH,
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
							MIN_SIDEBAR_WIDTH,
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

	const deselectItem = (event) => {
		if (event.target === event.currentTarget) {
			selectItem(null);
		}
	};

	const handleClick = (panel) => {
		const open =
			panel.sidebarPanelId === sidebarPanelId ? !sidebarOpen : true;

		const smallResolution = isSmallResolution();

		dispatch(
			switchSidebarPanel({
				itemConfigurationOpen: smallResolution
					? false
					: sidebar.itemConfigurationOpen,
				sidebarOpen: open,
				sidebarPanelId: panel.sidebarPanelId,
			})
		);

		if (open) {
			sidebarContentRef.current.style.visibility = 'visible';
			sidebarContentRef.current?.focus({preventScroll: true});
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
						MIN_SIDEBAR_WIDTH,
						sidebarWidth - SIDEBAR_WIDTH_RESIZE_STEP
					)
				);
			}
			else if (event.key === 'Home') {
				setSidebarWidth(MIN_SIDEBAR_WIDTH);
			}
			else if (event.key === 'End') {
				setSidebarWidth(MAX_SIDEBAR_WIDTH);
			}
		}
		else {
			if (event.key === 'ArrowLeft') {
				setSidebarWidth(
					Math.max(
						MIN_SIDEBAR_WIDTH,
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
				setSidebarWidth(MIN_SIDEBAR_WIDTH);
			}
			else if (event.key === 'End') {
				setSidebarWidth(MAX_SIDEBAR_WIDTH);
			}
		}
	};

	const shortcutButtonTitle = getOpenShortcutModalTooltip();

	return (
		<ReactPortal className="cadmin">
			<div
				className="page-editor__sidebar page-editor__theme-adapter-forms"
				ref={dropClearRef}
				style={{'--sidebar-content-width': `${sidebarWidth}px`}}
			>
				<div
					className={classNames('page-editor__sidebar__buttons', {
						'page-editor__sidebar__buttons--hidden': sidebarHidden,
					})}
				>
					<div
						aria-orientation="vertical"
						onClick={deselectItem}
						onKeyDown={handleTabKeyDown}
						ref={tabListRef}
						role="tablist"
					>
						{sidebarPanels.map((panel) => {
							const active =
								sidebarOpen &&
								panel.sidebarPanelId === sidebarPanelId;
							const {icon, label} = panel;

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
									role="tab"
									size="sm"
									symbol={icon}
									tabIndex={
										panel.sidebarPanelId !== sidebarPanelId
											? '-1'
											: null
									}
									title={label}
								/>
							);
						})}
					</div>

					<ClayButtonWithIcon
						aria-haspopup="dialog"
						aria-labelledby={shortcutButtonTitleId}
						className="mt-auto"
						data-title={ReactDOMServer.renderToString(
							shortcutButtonTitle
						)}
						data-title-set-as-html
						data-tooltip-align="left"
						displayType="unstyled"
						id={`${sidebarId}keyboard_shortcuts`}
						onClick={() => toggleShortcutModalAction(true)}
						size="sm"
						symbol="question-circle-full"
					/>
				</div>

				{openShortcutModal && (
					<ShortcutModal
						onCloseModal={() => toggleShortcutModalAction(false)}
					/>
				)}

				<div className="sr-only" id={shortcutButtonTitleId}>
					{shortcutButtonTitle}
				</div>

				<div
					aria-label={sub(
						Liferay.Language.get('x-panel'),
						sidebarPanel.label
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
					ref={sidebarContentRef}
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
												config.sidebarPanels[0],
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
							<SidebarPanel
								sidebarPanelId={sidebarPanel.sidebarPanelId}
							/>
						</ErrorBoundary>
					)}

					<div
						aria-controls={sidebarContentId}
						aria-label={Liferay.Language.get('resize-sidebar')}
						aria-orientation="vertical"
						aria-valuemax={MAX_SIDEBAR_WIDTH}
						aria-valuemin={MIN_SIDEBAR_WIDTH}
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

const PANEL_COMPONENTS = {
	browser: BrowserSidebar,
	comments: CommentsSidebar,
	fragments_and_widgets: FragmentsSidebar,
	mapping: MappingSidebar,
	page_content: ContentsSidebar,
	page_design_options: PageDesignOptionsSidebar,
	page_rules: RulesSidebar,
};

const SidebarPanel = React.memo(({sidebarPanelId}) => {
	const Component = PANEL_COMPONENTS[sidebarPanelId];

	if (Component !== null) {
		return <Component />;
	}

	return null;
});

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

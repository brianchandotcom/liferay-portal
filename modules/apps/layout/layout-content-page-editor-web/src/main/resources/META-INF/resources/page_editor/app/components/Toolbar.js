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
import ClayLayout from '@clayui/layout';
import {ReactPortal, useIsMounted} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {openConfirmModal} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import useLazy from '../../core/hooks/useLazy';
import useLoad from '../../core/hooks/useLoad';
import usePlugins from '../../core/hooks/usePlugins';
import CreateLayoutPageTemplateEntryButton from '../../plugins/create-layout-page-template-entry-modal/components/CreateLayoutPageTemplateEntryButton';
import * as Actions from '../actions/index';
import {LAYOUT_TYPES} from '../config/constants/layoutTypes';
import {SERVICE_NETWORK_STATUS_TYPES} from '../config/constants/serviceNetworkStatusTypes';
import {config} from '../config/index';
import {useSelectItem} from '../contexts/ControlsContext';
import {useEditableProcessorUniqueId} from '../contexts/EditableProcessorContext';
import {useDispatch, useSelector} from '../contexts/StoreContext';
import selectCanPublish from '../selectors/selectCanPublish';
import redo from '../thunks/redo';
import undo from '../thunks/undo';
import {useDropClear} from '../utils/drag-and-drop/useDragAndDrop';
import EditModeSelector from './EditModeSelector';
import ExperimentsLabel from './ExperimentsLabel';
import HideSidebarButton from './HideSidebarButton';
import NetworkStatusBar from './NetworkStatusBar';
import PublishButton from './PublishButton';
import Translation from './Translation';
import UnsafeHTML from './UnsafeHTML';
import ViewportSizeSelector from './ViewportSizeSelector';
import Undo from './undo/Undo';

const {Suspense, useCallback, useRef} = React;

function ToolbarBody({className}) {
	const dispatch = useDispatch();
	const dropClearRef = useDropClear();
	const editableProcessorUniqueId = useEditableProcessorUniqueId();
	const formRef = useRef();
	const {getInstance, register} = usePlugins();
	const isMounted = useIsMounted();
	const load = useLoad();
	const selectItem = useSelectItem();
	const store = useSelector((state) => state);

	const canPublish = selectCanPublish(store);

	const [publishPending, setPublishPending] = useState(false);

	const {
		network,
		segmentsExperienceId,
		segmentsExperimentStatus,
		selectedViewportSize,
	} = store;

	const loadingRef = useRef(() => {
		Promise.all(
			config.toolbarPlugins.map((toolbarPlugin) => {
				const {pluginEntryPoint} = toolbarPlugin;
				const promise = load(pluginEntryPoint, pluginEntryPoint);

				const app = {
					Actions,
					config,
					dispatch,
					store,
				};

				return register(pluginEntryPoint, promise, {
					app,
					toolbarPlugin,
				}).then((plugin) => {
					if (!plugin) {
						throw new Error(
							`Failed to get instance from ${pluginEntryPoint}`
						);
					}
					else if (isMounted()) {
						if (typeof plugin.activate === 'function') {
							plugin.activate();
						}
					}
				});
			})
		).catch((error) => {
			if (process.env.NODE_ENV === 'development') {
				console.error(error);
			}
		});
	});

	if (loadingRef.current) {

		// Do this once only.

		loadingRef.current();
		loadingRef.current = null;
	}

	const ToolbarSection = useLazy(
		useCallback(({instance}) => {
			if (typeof instance.renderToolbarSection === 'function') {
				return instance.renderToolbarSection();
			}
			else {
				return null;
			}
		}, [])
	);

	const handleDiscardVariant = (event) => {
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-discard-current-draft-and-apply-latest-published-changes'
			),
			onConfirm: (isConfirmed) => {
				if (!isConfirmed) {
					event.preventDefault();
				}
			},
		});
	};

	const onPublish = () => {
		if (!config.masterUsed) {
			setPublishPending(true);
		}
		else {
			openConfirmModal({
				message: Liferay.Language.get(
					'changes-made-on-this-master-are-going-to-be-propagated-to-all-page-templates,-display-page-templates,-and-pages-using-it.are-you-sure-you-want-to-proceed'
				),
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						setPublishPending(true);
					}
				},
			});
		}
	};

	const onUndo = () => {
		dispatch(undo({store}));
	};

	const onRedo = () => {
		dispatch(redo({store}));
	};

	const deselectItem = (event) => {
		if (event.target === event.currentTarget) {
			selectItem(null);
		}
	};

	let publishButtonLabel = Liferay.Language.get('publish');

	if (config.layoutType === LAYOUT_TYPES.master) {
		publishButtonLabel = Liferay.Language.get('publish-master');
	}
	else if (config.singleSegmentsExperienceMode) {
		publishButtonLabel = Liferay.Language.get('save-variant');
	}
	else if (config.workflowEnabled) {
		publishButtonLabel = Liferay.Language.get('submit-for-publication');
	}

	useEffect(() => {
		if (
			(network.status === SERVICE_NETWORK_STATUS_TYPES.draftSaved ||
				!network.status) &&
			!editableProcessorUniqueId &&
			publishPending &&
			formRef.current
		) {
			formRef.current.submit();
		}
	}, [publishPending, network, editableProcessorUniqueId]);

	return (
		<ClayLayout.ContainerFluid
			className={classNames(
				'page-editor__theme-adapter-buttons',
				className
			)}
			onClick={deselectItem}
			ref={dropClearRef}
		>
			<ul className="navbar-nav start" onClick={deselectItem}>
				{config.toolbarPlugins.map(
					({loadingPlaceholder, pluginEntryPoint}) => {
						return (
							<li className="nav-item" key={pluginEntryPoint}>
								<ErrorBoundary>
									<Suspense
										fallback={
											<UnsafeHTML
												hideFromAccessibilityTree={
													false
												}
												markup={loadingPlaceholder}
											/>
										}
									>
										<ToolbarSection
											getInstance={getInstance}
											pluginId={pluginEntryPoint}
										/>
									</Suspense>
								</ErrorBoundary>
							</li>
						);
					}
				)}

				<li className="nav-item">
					<Translation
						availableLanguages={config.availableLanguages}
						defaultLanguageId={config.defaultLanguageId}
						dispatch={dispatch}
						fragmentEntryLinks={store.fragmentEntryLinks}
						languageId={store.languageId}
						segmentsExperienceId={segmentsExperienceId}
					/>
				</li>

				{!config.singleSegmentsExperienceMode &&
					segmentsExperimentStatus && (
						<li className="nav-item pl-2">
							<ExperimentsLabel
								label={segmentsExperimentStatus.label}
								value={segmentsExperimentStatus.value}
							/>
						</li>
					)}
			</ul>

			<ul className="middle navbar-nav" onClick={deselectItem}>
				<li className="nav-item">
					<ViewportSizeSelector
						onSizeSelected={(size) => {
							if (size !== selectedViewportSize) {
								dispatch(Actions.switchViewportSize({size}));
							}
						}}
						selectedSize={selectedViewportSize}
					/>
				</li>
			</ul>

			<ul className="end navbar-nav" onClick={deselectItem}>
				<li className="nav-item">
					<NetworkStatusBar {...network} />
				</li>

				<li className="nav-item">
					<Undo onRedo={onRedo} onUndo={onUndo} />
				</li>

				<li className="nav-item">
					<EditModeSelector />
				</li>

				<li className="nav-item">
					<ul className="navbar-nav">
						<li className="nav-item">
							<HideSidebarButton />
						</li>

						{config.layoutType === LAYOUT_TYPES.content && (
							<li className="nav-item">
								<CreateLayoutPageTemplateEntryButton />
							</li>
						)}
					</ul>
				</li>

				{config.singleSegmentsExperienceMode && (
					<li className="nav-item">
						<form action={config.discardDraftURL} method="POST">
							<ClayButton
								className="btn btn-secondary"
								displayType="secondary"
								onClick={handleDiscardVariant}
								small
								type="submit"
							>
								{Liferay.Language.get('discard-variant')}
							</ClayButton>
						</form>
					</li>
				)}

				<li className="nav-item">
					<PublishButton
						canPublish={canPublish}
						formRef={formRef}
						label={publishButtonLabel}
						onPublish={onPublish}
					/>
				</li>
			</ul>
		</ClayLayout.ContainerFluid>
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
		if (process.env.NODE_ENV === 'development') {
			console.error(error);
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

export default function Toolbar() {
	const container = document.getElementById(config.toolbarId);
	const isMounted = useIsMounted();

	if (!isMounted()) {

		// First time here, must empty JSP-rendered markup from container.

		while (container.firstChild) {
			container.removeChild(container.firstChild);
		}
	}

	return (
		<ReactPortal container={container} wrapper={false}>
			<ToolbarBody />
		</ReactPortal>
	);
}

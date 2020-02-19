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
import {useIsMounted} from 'frontend-js-react-web';
import React, {useEffect, useState} from 'react';
import ReactDOM from 'react-dom';

import useLazy from '../../core/hooks/useLazy';
import useLoad from '../../core/hooks/useLoad';
import usePlugins from '../../core/hooks/usePlugins';
import * as Actions from '../actions/index';
import {PAGE_TYPES} from '../config/constants/pageTypes';
import {config} from '../config/index';
import {useDispatch, useSelector} from '../store/index';
import {useSelectItem} from './Controls';
import ExperimentsLabel from './ExperimentsLabel';
import NetworkStatusBar from './NetworkStatusBar';
import Translation from './Translation';
import UnsafeHTML from './UnsafeHTML';

const {Suspense, useCallback, useRef} = React;

function ToolbarBody() {
	const dispatch = useDispatch();
	const {getInstance, register} = usePlugins();
	const isMounted = useIsMounted();
	const load = useLoad();
	const selectItem = useSelectItem();
	const store = useSelector(state => state);

	const {layoutData, segmentsExperienceId, segmentsExperimentStatus} = store;

	const [enableDiscard, setEnableDiscard] = useState(false);

	useEffect(() => {
		const mainItemId = layoutData.rootItems.main;
		const mainItem = layoutData.items[mainItemId];
		setEnableDiscard(mainItem.children.length > 0);
	}, [layoutData]);

	const loading = useRef(() => {
		Promise.all(
			config.toolbarPlugins.map(toolbarPlugin => {
				const {pluginEntryPoint} = toolbarPlugin;
				const promise = load(pluginEntryPoint, pluginEntryPoint);

				const app = {
					Actions,
					config,
					dispatch,
					store
				};

				return register(pluginEntryPoint, promise, {
					app,
					toolbarPlugin
				}).then(plugin => {
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
		).catch(error => {
			if (process.env.NODE_ENV === 'development') {
				console.error(error);
			}
		});
	});

	if (loading.current) {
		// Do this once only.
		loading.current();
		loading.current = null;
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

	const handleDiscardDraft = event => {
		if (
			!confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-discard-current-draft-and-apply-latest-published-changes'
				)
			)
		) {
			event.preventDefault();
		}
	};

	const handleSubmit = event => {
		if (
			config.masterUsed &&
			!confirm(
				Liferay.Language.get(
					'changes-made-on-this-master-are-going-to-be-propagated-to-all-page-templates,-display-page-templates,-and-pages-using-it.are-you-sure-you-want-to-proceed'
				)
			)
		) {
			event.preventDefault();
		}
	};

	const deselectItem = event => {
		if (event.target === event.currentTarget) {
			selectItem(null, {multiSelect: event.shiftKey});
		}
	};

	let draftButtonLabel = Liferay.Language.get('discard-draft');

	if (config.pageType === PAGE_TYPES.conversion) {
		draftButtonLabel = Liferay.Language.get('discard-conversion-draft');
	}
	else if (config.singleSegmentsExperienceMode) {
		draftButtonLabel = Liferay.Language.get('discard-variant');
	}

	let publishButtonLabel = Liferay.Language.get('publish');

	if (config.pageType === PAGE_TYPES.master) {
		publishButtonLabel = Liferay.Language.get('publish-master');
	}
	else if (config.singleSegmentsExperienceMode) {
		publishButtonLabel = Liferay.Language.get('save-variant');
	}
	else if (config.workflowEnabled) {
		publishButtonLabel = Liferay.Language.get('submit-for-publication');
	}

	return (
		<div
			className="container-fluid container-fluid-max-xl"
			onClick={deselectItem}
		>
			<ul className="navbar-nav" onClick={deselectItem}>
				{config.toolbarPlugins.map(
					({loadingPlaceholder, pluginEntryPoint}) => {
						return (
							<li className="nav-item" key={pluginEntryPoint}>
								<ErrorBoundary>
									<Suspense
										fallback={
											<UnsafeHTML
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

			<ul className="navbar-nav" onClick={deselectItem}>
				<NetworkStatusBar {...store.network} />
				<li className="nav-item">
					<form action={config.discardDraftURL} method="POST">
						<input
							name={`${config.portletNamespace}redirect`}
							type="hidden"
							value={config.discardDraftRedirectURL}
						/>

						<ClayButton
							className="btn btn-secondary mr-3"
							disabled={!enableDiscard}
							displayType="secondary"
							onClick={handleDiscardDraft}
							small
							type="submit"
						>
							{draftButtonLabel}
						</ClayButton>
					</form>
				</li>
				<li className="nav-item">
					<form action={config.publishURL} method="POST">
						<input
							name={`${config.portletNamespace}redirect`}
							type="hidden"
							value={config.redirectURL}
						/>

						<ClayButton
							disabled={config.pending}
							displayType="primary"
							onClick={handleSubmit}
							small
							type="submit"
						>
							{publishButtonLabel}
						</ClayButton>
					</form>
				</li>
			</ul>
		</div>
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

	return ReactDOM.createPortal(<ToolbarBody />, container);
}

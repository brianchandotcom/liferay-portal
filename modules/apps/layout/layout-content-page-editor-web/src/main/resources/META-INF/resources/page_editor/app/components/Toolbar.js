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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {useIsMounted} from 'frontend-js-react-web';
import React from 'react';
import ReactDOM from 'react-dom';

import useLazy from '../../core/hooks/useLazy';
import useLoad from '../../core/hooks/useLoad';
import usePlugins from '../../core/hooks/usePlugins';
import * as Actions from '../actions/index';
import {getConfig} from '../config/index';
import {DispatchContext} from '../reducers/index';
import {StoreContext} from '../store/index';
import UnsafeHTML from './UnsafeHTML';

const {discard, publish} = Actions;

const {Suspense, useCallback, useContext, useRef} = React;

function ToolbarBody() {
	const config = getConfig();

	const {
		availableLanguages,
		defaultLanguageId,
		singleSegmentsExperienceMode,
		toolbarPlugins
	} = config;

	const dispatch = useContext(DispatchContext);

	const store = useContext(StoreContext);

	const isMounted = useIsMounted();

	const load = useLoad();

	const {getInstance, register} = usePlugins();

	const loading = useRef(() => {
		Promise.all(
			toolbarPlugins.map(toolbarPlugin => {
				const {pluginEntryPoint} = toolbarPlugin;
				const promise = load(pluginEntryPoint, pluginEntryPoint);

				const app = {
					Actions,
					StoreContext,
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
					} else if (isMounted()) {
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
			} else {
				return null;
			}
		}, [])
	);

	const {languageIcon} = availableLanguages[defaultLanguageId];

	return (
		<div className="container-fluid container-fluid-max-xl page-editor-toolbar">
			<ul className="navbar-nav">
				{toolbarPlugins.map(
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
											plugin={getInstance(
												pluginEntryPoint
											)}
										/>
									</Suspense>
								</ErrorBoundary>
							</li>
						);
					}
				)}
				<li className="nav-item">
					<ClayButtonWithIcon
						displayType="secondary"
						small
						symbol={languageIcon}
					/>
				</li>
			</ul>

			<ul className="navbar-nav">
				<li className="nav-item">
					<ClayButton
						className="nav-btn"
						disabled
						displayType="secondary"
						onClick={() => dispatch(discard())}
						small
					>
						{singleSegmentsExperienceMode
							? Liferay.Language.get('discard-variant')
							: Liferay.Language.get('discard-draft')}
					</ClayButton>
				</li>
				<li className="nav-item">
					<ClayButton
						className="nav-btn"
						disabled
						displayType="primary"
						onClick={() => dispatch(publish())}
						small
					>
						{singleSegmentsExperienceMode
							? Liferay.Language.get('save-variant')
							: Liferay.Language.get('publish')}
					</ClayButton>
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
		} else {
			return this.props.children;
		}
	}
}

export default function Toolbar() {
	const {toolbarId} = getConfig();

	const isMounted = useIsMounted();

	const container = document.getElementById(toolbarId);

	if (!isMounted()) {
		// First time here, must empty JSP-rendered markup from container.
		while (container.firstChild) {
			container.removeChild(container.firstChild);
		}
	}

	return ReactDOM.createPortal(<ToolbarBody />, container);
}

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
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useIsMounted, useTimeout} from 'frontend-js-react-web';
import {fetch, objectToFormData} from 'frontend-js-web';
import {globalEval} from 'metal-dom';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import SiteNavigationMenuEditor from './SiteNavigationMenuEditor';

function ContextualSidebar({
	editSiteNavigationMenuItemParentURL,
	editSiteNavigationMenuItemURL,
	editSiteNavigationMenuSettingsURL,
	portletId,
	redirect,
	siteNavigationMenuId,
	siteNavigationMenuName
}) {
	const [body, setBody] = useState('');
	const [changed, setChanged] = useState(false);
	const [loading, setLoading] = useState(false);
	const [title, setTitle] = useState('');
	const [visible, setVisible] = useState(false);
	const isMounted = useIsMounted();
	const delay = useTimeout();
	const siteNavigationMenuEditorRef = useRef();

	const namespace = Liferay.Util.getPortletNamespace(portletId);

	const openSidebar = useCallback(
		(title, url, requestBody) => {
			if (changed) {
				const confirm = confirmUnsavedChanges();

				if (confirm) {
					return;
				}
			}

			setLoading(true);
			setTitle(title);
			setVisible(true);

			fetch(url, {
				body: objectToFormData(Liferay.Util.ns(namespace, requestBody)),
				method: 'POST'
			})
				.then(response => response.text())
				.then(responseContent => {
					if (isMounted()) {
						setBody(responseContent);
						setChanged(false);
						setLoading(false);
					}
				});
		},
		[isMounted, namespace, changed]
	);

	useEffect(() => {
		if (!siteNavigationMenuEditorRef.current) {
			siteNavigationMenuEditorRef.current = new SiteNavigationMenuEditor({
				editSiteNavigationMenuItemParentURL,
				namespace
			});
		}

		const handle = siteNavigationMenuEditorRef.current.on(
			'selectedMenuItemChanged',
			event => {
				const {siteNavigationMenuItemId, title} = event.newVal.dataset;

				openSidebar(title, editSiteNavigationMenuItemURL, {
					redirect,
					siteNavigationMenuItemId
				});
			}
		);

		return () => {
			handle.removeListener();
			siteNavigationMenuEditorRef.current.dispose();
			siteNavigationMenuEditorRef.current = null;
		};
	}, [
		editSiteNavigationMenuItemParentURL,
		editSiteNavigationMenuItemURL,
		namespace,
		openSidebar,
		redirect
	]);

	useEffect(() => {
		const settingsButton = document.getElementById(
			`${namespace}showSiteNavigationMenuSettings`
		);

		if (settingsButton) {
			const handleSettingButtonClick = () => {
				openSidebar(
					siteNavigationMenuName,
					editSiteNavigationMenuSettingsURL,
					{
						redirect,
						siteNavigationMenuId
					}
				);
			};

			settingsButton.addEventListener('click', handleSettingButtonClick);

			return () =>
				settingsButton.removeEventListener(
					'click',
					handleSettingButtonClick
				);
		}
	}, [
		editSiteNavigationMenuSettingsURL,
		namespace,
		openSidebar,
		redirect,
		siteNavigationMenuId,
		siteNavigationMenuName,
		visible
	]);

	const confirmUnsavedChanges = () => {
		const form = document.querySelector(`.sidebar-body form`);

		const error = form ? form.querySelector('[role="alert"]') : null;

		let confirmChanged;

		if (!error) {
			confirmChanged = confirm(
				Liferay.Language.get(
					'you-have-unsaved-changes.-do-you-want-to-save-them'
				)
			);

			if (confirmChanged) {
				if (form) {
					form.submit();
				}
			}
		}

		return confirmChanged;
	};

	const closeSidebar = useCallback(() => {
		setChanged(false);

		delay(() => {
			setVisible(false);
		}, 0);
	}, [delay]);

	return (
		<div
			className={`contextual-sidebar ${
				visible ? 'contextual-sidebar-visible' : ''
			} sidebar sidebar-light`}
			id={`${namespace}sidebar`}
		>
			<div className="sidebar-header">
				<div className="autofit-row sidebar-section">
					<div className="autofit-col autofit-col-expand">
						<p className="component-title">
							<span className="text-truncate-inline">
								<span className="text-truncate">{title}</span>
							</span>
						</p>
					</div>
					<div className="autofit-col">
						<ClayButton
							displayType="unstyled"
							monospaced
							onClick={() => {
								if (changed) {
									confirmUnsavedChanges();
								}
								closeSidebar();
							}}
						>
							<ClayIcon symbol="times" />
						</ClayButton>
					</div>
				</div>
			</div>

			<div className="sidebar-body">
				{loading ? (
					<ClayLoadingIndicator />
				) : (
					<SidebarBody
						body={body}
						onChange={() => setChanged(true)}
					/>
				)}
			</div>
		</div>
	);
}

class SidebarBody extends React.Component {
	constructor(props) {
		super(props);

		this._handleOnChange = this._handleOnChange.bind(this);
		this._ref = React.createRef();
	}

	componentDidMount() {
		if (this._ref.current) {
			globalEval.runScriptsInElement(this._ref.current);

			this._ref.current.addEventListener('change', this._handleOnChange);
		}
	}

	componentWillUnmount() {
		if (this._ref.current) {
			this._ref.current.removeEventListener(
				'change',
				this._handleOnChange
			);
		}
	}

	shouldComponentUpdate() {
		return false;
	}

	_handleOnChange() {
		if (this.props.onChange) {
			this.props.onChange();
		}
	}

	render() {
		return (
			<div
				dangerouslySetInnerHTML={{__html: this.props.body}}
				ref={this._ref}
			/>
		);
	}
}

export default function(props) {
	return <ContextualSidebar {...props} />;
}

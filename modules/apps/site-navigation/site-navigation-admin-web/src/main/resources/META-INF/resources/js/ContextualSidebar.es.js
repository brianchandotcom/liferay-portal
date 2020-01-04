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

import {fetch, objectToFormData} from 'frontend-js-web';
import {globalEval} from 'metal-dom';
import {useIsMounted} from 'frontend-js-react-web';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {useEffect, useState} from 'react';
import SiteNavigationMenuEditor from './SiteNavigationMenuEditor.es';

function ContextualSidebar({
	editSiteNavigationMenuItemURL,
	portletId,
	redirect,
	siteNavigationMenuEditor
}) {
	const [body, setBody] = useState('');
	const [loading, setLoading] = useState(false);
	const [title, setTitle] = useState('');
	const [visible, setVisible] = useState(false);
	const isMounted = useIsMounted();

	const namespace = Liferay.Util.getPortletNamespace(portletId);

	useEffect(() => {
		const handle = siteNavigationMenuEditor.on(
			'selectedMenuItemChanged',
			event => {
				const {siteNavigationMenuItemId, title} = event.newVal.dataset;

				setLoading(true);
				setTitle(title);
				setVisible(true);

				fetch(editSiteNavigationMenuItemURL, {
					body: objectToFormData(
						Liferay.Util.ns(namespace, {
							redirect,
							siteNavigationMenuItemId
						})
					),
					method: 'POST'
				})
					.then(response => response.text())
					.then(responseContent => {
						if (isMounted()) {
							setBody(responseContent);
							setLoading(false);
						}
					});
			}
		);

		return () => handle.removeListener();
	}, []);

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
						<h4 className="component-title">
							<span className="text-truncate-inline">
								<span className="text-truncate">{title}</span>
							</span>
						</h4>
					</div>
					<div className="autofit-col">
						<ClayButton
							monospaced
							onClick={() => setVisible(false)}
							displayType="unstyled"
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
					<SidebarBody body={body} />
				)}
			</div>
		</div>
	);
}

class SidebarBody extends React.Component {
	constructor(props) {
		super(props);

		this._ref = React.createRef();
	}

	componentDidMount() {
		if (this._ref.current) {
			globalEval.runScriptsInElement(this._ref.current);
		}
	}

	shouldComponentUpdate() {
		return false;
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
	const siteNavigationMenuEditor = new SiteNavigationMenuEditor({
		editSiteNavigationMenuItemParentURL:
			props.editSiteNavigationMenuItemParentURL,
		namespace: Liferay.Util.getPortletNamespace(props.portletId)
	});

	return (
		<ContextualSidebar
			{...props}
			siteNavigationMenuEditor={siteNavigationMenuEditor}
		/>
	);
}

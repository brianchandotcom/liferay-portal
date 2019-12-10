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

import ClayCard from '@clayui/card';
import {ClayInput} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useIsMounted} from 'frontend-js-react-web';
import {fetch, objectToFormData} from 'frontend-js-web';
import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';
import SiteNavigationMenuEditor from './SiteNavigationMenuEditor.es';
import ClayIcon from '@clayui/icon';
import ClayButton from '@clayui/button';

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

	useEffect(() => {
		const handle = siteNavigationMenuEditor.on(
			'selectedMenuItemChanged',
			event => {
				const {siteNavigationMenuItemId, title} = event.newVal.dataset;

				/*
			if (!closeSidebar() || !siteNavigationMenuItem) {
				return;
			}

			openSidebar(
				siteNavigationMenuItem.dataset.title,
				'<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcPath" value="/edit_site_navigation_menu_item.jsp" /></portlet:renderURL>',
				{
					redirect: '<%= currentURL %>',
					siteNavigationMenuItemId
				}
			);
			*/

				setLoading(true);
				setTitle(title);
				setVisible(true);

				fetch(editSiteNavigationMenuItemURL, {
					body: objectToFormData(
						Liferay.Util.ns(Liferay.Util.getPortletNamespace(portletId), {
							redirect,
							siteNavigationMenuItemId
						})
					),
					method: 'POST'
				})
					.then(response => response.text())
					.then(responseContent => {
						/*
						const sidebarBody = document.getElementById(
							'<portlet:namespace />sidebarBody'
						);

						const sidebarHeaderButton = document.getElementById(
							'<portlet:namespace />sidebarHeaderButton'
						);

						if (sidebarBody) {
							sidebarBody.innerHTML = responseContent;

							globalEval.default.runScriptsInElement(sidebarBody);

							sidebarBodyChangeHandler = dom.on(
								sidebarBody,
								'change',
								handleSidebarBodyChange
							);
						}

						if (sidebarHeaderButton) {
							sidebarHeaderButtonClickEventListener = dom.on(
								sidebarHeaderButton,
								'click',
								handleSidebarCloseButtonClick
							);
						}
						*/
						setBody(responseContent);
						setLoading(false);
					});
			}
		);

		return () => handle.removeListener();
	}, []);

	return visible ? (
		<div
			className="{$contextualSidebarClasses} {$elementClasses ?: ''}"
			id="{$id ?: ''}"
		>
			<div className="sidebar-header {$headerClasses ?: ''}">
				<div className="autofit-row sidebar-section">
					<div className="autofit-col autofit-col-expand">
						<h4 className="component-title">
							<span className="text-truncate-inline">
								<span className="text-truncate">{title}</span>
							</span>
						</h4>
					</div>
					<div className="autofit-col">
						<ClayButton monospaced>
							<ClayIcon symbol="times" />
						</ClayButton>
					</div>
				</div>
			</div>

			<div className="sidebar-body {$bodyClasses ?: ''}">
				{loading ? <ClayLoadingIndicator /> : {body}}
			</div>
		</div>
	) : null;
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

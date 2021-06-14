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

import React, {useContext} from 'react';

import {StoreContext} from './StoreContext.es';

function Breadcrumbs(props) {
	const {state} = useContext(StoreContext);

	return props.data ? (
		<ol className="breadcrumb mb-3">
			{props.data.map((element, i) => {
				const content = (
					<span className="breadcrumb-text-truncate">
						{element.label}
					</span>
				);

				function handleBreadcrumbLink(event) {
					event.preventDefault();
					const filteredId = /^.*\/(.*)$/.exec(element.url)[1];
					const formattedUrl = `?folderId=${filteredId}`;
					state.app.history.push(formattedUrl);
				}

				return (
					<li className="breadcrumb-item" key={i}>
						{element.url ? (
							<a
								data-senna-off
								href="#"
								key={i}
								onClick={handleBreadcrumbLink}
							>
								{content}
							</a>
						) : (
							content
						)}
					</li>
				);
			})}
		</ol>
	) : null;
}

export default Breadcrumbs;

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
import React, {useContext} from 'react';

import useRequest from '../../hooks/useRequest.es';
import List from '../list/List.es';
import {SidebarContext} from './SidebarContext.es';

export default () => {
	const {
		field,
		formReportRecordsFieldValuesURL,
		isOpen,
		portletNamespace,
		toggleSidebar,
		totalEntries,
	} = useContext(SidebarContext);

	let endpoint = null;

	if (field) {
		const {origin} = new URL(formReportRecordsFieldValuesURL);
		const path = formReportRecordsFieldValuesURL.replace(origin, '');

		endpoint = `${path}&${portletNamespace}fieldName=${field.name}`;
	}
	else {
		return null;
	}

	const {isLoading, response: data = []} = useRequest(endpoint);

	if (!isOpen) {
		return null;
	}

	const {icon, label} = field;

	return (
		<>
			<div className="sidebar-backdrop"></div>
			<div className="open sidebar-container">
				<div className="sidebar sidebar-light">
					<nav className="component-tbar tbar">
						<div className="container-fluid">
							<ul className="tbar-nav">
								<li className="tbar-item">
									<div className="icon">
										<ClayIcon symbol={icon} />
									</div>
								</li>

								<li className="tbar-item tbar-item-expand">
									<div className="tbar-section">
										<div className="field-info">
											<p className="title">{label}</p>

											<p className="description">
												{totalEntries}{' '}
												{Liferay.Language.get(
													'entries'
												).toLowerCase()}
											</p>
										</div>
									</div>
								</li>

								<li className="tbar-item">
									<ClayButton
										displayType="unstyled"
										onClick={() => toggleSidebar()}
									>
										<ClayIcon
											className="close-button"
											symbol={'times-small'}
										/>
									</ClayButton>
								</li>
							</ul>
						</div>
					</nav>

					<div className="sidebar-body">
						{isLoading && (
							<div className="align-items-center d-flex loading-wrapper">
								<ClayLoadingIndicator />
							</div>
						)}

						<List data={data}></List>
					</div>
				</div>
			</div>
		</>
	);
};

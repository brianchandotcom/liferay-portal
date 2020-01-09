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

import React, {useContext} from 'react';

import {ConfigContext} from '../../../app/config/index';
import SidebarPanelContent from '../../../common/components/SidebarPanelContent';
import SidebarPanelHeader from '../../../common/components/SidebarPanelHeader';

export default function MappingSidebar() {
	const {selectedMappingTypes} = useContext(ConfigContext);

	return (
		<>
			<SidebarPanelHeader>
				{Liferay.Language.get('mapping')}
			</SidebarPanelHeader>

			<SidebarPanelContent>
				<p className="mb-4 small text-secondary">
					{Liferay.Language.get(
						'content-source-selected-for-this-display-page-template'
					)}
				</p>

				<div className="d-flex flex-column mb-4">
					<p className="list-group-title">
						{Liferay.Language.get('content-type')}:
					</p>
					<p className="mb-0 small">
						{selectedMappingTypes.type.label}
					</p>
				</div>

				{selectedMappingTypes.subtype && (
					<div className="d-flex flex-column">
						<p className="list-group-title">
							{Liferay.Language.get('subtype')}:
						</p>
						<p className="small">
							{selectedMappingTypes.subtype.label}
						</p>
					</div>
				)}
			</SidebarPanelContent>
		</>
	);
}

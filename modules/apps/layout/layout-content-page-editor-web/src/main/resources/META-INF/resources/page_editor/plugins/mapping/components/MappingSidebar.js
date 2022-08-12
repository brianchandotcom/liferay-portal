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

import ClayLink from '@clayui/link';
import React from 'react';

import {config} from '../../../app/config/index';
import SidebarPanelHeader from '../../../common/components/SidebarPanelHeader';

export default function MappingSidebar() {
	return (
		<>
			<SidebarPanelHeader>
				{Liferay.Language.get('mapping')}
			</SidebarPanelHeader>

			<p className="mb-4 small text-secondary">
				{config.selectedMappingTypes.mappingDescription}
			</p>

			<div className="d-flex flex-column mb-4">
				<p className="list-group-title">
					{config.selectedMappingTypes.type.groupTypeTitle}:
				</p>

				<p className="mb-0 small">
					{config.selectedMappingTypes.type.label}
				</p>
			</div>

			{config.selectedMappingTypes.subtype &&
				config.selectedMappingTypes.subtype.label && (
					<div className="d-flex flex-column mb-4">
						<p className="list-group-title">
							{
								config.selectedMappingTypes.subtype
									.groupSubtypeTitle
							}
							:
						</p>

						<p className="mb-0 small">
							{config.selectedMappingTypes.subtype.url ? (
								<ClayLink
									href={
										config.selectedMappingTypes.subtype.url
									}
									target="_parent"
								>
									{config.selectedMappingTypes.subtype.label}
								</ClayLink>
							) : (
								<>{config.selectedMappingTypes.subtype.label}</>
							)}
						</p>
					</div>
				)}

			{config.selectedMappingTypes.itemType &&
				config.selectedMappingTypes.itemType.label && (
					<div className="d-flex flex-column">
						<p className="list-group-title">
							{
								config.selectedMappingTypes.itemType
									.groupItemTypeTitle
							}
							:
						</p>

						<p className="small">
							{config.selectedMappingTypes.itemType.label}
						</p>
					</div>
				)}
		</>
	);
}

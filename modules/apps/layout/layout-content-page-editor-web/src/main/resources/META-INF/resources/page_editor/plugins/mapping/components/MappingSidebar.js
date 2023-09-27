/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

			<div className="p-3">
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
											config.selectedMappingTypes.subtype
												.url
										}
										target="_parent"
									>
										{
											config.selectedMappingTypes.subtype
												.label
										}
									</ClayLink>
								) : (
									<>
										{
											config.selectedMappingTypes.subtype
												.label
										}
									</>
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
			</div>
		</>
	);
}

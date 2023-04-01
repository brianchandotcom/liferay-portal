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

import {ClayToggle} from '@clayui/form';
import ClayPanel from '@clayui/panel';
import React from 'react';

interface ConfigurationContainerProps {
	hasUpdateObjectDefinitionPermission: boolean;
	setValues: (values: Partial<ObjectDefinition>) => void;
	values: Partial<ObjectDefinition>;
}

export function ConfigurationContainer({
	hasUpdateObjectDefinitionPermission,
	setValues,
	values,
}: ConfigurationContainerProps) {
	const isReadOnly = Liferay.FeatureFlags['LPS-167253']
		? !values.modifiable && values.system
		: values.system;

	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle={Liferay.Language.get('configuration')}
			displayType="unstyled"
		>
			<ClayPanel.Body>
				<div className="lfr-objects__object-definition-details-configuration">
					<ClayToggle
						disabled={
							isReadOnly || !hasUpdateObjectDefinitionPermission
						}
						label={Liferay.Language.get('show-widget')}
						name="showWidget"
						onToggle={() => setValues({portlet: !values.portlet})}
						toggled={values.portlet}
					/>

					<ClayToggle
						disabled={
							isReadOnly || !hasUpdateObjectDefinitionPermission
						}
						label={Liferay.Language.get('enable-categorization')}
						name="enableCategorization"
						onToggle={() =>
							setValues({
								enableCategorization: !values.enableCategorization,
							})
						}
						toggled={values.enableCategorization}
					/>

					<ClayToggle
						disabled={
							isReadOnly || !hasUpdateObjectDefinitionPermission
						}
						label={Liferay.Language.get('enable-comments')}
						name="enableComments"
						onToggle={() =>
							setValues({
								enableComments: !values.enableComments,
							})
						}
						toggled={values.enableComments}
					/>

					<ClayToggle
						disabled={isReadOnly}
						label={Liferay.Language.get('enable-entry-history')}
						name="enableEntryHistory"
						onToggle={() =>
							setValues({
								enableObjectEntryHistory: !values.enableObjectEntryHistory,
							})
						}
						toggled={values.enableObjectEntryHistory}
					/>
				</div>
			</ClayPanel.Body>
		</ClayPanel>
	);
}

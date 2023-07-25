/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Toggle} from '@liferay/object-js-components-web';
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
		<div className="lfr-objects__object-definition-details-configuration">
			<Toggle
				disabled={isReadOnly || !hasUpdateObjectDefinitionPermission}
				label={Liferay.Language.get('show-widget')}
				name="showWidget"
				onToggle={() => setValues({portlet: !values.portlet})}
				toggled={values.portlet}
			/>

			<Toggle
				disabled={isReadOnly || !hasUpdateObjectDefinitionPermission}
				label={Liferay.Language.get('enable-categorization')}
				name="enableCategorization"
				onToggle={() =>
					setValues({
						enableCategorization: !values.enableCategorization,
					})
				}
				toggled={values.enableCategorization}
			/>

			<Toggle
				disabled={isReadOnly || !hasUpdateObjectDefinitionPermission}
				label={Liferay.Language.get('enable-comments')}
				name="enableComments"
				onToggle={() =>
					setValues({
						enableComments: !values.enableComments,
					})
				}
				toggled={values.enableComments}
			/>

			<Toggle
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
	);
}

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Toggle} from '@liferay/object-js-components-web';
import {sub} from 'frontend-js-web';
import React from 'react';

interface ConfigurationContainerProps {
	hasUpdateObjectDefinitionPermission: boolean;
	isLinkedObjectDefinition?: boolean;
	isRootDescendantNode: boolean;
	setValues: (values: Partial<ObjectDefinition>) => void;
	values: Partial<ObjectDefinition>;
}

export function ConfigurationContainer({
	hasUpdateObjectDefinitionPermission,
	isLinkedObjectDefinition,
	isRootDescendantNode,
	setValues,
	values,
}: ConfigurationContainerProps) {
	const isReadOnly = Liferay.FeatureFlags['LPS-167253']
		? !values.modifiable && values.system
		: values.system;

	const disabled =
		!hasUpdateObjectDefinitionPermission ||
		isLinkedObjectDefinition ||
		isReadOnly;

	return (
		<div className="lfr-objects__object-definition-details-configuration">
			<Toggle
				disabled={disabled || isRootDescendantNode}
				label={sub(
					Liferay.Language.get('show-widget-in-x'),
					Liferay.Language.get('page-builder')
				)}
				name="showWidget"
				onToggle={() => setValues({portlet: !values.portlet})}
				toggled={values.portlet}
			/>

			<Toggle
				disabled={disabled}
				label={sub(
					Liferay.Language.get('enable-x'),
					Liferay.Language.get('categorization-of-object-entries')
				)}
				name="enableCategorization"
				onToggle={() =>
					setValues({
						enableCategorization: !values.enableCategorization,
					})
				}
				toggled={values.enableCategorization}
			/>

			<Toggle
				disabled={disabled}
				label={sub(
					Liferay.Language.get('enable-x'),
					Liferay.Language.get('comments-in-page-builder')
				)}
				name="enableComments"
				onToggle={() =>
					setValues({
						enableComments: !values.enableComments,
					})
				}
				toggled={values.enableComments}
			/>

			<Toggle
				disabled={isLinkedObjectDefinition || isReadOnly}
				label={sub(
					Liferay.Language.get('enable-x'),
					Liferay.Language.get('entry-history-in-audit-framework')
				)}
				name="enableEntryHistory"
				onToggle={() =>
					setValues({
						enableObjectEntryHistory: !values.enableObjectEntryHistory,
					})
				}
				toggled={values.enableObjectEntryHistory}
			/>

			{Liferay.FeatureFlags['LPS-181663'] && (
				<Toggle
					disabled={
						isReadOnly || !hasUpdateObjectDefinitionPermission
					}
					label={Liferay.Language.get(
						'allow-users-to-save-entries-as-draft'
					)}
					name="enableObjectEntryDraft"
					onToggle={() =>
						setValues({
							enableObjectEntryDraft: !values.enableObjectEntryDraft,
						})
					}
					toggled={values.enableObjectEntryDraft}
				/>
			)}
		</div>
	);
}

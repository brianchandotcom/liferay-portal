/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	CodeEditorLocalized,
	SidebarCategory,
	SingleSelect,
	stringUtils,
} from '@liferay/object-js-components-web';
import {createResourceURL, fetch} from 'frontend-js-web';
import React, {useMemo, useState} from 'react';
interface FreeMarkerTemplateEditorProps {
	baseResourceURL: string;
	objectDefinitions: ObjectDefinition[];
	selectedLocale: Liferay.Language.Locale;
	setSelectedLocale: (value: Liferay.Language.Locale) => void;
	setValues: (values: Partial<NotificationTemplate>) => void;
	values: NotificationTemplate;
}

export function FreeMarkerTemplateEditor({
	baseResourceURL,
	objectDefinitions,
	selectedLocale,
	setSelectedLocale,
	setValues,
	values,
}: FreeMarkerTemplateEditorProps) {
	const [selectedEntityValue, setSelectedEntityValue] = useState<number>();
	const [entityFields, setEntityFields] = useState<SidebarCategory[]>([]);

	const objectDefinitionItems = useMemo(() => {
		const availableObjectDefinitions = objectDefinitions.filter(
			(objectDefinition) => {
				const {label: statusLabel} = objectDefinition.status;

				if (objectDefinition.system) {
					return (
						objectDefinition.name !== 'Address' &&
						objectDefinition.name !== 'User' &&
						objectDefinition.name !== 'AccountEntry' &&
						objectDefinition.name !== 'CommercePricingClass'
					);
				}

				return statusLabel === 'approved';
			}
		);

		return availableObjectDefinitions.map(
			({defaultLanguageId, id, label, name}) => ({
				label: stringUtils.getLocalizableLabel(
					defaultLanguageId,
					label,
					name
				),
				value: id,
			})
		) as LabelValueObject<number>[];
	}, [objectDefinitions]);

	const getEntityFields = async (objectDefinitionId: number) => {
		const response = await fetch(
			createResourceURL(baseResourceURL, {
				objectDefinitionId,
				p_p_resource_id:
					'/notification_templates/notification_template_ftl_elements',
			}).toString()
		);

		setEntityFields(await response.json());
	};

	return (
		<CodeEditorLocalized
			CustomSidebarContent={
				<SingleSelect
					disabled={values.system}
					id="freeMarkerEntity"
					items={objectDefinitionItems ?? []}
					label={Liferay.Language.get('entity')}
					onSelectionChange={(value) => {
						setSelectedEntityValue(value as number);
						getEntityFields(value as number);
					}}
					selectedKey={selectedEntityValue}
				/>
			}
			mode="freemarker"
			onSelectedLocaleChange={({label}) => {
				setSelectedLocale(label);
			}}
			onTranslationsChange={(translations) => {
				setValues({
					...values,
					body: translations,
				});
			}}
			placeholder={`<#--${Liferay.Language.get(
				'add-elements-from-the-sidebar-to-define-your-template'
			)}-->`}
			readOnly={values.system}
			selectedLocale={selectedLocale}
			sidebarElements={entityFields}
			sidebarElementsDisabled={values.system}
			translations={values.body}
		/>
	);
}

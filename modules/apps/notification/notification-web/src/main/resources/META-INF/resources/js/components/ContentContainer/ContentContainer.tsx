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

import {Text} from '@clayui/core';
import {
	Card,
	FormError,
	InputLocalized,
	RichTextLocalized,
	SingleSelect,
} from '@liferay/object-js-components-web';
import React from 'react';

import {DefinitionOfTerms} from '../DefinitionOfTermsContainer/DefinitionOfTerms';
import {GeneralTerms} from '../DefinitionOfTermsContainer/GeneralTerms';
import {Attachments} from './Attachments';
import {FreeMarkerTemplateEditor} from './FreeMarkerTemplateEditor';

const EDITOR_TYPES = [
	{
		label: Liferay.Language.get('freemarker-template'),
		value: 'freeMarker',
	},
	{
		label: Liferay.Language.get('rich-text'),
		value: 'richText',
	},
];

interface EditorType extends LabelValueObject {
	value: editorTypeOptions;
}

interface ContentContainerProps {
	baseResourceURL: string;
	editorConfig: object;
	errors: FormError<NotificationTemplate>;
	objectDefinitions: ObjectDefinition[];
	selectedLocale: Locale;
	setSelectedLocale: React.Dispatch<
		React.SetStateAction<Liferay.Language.Locale>
	>;
	setValues: (values: Partial<NotificationTemplate>) => void;
	values: NotificationTemplate;
}

export default function ContentContainer({
	baseResourceURL,
	editorConfig,
	errors,
	objectDefinitions,
	selectedLocale,
	setSelectedLocale,
	setValues,
	values,
}: ContentContainerProps) {
	return (
		<Card title={Liferay.Language.get('content')}>
			<Text as="span" color="secondary">
				{Liferay.Language.get(
					'use-terms-to-populate-fields-dynamically-with-the-exception-of-the-freemarker-template-editor'
				)}
			</Text>

			<InputLocalized
				{...(values.type === 'userNotification' && {
					component: 'textarea',
				})}
				error={errors.subject}
				label={Liferay.Language.get('subject')}
				name="subject"
				onChange={(translation) => {
					setValues({
						...values,
						subject: translation,
					});
				}}
				placeholder=""
				required
				selectedLocale={selectedLocale}
				translations={values.subject}
			/>

			{values.type === 'email' && (
				<>
					<SingleSelect<EditorType>
						label={Liferay.Language.get('editor-type')}
						onChange={({value}: EditorType) => {
							setValues({
								...values,
								editorType: value,
							});
						}}
						options={EDITOR_TYPES as EditorType[]}
						required
						value={
							EDITOR_TYPES.find(
								({value}) => value === values.editorType
							)?.label
						}
					/>

					{values.editorType === 'richText' ? (
						<RichTextLocalized
							editorConfig={editorConfig}
							label={Liferay.Language.get('template')}
							name="template"
							onSelectedLocaleChange={({label}) =>
								setSelectedLocale(label)
							}
							onTranslationsChange={(translation) => {
								setValues({
									...values,
									body: translation,
								});
							}}
							selectedLocale={selectedLocale}
							translations={values.body}
						/>
					) : (
						<>
							<FreeMarkerTemplateEditor
								baseResourceURL={baseResourceURL}
								objectDefinitions={objectDefinitions}
								selectedLocale={selectedLocale}
								setSelectedLocale={setSelectedLocale}
								setValues={setValues}
								values={values}
							/>

							<Text as="span" color="secondary" size={3}>
								{Liferay.Language.get(
									'object-terms-cannot-be-used-in-freemarker-templates'
								)}
							</Text>
						</>
					)}
				</>
			)}

			{!Liferay.FeatureFlags['LPS-165849'] && (
				<>
					<DefinitionOfTerms
						baseResourceURL={baseResourceURL}
						objectDefinitions={objectDefinitions}
					/>

					<GeneralTerms baseResourceURL={baseResourceURL} />
				</>
			)}

			{values.type === 'email' && (
				<Attachments setValues={setValues} values={values} />
			)}
		</Card>
	);
}

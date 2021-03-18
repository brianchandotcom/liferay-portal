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

import ClayAlert from '@clayui/alert';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {ClassicEditor} from 'frontend-editor-ckeditor-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const TranslationFieldEditor = ({
	editorConfiguration,
	id,
	label,
	sourceContent,
	sourceContentDir,
	targetContent,
	targetContentDir,
}) => {
	const [value, setValue] = useState(targetContent);

	return (
		<ClayLayout.Row>
			<ClayLayout.Col md={6}>
				<ClayForm.Group>
					<label className="control-label">{label}</label>
					<div
						className="translation-editor-preview"
						dangerouslySetInnerHTML={{__html: sourceContent}}
						dir={sourceContentDir}
					/>
				</ClayForm.Group>
			</ClayLayout.Col>
			<ClayLayout.Col md={6}>
				<ClayForm.Group>
					<label className="control-label">{label}</label>
					<ClassicEditor
						contents={targetContent}
						editorConfig={{
							...editorConfiguration.editorConfig,
							contentsLangDirection: targetContentDir,
						}}
						name={id}
						onChange={(data) => {
							if (value !== data) {
								setValue(data);
							}
						}}
					/>
					<input defaultValue={value} name={id} type="hidden" />
				</ClayForm.Group>
			</ClayLayout.Col>
		</ClayLayout.Row>
	);
};

const TranslationFieldInput = ({
	id,
	label,
	multiline,
	sourceContent,
	sourceContentDir,
	targetContent,
	targetContentDir,
}) => (
	<ClayLayout.Row>
		<ClayLayout.Col md={6}>
			<ClayForm.Group>
				<label className="control-label">{label}</label>
				<ClayInput
					component={multiline ? 'textarea' : undefined}
					defaultValue={sourceContent}
					dir={sourceContentDir}
					readOnly
					type="text"
				/>
			</ClayForm.Group>
		</ClayLayout.Col>
		<ClayLayout.Col md={6}>
			<ClayForm.Group>
				<label className="control-label" htmlFor={id}>
					{label}
				</label>
				<ClayInput
					component={multiline ? 'textarea' : undefined}
					defaultValue={targetContent}
					dir={targetContentDir}
					id={id}
					name={id}
					type="text"
				/>
			</ClayForm.Group>
		</ClayLayout.Col>
	</ClayLayout.Row>
);

const TranslationFieldSetEntries = ({infoFieldSetEntries, portletNamespace}) =>
	infoFieldSetEntries.map(({fields, legend}) => (
		<React.Fragment key={legend}>
			<ClayLayout.Row>
				<ClayLayout.Col md={6}>
					<div className="fieldset-title">{legend}</div>
				</ClayLayout.Col>
				<ClayLayout.Col md={6}>
					<div className="fieldset-title">{legend}</div>
				</ClayLayout.Col>
			</ClayLayout.Row>
			{fields.map((field) => {
				const id = `${portletNamespace}${field.id}`;

				return field.html ? (
					<TranslationFieldEditor key={field.id} {...field} id={id} />
				) : (
					<TranslationFieldInput key={field.id} {...field} id={id} />
				);
			})}
		</React.Fragment>
	));

const TranslationHeader = ({sourceLanguageIdTitle, targetLanguageIdTitle}) => (
	<ClayLayout.Row>
		<ClayLayout.Col md={6}>
			<ClayIcon symbol={sourceLanguageIdTitle.toLowerCase()} />
			<span className="ml-1">{sourceLanguageIdTitle}</span>
			<div className="separator" />
		</ClayLayout.Col>
		<ClayLayout.Col md={6}>
			<ClayIcon symbol={targetLanguageIdTitle.toLowerCase()} />
			<span className="ml-1">{targetLanguageIdTitle}</span>
			<div className="separator" />
		</ClayLayout.Col>
	</ClayLayout.Row>
);

const Translate = ({
	infoFieldSetEntries,
	portletNamespace,
	sourceLanguageIdTitle,
	targetLanguageIdTitle,
	translationPermission,
}) => (
	<div className="sheet translation-edit-body-form">
		{!translationPermission ? (
			<ClayAlert>
				{Liferay.Language.get(
					'you-do-not-have-permissions-to-translate-to-any-of-the-available-languages'
				)}
			</ClayAlert>
		) : (
			<>
				<TranslationHeader
					sourceLanguageIdTitle={sourceLanguageIdTitle}
					targetLanguageIdTitle={targetLanguageIdTitle}
				/>
				<TranslationFieldSetEntries
					infoFieldSetEntries={infoFieldSetEntries}
					portletNamespace={portletNamespace}
				/>
			</>
		)}
	</div>
);

Translate.propTypes = {
	infoFieldSetEntries: PropTypes.arrayOf(
		PropTypes.shape({
			fields: PropTypes.arrayOf(
				PropTypes.shape({
					editorConfiguration: PropTypes.object,
					html: PropTypes.bool,
					id: PropTypes.string.isRequired,
					label: PropTypes.string.isRequired,
					multiline: PropTypes.bool,
					sourceContent: PropTypes.string.isRequired,
					sourceContentDir: PropTypes.string.isRequired,
					targetContent: PropTypes.string,
					targetContentDir: PropTypes.string,
				})
			),
			legend: PropTypes.string,
		})
	),
	portletNamespace: PropTypes.string.isRequired,
	sourceLanguageIdTitle: PropTypes.string.isRequired,
	targetLanguageIdTitle: PropTypes.string.isRequired,
	translationPermission: PropTypes.bool.isRequired,
};

export default Translate;

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
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {ClassicEditor} from 'frontend-editor-ckeditor-web';
import React from 'react';

const noop = () => {};

const TranslateFieldEditor = ({
	editorConfiguration,
	id,
	label,
	sourceContent,
	sourceContentDir,
	targetContent,
	targetContentDir,
	onChange = noop,
}) => (
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
					data={targetContent}
					editorConfig={{
						...editorConfiguration.editorConfig,
						contentsLangDirection: targetContentDir,
					}}
					name={id}
					onChange={(data) => {
						if (targetContent !== data.trim()) {
							onChange(data);
						}
					}}
				/>
				<input defaultValue={targetContent} name={id} type="hidden" />
			</ClayForm.Group>
		</ClayLayout.Col>
	</ClayLayout.Row>
);

const TranslateFieldInput = ({
	id,
	label,
	multiline,
	sourceContent,
	sourceContentDir,
	targetContent,
	targetContentDir,
	onChange = noop,
}) => (
	<ClayLayout.Row>
		<ClayLayout.ContentCol expand>
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
							dir={targetContentDir}
							id={id}
							name={id}
							onChange={(event) => {
								const data = event.target.value;
								onChange(data);
							}}
							type="text"
							value={targetContent}
						/>
					</ClayForm.Group>
				</ClayLayout.Col>
			</ClayLayout.Row>
		</ClayLayout.ContentCol>
		<ClayLayout.ContentCol className="align-self-top col-autotranslate-field">
			<ClayButton displayType="secondary" monospaced>
				<ClayIcon symbol="automatic-translate" />
				<span className="sr-only">Auto translate {label} field</span>
			</ClayButton>
		</ClayLayout.ContentCol>
	</ClayLayout.Row>
);

const TranslateFieldSetEntries = ({
	infoFieldSetEntries,
	onChange,
	portletNamespace,
	targetFieldsContent,
}) =>
	infoFieldSetEntries.map(({fields, legend}) => (
		<React.Fragment key={legend}>
			<ClayLayout.Row className="row-autotranslate-title">
				<ClayLayout.Col md={6}>
					<div className="fieldset-title">{legend}</div>
				</ClayLayout.Col>
				<ClayLayout.Col md={6}>
					<div className="fieldset-title">{legend}</div>
				</ClayLayout.Col>
			</ClayLayout.Row>
			{fields.map((field) => {
				const fieldProps = {
					...field,
					id: `${portletNamespace}${field.id}`,
					onChange: (content) => {
						onChange({content, id: field.id});
					},
					targetContent: targetFieldsContent[field.id],
				};

				return field.html ? (
					<TranslateFieldEditor key={field.id} {...fieldProps} />
				) : (
					<TranslateFieldInput key={field.id} {...fieldProps} />
				);
			})}
		</React.Fragment>
	));

export default TranslateFieldSetEntries;

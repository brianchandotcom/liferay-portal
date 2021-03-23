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
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import {ClassicEditor} from 'frontend-editor-ckeditor-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import TranslateLanguagesSelector from './TranslateLanguagesSelector';

const noop = () => {};

const TranslationFieldEditor = ({
	editorConfiguration,
	id,
	label,
	sourceContent,
	sourceContentDir,
	targetContent,
	targetContentDir,
	onChange = noop,
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
							if (value !== data.trim()) {
								setValue(data);
								onChange(data);
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
	onChange = noop,
}) => {
	const [value, setValue] = useState(targetContent);

	return (
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

							setValue(data);
							onChange(data);
						}}
						type="text"
						value={value}
					/>
				</ClayForm.Group>
			</ClayLayout.Col>
		</ClayLayout.Row>
	);
};

const TranslationFieldSetEntries = ({
	infoFieldSetEntries,
	onChange,
	portletNamespace,
}) =>
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
				const fieldProps = {
					...field,
					id: `${portletNamespace}${field.id}`,
					onChange,
				};

				return field.html ? (
					<TranslationFieldEditor key={field.id} {...fieldProps} />
				) : (
					<TranslationFieldInput key={field.id} {...fieldProps} />
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
	aditionalFields,
	infoFieldSetEntries,
	portletNamespace,
	publishButtonDisabled,
	publishButtonLabel,
	redirectURL,
	saveButtonDisabled,
	saveButtonLabel,
	sourceLanguageIdTitle,
	targetLanguageIdTitle,
	translateLanguagesSelectorData,
	translationPermission,
	updateTranslationPortletURL,
	workflowActions,
}) => {
	const [formHaschanges, setFormHaschanges] = useState(false);
	const [workflowAction, setWorkflowAction] = useState(
		workflowActions.PUBLISH
	);

	return (
		<form
			action={updateTranslationPortletURL}
			className="translation-edit"
			method="POST"
			name="translate_fm"
		>
			<input
				defaultValue={workflowAction}
				name={`${portletNamespace}workflowAction`}
				type="hidden"
			/>
			{Object.entries(aditionalFields).map(([name, value]) => (
				<input
					defaultValue={value}
					key={name}
					name={`${portletNamespace}${name}`}
					type="hidden"
				/>
			))}

			<nav className="component-tbar subnav-tbar-light tbar">
				<ClayLayout.ContainerFluid view>
					<ul className="tbar-nav">
						<li className="tbar-item tbar-item-expand">
							<TranslateLanguagesSelector
								{...translateLanguagesSelectorData}
								formHaschanges={formHaschanges}
								portletNamespace={portletNamespace}
							/>
						</li>
						<li className="tbar-item">
							<div className="metadata-type-button-row tbar-section text-right">
								<ClayButton.Group spaced>
									<ClayLink
										button={{small: true}}
										displayType="secondary"
										href={redirectURL}
									>
										{Liferay.Language.get('cancel')}
									</ClayLink>
									<ClayButton
										disabled={saveButtonDisabled}
										displayType="secondary"
										onClick={() => {
											setWorkflowAction(
												workflowActions.SAVE_DRAFT
											);
										}}
										small
										type="submit"
									>
										{saveButtonLabel}
									</ClayButton>
									<ClayButton
										disabled={publishButtonDisabled}
										displayType="primary"
										small
										type="submit"
									>
										{publishButtonLabel}
									</ClayButton>
								</ClayButton.Group>
							</div>
						</li>
					</ul>
				</ClayLayout.ContainerFluid>
			</nav>
			<ClayLayout.ContainerFluid view>
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
								onChange={() => setFormHaschanges(() => true)}
								portletNamespace={portletNamespace}
							/>
						</>
					)}
				</div>
			</ClayLayout.ContainerFluid>
		</form>
	);
};

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
	updateTranslationPortletURL: PropTypes.string.isRequired,
	workflowActions: PropTypes.shape({
		PUBLISH: PropTypes.string.isRequired,
		SAVE_DRAFT: PropTypes.string.isRequired,
	}).isRequired,
};

export default Translate;

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
import ClayLoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';
import {ClassicEditor} from 'frontend-editor-ckeditor-web';
import React from 'react';

import {FETCH_STATUS} from '../constants';

const noop = () => {};

const TranslateAutoTranslateRow = ({
	autoTranslateEnabled,
	children,
	handleAutoTranslateClick = noop,
	label,
	fieldStatus,
	sourceContent,
}) => {
	if (!autoTranslateEnabled) {
		return children;
	}

	const isLoading = fieldStatus.status === FETCH_STATUS.LOADING;
	const text = Liferay.Util.sub(
		Liferay.Language.get('auto-translate-x-field'),
		label
	);

	return (
		<ClayLayout.Row>
			<ClayLayout.ContentCol className="col-autotranslate-content" expand>
				{children}
			</ClayLayout.ContentCol>
			<ClayLayout.ContentCol className="align-self-top col-autotranslate-button">
				<ClayButton
					className="lfr-portal-tooltip"
					disabled={isLoading || !sourceContent}
					displayType="secondary"
					monospaced
					onClick={handleAutoTranslateClick}
					title={text}
				>
					{isLoading ? (
						<ClayLoadingIndicator className="my-0" small />
					) : (
						<ClayIcon symbol="automatic-translate" />
					)}
					<span className="sr-only">{text}</span>
				</ClayButton>
			</ClayLayout.ContentCol>
		</ClayLayout.Row>
	);
};

const TranslateFieldFeedback = ({message = '', status = ''}) => {
	return (
		status === FETCH_STATUS.ERROR ||
		(status === FETCH_STATUS.SUCCESS && (
			<div
				className={classNames({
					'has-error': status === FETCH_STATUS.ERROR,
					'has-success': status === FETCH_STATUS.SUCCESS,
				})}
			>
				<div className="form-feedback-item">
					<span className="form-feedback-indicator mr-1">
						<ClayIcon
							symbol={
								status === FETCH_STATUS.SUCCESS
									? 'check-circle-full'
									: 'exclamation-full'
							}
						/>
					</span>
					{message}
				</div>
			</div>
		))
	);
};

const TranslateFieldEditor = ({
	editorConfiguration,
	fieldStatus,
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
				<TranslateFieldFeedback
					message={fieldStatus.message}
					status={fieldStatus.status}
				/>
			</ClayForm.Group>
		</ClayLayout.Col>
	</ClayLayout.Row>
);

const TranslateFieldInput = ({
	fieldStatus,
	id,
	label,
	multiline,
	onChange = noop,
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
				<ClayLayout.Row>
					<ClayLayout.ContentCol expand>
						<label className="control-label" htmlFor={id}>
							{label}
						</label>
					</ClayLayout.ContentCol>
				</ClayLayout.Row>
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
				<TranslateFieldFeedback
					message={fieldStatus.message}
					status={fieldStatus.status}
				/>
			</ClayForm.Group>
		</ClayLayout.Col>
	</ClayLayout.Row>
);

const TranslateFieldSetEntries = ({
	autoTranslateEnabled,
	fetchAutoTranslateField,
	infoFieldSetEntries,
	onChange,
	portletNamespace,
	targetFieldsContent,
}) =>
	infoFieldSetEntries.map(({fields, legend}) => (
		<React.Fragment key={legend}>
			<ClayLayout.Row
				className={classNames({
					'row-autotranslate-title': autoTranslateEnabled,
				})}
			>
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
					fieldStatus: {
						message: targetFieldsContent[field.id].message,
						status: targetFieldsContent[field.id].status,
					},
					id: `${portletNamespace}${field.id}`,
					onChange: (content) => {
						onChange({content, id: field.id});
					},
					targetContent: targetFieldsContent[field.id].content,
				};

				return (
					<TranslateAutoTranslateRow
						autoTranslateEnabled={autoTranslateEnabled}
						fieldStatus={fieldProps.fieldStatus}
						handleAutoTranslateClick={() =>
							fetchAutoTranslateField(field.id)
						}
						key={field.id}
						label={fieldProps.label}
						sourceContent={fieldProps.sourceContent}
					>
						{field.html ? (
							<TranslateFieldEditor {...fieldProps} />
						) : (
							<TranslateFieldInput {...fieldProps} />
						)}
					</TranslateAutoTranslateRow>
				);
			})}
		</React.Fragment>
	));

export default TranslateFieldSetEntries;

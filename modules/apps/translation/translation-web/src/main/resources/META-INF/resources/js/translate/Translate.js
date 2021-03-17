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
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {ClassicEditor} from 'frontend-editor-ckeditor-web';
import PropTypes from 'prop-types';
import React from 'react';

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



function Translate({
	infoFieldSetEntries,
	sourceLanguageIdTitle,
	targetLanguageIdTitle,
	translationPermission = true,
}) {
	const infoFieldSetEntry = infoFieldSetEntries[0];

	return (
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
						<ClayLayout.Row>
							<ClayLayout.Col md={6}>
								<div className="fieldset-title">
									{infoFieldSetEntry.label}
								</div>
							</ClayLayout.Col>
							<ClayLayout.Col md={6}>
								<div className="fieldset-title">
									{infoFieldSetEntry.label}
								</div>
							</ClayLayout.Col>
						</ClayLayout.Row>
						{infoFieldSetEntry.fields.map(
							({
								editorConfiguration,
								id,
								label,
								sourceContent,
								targetContent,
							}) => (
								<ClayLayout.Row key={label}>
									<ClayLayout.Col md={6}>
										<label className="control-label">
											{label}
										</label>

										<div
											className="translation-editor-preview"
											dir="<%= sourceContentDir %>"
										>
											{sourceContent}
										</div>
									</ClayLayout.Col>
									<ClayLayout.Col md={6}>
										<label className="control-label">
											{label}
										</label>

										<ClassicEditor
											contents={targetContent}
											editorConfig={
												editorConfiguration.editorConfig
											}
											name={id}
										/>
									</ClayLayout.Col>
								</ClayLayout.Row>
							)
						)}
					</>
				)}
			</div>
		</ClayLayout.ContainerFluid>
	);
}

Translate.propTypes = {
	infoFieldSetEntries: PropTypes.arrayOf(
		PropTypes.shape({
			fields: PropTypes.arrayOf(
				PropTypes.shape({
					html: PropTypes.bool,
					id: PropTypes.string.isRequired,
					label: PropTypes.string.isRequired,
					multiline: PropTypes.bool,
					sourceContent: PropTypes.string,
					sourceContentDir: PropTypes.string,
				})
			),
			label: PropTypes.string,
		})
	),
	sourceLanguageIdTitle: PropTypes.string.isRequired,
	targetLanguageIdTitle: PropTypes.string.isRequired,
	translationPermission: PropTypes.bool.isRequired,
};

export default Translate;

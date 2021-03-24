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
import ClayLayout from '@clayui/layout';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import TranslateActionBar from './components/TranslateActionBar';
import TranslateFieldSetEntries from './components/TranslateFieldSetEntries';
import TranslateHeader from './components/TranslateHeader';

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
	const [formHasChanges, setFormHasChanges] = useState(false);
	const [workflowAction, setWorkflowAction] = useState(
		workflowActions.PUBLISH
	);

	const handleOnSaveDraft = () => {
		setWorkflowAction(workflowActions.SAVE_DRAFT);
	};

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

			<TranslateActionBar
				formHasChanges={formHasChanges}
				onSaveButtonClick={handleOnSaveDraft}
				portletNamespace={portletNamespace}
				publishButtonDisabled={publishButtonDisabled}
				publishButtonLabel={publishButtonLabel}
				redirectURL={redirectURL}
				saveButtonDisabled={saveButtonDisabled}
				saveButtonLabel={saveButtonLabel}
				translateLanguagesSelectorData={translateLanguagesSelectorData}
			/>

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
							<TranslateHeader
								sourceLanguageIdTitle={sourceLanguageIdTitle}
								targetLanguageIdTitle={targetLanguageIdTitle}
							/>
							<TranslateFieldSetEntries
								infoFieldSetEntries={infoFieldSetEntries}
								onChange={() => setFormHasChanges(true)}
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

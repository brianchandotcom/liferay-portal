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
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useMemo, useReducer, useState} from 'react';

import TranslateActionBar from './components/TranslateActionBar';
import TranslateFieldSetEntries from './components/TranslateFieldSetEntries';
import TranslateHeader from './components/TranslateHeader';
import {FETCH_STATUS} from './constants';

const ACTION_TYPES = {
	UPDATE_FETCH_STATUS: 'UPDATE_FETCH_STATUS',
	UPDATE_FIELD: 'UPDATE_FIELD',
	UPDATE_FIELDS_BULK: 'UPDATE_FIELDS_BULK',
};

const getInfoFields = (infoFieldSetEntries = []) => {
	const sourceFields = {};
	const targetFields = {};

	infoFieldSetEntries.forEach(({fields}) => {
		fields.forEach(({id, sourceContent, targetContent}) => {
			sourceFields[id] = sourceContent;

			targetFields[id] = {
				content: targetContent,
				message: '',
				status: '',
			};
		});
	});

	return {
		sourceFields,
		targetFields,
	};
};

const reducer = (state, action) => {
	switch (action.type) {
		case ACTION_TYPES.UPDATE_FIELD:
			return {
				...state,
				fields: {
					...state.fields,
					[action.payload.id]: {
						...state.fields[action.payload.id],
						...action.payload.field,
					},
				},
				formHasChanges: true,
			};
		case ACTION_TYPES.UPDATE_FIELDS_BULK:
			return {
				...state,
				fields: action.payload,
				formHasChanges: true,
			};
		case ACTION_TYPES.UPDATE_FETCH_STATUS:
			return {...state, fetchAutoTranslateStatus: action.payload};
		default:
			return state;
	}
};

const Translate = ({
	aditionalFields,
	autoTranslateEnabled = false,
	getAutoTranslateURL,
	infoFieldSetEntries,
	portletNamespace,
	publishButtonDisabled,
	publishButtonLabel,
	redirectURL,
	saveButtonDisabled,
	saveButtonLabel,
	sourceLanguageId,
	sourceLanguageIdTitle,
	targetLanguageId,
	targetLanguageIdTitle,
	translateLanguagesSelectorData,
	translationPermission,
	updateTranslationPortletURL,
	workflowActions,
}) => {
	const isMounted = useIsMounted();

	const [workflowAction, setWorkflowAction] = useState(
		workflowActions.PUBLISH
	);

	const {sourceFields, targetFields} = useMemo(
		() => getInfoFields(infoFieldSetEntries),
		[infoFieldSetEntries]
	);

	const [state, dispatch] = useReducer(reducer, {
		fetchAutoTranslateStatus: {
			message: '',
			status: '',
		},
		fields: targetFields,
		formHasChanges: false,
	});

	const handleOnSaveDraft = () => {
		setWorkflowAction(workflowActions.SAVE_DRAFT);
	};

	const handleOnChangeField = ({content, id}) => {
		dispatch({
			payload: {field: {content}, id},
			type: ACTION_TYPES.UPDATE_FIELD,
		});
	};

	const fetchAutoTranslation = ({fields}) =>
		fetch(getAutoTranslateURL, {
			body: JSON.stringify({
				fields,
				sourceLanguageId,
				targetLanguageId,
			}),
			method: 'POST',
		}).then((response) => response.json());

	const fetchAutoTranslateFieldsBulk = () => {
		dispatch({
			payload: {
				status: FETCH_STATUS.LOADING,
			},
			type: ACTION_TYPES.UPDATE_FETCH_STATUS,
		});

		fetchAutoTranslation({fields: sourceFields})
			.then(({error, fields}) => {
				if (error) {
					throw error;
				}

				if (isMounted()) {
					dispatch({
						payload: Object.entries(fields).reduce(
							(acc, [id, content]) => {
								acc[id] = {content};

								return acc;
							},
							{}
						),
						type: ACTION_TYPES.UPDATE_FIELDS_BULK,
					});

					dispatch({
						payload: {
							message: Liferay.Language.get(
								'successfully-received-translations'
							),
							status: FETCH_STATUS.SUCCESS,
						},
						type: ACTION_TYPES.UPDATE_FETCH_STATUS,
					});
				}
			})
			.catch(
				({
					message = Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
				}) => {
					if (isMounted()) {
						dispatch({
							payload: {
								message,
								status: FETCH_STATUS.ERROR,
							},
							type: ACTION_TYPES.UPDATE_FETCH_STATUS,
						});
					}
				}
			);
	};

	const fetchAutoTranslateField = (fieldId) => {
		dispatch({
			payload: {field: {status: FETCH_STATUS.LOADING}, id: fieldId},
			type: ACTION_TYPES.UPDATE_FIELD,
		});

		fetchAutoTranslation({
			fields: {[fieldId]: sourceFields[fieldId]},
		})
			.then(({error, fields}) => {
				if (error) {
					throw error;
				}

				if (isMounted()) {
					dispatch({
						payload: {
							field: {
								content: fields[fieldId],
								message: Liferay.Language.get(
									'field-translated'
								),
								status: FETCH_STATUS.SUCCESS,
							},
							id: fieldId,
						},
						type: ACTION_TYPES.UPDATE_FIELD,
					});
				}
			})
			.catch(
				({
					message = Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
				}) => {
					if (isMounted()) {
						dispatch({
							payload: {
								field: {
									message,
									status: FETCH_STATUS.ERROR,
								},
								id: fieldId,
							},
							type: ACTION_TYPES.UPDATE_FIELD,
						});
					}
				}
			);
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
				autoTranslateEnabled={autoTranslateEnabled}
				fetchAutoTranslateFields={fetchAutoTranslateFieldsBulk}
				fetchAutoTranslateStatus={state.fetchAutoTranslateStatus}
				formHasChanges={state.formHasChanges}
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
								autoTranslateEnabled={autoTranslateEnabled}
								fetchAutoTranslateField={
									fetchAutoTranslateField
								}
								infoFieldSetEntries={infoFieldSetEntries}
								onChange={handleOnChangeField}
								portletNamespace={portletNamespace}
								targetFieldsContent={state.fields}
							/>
						</>
					)}
				</div>
			</ClayLayout.ContainerFluid>
		</form>
	);
};

Translate.propTypes = {
	autoTranslateEnabled: PropTypes.bool,
	getAutoTranslateURL: PropTypes.string.isRequired,
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
	sourceLanguageId: PropTypes.string.isRequired,
	sourceLanguageIdTitle: PropTypes.string.isRequired,
	targetLanguageId: PropTypes.string.isRequired,
	targetLanguageIdTitle: PropTypes.string.isRequired,
	translationPermission: PropTypes.bool.isRequired,
	updateTranslationPortletURL: PropTypes.string.isRequired,
	workflowActions: PropTypes.shape({
		PUBLISH: PropTypes.string.isRequired,
		SAVE_DRAFT: PropTypes.string.isRequired,
	}).isRequired,
};

export default Translate;

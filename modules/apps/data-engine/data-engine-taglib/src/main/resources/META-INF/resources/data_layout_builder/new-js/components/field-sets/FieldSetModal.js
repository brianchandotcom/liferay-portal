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
import {ClayInput} from '@clayui/form';
import ClayModal, {ClayModalProvider, useModal} from '@clayui/modal';
import {
	ConfigProvider,
	EVENT_TYPES as EVENT_TYPES_CORE,
	FormProvider,
	INITIAL_STATE,
	useConfig,
	useForm,
	useFormState,
} from 'dynamic-data-mapping-form-renderer';
import {
	dragAndDropReducer,
	fieldEditableReducer,
	languageReducer,
	pagesStructureReducer,
} from 'dynamic-data-mapping-form-renderer/js/core/reducers/index.es';
import {pageReducer} from 'dynamic-data-mapping-form-renderer/js/custom/form/reducers/index.es';
import {default as React, useCallback, useState} from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import getFieldsWithoutOptions from '../../../js/components/field-sets/actions/getFieldsWithoutOptions.es';
import TranslationManager from '../../../js/components/translation-manager/TranslationManager.es';
import {addItem} from '../../../js/utils/client.es';
import {isDataLayoutEmpty} from '../../../js/utils/dataLayoutVisitor.es';
import {normalizeDataDefinition} from '../../../js/utils/normalizers.es';
import {errorToast, successToast} from '../../../js/utils/toast.es';
import {FormBuilder} from '../../FormBuilder';
import {EVENT_TYPES} from '../../eventTypes';
import sidebarReducer from '../../reducers/sidebarReducer';

const DEFAULT_DATA_DEFINITION = {
	dataDefinitionFields: [],
};

const DEFAULT_DATA_LAYOUT = {
	dataLayoutFields: {},
	dataLayoutPages: [],
	dataRules: [],
	paginationMode: 'single-page',
};

const createFieldSet = async ({
	allowInvalidAvailableLocalesForProperty,
	contentType,
	dataDefinition: {availableLanguageIds, dataDefinitionFields},
	dataLayout: {dataLayoutPages},
	defaultLanguageId,
	name,
}) => {
	const dataDefinition = {
		availableLanguageIds,
		dataDefinitionFields,
		name,
	};

	const fieldSet = {
		...(allowInvalidAvailableLocalesForProperty
			? dataDefinition
			: normalizeDataDefinition(dataDefinition, defaultLanguageId)),
		defaultDataLayout: {
			dataLayoutPages,
			name,
		},
		defaultLanguageId,
	};

	const fieldsWithoutOptions = getFieldsWithoutOptions(
		dataDefinition.dataDefinitionFields,
		defaultLanguageId
	);

	if (fieldsWithoutOptions.length) {
		throw new Error(
			Liferay.Util.sub(
				Liferay.Language.get(
					'at-least-one-option-should-be-set-for-field-x'
				),
				fieldsWithoutOptions[0].label[defaultLanguageId]
			)
		);
	}

	return await addItem(
		`/o/data-engine/v2.0/data-definitions/by-content-type/${contentType}`,
		fieldSet
	);
};

const ModalContent = ({onClose}) => {
	const dispatch = useForm();
	const {contentType, dataDefinitionId} = useConfig();
	const {
		allowInvalidAvailableLocalesForProperty,
		defaultLanguageId,
		editingLanguageId,
	} = useFormState();
	const {dataDefinition, dataLayout} = useFormState({
		schema: ['dataDefinition', 'dataLayout'],
	});

	const [name, setName] = useState({});
	const changeEditingLanguageId = (editingLanguageId) =>
		dispatch({
			payload: {editingLanguageId},
			type: EVENT_TYPES_CORE.LANGUAGE.CHANGE,
		});

	const onSave = async () => {
		try {
			if (!name[defaultLanguageId]) {
				changeEditingLanguageId(defaultLanguageId);
				throw new Error(
					Liferay.Language.get('please-enter-a-valid-title')
				);
			}

			const dataDefinitionFieldSet = await createFieldSet({
				allowInvalidAvailableLocalesForProperty,
				contentType,
				dataDefinition: dataDefinition.build(),
				dataLayout: dataLayout.build(),
				defaultLanguageId,
				name,
			});

			successToast(Liferay.Language.get('fieldset-saved'));

			onClose(dataDefinitionFieldSet);
		}
		catch ({message}) {
			errorToast(message);
		}
	};

	const isFieldSetInvalid = () =>
		Object.keys(name).length == 0 ||
		!dataLayout?.dataLayoutPages ||
		isDataLayoutEmpty(dataLayout.dataLayoutPages);

	return (
		<>
			<ClayModal.Header>
				{dataDefinitionId
					? Liferay.Language.get('edit-fieldset')
					: Liferay.Language.get('create-new-fieldset')}
			</ClayModal.Header>
			<ClayModal.Header withTitle={false}>
				<TranslationManager
					defaultLanguageId={defaultLanguageId}
					editingLanguageId={editingLanguageId}
					onEditingLanguageIdChange={changeEditingLanguageId}
					translatedLanguageIds={name}
				/>
				<ClayInput.Group className="pl-4 pr-4">
					<ClayInput.GroupItem>
						<ClayInput
							aria-label={Liferay.Language.get(
								'untitled-fieldset'
							)}
							autoFocus
							className="form-control-inline"
							dir={
								Liferay.Language.direction[editingLanguageId] ||
								'ltr'
							}
							onChange={({target: {value}}) =>
								setName((name) => ({
									...name,
									[editingLanguageId]: value,
								}))
							}
							placeholder={Liferay.Language.get(
								'untitled-fieldset'
							)}
							type="text"
							value={
								name[editingLanguageId] ??
								name[defaultLanguageId] ??
								''
							}
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayModal.Header>
			<ClayModal.Body>
				<div className="pl-4 pr-4">
					<ClayModalProvider>
						<DndProvider backend={HTML5Backend}>
							<FormBuilder />
						</DndProvider>
					</ClayModalProvider>
				</div>
			</ClayModal.Body>
			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>
						<ClayButton
							disabled={isFieldSetInvalid()}
							onClick={onSave}
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
};

export default ({isVisible, onClose: onCloseFn}) => {
	const {observer, onClose} = useModal({onClose: onCloseFn});
	const config = useConfig();
	const {defaultLanguageId, fieldSets, sidebarPanels} = useFormState();
	const dispatch = useForm();

	const closeAndUpdateFieldSetList = useCallback(
		(fieldSet) => {
			if (fieldSet) {
				dispatch({
					payload: {
						fieldSets: [...fieldSets, fieldSet],
					},
					type: EVENT_TYPES.FIELD_SET.UPDATE,
				});
			}
			onClose();
		},
		[dispatch, fieldSets, onClose]
	);

	return (
		isVisible && (
			<ClayModal
				className="data-layout-builder-editor-modal fieldset-modal"
				observer={observer}
				size="full-screen"
			>
				<ConfigProvider
					config={{
						...config,
						allowFieldSets: false,
						dataDefinitionId: null,
					}}
				>
					<FormProvider
						initialState={INITIAL_STATE}
						reducers={[
							dragAndDropReducer,
							fieldEditableReducer,
							languageReducer,
							pageReducer,
							pagesStructureReducer,
							sidebarReducer,
						]}
						value={{
							availableLanguageIds: [defaultLanguageId],
							dataDefinition: DEFAULT_DATA_DEFINITION,
							dataLayout: DEFAULT_DATA_LAYOUT,
							editingLanguageId: defaultLanguageId,
							sidebarPanels,
						}}
					>
						<ModalContent onClose={closeAndUpdateFieldSetList} />
					</FormProvider>
				</ConfigProvider>
			</ClayModal>
		)
	);
};

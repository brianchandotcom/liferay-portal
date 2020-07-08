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
import ClayModal, {useModal} from '@clayui/modal';
import React, {useCallback, useContext, useEffect, useState} from 'react';

import App from '../../App.es';
import AppContext from '../../AppContext.es';
import {UPDATE_EDITING_LANGUAGE_ID} from '../../actions.es';
import {isDataLayoutEmpty} from '../../utils/dataLayoutVisitor.es';
import ModalWithEventPrevented from '../modal/ModalWithEventPrevented.es';
import TranslationManager from '../translation-manager/TranslationManager.es';
import useCreateFieldSet from './actions/useCreateFieldSet.es';
import usePropagateFieldSet from './actions/usePropagateFieldSet.es';
import useSaveFieldSet from './actions/useSaveFieldSet.es';

const ModalContent = ({
	childrenAppProps,
	defaultLanguageId,
	fieldSet,
	onClose,
}) => {
	const [{appProps}] = useContext(AppContext);
	const [childrenContext, setChildrenContext] = useState({
		dispatch: () => {},
		state: {},
	});
	const [dataLayoutIsEmpty, setDataLayoutIsEmpty] = useState(true);
	const {editingLanguageId = defaultLanguageId} = childrenContext.state;
	const [name, setName] = useState({});

	const availableLanguageIds = [
		...new Set([...Object.keys(name), editingLanguageId]),
	];

	useEffect(() => {
		if (fieldSet) {
			setName(fieldSet.name);
		}
	}, [defaultLanguageId, fieldSet]);

	useEffect(() => {
		if (childrenContext.state.dataLayout) {
			const {
				dataLayout: {dataLayoutPages},
			} = childrenContext.state;
			setDataLayoutIsEmpty(isDataLayoutEmpty(dataLayoutPages));
		}
	}, [childrenContext]);

	const createFieldSet = useCreateFieldSet({
		availableLanguageIds,
		childrenContext,
	});
	const saveFieldSet = useSaveFieldSet({
		DataLayout: childrenAppProps.DataLayout,
		availableLanguageIds,
		childrenContext,
		defaultLanguageId,
		fieldSet,
	});
	const propagateFieldSet = usePropagateFieldSet();

	const onEditingLanguageIdChange = useCallback(
		(editingLanguageId) => {
			childrenContext.dispatch({
				payload: editingLanguageId,
				type: UPDATE_EDITING_LANGUAGE_ID,
			});
		},
		[childrenContext]
	);

	/**
	 * This functions is necessary to handle with ddm-container,
	 * just a trick to simply show/hide container. Actually the z-index of container
	 * is biggest than the Modal and DropDown, that's why is necessary to handle
	 * with Javascript interaction
	 * @param {Boolean} active DropDown Visible of TranslationManager
	 */

	const onActiveChange = (active) => {
		document
			.querySelectorAll('#ddm-actionable-fields-container')
			.forEach((container) => {
				if (container) {
					container.style.display = active ? 'none' : '';
				}
			});
	};

	const onSave = () => {
		if (fieldSet) {
			propagateFieldSet({
				fieldSet,
				modal: {
					actionMessage: Liferay.Language.get('propagate'),
					fieldSetMessage: Liferay.Language.get(
						'do-you-want-to-propagate-the-changes-to-other-objects-views-using-this-fieldset'
					),
					headerMessage: Liferay.Language.get('propagate-changes'),
					warningMessage: Liferay.Language.get(
						'the-changes-include-the-deletion-of-fields-and-may-erase-the-data-collected-permanently'
					),
				},
				onPropagate: () => saveFieldSet(name),
			}).finally(onClose);
		}
		else {
			createFieldSet(name).finally(onClose);
		}
	};

	return (
		<>
			<ClayModal.Header>
				{fieldSet
					? Liferay.Language.get('edit-fieldset')
					: Liferay.Language.get('create-new-fieldset')}
			</ClayModal.Header>
			<ClayModal.Header withTitle={false}>
				<ClayInput.Group className="pl-4 pr-4">
					<TranslationManager
						defaultLanguageId={defaultLanguageId}
						editingLanguageId={editingLanguageId}
						onActiveChange={onActiveChange}
						onEditingLanguageIdChange={onEditingLanguageIdChange}
						translatedLanguageIds={name}
					/>
					<ClayInput.GroupItem>
						<ClayInput
							aria-label={Liferay.Language.get(
								'untitled-fieldset'
							)}
							autoFocus
							className="form-control-inline"
							onChange={({target: {value}}) =>
								setName({...name, [editingLanguageId]: value})
							}
							placeholder={Liferay.Language.get(
								'untitled-fieldset'
							)}
							type="text"
							value={name[editingLanguageId]}
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayModal.Header>
			<ClayModal.Body>
				<div className="pl-4 pr-4">
					<App
						{...appProps}
						availableLanguageIds={availableLanguageIds}
						dataLayoutBuilderId={`${appProps.dataLayoutBuilderId}_2`}
						editingLanguageId={editingLanguageId}
						setChildrenContext={setChildrenContext}
						{...childrenAppProps}
					/>
				</div>
			</ClayModal.Body>
			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>
						<ClayButton
							disabled={!name || dataLayoutIsEmpty}
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

const FieldSetModal = ({isVisible, onClose, ...props}) => {
	const {observer} = useModal({
		onClose,
	});

	if (!isVisible) {
		return null;
	}

	return (
		<ClayModal
			className="data-layout-builder-editor-modal fieldset-modal"
			observer={observer}
			size="full-screen"
		>
			<ModalContent onClose={onClose} {...props} />
		</ClayModal>
	);
};

export default (props) => (
	<ModalWithEventPrevented>
		<FieldSetModal {...props} />
	</ModalWithEventPrevented>
);

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
import React, {useContext, useEffect, useState} from 'react';

import App from '../../App.es';
import AppContext from '../../AppContext.es';
import {
	UPDATE_CONFIG,
	UPDATE_EDITING_DATA_DEFINITION_ID,
} from '../../actions.es';
import {
	containsField,
	isDataLayoutEmpty,
} from '../../utils/dataLayoutVisitor.es';
import ModalWithEventPrevented from '../modal/ModalWithEventPrevented.es';
import useCreateFieldSet from './actions/useCreateFieldSet.es';
import usePropagateFieldSet from './actions/usePropagateFieldSet.es';
import useSaveFieldSet from './actions/useSaveFieldSet.es';

const ModalContent = ({
	childrenAppProps,
	defaultLanguageId,
	editingDataDefinition,
	fieldSet,
	onClose,
}) => {
	const [{appProps}] = useContext(AppContext);
	const [childrenContext, setChildrenContext] = useState({
		dataLayoutBuilder: null,
		dispatch: () => {},
		state: {},
	});
	const [dataLayoutIsEmpty, setDataLayoutIsEmpty] = useState(true);
	const [name, setName] = useState({});
	const {
		dataLayoutBuilder,
		dispatch,
		state: {config, dataLayout},
	} = childrenContext;

	const {contentType} = appProps;

	const availableLanguageIds = [
		...new Set([...Object.keys(name), defaultLanguageId]),
	];

	const changeZIndex = (zIndex) => {
		document
			.querySelectorAll('.ddm-field-actions-container')
			.forEach((container) => {
				container.style.zIndex = zIndex;
			});
	};

	useEffect(() => {
		if (fieldSet) {
			setName(fieldSet.name);
		}
	}, [fieldSet]);

	useEffect(() => {
		if (contentType === 'app-builder') {
			dispatch({
				payload: {
					config: {
						...config,
						allowFieldSets: false,
					},
				},
				type: UPDATE_CONFIG,
			});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [contentType, dispatch]);

	useEffect(() => {
		if (dataLayout) {
			const {dataLayoutPages} = dataLayout;
			setDataLayoutIsEmpty(isDataLayoutEmpty(dataLayoutPages));
		}
	}, [dataLayout]);

	useEffect(() => {
		if (dataLayoutBuilder) {
			changeZIndex('1050');
		}

		return () => {
			changeZIndex(null);
		};
	}, [dataLayoutBuilder]);

	useEffect(() => {
		if (editingDataDefinition && editingDataDefinition.id) {
			dispatch({
				payload: {
					editingDataDefinitionId: editingDataDefinition.id,
				},
				type: UPDATE_EDITING_DATA_DEFINITION_ID,
			});
		}
	}, [dispatch, editingDataDefinition]);

	const actionProps = {
		availableLanguageIds,
		childrenContext,
		editingDataDefinition,
		fieldSet,
	};

	const createFieldSet = useCreateFieldSet(actionProps);
	const saveFieldSet = useSaveFieldSet(actionProps);
	const propagateFieldSet = usePropagateFieldSet();

	const onSave = () => {
		const hasRemovedField = () => {
			const fieldNames = fieldSet.dataDefinitionFields.map(
				({name}) => name
			);

			const [prevLayoutFields, actualLayoutFields] = [
				fieldSet.defaultDataLayout.dataLayoutPages,
				dataLayout.dataLayoutPages,
			].map((layout) =>
				fieldNames.filter((field) => containsField(layout, field))
			);

			return !!prevLayoutFields.filter(
				(field) => !actualLayoutFields.includes(field)
			).length;
		};

		if (fieldSet) {
			propagateFieldSet({
				fieldSet,
				modal: {
					actionMessage: Liferay.Language.get('propagate'),
					fieldSetMessage: Liferay.Language.get(
						'do-you-want-to-propagate-the-changes-to-other-objects-views-using-this-fieldset'
					),
					headerMessage: Liferay.Language.get('propagate-changes'),
					...(hasRemovedField() && {
						warningMessage: Liferay.Language.get(
							'the-changes-include-the-deletion-of-fields-and-may-erase-the-data-collected-permanently'
						),
					}),
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
					<ClayInput.GroupItem>
						<ClayInput
							aria-label={Liferay.Language.get(
								'untitled-fieldset'
							)}
							autoFocus
							className="form-control-inline"
							onChange={({target: {value}}) =>
								setName({...name, [defaultLanguageId]: value})
							}
							placeholder={Liferay.Language.get(
								'untitled-fieldset'
							)}
							type="text"
							value={name[defaultLanguageId]}
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayModal.Header>
			<ClayModal.Body>
				<div className="pl-4 pr-4">
					<App
						{...appProps}
						dataLayoutBuilderId={`${appProps.dataLayoutBuilderId}_2`}
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
							disabled={
								!name[defaultLanguageId] || dataLayoutIsEmpty
							}
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

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
import {UPDATE_FIELDSETS} from '../../actions.es';

export default ({fieldSet, isVisible, onClose, otherProps}) => {
	const [{appProps, fieldSets}, dispatch] = useContext(AppContext);
	const [fieldSetName, setFieldSetName] = useState('');
	const [
		{dispatch: childrenDispatch, state: childrenContext},
		setChildrenContext,
	] = useState({
		dispatch: () => {},
		state: {},
	});

	const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();
	const childrenStringify = JSON.stringify(childrenContext);

	const {observer} = useModal({
		onClose,
	});

	const getFieldSetName = useCallback(({name}) => name[defaultLanguageId], [
		defaultLanguageId,
	]);

	useEffect(() => {
		if (fieldSet) {
			setFieldSetName(getFieldSetName(fieldSet));
		}
	}, [fieldSet, getFieldSetName]);

	useEffect(() => {
		let {fieldSets = []} = JSON.parse(childrenStringify);

		if (fieldSets.length) {
			fieldSets = fieldSets.map((fieldset) => {
				if (fieldset.name[defaultLanguageId] === fieldSetName) {
					fieldset.disabled = true;
				}

				return fieldset;
			});
			childrenDispatch({payload: {fieldSets}, type: UPDATE_FIELDSETS});
		}
	}, [childrenDispatch, childrenStringify, defaultLanguageId, fieldSetName]);

	if (!isVisible) {
		return null;
	}

	const saveAsFieldset = () => {
		let updatedFieldSets;
		if (fieldSet) {
			const {
				dataDefinition: {dataDefinitionFields},
			} = childrenContext;

			updatedFieldSets = fieldSets.map((field) => {
				if (fieldSetName === getFieldSetName(field)) {
					return {
						...field,
						dataDefinitionFields,
					};
				}

				return field;
			});

			dispatch({
				payload: {
					fieldSets: updatedFieldSets,
				},
				type: UPDATE_FIELDSETS,
			});
		}

		onClose();
	};

	return (
		<ClayModal
			className="data-layout-builder-editor-modal fieldset-modal"
			observer={observer}
			size="full-screen"
		>
			<ClayModal.Header>
				{fieldSet
					? Liferay.Language.get('edit-fieldset')
					: Liferay.Language.get('add-fieldset')}
			</ClayModal.Header>
			<ClayModal.Header withTitle={false}>
				<ClayInput.Group className="pl-4 pr-4">
					<ClayInput.GroupItem>
						<ClayInput
							aria-label={Liferay.Language.get(
								'untitled-fieldset'
							)}
							className="form-control-inline"
							onChange={({target: {value}}) =>
								setFieldSetName(value)
							}
							placeholder={Liferay.Language.get(
								'untitled-fieldset'
							)}
							type="text"
							value={fieldSetName}
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayModal.Header>
			<ClayModal.Body>
				<div className="pl-4 pr-4">
					<App
						{...appProps}
						dataLayoutBuilderId={`${appProps.dataLayoutBuilderId}_2`}
						defaultLanguageId={defaultLanguageId}
						setChildrenContext={setChildrenContext}
						{...otherProps}
					/>
				</div>
			</ClayModal.Body>
			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>
						<ClayButton onClick={saveAsFieldset}>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
};

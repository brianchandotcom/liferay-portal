/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {TreeView} from '@clayui/core';
import {ClayCheckbox} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {FDSViewType} from '../../../FDSViews';
import {getFields} from '../../../api';
import {IField} from '../../../types';
import openDefaultFailureToast from '../../../utils/openDefaultFailureToast';
import openDefaultSuccessToast from '../../../utils/openDefaultSuccessToast';
import {IFDSField} from '../Fields';

interface IFieldTreeItem extends IField {
	children?: IFieldTreeItem[];
	savedId?: number;
	selected?: boolean;
}

function visit(fields: Array<IFieldTreeItem>, callback: Function) {
	fields.forEach((field) => {
		callback(field);

		if (field.children) {
			visit(field.children, callback);
		}
	});
}

const applySavedFDSFields = ({
	fields: initialFields,
	savedFDSFields,
}: {
	fields: IField[];
	savedFDSFields: Array<IFDSField>;
}): [Set<React.Key>, Array<IFieldTreeItem>] => {
	const selectedKeys = new Set<React.Key>();
	const fields: IFieldTreeItem[] = Array.from(initialFields);

	visit(fields, (field: IFieldTreeItem) => {
		const savedFDSField = savedFDSFields.find(
			(savedFDSField) => savedFDSField.name === field.name
		);

		if (savedFDSField) {
			selectedKeys.add(savedFDSField.name);

			field.savedId = savedFDSField.id;
		}

		field.id = field.name;
	});

	return [selectedKeys, fields];
};

const AddFieldsModalContent = ({
	closeModal,
	fdsView,
	namespace,
	onSave,
	saveFDSFieldsURL,
	savedFDSFields,
}: {
	closeModal: Function;
	fdsView: FDSViewType;
	namespace: string;
	onSave: ({
		createdFDSFields,
		deletedFDSFieldsIds,
	}: {
		createdFDSFields: Array<IFDSField>;
		deletedFDSFieldsIds: Array<number>;
	}) => void;
	saveFDSFieldsURL: string;
	savedFDSFields: Array<IFDSField>;
}) => {
	const [fields, setFields] = useState<Array<IFieldTreeItem> | null>(null);
	const [saveButtonDisabled, setSaveButtonDisabled] = useState(false);
	const [selectedKeys, setSelectedKeys] = useState<Set<React.Key>>(
		new Set<React.Key>()
	);

	const saveFDSFields = async () => {
		setSaveButtonDisabled(true);

		const creationData: Array<{name: string; type: string}> = [];
		const deletionIds: Array<number> = [];

		visit(fields || [], (field: IFieldTreeItem) => {
			if (selectedKeys.has(field.name) && !field.savedId) {
				creationData.push({name: field.name, type: field.type});
			}

			if (field.savedId && !selectedKeys.has(field.name)) {
				deletionIds.push(field.savedId);
			}
		});

		const formData = new FormData();

		formData.append(
			`${namespace}creationData`,
			JSON.stringify(creationData)
		);

		deletionIds.forEach((id) => {
			formData.append(`${namespace}deletionIds`, String(id));
		});

		formData.append(`${namespace}fdsViewId`, fdsView.id);

		const response = await fetch(saveFDSFieldsURL, {
			body: formData,
			method: 'POST',
		});

		if (!response.ok) {
			openDefaultFailureToast();

			setSaveButtonDisabled(false);

			return;
		}

		const createdFDSFields: Array<IFDSField> = await response.json();

		closeModal();

		openDefaultSuccessToast();

		onSave({
			createdFDSFields: createdFDSFields.map((fdsField) => ({
				...fdsField,
				id: Number(fdsField.id),
			})),
			deletedFDSFieldsIds: deletionIds,
		});
	};

	useEffect(() => {
		getFields(fdsView).then((fields) => {
			if (fields) {
				const [
					initialSelectedKeys,
					updatedFields,
				] = applySavedFDSFields({
					fields,
					savedFDSFields,
				});

				setSelectedKeys(initialSelectedKeys);

				setFields(updatedFields);
			}
		});
	}, [savedFDSFields, fdsView]);

	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('add-fields')}
			</ClayModal.Header>

			<ClayModal.Body className="bg-light m-4 p-0">
				{fields === null ? (
					<ClayLoadingIndicator />
				) : (
					<div className="pb-2 pt-2">
						<TreeView
							defaultItems={fields}
							nestedKey="children"
							onSelectionChange={setSelectedKeys}
							selectedKeys={selectedKeys}
							selectionMode="multiple"
						>
							{({children, label}: IFieldTreeItem) => (
								<TreeView.Item>
									<TreeView.ItemStack>
										<ClayCheckbox checked label={label} />
									</TreeView.ItemStack>

									<TreeView.Group items={children}>
										{({label}: IFieldTreeItem) => (
											<TreeView.Item>
												<ClayCheckbox
													checked
													label={label}
												/>
											</TreeView.Item>
										)}
									</TreeView.Group>
								</TreeView.Item>
							)}
						</TreeView>
					</div>
				)}
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							disabled={saveButtonDisabled}
							onClick={saveFDSFields}
						>
							{Liferay.Language.get('save')}
						</ClayButton>

						<ClayButton
							displayType="secondary"
							onClick={() => closeModal()}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
};

export default AddFieldsModalContent;

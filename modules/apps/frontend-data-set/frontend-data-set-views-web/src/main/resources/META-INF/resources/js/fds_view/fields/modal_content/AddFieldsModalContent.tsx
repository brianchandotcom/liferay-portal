/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {TreeView} from '@clayui/core';
import {ClayCheckbox} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayManagementToolbar, {
	ClayResultsBar,
} from '@clayui/management-toolbar';
import ClayModal from '@clayui/modal';
import {fetch, sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {FDSViewType} from '../../../FDSViews';
import {getFields} from '../../../api';
import Search from '../../../components/Search';
import {IField} from '../../../types';
import openDefaultFailureToast from '../../../utils/openDefaultFailureToast';
import openDefaultSuccessToast from '../../../utils/openDefaultSuccessToast';
import {IFDSField} from '../Fields';

interface IFieldTreeItem extends IField {
	children?: IFieldTreeItem[];
	query?: string;
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

function filterFields({
	fields,
	onFilter,
	onMatch,
	query,
}: {
	fields: Array<IFieldTreeItem>;
	onFilter?: (field: IFieldTreeItem) => void;
	onMatch?: (field: IFieldTreeItem) => void;
	query: string;
}) {
	const filteredItems: Array<IFieldTreeItem> = [];
	const regexp = new RegExp(query, 'i');

	fields.forEach((field) => {
		const match = field.label ? regexp.test(field.label) : false;

		const filteredChildren = field.children?.length
			? filterFields({fields: field.children, onFilter, onMatch, query})
			: [];

		if (match || (field.children?.length && filteredChildren.length)) {
			filteredItems.push({
				...field,
				children: filteredChildren,
				query,
			});

			if (onFilter) {
				onFilter(field);
			}
		}

		if (match && onMatch) {
			onMatch(field);
		}
	});

	return filteredItems;
}

function applyFilter({
	fields,
	query,
}: {fields?: Array<IFieldTreeItem>; query?: string} = {}) {
	if (!query || !fields) {
		return {
			counter: 0,
			filteredItems: fields ?? [],
			filteredKeys: [],
		};
	}

	let counter = 0;
	const filteredKeys: Array<React.Key> = [];
	const filteredItems = filterFields({
		fields,
		onFilter: ({id}: IFieldTreeItem) => {
			if (id) {
				filteredKeys.push(id);
			}
		},
		onMatch: () => counter++,
		query,
	});

	return {
		counter,
		filteredItems,
		filteredKeys,
	};
}

const Highlight = ({query, text}: {query?: string; text?: string}) => {
	if (!query || !text) {
		return <>{text ?? ''}</>;
	}

	const indexMatch = text.search(RegExp(query, 'i'));

	return indexMatch > -1 ? (
		<>
			{text.substring(0, indexMatch)}
			<mark className="bg-transparent border-0 font-weight-bold p-0 shadow-none">
				{text.substring(indexMatch, indexMatch + query.length)}
			</mark>
			{text.substring(indexMatch + query.length)}
		</>
	) : (
		<>{text}</>
	);
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
	const [initialFields, setInitialFields] = useState<Array<
		IFieldTreeItem
	> | null>(null);
	const [saveButtonDisabled, setSaveButtonDisabled] = useState(false);
	const [selectedKeys, setSelectedKeys] = useState<Set<React.Key>>(
		new Set<React.Key>()
	);
	const [fields, setFields] = useState<Array<IFieldTreeItem> | null>(
		initialFields
	);
	const [query, setQuery] = useState<string>('');
	const [expandedKeys, setExpandedKeys] = useState<Array<React.Key>>([]);
	const [searchCounter, setSearchCounter] = useState<number>(0);

	const saveFDSFields = async () => {
		setSaveButtonDisabled(true);

		const creationData: Array<{name: string; type: string}> = [];
		const deletionIds: Array<number> = [];

		visit(initialFields || [], (field: IFieldTreeItem) => {
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

				setInitialFields(updatedFields);
				setFields(updatedFields);
			}
		});
	}, [savedFDSFields, fdsView]);

	const onSearch = (query: string) => {
		setQuery(query);

		const {counter, filteredItems, filteredKeys} = applyFilter({
			fields: initialFields ?? [],
			query,
		});

		setFields(filteredItems);
		setExpandedKeys(filteredKeys);
		setSearchCounter(counter);
	};

	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('add-fields')}
			</ClayModal.Header>

			<ClayModal.Body className="pt-0 px-0">
				{fields === null ? (
					<ClayLoadingIndicator />
				) : (
					<>
						<ClayManagementToolbar>
							<ClayManagementToolbar.Search>
								<Search onSearch={onSearch} query={query} />
							</ClayManagementToolbar.Search>
						</ClayManagementToolbar>

						{query && (
							<ClayResultsBar>
								<ClayResultsBar.Item expand>
									<span className="component-text text-truncate-inline">
										<span className="text-truncate">
											{sub(
												searchCounter === 1
													? Liferay.Language.get(
															'x-result-for-x'
													  )
													: Liferay.Language.get(
															'x-results-for-x'
													  ),
												searchCounter,
												query
											)}
										</span>
									</span>
								</ClayResultsBar.Item>

								<ClayResultsBar.Item>
									<ClayButton
										className="component-link tbar-link"
										displayType="unstyled"
										onClick={() => {
											setQuery('');
											setFields(initialFields);
										}}
									>
										{Liferay.Language.get('clear')}
									</ClayButton>
								</ClayResultsBar.Item>
							</ClayResultsBar>
						)}

						<div className="container-fluid container-fluid-max-xl px-4 py-2">
							<TreeView
								className="bg-light"
								expandedKeys={new Set(expandedKeys)}
								items={fields}
								nestedKey="children"
								onExpandedChange={(keys) => {
									setExpandedKeys(Array.from(keys));
								}}
								onItemsChange={(items) =>
									setInitialFields(
										items as Array<IFieldTreeItem>
									)
								}
								onSelectionChange={setSelectedKeys}
								selectedKeys={selectedKeys}
								selectionMode="multiple"
								showExpanderOnHover={false}
							>
								{({children, label, query}: IFieldTreeItem) => (
									<TreeView.Item>
										<TreeView.ItemStack>
											<ClayCheckbox checked>
												<span className="font-weight-normal pl-1 text-3">
													<Highlight
														query={query}
														text={label}
													/>
												</span>
											</ClayCheckbox>
										</TreeView.ItemStack>

										<TreeView.Group items={children}>
											{({label}: IFieldTreeItem) => (
												<TreeView.Item>
													<ClayCheckbox checked>
														<span className="font-weight-normal pl-1 text-3">
															<Highlight
																query={query}
																text={label}
															/>
														</span>
													</ClayCheckbox>
												</TreeView.Item>
											)}
										</TreeView.Group>
									</TreeView.Item>
								)}
							</TreeView>
						</div>
					</>
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

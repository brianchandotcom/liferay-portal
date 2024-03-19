/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayTable from '@clayui/table';
import classNames from 'classnames';
import {fetch, openModal, sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import '../../../../css/ListVisualizationMode.scss';

import {ClayDropDownWithItems} from '@clayui/drop-down';

import {IFDSViewSectionProps} from '../../../FDSView';
import FieldSelectModalContent from '../../../components/FieldSelectModalContent';
import {API_URL, OBJECT_RELATIONSHIP} from '../../../utils/constants';
import openDefaultFailureToast from '../../../utils/openDefaultFailureToast';
import openDefaultSuccessToast from '../../../utils/openDefaultSuccessToast';
import {IField} from '../../../utils/types';

interface IFDSListSection {
	externalReferenceCode: string;
	fieldName: string;
	name: string;
	rendererName?: string;
}
interface IListSection {
	externalReferenceCode?: IFDSListSection['externalReferenceCode'];
	field?: IField;
	label: string;
	name: IFDSListSection['name'];
}

export default function List(props: IFDSViewSectionProps) {
	const {fdsView} = props;

	const [listSections, setListSections] = useState<Array<IListSection>>([
		{label: Liferay.Language.get('title'), name: 'title'},
		{label: Liferay.Language.get('description'), name: 'description'},
		{label: Liferay.Language.get('image'), name: 'image'},
		{label: Liferay.Language.get('symbol'), name: 'symbol'},
	]);
	const [saveButtonDisabled, setSaveButtonDisabled] = useState(false);

	const getFDSListSections = async () => {
		const response = await fetch(
			`${API_URL.FDS_LIST_SECTIONS}?filter=(${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_LIST_SECTION_ERC} eq '${fdsView.externalReferenceCode}')`
		);

		if (!response.ok) {
			openDefaultFailureToast();

			return null;
		}

		const responseJSON = await response.json();

		const fdsListSections = responseJSON?.items;

		if (!fdsListSections) {
			openDefaultFailureToast();

			return null;
		}

		setListSections(
			listSections.map((listSection) => {
				const fdsListSection = fdsListSections.find(
					(fdsListSection: IFDSListSection) =>
						fdsListSection.name === listSection.name
				);

				if (!fdsListSection) {
					return listSection;
				}

				return {
					...listSection,
					externalReferenceCode: fdsListSection.externalReferenceCode,
					field: {
						name: fdsListSection.fieldName,
					},
				};
			})
		);
	};

	const clearFDSListSection = async (listSection: IListSection) => {
		setSaveButtonDisabled(true);

		const response = await fetch(
			`${API_URL.FDS_LIST_SECTIONS}/by-external-reference-code/${listSection.externalReferenceCode}`,
			{method: 'DELETE'}
		);

		setSaveButtonDisabled(false);

		if (!response.ok) {
			openDefaultFailureToast();

			return;
		}

		setListSections(
			listSections.map((section) => {
				if (section.name !== listSection.name) {
					return section;
				}

				const nextListSection = {...listSection};

				delete nextListSection.externalReferenceCode;
				delete nextListSection.field;

				return nextListSection;
			})
		);
	};

	const saveFDSListSection = async ({
		closeModal,
		field,
		listSection,
	}: {
		closeModal: Function;
		field: IField;
		listSection: IListSection;
	}) => {
		setSaveButtonDisabled(true);

		let method = 'POST';
		let url = API_URL.FDS_LIST_SECTIONS;

		if (listSection.externalReferenceCode) {
			method = 'PATCH';
			url = `${API_URL.FDS_LIST_SECTIONS}/by-external-reference-code/${listSection.externalReferenceCode}`;
		}

		const response = await fetch(url, {
			body: JSON.stringify({
				[OBJECT_RELATIONSHIP.FDS_VIEW_FDS_LIST_SECTION_ERC]:
					fdsView.externalReferenceCode,
				fieldName: field.name,
				name: listSection.name,
			}),
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json',
			},
			method,
		});

		setSaveButtonDisabled(false);

		if (!response.ok) {
			openDefaultFailureToast();

			return;
		}

		const fdsListSection: IFDSListSection = await response.json();

		closeModal();

		setListSections(
			listSections.map((listSection) => {
				if (listSection.name !== fdsListSection.name) {
					return listSection;
				}

				return {
					...listSection,
					externalReferenceCode: fdsListSection.externalReferenceCode,
					field: {
						name: fdsListSection.fieldName,
					},
				};
			})
		);

		openDefaultSuccessToast();
	};

	useEffect(() => {
		getFDSListSections();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<ClayLayout.ContentCol className="c-gap-4 list-visualization-mode">
			<ClayAlert
				displayType="info"
				title={`${Liferay.Language.get('info')}:`}
				variant="stripe"
			>
				{Liferay.Language.get(
					'this-visualization-mode-will-not-be-shown-until-you-assign-at-least-one-field-to-a-list-element'
				)}
			</ClayAlert>

			<ClayTable className="mb-0">
				<ClayTable.Head>
					<ClayTable.Row>
						<ClayTable.Cell
							className="list-section-label"
							headingCell
						>
							{Liferay.Language.get('list-element')}
						</ClayTable.Cell>

						<ClayTable.Cell className="field-name" headingCell>
							{Liferay.Language.get('field')}
						</ClayTable.Cell>
					</ClayTable.Row>
				</ClayTable.Head>

				<ClayTable.Body>
					{listSections.map((listSection) => (
						<ListSection
							key={listSection.name}
							listSection={listSection}
							modalProps={props}
							onClearSelection={() => {
								clearFDSListSection(listSection);
							}}
							onSelect={({closeModal, selectedField}) => {
								saveFDSListSection({
									closeModal,
									field: selectedField,
									listSection,
								});
							}}
							saveButtonDisabled={saveButtonDisabled}
						/>
					))}
				</ClayTable.Body>
			</ClayTable>
		</ClayLayout.ContentCol>
	);
}

interface IListSectionProps {
	listSection: IListSection;
	modalProps: IFDSViewSectionProps;
	onClearSelection: () => void;
	onSelect: ({
		closeModal,
		selectedField,
	}: {
		closeModal: Function;
		selectedField: IField;
	}) => void;
	saveButtonDisabled: boolean;
}

function ListSection({
	listSection,
	modalProps,
	onClearSelection,
	onSelect,
	saveButtonDisabled,
}: IListSectionProps) {
	const {field, label} = listSection;

	const openSelectFieldModal = () => {
		openModal({
			contentComponent: ({closeModal}: {closeModal: Function}) => (
				<FieldSelectModalContent
					{...modalProps}
					closeModal={closeModal}
					onSaveButtonClick={({
						selectedFields,
					}: {
						selectedFields: Array<IField>;
					}) => {
						onSelect({
							closeModal,
							selectedField: selectedFields[0],
						});
					}}
					saveButtonDisabled={saveButtonDisabled}
					selectedFields={field ? [field] : []}
				/>
			),
			size: 'full-screen',
		});
	};

	return (
		<ClayTable.Row>
			<ClayTable.Cell className="list-section-label">
				<strong>{label}</strong>
			</ClayTable.Cell>

			<ClayTable.Cell className="field-name">
				<ClayInput.Group small>
					<ClayInput.GroupItem>
						<p
							className={classNames(
								'align-items-center d-flex mb-0',
								{'text-secondary': !field}
							)}
						>
							{field
								? field.label || field.name
								: Liferay.Language.get('not-assigned')}
						</p>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem shrink>
						<UpdateButton
							field={field}
							label={label}
							onClearSelection={onClearSelection}
							openSelectFieldModal={openSelectFieldModal}
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayTable.Cell>
		</ClayTable.Row>
	);
}

interface IUpdateButtonProps {
	field?: IField;
	label: string;
	onClearSelection: () => void;
	openSelectFieldModal: () => void;
}

function UpdateButton({
	field,
	label,
	onClearSelection,
	openSelectFieldModal,
}: IUpdateButtonProps) {
	return field ? (
		<ClayDropDownWithItems
			items={[
				{
					label: Liferay.Language.get('change-assignment'),
					onClick: openSelectFieldModal,
					symbolLeft: 'change',
				},
				{
					label: Liferay.Language.get('clear-assignment'),
					onClick: onClearSelection,
					symbolLeft: 'times-circle',
				},
			]}
			trigger={
				<ClayButtonWithIcon
					aria-label={sub(
						Liferay.Language.get('view-x-options'),
						label
					)}
					displayType="secondary"
					size="sm"
					symbol="ellipsis-v"
					title={sub(Liferay.Language.get('view-x-options'), label)}
				/>
			}
		/>
	) : (
		<ClayButtonWithIcon
			aria-label={Liferay.Language.get('assign-field')}
			displayType="secondary"
			onClick={openSelectFieldModal}
			symbol="plus"
			title={Liferay.Language.get('assign-field')}
		/>
	);
}

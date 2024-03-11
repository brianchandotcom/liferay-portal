/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayTable from '@clayui/table';
import classNames from 'classnames';
import {fetch, openModal} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import '../../../../css/CardsVisualizationMode.scss';
import {IFDSViewSectionProps} from '../../../FDSView';
import FieldSelectModalContent from '../../../components/FieldSelectModalContent';
import {API_URL, OBJECT_RELATIONSHIP} from '../../../utils/constants';
import openDefaultFailureToast from '../../../utils/openDefaultFailureToast';
import openDefaultSuccessToast from '../../../utils/openDefaultSuccessToast';
import {IField} from '../../../utils/types';
import FieldAssignmentControls from '../components/FieldAssignmentControls';

interface IFDSCardsSection {
	externalReferenceCode: string;
	fieldName: string;
	name: string;
	rendererName?: string;
}
interface ICardsSection {
	externalReferenceCode?: IFDSCardsSection['externalReferenceCode'];
	field?: IField;
	label: string;
	name: IFDSCardsSection['name'];
}

export default function Cards(props: IFDSViewSectionProps) {
	const {fdsView} = props;

	const [cardsSections, setCardsSections] = useState<Array<ICardsSection>>([
		{label: Liferay.Language.get('title'), name: 'title'},
		{label: Liferay.Language.get('description'), name: 'description'},
		{label: Liferay.Language.get('image'), name: 'image'},
		{label: Liferay.Language.get('symbol'), name: 'symbol'},
	]);
	const [saveButtonDisabled, setSaveButtonDisabled] = useState(false);

	const getFDSCardsSections = async () => {
		const response = await fetch(
			`${API_URL.FDS_CARDS_SECTIONS}?filter=(${OBJECT_RELATIONSHIP.FDS_VIEW_FDS_CARDS_SECTION_ERC} eq '${fdsView.externalReferenceCode}')`
		);

		if (!response.ok) {
			openDefaultFailureToast();

			return null;
		}

		const responseJSON = await response.json();

		const fdsCardsSections = responseJSON?.items;

		if (!fdsCardsSections) {
			openDefaultFailureToast();

			return null;
		}

		setCardsSections(
			cardsSections.map((cardsSection) => {
				const fdsCardsSection = fdsCardsSections.find(
					(fdsCardsSection: IFDSCardsSection) =>
						fdsCardsSection.name === cardsSection.name
				);

				if (!fdsCardsSection) {
					return cardsSection;
				}

				return {
					...cardsSection,
					externalReferenceCode:
						fdsCardsSection.externalReferenceCode,
					field: {
						name: fdsCardsSection.fieldName,
					},
				};
			})
		);
	};

	const clearFDSCardSection = async (cardSection: ICardsSection) => {
		setSaveButtonDisabled(true);

		const response = await fetch(
			`${API_URL.FDS_CARDS_SECTIONS}/by-external-reference-code/${cardSection.externalReferenceCode}`,
			{method: 'DELETE'}
		);

		setSaveButtonDisabled(false);

		if (!response.ok) {
			openDefaultFailureToast();

			return;
		}

		setCardsSections(
			cardsSections.map((section) => {
				if (section.name !== cardSection.name) {
					return section;
				}

				const nextCardSection = {...cardSection};

				delete nextCardSection.externalReferenceCode;
				delete nextCardSection.field;

				return nextCardSection;
			})
		);
	};

	const saveFDSCardsSection = async ({
		cardsSection,
		closeModal,
		field,
	}: {
		cardsSection: ICardsSection;
		closeModal: Function;
		field: IField;
	}) => {
		setSaveButtonDisabled(true);

		let method = 'POST';
		let url = API_URL.FDS_CARDS_SECTIONS;

		if (cardsSection.externalReferenceCode) {
			method = 'PATCH';
			url = `${API_URL.FDS_CARDS_SECTIONS}/by-external-reference-code/${cardsSection.externalReferenceCode}`;
		}

		const response = await fetch(url, {
			body: JSON.stringify({
				[OBJECT_RELATIONSHIP.FDS_VIEW_FDS_CARDS_SECTION_ERC]:
					fdsView.externalReferenceCode,
				fieldName: field.name,
				name: cardsSection.name,
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

		const fdsCardsSection: IFDSCardsSection = await response.json();

		closeModal();

		setCardsSections(
			cardsSections.map((cardsSection) => {
				if (cardsSection.name !== fdsCardsSection.name) {
					return cardsSection;
				}

				return {
					...cardsSection,
					externalReferenceCode:
						fdsCardsSection.externalReferenceCode,
					field: {
						name: fdsCardsSection.fieldName,
					},
				};
			})
		);

		openDefaultSuccessToast();
	};

	useEffect(() => {
		getFDSCardsSections();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<ClayLayout.ContentCol className="c-gap-4 cards-visualization-mode">
			<ClayAlert
				displayType="info"
				title={`${Liferay.Language.get('info')}:`}
				variant="stripe"
			>
				{Liferay.Language.get(
					'this-visualization-mode-will-not-be-shown-until-you-assign-at-least-one-field-to-a-card-element'
				)}
			</ClayAlert>

			<ClayTable className="mb-0">
				<ClayTable.Head>
					<ClayTable.Row>
						<ClayTable.Cell
							className="cards-section-label"
							headingCell
						>
							{Liferay.Language.get('card-element')}
						</ClayTable.Cell>

						<ClayTable.Cell className="field-name" headingCell>
							{Liferay.Language.get('field')}
						</ClayTable.Cell>
					</ClayTable.Row>
				</ClayTable.Head>

				<ClayTable.Body>
					{cardsSections.map((cardsSection) => (
						<CardsSection
							cardsSection={cardsSection}
							key={cardsSection.name}
							modalProps={props}
							onClearSelection={() => {
								clearFDSCardSection(cardsSection);
							}}
							onSelect={({closeModal, selectedField}) => {
								saveFDSCardsSection({
									cardsSection,
									closeModal,
									field: selectedField,
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

interface ICardsSectionProps {
	cardsSection: ICardsSection;
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

function CardsSection({
	cardsSection,
	modalProps,
	onClearSelection,
	onSelect,
	saveButtonDisabled,
}: ICardsSectionProps) {
	const {field, label} = cardsSection;

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
			<ClayTable.Cell className="cards-section-label">
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
						<FieldAssignmentControls
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

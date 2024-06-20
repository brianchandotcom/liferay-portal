/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';
import {
	FDS_INTERNAL_CELL_RENDERERS,
	IClientExtensionRenderer,
	IInternalRenderer,
} from '@liferay/frontend-data-set-web';
import {InputLocalized} from 'frontend-js-components-web';
import {fetch, openModal} from 'frontend-js-web';
import fuzzy from 'fuzzy';
import React, {useEffect, useState} from 'react';

import FieldSelectModalContent, {
	visit,
} from '../../../components/FieldSelectModalContent';
import OrderableTable from '../../../components/OrderableTable';
import {
	API_URL,
	FUZZY_OPTIONS,
	OBJECT_RELATIONSHIP,
} from '../../../utils/constants';
import openDefaultFailureToast from '../../../utils/openDefaultFailureToast';
import openDefaultSuccessToast from '../../../utils/openDefaultSuccessToast';
import {IDataSetSectionProps} from '../../DataSet';

import '../../../../css/TableVisualizationMode.scss';

import ClayAlert from '@clayui/alert';
import ClayIcon from '@clayui/icon';

import sortItems from '../../../utils/sortItems';
import {
	EFieldType,
	IField,
	IFieldTreeItem,
	ITableSection,
} from '../../../utils/types';

const TABLE_SECTIONS_ORDER_OBJECT_FIELD_NAME = Liferay.FeatureFlags['LPD-15729']
	? 'tableSectionsOrder'
	: 'fdsFieldsOrder';

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

const getRendererLabel = ({
	cetRenderers = [],
	rendererName,
}: {
	cetRenderers?: IClientExtensionRenderer[];
	rendererName: string;
}): string => {
	let clientExtensionRenderer;

	const internalRenderer = FDS_INTERNAL_CELL_RENDERERS.find(
		(renderer: IInternalRenderer) => {
			return renderer.name === rendererName;
		}
	);

	if (internalRenderer?.label) {
		return internalRenderer.label;
	}
	else {
		clientExtensionRenderer = cetRenderers.find(
			(renderer: IClientExtensionRenderer) => {
				return renderer.externalReferenceCode === rendererName;
			}
		);

		if (clientExtensionRenderer?.name) {
			return clientExtensionRenderer.name;
		}

		return rendererName;
	}
};

const RendererLabelCellRendererComponent = ({
	cetRenderers = [],
	item,
	query,
}: {
	cetRenderers?: IClientExtensionRenderer[];
	item: ITableSection;
	query: string;
}) => {
	const itemFieldValue = getRendererLabel({
		cetRenderers,
		rendererName: item.renderer,
	});

	const fuzzyMatch = fuzzy.match(query, itemFieldValue, FUZZY_OPTIONS);

	return (
		<span>
			{fuzzyMatch ? (
				<span
					dangerouslySetInnerHTML={{
						__html: fuzzyMatch.rendered,
					}}
				/>
			) : (
				<span>{itemFieldValue}</span>
			)}
		</span>
	);
};

const EditTableSectionModalContent = ({
	clientExtensionCellRenderers,
	closeModal,
	namespace,
	onSaveButtonClick,
	sortable,
	tableSection,
}: {
	clientExtensionCellRenderers: IClientExtensionRenderer[];
	closeModal: Function;
	namespace: string;
	onSaveButtonClick: Function;
	sortable: boolean;
	tableSection: ITableSection;
}) => {
	const [selectedTableSectionRenderer, setSelectedTableSectionRenderer] =
		useState(tableSection.renderer ?? 'default');

	const [tableSectionSortable, setFSDFieldSortable] = useState<boolean>(
		tableSection.sortable
	);

	const fdsInternalCellRendererNames = FDS_INTERNAL_CELL_RENDERERS.map(
		(cellRenderer: IInternalRenderer) => cellRenderer.name
	);

	const tableSectionTranslations = tableSection.label_i18n;

	const [i18nFieldLabels, setI18nFieldLabels] = useState(
		tableSectionTranslations
	);

	const editTableSection = async () => {
		const body = {
			label_i18n: i18nFieldLabels,
			renderer: selectedTableSectionRenderer,
			rendererType: !fdsInternalCellRendererNames.includes(
				selectedTableSectionRenderer
			)
				? 'clientExtension'
				: 'internal',
			sortable: tableSectionSortable,
		};

		const response = await fetch(
			`${API_URL.TABLE_SECTIONS}/by-external-reference-code/${tableSection.externalReferenceCode}`,
			{
				body: JSON.stringify(body),
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				},
				method: 'PATCH',
			}
		);

		if (!response.ok) {
			openDefaultFailureToast();

			return;
		}

		const editedTableSection = await response.json();

		closeModal();

		onSaveButtonClick({editedTableSection});

		openDefaultSuccessToast();
	};

	const tableSectionNameInputId = `${namespace}tableSectionNameInput`;
	const tableSectionLabelInputId = `${namespace}tableSectionLabelInput`;
	const tableSectionRendererSelectId = `${namespace}tableSectionRendererSelectId`;

	const options = FDS_INTERNAL_CELL_RENDERERS.map(
		(renderer: IInternalRenderer) => ({
			label: renderer.label!,
			value: renderer.name!,
		})
	);

	options.push(
		...clientExtensionCellRenderers.map((item) => ({
			label: item.name!,
			value: item.externalReferenceCode!,
		}))
	);

	const CellRendererDropdown = ({
		cellRenderers,
		namespace,
		onItemClick,
	}: {
		cellRenderers: {
			label: string;
			value: string;
		}[];
		namespace: string;
		onItemClick: Function;
	}) => {
		const clientExtensionCellRenderersERCs =
			clientExtensionCellRenderers.map(
				(cellRendererCET) => cellRendererCET.externalReferenceCode
			);

		return (
			<ClayDropDown
				menuElementAttrs={{
					className: 'fds-cell-renderers-dropdown-menu',
				}}
				trigger={
					<ClayButton
						aria-labelledby={`${namespace}cellRenderersLabel`}
						className="form-control form-control-select form-control-select-secondary"
						displayType="secondary"
						id={tableSectionRendererSelectId}
					>
						{selectedTableSectionRenderer
							? getRendererLabel({
									cetRenderers: clientExtensionCellRenderers,
									rendererName: selectedTableSectionRenderer,
								})
							: Liferay.Language.get('choose-an-option')}
					</ClayButton>
				}
			>
				<ClayDropDown.ItemList items={cellRenderers} role="listbox">
					{cellRenderers.map((cellRenderer) => (
						<ClayDropDown.Item
							className="align-items-center d-flex justify-content-between"
							key={cellRenderer.value}
							onClick={() => onItemClick(cellRenderer.value)}
							roleItem="option"
						>
							{cellRenderer.label}

							{clientExtensionCellRenderersERCs.includes(
								cellRenderer.value
							) && (
								<ClayLabel displayType="info">
									{Liferay.Language.get('client-extension')}
								</ClayLabel>
							)}
						</ClayDropDown.Item>
					))}
				</ClayDropDown.ItemList>
			</ClayDropDown>
		);
	};

	return (
		<>
			<ClayModal.Header>
				{Liferay.Util.sub(
					Liferay.Language.get('edit-x'),
					tableSection.label_i18n[defaultLanguageId] ??
						tableSection.name
				)}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm.Group>
					<label htmlFor={tableSectionNameInputId}>
						{Liferay.Language.get('name')}
					</label>

					<ClayInput
						disabled
						id={tableSectionNameInputId}
						type="text"
						value={tableSection.name}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<InputLocalized
						id={tableSectionLabelInputId}
						label={Liferay.Language.get('label')}
						name="label"
						onChange={setI18nFieldLabels}
						translations={i18nFieldLabels}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor={tableSectionRendererSelectId}>
						{Liferay.Language.get('renderer')}
					</label>

					<CellRendererDropdown
						cellRenderers={options}
						namespace={namespace}
						onItemClick={(item: string) =>
							setSelectedTableSectionRenderer(item)
						}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<ClayCheckbox
						checked={tableSectionSortable}
						disabled={!sortable}
						inline
						label={Liferay.Language.get('sortable')}
						onChange={({target: {checked}}) =>
							setFSDFieldSortable(checked)
						}
					/>

					{tableSection.type !== EFieldType.OBJECT && (
						<span
							className="label-icon lfr-portal-tooltip ml-2"
							title={Liferay.Language.get(
								'if-checked,-data-set-items-can-be-sorted-by-this-field'
							)}
						>
							<ClayIcon symbol="question-circle-full" />
						</span>
					)}
				</ClayForm.Group>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton onClick={() => editTableSection()}>
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

function Table(props: IDataSetSectionProps & {title?: string}) {
	const {
		clientExtensionCellRenderers,
		dataSet,
		fieldTreeItems,
		namespace,
		saveTableSectionsURL,
		title,
	} = props;

	const [tableSections, setTableSections] =
		useState<Array<ITableSection> | null>(null);
	const [saveButtonDisabled, setSaveButtonDisabled] = useState(false);

	const getTableSections = async () => {
		const response = await fetch(
			`${API_URL.TABLE_SECTIONS}?filter=(${OBJECT_RELATIONSHIP.DATA_SET_TABLE_SECTION_ID} eq '${dataSet.id}')&nestedFields=${OBJECT_RELATIONSHIP.DATA_SET_TABLE_SECTION}&sort=dateCreated:asc`
		);

		if (!response.ok) {
			openDefaultFailureToast();

			return null;
		}

		const responseJSON = await response.json();

		const storedTableSections: ITableSection[] = responseJSON?.items;

		if (!storedTableSections) {
			openDefaultFailureToast();

			return null;
		}

		const tableSectionsOrder =
			storedTableSections?.[0]?.[
				OBJECT_RELATIONSHIP.DATA_SET_TABLE_SECTION
			]?.[TABLE_SECTIONS_ORDER_OBJECT_FIELD_NAME];

		setTableSections(
			sortItems(
				storedTableSections,
				tableSectionsOrder
			) as ITableSection[]
		);
	};

	const onDeleteButtonClick = ({item}: {item: ITableSection}) => {
		openModal({
			bodyHTML: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-this-field?-fragments-using-it-will-be-affected'
			),
			buttons: [
				{
					autoFocus: true,
					displayType: 'secondary',
					label: Liferay.Language.get('cancel'),
					type: 'cancel',
				},
				{
					displayType: 'warning',
					label: Liferay.Language.get('delete'),
					onClick: async ({
						processClose,
					}: {
						processClose: Function;
					}) => {
						processClose();

						const url = `${API_URL.TABLE_SECTIONS}/${item.id}`;

						const response = await fetch(url, {method: 'DELETE'});

						if (!response.ok) {
							openDefaultFailureToast();

							return;
						}

						openDefaultSuccessToast();

						setTableSections(
							tableSections?.filter(
								(tableSection: ITableSection) =>
									tableSection.id !== item.id
							) || []
						);
					},
				},
			],
			status: 'warning',
			title: Liferay.Language.get('delete-filter'),
		});
	};

	const saveTableSections = async ({
		closeModal,
		fields,
	}: {
		closeModal: Function;
		fields: Array<IField>;
	}) => {
		setSaveButtonDisabled(true);

		const creationData: Array<{
			name: string;
			sortable: boolean;
			type: string;
		}> = [];
		const deletionIds: Array<number> = [];

		fields.forEach((field) => {
			if (!field.id) {
				creationData.push({
					name: field.name,
					sortable: field.sortable || false,
					type: field.type || 'string',
				});
			}
		});

		tableSections?.forEach((tableSection) => {
			if (!fields.find((field) => field.name === tableSection.name)) {
				deletionIds.push(tableSection.id);
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

		formData.append(`${namespace}dataSetId`, dataSet.id);

		const response = await fetch(saveTableSectionsURL, {
			body: formData,
			method: 'POST',
		});

		setSaveButtonDisabled(false);

		if (!response.ok) {
			openDefaultFailureToast();

			return;
		}

		const createdTableSections: Array<ITableSection> =
			await response.json();

		closeModal();

		const newTableSections: Array<ITableSection> = [];

		tableSections?.forEach((tableSection) => {
			if (!deletionIds.includes(tableSection.id)) {
				newTableSections.push(tableSection);
			}
		});

		createdTableSections.forEach((tableSection) => {
			newTableSections.push(tableSection);
		});

		setTableSections(newTableSections);

		openDefaultSuccessToast();
	};

	const updateTableSectionsOrder = async ({
		tableSectionsOrder,
	}: {
		tableSectionsOrder: string;
	}) => {
		const body = {
			[TABLE_SECTIONS_ORDER_OBJECT_FIELD_NAME]: tableSectionsOrder,
		};

		const response = await fetch(
			`${API_URL.DATA_SETS}/by-external-reference-code/${dataSet.externalReferenceCode}`,
			{
				body: JSON.stringify(body),
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				},
				method: 'PATCH',
			}
		);

		if (!response.ok) {
			openDefaultFailureToast();

			return null;
		}

		const responseJSON = await response.json();

		const storedTableSectionsOrder =
			responseJSON?.[TABLE_SECTIONS_ORDER_OBJECT_FIELD_NAME];

		if (
			tableSections &&
			storedTableSectionsOrder &&
			storedTableSectionsOrder === tableSectionsOrder
		) {
			setTableSections(
				sortItems(
					tableSections,
					storedTableSectionsOrder
				) as ITableSection[]
			);

			openDefaultSuccessToast();
		}
		else {
			openDefaultFailureToast();
		}
	};

	useEffect(() => {
		getTableSections();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const onCreationButtonClick = () => {
		openModal({
			contentComponent: ({closeModal}: {closeModal: Function}) => (
				<FieldSelectModalContent
					{...props}
					closeModal={closeModal}
					fieldTreeItems={fieldTreeItems}
					onSaveButtonClick={({
						selectedFields,
					}: {
						selectedFields: Array<IField>;
					}) => {
						saveTableSections({closeModal, fields: selectedFields});
					}}
					saveButtonDisabled={saveButtonDisabled}
					selectedFields={
						tableSections
							? tableSections.map((tableSection) => ({
									id: String(tableSection.id),
									name: tableSection.name,
								}))
							: []
					}
					selectionMode="multiple"
				/>
			),
			size: 'full-screen',
		});
	};

	const onEditButtonClick = ({item}: {item: ITableSection}) => {
		openModal({
			className: 'overflow-auto',
			contentComponent: ({closeModal}: {closeModal: Function}) => (
				<EditTableSectionModalContent
					clientExtensionCellRenderers={clientExtensionCellRenderers}
					closeModal={closeModal}
					namespace={namespace}
					onSaveButtonClick={({
						editedTableSection,
					}: {
						editedTableSection: ITableSection;
					}) => {
						setTableSections(
							tableSections?.map((tableSection) => {
								if (
									tableSection.name ===
									editedTableSection.name
								) {
									return editedTableSection;
								}

								return tableSection;
							}) || null
						);
					}}
					sortable={isSortable(fieldTreeItems, item)}
					tableSection={item}
				/>
			),
		});
	};

	return tableSections ? (
		<ClayLayout.ContentCol className="c-gap-4 table-visualization-mode">
			<ClayAlert
				displayType="info"
				title={`${Liferay.Language.get('info')}:`}
				variant="stripe"
			>
				{Liferay.Language.get(
					'this-visualization-mode-will-not-be-shown-until-you-assign-at-least-one-field'
				)}
			</ClayAlert>

			<OrderableTable
				actions={[
					{
						icon: 'pencil',
						label: Liferay.Language.get('edit'),
						onClick: onEditButtonClick,
					},
					{
						icon: 'trash',
						label: Liferay.Language.get('delete'),
						onClick: onDeleteButtonClick,
					},
				]}
				creationMenuItems={[
					{
						label: Liferay.Language.get('add-fields'),
						onClick: onCreationButtonClick,
					},
				]}
				fields={[
					{
						label: Liferay.Language.get('name'),
						name: 'name',
					},
					{
						label: Liferay.Language.get('label'),
						name: 'label',
					},
					{
						label: Liferay.Language.get('type'),
						name: 'type',
					},
					{
						contentRenderer: {
							component: ({item, query}) => (
								<RendererLabelCellRendererComponent
									cetRenderers={clientExtensionCellRenderers}
									item={item}
									query={query}
								/>
							),
							textMatch: (item: ITableSection) =>
								getRendererLabel({
									cetRenderers: clientExtensionCellRenderers,
									rendererName: item.renderer,
								}),
						},
						label: Liferay.Language.get('renderer'),
						name: 'renderer',
					},
					{
						label: Liferay.Language.get('sortable'),
						name: 'sortable',
					},
				]}
				items={tableSections}
				noItemsButtonLabel={Liferay.Language.get('add-fields')}
				noItemsDescription={Liferay.Language.get(
					'add-fields-to-show-in-your-view'
				)}
				noItemsTitle={Liferay.Language.get('no-fields-added-yet')}
				onOrderChange={({order}: {order: string}) => {
					updateTableSectionsOrder({
						tableSectionsOrder: order,
					});
				}}
				title={title}
			/>
		</ClayLayout.ContentCol>
	) : (
		<ClayLoadingIndicator />
	);
}

export function Fields(props: IDataSetSectionProps) {
	return (
		<ClayLayout.ContainerFluid>
			<Table {...props} title={Liferay.Language.get('fields')} />
		</ClayLayout.ContainerFluid>
	);
}

function isSortable(
	fieldTreeItems: Array<IFieldTreeItem>,
	selectedItem: ITableSection
): boolean {
	let isSortable = false;
	visit(fieldTreeItems, (fieldTreeItem: IFieldTreeItem) => {
		if (fieldTreeItem.name === selectedItem.name) {
			isSortable = fieldTreeItem.sortable || false;

			return;
		}
	});

	return isSortable;
}

export default Table;

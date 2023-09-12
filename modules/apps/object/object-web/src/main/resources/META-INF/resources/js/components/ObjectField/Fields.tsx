/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {API} from '@liferay/object-js-components-web';
import {createResourceURL} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {defaultLanguageId} from '../../utils/constants';
import {
	IFDSTableProps,
	defaultDataSetProps,
	fdsItem,
	formatActionURL,
} from '../../utils/fds';
import FDSSourceDataRenderer from '../FDSPropsTransformer/FDSSourceDataRenderer';
import {ModalAddObjectField} from './ModalAddObjectField';
import {ModalDeleteObjectField} from './ModalDeleteObjectField';
import {deleteObjectField} from './deleteObjectFieldUtil';

interface ItemData {
	id: number;
	required: boolean;
	system?: boolean;
}

interface FieldsProps extends IFDSTableProps {
	baseResourceURL: string;
	objectFieldTypes: ObjectFieldType[];
}

export default function Fields({
	apiURL,
	baseResourceURL,
	creationMenu,
	formName,
	id,
	items,
	objectDefinitionExternalReferenceCode,
	objectFieldTypes,
	style,
	url,
}: FieldsProps) {
	const [creationLanguageId, setCreationLanguageId] = useState<
		Liferay.Language.Locale
	>();

	const [
		deletedObjectField,
		setDeletedObjectField,
	] = useState<ObjectField | null>(null);

	const [showAddFieldModal, setShowAddFieldModal] = useState(false);

	const [showDeletionModal, setShowDeletionModal] = useState<boolean>(false);

	const [
		showDeletionNotAllowedModal,
		setShowDeletionNotAllowedModal,
	] = useState<boolean>(false);

	useEffect(() => {
		Liferay.on('addObjectField', () => setShowAddFieldModal(true));

		return () => Liferay.detach('addObjectField');
	}, []);

	useEffect(() => {
		const makeFetch = async () => {
			const objectDefinition = await API.getObjectDefinitionByExternalReferenceCode(
				objectDefinitionExternalReferenceCode
			);

			setCreationLanguageId(objectDefinition.defaultLanguageId);
		};

		makeFetch();
	}, [objectDefinitionExternalReferenceCode]);

	function objectFieldLabelDataRenderer({
		itemData,
		openSidePanel,
		value,
	}: fdsItem<ItemData>) {
		const handleEditField = () => {
			openSidePanel({
				url: formatActionURL(url, itemData.id),
			});
		};

		return (
			<div className="table-list-title">
				<a href="#" onClick={handleEditField}>
					{value}
				</a>
			</div>
		);
	}

	function objectFieldMandatoryDataRenderer({
		itemData,
	}: {
		itemData: ItemData;
	}) {
		return itemData.required
			? Liferay.Language.get('yes')
			: Liferay.Language.get('no');
	}

	const dataSetProps = {
		...defaultDataSetProps,
		apiURL,
		creationMenu,
		customDataRenderers: {
			FDSSourceDataRenderer,
			objectFieldLabelDataRenderer,
			objectFieldMandatoryDataRenderer,
		},
		formName,
		id,
		itemsActions: items,
		namespace:
			'_com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet_',
		onActionDropdownItemClick({
			action,
			itemData,
		}: {
			action: {data: {id: string}};
			itemData: ObjectField;
		}) {
			if (action.data.id === 'deleteObjectField') {
				const makeFetch = async () => {
					const url = createResourceURL(baseResourceURL, {
						objectFieldId: itemData.id,
						p_p_resource_id:
							'/object_definitions/get_object_field_delete_info',
					}).href;

					const showModalResponse = await API.fetchJSON<{
						showDeletionModal: boolean;
						showDeletionNotAllowedModal: boolean;
					}>(url);

					if (showModalResponse.showDeletionModal) {
						setDeletedObjectField(itemData);

						setShowDeletionModal(
							showModalResponse.showDeletionModal
						);

						setShowDeletionNotAllowedModal(
							showModalResponse.showDeletionNotAllowedModal
						);

						return;
					}

					await deleteObjectField(
						defaultLanguageId,
						itemData.id,
						itemData
					);

					setTimeout(() => window.location.reload(), 1500);

					return;
				};

				makeFetch();
			}
		},
		portletId:
			'com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet',
		style,
		views: [
			{
				contentRenderer: 'table',
				label: 'Table',
				name: 'table',
				schema: {
					fields: [
						{
							contentRenderer: 'objectFieldLabelDataRenderer',
							expand: false,
							fieldName: 'label',
							label: Liferay.Language.get('label'),
							localizeLabel: true,
							sortable: true,
						},
						{
							expand: false,
							fieldName: 'businessType',
							label: Liferay.Language.get('type'),
							localizeLabel: true,
							sortable: false,
						},
						{
							contentRenderer: 'objectFieldMandatoryDataRenderer',
							expand: false,
							fieldName: 'mandatory',
							label: Liferay.Language.get('mandatory'),
							localizeLabel: true,
							sortable: false,
						},
						{
							contentRenderer: 'FDSSourceDataRenderer',
							expand: false,
							fieldName: 'source',
							label: Liferay.Language.get('source'),
							localizeLabel: true,
							sortable: false,
						},
					],
				},
				thumbnail: 'table',
			},
		],
	};

	return (
		<>
			<FrontendDataSet {...dataSetProps} />

			{showAddFieldModal && (
				<ModalAddObjectField
					apiURL={apiURL as string}
					creationLanguageId={
						creationLanguageId as Liferay.Language.Locale
					}
					objectDefinitionExternalReferenceCode={
						objectDefinitionExternalReferenceCode
					}
					objectFieldTypes={objectFieldTypes}
					setVisibility={setShowAddFieldModal}
				/>
			)}

			{showDeletionModal && (
				<ModalDeleteObjectField
					objectField={deletedObjectField as ObjectField}
					setModalVisibility={setShowDeletionModal}
					setObjectField={setDeletedObjectField}
					showDeletionNotAllowedModal={showDeletionNotAllowedModal}
				/>
			)}
		</>
	);
}

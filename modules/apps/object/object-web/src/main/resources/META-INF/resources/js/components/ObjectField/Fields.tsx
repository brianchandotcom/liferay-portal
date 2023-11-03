/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {API, getLocalizableLabel} from '@liferay/object-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {
	IFDSTableProps,
	defaultDataSetProps,
	fdsItem,
	formatActionURL,
} from '../../utils/fds';
import FDSSourceDataRenderer from '../FDSPropsTransformer/FDSSourceDataRenderer';
import ModalDeletionNotAllowed from '../ModalDeletionNotAllowed';
import {ModalAddObjectField} from './ModalAddObjectField';
import {ModalDeleteObjectField} from './ModalDeleteObjectField';
import {handleTriggerDeleteObjectField} from './deleteObjectFieldUtil';

interface ItemData {
	id: number;
	localized: boolean;
	required: boolean;
	system?: boolean;
}

interface FieldsProps extends IFDSTableProps {
	baseResourceURL: string;
}

export default function Fields({
	apiURL,
	baseResourceURL,
	creationMenu,
	formName,
	id,
	items,
	objectDefinitionExternalReferenceCode,
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

	const [objectFieldDeleteInfo, setObjectFieldDeleteInfo] = useState<
		ObjectFieldDeleteInfoProps
	>({
		deleteLastPublishedObjectDefinitionObjectField: false,
		deleteObjectFieldObjectValidationRuleSetting: false,
		showObjectFieldDeletionConfirmationModal: false,
		showObjectFieldDeletionNotAllowedModal: false,
	});

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

	function objectFieldLocalizedDataRenderer({
		itemData,
	}: {
		itemData: ItemData;
	}) {
		return itemData.localized
			? Liferay.Language.get('yes')
			: Liferay.Language.get('no');
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
			objectFieldLocalizedDataRenderer,
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
					handleTriggerDeleteObjectField({
						baseResourceURL,
						objectFieldId: itemData?.id,
						objectFieldLabel: getLocalizableLabel(
							creationLanguageId!,
							itemData.label,
							itemData.name
						),
						onAfterDelete: () => {
							setTimeout(() => window.location.reload(), 1500);
						},
						setObjectFieldDeleteInfo,
					});

					setDeletedObjectField(itemData);
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
						{
							contentRenderer: 'objectFieldLocalizedDataRenderer',
							expand: false,
							fieldName: 'localized',
							label: Liferay.Language.get('translatable'),
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
					baseResourceURL={baseResourceURL}
					creationLanguageId={
						creationLanguageId as Liferay.Language.Locale
					}
					objectDefinitionExternalReferenceCode={
						objectDefinitionExternalReferenceCode
					}
					onAfterSubmit={() => {
						setShowAddFieldModal(false);
						window.location.reload();
					}}
					setVisibility={setShowAddFieldModal}
				/>
			)}

			{objectFieldDeleteInfo.showObjectFieldDeletionConfirmationModal && (
				<ModalDeleteObjectField
					objectField={deletedObjectField as ObjectField}
					onAfterSubmit={() => {
						setTimeout(() => window.location.reload(), 1500);
					}}
					setObjectField={setDeletedObjectField}
				/>
			)}

			{!!deletedObjectField &&
				objectFieldDeleteInfo.showObjectFieldDeletionNotAllowedModal && (
					<ModalDeletionNotAllowed
						content={
							objectFieldDeleteInfo.deleteObjectFieldObjectValidationRuleSetting ? (
								<Text>
									{sub(
										Liferay.Language.get(
											'x-is-the-only-field-of-the-published-object-definition-and-cannot-be-deleted'
										),
										`${getLocalizableLabel(
											creationLanguageId as Liferay.Language.Locale,
											deletedObjectField.label,
											deletedObjectField.name
										)}`
									)}
								</Text>
							) : (
								<Text>
									{Liferay.Language.get(
										'this-field-cannot-be-deleted-because-it-is-used-in-a-unique-composite-key-validation'
									)}
								</Text>
							)
						}
						onVisibilityChange={() =>
							setObjectFieldDeleteInfo({
								...objectFieldDeleteInfo,
								showObjectFieldDeletionNotAllowedModal: false,
							})
						}
					/>
				)}
		</>
	);
}

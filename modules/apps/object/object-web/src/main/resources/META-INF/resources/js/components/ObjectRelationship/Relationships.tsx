/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	FrontendDataSet,

	// @ts-ignore

} from '@liferay/frontend-data-set-web';
import {API, getLocalizableLabel} from '@liferay/object-js-components-web';
import classNames from 'classnames';
import React, {useEffect, useState} from 'react';

import {
	IFDSTableProps,
	defaultDataSetProps,
	fdsItem,
	formatActionURL,
} from '../../utils/fds';
import {ModalDeletionNotAllowed} from '../ModalDeletionNotAllowed';
import {deleteRelationship} from '../ViewObjectDefinitions/objectDefinitionUtil';
import {ModalAddObjectRelationship} from './ModalAddObjectRelationship';
import {ModalDeleteObjectRelationship} from './ModalDeleteObjectRelationship';

interface ItemData {
	id: number;
	reverse: boolean;
}

interface RelationshipsProps extends IFDSTableProps {
	baseResourceURL: string;
	isApproved: boolean;
	objectRelationshipTypes: string[];
	parameterRequired: boolean;
}

export default function Relationships({
	apiURL,
	baseResourceURL,
	creationMenu,
	formName,
	id,
	isApproved,
	items,
	objectDefinitionExternalReferenceCode,
	parameterRequired,
	style,
	url,
}: RelationshipsProps) {
	const [creationLanguageId, setCreationLanguageId] = useState<
		Liferay.Language.Locale
	>();

	const [
		objectRelationship,
		setObjectRelationship,
	] = useState<ObjectRelationship | null>();
	const [showAddModal, setShowAddModal] = useState(false);
	const [showDeleteModal, setShowDeleteModal] = useState(false);

	const [
		selectedObjectRelationship,
		setSelectedObjectRelationship,
	] = useState<ObjectRelationship>();

	const [
		showDelitionNotAllowedModal,
		setShowDelitionNotAllowedModal,
	] = useState(false);

	useEffect(() => {
		const makeFetch = async () => {
			const objectDefinition = await API.getObjectDefinitionByExternalReferenceCode(
				objectDefinitionExternalReferenceCode
			);

			setCreationLanguageId(objectDefinition.defaultLanguageId);
		};

		makeFetch();
	}, [objectDefinitionExternalReferenceCode]);

	function ObjectFieldHierarchyDataRenderer({
		itemData,
	}: {
		itemData: ItemData;
	}) {
		return (
			<strong
				className={classNames(
					itemData.reverse ? 'label-info' : 'label-success',
					'label'
				)}
			>
				{itemData.reverse
					? Liferay.Language.get('child')
					: Liferay.Language.get('parent')}
			</strong>
		);
	}

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

	const dataSetProps = {
		...defaultDataSetProps,
		apiURL,
		creationMenu,
		customDataRenderers: {
			ObjectFieldHierarchyDataRenderer,
			objectFieldLabelDataRenderer,
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
			itemData: ObjectRelationship;
		}) {
			if (action.data.id === 'deleteObjectRelationship') {
				if (itemData.edge && Liferay.FeatureFlags['LPS-187142']) {
					setSelectedObjectRelationship(itemData);
					setShowDelitionNotAllowedModal(true);

					return;
				}

				if (isApproved || itemData.reverse) {
					setObjectRelationship(itemData);
					setShowDeleteModal(true);
				}
				else {
					deleteRelationship(itemData.id);
					setTimeout(() => window.location.reload(), 1500);
				}
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
							fieldName: 'objectDefinitionName2',
							label: Liferay.Language.get('related-object'),
							localizeLabel: true,
							sortable: false,
						},
						{
							expand: false,
							fieldName: 'type',
							label: Liferay.Language.get('type'),
							localizeLabel: true,
							sortable: false,
						},
						{
							contentRenderer: 'ObjectFieldHierarchyDataRenderer',
							expand: false,
							fieldName: 'hierarchy',
							label: Liferay.Language.get('hierarchy'),
							localizeLabel: true,
							sortable: false,
						},
					],
				},
				thumbnail: 'table',
			},
		],
	};

	useEffect(() => {
		Liferay.on('addObjectRelationship', () => setShowAddModal(true));

		return () => {
			Liferay.detach('addObjectRelationship');
		};
	}, []);

	return (
		<>
			<FrontendDataSet {...dataSetProps} />

			{showAddModal && (
				<ModalAddObjectRelationship
					baseResourceURL={baseResourceURL}
					handleOnClose={() => setShowAddModal(false)}
					objectDefinitionExternalReferenceCode={
						objectDefinitionExternalReferenceCode
					}
					parameterRequired={parameterRequired}
				/>
			)}

			{showDeleteModal && objectRelationship && (
				<ModalDeleteObjectRelationship
					handleOnClose={() => setShowDeleteModal(false)}
					objectRelationship={
						objectRelationship as ObjectRelationship
					}
					setObjectRelationship={setObjectRelationship}
				/>
			)}

			{showDelitionNotAllowedModal &&
				Liferay.FeatureFlags['LPS-187142'] && (
					<ModalDeletionNotAllowed
						onVisibilityChange={() =>
							setShowDelitionNotAllowedModal(false)
						}
						selectedItemLabel={getLocalizableLabel(
							creationLanguageId as Liferay.Language.Locale,
							selectedObjectRelationship?.label,
							selectedObjectRelationship?.name
						)}
					/>
				)}
		</>
	);
}

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

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {openModal, openToast} from 'frontend-js-web';
import React from 'react';

import {ConfirmUnpublishAPIApplicationModalContent} from '../modals/ConfirmUnpublishAPIApplicationModalContent';
import {CreateAPIApplicationModalContent} from '../modals/CreateAPIApplicationModalContent';
import {DeleteAPIApplicationModalContent} from '../modals/DeleteAPIApplicationModalContent';
import {updateData} from '../utils/fetchUtil';
import {getAPIApplicationsFDSProps} from './fdsUtils/fdsProps';

interface APIApplicationsTableProps {
	apiURLPaths: APIURLPaths;
	editURL: string;
	portletId: string;
	readOnly: boolean;
}

export default function APIApplicationsTable({
	apiURLPaths,
	editURL,
	portletId,
	readOnly,
}: APIApplicationsTableProps) {
	const changePublicationStatus = async (
		itemData: APIApplicationItem,
		loadData: voidReturn
	) => {
		const url = itemData.actions.update.href;
		const onError = (error: string) => {
			openToast({
				message: error,
				type: 'danger',
			});
		};

		if (itemData.applicationStatus.key === 'unpublished') {
			await updateData({
				dataToUpdate: {
					applicationStatus: {key: 'published'},
				},
				onError,
				onSuccess: () => {
					loadData();
					openToast({
						message: Liferay.Language.get(
							'api-application-was-published'
						),
						type: 'success',
					});
				},
				url,
			});
		}
		else if (itemData.applicationStatus.key === 'published') {
			openModal({
				center: true,
				contentComponent: ({closeModal}: {closeModal: voidReturn}) =>
					ConfirmUnpublishAPIApplicationModalContent({
						closeModal,
						handlePublish: () => {
							updateData({
								dataToUpdate: {
									applicationStatus: {key: 'unpublished'},
								},
								onError,
								onSuccess: () => {
									closeModal();
									loadData();
									openToast({
										message: Liferay.Language.get(
											'api-application-was-unpublished'
										),
										type: 'success',
									});
								},
								url,
							});
						},
					}),
				id: 'ConfirmUnpublishAPIApplicationModal',
				size: 'md',
				status: 'warning',
			});
		}
	};

	const createAPIApplication = {
		label: Liferay.Language.get('add-new-api-application'),
		onClick: ({loadData}: {loadData: voidReturn}) => {
			openModal({
				center: true,
				contentComponent: ({closeModal}: {closeModal: voidReturn}) =>
					CreateAPIApplicationModalContent({
						apiApplicationsURLPath: apiURLPaths.applications,
						closeModal,
						loadData,
					}),
				id: 'createAPIApplicationModal',
				size: 'md',
			});
		},
	};

	const deleteAPIApplication = (
		itemData: APIApplicationItem,
		loadData: voidReturn
	) => {
		openModal({
			center: true,
			contentComponent: ({closeModal}: {closeModal: voidReturn}) =>
				DeleteAPIApplicationModalContent({
					closeModal,
					itemData,
					loadData,
				}),
			id: 'deleteAPIApplicationModal',
			size: 'md',
			status: 'danger',
		});
	};

	function onActionDropdownItemClick({
		action,
		itemData,
		loadData,
	}: FDSItem<APIApplicationItem>) {
		if (action.id === 'deleteAPIApplication') {
			deleteAPIApplication(itemData, loadData);
		}
		else if (action.id === 'changePublicationStatus') {
			changePublicationStatus(itemData, loadData);
		}
	}

	return (
		<FrontendDataSet
			{...getAPIApplicationsFDSProps(
				apiURLPaths.applications,
				editURL,
				portletId
			)}
			creationMenu={{
				primaryItems: readOnly ? [] : [createAPIApplication],
			}}
			onActionDropdownItemClick={onActionDropdownItemClick}
		/>
	);
}

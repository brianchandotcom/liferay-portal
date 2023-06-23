/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 r*
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import ClayCard from '@clayui/card';
import {Heading} from '@clayui/core';
import ClayLayout from '@clayui/layout';
import ClayModal from '@clayui/modal';
import ClayNavigationBar from '@clayui/navigation-bar';
import {fetch, openToast} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {APIApplicationManagementToolbar} from './APIApplicationManagementToolbar';
import BaseAPIApplicationField from './baseComponents/BaseAPIApplicationFields';
import {fetchJSON, headers} from './utils/fetchUtil';
import {getCurrentURLParamValue} from './utils/urlUtil';

import '../../css/main.scss';

interface EditAPIApplicationProps {
	apiURLPaths: APIURLPaths;
	portletId: string;
}

type ApplicationStatus = {
	key: 'published' | 'unpublished';
};

type DataError = {
	baseURL: boolean;
	title: boolean;
};

type Data = {
	actions: Actions;
	applicationStatus: ApplicationStatus;
	baseURL: string;
	description: string;
	title: string;
	version: string;
};

export default function EditAPIApplication({
	apiURLPaths,
	portletId,
}: EditAPIApplicationProps) {
	const currentAPIApplicationID = getCurrentURLParamValue({
		paramSufix: 'apiApplicationId',
		portletId,
	});

	const [data, setData] = useState<ItemData>();
	const [title, setTitle] = useState<string>('');
	const [activeTab, setActiveTab] = useState(
		getCurrentURLParamValue(
			{
				paramSufix: 'editAPIApplicationNav',
				portletId,
			} || 'details'
		)
	);
	const [displayError, setDisplayError] = useState<DataError>({
		baseURL: false,
		title: false,
	});

	const fetchAPIApplication = () => {
		fetchJSON<ItemData>({
			input: apiURLPaths.applications + currentAPIApplicationID,
		}).then((response) => {
			if (response.id.toString() === currentAPIApplicationID) {
				setData(response);
				setTitle(response.title);
			}
		});
	};

	useEffect(() => {
		fetchAPIApplication();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	async function updateData(
		dataToUpdate: Partial<Data>,
		successMessage: string
	) {
		fetch(data!.actions.update.href, {
			body: JSON.stringify(dataToUpdate),
			headers,
			method: data!.actions.update.method,
		})
			.then((response) => {
				if (response.ok) {
					openToast({
						message: successMessage,
						type: 'success',
					});
					fetchAPIApplication();
				}
				else {
					return response.json();
				}
			})
			.then((responseJson) => {
				if (responseJson) {
					throw new Error(responseJson.title);
				}
			})
			.catch((error) => {
				openToast({
					message: error,
					type: 'danger',
				});
			});
	}

	function validateData() {
		let isDataValid = true;
		const mandatoryFields = ['baseURL', 'title'];

		if (!Object.keys(data!).length) {
			const errors = mandatoryFields.reduce(
				(errors, field) => ({...errors, [field]: true}),
				{}
			);
			setDisplayError(errors as DataError);

			isDataValid = false;
		}
		else {
			mandatoryFields.forEach((field) => {
				if (data![field as keyof Data]) {
					setDisplayError((previousErrors) => ({
						...previousErrors,
						[field]: false,
					}));
				}
				else {
					setDisplayError((previousErrors) => ({
						...previousErrors,
						[field]: true,
					}));
					isDataValid = false;
				}
			});
		}

		return isDataValid;
	}

	const handleUpdate = (
		applicationStatusKey: 'published' | 'unpublished',
		successMessage: string
	) => {
		const isDataValid = validateData();

		if (data && isDataValid) {
			updateData(
				{
					applicationStatus: {key: applicationStatusKey},
					baseURL: data.baseURL,
					description: data.description,
					title: data.title,
				},
				successMessage
			);
		}
	};

	return data ? (
		<>
			<APIApplicationManagementToolbar
				itemData={data}
				onSave={() =>
					handleUpdate(
						'unpublished',
						Liferay.Language.get('api-application-changes-saved')
					)
				}
				title={title}
			/>
			<ClayNavigationBar triggerLabel={activeTab as string}>
				<ClayNavigationBar.Item active={activeTab === 'details'}>
					<ClayButton onClick={() => setActiveTab('details')}>
						{Liferay.Language.get('details')}
					</ClayButton>
				</ClayNavigationBar.Item>

				<ClayNavigationBar.Item active={activeTab === 'endpoints'}>
					<ClayButton
						disabled
						onClick={() => setActiveTab('endpoints')}
					>
						{Liferay.Language.get('endpoints')}
					</ClayButton>
				</ClayNavigationBar.Item>

				<ClayNavigationBar.Item active={activeTab === 'schemas'}>
					<ClayButton
						disabled
						onClick={() => setActiveTab('schemas')}
					>
						{Liferay.Language.get('schemas')}
					</ClayButton>
				</ClayNavigationBar.Item>
			</ClayNavigationBar>
			<ClayLayout.Container className="api-app-details mt-5">
				<ClayCard className="pt-2">
					<ClayModal.Header withTitle={false}>
						<Heading fontSize={5} level={3} weight="semi-bold">
							{Liferay.Language.get('details')}
						</Heading>
					</ClayModal.Header>

					<ClayCard.Body>
						<BaseAPIApplicationField
							data={data as Data}
							displayError={displayError}
							setData={setData as voidReturn}
						/>
					</ClayCard.Body>
				</ClayCard>
			</ClayLayout.Container>
		</>
	) : null;
}

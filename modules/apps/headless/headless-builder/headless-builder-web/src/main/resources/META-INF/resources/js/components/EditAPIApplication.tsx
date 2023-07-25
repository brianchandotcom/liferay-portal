/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayCard from '@clayui/card';
import {Heading} from '@clayui/core';
import ClayLayout from '@clayui/layout';
import ClayModal from '@clayui/modal';
import ClayNavigationBar from '@clayui/navigation-bar';
import {openToast} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import APIApplicationsEndpointsTable from '../components/FDS/APIApplicationsEndpointsTable';
import APIApplicationsSchemasTable from '../components/FDS/APIApplicationsSchemasTable';
import {APIApplicationManagementToolbar} from './APIApplicationManagementToolbar';
import BaseAPIApplicationField from './baseComponents/BaseAPIApplicationFields';
import {fetchJSON, updateData} from './utils/fetchUtil';
import {getCurrentURLParamValue, updateHistory} from './utils/urlUtil';

import '../../css/main.scss';

interface EditAPIApplicationProps {
	apiURLPaths: APIURLPaths;
	basePath: string;
	portletId: string;
}

type DataError = {
	baseURL: boolean;
	title: boolean;
};

export default function EditAPIApplication({
	apiURLPaths,
	basePath,
	portletId,
}: EditAPIApplicationProps) {
	const currentAPIApplicationID = getCurrentURLParamValue({
		paramSufix: 'apiApplicationId',
		portletId,
	});

	const [data, setData] = useState<APIApplicationItem>();
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

	useEffect(() => {
		for (const key in data) {
			if (data[key as keyof APIApplicationItem] !== '') {
				setDisplayError((previousErrors) => ({
					...previousErrors,
					[key]: false,
				}));
			}
		}
	}, [data]);

	const fetchAPIApplication = () => {
		fetchJSON<APIApplicationItem>({
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

	const handleNavigate = (nav: 'details' | 'endpoints' | 'schemas') => {
		updateHistory({navState: nav, portletId});
		setActiveTab(nav);
	};

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
				if (data![field as keyof APIApplicationItem]) {
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

	const handleUpdate = async ({
		applicationStatusKey,
		successMessage,
	}: {
		applicationStatusKey: 'published' | 'unpublished';
		successMessage: string;
	}) => {
		const isDataValid = validateData();

		if (data && isDataValid) {
			await updateData({
				dataToUpdate: {
					applicationStatus: {key: applicationStatusKey},
					baseURL: data.baseURL,
					description: data.description,
					title: data.title,
				},
				onError: (error: string) => {
					openToast({
						message: error,
						type: 'danger',
					});
				},
				onSuccess: () => {
					openToast({
						message: successMessage,
						type: 'success',
					});
					fetchAPIApplication();
				},
				url: data.actions.update.href,
			});
		}
	};

	return data ? (
		<>
			<APIApplicationManagementToolbar
				hideButtons={activeTab !== 'details'}
				itemData={data}
				onPublish={() =>
					handleUpdate({
						applicationStatusKey: 'published',
						successMessage: Liferay.Language.get(
							'api-application-was-published'
						),
					})
				}
				onSave={() =>
					handleUpdate({
						applicationStatusKey: 'unpublished',
						successMessage: Liferay.Language.get(
							'api-application-changes-were-saved'
						),
					})
				}
				title={title}
			/>
			<ClayNavigationBar triggerLabel={activeTab as string}>
				<ClayNavigationBar.Item active={activeTab === 'details'}>
					<ClayButton onClick={() => handleNavigate('details')}>
						{Liferay.Language.get('details')}
					</ClayButton>
				</ClayNavigationBar.Item>

				<ClayNavigationBar.Item active={activeTab === 'endpoints'}>
					<ClayButton onClick={() => handleNavigate('endpoints')}>
						{Liferay.Language.get('endpoints')}
					</ClayButton>
				</ClayNavigationBar.Item>

				<ClayNavigationBar.Item active={activeTab === 'schemas'}>
					<ClayButton onClick={() => handleNavigate('schemas')}>
						{Liferay.Language.get('schemas')}
					</ClayButton>
				</ClayNavigationBar.Item>
			</ClayNavigationBar>
			{activeTab === 'details' && (
				<ClayLayout.Container className="api-app-details mt-5">
					<ClayCard className="pt-2">
						<ClayModal.Header withTitle={false}>
							<Heading fontSize={5} level={3} weight="semi-bold">
								{Liferay.Language.get('details')}
							</Heading>
						</ClayModal.Header>

						<ClayCard.Body>
							<BaseAPIApplicationField
								basePath={basePath}
								data={data as APIApplicationItem}
								displayError={displayError}
								setData={setData as voidReturn}
							/>
						</ClayCard.Body>
					</ClayCard>
				</ClayLayout.Container>
			)}
			{activeTab === 'endpoints' && (
				<APIApplicationsEndpointsTable
					apiApplicationBaseURL={data.baseURL}
					apiURLPaths={apiURLPaths}
					currentApplicationId={currentAPIApplicationID}
					portletId={portletId}
					readOnly={false}
				/>
			)}
			{activeTab === 'schemas' && (
				<APIApplicationsSchemasTable
					apiURLPaths={apiURLPaths}
					currentApplicationId={currentAPIApplicationID}
					portletId={portletId}
					readOnly={false}
				/>
			)}
		</>
	) : null;
}

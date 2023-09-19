/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import {fetch, openToast} from 'frontend-js-web';
import React, {Dispatch, SetStateAction, useEffect, useState} from 'react';

import BaseAPIEndpointFields from '../baseComponents/BaseAPIEndpointFields';
import {headers} from '../utils/fetchUtil';
import {beginStringWithForwardSlash} from '../utils/string';

interface CreateAPIEndpointModalProps {
	apiApplicationBaseURL: string;
	apiEndpointsURLPath: string;
	basePath: string;
	closeModal: voidReturn;
	currentAPIApplicationId: string | null;
	loadData: voidReturn;
	setMainEndpointNav: Dispatch<SetStateAction<MainEndpointNav>>;
}

export function CreateAPIEndpointModalContent({
	apiApplicationBaseURL,
	apiEndpointsURLPath,
	basePath,
	closeModal,
	currentAPIApplicationId,
	loadData,
	setMainEndpointNav,
}: CreateAPIEndpointModalProps) {
	const [localUIData, setLocalUIData] = useState<APIEndpointUIData>({
		description: '',
		path: '',
		scope: {key: '', name: ''},
	});
	const [displayError, setDisplayError] = useState<EndpointDataError>({
		description: false,
		path: false,
		scope: false,
	});

	useEffect(() => {
		for (const key in localUIData) {
			if (localUIData[key as keyof APIEndpointUIData] !== '') {
				setDisplayError((previousErrors) => ({
					...previousErrors,
					[key]: false,
				}));
			}
		}
	}, [localUIData]);

	async function postData() {
		fetch(apiEndpointsURLPath, {
			body: JSON.stringify({
				...localUIData,
				applicationStatus: {key: 'unpublished'},
				name: localUIData.path,
				path: beginStringWithForwardSlash(localUIData.path),
				r_apiApplicationToAPIEndpoints_c_apiApplicationId: currentAPIApplicationId,
				scope: {key: localUIData.scope.key},
				version: '1.0',
			}),
			headers,
			method: 'POST',
		})
			.then((response) => {
				if (response.ok) {
					return response.json();
				}
				else {
					throw response.json();
				}
			})
			.then((responseJSON) => {
				loadData();
				closeModal();
				setMainEndpointNav({edit: responseJSON.id});
				openToast({
					message: Liferay.Language.get(
						'new-api-application-endpoint-was-created'
					),
					type: 'success',
				});
			})
			.catch((error) => {
				error.then((response: {message: string; title: string}) => {
					{
						openToast({
							message: response.title ?? response.message,
							type: 'danger',
						});
					}
				});
			});
	}

	function validateData() {
		let isDataValid = true;
		const mandatoryFields = ['scope', 'path', 'description'];

		if (!Object.keys(localUIData).length) {
			const errors = mandatoryFields.reduce(
				(errors, field) => ({...errors, [field]: true}),
				{}
			);
			setDisplayError(errors as EndpointDataError);

			isDataValid = false;
		}
		else {
			mandatoryFields.forEach((field) => {
				if (localUIData[field as keyof APIEndpointUIData]) {
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

	const handleCreate = () => {
		const isDataValid = validateData();

		if (isDataValid) {
			postData();
		}
		else {
			return;
		}
	};

	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('new-api-endpoint')}
			</ClayModal.Header>

			<div className="modal-body">
				<BaseAPIEndpointFields
					apiApplicationBaseURL={apiApplicationBaseURL}
					basePath={basePath}
					data={localUIData}
					displayError={displayError}
					setData={setLocalUIData}
				/>
			</div>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							id="modalCancelButton"
							onClick={closeModal}
							type="button"
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="primary"
							id="modalCreateButton"
							onClick={handleCreate}
							type="button"
						>
							{Liferay.Language.get('create')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
}

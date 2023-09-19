/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBreadcrumb from '@clayui/breadcrumb';
import ClayCard from '@clayui/card';
import ClayTabs from '@clayui/tabs';
import {openModal, openToast} from 'frontend-js-web';
import React, {
	Dispatch,
	SetStateAction,
	useCallback,
	useContext,
	useEffect,
	useState,
} from 'react';

import {EditAPIApplicationContext} from './EditAPIApplicationContext';
import BaseAPIEndpointFields from './baseComponents/BaseAPIEndpointFields';
import {CancelEditAPIApplicationModalContent} from './modals/CancelEditAPIApplicationModalContent';
import {hasDataChanged, resetToFetched} from './utils/dataUtils';
import {fetchJSON, updateData} from './utils/fetchUtil';

import '../../css/main.scss';

interface EditAPIEndpointProps {
	apiApplicationBaseURL: string;
	apiURLPaths: APIURLPaths;
	basePath: string;
	currentAPIApplicationId: string;
	endpointId: number;
	setMainEndpointNav: Dispatch<SetStateAction<MainEndpointNav>>;
	setManagementButtonsProps: Dispatch<SetStateAction<ManagementButtonsProps>>;
	setStatus: Dispatch<SetStateAction<ApplicationStatusKeys>>;
	setTitle: Dispatch<SetStateAction<string>>;
}

export default function EditAPIEndpoint({
	apiApplicationBaseURL,
	apiURLPaths,
	basePath,
	currentAPIApplicationId,
	endpointId,
	setMainEndpointNav,
	setManagementButtonsProps,
	setStatus,
	setTitle,
}: EditAPIEndpointProps) {
	const {
		fetchedData,
		setFetchedData,
		setHideManagementButtons,
		setIsDataUnsaved,
	} = useContext(EditAPIApplicationContext);

	const [activeTab, setActiveTab] = useState(0);
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

	const fetchAPIEndpoint = () => {
		fetchJSON<APIEndpointItem>({
			input: apiURLPaths.endpoints + endpointId,
		}).then((response) => {
			if (response.id === endpointId) {
				setFetchedData((previous) => ({
					...previous,
					apiEndpoint: response,
				}));

				setLocalUIData({
					description: response.description,
					path: response.path,
					scope: response.scope,
				});
			}
		});
	};

	const resetLocalUIData = () => {
		if (fetchedData.apiEndpoint) {
			setLocalUIData(
				resetToFetched<APIEndpointItem, APIEndpointUIData>({
					fetchedEntityData: fetchedData.apiEndpoint,
					localUIData,
				})
			);
		}
	};

	function validateData() {
		let isDataValid = true;
		const mandatoryFields = ['name'];

		if (!Object.keys(localUIData!).length) {
			const errors = mandatoryFields.reduce(
				(errors, field) => ({...errors, [field]: true}),
				{}
			);
			setDisplayError(errors as EndpointDataError);

			isDataValid = false;
		}
		else {
			mandatoryFields.forEach((field) => {
				if (localUIData![field as keyof APIEndpointUIData]) {
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

	const handleUpdate = useCallback(
		({successMessage}: {successMessage: string}) => {
			const isDataValid = validateData();

			if (localUIData && isDataValid && fetchedData?.apiEndpoint) {
				updateData<APIEndpointUIData>({
					dataToUpdate: {
						description: localUIData.description,
						path: localUIData.path,
						scope: localUIData.scope,
					},
					method: 'PATCH',
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
						fetchAPIEndpoint();
					},
					url: fetchedData.apiEndpoint.actions.update.href,
				});
			}
		},

		// eslint-disable-next-line react-hooks/exhaustive-deps
		[localUIData]
	);

	const handlePublish = ({successMessage}: {successMessage: string}) => {
		const isDataValid = validateData();
		if (localUIData && isDataValid) {
			updateData<APIApplicationItem>({
				dataToUpdate: {
					applicationStatus: {key: 'published'},
				},
				method: 'PATCH',
				onError: (error: string) => {
					openToast({
						message: error,
						type: 'danger',
					});
				},
				onSuccess: (responseJSON: APIApplicationItem) => {
					setFetchedData((previous) => ({
						...previous,
						apiApplication: responseJSON,
					}));
					setStatus(responseJSON.applicationStatus.key);
					setTitle(responseJSON.title);
					openToast({
						message: successMessage,
						type: 'success',
					});
				},
				url: apiURLPaths.applications + currentAPIApplicationId,
			});
		}
	};

	const handleCancel = () => {
		if (
			fetchedData?.apiEndpoint &&
			hasDataChanged({
				fetchedEntityData: fetchedData.apiEndpoint,
				localUIData,
			})
		) {
			openModal({
				center: true,
				contentComponent: ({closeModal}: {closeModal: voidReturn}) =>
					CancelEditAPIApplicationModalContent({
						closeModal,
						onConfirm: () => {
							resetLocalUIData();
							setIsDataUnsaved(false);
							setMainEndpointNav('list');
						},
					}),
				id: 'confirmCancelEditModal',
				size: 'md',
				status: 'warning',
			});
		}
		else {
			setMainEndpointNav('list');
		}
	};

	useEffect(() => {
		setHideManagementButtons(false);

		fetchAPIEndpoint();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		if (fetchedData.apiEndpoint) {
			setIsDataUnsaved(
				hasDataChanged({
					fetchedEntityData: fetchedData.apiEndpoint,
					localUIData,
				})
			);
		}

		setManagementButtonsProps({
			cancel: {onClick: handleCancel, visible: true},
			publish: {
				onClick: () => {
					handlePublish({
						successMessage: Liferay.Language.get(
							'api-application-was-published'
						),
					});
					handleUpdate({
						successMessage: Liferay.Language.get(
							'api-endpoint-changes-were-saved'
						),
					});
				},
				visible: true,
			},
			save: {
				onClick: () =>
					handleUpdate({
						successMessage: Liferay.Language.get(
							'api-endpoint-changes-were-saved'
						),
					}),
				visible:
					fetchedData.apiApplication?.applicationStatus.key ===
					'unpublished',
			},
		});

		for (const key in localUIData) {
			if (localUIData[key as keyof APIEndpointUIData] !== '') {
				setDisplayError((previousErrors) => ({
					...previousErrors,
					[key]: false,
				}));
			}
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [localUIData]);

	const APIEndpointHttpMethodName = fetchedData.apiEndpoint?.httpMethod.name?.toUpperCase();
	const editAPIEndpointBreadcrumbLabel = `${
		APIEndpointHttpMethodName ?? APIEndpointHttpMethodName
	} ${fetchedData.apiEndpoint?.path ?? fetchedData.apiEndpoint?.path}`;

	return (
		<div className="container-fluid container-fluid-max-xl mt-3">
			<ClayBreadcrumb
				className="api-builder-navigation-breadcrumb"
				items={[
					{
						label: Liferay.Language.get('endpoints'),
						onClick: () => handleCancel(),
					},
					{
						active: true,
						label: editAPIEndpointBreadcrumbLabel,
					},
				]}
			/>

			<ClayCard className="mt-3 pt-2">
				<ClayTabs
					active={activeTab}
					className="mt-3"
					onActiveChange={setActiveTab}
				>
					<ClayTabs.Item
						innerProps={{
							'aria-controls': 'tabpanel-1',
						}}
					>
						{Liferay.Language.get('info')}
					</ClayTabs.Item>
				</ClayTabs>

				<ClayTabs.Content>
					<ClayTabs.TabPane
						aria-label={Liferay.Language.get('information-tab')}
						className="info-tab"
					>
						<ClayCard.Body>
							<BaseAPIEndpointFields
								apiApplicationBaseURL={apiApplicationBaseURL}
								basePath={basePath}
								data={localUIData}
								displayError={displayError}
								editMode={true}
								setData={setLocalUIData}
							/>
						</ClayCard.Body>
					</ClayTabs.TabPane>
				</ClayTabs.Content>
			</ClayCard>
		</div>
	);
}

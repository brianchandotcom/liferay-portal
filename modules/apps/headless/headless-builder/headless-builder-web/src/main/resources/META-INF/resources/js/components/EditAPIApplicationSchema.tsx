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
import BaseAPISchemaFields from './baseComponents/BaseAPISchemaFields';
import {CancelEditAPIApplicationModalContent} from './modals/CancelEditAPIApplicationModalContent';
import {hasDataChanged, resetToFetched} from './utils/dataUtils';
import {fetchJSON, updateData} from './utils/fetchUtil';

import '../../css/main.scss';

interface EditAPIApplicationSchemaProps {
	apiURLPaths: APIURLPaths;
	currentAPIApplicationId: string;
	schemaId: number;
	setMainSchemaNav: Dispatch<SetStateAction<MainSchemaNav>>;
	setManagementButtonsProps: Dispatch<SetStateAction<ManagementButtonsProps>>;
	setStatus: Dispatch<SetStateAction<ApplicationStatusKeys>>;
	setTitle: Dispatch<SetStateAction<string>>;
}

type DataError = {
	description: boolean;
	mainObjectDefinitionERC: boolean;
	name: boolean;
};

export default function EditAPIApplicationSchema({
	apiURLPaths,
	currentAPIApplicationId,
	schemaId,
	setMainSchemaNav,
	setManagementButtonsProps,
	setStatus,
	setTitle,
}: EditAPIApplicationSchemaProps) {
	const {
		fetchedData,
		setFetchedData,
		setHideManagementButtons,
		setIsDataUnsaved,
	} = useContext(EditAPIApplicationContext);

	const [activeTab, setActiveTab] = useState(0);
	const [localUIData, setLocalUIData] = useState<APISchemaUIData>({
		description: '',
		mainObjectDefinitionERC: '',
		name: '',
	});
	const [displayError, setDisplayError] = useState<DataError>({
		description: false,
		mainObjectDefinitionERC: false,
		name: false,
	});

	const fetchAPISchema = () => {
		fetchJSON<APIApplicationSchemaItem>({
			input: apiURLPaths.schemas + schemaId,
		}).then((response) => {
			if (response.id === schemaId) {
				setFetchedData((previous) => ({
					...previous,
					apiSchema: response,
				}));

				setLocalUIData(response);
			}
		});
	};

	const resetLocalUIData = () => {
		if (fetchedData.apiSchema) {
			setLocalUIData(
				resetToFetched<APIApplicationSchemaItem, APISchemaUIData>({
					fetchedEntityData: fetchedData.apiSchema,
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
			setDisplayError(errors as DataError);

			isDataValid = false;
		}
		else {
			mandatoryFields.forEach((field) => {
				if (localUIData![field as keyof APISchemaUIData]) {
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

			if (localUIData && isDataValid && fetchedData?.apiSchema) {
				updateData<APIApplicationSchemaItem>({
					dataToUpdate: {
						description: localUIData.description,
						name: localUIData.name,
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
						fetchAPISchema();
					},
					url: fetchedData.apiSchema.actions.update.href,
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
			fetchedData?.apiSchema &&
			hasDataChanged({
				fetchedEntityData: fetchedData.apiSchema,
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
							setMainSchemaNav('list');
						},
					}),
				id: 'confirmCancelEditModal',
				size: 'md',
				status: 'warning',
			});
		}
		else {
			setMainSchemaNav('list');
		}
	};

	useEffect(() => {
		setHideManagementButtons(false);

		fetchAPISchema();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		if (fetchedData.apiSchema) {
			setIsDataUnsaved(
				hasDataChanged({
					fetchedEntityData: fetchedData.apiSchema,
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
							'api-schema-changes-were-saved'
						),
					});
				},
				visible: true,
			},
			save: {
				onClick: () =>
					handleUpdate({
						successMessage: Liferay.Language.get(
							'api-schema-changes-were-saved'
						),
					}),
				visible:
					fetchedData.apiApplication?.applicationStatus.key ===
					'unpublished',
			},
		});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [localUIData]);

	return (
		<div className="container-fluid container-fluid-max-xl mt-3">
			<ClayBreadcrumb
				className="api-builder-navigation-breadcrum"
				items={[
					{
						label: Liferay.Language.get('schemas'),
						onClick: () => handleCancel(),
					},
					{
						active: true,
						label: fetchedData.apiSchema?.name ?? localUIData.name,
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
							<BaseAPISchemaFields
								data={localUIData}
								disableObjectSelect
								displayError={displayError}
								setData={setLocalUIData}
							/>
						</ClayCard.Body>
					</ClayTabs.TabPane>
				</ClayTabs.Content>
			</ClayCard>
		</div>
	);
}

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import {
	API,
	ManagementToolbar,
	REQUIRED_MSG,
	invalidateRequired,
	openToast,
	useForm,
} from '@liferay/object-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {defaultLanguageId} from '../util/constants';

import './EditNotificationTemplate.scss';
import {BasicInfoContainer} from './BasicInfoContainer/BasicInfoContainer';
import ContentContainer from './ContentContainer/ContentContainer';
import DefinitionOfTermsContainer from './DefinitionOfTermsContainer/DefinitionOfTermsContainer';
import {SettingsContainer} from './SettingsContainer/SettingsContainer';

const HEADERS = new Headers({
	'Accept': 'application/json',
	'Content-Type': 'application/json',
});

export type NotificationTemplateError = {
	bcc?: string;
	body?: string;
	cc?: string;
	description?: string;
	from?: string;
	fromName?: string;
	name?: string;
	subject?: string;
	to?: string;
	type?: string;
};

interface EditNotificationTemplateProps {
	backURL: string;
	baseResourceURL: string;
	editorConfig: object;
	externalReferenceCode: string;
	notificationTemplateId: number;
	notificationTemplateType: string;
	portletNamespace: string;
}

export default function EditNotificationTemplate({
	backURL,
	baseResourceURL,
	editorConfig,
	externalReferenceCode,
	notificationTemplateId = 0,
	notificationTemplateType,
	portletNamespace,
}: EditNotificationTemplateProps) {
	notificationTemplateId = Number(notificationTemplateId);

	const [isSubmitted, setIsSubmitted] = useState(false);

	const [objectDefinitions, setObjectDefinitions] = useState<
		ObjectDefinition[]
	>([]);

	const [selectedLocale, setSelectedLocale] = useState<Locale>(
		Liferay.ThemeDisplay.getDefaultLanguageId
	);

	const [templateTitle, setTemplateTitle] = useState<string>('');

	const validate = (values: any) => {
		const errors: NotificationTemplateError = {};

		if (!values.name) {
			errors.name = REQUIRED_MSG;
		}

		if (!values.subject[defaultLanguageId]) {
			errors.subject = Liferay.Language.get('required');
		}

		if (notificationTemplateType === 'email' || values.type === 'email') {
			if (!values.recipients[0].from) {
				errors.from = REQUIRED_MSG;
			}

			if (!values.recipients[0].fromName[defaultLanguageId]) {
				errors.fromName = REQUIRED_MSG;
			}

			if (!values.recipients[0].to[defaultLanguageId]) {
				errors.to = REQUIRED_MSG;
			}
		}

		return errors;
	};

	const onSubmit = async (notification: NotificationTemplate) => {
		if (isSubmitted) {
			return;
		}

		setIsSubmitted(true);

		const response = await fetch(
			notificationTemplateId !== 0
				? `/o/notification/v1.0/notification-templates/${notificationTemplateId}`
				: '/o/notification/v1.0/notification-templates',
			{
				body: JSON.stringify(notification),
				headers: HEADERS,
				method: notificationTemplateId !== 0 ? 'PUT' : 'POST',
			}
		);

		if (response.ok) {
			openToast({
				message: Liferay.Language.get(
					'notification-template-was-saved-successfully'
				),
				type: 'success',
			});

			window.location.assign(document.referrer);
		}
		else if (response.status === 400) {
			const {title} = (await response.json()) as {
				title: string;
			};

			openToast({
				message: title,
				type: 'danger',
			});
		}
		else {
			openToast({
				message: Liferay.Language.get('an-error-occurred'),
				type: 'danger',
			});
		}
	};

	let recipientInitialValue: any;

	if (
		notificationTemplateType === '' ||
		notificationTemplateType === 'email'
	) {
		recipientInitialValue = [
			{
				bcc: '',
				cc: '',
				from: '',
				fromName: {
					[defaultLanguageId]: '',
				},
				singleRecipient: !Liferay.FeatureFlags['LPS-187854'],
				to: {
					[defaultLanguageId]: '',
				},
			} as EmailRecipients,
		];
	}
	else {
		recipientInitialValue = [];
	}

	const initialValues: NotificationTemplate = {
		attachmentObjectFieldIds: [],
		body: {
			[defaultLanguageId]: '',
		},
		description: '',
		editorType: 'richText' as editorTypeOptions,
		externalReferenceCode: '',
		name: '',
		objectDefinitionExternalReferenceCode: '',
		objectDefinitionId: 0,
		recipientType:
			notificationTemplateType === 'userNotification' ? 'term' : 'email',
		recipients: recipientInitialValue,
		subject: {
			[defaultLanguageId]: '',
		},
		type: notificationTemplateType,
	};

	const {errors, setValues, validateSubmit, values} = useForm({
		initialValues,
		onSubmit,
		validate,
	});

	useEffect(() => {
		const makeFetch = async () => {
			if (notificationTemplateId !== 0) {
				const {
					attachmentObjectFieldIds,
					body,
					description,
					editorType,
					externalReferenceCode,
					name,
					objectDefinitionExternalReferenceCode,
					objectDefinitionId,
					recipientType,
					recipients,
					subject,
					type,
				} = await API.getNotificationTemplateById(
					notificationTemplateId
				);

				setValues({
					...values,
					attachmentObjectFieldIds,
					body,
					description,
					editorType,
					externalReferenceCode,
					name,
					objectDefinitionExternalReferenceCode,
					objectDefinitionId,
					recipientType,
					recipients,
					subject,
					type,
				});

				setTemplateTitle(name);
			}
			else {
				setTemplateTitle(
					Liferay.Language.get('untitled-notification-template')
				);
			}
			const objectDefinitionsItems = await API.getObjectDefinitions();

			setObjectDefinitions(objectDefinitionsItems);
		};

		makeFetch();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [notificationTemplateId]);

	return (
		<ClayForm>
			<ManagementToolbar
				backURL={backURL}
				badgeClassName={
					values.type === 'email' ? 'label-success' : 'label-info'
				}
				badgeLabel={
					values.type === 'email'
						? Liferay.Language.get('email')
						: Liferay.Language.get('user-notification')
				}
				entityId={notificationTemplateId}
				externalReferenceCode={
					invalidateRequired(values.externalReferenceCode)
						? externalReferenceCode
						: values.externalReferenceCode
				}
				externalReferenceCodeSaveURL={`/o/notification/v1.0/notification-templates/${notificationTemplateId}`}
				hasPublishPermission={true}
				hasUpdatePermission={true}
				helpMessage={Liferay.Language.get(
					'internal-key-to-reference-the-notification-template'
				)}
				label={templateTitle}
				onExternalReferenceCodeChange={(value) => {
					setValues({
						externalReferenceCode: value,
					});
				}}
				onGetEntity={() =>
					API.getNotificationTemplateById(notificationTemplateId)
				}
				onSubmit={validateSubmit}
				portletNamespace={portletNamespace}
				showEntityDetails={notificationTemplateId !== 0}
			/>

			<div className="lfr__notification-template-container">
				<div className="lfr__notification-template-cards">
					<div className="row">
						<div className="col-lg-6 lfr__notification-template-card">
							<BasicInfoContainer
								errors={errors}
								setValues={setValues}
								values={values}
							/>
						</div>

						<div className="col-lg-6 lfr__notification-template-card">
							<SettingsContainer
								errors={errors}
								selectedLocale={selectedLocale}
								setValues={setValues}
								values={values}
							/>
						</div>
					</div>

					<ContentContainer
						baseResourceURL={baseResourceURL}
						editorConfig={editorConfig}
						errors={errors}
						objectDefinitions={objectDefinitions}
						selectedLocale={selectedLocale}
						setSelectedLocale={setSelectedLocale}
						setValues={setValues}
						values={values}
					/>

					<DefinitionOfTermsContainer
						baseResourceURL={baseResourceURL}
						objectDefinitions={objectDefinitions}
					/>
				</div>
			</div>
		</ClayForm>
	);
}

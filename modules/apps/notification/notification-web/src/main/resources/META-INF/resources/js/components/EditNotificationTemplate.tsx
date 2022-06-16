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

import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayManagementToolbar from '@clayui/management-toolbar';
import {
	Card,
	FormCustomSelect,
	Input,
	InputLocalized,
	RichTextLocalized,
	openToast,
	useForm,
} from '@liferay/object-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {
	availableLocales,
	defaultLanguageId,
	defaultLocale,
} from '../utils/locale';
import {DefinitionOfTerms} from './DefinitionOfTerms';

import './EditNotificationTemplate.scss';

const HEADERS = new Headers({
	'Accept': 'application/json',
	'Content-Type': 'application/json',
});

export default function EditNotificationTemplate({
	baseResourceURL,
	editorConfig,
	notificationTemplateId,
}: IProps) {
	notificationTemplateId = Number(notificationTemplateId);

	const initialValues = {
		bcc: '',
		body: {
			[defaultLanguageId]: '',
		},
		cc: '',
		description: '',
		from: '',
		fromName: {
			[defaultLanguageId]: '',
		},
		name: '',
		subject: {
			[defaultLanguageId]: '',
		},
		to: {
			[defaultLanguageId]: '',
		},
	};

	const [selectedLocale, setSelectedLocale] = useState(
		defaultLocale as {
			label: string;
			symbol: string;
		}
	);

	const validate = (values: any) => {
		const errors: {
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
		} = {};

		if (!values.name) {
			errors.name = Liferay.Language.get('required');
		}

		if (!values.from) {
			errors.from = Liferay.Language.get('required');
		}

		if (!values.fromName[defaultLanguageId]) {
			errors.fromName = Liferay.Language.get('required');
		}

		return errors;
	};

	const onSubmit = async (notification: TNotificationTemplate) => {
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

			window.history.back();
		}
		else if (response.status === 404) {
			openToast({
				message: Liferay.Language.get('an-error-occurred'),
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

	const {errors, handleSubmit, setValues, values} = useForm({
		initialValues,
		onSubmit,
		validate,
	});

	useEffect(() => {
		if (notificationTemplateId !== 0) {
			const makeFetch = async () => {
				const response = await fetch(
					`/o/notification/v1.0/notification-templates/${notificationTemplateId}`,
					{
						headers: HEADERS,
						method: 'GET',
					}
				);

				const {
					bcc,
					body,
					cc,
					description,
					from,
					fromName,
					name,
					subject,
					to,
				} = (await response.json()) as TNotificationTemplate;

				setValues({
					...values,
					bcc,
					body,
					cc,
					description,
					from,
					fromName,
					name,
					subject,
					to,
				});
			};

			makeFetch();
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [notificationTemplateId]);

	return (
		<ClayForm onSubmit={handleSubmit}>
			<ClayManagementToolbar className="lfr__notification-template-management-tollbar">
				<ClayManagementToolbar.ItemList>
					<h2>{Liferay.Language.get('notification-templates')}</h2>
				</ClayManagementToolbar.ItemList>

				<ClayManagementToolbar.ItemList>
					<ClayButton
						displayType="secondary"
						onClick={() => window.history.back()}
					>
						{Liferay.Language.get('cancel')}
					</ClayButton>

					<ClayButton className="inline-item-after" type="submit">
						{Liferay.Language.get('save')}
					</ClayButton>
				</ClayManagementToolbar.ItemList>
			</ClayManagementToolbar>

			<div className="lfr__notification-template-container">
				<div className="lfr__notification-template-cards">
					<div className="row">
						<div className="col-lg-6">
							<Card title={Liferay.Language.get('basic-info')}>
								<Input
									error={errors.name}
									label={Liferay.Language.get('name')}
									name="name"
									onChange={({target}) =>
										setValues({
											...values,
											name: target.value,
										})
									}
									required
									value={values.name}
								/>

								<Input
									component="textarea"
									label={Liferay.Language.get('description')}
									name="description"
									onChange={({target}) =>
										setValues({
											...values,
											description: target.value,
										})
									}
									type="text"
									value={values.description}
								/>

								<FormCustomSelect
									disabled
									label={Liferay.Language.get('type')}
									options={[]}
									value={Liferay.Language.get('email')}
								/>
							</Card>
						</div>

						<div className="col-lg-6">
							<Card title={Liferay.Language.get('settings')}>
								<InputLocalized
									defaultLanguageId={defaultLanguageId}
									label={Liferay.Language.get('to')}
									locales={availableLocales}
									name="to"
									onSelectedLocaleChange={setSelectedLocale}
									onTranslationsChange={(translation) => {
										setValues({
											...values,
											to: translation,
										});
									}}
									selectedLocale={selectedLocale}
									translations={values.to}
								/>

								<div className="row">
									<div className="col-lg-6">
										<Input
											label={Liferay.Language.get('cc')}
											name="cc"
											onChange={({target}) =>
												setValues({
													...values,
													cc: target.value,
												})
											}
											value={values.cc}
										/>
									</div>

									<div className="col-lg-6">
										<Input
											label={Liferay.Language.get('bcc')}
											name="bcc"
											onChange={({target}) =>
												setValues({
													...values,
													bcc: target.value,
												})
											}
											value={values.bcc}
										/>
									</div>
								</div>

								<div className="row">
									<div className="col-lg-6">
										<Input
											error={errors.from}
											label={Liferay.Language.get(
												'from-address'
											)}
											name="fromAddress"
											onChange={({target}) =>
												setValues({
													...values,
													from: target.value,
												})
											}
											required
											value={values.from}
										/>
									</div>

									<div className="col-lg-6">
										<InputLocalized
											defaultLanguageId={
												defaultLanguageId
											}
											error={errors.fromName}
											label={Liferay.Language.get(
												'from-name'
											)}
											locales={availableLocales}
											name="fromName"
											onSelectedLocaleChange={
												setSelectedLocale
											}
											onTranslationsChange={(
												translation
											) => {
												setValues({
													...values,
													fromName: translation,
												});
											}}
											required
											selectedLocale={selectedLocale}
											translations={values.fromName}
										/>
									</div>
								</div>
							</Card>
						</div>
					</div>

					<Card title={Liferay.Language.get('content')}>
						<InputLocalized
							defaultLanguageId={defaultLanguageId}
							label={Liferay.Language.get('subject')}
							locales={availableLocales}
							name="subject"
							onSelectedLocaleChange={setSelectedLocale}
							onTranslationsChange={(translation) => {
								setValues({
									...values,
									subject: translation,
								});
							}}
							selectedLocale={selectedLocale}
							translations={values.subject}
						/>

						<RichTextLocalized
							editorConfig={editorConfig}
							label={Liferay.Language.get('body')}
							locales={availableLocales}
							name="body"
							onSelectedLocaleChange={setSelectedLocale}
							onTranslationsChange={(translation) => {
								setValues({
									...values,
									body: translation,
								});
							}}
							selectedLocale={selectedLocale}
							translations={values.body}
						/>

						<DefinitionOfTerms baseResourceURL={baseResourceURL} />
					</Card>
				</div>
			</div>
		</ClayForm>
	);
}

interface IProps {
	baseResourceURL: string;
	editorConfig: string;
	notificationTemplateId: number;
}

type TNotificationTemplate = {
	bcc: string;
	body: LocalizedValue<string>;
	cc: string;
	description: string;
	from: string;
	fromName: LocalizedValue<string>;
	name: string;
	subject: LocalizedValue<string>;
	to: LocalizedValue<string>;
};

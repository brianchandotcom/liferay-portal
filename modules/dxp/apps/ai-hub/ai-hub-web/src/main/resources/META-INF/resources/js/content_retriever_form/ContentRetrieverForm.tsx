/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {Link} from '@clayui/toolbar/src/Link';
import {openToast} from '@liferay/object-js-components-web';
import {useFormik} from 'formik';
import React, {useEffect, useMemo, useState} from 'react';

import Toolbar from '../components/ToolBar';
import InlineTextInput from './components/inline_text_input/InlineTextInput';
import {postContentRetriever} from './services/ContentRetrieverService';

import './ContentRetriever.scss';

import Icon from '@clayui/icon';

import LocalizedTextarea from './components/localized_text_area';

export default function ContentRetrieverForm({backURL}: {backURL: string}) {
	const [shouldNavigate, setShouldNavigate] = useState(false);

	const availableLocales = useMemo(
		() =>
			Object.entries(Liferay.Language.available).map(
				([localeId, displayName]) => ({
					displayName,
					icon: localeId.replace(/_/g, '-').toLowerCase(),
					localeId: localeId as Liferay.Language.Locale,
				})
			),
		[]
	);

	const [selectedLocale, setSelectedLocale] =
		useState<Liferay.Language.Locale>(() => {
			const defaultLang = Liferay.ThemeDisplay.getDefaultLanguageId();

			return (
				availableLocales.find((l) => l.localeId === defaultLang)
					?.localeId ||
				availableLocales[0]?.localeId ||
				'en_US'
			);
		});

	useEffect(() => {
		if (shouldNavigate) {
			window.location.href = backURL;
		}
	}, [shouldNavigate, backURL]);

	const formik = useFormik({
		initialValues: {
			description_i18n: {},
			title_i18n: {},
			url: '',
		},
		onSubmit: async (values, {setSubmitting}) => {
			try {
				await postContentRetriever(values);

				openToast({
					message: Liferay.Language.get(
						'content-retriever-saved-successfully'
					),
					type: 'success',
				});

				setShouldNavigate(true);
			}
			catch (error) {
				console.error(error);

				openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					type: 'danger',
				});

				setSubmitting(false);
			}
		},
		validate: (values) => {
			const errors: {
				description_i18n?: string;
				title_i18n?: string;
				url?: string;
			} = {};

			if (!values.title_i18n || !Object.keys(values.title_i18n).length) {
				errors.title_i18n = Liferay.Language.get('name-is-required');
			}

			if (!values.url) {
				errors.url = Liferay.Language.get('url-is-required');
			}
			else if (!/^https?:\/\//.test(values.url)) {
				errors.url = Liferay.Language.get('enter-a-valid-url');
			}

			if (
				!values.description_i18n ||
				!Object.keys(values.description_i18n).length
			) {
				errors.description_i18n = Liferay.Language.get(
					'description-is-required'
				);
			}

			return errors;
		},
	});

	return (
		<>
			<Toolbar backURL={backURL} title={Liferay.Language.get('add-url')}>
				<Toolbar.Item>
					<Link
						aria-label={Liferay.Language.get('cancel')}
						borderless
						button
						displayType="secondary"
						href={backURL}
						small
					>
						{Liferay.Language.get('cancel')}
					</Link>
				</Toolbar.Item>

				<Toolbar.Item>
					<Button
						aria-labelledby="saveButton"
						data-title="Save Button"
						data-title-set-as-html
						disabled={formik.isSubmitting}
						onClick={formik.submitForm}
						size="sm"
					>
						{Liferay.Language.get('save')}
					</Button>
				</Toolbar.Item>
			</Toolbar>
			<ClayLayout.ContainerFluid className="content-retriever-container mt-5">
				<ClayForm onSubmit={formik.handleSubmit}>
					<InlineTextInput
						error={
							formik.touched.title_i18n
								? typeof formik.errors.title_i18n === 'string'
									? formik.errors.title_i18n
									: String(
											Object.values(
												formik.errors.title_i18n || {}
											)[0] || ''
										)
								: ''
						}
						name="title_i18n"
						onBlur={formik.handleBlur}
						onChange={(translations) =>
							formik.setFieldValue('title_i18n', translations)
						}
						placeholder={Liferay.Language.get('add-a-name')}
						translations={
							(formik.values
								.title_i18n as Liferay.Language.LocalizedValue<string>) ||
							{}
						}
					/>

					<div className="align-items-center content-retriever-url-field-container mt-4">
						<div className="content-retriever-url-field-label">
							<span className="mr-3">
								<Icon symbol="link" />
							</span>

							<label htmlFor="data-source-url">
								{Liferay.Language.get('external-website')}
							</label>
						</div>

						<ClayInput
							className={
								formik.touched.url && formik.errors.url
									? 'is-invalid'
									: ''
							}
							id="data-source-url"
							name="url"
							onBlur={formik.handleBlur}
							onChange={(
								event: React.ChangeEvent<HTMLInputElement>
							) =>
								formik.setFieldValue('url', event.target.value)
							}
							placeholder={Liferay.Language.get('add-a-url-here')}
							required={true}
							value={formik.values.url}
						/>
					</div>

					{formik.touched.url && formik.errors.url && (
						<div className="d-block invalid-feedback mb-4">
							{formik.errors.url}
						</div>
					)}

					<label className="mt-4" htmlFor="data-source-description">
						{Liferay.Language.get('description')}

						<span className="ml-1 reference-mark text-warning">
							<Icon symbol="asterisk" />
						</span>
					</label>

					<LocalizedTextarea
						availableLocales={availableLocales}
						error={
							formik.touched.description_i18n
								? typeof formik.errors.description_i18n ===
									'string'
									? formik.errors.description_i18n
									: String(
											Object.values(
												formik.errors
													.description_i18n || {}
											) || ''
										)
								: ''
						}
						fieldName="description_i18n"
						id="data-source-description"
						onBlur={formik.handleBlur}
						onSelectedLocaleChange={setSelectedLocale}
						onTranslationsChange={(translations) => {
							formik.setFieldValue(
								'description_i18n',
								translations
							);
						}}
						placeholder={Liferay.Language.get('add-a-description')}
						selectedLocale={selectedLocale}
						translations={
							(formik.values
								.description_i18n as LocalizedValue<string>) ||
							{}
						}
						value={formik.values.description_i18n}
					/>
				</ClayForm>
			</ClayLayout.ContainerFluid>
		</>
	);
}

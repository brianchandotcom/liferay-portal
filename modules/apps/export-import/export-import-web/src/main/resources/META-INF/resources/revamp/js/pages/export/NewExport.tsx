/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {Form, Formik, FormikValues} from 'formik';
import React from 'react';

import Footer from '../../components/Footer';
import {DateFilterValues} from '../../components/date_filter';
import {FormikDebug} from '../../components/forms/formik';
import {flattenContentSelection} from '../../utils/flattenContentSelection';
import {
	PortletDataHandlerSection,
	mockPortletDataHandlerSections,
} from '../../utils/mockPortletDataHandlerSections';
import DataSelection from './components/DataSelection';
import Setup from './components/Setup';

interface NewExportProps {
	backURL: string;
	exportPreviewAPIURL: string;
	sections?: PortletDataHandlerSection[];
}

export function NewExport({
	backURL,
	sections = mockPortletDataHandlerSections,
}: NewExportProps) {
	const handleApplyFilter = (filterValues: DateFilterValues) => {

		// eslint-disable-next-line no-console
		console.log('Filtering by:', filterValues);
	};

	return (
		<Formik
			initialValues={{
				contentSelection: undefined,
				filename: '',
			}}
			onSubmit={async (values) => {
				const flatValues = flattenContentSelection({
					contentSelection: values.contentSelection,
					sections,
				});

				// eslint-disable-next-line no-console
				console.log({
					contentSelection: values.contentSelection,
					filename: values.filename,
					flatValues,
				});
			}}
			validate={(values: FormikValues) => {
				const errors: {[key: string]: string} = {};

				if (!values?.filename) {
					errors.filename = Liferay.Language.get(
						'this-field-is-required'
					);
				}

				if (!values?.contentSelection) {
					errors.contentSelection = Liferay.Language.get(
						'please-select-at-least-one-entity-type-to-continue'
					);
				}

				return errors;
			}}
			validateOnMount
		>
			{(formik) => (
				<Form noValidate>
					<Setup />

					<DataSelection
						onApplyFilter={handleApplyFilter}
						sections={sections}
					/>

					<Footer
						actionButton={
							<ClayButton
								disabled={
									formik.isSubmitting || !formik.isValid
								}
								type="submit"
							>
								<span className="inline-item inline-item-before">
									<ClayIcon
										className="mr-1"
										symbol="export"
									/>
								</span>

								{Liferay.Language.get('export')}
							</ClayButton>
						}
						backURL={backURL}
					/>

					{process.env.NODE_ENV === 'development' && <FormikDebug />}
				</Form>
			)}
		</Formik>
	);
}

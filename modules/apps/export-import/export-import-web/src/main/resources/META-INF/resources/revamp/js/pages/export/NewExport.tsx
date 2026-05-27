/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {Form, Formik, FormikValues} from 'formik';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import Footer from '../../components/Footer';
import {
	DateFilterValues,
	Range,
	normalizeDateFilter,
} from '../../components/date_filter';
import {FormikDebug} from '../../components/forms/formik';
import {
	ExportPreviewParams,
	getExportPreview,
} from '../../services/getExportPreview';
import {postExportProcess} from '../../services/postExportProcess';
import {ExportPreview} from '../../types/exportImportPreview';
import {toRequestPortletDataHandlers} from '../../utils/toRequestPortletDataHandlers';
import DataSelection from './components/DataSelection';
import {PageTreeModalConfiguration} from './components/PageTreeModal';
import Setup from './components/Setup';

export function NewExport({
	backURL,
	exportPreview,
	exportPreviewAPIURL,
	exportProcessAPIURL,
	pageTreeModalConfiguration,
}: {
	backURL: string;
	exportPreview?: ExportPreview;
	exportPreviewAPIURL: string;
	exportProcessAPIURL: string;
	pageTreeModalConfiguration: PageTreeModalConfiguration;
}) {
	const [preview, setPreview] = useState<ExportPreview | undefined>(
		exportPreview
	);
	const [error, setError] = useState<string | null>(null);
	const [loading, setLoading] = useState(!exportPreview);
	const initialPreviewRef = useRef<ExportPreview | undefined>(exportPreview);

	const getPreview = useCallback(
		(exportPreviewParams: ExportPreviewParams) => {
			setLoading(true);
			setError(null);

			getExportPreview(exportPreviewParams).then((result) => {
				if (result.error !== null) {
					setError(result.error);
				}
				else {
					setPreview(result.data);

					if (!initialPreviewRef.current) {
						initialPreviewRef.current = result.data;
					}
				}

				setLoading(false);
			});
		},
		[]
	);

	useEffect(() => {
		if (exportPreview) {
			return;
		}

		getPreview({url: exportPreviewAPIURL});
	}, [exportPreview, exportPreviewAPIURL, getPreview]);

	if (error) {
		return <ClayAlert displayType="danger">{error}</ClayAlert>;
	}

	const sections = preview?.previewPortletDataHandlerSections ?? [];

	const handleApplyFilter = (filterValues: DateFilterValues) => {
		if (filterValues.range === Range.All && initialPreviewRef.current) {
			setPreview(initialPreviewRef.current);

			return;
		}

		getPreview({
			query: normalizeDateFilter(filterValues),
			url: exportPreviewAPIURL,
		});
	};

	return (
		<Formik
			initialValues={{
				contentSelection: undefined,
				dateFilter: {range: Range.All} as DateFilterValues,
				deletions: false,
				name: '',
				permissions: false,
			}}
			onSubmit={async (values) => {
				const result = await postExportProcess({
					exportProcessRequest: {
						...normalizeDateFilter(values.dateFilter),
						deletions: !!values.deletions,
						name: values.name,
						permissions: !!values.permissions,
						requestPortletDataHandlers:
							toRequestPortletDataHandlers(
								sections,
								values.contentSelection
							),
					},
					url: exportProcessAPIURL,
				});

				if (result.error) {
					Liferay.Util.openToast({
						message: result.error,
						type: 'danger',
					});

					return;
				}

				Liferay.Util.navigate(backURL);
			}}
			validate={(values: FormikValues) => {
				const errors: {[key: string]: string} = {};

				if (!values?.name) {
					errors.name = Liferay.Language.get(
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
						deletionCount={preview?.deletionCount}
						itemsCount={preview?.additionCount}
						loading={loading}
						onApplyFilter={handleApplyFilter}
						pageTreeModalConfiguration={pageTreeModalConfiguration}
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

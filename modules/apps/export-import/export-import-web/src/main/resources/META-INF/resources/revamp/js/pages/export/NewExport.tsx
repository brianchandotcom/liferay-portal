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
	FilterType,
	HOURS_BY_MODIFIED_LAST,
} from '../../components/date_filter';
import {FormikDebug} from '../../components/forms/formik';
import {
	ExportPreviewParams,
	ExportPreviewQuery,
	getExportPreview,
} from '../../services/getExportPreview';
import {ExportPreview} from '../../types/exportImportPreview';
import {flattenContentSelection} from '../../utils/flattenContentSelection';
import DataSelection from './components/DataSelection';
import Setup from './components/Setup';

function dateFilterToQuery(values: DateFilterValues): ExportPreviewQuery {
	if (values.filterType === FilterType.Last) {
		return {
			last: HOURS_BY_MODIFIED_LAST[values.modifiedLast],
			range: 'last',
		};
	}

	if (values.filterType === FilterType.Range) {
		return {
			endDate: new Date(values.toDate).toISOString(),
			range: 'dateRange',
			startDate: new Date(values.fromDate).toISOString(),
		};
	}

	return {range: 'all'};
}

export function NewExport({
	backURL,
	exportPreview,
	exportPreviewAPIURL,
}: {
	backURL: string;
	exportPreview?: ExportPreview;
	exportPreviewAPIURL: string;
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

	const sections = preview?.portletDataHandlerSections ?? [];

	const handleApplyFilter = (filterValues: DateFilterValues) => {
		if (
			filterValues.filterType === FilterType.All &&
			initialPreviewRef.current
		) {
			setPreview(initialPreviewRef.current);

			return;
		}

		getPreview({
			query: dateFilterToQuery(filterValues),
			url: exportPreviewAPIURL,
		});
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
						itemsCount={preview?.additionCount}
						loading={loading}
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

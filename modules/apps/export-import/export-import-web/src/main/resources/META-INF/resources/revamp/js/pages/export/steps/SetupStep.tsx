/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayLayout from '@clayui/layout';
import {
	ArrayHelpers,
	FieldArray,
	FormikContextType,
	FormikErrors,
	FormikTouched,
	FormikValues,
	useFormikContext,
} from 'formik';
import {sub} from 'frontend-js-web';
import React from 'react';

import {FieldCheckbox} from '../../../components/forms/FieldCheckbox';
import {FormikFieldText} from '../../../components/forms/FormikFields';
import {mockPorletDataHandlerSections} from '../../../utils/mockPorletDataHandlerSections';

export default function SetupStep() {
	const {errors, setFieldTouched, touched, values} =
		useFormikContext<FormikValues>();

	return (
		<>
			<ClayLayout.Sheet>
				<ClayLayout.SheetHeader className="mb-0">
					<div className="mb-2 sheet-title">
						{sub(
							Liferay.Language.get('x-details'),
							Liferay.Language.get('export')
						)}
					</div>

					<div className="sheet-text text-3">
						{Liferay.Language.get(
							'provide-a-descriptive-name-for-your-export'
						)}
					</div>
				</ClayLayout.SheetHeader>

				<FormikFieldText
					label={Liferay.Language.get('file-name')}
					name="filename"
					required
				/>
			</ClayLayout.Sheet>

			<ClayLayout.Sheet>
				<ClayLayout.SheetHeader className="mb-0">
					<div className="mb-2 sheet-title">
						{Liferay.Language.get('what-would-you-like-to-export')}
					</div>

					<div className="sheet-text text-3">
						{Liferay.Language.get(
							'select-all-the-entity-types-that-you-want-to-export.-please-select-at-least-one-to-continue'
						)}
					</div>
				</ClayLayout.SheetHeader>

				<FieldArray name="selectedSectionIds">
					{(arrayHelper: ArrayHelpers) => {
						return mockPorletDataHandlerSections.map(
							({name, portletEntries}, index) => (
								<FieldCheckbox
									checked={values?.selectedSectionIds?.includes(
										name
									)}
									description={portletEntries
										.map(
											(portletEntry) =>
												portletEntry.portletTitle
										)
										.join(', ')}
									key={name}
									label={name}
									name={`selectedSectionIds[${index}]`}
									onChange={(checked) => {
										checked
											? arrayHelper.push(name)
											: arrayHelper.remove(
													values.selectedSectionIds.indexOf(
														name
													)
												);

										setFieldTouched(
											'selectedSectionIds',
											true
										);
									}}
								/>
							)
						);
					}}
				</FieldArray>

				{touched.selectedSectionIds && errors.selectedSectionIds && (
					<ClayAlert
						displayType="danger"
						title={Liferay.Language.get('error-colon')}
					>
						{errors.selectedSectionIds as string}
					</ClayAlert>
				)}
			</ClayLayout.Sheet>
		</>
	);
}

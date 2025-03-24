/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import ClayMultiSelect from '@clayui/multi-select';
import ClaySticker from '@clayui/sticker';
import {useFormik} from 'formik';
import {openToast} from 'frontend-js-components-web';
import {fetch, navigate} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import SpaceService from '../../../structure_builder/services/SpaceService';
import {FieldText} from '../../components/forms';
import {required, validate} from '../../components/forms/validations';

export default function CreationTagModalContent({
	assetLibraryId,
	tagsURL,
}: {
	assetLibraryId?: string;
	tagsURL: string;
}) {
	const [availableSpaces, setAvailableSpaces] = useState<Space[]>([]);
	const [checkbox, setCheckbox] = useState(true);
	const [allSpaces, setAllSpaces] = useState<Space[]>([]);

	useEffect(() => {
		SpaceService.getSpaces().then((response) => {
			const spaces = response.map((space) => ({
				label: space.name,
				value: space.id,
			}));

			setAvailableSpaces(spaces);

			setAllSpaces([
				{
					label: 'All Spaces',
					value: response.map(({id}) => id),
				},
			]);
		});
	}, [assetLibraryId]);

	const {
		errors,
		handleChange,
		handleSubmit,
		resetForm,
		setFieldValue,
		touched,
		values,
	} = useFormik({
		initialValues: {
			assetLibraryIds: [],
			tagName: '',
		},
		onSubmit: (values) => {
			const url =
				'/o/headless-admin-taxonomy/v1.0/asset-libraries/' +
				assetLibraryId +
				'/keywords';

			const body = {
				name: values.tagName,
			};

			fetch(url, {
				body: JSON.stringify(body),
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				},
				method: 'POST',
			})
				.then((response) => {
					if (response.ok) {
						openToast({
							message: Liferay.Language.get(
								'your-request-completed-successfully'
							),
							title: Liferay.Language.get('success'),
							type: 'success',
						});
					}
					else {
						openToast({
							message: Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
							title: Liferay.Language.get('error'),
							type: 'danger',
						});
					}
				})
				.catch(() => {
					openToast({
						message: Liferay.Language.get(
							'an-unexpected-error-occurred'
						),
						title: Liferay.Language.get('error'),
						type: 'danger',
					});
				});
			resetForm();
			setCheckbox(true);
		},
		validate: (values) => {
			if (!checkbox) {
				validate(
					{
						assetLibraryIds: [required],
						tagName: [required],
					},
					values
				);
			}
		},
	});

	const isChecked = (itemValue: string) => {
		return (values.assetLibraryIds as string[]).includes(itemValue);
	};

	const handleCheckboxChange = (itemValue: any) => {
		setFieldValue(
			'assetLibraryIds',
			isChecked(itemValue)
				? values.assetLibraryIds.filter((id) => id !== itemValue)
				: [...values.assetLibraryIds, itemValue]
		);
	};

	useEffect(() => {
		if (checkbox) {
			setFieldValue(
				'assetLibraryIds',
				allSpaces.flatMap((item) => item.value)
			);
		}
		else {
			setFieldValue('assetLibraryIds', []);
		}
	}, [checkbox, allSpaces, setFieldValue]);

	return (
		<form onSubmit={handleSubmit}>
			<ClayModal.Header>
				{Liferay.Language.get('new-tag')}
			</ClayModal.Header>

			<ClayModal.Body>
				<FieldText
					errorMessage={touched.tagName ? errors.tagName : undefined}
					label={Liferay.Language.get('name')}
					name="tagName"
					onChange={handleChange}
					required
					value={values.tagName}
				/>

				<label htmlFor="multiSelect">
					{Liferay.Language.get('space')}

					<span className="ml-1 reference-mark">
						<ClayIcon symbol="asterisk" />
					</span>
				</label>

				{checkbox && (
					<ClayMultiSelect
						disabled={true}
						id="multiSelect"
						items={allSpaces}
					/>
				)}

				{!checkbox && (
					<ClayMultiSelect
						disabled={checkbox}
						id="multiSelect"
						loadingState={3}
						onChange={handleChange}
						onItemsChange={(items: any) => {
							setFieldValue(
								'assetLibraryIds',
								items.map((item: any) => item.value)
							);
						}}
						sourceItems={availableSpaces}
					>
						{(item) => (
							<ClayMultiSelect.Item
								key={item.value}
								textValue={item.label}
							>
								<div className="autofit-row autofit-row-center">
									<div className="autofit-col">
										<ClayCheckbox
											aria-label={item.label}
											checked={isChecked(item.value)}
											onChange={() => {
												handleCheckboxChange(
													item.value
												);
											}}
										/>
									</div>

									<div className="autofit-col">
										<ClaySticker
											displayType="outline-2"
											size="sm"
										>
											{item.label.charAt(0).toUpperCase()}
										</ClaySticker>
									</div>

									<div className="autofit-col">
										{item.label}
									</div>
								</div>
							</ClayMultiSelect.Item>
						)}
					</ClayMultiSelect>
				)}

				<div className="mt-2">
					<ClayCheckbox
						checked={checkbox}
						label={Liferay.Language.get(
							'make-this-tag-available-in-all-spaces'
						)}
						onChange={() => setCheckbox(!checkbox)}
					/>
				</div>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={() => navigate(tagsURL)}
							type="button"
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton displayType="secondary" type="submit">
							{Liferay.Language.get('save-and-add-another')}
						</ClayButton>

						<ClayButton
							displayType="primary"
							onClick={() => navigate(tagsURL)}
							type="submit"
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</form>
	);
}

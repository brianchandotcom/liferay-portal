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

import ClayAlert from '@clayui/alert';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayLayout from '@clayui/layout';
import {Fragment, useEffect, useState} from 'react';
import {Control, UseFormRegister, useFieldArray} from 'react-hook-form';
import {useParams} from 'react-router-dom';

import Form from '../../../../../components/Form';
import {useFetch} from '../../../../../hooks/useFetch';
import useFormModal from '../../../../../hooks/useFormModal';
import i18n from '../../../../../i18n';
import yupSchema from '../../../../../schema/yup';
import {
	APIResponse,
	TestrayFactor,
	testrayFactorCategoryRest,
	testrayFactorRest,
} from '../../../../../services/rest';
import {searchUtil} from '../../../../../util/search';
import FactorOptionsFormModal from '../../../../Standalone/FactorOptions/FactorOptionsFormModal';
import BuildSelectOptionsModal from '../BuildSelectOptionsModal';

export type BuildFormType = typeof yupSchema.build.__outputType;

export type CategoryOptions = {
	factorCategory: string;
	factorCategoryId: number;
	factorOption: string;
	factorOptionId: number;
};

type Category = {
	[key: number]: CategoryOptions;
};

type BuildFormRunProps = {
	categories: Category[];
	control: Control<BuildFormType>;
	register: UseFormRegister<BuildFormType>;
};

const BuildFormRun: React.FC<BuildFormRunProps> = ({
	categories,
	control,
	register,
}) => {
	const {modal: optionSelectModal} = useFormModal();
	const {modal: optionModal} = useFormModal();
	const [factorOptionsList, setFactorOptionsList] = useState<
		TestrayFactor[][]
	>([[] as any]);

	const {append, fields, remove, update} = useFieldArray({
		control,
		name: 'categories',
	});

	const {routineId} = useParams();

	const {data: factorsData} = useFetch<APIResponse<TestrayFactor>>(
		`${testrayFactorRest.resource}&filter=${searchUtil.eq(
			'routineId',
			routineId as string
		)}`,
		(response) => testrayFactorRest.transformDataFromList(response)
	);

	// eslint-disable-next-line react-hooks/exhaustive-deps
	const factorItems = factorsData?.items || [];

	useEffect(() => {
		if (factorItems.length) {
			testrayFactorCategoryRest
				.getFactoryCategoryItems(factorItems)
				.then(setFactorOptionsList);
		}
	}, [factorItems]);

	useEffect(() => {
		if (factorItems.length) {
			const runItem: Category = {};

			factorItems.forEach((factorItem, index) => {
				runItem[index] = {
					factorCategory: factorItem.factorCategory?.name as string,
					factorCategoryId: factorItem.factorCategory?.id as number,
					factorOption: factorItem.factorOption?.name as string,
					factorOptionId: factorItem.factorOption?.id as number,
				};
			});

			update(0, runItem);
		}
	}, [update, factorItems]);

	return (
		<>
			<h3>{i18n.translate('runs')}</h3>

			<Form.Divider />

			{factorItems.length ? (
				<ClayButton.Group className="mb-4">
					<ClayButton
						displayType="secondary"
						onClick={() => optionModal.open()}
					>
						{i18n.translate('add-option')}
					</ClayButton>

					<ClayButton
						className="ml-1"
						displayType="secondary"
						onClick={() => optionSelectModal.open()}
					>
						{i18n.translate('select-stacks')}
					</ClayButton>
				</ClayButton.Group>
			) : (
				<ClayAlert>
					{i18n.translate(
						'create-environment-factors-if-you-want-to-generate-runs'
					)}
				</ClayAlert>
			)}

			<ClayLayout.Row>
				{fields.map((field, index) => {
					return (
						<Fragment key={field.id}>
							<ClayLayout.Col size={12}>
								<ClayLayout.Row className="align-items-center d-flex justify-content-space-between">
									{factorItems.map(
										(factorItem, factorIndex) => {
											const factorOptions: any =
												factorOptionsList[
													factorIndex
												] || [];

											const currentCategory =
												categories[index];

											const categoryOptions =
												currentCategory[factorIndex] ||
												{};

											const factorOptionSelected =
												factorOptions.find(
													({id}: any) =>
														String(id) ===
														String(
															categoryOptions?.factorOptionId
														)
												) || {};

											return (
												<ClayLayout.Col
													key={factorIndex}
													size={3}
												>
													<input
														type="hidden"
														value={
															factorOptionSelected.name
														}
														{...register(
															`categories.${index}.${factorIndex}.factorOption` as const
														)}
													/>

													<input
														type="hidden"
														value={
															factorItem
																.factorCategory
																?.name
														}
														{...register(
															`categories.${index}.${factorIndex}.factorCategory` as const
														)}
													/>

													<input
														type="hidden"
														value={
															factorItem
																.factorCategory
																?.id
														}
														{...register(
															`categories.${index}.${factorIndex}.factorCategoryId` as const
														)}
													/>

													<Form.Select
														defaultValue={
															factorItem
																.factorOption
																?.id
														}
														label={
															factorItem
																.factorCategory
																?.name
														}
														name={`categories.${index}.${factorIndex}.factorOptionId`}
														options={factorOptions.map(
															({
																id,
																name,
															}: any) => ({
																label: name,
																value: id,
															})
														)}
														register={register}
														registerOptions={{
															onBlur: () => {
																update(index, {
																	...currentCategory,
																	[factorIndex]: {
																		...categoryOptions,
																		factorOption:
																			factorOptionSelected.name,
																	},
																});
															},
														}}
													/>
												</ClayLayout.Col>
											);
										}
									)}

									{!!factorItems.length && (
										<ClayLayout.Col className="d-flex justify-content-end">
											<ClayButtonWithIcon
												displayType="secondary"
												onClick={() =>
													append({} as const)
												}
												symbol="plus"
											/>

											{index !== 0 && (
												<ClayButtonWithIcon
													className="ml-1"
													displayType="secondary"
													onClick={() =>
														remove(index)
													}
													symbol="hr"
												/>
											)}
										</ClayLayout.Col>
									)}
								</ClayLayout.Row>

								<Form.Divider />
							</ClayLayout.Col>
						</Fragment>
					);
				})}
			</ClayLayout.Row>

			<FactorOptionsFormModal modal={optionModal} />

			<BuildSelectOptionsModal
				factorItems={factorItems}
				modal={optionSelectModal}
			/>
		</>
	);
};

export default BuildFormRun;

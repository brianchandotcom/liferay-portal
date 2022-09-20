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
import ClayButton from '@clayui/button';
import {useEffect, useMemo, useState} from 'react';
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
	TestrayFactorOption,
	testrayFactorCategoryRest,
	testrayFactorRest,
} from '../../../../../services/rest';
import {searchUtil} from '../../../../../util/search';
import FactorOptionsFormModal from '../../../../Standalone/FactorOptions/FactorOptionsFormModal';
import BuildSelectStacksModal, {FactorStack} from './BuildSelectStacksModal';
import StackList from './Stack';
import {Category} from './Stack/StackList';

export type BuildFormType = typeof yupSchema.build.__outputType;

type BuildFormRunProps = {
	control: Control<BuildFormType>;
	register: UseFormRegister<BuildFormType>;
};

const BuildFormRun: React.FC<BuildFormRunProps> = ({control, register}) => {
	const {modal: optionModal} = useFormModal();
	const {routineId} = useParams();

	const {append, fields, remove, update} = useFieldArray({
		control,
		name: 'factorStacks',
	});

	const {modal: optionSelectModal} = useFormModal({
		onSave: (factorStacks: FactorStack[]) => {
			for (const factor of factorStacks) {
				append({...factor, disabled: false});
			}
		},
	});

	const [factorOptionsList, setFactorOptionsList] = useState<
		TestrayFactorOption[][]
	>([[] as any]);

	const {data: factorsData} = useFetch<APIResponse<TestrayFactor>>(
		`${testrayFactorRest.resource}&filter=${searchUtil.eq(
			'routineId',
			routineId as string
		)}&pageSize=100`,
		(response) => testrayFactorRest.transformDataFromList(response)
	);

	const factorItems = useMemo(() => factorsData?.items || [], [
		factorsData?.items,
	]);

	useEffect(() => {
		if (factorItems.length) {
			testrayFactorCategoryRest
				.getFactorCategoryItems(factorItems)
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

			{!factorItems.length && (
				<ClayAlert>
					{i18n.translate(
						'create-environment-factors-if-you-want-to-generate-runs'
					)}
				</ClayAlert>
			)}

			{!!factorItems.length && (
				<>
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

					<StackList
						append={append as any}
						factorItems={factorItems}
						factorOptionsList={factorOptionsList}
						fields={fields}
						register={register}
						remove={remove}
						update={update as any}
					/>
				</>
			)}

			<FactorOptionsFormModal modal={optionModal} />

			<BuildSelectStacksModal
				factorItems={factorItems}
				modal={optionSelectModal}
			/>
		</>
	);
};

export default BuildFormRun;

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {useEffect, useMemo, useState} from 'react';
import {Control, UseFormRegister, useFieldArray} from 'react-hook-form';
import {useParams} from 'react-router-dom';

import Form from '../../../../../components/Form';
import SearchBuilder from '../../../../../core/SearchBuilder';
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
		testrayFactorRest.resource,
		{
			params: {
				filter: SearchBuilder.eq('routineId', routineId as string),
				pageSize: 100,
			},
			transformData: (response) =>
				testrayFactorRest.transformDataFromList(response),
		}
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

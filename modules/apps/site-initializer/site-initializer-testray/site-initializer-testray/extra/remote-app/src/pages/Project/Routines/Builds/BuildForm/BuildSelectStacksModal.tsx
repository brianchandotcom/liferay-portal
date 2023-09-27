/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useMemo, useState} from 'react';
import {useFieldArray, useForm} from 'react-hook-form';

import Form from '../../../../../components/Form';
import Modal from '../../../../../components/Modal';
import {withVisibleContent} from '../../../../../hoc/withVisibleContent';
import {FormModalComponent} from '../../../../../hooks/useFormModal';
import i18n from '../../../../../i18n';
import yupSchema, {yupResolver} from '../../../../../schema/yup';
import {
	TestrayFactor,
	TestrayFactorCategory,
	TestrayFactorOption,
	testrayFactorCategoryRest,
	testrayFactorRest,
} from '../../../../../services/rest';
import StackList from './Stack';

export type FactorStack = {
	[number: string]: {
		factorCategory: TestrayFactorCategory;
		factorOption: TestrayFactorOption;
	};
};

type BuildSelectStacksModalForm = {
	factorStacks: FactorStack[];
	selectedOptions: string[][];
};

const BuildSelectStacksModal: React.FC<
	FormModalComponent & {factorItems: TestrayFactor[]}
> = ({factorItems, modal: {observer, onClose, onSave}}) => {
	const [step, setStep] = useState(0);

	const [factorOptionsList, setFactorOptionsList] = useState<
		TestrayFactorOption[][]
	>([[] as any]);

	const factorWithOptionsList = useMemo(
		() =>
			factorItems.map((factor, index) => ({
				...factor,
				options: factorOptionsList[index],
			})),
		[factorItems, factorOptionsList]
	);

	const {
		control,
		formState: {errors},
		handleSubmit,
		register,
		watch,
	} = useForm<BuildSelectStacksModalForm>({
		defaultValues: {factorStacks: [{}], selectedOptions: []},
		resolver: yupResolver(yupSchema.option),
	});

	const {append, fields, remove, update} = useFieldArray({
		control,
		name: 'factorStacks',
	});

	const selectedOptions = watch('selectedOptions').map((values) =>
		values.map(Number)
	);

	const onStepNext = (form: BuildSelectStacksModalForm) => {
		if (step === 0) {
			const factorCombinations = testrayFactorRest.getTestrayFactorCombinations(
				factorWithOptionsList,
				selectedOptions
			);

			remove();

			factorCombinations.forEach((factorCombination) => {
				append({...factorCombination, disabled: true} as any);
			});

			append({disabled: false} as any);

			return setStep(1);
		}

		onSave(form.factorStacks);
	};

	const lastStep = step === 1;

	useEffect(() => {
		if (factorItems.length) {
			testrayFactorCategoryRest
				.getFactorCategoryItems(factorItems)
				.then(setFactorOptionsList as any);
		}
	}, [factorItems]);

	const onStepBack = () => {
		if (step === 0) {
			return onClose();
		}

		setStep(0);
	};

	return (
		<Modal
			last={
				<Form.Footer
					onClose={onStepBack}
					onSubmit={handleSubmit(onStepNext)}
					primaryButtonProps={{
						title: i18n.translate(lastStep ? 'add' : 'next'),
					}}
					secondaryButtonProps={{
						title: i18n.translate(lastStep ? 'back' : 'cancel'),
					}}
				/>
			}
			observer={observer}
			size="full-screen"
			title={i18n.translate(
				lastStep ? 'select-stacks' : 'select-options'
			)}
			visible
		>
			{step === 0 &&
				factorItems.map((factorItem, index) => {
					const options = (factorOptionsList[index] || []).map(
						({id, name}) => ({
							label: name,
							value: id,
						})
					);

					return (
						<Form.Select
							defaultOption={false}
							defaultValue={factorItem.factorOption?.id}
							errors={errors}
							forceSelectOption
							key={index}
							label={factorItem.factorCategory?.name}
							multiple
							name={`selectedOptions.${index}`}
							options={options}
							register={register}
							required
							size={8}
						/>
					);
				})}

			{lastStep && (
				<StackList
					append={append as any}
					displayVertical
					factorItems={factorItems}
					factorOptionsList={factorOptionsList}
					fields={fields}
					register={register}
					remove={remove}
					update={update as any}
				/>
			)}
		</Modal>
	);
};

export default withVisibleContent(BuildSelectStacksModal);

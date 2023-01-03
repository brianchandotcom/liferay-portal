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

import {useCallback, useEffect, useMemo, useState} from 'react';
import {createPortal} from 'react-dom';
import {useForm} from 'react-hook-form';

import Form from '../../../components/Form';
import DualListBox, {Boxes} from '../../../components/Form/DualListBox';
import {useFetch} from '../../../hooks/useFetch';
import useFormActions from '../../../hooks/useFormActions';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';
import {APIResponse, TestrayFactor} from '../../../services/rest';
import {testrayFactorRest} from '../../../services/rest/TestrayFactor';
import {searchUtil} from '../../../util/search';
import FactorsToOptions from './FactorsToOptions';

type EnvironmentFactorsModalProps = {
	onCloseModal: () => void;
	routineId: number;
};

type FactorCategoryForm = typeof yupSchema.factorCategory.__outputType;
type FactorEnviroment = typeof yupSchema.enviroment.__outputType;

const onMapAvailable = (factor: FactorCategoryForm) => ({
	label: factor?.name,
	value: String(factor?.id),
});

export type State = Boxes<[]>;

const EnvironmentFactorsModal: React.FC<EnvironmentFactorsModalProps> = ({
	onCloseModal,
	routineId,
}) => {
	const [shouldRequestCategories, setShouldRequestCategories] = useState(
		false
	);

	const {
		form: {onSuccess, submitting},
	} = useFormActions();

	const {handleSubmit, register, setValue} = useForm<FactorEnviroment>({
		resolver: yupResolver(yupSchema.enviroment),
	});

	const [state, setState] = useState<State>([[], []]);
	const [step, setStep] = useState(0);

	const {data: factorCategoryResponse} = useFetch<
		APIResponse<FactorCategoryForm>
	>(`/factorcategories`);

	const {data: factorResponse, mutate} = useFetch<APIResponse<TestrayFactor>>(
		testrayFactorRest.resource,
		{
			filter: searchUtil.eq('routineId', routineId),
			transformData: (response) =>
				testrayFactorRest.transformDataFromList(response),
		}
	);

	const factors = useMemo(() => factorResponse?.items || [], [
		factorResponse?.items,
	]);

	const getCategoryDualBox = useCallback(() => {
		const selectedItems =
			factors.map(({factorCategory}) => factorCategory) || [];

		const availableItems =
			factorCategoryResponse?.items.filter(
				(factorCategory) =>
					!selectedItems.find(
						(item) => Number(item?.id) === Number(factorCategory.id)
					)
			) || [];

		setState([
			availableItems.map(onMapAvailable) as any,
			selectedItems.map(onMapAvailable as any),
		]);
	}, [factorCategoryResponse?.items, factors, setState]);

	useEffect(() => {
		getCategoryDualBox();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const lastStep = step === 1;

	const [, selectedEnvironmentFactors] = state;

	const _onSubmit = async (form: FactorEnviroment) => {
		if (step === 0) {
			const factorCategoryIds = selectedEnvironmentFactors.map((item) =>
				Number(item.value)
			);

			setValue('factorCategoryIds', factorCategoryIds);
			setShouldRequestCategories(true);

			return setStep(1);
		}

		setShouldRequestCategories(false);

		const _factors = await testrayFactorRest.selectDefaultEnvironmentFactor(
			{
				factorCategoryIds: form.factorCategoryIds,
				factorOptionIds: (form.factorOptionIds as string[]).map(
					(factorOptionId) => Number(factorOptionId)
				),
				routineId,
			},
			factors
		);

		mutate((response) => {
			if (response) {
				return {
					...response,
					items: _factors,
					totalCount: _factors.length,
				};
			}

			return response;
		});

		onCloseModal();

		onSuccess();
	};

	const environmentFactorModalFooterContainer = document.getElementById(
		'environment-factor-modal-footer'
	);

	return (
		<>
			{step === 0 && (
				<DualListBox
					boxes={state}
					leftLabel={i18n.translate('available')}
					rightLabel={i18n.translate('selected')}
					setValue={setState}
				/>
			)}

			{lastStep && (
				<FactorsToOptions
					factors={factors}
					register={register}
					selectedEnvironmentFactors={
						selectedEnvironmentFactors as any
					}
					setValue={setValue}
					shouldRequestCategories={shouldRequestCategories}
				/>
			)}

			{environmentFactorModalFooterContainer &&
				// eslint-disable-next-line @liferay/portal/no-react-dom-create-portal
				createPortal(
					<Form.Footer
						onClose={() => (lastStep ? setStep(0) : onCloseModal())}
						onSubmit={handleSubmit(_onSubmit)}
						primaryButtonProps={{
							disabled: lastStep
								? submitting
								: !selectedEnvironmentFactors.length,
							title: i18n.translate(lastStep ? 'save' : 'next'),
						}}
						secondaryButtonProps={{
							title: i18n.translate(lastStep ? 'back' : 'cancel'),
						}}
					/>,
					environmentFactorModalFooterContainer
				)}
		</>
	);
};

export default EnvironmentFactorsModal;

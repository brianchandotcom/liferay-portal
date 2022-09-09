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

import {useEffect, useState} from 'react';
import {useForm} from 'react-hook-form';
import {useParams} from 'react-router-dom';

import Form from '../../../../../../components/Form';
import Modal from '../../../../../../components/Modal/index';
import {withVisibleContent} from '../../../../../../hoc/withVisibleContent';
import {useFetch} from '../../../../../../hooks/useFetch';
import {FormModalOptions} from '../../../../../../hooks/useFormModal';
import i18n from '../../../../../../i18n';
import yupSchema, {yupResolver} from '../../../../../../schema/yup';
import {
	APIResponse,
	TestrayFactor,
	testrayFactorCategoryRest,
	testrayFactorRest,
	testrayRunRest,
} from '../../../../../../services/rest';
import {searchUtil} from '../../../../../../util/search';

type Run = typeof yupSchema.factorToRun.__outputType;
type RunFormModalProps = {
	modal: FormModalOptions;
};

const RunFormModal: React.FC<RunFormModalProps> = ({
	modal: {modalState, observer, onClose, onError, onSave, onSubmit},
}) => {
	const {
		formState: {errors},
		handleSubmit,
		register,
	} = useForm<Run>({
		defaultValues: modalState,

		resolver: yupResolver(yupSchema.factorToRun),
	});
	const {buildId, routineId} = useParams();
	const [factorOptionsList, setFactorOptionsList] = useState<
		TestrayFactor[][]
	>([[] as any]);

	const {data: factorsData} = useFetch<APIResponse<TestrayFactor>>(
		modalState
			? `${testrayFactorRest.resource}&filter=${searchUtil.eq(
					'runId',
					modalState.id as string
			  )}&pageSize=1000`
			: `${testrayFactorRest.resource}&filter=${searchUtil.eq(
					'routineId',
					routineId as string
			  )}&pageSize=1000`,
		(response) => testrayFactorRest.transformDataFromList(response)
	);

	const {data: Runs} = useFetch<APIResponse<Run>>(
		!modalState
			? `${testrayRunRest.resource}&filter=${searchUtil.eq(
					'buildId',
					buildId as string
			  )}&pageSize=1000`
			: null
	);

	const _Number = Runs?.items.map((item) => item.number).slice(-1)[0];

	const _onSubmit = (Form: Run) => {
		onSubmit(
			{
				buildId: buildId as string,
				id: Form.id,
				name: Form.factorOptionName.join(' | '),
				number: modalState ? Form.number : Number(_Number) + 1,
			},
			{
				create: (data) => testrayRunRest.create(data),
				update: (id, data) => testrayRunRest.update(id, data),
			}
		)
			.then(onSave)
			.catch(onError);
	};

	// eslint-disable-next-line react-hooks/exhaustive-deps
	const factorItems = factorsData?.items || [];

	useEffect(() => {
		if (factorItems.length) {
			testrayFactorCategoryRest
				.getFactorCategoryItems(factorItems)
				.then(setFactorOptionsList);
		}
	}, [factorItems]);

	return (
		<Modal
			last={
				<Form.Footer
					isModal
					onClose={onClose}
					onSubmit={handleSubmit(_onSubmit)}
				/>
			}
			observer={observer}
			size="full-screen"
			title={i18n.translate('select-options')}
			visible
		>
			{factorItems.map((factorItem, index) => (
				<Form.Select
					defaultValue={factorItem.factorOption?.name}
					errors={errors}
					key={index}
					label={factorItem.factorCategory?.name}
					name={`factorOptionName.${index}`}
					options={(factorOptionsList[index] || []).map(
						({name}: any) => ({
							label: name,
							value: name,
						})
					)}
					register={register}
					required
				/>
			))}
		</Modal>
	);
};

export default withVisibleContent(RunFormModal);

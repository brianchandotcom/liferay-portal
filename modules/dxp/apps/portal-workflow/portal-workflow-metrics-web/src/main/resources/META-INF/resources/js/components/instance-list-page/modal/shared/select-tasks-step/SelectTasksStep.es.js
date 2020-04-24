/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React, {useContext, useEffect, useMemo, useState} from 'react';

import PromisesResolver from '../../../../../shared/components/promises-resolver/PromisesResolver.es';
import {useFilter} from '../../../../../shared/hooks/useFilter.es';
import {usePaginationState} from '../../../../../shared/hooks/usePaginationState.es';
import {AppContext} from '../../../../AppContext.es';
import {ModalContext} from '../../ModalProvider.es';
import {Body} from './SelectTasksStepBody.es';
import {Header} from './SelectTasksStepHeader.es';
import {useFetchTasks} from './hooks/useFetchTasks.es';

const SelectTasksStep = ({setErrorToast, withoutUnassigned}) => {
	const {
		deltaValues: [initialPageSize],
	} = useContext(AppContext);
	const {setSelectTasks} = useContext(ModalContext);
	const [retry, setRetry] = useState(0);
	const {
		filterValues: {bulkAssigneeIds, bulkTaskNames},
	} = useFilter({withoutRouteParams: true});
	const {page, pageSize, pagination} = usePaginationState({
		initialPageSize,
	});

	const {data, fetchTasks} = useFetchTasks({
		page,
		pageSize,
		withoutUnassigned,
	});

	const paginationState = useMemo(
		() => ({
			...pagination,
			totalCount: data.totalCount,
		}),
		[data.totalCount, pagination]
	);

	useEffect(() => {
		if (page !== 1) {
			pagination.setPage(1);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [bulkAssigneeIds, bulkTaskNames]);

	const promises = useMemo(() => {
		setErrorToast(false);

		return [
			fetchTasks().catch((err) => {
				setSelectTasks({selectAll: false, tasks: []});
				setErrorToast(Liferay.Language.get('your-request-has-failed'));

				return Promise.reject(err);
			}),
		];
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [fetchTasks, retry]);

	return (
		<div className="fixed-height modal-metrics-content">
			<PromisesResolver promises={promises}>
				<SelectTasksStep.Header
					{...data}
					withoutUnassigned={withoutUnassigned}
				/>

				<SelectTasksStep.Body
					{...data}
					pagination={paginationState}
					setRetry={setRetry}
				/>
			</PromisesResolver>
		</div>
	);
};

SelectTasksStep.Body = Body;
SelectTasksStep.Header = Header;

export default SelectTasksStep;

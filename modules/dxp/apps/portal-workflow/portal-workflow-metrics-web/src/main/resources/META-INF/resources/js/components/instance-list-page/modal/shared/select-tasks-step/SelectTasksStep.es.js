/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext, useEffect, useMemo, useState} from 'react';

import PromisesResolver from '../../../../../shared/components/promises-resolver/PromisesResolver.es';
import {useFilter} from '../../../../../shared/hooks/useFilter.es';
import {usePaginationState} from '../../../../../shared/hooks/usePaginationState.es';
import {AppContext} from '../../../../AppContext.es';
import {ModalContext} from '../../ModalProvider.es';
import Body from './SelectTasksStepBody.es';
import Header from './SelectTasksStepHeader.es';
import {useFetchTasks} from './hooks/useFetchTasks.es';

function SelectTasksStep({setErrorToast, withoutUnassigned}) {
	const {deltaValues} = useContext(AppContext);
	const {setSelectTasks} = useContext(ModalContext);

	const {
		filterValues: {bulkAssigneeIds, bulkTaskNames},
	} = useFilter({withoutRouteParams: true});

	const {page, pageSize, pagination} = usePaginationState({
		initialPageSize: deltaValues[0],
	});

	const {data, fetchTasks, instanceIds} = useFetchTasks({
		page,
		pageSize,
		withoutUnassigned,
	});

	const paginationState = {
		...pagination,
		totalCount: data?.totalCount,
	};

	useEffect(() => {
		if (page !== 1) {
			pagination.setPage(1);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [bulkAssigneeIds, bulkTaskNames]);

	useEffect(() => {
		fetchTasks();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [page, pageSize, bulkAssigneeIds, bulkTaskNames]);

	const [retry, setRetry] = useState(0);

	const promises = useMemo(() => {
		setErrorToast(false);

		return [
			fetchTasks().catch((error) => {
				setSelectTasks({selectAll: false, tasks: []});
				setErrorToast(Liferay.Language.get('your-request-has-failed'));

				return Promise.reject(error);
			}),
		];

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [retry]);

	return (
		<div className="fixed-height modal-metrics-content">
			<PromisesResolver promises={promises}>
				<SelectTasksStep.Header
					{...data}
					instanceIds={instanceIds}
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
}

SelectTasksStep.Body = Body;
SelectTasksStep.Header = Header;

export default SelectTasksStep;

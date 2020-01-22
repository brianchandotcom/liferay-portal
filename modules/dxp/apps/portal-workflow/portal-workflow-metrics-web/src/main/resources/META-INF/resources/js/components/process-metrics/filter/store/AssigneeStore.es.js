/* eslint-disable react-hooks/exhaustive-deps */
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

import {usePrevious} from 'frontend-js-react-web';
import React, {createContext, useContext, useEffect, useState} from 'react';

import {buildFallbackItems} from '../../../../shared/components/filter/util/filterEvents.es';
import {ErrorContext} from '../../../../shared/components/request/Error.es';
import {LoadingContext} from '../../../../shared/components/request/Loading.es';
import {compareArrays} from '../../../../shared/util/array.es';
import {AppContext} from '../../../AppContext.es';

const useAssignee = (assigneeKeys, processId) => {
	const {client} = useContext(AppContext);
	const [assignees, setAssignees] = useState([]);
	const {setError} = useContext(ErrorContext);
	const {setLoading} = useContext(LoadingContext);

	const fetchData = () => {
		setError(null);
		setLoading(true);

		client
			.get(`/processes/${processId}/assignee-users?page=0&pageSize=0`)
			.then(({data}) => {
				const items = data.items || [];

				const assignees = [
					getUnassigned(assigneeKeys, data.totalCount),
					...items.map(item => {
						const itemKey = String(item.id);

						return {
							...item,
							active: assigneeKeys.includes(itemKey),
							key: itemKey
						};
					})
				];

				setAssignees(assignees);
			})
			.catch(error => {
				setError(error);
			})
			.then(() => {
				setLoading(false);
			});
	};

	const getSelectedAssignees = fallbackKeys => {
		if (!assignees || !assignees.length) {
			return buildFallbackItems(fallbackKeys);
		}

		return assignees.filter(item => item.active);
	};

	const updateData = () => {
		setAssignees(
			assignees.map(assignee => ({
				...assignee,
				active: assigneeKeys.includes(assignee.key)
			}))
		);
	};

	useEffect(() => {
		fetchData();
	}, [processId]);

	const previousKeys = usePrevious(assigneeKeys);

	useEffect(() => {
		const filterChanged = !compareArrays(previousKeys, assigneeKeys);

		if (filterChanged && assignees.length) {
			updateData();
		}
	}, [assigneeKeys]);

	return {
		assignees,
		getSelectedAssignees
	};
};

const getUnassigned = (assigneeKeys, totalCount) => {
	const unassigned = {
		dividerAfter: !!totalCount,
		id: -1,
		key: '-1',
		name: Liferay.Language.get('unassigned')
	};

	unassigned.active = assigneeKeys.includes(unassigned.key);

	return unassigned;
};

const AssigneeContext = createContext(null);

const AssigneeProvider = ({assigneeKeys, children, processId}) => {
	return (
		<AssigneeContext.Provider value={useAssignee(assigneeKeys, processId)}>
			{children}
		</AssigneeContext.Provider>
	);
};

export {AssigneeContext, AssigneeProvider, useAssignee};

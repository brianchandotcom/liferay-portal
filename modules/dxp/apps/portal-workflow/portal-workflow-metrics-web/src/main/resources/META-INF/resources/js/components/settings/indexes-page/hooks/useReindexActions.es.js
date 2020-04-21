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
import {useCallback, useContext} from 'react';

import {useToaster} from '../../../../shared/components/toaster/hooks/useToaster.es';
import {useFetch} from '../../../../shared/hooks/useFetch.es';
import {useInterval} from '../../../../shared/hooks/useInterval.es';
import {usePatch} from '../../../../shared/hooks/usePatch.es';
import {sub} from '../../../../shared/util/lang.es';
import {AppContext} from '../../../AppContext.es';
import {INDEXES_GROUPS_KEYS, SUCCESS_MESSAGES} from '../IndexesConstants.es';

const useReindexActions = () => {
	const {reindexStatuses, setReindexStatuses} = useContext(AppContext);
	const previousStatuses = usePrevious(reindexStatuses);
	const schedule = useInterval();
	const toaster = useToaster();

	const {fetchData} = useFetch({url: '/indexes/reindex/status'});
	const {patchData} = usePatch({url: '/indexes/reindex'});

	const getReindexStatus = useCallback(
		key => reindexStatuses.find(item => key === item.key) || {},
		[reindexStatuses]
	);

	const getStatuses = useCallback(
		(key, label = Liferay.Language.get('workflow-indexes')) => {
			setReindexStatuses([
				...reindexStatuses,
				{completionPercentage: 0, key},
			]);

			const cancelInterval = schedule(() => {
				fetchData()
					.then(({items, totalCount}) => {
						if (!totalCount) {
							if (previousStatuses) {
								sendSuccess(key, label);
							}

							cancelInterval();
						}

						setReindexStatuses(items);
					})
					.catch(() => {
						cancelInterval();
						sendError();
						setReindexStatuses([]);
					});
			}, 1000);
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[fetchData, previousStatuses, setReindexStatuses, reindexStatuses]
	);

	const handleReindex = useCallback(
		(key, label) => {
			patchData({key})
				.then(() => getStatuses(key, label))
				.catch(sendError);
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[getStatuses, patchData]
	);

	const isReindexing = useCallback(
		key => reindexStatuses.findIndex(item => key === item.key) > -1,
		[reindexStatuses]
	);

	const sendError = () => {
		toaster.danger(Liferay.Language.get('your-request-has-failed'));
	};

	const sendSuccess = (key, label) => {
		const message = INDEXES_GROUPS_KEYS.includes(key)
			? SUCCESS_MESSAGES.ALL
			: SUCCESS_MESSAGES.SINGLE;

		toaster.success(sub(message, [label]));
	};

	return {
		getReindexStatus,
		getStatuses,
		handleReindex,
		isReindexing,
		reindexStatuses,
	};
};

export {useReindexActions};

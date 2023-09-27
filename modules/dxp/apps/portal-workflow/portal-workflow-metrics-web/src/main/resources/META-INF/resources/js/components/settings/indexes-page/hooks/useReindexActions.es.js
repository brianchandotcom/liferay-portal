/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useContext} from 'react';

import {useToaster} from '../../../../shared/components/toaster/hooks/useToaster.es';
import {useFetch} from '../../../../shared/hooks/useFetch.es';
import {useInterval} from '../../../../shared/hooks/useInterval.es';
import {usePatch} from '../../../../shared/hooks/usePatch.es';
import {sub} from '../../../../shared/util/lang.es';
import {AppContext} from '../../../AppContext.es';
import {INDEXES_GROUPS_KEYS} from '../IndexesConstants.es';

const useReindexActions = () => {
	const {reindexStatuses, setReindexStatuses} = useContext(AppContext);
	const schedule = useInterval();
	const toaster = useToaster();

	const {fetchData} = useFetch({
		url: '/reindex/statuses',
	});
	const {patchData} = usePatch({
		url: '/indexes/reindex',
	});

	const getReindexStatus = (key) =>
		reindexStatuses.find((item) => key === item.key) || {};

	const sendSuccess = (
		key,
		label = Liferay.Language.get('workflow-indexes')
	) => {
		const message = INDEXES_GROUPS_KEYS.includes(key)
			? Liferay.Language.get('all-x-have-reindexed-successfully')
			: Liferay.Language.get('x-has-reindexed-successfully');

		toaster.success(sub(message, [label]));
	};

	const sendError = () => {
		toaster.danger(Liferay.Language.get('your-request-has-failed'));
	};

	const isReindexing = (key) =>
		reindexStatuses.findIndex((item) => key === item.key) > -1;

	const getStatuses = useCallback(
		() => {
			const cancelInterval = schedule(() => {
				fetchData()
					.then(({items}) => {
						if (!items.length) {
							cancelInterval();
						}

						setReindexStatuses((prevStatuses) =>
							prevStatuses
								.filter(({key, label}) => {
									const finished =
										items.findIndex(
											(item) => item.key === key
										) === -1;

									if (
										finished &&
										/\/settings\/indexes/gi.test(
											window.location.hash
										)
									) {
										sendSuccess(key, label);
									}

									return !finished;
								})
								.map(({key, ...status}) => ({
									...status,
									...items.find((item) => item.key === key),
								}))
						);
					})
					.catch(() => {
						cancelInterval();
						sendError();
						setReindexStatuses([]);
					});
			}, 1000);
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[isReindexing]
	);

	const handleReindex = (key, label) => {
		setReindexStatuses((prevStatuses) => [
			...prevStatuses,
			{completionPercentage: 0, key, label},
		]);

		patchData({key})
			.then(getStatuses)
			.catch(() => {
				sendError();

				setReindexStatuses((prevStatuses) => [
					...prevStatuses.filter((item) => item.key !== key),
				]);
			});
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

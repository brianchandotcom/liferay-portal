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

import {openToast} from 'frontend-js-web';
import {useMutation} from 'graphql-hooks';
import {useCallback, useMemo, useState} from 'react';

import {subscribeQuery, unsubscribeQuery} from '../../../utils/client.es';
import {deleteCache, getFullPath} from '../../../utils/utils.es';

const FEEDBACK_DELAY = 2000;

const useActiviyQuestionKebabOptions = ({
	context,
	question,
	questionId,
	sectionTitle,
	setError,
	setShowDeleteModalPanel,
}) => {
	const [isSubscribed, setIsSubscribe] = useState(true);

	const [unsubscribe] = useMutation(unsubscribeQuery);
	const [subscribe] = useMutation(subscribeQuery);

	const onClickShare = useCallback(async () => {
		const urlClipboard = `${getFullPath(
			context.historyRouterBasePath || 'questions'
		)}${
			context.historyRouterBasePath ? context.historyRouterBasePath : '#'
		}/questions/${sectionTitle}/${questionId}`;

		try {
			await navigator.clipboard.writeText(urlClipboard);

			openToast({
				message: Liferay.Language.get('copied-link-to-the-clipboard'),
				type: 'success',
			});
		}
		catch (error) {
			setError(error);

			openToast({
				message: error,
				title: Liferay.Language.get('an-error-occurred'),
				type: 'warning',
			});
		}
		finally {
			setTimeout(() => {
				setError(null);
			}, FEEDBACK_DELAY);
		}
	}, [context.historyRouterBasePath, questionId, sectionTitle, setError]);

	const onSubscribe = useCallback(
		async (question) => {
			const fn = isSubscribed ? unsubscribe : subscribe;

			await fn({
				variables: {
					messageBoardThreadId: question.id,
				},
			});

			setIsSubscribe(!isSubscribed);

			deleteCache();

			openToast({
				message: isSubscribed
					? Liferay.Language.get('unsubscribe')
					: Liferay.Language.get('subscribe'),
				type: 'success',
			});
		},
		[isSubscribed, subscribe, unsubscribe]
	);

	const kebabOptions = useMemo(() => {
		const options = [
			{
				href: `${getFullPath(
					context.historyRouterBasePath || 'questions'
				)}${
					context.historyRouterBasePath
						? context.historyRouterBasePath
						: '#'
				}/questions/${sectionTitle}/${questionId}`,
				label: Liferay.Language.get('view-question'),
				symbolLeft: 'shortcut',
			},
			{
				label: Liferay.Language.get('share'),
				onClick: onClickShare,
				symbolLeft: 'share',
			},
		];

		if (question?.actions?.replace) {
			options.push({
				href: `${getFullPath(
					context.historyRouterBasePath || 'questions'
				)}${
					context.historyRouterBasePath
						? context.historyRouterBasePath
						: '#'
				}/questions/${sectionTitle}/${questionId}/edit`,
				label: Liferay.Language.get('edit'),
				symbolLeft: 'pencil',
			});
		}

		if (question?.actions?.subscribe || question?.actions?.unsubscribe) {
			options.push({
				label: isSubscribed
					? Liferay.Language.get('unsubscribe')
					: Liferay.Language.get('subscribe'),
				onClick: () => onSubscribe(question),
				symbolLeft: isSubscribed ? 'bell-off' : 'bell-on',
			});
		}
		if (question?.actions?.delete) {
			options.push(
				{
					type: 'divider',
				},
				{
					label: Liferay.Language.get('delete'),
					onClick: () => setShowDeleteModalPanel(true),
					symbolLeft: 'trash',
				}
			);
		}

		return options;
	}, [
		context.historyRouterBasePath,
		isSubscribed,
		onClickShare,
		onSubscribe,
		question,
		questionId,
		sectionTitle,
		setShowDeleteModalPanel,
	]);

	return {kebabOptions, setIsSubscribe};
};

export default useActiviyQuestionKebabOptions;

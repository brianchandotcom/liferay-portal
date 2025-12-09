/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useQuery} from 'graphql-hooks';

/* eslint-disable no-unused-vars*/
import React, {useContext, useEffect, useMemo} from 'react';

import {AppContext} from '../AppContext.es';
import {withRouter} from '../hooks/withRouter.es';
import {getSectionByMessageQuery} from '../utils/client.es';
import {historyPushWithSlug} from '../utils/utils.es';

export default withRouter(({history, params: {questionId}}) => {
	const context = useContext(AppContext);

	const historyPushParser = historyPushWithSlug(history.replace);

	const {data: {messageBoardMessage} = {}} = useQuery(
		getSectionByMessageQuery,
		{
			variables: {
				messageBoardMessageId: questionId,
			},
		}
	);

	useEffect(() => {
		if (messageBoardMessage) {
			const messageBoardSection =
				messageBoardMessage.messageBoardThread.messageBoardSection;
			historyPushParser(
				`/questions/${
					context.useTopicNamesInURL
						? messageBoardSection.title
						: messageBoardSection.id
				}/${messageBoardMessage.friendlyUrlPath}`
			);
		}
	}, [context.useTopicNamesInURL, messageBoardMessage, historyPushParser]);

	return null;
});

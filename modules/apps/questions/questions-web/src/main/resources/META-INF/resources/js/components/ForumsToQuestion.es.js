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

import {useQuery} from 'graphql-hooks';

/* eslint-disable no-unused-vars*/
import React, {useContext, useEffect, useMemo} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../AppContext.es';
import {getSectionByMessageQuery} from '../utils/client.es';
import {historyPushWithSlug} from '../utils/utils.es';

export default withRouter(
	({
		history,
		match: {
			params: {questionId},
		},
	}) => {
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
		}, [
			context.useTopicNamesInURL,
			messageBoardMessage,
			historyPushParser,
		]);

		return null;
	}
);

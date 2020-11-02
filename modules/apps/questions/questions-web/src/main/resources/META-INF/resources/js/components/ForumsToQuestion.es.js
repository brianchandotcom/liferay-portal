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

import {useQuery} from '@apollo/client';

/*eslint-disable no-unused-vars*/
import React from 'react';
import {withRouter} from 'react-router-dom';

import {getSectionByMessageQuery} from '../utils/client.es';
import {historyPushWithSlug} from '../utils/utils.es';

export default withRouter(
	({
		history,
		match: {
			params: {questionId},
		},
	}) => {
		const historyPushParser = historyPushWithSlug(history.push);

		useQuery(getSectionByMessageQuery, {
			onCompleted({messageBoardMessage}) {
				historyPushParser(
					`/questions/${
						messageBoardMessage.messageBoardThread
						.messageBoardSection.title
					}/${messageBoardMessage.friendlyUrlPath}`
				);
			},
			variables: {
				messageBoardMessageId: questionId,
			},
		});

		return null;
	}
);
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

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import React from 'react';

export default function AskQuestionButton({navigateToNewQuestion}) {
	return (
		<ClayInput.GroupItem shrink>
			<ClayButton
				className="d-none d-sm-block text-nowrap"
				displayType="primary"
				onClick={navigateToNewQuestion}
			>
				{Liferay.Language.get('ask-question')}
			</ClayButton>
		</ClayInput.GroupItem>
	);
}

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

import React, {createContext, useState, useContext} from 'react';
import ReloadButton from '../list/ReloadButton.es';

function useError() {
	const [error, setError] = useState(null);

	return {
		error,
		setError
	};
}

const ErrorContext = createContext(null);

function Error({children}) {
	const {error} = useContext(ErrorContext);

	return (
		error &&
		(children || (
			<div className="pb-6 pt-5 text-center">
				<p className="small" data-testId="error">
					{Liferay.Language.get(
						'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
					)}
				</p>
				<ReloadButton />
			</div>
		))
	);
}

export {Error, ErrorContext, useError};

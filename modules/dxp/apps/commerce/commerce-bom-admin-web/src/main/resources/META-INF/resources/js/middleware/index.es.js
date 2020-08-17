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

import {actions as appActions} from '../actions/app.es';

const applyMiddleware = (dispatch) => (action) => {
	if (action.type.indexOf('Fulfilled') > -1) {
		appActions.setError(dispatch)(null);
		appActions.setLoading(dispatch)(false);
	}
	if (action.type.indexOf('Pending') > -1) {
		appActions.setLoading(dispatch)(true);
	}
	if (action.type.indexOf('Rejected') > -1) {
		appActions.setError(dispatch)(action.payload.message);
		appActions.setLoading(dispatch)(false);
	}
	dispatch(action);
};

export default applyMiddleware;

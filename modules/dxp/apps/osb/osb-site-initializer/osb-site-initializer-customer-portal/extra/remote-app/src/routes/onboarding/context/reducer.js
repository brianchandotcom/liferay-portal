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

export const actionTypes = {
	CHANGE_STEP: 'CHANGE_STEP',
	UPDATE_ANALYTICS_CLOUD_ACTIVATION_SUBMITTED_STATUS:
		'UPDATE_ANALYTICS_CLOUD_ACTIVATION_SUBMITTED_STATUS',
	UPDATE_CURRENT_TOTAL_ADMINISTRATORS: 'UPDATE_CURRENT_TOTAL_ADMINISTRATORS',
	UPDATE_DXP_CLOUD_ACTIVATION_SUBMITTED_STATUS:
		'UPDATE_DXP_CLOUD_ACTIVATION_SUBMITTED_STATUS',
	UPDATE_LIFERAY_EXPERIENCE_CLOUD_ACTIVATION_SUBMITTED_STATUS:
		'UPDATE_LIFERAY_EXPERIENCE_CLOUD_ACTIVATION_SUBMITTED_STATUS',
	UPDATE_PROJECT: 'UPDATE_PROJECT',
	UPDATE_SESSION_ID: 'UPDATE_SESSION_ID',
	UPDATE_SUBSCRIPTION_GROUPS: 'UPDATE_SUBSCRIPTION_GROUPS',
	UPDATE_USER_ACCOUNT: 'UPDATE_USER_ACCOUNT',
};

const reducer = (state, action) => {
	switch (action.type) {
		case actionTypes.CHANGE_STEP: {
			return {
				...state,
				step: action.payload,
			};
		}
		case actionTypes.UPDATE_CURRENT_TOTAL_ADMINISTRATORS: {
			return {
				...state,
				totalAdministratorAccounts: action.payload,
			};
		}
		case actionTypes.UPDATE_PROJECT: {
			return {
				...state,
				project: action.payload,
			};
		}
		case actionTypes.UPDATE_SESSION_ID: {
			return {
				...state,
				sessionId: action.payload,
			};
		}
		case actionTypes.UPDATE_USER_ACCOUNT: {
			return {
				...state,
				userAccount: action.payload,
			};
		}
		case actionTypes.UPDATE_SUBSCRIPTION_GROUPS: {
			return {
				...state,
				subscriptionGroups: action.payload,
			};
		}
		case actionTypes.UPDATE_DXP_CLOUD_ACTIVATION_SUBMITTED_STATUS: {
			return {
				...state,
				dxpCloudActivationSubmittedStatus: action.payload,
			};
		}
		case actionTypes.UPDATE_LIFERAY_EXPERIENCE_CLOUD_ACTIVATION_SUBMITTED_STATUS: {
			return {
				...state,
				liferayExperienceCloudActivationSubmittedStatus: action.payload,
			};
		}
		case actionTypes.UPDATE_ANALYTICS_CLOUD_ACTIVATION_SUBMITTED_STATUS: {
			return {
				...state,
				analyticsCloudActivationSubmittedStatus: action.payload,
			};
		}
		default: {
			return state;
		}
	}
};

export default reducer;

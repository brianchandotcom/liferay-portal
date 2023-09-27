/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

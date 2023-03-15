import * as API from 'shared/api';
import {CALL_API} from '../middleware/api';
import {createActionTypes} from 'redux-toolbox';
import {PreferencesScopes} from 'shared/util/constants';

export const actionTypes = {
	...createActionTypes('add', 'distribution_tabs'),
	...createActionTypes('fetch', 'default_channel_id'),
	...createActionTypes('fetch', 'default_site_id'),
	...createActionTypes('fetch', 'upgrade_modal_seen'),
	...createActionTypes('fetch', 'distribution_tabs'),
	...createActionTypes('remove', 'distribution_tabs'),
	...createActionTypes('update', 'default_channel_id'),
	...createActionTypes('update', 'default_site_id'),
	...createActionTypes('update', 'upgrade_modal_seen')
};

export function addDistributionTab({
	distributionKey,
	distributionTab,
	distributionTabId,
	groupId,
	id
}) {
	return {
		meta: {
			[CALL_API]: {
				data: {
					distributionTab,
					distributionTabId,
					groupId,
					scope: PreferencesScopes.Group,
					segmentId: id
				},
				requestFn: API.preferences.addDistributionTab,
				types: [
					actionTypes.ADD_DISTRIBUTION_TABS_REQUEST,
					actionTypes.ADD_DISTRIBUTION_TABS_SUCCESS,
					actionTypes.ADD_DISTRIBUTION_TABS_FAILURE
				]
			},
			id: distributionKey,
			scope: PreferencesScopes.Group
		},
		type: 'NO_OP'
	};
}

export function fetchDefaultChannelId(groupId = 0) {
	return {
		meta: {
			[CALL_API]: {
				data: {groupId, scope: PreferencesScopes.User},
				requestFn: API.preferences.fetchDefaultChannelId,
				types: [
					actionTypes.FETCH_DEFAULT_CHANNEL_ID_REQUEST,
					actionTypes.FETCH_DEFAULT_CHANNEL_ID_SUCCESS,
					actionTypes.FETCH_DEFAULT_CHANNEL_ID_FAILURE
				]
			},
			scope: PreferencesScopes.User
		},
		type: 'NO_OP'
	};
}

export function fetchDistributionTabs({distributionKey, groupId, id}) {
	return {
		meta: {
			[CALL_API]: {
				data: {
					groupId,
					scope: PreferencesScopes.Group,
					segmentId: id
				},
				requestFn: API.preferences.fetchDistributionTabs,
				types: [
					actionTypes.FETCH_DISTRIBUTION_TABS_REQUEST,
					actionTypes.FETCH_DISTRIBUTION_TABS_SUCCESS,
					actionTypes.FETCH_DISTRIBUTION_TABS_FAILURE
				]
			},
			id: distributionKey,
			scope: PreferencesScopes.Group
		},
		type: 'NO_OP'
	};
}

export function fetchUpgradeModalSeen(groupId) {
	return {
		meta: {
			[CALL_API]: {
				data: {groupId, scope: PreferencesScopes.User},
				requestFn: API.preferences.fetchUpgradeModalSeen,
				types: [
					actionTypes.FETCH_UPGRADE_MODAL_SEEN_REQUEST,
					actionTypes.FETCH_UPGRADE_MODAL_SEEN_SUCCESS,
					actionTypes.FETCH_UPGRADE_MODAL_SEEN_FAILURE
				]
			},
			scope: PreferencesScopes.User
		},
		type: 'NO_OP'
	};
}

export function removeDistributionTab({
	distributionKey,
	distributionTabId,
	groupId,
	id
}) {
	return {
		meta: {
			[CALL_API]: {
				data: {
					distributionTabId,
					groupId,
					scope: PreferencesScopes.Group,
					segmentId: id
				},
				requestFn: API.preferences.removeDistributionTab,
				types: [
					actionTypes.REMOVE_DISTRIBUTION_TABS_REQUEST,
					actionTypes.REMOVE_DISTRIBUTION_TABS_SUCCESS,
					actionTypes.REMOVE_DISTRIBUTION_TABS_FAILURE
				]
			},
			id: distributionKey,
			scope: PreferencesScopes.Group
		},
		type: 'NO_OP'
	};
}

export function updateDefaultChannelId({defaultChannelId, groupId}) {
	return {
		meta: {
			[CALL_API]: {
				data: {
					defaultChannelId,
					groupId,
					scope: PreferencesScopes.User
				},
				requestFn: API.preferences.updateDefaultChannelId,
				types: [
					actionTypes.UPDATE_DEFAULT_CHANNEL_ID_REQUEST,
					actionTypes.UPDATE_DEFAULT_CHANNEL_ID_SUCCESS,
					actionTypes.UPDATE_DEFAULT_CHANNEL_ID_FAILURE
				]
			},
			scope: PreferencesScopes.User
		},
		type: 'NO_OP'
	};
}

export function updateUpgradeModalSeen({groupId, upgradeModalSeen}) {
	return {
		meta: {
			[CALL_API]: {
				data: {
					groupId,
					scope: PreferencesScopes.User,
					upgradeModalSeen
				},
				requestFn: API.preferences.updateUpgradeModalSeen,
				types: [
					actionTypes.UPDATE_UPGRADE_MODAL_SEEN_REQUEST,
					actionTypes.UPDATE_UPGRADE_MODAL_SEEN_SUCCESS,
					actionTypes.UPDATE_UPGRADE_MODAL_SEEN_FAILURE
				]
			},
			scope: PreferencesScopes.User
		},
		type: 'NO_OP'
	};
}

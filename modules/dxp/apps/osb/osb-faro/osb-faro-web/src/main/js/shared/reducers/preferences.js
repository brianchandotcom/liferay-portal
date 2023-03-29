import {actionTypes} from '../actions/preferences';
import {createReducer} from 'redux-toolbox';
import {DistributionTab} from 'shared/util/records';
import {Map} from 'immutable';
import {PreferencesScopes} from 'shared/util/constants';

/* Distribution Tabs */
const handleDistributionTabFailure = (
	state,
	{meta: {id, scope = PreferencesScopes.User}}
) =>
	state.mergeIn([scope, 'distributionCardTabs', id], {
		error: true,
		loading: false
	});
const handleDistributionTabRequest = (
	state,
	{meta: {id, scope = PreferencesScopes.User}}
) =>
	state.mergeIn([scope, 'distributionCardTabs', id], {
		error: false,
		loading: true
	});
const handleDistributionTabSuccess = (
	state,
	{
		meta: {id, scope = PreferencesScopes.User},
		payload: {distributionCardTabPreferencesMap, order}
	}
) =>
	state.mergeIn([scope, 'distributionCardTabs', id], {
		data: order.map(
			id => new DistributionTab(distributionCardTabPreferencesMap[id])
		),
		error: false,
		loading: false
	});

/* Default Channel Id */
const handleDefaultChannelIdSuccess = (
	state,
	{meta: {scope = PreferencesScopes.User}, payload}
) =>
	state.mergeIn([scope, 'defaultChannelId'], {
		data: payload.defaultChannelId,
		error: false,
		loading: false
	});
const handleDefaultChannelIdRequest = (
	state,
	{meta: {scope = PreferencesScopes.User}}
) => state.mergeIn([scope, 'defaultChannelId'], {error: false, loading: true});
const handleDefaultChannelIdFailure = (
	state,
	{meta: {scope = PreferencesScopes.User}}
) => state.mergeIn([scope, 'defaultChannelId'], {error: true, loading: false});

/* Upgrade Modal Seen */
const handleUpgradeModalSeenSuccess = (
	state,
	{meta: {scope = PreferencesScopes.User}, payload}
) =>
	state.mergeIn([scope, 'upgradeModalSeen'], {
		data: payload,
		error: false,
		loading: false
	});
const handleUpgradeModalSeenRequest = (
	state,
	{meta: {scope = PreferencesScopes.User}}
) => state.mergeIn([scope, 'upgradeModalSeen'], {error: false, loading: true});
const handleUpgradeModalSeenFailure = (
	state,
	{meta: {scope = PreferencesScopes.User}}
) => state.mergeIn([scope, 'upgradeModalSeen'], {error: true, loading: false});

const actionHandlers = {
	[actionTypes.ADD_DISTRIBUTION_TABS_SUCCESS]: handleDistributionTabSuccess,
	[actionTypes.ADD_DISTRIBUTION_TABS_REQUEST]: handleDistributionTabRequest,
	[actionTypes.ADD_DISTRIBUTION_TABS_FAILURE]: handleDistributionTabFailure,
	[actionTypes.FETCH_DEFAULT_CHANNEL_ID_SUCCESS]: handleDefaultChannelIdSuccess,
	[actionTypes.FETCH_DEFAULT_CHANNEL_ID_REQUEST]: handleDefaultChannelIdRequest,
	[actionTypes.FETCH_DEFAULT_CHANNEL_ID_FAILURE]: handleDefaultChannelIdFailure,
	[actionTypes.FETCH_DISTRIBUTION_TABS_SUCCESS]: handleDistributionTabSuccess,
	[actionTypes.FETCH_DISTRIBUTION_TABS_REQUEST]: handleDistributionTabRequest,
	[actionTypes.FETCH_DISTRIBUTION_TABS_FAILURE]: handleDistributionTabFailure,
	[actionTypes.FETCH_UPGRADE_MODAL_SEEN_SUCCESS]: handleUpgradeModalSeenSuccess,
	[actionTypes.FETCH_UPGRADE_MODAL_SEEN_REQUEST]: handleUpgradeModalSeenRequest,
	[actionTypes.FETCH_UPGRADE_MODAL_SEEN_FAILURE]: handleUpgradeModalSeenFailure,
	[actionTypes.REMOVE_DISTRIBUTION_TABS_SUCCESS]: handleDistributionTabSuccess,
	[actionTypes.REMOVE_DISTRIBUTION_TABS_REQUEST]: handleDistributionTabRequest,
	[actionTypes.REMOVE_DISTRIBUTION_TABS_FAILURE]: handleDistributionTabFailure,
	[actionTypes.UPDATE_DEFAULT_CHANNEL_ID_SUCCESS]: handleDefaultChannelIdSuccess,
	[actionTypes.UPDATE_DEFAULT_CHANNEL_ID_REQUEST]: handleDefaultChannelIdRequest,
	[actionTypes.UPDATE_DEFAULT_CHANNEL_ID_FAILURE]: handleDefaultChannelIdFailure,
	[actionTypes.UPDATE_UPGRADE_MODAL_SEEN_SUCCESS]: handleUpgradeModalSeenSuccess,
	[actionTypes.UPDATE_UPGRADE_MODAL_SEEN_REQUEST]: handleUpgradeModalSeenRequest,
	[actionTypes.UPDATE_UPGRADE_MODAL_SEEN_FAILURE]: handleUpgradeModalSeenFailure
};

export default createReducer(new Map(), actionHandlers);
